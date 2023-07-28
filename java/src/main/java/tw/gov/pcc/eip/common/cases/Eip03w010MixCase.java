package tw.gov.pcc.eip.common.cases;

import lombok.Data;
import lombok.NoArgsConstructor;
import tw.gov.pcc.eip.domain.KeepTrkDtl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 重要列管事項_重要列管事項維護  明細頁
 * @author 2201009
 */
@Data
@NoArgsConstructor
public class Eip03w010MixCase implements Serializable {

    private String selectedTrkID; //列管編號

//    明細頁
    private String trkID; //列管編號
    private String trkSts; // 列管狀態：0-暫存 1-未完成 9-結案 D-作廢
    private String trkObj; //列管對象
    private String trkCont; //列管事項內容
    private String trkFrom; //交辦來源
    private String allStDt; //全案列管日期
    private String clsDt; //結案日期
    private String creDept; //建立部門
    private String creUser; //建立人員
    private String creDt; //建立時間
    private String updDept; //更新部門
    private String updUser; //更新人員
    private String updDt; //更新時間

    //   辦理進度
    private String rptDept; //列管對象 & 指定填報單位
    private String prcSts;  //處理狀態 置換為代碼-中文說明
    private String rptCont;  //辦理情形
    private String rptRate; //完成進度
    private String rptAskEnd; //是否要求解列 Y/N換中文是/否。
    private String rptUser; //指定填報人員
    private String rptUpdDept; //更新部門
    private String rptUpdUser; //更新人員
    private String rptUpdDt; // 更新時間

    private String supCont; //回應內容
    private String supAgree; //是否同意解列  Y/N換中文是/否，必填，若為空值預設為否。
    private String supDept; //回應部門
    private String supUser; //回應人員
    private String supDt; //回應時間

    private List<KeepTrkDtl> kdList = new ArrayList<>();
    private List<String> trkObjList = new ArrayList<>();
    Map<String, Map<String, String>> doubleMap = new HashMap<>();
}

