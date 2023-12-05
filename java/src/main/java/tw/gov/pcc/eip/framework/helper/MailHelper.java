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
 * 針對EIP進行修改
 * 不要直接呼叫，透過MailService呼叫
 *
 * @author swho
 */
@Component
@Slf4j
public class MailHelper {

    private static final String MAIL_ENCODING = "UTF-8";
    private static final String DEFAULT_SENDER_TITLE = "行政院公共工程委員會";
    private final JavaMailSenderImpl mailSender;

    public MailHelper(EipcodeDao eipcodeDao) {
        mailSender = new JavaMailSenderImpl();
        mailSender.setDefaultEncoding(MAIL_ENCODING);
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.debug", "true");

        try {
            eipcodeDao.findByCodeKind("SYS_SMTP")
                    .forEach(x -> {
                        //全轉小寫也試一次
                        BeanUtility.setBeanProperty(mailSender,
                                ObjectUtility.normalizeObject(StringUtils.lowerCase(x.getScodekind())),
                                ObjectUtility.normalizeObject(x.getCodename()));
                        //原始大小
                        BeanUtility.setBeanProperty(mailSender,
                                ObjectUtility.normalizeObject(x.getScodekind()),
                                ObjectUtility.normalizeObject(x.getCodename()));
                    });
        } catch (Exception e) {
            log.error("MailHelper 初始化失敗 {}", ExceptionUtility.getStackTrace(e));
        }

        props.put("mail.smtp.ssl.trust", mailSender.getHost()); //always trust smtp from db

    }

    private String getDefaultSender() {
        if(StringUtils.contains(mailSender.getUsername(),"@")){
            return mailSender.getUsername();
        }else{
            return mailSender.getUsername()+"@mail.pcc.gov.tw"; 
        }
    }

    /**
     * 寄發 Email(包含文字訊息或文字和附檔)
     *
     * @param subject     Mail 標題 (必填)
     * @param receiver    收件人，如有多個收件人以「,」隔開 (必填)
     * @param sender      寄件人 Email，若傳入 <code>null</code> 會以系統預設寄件人寄發
     * @param senderTitle 寄件人 Title，若傳入 <code>null</code> 會以系統預設寄件人 Title 寄發
     * @param mailContent Mail 訊息內容
     * @param fileName    附件檔案名稱 (選填)
     * @param file        附件檔(二進位檔)(選填，沒有檔案就<code>null</code>，會忽略fileName，不包含附件)
     */
    public void sendEmail(final String subject, final String receiver, final String sender,
                          final String senderTitle, final String mailContent, final String fileName, byte[] file) {

        String defaultSender = getDefaultSender();
        if (StringUtils.isBlank(subject) || StringUtils.isBlank(receiver)) {
            return;
        }
        MimeMessagePreparator preparator = mimeMessage -> {
            String[] sendTo = StringUtils.split(receiver, ",");
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, file != null);

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

            if (file == null) {
                return;
            }

            InputStreamSource source = new ByteArrayResource(file);
            message.addAttachment(fileName, source);
        };
        this.mailSender.send(preparator);
    }


}
