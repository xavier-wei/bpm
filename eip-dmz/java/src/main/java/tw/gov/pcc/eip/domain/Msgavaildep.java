package tw.gov.pcc.eip.domain;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;
import tw.gov.pcc.common.annotation.LogField;
import tw.gov.pcc.common.annotation.PkeyField;
import tw.gov.pcc.common.annotation.Table;
import tw.gov.pcc.eip.dao.MsgavaildepDao;

/**
 * 訊息分眾表
 * 
 * @author vita
 *
 */
@Table(MsgavaildepDao.TABLE_NAME)
@NoArgsConstructor
@Data
public class Msgavaildep implements Serializable {

    private static final long serialVersionUID = 4800184343133573768L;

    /** 項次 */
    @PkeyField("FSEQ")
    @LogField("FSEQ")
    private String fseq; // 系統自動編號
    /** 分眾 */
    @LogField("AVAILABLEDEP")
    private String availabledep; // 0:全會人員 1:企劃處 2:技術處 3:工管處 4:秘書處 5:申訴會 6:工程鑑定技術委員會 7:中央採購稽核小組 8:人事室 9:主計室
                                 // 10:法規會

}
