package tw.gov.pcc.eip.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

public class DateUtility {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(DateUtility.class);

    /**
     * 將字串轉成 Calendar Class, 若日期格式不合則傳回 null, <br>
     * 可傳入 民國 / 西元 年月日 <br>
     * <p>
     * <pre>
     * 用法:
     *   asCalendar('0890101') 傳回 Calendar 物件
     *   asCalendar('19980101') 傳回 Calendar 物件
     *   asCalendar('19981131') 傳回 null
     * </pre>
     *
     * @param sDate java.lang.String 欲轉換的日期字串
     * @return java.util.Calendar
     */
    public static Calendar asCalendar(String sDate) {
        return asCalendar(sDate, false);
    }

    /**
     * 將字串轉成 Calendar Class, 若日期格式不合則傳回 null, <br>
     * 可傳入 民國 / 民前 / 西元 年月日<br>
     * 若傳入的日期為西元日期, 則忽略 民前 民國 的參數<br>
     *
     * @param sDate      欲轉換的日期字串
     * @param bBeforeROC ture: 民前 fasle: 民國
     * @return
     */
    public static Calendar asCalendar(String sDate, boolean bBeforeROC) {
        int nYear;
        Calendar calendar = null;
        if (sDate.length() == 7) {
            // 七碼碼日期先轉成西元日期
            if (bBeforeROC) nYear = 1912 - Integer.parseInt(sDate.substring(0, 3));
             else nYear = Integer.parseInt(sDate.substring(0, 3)) + 1911;
            sDate = nYear + sDate.substring(3, 7);
        }
        if (sDate.length() == 8) {
            try {
                DateFormat df = new SimpleDateFormat("yyyyMMdd", Locale.TAIWAN);
                Date tm = df.parse(sDate);
                calendar = Calendar.getInstance();
                calendar.setTime(tm);
            } catch (ParseException e) {
                log.error("Calendar asCalendar(String sDate, boolean bBeforeROC) 發生錯誤");
            }
        }
        return calendar;
    }

    /**
     * 將字串轉成 Date Class, 若日期格式不合則傳回 null, <br>
     * 可傳入 民國 / 西元 年月日 <br>
     * <p>
     * <pre>
     * 用法:
     *   asDate('0890101') 傳回 Date 物件
     *   asDate('19980101') 傳回 Date 物件
     *   asDate('19981131') 傳回 null
     * </pre>
     *
     * @param sDate java.lang.String 欲轉換的日期字串
     * @return java.util.Date
     */
    public static Date asDate(String sDate) {
        return asDate(sDate, false);
    }

    /**
     * 將字串轉成 Date Class, 若日期格式不合則傳回 null, <br>
     * 可傳入 民國 / 民前 / 西元 年月日 <br>
     * 若傳入的日期為西元日期, 則忽略 民前 民國 的參數<br>
     *
     * @param sDate      欲轉換的日期字串
     * @param bBeforeROC ture: 民前 fasle: 民國
     * @return
     */
    public static Date asDate(String sDate, boolean bBeforeROC) {
        int nYear;
        Date date = null;
        if (sDate.length() == 7) {
            // 七碼日期先轉成西元日期
            if (bBeforeROC) nYear = 1912 - Integer.parseInt(sDate.substring(0, 3));
             else nYear = Integer.parseInt(sDate.substring(0, 3)) + 1911;
            sDate = nYear + sDate.substring(3, 7);
        }
        if (sDate.length() == 8) {
            try {
                DateFormat df = new SimpleDateFormat("yyyyMMdd", Locale.TAIWAN);
                Date tm = df.parse(sDate);
                date = tm;
            } catch (ParseException e) {
                log.error("Date asDate(String sDate, boolean bBeforeROC) 發生錯誤");
            }
        }
        return date;
    }

    /**
     * 傳入一日期, 傳回該月份有幾天, 若日期格式錯誤則傳回 -1 <br>
     * 可傳入 民國 / 西元 年月日 <br>
     * <p>
     * <pre>
     * 用法:
     *   lastDay(&quot;0890203&quot;) 傳回 29
     *   lastDay(&quot;19980101&quot;) 傳回 31
     * </pre>
     *
     * @param sDate java.lang.String 日期
     * @return int
     */
    public static int lastDay(String sDate) {
        return lastDay(sDate, false);
    }

    /**
     * 傳入一日期, 傳回該月份有幾天, 若日期格式錯誤則傳回 -1 <br>
     * 可傳入 民國 / 民前 / 西元 年月日 <br>
     * 若傳入的日期為西元日期, 則忽略 民前 民國 的參數<br>
     *
     * @param sDate      欲轉換的日期字串
     * @param bBeforeROC ture: 民前 fasle: 民國
     * @return
     */
    public static int lastDay(String sDate, boolean bBeforeROC) {
        DateFormat df = new SimpleDateFormat("yyyyMMdd", Locale.TAIWAN);
        try {
            if (sDate.length() == 7) {
                if (bBeforeROC) sDate = (1912 - Integer.parseInt(sDate.substring(0, 3))) + sDate.substring(3, 7);
                 else sDate = (1911 + Integer.parseInt(sDate.substring(0, 3))) + sDate.substring(3, 7);
            }
            if (sDate.length() == 8) {
                Date tm = df.parse(sDate);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(tm);
                // 此處的邏輯為, 先設定日期為下個月的一日, 再減一天, 即得到本月最後一天
                calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);
                calendar.set(Calendar.DATE, 1);
                calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - 1);
                return calendar.get(Calendar.DATE);
            } else {
                return -1;
            }
        } catch (ParseException e) {
            return -1;
        }
    }

    /**
     * 天數加減運算 <br>
     * 可傳入 民國 / 西元 年月日 <br>
     * <p>
     * <pre>
     * 用法:
     *   calDay(&quot;0890101&quot;, -2) 傳回 &quot;0881230&quot;
     *   calDay(&quot;19980101&quot;, 4) 傳回 &quot;19980105&quot;
     * </pre>
     *
     * @param sDate java.lang.String 欲計算的日期字串
     * @param n     int 欲加減的天數
     * @return java.lang.String
     */
    public static String calDay(String sDate, int n) {
        int nYear;
        boolean bChineseDate = false;
        String sReturnDate = "";
        if (sDate.length() == 7) {
            nYear = Integer.parseInt(sDate.substring(0, 3)) + 1911;
            sDate = nYear + sDate.substring(3, 7);
            bChineseDate = true;
        }
        if (sDate.length() == 8) {
            try {
                DateFormat df = new SimpleDateFormat("yyyyMMdd", Locale.TAIWAN);
                Date tm = df.parse(sDate);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(tm);
                calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + n);
                tm = calendar.getTime();
                sReturnDate = df.format(tm);
            } catch (ParseException e) {
                log.error("String calDay(String sDate, int n) 發生錯誤");
            }
        }
        if (bChineseDate) {
            nYear = Integer.parseInt(sReturnDate.substring(0, 4)) - 1911;
            sReturnDate = nYear + sReturnDate.substring(4, 8);
            sReturnDate = StringUtils.leftPad(sReturnDate, 7, "0");
        }
        return sReturnDate;
    }

    /**
     * 月份加減運算 <br>
     * 可傳入 民國 / 西元 年月日, <br>
     * 若傳入日期正好是月底, 運算後會自動捨去至該月份最後一天 <br>
     * <p>
     * <pre>
     * 用法:
     *   calMonth(&quot;0890101&quot;, -2) 傳回 &quot;0881101&quot;
     *   calMonth(&quot;19980131&quot;, 1) 傳回 &quot;19980228&quot;
     * 注意: 第二例中, 由於傳入的日期為 31 日, 但 2 月只有 28 天, 故回傳的結果為 0228
     * </pre>
     *
     * @param sDate java.lang.String 欲計算的日期字串
     * @param n     int 欲加減的月數
     * @return java.lang.String
     */
    public static String calMonth(String sDate, int n) {
        int nLength = sDate.length();
        if (!(nLength == 7 || nLength == 8)) return "";
        Date newDate = DateUtils.addMonths(asDate(sDate), n);
        if (nLength == 7) return parseDateToChineseDateTime(newDate, false);
         else return parseDateToWestDateTime(newDate, false);
    }

    /**
     * 年份加減運算 <br>
     * 可傳入 民國 / 西元 年月日 <br>
     * <br>
     * <p>
     * <pre>
     * 用法:
     *   calYear(&quot;0890101&quot;, -2) 傳回 &quot;0870101&quot;
     *   calYear(&quot;19980101&quot;, 4) 傳回 &quot;20020101&quot;
     * </pre>
     *
     * @param sDate java.lang.String 欲計算的日期字串
     * @param n     int 欲加減的年數
     * @return java.lang.String
     */
    public static String calYear(String sDate, int n) {
        int nYear;
        boolean bChineseDate = false;
        String sReturnDate = "";
        if (sDate.length() == 7) {
            nYear = Integer.parseInt(sDate.substring(0, 3)) + 1911;
            sDate = nYear + sDate.substring(3, 7);
            bChineseDate = true;
        }
        if (sDate.length() == 8) {
            try {
                DateFormat df = new SimpleDateFormat("yyyyMMdd", Locale.TAIWAN);
                Date tm = df.parse(sDate);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(tm);
                calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + n);
                tm = calendar.getTime();
                sReturnDate = df.format(tm);
            } catch (ParseException e) {
                log.error("String calYear(String sDate, int n) 發生錯誤");
            }
        }
        if (bChineseDate) {
            nYear = Integer.parseInt(sReturnDate.substring(0, 4)) - 1911;
            sReturnDate = nYear + sReturnDate.substring(4, 8);
            sReturnDate = StringUtils.leftPad(sReturnDate, 7, "0");
        }
        return sReturnDate;
    }

    /**
     * 將民國年月日轉成西元年月日 或 將西元年月日轉成民國年月日 <br>
     * <p>
     * <pre>
     * 用法:
     *   changeDateType(&quot;0950101&quot;) 傳回 20060101
     *   changeDateType(&quot;20060101&quot;) 傳回 0950101
     * </pre>
     *
     * @param sDate java.lang.String 欲轉換的日期字串
     * @return java.lang.String
     */
    public static String changeDateType(String sDate) {
        if (StringUtils.length(sDate) == 8) {
            if (StringUtils.equals(sDate.substring(0, 1), "-")) return changeDateType(sDate.substring(1, 8), true);
             else {
                if (Integer.parseInt(sDate.substring(0, 4)) < 1912) return "-" + changeDateType(sDate, true);
                 else return changeDateType(sDate, false);
            }
        } else if (StringUtils.length(sDate) == 7) {
            return changeDateType(sDate, false);
        } else {
            return "";
        }
    }

    /**
     * 將民國年月日轉成西元年月日 或 將西元年月日轉成民國年月日(移除開頭為0) <br>
     * <p>
     * <pre>
     * 用法:
     *   changeDateType(&quot;0950101&quot;) 傳回 20060101
     *   changeDateType(&quot;950101&quot;) 傳回 20060101
     *   changeDateType(&quot;20060101&quot;) 傳回 950101
     * </pre>
     *
     * @param sDate java.lang.String 欲轉換的日期字串
     * @return java.lang.String
     */
    public static String changeDateTypeNoZeroHead(String sDate) {
        if (StringUtils.length(sDate) == 6) {
            return changeDateType("0" + sDate);
        }
        String dateStr = changeDateType(sDate);
        if (StringUtils.length(dateStr) > 0 && dateStr.startsWith("0")) {
            return dateStr.substring(1, dateStr.length());
        }
        return dateStr;
    }

    /**
     * 西元日期轉成民國日期 （民國日期不變）
     *
     * @param sDate
     * @return
     */
    public static String changeDateTypeToChineseDate(String sDate) {
        if (StringUtils.length(sDate) == 8) {
            if (StringUtils.equals(sDate.substring(0, 1), "-")) return sDate;
             else {
                if (Integer.parseInt(sDate.substring(0, 4)) < 1912) return "-" + changeDateType(sDate, true);
                 else return changeDateType(sDate, false);
            }
        } else if (StringUtils.length(sDate) == 7) {
            return sDate;
        } else {
            return "";
        }
    }

    /**
     * 民國日期轉成西元日期 （西元日期不變）
     *
     * @param sDate
     * @return
     */
    public static String changeDateTypeToWestDate(String sDate) {
        if (StringUtils.length(sDate) == 8) {
            return sDate;
        } else if (StringUtils.length(sDate) == 7) {
            return changeDateType(sDate, false);
        } else {
            return "";
        }
    }

    /**
     * 將民國年月日轉成西元年月日 或 將西元年月日轉成民國年月日 <br>
     * 可傳入 民國 / 民前 / 西元 年月日 <br>
     * 若傳入的日期為西元日期, 則忽略 民前 民國 的參數<br>
     *
     * @param sDate      欲轉換的日期字串
     * @param bBeforeROC ture: 民前 fasle: 民國
     * @return
     */
    public static String changeDateType(String sDate, boolean bBeforeROC) {
        String sReturnDate = StringUtils.right(sDate, 4);
        if (sDate.length() == 7) {
            if (bBeforeROC) sReturnDate = StringUtils.leftPad(String.valueOf(1912 - Integer.parseInt(sDate.substring(0, 3))), 4, "0") + sReturnDate;
             else sReturnDate = StringUtils.leftPad(String.valueOf(Integer.parseInt(sDate.substring(0, 3)) + 1911), 4, "0") + sReturnDate;
        } else if (sDate.length() == 8) {
            if (Integer.parseInt(sDate.substring(0, 4)) < 1912) // 民前
             sReturnDate = StringUtils.leftPad(String.valueOf(1912 - Integer.parseInt(sDate.substring(0, 4))), 3, "0") + sReturnDate;
             else sReturnDate = StringUtils.leftPad(String.valueOf(Integer.parseInt(sDate.substring(0, 4)) - 1911), 3, "0") + sReturnDate;
        }
        return sReturnDate;
    }

    /**
     * 日期檢查 <br>
     * 可傳入 民國 / 西元 年月日 <br>
     * <br>
     * <p>
     * <pre>
     * 用法:
     *   isValidDate(&quot;0890133&quot;) 傳回 false
     *   isValidDate(&quot;19980101&quot;) 傳回 true
     * </pre>
     *
     * @param sDate java.lang.String 欲檢查的日期
     * @return boolean
     */
    public static boolean isValidDate(String sDate) {
        return isValidDate(sDate, false);
    }

    /**
     * 日期檢查 <br>
     * 可傳入 民國 / 民前 / 西元 年月日 <br>
     * 若傳入的日期為西元日期, 則忽略 民前 民國 的參數<br>
     *
     * @param sDate      欲檢查的日期
     * @param bBeforeROC ture: 民前 fasle: 民國
     * @return
     */
    public static boolean isValidDate(String sDate, boolean bBeforeROC) {
        int nYear;
        String sReturnDate = sDate;
        try {
            if (StringUtils.length(sDate) == 7) {
                if (Integer.parseInt(sDate.substring(0, 3)) == 0) // 沒有民國 0 年這種東西
                 return false;
                if (bBeforeROC) nYear = 1912 - Integer.parseInt(sDate.substring(0, 3));
                 else nYear = Integer.parseInt(sDate.substring(0, 3)) + 1911;
                sDate = nYear + sDate.substring(3, 7);
            }
            if (StringUtils.length(sDate) == 8) {
                if (Integer.parseInt(sDate.substring(0, 4)) == 0) // 沒有西元 0 年這種東西
                 return false;
                DateFormat df = new SimpleDateFormat("yyyyMMdd", Locale.TAIWAN);
                Date tm = df.parse(sDate);
                sReturnDate = df.format(tm);
                if (sReturnDate.equals(sDate)) return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    /**
     * 日期檢查 <br>
     * 可傳入 民國 / 西元 年月 <br>
     * <br>
     * <p>
     * <pre>
     * 用法:
     *   isValidDate(&quot;08913&quot;) 傳回 false
     *   isValidDate(&quot;19980101&quot;) 傳回 true
     * </pre>
     *
     * @param sDate java.lang.String 欲檢查的日期
     * @return boolean
     */
    public static boolean isValidYM(String sDate) {
        return isValidYM(sDate, false);
    }

    /**
     * 日期檢查 <br>
     * 可傳入 民國 / 民前 / 西元 年月 <br>
     * 若傳入的日期為西元日期, 則忽略 民前 民國 的參數<br>
     *
     * @param sDate      欲檢查的年月
     * @param bBeforeROC ture: 民前 fasle: 民國
     * @return
     */
    public static boolean isValidYM(String sDate, boolean bBeforeROC) {
        int nYear;
        String sReturnDate = sDate;
        try {
            if (StringUtils.length(sDate) == 5) {
                if (Integer.parseInt(sDate.substring(0, 3)) == 0) // 沒有民國 0 年這種東西
                 return false;
                if (bBeforeROC) nYear = 1912 - Integer.parseInt(sDate.substring(0, 3));
                 else nYear = Integer.parseInt(sDate.substring(0, 3)) + 1911;
                sDate = nYear + sDate.substring(3, 5);
            }
            if (StringUtils.length(sDate) == 6) {
                if (Integer.parseInt(sDate.substring(0, 4)) == 0) // 沒有西元 0 年這種東西
                 return false;
                DateFormat df = new SimpleDateFormat("yyyyMM", Locale.TAIWAN);
                Date tm = df.parse(sDate);
                sReturnDate = df.format(tm);
                if (sReturnDate.equals(sDate)) return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    /**
     * 時間檢查 <br>
     * <p>
     * <pre>
     * 用法:
     *   isValidDate(&quot;122599&quot;) 傳回 false
     *   isValidDate(&quot;122530&quot;) 傳回 true
     * </pre>
     *
     * @param sTime java.lang.String 欲檢查的時間
     * @return boolean
     */
    public static boolean isValidTime(String sTime) {
        String sReturnTime = sTime;
        try {
            if (StringUtils.length(sTime) == 6) {
                DateFormat df = new SimpleDateFormat("HHmmss", Locale.TAIWAN);
                Date tm = df.parse(sTime);
                sReturnTime = df.format(tm);
                if (sReturnTime.equals(sTime)) return true;
            } else if (StringUtils.length(sTime) == 4) {
                DateFormat df = new SimpleDateFormat("HHmm", Locale.TAIWAN);
                Date tm = df.parse(sTime);
                sReturnTime = df.format(tm);
                if (sReturnTime.equals(sTime)) return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    /**
     * 取得目前西元日期
     *
     * @return java.lang.String
     */
    public static String getNowWestDate() {
        String sReturnDate;
        DateFormat df = new SimpleDateFormat("yyyyMMdd", Locale.TAIWAN);
        Date date = new Date();
        date.setTime(System.currentTimeMillis());
        sReturnDate = df.format(date);
        return sReturnDate;
    }

    /**
     * 取得目前西元日期時間
     *
     * @param bSec boolean 是否取得秒數
     * @return java.lang.String
     */
    public static String getNowWestDateTime(boolean bSec) {
        String sReturnDateTime;
        String sFormater = "yyyyMMddHHmm";
        if (bSec) sFormater = "yyyyMMddHHmmss";
        DateFormat df = new SimpleDateFormat(sFormater, Locale.TAIWAN);
        Date date = new Date();
        date.setTime(System.currentTimeMillis());
        sReturnDateTime = df.format(date);
        return sReturnDateTime;
    }

    /**
     * 取得目前民國日期
     *
     * @return java.lang.String
     */
    public static String getNowChineseDate() {
        String sReturnDate;
        DateFormat df = new SimpleDateFormat("yyyyMMdd", Locale.TAIWAN);
        Date date = new Date();
        date.setTime(System.currentTimeMillis());
        sReturnDate = df.format(date);
        // 將西元日期轉成民國日期
        int nYear;
        nYear = Integer.parseInt(sReturnDate.substring(0, 4)) - 1911;
        sReturnDate = nYear + sReturnDate.substring(4, 8);
        sReturnDate = StringUtils.leftPad(sReturnDate, 7, "0");
        return sReturnDate;
    }

    /**
     * 取得目前民國日期時間
     *
     * @param bSec boolean 是否取得秒數
     * @return java.lang.String
     */
    public static String getNowChineseDateTime(boolean bSec) {
        String sReturnDateTime;
        String sFormater = "yyyyMMddHHmm";
        if (bSec) sFormater = "yyyyMMddHHmmss";
        DateFormat df = new SimpleDateFormat(sFormater, Locale.TAIWAN);
        Date date = new Date();
        date.setTime(System.currentTimeMillis());
        sReturnDateTime = df.format(date);
        if (bSec) sReturnDateTime = changeDateType(sReturnDateTime.substring(0, 8)) + sReturnDateTime.substring(8, 14);
         else sReturnDateTime = changeDateType(sReturnDateTime.substring(0, 8)) + sReturnDateTime.substring(8, 12);
        return sReturnDateTime;
    }

    /**
     * 取得某日期是星期幾 <br>
     * 可傳入 民國 / 西元 年月日 <br>
     * <br>
     * <p>
     * <pre>
     * 回傳值:
     *   星期日 - 0
     *   星期一 - 1
     *   ...
     *   星期六 - 6
     *   錯誤的日期或轉換錯誤 - 99
     * </pre>
     *
     * @param sDate java.lang.String 日期
     * @return int
     */
    public static int getDayOfWeek(String sDate) {
        return getDayOfWeek(sDate, false);
    }

    /**
     * 取得某日期是星期幾 <br>
     * 可傳入 民國 / 民前 / 西元 年月日 <br>
     * 若傳入的日期為西元日期, 則忽略 民前 民國 的參數<br>
     *
     * @param sDate
     * @param bBeforeROC ture: 民前 fasle: 民國
     * @return
     */
    public static int getDayOfWeek(String sDate, boolean bBeforeROC) {
        int dayOfWeek = 1;
        int nYear;
        if (!isValidDate(sDate, bBeforeROC)) return 99;
        if (sDate.length() == 7) {
            if (bBeforeROC) nYear = 1912 - Integer.parseInt(sDate.substring(0, 3));
             else nYear = Integer.parseInt(sDate.substring(0, 3)) + 1911;
            sDate = nYear + sDate.substring(3, 7);
        }
        if (sDate.length() == 8) {
            try {
                DateFormat df = new SimpleDateFormat("yyyyMMdd", java.util.Locale.TAIWAN);
                Calendar calendar = Calendar.getInstance();
                java.util.Date tm = df.parse(sDate);
                calendar.setTime(tm);
                dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
            } catch (ParseException e) {
                return 99;
            }
        }
        return dayOfWeek - 1;
    }

    /**
     * 取得兩日期相差的天數, 若日期格式錯誤則傳回 -1 <br>
     * 可傳入 民國 / 西元 年月日 <br>
     * <br>
     * <p>
     * <pre>
     * 用法:
     *   daysBetween(&quot;0890203&quot;, &quot;0900522&quot;) 傳回 474
     *   daysBetween(&quot;19980101&quot;, &quot;19960505&quot;) 傳回 606
     * </pre>
     *
     * @param sStartDate java.lang.String 開始日期
     * @param sEndDate   java.lang.String 結束日期
     * @return int
     */
    public static int daysBetween(String sStartDate, String sEndDate) {
        int nReturnDays = 0;
        long nStart;
        long nEnd;
        try {
            // 使用民國年先轉換成西元
            if (sStartDate.length() == 7) {
                int nYear = Integer.parseInt(sStartDate.substring(0, 3)) + 1911;
                sStartDate = nYear + sStartDate.substring(3, 7);
            }
            if (sEndDate.length() == 7) {
                int nYear = Integer.parseInt(sEndDate.substring(0, 3)) + 1911;
                sEndDate = nYear + sEndDate.substring(3, 7);
            }
            if (sStartDate.length() == 8) {
                DateFormat df = new SimpleDateFormat("yyyyMMdd", Locale.TAIWAN);
                Date tm = df.parse(sStartDate);
                nStart = tm.getTime();
                tm = df.parse(sEndDate);
                nEnd = tm.getTime();
                if (nEnd >= nStart) nReturnDays = (int) ((nEnd - nStart) / 86400000); // 86400000 是一天的秒數
                 else nReturnDays = (int) ((nStart - nEnd) / 86400000); // 86400000 是一天的秒數
            }
        } catch (Exception e) {
            return -1;
        }
        return nReturnDays;
    }

    /**
     * 取得兩日期相差的月數 (天數不足一個月以一個月計), 若日期錯誤則傳回 -1 <br>
     * 可傳入 民國 / 西元 年月日 <br>
     * <br>
     * <p>
     * <pre>
     * 用法:
     *   monthsBetween(&quot;0890203&quot;, &quot;0900522&quot;) 傳回 16
     *   monthsBetween(&quot;19980101&quot;, &quot;19960505&quot;) 傳回 19
     * </pre>
     *
     * @param sStartDate java.lang.String 開始日期
     * @param sEndDate   java.lang.String 結束日期
     * @return int
     */
    public static int monthsBetween(String sStartDate, String sEndDate) {
        int nReturnMonths = 0;
        // 使用民國年先轉換成西元
        if (sStartDate.length() == 7) {
            int nYear = Integer.parseInt(sStartDate.substring(0, 3)) + 1911;
            sStartDate = nYear + sStartDate.substring(3, 7);
        }
        if (sEndDate.length() == 7) {
            int nYear = Integer.parseInt(sEndDate.substring(0, 3)) + 1911;
            sEndDate = nYear + sEndDate.substring(3, 7);
        }
        try {
            if (sStartDate.length() == 8) {
                int nStart;
                int nEnd;
                int nDay;
                DateFormat df = new SimpleDateFormat("yyyyMMdd", Locale.TAIWAN);
                Date tm = df.parse(sStartDate);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(tm);
                nDay = calendar.get(Calendar.DATE);
                nStart = calendar.get(Calendar.YEAR) * 12 + calendar.get(Calendar.MONTH);
                tm = df.parse(sEndDate);
                calendar.setTime(tm);
                nDay = calendar.get(Calendar.DATE) - nDay;
                nEnd = calendar.get(Calendar.YEAR) * 12 + calendar.get(Calendar.MONTH);
                // 天數不足一個月以一個月計
                if (nEnd >= nStart) {
                    if (nDay <= 0) nReturnMonths = nEnd - nStart;
                     else nReturnMonths = nEnd - nStart + 1;
                } else {
                    if (nDay >= 0) nReturnMonths = nStart - nEnd;
                     else nReturnMonths = nStart - nEnd + 1;
                }
            } else {
                return -1;
            }
        } catch (Exception e) {
            return -1;
        }
        return nReturnMonths;
    }

    /**
     * 取得兩日期相差的月數(天數不足一個月不計),若日期格式錯誤則傳回-1 <br>
     * 可傳入 民國 / 西元 年月日 <br>
     * <br>
     * <p>
     * <pre>
     * 用法:
     *   wholeMonthsBetween(&quot;0890203&quot;, &quot;0900522&quot;) 傳回 15
     *   wholeMonthsBetween(&quot;19980101&quot;, &quot;19960505&quot;) 傳回 19
     * </pre>
     *
     * @param sStartDate java.lang.String 開始日期
     * @param sEndDate   java.lang.String 結束日期
     * @return int
     */
    public static int wholeMonthsBetween(String sStartDate, String sEndDate) {
        int nReturnMonths = 0;
        try {
            // 使用民國年先轉換成西元
            if (sStartDate.length() == 7) {
                int nYear = Integer.parseInt(sStartDate.substring(0, 3)) + 1911;
                sStartDate = nYear + sStartDate.substring(3, 7);
            }
            if (sEndDate.length() == 7) {
                int nYear = Integer.parseInt(sEndDate.substring(0, 3)) + 1911;
                sEndDate = nYear + sEndDate.substring(3, 7);
            }
            if (sStartDate.length() == 8) {
                int nStart;
                int nEnd;
                int nDay;
                DateFormat df = new SimpleDateFormat("yyyyMMdd", Locale.TAIWAN);
                Date tm = df.parse(sStartDate);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(tm);
                nDay = calendar.get(Calendar.DATE);
                nStart = calendar.get(Calendar.YEAR) * 12 + calendar.get(Calendar.MONTH);
                tm = df.parse(sEndDate);
                calendar.setTime(tm);
                nDay = calendar.get(Calendar.DATE) - nDay;
                nEnd = calendar.get(Calendar.YEAR) * 12 + calendar.get(Calendar.MONTH);
                // 天數不足一個月不計
                if (nEnd >= nStart) {
                    if (nDay < 0) nReturnMonths = nEnd - nStart - 1;
                     else nReturnMonths = nEnd - nStart;
                } else {
                    if (nDay > 0) nReturnMonths = nStart - nEnd - 1;
                     else nReturnMonths = nStart - nEnd;
                }
            } else {
                return -1;
            }
        } catch (Exception e) {
            return -1;
        }
        return nReturnMonths;
    }

    /**
     * 將傳入的日期字串加入年月日的分隔符號 (民國)<br>
     * 可傳入 民前 / 民國 / 西元 年月日 <br>
     * 注意: 若傳入西元年月日, 前端需自行判斷為 民前 或 民國<br>
     * <p>
     * <pre>
     * 用法:
     *   formatChineseDateString(&quot;0931231&quot;, true) 傳回 93年12月31日
     *   formatChineseDateString(&quot;0931231&quot;, false) 傳回 93/12/31
     * </pre>
     *
     * @param sDate    String 日期字串, 如: 0931231
     * @param bChinese boolean true 國字 (年月日) false 斜線
     * @return java.lang.String
     */
    public static String formatChineseDateString(String sDate, boolean bChinese) {
        // 西元日期先轉成 民前 或 民國 日期
        if (StringUtils.defaultString(sDate).trim().length() == 8) {
            if (Integer.parseInt(sDate.substring(0, 4)) < 1912) sDate = changeDateType(sDate, true);
             else sDate = changeDateType(sDate, false);
        } else if (StringUtils.defaultString(sDate).trim().length() != 7) {
            return StringUtils.defaultString(sDate);
        }
        if (bChinese) {
            return sDate.substring(0, 3) + "年" + sDate.substring(3, 5) + "月" + sDate.substring(5, 7) + "日";
        } else {
            return sDate.substring(0, 3) + "/" + sDate.substring(3, 5) + "/" + sDate.substring(5, 7);
        }
    }

    /**
     * 將傳入的日期字串加入年月的分隔符號 (民國)<br>
     * 可傳入 民國 年月 或 民國 年月日<br>
     * <p>
     * <pre>
     * 用法:
     *   formatChineseYearMonthString(&quot;09312&quot;, true) 傳回 093年12月
     *   formatChineseYearMonthString(&quot;09312&quot;, false) 傳回 093/12
     * </pre>
     *
     * @param sDate    String 日期字串, 如: 0931231
     * @param bChinese boolean true 國字 (年月日) false 斜線
     * @return java.lang.String
     */
    public static String formatChineseYearMonthString(String sDate, boolean bChinese) {
        if (StringUtils.defaultString(sDate).trim().length() != 5 && StringUtils.defaultString(sDate).trim().length() != 7) {
            return StringUtils.defaultString(sDate);
        }
        if (bChinese) {
            return sDate.substring(0, 3) + " 年 " + sDate.substring(3, 5) + " 月 ";
        } else {
            return sDate.substring(0, 3) + "/" + sDate.substring(3, 5);
        }
    }

    /**
     * 將傳入的日期字串加入年月的分隔符號 (西元)<br>
     * 可傳入 西元 年月 或 西元 年月日<br>
     * <p>
     * <pre>
     * 用法:
     *   formatWestYearMonthString(&quot;199212&quot;, true) 傳回 1992年12月
     *   formatWestYearMonthString(&quot;199212&quot;, false) 傳回 1992/12
     * </pre>
     *
     * @param sDate    String 日期字串, 如: 19921231
     * @param bChinese boolean true 國字 (年月) false 斜線
     * @return java.lang.String
     */
    public static String formatWestYearMonthString(String sDate, boolean bChinese) {
        if (StringUtils.defaultString(sDate).trim().length() != 6 && StringUtils.defaultString(sDate).trim().length() != 8) {
            return StringUtils.defaultString(sDate);
        }
        if (bChinese) {
            return sDate.substring(0, 4) + "年" + sDate.substring(4, 6) + "月";
        } else {
            return sDate.substring(0, 4) + "/" + sDate.substring(4, 6);
        }
    }

    /**
     * 將傳入的日期字串加入年月日時分秒的分隔符號<br>
     * 可傳入 民前 / 民國 / 西元 年月日 <br>
     * 注意: 若傳入西元年月日, 前端需自行判斷為 民前 或 民國<br>
     * <p>
     * <pre>
     * 用法:
     *   formatChineseDateTimeString(&quot;20061231083010&quot;, false) 傳回 095/12/31 08:30:10
     *   formatChineseDateTimeString(&quot;20061231&quot;, false) 傳回 095/12/31
     *   formatChineseDateTimeString(&quot;20061231083010&quot;, true) 傳回 095年12月31日 8時30分10秒
     *   formatChineseDateTimeString(&quot;20061231&quot;, true) 傳回 095年12月31日
     * </pre>
     *
     * @param sDateTime
     * @param bChinese
     * @return
     */
    public static String formatChineseDateTimeString(String sDateTime, boolean bChinese) {
        String sYear = "";
        String sDate = "";
        String sTime = "";
        // 西元先轉成 民前 或 民國
        if (StringUtils.defaultString(sDateTime).length() == 8 || StringUtils.defaultString(sDateTime).length() == 12 || StringUtils.defaultString(sDateTime).length() == 14) {
            sYear = sDateTime.substring(0, 4);
            if (Integer.parseInt(sYear) < 1912) sDate = changeDateType(sDateTime.substring(0, 8), true);
             else sDate = changeDateType(sDateTime.substring(0, 8), false);
            sTime = sDateTime.substring(8);
        } else if (StringUtils.defaultString(sDateTime).length() == 7 || StringUtils.defaultString(sDateTime).length() == 11 || StringUtils.defaultString(sDateTime).length() == 13) {
            sDate = sDateTime.substring(0, 7);
            sTime = sDateTime.substring(7);
        }
        return formatChineseDateString(sDate, bChinese) + " " + formatTimeString(sTime, bChinese);
    }

    /**
     * 將目前的日期字串加入年月日的分隔符號 (民國)
     *
     * @param bChinese true 國字 (年月日) false 斜線
     * @return java.lang.String
     */
    public static String formatNowChineseDateString(boolean bChinese) {
        return formatChineseDateString(getNowChineseDate(), bChinese);
    }

    /**
     * 將目前的日期時間字串加入年月日時分秒的分隔符號 (民國)
     *
     * @param bChinese true 國字 (年月日時分秒) false 斜線及冒號
     * @return java.lang.String
     */
    public static String formatNowChineseDateTimeString(boolean bChinese) {
        return formatChineseDateTimeString(getNowChineseDateTime(true), bChinese);
    }

    /**
     * 將傳入的時間字串加入時分秒分隔符號
     *
     * @param sTime    時間
     * @param bChinese true 國字 (時分秒) false 冒號
     * @return
     */
    public static String formatTimeString(String sTime, boolean bChinese) {
        String sReturnTimeString = "";
        if (StringUtils.defaultString(sTime).length() >= 4) {
            // ... [
            if (bChinese) sReturnTimeString = sTime.substring(0, 2) + " 時 " + sTime.substring(2, 4) + " 分 ";
             else sReturnTimeString = sTime.substring(0, 2) + ":" + sTime.substring(2, 4);
            if (StringUtils.defaultString(sTime).length() == 6) {
                // ... [
                if (bChinese) sReturnTimeString += sTime.substring(4, 6) + " 秒";
                 else sReturnTimeString += ":" + sTime.substring(4, 6);
            } // ] ... end if (StringUtils.defaultString(sTime).length() == 6)
        } // ] ... end if (StringUtils.defaultString(sTime).length() >= 4)
        return sReturnTimeString;
    }

    /**
     * 將傳入的日期字串加入年月日的分隔符號 (西元) <br>
     * <br>
     * <p>
     * <pre>
     * 用法:
     *   formatWestDateString(&quot;20061231&quot;) 傳回 2006/12/31
     * </pre>
     *
     * @param sDate String 日期字串, 如: 20061231
     * @return java.lang.String
     */
    public static String formatWestDateString(String sDate) {
        return formatWestDateString(sDate, false);
    }

    /**
     * 將傳入的日期字串加入年月日的分隔符號 (西元) <br>
     * 可傳入 民前 / 民國 / 西元 年月日 <br>
     *
     * @param sDate
     * @param bBeforeROC ture: 民前 fasle: 民國
     * @return
     */
    public static String formatWestDateString(String sDate, boolean bBeforeROC) {
        // 七碼日期先轉成西元日期
        if (StringUtils.defaultString(sDate).trim().length() == 7) sDate = changeDateType(sDate, bBeforeROC);
         else if (StringUtils.defaultString(sDate).trim().length() != 8) return StringUtils.defaultString(sDate);
        return sDate.substring(0, 4) + "/" + sDate.substring(4, 6) + "/" + sDate.substring(6, 8);
    }

    /**
     * 將傳入的日期字串加入年月日時分秒的分隔符號<br>
     * 可傳入 民國 / 西元 年月日時分秒 <br>
     * <p>
     * <pre>
     * 用法:
     *   formatWestDateTimeString(&quot;0951231083010&quot;) 傳回 2006/12/31 08:30:10
     *   formatWestDateTimeString(&quot;0951231&quot;) 傳回 2006/12/31
     * </pre>
     *
     * @param sDateTime
     * @return
     */
    public static String formatWestDateTimeString(String sDateTime) {
        return formatWestDateTimeString(sDateTime, false);
    }

    /**
     * 將傳入的日期字串加入年月日時分秒的分隔符號<br>
     * 可傳入 民前 / 民國 / 西元 年月日時分秒<br>
     *
     * @param sDateTime
     * @param bBeforeROC ture: 民前 fasle: 民國
     * @return
     */
    public static String formatWestDateTimeString(String sDateTime, boolean bBeforeROC) {
        String sDate = "";
        String sTime = "";
        // 七碼先轉成西元
        if (StringUtils.defaultString(sDateTime).length() == 7 || StringUtils.defaultString(sDateTime).length() == 11 || StringUtils.defaultString(sDateTime).length() == 13) {
            sDate = changeDateType(sDateTime.substring(0, 7), bBeforeROC);
            sTime = sDateTime.substring(7);
        } else if (StringUtils.defaultString(sDateTime).length() == 8 || StringUtils.defaultString(sDateTime).length() == 12 || StringUtils.defaultString(sDateTime).length() == 14) {
            sDate = sDateTime.substring(0, 8);
            sTime = sDateTime.substring(8);
        }
        return formatWestDateString(sDate) + " " + formatTimeString(sTime, false);
    }

    /**
     * 將目前的日期字串加入年月日的分隔符號 (西元)
     *
     * @return java.lang.String
     */
    public static String formatNowWestDateString() {
        return formatWestDateString(getNowWestDate());
    }

    /**
     * 將目前的日期時間字串加入年月日時分秒的分隔符號 (西元)
     *
     * @return
     */
    public static String formatNowWestDateTimeString() {
        return formatWestDateTimeString(getNowWestDateTime(true));
    }

    /**
     * 取得傳入日期時間的 日期 或 時間 部份字串 <br>
     * <br>
     * <p>
     * <pre>
     * 用法:
     *   splitChineseDateTime(&quot;0951231205930&quot;, true) 傳回 0951231
     *   splitChineseDateTime(&quot;0951231205930&quot;, fase) 傳回 205930
     *   splitChineseDateTime(&quot;09512312059&quot;, false) 傳回 2059
     * </pre>
     *
     * @param sDateTime
     * @param returnDatePart
     * @return
     */
    public static String splitChineseDateTime(String sDateTime, boolean returnDatePart) {
        if (returnDatePart) return sDateTime.substring(0, 7);
         else return sDateTime.substring(7);
    }

    /**
     * 取得傳入日期時間的 日期 或 時間 部份字串 <br>
     * <br>
     * <p>
     * <pre>
     * 用法:
     *   splitWestDateTime(&quot;20071231205930&quot;, true) 傳回 20071231
     *   splitWestDateTime(&quot;20071231205930&quot;, fase) 傳回 205930
     *   splitWestDateTime(&quot;200712312059&quot;, false) 傳回 2059
     * </pre>
     *
     * @param sDateTime
     * @param returnDatePart
     * @return
     */
    public static String splitWestDateTime(String sDateTime, boolean returnDatePart) {
        if (returnDatePart) return sDateTime.substring(0, 8);
         else return sDateTime.substring(8);
    }

    /**
     * 將傳入的民國年月轉成西元年月<br>
     *
     * @param yearMonth
     * @return
     */
    public static String changeChineseYearMonthType(String yearMonth) {
        if (StringUtils.defaultString(yearMonth).equals("") || yearMonth.length() != 5) return yearMonth;
         else {
            String year = yearMonth.substring(0, 3);
            String month = yearMonth.substring(3, 5);
            return StringUtils.leftPad(String.valueOf(Integer.parseInt(year) + 1911), 4, "0") + month;
        }
    }

    /**
     * 民國及西元年互轉<br>
     *
     * @param year
     * @return
     */
    public static String changeYearType(String year) {
        if (StringUtils.length(year) == 4) {
            return StringUtils.substring(changeDateType(year + "0101"), 0, 3);
        } else if (StringUtils.length(year) == 3) {
            return StringUtils.substring(changeDateType(year + "0101"), 0, 4);
        } else {
            return year;
        }
    }

    /**
     * 將傳入的西元年月轉成民國年月<br>
     *
     * @param yearMonth
     * @return
     */
    public static String changeWestYearMonthType(String yearMonth) {
        if (StringUtils.defaultString(yearMonth).equals("") || yearMonth.length() != 6) return yearMonth;
         else {
            String year = yearMonth.substring(0, 4);
            String month = yearMonth.substring(4, 6);
            return StringUtils.leftPad(String.valueOf(Integer.parseInt(year) - 1911), 3, "0") + month;
        }
    }

    /**
     * 將 <code>java.util.Date</code> 物件轉換為 <code>java.sql.Timestamp</code>
     *
     * @param date
     * @return
     */
    public static Timestamp convertToTimestamp(Date date) {
        if (date == null) return null;
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return new java.sql.Timestamp(cal.getTimeInMillis());
    }

    /**
     * 取得目前日期時間的 Date 物件<br>
     * 用於 SQL 的 TIMESTAMP 欄位<br>
     *
     * @return
     */
    public static Timestamp getNowDateTimeAsTimestamp() {
        return new Timestamp(Calendar.getInstance().getTimeInMillis());
    }

    /**
     * 將 LocalDateTime 轉為民國日期字串
     *
     * @param dateTime LocalDateTime 物件
     * @param bTime 是否包含時間
     * @return
     */
    public static String parseLocalDateTimeToChineseDateTime(LocalDateTime dateTime, boolean bTime) {
        return parseTimestampToChineseDateTime(dateTime != null ? Timestamp.valueOf(dateTime) : null, bTime);
    }

    /**
     * 將 TIMESTAMP 轉為民國日期字串
     *
     * @param date  TIMESTAMP 物件
     * @param bTime 是否包含時間
     * @return
     */
    public static String parseTimestampToChineseDateTime(Timestamp date, boolean bTime) {
        if (date == null) return "";
        String sReturnDateTime;
        String sFormater = "yyyyMMdd";
        if (bTime) sFormater = "yyyyMMddHHmmss";
        DateFormat df = new SimpleDateFormat(sFormater, Locale.TAIWAN);
        sReturnDateTime = df.format(date);
        if (bTime) sReturnDateTime = changeDateType(sReturnDateTime.substring(0, 8)) + sReturnDateTime.substring(8, 14);
         else sReturnDateTime = changeDateType(sReturnDateTime.substring(0, 8));
        return sReturnDateTime;
    }

    /**
     * 將 TIMESTAMP 轉為西元日期字串
     *
     * @param date  TIMESTAMP 物件
     * @param bTime 是否包含時間
     * @return
     */
    public static String parseTimestampToWestDateTime(Timestamp date, boolean bTime) {
        if (date == null) return "";
        String sReturnDateTime;
        String sFormater = "yyyyMMdd";
        if (bTime) sFormater = "yyyyMMddHHmmss";
        DateFormat df = new SimpleDateFormat(sFormater, Locale.TAIWAN);
        sReturnDateTime = df.format(date);
        return sReturnDateTime;
    }

    /**
     * 將 LocalDate 轉為民國日期字串
     *
     * @param localDate  LocalDate 物件
     * @return
     */
    public static String parseLocalDateToChineseDateTime(LocalDate localDate) {
        return parseDateToChineseDateTime(localDate != null ? Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant()) : null, false);
    }

    /**
     * 將 DATE 轉為民國日期字串
     *
     * @param date  DATE 物件
     * @param bTime 是否包含時間
     * @return
     */
    public static String parseDateToChineseDateTime(Date date, boolean bTime) {
        if (date == null) return "";
        String sReturnDateTime;
        String sFormater = "yyyyMMdd";
        if (bTime) sFormater = "yyyyMMddHHmmss";
        DateFormat df = new SimpleDateFormat(sFormater, Locale.TAIWAN);
        sReturnDateTime = df.format(date);
        if (bTime) sReturnDateTime = changeDateType(sReturnDateTime.substring(0, 8)) + sReturnDateTime.substring(8, 14);
         else sReturnDateTime = changeDateType(sReturnDateTime.substring(0, 8));
        return sReturnDateTime;
    }

    /**
     * 將 DATE 轉為西元日期字串
     *
     * @param date  DATE 物件
     * @param bTime 是否包含時間
     * @return
     */
    public static String parseDateToWestDateTime(Date date, boolean bTime) {
        if (date == null) return "";
        String sReturnDateTime;
        String sFormater = "yyyyMMdd";
        if (bTime) sFormater = "yyyyMMddHHmmss";
        DateFormat df = new SimpleDateFormat(sFormater, Locale.TAIWAN);
        sReturnDateTime = df.format(date);
        return sReturnDateTime;
    }

    /**
     * 取得目前民國日期時間
     *
     * @return java.lang.String
     */
    public static String getNowChineseDateTimeMs() {
        String sReturnDateTime;
        DateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSS", Locale.TAIWAN);
        Date date = new Date();
        date.setTime(System.currentTimeMillis());
        sReturnDateTime = df.format(date);
        sReturnDateTime = changeDateType(sReturnDateTime.substring(0, 8)) + sReturnDateTime.substring(8, 16);
        return sReturnDateTime;
    }

    /**
     * 將傳入的西元日期轉為民國日期, 若傳入的日期是民國前, 則會以負號開頭顯示<br>
     * <br>
     * <p>
     * <pre>
     * 用法:
     *   formatChineseBirtydayString(&quot;19110101&quot;) 傳回 -0010101
     *   formatChineseBirtydayString(&quot;19770101&quot;) 傳回 0660101
     * </pre>
     *
     * @return
     */
    public static String parseChineseBirtydayString(String westBirthday) {
        if (StringUtils.length(westBirthday) == 8) {
            if (Integer.parseInt(westBirthday.substring(0, 4)) < 1912) return "-" + changeDateType(westBirthday, true);
             else return changeDateType(westBirthday);
        } else {
            return westBirthday;
        }
    }

    /**
     * 傳入一日期, 傳回該月份有幾天, 若日期格式錯誤則傳回 -1 <br>
     * 可傳入 民國 / 西元 年月日 <br>
     * 用該年月的第一天算 可避免日期若超過30 31 會回傳下個月的最後一天
     * <p>
     * <pre>
     * 用法:
     *   lastDay(&quot;0890203&quot;) 傳回 29
     *   lastDay(&quot;19980101&quot;) 傳回 31
     * </pre>
     *
     * @param sDate java.lang.String 日期
     * @return int
     */
    public static int lastDayByYearMonth(String sDate) {
        if (StringUtils.isBlank(sDate)) return -1;
        if (sDate.length() == 5 || sDate.length() == 7) {
            return lastDay(StringUtils.substring(sDate, 0, 5) + "01");
        } else if (sDate.length() == 6 || sDate.length() == 8) {
            return lastDay(StringUtils.substring(sDate, 0, 6) + "01");
        }
        return -1;
    }

    /**
     * 判斷傳入的日期是否為閏年,若傳入民國日期會轉為西元日期判斷<br>
     * 閏年成立規則 1.能被4整除但不能被100整除的是閏年! 2.能被400整除的就是閏年!
     * <p>
     * <pre>
     * 用法:
     *   isLeapYear(&quot;2000101&quot;) 傳回 true
     *   isLeapYear(&quot;19990101&quot;) 傳回 false
     * </pre>
     *
     * @param date 日期
     * @return
     */
    public static boolean isLeapYear(String date) {
        if (StringUtils.length(date) == 7) {
            date = changeDateType(date);
        } else if (StringUtils.length(date) == 8) {
            if (StringUtils.equals(date.substring(0, 1), "-")) date = changeDateType(date.substring(1, 8), true);
        } else {
            return false;
        }
        int year = Integer.parseInt(StringUtils.substring(date, 0, 4));
        if (year % 4 == 0 && year % 100 != 0) {
            return true;
        } else {
            return year % 400 == 0;
        }
    }

    /**
     * 取得目前民國年
     *
     * @return
     */
    public static String getNowChineseYear() {
        return StringUtils.substring(DateUtility.getNowChineseDate(), 0, 3);
    }

    /**
     * 取得目前西元年
     *
     * @return
     */
    public static String getNowWestYear() {
        return StringUtils.substring(DateUtility.getNowWestDate(), 0, 4);
    }

    /**
     * 取得目前民國年月
     *
     * @return
     */
    public static String getNowChineseYearMonth() {
        return StringUtils.substring(DateUtility.getNowChineseDate(), 0, 5);
    }

    /**
     * 取得目前西元年月
     *
     * @return
     */
    public static String getNowWestYearMonth() {
        return StringUtils.substring(DateUtility.getNowWestDate(), 0, 6);
    }

    /**
     * 取得目前西元日期時間
     *
     * @return java.lang.String
     */
    public static String getNowWestDateTimeMs() {
        String sReturnDateTime;
        DateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS", Locale.TAIWAN);
        Date date = new Date();
        date.setTime(System.currentTimeMillis());
        sReturnDateTime = df.format(date);
        return sReturnDateTime;
    }

    /**
     * 取得時間的 上午 / 下午、AM / PM 字串
     *
     * @param time     時間（hhmmss 或 hhmm）
     * @param bChinese <code>true</code> 上午 / 下午; <code>false</code> AM / PM
     * @return
     */
    public static String getChineseTimeString(String time, boolean bChinese) {
        if (StringUtils.length(time) != 4 && StringUtils.length(time) != 6) return StringUtils.defaultString(time);
        int hours = Integer.parseInt(StringUtils.substring(time, 0, 2));
        if (hours == 0) {
            String newTime = "12" + StringUtils.substring(time, 2);
            if (bChinese) return "午夜 " + DateUtility.formatTimeString(newTime, false);
             else return DateUtility.formatTimeString(newTime, false) + " midnight";
        } else if (hours > 0 && hours < 12) {
            String newTime = StringUtils.leftPad(String.valueOf(hours), 2, "0") + StringUtils.substring(time, 2);
            if (bChinese) return "上午 " + DateUtility.formatTimeString(newTime, false);
             else return DateUtility.formatTimeString(newTime, false) + " AM";
        } else if (hours == 12) {
            String newTime = StringUtils.leftPad(String.valueOf(hours), 2, "0") + StringUtils.substring(time, 2);
            if (bChinese) return "中午 " + DateUtility.formatTimeString(newTime, false);
             else return DateUtility.formatTimeString(newTime, false) + " noon";
        } else {
            String newTime = StringUtils.leftPad(String.valueOf(hours - 12), 2, "0") + StringUtils.substring(time, 2);
            if (bChinese) return "下午 " + DateUtility.formatTimeString(newTime, false);
             else return DateUtility.formatTimeString(newTime, false) + " PM";
        }
    }

    /**
     * 取得 (西元/民國)日期及   (上午 / 下午)、(AM / PM)時間 字串
     * ex: 109/02/20 (下午)02:30:20 (PM)
     * @param timestamp     日期時間
     * @param bChineseYear <code>true</code>民國時間 <code>false</code> 西元時間
     * @param bChineseTime <code>true</code> 上午 / 下午; <code>false</code> AM / PM
     * @return
     */
    public static String getDateTimeString(Timestamp timestamp, boolean bChineseYear, boolean bChineseTime) {
        if (timestamp == null) return "";
        String dateStr = new SimpleDateFormat("yyyyMMdd").format(timestamp);
        String timeStr = new SimpleDateFormat("hhmmss").format(timestamp);
        String dateStrFormat = bChineseYear ? formatChineseDateTimeString(dateStr, false) : formatWestDateString(dateStr, false);
        String timeStrFormat = getChineseTimeString(timeStr, bChineseTime);
        return dateStrFormat + " " + timeStrFormat;
    }

    /**
     * 取得 (西元/民國)日期及   (上午 / 下午)、(AM / PM)時間 字串
     * ex: 109/02/20 (下午)02:30:20 (PM)
     * @param timestamp     日期時間
     * @param bChineseYear <code>true</code>民國時間 <code>false</code> 西元時間
     * @param bChineseTime <code>true</code> 上午 / 下午; <code>false</code> AM / PM
     * @return
     */
    public static String getDateTimeString(LocalDateTime localDateTime, boolean bChineseYear, boolean bChineseTime) {
        if (localDateTime == null) return "";
        return getDateTimeString(Timestamp.valueOf(localDateTime), bChineseYear, bChineseTime);
    }

    /**
     * 取得現在系統時間 yyyy年MM月[dd日] 及 hh時mm分[ss秒] Map物件
     * Map("year") => yyyy年MM月[dd日]
     * Map("time") => hh時mm分[ss秒]
     * @param bchinese 是否轉為民國日期
     * @param bDate    日期是否包含日
     * @param bSec     時間是否包含秒
     * @return
     */
    public static Map<String, String> getNowDateTimeStrMap(boolean bchinese, boolean bDate, boolean bSec) {
        Map<String, String> resultMap = new HashMap<String, String>();
        StringBuffer resultDate = new StringBuffer();
        StringBuffer resultTime = new StringBuffer();
        String westDateStr = getNowWestDateTime(true);
        // 處理年月日
        if (bchinese) {
            // 民國日期時間
            StringBuffer chineseDateNum = new StringBuffer(changeDateType(westDateStr.substring(0, 9)));
            resultDate.append(chineseDateNum.substring(0, 3) + "年" + chineseDateNum.substring(3, 5) + "月");
            // 是否包含日
            if (bDate) resultDate.append(chineseDateNum.substring(5, 7) + "日");
        } else {
            // 西元日期時間
            resultDate.append(westDateStr.substring(0, 4) + "年" + westDateStr.substring(4, 6) + "月");
            // 是否包含日
            if (bDate) resultDate.append(westDateStr.substring(6, 8) + "日");
        }
        resultTime.append(westDateStr.substring(8, 10) + "時" + westDateStr.substring(10, 12) + "分");
        if (bSec) resultTime.append(westDateStr.substring(12, 14) + "秒");
        resultMap.put("date", resultDate.toString());
        resultMap.put("time", resultTime.toString());
        return resultMap;
    }

    /**
     * 20210916 --> localDateTime 時分秒均為 0
     * @param yyyyMMdd
     * @return
     */
    public static LocalDate westDateToLocalDate(String yyyyMMdd) {
        if (StringUtils.isNotBlank(yyyyMMdd)) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            return LocalDate.parse(yyyyMMdd, formatter);
        }
        return null;
    }

    /**
     * 民國 0970916 --> localDateTime 時分秒均為 0
     * @param yyyyMMdd
     * @return
     */
    public static LocalDate rocDateToLocalDate(String yyyMMdd) {
        return westDateToLocalDate(changeDateTypeToWestDate(yyyMMdd));
    }
}
