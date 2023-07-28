package tw.gov.pcc.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link tw.gov.pcc.domain.BpmFunction} entity.
 */
public class BpmFunctionDTO implements Serializable {

    /**
     * type: bigint
     */
    @NotNull
    private Long id;

    /**
     * type: varchar(20)
     */
    @NotNull
    @Size(max = 20)
    private String functionId;

    /**
     * type: nvarchar(20)
     */
    @NotNull
    @Size(max = 20)
    private String functionName;

    /**
     * type: nvarchar(500)
     */
    @NotNull
    @Size(max = 500)
    private String functionDescript;

    /**
     * type: varchar(500)
     */
    @NotNull
    @Size(max = 500)
    private String functionPath;

    /**
     * type: varchar(1)
     */
    @NotNull
    @Size(max = 1)
    private String functionCategory;

    /**
     * type: decimal(3,0)
     */
    @NotNull
    @DecimalMin(value = "0")
    @DecimalMax(value = "255")
    private BigDecimal sortNo;

    /**
     * type: varchar(20)
     */
    @NotNull
    @Size(max = 20)
    private String masterFunctionId;

    /**
     * type: varchar(1)
     */
    @NotNull
    @Size(max = 1)
    private String status;

    /**
     * type: varchar(20)
     */
    @NotNull
    @Size(max = 20)
    private String updateUser;

    /**
     * type: datetime
     */
    @NotNull
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

    public BigDecimal getSortNo() {
        return sortNo;
    }

    public void setSortNo(BigDecimal sortNo) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BpmFunctionDTO)) {
            return false;
        }

        BpmFunctionDTO bpmFunctionDTO = (BpmFunctionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, bpmFunctionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BpmFunctionDTO{" +
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
