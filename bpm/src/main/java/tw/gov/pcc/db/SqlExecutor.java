package tw.gov.pcc.db;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.intellij.lang.annotations.Language;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;
import tw.gov.pcc.utils.PwcPropertyUtils;

import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.Map.Entry;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;

/**
 * SQL 執行程式，提供一般 CRUD 功能。
 * <p>
 * 底層是 Spring Framework 的 {@link NamedParameterJdbcTemplate}，所以本程式所指的 SQL 均可以包含定名參數，例如
 * {@code SELECT * FROM T WHERE T.A = :a"}，當中的 {@code a} 就是參數 Map 中的鍵值。<strong>強烈建議使用
 * {@link Query#builder(String, Object...)} 協助產生 Query 物件。</strong>
 */
@Component
public class SqlExecutor {

    private final Logger log = LoggerFactory.getLogger(SqlExecutor.class);

    @Getter
    @Setter
    private Class<? extends RowMapper> rowMapper = BeanPropertyRowMapper.class;

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final Resources resources;

    private final int batchSize;

    public SqlExecutor(
            final NamedParameterJdbcTemplate jdbcTemplate,
            final Resources resources,
            @Value("${spring.jpa.properties.hibernate.jdbc.batch_size:50}") final int batchSize
    ) {
        this.jdbcTemplate = jdbcTemplate;
        this.resources = resources;
        this.batchSize = batchSize;
    }

    /**
     * 執行無參數 DELETE SQL 語句。
     *
     * @param sql DELETE SQL 語句。
     * @return 資料刪除筆數。
     */
    public int delete(@Language("SQL") String sql) {
        return delete(new Query(sql));
    }

    /**
     * 整批執行 DELETE SQL 語句。
     *
     * @param sql              DELETE SQL 語句。
     * @param listOfParameters 每句 SQL 的參數 Map 集合。
     * @return 每次執行的資料刪除筆數。
     */
    public int[] delete(@Language("SQL") String sql, List<Map<String, ?>> listOfParameters) {
        return executeInBatch(sql, listOfParameters);
    }

    /**
     * 執行 DELETE SQL 語句。
     *
     * @param sql        DELETE SQL 語句。
     * @param parameters 參數集。
     * @return 資料刪除筆數。
     */
    public int delete(@Language("SQL") String sql, Map<String, ?> parameters) {
        return delete(new Query(sql, parameters));
    }

    /**
     * 執行 DELETE Query。
     *
     * @param query 包含 SQL 語句，也可能有參數的 Query 物件。
     * @return 資料刪除筆數。
     */
    public int delete(Query query) {
        return execute(query);
    }

    public int execute(Query query) {
        requireNonNull(query, "Parameter \"query\" must not be null.");

        String queryString = query.getString();
        Map<String, Object> parameters = query.getParameters();
        logSqlExecute(queryString, parameters);

        return jdbcTemplate.update(queryString, parameters);
    }

    public int execute(Query query, KeyHolder generatedKeyHolder) {
        requireNonNull(query, "Parameter \"query\" must not be null.");

        String queryString = query.getString();
        Map<String, Object> parameters = query.getParameters();
        logSqlExecute(queryString, parameters);

        return jdbcTemplate.update(queryString, new MapSqlParameterSource(parameters), generatedKeyHolder);
    }


    @SuppressWarnings("unchecked")
    private int[] executeInBatch(String sql, List<Map<String, ?>> parameters) {
        if (!isNotNullAndNotEmpty(sql)) {
            throw new IllegalArgumentException("Query string must not be null.");
        }
        requireNonNull(parameters, "Conditions must not be null. SQL without parameter is inadequate to use in batch method.");

        logExecuteInBatch(sql, parameters);

        return jdbcTemplate.batchUpdate(sql, parameters.toArray(new Map[parameters.size()]));
    }

    /**
     * 執行無參數 INSERT SQL 語句。
     *
     * @param sql INSERT SQL 語句。
     * @return 資料新增筆數。
     */
    public int insert(@Language("SQL") String sql) {
        return insert(new Query(sql));
    }

    /**
     * 整批執行 INSERT SQL 語句。
     *
     * @param sql              INSERT SQL 語句。
     * @param listOfParameters 每句 SQL 的參數 Map 集合。
     * @return 每次執行的資料新增筆數。
     */
    public int[] insert(@Language("SQL") String sql, List<Map<String, ?>> listOfParameters) {
        return executeInBatch(sql, listOfParameters);
    }

    /**
     * 執行 INSERT SQL 語句。
     *
     * @param sql        INSERT SQL 語句。
     * @param parameters 參數集。
     * @return 資料新增筆數。
     */
    public int insert(@Language("SQL") String sql, Map<String, ?> parameters) {
        return insert(new Query(sql, parameters));
    }

    /**
     * 執行 INSERT Query。
     *
     * @param query 包含 SQL 語句，也可能有參數的 Query 物件。
     * @return 資料新增筆數。
     */
    public int insert(Query query) {
        return execute(query);
    }

    private boolean isNotNullAndNotEmpty(String sql) {
        return sql != null && !sql.trim().isEmpty();
    }

    private void logSqlExecute(String sql, Map<String, ?> parameters) {
        if (log.isDebugEnabled()) {
            log.debug("Executing SQL:{}", sql);
            if (parameters == null || parameters.isEmpty()) {
                log.debug("With NO parameters!");
            } else {
                for (Entry<String, ?> entry : parameters.entrySet()) {
                    log.debug("Param:{}, Value:{}", entry.getKey(), entry.getValue());
                }
            }
        }
    }

    private void logExecuteInBatch(String sql, List<Map<String, ?>> parametersList) {
        if (log.isDebugEnabled()) {
            log.debug("Executing SQL:{}", sql);
            for (int i = 0; i < parametersList.size(); i++) {
                Map<String, ?> parameters = parametersList.get(0);
                log.debug("List[{}]", i);
                if (parameters == null || parameters.isEmpty()) {
                    log.debug("With NO parameters!");
                } else {
                    for (Entry<String, ?> entry : parameters.entrySet()) {
                        log.debug("Param:{}, Value:{}", entry.getKey(), entry.getValue());
                    }
                }
            }
        }
    }

    /**
     * 執行 SELECT SQL 語句，取得所有符合的資料。
     *
     * @param <T>   資料型別。
     * @param sql   SELECT SQL 語句。
     * @param clazz 查詢資料型別。
     * @return 符合查詢條件的資料清單; 如果完全沒有符合的資料，則回傳空的清單。
     */
    public <T> List<T> queryForList(@Language("SQL") String sql, Class<T> clazz) {
        return queryForList(new Query(sql), clazz);
    }

    /**
     * 執行 SELECT SQL 語句，取得所有符合的資料。
     *
     * @param <T>        資料型別。
     * @param sql        SELECT SQL 語句。
     * @param parameters 參數集。
     * @param clazz      查詢資料型別。
     * @return 符合查詢條件的資料清單; 如果完全沒有符合的資料，則回傳空的清單。
     */
    public <T> List<T> queryForList(@Language("SQL") String sql, Map<String, ?> parameters, Class<T> clazz) {
        return queryForList(new Query(sql, parameters), clazz);
    }

    public <T> List<T> queryListByResource(String resourceName, Map<String, ?> parameters, Class<T> clazz) {
        String sql = resources.readSqlFromResouces(resourceName);
        return queryForList(new Query(sql, parameters), clazz);
    }

    public <T extends Serializable, C extends Serializable> List<T> queryListByResource(
            final String resourceName,
            final Map<String, ?> parameters,
            final Map<String, Predicate<C>> predicates,
            final Class<T> clazz) {
        final String sql = this.generateSql(resourceName, parameters, predicates, null);
        return queryForList(new Query(sql, parameters), clazz);
    }

    public <C extends Serializable> List<Map<String, Object>> queryListByResource(
            final String resourceName,
            final Map<String, ?> parameters,
            final Map<String, Predicate<C>> predicates,
            final C criteria) {
        final String sql = this.generateSql(resourceName, parameters, predicates, criteria);
        return queryForList(new Query(sql, parameters));
    }

    public <T extends Serializable, C extends Serializable> List<T> queryListByResource(
            final String resourceName,
            final Map<String, ?> parameters,
            final Map<String, Predicate<C>> predicates,
            final C criteria,
            final Class<T> clazz) {
        final String sql = this.generateSql(resourceName, parameters, predicates, criteria);
        return queryForList(new Query(sql, parameters), clazz);
    }

    private <C extends Serializable> String generateSql(
            final String resourceName,
            final Map<String, ?> parameters,
            final Map<String, Predicate<C>> predicates,
            final C criteria) {
        final String resourceSql = resources.readSqlFromResouces(resourceName);
        if (MapUtils.isEmpty(predicates)) {
            throw new IllegalArgumentException("predicates is empty");
        }

        final Pattern paramPattern = Pattern.compile(":\\w+");
        final StringBuilder tmp = new StringBuilder(resourceSql);
        for (Entry<String, Predicate<C>> entry : predicates.entrySet()) {
            final Pattern pattern = Pattern.compile("\\$\\{(" + entry.getKey() + ")\\}");
            final Matcher m = pattern.matcher(tmp);
            int start = 0;
            int end = 0;
            String key = null;
            String fullKey = null;

            // find start tag
            if (m.find()) {
                key = m.group(1);
                fullKey = m.group();
                start = m.start();

                // find end tag
                if (m.find()) {
                    end = m.end();
                    if (m.find()) {
                        throw new IllegalArgumentException("found predicate key=" + entry.getKey() + " more than twice");
                    }
                } else {
                    throw new IllegalArgumentException("predicate end key=" + entry.getKey() + " not found");
                }
            } else {
                throw new IllegalArgumentException("predicate key=" + entry.getKey() + " not found");
            }

            C testObj = null;
            if (null == criteria) {
                if (!parameters.containsKey(key)) {
                    throw new IllegalArgumentException("parameters key=" + key + " not found");
                }
                testObj = (C) parameters.get(key);
            } else {
                testObj = criteria;
            }
            final boolean isPass = entry.getValue().test(testObj);
            if (isPass) {
                tmp.delete(end - fullKey.length(), end).delete(start, start + fullKey.length());
            } else {
                final String optionalSql = tmp.substring(start + fullKey.length(), end - fullKey.length());
                final Matcher matcher = paramPattern.matcher(optionalSql);
                while (matcher.find()) {
                    final String paramKey = matcher.group().substring(1);
                    parameters.remove(paramKey);
                }
                tmp.delete(start, end);
            }
        }
        return Stream.of(tmp.toString().split(System.lineSeparator()))
                .filter(s -> StringUtils.isNotBlank(s))
                .map(s -> s.replaceAll("\\s+", " "))
                .collect(Collectors.joining(System.lineSeparator()));
    }

    public List<Map<String, Object>> queryListByResource(String resourceName, Map<String, ?> parameters) {
        final String sql = resources.readSqlFromResouces(resourceName);
        return queryForList(new Query(sql, parameters));
    }

    public Map<String, Object> queryMapByResource(String resourceName, Map<String, ?> parameters) {
        final String sql = resources.readSqlFromResouces(resourceName);
        return queryForMap(new Query(sql, parameters));
    }

    public <T> Stream<T> queryStreamByResource(String resourceName, Map<String, ?> parameters, final Class<T> clazz) {
        final String queryString = resources.readSqlFromResouces(resourceName);
        final RowMapper mapper = BeanPropertyRowMapper.newInstance(clazz);
        return this.jdbcTemplate.queryForStream(queryString, parameters, mapper);
    }

    public Query.Builder createBuilderByResource(String resourceName) {
        String sql = resources.readSqlFromResouces(resourceName);
        return Query.builder(sql);
    }

    /**
     * 執行 SELECT SQL 語句，取得所有符合的資料。
     *
     * @param <T>   資料型別。
     * @param query 包含 SQL 語句，也可能有參數的 Query 物件。
     * @param clazz 查詢資料型別。
     * @return 符合查詢條件的資料清單; 如果完全沒有符合的資料，則回傳空的清單。
     */
    @SuppressWarnings("unchecked")
    public <T> List<T> queryForList(Query query, Class<T> clazz) {
        String queryString = query.getString();
        Map<String, Object> parameters = query.getParameters();

        logSqlExecute(queryString, parameters);

        if (isSimpleType(clazz)) {
            return this.jdbcTemplate.queryForList(queryString, parameters, clazz);
        } else {
            final RowMapper mapper = BeanPropertyRowMapper.newInstance(clazz);
            return this.jdbcTemplate.query(queryString, parameters, mapper);
        }
    }


    public List<Map<String, Object>> queryForList(Query query) {
        String queryString = query.getString();
        Map<String, Object> parameters = query.getParameters();
        logSqlExecute(queryString, parameters);
        return this.jdbcTemplate.queryForList(queryString, parameters);
    }

    public Map<String, Object> queryForMap(Query query) {
        String queryString = query.getString();
        Map<String, Object> parameters = query.getParameters();
        logSqlExecute(queryString, parameters);
        return this.jdbcTemplate.queryForMap(queryString, parameters);
    }

    private <T> boolean isSimpleType(Class<T> clazz) {
        return (
                ClassUtils.isPrimitiveOrWrapper(clazz) ||
                        clazz.equals(String.class) ||
                        clazz.equals(BigDecimal.class) ||
                        clazz.equals(BigInteger.class)
        );
    }

    /**
     * 執行無參數 UPDATE SQL 語句。
     *
     * @param sql UPDATE SQL 語句。
     * @return 資料異動筆數。
     */
    public int update(@Language("SQL") String sql) {
        return update(new Query(sql));
    }

    /**
     * 整批執行 UPDATE SQL 語句。
     *
     * @param sql              UPDATE SQL 語句。
     * @param listOfParameters 每句 SQL 的參數 Map 集合。
     * @return 每次執行的資料異動筆數。
     */
    public int[] update(@Language("SQL") String sql, List<Map<String, ?>> listOfParameters) {
        return executeInBatch(sql, listOfParameters);
    }

    /**
     * 執行 UPDATE SQL 語句。
     *
     * @param sql        UPDATE SQL 語句。
     * @param parameters 參數集。
     * @return 資料異動筆數。
     */
    public int update(@Language("SQL") String sql, Map<String, ?> parameters) {
        return update(new Query(sql, parameters));
    }

    /**
     * 執行 UPDATE Query。
     *
     * @param query 包含 SQL 語句，也可能有參數的 Query 物件。
     * @return 資料異動筆數。
     */
    public int update(Query query) {
        return execute(query);
    }


    public int executeByResource(String resourceName) {
        final String sql = resources.readSqlFromResouces(resourceName);
        return executeByResource(new Query(sql));
    }

    public int[] executeByResource(String resourceName, List<Map<String, ?>> listOfParameters) {
        final String sql = resources.readSqlFromResouces(resourceName);
        return executeInBatch(sql, listOfParameters);
    }

    public int executeByResource(String resourceName, Map<String, ?> parameters) {
        final String sql = resources.readSqlFromResouces(resourceName);
        return executeByResource(new Query(sql, parameters));
    }

    public int executeByResource(String resourceName, Map<String, ?> parameters, KeyHolder generatedKeyHolder) {
        final String sql = resources.readSqlFromResouces(resourceName);
        return executeByResource(new Query(sql, parameters), generatedKeyHolder);
    }

    public int executeByResource(Query query, KeyHolder generatedKeyHolder) {
        return execute(query, generatedKeyHolder);
    }

    public int executeByResource(Query query) {
        return execute(query);
    }

    public int updateByResource(String resourceName, Map<String, ?> parameters) {
        String sql = resources.readSqlFromResouces(resourceName);
        return update(new Query(sql, parameters));
    }

    /**
     * 指定參數型別(ex. Types.NVARCHAR)
     *
     * @param resourceName
     * @param vNamedParameters
     */
    public void updateByResource(String resourceName, MapSqlParameterSource vNamedParameters) {
        String sql = resources.readSqlFromResouces(resourceName);
        jdbcTemplate.update(sql, vNamedParameters);
    }

    /**
     * Streaming ResultSet <br/>
     * 使用需使用 try-with-resource 關閉 stream，否則連線不會釋放
     */
    public <T> Stream<T> queryForStream(final Query query, final Class<T> clazz) {
        final String queryString = query.getString();
        final Map<String, Object> parameters = query.getParameters();

        logSqlExecute(queryString, parameters);

        final RowMapper mapper = BeanPropertyRowMapper.newInstance(clazz);
        return this.jdbcTemplate.queryForStream(queryString, parameters, mapper);
    }

    public String readSqlFromResouces(String resourceName) {
        return resources.readSqlFromResouces(resourceName);
    }

    /**
     * 批次新增資料，由 entity class 組出 insert sql
     * @param clazz entity class
     * @param entities 要新增的資料
     */
    public <T> int[] insertInBatch(final Class<T> clazz, final List<T> entities) {
        if (CollectionUtils.isEmpty(entities)) {
            return null;
        }
        requireNonNull(clazz, "entity class is null");

        final Table tableAnno = AnnotationUtils.findAnnotation(clazz, Table.class);
        requireNonNull(tableAnno, "Table Annotation is null");
        final String tableName = tableAnno.name();

        final Map<String, String> columns = new LinkedHashMap<>();
        final Field[] fields = FieldUtils.getAllFields(clazz);
        for (Field field : fields) {
            final Column column = AnnotationUtils.findAnnotation(field, Column.class);
            if (column != null) {
                final String dbName = column.name();
                final String propertyName = field.getName();
                columns.put(propertyName, dbName);
            }
        }

        final String columnNames = columns.values().stream().collect(Collectors.joining(", "));
        final String keys = columns.keySet().stream().map(key -> ":" + key).collect(Collectors.joining(", "));
        final StringBuilder sb = new StringBuilder("INSERT INTO ");
        sb.append(tableName);
        sb.append("(");
        sb.append(columnNames);
        sb.append(") VALUES (");
        sb.append(keys);
        sb.append(")");

        final List<Map<String, ?>> params = new ArrayList<>();
        entities.stream().forEach(entity -> {
            final Map<String, Object> param = new HashMap<>();
            columns.keySet().stream().forEach(key -> {
                final Object value = PwcPropertyUtils.getProperty(entity, key);
                param.put(key, value);
            });
            params.add(param);
        });
        final String sql = sb.toString();
        final int size = params.size();
        if (size < batchSize) {
            return this.executeInBatch(sql, params);
        } else {
            final int[] results = new int[size];
            int start = 0;
            int end = batchSize;
            int index = 0;
            while (start < end) {
                final int[] ints = this.executeInBatch(sql, params.subList(start, end));
                for (int count : ints) {
                    results[index++] = count;
                }
                start += batchSize;
                end = (end + batchSize) > size ? size : end + batchSize;
            }
            return results;
        }
    }
}
