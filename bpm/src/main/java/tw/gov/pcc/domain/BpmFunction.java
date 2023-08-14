package tw.gov.pcc.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A BpmFunction.
 */
@Entity
@Table(name = "BPM_FUNCTION")
public class BpmFunction implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * type: bigint
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * type: varchar(20)
     */
    @NotNull
    @Size(max = 20)
    @Column(name = "function_id", length = 20, nullable = false, unique = true)
    private String functionId;

    /**
     * type: nvarchar(20)
     */
    @NotNull
    @Size(max = 100)
    @Column(name = "function_name", length = 20, nullable = false)
    private String functionName;

    /**
     * type: nvarchar(500)
     */
    @NotNull
    @Size(max = 500)
    @Column(name = "function_descript", length = 500, nullable = false)
    private String functionDescript;

    /**
     * type: varchar(500)
     */
    @NotNull
    @Size(max = 500)
    @Column(name = "function_path", length = 500, nullable = true)
    private String functionPath;

    /**
     * type: varchar(1)
     */
    @NotNull
    @Size(max = 1)
    @Column(name = "function_category", length = 1, nullable = false)
    private String functionCategory;

    /**
     * type: decimal(3,0)
     */
    @NotNull
    @Min(value = 0)
    @Column(name = "sort_no", nullable = false)
    private int sortNo;

    /**
     * type: varchar(20)
     */
    @NotNull
    @Size(max = 20)
    @Column(name = "master_function_id", length = 20, nullable = true)
    private String masterFunctionId;

    /**
     * type: varchar(1)
     */
    @NotNull
    @Size(max = 1)
    @Column(name = "status", length = 1, nullable = false)
    private String status;

    /**
     * type: varchar(20)
     */
    @NotNull
    @Size(max = 20)
    @Column(name = "update_user", length = 20, nullable = false)
    private String updateUser;

    /**
     * type: datetime
     */
    @NotNull
    @Column(name = "update_time", nullable = false)
    private Instant updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFunctionId() {
        return functionId;
    }

    public void setFunctionId(String functionId) {
        this.functionId = functionId;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public String getFunctionDescript() {
        return functionDescript;
    }

    public void setFunctionDescript(String functionDescript) {
        this.functionDescript = functionDescript;
    }

    public String getFunctionPath() {
        return functionPath;
    }

    public void setFunctionPath(String functionPath) {
        this.functionPath = functionPath;
    }

    public String getFunctionCategory() {
        return functionCategory;
    }

    public void setFunctionCategory(String functionCategory) {
        this.functionCategory = functionCategory;
    }


    public int getSortNo() {
        return sortNo;
    }

    public void setSortNo(int sortNo) {
        this.sortNo = sortNo;
    }

    public String getMasterFunctionId() {
        return masterFunctionId;
    }

    public void setMasterFunctionId(String masterFunctionId) {
        this.masterFunctionId = masterFunctionId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public Instant getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Instant updateTime) {
        this.updateTime = updateTime;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BpmFunction)) {
            return false;
        }
        return id != null && id.equals(((BpmFunction) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BpmFunction{" +
            "id=" + getId() +
            ", functionId='" + getFunctionId() + "'" +
            ", functionName='" + getFunctionName() + "'" +
            ", functionDescript='" + getFunctionDescript() + "'" +
            ", functionPath='" + getFunctionPath() + "'" +
            ", functionCategory='" + getFunctionCategory() + "'" +
            ", sortNo=" + getSortNo() +
            ", masterFunctionId='" + getMasterFunctionId() + "'" +
            ", status='" + getStatus() + "'" +
            ", updateUser='" + getUpdateUser() + "'" +
            ", updateTime='" + getUpdateTime() + "'" +
            "}";
    }
}
