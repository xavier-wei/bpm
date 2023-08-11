package tw.gov.pcc.eip.domain;

import lombok.Data;
import tw.gov.pcc.common.annotation.LogField;
import tw.gov.pcc.common.annotation.PkeyField;
import tw.gov.pcc.common.annotation.Table;
import tw.gov.pcc.eip.dao.View_cpape05mDao;
import java.io.Serializable;
import java.math.BigDecimal;

/**
* 人員資料表
*
* @author swho
*/
@Table(View_cpape05mDao.TABLE_NAME)
@Data
public class View_cpape05m implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 員工編號
     */
    @LogField
    private String pecard;
    
    /**
     * 人員所屬機關代碼
     */
    @LogField
    private String peorg;
        
    /**
     * 機關名稱
     */
    @LogField
    private String org_name;
        
    /**
     * 人員所屬單位代碼
     */
    @LogField
    private String peunit;
        
    /**
     * 單位名稱
     */
    @LogField
    private String unit_name;
        
    /**
     * 身分證字號
     */
    @LogField
    private String peidno;
        
    /**
     * 登入帳號
     */
    @LogField
    @PkeyField
    private String login_id;
        
    /**
     * 姓名
     */
    @LogField
    private String pename;
        
    /**
     * Email
     */
    @LogField
    private String email;
        
    /**
     * 職稱代碼
     */
    @LogField
    private String petit;
        
    /**
     * 職稱
     */
    @LogField
    private String title;
        
    /**
     * 休假年資-年
     */
    @LogField
    private String pehyear;
        
    /**
     * 休假年資-月
     */
    @LogField
    private String pehmon;
        
    /**
     * 休假天數
     */
    @LogField
    private BigDecimal pehday;
        
    /**
     * 性別
     */
    @LogField
    private String pesex;
        
    /**
     * 出生年月日
     */
    @LogField
    private String pebirthd;
        
    /**
     * 官職等代碼
     */
    @LogField
    private String pecrkcod;
        
    /**
     * 官職等
     */
    @LogField
    private String crkcod_name;
        
    /**
     * 職務類別代碼
     */
    @LogField
    private String pememcod;
        
    /**
     * 職務類別
     */
    @LogField
    private String memcod_name;
        
    /**
     * 每小時加班費
     */
    @LogField
    private BigDecimal peoverhfee;
        
    /**
     * 到職日期
     */
    @LogField
    private String peactdate;
        
    /**
     * 離職日期
     */
    @LogField
    private String pelevdate;
        
    /**
     * 俸點
     */
    @LogField
    private String pepoint;
        
    /**
     * 年制別
     */
    @LogField
    private String peykind;
        
    /**
     * 本年度保留休假天數
     */
    @LogField
    private BigDecimal perday;
        
    /**
     * 前一年休假保留天數
     */
    @LogField
    private BigDecimal perday1;
        
    /**
     * 前二年休假保留天數
     */
    @LogField
    private BigDecimal perday2;
        
    /**
     * 初任公職日
     */
    @LogField
    private String pefstdate;
        
    /**
     * 得併計休假年資-年
     */
    @LogField
    private String pehyear2;
        
    /**
     * 得併計休假年資-月
     */
    @LogField
    private String pehmon2;
        
    /**
     * 訓練期滿日
     */
    @LogField
    private String petraining;
        
    /**
     * 地域加給
     */
    @LogField
    private BigDecimal pearea;
        
    /**
     * 公務電話：區碼
     */
    @LogField
    private String ct_area_code;
        
    /**
     * 公務電話：聯絡電話
     */
    @LogField
    private String ct_tel;
        
    /**
     * 公務電話：分機號碼
     */
    @LogField
    private String ct_ext;
        
    /**
     * 公務電話：手機號碼
     */
    @LogField
    private String ct_mobile;
        
    /**
     * 住家地址
     */
    @LogField
    private String ct_home_addr;
        
    /**
     * 是否具外國國籍
     */
    @LogField
    private BigDecimal is_nationality;
        
    /**
     * 異動時間
     */
    @LogField
    private String peupdate;
        
}