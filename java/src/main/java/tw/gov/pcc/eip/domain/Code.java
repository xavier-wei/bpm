package tw.gov.pcc.eip.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;
import tw.gov.pcc.eip.dao.CodeDao;
import tw.gov.pcc.common.annotation.LogField;
import tw.gov.pcc.common.annotation.PkeyField;
import tw.gov.pcc.common.annotation.Table;

/**
 * @author jerrywu
 * CODE 代碼檔
 */
@Table(CodeDao.TABLE_NAME)
@NoArgsConstructor
@Data
public class Code implements Serializable {
	/**
	 */
	private static final long serialVersionUID = 1L;
	@PkeyField("CODEKIND")
	@LogField("CODEKIND")
	private String codekind; // 主代號類別
	@PkeyField("CODEKIND")
	@LogField("CODENO")
	private String codeno; // 主代號
	@LogField("SCODEKIND")
	private String scodekind; // 副代號類別
	@LogField("SCODENO")
	private String scodeno; // 副代號
	@LogField("CODENAME")
	private String codename; // 主代號名稱
	@LogField("PRCDAT")
	private LocalDateTime prcdat; // 異動日期時間
	@LogField("STAFF")
	private String staff; // 異動者代號
	@LogField("REMARK")
	private String remark; // 說明
	private String afcodekind;//主代號類別-改後
	private String afcodeno;//主代號-改後
	//join col for 丟印單
	private String paperType; // 列印用紙
	private String notice; // 注意事項
	private String reportNo; // 報表編號
	private String reportName; //檔名
	private Integer pageTotal; //列印頁數
	private Integer printNum; //列印份數
}
