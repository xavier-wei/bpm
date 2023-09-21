package com.iisigroup.easyreport.pdf.Helper;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPTableEvent;

/**
 * 表格畫虛線
 * 
 * @author Goston
 */
public class DottedTableHelper implements PdfPTableEvent {
    private float dashLength = 3f;
    private float spaceLength = 3f;
    private float lineWidth = 0.5f;
    private BaseColor color = BaseColor.BLACK;

    /**
     * 以預設參數將表格畫虛線<br>
     * <br>
     * 虛線的長度 ＝ <code>3f</code><br>
     * 虛線間空白的大小 = <code>3f</code><br>
     * 虛線的粗細 = <code>0.5f</code><br>
     */
    public DottedTableHelper() {
        super();
    }

    /**
     * 以預設參數將表格畫虛線<br>
     * <br>
     * 虛線的長度 ＝ <code>3f</code><br>
     * 虛線間空白的大小 = <code>3f</code><br>
     * 虛線的粗細 = <code>0.5f</code><br>
     * 
     * @param color 色彩 RGB 值（如: <code>{255, 255, 200}</code>），若不指定則傳入 <code>null</code>
     */
    public DottedTableHelper(int[] color) {
        super();

        if (color != null && color.length == 3)
            this.color = new BaseColor(color[0], color[1], color[2]);
    }

    /**
     * 以預設參數將表格畫虛線<br>
     * <br>
     * 虛線的長度 ＝ <code>3f</code><br>
     * 虛線間空白的大小 = <code>3f</code><br>
     * 虛線的粗細 = <code>0.5f</code><br>
     * 
     * @param color 色彩
     */
    public DottedTableHelper(BaseColor color) {
        super();
        this.color = color;
    }

    /**
     * 表格畫虛線
     * 
     * @param dashLength 虛線的長度
     */
    public DottedTableHelper(float dashLength) {
        super();
        this.dashLength = dashLength;
        this.spaceLength = dashLength;
    }

    /**
     * 表格畫虛線
     * 
     * @param dashLength 虛線的長度
     * @param color 色彩 RGB 值（如: <code>{255, 255, 200}</code>），若不指定則傳入 <code>null</code>
     */
    public DottedTableHelper(float dashLength, int[] color) {
        super();
        this.dashLength = dashLength;
        this.spaceLength = dashLength;
        if (color != null && color.length == 3)
            this.color = new BaseColor(color[0], color[1], color[2]);
    }

    /**
     * 表格畫虛線
     * 
     * @param dashLength 虛線的長度
     * @param color 色彩
     */
    public DottedTableHelper(float dashLength, BaseColor color) {
        super();
        this.dashLength = dashLength;
        this.spaceLength = dashLength;
        this.color = color;
    }

    /**
     * 表格畫虛線
     * 
     * @param dashLength 虛線的長度
     * @param spaceLength 虛線間空白的大小
     */
    public DottedTableHelper(float dashLength, float spaceLength) {
        super();
        this.dashLength = dashLength;
        this.spaceLength = spaceLength;
    }

    /**
     * 表格畫虛線
     * 
     * @param dashLength 虛線的長度
     * @param spaceLength 虛線間空白的大小
     * @param color 色彩 RGB 值（如: <code>{255, 255, 200}</code>），若不指定則傳入 <code>null</code>
     */
    public DottedTableHelper(float dashLength, float spaceLength, int[] color) {
        super();
        this.dashLength = dashLength;
        this.spaceLength = spaceLength;
        if (color != null && color.length == 3)
            this.color = new BaseColor(color[0], color[1], color[2]);
    }

    /**
     * 表格畫虛線
     * 
     * @param dashLength 虛線的長度
     * @param spaceLength 虛線間空白的大小
     * @param color 色彩
     */
    public DottedTableHelper(float dashLength, float spaceLength, BaseColor color) {
        super();
        this.dashLength = dashLength;
        this.spaceLength = spaceLength;
        this.color = color;
    }

    /**
     * 表格畫虛線
     * 
     * @param dashLength 虛線的長度
     * @param spaceLength 虛線間空白的大小
     * @param lineWidth 虛線的粗細
     */
    public DottedTableHelper(float dashLength, float spaceLength, float lineWidth) {
        super();
        this.dashLength = dashLength;
        this.spaceLength = spaceLength;
        this.lineWidth = lineWidth;
    }

    /**
     * 表格畫虛線
     * 
     * @param dashLength 虛線的長度
     * @param spaceLength 虛線間空白的大小
     * @param lineWidth 虛線的粗細
     * @param color 色彩 RGB 值（如: <code>{255, 255, 200}</code>），若不指定則傳入 <code>null</code>
     */
    public DottedTableHelper(float dashLength, float spaceLength, float lineWidth, int[] color) {
        super();
        this.dashLength = dashLength;
        this.spaceLength = spaceLength;
        this.lineWidth = lineWidth;
        if (color != null && color.length == 3)
            this.color = new BaseColor(color[0], color[1], color[2]);
    }

    /**
     * 表格畫虛線
     * 
     * @param dashLength 虛線的長度
     * @param spaceLength 虛線間空白的大小
     * @param lineWidth 虛線的粗細
     * @param color 色彩
     */
    public DottedTableHelper(float dashLength, float spaceLength, float lineWidth, BaseColor color) {
        super();
        this.dashLength = dashLength;
        this.spaceLength = spaceLength;
        this.lineWidth = lineWidth;
        this.color = color;
    }

    public void tableLayout(PdfPTable table, float[][] widths, float[] heights, int headerRows, int rowStart, PdfContentByte[] canvases) {
        PdfContentByte canvas = canvases[PdfPTable.LINECANVAS];
        canvas.saveState();
        canvas.setLineDash(dashLength, spaceLength, 0);
        canvas.setLineWidth(lineWidth);
        canvas.setColorStroke(color);
        float llx = widths[0][0];
        float urx = widths[0][widths[0].length - 1];
        for (int i = 0; i < heights.length; i++) {
            canvas.moveTo(llx, heights[i]);
            canvas.lineTo(urx, heights[i]);
        }
        for (int i = 0; i < widths.length; i++) {
            for (int j = 0; j < widths[i].length; j++) {
                canvas.moveTo(widths[i][j], heights[i]);
                canvas.lineTo(widths[i][j], heights[i + 1]);
            }
        }
        canvas.stroke();
        canvas.restoreState();
    }

}
