package tw.gov.pcc.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@IdClass(EipBpmSignStatusPrimaryKey.class)
@Table(schema = "dbo",name = "EIP_BPM_SIGN_STATUS")
public class EipBpmSignStatus implements Serializable {
    @Id
    @Column(name = "FORM_ID",nullable = false)
    private String formId;

    @Id
    @Column(name = "PROCESS_INSTANCE_ID",nullable = false)
    private String processInstanceId;

    @Id
    @Column(name = "TASK_ID",nullable = false)
    private String taskId;

    @Id
    @Column(name = "SIGNER_ID",nullable = false)
    private String signerId;

    @Column(name = "SIGNER", nullable = false)
    private String signer;

    @Column(name = "STATUS", nullable = false)
    private String status;

    @Column(name = "OPINION", nullable = false)
    private String opinion;

    @Column(name = "SIGNING_DATETIME", nullable = false)
    private Timestamp signingDatetime;
}
