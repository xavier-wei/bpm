package tw.gov.pcc.eip.common.cases;

import lombok.Data;
import lombok.NoArgsConstructor;
import tw.gov.pcc.eip.domain.Eipcode;
import tw.gov.pcc.eip.framework.validation.ChineseDate;

import javax.validation.constraints.AssertTrue;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 重要列管事項_填報辦理進度
 * @author 2201009
 */
@Data
@NoArgsConstructor
public class Eip03w020Case implements Serializable {

//    初始頁
    private String trkID; //列管編號
    private String trkCont; //列管事項內容
    private String allStDt; //全案列管日期
    @ChineseDate(label = "全案列管日期起日")
    private String allStDtSt; //全案列管日期起日
    @ChineseDate(label = "全案列管日期訖日")
    private String allStDtEnd; //全案列管日期訖日
    private String trkSts; // 列管狀態：0-暫存 1-未完成 9-結案 D-作廢
    private String prcSts; // 處理狀態 0-全部/1-待處理/2-待解列/3-已解列
    private String cnt_all;  //全部
    private String cnt_doing;  //待處理
    private String cnt_wait;  //待解列
    private String cnt_done; //已解列
    private String nowStat;
    private List<Eip03w020Case> keepTrkMstList = new ArrayList<>();
    private List<Eipcode> trkStsList = new ArrayList<>();  //列管狀態下拉選單
    private List<Eipcode> prcStsList = new ArrayList<>();  //處理狀態下拉選單
    private Map<String, String> trkStsCombobox; //列管狀態下拉選單
    private Map<String, String> prcStsCombobox; //處理狀態下拉選單

//    明細頁
    private String selectedTrkID; //列管編號

    @AssertTrue(message="「全案列管開始日期」須早於「全案列管結束日期」")
    private boolean isValidMeetingTime() {
            if (allStDtSt != null && allStDtEnd != null){
                if (!allStDtSt.equals("") && !allStDtEnd.equals("") && Integer.parseInt(allStDtSt) > Integer.parseInt(allStDtEnd)){
                    return false;
                }
            }
        return true;
    }
}

