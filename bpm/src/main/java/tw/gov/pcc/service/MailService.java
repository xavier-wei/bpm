package tw.gov.pcc.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {
    private final JavaMailSender javaMailSender;

    public MailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }


    //表單狀態：處理完畢
    public String sendMailAfterProcessCompleted() {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom("noreply@baeldung.com");
        message.setTo("shzerokimo@gmail.com");
        //標題：
        // 處理完畢通知--L414-網路服務連結申請單({填表人},L414-1121008-0003),{完成日期}
        //
        //
        //主旨：
        //EIP表單管理郵件通知
        //表單：L414-網路服務連結申請單
        //主旨：處理完畢通知--L414-網路服務連結申請單({填表人},L414-1121008-0003),{完成日期}
        //申請人：{申請人}
        //申請時間：2023/10/08 下午 02:45:32
        //表單狀態：處理完畢
        String title = "處理完畢通知--L414-網路服務連結申請單(%s,%s),%s";
        message.setSubject(title);
        String text = "主旨：" +
            "    EIP表單管理郵件通知 \n" +
            "    表單：L414-網路服務連結申請單 \n" +
            "    主旨：處理完畢通知--%s(%s,%s),{%s}\n" +
            "    申請人：%s\n" +
            "    申請時間：%s";
        message.setText(text);

        javaMailSender.send(message);
        return "test";
    }
}
