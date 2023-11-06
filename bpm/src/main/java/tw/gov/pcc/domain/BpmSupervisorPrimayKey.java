package tw.gov.pcc.domain;

import java.io.Serializable;
import java.util.Objects;

public class BpmSupervisorPrimayKey implements Serializable {
    private String appUnit;
    private String appTitle;

    public BpmSupervisorPrimayKey() {
    }

    public BpmSupervisorPrimayKey(String appUnit, String appTitle) {
        this.appUnit = appUnit;
        this.appTitle = appTitle;
    }

    public String getAppUnit() {
        return appUnit;
    }

    public void setAppUnit(String appUnit) {
        this.appUnit = appUnit;
    }

    public String getAppTitle() {
        return appTitle;
    }

    public void setAppTitle(String appTitle) {
        this.appTitle = appTitle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BpmSupervisorPrimayKey that = (BpmSupervisorPrimayKey) o;
        return Objects.equals(appUnit, that.appUnit) && Objects.equals(appTitle, that.appTitle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(appUnit, appTitle);
    }

    @Override
    public String toString() {
        return "BpmSupervisorPrimayKey{" +
            "appUnit='" + appUnit + '\'' +
            ", appTitle='" + appTitle + '\'' +
            '}';
    }
}
