package tw.gov.pcc.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "BPM_ISMS_SIGNER_ORDER")
@IdClass(BpmIsmsSignerOrderPrimaryKey.class)
public class BpmIsmsSignerOrder implements Serializable {

    @Id
    private String bpmIsmsForm;

    @Id
    private Integer sort;

    @Column(name = "TASK_NAME")
    private String taskName;

    public String getBpmIsmsForm() {
        return bpmIsmsForm;
    }

    public void setBpmIsmsForm(String bpmIsmsForm) {
        this.bpmIsmsForm = bpmIsmsForm;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BpmIsmsSignerOrder that = (BpmIsmsSignerOrder) o;
        return Objects.equals(bpmIsmsForm, that.bpmIsmsForm) && Objects.equals(sort, that.sort) && Objects.equals(taskName, that.taskName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bpmIsmsForm, sort, taskName);
    }
}
