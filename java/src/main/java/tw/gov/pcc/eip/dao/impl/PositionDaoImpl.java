package tw.gov.pcc.eip.dao.impl;

import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.stereotype.Repository;
import tw.gov.pcc.common.annotation.DaoTable;
import tw.gov.pcc.common.annotation.SkipLog;
import tw.gov.pcc.common.framework.dao.BaseDao;
import tw.gov.pcc.eip.dao.PositionDao;
import tw.gov.pcc.eip.domain.Position;

import java.util.HashMap;

/**
 * POSITION DaoImpl
 */
@DaoTable(PositionDao.TABLE_NAME)
@Repository
public class PositionDaoImpl extends BaseDao<Position> implements PositionDao {
    /**
     * 根據key選取資料(底層用)
     *
     * @param position 條件
     * @return 唯一值
     */
    @Override
    @SkipLog
    public Position selectDataByPrimaryKey(Position position) {
        return null;
    }

    /**
     * 新增一筆資料
     *
     * @param position 新增資料
     */
    @Override
    @SkipLog
    public int insert(Position position) {
        return getNamedParameterJdbcTemplate().update(" INSERT INTO " + TABLE_NAME + "(" + " POSID, FID, POSNAME, CLASS, DEPART, DEPUTY, ID, PREVID, FLOWGROUP, ORGCODE" + ")" + " VALUES ( " + " :posid, :fid, :posname, :cLass, :depart, :deputy, :id, :previd, :flowgroup, :orgcode" + ")", new BeanPropertySqlParameterSource(position));
    }

    @Override
    @SkipLog
    public void truncateAll() {
        getNamedParameterJdbcTemplate().update("TRUNCATE TABLE " + TABLE_NAME, new HashMap<>());
    }
}