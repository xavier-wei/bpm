package tw.gov.pcc.eip.common.cases;

import lombok.Data;
import lombok.NoArgsConstructor;
import tw.gov.pcc.eip.domain.MeetingCode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 會議室批次預約作業
 * @author 2201009
 */
@Data
@NoArgsConstructor
public class Eip06w030Case implements Serializable {

    private String meetingName; //會議室名稱
    private String chairman; //主持人
    private String organizerId; //申請人
    private String meetingdt; //會議日期
    private String meetingBegin; //會議開始時間
    private String meetingEnd; //會議結束時間
    private int meetingQty; //會議人數
    private String roomId; //會議室編號
    private String itemId; //物品編號
    private String foodId; //餐點編號
    private boolean isRepeat; //預約會議室是否重複 1=不重複 2=自訂(重複)
    private String dateWeekMonth; //每日 週 月
    private int week; //第幾週 1~4
    private int day;  //星期幾 mon tue wed thu fri sat
    private String periodStart; //預定區間起
    private String periodEnd;  //預定區間訖
    List<Object> itemIds; //選取物品編號
    List<Object> foodId_Qty; //選取餐點編號
    List<Object> food_Qty;; //餐點數量
    List<Object> meetingsNeedCancel;; //須取消會議
    private List<Eip06w030OptionCase> itemIdList = new ArrayList<>(); //會議物品清單
    private List<Eip06w030OptionCase> foodIdList = new ArrayList<>(); //會議餐點清單
    private List<Eip06w030OptionCase> roomIdList = new ArrayList<>(); //會議場地清單

    //會議起始時間下拉選單
    private Map<String, String> meetingTimeCombobox;

    @Data
    @NoArgsConstructor
    public static class Eip06w030OptionCase{
        private String itemTyp; // 物件類別  F：場地 A：餐點  B：物品 C:管理者 D:預約天數
        private String itemId; // 物件編號
        private String itemName; // 物件名稱
        private Integer qty; // 數量

        public Eip06w030OptionCase(MeetingCode meetingCode){
            super();
            this.itemTyp = meetingCode.getItemTyp();
            this.itemId = meetingCode.getItemId();
            this.itemName = meetingCode.getItemName();
            this.qty = meetingCode.getQty();
        }
    }
}
