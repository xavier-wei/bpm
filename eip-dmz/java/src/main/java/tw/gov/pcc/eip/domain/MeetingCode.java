package tw.gov.pcc.eip.domain;

import tw.gov.pcc.common.annotation.LogField;
import tw.gov.pcc.common.annotation.PkeyField;
import tw.gov.pcc.common.annotation.Table;
import tw.gov.pcc.eip.dao.MeetingCodeDao;
import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 參數(MEETINGCODE)
 * @author 2207003
 *
 */



@Data
@NoArgsConstructor
@Table(MeetingCodeDao.TABLE_NAME)
public class MeetingCode implements Serializable {
	/**
	 */
	private static final long serialVersionUID = 1L;

	@LogField("ITEMTYP")
	private String itemTyp; // 物件類別  F：場地 A：餐點  B：物品 C:管理者 D:預約天數

	@PkeyField("ITEMID")
	@LogField("ITEMID")
	private String itemId; // 物件編號

	@LogField("ITEMNAME")
	private String itemName; // 物件名稱

	@LogField("QTY")
	private Integer qty; // 數量

}
