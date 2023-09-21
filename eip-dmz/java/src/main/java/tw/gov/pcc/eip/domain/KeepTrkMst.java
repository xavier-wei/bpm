package tw.gov.pcc.eip.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import tw.gov.pcc.common.annotation.LogField;
import tw.gov.pcc.common.annotation.PkeyField;
import tw.gov.pcc.common.annotation.Table;
import tw.gov.pcc.eip.dao.KeepTrkMstDao;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 重要列管事項主檔
 * 
 * @author 2201009
 *
 */
@Table(KeepTrkMstDao.TABLE_NAME)
@NoArgsConstructor
@Data
public class KeepTrkMst implements Serializable {

    @PkeyField("TRKID")
    @LogField("TRKID")
    private	String trkID;	//列管事項編號

    @LogField("TRKCONT")
    private	String trkCont;	//列管事項內容

    @LogField("TRKFROM")
    private	String trkFrom;	//交辦來源

    @LogField("TRKSTS")
    private	String trkSts;	//列管狀態：0-暫存 1-未完成 9-結案 D-作廢

    @LogField("ALLSTDT")
    private	String allStDt;	//全案列管日期

    @LogField("CLSDT")
    private	String clsDt;	//結案日期

    @LogField("CREDEPT")
    private	String creDept;	//建立人員部門

    @LogField("CREUSER")
    private	String creUser;	//建立人員

    @LogField("CREDT")
    private	LocalDateTime creDt;	//建立日期時間

    @LogField("UPDDEPT")
    private	String updDept;	//更新人員部門

    @LogField("UPDUSER")
    private	String updUser;	//更新人員

    @LogField("UPDDT")
    private	LocalDateTime updDt;	//更新日期時間
}
