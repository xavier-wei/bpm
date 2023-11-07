package tw.gov.pcc.service.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

public class BpmSignStatusDTO implements Serializable {

    private UUID uuid;

    private String formId;

    private String processInstanceId;

    private String taskId;

    private String taskName;

    private String signerId;

    private String signer;

    private String signUnit;

    private String signResult;

    private String opinion;

    private String directions;

    private Timestamp signingDatetime;

    public BpmSignStatusDTO() {
    }
    public BpmSignStatusDTO(UUID uuid, String formId, String processInstanceId, String taskId, String signerId, String signer, String signUnit, String signResult, String opinion, String directions, Timestamp signingDatetime) {
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

    public BpmSignStatusDTO(CompleteReqDTO completeReqDTO, String formId) {
        BpmSignStatusDTO bpmSignStatusDTO = new BpmSignStatusDTO();
        this.formId = formId;
        this.processInstanceId = completeReqDTO.getProcessInstanceId();
        this.taskId = completeReqDTO.getTaskId();
        this.taskName = completeReqDTO.getTaskName();
        this.signerId = completeReqDTO.getSignerId();
        this.signer = completeReqDTO.getSigner();
        this.signUnit = completeReqDTO.getSignUnit();
        this.opinion = completeReqDTO.getOpinion();
        this.directions = completeReqDTO.getDirections();
        this.signingDatetime = Timestamp.valueOf(LocalDateTime.now());
        if (completeReqDTO.getVariables().isEmpty()) {
            this.signResult = "1";
        } else {
            Set<String> strings = completeReqDTO.getVariables().keySet();
            Iterator<String> iterator = strings.iterator();
            if (iterator.hasNext()) {
                this.signResult=(String) completeReqDTO.getVariables().get(iterator.next());
            }
        }
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

    @Override
    public String toString() {
        return "BpmSignStatusDTO{" +
            "uuid=" + uuid +
            ", formId='" + formId + '\'' +
            ", processInstanceId='" + processInstanceId + '\'' +
            ", taskId='" + taskId + '\'' +
            ", taskName='" + taskName + '\'' +
            ", signerId='" + signerId + '\'' +
            ", signer='" + signer + '\'' +
            ", signUnit='" + signUnit + '\'' +
            ", signResult='" + signResult + '\'' +
            ", opinion='" + opinion + '\'' +
            ", directions='" + directions + '\'' +
            ", signingDatetime=" + signingDatetime +
            '}';
    }
}
