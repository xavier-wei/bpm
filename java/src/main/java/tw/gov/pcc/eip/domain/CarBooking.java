package tw.gov.pcc.eip.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import tw.gov.pcc.common.annotation.LogField;
import tw.gov.pcc.common.annotation.Table;
import tw.gov.pcc.eip.dao.CarBookingDao;
import tw.gov.pcc.eip.dao.DriverBaseDao;

import java.io.Serializable;

/**
 * 
 *
 * @author York
 */
@Table(CarBookingDao.TABLE_NAME)
@NoArgsConstructor
@Data
public class CarBooking implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     *主鍵值(如:DC20230629001)
     */
    @LogField
    private String applyid;


    /**
     *
     */
    @LogField
    private String apply_user;


    /**
     *
     */
    @LogField
    private String apply_date;


    /**
     *
     */
    @LogField
    private String apply_dept;


    /**
     *
     */
    @LogField
    private String apply_memo;


    /**
     *
     */
    @LogField
    private String using_date;


    /**
     *
     */
    @LogField
    private String using_time_s;


    /**
     *
     */
    @LogField
    private String using_time_e;


    /**
     *使用位元0無使用1已使用
     */
    @LogField
    private String using;


    /**
     *目的地
     */
    @LogField
    private String destination;


    /**
     *
     */
    @LogField
    private String apply_car_type;


    /**
     *
     */
    @LogField
    private String num_of_people;


    /**
     *
     */
    @LogField
    private String carprocess_status;


    /**
     *
     */
    @LogField
    private String carno1;


    /**
     *
     */
    @LogField
    private String carno2;


    /**
     *
     */
    @LogField
    private String name;


    /**
     *
     */
    @LogField
    private String cellphone;


    /**
     *
     */
    @LogField
    private String cartype;


    /**
     *
     */
    @LogField
    private String carcolor;


    /**
     *
     */
    @LogField
    private String change_mk;


    /**
     *
     */
    @LogField
    private String change_count;


    /**
     *
     */
    @LogField
    private String change_reason;


    /**
     *
     */
    @LogField
    private String b_apply_memo;


    /**
     *
     */
    @LogField
    private String b_apply_time_s;


    /**
     *
     */
    @LogField
    private String b_apply_time_e;


    /**
     *
     */
    @LogField
    private String b_using;


    /**
     *
     */
    @LogField
    private String b_destination;


    /**
     *
     */
    @LogField
    private String b_apply_car_type;


    /**
     *
     */
    @LogField
    private String b_num_of_people;


    /**
     *
     */
    @LogField
    private String reconfirm_mk;


    /**
     *
     */
    @LogField
    private String reconfirm_user;


    /**
     *
     */
    @LogField
    private String reconfirm_date;


    /**
     *
     */
    @LogField
    private String reconfirm_mk2;


    /**
     *
     */
    @LogField
    private String reconfirm_user2;

    /**
     *
     */
    @LogField
    private String reconfirm_date2;

    /**
     *
     */
    @LogField
    private String combine_mk;


    /**
     *
     */
    @LogField
    private String combine_applyid;


    /**
     *
     */
    @LogField
    private String combine_reason;


    /**
     *
     */
    @LogField
    private String print_mk;


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

    /**
     *用車起:時
     */
    @LogField
    private String starH;

    /**
     *用車起:分
     */
    @LogField
    private String starM;

    /**
     *用車(迄):時
     */
    @LogField
    private String endH;

    /**
     *用車(迄):分
     */
    @LogField
    private String endM;


    @LogField
    private String codeName;



}