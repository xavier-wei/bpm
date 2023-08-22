package tw.gov.pcc.service.dto;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Objects;


public class CompleteReqDTO {

    private String signer;

    private String signerId;

    private String signUnit;
    private String directions;
    private String opinion;

    @NotNull
    private String processInstanceId;

    @NotNull
    private String taskId;

    @NotNull
    private String taskName;
    private HashMap<String, Object> variables;

    @NotNull
    private BpmIsmsL414DTO bpmIsmsL414DTO;

    private Boolean ipt; //用來判斷登入者使否為InfoTester，判斷是否儲存資推小組填寫的欄位

    // 科長簽核：  "chiefDecision": "0"(不同意) || "1" (同意) || "2"(補件)
    // 主管簽核：  "directorDecision": "0"(不同意) || "1" (同意) || "2"(補件)
    // 簡任技正簽核： "seniorTechSpecialistDecision": "0"(不同意) || "1" (同意))


    public CompleteReqDTO() {
    }


    public CompleteReqDTO(String signer, String signerId, String signUnit,Boolean ipt, String processInstanceId, String taskId, String taskName, HashMap<String, Object> variables, BpmIsmsL414DTO bpmIsmsL414DTO,String directions,String opinion) {
        this.signer = signer;
        this.signerId = signerId;
        this.signUnit = signUnit;
        this.processInstanceId = processInstanceId;
        this.taskId = taskId;
        this.taskName = taskName;
        this.variables = variables;
        this.bpmIsmsL414DTO = bpmIsmsL414DTO;
        this.directions = directions;
        this.opinion = opinion;
        this.ipt = ipt;
    }

    public String getSigner() {
        return signer;
    }

    public void setSigner(String signer) {
        this.signer = signer;
    }

    public String getSignerId() {
        return signerId;
    }

    public void setSignerId(String signerId) {
        this.signerId = signerId;
    }

    public String getSignUnit() {
        return signUnit;
    }

    public void setSignUnit(String signUnit) {
        this.signUnit = signUnit;
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

    public HashMap<String, Object> getVariables() {
        return variables;
    }

    public void setVariables(HashMap<String, Object> variables) {
        this.variables = variables;
    }

    public BpmIsmsL414DTO getBpmIsmsL414DTO() {
        return bpmIsmsL414DTO;
    }

    public void setBpmIsmsL414DTO(BpmIsmsL414DTO bpmIsmsL414DTO) {
        this.bpmIsmsL414DTO = bpmIsmsL414DTO;
    }

    public String getDirections() {
        return directions;
    }

    public void setDirections(String directions) {
        this.directions = directions;
    }

    public String getOpinion() {
        return opinion;
    }

    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }

    public Boolean getIpt() {
        return ipt;
    }

    public void setIpt(Boolean ipt) {
        this.ipt = ipt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompleteReqDTO that = (CompleteReqDTO) o;
        return Objects.equals(signer, that.signer) && Objects.equals(signerId, that.signerId) && Objects.equals(signUnit, that.signUnit) && Objects.equals(directions, that.directions) && Objects.equals(opinion, that.opinion) && Objects.equals(processInstanceId, that.processInstanceId) && Objects.equals(taskId, that.taskId) && Objects.equals(taskName, that.taskName) && Objects.equals(variables, that.variables) && Objects.equals(bpmIsmsL414DTO, that.bpmIsmsL414DTO) && Objects.equals(ipt, that.ipt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(signer, signerId, signUnit, directions, opinion, processInstanceId, taskId, taskName, variables, bpmIsmsL414DTO, ipt);
    }

    @Override
    public String toString() {
        return "CompleteReqDTO{" +
                "signer='" + signer + '\'' +
                ", signerId='" + signerId + '\'' +
                ", signUnit='" + signUnit + '\'' +
                ", directions='" + directions + '\'' +
                ", opinion='" + opinion + '\'' +
                ", processInstanceId='" + processInstanceId + '\'' +
                ", taskId='" + taskId + '\'' +
                ", taskName='" + taskName + '\'' +
                ", variables=" + variables +
                ", bpmIsmsL414DTO=" + bpmIsmsL414DTO +
                ", ipt=" + ipt +
                '}';
    }
}
