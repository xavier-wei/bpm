package org.apache.commons.lang;


/**
 * 這個 class 只是為了相容報表工具用，不要直接使用
 *
 * @author swho
 */
@Deprecated
public class StringUtils {

    public static String defaultString(String string) {
        return org.apache.commons.lang3.StringUtils.defaultString(string);
    }
    
    public static boolean isNotBlank(String string) {
        return org.apache.commons.lang3.StringUtils.isNotBlank(string);
    }
    
    public static String[] split(String string, String separatorChars){
        return org.apache.commons.lang3.StringUtils.split(string, separatorChars);
    }
}
