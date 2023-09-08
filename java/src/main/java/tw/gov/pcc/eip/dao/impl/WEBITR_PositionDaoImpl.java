package tw.gov.pcc.eip.dao.impl;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;
import tw.gov.pcc.common.annotation.DaoTable;
import tw.gov.pcc.common.annotation.SkipLog;
import tw.gov.pcc.common.framework.dao.ItrBaseDao;
import tw.gov.pcc.eip.dao.WEBITR_PositionDao;
import tw.gov.pcc.eip.domain.Position;

import java.util.HashMap;
import java.util.List;

/**
 * webitr.dbo.position DaoImpl
 */
@DaoTable(WEBITR_PositionDaoImpl.TABLE_NAME)
@Repository
public class WEBITR_PositionDaoImpl extends ItrBaseDao<Position> implements WEBITR_PositionDao {

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

    @Override
    @SkipLog
    public List<Position> selectAll() {
        String sql = "SELECT " + " * " + " FROM " + WEBITR_PositionDaoImpl.TABLE_NAME;
        return getNamedParameterJdbcTemplate().query(sql, new HashMap<>(), BeanPropertyRowMapper.newInstance(Position.class));
    }

}