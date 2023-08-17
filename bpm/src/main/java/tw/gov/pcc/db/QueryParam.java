package tw.gov.pcc.db;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QueryParam {

    private String queryString;

    private Object[] parameters;

    public QueryParam(final String queryString) {
        this.queryString = queryString;
    }

    public static QueryParam of(final String queryString, final Object... parameters) {
        return new QueryParam(queryString, parameters);
    }

}
