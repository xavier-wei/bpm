package tw.gov.pcc.common.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import tw.gov.pcc.common.helper.EnvFacadeHelper;
import tw.gov.pcc.common.util.DateUtil;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Domain Object for 使用者<br>
 * Framework 須仰賴此類別實作之功能, 因此若各系統欲擴充使用者物件之功能需繼承此類別<br>
 *
 * @author Goston
 */
@Data
@NoArgsConstructor
public class FrameworkUserInfoBean implements UserInfo, Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 使用者代碼
     */
    private String userId;
    /**
     * 使用者名稱
     */
    private String userName;
    /**
     * 部門代碼
     */
    private String deptId;
    /**
     * 部門名稱
     */
    private String deptName;
    /**
     * 員工編號
     */
    private String empId;
    /**
     * 使用者 IP
     */
    private String loginIP;
    /**
     * 檢查資訊 Token
     */
    private String token;
    /**
     * 使用者可執行之所有系統項目代碼 (<code>ITEMS.ITEM_ID</code>)
     */
    private List<String> itemIdList;
    /**
     * for Framework Logging, 代表目前使用者作業之 Protal Log Id
     */
    private String sno;
    /**
     * for Framework Logging, 使用者目前執行的功能的 應用系統功能代號
     */
    private String apFunctionCode;
    /**
     * for Framework Logging, 使用者目前執行的功能的 應用系統功能名稱
     */
    private String apFunctionName;
    /**
     * for Framework Logging, 使用者目前執行的功能的 頁面程式代號 - 螢幕編號
     */
    private String progId;
    /**
     * 電話
     */
    private String tel1;
    /**
     * 分機
     */
    private String tel2;
    /**
     * 職稱代號
     */
    private String titleId;
    /**
     * LINE TOKEN
     */
    private String lineToken;
    /**
     * 電子信箱
     */
    private String email;
    /**
     * 機關代碼
     */
    private String orgId;

    /**
     * 登入日期
     */
    private String loginDate;
    /**
     * 登入時間
     */
    private String loginTime;
    /**
     * 系統要用的 Header 圖檔 (節日變換用)
     */
    private String headerFileName;
    /**
     * 使用者選單
     */
    private Menu menu;
    /**
     * 使用者選單 JSON 字串
     */
    private String menuJson;
    /**
     * 系統功能清單(檢核權限用)
     */
    private Map<String, SystemFunction> systemFunctionMap; // 系統功能清單
    /**
     * 系統功能子標題
     */
    private Map<String, String> subTitleMap; // 系統功能子標題

    public String getMenuJson() {
        if (menu != null && StringUtils.isBlank(menuJson)) {
            try {
                // JSON
                ObjectMapper mapper = new ObjectMapper();
                menuJson = mapper.writeValueAsString(menu);
            } catch (Exception e) {
            }
        }
        // Should not happen
        return menuJson;
    }

    /**
     * 使用者是否有執行某項功能之權限
     *
     * @param itemId 系統項目代碼
     * @return 有回傳 <code>true</code>; 沒有回傳 <code>false</code>
     */
    public boolean hasPrivilege(String itemId) {
        if (StringUtils.defaultString(itemId)
                .equals("")) return false;
        return itemIdList.contains(itemId) || StringUtils.defaultString(itemId).equals(EnvFacadeHelper.getSystemId());
    }

    /**
     * 取得登入日期格式化字串
     *
     * @return 登入日期格式化字串
     */
    public String getLoginDateString() {
        return DateUtil.formatChineseDateString(loginDate, false);
    }

    /**
     * 取得登入時間格式化字串
     *
     * @return 登入時間格式化字串
     */
    public String getLoginTimeString() {
        return DateUtil.formatTimeString(loginTime, false);
    }
}
