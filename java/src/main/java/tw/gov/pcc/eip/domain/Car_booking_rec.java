package tw.gov.pcc.eip.domain;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;
import tw.gov.pcc.common.annotation.LogField;
import tw.gov.pcc.common.annotation.PkeyField;
import tw.gov.pcc.common.annotation.Table;
import tw.gov.pcc.eip.dao.Car_booking_recDao;

/**
* CAR_BOOKING_REC
*
* @author swho
*/
@Data
@NoArgsConstructor
@Table(Car_booking_recDao.TABLE_NAME)
public class Car_booking_rec implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * APPLYID
     */
    @PkeyField
    @LogField
    private String applyid;
    
    /**
     * CARNO1
     */
    @LogField
    private String carno1;
        
    /**
     * CARNO2
     */
    @LogField
    private String carno2;
        
    /**
     * USING_DATE
     */
    @LogField
    private String using_date;
        
    /**
     * USING_REC
     */
    @LogField
    private String using_rec;
        
    /**
     * COMBINE_MK
     */
    @LogField
    private String combine_mk;
        
    /**
     * COMBINE_REASON
     */
    @LogField
    private String combine_reason;
        
    /**
     * COMBINE_APPLYID
     */
    @LogField
    private String combine_applyid;
        
    /**
     * CRE_USER
     */
    @LogField
    private String cre_user;
        
    /**
     * CRE_DATETIME
     */
    @LogField
    private String cre_datetime;
        
    /**
     * UPD_USER
     */
    @LogField
    private String upd_user;
        
    /**
     * UPD_DATETIME
     */
    @LogField
    private String upd_datetime;
        
}