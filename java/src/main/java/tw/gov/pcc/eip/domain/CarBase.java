package tw.gov.pcc.eip.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import tw.gov.pcc.common.annotation.LogField;
import tw.gov.pcc.common.annotation.PkeyField;
import tw.gov.pcc.common.annotation.Table;
import tw.gov.pcc.eip.dao.DriverBaseDao;

import java.io.Serializable;

/**
 * 
 *
 * @author ivan
 */
@Table(DriverBaseDao.TABLE_NAME)
@NoArgsConstructor
@Data
public class CarBase implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     *主鍵值1
     */
    @LogField
    @PkeyField
    private String carno1;

    /**
     *主鍵值2
     */
    @LogField
    @PkeyField
    private String carno2;

    /**
     *財產編號
     */
    @LogField
    private String owned;

    /**
     *車輛種類: 1:4人座2:7人座
     */
    @LogField
    private String cartype;

    /**
     *車輛廠牌
     */
    @LogField
    private String carsource;

    /**
     *購置年份
     */
    @LogField
    private String caryear;

    /**
     *顏色
     */
    @LogField
    private String carcolor;

    /**
     *排放量cc
     */
    @LogField
    private String ccsize;

    /**
     *首長專用車: y是/n否
     */
    @LogField
    private String boss_mk;

    /**
     *首長
     */
    @LogField
    private String bossname;

    /**
     *車輛狀態1可使用2不可使用-首長3維修中4報廢
     */
    @LogField
    private String carstatus;

    /**
     *保險公司
     */
    @LogField
    private String insurance_company;

    /**
     *主鍵值
     */
    @LogField
    private String insurance_start;

    /**
     *保險期間(起)
     */
    @LogField
    private String insurance_end;

    /**
     *保險期間(迄)
     */
    @LogField
    private String cre_user;

    /**
     *
     */
    @LogField
    private String cre_datetime;

    /**
     *
     */
    @LogField
    private String upd_user;

    /**
     *
     */
    @LogField
    private String upd_datetime;

    /**
     *主鍵值2
     */
    @LogField
    private String carno;


    private String name;//for eip07w040
    private String cellphone;//for eip07w040
    
}