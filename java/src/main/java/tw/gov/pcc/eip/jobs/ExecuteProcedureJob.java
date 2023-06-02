package tw.gov.pcc.eip.jobs;


import javax.sql.DataSource;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

@DisallowConcurrentExecution
public class ExecuteProcedureJob extends QuartzJob {

    @Autowired
    private DataSource dataSource;
    private String procedureName;
    private Object[] procedureParams;
    
    @Override
    protected void handler(JobExecutionContext context) throws JobExecutionException {
        String query = String.format("{call %s %s}", procedureName, getDeclareParams());
        new NamedParameterJdbcTemplate(dataSource).update(query, getParams());

    }

    public void setProcedureName(String procedureName) {
        this.procedureName = procedureName;
    }

    public void setProcedureParams(Object[] procedureParams) {
        this.procedureParams = procedureParams;
    }

    private String getDeclareParams() {
        if (procedureParams != null && procedureParams.length > 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("(");
            for (int i = 0, l = procedureParams.length ; i < l ; i++) {
                sb.append(":" + "p" + i);
                if (i < l - 1) {
                    sb.append(",");
                }
            }
            sb.append(")");
            return sb.toString();
        }
        return "";
    }
    
    private SqlParameterSource getParams() {
        MapSqlParameterSource p = new MapSqlParameterSource();
        if (procedureParams != null && procedureParams.length > 0) {
            for (int i = 0, l = procedureParams.length ; i < l ; i++) {
                p.addValue("p" + i, procedureParams[i]);
            }
        }
        return p;
    }
}
