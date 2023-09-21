package tw.gov.pcc.eip.util;

import java.io.StringReader;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.owasp.encoder.Encode;
import com.itextpdf.text.pdf.BaseFont;

public class StringUtility {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(StringUtility.class);
    private static final int PAD_LIMIT = 8192;
    private static final String MADE_WORD_REPLACE_STAR = "＊";

    /**
     * 將字串右邊補上指定的字串到某長度<br>
     * 可處理中文, 會將中文字的長度視為 2<br>
     * 例如:<br>
     *
     * <pre>
     * StringUtility.chtRightPad(&quot;abc&quot;, 6, &quot;1&quot;) 將回傳 &quot;abc111&quot;
     * StringUtility.chtRightPad(&quot;張三&quot;, 6, &quot;1&quot;) 將回傳 &quot;張三11&quot;
     * StringUtility.chtRightPad(&quot;張三&quot;, 6, &quot;abc&quot;) 將回傳 &quot;張三ab&quot;
     * StringUtility.chtRightPad(&quot;abc&quot;, 2, &quot;1&quot;) 將回傳 &quot;abc&quot;
     * StringUtility.chtRightPad(null, 3, &quot;1&quot;) 將回傳 &quot;111&quot;
     * </pre>
     *
     * @param str    字串
     * @param length 欲補足的總長度
     * @param padStr 欲補的字元 (不可是全形, 若欲補全形請用 jakarta commons.lang)
     * @return
     */
    public static String chtRightPad(String str, int length, String padStr) {
        str = StringUtils.defaultString(str);
        int padLen = stringRealLength(padStr);
        int strLen = stringRealLength(str);
        int pads = length - strLen;
        if (pads <= 0) {
            return str; // returns original String when possible
        }
        if (padLen == 1 && pads <= PAD_LIMIT) {
            for (int i = 0; i < (length - strLen); i++) {
                str = str + padStr;
            }
            return str;
        }
        if (pads == padLen) {
            return str.concat(padStr);
        } else if (pads < padLen) {
            return str.concat(padStr.substring(0, pads));
        } else {
            char[] padding = new char[pads];
            char[] padChars = padStr.toCharArray();
            for (int i = 0; i < pads; i++) {
                padding[i] = padChars[i % padLen];
            }
            return str.concat(new String(padding));
        }
    }

    /**
     * 將字串左邊補上指定的字串到某長度<br>
     * 可處理中文, 會將中文字的長度視為 2<br>
     * 例如:<br>
     *
     * <pre>
     * StringUtility.chtLeftPad(&quot;abc&quot;, 6, &quot;1&quot;) 將回傳 &quot;111abc&quot;
     * StringUtility.chtLeftPad(&quot;張三&quot;, 6, &quot;1&quot;) 將回傳 &quot;11張三&quot;
     * StringUtility.chtLeftPad(&quot;張三&quot;, 6, &quot;abc&quot;) 將回傳 &quot;ab張三&quot;
     * StringUtility.chtLeftPad(&quot;abc&quot;, 2, &quot;1&quot;) 將回傳 &quot;abc&quot;
     * StringUtility.chtLeftPad(null, 3, &quot;1&quot;) 將回傳 &quot;111&quot;
     * </pre>
     *
     * @param str    字串
     * @param length 欲補足的總長度
     * @param padStr 欲補的字串 (不可是全形, 若欲補全形請用 jakarta commons.lang)
     * @return
     */
    public static String chtLeftPad(String str, int length, String padStr) {
        str = StringUtils.defaultString(str);
        int padLen = stringRealLength(padStr);
        int strLen = stringRealLength(str);
        int pads = length - strLen;
        if (pads <= 0) {
            return str; // returns original String when possible
        }
        if (padLen == 1 && pads <= PAD_LIMIT) {
            for (int i = 0; i < (length - strLen); i++) {
                str = padStr + str;
            }
            return str;
        }
        if (pads == padLen) {
            return padStr.concat(str);
        } else if (pads < padLen) {
            return padStr.substring(0, pads).concat(str);
        } else {
            char[] padding = new char[pads];
            char[] padChars = padStr.toCharArray();
            for (int i = 0; i < pads; i++) {
                padding[i] = padChars[i % padLen];
            }
            return new String(padding).concat(str);
        }
    }

    /**
     * 計算字串長度 (中文長度為 2, 英文長度為 1)
     *
     * @param str
     * @return
     */
    public static int stringRealLength(String str) {
        if (str == null) return 0;
        if (StringUtils.containsIgnoreCase(System.getProperties().getProperty("os.name"), "windows")) {
            // 中文 Windows 平台預設 I/O 編碼為 MS950, 因此呼叫 "中文".getBytes().length 傳回的長度為 4
            return str.getBytes().length;
        } else {
            // UNIX 平台為防止預設 I/O 編碼不為 Big5, 因此強制轉為 Big5 再計算字串長度
            StringReader reader = new StringReader(str);
            try {
                int nLength = IOUtils.toByteArray(reader, "MS950").length;
                return nLength;
            } catch (Exception e) {
                return StringUtils.length(str);
            } finally {
                IOUtils.closeQuietly(reader);
            }
        }
    }

    /**
     * 取得子字串 (中文長度為 2, 英文長度為 1) 
     * 替換原本異常的 chtStrSubstring
     * @param str
     * @param beginIndex
     * @param endIndex
     * @return
     */
    public static String chtStrSubstring(String str, int beginIndex, int endIndex) {
        if (StringUtils.isBlank(str)) return null;
        StringReader reader = new StringReader(str);
        try {
            int current= 0;
            boolean isOpen = false;
            boolean isRemoveEnd = false;
            StringBuilder sb=new StringBuilder();
            for (String token : str.split("")) {
                if(!isOpen){
                    isOpen = current >= beginIndex;
                }
                if(isOpen){
                    sb.append(token);
                }
                current += stringRealLength(token);
                if(isOpen){
                    isOpen = current < endIndex;
                    isRemoveEnd = !isOpen && current > endIndex;
                    if(!isOpen){
                        break;
                    }
                }
            }
            if(sb.length() > 0  && isRemoveEnd)
                sb.setLength(sb.length()-1);
            return sb.toString();
        } catch (Exception e) {
            return null;
        } finally {
            IOUtils.closeQuietly(reader);
        }
    }

    /**
     * 將傳入的字串轉成 String Array, 並去掉 Array 中每個字串的頭尾空白
     *
     * @param str       欲轉成 Array 的字串
     * @param separator 字串的分隔符號
     * @return String Array
     */
    public static String[] splitTrim(String str, String separator) {
        String[] returnStringArray = StringUtils.splitByWholeSeparator(str, separator);
        for (int i = 0; i < returnStringArray.length; i++) {
            returnStringArray[i] = returnStringArray[i].trim();
        }
        return returnStringArray;
    }

    /**
     * 處理 XML 跳脫字元<br>
     * 由於 StringEscapeUtils.escapeXml() 中文字會轉編碼成類似: <code>&amp;#32769;</code> 故另寫 function 處理
     *
     * @param value
     * @return
     */
    public static String escapeXml(String value) {
        if (value == null) return "";
        return StringUtils.replaceEach(value, new String[] {"\"", "\'", "&", "<", ">"}, new String[] {"&quot;", "&apos;", "&amp;", "&lt;", "&gt;"});
    }

    /**
     * 將字串轉成指定編碼
     *
     * @param str
     * @param encode
     * @return 編碼後的字串, 若轉碼失敗則回傳原字串
     */
    public static String convertString(String str, String encode) {
        StringReader reader = new StringReader(str);
        try {
            byte[] strBytes = IOUtils.toByteArray(reader, encode);
            return new String(strBytes);
        } catch (Exception e) {
            return str;
        } finally {
            IOUtils.closeQuietly(reader);
        }
    }

    /**
     * 將字串中的空白及全形空白去除
     *
     * @param str
     * @return
     */
    public static String replaceSpaceString(String str) {
        return StringUtils.replace(StringUtils.replace(StringUtils.trimToEmpty(str), " ", ""), "　", "");
    }

    /**
     * 將字串中的半形英文轉全形
     *
     * @param str
     * @return
     */
    public static String encodeFromAscii(String str) {
        if (StringUtils.isBlank(str)) return str;
        String asciiStr = " !\"#$%&\'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]_`abcdefghijklmnopqrstuvwxyz{|}~";
        String encodeStr = "　！”＃＄％＆’（）＊＋，－．／０１２３４５６７８９：；＜＝＞？＠ＡＢＣＤＥＦＧＨＩＪＫＬＭＮＯＰＱＲＳＴＵＶＷＸＹＺ〔＼〕＿‵ａｂｃｄｅｆｇｈｉｊｋｌｍｎｏｐｑｒｓｔｕｖｗｘｙｚ｛｜｝～";
        StringBuffer returnString = new StringBuffer();
        for (int i = 0; i < str.length(); i++) {
            String sTemp = str.substring(i, i + 1);
            int nPos = StringUtils.indexOf(asciiStr, sTemp);
            if (nPos >= 0) sTemp = StringUtils.substring(encodeStr, nPos, nPos + 1);
            returnString.append(sTemp);
        }
        return returnString.toString();
    }

    /**
     * 將字串中的全形英文轉半形
     *
     * @param str
     * @return
     */
    public static String decodeToAscii(String str) {
        if (StringUtils.isBlank(str)) return str;
        String asciiStr = " !\"#$%&\'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]_`abcdefghijklmnopqrstuvwxyz{|}~";
        String encodeStr = "　！”＃＄％＆’（）＊＋，－．／０１２３４５６７８９：；＜＝＞？＠ＡＢＣＤＥＦＧＨＩＪＫＬＭＮＯＰＱＲＳＴＵＶＷＸＹＺ〔＼〕＿‵ａｂｃｄｅｆｇｈｉｊｋｌｍｎｏｐｑｒｓｔｕｖｗｘｙｚ｛｜｝～";
        StringBuffer returnString = new StringBuffer();
        for (int i = 0; i < str.length(); i++) {
            String sTemp = str.substring(i, i + 1);
            int nPos = StringUtils.indexOf(encodeStr, sTemp);
            if (nPos >= 0) sTemp = StringUtils.substring(asciiStr, nPos, nPos + 1);
            returnString.append(sTemp);
        }
        return returnString.toString();
    }

    /**
     * 去除字串頭尾全形及半形空白
     *
     * @param str
     * @return
     */
    public static String chtTrimToEmpty(String str) {
        if (str == null) return "";
        int beginIndex = 0;
        int endIndex = 0;
        for (int i = 0; i < str.length(); i++) {
            if (!StringUtils.equals(StringUtils.substring(str, i, i + 1), " ") && !StringUtils.equals(StringUtils.substring(str, i, i + 1), "　")) {
                beginIndex = i;
                break;
            }
        }
        for (int i = str.length(); i >= 0; i--) {
            if (!StringUtils.equals(StringUtils.substring(str, i - 1, i), " ") && !StringUtils.equals(StringUtils.substring(str, i - 1, i), "　")) {
                endIndex = i;
                break;
            }
        }
        return StringUtils.substring(str, beginIndex, endIndex);
    }

    /**
     * 將造字替換成'＊'
     * charSet=UTF-8
     *
     * @param str 欲替換的字串
     * @return
     */
    public static String replaceMadeWordToStarUtf8(String str) {
        return replaceMadeWordToStar("UTF-8", str);
    }

    /**
     * 將造字替換成'＊'
     * charSet=Big5
     *
     * @param str 欲替換的字串
     * @return
     */
    public static String replaceMadeWordToStarBig5(String str) {
        return replaceMadeWordToStar("Big5", str);
    }

    /**
     * 將造字替換成'＊'
     *
     * @param charSet 欲檢核造字區之字元集（非 str 傳入字串的字元集）
     * @param str     欲替換的字串
     * @return
     */
    public static String replaceMadeWordToStar(String charSet, String str) {
        if (StringUtils.isNotBlank(str)) {
            StringBuffer sb = new StringBuffer();
            CharsetEncoder enc = Charset.forName(charSet).newEncoder();
            int size = str.length();
            for (int i = 0; i < size; i++) {
                if (enc.canEncode(str.charAt(i))) {
                    sb.append(str.charAt(i));
                } else {
                    sb.append(MADE_WORD_REPLACE_STAR);
                }
            }
            return sb.toString();
        } else {
            return str;
        }
    }

    private static final Pattern AMOUNT_PATTERN = Pattern.compile("^(0|[1-9]\\d{0,11})$"); // 不考慮分隔符的正確性
    private static final char[] TWD_NUMS = "零壹貳參肆伍陸柒捌玖".toCharArray();
    private static final String[] UNITS = {"元", "角", "分", "整"};
    private static final String[] U1 = {"", "拾", "佰", "仟"};
    private static final String[] U2 = {"", "萬", "億"};

    /**
     * 將金額轉為中文大寫國字（可到千億，只支援正整數，無法算到角）
     *
     * @param money 金額數字
     * @return 中文大寫
     * @throws IllegalArgumentException
     */
    public static String toChineseCurrency(BigDecimal money) throws Exception {
        String amount = "";
        if (money != null) amount = money.toPlainString();
        if (StringUtils.isBlank(amount)) return "";
        // 去掉分隔符
        amount = amount.replace(",", "");
        // 驗證金額正確性
        if (Long.parseLong(amount) == 0L) {
            return "零元整";
        }
        Matcher matcher = AMOUNT_PATTERN.matcher(amount);
        if (!matcher.find()) {
            throw new IllegalArgumentException("金額有誤");
        }
        String integer = matcher.group(1); // 整數部分
        String result = "";
        if (!integer.equals("0")) {
            result += integer2TWD(integer) + UNITS[0]; // 整數部分
        }
        return result + "整";
    }

    // 將金額整數部分轉換為中文大寫
    private static String integer2TWD(String integer) {
        StringBuilder buffer = new StringBuilder();
        // 從個位數開始轉換
        int i;
        int j;
        for (i = integer.length() - 1, j = 0; i >= 0; i--, j++) {
            char n = integer.charAt(i);
            if (n == '0') {
                // 當n是0且n的右邊一位不是0時，插入[零]
                if (i < integer.length() - 1 && integer.charAt(i + 1) != '0') {
                    buffer.append(TWD_NUMS[0]);
                }
                // 插入[萬]或者[億]
                if (j % 4 == 0) {
                    if (i > 0 && integer.charAt(i - 1) != '0' || i > 1 && integer.charAt(i - 2) != '0' || i > 2 && integer.charAt(i - 3) != '0') {
                        buffer.append(U2[j / 4]);
                        String temp = buffer.toString();
                        if (StringUtils.substring(temp, (temp.length() - 4), (temp.length() - 3)).equals(U1[3])) buffer.deleteCharAt(temp.length() - 2);
                    }
                }
            } else {
                if (j % 4 == 0) {
                    buffer.append(U2[j / 4]); // 插入[萬]或者[億]
                }
                buffer.append(U1[j % 4]); // 插入[拾]、[佰]或[仟]
                buffer.append(TWD_NUMS[n - '0']); // 插入數字
            }
        }
        return buffer.reverse().toString();
    }

    /**
     * Log 訊息，防止 CRLF Injection
     *
     * @param msg Log 訊息
     * @return
     */
    public static String safeLog(String msg) {
        return removeControlCharacter(Encode.forJava(StringUtils.defaultString(msg)));
    }

    /**
     * 過濾字串，防止 CRLF Injection
     *
     * @param input
     * @return
     */
    public static final String removeControlCharacter(String input) {
        if (input == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < input.codePointCount(0, input.length()); i++) {
            int codePoint = input.codePointAt(i);
            if (!Character.isISOControl(codePoint)) {
                sb.appendCodePoint(codePoint);
            }
        }
        return sb.toString();
    }

    /**
     * 過濾字串，防止 LDAP Injection
     *
     * @param name
     * @return
     */
    public static String escapeDN(String name) {
        StringBuffer sb = new StringBuffer(); // If using JDK >= 1.5 consider using StringBuilder
        if ((name.length() > 0) && ((name.charAt(0) == ' ') || (name.charAt(0) == '#'))) {
            sb.append('\\'); // add the leading backslash if needed
        }
        for (int i = 0; i < name.length(); i++) {
            char curChar = name.charAt(i);
            switch (curChar) {
            case '\\': 
                sb.append("\\\\");
                break;
            // case ',':
            // sb.append("\\,");
            // break;
            case '+': 
                sb.append("\\+");
                break;
            case '\"': 
                sb.append("\\\"");
                break;
            case '<': 
                sb.append("\\<");
                break;
            case '>': 
                sb.append("\\>");
                break;
            case ';': 
                sb.append("\\;");
                break;
            default: 
                sb.append(curChar);
            }
        }
        // if ((name.length() > 1) && (name.charAt(name.length() - 1) == ' ')) {
        // sb.insert(sb.length() - 1, '\\'); // add the trailing backslash if needed
        // }
        return sb.toString();
    }

    /**
     * 取得隱藏後4碼的身分證號
     *
     * @param idn
     * @return
     */
    public static String getHiddenIdn(String idn) {
        if (idn != null) {
            StringBuffer buf = new StringBuffer(idn);
            int length = buf.length();
            buf.replace(length - 4, length, "****");
            return buf.toString();
        } else return "";
    }

    /**
     * 顯示首尾文字、中間文字以“○”遮罩;若姓名僅2字者，僅遮罩第2個字。
     *  姓名固定為六位全形，右補全形空白。
     * @param name 欲遮罩之姓名
     * @return 遮罩過後的姓名
     */
    public static String getMaskedName(String name) {
        String nameString = name;
        String originalName = StringUtils.remove(StringUtils.remove(nameString, "　"), " "); // 去掉"全形"、"半形"空格
        if (originalName.length() > 1) {
            // 名字只有大於2個字才做遮罩
            StringBuffer maskName = new StringBuffer(originalName); // 遮罩過後的名字
            String maskNameSymbol = "○"; // 隱藏部分名字之遮罩符號
            if (maskName.length() == 2) {
                // 名字只有兩個字尾端為 遮罩符號
                maskName.replace(1, maskName.length(), maskNameSymbol);
            } else if (maskName.length() >= 3) {
                for (int m = 1; m < maskName.length() - 1; m++) maskName.replace(m, m + 1, maskNameSymbol);
            }
            nameString = maskName.toString();
        }
        nameString = StringUtils.rightPad(nameString.trim(), 6, "　");
        return nameString;
    }

    /**
     * 將內容切成相當rowPt寬度段落清單<br/>
     * @param content 內容
     * @param rowPt   每行相當多少pt寬度
     * @return 內容每行字串清單
     */
    public static List<String> spliteStringToList(String tartget, double rowPt, BaseFont baseFont, float fontSize) {
        StringBuffer content = new StringBuffer(tartget);
        List<String> rows = new ArrayList<String>();
        StringBuffer cutStrTmp = null;// 暫存準備切行的字串緩存
        double rowPx = 0;
        int index = 1;
        // content中還有未處理的字串
        while (content.length() > 0) {
            // content剩餘字寬是否達rowPt(可切成一行)
            if (index >= content.length()) {
                cutStrTmp = new StringBuffer(content.subSequence(0, index));
                content.delete(0, index);
                rows.add(cutStrTmp.toString());
            } else 
            // 逐字加入cutStrTmp中，直到超過指定的寬度後切成一段
            if (rowPx < rowPt) {
                index++;
                rowPx = baseFont.getWidthPoint(content.subSequence(0, index).toString(), fontSize);
            } else {
                cutStrTmp = new StringBuffer(content.subSequence(0, index));
                content.delete(0, index);
                rows.add(cutStrTmp.toString());
                index = 1;
                rowPx = 0;
            }
        }
        return rows;
    }

    /**
     * 將內容切成相當rowPt寬度段落<br/>
     * @param content 內容
     * @param rowPt   裁切的Pt單位
     * @return 裁切的字串
     */
    public static StringBuffer spliteString(String tartget, double rowPt, BaseFont baseFont, float fontSize) {
        StringBuffer content = new StringBuffer(tartget);
        double rowPx = 0;
        int index = 1;
        while (rowPx < rowPt) {
            if (index >= content.length()) {
                return new StringBuffer(content.subSequence(0, index));
            } else {
                index++;
                rowPx = baseFont.getWidthPoint(content.subSequence(0, index).toString(), fontSize);
            }
        }
        return new StringBuffer(content.subSequence(0, index));
    }

    /**
     * for Fortify
     *
     * @param string
     * @return
     */
    public static String normalizeString(String string) {
        return ObjectUtility.normalizeObject(StringUtils.replace(string, "\r\n", StringUtils.EMPTY));
    }

    /**
     * 半型轉全型.
     * @param s
     * @return　全型中文字
     */
    public static String toChineseFullChar(String s) {
        // copy from bc system
        if (s == null || s.equals("")) {
            return "";
        }
        char[] ca = s.toCharArray();
        outer:
        for (int i = 0; i < ca.length; i++) {
            if (ca[i] > 128) {
                continue;
            }
            switch (ca[i]) {
            case '\b': 
            //backspace BS
            case '\t': 
            //horizontal tab HT
            case '\n': 
            //linefeed LF
            case '\r': 
            //carriage return CR
            case '\f': 
                //form feed FF
                continue outer;
            case 32: 
                //半型空白轉成全型空白
                ca[i] = (char) 12288;
                continue outer;
            default: 
                if (Character.isLetterOrDigit(ca[i])) {
                    //數字或字母
                    ca[i] = (char) (ca[i] + 65248);
                } else {
                    if (ca[i] >= 33 && ca[i] <= 126) {
                        ca[i] = (char) (ca[i] + 65248);
                    } else {
                        if (!(ca[i] == ',' || ca[i] == '.')) {
                            ca[i] = (char) 12288; //其它不合要求的，全部轉成全型空白
                        }
                    }
                }
                continue outer;
            }
        }
        return String.valueOf(ca);
    }

    /**
     * 組合字串(寫入更正日誌用)
     * @param datas
     * @param symbol
     * @return
     */
    public static String formatRifDatas(String[] datas, String symbol) {
        // copy from bc system
        int flag = -1;//記錄最後有值的位子
        StringBuffer data = new StringBuffer();
        for (int i = datas.length - 1; i >= 0; i--) {
            if (!"".equals(datas[i])) {
                flag = i;
                break;//找到值則跳出
            }
        }
        if (flag == -1) return "";
        //組合字串:無值補空白，有值則值前加DASH，第N個值開始無值，則不必補DASH及空白。
        //例:有欄位ABC,情況1:A=OO,B=CC,C=XX→顯示OO-CC-XX。情況2:A=OO,B=無值,C=XX→顯示OO- -XX。情況3:A=OO,B=無值,C=無值→顯示OO
        for (int i = 0; i <= flag; i++) {
            if (i == 0) {
                data.append("".equals(datas[i]) ? " " : datas[i]);
            } else if (i <= flag) {
                data.append(symbol).append("".equals(datas[i]) ? " " : datas[i]);
            }
        }
        return data.toString();
    }

    public static String getProp(String str, String key) {
        return StringUtils.defaultString(StringUtils.substringBetween("," + StringUtils.defaultString(str) + ",", "," + key + "=", ","));
    }

    /**
     * 格式化全形並換行
     *
     * @param str                    原字串
     * @param leftPaddingWhenNewLine 第二行左邊全形空白數量
     * @param width                  整文全形數量
     * @return
     */
    public static String getFormatStringBlock(String str, int leftPaddingWhenNewLine, int width) {
        StringBuffer sb = new StringBuffer();
        AtomicInteger index = new AtomicInteger();
        Arrays.stream(str.split(""))
                .forEach(x -> {
                    sb.append(x);
                    if (index.incrementAndGet() == width) {
                        sb.append("\n");
                        IntStream.range(0, leftPaddingWhenNewLine)
                                .forEach(r -> sb.append("　"));
                        index.set(1);
                    }
                });
        return sb.toString();
    }

}
