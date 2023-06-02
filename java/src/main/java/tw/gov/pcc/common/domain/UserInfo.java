package tw.gov.pcc.common.domain;

import java.io.Serializable;

public interface UserInfo extends Serializable {

    /**
     * 取得登入日期格式化字串
     *
     * @return 登入日期格式化字串
     */
    String getLoginDateString();

    /**
     * 取得登入時間格式化字串
     *
     * @return 登入時間格式化字串
     */
    String getLoginTimeString();

    String getUserId();

    void setUserId(String userId);

    String getUserName();

    void setUserName(String userName);

    String getDeptId();

    void setDeptId(String deptId);

    String getEmpNo();

    void setEmpNo(String empNo);

    String getLoginIP();

    void setLoginIP(String loginIP);

    String getLoginDate();

    void setLoginDate(String loginDate);

    String getLoginTime();

    void setLoginTime(String loginTime);

}
