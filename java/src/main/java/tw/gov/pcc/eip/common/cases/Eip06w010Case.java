package tw.gov.pcc.eip.common.cases;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import tw.gov.pcc.eip.domain.MeetingCode;
import tw.gov.pcc.eip.framework.validation.MinValue;
import tw.gov.pcc.eip.framework.validation.RequiredString;

import javax.validation.GroupSequence;
import javax.validation.constraints.AssertTrue;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 會議室查詢/維護作業
 * @author 2201009
 */
@Data
@NoArgsConstructor
public class Eip06w010Case implements Serializable {

    private String meetingId; //會議室ID
    @RequiredString(groups={Eip06w010Case.Sequence.First.class}, label = "會議室名稱")
    private String meetingName; //會議室名稱
    @RequiredString(groups={Eip06w010Case.Sequence.First.class}, label = "主持人")
    private String chairman; //主持人
    private String organizerId; //申請人
    private String organizerIdName; //申請人
    private List<String> userIdList; //使用者ID list
    @RequiredString(groups={Eip06w010Case.Sequence.First.class}, label = "會議日期")
    private String meetingdt; //會議日期
    private int maxMeetingDays; //會議日期預約天數上限
    private String meetingdtBegin; //會議日期開始
    private String meetingdtEnd; //會議日期結束
    @RequiredString(groups={Eip06w010Case.Sequence.First.class}, label = "會議開始時間")
    private String meetingBegin; //會議開始時間
    @RequiredString(groups={Eip06w010Case.Sequence.First.class}, label = "會議結束時間")
    private String meetingEnd; //會議結束時間
    @MinValue(groups={Sequence.Second.class}, label = "開會人數", value = 1, message = "開會人數需大於1位" )
    private int meetingQty; //會議人數
    private int qty; //會議人數
    private LocalDateTime applydt; //申請日期
    private String itemName; //回傳會議室名稱
    private String roomId; //會議室編號
    private String itemId; //物品編號
    private String foodId; //餐點編號
    private String itemList; //查詢畫面設備欄位

    List<Object> itemIds; //選取物品編號
    List<Object> foodId_Qty; //選取餐點編號
    List<Object> food_Qty;; //餐點數量

    private List<Eip06w010OptionCase> roomIdList = new ArrayList<>(); //會議室清單
    private List<Eip06w010OptionCase> itemIdList = new ArrayList<>(); //會議物品清單
    private List<Eip06w010OptionCase> foodIdList = new ArrayList<>(); //會議餐點清單
    private List<Eip06w010Case> resultList = new ArrayList<>(); //查詢結果
    //會議起始時間下拉選單
    private Map<String, String> meetingTimeCombobox;
    //依條件查詢會議場地下拉選單
    private Map<String, String> meetingRoomCombobox;

    private boolean isEditable; //是否有編輯權限（管理員或會議預約者）
    private int orderNum; //是否有訂餐
    private boolean isOrderFood; //是否有訂餐
    private boolean isAdmin; //是否為管理員

    private String operation; //操作權限　A_編輯　B_查詢

//    private String selectedRoomId; //原選取會議室編號

    public Eip06w010Case(Eip06w010Case eip06w010Case) {
        this.meetingId = eip06w010Case.getMeetingId();
        this.meetingName = eip06w010Case.getMeetingName();
        this.chairman = eip06w010Case.getChairman();
        this.organizerId = eip06w010Case.getOrganizerId();
        this.meetingdt = eip06w010Case.getMeetingdt();
        this.meetingBegin = eip06w010Case.getMeetingBegin();
        this.meetingEnd = eip06w010Case.getMeetingEnd();
        this.roomId = eip06w010Case.getRoomId();
        this.itemName = eip06w010Case.getItemName();
        this.isEditable = eip06w010Case.isEditable();
        this.isOrderFood = eip06w010Case.isOrderFood();
        this.qty = eip06w010Case.getQty();
        this.itemList = eip06w010Case.getItemList();
    }




        /**
     * 定義欄位驗證順序
     */
    @GroupSequence({Sequence.First.class, Sequence.Second.class})
    public interface Sequence{
        interface First{}
        interface Second{}
    }

    @AssertTrue(groups={Sequence.First.class}, message="「餐點數量」需大於或等於10")
    private boolean isValidFoodQtys() {
        if(!StringUtils.contains(food_Qty.get(0).toString(),"]")){ //如果get(0)包含"]"表示傳入空值
            String s = null;
            for(int i = 0 ; i < food_Qty.size() ; i++){
                s = StringUtils.replace(food_Qty.get(i).toString(),"]","");
                s = StringUtils.replace(s,"[","");
                int qty = Integer.parseInt(s);
                if (qty < 10){
                    return false;
                }
            }
        }
        return true;
    }

    @AssertTrue(groups = {Sequence.Second.class}, message="「會議開始時間」須早於「會議結束時間」")
    private boolean isValidMeetingTime() {
        if (Integer.parseInt(meetingBegin) >= Integer.parseInt(meetingEnd)){
            return false;
        }
        return true;
    }

    @Data
    @NoArgsConstructor
    public static class Eip06w010OptionCase{
        private String itemTyp; // 物件類別  F：場地 A：餐點  B：物品 C:管理者 D:預約天數
        private String itemId; // 物件編號
        private String itemName; // 物件名稱
        private Integer qty; // 數量

        public Eip06w010OptionCase(MeetingCode meetingCode){
            super();
            this.itemTyp = meetingCode.getItemTyp();
            this.itemId = meetingCode.getItemId();
            this.itemName = meetingCode.getItemName();
            this.qty = meetingCode.getQty();
        }
    }

    public void clear(){
        this.meetingName = "";
        this.chairman =  "";
        this.organizerId =  "";
        this.meetingdtBegin =  "";
        this.meetingdtEnd =  "";
        this.meetingBegin =  "";
        this.meetingEnd =  "";
        this.roomId =  "";
    }
}
