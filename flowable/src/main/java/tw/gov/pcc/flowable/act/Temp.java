package tw.gov.pcc.flowable.act;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.springframework.jndi.JndiTemplate;

import javax.naming.NamingException;
import javax.sql.DataSource;

public class Temp implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) {
        // get jndi Datasource which pool name is hikari and dbname is eip

    }
}
