package tw.gov.pcc.service.dto;

import java.util.ArrayList;
import java.util.List;

public class MenuTreeDTO {

    private String functionId;

    private String functionName;

    private String functionDescript;

    private String functionPath;

    private String functionCategory;

    private Integer sortNo;

    private String masterFunctionId;

    private String status;

    private List<MenuTreeDTO> children = new ArrayList<>();


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

    public Integer getSortNo() {
        return sortNo;
    }

    public void setSortNo(Integer sortNo) {
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

    public List<MenuTreeDTO> getChildren() {
        return children;
    }

    public void setChildren(List<MenuTreeDTO> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "MenuTreeDTO{" +
            "functionId='" + functionId + '\'' +
            ", functionName='" + functionName + '\'' +
            ", functionDescript='" + functionDescript + '\'' +
            ", functionPath='" + functionPath + '\'' +
            ", functionCategory='" + functionCategory + '\'' +
            ", sortNo=" + sortNo +
            ", masterFunctionId='" + masterFunctionId + '\'' +
            ", status='" + status + '\'' +
            ", children=" + children +
            '}';
    }
}
