//package tw.gov.pcc.service;
//
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.stereotype.Service;
//
//@Service
//public class MailService {
//    private final JavaMailSender javaMailSender;
//
//    public MailService(JavaMailSender javaMailSender) {
//        this.javaMailSender = javaMailSender;
//    }
//
//
//    //表單狀態：處理完畢
//    public String sendMailAfterProcessCompleted() {
//        SimpleMailMessage message = new SimpleMailMessage();
//
//        message.setFrom("noreply@baeldung.com");
//        message.setTo("shzerokimo@gmail.com");
//        String title = "處理完畢通知--L414-網路服務連結申請單(%s,%s),%s";
//        message.setSubject(title);
//        String text = "主旨：" +
//            "    EIP表單管理郵件通知 \n" +
//            "    表單：L414-網路服務連結申請單 \n" +
//            "    主旨：處理完畢通知--%s(%s,%s),{%s}\n" +
//            "    申請人：%s\n" +
//            "    申請時間：%s";
//        message.setText(text);
//
//        javaMailSender.send(message);
//        return "test";
//    }
//}
