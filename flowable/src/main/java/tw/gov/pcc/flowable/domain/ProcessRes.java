package tw.gov.pcc.flowable.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class ProcessRes {
    private Integer signatureStatus;
    private String message;
    private Date time=new Date();
    private Boolean isComplete;
    public ProcessRes(Integer signatureStatus, String message) {
        this.signatureStatus = signatureStatus;
        this.message = message;
    }

}
