package tw.gov.pcc.eip.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;
import tw.gov.pcc.common.annotation.LogField;
import tw.gov.pcc.common.annotation.Table;
import tw.gov.pcc.eip.dao.DeptsDao;

/**
 * 
 *
 * @author ivan
 */
@Table(DeptsDao.TABLE_NAME)
@NoArgsConstructor
@Data
public class Depts implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * item_id
     */
    @LogField
    private String dept_id; 
    
    /**
     * dept_name
     */
    @LogField
    private String dept_name; 
    
    /**
     * sort_order
     */
    @LogField
    private Integer sort_order; 

    /**
     * dept_desc
     */
    @LogField
    private String dept_desc;  
    
    /**
     * is_valid
     */
    @LogField
    private String is_valid;  
    
    /**
     * dept_id_p
     */
    @LogField
    private String dept_id_p;  
    
    /**
     * CREATE_USER_ID
     */
    @LogField
    private String create_user_id;

    /**
     * CREATE_TIMESTAMP
     */
    @LogField
    private LocalDateTime create_timestamp;

    /**
     * MODIFY_USER_ID
     */
    @LogField
    private String modify_user_id;

    /**
     * MODIFY_TIMESTAMP
     */
    @LogField
    private LocalDateTime modify_timestamp;
    
    /**
     * fromhr
     */
    @LogField
    private String fromhr;

}