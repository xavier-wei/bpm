package tw.gov.pcc.common.domain;

import java.io.Serializable;
import java.util.regex.Pattern;

/**
 * Domain Object for 系統功能項目
 *
 * @author Goston
 */
public class SystemFunction implements Serializable {
    private static final long serialVersionUID = -5698100370527440902L;
    private String itemId; // 系統項目代碼
    private String itemName; // 系統項目名稱
    private String url; // 系統項目 URL & 頁面程式代號
    private String urlDesc; // URL 描述 & 頁面程式描述
    private Pattern urlPattern; // URL Regex Pattern

    public String getItemId() {
        return this.itemId;
    }

    public String getItemName() {
        return this.itemName;
    }

    public String getUrl() {
        return this.url;
    }

    public String getUrlDesc() {
        return this.urlDesc;
    }

    public Pattern getUrlPattern() {
        return this.urlPattern;
    }

    public void setItemId(final String itemId) {
        this.itemId = itemId;
    }

    public void setItemName(final String itemName) {
        this.itemName = itemName;
    }

    public void setUrl(final String url) {
        this.url = url;
    }

    public void setUrlDesc(final String urlDesc) {
        this.urlDesc = urlDesc;
    }

    public void setUrlPattern(final Pattern urlPattern) {
        this.urlPattern = urlPattern;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof SystemFunction)) return false;
        final SystemFunction other = (SystemFunction) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$itemId = this.getItemId();
        final Object other$itemId = other.getItemId();
        if (this$itemId == null ? other$itemId != null : !this$itemId.equals(other$itemId)) return false;
        final Object this$itemName = this.getItemName();
        final Object other$itemName = other.getItemName();
        if (this$itemName == null ? other$itemName != null : !this$itemName.equals(other$itemName)) return false;
        final Object this$url = this.getUrl();
        final Object other$url = other.getUrl();
        if (this$url == null ? other$url != null : !this$url.equals(other$url)) return false;
        final Object this$urlDesc = this.getUrlDesc();
        final Object other$urlDesc = other.getUrlDesc();
        if (this$urlDesc == null ? other$urlDesc != null : !this$urlDesc.equals(other$urlDesc)) return false;
        final Object this$urlPattern = this.getUrlPattern();
        final Object other$urlPattern = other.getUrlPattern();
        if (this$urlPattern == null ? other$urlPattern != null : !this$urlPattern.equals(other$urlPattern)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof SystemFunction;
    }

    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $itemId = this.getItemId();
        result = result * PRIME + ($itemId == null ? 43 : $itemId.hashCode());
        final Object $itemName = this.getItemName();
        result = result * PRIME + ($itemName == null ? 43 : $itemName.hashCode());
        final Object $url = this.getUrl();
        result = result * PRIME + ($url == null ? 43 : $url.hashCode());
        final Object $urlDesc = this.getUrlDesc();
        result = result * PRIME + ($urlDesc == null ? 43 : $urlDesc.hashCode());
        final Object $urlPattern = this.getUrlPattern();
        result = result * PRIME + ($urlPattern == null ? 43 : $urlPattern.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "SystemFunction(itemId=" + this.getItemId() + ", itemName=" + this.getItemName() + ", url=" + this.getUrl() + ", urlDesc=" + this.getUrlDesc() + ", urlPattern=" + this.getUrlPattern() + ")";
    }

    public SystemFunction() {
    }
}
