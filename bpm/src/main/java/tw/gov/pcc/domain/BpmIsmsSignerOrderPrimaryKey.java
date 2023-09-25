package tw.gov.pcc.domain;

import java.io.Serializable;
import java.util.Objects;

public class BpmIsmsSignerOrderPrimaryKey implements Serializable {
    private String bpmIsmsForm;
    private Integer sort;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BpmIsmsSignerOrderPrimaryKey that = (BpmIsmsSignerOrderPrimaryKey) o;
        return Objects.equals(bpmIsmsForm, that.bpmIsmsForm) && Objects.equals(sort, that.sort);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bpmIsmsForm, sort);
    }

    @Override
    public String toString() {
        return "BpmIsmsSignerOrderPrimaryKey{" +
            "bpmIsmsForm='" + bpmIsmsForm + '\'' +
            ", sort=" + sort +
            '}';
    }
}
