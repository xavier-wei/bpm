package tw.gov.pcc.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class SubordinateRepository {
    private JdbcTemplate jdbcTemplate;
    private final String FIND_SUBORDINATE =
        "SELECT PECARD \n" +
            "FROM [view_cpape05m]\n" +
            "where 1=1\n" +
            "  and [PEORG] = '360000000G'\n" +
            "  and PELEVDATE = ''\n" +
            "  and PEUNIT != '600037'\n" +
            "  and PEUNIT in (\n" +
            "    SELECT [unit_id]\n" +
            "    FROM [view_oup_unit]\n" +
            "    where uhead_pos_id in (\n" +
            "        SELECT [posid]\n" +
            "        FROM [position] P\n" +
            "                 left join [view_cpape05m] C on P.id = C.PEIDNO\n" +
            "        where C.PECARD = ?\n" +
            "        UNION ALL\n" +
            "        SELECT [posid] FROM [position]\n" +
            "        WHERE [fid] in (\n" +
            "            SELECT [posid]\n" +
            "            FROM [position] P\n" +
            "                     left join [view_cpape05m] C on P.id = C.PEIDNO\n" +
            "            where C.PECARD = ?\n" +
            "        )\n" +
            "        UNION ALL\n" +
            "        SELECT [posid] FROM [position]\n" +
            "        WHERE [fid] in (\n" +
            "            SELECT [posid] FROM [position]\n" +
            "            WHERE [fid] in (\n" +
            "                SELECT [posid]\n" +
            "                FROM [position] P\n" +
            "                         left join [view_cpape05m] C on P.id = C.PEIDNO\n" +
            "                where C.PECARD = ?\n" +
            "            )\n" +
            "        )\n" +
            "    )\n" +
            ")\n" +
            "order by PENAME";

    public SubordinateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Map<String, Object>> executeQuery(String pecard) {
        return jdbcTemplate.queryForList(FIND_SUBORDINATE,pecard,pecard,pecard);
    }
}
