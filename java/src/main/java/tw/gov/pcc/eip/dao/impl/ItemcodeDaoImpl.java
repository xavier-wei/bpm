package tw.gov.pcc.eip.dao.impl;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import tw.gov.pcc.common.annotation.DaoTable;
import tw.gov.pcc.common.framework.dao.BaseDao;
import tw.gov.pcc.eip.dao.ItemcodeDao;
import tw.gov.pcc.eip.dao.ItemsDao;
import tw.gov.pcc.eip.domain.Itemcode;

/**
 * 選單項目資料 DaoImpl
 */
@DaoTable(ItemsDao.TABLE_NAME)
@Repository
public class ItemcodeDaoImpl extends BaseDao<Itemcode> implements ItemcodeDao {

    private static final String ALL_COLUMNS_SQL;

    static {
        ALL_COLUMNS_SQL = " ITEMKIND, ITEMNO, ITEMNAME, PURCHASE_CNT, RETURN_CNT, FINAL_CNT, WITHHOLD_CNT, "
        		          + " BOOK_CNT, CRE_USER, CRE_DATETIME, UPD_USER, UPD_DATETIME ";
    }

    /**
     * 根據key選取資料(底層用)
     *
     * @param items 條件
     * @return 唯一值
     */
    @Override
    public Itemcode selectDataByPrimaryKey(Itemcode itemcode) {
        String sql = "SELECT " +
                ALL_COLUMNS_SQL +
                " FROM " + TABLE_NAME + " t WHERE ITEMKIND = :itemkind AND ITEMNO = :itemno ";
        List<Itemcode> list = getNamedParameterJdbcTemplate().query(sql, new BeanPropertySqlParameterSource(itemcode), BeanPropertyRowMapper.newInstance(Itemcode.class));
        return CollectionUtils.isEmpty(list) ? null : list.get(0);
    }
    
    /**
     * 根據key選取資料(底層用)
     *
     * @param items 條件
     * @return List
     */
    @Override
	public List<Itemcode>selectAllData(Itemcode itemcode){
        String sql = "SELECT " +
                ALL_COLUMNS_SQL +
                " FROM " + TABLE_NAME + " t WHERE ITEMKIND = :ITEMKIND ";
        SqlParameterSource params = new MapSqlParameterSource("ITEMKIND", itemcode.getItemkind());
        List<Itemcode> list = getNamedParameterJdbcTemplate().query(sql, params,BeanPropertyRowMapper.newInstance(Itemcode.class));
        return list;
    }
    
    @Override
    public List<Itemcode> findByItemkind(String itemkind) {
        String sql = "SELECT * FROM " + TABLE_NAME
        		      + " WHERE ITEMKIND = :itemkind ORDER BY ITEMNO";
        SqlParameterSource params = new MapSqlParameterSource("itemkind", itemkind);
        List<Itemcode> list = getNamedParameterJdbcTemplate().query(sql, params,
                BeanPropertyRowMapper.newInstance(Itemcode.class));
        return list;
    }
    

    @Override
    public List<Itemcode> selectDataListByKey(Itemcode itemcode) {
        String sql = "SELECT " +
                ALL_COLUMNS_SQL +
                " FROM " + TABLE_NAME + " t WHERE ITEMKIND = :itemkind AND ITEMNO = ISNULL(:itemno, ITEMNO) ORDER BY ITEMNO ";
        return getNamedParameterJdbcTemplate().query(sql, new BeanPropertySqlParameterSource(itemcode), BeanPropertyRowMapper.newInstance(Itemcode.class));

    }
    
    /**
     * 新增一筆資料
     *
     * @param itemcode 新增資料
     */
    @Override
    public int insert(Itemcode itemcode) {
        return getNamedParameterJdbcTemplate().update(" INSERT INTO " + TABLE_NAME +
                        "(" +
                        " ITEMKIND, ITEMNO, ITEMNAME, PURCHASE_CNT, RETURN_CNT, FINAL_CNT, WITHHOLD_CNT, " +
      		            " BOOK_CNT, CRE_USER, CRE_DATETIME, UPD_USER, UPD_DATETIME " +
                        ")" +
                        " VALUES ( " +
                        " :itemkind, :itemno, :itemname, :purchase_cnt, :return_cnt, :final_cnt, :withhold_cnt, " +
      		            " :book_cnt, :cre_user, :cre_datetime, :upd_user, :upd_datetime " +
                        ")",
                new BeanPropertySqlParameterSource(itemcode));
    }
    
    /**
     * 根據key刪除資料
     *
     * @param itemcode 條件
     * @return 異動筆數
     */
    @Override
    public int deleteByKey(Itemcode itemcode) {
        return getNamedParameterJdbcTemplate().update(" DELETE FROM " + TABLE_NAME +
                        " WHERE ITEMKIND = :itemkind AND ITEMNO = :itemno ",
                new BeanPropertySqlParameterSource(itemcode));
    }

    /**
     * 根據key更新資料
     *
     * @param itemcode 條件
     * @return 異動筆數
     */
    @Override
    public int updateByKey(Itemcode itemcode) {
        return getNamedParameterJdbcTemplate().update(" UPDATE " + TABLE_NAME + "  SET " +
                " ITEMNAME = :itemname, PURCHASE_CNT = :purchase_cnt, RETURN_CNT = :return_cnt, FINAL_CNT = :final_cnt, WITHHOLD_CNT = :withhold_cnt, " +
                " BOOK_CNT = :book_cnt, CRE_USER = :cre_user, CRE_DATETIME = :cre_datetime, UPD_USER = :upd_user, UPD_DATETIME = :upd_datetime" +
                " WHERE ITEMKIND = :itemkind AND ITEMNO = :itemno ",
                new BeanPropertySqlParameterSource(itemcode));
    }

	@Override
	public Itemcode getItemname(String itemno) {
        String sql = " SELECT itemname ,"+
  		  " (select itemname from itemcode where itemkind = 'MAIN' and itemno = a.itemkind ) as itemkind "+
  		  " FROM ITEMCODE a "+
  		  " where itemno=:itemno ";
		  SqlParameterSource params = new MapSqlParameterSource("itemno", itemno); 
		  List<Itemcode> list = getNamedParameterJdbcTemplate().query(sql, params,BeanPropertyRowMapper.newInstance(Itemcode.class));
		  return CollectionUtils.isEmpty(list)? null : list.get(0);
	}

	@Override
	public List<Itemcode> getStatus2List(String applyno) {
			StringBuffer sb = new StringBuffer();
			sb.append(" Select A.*, ");
			sb.append(" (select itemno+'-'+itemname from itemcode where itemkind='MAIN' AND  substring(a.itemno,1,1)=itemno) as itemkind_nm, ");
			sb.append(" (select itemno+'-'+itemname from itemcode where itemkind=a.itemkind And itemno=a.itemno) as itemno_nm, ");
			sb.append(" I.FINAL_CNT,  ");
			sb.append(" I.WITHHOLD_CNT, "); 
			sb.append(" I.BOOK_CNT, apply_cnt ");
			sb.append(" from applyitem A, ITEMCODE I ");
			sb.append(" Where A.applyno= :applyno ");
			sb.append(" AND A.Reconfirm_mk ='Y' ");
			sb.append(" AND A.process_status ='2' ");
			sb.append(" AND A.ITEMKIND=I.ITEMKIND ");
			sb.append(" AND A.ITEMNO=I.ITEMNO ");
			sb.append(" ORDER BY A.SEQNO ");
		  
		  SqlParameterSource params = new MapSqlParameterSource("applyno", applyno);
		  List<Itemcode> list = getNamedParameterJdbcTemplate().query(sb.toString(), params,
		          BeanPropertyRowMapper.newInstance(Itemcode.class));
		  return list;
	}
}