package tw.gov.pcc.utils;

import java.io.File;
import java.util.regex.Matcher;

public class CommonUtils {


    public static String normalizePath(String path) {
        return path.replaceAll("(\\\\+|/+)", Matcher.quoteReplacement(File.separator));
    }


}
