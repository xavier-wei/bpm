package com.iisigroup.easyreport.pdf.Helper;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPTableEvent;

/**
 * PdfPTable 奇偶行數底色變色
 * 
 * @author Goston
 */
public class AlternatingBackgroundHelper implements PdfPTableEvent {
    private BaseColor oddColor = null;
    private BaseColor evenColor = null;

    private int headerRows = -1;
    private int footerRows = -1;

    private int recordRows = 1; // 一筆資料的行數

    /**
     * 奇偶行數底色變色
     * 
     * @param oddColor 奇數行底色，若不指定則傳入 <code>null</code>
     * @param evenColor 偶數行底色，若不指定則傳入 <code>null</code>
     */
    public AlternatingBackgroundHelper(BaseColor oddColor, BaseColor evenColor) {
        super();
        this.oddColor = oddColor;
        this.evenColor = evenColor;
    }

    /**
     * 奇偶行數底色變色
     * 
     * @param oddColor 奇數行底色，若不指定則傳入 <code>null</code>
     * @param evenColor 偶數行底色，若不指定則傳入 <code>null</code>
     * @param recordRows 一筆資料的行數
     */
    public AlternatingBackgroundHelper(BaseColor oddColor, BaseColor evenColor, int recordRows) {
        super();
        this.oddColor = oddColor;
        this.evenColor = evenColor;
        this.recordRows = recordRows;
    }

    /**
     * 奇偶行數底色變色
     * 
     * @param oddColor 奇數行底色，若不指定則傳入 <code>null</code>
     * @param evenColor 偶數行底色，若不指定則傳入 <code>null</code>
     * @param headerRows 表頭行數
     * @param footerRows 表尾行數
     */
    public AlternatingBackgroundHelper(BaseColor oddColor, BaseColor evenColor, int headerRows, int footerRows) {
        super();
        this.oddColor = oddColor;
        this.evenColor = evenColor;
        this.headerRows = headerRows;
        this.footerRows = footerRows;
    }

    /**
     * 奇偶行數底色變色
     * 
     * @param oddColor 奇數行底色，若不指定則傳入 <code>null</code>
     * @param evenColor 偶數行底色，若不指定則傳入 <code>null</code>
     * @param recordRows 一筆資料的行數
     * @param headerRows 表頭行數
     * @param footerRows 表尾行數
     */
    public AlternatingBackgroundHelper(BaseColor oddColor, BaseColor evenColor, int recordRows, int headerRows, int footerRows) {
        super();
        this.oddColor = oddColor;
        this.evenColor = evenColor;
        this.recordRows = recordRows;
        this.headerRows = headerRows;
        this.footerRows = footerRows;
    }

    /**
     * 奇偶行數底色變色
     * 
     * @param oddColor 奇數行底色 RGB 值（如: <code>{255, 255, 200}</code>），若不指定則傳入 <code>null</code>
     * @param evenColor 偶數行底色 RGB 值（如: <code>{255, 255, 200}</code>），若不指定則傳入 <code>null</code>
     */
    public AlternatingBackgroundHelper(int[] oddColor, int[] evenColor) {
        super();

        if (oddColor == null || oddColor.length != 3)
            this.oddColor = null;
        else
            this.oddColor = new BaseColor(oddColor[0], oddColor[1], oddColor[2]);

        if (evenColor == null || evenColor.length != 3)
            this.evenColor = null;
        else
            this.evenColor = new BaseColor(evenColor[0], evenColor[1], evenColor[2]);
    }

    /**
     * 奇偶行數底色變色
     * 
     * @param oddColor 奇數行底色 RGB 值（如: <code>{255, 255, 200}</code>），若不指定則傳入 <code>null</code>
     * @param evenColor 偶數行底色 RGB 值（如: <code>{255, 255, 200}</code>），若不指定則傳入 <code>null</code>
     * @param recordRows 一筆資料的行數
     */
    public AlternatingBackgroundHelper(int[] oddColor, int[] evenColor, int recordRows) {
        super();

        if (oddColor == null || oddColor.length != 3)
            this.oddColor = null;
        else
            this.oddColor = new BaseColor(oddColor[0], oddColor[1], oddColor[2]);

        if (evenColor == null || evenColor.length != 3)
            this.evenColor = null;
        else
            this.evenColor = new BaseColor(evenColor[0], evenColor[1], evenColor[2]);

        this.recordRows = recordRows;
    }

    /**
     * 奇偶行數底色變色
     * 
     * @param oddColor 奇數行底色 RGB 值（如: <code>{255, 255, 200}</code>），若不指定則傳入 <code>null</code>
     * @param evenColor 偶數行底色 RGB 值（如: <code>{255, 255, 200}</code>），若不指定則傳入 <code>null</code>
     * @param headerRows 表頭行數
     * @param footerRows 表尾行數
     */
    public AlternatingBackgroundHelper(int[] oddColor, int[] evenColor, int headerRows, int footerRows) {
        super();

        if (oddColor == null || oddColor.length != 3)
            this.oddColor = null;
        else
            this.oddColor = new BaseColor(oddColor[0], oddColor[1], oddColor[2]);

        if (evenColor == null || evenColor.length != 3)
            this.evenColor = null;
        else
            this.evenColor = new BaseColor(evenColor[0], evenColor[1], evenColor[2]);

        this.headerRows = headerRows;
        this.footerRows = footerRows;
    }

    /**
     * 奇偶行數底色變色
     * 
     * @param oddColor 奇數行底色 RGB 值（如: <code>{255, 255, 200}</code>），若不指定則傳入 <code>null</code>
     * @param evenColor 偶數行底色 RGB 值（如: <code>{255, 255, 200}</code>），若不指定則傳入 <code>null</code>
     * @param recordRows 一筆資料的行數
     * @param headerRows 表頭行數
     * @param footerRows 表尾行數
     */
    public AlternatingBackgroundHelper(int[] oddColor, int[] evenColor, int recordRows, int headerRows, int footerRows) {
        super();

        if (oddColor == null || oddColor.length != 3)
            this.oddColor = null;
        else
            this.oddColor = new BaseColor(oddColor[0], oddColor[1], oddColor[2]);

        if (evenColor == null || evenColor.length != 3)
            this.evenColor = null;
        else
            this.evenColor = new BaseColor(evenColor[0], evenColor[1], evenColor[2]);

        this.recordRows = recordRows;
        this.headerRows = headerRows;
        this.footerRows = footerRows;
    }

    public void tableLayout(PdfPTable table, float[][] widths, float[] heights, int headers, int rowStart, PdfContentByte[] canvases) {
        int columns;
        boolean odd = true; // 是否為奇數行
        Rectangle rect;

        int header = headerRows;
        int footer = footerRows;

        if (headerRows < 0)
            header = table.getHeaderRows() - table.getFooterRows();

        if (footerRows >= 0)
            footer = widths.length - footerRows;
        else
            footer = widths.length - table.getFooterRows();

        if (recordRows < 1)
            recordRows = 1;

        int counter = 0;

        for (int row = header; row < footer; row++) {
            columns = widths[row].length - 1;

            // 參數說明: Rectangle(x, y, width, height)
            rect = new Rectangle(widths[row][0], heights[row], widths[row][columns], heights[row + 1]);

            if (odd)
                rect.setBackgroundColor(oddColor);
            else
                rect.setBackgroundColor(evenColor);

            counter++;

            if (counter == recordRows) {
                odd = !odd;
                counter = 0;
            }

            rect.setBorder(Rectangle.NO_BORDER);
            canvases[PdfPTable.BASECANVAS].rectangle(rect);
        }
    }

}
