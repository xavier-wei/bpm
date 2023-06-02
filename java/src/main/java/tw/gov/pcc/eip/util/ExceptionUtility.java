package tw.gov.pcc.eip.util;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 處理 Exception 的公用程式
 *
 * @author Goston
 */
public class ExceptionUtility {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ExceptionUtility.class);

    public static String getStackTrace(Throwable e) {
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }
}
