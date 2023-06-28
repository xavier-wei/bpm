package com.iisigroup.easyreport.pdf.Helper;

import com.itextpdf.text.pdf.BaseFont;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * 儲存環境自訂參數用<br>
 * 僅供 Easy Report 內部使用
 *
 * @author Goston
 */
public class EnvHelper {

    private static Log log = LogFactory.getLog(EnvHelper.class);

    public static final String FONT_PATH_KY = "default.pdf.font.path";
    private static final String FONT_NAME_KY = "default.pdf.font.name";
    private static final String CNS11643_FONT_PATH_KY = "default.pdf.cns11643.font.path";
    private static final String CNS11643_SUNG_FONT_NAME_KY = "default.pdf.cns11643.sung.font.name";
    private static final String CNS11643_KAI_FONT_NAME_KY = "default.pdf.cns11643.kai.font.name";
    private static final String FONT_INITIAL_KY = "default.pdf.font.initial";
    private static final String DOC_MARGIN_LEFT_KY = "default.pdf.document.marginLeft";
    private static final String DOC_MARGIN_RIGHT_KY = "default.pdf.document.marginRight";
    private static final String DOC_MARGIN_TOP_KY = "default.pdf.document.marginTop";
    private static final String DOC_MARGIN_BOTTOM_KY = "default.pdf.document.marginBottom";
    private static final String INTEGER_FORMAT_KY = "default.pdf.integer.format";
    private static final String DECIMAL_FORMAT_KY = "default.pdf.decimal.format";
    private static final String QRCODE_VERSION_KY = "default.pdf.qrcode.version";
    private static final String CODE128_CODESET_KY = "default.barcode128.codeSet";
    private static final String OWNER_PD_KY = "default.pdf.owner.password";

    private static String fontPath;
    private static String fontName;
    private static String cns11643FontPath;
    private static String[] cns11643SungFontName;
    private static String[] cns11643KaiFontName;
    private static Boolean fontInitial;
    private static Float marginLeft;
    private static Float marginRight;
    private static Float marginTop;
    private static Float marginBottom;
    private static String integerFormat;
    private static String decimalFormat;
    private static String qrcodeVersion;
    private static String code128CodeSet;
    private static String ownerPassword;

    //private static BaseFont baseFont = null;
    private static Map<String, BaseFont> baseFontMap = new HashMap<String, BaseFont>();
    private static List<BaseFont> cnsSungBaseFontList = null;
    private static List<BaseFont> cnsKaiBaseFontList = null;

    static {
        try {
            Properties props = PropertiesHelper.getProperties(PropertiesHelper.DEFAULT_CONFIG);
            fontPath = props.getProperty(FONT_PATH_KY);
            fontName = props.getProperty(FONT_NAME_KY);
            fontInitial = Boolean.valueOf(props.getProperty(FONT_INITIAL_KY));
            marginLeft = Float.valueOf(props.getProperty(DOC_MARGIN_LEFT_KY));
            marginRight = Float.valueOf(props.getProperty(DOC_MARGIN_RIGHT_KY));
            marginTop = Float.valueOf(props.getProperty(DOC_MARGIN_TOP_KY));
            marginBottom = Float.valueOf(props.getProperty(DOC_MARGIN_BOTTOM_KY));
            integerFormat = props.getProperty(INTEGER_FORMAT_KY);
            decimalFormat = props.getProperty(DECIMAL_FORMAT_KY);
            qrcodeVersion = props.getProperty(QRCODE_VERSION_KY);
            code128CodeSet = props.getProperty(CODE128_CODESET_KY);
            ownerPassword = props.getProperty(OWNER_PD_KY);

            props = PropertiesHelper.getProperties(PropertiesHelper.USER_CONFIG);
            if (props != null) {
                String value = props.getProperty(FONT_PATH_KY);
                if (StringUtils.isNotBlank(value)) {
                    fontPath = value;
                }
                value = props.getProperty(FONT_NAME_KY);
                if (StringUtils.isNotBlank(value)) {
                    fontName = value;
                }
                value = props.getProperty(CNS11643_FONT_PATH_KY);
                if (StringUtils.isNotBlank(value)) {
                    cns11643FontPath = value;
                }
                value = props.getProperty(CNS11643_SUNG_FONT_NAME_KY);
                if (StringUtils.isNotBlank(value)) {
                    cns11643SungFontName = StringUtils.split(value, ";");
                }
                value = props.getProperty(CNS11643_KAI_FONT_NAME_KY);
                if (StringUtils.isNotBlank(value)) {
                    cns11643KaiFontName = StringUtils.split(value, ";");
                }
                value = props.getProperty(FONT_INITIAL_KY);
                if (StringUtils.isNotBlank(value)) {
                    fontInitial = Boolean.valueOf(value);
                }
                value = props.getProperty(DOC_MARGIN_LEFT_KY);
                if (StringUtils.isNotBlank(value)) {
                    marginLeft = Float.valueOf(value);
                }
                value = props.getProperty(DOC_MARGIN_RIGHT_KY);
                if (StringUtils.isNotBlank(value)) {
                    marginRight = Float.valueOf(value);
                }
                value = props.getProperty(DOC_MARGIN_TOP_KY);
                if (StringUtils.isNotBlank(value)) {
                    marginTop = Float.valueOf(value);
                }
                value = props.getProperty(DOC_MARGIN_BOTTOM_KY);
                if (StringUtils.isNotBlank(value)) {
                    marginBottom = Float.valueOf(value);
                }
                value = props.getProperty(INTEGER_FORMAT_KY);
                if (StringUtils.isNotBlank(value)) {
                    integerFormat = value;
                }
                value = props.getProperty(DECIMAL_FORMAT_KY);
                if (StringUtils.isNotBlank(value)) {
                    decimalFormat = value;
                }
                value = props.getProperty(QRCODE_VERSION_KY);
                if (StringUtils.isNotBlank(value)) {
                    qrcodeVersion = value;
                }
                value = props.getProperty(CODE128_CODESET_KY);
                if (StringUtils.isNotBlank(value)) {
                    code128CodeSet = value;
                }
                value = props.getProperty(OWNER_PD_KY);
                if (StringUtils.isNotBlank(value)) {
                    ownerPassword = value;
                }
            }

            if (fontInitial.booleanValue()) {
                // BaseFont - 防止字型檔有時可能是存於 NAS，讀取字型速度受影響的問題
                baseFontMap.put(fontPath + fontName, BaseFont.createFont(fontPath + fontName, BaseFont.IDENTITY_H, BaseFont.EMBEDDED));

                if (StringUtils.isNotBlank(cns11643FontPath) && cns11643SungFontName != null && cns11643SungFontName.length > 0) {
                    cnsSungBaseFontList = new ArrayList<BaseFont>();
                    for (String font : cns11643SungFontName) {
                        cnsSungBaseFontList.add(BaseFont.createFont(cns11643FontPath + font, BaseFont.IDENTITY_H, BaseFont.EMBEDDED));
                    }
                }

                if (StringUtils.isNotBlank(cns11643FontPath) && cns11643KaiFontName != null && cns11643KaiFontName.length > 0) {
                    cnsKaiBaseFontList = new ArrayList<BaseFont>();
                    for (String font : cns11643KaiFontName) {
                        cnsKaiBaseFontList.add(BaseFont.createFont(cns11643FontPath + font, BaseFont.IDENTITY_H, BaseFont.EMBEDDED));
                    }
                }
            }
        }
        catch (Exception e) {
            log.error("Easy Report Initializing Error: " + e.getMessage());
            throw new ExceptionInInitializerError("報表環境初始化失敗...");
        }
    }

    /**
     * 取得 BaseFont
     *
     * @return BaseFont
     */
    public static BaseFont getBaseFont() {
        return baseFontMap.get(fontPath + fontName);
    }

    /**
     * 取得 BaseFont
     *
     * @param fontPath 路徑
     * @param fontName 檔名
     * @return BaseFont
     */
    public static BaseFont getBaseFont(String fontPath, String fontName) {
        return baseFontMap.get(fontPath + fontName);
    }

    /**
     * 新增 BaseFont
     *
     * @param fontPath 路徑
     * @param fontName 檔名
     * @param baseFont BaseFont
     */
    public static void addBaseFont(String fontPath, String fontName, BaseFont baseFont) {
        if (!baseFontMap.containsKey(fontPath + fontName))
            baseFontMap.put(fontPath + fontName, baseFont);
    }

    public static String getFontPath() {
        return fontPath;
    }

    public static String getFontName() {
        return fontName;
    }

    public static Boolean getFontInitial() {
        return fontInitial;
    }

    public static Float getMarginLeft() {
        return marginLeft;
    }

    public static Float getMarginRight() {
        return marginRight;
    }

    public static Float getMarginTop() {
        return marginTop;
    }

    public static Float getMarginBottom() {
        return marginBottom;
    }

    public static String getIntegerFormat() {
        return integerFormat;
    }

    public static String getDecimalFormat() {
        return decimalFormat;
    }

    public static String getQrcodeVersion() {
        return qrcodeVersion;
    }

    public static String getCode128CodeSet() {
        return code128CodeSet;
    }

    public static String getOwnerPassword() {
        return ownerPassword;
    }

    public static String getCns11643FontPath() {
        return cns11643FontPath;
    }

    public static String[] getCns11643SungFontName() {
        if (cns11643SungFontName != null)
            return cns11643SungFontName.clone();
        else
            return null;
    }

    public static String[] getCns11643KaiFontName() {
        if (cns11643KaiFontName != null)
            return cns11643KaiFontName.clone();
        else
            return null;
    }

    public static List<BaseFont> getCnsSungBaseFontList() {
        return cnsSungBaseFontList;
    }

    public static List<BaseFont> getCnsKaiBaseFontList() {
        return cnsKaiBaseFontList;
    }

}
