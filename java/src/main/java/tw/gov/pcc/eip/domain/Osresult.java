package tw.gov.pcc.eip.domain;


import lombok.Data;
import lombok.NoArgsConstructor;
import tw.gov.pcc.common.annotation.LogField;
import tw.gov.pcc.common.annotation.PkeyField;
import tw.gov.pcc.common.annotation.Table;
import tw.gov.pcc.eip.dao.OsresultDao;

import java.time.LocalDateTime;

/**
 * 意見調查結果資料
 *
 * @Author Weith
 */
@Data
@NoArgsConstructor
@Table(OsresultDao.TABLE_NAME)
public class Osresult {
    @PkeyField("OSFORMNO")
    @LogField("OSFORMNO")
    private String osformno;
    @PkeyField("WRISEQ")
    @LogField("WRISEQ")
    private Integer wriseq;
    @LogField("WRIJOGTITLE")
    private String wrijogtitle;
    @LogField("WRINAME")
    private String wriname;
    @LogField("WRIAD")
    private String wriad;
    @LogField("WRICONTENT")
    private String wricontent;
    @LogField("CREUSER")
    private String creuser;
    @LogField("CREDT")
    private LocalDateTime credt;
    @LogField("UPDUSER")
    private String upduser;
    @LogField("UPDDT")
    private LocalDateTime upddt;
}
