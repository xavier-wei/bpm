package tw.gov.pcc.eip.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import tw.gov.pcc.common.annotation.LogField;
import tw.gov.pcc.common.annotation.PkeyField;
import tw.gov.pcc.common.annotation.Table;

/**
 * 會議物件(MEETINGITEM)
 *
 * @author 2201009
 */
@Data
@NoArgsConstructor
@Table("MEETINGITEM")
public class MeetingItemAndMeetingCode {
    private static final long serialVersionUID = 1L;

    private Integer meetingId; //會議ID

    private String itemId; //物件編號

    private Integer qty; //數量

    private String itemTyp; // 物件類別  F：場地 A：餐點  B：物品 C:管理者 D:預約天數

    private String itemName; // 物件名稱
}
