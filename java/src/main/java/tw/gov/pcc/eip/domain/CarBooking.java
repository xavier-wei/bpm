package tw.gov.pcc.eip.domain;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;
import tw.gov.pcc.common.annotation.LogField;
import tw.gov.pcc.common.annotation.Table;
import tw.gov.pcc.eip.dao.CarBookingDao;

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
     *核定時間(起)
     */
    @LogField
    private String approve_using_time_s;
    /**
     *核定時間(迄)
     */
    @LogField
    private String approve_using_time_e;
    /**
     *核定用車時間
     */
    @LogField
    private String approve_using;

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
    
    private String orderCondition;//eip07w070排序條件
    private String using_rec;//eip07w040
    private String using_date_s;
    private String using_date_e;

    private String carTyNm;
    private String usingStr;//eip07w040
    
	private String startuseH;//eip07w060 用車時間起 時
	private String startuseM;//eip07w060 用車時間起 分
	private String enduseH;//eip07w060 用車時間迄 時
	private String enduseM;//eip07w060 用車時間迄 分
	private String milageStart;//eip07w060 出場公里數
	private String milageEnd;//eip07w060 回場公里數
	private String milage;//eip07w060 行駛公里數
	private String gasUsed;//eip07w060 耗油公里數

    @LogField
    private String rmStarH;

    @LogField
    private String rmStarM;

    @LogField
    private String rmEndH;

    @LogField
    private String rmEndM;
    
    private String apply_deptStr;//eip07w040y
    
    private String usingTimeList;//eip07w040用於畫面顯示今日該車輛已派出的時間
    private String fillmk;//補單註記
    private Integer car_max_num;//乘車人數上限：抓車輛種類的第一碼，例如7人座：就抓7
}