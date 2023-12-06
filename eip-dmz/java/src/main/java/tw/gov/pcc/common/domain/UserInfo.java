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

    String getEmpId();

    void setEmpId(String empId);

    String getLoginIP();

    void setLoginIP(String loginIP);

    String getLoginDate();

    void setLoginDate(String loginDate);

    String getLoginTime();

    void setLoginTime(String loginTime);

    String getTel1();

    void setTel1(String tel1);

    String getTel2();

    void setTel2(String tel2);

    String getTitleId();

    void setTitleId(String titleId);

    String getLineToken();

    void setLineToken(String lineToken);

    String getEmail();

    void setEmail(String email);
    
    String getOrgId();
    
    void setOrgId(String orgId);
    
    String getDeptName();
    
    void setDeptName(String deptName);
    
}
