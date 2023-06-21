package tw.gov.pcc.eip.framework.helper;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import tw.gov.pcc.common.helper.EnvFacadeHelper;
import tw.gov.pcc.eip.dao.EipcodeDao;
import tw.gov.pcc.eip.util.BeanUtility;
import tw.gov.pcc.eip.util.ExceptionUtility;
import tw.gov.pcc.eip.util.ObjectUtility;

import javax.mail.internet.InternetAddress;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

/**
 * Email 輔助類別
 */
@Component
@Slf4j
public class MailHelper {

    private static final String MAIL_ENCODING = "UTF-8";

    private static final String DEFAULT_SENDER_TITLE = "行政院公共工程委員會";
    private static final String DEFAULT_SENDER_TEMPLATE = "pcc-%s@ms.pcc.gov.tw";
    private final JavaMailSenderImpl mailSender;

    private final EipcodeDao eipcodeDao;

    public MailHelper(EipcodeDao eipcodeDao) {
        this.eipcodeDao = eipcodeDao;
        mailSender = new JavaMailSenderImpl();
        mailSender.setDefaultEncoding("UTF-8");
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.starttls.required", "true");
        props.put("mail.debug", "true");
        props.put("mail.smtp.ssl.trust", "210.69.177.66");

        try {
            eipcodeDao.findByCodeKind("SYS_SMTP")
                    .forEach(x -> BeanUtility.setBeanProperty(mailSender,
                            ObjectUtility.normalizeObject(StringUtils.lowerCase(x.getScodekind())),
                            ObjectUtility.normalizeObject(x.getCodename())));
        } catch (Exception e) {
            log.error("MailHelper 初始化失敗 {}", ExceptionUtility.getStackTrace(e));
        }
    }


    /**
     * 寄發 Email (簡單文字內容)
     *
     * @param subject     Mail 標題 (必填)
     * @param mailContent Mail 訊息內容
     * @param receiver    收件人，如有多個收件人以「,」隔開 (必填)
     * @param sender      寄件人 Email，若傳入 <code>null</code> 會以系統預設寄件人寄發
     * @param senderTitle 寄件人 Title，若傳入 <code>null</code> 會以系統預設寄件人 Title 寄發
     */
    public void sendEmail(final String subject, final String mailContent, final String receiver, final String sender, final String senderTitle) {
        String content = StringUtils.isBlank(mailContent) ? "無內文" : mailContent;
        sendEmail(subject, content, receiver, sender, senderTitle, "SimpleMessage.html");
    }

    /**
     * 寄發 Email
     *
     * @param subject     Mail 標題 (必填)
     * @param receiver    收件人，如有多個收件人以「,」隔開 (必填)
     * @param sender      寄件人 Email，若傳入 <code>null</code> 會以系統預設寄件人寄發
     * @param senderTitle 寄件人 Title，若傳入 <code>null</code> 會以系統預設寄件人 Title 寄發
     * @param template    Template 檔 (必填)
     */
    public void sendEmail(final String subject, final String mailContent, final String receiver, final String sender, final String senderTitle, final String template) {
        String defaultSender = getDefaultSender();
        if (StringUtils.isBlank(subject) || mailContent == null || StringUtils.isBlank(receiver) || StringUtils.isBlank(template))
            return;

        MimeMessagePreparator preparator = mimeMessage -> {
            String[] sendTo = StringUtils.split(receiver, ",");
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
            message.setTo(sendTo);
            message.setFrom(new InternetAddress((StringUtils.isNotBlank(sender) ? sender : defaultSender), (StringUtils.isNotBlank(senderTitle) ? senderTitle : DEFAULT_SENDER_TITLE), MAIL_ENCODING));
            message.setSubject(subject);
            String templateString;
            try (InputStream is = new ClassPathResource(String.format("mailTemplate/%s", template)).getInputStream()) {
                templateString = StreamUtils.copyToString(is, StandardCharsets.UTF_8);
            }
            String htmlContent = StringUtils.replace(templateString, "${message}", mailContent);

            message.setText(htmlContent, true);
        };

        this.mailSender.send(preparator);
    }

    private String getDefaultSender() {
        return StringUtils.lowerCase(String.format(DEFAULT_SENDER_TEMPLATE, EnvFacadeHelper.getSystemId()));
    }

    /**
     * 寄發 Email(包含文字訊息及附檔)
     *
     * @param subject     Mail 標題 (必填)
     * @param receiver    收件人，如有多個收件人以「,」隔開 (必填)
     * @param sender      寄件人 Email，若傳入 <code>null</code> 會以系統預設寄件人寄發
     * @param senderTitle 寄件人 Title，若傳入 <code>null</code> 會以系統預設寄件人 Title 寄發
     * @param fileName    附件檔案名稱 (必填)
     * @param file        附件檔(二進位檔)(必填)
     * @param mailContent 信件內容
     */
    public void sendEmailWithMessageFile(final String subject, final String receiver, final String sender,
                                         final String senderTitle, final String fileName, final byte[] file, final String mailContent) {

        String defaultSender = getDefaultSender();
        if (StringUtils.isBlank(subject) || StringUtils.isBlank(receiver) || StringUtils.isBlank(fileName) || file == null) {
            return;
        }
        MimeMessagePreparator preparator = mimeMessage -> {
            String[] sendTo = StringUtils.split(receiver, ",");
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true);

            message.setTo(sendTo);
            message.setFrom(new InternetAddress((StringUtils.isNotBlank(sender) ? sender : defaultSender),
                    (StringUtils.isNotBlank(senderTitle) ? senderTitle : DEFAULT_SENDER_TITLE), MAIL_ENCODING));
            message.setSubject(subject);

            String templateString;
            try (InputStream is = new ClassPathResource(String.format("mailTemplate/%s", "SimpleMessage.html")).getInputStream()) {
                templateString = StreamUtils.copyToString(is, StandardCharsets.UTF_8);
            }
            String htmlContent = StringUtils.replace(templateString, "${message}", mailContent);

            message.setText(htmlContent, true);

            InputStreamSource source = new ByteArrayResource(file);
            message.addAttachment(fileName, source);
        };
        this.mailSender.send(preparator);
    }


}
