package tw.gov.pcc.domain;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "BPM_SIGNER_LIST")
@IdClass(BpmSignerListPrimaryKey.class)
public class BpmSignerList {

    @Id
    @Column(name = "FORM_ID")
    private String formId;
    @Id
    @Column(name = "TASK_NAME")
    private String taskName;

    @Column(name = "DEPT_ID")
    private String deptId;

    @Column(name = "EMP_IDS")
    private String empIds;

    @Column(name = "EMP_NAMES")
    private String empNames;

    @Column(name = "CREATE_TIME")
    private Timestamp createTime;
    public String getFormId() {
        return formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getEmpIds() {
        return empIds;
    }

    public void setEmpIds(String empIds) {
        this.empIds = empIds;
    }

    public String getEmpNames() {
        return empNames;
    }

    public void setEmpNames(String empNames) {
        this.empNames = empNames;
    }

    @Override
    public String toString() {
        return "BpmSignerList{" +
            "formId='" + formId + '\'' +
            ", taskName='" + taskName + '\'' +
            ", deptId='" + deptId + '\'' +
            ", empIds='" + empIds + '\'' +
            ", empNames='" + empNames + '\'' +
            '}';
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }
}
