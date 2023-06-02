package com.iisigroup.easyreport.pdf;

import com.iisigroup.easyreport.pdf.Helper.EnvHelper;
import com.iisigroup.easyreport.pdf.enums.CNS11643;
import com.iisigroup.easyreport.pdf.exception.ReportException;
import com.iisigroup.easyreport.pdf.utility.PdfPTableUtility;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.Barcode39;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.FontSelector;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfDestination;
import com.itextpdf.text.pdf.PdfOutline;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPCellEvent;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 所有報表繼承之 Base Class
 *
 * @author Goston
 */
public class ReportBase {

    private static Log log = LogFactory.getLog(ReportBase.class);

    /**
     * 預設報表紙張大小為 <code>PageSize.A4.rotate()</code> <br>
     * <code>marginLeft: 30</code><br>
     * <code>marginRight: 30</code><br>
     * <code>marginTop: 30</code><br>
     * <code>marginBottom: 30</code><br>
     * 預設之 <code>margin</code> 可於 <code>easyreport.properties</code> 中覆寫<br>
     */
    protected Document document;

    private ByteArrayOutputStream bao;
    private PdfWriter writer;

    private PdfContentByte contentByte = null;
    private PdfContentByte contentByteUnder = null;
    private PdfTemplate template = null;
    private PdfOutline rootOutline = null;

    /**
     * 水平置中
     */
    public static final int CENTER = Element.ALIGN_CENTER;

    /**
     * 水平置右
     */
    public static final int RIGHT = Element.ALIGN_RIGHT;

    /**
     * 水平置左
     */
    public static final int LEFT = Element.ALIGN_LEFT;

    /**
     * 段落左右對齊
     */
    public static final int JUSTIFIED = Element.ALIGN_JUSTIFIED;

    /**
     * 段落左右對齊（含最後一行）
     */
    public static final int JUSTIFIEDALL = Element.ALIGN_JUSTIFIED_ALL;

    /**
     * 垂直對準基線
     */
    public static final int BASELINE = Element.ALIGN_BASELINE;

    /**
     * 垂直置底
     */
    public static final int BOTTOM = Element.ALIGN_BOTTOM;

    /**
     * 垂直置中
     */
    public static final int MIDDLE = Element.ALIGN_MIDDLE;

    /**
     * 垂直置頂
     */
    public static final int TOP = Element.ALIGN_TOP;

    /**
     * 空白字串（用於欄位值是空值時，避免 new 太多空白 String 造成記憶體浪費）
     */
    public static final String EMPTY_FIELD = " ";

    /**
     * Base Font
     */
    private BaseFont baseFont = null;

    /**
     * Base Font - CNS11643 全字庫 宋體
     */
    private static List<BaseFont> cnsSungBaseFontList = null;

    /**
     * Base Font - CNS11643 全字庫 楷體
     */
    private static List<BaseFont> cnsKaiBaseFontList = null;

    private Map<String, Font> fontMap = new HashMap<String, Font>();
    private Map<String, FontSelector> fontSelectorMap = new HashMap<String, FontSelector>();

    /**
     * 以預設參數建立報表: <br>
     * <br>
     * 預設報表紙張大小為 <code>PageSize.A4.rotate()</code> <br>
     * <code>marginLeft: 30</code><br>
     * <code>marginRight: 30</code><br>
     * <code>marginTop: 30</code><br>
     * <code>marginBottom: 30</code><br>
     * <code>Base Font</code>: <code>easyreport.properties</code> 中 <code>default.pdf.font.name</code> 所指定之字型<br>
     * 預設之 <code>margin</code> 可於 <code>easyreport.properties</code> 中覆寫<br>
     * <br>
     * 將報表輸出至 <code>ByteArrayOutputStream</code>
     *
     * @throws ReportException
     */
    public ReportBase() throws ReportException {
        super();

        try {
            initFont();
            document = createDocument();
            bao = new ByteArrayOutputStream();
            writer = PdfWriter.getInstance(document, bao);
        }
        catch (Exception e) {
            log.error("Easy Report Initializing Error: " + e.getMessage());
            throw new ReportException(e);
        }
    }

    /**
     * 以預設參數建立報表: <br>
     * <br>
     * 預設報表紙張大小為 <code>PageSize.A4.rotate()</code> <br>
     * <code>marginLeft: 30</code><br>
     * <code>marginRight: 30</code><br>
     * <code>marginTop: 30</code><br>
     * <code>marginBottom: 30</code><br>
     * <code>Base Font</code>: <code>easyreport.properties</code> 中 <code>default.pdf.font.name</code> 所指定之字型<br>
     * 預設之 <code>margin</code> 可於 <code>easyreport.properties</code> 中覆寫<br>
     * <br>
     * 將報表輸出至指定檔案
     *
     * @param outputFilename
     */
    public ReportBase(String outputFilename) throws ReportException {
        super();
        FileOutputStream fos = null;

        try {
            initFont();
            document = createDocument();
            fos = new FileOutputStream(outputFilename);
            writer = PdfWriter.getInstance(document, fos);
        }
        catch (Exception e) {
            log.error("Easy Report Initializing Error: " + e.getMessage());
            throw new ReportException(e);
        }
        finally {
            try {
                if (fos != null)
                    fos.close();
            }
            catch (IOException ioe) {
                // 運氣很不好...
                ioe.printStackTrace();
            }
        }
    }

    /**
     * 若報表並非直接產製為檔案，則可在產製完成後以此 Method 取得輸出之 <code>ByteArrayOutputStream</code> 進行後續處理
     *
     * @return
     */
    public ByteArrayOutputStream getOutputStream() {
        return bao;
    }

    /**
     * 取得 <code>PdfWriter</code>
     *
     * @return
     */
    protected PdfWriter getWriter() {
        return writer;
    }

    /**
     * 取得 Base Font
     */
    public BaseFont getBaseFont() {
        return baseFont;
    }

    /**
     * 設定 Base Font
     */
    public void setBaseFont(BaseFont baseFont) {
        this.baseFont = baseFont;
    }

    /**
     * 取得 Base Font - CNS11643 全字庫 宋體
     */
    public static List<BaseFont> getCnsSungBaseFontList() {
        return cnsSungBaseFontList;
    }

    /**
     * 設定 Base Font - CNS11643 全字庫 宋體
     */
    public static void setCnsSungBaseFontList(List<BaseFont> cnsSungBaseFontList) {
        ReportBase.cnsSungBaseFontList = cnsSungBaseFontList;
    }

    /**
     * 取得 Base Font - CNS11643 全字庫 楷體
     */
    public static List<BaseFont> getCnsKaiBaseFontList() {
        return cnsKaiBaseFontList;
    }

    /**
     * 設定 Base Font - CNS11643 全字庫 楷體
     */
    public static void setCnsKaiBaseFontList(List<BaseFont> cnsKaiBaseFontList) {
        ReportBase.cnsKaiBaseFontList = cnsKaiBaseFontList;
    }

    /**
     * 以預設參數設定報表：<br>
     * 預設報表紙張大小為 <code>PageSize.A4.rotate()</code> <br>
     * <code>marginLeft: 30</code><br>
     * <code>marginRight: 30</code><br>
     * <code>marginTop: 30</code><br>
     * <code>marginBottom: 30</code><br>
     * 預設之 <code>margin</code> 可於 <code>easyreport.properties</code> 中覆寫<br>
     * <br>
     * 若要自訂紙張大小可繼承 ReportBase 後再 Override 此 Method<br>
     *
     * @return
     */
    protected Document createDocument() {
        // 參數說明: Document(頁面大小, 左邊空白, 右邊空白, 上面空白, 下面空白)
        return new Document(PageSize.A4.rotate(), EnvHelper.getMarginLeft().floatValue(), EnvHelper.getMarginRight().floatValue(), EnvHelper.getMarginTop().floatValue(), EnvHelper.getMarginBottom().floatValue());
    }

    /**
     * 以 <code>easyreport.properties</code> 的 <code>default.pdf.font.path</code> 及 <code>default.pdf.font.name</code> 所指定之字型初始化 Base Font
     *
     * @throws Exception
     */
    protected void initFont() throws ReportException {
        try {
            if (EnvHelper.getFontInitial().booleanValue()) {
                // 防止字型檔有時可能是存於 NAS，讀取字型速度受影響的問題
                baseFont = EnvHelper.getBaseFont();

                cnsSungBaseFontList = EnvHelper.getCnsSungBaseFontList();
                cnsKaiBaseFontList = EnvHelper.getCnsKaiBaseFontList();
            }
            else {
                initFont(EnvHelper.getFontPath(), EnvHelper.getFontName());

                initCnsFont();
            }
        }
        catch (Exception e) {
            log.error("Easy Report Initializing Error: " + e.getMessage());
            throw new ReportException(e);
        }
    }

    /**
     * CNS11643 全字庫字型初始化
     */
    private void initCnsFont() {
        try {
            if (StringUtils.isNotBlank(EnvHelper.getCns11643FontPath()) && EnvHelper.getCns11643SungFontName() != null && EnvHelper.getCns11643SungFontName().length > 0) {
                cnsSungBaseFontList = new ArrayList<BaseFont>();
                for (String font : EnvHelper.getCns11643SungFontName()) {
                    cnsSungBaseFontList.add(BaseFont.createFont(EnvHelper.getCns11643FontPath() + font, BaseFont.IDENTITY_H, BaseFont.EMBEDDED));
                }
            }

            if (StringUtils.isNotBlank(EnvHelper.getCns11643FontPath()) && EnvHelper.getCns11643KaiFontName() != null && EnvHelper.getCns11643KaiFontName().length > 0) {
                cnsKaiBaseFontList = new ArrayList<BaseFont>();
                for (String font : EnvHelper.getCns11643KaiFontName()) {
                    cnsKaiBaseFontList.add(BaseFont.createFont(EnvHelper.getCns11643FontPath() + font, BaseFont.IDENTITY_H, BaseFont.EMBEDDED));
                }
            }
        }
        catch (Exception e) {
            log.error("Easy Report Initializing Error: " + e.getMessage());
            throw new ReportException(e);
        }
    }

    /**
     * 自 <code>easyreport.properties</code> 的 <code>default.pdf.font.path</code> 取得指定之字型檔檔名初始化 Base Font
     *
     * @param fontName 字型檔檔名
     * @throws Exception
     */
    protected void initFont(String fontName) throws ReportException {
        try {
            initFont(EnvHelper.getFontPath(), fontName);
        }
        catch (Exception e) {
            log.error("Easy Report Initializing Error: " + e.getMessage());
            throw new ReportException(e);
        }
    }

    /**
     * 初始化 Base Font
     *
     * @param fontPath 字型檔所在的目錄路徑（結尾須加「/」）
     * @param fontName 字型檔檔名
     * @throws Exception
     */
    protected void initFont(String fontPath, String fontName) throws ReportException {
        try {
            if (EnvHelper.getFontInitial().booleanValue()) {
                if (EnvHelper.getBaseFont(fontPath, fontName) != null) {
                    baseFont = EnvHelper.getBaseFont(fontPath, fontName);
                }
                else {
                    baseFont = BaseFont.createFont(fontPath + fontName, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
                    EnvHelper.addBaseFont(fontPath, fontName, baseFont);
                }
            }
            else {
                baseFont = BaseFont.createFont(fontPath + fontName, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            }
        }
        catch (Exception e) {
            log.error("Easy Report Initializing Error: " + e.getMessage());
            throw new ReportException(e);
        }
    }

    /**
     * 取得指定字體大小之 Base Font 字型
     *
     * @param fontSize 字體大小
     * @return
     */
    protected Font getFont(int fontSize) {
        return getFont(fontSize, Font.NORMAL);
    }

    /**
     * 取得指定字體樣式及大小之 Base Font 字型
     *
     * @param fontSize  字體大小
     * @param fontStyle 字體樣式，可為 <code>Font.NORMAL</code>、<code>Font.BOLD</code>、<code>Font.BOLDITALIC</code>、<code>Font.ITALIC</code>、<code>Font.STRIKETHRU</code>、<code>Font.UNDERLINE</code>
     * @return
     */
    protected Font getFont(int fontSize, int fontStyle) {
        String fontKey = "";

        if (fontStyle == Font.BOLD)
            fontKey = String.valueOf(fontSize) + "b";
        else if (fontStyle == Font.BOLDITALIC)
            fontKey = String.valueOf(fontSize) + "bi";
        else if (fontStyle == Font.ITALIC)
            fontKey = String.valueOf(fontSize) + "i";
        else if (fontStyle == Font.STRIKETHRU)
            fontKey = String.valueOf(fontSize) + "s";
        else if (fontStyle == Font.UNDERLINE)
            fontKey = String.valueOf(fontSize) + "u";
        else
            fontKey = String.valueOf(fontSize);

        Font font = fontMap.get(fontKey);
        if (font == null) {
            font = new Font(baseFont, fontSize, fontStyle);
            fontMap.put(fontKey, font);
        }

        return font;
    }

    /**
     * 取得指定字體大小的全字庫 FontSelector
     *
     * @param cnsFont  CNS11643.SUNG 或 CNS11643.KAI
     * @param fontSize 字體大小
     * @return
     */
    private FontSelector getFontSelector(CNS11643 cnsFont, int fontSize) {
        return getFontSelector(cnsFont, fontSize, Font.NORMAL);
    }

    /**
     * 取得指定字體樣式及大小的全字庫 FontSelector
     *
     * @param cnsFont   CNS11643.SUNG 或 CNS11643.KAI
     * @param fontSize  字體大小
     * @param fontStyle 字體樣式，可為 <code>Font.NORMAL</code>、<code>Font.BOLD</code>、<code>Font.BOLDITALIC</code>、<code>Font.ITALIC</code>、<code>Font.STRIKETHRU</code>、<code>Font.UNDERLINE</code>
     * @return
     */
    private FontSelector getFontSelector(CNS11643 cnsFont, int fontSize, int fontStyle) {
        String fontKey = "";

        if (fontStyle == Font.BOLD)
            fontKey = cnsFont.toString() + String.valueOf(fontSize) + "b";
        else if (fontStyle == Font.BOLDITALIC)
            fontKey = cnsFont.toString() + String.valueOf(fontSize) + "bi";
        else if (fontStyle == Font.ITALIC)
            fontKey = cnsFont.toString() + String.valueOf(fontSize) + "i";
        else if (fontStyle == Font.STRIKETHRU)
            fontKey = cnsFont.toString() + String.valueOf(fontSize) + "s";
        else if (fontStyle == Font.UNDERLINE)
            fontKey = cnsFont.toString() + String.valueOf(fontSize) + "u";
        else
            fontKey = cnsFont.toString() + String.valueOf(fontSize);

        FontSelector fontSelector = fontSelectorMap.get(fontKey);

        if (fontSelector == null) {
            fontSelector = new FontSelector();

            if (cnsFont == CNS11643.SUNG) {
                for (BaseFont baseFont : cnsSungBaseFontList) {
                    fontSelector.addFont(new Font(baseFont, fontSize, fontStyle));
                }
            }
            else {
                for (BaseFont baseFont : cnsKaiBaseFontList) {
                    fontSelector.addFont(new Font(baseFont, fontSize, fontStyle));
                }
            }

            fontSelectorMap.put(fontKey, fontSelector);
        }

        return fontSelector;
    }

    /**
     * 回傳由 <code>PdfWriter</code> 產生的 <code>PdfContentByte</code>
     *
     * @return
     */
    protected PdfContentByte getPdfContetByte() {
        if (contentByte == null)
            contentByte = writer.getDirectContent();
        return contentByte;
    }

    /**
     * 回傳由 <code>PdfWriter</code> 產生的 <code>PdfContentByte</code> - Under
     *
     * @return
     */
    protected PdfContentByte getPdfContetByteUnder() {
        if (contentByteUnder == null)
            contentByteUnder = writer.getDirectContentUnder();
        return contentByteUnder;
    }

    /**
     * 取得 <code>PdfTemplate</code>
     *
     * @param width  欲建立之 <code>PdfTemplate</code> 的寬
     * @param height 欲建立之 <code>PdfTemplate</code> 的高
     * @return
     */
    protected PdfTemplate getPdfTemplate(float width, float height) {
        return getPdfContetByte().createTemplate(width, height);
    }

    /**
     * 取得目前頁數
     *
     * @return
     */
    protected int getCurrentPageNumber() {
        return getWriter().getCurrentPageNumber();
    }

    /**
     * 判斷 <code>PdfPTable</code> 是否能在此頁面印得下（僅可適用於整個頁面是由單一 <code>PdfPTable</code> 構成的情況）
     *
     * @param table 表格物件
     * @return <code>true</code> 本頁可容納該表格；<code>false</code> 本頁無法容納該表格
     * @throws ReportException
     */
    protected boolean fitsPage(PdfPTable table) throws ReportException {
        try {
            ColumnText ct = new ColumnText(getWriter().getDirectContent());
            ct.setSimpleColumn(document.getPageSize().getLeft(document.leftMargin()), document.getPageSize().getBottom(document.bottomMargin()), document.getPageSize().getRight(document.rightMargin()), document.getPageSize().getTop(document.topMargin()));

            int status = ColumnText.START_COLUMN;

            ct.addElement(table);
            status = ct.go(true);

            if (ColumnText.hasMoreText(status)) {
                return false;
            }
            else {
                return true;
            }
        }
        catch (Exception e) {
            log.error("Easy Report Error: " + e.getMessage());
            throw new ReportException(e);
        }
    }

    /**
     * 刪除表格最後一行
     *
     * @param table 表格物件
     */
    protected static void deleteLastRow(PdfPTable table) {
        table.deleteLastRow();
    }

    /**
     * 刪除表格最後 <code>n</code> 行
     *
     * @param table 表格物件
     * @param n     <code>n</code> 須大於 <code>0</code>
     */
    protected static void deleteLastRow(PdfPTable table, int n) {
        if (n > 0) {
            for (int i = 0; i < n; i++) {
                table.deleteLastRow();
            }
        }
    }

    /**
     * 建立 Cell - 基本型 最常用的
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param colspan     Column Span 若無填 <code>1</code>
     * @param rowspan     Row Span 若無填 <code>1</code>
     * @param str         欄位內容字串
     * @param fontSize    字體大小
     * @param borderWidth 框線的寬度
     * @param hAlignment  水平對齊方式
     */
    protected void addCell(PdfPTable table, int colspan, int rowspan, String str, int fontSize, float borderWidth, int hAlignment) {
        table.addCell(PdfPTableUtility.createPdfPCell(colspan, rowspan, str, getFont(fontSize), borderWidth, Rectangle.UNDEFINED, hAlignment, Element.ALIGN_UNDEFINED, PdfPTableUtility.LINESPACE_UNDEFINED));
    }

    /**
     * 建立 Cell
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param rowspan     Row Span 若無填 <code>1</code>
     * @param str         欄位內容字串
     * @param fontSize    字體大小
     * @param borderWidth 框線的寬度
     * @param hAlignment  水平對齊方式
     */
    protected void addCell(PdfPTable table, int rowspan, String str, int fontSize, float borderWidth, int hAlignment) {
        table.addCell(PdfPTableUtility.createPdfPCell(PdfPTableUtility.SPAN_UNDEFINED, rowspan, str, getFont(fontSize), borderWidth, Rectangle.UNDEFINED, hAlignment, Element.ALIGN_UNDEFINED, PdfPTableUtility.LINESPACE_UNDEFINED));
    }

    /**
     * 建立 Cell
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param str         欄位內容字串
     * @param fontSize    字體大小
     * @param borderWidth 框線的寬度
     * @param hAlignment  水平對齊方式
     */
    protected void addCell(PdfPTable table, String str, int fontSize, float borderWidth, int hAlignment) {
        table.addCell(PdfPTableUtility.createPdfPCell(PdfPTableUtility.SPAN_UNDEFINED, PdfPTableUtility.SPAN_UNDEFINED, str, getFont(fontSize), borderWidth, Rectangle.UNDEFINED, hAlignment, Element.ALIGN_UNDEFINED, PdfPTableUtility.LINESPACE_UNDEFINED));
    }

    /**
     * 建立 Cell - 基本型 最常用的
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param colspan     Column Span 若無填 <code>1</code>
     * @param rowspan     Row Span 若無填 <code>1</code>
     * @param str         欄位內容字串
     * @param font        字體
     * @param borderWidth 框線的寬度
     * @param hAlignment  水平對齊方式
     */
    protected void addCell(PdfPTable table, int colspan, int rowspan, String str, Font font, float borderWidth, int hAlignment) {
        table.addCell(PdfPTableUtility.createPdfPCell(colspan, rowspan, str, font, borderWidth, Rectangle.UNDEFINED, hAlignment, Element.ALIGN_UNDEFINED, PdfPTableUtility.LINESPACE_UNDEFINED));
    }

    /**
     * 建立 Cell
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param rowspan     Row Span 若無填 <code>1</code>
     * @param str         欄位內容字串
     * @param font        字體
     * @param borderWidth 框線的寬度
     * @param hAlignment  水平對齊方式
     */
    protected void addCell(PdfPTable table, int rowspan, String str, Font font, float borderWidth, int hAlignment) {
        table.addCell(PdfPTableUtility.createPdfPCell(PdfPTableUtility.SPAN_UNDEFINED, rowspan, str, font, borderWidth, Rectangle.UNDEFINED, hAlignment, Element.ALIGN_UNDEFINED, PdfPTableUtility.LINESPACE_UNDEFINED));
    }

    /**
     * 建立 Cell
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param str         欄位內容字串
     * @param font        字體
     * @param borderWidth 框線的寬度
     * @param hAlignment  水平對齊方式
     */
    protected void addCell(PdfPTable table, String str, Font font, float borderWidth, int hAlignment) {
        table.addCell(PdfPTableUtility.createPdfPCell(PdfPTableUtility.SPAN_UNDEFINED, PdfPTableUtility.SPAN_UNDEFINED, str, font, borderWidth, Rectangle.UNDEFINED, hAlignment, Element.ALIGN_UNDEFINED, PdfPTableUtility.LINESPACE_UNDEFINED));
    }

    /**
     * 建立 Cell - 基本型 最常用的 - for 全字庫
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param colspan     Column Span 若無填 <code>1</code>
     * @param rowspan     Row Span 若無填 <code>1</code>
     * @param str         欄位內容字串
     * @param cnsFont     CNS11643.SUNG 或 CNS11643.KAI
     * @param fontSize    字體大小
     * @param borderWidth 框線的寬度
     * @param hAlignment  水平對齊方式
     */
    protected void addCell(PdfPTable table, int colspan, int rowspan, String str, CNS11643 cnsFont, int fontSize, float borderWidth, int hAlignment) {
        table.addCell(PdfPTableUtility.createPdfPCell(colspan, rowspan, str, getFontSelector(cnsFont, fontSize), borderWidth, Rectangle.UNDEFINED, hAlignment, Element.ALIGN_UNDEFINED, PdfPTableUtility.LINESPACE_UNDEFINED));
    }

    /**
     * 建立 Cell - for 全字庫
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param rowspan     Row Span 若無填 <code>1</code>
     * @param str         欄位內容字串
     * @param cnsFont     CNS11643.SUNG 或 CNS11643.KAI
     * @param fontSize    字體大小
     * @param borderWidth 框線的寬度
     * @param hAlignment  水平對齊方式
     */
    protected void addCell(PdfPTable table, int rowspan, String str, CNS11643 cnsFont, int fontSize, float borderWidth, int hAlignment) {
        table.addCell(PdfPTableUtility.createPdfPCell(PdfPTableUtility.SPAN_UNDEFINED, rowspan, str, getFontSelector(cnsFont, fontSize), borderWidth, Rectangle.UNDEFINED, hAlignment, Element.ALIGN_UNDEFINED, PdfPTableUtility.LINESPACE_UNDEFINED));
    }

    /**
     * 建立 Cell - for 全字庫
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param str         欄位內容字串
     * @param cnsFont     CNS11643.SUNG 或 CNS11643.KAI
     * @param fontSize    字體大小
     * @param borderWidth 框線的寬度
     * @param hAlignment  水平對齊方式
     */
    protected void addCell(PdfPTable table, String str, CNS11643 cnsFont, int fontSize, float borderWidth, int hAlignment) {
        table.addCell(PdfPTableUtility.createPdfPCell(PdfPTableUtility.SPAN_UNDEFINED, PdfPTableUtility.SPAN_UNDEFINED, str, getFontSelector(cnsFont, fontSize), borderWidth, Rectangle.UNDEFINED, hAlignment, Element.ALIGN_UNDEFINED, PdfPTableUtility.LINESPACE_UNDEFINED));
    }

    /**
     * 建立 Cell
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param colspan     Column Span 若無填 <code>1</code>
     * @param rowspan     Row Span 若無填 <code>1</code>
     * @param str         欄位內容字串
     * @param fontSize    字體大小
     * @param fontStyle   字體樣式
     * @param borderWidth 框線的寬度
     * @param hAlignment  水平對齊方式
     */
    protected void addCellWithFontStyle(PdfPTable table, int colspan, int rowspan, String str, int fontSize, int fontStyle, float borderWidth, int hAlignment) {
        table.addCell(PdfPTableUtility.createPdfPCell(colspan, rowspan, str, getFont(fontSize, fontStyle), borderWidth, Rectangle.UNDEFINED, hAlignment, Element.ALIGN_UNDEFINED, PdfPTableUtility.LINESPACE_UNDEFINED));
    }

    /**
     * 建立 Cell
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param rowspan     Row Span 若無填 <code>1</code>
     * @param str         欄位內容字串
     * @param fontSize    字體大小
     * @param fontStyle   字體樣式
     * @param borderWidth 框線的寬度
     * @param hAlignment  水平對齊方式
     */
    protected void addCellWithFontStyle(PdfPTable table, int rowspan, String str, int fontSize, int fontStyle, float borderWidth, int hAlignment) {
        table.addCell(PdfPTableUtility.createPdfPCell(PdfPTableUtility.SPAN_UNDEFINED, rowspan, str, getFont(fontSize, fontStyle), borderWidth, Rectangle.UNDEFINED, hAlignment, Element.ALIGN_UNDEFINED, PdfPTableUtility.LINESPACE_UNDEFINED));
    }

    /**
     * 建立 Cell
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param str         欄位內容字串
     * @param fontSize    字體大小
     * @param fontStyle   字體樣式
     * @param borderWidth 框線的寬度
     * @param hAlignment  水平對齊方式
     */
    protected void addCellWithFontStyle(PdfPTable table, String str, int fontSize, int fontStyle, float borderWidth, int hAlignment) {
        table.addCell(PdfPTableUtility.createPdfPCell(PdfPTableUtility.SPAN_UNDEFINED, PdfPTableUtility.SPAN_UNDEFINED, str, getFont(fontSize, fontStyle), borderWidth, Rectangle.UNDEFINED, hAlignment, Element.ALIGN_UNDEFINED, PdfPTableUtility.LINESPACE_UNDEFINED));
    }

    /**
     * 建立 Cell - for 全字庫
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param colspan     Column Span 若無填 <code>1</code>
     * @param rowspan     Row Span 若無填 <code>1</code>
     * @param str         欄位內容字串
     * @param cnsFont     CNS11643.SUNG 或 CNS11643.KAI
     * @param fontSize    字體大小
     * @param fontStyle   字體樣式
     * @param borderWidth 框線的寬度
     * @param hAlignment  水平對齊方式
     */
    protected void addCellWithFontStyle(PdfPTable table, int colspan, int rowspan, String str, CNS11643 cnsFont, int fontSize, int fontStyle, float borderWidth, int hAlignment) {
        table.addCell(PdfPTableUtility.createPdfPCell(colspan, rowspan, str, getFontSelector(cnsFont, fontSize, fontStyle), borderWidth, Rectangle.UNDEFINED, hAlignment, Element.ALIGN_UNDEFINED, PdfPTableUtility.LINESPACE_UNDEFINED));
    }

    /**
     * 建立 Cell - for 全字庫
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param rowspan     Row Span 若無填 <code>1</code>
     * @param str         欄位內容字串
     * @param cnsFont     CNS11643.SUNG 或 CNS11643.KAI
     * @param fontSize    字體大小
     * @param fontStyle   字體樣式
     * @param borderWidth 框線的寬度
     * @param hAlignment  水平對齊方式
     */
    protected void addCellWithFontStyle(PdfPTable table, int rowspan, String str, CNS11643 cnsFont, int fontSize, int fontStyle, float borderWidth, int hAlignment) {
        table.addCell(PdfPTableUtility.createPdfPCell(PdfPTableUtility.SPAN_UNDEFINED, rowspan, str, getFontSelector(cnsFont, fontSize, fontStyle), borderWidth, Rectangle.UNDEFINED, hAlignment, Element.ALIGN_UNDEFINED, PdfPTableUtility.LINESPACE_UNDEFINED));
    }

    /**
     * 建立 Cell - for 全字庫
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param str         欄位內容字串
     * @param cnsFont     CNS11643.SUNG 或 CNS11643.KAI
     * @param fontSize    字體大小
     * @param fontStyle   字體樣式
     * @param borderWidth 框線的寬度
     * @param hAlignment  水平對齊方式
     */
    protected void addCellWithFontStyle(PdfPTable table, String str, CNS11643 cnsFont, int fontSize, int fontStyle, float borderWidth, int hAlignment) {
        table.addCell(PdfPTableUtility.createPdfPCell(PdfPTableUtility.SPAN_UNDEFINED, PdfPTableUtility.SPAN_UNDEFINED, str, getFontSelector(cnsFont, fontSize, fontStyle), borderWidth, Rectangle.UNDEFINED, hAlignment, Element.ALIGN_UNDEFINED, PdfPTableUtility.LINESPACE_UNDEFINED));
    }

    /**
     * 建立 Cell - 基本型 最常用的
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param colspan     Column Span 若無填 <code>1</code>
     * @param rowspan     Row Span 若無填 <code>1</code>
     * @param str         欄位內容字串
     * @param fontSize    字體大小
     * @param borderWidth 框線的寬度
     * @param hAlignment  水平對齊方式
     * @param cellEvent   <code>PdfPCellEvent</code> 物件
     */
    protected void addCell(PdfPTable table, int colspan, int rowspan, String str, int fontSize, float borderWidth, int hAlignment, PdfPCellEvent cellEvent) {
        PdfPCell cell = PdfPTableUtility.createPdfPCell(colspan, rowspan, str, getFont(fontSize), borderWidth, Rectangle.UNDEFINED, hAlignment, Element.ALIGN_UNDEFINED, PdfPTableUtility.LINESPACE_UNDEFINED);
        cell.setCellEvent(cellEvent);
        table.addCell(cell);
    }

    /**
     * 建立 Cell
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param rowspan     Row Span 若無填 <code>1</code>
     * @param str         欄位內容字串
     * @param fontSize    字體大小
     * @param borderWidth 框線的寬度
     * @param hAlignment  水平對齊方式
     * @param cellEvent   <code>PdfPCellEvent</code> 物件
     */
    protected void addCell(PdfPTable table, int rowspan, String str, int fontSize, float borderWidth, int hAlignment, PdfPCellEvent cellEvent) {
        PdfPCell cell = PdfPTableUtility.createPdfPCell(PdfPTableUtility.SPAN_UNDEFINED, rowspan, str, getFont(fontSize), borderWidth, Rectangle.UNDEFINED, hAlignment, Element.ALIGN_UNDEFINED, PdfPTableUtility.LINESPACE_UNDEFINED);
        cell.setCellEvent(cellEvent);
        table.addCell(cell);
    }

    /**
     * 建立 Cell
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param str         欄位內容字串
     * @param fontSize    字體大小
     * @param borderWidth 框線的寬度
     * @param hAlignment  水平對齊方式
     * @param cellEvent   <code>PdfPCellEvent</code> 物件
     */
    protected void addCell(PdfPTable table, String str, int fontSize, float borderWidth, int hAlignment, PdfPCellEvent cellEvent) {
        PdfPCell cell = PdfPTableUtility.createPdfPCell(PdfPTableUtility.SPAN_UNDEFINED, PdfPTableUtility.SPAN_UNDEFINED, str, getFont(fontSize), borderWidth, Rectangle.UNDEFINED, hAlignment, Element.ALIGN_UNDEFINED, PdfPTableUtility.LINESPACE_UNDEFINED);
        cell.setCellEvent(cellEvent);
        table.addCell(cell);
    }

    /**
     * 建立 Cell - 基本型 最常用的
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param colspan     Column Span 若無填 <code>1</code>
     * @param rowspan     Row Span 若無填 <code>1</code>
     * @param str         欄位內容字串
     * @param font        字體
     * @param borderWidth 框線的寬度
     * @param hAlignment  水平對齊方式
     * @param cellEvent   <code>PdfPCellEvent</code> 物件
     */
    protected void addCell(PdfPTable table, int colspan, int rowspan, String str, Font font, float borderWidth, int hAlignment, PdfPCellEvent cellEvent) {
        PdfPCell cell = PdfPTableUtility.createPdfPCell(colspan, rowspan, str, font, borderWidth, Rectangle.UNDEFINED, hAlignment, Element.ALIGN_UNDEFINED, PdfPTableUtility.LINESPACE_UNDEFINED);
        cell.setCellEvent(cellEvent);
        table.addCell(cell);
    }

    /**
     * 建立 Cell
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param rowspan     Row Span 若無填 <code>1</code>
     * @param str         欄位內容字串
     * @param font        字體
     * @param borderWidth 框線的寬度
     * @param hAlignment  水平對齊方式
     * @param cellEvent   <code>PdfPCellEvent</code> 物件
     */
    protected void addCell(PdfPTable table, int rowspan, String str, Font font, float borderWidth, int hAlignment, PdfPCellEvent cellEvent) {
        PdfPCell cell = PdfPTableUtility.createPdfPCell(PdfPTableUtility.SPAN_UNDEFINED, rowspan, str, font, borderWidth, Rectangle.UNDEFINED, hAlignment, Element.ALIGN_UNDEFINED, PdfPTableUtility.LINESPACE_UNDEFINED);
        cell.setCellEvent(cellEvent);
        table.addCell(cell);
    }

    /**
     * 建立 Cell
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param str         欄位內容字串
     * @param font        字體
     * @param borderWidth 框線的寬度
     * @param hAlignment  水平對齊方式
     * @param cellEvent   <code>PdfPCellEvent</code> 物件
     */
    protected void addCell(PdfPTable table, String str, Font font, float borderWidth, int hAlignment, PdfPCellEvent cellEvent) {
        PdfPCell cell = PdfPTableUtility.createPdfPCell(PdfPTableUtility.SPAN_UNDEFINED, PdfPTableUtility.SPAN_UNDEFINED, str, font, borderWidth, Rectangle.UNDEFINED, hAlignment, Element.ALIGN_UNDEFINED, PdfPTableUtility.LINESPACE_UNDEFINED);
        cell.setCellEvent(cellEvent);
        table.addCell(cell);
    }

    /**
     * 建立 Cell - for 全字庫
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param colspan     Column Span 若無填 <code>1</code>
     * @param rowspan     Row Span 若無填 <code>1</code>
     * @param str         欄位內容字串
     * @param cnsFont     CNS11643.SUNG 或 CNS11643.KAI
     * @param fontSize    字體大小
     * @param borderWidth 框線的寬度
     * @param hAlignment  水平對齊方式
     * @param cellEvent   <code>PdfPCellEvent</code> 物件
     */
    protected void addCell(PdfPTable table, int colspan, int rowspan, String str, CNS11643 cnsFont, int fontSize, float borderWidth, int hAlignment, PdfPCellEvent cellEvent) {
        PdfPCell cell = PdfPTableUtility.createPdfPCell(colspan, rowspan, str, getFontSelector(cnsFont, fontSize), borderWidth, Rectangle.UNDEFINED, hAlignment, Element.ALIGN_UNDEFINED, PdfPTableUtility.LINESPACE_UNDEFINED);
        cell.setCellEvent(cellEvent);
        table.addCell(cell);
    }

    /**
     * 建立 Cell - for 全字庫
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param rowspan     Row Span 若無填 <code>1</code>
     * @param str         欄位內容字串
     * @param cnsFont     CNS11643.SUNG 或 CNS11643.KAI
     * @param fontSize    字體大小
     * @param borderWidth 框線的寬度
     * @param hAlignment  水平對齊方式
     * @param cellEvent   <code>PdfPCellEvent</code> 物件
     */
    protected void addCell(PdfPTable table, int rowspan, String str, CNS11643 cnsFont, int fontSize, float borderWidth, int hAlignment, PdfPCellEvent cellEvent) {
        PdfPCell cell = PdfPTableUtility.createPdfPCell(PdfPTableUtility.SPAN_UNDEFINED, rowspan, str, getFontSelector(cnsFont, fontSize), borderWidth, Rectangle.UNDEFINED, hAlignment, Element.ALIGN_UNDEFINED, PdfPTableUtility.LINESPACE_UNDEFINED);
        cell.setCellEvent(cellEvent);
        table.addCell(cell);
    }

    /**
     * 建立 Cell - for 全字庫
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param str         欄位內容字串
     * @param cnsFont     CNS11643.SUNG 或 CNS11643.KAI
     * @param fontSize    字體大小
     * @param borderWidth 框線的寬度
     * @param hAlignment  水平對齊方式
     * @param cellEvent   <code>PdfPCellEvent</code> 物件
     */
    protected void addCell(PdfPTable table, String str, CNS11643 cnsFont, int fontSize, float borderWidth, int hAlignment, PdfPCellEvent cellEvent) {
        PdfPCell cell = PdfPTableUtility.createPdfPCell(PdfPTableUtility.SPAN_UNDEFINED, PdfPTableUtility.SPAN_UNDEFINED, str, getFontSelector(cnsFont, fontSize), borderWidth, Rectangle.UNDEFINED, hAlignment, Element.ALIGN_UNDEFINED, PdfPTableUtility.LINESPACE_UNDEFINED);
        cell.setCellEvent(cellEvent);
        table.addCell(cell);
    }

    /**
     * 建立 Cell
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param colspan     Column Span 若無填 <code>1</code>
     * @param rowspan     Row Span 若無填 <code>1</code>
     * @param str         欄位內容字串
     * @param fontSize    字體大小
     * @param fontStyle   字體樣式
     * @param borderWidth 框線的寬度
     * @param hAlignment  水平對齊方式
     * @param cellEvent   <code>PdfPCellEvent</code> 物件
     */
    protected void addCellWithFontStyle(PdfPTable table, int colspan, int rowspan, String str, int fontSize, int fontStyle, float borderWidth, int hAlignment, PdfPCellEvent cellEvent) {
        PdfPCell cell = PdfPTableUtility.createPdfPCell(colspan, rowspan, str, getFont(fontSize, fontStyle), borderWidth, Rectangle.UNDEFINED, hAlignment, Element.ALIGN_UNDEFINED, PdfPTableUtility.LINESPACE_UNDEFINED);
        cell.setCellEvent(cellEvent);
        table.addCell(cell);
    }

    /**
     * 建立 Cell
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param rowspan     Row Span 若無填 <code>1</code>
     * @param str         欄位內容字串
     * @param fontSize    字體大小
     * @param fontStyle   字體樣式
     * @param borderWidth 框線的寬度
     * @param hAlignment  水平對齊方式
     * @param cellEvent   <code>PdfPCellEvent</code> 物件
     */
    protected void addCellWithFontStyle(PdfPTable table, int rowspan, String str, int fontSize, int fontStyle, float borderWidth, int hAlignment, PdfPCellEvent cellEvent) {
        PdfPCell cell = PdfPTableUtility.createPdfPCell(PdfPTableUtility.SPAN_UNDEFINED, rowspan, str, getFont(fontSize, fontStyle), borderWidth, Rectangle.UNDEFINED, hAlignment, Element.ALIGN_UNDEFINED, PdfPTableUtility.LINESPACE_UNDEFINED);
        cell.setCellEvent(cellEvent);
        table.addCell(cell);
    }

    /**
     * 建立 Cell
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param str         欄位內容字串
     * @param fontSize    字體大小
     * @param fontStyle   字體樣式
     * @param borderWidth 框線的寬度
     * @param hAlignment  水平對齊方式
     * @param cellEvent   <code>PdfPCellEvent</code> 物件
     */
    protected void addCellWithFontStyle(PdfPTable table, String str, int fontSize, int fontStyle, float borderWidth, int hAlignment, PdfPCellEvent cellEvent) {
        PdfPCell cell = PdfPTableUtility.createPdfPCell(PdfPTableUtility.SPAN_UNDEFINED, PdfPTableUtility.SPAN_UNDEFINED, str, getFont(fontSize, fontStyle), borderWidth, Rectangle.UNDEFINED, hAlignment, Element.ALIGN_UNDEFINED, PdfPTableUtility.LINESPACE_UNDEFINED);
        cell.setCellEvent(cellEvent);
        table.addCell(cell);
    }

    /**
     * 建立 Cell - for 全字庫
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param colspan     Column Span 若無填 <code>1</code>
     * @param rowspan     Row Span 若無填 <code>1</code>
     * @param str         欄位內容字串
     * @param cnsFont     CNS11643.SUNG 或 CNS11643.KAI
     * @param fontSize    字體大小
     * @param fontStyle   字體樣式
     * @param borderWidth 框線的寬度
     * @param hAlignment  水平對齊方式
     * @param cellEvent   <code>PdfPCellEvent</code> 物件
     */
    protected void addCellWithFontStyle(PdfPTable table, int colspan, int rowspan, String str, CNS11643 cnsFont, int fontSize, int fontStyle, float borderWidth, int hAlignment, PdfPCellEvent cellEvent) {
        PdfPCell cell = PdfPTableUtility.createPdfPCell(colspan, rowspan, str, getFontSelector(cnsFont, fontSize, fontStyle), borderWidth, Rectangle.UNDEFINED, hAlignment, Element.ALIGN_UNDEFINED, PdfPTableUtility.LINESPACE_UNDEFINED);
        cell.setCellEvent(cellEvent);
        table.addCell(cell);
    }

    /**
     * 建立 Cell - for 全字庫
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param rowspan     Row Span 若無填 <code>1</code>
     * @param str         欄位內容字串
     * @param cnsFont     CNS11643.SUNG 或 CNS11643.KAI
     * @param fontSize    字體大小
     * @param fontStyle   字體樣式
     * @param borderWidth 框線的寬度
     * @param hAlignment  水平對齊方式
     * @param cellEvent   <code>PdfPCellEvent</code> 物件
     */
    protected void addCellWithFontStyle(PdfPTable table, int rowspan, String str, CNS11643 cnsFont, int fontSize, int fontStyle, float borderWidth, int hAlignment, PdfPCellEvent cellEvent) {
        PdfPCell cell = PdfPTableUtility.createPdfPCell(PdfPTableUtility.SPAN_UNDEFINED, rowspan, str, getFontSelector(cnsFont, fontSize, fontStyle), borderWidth, Rectangle.UNDEFINED, hAlignment, Element.ALIGN_UNDEFINED, PdfPTableUtility.LINESPACE_UNDEFINED);
        cell.setCellEvent(cellEvent);
        table.addCell(cell);
    }

    /**
     * 建立 Cell - for 全字庫
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param str         欄位內容字串
     * @param cnsFont     CNS11643.SUNG 或 CNS11643.KAI
     * @param fontSize    字體大小
     * @param fontStyle   字體樣式
     * @param borderWidth 框線的寬度
     * @param hAlignment  水平對齊方式
     * @param cellEvent   <code>PdfPCellEvent</code> 物件
     */
    protected void addCellWithFontStyle(PdfPTable table, String str, CNS11643 cnsFont, int fontSize, int fontStyle, float borderWidth, int hAlignment, PdfPCellEvent cellEvent) {
        PdfPCell cell = PdfPTableUtility.createPdfPCell(PdfPTableUtility.SPAN_UNDEFINED, PdfPTableUtility.SPAN_UNDEFINED, str, getFontSelector(cnsFont, fontSize, fontStyle), borderWidth, Rectangle.UNDEFINED, hAlignment, Element.ALIGN_UNDEFINED, PdfPTableUtility.LINESPACE_UNDEFINED);
        cell.setCellEvent(cellEvent);
        table.addCell(cell);
    }

    /**
     * 建立 Cell
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param colspan     Column Span 若無填 <code>1</code>
     * @param rowspan     Row Span 若無填 <code>1</code>
     * @param str         欄位內容字串
     * @param fontSize    字體大小
     * @param borderWidth 框線的寬度
     * @param borderType  框線的樣式
     * @param hAlignment  水平對齊方式
     */
    protected void addCellAssignBorderType(PdfPTable table, int colspan, int rowspan, String str, int fontSize, float borderWidth, int borderType, int hAlignment) {
        table.addCell(PdfPTableUtility.createPdfPCell(colspan, rowspan, str, getFont(fontSize), borderWidth, borderType, hAlignment, Element.ALIGN_UNDEFINED, PdfPTableUtility.LINESPACE_UNDEFINED));
    }

    /**
     * 建立 Cell
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param rowspan     Row Span 若無填 <code>1</code>
     * @param str         欄位內容字串
     * @param fontSize    字體大小
     * @param borderWidth 框線的寬度
     * @param borderType  框線的樣式
     * @param hAlignment  水平對齊方式
     */
    protected void addCellAssignBorderType(PdfPTable table, int rowspan, String str, int fontSize, float borderWidth, int borderType, int hAlignment) {
        table.addCell(PdfPTableUtility.createPdfPCell(PdfPTableUtility.SPAN_UNDEFINED, rowspan, str, getFont(fontSize), borderWidth, borderType, hAlignment, Element.ALIGN_UNDEFINED, PdfPTableUtility.LINESPACE_UNDEFINED));
    }

    /**
     * 建立 Cell
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param str         欄位內容字串
     * @param fontSize    字體大小
     * @param borderWidth 框線的寬度
     * @param borderType  框線的樣式
     * @param hAlignment  水平對齊方式
     */
    protected void addCellAssignBorderType(PdfPTable table, String str, int fontSize, float borderWidth, int borderType, int hAlignment) {
        table.addCell(PdfPTableUtility.createPdfPCell(PdfPTableUtility.SPAN_UNDEFINED, PdfPTableUtility.SPAN_UNDEFINED, str, getFont(fontSize), borderWidth, borderType, hAlignment, Element.ALIGN_UNDEFINED, PdfPTableUtility.LINESPACE_UNDEFINED));
    }

    /**
     * 建立 Cell
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param colspan     Column Span 若無填 <code>1</code>
     * @param rowspan     Row Span 若無填 <code>1</code>
     * @param str         欄位內容字串
     * @param font        字體
     * @param borderWidth 框線的寬度
     * @param borderType  框線的樣式
     * @param hAlignment  水平對齊方式
     */
    protected void addCellAssignBorderType(PdfPTable table, int colspan, int rowspan, String str, Font font, float borderWidth, int borderType, int hAlignment) {
        table.addCell(PdfPTableUtility.createPdfPCell(colspan, rowspan, str, font, borderWidth, borderType, hAlignment, Element.ALIGN_UNDEFINED, PdfPTableUtility.LINESPACE_UNDEFINED));
    }

    /**
     * 建立 Cell
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param rowspan     Row Span 若無填 <code>1</code>
     * @param str         欄位內容字串
     * @param font        字體
     * @param borderWidth 框線的寬度
     * @param borderType  框線的樣式
     * @param hAlignment  水平對齊方式
     */
    protected void addCellAssignBorderType(PdfPTable table, int rowspan, String str, Font font, float borderWidth, int borderType, int hAlignment) {
        table.addCell(PdfPTableUtility.createPdfPCell(PdfPTableUtility.SPAN_UNDEFINED, rowspan, str, font, borderWidth, borderType, hAlignment, Element.ALIGN_UNDEFINED, PdfPTableUtility.LINESPACE_UNDEFINED));
    }

    /**
     * 建立 Cell
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param str         欄位內容字串
     * @param font        字體
     * @param borderWidth 框線的寬度
     * @param borderType  框線的樣式
     * @param hAlignment  水平對齊方式
     */
    protected void addCellAssignBorderType(PdfPTable table, String str, Font font, float borderWidth, int borderType, int hAlignment) {
        table.addCell(PdfPTableUtility.createPdfPCell(PdfPTableUtility.SPAN_UNDEFINED, PdfPTableUtility.SPAN_UNDEFINED, str, font, borderWidth, borderType, hAlignment, Element.ALIGN_UNDEFINED, PdfPTableUtility.LINESPACE_UNDEFINED));
    }

    /**
     * 建立 Cell - for 全字庫
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param colspan     Column Span 若無填 <code>1</code>
     * @param rowspan     Row Span 若無填 <code>1</code>
     * @param str         欄位內容字串
     * @param cnsFont     CNS11643.SUNG 或 CNS11643.KAI
     * @param fontSize    字體大小
     * @param borderWidth 框線的寬度
     * @param borderType  框線的樣式
     * @param hAlignment  水平對齊方式
     */
    protected void addCellAssignBorderType(PdfPTable table, int colspan, int rowspan, String str, CNS11643 cnsFont, int fontSize, float borderWidth, int borderType, int hAlignment) {
        table.addCell(PdfPTableUtility.createPdfPCell(colspan, rowspan, str, getFontSelector(cnsFont, fontSize), borderWidth, borderType, hAlignment, Element.ALIGN_UNDEFINED, PdfPTableUtility.LINESPACE_UNDEFINED));
    }

    /**
     * 建立 Cell - for 全字庫
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param rowspan     Row Span 若無填 <code>1</code>
     * @param str         欄位內容字串
     * @param cnsFont     CNS11643.SUNG 或 CNS11643.KAI
     * @param fontSize    字體大小
     * @param borderWidth 框線的寬度
     * @param borderType  框線的樣式
     * @param hAlignment  水平對齊方式
     */
    protected void addCellAssignBorderType(PdfPTable table, int rowspan, String str, CNS11643 cnsFont, int fontSize, float borderWidth, int borderType, int hAlignment) {
        table.addCell(PdfPTableUtility.createPdfPCell(PdfPTableUtility.SPAN_UNDEFINED, rowspan, str, getFontSelector(cnsFont, fontSize), borderWidth, borderType, hAlignment, Element.ALIGN_UNDEFINED, PdfPTableUtility.LINESPACE_UNDEFINED));
    }

    /**
     * 建立 Cell - for 全字庫
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param str         欄位內容字串
     * @param cnsFont     CNS11643.SUNG 或 CNS11643.KAI
     * @param fontSize    字體大小
     * @param borderWidth 框線的寬度
     * @param borderType  框線的樣式
     * @param hAlignment  水平對齊方式
     */
    protected void addCellAssignBorderType(PdfPTable table, String str, CNS11643 cnsFont, int fontSize, float borderWidth, int borderType, int hAlignment) {
        table.addCell(PdfPTableUtility.createPdfPCell(PdfPTableUtility.SPAN_UNDEFINED, PdfPTableUtility.SPAN_UNDEFINED, str, getFontSelector(cnsFont, fontSize), borderWidth, borderType, hAlignment, Element.ALIGN_UNDEFINED, PdfPTableUtility.LINESPACE_UNDEFINED));
    }

    /**
     * 建立 Cell
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param colspan     Column Span 若無填 <code>1</code>
     * @param rowspan     Row Span 若無填 <code>1</code>
     * @param str         欄位內容字串
     * @param fontSize    字體大小
     * @param fontStyle   字體樣式
     * @param borderWidth 框線的寬度
     * @param borderType  框線的樣式
     * @param hAlignment  水平對齊方式
     */
    protected void addCellAssignBorderTypeWithFontStyle(PdfPTable table, int colspan, int rowspan, String str, int fontSize, int fontStyle, float borderWidth, int borderType, int hAlignment) {
        table.addCell(PdfPTableUtility.createPdfPCell(colspan, rowspan, str, getFont(fontSize, fontStyle), borderWidth, borderType, hAlignment, Element.ALIGN_UNDEFINED, PdfPTableUtility.LINESPACE_UNDEFINED));
    }

    /**
     * 建立 Cell
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param rowspan     Row Span 若無填 <code>1</code>
     * @param str         欄位內容字串
     * @param fontSize    字體大小
     * @param fontStyle   字體樣式
     * @param borderWidth 框線的寬度
     * @param borderType  框線的樣式
     * @param hAlignment  水平對齊方式
     */
    protected void addCellAssignBorderTypeWithFontStyle(PdfPTable table, int rowspan, String str, int fontSize, int fontStyle, float borderWidth, int borderType, int hAlignment) {
        table.addCell(PdfPTableUtility.createPdfPCell(PdfPTableUtility.SPAN_UNDEFINED, rowspan, str, getFont(fontSize, fontStyle), borderWidth, borderType, hAlignment, Element.ALIGN_UNDEFINED, PdfPTableUtility.LINESPACE_UNDEFINED));
    }

    /**
     * 建立 Cell
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param str         欄位內容字串
     * @param fontSize    字體大小
     * @param fontStyle   字體樣式
     * @param borderWidth 框線的寬度
     * @param borderType  框線的樣式
     * @param hAlignment  水平對齊方式
     */
    protected void addCellAssignBorderTypeWithFontStyle(PdfPTable table, String str, int fontSize, int fontStyle, float borderWidth, int borderType, int hAlignment) {
        table.addCell(PdfPTableUtility.createPdfPCell(PdfPTableUtility.SPAN_UNDEFINED, PdfPTableUtility.SPAN_UNDEFINED, str, getFont(fontSize, fontStyle), borderWidth, borderType, hAlignment, Element.ALIGN_UNDEFINED, PdfPTableUtility.LINESPACE_UNDEFINED));
    }

    /**
     * 建立 Cell - for 全字庫
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param colspan     Column Span 若無填 <code>1</code>
     * @param rowspan     Row Span 若無填 <code>1</code>
     * @param str         欄位內容字串
     * @param cnsFont     CNS11643.SUNG 或 CNS11643.KAI
     * @param fontSize    字體大小
     * @param fontStyle   字體樣式
     * @param borderWidth 框線的寬度
     * @param borderType  框線的樣式
     * @param hAlignment  水平對齊方式
     */
    protected void addCellAssignBorderTypeWithFontStyle(PdfPTable table, int colspan, int rowspan, String str, CNS11643 cnsFont, int fontSize, int fontStyle, float borderWidth, int borderType, int hAlignment) {
        table.addCell(PdfPTableUtility.createPdfPCell(colspan, rowspan, str, getFontSelector(cnsFont, fontSize, fontStyle), borderWidth, borderType, hAlignment, Element.ALIGN_UNDEFINED, PdfPTableUtility.LINESPACE_UNDEFINED));
    }

    /**
     * 建立 Cell - for 全字庫
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param rowspan     Row Span 若無填 <code>1</code>
     * @param str         欄位內容字串
     * @param cnsFont     CNS11643.SUNG 或 CNS11643.KAI
     * @param fontSize    字體大小
     * @param fontStyle   字體樣式
     * @param borderWidth 框線的寬度
     * @param borderType  框線的樣式
     * @param hAlignment  水平對齊方式
     */
    protected void addCellAssignBorderTypeWithFontStyle(PdfPTable table, int rowspan, String str, CNS11643 cnsFont, int fontSize, int fontStyle, float borderWidth, int borderType, int hAlignment) {
        table.addCell(PdfPTableUtility.createPdfPCell(PdfPTableUtility.SPAN_UNDEFINED, rowspan, str, getFontSelector(cnsFont, fontSize, fontStyle), borderWidth, borderType, hAlignment, Element.ALIGN_UNDEFINED, PdfPTableUtility.LINESPACE_UNDEFINED));
    }

    /**
     * 建立 Cell - for 全字庫
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param str         欄位內容字串
     * @param cnsFont     CNS11643.SUNG 或 CNS11643.KAI
     * @param fontSize    字體大小
     * @param fontStyle   字體樣式
     * @param borderWidth 框線的寬度
     * @param borderType  框線的樣式
     * @param hAlignment  水平對齊方式
     */
    protected void addCellAssignBorderTypeWithFontStyle(PdfPTable table, String str, CNS11643 cnsFont, int fontSize, int fontStyle, float borderWidth, int borderType, int hAlignment) {
        table.addCell(PdfPTableUtility.createPdfPCell(PdfPTableUtility.SPAN_UNDEFINED, PdfPTableUtility.SPAN_UNDEFINED, str, getFontSelector(cnsFont, fontSize, fontStyle), borderWidth, borderType, hAlignment, Element.ALIGN_UNDEFINED, PdfPTableUtility.LINESPACE_UNDEFINED));
    }

    /**
     * 建立 Cell
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param colspan     Column Span 若無填 <code>1</code>
     * @param rowspan     Row Span 若無填 <code>1</code>
     * @param str         欄位內容字串
     * @param fontSize    字體大小
     * @param borderWidth 框線的寬度
     * @param borderType  框線的樣式
     * @param hAlignment  水平對齊方式
     * @param cellEvent   <code>PdfPCellEvent</code> 物件
     */
    protected void addCellAssignBorderType(PdfPTable table, int colspan, int rowspan, String str, int fontSize, float borderWidth, int borderType, int hAlignment, PdfPCellEvent cellEvent) {
        PdfPCell cell = PdfPTableUtility.createPdfPCell(colspan, rowspan, str, getFont(fontSize), borderWidth, borderType, hAlignment, Element.ALIGN_UNDEFINED, PdfPTableUtility.LINESPACE_UNDEFINED);
        cell.setCellEvent(cellEvent);
        table.addCell(cell);
    }

    /**
     * 建立 Cell
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param rowspan     Row Span 若無填 <code>1</code>
     * @param str         欄位內容字串
     * @param fontSize    字體大小
     * @param borderWidth 框線的寬度
     * @param borderType  框線的樣式
     * @param hAlignment  水平對齊方式
     * @param cellEvent   <code>PdfPCellEvent</code> 物件
     */
    protected void addCellAssignBorderType(PdfPTable table, int rowspan, String str, int fontSize, float borderWidth, int borderType, int hAlignment, PdfPCellEvent cellEvent) {
        PdfPCell cell = PdfPTableUtility.createPdfPCell(PdfPTableUtility.SPAN_UNDEFINED, rowspan, str, getFont(fontSize), borderWidth, borderType, hAlignment, Element.ALIGN_UNDEFINED, PdfPTableUtility.LINESPACE_UNDEFINED);
        cell.setCellEvent(cellEvent);
        table.addCell(cell);
    }

    /**
     * 建立 Cell
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param str         欄位內容字串
     * @param fontSize    字體大小
     * @param borderWidth 框線的寬度
     * @param borderType  框線的樣式
     * @param hAlignment  水平對齊方式
     * @param cellEvent   <code>PdfPCellEvent</code> 物件
     */
    protected void addCellAssignBorderType(PdfPTable table, String str, int fontSize, float borderWidth, int borderType, int hAlignment, PdfPCellEvent cellEvent) {
        PdfPCell cell = PdfPTableUtility.createPdfPCell(PdfPTableUtility.SPAN_UNDEFINED, PdfPTableUtility.SPAN_UNDEFINED, str, getFont(fontSize), borderWidth, borderType, hAlignment, Element.ALIGN_UNDEFINED, PdfPTableUtility.LINESPACE_UNDEFINED);
        cell.setCellEvent(cellEvent);
        table.addCell(cell);
    }

    /**
     * 建立 Cell
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param colspan     Column Span 若無填 <code>1</code>
     * @param rowspan     Row Span 若無填 <code>1</code>
     * @param str         欄位內容字串
     * @param font        字體
     * @param borderWidth 框線的寬度
     * @param borderType  框線的樣式
     * @param hAlignment  水平對齊方式
     * @param cellEvent   <code>PdfPCellEvent</code> 物件
     */
    protected void addCellAssignBorderType(PdfPTable table, int colspan, int rowspan, String str, Font font, float borderWidth, int borderType, int hAlignment, PdfPCellEvent cellEvent) {
        PdfPCell cell = PdfPTableUtility.createPdfPCell(colspan, rowspan, str, font, borderWidth, borderType, hAlignment, Element.ALIGN_UNDEFINED, PdfPTableUtility.LINESPACE_UNDEFINED);
        cell.setCellEvent(cellEvent);
        table.addCell(cell);
    }

    /**
     * 建立 Cell
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param rowspan     Row Span 若無填 <code>1</code>
     * @param str         欄位內容字串
     * @param font        字體
     * @param borderWidth 框線的寬度
     * @param borderType  框線的樣式
     * @param hAlignment  水平對齊方式
     * @param cellEvent   <code>PdfPCellEvent</code> 物件
     */
    protected void addCellAssignBorderType(PdfPTable table, int rowspan, String str, Font font, float borderWidth, int borderType, int hAlignment, PdfPCellEvent cellEvent) {
        PdfPCell cell = PdfPTableUtility.createPdfPCell(PdfPTableUtility.SPAN_UNDEFINED, rowspan, str, font, borderWidth, borderType, hAlignment, Element.ALIGN_UNDEFINED, PdfPTableUtility.LINESPACE_UNDEFINED);
        cell.setCellEvent(cellEvent);
        table.addCell(cell);
    }

    /**
     * 建立 Cell
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param str         欄位內容字串
     * @param font        字體
     * @param borderWidth 框線的寬度
     * @param borderType  框線的樣式
     * @param hAlignment  水平對齊方式
     * @param cellEvent   <code>PdfPCellEvent</code> 物件
     */
    protected void addCellAssignBorderType(PdfPTable table, String str, Font font, float borderWidth, int borderType, int hAlignment, PdfPCellEvent cellEvent) {
        PdfPCell cell = PdfPTableUtility.createPdfPCell(PdfPTableUtility.SPAN_UNDEFINED, PdfPTableUtility.SPAN_UNDEFINED, str, font, borderWidth, borderType, hAlignment, Element.ALIGN_UNDEFINED, PdfPTableUtility.LINESPACE_UNDEFINED);
        cell.setCellEvent(cellEvent);
        table.addCell(cell);
    }

    /**
     * 建立 Cell - for 全字庫
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param colspan     Column Span 若無填 <code>1</code>
     * @param rowspan     Row Span 若無填 <code>1</code>
     * @param str         欄位內容字串
     * @param cnsFont     CNS11643.SUNG 或 CNS11643.KAI
     * @param fontSize    字體大小
     * @param borderWidth 框線的寬度
     * @param borderType  框線的樣式
     * @param hAlignment  水平對齊方式
     * @param cellEvent   <code>PdfPCellEvent</code> 物件
     */
    protected void addCellAssignBorderType(PdfPTable table, int colspan, int rowspan, String str, CNS11643 cnsFont, int fontSize, float borderWidth, int borderType, int hAlignment, PdfPCellEvent cellEvent) {
        PdfPCell cell = PdfPTableUtility.createPdfPCell(colspan, rowspan, str, getFontSelector(cnsFont, fontSize), borderWidth, borderType, hAlignment, Element.ALIGN_UNDEFINED, PdfPTableUtility.LINESPACE_UNDEFINED);
        cell.setCellEvent(cellEvent);
        table.addCell(cell);
    }

    /**
     * 建立 Cell - for 全字庫
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param rowspan     Row Span 若無填 <code>1</code>
     * @param str         欄位內容字串
     * @param cnsFont     CNS11643.SUNG 或 CNS11643.KAI
     * @param fontSize    字體大小
     * @param borderWidth 框線的寬度
     * @param borderType  框線的樣式
     * @param hAlignment  水平對齊方式
     * @param cellEvent   <code>PdfPCellEvent</code> 物件
     */
    protected void addCellAssignBorderType(PdfPTable table, int rowspan, String str, CNS11643 cnsFont, int fontSize, float borderWidth, int borderType, int hAlignment, PdfPCellEvent cellEvent) {
        PdfPCell cell = PdfPTableUtility.createPdfPCell(PdfPTableUtility.SPAN_UNDEFINED, rowspan, str, getFontSelector(cnsFont, fontSize), borderWidth, borderType, hAlignment, Element.ALIGN_UNDEFINED, PdfPTableUtility.LINESPACE_UNDEFINED);
        cell.setCellEvent(cellEvent);
        table.addCell(cell);
    }

    /**
     * 建立 Cell - for 全字庫
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param str         欄位內容字串
     * @param cnsFont     CNS11643.SUNG 或 CNS11643.KAI
     * @param fontSize    字體大小
     * @param borderWidth 框線的寬度
     * @param borderType  框線的樣式
     * @param hAlignment  水平對齊方式
     * @param cellEvent   <code>PdfPCellEvent</code> 物件
     */
    protected void addCellAssignBorderType(PdfPTable table, String str, CNS11643 cnsFont, int fontSize, float borderWidth, int borderType, int hAlignment, PdfPCellEvent cellEvent) {
        PdfPCell cell = PdfPTableUtility.createPdfPCell(PdfPTableUtility.SPAN_UNDEFINED, PdfPTableUtility.SPAN_UNDEFINED, str, getFontSelector(cnsFont, fontSize), borderWidth, borderType, hAlignment, Element.ALIGN_UNDEFINED, PdfPTableUtility.LINESPACE_UNDEFINED);
        cell.setCellEvent(cellEvent);
        table.addCell(cell);
    }

    /**
     * 建立 Cell
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param colspan     Column Span 若無填 <code>1</code>
     * @param rowspan     Row Span 若無填 <code>1</code>
     * @param str         欄位內容字串
     * @param fontSize    字體大小
     * @param fontStyle   字體樣式
     * @param borderWidth 框線的寬度
     * @param borderType  框線的樣式
     * @param hAlignment  水平對齊方式
     * @param cellEvent   <code>PdfPCellEvent</code> 物件
     */
    protected void addCellAssignBorderTypeWithFontStyle(PdfPTable table, int colspan, int rowspan, String str, int fontSize, int fontStyle, float borderWidth, int borderType, int hAlignment, PdfPCellEvent cellEvent) {
        PdfPCell cell = PdfPTableUtility.createPdfPCell(colspan, rowspan, str, getFont(fontSize, fontStyle), borderWidth, borderType, hAlignment, Element.ALIGN_UNDEFINED, PdfPTableUtility.LINESPACE_UNDEFINED);
        cell.setCellEvent(cellEvent);
        table.addCell(cell);
    }

    /**
     * 建立 Cell
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param rowspan     Row Span 若無填 <code>1</code>
     * @param str         欄位內容字串
     * @param fontSize    字體大小
     * @param fontStyle   字體樣式
     * @param borderWidth 框線的寬度
     * @param borderType  框線的樣式
     * @param hAlignment  水平對齊方式
     * @param cellEvent   <code>PdfPCellEvent</code> 物件
     */
    protected void addCellAssignBorderTypeWithFontStyle(PdfPTable table, int rowspan, String str, int fontSize, int fontStyle, float borderWidth, int borderType, int hAlignment, PdfPCellEvent cellEvent) {
        PdfPCell cell = PdfPTableUtility.createPdfPCell(PdfPTableUtility.SPAN_UNDEFINED, rowspan, str, getFont(fontSize, fontStyle), borderWidth, borderType, hAlignment, Element.ALIGN_UNDEFINED, PdfPTableUtility.LINESPACE_UNDEFINED);
        cell.setCellEvent(cellEvent);
        table.addCell(cell);
    }

    /**
     * 建立 Cell
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param str         欄位內容字串
     * @param fontSize    字體大小
     * @param fontStyle   字體樣式
     * @param borderWidth 框線的寬度
     * @param borderType  框線的樣式
     * @param hAlignment  水平對齊方式
     * @param cellEvent   <code>PdfPCellEvent</code> 物件
     */
    protected void addCellAssignBorderTypeWithFontStyle(PdfPTable table, String str, int fontSize, int fontStyle, float borderWidth, int borderType, int hAlignment, PdfPCellEvent cellEvent) {
        PdfPCell cell = PdfPTableUtility.createPdfPCell(PdfPTableUtility.SPAN_UNDEFINED, PdfPTableUtility.SPAN_UNDEFINED, str, getFont(fontSize, fontStyle), borderWidth, borderType, hAlignment, Element.ALIGN_UNDEFINED, PdfPTableUtility.LINESPACE_UNDEFINED);
        cell.setCellEvent(cellEvent);
        table.addCell(cell);
    }

    /**
     * 建立 Cell - for 全字庫
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param colspan     Column Span 若無填 <code>1</code>
     * @param rowspan     Row Span 若無填 <code>1</code>
     * @param str         欄位內容字串
     * @param cnsFont     CNS11643.SUNG 或 CNS11643.KAI
     * @param fontSize    字體大小
     * @param fontStyle   字體樣式
     * @param borderWidth 框線的寬度
     * @param borderType  框線的樣式
     * @param hAlignment  水平對齊方式
     * @param cellEvent   <code>PdfPCellEvent</code> 物件
     */
    protected void addCellAssignBorderTypeWithFontStyle(PdfPTable table, int colspan, int rowspan, String str, CNS11643 cnsFont, int fontSize, int fontStyle, float borderWidth, int borderType, int hAlignment, PdfPCellEvent cellEvent) {
        PdfPCell cell = PdfPTableUtility.createPdfPCell(colspan, rowspan, str, getFontSelector(cnsFont, fontSize, fontStyle), borderWidth, borderType, hAlignment, Element.ALIGN_UNDEFINED, PdfPTableUtility.LINESPACE_UNDEFINED);
        cell.setCellEvent(cellEvent);
        table.addCell(cell);
    }

    /**
     * 建立 Cell - for 全字庫
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param rowspan     Row Span 若無填 <code>1</code>
     * @param str         欄位內容字串
     * @param cnsFont     CNS11643.SUNG 或 CNS11643.KAI
     * @param fontSize    字體大小
     * @param fontStyle   字體樣式
     * @param borderWidth 框線的寬度
     * @param borderType  框線的樣式
     * @param hAlignment  水平對齊方式
     * @param cellEvent   <code>PdfPCellEvent</code> 物件
     */
    protected void addCellAssignBorderTypeWithFontStyle(PdfPTable table, int rowspan, String str, CNS11643 cnsFont, int fontSize, int fontStyle, float borderWidth, int borderType, int hAlignment, PdfPCellEvent cellEvent) {
        PdfPCell cell = PdfPTableUtility.createPdfPCell(PdfPTableUtility.SPAN_UNDEFINED, rowspan, str, getFontSelector(cnsFont, fontSize, fontStyle), borderWidth, borderType, hAlignment, Element.ALIGN_UNDEFINED, PdfPTableUtility.LINESPACE_UNDEFINED);
        cell.setCellEvent(cellEvent);
        table.addCell(cell);
    }

    /**
     * 建立 Cell - for 全字庫
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param str         欄位內容字串
     * @param cnsFont     CNS11643.SUNG 或 CNS11643.KAI
     * @param fontSize    字體大小
     * @param fontStyle   字體樣式
     * @param borderWidth 框線的寬度
     * @param borderType  框線的樣式
     * @param hAlignment  水平對齊方式
     * @param cellEvent   <code>PdfPCellEvent</code> 物件
     */
    protected void addCellAssignBorderTypeWithFontStyle(PdfPTable table, String str, CNS11643 cnsFont, int fontSize, int fontStyle, float borderWidth, int borderType, int hAlignment, PdfPCellEvent cellEvent) {
        PdfPCell cell = PdfPTableUtility.createPdfPCell(PdfPTableUtility.SPAN_UNDEFINED, PdfPTableUtility.SPAN_UNDEFINED, str, getFontSelector(cnsFont, fontSize, fontStyle), borderWidth, borderType, hAlignment, Element.ALIGN_UNDEFINED, PdfPTableUtility.LINESPACE_UNDEFINED);
        cell.setCellEvent(cellEvent);
        table.addCell(cell);
    }

    /**
     * 建立 Cell
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param colspan     Column Span 若無填 <code>1</code>
     * @param rowspan     Row Span 若無填 <code>1</code>
     * @param str         欄位內容字串
     * @param fontSize    字體大小
     * @param borderWidth 框線的寬度
     * @param hAlignment  水平對齊方式
     * @param lineSpace   行距
     */
    protected void addCellAssignLinespace(PdfPTable table, int colspan, int rowspan, String str, int fontSize, float borderWidth, int hAlignment, int lineSpace) {
        table.addCell(PdfPTableUtility.createPdfPCell(colspan, rowspan, str, getFont(fontSize), borderWidth, Rectangle.UNDEFINED, hAlignment, Element.ALIGN_UNDEFINED, lineSpace));
    }

    /**
     * 建立 Cell
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param rowspan     Row Span 若無填 <code>1</code>
     * @param str         欄位內容字串
     * @param fontSize    字體大小
     * @param borderWidth 框線的寬度
     * @param hAlignment  水平對齊方式
     * @param lineSpace   行距
     */
    protected void addCellAssignLinespace(PdfPTable table, int rowspan, String str, int fontSize, float borderWidth, int hAlignment, int lineSpace) {
        table.addCell(PdfPTableUtility.createPdfPCell(PdfPTableUtility.SPAN_UNDEFINED, rowspan, str, getFont(fontSize), borderWidth, Rectangle.UNDEFINED, hAlignment, Element.ALIGN_UNDEFINED, lineSpace));
    }

    /**
     * 建立 Cell
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param str         欄位內容字串
     * @param fontSize    字體大小
     * @param borderWidth 框線的寬度
     * @param hAlignment  水平對齊方式
     * @param lineSpace   行距
     */
    protected void addCellAssignLinespace(PdfPTable table, String str, int fontSize, float borderWidth, int hAlignment, int lineSpace) {
        table.addCell(PdfPTableUtility.createPdfPCell(PdfPTableUtility.SPAN_UNDEFINED, PdfPTableUtility.SPAN_UNDEFINED, str, getFont(fontSize), borderWidth, Rectangle.UNDEFINED, hAlignment, Element.ALIGN_UNDEFINED, lineSpace));
    }

    /**
     * 建立 Cell
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param colspan     Column Span 若無填 <code>1</code>
     * @param rowspan     Row Span 若無填 <code>1</code>
     * @param str         欄位內容字串
     * @param font        字體
     * @param borderWidth 框線的寬度
     * @param hAlignment  水平對齊方式
     * @param lineSpace   行距
     */
    protected void addCellAssignLinespace(PdfPTable table, int colspan, int rowspan, String str, Font font, float borderWidth, int hAlignment, int lineSpace) {
        table.addCell(PdfPTableUtility.createPdfPCell(colspan, rowspan, str, font, borderWidth, Rectangle.UNDEFINED, hAlignment, Element.ALIGN_UNDEFINED, lineSpace));
    }

    /**
     * 建立 Cell
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param rowspan     Row Span 若無填 <code>1</code>
     * @param str         欄位內容字串
     * @param font        字體
     * @param borderWidth 框線的寬度
     * @param hAlignment  水平對齊方式
     * @param lineSpace   行距
     */
    protected void addCellAssignLinespace(PdfPTable table, int rowspan, String str, Font font, float borderWidth, int hAlignment, int lineSpace) {
        table.addCell(PdfPTableUtility.createPdfPCell(PdfPTableUtility.SPAN_UNDEFINED, rowspan, str, font, borderWidth, Rectangle.UNDEFINED, hAlignment, Element.ALIGN_UNDEFINED, lineSpace));
    }

    /**
     * 建立 Cell
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param str         欄位內容字串
     * @param font        字體
     * @param borderWidth 框線的寬度
     * @param hAlignment  水平對齊方式
     * @param lineSpace   行距
     */
    protected void addCellAssignLinespace(PdfPTable table, String str, Font font, float borderWidth, int hAlignment, int lineSpace) {
        table.addCell(PdfPTableUtility.createPdfPCell(PdfPTableUtility.SPAN_UNDEFINED, PdfPTableUtility.SPAN_UNDEFINED, str, font, borderWidth, Rectangle.UNDEFINED, hAlignment, Element.ALIGN_UNDEFINED, lineSpace));
    }

    /**
     * 建立 Cell - for 全字庫
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param colspan     Column Span 若無填 <code>1</code>
     * @param rowspan     Row Span 若無填 <code>1</code>
     * @param str         欄位內容字串
     * @param cnsFont     CNS11643.SUNG 或 CNS11643.KAI
     * @param fontSize    字體大小
     * @param borderWidth 框線的寬度
     * @param hAlignment  水平對齊方式
     * @param lineSpace   行距
     */
    protected void addCellAssignLinespace(PdfPTable table, int colspan, int rowspan, String str, CNS11643 cnsFont, int fontSize, float borderWidth, int hAlignment, int lineSpace) {
        table.addCell(PdfPTableUtility.createPdfPCell(colspan, rowspan, str, getFontSelector(cnsFont, fontSize), borderWidth, Rectangle.UNDEFINED, hAlignment, Element.ALIGN_UNDEFINED, fontSize + lineSpace));
    }

    /**
     * 建立 Cell - for 全字庫
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param rowspan     Row Span 若無填 <code>1</code>
     * @param str         欄位內容字串
     * @param cnsFont     CNS11643.SUNG 或 CNS11643.KAI
     * @param fontSize    字體大小
     * @param borderWidth 框線的寬度
     * @param hAlignment  水平對齊方式
     * @param lineSpace   行距
     */
    protected void addCellAssignLinespace(PdfPTable table, int rowspan, String str, CNS11643 cnsFont, int fontSize, float borderWidth, int hAlignment, int lineSpace) {
        table.addCell(PdfPTableUtility.createPdfPCell(PdfPTableUtility.SPAN_UNDEFINED, rowspan, str, getFontSelector(cnsFont, fontSize), borderWidth, Rectangle.UNDEFINED, hAlignment, Element.ALIGN_UNDEFINED, fontSize + lineSpace));
    }

    /**
     * 建立 Cell - for 全字庫
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param str         欄位內容字串
     * @param cnsFont     CNS11643.SUNG 或 CNS11643.KAI
     * @param fontSize    字體大小
     * @param borderWidth 框線的寬度
     * @param hAlignment  水平對齊方式
     * @param lineSpace   行距
     */
    protected void addCellAssignLinespace(PdfPTable table, String str, CNS11643 cnsFont, int fontSize, float borderWidth, int hAlignment, int lineSpace) {
        table.addCell(PdfPTableUtility.createPdfPCell(PdfPTableUtility.SPAN_UNDEFINED, PdfPTableUtility.SPAN_UNDEFINED, str, getFontSelector(cnsFont, fontSize), borderWidth, Rectangle.UNDEFINED, hAlignment, Element.ALIGN_UNDEFINED, fontSize + lineSpace));
    }

    /**
     * 建立 Cell
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param colspan     Column Span 若無填 <code>1</code>
     * @param rowspan     Row Span 若無填 <code>1</code>
     * @param str         欄位內容字串
     * @param fontSize    字體大小
     * @param fontStyle   字體樣式
     * @param borderWidth 框線的寬度
     * @param hAlignment  水平對齊方式
     * @param lineSpace   行距
     */
    protected void addCellAssignLinespaceWithFontStyle(PdfPTable table, int colspan, int rowspan, String str, int fontSize, int fontStyle, float borderWidth, int hAlignment, int lineSpace) {
        table.addCell(PdfPTableUtility.createPdfPCell(colspan, rowspan, str, getFont(fontSize, fontStyle), borderWidth, Rectangle.UNDEFINED, hAlignment, Element.ALIGN_UNDEFINED, lineSpace));
    }

    /**
     * 建立 Cell
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param rowspan     Row Span 若無填 <code>1</code>
     * @param str         欄位內容字串
     * @param fontSize    字體大小
     * @param fontStyle   字體樣式
     * @param borderWidth 框線的寬度
     * @param hAlignment  水平對齊方式
     * @param lineSpace   行距
     */
    protected void addCellAssignLinespaceWithFontStyle(PdfPTable table, int rowspan, String str, int fontSize, int fontStyle, float borderWidth, int hAlignment, int lineSpace) {
        table.addCell(PdfPTableUtility.createPdfPCell(PdfPTableUtility.SPAN_UNDEFINED, rowspan, str, getFont(fontSize, fontStyle), borderWidth, Rectangle.UNDEFINED, hAlignment, Element.ALIGN_UNDEFINED, lineSpace));
    }

    /**
     * 建立 Cell
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param str         欄位內容字串
     * @param fontSize    字體大小
     * @param fontStyle   字體樣式
     * @param borderWidth 框線的寬度
     * @param hAlignment  水平對齊方式
     * @param lineSpace   行距
     */
    protected void addCellAssignLinespaceWithFontStyle(PdfPTable table, String str, int fontSize, int fontStyle, float borderWidth, int hAlignment, int lineSpace) {
        table.addCell(PdfPTableUtility.createPdfPCell(PdfPTableUtility.SPAN_UNDEFINED, PdfPTableUtility.SPAN_UNDEFINED, str, getFont(fontSize, fontStyle), borderWidth, Rectangle.UNDEFINED, hAlignment, Element.ALIGN_UNDEFINED, lineSpace));
    }

    /**
     * 建立 Cell - for 全字庫
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param colspan     Column Span 若無填 <code>1</code>
     * @param rowspan     Row Span 若無填 <code>1</code>
     * @param str         欄位內容字串
     * @param cnsFont     CNS11643.SUNG 或 CNS11643.KAI
     * @param fontSize    字體大小
     * @param fontStyle   字體樣式
     * @param borderWidth 框線的寬度
     * @param hAlignment  水平對齊方式
     * @param lineSpace   行距
     */
    protected void addCellAssignLinespaceWithFontStyle(PdfPTable table, int colspan, int rowspan, String str, CNS11643 cnsFont, int fontSize, int fontStyle, float borderWidth, int hAlignment, int lineSpace) {
        table.addCell(PdfPTableUtility.createPdfPCell(colspan, rowspan, str, getFontSelector(cnsFont, fontSize, fontStyle), borderWidth, Rectangle.UNDEFINED, hAlignment, Element.ALIGN_UNDEFINED, fontSize + lineSpace));
    }

    /**
     * 建立 Cell - for 全字庫
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param rowspan     Row Span 若無填 <code>1</code>
     * @param str         欄位內容字串
     * @param cnsFont     CNS11643.SUNG 或 CNS11643.KAI
     * @param fontSize    字體大小
     * @param fontStyle   字體樣式
     * @param borderWidth 框線的寬度
     * @param hAlignment  水平對齊方式
     * @param lineSpace   行距
     */
    protected void addCellAssignLinespaceWithFontStyle(PdfPTable table, int rowspan, String str, CNS11643 cnsFont, int fontSize, int fontStyle, float borderWidth, int hAlignment, int lineSpace) {
        table.addCell(PdfPTableUtility.createPdfPCell(PdfPTableUtility.SPAN_UNDEFINED, rowspan, str, getFontSelector(cnsFont, fontSize, fontStyle), borderWidth, Rectangle.UNDEFINED, hAlignment, Element.ALIGN_UNDEFINED, fontSize + lineSpace));
    }

    /**
     * 建立 Cell - for 全字庫
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param str         欄位內容字串
     * @param cnsFont     CNS11643.SUNG 或 CNS11643.KAI
     * @param fontSize    字體大小
     * @param fontStyle   字體樣式
     * @param borderWidth 框線的寬度
     * @param hAlignment  水平對齊方式
     * @param lineSpace   行距
     */
    protected void addCellAssignLinespaceWithFontStyle(PdfPTable table, String str, CNS11643 cnsFont, int fontSize, int fontStyle, float borderWidth, int hAlignment, int lineSpace) {
        table.addCell(PdfPTableUtility.createPdfPCell(PdfPTableUtility.SPAN_UNDEFINED, PdfPTableUtility.SPAN_UNDEFINED, str, getFontSelector(cnsFont, fontSize, fontStyle), borderWidth, Rectangle.UNDEFINED, hAlignment, Element.ALIGN_UNDEFINED, fontSize + lineSpace));
    }

    /**
     * 建立 Cell
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param colspan     Column Span 若無填 <code>1</code>
     * @param rowspan     Row Span 若無填 <code>1</code>
     * @param str         欄位內容字串
     * @param fontSize    字體大小
     * @param borderWidth 框線的寬度
     * @param hAlignment  水平對齊方式
     * @param lineSpace   行距
     * @param cellEvent   <code>PdfPCellEvent</code> 物件
     */
    protected void addCellAssignLinespace(PdfPTable table, int colspan, int rowspan, String str, int fontSize, float borderWidth, int hAlignment, int lineSpace, PdfPCellEvent cellEvent) {
        PdfPCell cell = PdfPTableUtility.createPdfPCell(colspan, rowspan, str, getFont(fontSize), borderWidth, Rectangle.UNDEFINED, hAlignment, Element.ALIGN_UNDEFINED, lineSpace);
        cell.setCellEvent(cellEvent);
        table.addCell(cell);
    }

    /**
     * 建立 Cell
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param rowspan     Row Span 若無填 <code>1</code>
     * @param str         欄位內容字串
     * @param fontSize    字體大小
     * @param borderWidth 框線的寬度
     * @param hAlignment  水平對齊方式
     * @param lineSpace   行距
     * @param cellEvent   <code>PdfPCellEvent</code> 物件
     */
    protected void addCellAssignLinespace(PdfPTable table, int rowspan, String str, int fontSize, float borderWidth, int hAlignment, int lineSpace, PdfPCellEvent cellEvent) {
        PdfPCell cell = PdfPTableUtility.createPdfPCell(PdfPTableUtility.SPAN_UNDEFINED, rowspan, str, getFont(fontSize), borderWidth, Rectangle.UNDEFINED, hAlignment, Element.ALIGN_UNDEFINED, lineSpace);
        cell.setCellEvent(cellEvent);
        table.addCell(cell);
    }

    /**
     * 建立 Cell
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param str         欄位內容字串
     * @param fontSize    字體大小
     * @param borderWidth 框線的寬度
     * @param hAlignment  水平對齊方式
     * @param lineSpace   行距
     * @param cellEvent   <code>PdfPCellEvent</code> 物件
     */
    protected void addCellAssignLinespace(PdfPTable table, String str, int fontSize, float borderWidth, int hAlignment, int lineSpace, PdfPCellEvent cellEvent) {
        PdfPCell cell = PdfPTableUtility.createPdfPCell(PdfPTableUtility.SPAN_UNDEFINED, PdfPTableUtility.SPAN_UNDEFINED, str, getFont(fontSize), borderWidth, Rectangle.UNDEFINED, hAlignment, Element.ALIGN_UNDEFINED, lineSpace);
        cell.setCellEvent(cellEvent);
        table.addCell(cell);
    }

    /**
     * 建立 Cell
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param colspan     Column Span 若無填 <code>1</code>
     * @param rowspan     Row Span 若無填 <code>1</code>
     * @param str         欄位內容字串
     * @param font        字體
     * @param borderWidth 框線的寬度
     * @param hAlignment  水平對齊方式
     * @param lineSpace   行距
     * @param cellEvent   <code>PdfPCellEvent</code> 物件
     */
    protected void addCellAssignLinespace(PdfPTable table, int colspan, int rowspan, String str, Font font, float borderWidth, int hAlignment, int lineSpace, PdfPCellEvent cellEvent) {
        PdfPCell cell = PdfPTableUtility.createPdfPCell(colspan, rowspan, str, font, borderWidth, Rectangle.UNDEFINED, hAlignment, Element.ALIGN_UNDEFINED, lineSpace);
        cell.setCellEvent(cellEvent);
        table.addCell(cell);
    }

    /**
     * 建立 Cell
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param rowspan     Row Span 若無填 <code>1</code>
     * @param str         欄位內容字串
     * @param font        字體
     * @param borderWidth 框線的寬度
     * @param hAlignment  水平對齊方式
     * @param lineSpace   行距
     * @param cellEvent   <code>PdfPCellEvent</code> 物件
     */
    protected void addCellAssignLinespace(PdfPTable table, int rowspan, String str, Font font, float borderWidth, int hAlignment, int lineSpace, PdfPCellEvent cellEvent) {
        PdfPCell cell = PdfPTableUtility.createPdfPCell(PdfPTableUtility.SPAN_UNDEFINED, rowspan, str, font, borderWidth, Rectangle.UNDEFINED, hAlignment, Element.ALIGN_UNDEFINED, lineSpace);
        cell.setCellEvent(cellEvent);
        table.addCell(cell);
    }

    /**
     * 建立 Cell
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param str         欄位內容字串
     * @param font        字體
     * @param borderWidth 框線的寬度
     * @param hAlignment  水平對齊方式
     * @param lineSpace   行距
     * @param cellEvent   <code>PdfPCellEvent</code> 物件
     */
    protected void addCellAssignLinespace(PdfPTable table, String str, Font font, float borderWidth, int hAlignment, int lineSpace, PdfPCellEvent cellEvent) {
        PdfPCell cell = PdfPTableUtility.createPdfPCell(PdfPTableUtility.SPAN_UNDEFINED, PdfPTableUtility.SPAN_UNDEFINED, str, font, borderWidth, Rectangle.UNDEFINED, hAlignment, Element.ALIGN_UNDEFINED, lineSpace);
        cell.setCellEvent(cellEvent);
        table.addCell(cell);
    }

    /**
     * 建立 Cell - for 全字庫
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param colspan     Column Span 若無填 <code>1</code>
     * @param rowspan     Row Span 若無填 <code>1</code>
     * @param str         欄位內容字串
     * @param cnsFont     CNS11643.SUNG 或 CNS11643.KAI
     * @param fontSize    字體大小
     * @param borderWidth 框線的寬度
     * @param hAlignment  水平對齊方式
     * @param lineSpace   行距
     * @param cellEvent   <code>PdfPCellEvent</code> 物件
     */
    protected void addCellAssignLinespace(PdfPTable table, int colspan, int rowspan, String str, CNS11643 cnsFont, int fontSize, float borderWidth, int hAlignment, int lineSpace, PdfPCellEvent cellEvent) {
        PdfPCell cell = PdfPTableUtility.createPdfPCell(colspan, rowspan, str, getFontSelector(cnsFont, fontSize), borderWidth, Rectangle.UNDEFINED, hAlignment, Element.ALIGN_UNDEFINED, fontSize + lineSpace);
        cell.setCellEvent(cellEvent);
        table.addCell(cell);
    }

    /**
     * 建立 Cell - for 全字庫
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param rowspan     Row Span 若無填 <code>1</code>
     * @param str         欄位內容字串
     * @param cnsFont     CNS11643.SUNG 或 CNS11643.KAI
     * @param fontSize    字體大小
     * @param borderWidth 框線的寬度
     * @param hAlignment  水平對齊方式
     * @param lineSpace   行距
     * @param cellEvent   <code>PdfPCellEvent</code> 物件
     */
    protected void addCellAssignLinespace(PdfPTable table, int rowspan, String str, CNS11643 cnsFont, int fontSize, float borderWidth, int hAlignment, int lineSpace, PdfPCellEvent cellEvent) {
        PdfPCell cell = PdfPTableUtility.createPdfPCell(PdfPTableUtility.SPAN_UNDEFINED, rowspan, str, getFontSelector(cnsFont, fontSize), borderWidth, Rectangle.UNDEFINED, hAlignment, Element.ALIGN_UNDEFINED, fontSize + lineSpace);
        cell.setCellEvent(cellEvent);
        table.addCell(cell);
    }

    /**
     * 建立 Cell - for 全字庫
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param str         欄位內容字串
     * @param cnsFont     CNS11643.SUNG 或 CNS11643.KAI
     * @param fontSize    字體大小
     * @param borderWidth 框線的寬度
     * @param hAlignment  水平對齊方式
     * @param lineSpace   行距
     * @param cellEvent   <code>PdfPCellEvent</code> 物件
     */
    protected void addCellAssignLinespace(PdfPTable table, String str, CNS11643 cnsFont, int fontSize, float borderWidth, int hAlignment, int lineSpace, PdfPCellEvent cellEvent) {
        PdfPCell cell = PdfPTableUtility.createPdfPCell(PdfPTableUtility.SPAN_UNDEFINED, PdfPTableUtility.SPAN_UNDEFINED, str, getFontSelector(cnsFont, fontSize), borderWidth, Rectangle.UNDEFINED, hAlignment, Element.ALIGN_UNDEFINED, fontSize + lineSpace);
        cell.setCellEvent(cellEvent);
        table.addCell(cell);
    }

    /**
     * 建立 Cell
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param colspan     Column Span 若無填 <code>1</code>
     * @param rowspan     Row Span 若無填 <code>1</code>
     * @param str         欄位內容字串
     * @param fontSize    字體大小
     * @param fontStyle   字體樣式
     * @param borderWidth 框線的寬度
     * @param hAlignment  水平對齊方式
     * @param lineSpace   行距
     * @param cellEvent   <code>PdfPCellEvent</code> 物件
     */
    protected void addCellAssignLinespaceWithFontStyle(PdfPTable table, int colspan, int rowspan, String str, int fontSize, int fontStyle, float borderWidth, int hAlignment, int lineSpace, PdfPCellEvent cellEvent) {
        PdfPCell cell = PdfPTableUtility.createPdfPCell(colspan, rowspan, str, getFont(fontSize, fontStyle), borderWidth, Rectangle.UNDEFINED, hAlignment, Element.ALIGN_UNDEFINED, lineSpace);
        cell.setCellEvent(cellEvent);
        table.addCell(cell);
    }

    /**
     * 建立 Cell
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param rowspan     Row Span 若無填 <code>1</code>
     * @param str         欄位內容字串
     * @param fontSize    字體大小
     * @param fontStyle   字體樣式
     * @param borderWidth 框線的寬度
     * @param hAlignment  水平對齊方式
     * @param lineSpace   行距
     * @param cellEvent   <code>PdfPCellEvent</code> 物件
     */
    protected void addCellAssignLinespaceWithFontStyle(PdfPTable table, int rowspan, String str, int fontSize, int fontStyle, float borderWidth, int hAlignment, int lineSpace, PdfPCellEvent cellEvent) {
        PdfPCell cell = PdfPTableUtility.createPdfPCell(PdfPTableUtility.SPAN_UNDEFINED, rowspan, str, getFont(fontSize, fontStyle), borderWidth, Rectangle.UNDEFINED, hAlignment, Element.ALIGN_UNDEFINED, lineSpace);
        cell.setCellEvent(cellEvent);
        table.addCell(cell);
    }

    /**
     * 建立 Cell
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param str         欄位內容字串
     * @param fontSize    字體大小
     * @param fontStyle   字體樣式
     * @param borderWidth 框線的寬度
     * @param hAlignment  水平對齊方式
     * @param lineSpace   行距
     * @param cellEvent   <code>PdfPCellEvent</code> 物件
     */
    protected void addCellAssignLinespaceWithFontStyle(PdfPTable table, String str, int fontSize, int fontStyle, float borderWidth, int hAlignment, int lineSpace, PdfPCellEvent cellEvent) {
        PdfPCell cell = PdfPTableUtility.createPdfPCell(PdfPTableUtility.SPAN_UNDEFINED, PdfPTableUtility.SPAN_UNDEFINED, str, getFont(fontSize, fontStyle), borderWidth, Rectangle.UNDEFINED, hAlignment, Element.ALIGN_UNDEFINED, lineSpace);
        cell.setCellEvent(cellEvent);
        table.addCell(cell);
    }

    /**
     * 建立 Cell - for 全字庫
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param colspan     Column Span 若無填 <code>1</code>
     * @param rowspan     Row Span 若無填 <code>1</code>
     * @param str         欄位內容字串
     * @param cnsFont     CNS11643.SUNG 或 CNS11643.KAI
     * @param fontSize    字體大小
     * @param fontStyle   字體樣式
     * @param borderWidth 框線的寬度
     * @param hAlignment  水平對齊方式
     * @param lineSpace   行距
     * @param cellEvent   <code>PdfPCellEvent</code> 物件
     */
    protected void addCellAssignLinespaceWithFontStyle(PdfPTable table, int colspan, int rowspan, String str, CNS11643 cnsFont, int fontSize, int fontStyle, float borderWidth, int hAlignment, int lineSpace, PdfPCellEvent cellEvent) {
        PdfPCell cell = PdfPTableUtility.createPdfPCell(colspan, rowspan, str, getFontSelector(cnsFont, fontSize, fontStyle), borderWidth, Rectangle.UNDEFINED, hAlignment, Element.ALIGN_UNDEFINED, fontSize + lineSpace);
        cell.setCellEvent(cellEvent);
        table.addCell(cell);
    }

    /**
     * 建立 Cell - for 全字庫
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param rowspan     Row Span 若無填 <code>1</code>
     * @param str         欄位內容字串
     * @param cnsFont     CNS11643.SUNG 或 CNS11643.KAI
     * @param fontSize    字體大小
     * @param fontStyle   字體樣式
     * @param borderWidth 框線的寬度
     * @param hAlignment  水平對齊方式
     * @param lineSpace   行距
     * @param cellEvent   <code>PdfPCellEvent</code> 物件
     */
    protected void addCellAssignLinespaceWithFontStyle(PdfPTable table, int rowspan, String str, CNS11643 cnsFont, int fontSize, int fontStyle, float borderWidth, int hAlignment, int lineSpace, PdfPCellEvent cellEvent) {
        PdfPCell cell = PdfPTableUtility.createPdfPCell(PdfPTableUtility.SPAN_UNDEFINED, rowspan, str, getFontSelector(cnsFont, fontSize, fontStyle), borderWidth, Rectangle.UNDEFINED, hAlignment, Element.ALIGN_UNDEFINED, fontSize + lineSpace);
        cell.setCellEvent(cellEvent);
        table.addCell(cell);
    }

    /**
     * 建立 Cell - for 全字庫
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param str         欄位內容字串
     * @param cnsFont     CNS11643.SUNG 或 CNS11643.KAI
     * @param fontSize    字體大小
     * @param fontStyle   字體樣式
     * @param borderWidth 框線的寬度
     * @param hAlignment  水平對齊方式
     * @param lineSpace   行距
     * @param cellEvent   <code>PdfPCellEvent</code> 物件
     */
    protected void addCellAssignLinespaceWithFontStyle(PdfPTable table, String str, CNS11643 cnsFont, int fontSize, int fontStyle, float borderWidth, int hAlignment, int lineSpace, PdfPCellEvent cellEvent) {
        PdfPCell cell = PdfPTableUtility.createPdfPCell(PdfPTableUtility.SPAN_UNDEFINED, PdfPTableUtility.SPAN_UNDEFINED, str, getFontSelector(cnsFont, fontSize, fontStyle), borderWidth, Rectangle.UNDEFINED, hAlignment, Element.ALIGN_UNDEFINED, fontSize + lineSpace);
        cell.setCellEvent(cellEvent);
        table.addCell(cell);
    }

    /**
     * 建立 Cell
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param colspan     Column Span 若無填 <code>1</code>
     * @param rowspan     Row Span 若無填 <code>1</code>
     * @param str         欄位內容字串
     * @param fontSize    字體大小
     * @param borderWidth 框線的寬度
     * @param hAlignment  水平對齊方式
     * @param vAlignment  垂直對齊方式
     */
    protected void addCellAssignVAlignment(PdfPTable table, int colspan, int rowspan, String str, int fontSize, float borderWidth, int hAlignment, int vAlignment) {
        table.addCell(PdfPTableUtility.createPdfPCell(colspan, rowspan, str, getFont(fontSize), borderWidth, Rectangle.UNDEFINED, hAlignment, vAlignment, PdfPTableUtility.LINESPACE_UNDEFINED));
    }

    /**
     * 建立 Cell
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param rowspan     Row Span 若無填 <code>1</code>
     * @param str         欄位內容字串
     * @param fontSize    字體大小
     * @param borderWidth 框線的寬度
     * @param hAlignment  水平對齊方式
     * @param vAlignment  垂直對齊方式
     */
    protected void addCellAssignVAlignment(PdfPTable table, int rowspan, String str, int fontSize, float borderWidth, int hAlignment, int vAlignment) {
        table.addCell(PdfPTableUtility.createPdfPCell(PdfPTableUtility.SPAN_UNDEFINED, rowspan, str, getFont(fontSize), borderWidth, Rectangle.UNDEFINED, hAlignment, vAlignment, PdfPTableUtility.LINESPACE_UNDEFINED));
    }

    /**
     * 建立 Cell
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param str         欄位內容字串
     * @param fontSize    字體大小
     * @param borderWidth 框線的寬度
     * @param hAlignment  水平對齊方式
     * @param vAlignment  垂直對齊方式
     */
    protected void addCellAssignVAlignment(PdfPTable table, String str, int fontSize, float borderWidth, int hAlignment, int vAlignment) {
        table.addCell(PdfPTableUtility.createPdfPCell(PdfPTableUtility.SPAN_UNDEFINED, PdfPTableUtility.SPAN_UNDEFINED, str, getFont(fontSize), borderWidth, Rectangle.UNDEFINED, hAlignment, vAlignment, PdfPTableUtility.LINESPACE_UNDEFINED));
    }

    /**
     * 建立 Cell
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param colspan     Column Span 若無填 <code>1</code>
     * @param rowspan     Row Span 若無填 <code>1</code>
     * @param str         欄位內容字串
     * @param font        字體
     * @param borderWidth 框線的寬度
     * @param hAlignment  水平對齊方式
     * @param vAlignment  垂直對齊方式
     */
    protected void addCellAssignVAlignment(PdfPTable table, int colspan, int rowspan, String str, Font font, float borderWidth, int hAlignment, int vAlignment) {
        table.addCell(PdfPTableUtility.createPdfPCell(colspan, rowspan, str, font, borderWidth, Rectangle.UNDEFINED, hAlignment, vAlignment, PdfPTableUtility.LINESPACE_UNDEFINED));
    }

    /**
     * 建立 Cell
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param rowspan     Row Span 若無填 <code>1</code>
     * @param str         欄位內容字串
     * @param font        字體
     * @param borderWidth 框線的寬度
     * @param hAlignment  水平對齊方式
     * @param vAlignment  垂直對齊方式
     */
    protected void addCellAssignVAlignment(PdfPTable table, int rowspan, String str, Font font, float borderWidth, int hAlignment, int vAlignment) {
        table.addCell(PdfPTableUtility.createPdfPCell(PdfPTableUtility.SPAN_UNDEFINED, rowspan, str, font, borderWidth, Rectangle.UNDEFINED, hAlignment, vAlignment, PdfPTableUtility.LINESPACE_UNDEFINED));
    }

    /**
     * 建立 Cell
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param str         欄位內容字串
     * @param font        字體大小
     * @param borderWidth 框線的寬度
     * @param hAlignment  水平對齊方式
     * @param vAlignment  垂直對齊方式
     */
    protected void addCellAssignVAlignment(PdfPTable table, String str, Font font, float borderWidth, int hAlignment, int vAlignment) {
        table.addCell(PdfPTableUtility.createPdfPCell(PdfPTableUtility.SPAN_UNDEFINED, PdfPTableUtility.SPAN_UNDEFINED, str, font, borderWidth, Rectangle.UNDEFINED, hAlignment, vAlignment, PdfPTableUtility.LINESPACE_UNDEFINED));
    }

    /**
     * 建立 Cell - for 全字庫
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param colspan     Column Span 若無填 <code>1</code>
     * @param rowspan     Row Span 若無填 <code>1</code>
     * @param str         欄位內容字串
     * @param cnsFont     CNS11643.SUNG 或 CNS11643.KAI
     * @param fontSize    字體大小
     * @param borderWidth 框線的寬度
     * @param hAlignment  水平對齊方式
     * @param vAlignment  垂直對齊方式
     */
    protected void addCellAssignVAlignment(PdfPTable table, int colspan, int rowspan, String str, CNS11643 cnsFont, int fontSize, float borderWidth, int hAlignment, int vAlignment) {
        table.addCell(PdfPTableUtility.createPdfPCell(colspan, rowspan, str, getFontSelector(cnsFont, fontSize), borderWidth, Rectangle.UNDEFINED, hAlignment, vAlignment, PdfPTableUtility.LINESPACE_UNDEFINED));
    }

    /**
     * 建立 Cell - for 全字庫
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param rowspan     Row Span 若無填 <code>1</code>
     * @param str         欄位內容字串
     * @param cnsFont     CNS11643.SUNG 或 CNS11643.KAI
     * @param fontSize    字體大小
     * @param borderWidth 框線的寬度
     * @param hAlignment  水平對齊方式
     * @param vAlignment  垂直對齊方式
     */
    protected void addCellAssignVAlignment(PdfPTable table, int rowspan, String str, CNS11643 cnsFont, int fontSize, float borderWidth, int hAlignment, int vAlignment) {
        table.addCell(PdfPTableUtility.createPdfPCell(PdfPTableUtility.SPAN_UNDEFINED, rowspan, str, getFontSelector(cnsFont, fontSize), borderWidth, Rectangle.UNDEFINED, hAlignment, vAlignment, PdfPTableUtility.LINESPACE_UNDEFINED));
    }

    /**
     * 建立 Cell - for 全字庫
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param str         欄位內容字串
     * @param cnsFont     CNS11643.SUNG 或 CNS11643.KAI
     * @param fontSize    字體大小
     * @param borderWidth 框線的寬度
     * @param hAlignment  水平對齊方式
     * @param vAlignment  垂直對齊方式
     */
    protected void addCellAssignVAlignment(PdfPTable table, String str, CNS11643 cnsFont, int fontSize, float borderWidth, int hAlignment, int vAlignment) {
        table.addCell(PdfPTableUtility.createPdfPCell(PdfPTableUtility.SPAN_UNDEFINED, PdfPTableUtility.SPAN_UNDEFINED, str, getFontSelector(cnsFont, fontSize), borderWidth, Rectangle.UNDEFINED, hAlignment, vAlignment, PdfPTableUtility.LINESPACE_UNDEFINED));
    }

    /**
     * 建立 Cell
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param colspan     Column Span 若無填 <code>1</code>
     * @param rowspan     Row Span 若無填 <code>1</code>
     * @param str         欄位內容字串
     * @param fontSize    字體大小
     * @param fontStyle   字體樣式
     * @param borderWidth 框線的寬度
     * @param hAlignment  水平對齊方式
     * @param vAlignment  垂直對齊方式
     */
    protected void addCellAssignVAlignmentWithFontStyle(PdfPTable table, int colspan, int rowspan, String str, int fontStyle, int fontSize, float borderWidth, int hAlignment, int vAlignment) {
        table.addCell(PdfPTableUtility.createPdfPCell(colspan, rowspan, str, getFont(fontSize, fontStyle), borderWidth, Rectangle.UNDEFINED, hAlignment, vAlignment, PdfPTableUtility.LINESPACE_UNDEFINED));
    }

    /**
     * 建立 Cell
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param rowspan     Row Span 若無填 <code>1</code>
     * @param str         欄位內容字串
     * @param fontSize    字體大小
     * @param fontStyle   字體樣式
     * @param borderWidth 框線的寬度
     * @param hAlignment  水平對齊方式
     * @param vAlignment  垂直對齊方式
     */
    protected void addCellAssignVAlignmentWithFontStyle(PdfPTable table, int rowspan, String str, int fontSize, int fontStyle, float borderWidth, int hAlignment, int vAlignment) {
        table.addCell(PdfPTableUtility.createPdfPCell(PdfPTableUtility.SPAN_UNDEFINED, rowspan, str, getFont(fontSize, fontStyle), borderWidth, Rectangle.UNDEFINED, hAlignment, vAlignment, PdfPTableUtility.LINESPACE_UNDEFINED));
    }

    /**
     * 建立 Cell
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param str         欄位內容字串
     * @param fontSize    字體大小
     * @param fontStyle   字體樣式
     * @param borderWidth 框線的寬度
     * @param hAlignment  水平對齊方式
     * @param vAlignment  垂直對齊方式
     */
    protected void addCellAssignVAlignmentWithFontStyle(PdfPTable table, String str, int fontSize, int fontStyle, float borderWidth, int hAlignment, int vAlignment) {
        table.addCell(PdfPTableUtility.createPdfPCell(PdfPTableUtility.SPAN_UNDEFINED, PdfPTableUtility.SPAN_UNDEFINED, str, getFont(fontSize, fontStyle), borderWidth, Rectangle.UNDEFINED, hAlignment, vAlignment, PdfPTableUtility.LINESPACE_UNDEFINED));
    }

    /**
     * 建立 Cell - for 全字庫
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param colspan     Column Span 若無填 <code>1</code>
     * @param rowspan     Row Span 若無填 <code>1</code>
     * @param str         欄位內容字串
     * @param cnsFont     CNS11643.SUNG 或 CNS11643.KAI
     * @param fontSize    字體大小
     * @param fontStyle   字體樣式
     * @param borderWidth 框線的寬度
     * @param hAlignment  水平對齊方式
     * @param vAlignment  垂直對齊方式
     */
    protected void addCellAssignVAlignmentWithFontStyle(PdfPTable table, int colspan, int rowspan, String str, CNS11643 cnsFont, int fontStyle, int fontSize, float borderWidth, int hAlignment, int vAlignment) {
        table.addCell(PdfPTableUtility.createPdfPCell(colspan, rowspan, str, getFontSelector(cnsFont, fontSize, fontStyle), borderWidth, Rectangle.UNDEFINED, hAlignment, vAlignment, PdfPTableUtility.LINESPACE_UNDEFINED));
    }

    /**
     * 建立 Cell - for 全字庫
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param rowspan     Row Span 若無填 <code>1</code>
     * @param str         欄位內容字串
     * @param cnsFont     CNS11643.SUNG 或 CNS11643.KAI
     * @param fontSize    字體大小
     * @param fontStyle   字體樣式
     * @param borderWidth 框線的寬度
     * @param hAlignment  水平對齊方式
     * @param vAlignment  垂直對齊方式
     */
    protected void addCellAssignVAlignmentWithFontStyle(PdfPTable table, int rowspan, String str, CNS11643 cnsFont, int fontSize, int fontStyle, float borderWidth, int hAlignment, int vAlignment) {
        table.addCell(PdfPTableUtility.createPdfPCell(PdfPTableUtility.SPAN_UNDEFINED, rowspan, str, getFontSelector(cnsFont, fontSize, fontStyle), borderWidth, Rectangle.UNDEFINED, hAlignment, vAlignment, PdfPTableUtility.LINESPACE_UNDEFINED));
    }

    /**
     * 建立 Cell - for 全字庫
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param str         欄位內容字串
     * @param cnsFont     CNS11643.SUNG 或 CNS11643.KAI
     * @param fontSize    字體大小
     * @param fontStyle   字體樣式
     * @param borderWidth 框線的寬度
     * @param hAlignment  水平對齊方式
     * @param vAlignment  垂直對齊方式
     */
    protected void addCellAssignVAlignmentWithFontStyle(PdfPTable table, String str, CNS11643 cnsFont, int fontSize, int fontStyle, float borderWidth, int hAlignment, int vAlignment) {
        table.addCell(PdfPTableUtility.createPdfPCell(PdfPTableUtility.SPAN_UNDEFINED, PdfPTableUtility.SPAN_UNDEFINED, str, getFontSelector(cnsFont, fontSize, fontStyle), borderWidth, Rectangle.UNDEFINED, hAlignment, vAlignment, PdfPTableUtility.LINESPACE_UNDEFINED));
    }

    /**
     * 建立 Cell
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param colspan     Column Span 若無填 <code>1</code>
     * @param rowspan     Row Span 若無填 <code>1</code>
     * @param str         欄位內容字串
     * @param fontSize    字體大小
     * @param borderWidth 框線的寬度
     * @param hAlignment  水平對齊方式
     * @param vAlignment  垂直對齊方式
     * @param cellEvent   <code>PdfPCellEvent</code> 物件
     */
    protected void addCellAssignVAlignment(PdfPTable table, int colspan, int rowspan, String str, int fontSize, float borderWidth, int hAlignment, int vAlignment, PdfPCellEvent cellEvent) {
        PdfPCell cell = PdfPTableUtility.createPdfPCell(colspan, rowspan, str, getFont(fontSize), borderWidth, Rectangle.UNDEFINED, hAlignment, vAlignment, PdfPTableUtility.LINESPACE_UNDEFINED);
        cell.setCellEvent(cellEvent);
        table.addCell(cell);
    }

    /**
     * 建立 Cell
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param rowspan     Row Span 若無填 <code>1</code>
     * @param str         欄位內容字串
     * @param fontSize    字體大小
     * @param borderWidth 框線的寬度
     * @param hAlignment  水平對齊方式
     * @param vAlignment  垂直對齊方式
     * @param cellEvent   <code>PdfPCellEvent</code> 物件
     */
    protected void addCellAssignVAlignment(PdfPTable table, int rowspan, String str, int fontSize, float borderWidth, int hAlignment, int vAlignment, PdfPCellEvent cellEvent) {
        PdfPCell cell = PdfPTableUtility.createPdfPCell(PdfPTableUtility.SPAN_UNDEFINED, rowspan, str, getFont(fontSize), borderWidth, Rectangle.UNDEFINED, hAlignment, vAlignment, PdfPTableUtility.LINESPACE_UNDEFINED);
        cell.setCellEvent(cellEvent);
        table.addCell(cell);
    }

    /**
     * 建立 Cell
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param str         欄位內容字串
     * @param fontSize    字體大小
     * @param borderWidth 框線的寬度
     * @param hAlignment  水平對齊方式
     * @param vAlignment  垂直對齊方式
     * @param cellEvent   <code>PdfPCellEvent</code> 物件
     */
    protected void addCellAssignVAlignment(PdfPTable table, String str, int fontSize, float borderWidth, int hAlignment, int vAlignment, PdfPCellEvent cellEvent) {
        PdfPCell cell = PdfPTableUtility.createPdfPCell(PdfPTableUtility.SPAN_UNDEFINED, PdfPTableUtility.SPAN_UNDEFINED, str, getFont(fontSize), borderWidth, Rectangle.UNDEFINED, hAlignment, vAlignment, PdfPTableUtility.LINESPACE_UNDEFINED);
        cell.setCellEvent(cellEvent);
        table.addCell(cell);
    }

    /**
     * 建立 Cell
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param colspan     Column Span 若無填 <code>1</code>
     * @param rowspan     Row Span 若無填 <code>1</code>
     * @param str         欄位內容字串
     * @param font        字體
     * @param borderWidth 框線的寬度
     * @param hAlignment  水平對齊方式
     * @param vAlignment  垂直對齊方式
     * @param cellEvent   <code>PdfPCellEvent</code> 物件
     */
    protected void addCellAssignVAlignment(PdfPTable table, int colspan, int rowspan, String str, Font font, float borderWidth, int hAlignment, int vAlignment, PdfPCellEvent cellEvent) {
        PdfPCell cell = PdfPTableUtility.createPdfPCell(colspan, rowspan, str, font, borderWidth, Rectangle.UNDEFINED, hAlignment, vAlignment, PdfPTableUtility.LINESPACE_UNDEFINED);
        cell.setCellEvent(cellEvent);
        table.addCell(cell);
    }

    /**
     * 建立 Cell
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param rowspan     Row Span 若無填 <code>1</code>
     * @param str         欄位內容字串
     * @param font        字體
     * @param borderWidth 框線的寬度
     * @param hAlignment  水平對齊方式
     * @param vAlignment  垂直對齊方式
     * @param cellEvent   <code>PdfPCellEvent</code> 物件
     */
    protected void addCellAssignVAlignment(PdfPTable table, int rowspan, String str, Font font, float borderWidth, int hAlignment, int vAlignment, PdfPCellEvent cellEvent) {
        PdfPCell cell = PdfPTableUtility.createPdfPCell(PdfPTableUtility.SPAN_UNDEFINED, rowspan, str, font, borderWidth, Rectangle.UNDEFINED, hAlignment, vAlignment, PdfPTableUtility.LINESPACE_UNDEFINED);
        cell.setCellEvent(cellEvent);
        table.addCell(cell);
    }

    /**
     * 建立 Cell
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param str         欄位內容字串
     * @param font        字體大小
     * @param borderWidth 框線的寬度
     * @param hAlignment  水平對齊方式
     * @param vAlignment  垂直對齊方式
     * @param cellEvent   <code>PdfPCellEvent</code> 物件
     */
    protected void addCellAssignVAlignment(PdfPTable table, String str, Font font, float borderWidth, int hAlignment, int vAlignment, PdfPCellEvent cellEvent) {
        PdfPCell cell = PdfPTableUtility.createPdfPCell(PdfPTableUtility.SPAN_UNDEFINED, PdfPTableUtility.SPAN_UNDEFINED, str, font, borderWidth, Rectangle.UNDEFINED, hAlignment, vAlignment, PdfPTableUtility.LINESPACE_UNDEFINED);
        cell.setCellEvent(cellEvent);
        table.addCell(cell);
    }

    /**
     * 建立 Cell - for 全字庫
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param colspan     Column Span 若無填 <code>1</code>
     * @param rowspan     Row Span 若無填 <code>1</code>
     * @param str         欄位內容字串
     * @param cnsFont     CNS11643.SUNG 或 CNS11643.KAI
     * @param fontSize    字體大小
     * @param borderWidth 框線的寬度
     * @param hAlignment  水平對齊方式
     * @param vAlignment  垂直對齊方式
     * @param cellEvent   <code>PdfPCellEvent</code> 物件
     */
    protected void addCellAssignVAlignment(PdfPTable table, int colspan, int rowspan, String str, CNS11643 cnsFont, int fontSize, float borderWidth, int hAlignment, int vAlignment, PdfPCellEvent cellEvent) {
        PdfPCell cell = PdfPTableUtility.createPdfPCell(colspan, rowspan, str, getFontSelector(cnsFont, fontSize), borderWidth, Rectangle.UNDEFINED, hAlignment, vAlignment, PdfPTableUtility.LINESPACE_UNDEFINED);
        cell.setCellEvent(cellEvent);
        table.addCell(cell);
    }

    /**
     * 建立 Cell - for 全字庫
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param rowspan     Row Span 若無填 <code>1</code>
     * @param str         欄位內容字串
     * @param cnsFont     CNS11643.SUNG 或 CNS11643.KAI
     * @param fontSize    字體大小
     * @param borderWidth 框線的寬度
     * @param hAlignment  水平對齊方式
     * @param vAlignment  垂直對齊方式
     * @param cellEvent   <code>PdfPCellEvent</code> 物件
     */
    protected void addCellAssignVAlignment(PdfPTable table, int rowspan, String str, CNS11643 cnsFont, int fontSize, float borderWidth, int hAlignment, int vAlignment, PdfPCellEvent cellEvent) {
        PdfPCell cell = PdfPTableUtility.createPdfPCell(PdfPTableUtility.SPAN_UNDEFINED, rowspan, str, getFontSelector(cnsFont, fontSize), borderWidth, Rectangle.UNDEFINED, hAlignment, vAlignment, PdfPTableUtility.LINESPACE_UNDEFINED);
        cell.setCellEvent(cellEvent);
        table.addCell(cell);
    }

    /**
     * 建立 Cell - for 全字庫
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param str         欄位內容字串
     * @param cnsFont     CNS11643.SUNG 或 CNS11643.KAI
     * @param fontSize    字體大小
     * @param borderWidth 框線的寬度
     * @param hAlignment  水平對齊方式
     * @param vAlignment  垂直對齊方式
     * @param cellEvent   <code>PdfPCellEvent</code> 物件
     */
    protected void addCellAssignVAlignment(PdfPTable table, String str, CNS11643 cnsFont, int fontSize, float borderWidth, int hAlignment, int vAlignment, PdfPCellEvent cellEvent) {
        PdfPCell cell = PdfPTableUtility.createPdfPCell(PdfPTableUtility.SPAN_UNDEFINED, PdfPTableUtility.SPAN_UNDEFINED, str, getFontSelector(cnsFont, fontSize), borderWidth, Rectangle.UNDEFINED, hAlignment, vAlignment, PdfPTableUtility.LINESPACE_UNDEFINED);
        cell.setCellEvent(cellEvent);
        table.addCell(cell);
    }

    /**
     * 建立 Cell
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param colspan     Column Span 若無填 <code>1</code>
     * @param rowspan     Row Span 若無填 <code>1</code>
     * @param str         欄位內容字串
     * @param fontSize    字體大小
     * @param fontStyle   字體樣式
     * @param borderWidth 框線的寬度
     * @param hAlignment  水平對齊方式
     * @param vAlignment  垂直對齊方式
     * @param cellEvent   <code>PdfPCellEvent</code> 物件
     */
    protected void addCellAssignVAlignmentWithFontStyle(PdfPTable table, int colspan, int rowspan, String str, int fontStyle, int fontSize, float borderWidth, int hAlignment, int vAlignment, PdfPCellEvent cellEvent) {
        PdfPCell cell = PdfPTableUtility.createPdfPCell(colspan, rowspan, str, getFont(fontSize, fontStyle), borderWidth, Rectangle.UNDEFINED, hAlignment, vAlignment, PdfPTableUtility.LINESPACE_UNDEFINED);
        cell.setCellEvent(cellEvent);
        table.addCell(cell);
    }

    /**
     * 建立 Cell
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param rowspan     Row Span 若無填 <code>1</code>
     * @param str         欄位內容字串
     * @param fontSize    字體大小
     * @param fontStyle   字體樣式
     * @param borderWidth 框線的寬度
     * @param hAlignment  水平對齊方式
     * @param vAlignment  垂直對齊方式
     * @param cellEvent   <code>PdfPCellEvent</code> 物件
     */
    protected void addCellAssignVAlignmentWithFontStyle(PdfPTable table, int rowspan, String str, int fontSize, int fontStyle, float borderWidth, int hAlignment, int vAlignment, PdfPCellEvent cellEvent) {
        PdfPCell cell = PdfPTableUtility.createPdfPCell(PdfPTableUtility.SPAN_UNDEFINED, rowspan, str, getFont(fontSize, fontStyle), borderWidth, Rectangle.UNDEFINED, hAlignment, vAlignment, PdfPTableUtility.LINESPACE_UNDEFINED);
        cell.setCellEvent(cellEvent);
        table.addCell(cell);
    }

    /**
     * 建立 Cell
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param str         欄位內容字串
     * @param fontSize    字體大小
     * @param fontStyle   字體樣式
     * @param borderWidth 框線的寬度
     * @param hAlignment  水平對齊方式
     * @param vAlignment  垂直對齊方式
     * @param cellEvent   <code>PdfPCellEvent</code> 物件
     */
    protected void addCellAssignVAlignmentWithFontStyle(PdfPTable table, String str, int fontSize, int fontStyle, float borderWidth, int hAlignment, int vAlignment, PdfPCellEvent cellEvent) {
        PdfPCell cell = PdfPTableUtility.createPdfPCell(PdfPTableUtility.SPAN_UNDEFINED, PdfPTableUtility.SPAN_UNDEFINED, str, getFont(fontSize, fontStyle), borderWidth, Rectangle.UNDEFINED, hAlignment, vAlignment, PdfPTableUtility.LINESPACE_UNDEFINED);
        cell.setCellEvent(cellEvent);
        table.addCell(cell);
    }

    /**
     * 建立 Cell - for 全字庫
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param colspan     Column Span 若無填 <code>1</code>
     * @param rowspan     Row Span 若無填 <code>1</code>
     * @param str         欄位內容字串
     * @param cnsFont     CNS11643.SUNG 或 CNS11643.KAI
     * @param fontSize    字體大小
     * @param fontStyle   字體樣式
     * @param borderWidth 框線的寬度
     * @param hAlignment  水平對齊方式
     * @param vAlignment  垂直對齊方式
     * @param cellEvent   <code>PdfPCellEvent</code> 物件
     */
    protected void addCellAssignVAlignmentWithFontStyle(PdfPTable table, int colspan, int rowspan, String str, CNS11643 cnsFont, int fontStyle, int fontSize, float borderWidth, int hAlignment, int vAlignment, PdfPCellEvent cellEvent) {
        PdfPCell cell = PdfPTableUtility.createPdfPCell(colspan, rowspan, str, getFontSelector(cnsFont, fontSize, fontStyle), borderWidth, Rectangle.UNDEFINED, hAlignment, vAlignment, PdfPTableUtility.LINESPACE_UNDEFINED);
        cell.setCellEvent(cellEvent);
        table.addCell(cell);
    }

    /**
     * 建立 Cell - for 全字庫
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param rowspan     Row Span 若無填 <code>1</code>
     * @param str         欄位內容字串
     * @param cnsFont     CNS11643.SUNG 或 CNS11643.KAI
     * @param fontSize    字體大小
     * @param fontStyle   字體樣式
     * @param borderWidth 框線的寬度
     * @param hAlignment  水平對齊方式
     * @param vAlignment  垂直對齊方式
     * @param cellEvent   <code>PdfPCellEvent</code> 物件
     */
    protected void addCellAssignVAlignmentWithFontStyle(PdfPTable table, int rowspan, String str, CNS11643 cnsFont, int fontSize, int fontStyle, float borderWidth, int hAlignment, int vAlignment, PdfPCellEvent cellEvent) {
        PdfPCell cell = PdfPTableUtility.createPdfPCell(PdfPTableUtility.SPAN_UNDEFINED, rowspan, str, getFontSelector(cnsFont, fontSize, fontStyle), borderWidth, Rectangle.UNDEFINED, hAlignment, vAlignment, PdfPTableUtility.LINESPACE_UNDEFINED);
        cell.setCellEvent(cellEvent);
        table.addCell(cell);
    }

    /**
     * 建立 Cell - for 全字庫
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param str         欄位內容字串
     * @param cnsFont     CNS11643.SUNG 或 CNS11643.KAI
     * @param fontSize    字體大小
     * @param fontStyle   字體樣式
     * @param borderWidth 框線的寬度
     * @param hAlignment  水平對齊方式
     * @param vAlignment  垂直對齊方式
     * @param cellEvent   <code>PdfPCellEvent</code> 物件
     */
    protected void addCellAssignVAlignmentWithFontStyle(PdfPTable table, String str, CNS11643 cnsFont, int fontSize, int fontStyle, float borderWidth, int hAlignment, int vAlignment, PdfPCellEvent cellEvent) {
        PdfPCell cell = PdfPTableUtility.createPdfPCell(PdfPTableUtility.SPAN_UNDEFINED, PdfPTableUtility.SPAN_UNDEFINED, str, getFontSelector(cnsFont, fontSize, fontStyle), borderWidth, Rectangle.UNDEFINED, hAlignment, vAlignment, PdfPTableUtility.LINESPACE_UNDEFINED);
        cell.setCellEvent(cellEvent);
        table.addCell(cell);
    }

    /**
     * 建立 Cell
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param colspan     Column Span 若無填 <code>1</code>
     * @param rowspan     Row Span 若無填 <code>1</code>
     * @param str         欄位內容字串
     * @param fontSize    字體大小
     * @param borderWidth 框線的寬度
     * @param hAlignment  水平對齊方式
     * @param vAlignment  垂直對齊方式
     * @param linespace   行距
     */
    protected void addCellAssignVAlignmentAndLinespace(PdfPTable table, int colspan, int rowspan, String str, int fontSize, float borderWidth, int hAlignment, int vAlignment, int linespace) {
        table.addCell(PdfPTableUtility.createPdfPCell(colspan, rowspan, str, getFont(fontSize), borderWidth, Rectangle.UNDEFINED, hAlignment, vAlignment, linespace));
    }

    /**
     * 建立 Cell
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param rowspan     Row Span 若無填 <code>1</code>
     * @param str         欄位內容字串
     * @param fontSize    字體大小
     * @param borderWidth 框線的寬度
     * @param hAlignment  水平對齊方式
     * @param vAlignment  垂直對齊方式
     * @param linespace   行距
     */
    protected void addCellAssignVAlignmentAndLinespace(PdfPTable table, int rowspan, String str, int fontSize, float borderWidth, int hAlignment, int vAlignment, int linespace) {
        table.addCell(PdfPTableUtility.createPdfPCell(PdfPTableUtility.SPAN_UNDEFINED, rowspan, str, getFont(fontSize), borderWidth, Rectangle.UNDEFINED, hAlignment, vAlignment, linespace));
    }

    /**
     * 建立 Cell
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param str         欄位內容字串
     * @param fontSize    字體大小
     * @param borderWidth 框線的寬度
     * @param hAlignment  水平對齊方式
     * @param vAlignment  垂直對齊方式
     * @param linespace   行距
     */
    protected void addCellAssignVAlignmentAndLinespace(PdfPTable table, String str, int fontSize, float borderWidth, int hAlignment, int vAlignment, int linespace) {
        table.addCell(PdfPTableUtility.createPdfPCell(PdfPTableUtility.SPAN_UNDEFINED, PdfPTableUtility.SPAN_UNDEFINED, str, getFont(fontSize), borderWidth, Rectangle.UNDEFINED, hAlignment, vAlignment, linespace));
    }

    /**
     * 建立 Cell
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param colspan     Column Span 若無填 <code>1</code>
     * @param rowspan     Row Span 若無填 <code>1</code>
     * @param str         欄位內容字串
     * @param font        字體
     * @param borderWidth 框線的寬度
     * @param hAlignment  水平對齊方式
     * @param vAlignment  垂直對齊方式
     * @param linespace   行距
     */
    protected void addCellAssignVAlignmentAndLinespace(PdfPTable table, int colspan, int rowspan, String str, Font font, float borderWidth, int hAlignment, int vAlignment, int linespace) {
        table.addCell(PdfPTableUtility.createPdfPCell(colspan, rowspan, str, font, borderWidth, Rectangle.UNDEFINED, hAlignment, vAlignment, linespace));
    }

    /**
     * 建立 Cell
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param rowspan     Row Span 若無填 <code>1</code>
     * @param str         欄位內容字串
     * @param font        字體
     * @param borderWidth 框線的寬度
     * @param hAlignment  水平對齊方式
     * @param vAlignment  垂直對齊方式
     * @param linespace   行距
     */
    protected void addCellAssignVAlignmentAndLinespace(PdfPTable table, int rowspan, String str, Font font, float borderWidth, int hAlignment, int vAlignment, int linespace) {
        table.addCell(PdfPTableUtility.createPdfPCell(PdfPTableUtility.SPAN_UNDEFINED, rowspan, str, font, borderWidth, Rectangle.UNDEFINED, hAlignment, vAlignment, linespace));
    }

    /**
     * 建立 Cell
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param str         欄位內容字串
     * @param font        字體大小
     * @param borderWidth 框線的寬度
     * @param hAlignment  水平對齊方式
     * @param vAlignment  垂直對齊方式
     * @param linespace   行距
     */
    protected void addCellAssignVAlignmentAndLinespace(PdfPTable table, String str, Font font, float borderWidth, int hAlignment, int vAlignment, int linespace) {
        table.addCell(PdfPTableUtility.createPdfPCell(PdfPTableUtility.SPAN_UNDEFINED, PdfPTableUtility.SPAN_UNDEFINED, str, font, borderWidth, Rectangle.UNDEFINED, hAlignment, vAlignment, linespace));
    }

    /**
     * 建立 Cell - for 全字庫
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param colspan     Column Span 若無填 <code>1</code>
     * @param rowspan     Row Span 若無填 <code>1</code>
     * @param str         欄位內容字串
     * @param cnsFont     CNS11643.SUNG 或 CNS11643.KAI
     * @param fontSize    字體大小
     * @param borderWidth 框線的寬度
     * @param hAlignment  水平對齊方式
     * @param vAlignment  垂直對齊方式
     * @param linespace   行距
     */
    protected void addCellAssignVAlignmentAndLinespace(PdfPTable table, int colspan, int rowspan, String str, CNS11643 cnsFont, int fontSize, float borderWidth, int hAlignment, int vAlignment, int linespace) {
        table.addCell(PdfPTableUtility.createPdfPCell(colspan, rowspan, str, getFontSelector(cnsFont, fontSize), borderWidth, Rectangle.UNDEFINED, hAlignment, vAlignment, linespace));
    }

    /**
     * 建立 Cell - for 全字庫
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param rowspan     Row Span 若無填 <code>1</code>
     * @param str         欄位內容字串
     * @param cnsFont     CNS11643.SUNG 或 CNS11643.KAI
     * @param fontSize    字體大小
     * @param borderWidth 框線的寬度
     * @param hAlignment  水平對齊方式
     * @param vAlignment  垂直對齊方式
     * @param linespace   行距
     */
    protected void addCellAssignVAlignmentAndLinespace(PdfPTable table, int rowspan, String str, CNS11643 cnsFont, int fontSize, float borderWidth, int hAlignment, int vAlignment, int linespace) {
        table.addCell(PdfPTableUtility.createPdfPCell(PdfPTableUtility.SPAN_UNDEFINED, rowspan, str, getFontSelector(cnsFont, fontSize), borderWidth, Rectangle.UNDEFINED, hAlignment, vAlignment, linespace));
    }

    /**
     * 建立 Cell - for 全字庫
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param str         欄位內容字串
     * @param cnsFont     CNS11643.SUNG 或 CNS11643.KAI
     * @param fontSize    字體大小
     * @param borderWidth 框線的寬度
     * @param hAlignment  水平對齊方式
     * @param vAlignment  垂直對齊方式
     * @param linespace   行距
     */
    protected void addCellAssignVAlignmentAndLinespace(PdfPTable table, String str, CNS11643 cnsFont, int fontSize, float borderWidth, int hAlignment, int vAlignment, int linespace) {
        table.addCell(PdfPTableUtility.createPdfPCell(PdfPTableUtility.SPAN_UNDEFINED, PdfPTableUtility.SPAN_UNDEFINED, str, getFontSelector(cnsFont, fontSize), borderWidth, Rectangle.UNDEFINED, hAlignment, vAlignment, linespace));
    }

    /**
     * 建立 Cell
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param colspan     Column Span 若無填 <code>1</code>
     * @param rowspan     Row Span 若無填 <code>1</code>
     * @param str         欄位內容字串
     * @param fontSize    字體大小
     * @param fontStyle   字體樣式
     * @param borderWidth 框線的寬度
     * @param hAlignment  水平對齊方式
     * @param vAlignment  垂直對齊方式
     * @param linespace   行距
     */
    protected void addCellAssignVAlignmentAndLinespaceWithFontStyle(PdfPTable table, int colspan, int rowspan, String str, int fontStyle, int fontSize, float borderWidth, int hAlignment, int vAlignment, int linespace) {
        table.addCell(PdfPTableUtility.createPdfPCell(colspan, rowspan, str, getFont(fontSize, fontStyle), borderWidth, Rectangle.UNDEFINED, hAlignment, vAlignment, linespace));
    }

    /**
     * 建立 Cell
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param rowspan     Row Span 若無填 <code>1</code>
     * @param str         欄位內容字串
     * @param fontSize    字體大小
     * @param fontStyle   字體樣式
     * @param borderWidth 框線的寬度
     * @param hAlignment  水平對齊方式
     * @param vAlignment  垂直對齊方式
     * @param linespace   行距
     */
    protected void addCellAssignVAlignmentAndLinespaceWithFontStyle(PdfPTable table, int rowspan, String str, int fontSize, int fontStyle, float borderWidth, int hAlignment, int vAlignment, int linespace) {
        table.addCell(PdfPTableUtility.createPdfPCell(PdfPTableUtility.SPAN_UNDEFINED, rowspan, str, getFont(fontSize, fontStyle), borderWidth, Rectangle.UNDEFINED, hAlignment, vAlignment, linespace));
    }

    /**
     * 建立 Cell
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param str         欄位內容字串
     * @param fontSize    字體大小
     * @param fontStyle   字體樣式
     * @param borderWidth 框線的寬度
     * @param hAlignment  水平對齊方式
     * @param vAlignment  垂直對齊方式
     * @param linespace   行距
     */
    protected void addCellAssignVAlignmentAndLinespaceWithFontStyle(PdfPTable table, String str, int fontSize, int fontStyle, float borderWidth, int hAlignment, int vAlignment, int linespace) {
        table.addCell(PdfPTableUtility.createPdfPCell(PdfPTableUtility.SPAN_UNDEFINED, PdfPTableUtility.SPAN_UNDEFINED, str, getFont(fontSize, fontStyle), borderWidth, Rectangle.UNDEFINED, hAlignment, vAlignment, linespace));
    }

    /**
     * 建立 Cell - for 全字庫
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param colspan     Column Span 若無填 <code>1</code>
     * @param rowspan     Row Span 若無填 <code>1</code>
     * @param str         欄位內容字串
     * @param cnsFont     CNS11643.SUNG 或 CNS11643.KAI
     * @param fontSize    字體大小
     * @param fontStyle   字體樣式
     * @param borderWidth 框線的寬度
     * @param hAlignment  水平對齊方式
     * @param vAlignment  垂直對齊方式
     * @param linespace   行距
     */
    protected void addCellAssignVAlignmentAndLinespaceWithFontStyle(PdfPTable table, int colspan, int rowspan, String str, CNS11643 cnsFont, int fontStyle, int fontSize, float borderWidth, int hAlignment, int vAlignment, int linespace) {
        table.addCell(PdfPTableUtility.createPdfPCell(colspan, rowspan, str, getFontSelector(cnsFont, fontSize, fontStyle), borderWidth, Rectangle.UNDEFINED, hAlignment, vAlignment, linespace));
    }

    /**
     * 建立 Cell - for 全字庫
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param rowspan     Row Span 若無填 <code>1</code>
     * @param str         欄位內容字串
     * @param cnsFont     CNS11643.SUNG 或 CNS11643.KAI
     * @param fontSize    字體大小
     * @param fontStyle   字體樣式
     * @param borderWidth 框線的寬度
     * @param hAlignment  水平對齊方式
     * @param vAlignment  垂直對齊方式
     * @param linespace   行距
     */
    protected void addCellAssignVAlignmentAndLinespaceWithFontStyle(PdfPTable table, int rowspan, String str, CNS11643 cnsFont, int fontSize, int fontStyle, float borderWidth, int hAlignment, int vAlignment, int linespace) {
        table.addCell(PdfPTableUtility.createPdfPCell(PdfPTableUtility.SPAN_UNDEFINED, rowspan, str, getFontSelector(cnsFont, fontSize, fontStyle), borderWidth, Rectangle.UNDEFINED, hAlignment, vAlignment, linespace));
    }

    /**
     * 建立 Cell - for 全字庫
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param str         欄位內容字串
     * @param cnsFont     CNS11643.SUNG 或 CNS11643.KAI
     * @param fontSize    字體大小
     * @param fontStyle   字體樣式
     * @param borderWidth 框線的寬度
     * @param hAlignment  水平對齊方式
     * @param vAlignment  垂直對齊方式
     * @param linespace   行距
     */
    protected void addCellAssignVAlignmentAndLinespaceWithFontStyle(PdfPTable table, String str, CNS11643 cnsFont, int fontSize, int fontStyle, float borderWidth, int hAlignment, int vAlignment, int linespace) {
        table.addCell(PdfPTableUtility.createPdfPCell(PdfPTableUtility.SPAN_UNDEFINED, PdfPTableUtility.SPAN_UNDEFINED, str, getFontSelector(cnsFont, fontSize, fontStyle), borderWidth, Rectangle.UNDEFINED, hAlignment, vAlignment, linespace));
    }

    /**
     * 建立 Cell
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param colspan     Column Span 若無填 <code>1</code>
     * @param rowspan     Row Span 若無填 <code>1</code>
     * @param str         欄位內容字串
     * @param fontSize    字體大小
     * @param borderWidth 框線的寬度
     * @param hAlignment  水平對齊方式
     * @param vAlignment  垂直對齊方式
     * @param linespace   行距
     * @param cellEvent   <code>PdfPCellEvent</code> 物件
     */
    protected void addCellAssignVAlignmentAndLinespace(PdfPTable table, int colspan, int rowspan, String str, int fontSize, float borderWidth, int hAlignment, int vAlignment, int linespace, PdfPCellEvent cellEvent) {
        PdfPCell cell = PdfPTableUtility.createPdfPCell(colspan, rowspan, str, getFont(fontSize), borderWidth, Rectangle.UNDEFINED, hAlignment, vAlignment, linespace);
        cell.setCellEvent(cellEvent);
        table.addCell(cell);
    }

    /**
     * 建立 Cell
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param rowspan     Row Span 若無填 <code>1</code>
     * @param str         欄位內容字串
     * @param fontSize    字體大小
     * @param borderWidth 框線的寬度
     * @param hAlignment  水平對齊方式
     * @param vAlignment  垂直對齊方式
     * @param linespace   行距
     * @param cellEvent   <code>PdfPCellEvent</code> 物件
     */
    protected void addCellAssignVAlignmentAndLinespace(PdfPTable table, int rowspan, String str, int fontSize, float borderWidth, int hAlignment, int vAlignment, int linespace, PdfPCellEvent cellEvent) {
        PdfPCell cell = PdfPTableUtility.createPdfPCell(PdfPTableUtility.SPAN_UNDEFINED, rowspan, str, getFont(fontSize), borderWidth, Rectangle.UNDEFINED, hAlignment, vAlignment, linespace);
        cell.setCellEvent(cellEvent);
        table.addCell(cell);
    }

    /**
     * 建立 Cell
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param str         欄位內容字串
     * @param fontSize    字體大小
     * @param borderWidth 框線的寬度
     * @param hAlignment  水平對齊方式
     * @param vAlignment  垂直對齊方式
     * @param linespace   行距
     * @param cellEvent   <code>PdfPCellEvent</code> 物件
     */
    protected void addCellAssignVAlignmentAndLinespace(PdfPTable table, String str, int fontSize, float borderWidth, int hAlignment, int vAlignment, int linespace, PdfPCellEvent cellEvent) {
        PdfPCell cell = PdfPTableUtility.createPdfPCell(PdfPTableUtility.SPAN_UNDEFINED, PdfPTableUtility.SPAN_UNDEFINED, str, getFont(fontSize), borderWidth, Rectangle.UNDEFINED, hAlignment, vAlignment, linespace);
        cell.setCellEvent(cellEvent);
        table.addCell(cell);
    }

    /**
     * 建立 Cell
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param colspan     Column Span 若無填 <code>1</code>
     * @param rowspan     Row Span 若無填 <code>1</code>
     * @param str         欄位內容字串
     * @param font        字體
     * @param borderWidth 框線的寬度
     * @param hAlignment  水平對齊方式
     * @param vAlignment  垂直對齊方式
     * @param linespace   行距
     * @param cellEvent   <code>PdfPCellEvent</code> 物件
     */
    protected void addCellAssignVAlignmentAndLinespace(PdfPTable table, int colspan, int rowspan, String str, Font font, float borderWidth, int hAlignment, int vAlignment, int linespace, PdfPCellEvent cellEvent) {
        PdfPCell cell = PdfPTableUtility.createPdfPCell(colspan, rowspan, str, font, borderWidth, Rectangle.UNDEFINED, hAlignment, vAlignment, linespace);
        cell.setCellEvent(cellEvent);
        table.addCell(cell);
    }

    /**
     * 建立 Cell
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param rowspan     Row Span 若無填 <code>1</code>
     * @param str         欄位內容字串
     * @param font        字體
     * @param borderWidth 框線的寬度
     * @param hAlignment  水平對齊方式
     * @param vAlignment  垂直對齊方式
     * @param linespace   行距
     * @param cellEvent   <code>PdfPCellEvent</code> 物件
     */
    protected void addCellAssignVAlignmentAndLinespace(PdfPTable table, int rowspan, String str, Font font, float borderWidth, int hAlignment, int vAlignment, int linespace, PdfPCellEvent cellEvent) {
        PdfPCell cell = PdfPTableUtility.createPdfPCell(PdfPTableUtility.SPAN_UNDEFINED, rowspan, str, font, borderWidth, Rectangle.UNDEFINED, hAlignment, vAlignment, linespace);
        cell.setCellEvent(cellEvent);
        table.addCell(cell);
    }

    /**
     * 建立 Cell
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param str         欄位內容字串
     * @param font        字體大小
     * @param borderWidth 框線的寬度
     * @param hAlignment  水平對齊方式
     * @param vAlignment  垂直對齊方式
     * @param linespace   行距
     * @param cellEvent   <code>PdfPCellEvent</code> 物件
     */
    protected void addCellAssignVAlignmentAndLinespace(PdfPTable table, String str, Font font, float borderWidth, int hAlignment, int vAlignment, int linespace, PdfPCellEvent cellEvent) {
        PdfPCell cell = PdfPTableUtility.createPdfPCell(PdfPTableUtility.SPAN_UNDEFINED, PdfPTableUtility.SPAN_UNDEFINED, str, font, borderWidth, Rectangle.UNDEFINED, hAlignment, vAlignment, linespace);
        cell.setCellEvent(cellEvent);
        table.addCell(cell);
    }

    /**
     * 建立 Cell - for 全字庫
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param colspan     Column Span 若無填 <code>1</code>
     * @param rowspan     Row Span 若無填 <code>1</code>
     * @param str         欄位內容字串
     * @param cnsFont     CNS11643.SUNG 或 CNS11643.KAI
     * @param fontSize    字體大小
     * @param borderWidth 框線的寬度
     * @param hAlignment  水平對齊方式
     * @param vAlignment  垂直對齊方式
     * @param linespace   行距
     * @param cellEvent   <code>PdfPCellEvent</code> 物件
     */
    protected void addCellAssignVAlignmentAndLinespace(PdfPTable table, int colspan, int rowspan, String str, CNS11643 cnsFont, int fontSize, float borderWidth, int hAlignment, int vAlignment, int linespace, PdfPCellEvent cellEvent) {
        PdfPCell cell = PdfPTableUtility.createPdfPCell(colspan, rowspan, str, getFontSelector(cnsFont, fontSize), borderWidth, Rectangle.UNDEFINED, hAlignment, vAlignment, linespace);
        cell.setCellEvent(cellEvent);
        table.addCell(cell);
    }

    /**
     * 建立 Cell - for 全字庫
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param rowspan     Row Span 若無填 <code>1</code>
     * @param str         欄位內容字串
     * @param cnsFont     CNS11643.SUNG 或 CNS11643.KAI
     * @param fontSize    字體大小
     * @param borderWidth 框線的寬度
     * @param hAlignment  水平對齊方式
     * @param vAlignment  垂直對齊方式
     * @param linespace   行距
     * @param cellEvent   <code>PdfPCellEvent</code> 物件
     */
    protected void addCellAssignVAlignmentAndLinespace(PdfPTable table, int rowspan, String str, CNS11643 cnsFont, int fontSize, float borderWidth, int hAlignment, int vAlignment, int linespace, PdfPCellEvent cellEvent) {
        PdfPCell cell = PdfPTableUtility.createPdfPCell(PdfPTableUtility.SPAN_UNDEFINED, rowspan, str, getFontSelector(cnsFont, fontSize), borderWidth, Rectangle.UNDEFINED, hAlignment, vAlignment, linespace);
        cell.setCellEvent(cellEvent);
        table.addCell(cell);
    }

    /**
     * 建立 Cell - for 全字庫
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param str         欄位內容字串
     * @param cnsFont     CNS11643.SUNG 或 CNS11643.KAI
     * @param fontSize    字體大小
     * @param borderWidth 框線的寬度
     * @param hAlignment  水平對齊方式
     * @param vAlignment  垂直對齊方式
     * @param linespace   行距
     * @param cellEvent   <code>PdfPCellEvent</code> 物件
     */
    protected void addCellAssignVAlignmentAndLinespace(PdfPTable table, String str, CNS11643 cnsFont, int fontSize, float borderWidth, int hAlignment, int vAlignment, int linespace, PdfPCellEvent cellEvent) {
        PdfPCell cell = PdfPTableUtility.createPdfPCell(PdfPTableUtility.SPAN_UNDEFINED, PdfPTableUtility.SPAN_UNDEFINED, str, getFontSelector(cnsFont, fontSize), borderWidth, Rectangle.UNDEFINED, hAlignment, vAlignment, linespace);
        cell.setCellEvent(cellEvent);
        table.addCell(cell);
    }

    /**
     * 建立 Cell
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param colspan     Column Span 若無填 <code>1</code>
     * @param rowspan     Row Span 若無填 <code>1</code>
     * @param str         欄位內容字串
     * @param fontSize    字體大小
     * @param fontStyle   字體樣式
     * @param borderWidth 框線的寬度
     * @param hAlignment  水平對齊方式
     * @param vAlignment  垂直對齊方式
     * @param linespace   行距
     * @param cellEvent   <code>PdfPCellEvent</code> 物件
     */
    protected void addCellAssignVAlignmentAndLinespaceWithFontStyle(PdfPTable table, int colspan, int rowspan, String str, int fontStyle, int fontSize, float borderWidth, int hAlignment, int vAlignment, int linespace, PdfPCellEvent cellEvent) {
        PdfPCell cell = PdfPTableUtility.createPdfPCell(colspan, rowspan, str, getFont(fontSize, fontStyle), borderWidth, Rectangle.UNDEFINED, hAlignment, vAlignment, linespace);
        cell.setCellEvent(cellEvent);
        table.addCell(cell);
    }

    /**
     * 建立 Cell
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param rowspan     Row Span 若無填 <code>1</code>
     * @param str         欄位內容字串
     * @param fontSize    字體大小
     * @param fontStyle   字體樣式
     * @param borderWidth 框線的寬度
     * @param hAlignment  水平對齊方式
     * @param vAlignment  垂直對齊方式
     * @param linespace   行距
     * @param cellEvent   <code>PdfPCellEvent</code> 物件
     */
    protected void addCellAssignVAlignmentAndLinespaceWithFontStyle(PdfPTable table, int rowspan, String str, int fontSize, int fontStyle, float borderWidth, int hAlignment, int vAlignment, int linespace, PdfPCellEvent cellEvent) {
        PdfPCell cell = PdfPTableUtility.createPdfPCell(PdfPTableUtility.SPAN_UNDEFINED, rowspan, str, getFont(fontSize, fontStyle), borderWidth, Rectangle.UNDEFINED, hAlignment, vAlignment, linespace);
        cell.setCellEvent(cellEvent);
        table.addCell(cell);
    }

    /**
     * 建立 Cell
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param str         欄位內容字串
     * @param fontSize    字體大小
     * @param fontStyle   字體樣式
     * @param borderWidth 框線的寬度
     * @param hAlignment  水平對齊方式
     * @param vAlignment  垂直對齊方式
     * @param linespace   行距
     * @param cellEvent   <code>PdfPCellEvent</code> 物件
     */
    protected void addCellAssignVAlignmentAndLinespaceWithFontStyle(PdfPTable table, String str, int fontSize, int fontStyle, float borderWidth, int hAlignment, int vAlignment, int linespace, PdfPCellEvent cellEvent) {
        PdfPCell cell = PdfPTableUtility.createPdfPCell(PdfPTableUtility.SPAN_UNDEFINED, PdfPTableUtility.SPAN_UNDEFINED, str, getFont(fontSize, fontStyle), borderWidth, Rectangle.UNDEFINED, hAlignment, vAlignment, linespace);
        cell.setCellEvent(cellEvent);
        table.addCell(cell);
    }

    /**
     * 建立 Cell - for 全字庫
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param colspan     Column Span 若無填 <code>1</code>
     * @param rowspan     Row Span 若無填 <code>1</code>
     * @param str         欄位內容字串
     * @param cnsFont     CNS11643.SUNG 或 CNS11643.KAI
     * @param fontSize    字體大小
     * @param fontStyle   字體樣式
     * @param borderWidth 框線的寬度
     * @param hAlignment  水平對齊方式
     * @param vAlignment  垂直對齊方式
     * @param linespace   行距
     * @param cellEvent   <code>PdfPCellEvent</code> 物件
     */
    protected void addCellAssignVAlignmentAndLinespaceWithFontStyle(PdfPTable table, int colspan, int rowspan, String str, CNS11643 cnsFont, int fontStyle, int fontSize, float borderWidth, int hAlignment, int vAlignment, int linespace, PdfPCellEvent cellEvent) {
        PdfPCell cell = PdfPTableUtility.createPdfPCell(colspan, rowspan, str, getFontSelector(cnsFont, fontSize, fontStyle), borderWidth, Rectangle.UNDEFINED, hAlignment, vAlignment, linespace);
        cell.setCellEvent(cellEvent);
        table.addCell(cell);
    }

    /**
     * 建立 Cell - for 全字庫
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param rowspan     Row Span 若無填 <code>1</code>
     * @param str         欄位內容字串
     * @param cnsFont     CNS11643.SUNG 或 CNS11643.KAI
     * @param fontSize    字體大小
     * @param fontStyle   字體樣式
     * @param borderWidth 框線的寬度
     * @param hAlignment  水平對齊方式
     * @param vAlignment  垂直對齊方式
     * @param linespace   行距
     * @param cellEvent   <code>PdfPCellEvent</code> 物件
     */
    protected void addCellAssignVAlignmentAndLinespaceWithFontStyle(PdfPTable table, int rowspan, String str, CNS11643 cnsFont, int fontSize, int fontStyle, float borderWidth, int hAlignment, int vAlignment, int linespace, PdfPCellEvent cellEvent) {
        PdfPCell cell = PdfPTableUtility.createPdfPCell(PdfPTableUtility.SPAN_UNDEFINED, rowspan, str, getFontSelector(cnsFont, fontSize, fontStyle), borderWidth, Rectangle.UNDEFINED, hAlignment, vAlignment, linespace);
        cell.setCellEvent(cellEvent);
        table.addCell(cell);
    }

    /**
     * 建立 Cell - for 全字庫
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param str         欄位內容字串
     * @param cnsFont     CNS11643.SUNG 或 CNS11643.KAI
     * @param fontSize    字體大小
     * @param fontStyle   字體樣式
     * @param borderWidth 框線的寬度
     * @param hAlignment  水平對齊方式
     * @param vAlignment  垂直對齊方式
     * @param linespace   行距
     * @param cellEvent   <code>PdfPCellEvent</code> 物件
     */
    protected void addCellAssignVAlignmentAndLinespaceWithFontStyle(PdfPTable table, String str, CNS11643 cnsFont, int fontSize, int fontStyle, float borderWidth, int hAlignment, int vAlignment, int linespace, PdfPCellEvent cellEvent) {
        PdfPCell cell = PdfPTableUtility.createPdfPCell(PdfPTableUtility.SPAN_UNDEFINED, PdfPTableUtility.SPAN_UNDEFINED, str, getFontSelector(cnsFont, fontSize, fontStyle), borderWidth, Rectangle.UNDEFINED, hAlignment, vAlignment, linespace);
        cell.setCellEvent(cellEvent);
        table.addCell(cell);
    }

    /**
     * 建立 Cell
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param colspan     Column Span 若無填 <code>1</code>
     * @param rowspan     Row Span 若無填 <code>1</code>
     * @param str         欄位內容字串
     * @param fontSize    字體大小
     * @param borderWidth 框線的寬度
     * @param borderType  框線的樣式
     * @param hAlignment  水平對齊方式
     * @param vAlignment  垂直對齊方式
     * @param lineSpace   行距
     */
    protected void addCellFullParam(PdfPTable table, int colspan, int rowspan, String str, int fontSize, float borderWidth, int borderType, int hAlignment, int vAlignment, int lineSpace) {
        table.addCell(PdfPTableUtility.createPdfPCell(colspan, rowspan, str, getFont(fontSize), borderWidth, borderType, hAlignment, vAlignment, lineSpace));
    }

    /**
     * 建立 Cell
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param colspan     Column Span 若無填 <code>1</code>
     * @param rowspan     Row Span 若無填 <code>1</code>
     * @param str         欄位內容字串
     * @param font        字體
     * @param borderWidth 框線的寬度
     * @param borderType  框線的樣式
     * @param hAlignment  水平對齊方式
     * @param vAlignment  垂直對齊方式
     * @param lineSpace   行距
     */
    protected void addCellFullParam(PdfPTable table, int colspan, int rowspan, String str, Font font, float borderWidth, int borderType, int hAlignment, int vAlignment, int lineSpace) {
        table.addCell(PdfPTableUtility.createPdfPCell(colspan, rowspan, str, font, borderWidth, borderType, hAlignment, vAlignment, lineSpace));
    }

    /**
     * 建立 Cell - for 全字庫
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param colspan     Column Span 若無填 <code>1</code>
     * @param rowspan     Row Span 若無填 <code>1</code>
     * @param str         欄位內容字串
     * @param cnsFont     CNS11643.SUNG 或 CNS11643.KAI
     * @param fontSize    字體大小
     * @param borderWidth 框線的寬度
     * @param borderType  框線的樣式
     * @param hAlignment  水平對齊方式
     * @param vAlignment  垂直對齊方式
     * @param lineSpace   行距
     */
    protected void addCellFullParam(PdfPTable table, int colspan, int rowspan, String str, CNS11643 cnsFont, int fontSize, float borderWidth, int borderType, int hAlignment, int vAlignment, int lineSpace) {
        table.addCell(PdfPTableUtility.createPdfPCell(colspan, rowspan, str, getFontSelector(cnsFont, fontSize), borderWidth, borderType, hAlignment, vAlignment, fontSize + lineSpace));
    }

    /**
     * 建立 Cell
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param colspan     Column Span 若無填 <code>1</code>
     * @param rowspan     Row Span 若無填 <code>1</code>
     * @param str         欄位內容字串
     * @param fontSize    字體大小
     * @param fontStyle   字體樣式
     * @param borderWidth 框線的寬度
     * @param borderType  框線的樣式
     * @param hAlignment  水平對齊方式
     * @param vAlignment  垂直對齊方式
     * @param lineSpace   行距
     */
    protected void addCellFullParamWithFontStyle(PdfPTable table, int colspan, int rowspan, String str, int fontSize, int fontStyle, float borderWidth, int borderType, int hAlignment, int vAlignment, int lineSpace) {
        table.addCell(PdfPTableUtility.createPdfPCell(colspan, rowspan, str, getFont(fontSize, fontStyle), borderWidth, borderType, hAlignment, vAlignment, lineSpace));
    }

    /**
     * 建立 Cell - for 全字庫
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param colspan     Column Span 若無填 <code>1</code>
     * @param rowspan     Row Span 若無填 <code>1</code>
     * @param str         欄位內容字串
     * @param cnsFont     CNS11643.SUNG 或 CNS11643.KAI
     * @param fontSize    字體大小
     * @param fontStyle   字體樣式
     * @param borderWidth 框線的寬度
     * @param borderType  框線的樣式
     * @param hAlignment  水平對齊方式
     * @param vAlignment  垂直對齊方式
     * @param lineSpace   行距
     */
    protected void addCellFullParamWithFontStyle(PdfPTable table, int colspan, int rowspan, String str, CNS11643 cnsFont, int fontSize, int fontStyle, float borderWidth, int borderType, int hAlignment, int vAlignment, int lineSpace) {
        table.addCell(PdfPTableUtility.createPdfPCell(colspan, rowspan, str, getFontSelector(cnsFont, fontSize, fontStyle), borderWidth, borderType, hAlignment, vAlignment, fontSize + lineSpace));
    }

    /**
     * 建立 Cell
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param colspan     Column Span 若無填 <code>1</code>
     * @param rowspan     Row Span 若無填 <code>1</code>
     * @param str         欄位內容字串
     * @param fontSize    字體大小
     * @param borderWidth 框線的寬度
     * @param borderType  框線的樣式
     * @param hAlignment  水平對齊方式
     * @param vAlignment  垂直對齊方式
     * @param lineSpace   行距
     * @param cellEvent   <code>PdfPCellEvent</code> 物件
     */
    protected void addCellFullParam(PdfPTable table, int colspan, int rowspan, String str, int fontSize, float borderWidth, int borderType, int hAlignment, int vAlignment, int lineSpace, PdfPCellEvent cellEvent) {
        PdfPCell cell = PdfPTableUtility.createPdfPCell(colspan, rowspan, str, getFont(fontSize), borderWidth, borderType, hAlignment, vAlignment, lineSpace);
        cell.setCellEvent(cellEvent);
        table.addCell(cell);
    }

    /**
     * 建立 Cell
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param colspan     Column Span 若無填 <code>1</code>
     * @param rowspan     Row Span 若無填 <code>1</code>
     * @param str         欄位內容字串
     * @param font        字體
     * @param borderWidth 框線的寬度
     * @param borderType  框線的樣式
     * @param hAlignment  水平對齊方式
     * @param vAlignment  垂直對齊方式
     * @param lineSpace   行距
     * @param cellEvent   <code>PdfPCellEvent</code> 物件
     */
    protected void addCellFullParam(PdfPTable table, int colspan, int rowspan, String str, Font font, float borderWidth, int borderType, int hAlignment, int vAlignment, int lineSpace, PdfPCellEvent cellEvent) {
        PdfPCell cell = PdfPTableUtility.createPdfPCell(colspan, rowspan, str, font, borderWidth, borderType, hAlignment, vAlignment, lineSpace);
        cell.setCellEvent(cellEvent);
        table.addCell(cell);
    }

    /**
     * 建立 Cell - for 全字庫
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param colspan     Column Span 若無填 <code>1</code>
     * @param rowspan     Row Span 若無填 <code>1</code>
     * @param str         欄位內容字串
     * @param cnsFont     CNS11643.SUNG 或 CNS11643.KAI
     * @param fontSize    字體大小
     * @param borderWidth 框線的寬度
     * @param borderType  框線的樣式
     * @param hAlignment  水平對齊方式
     * @param vAlignment  垂直對齊方式
     * @param lineSpace   行距
     * @param cellEvent   <code>PdfPCellEvent</code> 物件
     */
    protected void addCellFullParam(PdfPTable table, int colspan, int rowspan, String str, CNS11643 cnsFont, int fontSize, float borderWidth, int borderType, int hAlignment, int vAlignment, int lineSpace, PdfPCellEvent cellEvent) {
        PdfPCell cell = PdfPTableUtility.createPdfPCell(colspan, rowspan, str, getFontSelector(cnsFont, fontSize), borderWidth, borderType, hAlignment, vAlignment, fontSize + lineSpace);
        cell.setCellEvent(cellEvent);
        table.addCell(cell);
    }

    /**
     * 建立 Cell
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param colspan     Column Span 若無填 <code>1</code>
     * @param rowspan     Row Span 若無填 <code>1</code>
     * @param str         欄位內容字串
     * @param fontSize    字體大小
     * @param fontStyle   字體樣式
     * @param borderWidth 框線的寬度
     * @param borderType  框線的樣式
     * @param hAlignment  水平對齊方式
     * @param vAlignment  垂直對齊方式
     * @param lineSpace   行距
     * @param cellEvent   <code>PdfPCellEvent</code> 物件
     */
    protected void addCellFullParamWithFontStyle(PdfPTable table, int colspan, int rowspan, String str, int fontSize, int fontStyle, float borderWidth, int borderType, int hAlignment, int vAlignment, int lineSpace, PdfPCellEvent cellEvent) {
        PdfPCell cell = PdfPTableUtility.createPdfPCell(colspan, rowspan, str, getFont(fontSize, fontStyle), borderWidth, borderType, hAlignment, vAlignment, lineSpace);
        cell.setCellEvent(cellEvent);
        table.addCell(cell);
    }

    /**
     * 建立 Cell - for 全字庫
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param colspan     Column Span 若無填 <code>1</code>
     * @param rowspan     Row Span 若無填 <code>1</code>
     * @param str         欄位內容字串
     * @param cnsFont     CNS11643.SUNG 或 CNS11643.KAI
     * @param fontSize    字體大小
     * @param fontStyle   字體樣式
     * @param borderWidth 框線的寬度
     * @param borderType  框線的樣式
     * @param hAlignment  水平對齊方式
     * @param vAlignment  垂直對齊方式
     * @param lineSpace   行距
     * @param cellEvent   <code>PdfPCellEvent</code> 物件
     */
    protected void addCellFullParamWithFontStyle(PdfPTable table, int colspan, int rowspan, String str, CNS11643 cnsFont, int fontSize, int fontStyle, float borderWidth, int borderType, int hAlignment, int vAlignment, int lineSpace, PdfPCellEvent cellEvent) {
        PdfPCell cell = PdfPTableUtility.createPdfPCell(colspan, rowspan, str, getFontSelector(cnsFont, fontSize, fontStyle), borderWidth, borderType, hAlignment, vAlignment, fontSize + lineSpace);
        cell.setCellEvent(cellEvent);
        table.addCell(cell);
    }

    /**
     * 建立 Cell
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param colspan     Column Span 若無填 <code>1</code>
     * @param rowspan     Row Span 若無填 <code>1</code>
     * @param image       圖片
     * @param borderWidth 框線的寬度
     * @param hAlignment  水平對齊方式
     * @param vAlignment  垂直對齊方式
     */
    protected void addImageCell(PdfPTable table, int colspan, int rowspan, Image image, float borderWidth, int hAlignment, int vAlignment) {
        table.addCell(PdfPTableUtility.createPdfPCell(colspan, rowspan, image, borderWidth, Rectangle.UNDEFINED, hAlignment, vAlignment));
    }

    /**
     * 建立 Cell
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param rowspan     Row Span 若無填 <code>1</code>
     * @param image       圖片
     * @param borderWidth 框線的寬度
     * @param hAlignment  水平對齊方式
     * @param vAlignment  垂直對齊方式
     */
    protected void addImageCell(PdfPTable table, int rowspan, Image image, float borderWidth, int hAlignment, int vAlignment) {
        table.addCell(PdfPTableUtility.createPdfPCell(PdfPTableUtility.SPAN_UNDEFINED, rowspan, image, borderWidth, Rectangle.UNDEFINED, hAlignment, vAlignment));
    }

    /**
     * 建立 Cell
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param image       圖片
     * @param borderWidth 框線的寬度
     * @param hAlignment  水平對齊方式
     * @param vAlignment  垂直對齊方式
     */
    protected void addImageCell(PdfPTable table, Image image, float borderWidth, int hAlignment, int vAlignment) {
        table.addCell(PdfPTableUtility.createPdfPCell(PdfPTableUtility.SPAN_UNDEFINED, PdfPTableUtility.SPAN_UNDEFINED, image, borderWidth, Rectangle.UNDEFINED, hAlignment, vAlignment));
    }

    /**
     * 建立 Cell
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param colspan     Column Span 若無填 <code>1</code>
     * @param rowspan     Row Span 若無填 <code>1</code>
     * @param image       圖片
     * @param borderWidth 框線的寬度
     * @param borderType  框線的樣式
     * @param hAlignment  水平對齊方式
     * @param vAlignment  垂直對齊方式
     */
    protected void addImageCell(PdfPTable table, int colspan, int rowspan, Image image, float borderWidth, int borderType, int hAlignment, int vAlignment) {
        table.addCell(PdfPTableUtility.createPdfPCell(colspan, rowspan, image, borderWidth, borderType, hAlignment, vAlignment));
    }

    /**
     * 建立 Cell
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param colspan     Column Span 若無填 <code>1</code>
     * @param rowspan     Row Span 若無填 <code>1</code>
     * @param image       圖片
     * @param borderWidth 框線的寬度
     * @param hAlignment  水平對齊方式
     * @param vAlignment  垂直對齊方式
     * @param cellEvent   <code>PdfPCellEvent</code> 物件
     */
    protected void addImageCell(PdfPTable table, int colspan, int rowspan, Image image, float borderWidth, int hAlignment, int vAlignment, PdfPCellEvent cellEvent) {
        PdfPCell cell = PdfPTableUtility.createPdfPCell(colspan, rowspan, image, borderWidth, Rectangle.UNDEFINED, hAlignment, vAlignment);
        cell.setCellEvent(cellEvent);
        table.addCell(cell);
    }

    /**
     * 建立 Cell
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param rowspan     Row Span 若無填 <code>1</code>
     * @param image       圖片
     * @param borderWidth 框線的寬度
     * @param hAlignment  水平對齊方式
     * @param vAlignment  垂直對齊方式
     * @param cellEvent   <code>PdfPCellEvent</code> 物件
     */
    protected void addImageCell(PdfPTable table, int rowspan, Image image, float borderWidth, int hAlignment, int vAlignment, PdfPCellEvent cellEvent) {
        PdfPCell cell = PdfPTableUtility.createPdfPCell(PdfPTableUtility.SPAN_UNDEFINED, rowspan, image, borderWidth, Rectangle.UNDEFINED, hAlignment, vAlignment);
        cell.setCellEvent(cellEvent);
        table.addCell(cell);
    }

    /**
     * 建立 Cell
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param image       圖片
     * @param borderWidth 框線的寬度
     * @param hAlignment  水平對齊方式
     * @param vAlignment  垂直對齊方式
     * @param cellEvent   <code>PdfPCellEvent</code> 物件
     */
    protected void addImageCell(PdfPTable table, Image image, float borderWidth, int hAlignment, int vAlignment, PdfPCellEvent cellEvent) {
        PdfPCell cell = PdfPTableUtility.createPdfPCell(PdfPTableUtility.SPAN_UNDEFINED, PdfPTableUtility.SPAN_UNDEFINED, image, borderWidth, Rectangle.UNDEFINED, hAlignment, vAlignment);
        cell.setCellEvent(cellEvent);
        table.addCell(cell);
    }

    /**
     * 建立 Cell
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param colspan     Column Span 若無填 <code>1</code>
     * @param rowspan     Row Span 若無填 <code>1</code>
     * @param image       圖片
     * @param borderWidth 框線的寬度
     * @param borderType  框線的樣式
     * @param hAlignment  水平對齊方式
     * @param vAlignment  垂直對齊方式
     * @param cellEvent   <code>PdfPCellEvent</code> 物件
     */
    protected void addImageCell(PdfPTable table, int colspan, int rowspan, Image image, float borderWidth, int borderType, int hAlignment, int vAlignment, PdfPCellEvent cellEvent) {
        PdfPCell cell = PdfPTableUtility.createPdfPCell(colspan, rowspan, image, borderWidth, borderType, hAlignment, vAlignment);
        cell.setCellEvent(cellEvent);
        table.addCell(cell);
    }

    /**
     * 將圖片置於指定的 <code>X</code> 及 <code>Y</code> 軸位置
     *
     * @param image 圖片
     * @param x     放置位置 <code>X</code> 軸
     * @param y     放置位置 <code>Y</code> 軸
     * @param cb    <code>PdfContentByte</code> 物件
     */
    protected void addImageAtXY(Image image, int x, int y, PdfContentByte cb) throws ReportException {
        try {
            // 圖片位置
            image.setAbsolutePosition(x, y);
            cb.addImage(image);
        }
        catch (Exception e) {
            log.error("Easy Report Error: " + e.getMessage());
            throw new ReportException(e);
        }
    }

    /**
     * 於目前面面位置新增書籤
     *
     * @param title  書籤標題
     * @param isOpen 側欄書籤選單的項目節點預設是否開啟
     * @return
     */
    protected PdfOutline addBookmarkHere(String title, boolean isOpen) {
        if (rootOutline == null)
            rootOutline = getWriter().getRootOutline();
        PdfOutline bookmark = new PdfOutline(rootOutline, new PdfDestination(PdfDestination.FITH, getWriter().getVerticalPosition(true)), title, isOpen);
        return bookmark;
    }

    /**
     * 於目前面面位置新增書籤
     *
     * @param parentNode 所屬節點
     * @param title      書籤標題
     * @param isOpen     側欄書籤選單的項目節點預設是否開啟
     * @return
     */
    protected PdfOutline addBookmarkHere(PdfOutline parentNode, String title, boolean isOpen) {
        if (parentNode == null) {
            if (rootOutline == null)
                rootOutline = getWriter().getRootOutline();
            PdfOutline bookmark = new PdfOutline(rootOutline, new PdfDestination(PdfDestination.FITH, getWriter().getVerticalPosition(true)), title, isOpen);
            return bookmark;
        }
        else {
            PdfOutline bookmark = new PdfOutline(parentNode, new PdfDestination(PdfDestination.FITH, getWriter().getVerticalPosition(true)), title, isOpen);
            return bookmark;
        }
    }

    /**
     * 畫線
     *
     * @param fromX     起始點 X 座標
     * @param fromY     起始點 Y 座標
     * @param toX       結束點 X 座標
     * @param toY       結束點 Y 座標
     * @param lineWidth 線寬
     */
    protected void drawLine(int fromX, int fromY, int toX, int toY, float lineWidth) {
        PdfContentByte canvas = getPdfContetByteUnder();
        canvas.saveState();
        canvas.setLineWidth(lineWidth);
        canvas.moveTo(fromX, fromY);
        canvas.lineTo(toX, toY);
        canvas.stroke();
        canvas.restoreState();
    }

    /**
     * 畫虛線
     *
     * @param fromX     起始點 X 座標
     * @param fromY     起始點 Y 座標
     * @param toX       結束點 X 座標
     * @param toY       結束點 Y 座標
     * @param lineWidth 線寬
     * @param dashWidth 虛線長度
     * @param dashSpace 虛線間格寬度
     */
    protected void drawDash(int fromX, int fromY, int toX, int toY, float lineWidth, int dashWidth, int dashSpace) {
        PdfContentByte canvas = getPdfContetByteUnder();
        canvas.saveState();
        canvas.setLineWidth(lineWidth);
        canvas.setLineDash(dashWidth, dashSpace, 0);
        canvas.moveTo(fromX, fromY);
        canvas.lineTo(toX, toY);
        canvas.stroke();
        canvas.restoreState();
    }

    /**
     * 畫字
     *
     * @param str        文字內容
     * @param x          起始點的 X 座標
     * @param y          起始點的 Y 座標
     * @param fontSize   字型大小
     * @param hAlignment 水平對齊方式
     */
    protected void drawString(String str, int x, int y, int fontSize, int hAlignment) {
        drawString(str, x, y, fontSize, Font.NORMAL, hAlignment);
    }

    /**
     * 畫字
     *
     * @param str        文字內容
     * @param x          起始點的 X 座標
     * @param y          起始點的 Y 座標
     * @param fontSize   字型大小
     * @param fontStyle  字體樣式，可為 <code>Font.NORMAL</code>、<code>Font.BOLD</code>、<code>Font.BOLDITALIC</code>、<code>Font.ITALIC</code>、<code>Font.STRIKETHRU</code>、<code>Font.UNDERLINE</code>
     * @param hAlignment 水平對齊方式
     */
    protected void drawString(String str, int x, int y, int fontSize, int fontStyle, int hAlignment) {
        if (str == null)
            str = "";

        Font font = getFont(fontSize);
        BaseFont baseFont = font.getCalculatedBaseFont(false);

        PdfContentByte canvas = getPdfContetByteUnder();
        canvas.saveState();
        canvas.beginText();
        canvas.setFontAndSize(baseFont, fontSize);

        if (fontStyle == Font.BOLD || fontStyle == Font.BOLDITALIC) {
            canvas.setTextRenderingMode(PdfContentByte.TEXT_RENDER_MODE_FILL_STROKE);
            canvas.setLineWidth(0.5f);
        }

        if (fontStyle == Font.ITALIC || fontStyle == Font.BOLDITALIC) {
            canvas.setTextMatrix(1, 0, 0.25f, 1, x, y);
            canvas.showText(str);
        }
        else {
            // 非斜體相關樣式
            canvas.showTextAligned(hAlignment, str, x, y, 0);
        }

        canvas.endText();

        if (fontStyle == Font.UNDERLINE) {
            canvas.setRGBColorStroke(0, 0, 0);
            canvas.setLineWidth(0.5f);
            canvas.moveTo(x + baseFont.getWidthPoint(str, fontSize), y - 2);
            canvas.lineTo(x, y - 2);
            canvas.stroke();
        }

        if (fontStyle == Font.STRIKETHRU) {
            canvas.setRGBColorStroke(0, 0, 0);
            canvas.setLineWidth(0.5f);
            canvas.moveTo(x + baseFont.getWidthPoint(str, fontSize), (y + (fontSize / 2) - 2));
            canvas.lineTo(x, (y + (fontSize / 2) - 2));
            canvas.stroke();
        }

        canvas.restoreState();
    }
    /**
     * 相對位置加入字段
     *
     * @param str
     * @param x
     * @param y
     * @param font
     * @param hAlignment
     */
    protected void drawString(String str, int x, int y, Font font, int hAlignment) {
        if (str == null)
            str = "";

        BaseFont baseFont = font.getCalculatedBaseFont(false);

        PdfContentByte canvas = getPdfContetByteUnder();
        canvas.saveState();
        canvas.beginText();
        canvas.setFontAndSize(baseFont, font.getSize());
        canvas.showTextAligned(hAlignment, str, x, y, 0);
        canvas.endText();
        canvas.restoreState();
    }

    /**
     * 建立 Barcode 格式為 Barcode39
     *
     * @param table           欲填入欄位的table元件
     * @param code            Barcode 內容
     * @param iBarScaleWidth  Barcode 寬度縮圖百分比
     * @param iBarScaleHeight Barcode 寬度縮圖百分比
     * @param icolspan        column span 若無填 1
     * @param irowspan        row span 若無填 1
     * @param borderWidth     欄位 border 的寬度
     * @param hAlignment      水平對齊方式
     * @param vAlignment      垂直對齊方式
     * @throws BadElementException
     */
    protected void drawBarcode39(Document document, String code, int iBarScaleWidth, int iBarScaleHeight, int x, int y) throws BadElementException {
        try {
            PdfContentByte cb = this.getWriter().getDirectContent();
            Barcode39 barCode = new Barcode39();
            barCode.setCode(StringUtils.defaultString(code));
            barCode.setAltText("");
            barCode.setStartStopText(true);
            Image image = barCode.createImageWithBarcode(cb, null, null);
            // image.scalePercent(iBarScaleWidth, iBarScaleHeight);
            image.scaleAbsoluteWidth(iBarScaleWidth);
            image.scaleAbsoluteHeight(iBarScaleHeight);
            image.setAbsolutePosition(x, y);
            document.add(image);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
