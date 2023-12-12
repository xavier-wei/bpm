package tw.gov.pcc.service;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import tw.gov.pcc.domain.EipMailData;
import tw.gov.pcc.domain.MailInfo;
import tw.gov.pcc.domain.User;
import tw.gov.pcc.repository.EipMailDataRepository;

import java.util.HashMap;

@Service
public class MailService {

    private final EipMailDataRepository eipMailDataRepository;
    private final UserService userService;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final String mailTitle = "ISMS表單處理完畢通知：%s({%s},{%s}) %s)"; // 表單名稱，申請人，申請人ID，狀態
    private final String mailContent = "%s \n" +
        "表單單號: %s \n" +
        "申請人：{ %s}, { %s}\n" +
        "狀態：  %s，詳情請參閱表單查詢之簽核狀態\n" ; // 表單名稱，表單編號，申請人，申請人ID，狀態

    public MailService(EipMailDataRepository eipMailDataRepository, UserService userService, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.eipMailDataRepository = eipMailDataRepository;
        this.userService = userService;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public void sendMail(MailInfo mailInfo) {
        User userInfo = userService.getUserInfo(mailInfo.getApplierId());
        EipMailData eipMailData = new EipMailData();
        eipMailData.setMailId(getNewMailId());
        eipMailData.setEmail(userInfo.getEmail());
        eipMailData.setSubject(String.format(mailTitle, mailInfo.getFormName(), mailInfo.getApplier(), mailInfo.getApplierId(), mailInfo.getStatus()));
        eipMailData.setMessage(String.format(mailContent, mailInfo.getFormName(), mailInfo.getFormId(), mailInfo.getApplier(), mailInfo.getApplierId(), mailInfo.getStatus()));
        eipMailDataRepository.save(eipMailData);
    }

    public String getNewMailId() {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        sql.append("FORMAT (getdate(), 'yyyyMMddHHmmss') + RIGHT(REPLICATE('0', 6) + CAST(NEXT VALUE FOR EIP_COMMON as NVARCHAR), 6) ");
        return namedParameterJdbcTemplate.queryForObject(sql.toString(), new HashMap<>(), String.class);
    }

}
