package tw.gov.pcc.eip.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import tw.gov.pcc.common.annotation.LogField;
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
public class DriverBbase implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     *主鍵值
     */
    @LogField
    private String driverid;

    /**
     *駕駛人姓名
     */
    @LogField
    private String name;


    /**
     *員工編號
     */
    @LogField
    private String staffno;

    /**
     *身分證號(駕照證號)
     */
    @LogField
    private String id  ;



    /**
     *出生日期
     */
    @LogField
    private String brdte;

    /**
     *職稱(1:駕駛)
     */
    @LogField
    private String title;

    /**
     *手機號碼
     */
    @LogField
    private String cellphone;

    /**
     *電話
     */
    @LogField
    private String phone;

    /**
     *在職註記: y:是/n:否
     */
    @LogField
    private String still_work;

    /**
     *到職日期
     */
    @LogField
    private String startwork_date;

    /**
     *離職日期
     */
    @LogField
    private String endwork_date;

    /**
     *駕照到期日
     */
    @LogField
    private String licence_expire_date;

    /**
     *駕照種類 1.小客車2.小客車
     */
    @LogField
    private String  licence_car_type;

    /**
     *車牌號碼(預定保管車號)
     */
    @LogField
    private String carno1;

    /**
     *車牌號碼(預定保管車號)
     */
    @LogField
    private String carno2;

    /**
     *代理人
     */
    @LogField
    private String temp_staff;

    /**
     *
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



}