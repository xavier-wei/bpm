package tw.gov.pcc.domain;


import java.io.Serializable;

public class BpmSignerListPrimaryKey implements Serializable {

    private String formId;
    private String taskName;

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
}
