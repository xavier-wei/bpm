package tw.gov.pcc.common.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 使用者功能選單
 *
 * @author Goston
 */
public class Menu implements Serializable {
    private static final long serialVersionUID = -2974842076132900032L;
    private List<MenuItem> menuItemList;

    public Menu() {
        menuItemList = new ArrayList<MenuItem>();
    }

    public List<MenuItem> getMenuItemList() {
        return this.menuItemList;
    }

    public void setMenuItemList(final List<MenuItem> menuItemList) {
        this.menuItemList = menuItemList;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Menu)) return false;
        final Menu other = (Menu) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$menuItemList = this.getMenuItemList();
        final Object other$menuItemList = other.getMenuItemList();
        if (this$menuItemList == null ? other$menuItemList != null : !this$menuItemList.equals(other$menuItemList)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Menu;
    }

    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $menuItemList = this.getMenuItemList();
        result = result * PRIME + ($menuItemList == null ? 43 : $menuItemList.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "Menu(menuItemList=" + this.getMenuItemList() + ")";
    }
}
