package tw.gov.pcc.eip.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import tw.gov.pcc.common.annotation.LogField;
import tw.gov.pcc.common.annotation.PkeyField;
import tw.gov.pcc.common.annotation.Table;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 會議主檔(MEETING)
 *
 * @author 2201009
 */
@Data
@NoArgsConstructor
@Table("MEETING")
public class Meeting implements Serializable {
    private static final long serialVersionUID = 1L;

    @PkeyField("MEETINGID")
    @LogField("MEETINGID")
    private Integer meetingId; //會議ID

    @LogField("MEETINGNAME")
    private String meetingName; //會議名稱

    @LogField("CHAIRMAN")
    private String chairman; //主持人

    @LogField("MEETINGDT")
    private String meetingdt; //會議日期

    @LogField("MEETINGBEGIN")
    private String meetingBegin; //會議開始時間

    @LogField("MEETINGEND")
    private String meetingEnd; //會議結束時間

    @LogField("ORGANIZERID")
    private String organizerId; //承辦人

    @LogField("ROOMID")
    private String roomId; //會議室編號

    @LogField("QTY")
    private Integer qty; //會議人數

    @LogField("APPLYDT")
    private LocalDateTime applydt; //申請日期

    @LogField("UPDT")
    private LocalDateTime updt; //異動日期

    @LogField("STATUS")
    private String status; //A 待審核 B 取消

    @LogField("USING")
    private String using; //使用時間【每半小時為間隔，方便比對時間是否重複】
}
