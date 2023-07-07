package tw.gov.pcc.eip.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import tw.gov.pcc.common.annotation.LogField;
import tw.gov.pcc.common.annotation.PkeyField;
import tw.gov.pcc.common.annotation.Table;
import tw.gov.pcc.eip.dao.KeepTrkDtlDao;
import tw.gov.pcc.eip.dao.MsgdataDao;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 重要列管事項明細
 * 
 * @author 2201009
 *
 */
@Table(KeepTrkDtlDao.TABLE_NAME)
@NoArgsConstructor
@Data
public class KeepTrkDtl implements Serializable {

    @PkeyField("TRKID")
    @LogField("TRKID")
    private String trkID;      //列管事項編號

    @PkeyField("TRKOBJ")
    @LogField("TRKOBJ")
    private String trkObj;     //列管對象 (處室)

    @LogField("PRCSTS")
    private String prcSts;     //處理狀態：1-待處理 2-待解列 3-已解列

    @LogField("STDT")
    private String stDt;    //列管起日

    @LogField("ENDDT")
    private String endDt;      //列管迄日

    @LogField("RPTCONT")
    private String rptCont;    //辦理情形

    @LogField("RPTRATE")
    private int RptRate;    //辦理完成進度(0-100)

    @LogField("RPTASKEND")
    private String rptAskEnd;  //是否要求解列(Y/N)

    @LogField("RPTDEPT")
    private String rptDept;    //指定填報單位

    @LogField("RPTUSER")
    private String rptUser;    //指定填報人員

    @LogField("RPTUPDUSER")
    private String rptUpdUser; //填報更新人員

    @LogField("RPTUPDDT")
    private LocalDateTime rptUpdDt;   //填報更新日期時間

    @LogField("SUPCONT")
    private String supCont;    //回應內容

    @LogField("SUPAGREE")
    private String supAgree;   //是否同意解列(Y/N)

    @LogField("SUPDEPT")
    private String supDept;    //回應人員所屬部門

    @LogField("SUPUSER")
    private String supUser;    //回應人員

    @LogField("SUPDT")
    private LocalDateTime supDt;      //回應日期時間
}
