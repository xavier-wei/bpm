package tw.gov.pcc.eip.domain;


import lombok.Data;
import lombok.NoArgsConstructor;
import tw.gov.pcc.common.annotation.LogField;
import tw.gov.pcc.common.annotation.PkeyField;
import tw.gov.pcc.common.annotation.Table;
import tw.gov.pcc.eip.dao.OsformdataDao;

import java.time.LocalDateTime;

/**
 * 意見調查表單資料
 *
 * @Author Weith
 */
@Data
@NoArgsConstructor
@Table(OsformdataDao.TABLE_NAME)
public class Osformdata {
    @PkeyField("OSFORMNO")
    @LogField("OSFORMNO")
    private String osformno;
    @LogField("OSCCODE")
    private Integer osccode;
    @LogField("TOPICNAME")
    private String topicname;
    @LogField("OSFMDT")
    private LocalDateTime osfmdt;
    @LogField("OSENDT")
    private LocalDateTime osendt;
    @LogField("TOPICDESC")
    private String topicdesc;
    @LogField("ORGANIZER")
    private String organizer;
    @LogField("PROMPTMSG")
    private String promptmsg;
    @LogField("ISDISSTATIC")
    private String isdisstatic;
    @LogField("ISLIMITONE")
    private String islimitone;
    @LogField("ISANONYMOUS")
    private String isanonymous;
    @LogField("LIMITVOTE")
    private String limitvote;
    @LogField("MAILSUBJECT")
    private String mailsubject;
    @LogField("MAILMSG")
    private String mailmsg;
    @LogField("STATUS")
    private String status;
    @LogField("CREUSER")
    private String creuser;
    @LogField("CREDT")
    private LocalDateTime credt;
    @LogField("UPDUSER")
    private String upduser;
    @LogField("UPDDT")
    private LocalDateTime upddt;
}
