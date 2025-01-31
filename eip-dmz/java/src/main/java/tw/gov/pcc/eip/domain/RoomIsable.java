package tw.gov.pcc.eip.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import tw.gov.pcc.common.annotation.LogField;
import tw.gov.pcc.common.annotation.PkeyField;
import tw.gov.pcc.common.annotation.Table;
import tw.gov.pcc.eip.dao.RoomIsableDao;

import java.io.Serializable;


/**
 * 會議室禁用(ROOMISABLE)
 * @author 2207003
 *
 */



@Data
@NoArgsConstructor
@Table(RoomIsableDao.TABLE_NAME)
public class RoomIsable implements Serializable {
	private static final long serialVersionUID = 1L;

	@PkeyField("ITEMID")
	@LogField("ITEMID")
	private String itemId; // 場地

	@LogField("ITEMNAME")
	private String itemName; // 場地名稱


	@PkeyField("ISABLEDATE")
	@LogField("ISABLEDATE")
	private String isableDate; // 啟用日期

	@LogField("MEETINGBEGIN")
	private String meetingBegin; // 啟用開始時間  0800

	@LogField("MEETINGEND")
	private String meetingEnd; // 啟用結束時間  1200

	@PkeyField("ISABLETIME")
	@LogField("ISABLETIME")
	private String isableTime; // 啟用時間【每半小時為間隔，方便比對時間是否重複】

}
