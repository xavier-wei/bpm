package com.iisigroup.easyreport.pdf.Helper;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPCellEvent;
import com.itextpdf.text.pdf.PdfPTable;

/**
 * 表格單一儲存格畫虛線
 *
 * @author Goston
 */
public class DottedCellHelper implements PdfPCellEvent {

    private float dashLength = 3f;
    private float spaceLength = 3f;
    private float lineWidth = 0.5f;
    private BaseColor color = BaseColor.BLACK;
    private int borderType = 0;

    /**
     * 以預設參數將儲存格畫虛線<br>
     * <br>
     * 虛線的長度 ＝ <code>3f</code><br>
     * 虛線間空白的大小 = <code>3f</code><br>
     * 虛線的粗細 = <code>0.5f</code><br>
     */
    public DottedCellHelper() {
        super();
    }

    /**
     * 以預設參數將儲存格畫虛線<br>
     * <br>
     * 虛線的長度 ＝ <code>3f</code><br>
     * 虛線間空白的大小 = <code>3f</code><br>
     * 虛線的粗細 = <code>0.5f</code><br>
     *
     * @param color 色彩 RGB 值（如: <code>{255, 255, 200}</code>），若不指定則傳入 <code>null</code>
     */
    public DottedCellHelper(int[] color) {
        super();

        if (color != null && color.length == 3)
            this.color = new BaseColor(color[0], color[1], color[2]);
    }

    /**
     * 以預設參數將儲存格畫虛線<br>
     * <br>
     * 虛線的長度 ＝ <code>3f</code><br>
     * 虛線間空白的大小 = <code>3f</code><br>
     * 虛線的粗細 = <code>0.5f</code><br>
     *
     * @param color 色彩
     */
    public DottedCellHelper(BaseColor color) {
        super();
        this.color = color;
    }

    /**
     * 儲存格畫虛線
     *
     * @param dashLength 虛線的長度
     */
    public DottedCellHelper(float dashLength) {
        super();
        this.dashLength = dashLength;
        this.spaceLength = dashLength;
    }

    /**
     * 儲存格畫虛線
     *
     * @param dashLength 虛線的長度
     * @param color      色彩 RGB 值（如: <code>{255, 255, 200}</code>），若不指定則傳入 <code>null</code>
     */
    public DottedCellHelper(float dashLength, int[] color) {
        super();
        this.dashLength = dashLength;
        this.spaceLength = dashLength;
        if (color != null && color.length == 3)
            this.color = new BaseColor(color[0], color[1], color[2]);
    }

    /**
     * 儲存格畫虛線
     *
     * @param dashLength 虛線的長度
     * @param color      色彩
     */
    public DottedCellHelper(float dashLength, BaseColor color) {
        super();
        this.dashLength = dashLength;
        this.spaceLength = dashLength;
        this.color = color;
    }

    /**
     * 儲存格畫虛線
     *
     * @param dashLength  虛線的長度
     * @param spaceLength 虛線間空白的大小
     */
    public DottedCellHelper(float dashLength, float spaceLength) {
        super();
        this.dashLength = dashLength;
        this.spaceLength = spaceLength;
    }

    /**
     * 儲存格畫虛線
     *
     * @param dashLength  虛線的長度
     * @param spaceLength 虛線間空白的大小
     * @param color       色彩 RGB 值（如: <code>{255, 255, 200}</code>），若不指定則傳入 <code>null</code>
     */
    public DottedCellHelper(float dashLength, float spaceLength, int[] color) {
        super();
        this.dashLength = dashLength;
        this.spaceLength = spaceLength;
        if (color != null && color.length == 3)
            this.color = new BaseColor(color[0], color[1], color[2]);
    }

    /**
     * 儲存格畫虛線
     *
     * @param dashLength  虛線的長度
     * @param spaceLength 虛線間空白的大小
     * @param color       色彩
     */
    public DottedCellHelper(float dashLength, float spaceLength, BaseColor color) {
        super();
        this.dashLength = dashLength;
        this.spaceLength = spaceLength;
        this.color = color;
    }

    /**
     * 儲存格畫虛線
     *
     * @param dashLength  虛線的長度
     * @param spaceLength 虛線間空白的大小
     * @param lineWidth   虛線的粗細
     */
    public DottedCellHelper(float dashLength, float spaceLength, float lineWidth) {
        super();
        this.dashLength = dashLength;
        this.spaceLength = spaceLength;
        this.lineWidth = lineWidth;
    }

    /**
     * 儲存格畫虛線
     *
     * @param dashLength  虛線的長度
     * @param spaceLength 虛線間空白的大小
     * @param lineWidth   虛線的粗細
     * @param color       色彩 RGB 值（如: <code>{255, 255, 200}</code>），若不指定則傳入 <code>null</code>
     */
    public DottedCellHelper(float dashLength, float spaceLength, float lineWidth, int[] color) {
        super();
        this.dashLength = dashLength;
        this.spaceLength = spaceLength;
        this.lineWidth = lineWidth;
        if (color != null && color.length == 3)
            this.color = new BaseColor(color[0], color[1], color[2]);
    }

    /**
     * 儲存格畫虛線
     *
     * @param dashLength  虛線的長度
     * @param spaceLength 虛線間空白的大小
     * @param lineWidth   虛線的粗細
     * @param color       色彩
     */
    public DottedCellHelper(float dashLength, float spaceLength, float lineWidth, BaseColor color) {
        super();
        this.dashLength = dashLength;
        this.spaceLength = spaceLength;
        this.lineWidth = lineWidth;
        this.color = color;
    }

    /**
     * 儲存格畫虛線
     *
     * @param borderType 框線的樣式 （如： <code>Rectangle.LEFT|Rectangle.RIGHT</code>）
     */
    public DottedCellHelper(int borderType) {
        super();
        this.borderType = borderType;
    }

    /**
     * 儲存格畫虛線
     *
     * @param borderType 框線的樣式 （如： <code>Rectangle.LEFT|Rectangle.RIGHT</code>）
     * @param color      色彩 RGB 值（如: <code>{255, 255, 200}</code>），若不指定則傳入 <code>null</code>
     */
    public DottedCellHelper(int borderType, int[] color) {
        super();
        this.borderType = borderType;
        if (color != null && color.length == 3)
            this.color = new BaseColor(color[0], color[1], color[2]);
    }

    /**
     * 儲存格畫虛線
     *
     * @param borderType 框線的樣式 （如： <code>Rectangle.LEFT|Rectangle.RIGHT</code>）
     * @param color      色彩
     */
    public DottedCellHelper(int borderType, BaseColor color) {
        super();
        this.borderType = borderType;
        this.color = color;
    }

    /**
     * 儲存格畫虛線
     *
     * @param dashLength 虛線的長度
     * @param borderType 框線的樣式 （如： <code>Rectangle.LEFT|Rectangle.RIGHT</code>）
     */
    public DottedCellHelper(float dashLength, int borderType) {
        super();
        this.dashLength = dashLength;
        this.spaceLength = dashLength;
        this.borderType = borderType;
    }

    /**
     * 儲存格畫虛線
     *
     * @param dashLength 虛線的長度
     * @param borderType 框線的樣式 （如： <code>Rectangle.LEFT|Rectangle.RIGHT</code>）
     * @param color      色彩 RGB 值（如: <code>{255, 255, 200}</code>），若不指定則傳入 <code>null</code>
     */
    public DottedCellHelper(float dashLength, int borderType, int[] color) {
        super();
        this.dashLength = dashLength;
        this.spaceLength = dashLength;
        this.borderType = borderType;
        if (color != null && color.length == 3)
            this.color = new BaseColor(color[0], color[1], color[2]);
    }

    /**
     * 儲存格畫虛線
     *
     * @param dashLength 虛線的長度
     * @param borderType 框線的樣式 （如： <code>Rectangle.LEFT|Rectangle.RIGHT</code>）
     * @param color      色彩
     */
    public DottedCellHelper(float dashLength, int borderType, BaseColor color) {
        super();
        this.dashLength = dashLength;
        this.spaceLength = dashLength;
        this.borderType = borderType;
        this.color = color;
    }

    /**
     * 儲存格畫虛線
     *
     * @param dashLength  虛線的長度
     * @param spaceLength 虛線間空白的大小
     * @param borderType  框線的樣式 （如： <code>Rectangle.LEFT|Rectangle.RIGHT</code>）
     */
    public DottedCellHelper(float dashLength, float spaceLength, int borderType) {
        super();
        this.dashLength = dashLength;
        this.spaceLength = spaceLength;
        this.borderType = borderType;
    }

    /**
     * 儲存格畫虛線
     *
     * @param dashLength  虛線的長度
     * @param spaceLength 虛線間空白的大小
     * @param borderType  框線的樣式 （如： <code>Rectangle.LEFT|Rectangle.RIGHT</code>）
     * @param color       色彩 RGB 值（如: <code>{255, 255, 200}</code>），若不指定則傳入 <code>null</code>
     */
    public DottedCellHelper(float dashLength, float spaceLength, int borderType, int[] color) {
        super();
        this.dashLength = dashLength;
        this.spaceLength = spaceLength;
        this.borderType = borderType;
        if (color != null && color.length == 3)
            this.color = new BaseColor(color[0], color[1], color[2]);
    }

    /**
     * 儲存格畫虛線
     *
     * @param dashLength  虛線的長度
     * @param spaceLength 虛線間空白的大小
     * @param borderType  框線的樣式 （如： <code>Rectangle.LEFT|Rectangle.RIGHT</code>）
     * @param color       色彩
     */
    public DottedCellHelper(float dashLength, float spaceLength, int borderType, BaseColor color) {
        super();
        this.dashLength = dashLength;
        this.spaceLength = spaceLength;
        this.borderType = borderType;
        this.color = color;
    }

    /**
     * 儲存格畫虛線
     *
     * @param dashLength  虛線的長度
     * @param spaceLength 虛線間空白的大小
     * @param lineWidth   虛線的粗細
     * @param borderType  框線的樣式 （如： <code>Rectangle.LEFT|Rectangle.RIGHT</code>）
     */
    public DottedCellHelper(float dashLength, float spaceLength, float lineWidth, int borderType) {
        super();
        this.dashLength = dashLength;
        this.spaceLength = spaceLength;
        this.lineWidth = lineWidth;
        this.borderType = borderType;
    }

    /**
     * 儲存格畫虛線
     *
     * @param dashLength  虛線的長度
     * @param spaceLength 虛線間空白的大小
     * @param lineWidth   虛線的粗細
     * @param borderType  框線的樣式 （如： <code>Rectangle.LEFT|Rectangle.RIGHT</code>）
     * @param color       色彩 RGB 值（如: <code>{255, 255, 200}</code>），若不指定則傳入 <code>null</code>
     */
    public DottedCellHelper(float dashLength, float spaceLength, float lineWidth, int borderType, int[] color) {
        super();
        this.dashLength = dashLength;
        this.spaceLength = spaceLength;
        this.lineWidth = lineWidth;
        this.borderType = borderType;
        if (color != null && color.length == 3)
            this.color = new BaseColor(color[0], color[1], color[2]);
    }

    /**
     * 儲存格畫虛線
     *
     * @param dashLength  虛線的長度
     * @param spaceLength 虛線間空白的大小
     * @param lineWidth   虛線的粗細
     * @param borderType  框線的樣式 （如： <code>Rectangle.LEFT|Rectangle.RIGHT</code>）
     * @param color       色彩
     */
    public DottedCellHelper(float dashLength, float spaceLength, float lineWidth, int borderType, BaseColor color) {
        super();
        this.dashLength = dashLength;
        this.spaceLength = spaceLength;
        this.lineWidth = lineWidth;
        this.borderType = borderType;
        this.color = color;
    }

    public void cellLayout(PdfPCell cell, Rectangle position, PdfContentByte[] canvases) {
        PdfContentByte canvas = canvases[PdfPTable.LINECANVAS];

        if (borderType == 0) {
            canvas.saveState();
            canvas.setLineDash(dashLength, spaceLength, 0);
            canvas.setLineWidth(lineWidth);
            canvas.setColorStroke(color);
            canvas.rectangle(position.getLeft(), position.getBottom(), position.getWidth(), position.getHeight());
            canvas.stroke();
            canvas.restoreState();
        }
        else {
            canvas.saveState();
            canvas.setLineDash(dashLength, spaceLength, 0);
            canvas.setLineWidth(lineWidth);
            canvas.setColorStroke(color);
            if ((Rectangle.TOP & borderType) != 0) {
                canvas.moveTo(position.getLeft(), position.getTop());
                canvas.lineTo(position.getRight(), position.getTop());
            }
            if ((Rectangle.BOTTOM & borderType) != 0) {
                canvas.moveTo(position.getLeft(), position.getBottom());
                canvas.lineTo(position.getRight(), position.getBottom());
            }
            if ((Rectangle.LEFT & borderType) != 0) {
                canvas.moveTo(position.getLeft(), position.getTop());
                canvas.lineTo(position.getLeft(), position.getBottom());
            }
            if ((Rectangle.RIGHT & borderType) != 0) {
                canvas.moveTo(position.getRight(), position.getTop());
                canvas.lineTo(position.getRight(), position.getBottom());
            }
            canvas.stroke();
            canvas.restoreState();
        }
    }
}
