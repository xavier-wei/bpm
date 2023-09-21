package tw.gov.pcc.eip.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import tw.gov.pcc.common.annotation.LogField;
import tw.gov.pcc.common.annotation.Table;

/**
 * 會議物件(MEETINGITEM)
 *
 * @author 2201009
 */
@Data
@NoArgsConstructor
@Table("MEETINGITEM")
public class MeetingItem {
    private static final long serialVersionUID = 1L;

    @LogField("MEETINGID")
    private Integer meetingId; //會議ID

    @LogField("ITEMID")
    private String itemId; //物件編號

    @LogField("QTY")
    private Integer qty; //數量
}
