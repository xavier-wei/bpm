package tw.gov.pcc.eip.domain;


import lombok.Data;
import lombok.NoArgsConstructor;
import tw.gov.pcc.common.annotation.LogField;
import tw.gov.pcc.common.annotation.PkeyField;
import tw.gov.pcc.common.annotation.Table;
import tw.gov.pcc.eip.dao.OsquestionDao;

import java.time.LocalDateTime;

/**
 * 意見調查問題資料
 *
 * @Author Weith
 */
@Data
@NoArgsConstructor
@Table(OsquestionDao.TABLE_NAME)
public class Osquestion {
    @PkeyField("OSFORMNO")
    @LogField("OSFORMNO")
    private String osformno;
    @PkeyField("QSEQNO")
    @LogField("QSEQNO")
    private Integer qseqno;
    @LogField("SECTITLESEQ")
    private Integer sectitleseq;
    @LogField("SECTITLE")
    private String sectitle;
    @LogField("TOPICSEQ")
    private Integer topicseq;
    @LogField("TOPIC")
    private String topic;
    @LogField("OPTIONTYPE")
    private String optiontype;
    @LogField("ISREQUIRED")
    private String isrequired;
    @LogField("CREUSER")
    private String creuser;
    @LogField("CREDT")
    private LocalDateTime credt;
    @LogField("UPDUSER")
    private String upduser;
    @LogField("UPDDT")
    private LocalDateTime upddt;
    private Integer rowspan;
}
