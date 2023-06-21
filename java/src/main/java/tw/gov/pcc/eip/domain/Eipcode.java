package tw.gov.pcc.eip.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import tw.gov.pcc.common.annotation.LogField;
import tw.gov.pcc.common.annotation.PkeyField;
import tw.gov.pcc.common.annotation.Table;
import tw.gov.pcc.eip.dao.EipcodeDao;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Weith
 * EIPCODE 代碼檔
 */
@Table(EipcodeDao.TABLE_NAME)
@Data
@NoArgsConstructor
public class Eipcode implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 98432953901114332L;
	
	@PkeyField("CODEKIND")
	@LogField("CODEKIND")
	private String codekind;  // 主代號類別

	@PkeyField("CODEKIND")
	@LogField("CODENO")
	private String codeno;  // 主代號

	@LogField("SCODEKIND")
	private String scodekind;  // 副代號類別

	@LogField("SCODENO")
	private String scodeno;  // 副代號

	@LogField("CODENAME")
	private String codename;  // 主代號名稱

	@LogField("PRCDAT")
	private LocalDateTime prcdat;  // 異動日期時間

	@LogField("STAFF")
	private String staff;  // 異動者代號
}