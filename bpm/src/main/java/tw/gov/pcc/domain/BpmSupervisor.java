package tw.gov.pcc.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "BPM_SUPERVISOR")
@IdClass(BpmSupervisorPrimayKey.class)
public class BpmSupervisor  implements Serializable {

    @Id
    private String appUnit;
    @Id
    private String appTitle;
    private String firstLayerUnit;
    private String firstLayerSupervisor;
    private String secondLayerUnit;
    private String secondLayerSupervisor;
    private String thirdLayerUnit;
    private String thirdLayerSupervisor;

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

    public String getFirstLayerUnit() {
        return firstLayerUnit;
    }

    public void setFirstLayerUnit(String firstLayerUnit) {
        this.firstLayerUnit = firstLayerUnit;
    }

    public String getFirstLayerSupervisor() {
        return firstLayerSupervisor;
    }

    public void setFirstLayerSupervisor(String firstLayerSupervisor) {
        this.firstLayerSupervisor = firstLayerSupervisor;
    }

    public String getSecondLayerUnit() {
        return secondLayerUnit;
    }

    public void setSecondLayerUnit(String secondLayerUnit) {
        this.secondLayerUnit = secondLayerUnit;
    }

    public String getSecondLayerSupervisor() {
        return secondLayerSupervisor;
    }

    public void setSecondLayerSupervisor(String secondLayerSupervisor) {
        this.secondLayerSupervisor = secondLayerSupervisor;
    }

    public String getThirdLayerUnit() {
        return thirdLayerUnit;
    }

    public void setThirdLayerUnit(String thirdLayerUnit) {
        this.thirdLayerUnit = thirdLayerUnit;
    }

    public String getThirdLayerSupervisor() {
        return thirdLayerSupervisor;
    }

    public void setThirdLayerSupervisor(String thirdLayerSupervisor) {
        this.thirdLayerSupervisor = thirdLayerSupervisor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BpmSupervisor that = (BpmSupervisor) o;
        return Objects.equals(appUnit, that.appUnit) && Objects.equals(appTitle, that.appTitle) && Objects.equals(firstLayerUnit, that.firstLayerUnit) && Objects.equals(firstLayerSupervisor, that.firstLayerSupervisor) && Objects.equals(secondLayerUnit, that.secondLayerUnit) && Objects.equals(secondLayerSupervisor, that.secondLayerSupervisor) && Objects.equals(thirdLayerUnit, that.thirdLayerUnit) && Objects.equals(thirdLayerSupervisor, that.thirdLayerSupervisor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(appUnit, appTitle, firstLayerUnit, firstLayerSupervisor, secondLayerUnit, secondLayerSupervisor, thirdLayerUnit, thirdLayerSupervisor);
    }

    @Override
    public String toString() {
        return "BpmSupervisor{" +
            "appUnit='" + appUnit + '\'' +
            ", appTitle='" + appTitle + '\'' +
            ", firstLayerUnit='" + firstLayerUnit + '\'' +
            ", firstLayerSupervisor='" + firstLayerSupervisor + '\'' +
            ", secondLayerUnit='" + secondLayerUnit + '\'' +
            ", secondLayerSupervisor='" + secondLayerSupervisor + '\'' +
            ", thirdLayerUnit='" + thirdLayerUnit + '\'' +
            ", thirdLayerSupervisor='" + thirdLayerSupervisor + '\'' +
            '}';
    }
}
