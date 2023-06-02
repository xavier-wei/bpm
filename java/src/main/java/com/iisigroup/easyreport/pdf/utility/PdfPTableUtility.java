package com.iisigroup.easyreport.pdf.utility;

import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.FontSelector;
import com.itextpdf.text.pdf.PdfPCell;
import org.apache.commons.lang3.StringUtils;

/**
 * Utility for <code>PdfPTable</code>
 *
 * @author Goston
 */
public class PdfPTableUtility {
    /**
     * 未定義 Column Span 或 Row Span
     */
    public static final int SPAN_UNDEFINED = -1;

    /**
     * 未定義行距
     */
    public static final int LINESPACE_UNDEFINED = -1;

    /**
     * 依傳入的參數建立 <code>PdfPCell</code><br>
     * 用於內容是文字時
     *
     * @param colspan Column Span （若不指定請傳入 <code>PdfPTableUtility.SPAN_UNDEFINED</code>）
     * @param rowspan Row Span （若不指定請傳入 <code>PdfPTableUtility.SPAN_UNDEFINED</code>）
     * @param str 欄位內容字串
     * @param font 字體
     * @param borderWidth 框線的寬度
     * @param borderType 框線的樣式 （若不指定請傳入 <code>Rectangle.UNDEFINED</code>）
     * @param hAlignment 水平對齊方式 （若不指定請傳入 <code>Element.ALIGN_UNDEFINED</code>）
     * @param vAlignment 垂直對齊方式 （若不指定請傳入 <code>Element.ALIGN_UNDEFINED</code>）
     * @param lineSpace 行距 （若不指定請設定 <code>PdfPTableUtility.LINESPACE_UNDEFINED</code>）
     * @return <code>PdfPCell</code> 物件，若傳入的 <code>str</code> 是 <code>null</code> 則會回傳一個無內容的 <code>PdfPCell</code> 物件
     */
    public static PdfPCell createPdfPCell(int colspan, int rowspan, String str, Font font, float borderWidth, int borderType, int hAlignment, int vAlignment, int lineSpace) {
        PdfPCell cell = new PdfPCell(new Phrase(StringUtils.defaultString(str), font));

        cell.setUseDescender(true);

        cell.setBorderWidth(borderWidth);

        if (hAlignment != Element.ALIGN_UNDEFINED)
            cell.setHorizontalAlignment(hAlignment);

        if (vAlignment != Element.ALIGN_UNDEFINED)
            cell.setVerticalAlignment(vAlignment);

        if (borderType != Rectangle.UNDEFINED)
            cell.setBorder(borderType);

        if (colspan != SPAN_UNDEFINED && colspan > 0)
            cell.setColspan(colspan);

        if (rowspan != SPAN_UNDEFINED && rowspan > 0)
            cell.setRowspan(rowspan);

        if (lineSpace != LINESPACE_UNDEFINED)
            cell.setLeading(font.getSize() + lineSpace, 0);

        return cell;
    }

    /**
     * 依傳入的參數建立 <code>PdfPCell</code> - 全字庫用 <br>
     * 用於內容是文字時
     *
     * @param colspan Column Span （若不指定請傳入 <code>PdfPTableUtility.SPAN_UNDEFINED</code>）
     * @param rowspan Row Span （若不指定請傳入 <code>PdfPTableUtility.SPAN_UNDEFINED</code>）
     * @param str 欄位內容字串
     * @param fontSelector 字體
     * @param borderWidth 框線的寬度
     * @param borderType 框線的樣式 （若不指定請傳入 <code>Rectangle.UNDEFINED</code>）
     * @param hAlignment 水平對齊方式 （若不指定請傳入 <code>Element.ALIGN_UNDEFINED</code>）
     * @param vAlignment 垂直對齊方式 （若不指定請傳入 <code>Element.ALIGN_UNDEFINED</code>）
     * @param lineSpace 行距 （若不指定請設定 <code>PdfPTableUtility.LINESPACE_UNDEFINED</code>）
     * @return <code>PdfPCell</code> 物件，若傳入的 <code>str</code> 是 <code>null</code> 則會回傳一個無內容的 <code>PdfPCell</code> 物件
     */
    public static PdfPCell createPdfPCell(int colspan, int rowspan, String str, FontSelector fontSelector, float borderWidth, int borderType, int hAlignment, int vAlignment, int lineSpace) {
        PdfPCell cell = new PdfPCell(fontSelector.process(StringUtils.defaultString(str)));

        cell.setUseDescender(true);

        cell.setBorderWidth(borderWidth);

        if (hAlignment != Element.ALIGN_UNDEFINED)
            cell.setHorizontalAlignment(hAlignment);

        if (vAlignment != Element.ALIGN_UNDEFINED)
            cell.setVerticalAlignment(vAlignment);

        if (borderType != Rectangle.UNDEFINED)
            cell.setBorder(borderType);

        if (colspan != SPAN_UNDEFINED && colspan > 0)
            cell.setColspan(colspan);

        if (rowspan != SPAN_UNDEFINED && rowspan > 0)
            cell.setRowspan(rowspan);

        if (lineSpace != LINESPACE_UNDEFINED)
            cell.setLeading(lineSpace, 0);

        return cell;
    }

    /**
     * 依傳入的參數建立 <code>PdfPCell</code><br>
     * 用於內容是圖片時
     *
     * @param colspan Column Span （若不指定請傳入 <code>PdfPTableUtility.SPAN_UNDEFINED</code>）
     * @param rowspan Row Span （若不指定請傳入 <code>PdfPTableUtility.SPAN_UNDEFINED</code>）
     * @param image 圖片
     * @param borderWidth 框線的寬度
     * @param borderType 框線的樣式 （若不指定請傳入 <code>Rectangle.UNDEFINED</code>）
     * @param hAlignment 水平對齊方式 （若不指定請傳入 <code>Element.ALIGN_UNDEFINED</code>）
     * @param vAlignment 垂直對齊方式 （若不指定請傳入 <code>Element.ALIGN_UNDEFINED</code>）
     * @return <code>PdfPCell</code> 物件，若傳入的 <code>image</code> 是 <code>null</code> 則會回傳一個無內容的 <code>PdfPCell</code> 物件
     */
    public static PdfPCell createPdfPCell(int colspan, int rowspan, Image image, float borderWidth, int borderType, int hAlignment, int vAlignment) {
        PdfPCell cell = null;

        if (image == null)
            cell = new PdfPCell();
        else
            cell = new PdfPCell(image);

        cell.setBorderWidth(borderWidth);

        if (hAlignment != Element.ALIGN_UNDEFINED)
            cell.setHorizontalAlignment(hAlignment);

        if (vAlignment != Element.ALIGN_UNDEFINED)
            cell.setVerticalAlignment(vAlignment);

        if (borderType != Rectangle.UNDEFINED)
            cell.setBorder(borderType);

        if (colspan != SPAN_UNDEFINED && colspan > 0)
            cell.setColspan(colspan);

        if (rowspan != SPAN_UNDEFINED && rowspan > 0)
            cell.setRowspan(rowspan);

        return cell;
    }

    /**
     * 依傳入的參數建立 <code>PdfPCell</code><br>
     * 用於內容是文字時
     * BF增加，懶得畫格子直接指定方框高度，直接畫文字框
     *
     * @param colspan Column Span （若不指定請傳入 <code>PdfPTableUtility.SPAN_UNDEFINED</code>）
     * @param rowspan Row Span （若不指定請傳入 <code>PdfPTableUtility.SPAN_UNDEFINED</code>）
     * @param str 欄位內容字串
     * @param font 字體
     * @param borderWidth 框線的寬度
     * @param borderType 框線的樣式 （若不指定請傳入 <code>Rectangle.UNDEFINED</code>）
     * @param hAlignment 水平對齊方式 （若不指定請傳入 <code>Element.ALIGN_UNDEFINED</code>）
     * @param vAlignment 垂直對齊方式 （若不指定請傳入 <code>Element.ALIGN_UNDEFINED</code>）
     * @param lineSpace 行距 （若不指定請設定 <code>PdfPTableUtility.LINESPACE_UNDEFINED</code>）
     * @return <code>PdfPCell</code> 物件，若傳入的 <code>str</code> 是 <code>null</code> 則會回傳一個無內容的 <code>PdfPCell</code> 物件
     */
    public static PdfPCell createPdfPCell(int colspan, int rowspan, String str, Font font, float borderWidth, int borderType, int hAlignment, int vAlignment, int lineSpace, int height) {
        PdfPCell cell = createPdfPCell(colspan, rowspan, str, font, borderWidth, borderType, hAlignment, vAlignment, lineSpace);
        cell.setFixedHeight(height);
        return cell;
    }
}