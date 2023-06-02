/**
 *
 */
package tw.gov.pcc.eip.dao;

import java.util.List;
import java.util.Optional;

import tw.gov.pcc.eip.domain.Code;

/**
 * @author jerrywu
 *
 */
public interface CodeDao {

	public static final String TABLE_NAME = "CODE";

	/**
	 * 依 CodeKind 查詢
	 * @param codeKind  主代號類別
	 * @return
	 */
	public List<Code> findByCodeKind(String codeKind);

    /**
     * 依 CodeKind 查詢
     * @param codeKind  主代號類別
     * @return
     */
    public List<Code> findByCodeKindOrderByScodeno(String codeKind);

    /**
     * 取得列印用紙與注意事項
     * @param reportList
     * @return
     */
    public List<Code> getPaperInfo(List<String> reportList);
    
    /**
     * 取得注意事項
     * @return
     */
    public Optional<Code> getNotice();


	/**
	 * 查詢轉帳約定檔處理狀態用
	 * @param codeKind 主代號類別
	 * @return
	 */
    public Optional<Code> findByCodeKindForProstat(String codeKind);

    /**
     * 依  主代號類別 副代號 條件取 CODE 說明
     * @param codeKind  主代號類別
     * @param scodeno  副代號
     * @return
     */
    public List<Code> findByCodeKindScodeno(String codeKind, String scodeno);

    /**
     * 查詢 CODE by codekind，依 codeno 排序結果
     * @param codekind
     * @return
     */
    List<Code> findByCodekindOrderByCodeno(String codekind);

    public Optional<Code> findByCodeKindPresent(String afcodekind);

    public List<Code> findAllCodeKind();

    public int insertCode(Code code);

    public int updateCode(Code code);

    public int deleteCode(Code code);

    public int updateCode_batch(Code code);

    /**
     * 查詢 CODE by codeKind and codeNo
     * @param codekind
     * @param codeno
     * @return
     */
    Optional<Code> findByCodeKindCodeNo(String codekind, String codeno);

    /**
     * 批次報表查詢收件者
     * @param codekind
     * @param codeno
     * @param scodekind
     * @param scodeno
     * @return
     */
    Optional<Code> findReceiver(String codekind, String codeno, String scodekind, String scodeno);

    /**
     * 查詢 CODE by codeKind, scodekind，依 codeNo 排序結果
     * @param codekind
     * @return
     */
    List<Code> findByCodekindScodekindOrderByCodeno(String codekind, String scodekind);

    /**
     * 雜項收入轉預收作業-查詢批次執行狀態
     * @return
     */
    public String getLocks();
    
    /**
     * 取得 背景批次鎖時顯示訊息
     * @return
     */
	public String getBatchLocksMesg();
}
