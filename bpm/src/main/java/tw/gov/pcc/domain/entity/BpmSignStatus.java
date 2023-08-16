package tw.gov.pcc.domain.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;


@Entity
@Table(schema = "dbo",name = "BPM_SIGN_STATUS")
public class BpmSignStatus implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "UUID")
    private UUID uuid;

    @Column(name = "FORM_ID",nullable = false)
    private String formId;

    @Column(name = "PROCESS_INSTANCE_ID",nullable = false)
    private String processInstanceId;

    @Column(name = "TASK_ID",nullable = false)
    private String taskId;

    @Column(name = "TASK_NAME",nullable = false)
    private String taskName;

    @Column(name = "SIGNER_ID",nullable = false)
    private String signerId;

    @Column(name = "SIGNER", nullable = false)
    private String signer;

    @Column(name = "SIGN_UNIT", nullable = false)
    private String signUnit;

    @Column(name = "SIGN_RESULT", nullable = false)
    private String signResult;

    @Column(name = "OPINION")
    private String opinion;
    @Column(name="DIRECTIONS")
    private String directions;

    @Column(name = "SIGNING_DATETIME")
    private Timestamp signingDatetime;

    public BpmSignStatus() {
    }

    public BpmSignStatus(UUID uuid, String formId, String processInstanceId, String taskId, String signerId, String signer, String signUnit, String signResult, String opinion, String directions, Timestamp signingDatetime) {
        this.uuid = uuid;
        this.formId = formId;
        this.processInstanceId = processInstanceId;
        this.taskId = taskId;
        this.signerId = signerId;
        this.signer = signer;
        this.signUnit = signUnit;
        this.signResult = signResult;
        this.opinion = opinion;
        this.directions = directions;
        this.signingDatetime = signingDatetime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BpmSignStatus that = (BpmSignStatus) o;
        return Objects.equals(uuid, that.uuid) && Objects.equals(formId, that.formId) && Objects.equals(processInstanceId, that.processInstanceId) && Objects.equals(taskId, that.taskId) && Objects.equals(signerId, that.signerId) && Objects.equals(signer, that.signer) && Objects.equals(signUnit, that.signUnit) && Objects.equals(signResult, that.signResult) && Objects.equals(opinion, that.opinion) && Objects.equals(directions, that.directions) && Objects.equals(signingDatetime, that.signingDatetime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, formId, processInstanceId, taskId, signerId, signer, signUnit, signResult, opinion, directions, signingDatetime);
    }

    @Override
    public String toString() {
        return "BpmSignStatus{" +
            "uuid=" + uuid +
            ", formId='" + formId + '\'' +
            ", processInstanceId='" + processInstanceId + '\'' +
            ", taskId='" + taskId + '\'' +
            ", signerId='" + signerId + '\'' +
            ", signer='" + signer + '\'' +
            ", signUnit='" + signUnit + '\'' +
            ", signResult='" + signResult + '\'' +
            ", opinion='" + opinion + '\'' +
            ", directions='" + directions + '\'' +
            ", signingDatetime=" + signingDatetime +
            '}';
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getFormId() {
        return formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getSignerId() {
        return signerId;
    }

    public void setSignerId(String signerId) {
        this.signerId = signerId;
    }

    public String getSigner() {
        return signer;
    }

    public void setSigner(String signer) {
        this.signer = signer;
    }

    public String getSignUnit() {
        return signUnit;
    }

    public void setSignUnit(String signUnit) {
        this.signUnit = signUnit;
    }

    public String getSignResult() {
        return signResult;
    }

    public void setSignResult(String signResult) {
        this.signResult = signResult;
    }

    public String getOpinion() {
        return opinion;
    }

    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }

    public String getDirections() {
        return directions;
    }

    public void setDirections(String directions) {
        this.directions = directions;
    }

    public Timestamp getSigningDatetime() {
        return signingDatetime;
    }

    public void setSigningDatetime(Timestamp signingDatetime) {
        this.signingDatetime = signingDatetime;
    }
}
