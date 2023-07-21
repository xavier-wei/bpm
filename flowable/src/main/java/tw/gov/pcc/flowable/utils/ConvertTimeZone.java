package tw.gov.pcc.flowable.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class ConvertTimeZone {
    public static String convertTimezoneTaipei(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        TimeZone timeZone = TimeZone.getTimeZone("Asia/Taipei");
        sdf.setTimeZone(timeZone);
        return sdf.format(date);
    }
}
