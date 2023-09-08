package tw.gov.pcc.eip.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import tw.gov.pcc.common.annotation.LogField;
import tw.gov.pcc.common.annotation.Table;
import tw.gov.pcc.eip.dao.PositionDao;

import java.io.Serializable;

/**
 * POSITION
 *
 * @author swho
 */
@Table(PositionDao.TABLE_NAME)
@NoArgsConstructor
@Data
public class Position implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * POSID
     */
    @LogField
    private String posid;

    /**
     * FID
     */
    @LogField
    private String fid;

    /**
     * POSNAME
     */
    @LogField
    private String posname;

    /**
     * CLASS
     */
    @LogField
    private String cLass;

    /**
     * DEPART
     */
    @LogField
    private String depart;

    /**
     * DEPUTY
     */
    @LogField
    private String deputy;

    /**
     * ID
     */
    @LogField
    private String id;

    /**
     * PREVID
     */
    @LogField
    private String previd;

    /**
     * FLOWGROUP
     */
    @LogField
    private String flowgroup;

    /**
     * ORGCODE
     */
    @LogField
    private String orgcode;

}