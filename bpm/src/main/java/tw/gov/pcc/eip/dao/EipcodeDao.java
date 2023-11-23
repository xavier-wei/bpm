package tw.gov.pcc.eip.dao;


import tw.gov.pcc.domain.Eipcode;

import java.util.List;

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
    public List<Eipcode> findByCodeKindOrderByScodeno(String codeKind);

    /**
     * 查詢 BECODE by codeKind, scodekind，依 codeNo 排序結果
     * @param codekind
     * @return
     */
    List<Eipcode> findByCodekindScodekindOrderByCodeno(String codekind, String scodekind);

    public List<Eipcode> findByCodeKind(String codeKind);


}
