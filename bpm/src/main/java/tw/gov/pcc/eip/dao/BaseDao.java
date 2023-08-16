package tw.gov.pcc.eip.dao;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.util.Assert;
import tw.gov.pcc.eip.annotation.LogField;
import tw.gov.pcc.eip.annotation.PkeyField;
import tw.gov.pcc.eip.annotation.Table;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.*;

/**
 * DAO Base
 *
 * @author Goston
 */
public abstract class BaseDao<T> {
	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(BaseDao.class);

	public abstract T selectDataByPrimaryKey(T t);

	private JdbcTemplate jdbcTemplate;
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Autowired
	@Qualifier("dataSource")
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

    public  DataSource getDataSource() {
		return (this.jdbcTemplate != null ? this.jdbcTemplate.getDataSource() : null);
	}

	protected  Connection getConnection() throws CannotGetJdbcConnectionException {
		DataSource dataSource = getDataSource();
		Assert.state(dataSource != null, "No DataSource set");
		return DataSourceUtils.getConnection(dataSource);
	}

	public NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
		return namedParameterJdbcTemplate;
	}

	public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}

	protected  void releaseConnection(Connection con) {
		DataSourceUtils.releaseConnection(con, getDataSource());
	}

	/**
	 * <pre>
	 * 若 propList
	 * List<String> propList = new ArrayList<>();
	 *	propList.add("1");
	 *	propList.add("2");
	 *	propList.add("3");
	 *
	 *	getPropRange(propList);// 1,2,3
	 *
	 *
	 * </pre>
	 *
	 * @param <E>
	 * @param propList
	 * @return
	 */
	protected static <E> String getPropRange(List<E> propList) {
		if (propList == null || propList.size() == 0) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
//    	sb.append("(");
		for (int i = 0; i < propList.size(); i++) {
			E e = propList.get(i);
			sb.append(e.toString());
			if (i != propList.size() - 1) {
				sb.append(",");
			}
		}
//    	sb.append(")");
		return sb.toString();
	}

	/**
	 * 驗証 sql語法 與 傳入參數 是否匹配
	 *
	 * @param sql
	 * @param paramMap
	 */
	public void paramsAllEmptyThrowException(String sql, MapSqlParameterSource paramMap) {
		if (StringUtils.isNotEmpty(sql) && !Objects.isNull(paramMap)) {
			String[] parameterNames = paramMap.getParameterNames();
			paramsAllEmptyThrowException(sql, Arrays.asList(parameterNames));
		}
	}

	/**
	 * 驗証 sql語法 與 傳入參數 是否匹配
	 *
	 * @param sql
	 * @param paramMap
	 */
	public void paramsAllEmptyThrowException(String sql, Map<String, ? extends Object> paramMap) {
		if (StringUtils.isNotEmpty(sql) && !Objects.isNull(paramMap) && !paramMap.isEmpty()) {
			paramsAllEmptyThrowException(sql, new ArrayList<String>(paramMap.keySet()));
		}
	}

	private void paramsAllEmptyThrowException(String sql, List<String> params) {
		for (String param : params) {
			String search = ":" + param;
			if (StringUtils.indexOf(sql, search) > 0) {
				// sql 在條件句中 有搜尋到 參數 placeholder
				return;
			}
		}
		throw new IllegalArgumentException(" sql語法 與 傳入參數 至少對應一項 ");
	}

	/**
	 * 新增資料
	 *
	 * @param Domain物件
	 * return 筆數
	 */
	public int insert(T t) {
		Table tableAnno = t.getClass().getAnnotationsByType(Table.class)[0];
		String tableName = tableAnno.value();
		StringBuilder columnSql = new StringBuilder();
		Arrays.asList(t.getClass().getDeclaredFields()).stream().filter(field -> field.isAnnotationPresent(LogField.class)).forEach(field -> {
			String columnName = field.getAnnotationsByType(LogField.class)[0].value();
			columnSql.append(columnName).append(",");
		});
		String _columnSql = columnSql.substring(0, columnSql.length() - 1);
		StringBuilder fieldSql = new StringBuilder();
		Arrays.asList(t.getClass().getDeclaredFields()).stream().filter(field -> field.isAnnotationPresent(LogField.class)).forEach(field -> {
			fieldSql.append(":").append(field.getName()).append(",");
		});
		String _fieldSql = fieldSql.substring(0, fieldSql.length() - 1);
        //		log.debug("insert sql :[{}]",sql.toString());
		SqlParameterSource params = new BeanPropertySqlParameterSource(t);
		return getNamedParameterJdbcTemplate().update("INSERT INTO " + tableName + "( " + _columnSql + ") VALUES (" + _fieldSql + ")"
//		log.debug("insert sql :[{}]",sql.toString());
                , params);
	}

	/**
	 * 驗証 sql語法 與 傳入參數 是否匹配
	 *
	 * @param Domain物件
	 * @param sql
	 */
	private void paramsAllEmptyThrowException(T t, String conditions) {
		SqlParameterSource params = new BeanPropertySqlParameterSource(t);
		if (StringUtils.isEmpty(conditions) || t == null) {
			throw new IllegalArgumentException(" 傳入物件不可為NULL ");
		}
		if (conditions.indexOf(":") == -1) {
			throw new IllegalArgumentException(" sql語法 與 傳入參數 至少對應一項 ");
		}
		String[] sary = conditions.toUpperCase().split(" AND ");
		String field = "";
		if (sary.length > 0) {
			for (String s : sary) {
				String[] sary2 = s.split(":");
				if (sary2.length > 0) {
					field = sary2[1].trim().toLowerCase();
					if (field.indexOf(")") > -1) {
						field = field.replace(")", "");
					}
					if (params.getValue(field) == null) {
						throw new IllegalArgumentException(field + " 條件參數不可為NULL ");
					}
				}
			}
		} else {
			String[] sary2 = conditions.split(":");
			field = sary2[1].trim().toLowerCase();
			if (field.indexOf(")") > -1) {
				field = field.replace(")", "");
			}
			if (params.getValue(field) == null) {
				throw new IllegalArgumentException(field + " 條件參數不可為NULL ");
			}
		}
	}

	/**
	 * 查詢資料
	 *
	 * @param Domain物件
	 * @param where 條件，EX: apno = :apno
	 *
	 * return 單筆資料
	 */
	@SuppressWarnings("unchecked")
	public List<T> selectDataByWhereCondition(T t, String conditions) {
		paramsAllEmptyThrowException(t, conditions);
		Table tableAnno = t.getClass().getAnnotationsByType(Table.class)[0];
		String tableName = tableAnno.value();
		StringBuilder sql = new StringBuilder();
		StringBuilder columns = new StringBuilder();
		Arrays.asList(t.getClass().getDeclaredFields()).stream().filter(field -> field.isAnnotationPresent(LogField.class)).forEach(field -> {
			String columnName = field.getAnnotationsByType(LogField.class)[0].value();
			columns.append("T.").append(columnName.toUpperCase()).append(" as ").append(columnName.toLowerCase()).append(",");
		});
		columns.setLength(columns.length() - 1);
		sql.append(" SELECT ").append(columns).append(" FROM " + tableName + " T ").append(" WHERE ").append(conditions);
		log.debug(sql.toString());
		SqlParameterSource params = new BeanPropertySqlParameterSource(t);
		return (List<T>) getNamedParameterJdbcTemplate().query(sql.toString(), params, BeanPropertyRowMapper.newInstance(t.getClass()));
	}

	/**
	 * 查詢資料 - PK為Domain設定欄位
	 *
	 * @param Domain物件
	 * return Domain物件
	 */
	public T selectByPK(T t) {
		StringBuilder conditions = new StringBuilder();
		Arrays.asList(t.getClass().getDeclaredFields()).stream().filter(field -> field.isAnnotationPresent(PkeyField.class)).forEach(field -> {
			String columnName = field.getAnnotationsByType(PkeyField.class)[0].value();
			conditions.append("T.").append(columnName.toUpperCase()).append(" = ").append(":").append(columnName.toLowerCase()).append(" AND ");
		});
		conditions.setLength(conditions.length() - 5);
		List<T> list = selectDataByWhereCondition(t, conditions.toString());
		return CollectionUtils.isEmpty(list) ? null : list.get(0);
	}

	/**
	 * 更新資料 - PK為Domain設定欄位
	 *
	 * @param Domain物件
	 * return 更新筆數
	 */
	public int updateByPK(T t) {
		Table tableAnno = t.getClass().getAnnotationsByType(Table.class)[0];
		String tableName = tableAnno.value();
		StringBuilder sql = new StringBuilder();
		StringBuilder columns = new StringBuilder();
		StringBuilder conditions = new StringBuilder();
		Arrays.asList(t.getClass().getDeclaredFields()).stream().filter(field -> field.isAnnotationPresent(LogField.class)).forEach(field -> {
			String columnName = field.getAnnotationsByType(LogField.class)[0].value();
			columns.append(columnName.toUpperCase()).append(" = ").append(":").append(columnName.toLowerCase()).append(",");
		});
		Arrays.asList(t.getClass().getDeclaredFields()).stream().filter(field -> field.isAnnotationPresent(PkeyField.class)).forEach(field -> {
			String columnName = field.getAnnotationsByType(PkeyField.class)[0].value();
			conditions.append(columnName.toUpperCase()).append(" = ").append(":").append(columnName.toLowerCase()).append(" AND ");
		});
		columns.setLength(columns.length() - 1);
		conditions.setLength(conditions.length() - 5);
		paramsAllEmptyThrowException(t, conditions.toString());
		sql.append(" UPDATE ").append(tableName).append(" SET ").append(columns).append(" WHERE ").append(conditions);
		log.debug(sql.toString());
		SqlParameterSource params = new BeanPropertySqlParameterSource(t);
		return getNamedParameterJdbcTemplate().update(sql.toString(), params);
	}

	/**
	 * 更新資料 - PK為Domain設定欄位
	 *
	 * @param Domain物件
	 * return 更新筆數
	 */
	public int updateByPKForMssql(T t) {
		Table tableAnno = t.getClass().getAnnotationsByType(Table.class)[0];
		String tableName = tableAnno.value();
		StringBuilder sql = new StringBuilder();
		StringBuilder columns = new StringBuilder();
		StringBuilder conditions = new StringBuilder();
		Arrays.asList(t.getClass().getDeclaredFields()).stream().filter(field -> field.isAnnotationPresent(LogField.class)).forEach(field -> {
			String columnName = field.getAnnotationsByType(LogField.class)[0].value();
			columns.append(columnName.toUpperCase()).append(" = ").append(":").append(columnName.toLowerCase()).append(",");
		});
		Arrays.asList(t.getClass().getDeclaredFields()).stream().filter(field -> field.isAnnotationPresent(PkeyField.class)).forEach(field -> {
			String columnName = field.getAnnotationsByType(PkeyField.class)[0].value();
			conditions.append(columnName.toUpperCase()).append(" = ").append(":").append(columnName.toLowerCase()).append(" AND ");
		});
		columns.setLength(columns.length() - 1);
		conditions.setLength(conditions.length() - 5);
		paramsAllEmptyThrowException(t, conditions.toString());
		sql.append(" UPDATE ").append(tableName).append(" SET ").append(columns).append(" WHERE ").append(conditions);
		log.debug(sql.toString());
		SqlParameterSource params = new BeanPropertySqlParameterSource(t);
		return getNamedParameterJdbcTemplate().update(sql.toString(), params);
	}

	/**
	 * 刪除資料 - PK為Domain設定欄位
	 *
	 * @param Domain物件
	 * return 新增筆數
	 */
	public int deleteByPK(T t) {
		Table tableAnno = t.getClass().getAnnotationsByType(Table.class)[0];
		String tableName = tableAnno.value();
		StringBuilder sql = new StringBuilder();
		StringBuilder conditions = new StringBuilder();
		Arrays.asList(t.getClass().getDeclaredFields()).stream().filter(field -> field.isAnnotationPresent(PkeyField.class)).forEach(field -> {
			String columnName = field.getAnnotationsByType(PkeyField.class)[0].value();
			conditions.append(columnName.toUpperCase()).append(" = ").append(":").append(columnName.toLowerCase()).append(" AND ");
		});
		conditions.setLength(conditions.length() - 5);
		paramsAllEmptyThrowException(t, conditions.toString());
		sql.append(" DELETE ").append(tableName);
		sql.append(" WHERE ");
		sql.append(conditions);
		log.debug(sql.toString());
		SqlParameterSource params = new BeanPropertySqlParameterSource(t);
		return getNamedParameterJdbcTemplate().update(sql.toString(), params);
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}


}
