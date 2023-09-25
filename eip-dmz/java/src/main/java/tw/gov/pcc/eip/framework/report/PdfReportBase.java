package tw.gov.pcc.eip.framework.report;

import com.iisigroup.easyreport.pdf.ReportBase;
import com.iisigroup.easyreport.pdf.exception.ReportException;
import com.iisigroup.easyreport.pdf.utility.PdfPTableUtility;
import com.lowagie.text.Font;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPCellEvent;
import com.lowagie.text.pdf.PdfPTable;
import org.apache.commons.lang3.StringUtils;

/**
 * Report Base for PDF Report
 *
 * @author Goston
 */
public class PdfReportBase extends ReportBase {


    public PdfReportBase() throws ReportException {
        super();
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
     * @param padding     Padding
     */
    protected void addCellAssignVAlignmentAndPadding(PdfPTable table, int colspan, int rowspan, String str, int fontSize, float borderWidth, int hAlignment, int vAlignment, int padding) {
        PdfPCell cell = PdfPTableUtility.createPdfPCell(colspan, rowspan, str, getFont(fontSize), borderWidth, Rectangle.UNDEFINED, hAlignment, vAlignment, PdfPTableUtility.LINESPACE_UNDEFINED);
        cell.setPaddingTop(padding);
        cell.setPaddingBottom(padding);
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
     * @param padding     Padding
     */
    protected void addCellAssignVAlignmentAndAllPadding(PdfPTable table, int colspan, int rowspan, String str, int fontSize, float borderWidth, int hAlignment, int vAlignment, int padding) {
        PdfPCell cell = PdfPTableUtility.createPdfPCell(colspan, rowspan, str, getFont(fontSize), borderWidth, Rectangle.UNDEFINED, hAlignment, vAlignment, PdfPTableUtility.LINESPACE_UNDEFINED);
        cell.setPaddingTop(padding);
        cell.setPaddingBottom(padding);
        cell.setPaddingLeft(padding);
        cell.setPaddingRight(padding);
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
     * @param lineSpace   行距
     * @param padding     Padding
     */
    protected void addCellAssignVAlignmentAndAllPadding(PdfPTable table, int colspan, int rowspan, String str, int fontSize, float borderWidth, int hAlignment, int vAlignment, int lineSpace, int padding) {
        PdfPCell cell = PdfPTableUtility.createPdfPCell(colspan, rowspan, str, getFont(fontSize), borderWidth, Rectangle.UNDEFINED, hAlignment, vAlignment, lineSpace);
        cell.setPaddingTop(padding);
        cell.setPaddingBottom(padding);
        cell.setPaddingLeft(padding);
        cell.setPaddingRight(padding);
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
     * @param padding     Padding
     * @param cellEvent   <code>PdfPCellEvent</code> 物件
     */
    protected void addCellAssignVAlignmentAndPadding(PdfPTable table, int colspan, int rowspan, String str, int fontSize, float borderWidth, int hAlignment, int vAlignment, int padding, PdfPCellEvent cellEvent) {
        PdfPCell cell = PdfPTableUtility.createPdfPCell(colspan, rowspan, str, getFont(fontSize), borderWidth, Rectangle.UNDEFINED, hAlignment, vAlignment, PdfPTableUtility.LINESPACE_UNDEFINED);
        cell.setCellEvent(cellEvent);
        cell.setPaddingTop(padding);
        cell.setPaddingBottom(padding);
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
     * @param padding     Padding
     * @param cellEvent   <code>PdfPCellEvent</code> 物件
     */
    protected void addCellAssignVAlignmentAndAllPadding(PdfPTable table, int colspan, int rowspan, String str, int fontSize, float borderWidth, int hAlignment, int vAlignment, int padding, PdfPCellEvent cellEvent) {
        PdfPCell cell = PdfPTableUtility.createPdfPCell(colspan, rowspan, str, getFont(fontSize), borderWidth, Rectangle.UNDEFINED, hAlignment, vAlignment, PdfPTableUtility.LINESPACE_UNDEFINED);
        cell.setCellEvent(cellEvent);
        cell.setPaddingTop(padding);
        cell.setPaddingBottom(padding);
        cell.setPaddingLeft(padding);
        cell.setPaddingRight(padding);
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
     * @param lineSpace   行距
     * @param padding     Padding
     * @param cellEvent   <code>PdfPCellEvent</code> 物件
     */
    protected void addCellAssignVAlignmentAndAllPadding(PdfPTable table, int colspan, int rowspan, String str, int fontSize, float borderWidth, int hAlignment, int vAlignment, int lineSpace, int padding, PdfPCellEvent cellEvent) {
        PdfPCell cell = PdfPTableUtility.createPdfPCell(colspan, rowspan, str, getFont(fontSize), borderWidth, Rectangle.UNDEFINED, hAlignment, vAlignment, lineSpace);
        cell.setCellEvent(cellEvent);
        cell.setPaddingTop(padding);
        cell.setPaddingBottom(padding);
        cell.setPaddingLeft(padding);
        cell.setPaddingRight(padding);
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
     * @param vAlignment  垂直對齊方式
     * @param padding     Padding
     */
    protected void addCellAssignBorderTypeAndPadding(PdfPTable table, int colspan, int rowspan, String str, int fontSize, float borderWidth, int borderType, int hAlignment, int vAlignment, int padding) {
        PdfPCell cell = PdfPTableUtility.createPdfPCell(colspan, rowspan, str, getFont(fontSize), borderWidth, borderType, hAlignment, vAlignment, PdfPTableUtility.LINESPACE_UNDEFINED);
        cell.setPaddingTop(padding);
        cell.setPaddingBottom(padding);
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
     * @param vAlignment  垂直對齊方式
     * @param padding     Padding
     */
    protected void addCellAssignBorderTypeAndAllPadding(PdfPTable table, int colspan, int rowspan, String str, int fontSize, float borderWidth, int borderType, int hAlignment, int vAlignment, int padding) {
        PdfPCell cell = PdfPTableUtility.createPdfPCell(colspan, rowspan, str, getFont(fontSize), borderWidth, borderType, hAlignment, vAlignment, PdfPTableUtility.LINESPACE_UNDEFINED);
        cell.setPaddingTop(padding);
        cell.setPaddingBottom(padding);
        cell.setPaddingLeft(padding);
        cell.setPaddingRight(padding);
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
     * @param vAlignment  垂直對齊方式
     * @param lineSpace   行距
     * @param padding     Padding
     */
    protected void addCellAssignBorderTypeAndAllPadding(PdfPTable table, int colspan, int rowspan, String str, int fontSize, float borderWidth, int borderType, int hAlignment, int vAlignment, int lineSpace, int padding) {
        PdfPCell cell = PdfPTableUtility.createPdfPCell(colspan, rowspan, str, getFont(fontSize), borderWidth, borderType, hAlignment, vAlignment, lineSpace);
        cell.setPaddingTop(padding);
        cell.setPaddingBottom(padding);
        cell.setPaddingLeft(padding);
        cell.setPaddingRight(padding);
        table.addCell(cell);
    }

    /**
     * 在特定位置畫垂直字串
     *
     * @param text       字串內容
     * @param x          起始點的 X 座標
     * @param y          y 起始點的 Y 座標
     * @param fontSize   字型大小
     * @param lineHeight 行高
     */
    protected void drawVerticalString(String text, int x, int y, int fontSize, int lineHeight) {
        if (StringUtils.isNotBlank(text)) {
            for (int i = 0; i < StringUtils.length(text); i++) {
                drawString(StringUtils.substring(text, i, i + 1), x, (y - (lineHeight * i)), fontSize, LEFT);
            }
        }
    }

    /**
     * 在特定位置畫垂直字串
     *
     * @param text       字串內容
     * @param x          起始點的 X 座標
     * @param y          y 起始點的 Y 座標
     * @param fontSize   字型大小
     * @param fontStyle  文字樣式
     * @param lineHeight 行高
     */
    protected void drawVerticalString(String text, int x, int y, int fontSize, int fontStyle, int lineHeight) {
        if (StringUtils.isNotBlank(text)) {
            for (int i = 0; i < StringUtils.length(text); i++) {
                drawString(StringUtils.substring(text, i, i + 1), x, (y - (lineHeight * i)), fontSize, fontStyle, LEFT);
            }
        }
    }

    /**
     *
     * @param table       欲加入 Cell 的 <code>PdfPTable</code> 物件
     * @param colspan     Column Span 若無填 <code>1</code>
     * @param rowspan     Row Span 若無填 <code>1</code>
     * @param phrase      欄位內容片段 
     * @param fontSize    字體大小
     * @param borderWidth 框線的寬度
     * @param hAlignment  水平對齊方式
     */
    public void addCell(PdfPTable table, int colspan, int rowspan, Phrase phrase, int fontSize, float borderWidth,  int hAlignment) {

        table.addCell(this.createPdfPCell(colspan, rowspan, phrase,getFont(fontSize) , borderWidth, -1,hAlignment, -1, -1));

    }


    private PdfPCell createPdfPCell(int colspan, int rowspan, Phrase phrase, Font font,float borderWidth, int borderType, int hAlignment, int vAlignment, int lineSpace) {
        PdfPCell cell = new PdfPCell(phrase);

        cell.setUseDescender(true);

        cell.setBorderWidth(borderWidth);

        if (hAlignment != -1) {
            cell.setHorizontalAlignment(hAlignment);
        }
        if (vAlignment != -1) {
            cell.setVerticalAlignment(vAlignment);
        }
        if (borderType != -1) {
            cell.setBorder(borderType);
        }
        if (colspan != -1 && colspan > 0) {
            cell.setColspan(colspan);
        }
        if (rowspan != -1 && rowspan > 0) {
            cell.setRowspan(rowspan);
        }
        if (lineSpace != -1) {
            cell.setLeading(font.getSize() + lineSpace, 0.0F);
        }
        return cell;
    }
}
