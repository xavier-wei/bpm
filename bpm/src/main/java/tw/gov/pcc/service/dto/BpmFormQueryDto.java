package tw.gov.pcc.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class BpmFormQueryDto implements Serializable {

    private String formId;//表單
    private String processInstanceStatus;//處理狀態
    private String formType;//表單分類
    private LocalDate dateStart;//起
    private LocalDate dateEnd;//迄
    private String unit;//部門
    private String appName;//申請者

    public String getFormId() {
        return formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public String getProcessInstanceStatus() {
        return processInstanceStatus;
    }

    public void setProcessInstanceStatus(String processInstanceStatus) {
        this.processInstanceStatus = processInstanceStatus;
    }

    public String getFormType() {
        return formType;
    }

    public void setFormType(String formType) {
        this.formType = formType;
    }

    public LocalDate getDateStart() {
        return dateStart;
    }

    public void setDateStart(LocalDate dateStart) {
        this.dateStart = dateStart;
    }

    public LocalDate getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(LocalDate dateEnd) {
        this.dateEnd = dateEnd;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BpmFormQueryDto that = (BpmFormQueryDto) o;
        return Objects.equals(formId, that.formId) && Objects.equals(processInstanceStatus, that.processInstanceStatus) && Objects.equals(formType, that.formType) && Objects.equals(dateStart, that.dateStart) && Objects.equals(dateEnd, that.dateEnd) && Objects.equals(unit, that.unit) && Objects.equals(appName, that.appName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(formId, processInstanceStatus, formType, dateStart, dateEnd, unit, appName);
    }

    @Override
    public String toString() {
        return "BpmFormQueryDto{" +
                "formId='" + formId + '\'' +
                ", processInstanceStatus='" + processInstanceStatus + '\'' +
                ", formType='" + formType + '\'' +
                ", dateStart='" + dateStart + '\'' +
                ", dateEnd='" + dateEnd + '\'' +
                ", unit='" + unit + '\'' +
                ", appName='" + appName + '\'' +
                '}';
    }
}
