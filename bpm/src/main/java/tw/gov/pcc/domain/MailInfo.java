package tw.gov.pcc.domain;

import lombok.Data;

@Data
public class MailInfo{
    // 表單名稱，表單編號，申請人，狀態
    private String formName;
    private String formId;
    private String applier;
    private String applierId;
    private String status;
    private Boolean isSend;

    public MailInfo(String formName, String formId, String applier, String applierId, String status, Boolean isSend) {
        this.formName = formName;
        this.formId = formId;
        this.applier = applier;
        this.applierId = applierId;
        this.status = status;
        this.isSend = isSend;
    }
}
