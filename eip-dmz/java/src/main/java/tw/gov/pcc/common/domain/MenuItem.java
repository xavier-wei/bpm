package tw.gov.pcc.common.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 使用者功能選單項目
 *
 * @author Goston
 */
public class MenuItem implements Serializable {
    private static final long serialVersionUID = -7091480687177810884L;
    private String levelNo; // Menu Level
    private String functionName; // 中文功能名稱
    private String url; // URL
    private List<MenuItem> sub; // 子選單項目

    public MenuItem() {
    }

    public MenuItem(String levelNo, String functionName, String url) {
        this.levelNo = levelNo;
        this.functionName = functionName;
        this.url = url;
    }

    public void addSub(MenuItem menuItem) {
        if (sub == null) sub = new ArrayList<MenuItem>();
        sub.add(menuItem);
    }

    public String getLevelNo() {
        return this.levelNo;
    }

    public void setLevelNo(final String levelNo) {
        this.levelNo = levelNo;
    }

    public String getFunctionName() {
        return this.functionName;
    }

    public void setFunctionName(final String functionName) {
        this.functionName = functionName;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(final String url) {
        this.url = url;
    }

    public List<MenuItem> getSub() {
        return this.sub;
    }

    public void setSub(final List<MenuItem> sub) {
        this.sub = sub;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof MenuItem)) return false;
        final MenuItem other = (MenuItem) o;
        if (!other.canEqual(this)) return false;
        final Object this$levelNo = this.getLevelNo();
        final Object other$levelNo = other.getLevelNo();
        if (!Objects.equals(this$levelNo, other$levelNo)) return false;
        final Object this$functionName = this.getFunctionName();
        final Object other$functionName = other.getFunctionName();
        if (!Objects.equals(this$functionName, other$functionName))
            return false;
        final Object this$url = this.getUrl();
        final Object other$url = other.getUrl();
        if (!Objects.equals(this$url, other$url)) return false;
        final Object this$sub = this.getSub();
        final Object other$sub = other.getSub();
        return Objects.equals(this$sub, other$sub);
    }

    protected boolean canEqual(final Object other) {
        return other instanceof MenuItem;
    }

    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $levelNo = this.getLevelNo();
        result = result * PRIME + ($levelNo == null ? 43 : $levelNo.hashCode());
        final Object $functionName = this.getFunctionName();
        result = result * PRIME + ($functionName == null ? 43 : $functionName.hashCode());
        final Object $url = this.getUrl();
        result = result * PRIME + ($url == null ? 43 : $url.hashCode());
        final Object $sub = this.getSub();
        result = result * PRIME + ($sub == null ? 43 : $sub.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "MenuItem(levelNo=" + this.getLevelNo() + ", functionName=" + this.getFunctionName() + ", url=" + this.getUrl() + ", sub=" + this.getSub() + ")";
    }
}
