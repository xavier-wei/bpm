package tw.gov.pcc.eip.framework.domain;

import lombok.Data;
import tw.gov.pcc.common.domain.UserInfo;
import tw.gov.pcc.eip.util.DateUtility;

import java.io.Serializable;

@Data
public class UserBean implements UserInfo, Serializable {
    private static final long serialVersionUID = 7245620384307255819L;
    private String userId; // 使用者代碼
    private String userName; // 使用者名稱
    private String deptId; // 部門代碼
    private String empId; // 員工編號
    private String loginIP; // 使用者 IP
    private String loginDate; // 登入日期
    private String loginTime; // 登入時間
    private String tel1; //電話
    private String tel2; //分機
    private String titleId; //職稱
    private String lineToken; //LineToken
    private String email; //EMAIL

    /**
     * 取得登入日期格式化字串
     *
     * @return 登入日期格式化字串
     */
    public String getLoginDateString() {
        return DateUtility.formatChineseDateString(loginDate, true);
    }

    /**
     * 取得登入時間格式化字串
     *
     * @return 登入時間格式化字串
     */
    public String getLoginTimeString() {
        return DateUtility.formatTimeString(loginTime, false);
    }

}
