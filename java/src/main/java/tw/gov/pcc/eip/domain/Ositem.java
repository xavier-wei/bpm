package tw.gov.pcc.eip.domain;


import lombok.Data;
import lombok.NoArgsConstructor;
import tw.gov.pcc.common.annotation.LogField;
import tw.gov.pcc.common.annotation.PkeyField;
import tw.gov.pcc.common.annotation.Table;
import tw.gov.pcc.eip.dao.OsitemDao;

import java.time.LocalDateTime;

/**
 * 意見調查問題選項
 *
 * @Author Weith
 */
@Data
@NoArgsConstructor
@Table(OsitemDao.TABLE_NAME)
public class Ositem {
    @PkeyField("OSFORMNO")
    @LogField("OSFORMNO")
    private String osformno;
    @PkeyField("ISEQNO")
    @LogField("ISEQNO")
    private Integer iseqno;
    @LogField("QSEQNO")
    private Integer qseqno;
    @LogField("ITEMSEQ")
    private Integer itemseq;
    @LogField("DESC")
    private String desc;
    @LogField("ISADDTEXT")
    private String isaddtext;
    @LogField("CREUSER")
    private String creuser;
    @LogField("CREDT")
    private LocalDateTime credt;
    @LogField("UPDUSER")
    private String upduser;
    @LogField("UPDDT")
    private LocalDateTime upddt;
}
