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
public class GasRec implements Serializable {

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
     *加油日期
     */
    @LogField
    private String fuel_date;

    /**
     *加油時間
     */
    @LogField
    private String fuel_time;

    /**
     *加油金額
     */
    @LogField
    private String gas_money;

    /**
     *加油量
     */
    @LogField
    private String gas_amount;

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