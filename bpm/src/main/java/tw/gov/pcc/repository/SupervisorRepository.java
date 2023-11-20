package tw.gov.pcc.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class SupervisorRepository {
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final String FIND_SUPERVISOR =
        "SELECT A.[UNIT_NAME] " +
            "     ,A.[PEIDNO] " +
            "     ,A.[PECARD] " +
            "     ,A.[PENAME] " +
            "     ,P1.POSNAME " +
            "     ,F1.[UNIT_NAME] F1_UNIT_NAME " +
            "     ,P2.ID F1_ID " +
            "     ,F1.PECARD F1_ACCOUNT " +
            "     ,F1.PENAME F1_PENAME " +
            "     ,P2.POSNAME F1_POSNAME " +
            "     ,F2.[UNIT_NAME] F2_UNIT_NAME " +
            "     ,P3.ID F2_ID " +
            "     ,F2.PECARD F2_ACCOUNT " +
            "     ,F2.PENAME F2_PENAME " +
            "     ,P3.POSNAME F2_POSNAME " +
            "     ,P4.ID F3_ID " +
            "     ,F3.PECARD F3_ACCOUNT " +
            "     ,F3.PENAME F3_PENAME " +
            "     ,P4.POSNAME F3_POSNAME " +
            "FROM [eip].[dbo].[view_cpape05m] A " +
            "         LEFT JOIN POSITION P1 ON  A.PEIDNO =  P1.ID " +
            "         LEFT JOIN POSITION P2 ON  P1.FID =  P2.POSID " +
            "         LEFT JOIN [view_cpape05m] F1 ON F1.PEIDNO = P2.ID " +
            "         LEFT JOIN POSITION P3 ON  P2.FID =  P3.POSID " +
            "         LEFT JOIN [view_cpape05m] F2 ON F2.PEIDNO = P3.ID " +
            "         LEFT JOIN POSITION P4 ON  P3.FID =  P4.POSID " +
            "         LEFT JOIN [view_cpape05m] F3 ON F3.PEIDNO = P4.ID " +
            "WHERE 1=1" +
            "  AND A.[PEORG] = ? " +
            "  AND A.PECARD = ? ";

    private static final String FIND_SUPERVISOR_OTHERS =
        "SELECT A.[UNIT_NAME] " +
            "     ,A.[PEIDNO] " +
            "     ,A.[PECARD] " +
            "     ,A.[PENAME] " +
            "     ,P1.POSNAME " +
            "     ,F1.[UNIT_NAME] F1_UNIT_NAME " +
            "     ,P2.ID F1_ID " +
            "     ,F1.PECARD F1_ACCOUNT " +
            "     ,F1.PENAME F1_PENAME " +
            "     ,P2.POSNAME F1_POSNAME " +
            "     ,F2.[UNIT_NAME] F2_UNIT_NAME " +
            "     ,P3.ID F2_ID " +
            "     ,F2.PECARD F2_ACCOUNT " +
            "     ,F2.PENAME F2_PENAME " +
            "     ,P3.POSNAME F2_POSNAME " +
            "     ,P4.ID F3_ID " +
            "     ,F3.PECARD F3_ACCOUNT " +
            "     ,F3.PENAME F3_PENAME " +
            "     ,P4.POSNAME F3_POSNAME " +
            "FROM [eip].[dbo].[VIEW_CPAPE05M_OTHERS] A " +
            "         LEFT JOIN POSITION_OTHERS P1 ON  A.PEIDNO =  P1.ID " +
            "         LEFT JOIN POSITION_OTHERS P2 ON  P1.FID =  P2.POSID " +
            "         LEFT JOIN [VIEW_CPAPE05M_OTHERS] F1 ON F1.PEIDNO = P2.ID " +
            "         LEFT JOIN POSITION_OTHERS P3 ON  P2.FID =  P3.POSID " +
            "         LEFT JOIN [VIEW_CPAPE05M_OTHERS] F2 ON F2.PEIDNO = P3.ID " +
            "         LEFT JOIN POSITION_OTHERS P4 ON  P3.FID =  P4.POSID " +
            "         LEFT JOIN [VIEW_CPAPE05M_OTHERS] F3 ON F3.PEIDNO = P4.ID " +
            "WHERE 1=1" +
            "  AND A.[PEORG] = ? " +
            "  AND A.PECARD = ? ";
    private static final String FIND_SPECILIST =
        "SELECT PECARD FROM view_cpape05m where PEIDNO =" +
            " (SELECT id " +
            "  from position " +
            "  where depart = '600038' " +
            "  and posname = '技正')";

    private static final String FIND_SUPERVISOR_RULE_MODEL =
        "SELECT PECARD FROM VIEW_CPAPE05M " +
            "WHERE PEIDNO = (SELECT TOP 1 ID FROM POSITION WHERE DEPART = :DEPART AND POSNAME = :POSNAME)";
    private static final String FIND_SUPERVISOR_RULE_MODEL_OTHERS =
        "SELECT PECARD FROM VIEW_CPAPE05M_OTHERS " +
            "WHERE PEIDNO = (SELECT TOP 1 ID FROM POSITION_OTHERS WHERE DEPART = :DEPART AND POSNAME = :POSNAME)";


    public SupervisorRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }


    public List<Map<String, Object>> executeQuery(String orgId, String userId) {
        List<Map<String, Object>> supervisors = jdbcTemplate.queryForList(FIND_SUPERVISOR, orgId, userId);
        if (supervisors.isEmpty()) {
            supervisors = jdbcTemplate.queryForList(FIND_SUPERVISOR_OTHERS, orgId, userId);
        }
        return supervisors;
    }

    public List<Map<String, Object>> executeQueryInfoGroup() {
        return jdbcTemplate.queryForList(FIND_SPECILIST);
    }

    public String executeQueryRuleModel(String depart, String posname) {

        List<Map<String, Object>> maps = namedParameterJdbcTemplate.queryForList(FIND_SUPERVISOR_RULE_MODEL, Map.of("DEPART", depart, "POSNAME", posname));

        if (maps.isEmpty()) {
            maps = namedParameterJdbcTemplate.queryForList(FIND_SUPERVISOR_RULE_MODEL_OTHERS, Map.of("DEPART", depart, "POSNAME", posname));
        }

        if (maps.isEmpty()) {
            return null;
        } else {
            return (String) maps.get(0).get("PECARD");
        }

    }


}
