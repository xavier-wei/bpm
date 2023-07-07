package tw.gov.pcc.eip.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tw.gov.pcc.common.annotation.LogField;
import tw.gov.pcc.common.annotation.PkeyField;
import tw.gov.pcc.common.annotation.Table;
import tw.gov.pcc.eip.dao.User_profileDao;

import java.io.Serializable;

/**
 * 使用者設定
 *
 * @author swho
 */
@Table(User_profileDao.TABLE_NAME)
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class User_profile implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 使用者代號
     */
    @PkeyField
    @LogField
    private String user_id;

    /**
     * JSON
     */
    @LogField
    private String profile;

}