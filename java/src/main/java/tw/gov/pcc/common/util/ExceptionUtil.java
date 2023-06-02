package tw.gov.pcc.common.util;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 處理 Exception 的公用程式
 *
 * @author Goston
 */
public class ExceptionUtil {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ExceptionUtil.class);

    public static String getStackTrace(Exception e) {
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }
}
