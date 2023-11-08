package tw.gov.pcc.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class SubordinateRepository {
    private JdbcTemplate jdbcTemplate;
    private final String FIND_SUBORDINATE =
        "SELECT PECARD  " +
            "FROM [view_cpape05m] " +
            "where 1=1 " +
            "  and [PEORG] = '360000000G' " +
            "  and PELEVDATE = '' " +
            "  and PEUNIT != '600037' " +
            "  and PEUNIT in ( " +
            "    SELECT [unit_id] " +
            "    FROM [view_oup_unit] " +
            "    where uhead_pos_id in ( " +
            "        SELECT [posid] " +
            "        FROM [position] P " +
            "                 left join [view_cpape05m] C on P.id = C.PEIDNO " +
            "        where C.PECARD = ? " +
            "        UNION ALL " +
            "        SELECT [posid] FROM [position] " +
            "        WHERE [fid] in ( " +
            "            SELECT [posid] " +
            "            FROM [position] P " +
            "                     left join [view_cpape05m] C on P.id = C.PEIDNO " +
            "            where C.PECARD = ? " +
            "        ) " +
            "        UNION ALL " +
            "        SELECT [posid] FROM [position] " +
            "        WHERE [fid] in ( " +
            "            SELECT [posid] FROM [position] " +
            "            WHERE [fid] in ( " +
            "                SELECT [posid] " +
            "                FROM [position] P " +
            "                         left join [view_cpape05m] C on P.id = C.PEIDNO " +
            "                where C.PECARD = ? " +
            "            ) " +
            "        ) " +
            "    ) " +
            ") " +
            "order by PENAME";

    private final String FIND_SUBORDINATE_TEST =
        "SELECT PECARD  " +
            "FROM [view_cpape05m_others] " +
            "where 1=1 " +
            "  and [PEORG] = '360000000G' " +
            "  and PELEVDATE = '' " +
            "  and PEUNIT != '600037' " +
            "  and PEUNIT in ( " +
            "    SELECT [unit_id] " +
            "    FROM [view_oup_unit_forTest] " +
            "    where uhead_pos_id in ( " +
            "        SELECT [posid] " +
            "        FROM [position_others] P " +
            "                 left join [view_cpape05m_others] C on P.id = C.PEIDNO " +
            "        where C.PECARD = ? " +
            "        UNION ALL " +
            "        SELECT [posid] FROM [position_others] " +
            "        WHERE [fid] in ( " +
            "            SELECT [posid] " +
            "            FROM [position_others] P " +
            "                     left join [view_cpape05m_others] C on P.id = C.PEIDNO " +
            "            where C.PECARD = ? " +
            "        ) " +
            "        UNION ALL " +
            "        SELECT [posid] FROM [position_others] " +
            "        WHERE [fid] in ( " +
            "            SELECT [posid] FROM [position_others] " +
            "            WHERE [fid] in ( " +
            "                SELECT [posid] " +
            "                FROM [position_others] P " +
            "                         left join [view_cpape05m_others] C on P.id = C.PEIDNO " +
            "                where C.PECARD = ? " +
            "            ) " +
            "        ) " +
            "    ) " +
            ") " +
            "order by PENAME";

    public SubordinateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Map<String, Object>> executeQuery(String pecard) {
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(FIND_SUBORDINATE, pecard, pecard, pecard);
        if (maps.isEmpty()) {
            maps = jdbcTemplate.queryForList(FIND_SUBORDINATE_TEST, pecard, pecard, pecard);
        }
        return maps;
    }
}
