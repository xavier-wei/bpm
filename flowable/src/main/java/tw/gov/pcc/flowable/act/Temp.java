package tw.gov.pcc.flowable.act;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;

public class Temp implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) {
        System.out.println("執行成功");
    }
}
