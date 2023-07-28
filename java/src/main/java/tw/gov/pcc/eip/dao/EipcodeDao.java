package tw.gov.pcc.eip.dao;

import java.util.List;
import java.util.Optional;

import tw.gov.pcc.eip.domain.Eipcode;

/**
 * @author Weith
 *
 */
public interface EipcodeDao {

	public static final String TABLE_NAME = "EIPCODE";

	/**
	 * 依 CodeKind 查詢
	 * @param codeKind  主代號類別
	 * @return
	 */
	public List<Eipcode> findByCodeKind(String codeKind);

    /**
     * 依 CodeKind 查詢
     * @param codeKind  主代號類別
     * @return
     */
    public List<Eipcode> findByCodeKindOrderByScodeno(String codeKind);

    /**
     * 依  主代號類別 副代號 條件取 BECODE 說明
     * @param codeKind  主代號類別
     * @param scodeno  副代號
     * @return
     */
    public List<Eipcode> findByCodeKindScodeno(String codeKind, String scodeno);

    /**
     * 查詢 BECODE by codekind，依 codeno 排序結果
     * @param codekind
     * @return
     */
    List<Eipcode> findByCodekindOrderByCodeno(String codekind);

    public Optional<Eipcode> findByCodeKindPresent(String afcodekind);

    public List<Eipcode> findAllCodeKind();

    public int insertEipcode(Eipcode eipcode);

    public int updateEipcode(Eipcode eipcode);

    public int deleteEipcode(Eipcode eipcode);

    public int updateEipcode_batch(Eipcode eipcode);

    /**
     * 查詢 BECODE by codeKind and codeNo
     * @param codekind
     * @param codeno
     * @return
     */
    Optional<Eipcode> findByCodeKindCodeNo(String codekind, String codeno);

    /**
     * 查詢 BECODE by codeKind, scodekind，依 codeNo 排序結果
     * @param codekind
     * @return
     */
    List<Eipcode> findByCodekindScodekindOrderByCodeno(String codekind, String scodekind);

	/**
     * 依 CodeKind 模糊查詢
     * @param codeKind  主代號類別
     * @return
     */
    public List<Eipcode> findLikeCodeKind(String codekind);

    /**
     * 依傳入codekind與list搜尋
     * @param codeKind  主代號類別
     * @return
     */
    public List<String> findByCodeKindAndList(String codeKind, List<String>list);

    /**
     * 依傳入codekind並排除傳入scodekind進行查詢
     * @param codeKind
     * @param scodekind
     * @return
     */
    public List<Eipcode> findByCodeKindAndExcludeScodekind(String codeKind, String scodekind);
    
    public Eipcode selectDataByPrimaryKey(Eipcode eipcode);
}