package com.iisigroup.easyreport.pdf.Helper;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPCellEvent;
import com.itextpdf.text.pdf.PdfPTable;

/**
 * PDF 表格單一儲存格框線 - 上下: 虛線、左右: 實線
 *
 * @author Goston
 */
public class DottedTopDownLineBorderCellHelper implements PdfPCellEvent {

    private float dashLength = 3f;
    private float spaceLength = 3f;
    private float lineWidth = 0.5f;
    private BaseColor color = BaseColor.BLACK;

    /**
     * 以預設參數將儲存格框線上下畫虛線、左右畫實線<br>
     * <br>
     * 虛線的長度 ＝ <code>3f</code><br>
     * 虛線間空白的大小 = <code>3f</code><br>
     * 虛線的粗細 = <code>0.5f</code><br>
     */
    public DottedTopDownLineBorderCellHelper() {
        super();
    }

    /**
     * 以預設參數將儲存格框線上下畫虛線、左右畫實線<br>
     * <br>
     * 虛線的長度 ＝ <code>3f</code><br>
     * 虛線間空白的大小 = <code>3f</code><br>
     * 虛線的粗細 = <code>0.5f</code><br>
     *
     * @param color 色彩 RGB 值（如: <code>{255, 255, 200}</code>），若不指定則傳入 <code>null</code>
     */
    public DottedTopDownLineBorderCellHelper(int[] color) {
        super();

        if (color != null && color.length == 3)
            this.color = new BaseColor(color[0], color[1], color[2]);
    }

    /**
     * 以預設參數將儲存格框線上下畫虛線、左右畫實線<br>
     * <br>
     * 虛線的長度 ＝ <code>3f</code><br>
     * 虛線間空白的大小 = <code>3f</code><br>
     * 虛線的粗細 = <code>0.5f</code><br>
     *
     * @param color 色彩
     */
    public DottedTopDownLineBorderCellHelper(BaseColor color) {
        super();
        this.color = color;
    }

    /**
     * 儲存格框線上下畫虛線、左右畫實線
     *
     * @param dashLength 虛線的長度
     */
    public DottedTopDownLineBorderCellHelper(float dashLength) {
        super();
        this.dashLength = dashLength;
        this.spaceLength = dashLength;
    }

    /**
     * 儲存格框線上下畫虛線、左右畫實線
     *
     * @param dashLength 虛線的長度
     * @param color      色彩 RGB 值（如: <code>{255, 255, 200}</code>），若不指定則傳入 <code>null</code>
     */
    public DottedTopDownLineBorderCellHelper(float dashLength, int[] color) {
        super();
        this.dashLength = dashLength;
        this.spaceLength = dashLength;
        if (color != null && color.length == 3)
            this.color = new BaseColor(color[0], color[1], color[2]);
    }

    /**
     * 儲存格框線上下畫虛線、左右畫實線
     *
     * @param dashLength 虛線的長度
     * @param color      色彩
     */
    public DottedTopDownLineBorderCellHelper(float dashLength, BaseColor color) {
        super();
        this.dashLength = dashLength;
        this.spaceLength = dashLength;
        this.color = color;
    }

    /**
     * 儲存格框線上下畫虛線、左右畫實線
     *
     * @param dashLength  虛線的長度
     * @param spaceLength 虛線間空白的大小
     */
    public DottedTopDownLineBorderCellHelper(float dashLength, float spaceLength) {
        super();
        this.dashLength = dashLength;
        this.spaceLength = spaceLength;
    }

    /**
     * 儲存格框線上下畫虛線、左右畫實線
     *
     * @param dashLength  虛線的長度
     * @param spaceLength 虛線間空白的大小
     * @param color       色彩 RGB 值（如: <code>{255, 255, 200}</code>），若不指定則傳入 <code>null</code>
     */
    public DottedTopDownLineBorderCellHelper(float dashLength, float spaceLength, int[] color) {
        super();
        this.dashLength = dashLength;
        this.spaceLength = spaceLength;
        if (color != null && color.length == 3)
            this.color = new BaseColor(color[0], color[1], color[2]);
    }

    /**
     * 儲存格框線上下畫虛線、左右畫實線
     *
     * @param dashLength  虛線的長度
     * @param spaceLength 虛線間空白的大小
     * @param color       色彩
     */
    public DottedTopDownLineBorderCellHelper(float dashLength, float spaceLength, BaseColor color) {
        super();
        this.dashLength = dashLength;
        this.spaceLength = spaceLength;
        this.color = color;
    }

    /**
     * 儲存格框線上下畫虛線、左右畫實線
     *
     * @param dashLength  虛線的長度
     * @param spaceLength 虛線間空白的大小
     * @param lineWidth   虛線的粗細
     */
    public DottedTopDownLineBorderCellHelper(float dashLength, float spaceLength, float lineWidth) {
        super();
        this.dashLength = dashLength;
        this.spaceLength = spaceLength;
        this.lineWidth = lineWidth;
    }

    /**
     * 儲存格框線上下畫虛線、左右畫實線
     *
     * @param dashLength  虛線的長度
     * @param spaceLength 虛線間空白的大小
     * @param lineWidth   虛線的粗細
     * @param color       色彩 RGB 值（如: <code>{255, 255, 200}</code>），若不指定則傳入 <code>null</code>
     */
    public DottedTopDownLineBorderCellHelper(float dashLength, float spaceLength, float lineWidth, int[] color) {
        super();
        this.dashLength = dashLength;
        this.spaceLength = spaceLength;
        this.lineWidth = lineWidth;
        if (color != null && color.length == 3)
            this.color = new BaseColor(color[0], color[1], color[2]);
    }

    /**
     * 儲存格框線上下畫虛線、左右畫實線
     *
     * @param dashLength  虛線的長度
     * @param spaceLength 虛線間空白的大小
     * @param lineWidth   虛線的粗細
     * @param color       色彩
     */
    public DottedTopDownLineBorderCellHelper(float dashLength, float spaceLength, float lineWidth, BaseColor color) {
        super();
        this.dashLength = dashLength;
        this.spaceLength = spaceLength;
        this.lineWidth = lineWidth;
        this.color = color;
    }

    public void cellLayout(PdfPCell cell, Rectangle position, PdfContentByte[] canvases) {
        PdfContentByte canvas = canvases[PdfPTable.LINECANVAS];

        // 畫上、下兩邊的虛線
        canvas.saveState();
        canvas.setLineDash(dashLength, spaceLength);
        canvas.setLineWidth(lineWidth);
        canvas.setColorStroke(color);
        canvas.moveTo(position.getLeft(), position.getTop());
        canvas.lineTo(position.getRight(), position.getTop());
        canvas.moveTo(position.getLeft(), position.getBottom());
        canvas.lineTo(position.getRight(), position.getBottom());
        canvas.stroke();
        canvas.restoreState();

        // 畫左、右兩邊的實線
        canvas.saveState();
        canvas.setLineWidth(lineWidth);
        canvas.setColorStroke(color);
        canvas.moveTo(position.getLeft(), position.getTop());
        canvas.lineTo(position.getLeft(), position.getBottom());
        canvas.moveTo(position.getRight(), position.getTop());
        canvas.lineTo(position.getRight(), position.getBottom());
        canvas.stroke();
        canvas.restoreState();
    }

}
