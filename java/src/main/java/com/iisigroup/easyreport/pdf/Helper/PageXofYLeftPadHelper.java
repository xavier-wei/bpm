package com.iisigroup.easyreport.pdf.Helper;

import com.iisigroup.easyreport.pdf.exception.ReportException;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.*;

import static org.apache.commons.lang3.StringUtils.defaultString;
import static org.apache.commons.lang3.StringUtils.leftPad;

/**
 * Page X of Y
 *
 * @author tunhao.hsu
 */
public class PageXofYLeftPadHelper extends PdfPageEventHelper {
    private Font font = null;
    private String prefix = " ";
    private String middle = "/";
    private String suffix = " ";
    private int x = 0;
    private int y = 0;

    private PdfContentByte contentByte;
    private PdfTemplate template;

    /**
     * 以預設字體及樣式顯示 Page X of Y<br>
     * 顯示結果類似：「0005/0010」
     *
     * @param fontSize 字體大小
     * @param x        位置 <code>X</code> 軸
     * @param y        位置 <code>Y</code> 軸
     */
    public PageXofYLeftPadHelper(int fontSize, int x, int y) throws ReportException {
        super();

        try {
            BaseFont basefont = BaseFont.createFont(EnvHelper.getFontPath() + EnvHelper.getFontName(), BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            this.font = new Font(basefont, fontSize);
            this.x = x;
            this.y = y;
        } catch (Exception e) {
            throw new ReportException(e);
        }
    }

    /**
     * 以指定的字體及樣式顯示 Page X of Y<br>
     * 顯示結果類似：「0005/0010」
     *
     * @param font 字體及樣式
     * @param x    位置 <code>X</code> 軸
     * @param y    位置 <code>Y</code> 軸
     */
    public PageXofYLeftPadHelper(Font font, int x, int y) throws ReportException {
        super();

        try {
            this.font = font;
            this.x = x;
            this.y = y;
        } catch (Exception e) {
            throw new ReportException(e);
        }
    }

    /**
     * 以預設字體及樣式顯示 Page X of Y<br>
     * 若：<br>
     * <code>prefix</code> 傳入「第」<br>
     * <code>middle</code> 傳入「 頁 / 共」<br>
     * <code>suffix</code> 傳入「頁」<br>
     * 顯示結果類似：「第 0005 頁 / 共 0010 頁」
     *
     * @param fontSize 字體大小
     * @param prefix   前綴字串，如: 第
     * @param middle   中間字串，如: 頁 / 共
     * @param suffix   後綴字串，如: 頁
     * @param x        位置 <code>X</code> 軸
     * @param y        位置 <code>Y</code> 軸
     */
    public PageXofYLeftPadHelper(int fontSize, String prefix, String middle, String suffix, int x, int y) throws ReportException {
        super();

        try {
            BaseFont basefont = BaseFont.createFont(EnvHelper.getFontPath() + EnvHelper.getFontName(), BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            this.font = new Font(basefont, fontSize);
            this.prefix = prefix;
            this.middle = middle;
            this.suffix = suffix;
            this.x = x;
            this.y = y;
        } catch (Exception e) {
            throw new ReportException(e);
        }
    }

    /**
     * 以指定的字體及樣式顯示 Page X of Y<br>
     * 若：<br>
     * <code>prefix</code> 傳入「第」<br>
     * <code>middle</code> 傳入「 頁 / 共」<br>
     * <code>suffix</code> 傳入「頁」<br>
     * 顯示結果類似：「第 0005 頁 / 共 0010 頁」
     *
     * @param font   字體及樣式
     * @param prefix 前綴字串，如: 第
     * @param middle 中間字串，如: 頁 / 共
     * @param suffix 後綴字串，如: 頁
     * @param x      位置 <code>X</code> 軸
     * @param y      位置 <code>Y</code> 軸
     */
    public PageXofYLeftPadHelper(Font font, String prefix, String middle, String suffix, int x, int y) {
        super();

        this.font = font;
        this.prefix = prefix;
        this.middle = middle;
        this.suffix = suffix;
        this.x = x;
        this.y = y;
    }

    @Override
    public void onOpenDocument(PdfWriter writer, Document document) {
        contentByte = writer.getDirectContent();
        template = contentByte.createTemplate(200, font.getSize() + 8);
        template.setBoundingBox(new Rectangle(0, -font.getSize(), 200, font.getSize() + 8)); // 解決文字底部被截斷的問題
    }

    @Override
    public void onEndPage(PdfWriter writer, Document document) {
        String text = defaultString(prefix) + leftPad(String.valueOf(writer.getPageNumber()), 4, '0') + defaultString(middle);
        float len = font.getBaseFont()
                .getWidthPoint(text, font.getSize());

        contentByte.beginText();
        contentByte.setFontAndSize(font.getBaseFont(), font.getSize());
        contentByte.setTextMatrix(x, y);
        contentByte.showText(text);
        contentByte.endText();
        contentByte.addTemplate(template, x + len, y);
    }

    @Override
    public void onCloseDocument(PdfWriter writer, Document document) {
        template.beginText();
        template.setFontAndSize(font.getBaseFont(), font.getSize());
        // template.showText(String.valueOf(writer.getPageNumber() - 1)); // 當 Document Close 時，newPage() 會被呼叫，故頁數需減 1
        template.showText(leftPad(String.valueOf(writer.getPageNumber()), 4, '0')); // 新版 iText 似乎不會在 Document Close 時呼叫 newPage()
        template.showText(defaultString(suffix));
        template.endText();
    }
}
