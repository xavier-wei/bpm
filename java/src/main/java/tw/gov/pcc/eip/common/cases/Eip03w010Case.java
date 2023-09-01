package tw.gov.pcc.eip.common.cases;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import tw.gov.pcc.eip.domain.Eipcode;
import tw.gov.pcc.eip.domain.KeepTrkDtl;
import tw.gov.pcc.eip.framework.validation.ChineseDate;
import tw.gov.pcc.eip.framework.validation.RequiredString;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

/**
 * 重要列管事項_重要列管事項維護
 * @author 2201009
 */
@Data
@NoArgsConstructor
public class Eip03w010Case implements Serializable {

////    初始頁
    private String trkID; //列管編號
    private String trkCont; //列管事項內容
    private String allStDt; //全案列管日期
    @ChineseDate(label = "全案列管日期起日")
    private String allStDtSt; //全案列管日期起日
    @ChineseDate(label = "全案列管日期訖日")
    private String allStDtEnd; //全案列管日期訖日
    private String trkSts; // 列管狀態：0-暫存 1-未完成 9-結案 D-作廢
    private String cnt_all;  //全部
    private String cnt_doing;  //待處理
    private String cnt_wait;  //待解列
    private String cnt_done; //已解列
    private List<Eip03w010Case> keepTrkMstList = new ArrayList<>();
    private List<Eipcode> trkStsList = new ArrayList<>();
    public String mode;
    public String selectedTrkID;
//    新增頁
    private String creDept; //建立部門
    private String creUser; //建立人員
    private String creDt; //建立時間
    private String trkObj; //列管對象
    private Map<String, String> trkObjCombobox; //列管對象下拉選單
    private Map<String, String> trkFromCombobox; //交辦來源下拉選單
    private String otherTrkFrom; //其他交辦來源
    private String temp; //0為暫存 1為儲存
    private String trkFrom; //交辦來源
    private String jsonMap;
    private String stDt; //分案列管日期起日
    private String endDt; //分案列管日期訖日


    //修改頁
    private String updDept; //更新部門
    private String updUser; //更新人員
    private String updDt; //更新時間 含時分秒
    private List<KeepTrkDtl> keepTrkDtlList = new ArrayList<>();

    public void clear(){
        this.trkID = "";
        this.trkCont = "";
        this.allStDt = "";
        this.allStDtSt = "";
        this.allStDtEnd = "";
        this.trkSts = "";
        this.cnt_all = "";
        this.cnt_doing = "";
        this.cnt_wait = "";
        this.cnt_done = "";
        this.keepTrkMstList.clear();
        this.trkStsList.clear();
        this.creDept = "";
        this.creUser = "";
        this.creDt = null;
        this.trkObj = "";
        this.trkObjCombobox.clear();
        this.temp = "";
        this.trkFrom = "";
        this.jsonMap = "";
        this.mode = "";
    }

    //    private String prcSts; // 處理狀態 0-全部/1-待處理/2-待解列/3-已解列

    @AssertTrue(message="「全案列管開始日期」須早於「全案列管結束日期」")
    private boolean isValidMeetingTime() {
        if(getMode().equals("query")){
            if (allStDtSt != null && allStDtEnd != null){
                if (!allStDtSt.equals("") && !allStDtEnd.equals("") && Integer.parseInt(allStDtSt) > Integer.parseInt(allStDtEnd)){
                    return false;
                }
            }
        }
        return true;
    }

    @AssertTrue(message="全案列管日期為必填")
    private boolean isValidAllStDt() {
        if(getMode().equals("insert") || getMode().equals("modify")){
            if(getAllStDt() == null){
                return false;
            }
        }
        return true;
    }

    @AssertTrue(message="「內容」為必填")
    private boolean isValidCont(){
        if(getMode().equals("insert") || getMode().equals("modify")){
            if(StringUtils.isBlank(getTrkCont())){
                return false;
            }
        }
        return true;
    }

    @AssertTrue(message="「交辦來源」為必選")
    private boolean isValidTrkFrom(){
        if(getMode().equals("insert") || getMode().equals("modify")){
            if(StringUtils.isBlank(getTrkFrom())){
                return false;
            }
        }
        return true;
    }

    @AssertTrue(message="請輸入「其他交辦來源」")
    private boolean isValidTrkFromForOthers(){
        if(getMode().equals("insert") || getMode().equals("modify")){
            if(StringUtils.equals(getTrkFrom(), "others") && StringUtils.isBlank(getOtherTrkFrom())){
                return false;
            }
        }
        return true;
    }

    @AssertTrue(message="「列管對象」請至少選擇一個")
    private boolean isValidTrkObj() throws JsonProcessingException {
        if(getMode().equals("insert") || getMode().equals("modify")){
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Map<String, String>> jsonMap = objectMapper.readValue(getJsonMap(), Map.class);
            if(jsonMap.size() <= 1){
                return false;
            }
        }
        return true;
    }

    @AssertTrue(message="「列管起日」為必填")
    private boolean isRequiredAllStDtSt() throws JsonProcessingException {
        if(getMode().equals("insert") || getMode().equals("modify")){
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Map<String, String>> jsonMap = objectMapper.readValue(getJsonMap(), Map.class);
            if(jsonMap.size() > 1){
                for(String k : jsonMap.keySet()){
                    if(StringUtils.isNotBlank(k)){
                        Map<String, String> innerJsonMap = jsonMap.get(k);
                        String stDt = innerJsonMap.get("stDt");
                        if ( StringUtils.isBlank(stDt) ){
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    @AssertTrue(message="「列管起日」須早於「列管迄日」")
    private boolean isValidMeetingTimeWhenInsert() throws JsonProcessingException {
        if(getMode().equals("insert") || getMode().equals("modify")){
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Map<String, String>> jsonMap = objectMapper.readValue(getJsonMap(), Map.class);
            if(jsonMap.size() > 1){
                for(String k : jsonMap.keySet()){
                    if(!k.equals("")){
                        Map<String, String> innerJsonMap = jsonMap.get(k);
                        String stDt = innerJsonMap.get("stDt").replace("/","");
                        String endDt = innerJsonMap.get("endDt").replace("/","");;
                        if (StringUtils.isNotBlank(stDt) && StringUtils.isNotBlank(endDt) && Integer.parseInt(stDt) > Integer.parseInt(endDt)){
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

}

