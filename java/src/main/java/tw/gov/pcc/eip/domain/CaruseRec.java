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
public class CaruseRec implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     *主鍵值1
     */
    @LogField
    private String carno1;

    /**
     *主鍵值2
     */
    @LogField
    private String carno2;

    /**
     *派車單號
     */
    @LogField
    private String applyid;

    /**
     *用車日期
     */
    @LogField
    private String use_date;
    /**
     *用車時間起
     */
    @LogField
    private String use_time_s;

    /**
     *用車時間迄
     */
    @LogField
    private String use_time_e;

    /**
     *出場里程數
     */
    @LogField
    private String milage_start;

    /**
     *回場里程數
     */
    @LogField
    private String milage_end;

    /**
     *行駛公里數
     */
    @LogField
    private String milage;

    /**
     *耗油量
     */
    @LogField
    private String gas_used ;
    
    /**
     *開車時間
     */
    @LogField
    private String driver_time_s ;
    
    /**
     *到達時間
     */
    @LogField
    private String driver_time_e ;
    
    /**
     *行駛路線
     */
    @LogField
    private String drive_road ;

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