package tw.gov.pcc.eip.domain;


import lombok.Data;
import lombok.NoArgsConstructor;
import tw.gov.pcc.common.annotation.LogField;
import tw.gov.pcc.common.annotation.PkeyField;
import tw.gov.pcc.common.annotation.Table;
import tw.gov.pcc.eip.dao.OsclassDao;

import java.time.LocalDateTime;

/**
 * 意見調查分類資料
 *
 * @Author Weith
 */
@Data
@NoArgsConstructor
@Table(OsclassDao.TABLE_NAME)
public class Osclass {
    @PkeyField("OSCCODE")
    @LogField("OSCCODE")
    private Integer osccode;
    @LogField("OSCNAME")
    private String oscname;
    @LogField("CREUSER")
    private String creuser;
    @LogField("CREDT")
    private LocalDateTime credt;
    @LogField("UPDUSER")
    private String upduser;
    @LogField("UPDDT")
    private LocalDateTime upddt;
}
