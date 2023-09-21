package tw.gov.pcc.eip.framework.domain;

import java.io.Serializable;

/**
 * 用於存放下拉式選單的 Label 及 Value
 *
 * @author Goston
 */
public class LabelValueBean implements Serializable {
    private static final long serialVersionUID = -7681325789929738922L;
    private String label;
    private String value;

    public LabelValueBean() {
    }

    public LabelValueBean(String label, String value) {
        super();
        this.label = label;
        this.value = value;
    }

    public String getLabel() {
        return this.label;
    }

    public String getValue() {
        return this.value;
    }

    public void setLabel(final String label) {
        this.label = label;
    }

    public void setValue(final String value) {
        this.value = value;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof LabelValueBean)) return false;
        final LabelValueBean other = (LabelValueBean) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$label = this.getLabel();
        final Object other$label = other.getLabel();
        if (this$label == null ? other$label != null : !this$label.equals(other$label)) return false;
        final Object this$value = this.getValue();
        final Object other$value = other.getValue();
        if (this$value == null ? other$value != null : !this$value.equals(other$value)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof LabelValueBean;
    }

    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $label = this.getLabel();
        result = result * PRIME + ($label == null ? 43 : $label.hashCode());
        final Object $value = this.getValue();
        result = result * PRIME + ($value == null ? 43 : $value.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "LabelValueBean(label=" + this.getLabel() + ", value=" + this.getValue() + ")";
    }
}
