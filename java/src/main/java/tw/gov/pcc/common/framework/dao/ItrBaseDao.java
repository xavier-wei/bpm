package tw.gov.pcc.common.framework.dao;

import javax.sql.DataSource;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

/**
 * 差勤使用的 BASEDAO
 * 不紀錄
 *
 * @author swho
 */
@Getter
@Slf4j
public abstract class ItrBaseDao<T> {

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public abstract T selectDataByPrimaryKey(T t);

    public  DataSource getDataSource() {
        return (this.jdbcTemplate != null ? this.jdbcTemplate.getDataSource() : null);
    }

    @Autowired
    @Qualifier("itrDataSource")
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }
}
