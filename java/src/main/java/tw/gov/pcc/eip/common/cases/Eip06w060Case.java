package tw.gov.pcc.eip.common.cases;

import lombok.Data;
import tw.gov.pcc.eip.framework.validation.RequiredString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 會議室管理
 * @author 2207003
 */
@Data
public class Eip06w060Case implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<String> meetingCodeList;//畫面所選表單編號列表

    @RequiredString(label = "類別")
    private String  itemTyp="F"; //物件類別  F：會議室  FX：會議室(禁用)
    private String itemNo;
    @RequiredString(label = "編號")
    private String itemId;
    @RequiredString(label = "名稱")
    private String itemName;
    private Integer qty;

//    @RequiredString(label = "日期")
    private String isableDate; //日期
//    private String repeat;//是否重複 false不重複 true自訂
    private boolean isRepeat; //預約會議室是否重複 1=不重複false 2=自訂true(不重複)
    private String dateWeekMonth; //每日 週 月
    private int week; //第幾週 1-4
    private int day;  //星期幾 mon tue wed thu fri sat
    private String periodStart; //起日
    private String periodEnd;  //訖日
    private String meetingBegin; //會議室開啟時間
    private String meetingEnd; //會議室關閉時間
    private String isableTime;//啟用時間48位元

    private Map<String, String> timeBeginMap; //會議室時間 開始選單
    private Map<String, String> timeEndMap; //會議室時間 結束選單

    //會議室參數
    @Data
    public static class MeetingCodeCase {
        private String  itemTyp;
        private String itemId;
        private String itemName;
        private Integer qty;
    }
    private List<MeetingCodeCase> meetingCodeCaseList = new ArrayList<>();

    //會議室啟用
    @Data
    public static class RoomIsableCase {
        private String itemNo;
        private String itemId;
        private String itemName;
        private String isableDate;
        private String meetingBegin;
        private String meetingEnd;
//        private String isableTime;
    }
    private List<RoomIsableCase> roomIsableCaseList = new ArrayList<>();

}
