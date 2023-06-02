package tw.gov.pcc.eip.framework.domain;

import tw.gov.pcc.eip.util.DateUtility;
import tw.gov.pcc.common.domain.UserInfo;
import java.io.Serializable;

public class UserBean implements UserInfo, Serializable {
    private static final long serialVersionUID = 7245620384307255819L;
    private String userId; // 使用者代碼
    private String userName; // 使用者名稱
    private String deptId; // 部門代碼
    private String empNo; // 員工編號
    private String loginIP; // 使用者 IP
    private String loginDate; // 登入日期
    private String loginTime; // 登入時間

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

    public String getUserId() {
        return this.userId;
    }

    public String getUserName() {
        return this.userName;
    }

    public String getDeptId() {
        return this.deptId;
    }

    public String getEmpNo() {
        return this.empNo;
    }

    public String getLoginIP() {
        return this.loginIP;
    }

    public String getLoginDate() {
        return this.loginDate;
    }

    public String getLoginTime() {
        return this.loginTime;
    }

    public void setUserId(final String userId) {
        this.userId = userId;
    }

    public void setUserName(final String userName) {
        this.userName = userName;
    }

    public void setDeptId(final String deptId) {
        this.deptId = deptId;
    }

    public void setEmpNo(final String empNo) {
        this.empNo = empNo;
    }

    public void setLoginIP(final String loginIP) {
        this.loginIP = loginIP;
    }

    public void setLoginDate(final String loginDate) {
        this.loginDate = loginDate;
    }

    public void setLoginTime(final String loginTime) {
        this.loginTime = loginTime;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof UserBean)) return false;
        final UserBean other = (UserBean) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$userId = this.getUserId();
        final Object other$userId = other.getUserId();
        if (this$userId == null ? other$userId != null : !this$userId.equals(other$userId)) return false;
        final Object this$userName = this.getUserName();
        final Object other$userName = other.getUserName();
        if (this$userName == null ? other$userName != null : !this$userName.equals(other$userName)) return false;
        final Object this$deptId = this.getDeptId();
        final Object other$deptId = other.getDeptId();
        if (this$deptId == null ? other$deptId != null : !this$deptId.equals(other$deptId)) return false;
        final Object this$empNo = this.getEmpNo();
        final Object other$empNo = other.getEmpNo();
        if (this$empNo == null ? other$empNo != null : !this$empNo.equals(other$empNo)) return false;
        final Object this$loginIP = this.getLoginIP();
        final Object other$loginIP = other.getLoginIP();
        if (this$loginIP == null ? other$loginIP != null : !this$loginIP.equals(other$loginIP)) return false;
        final Object this$loginDate = this.getLoginDate();
        final Object other$loginDate = other.getLoginDate();
        if (this$loginDate == null ? other$loginDate != null : !this$loginDate.equals(other$loginDate)) return false;
        final Object this$loginTime = this.getLoginTime();
        final Object other$loginTime = other.getLoginTime();
        if (this$loginTime == null ? other$loginTime != null : !this$loginTime.equals(other$loginTime)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof UserBean;
    }

    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $userId = this.getUserId();
        result = result * PRIME + ($userId == null ? 43 : $userId.hashCode());
        final Object $userName = this.getUserName();
        result = result * PRIME + ($userName == null ? 43 : $userName.hashCode());
        final Object $deptId = this.getDeptId();
        result = result * PRIME + ($deptId == null ? 43 : $deptId.hashCode());
        final Object $empNo = this.getEmpNo();
        result = result * PRIME + ($empNo == null ? 43 : $empNo.hashCode());
        final Object $loginIP = this.getLoginIP();
        result = result * PRIME + ($loginIP == null ? 43 : $loginIP.hashCode());
        final Object $loginDate = this.getLoginDate();
        result = result * PRIME + ($loginDate == null ? 43 : $loginDate.hashCode());
        final Object $loginTime = this.getLoginTime();
        result = result * PRIME + ($loginTime == null ? 43 : $loginTime.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "UserBean(userId=" + this.getUserId() + ", userName=" + this.getUserName() + ", deptId=" + this.getDeptId() + ", empNo=" + this.getEmpNo() + ", loginIP=" + this.getLoginIP() + ", loginDate=" + this.getLoginDate() + ", loginTime=" + this.getLoginTime() + ")";
    }

    public UserBean() {
    }
}
