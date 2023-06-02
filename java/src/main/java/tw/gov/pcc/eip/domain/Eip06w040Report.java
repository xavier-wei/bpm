package tw.gov.pcc.eip.domain;

import lombok.Data;
import tw.gov.pcc.common.annotation.LogField;

import java.io.Serializable;

/**
 * 產製會議室活動報表欄位
 * 
 * @author 2201009
 *
 */
@Data
public class Eip06w040Report implements Serializable {

    private static final long serialVersionUID = 1L;

    @LogField
    private String roomName; // 會議室地點
    @LogField
    private String meetingId; // 保險別
    @LogField
    private String meetingBegin; // 會議開始時間
    @LogField
    private String meetingName; // 會議名稱
    @LogField
    private String chairman; // 主持人
    @LogField
    private String organizerId; // 承辦單位
    @LogField
    private String meetingQty; // 開會人數
    @LogField
    private String itemId; // 餐點、設備ID
    @LogField
    private String itemName; // 餐點、設備名稱
    @LogField
    private String itemQty; // 餐點數量
    @LogField
    private String foodName; // 餐點、設備名稱
    @LogField
    private String foodQty; // 餐點數量
}
