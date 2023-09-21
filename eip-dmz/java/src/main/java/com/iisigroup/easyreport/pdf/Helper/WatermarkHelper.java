package com.iisigroup.easyreport.pdf.Helper;

import org.apache.commons.lang3.StringUtils;
import com.iisigroup.easyreport.pdf.exception.ReportException;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.GrayColor;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * 浮水印 - 支援文字或圖片做為浮水印
 * 
 * @author Goston
 */
public class WatermarkHelper extends PdfPageEventHelper {
    private Font font = null;
    private String text = null;
    private int angle = 0;
    private boolean flip = false;
    private Image image = null;
    private int x = -1;
    private int y = -1;
    private int scale = -1;

    /**
     * 在文件中央顯示文字浮水印（粗體樣式）
     * 
     * @param text 浮水印文字
     * @param fontSize 字體大小
     */
    public WatermarkHelper(String text, int fontSize) throws ReportException {
        super();

        try {
            BaseFont basefont = BaseFont.createFont(EnvHelper.getFontPath() + EnvHelper.getFontName(), BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            this.font = new Font(basefont, fontSize, Font.BOLD, new GrayColor(0.75f));
            this.text = StringUtils.defaultString(text, "Watermark");
        }
        catch (Exception e) {
            throw new ReportException(e);
        }
    }

    /**
     * 在文件中央顯示文字浮水印（粗體樣式）
     * 
     * @param text 浮水印文字
     * @param fontSize 字體大小
     * @param alpha 透明度 <code>0</code> 至 <code>1.0</code> 間，數字愈小愈不透明
     */
    public WatermarkHelper(String text, int fontSize, float alpha) throws ReportException {
        super();

        try {
            BaseFont basefont = BaseFont.createFont(EnvHelper.getFontPath() + EnvHelper.getFontName(), BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            this.font = new Font(basefont, fontSize, Font.BOLD, new GrayColor(alpha));
            this.text = StringUtils.defaultString(text, "Watermark");
        }
        catch (Exception e) {
            throw new ReportException(e);
        }
    }

    /**
     * 在文件中央顯示文字浮水印（粗體樣式）
     * 
     * @param text 浮水印文字
     * @param fontSize 字體大小
     * @param alpha 透明度 <code>0</code> 至 <code>1.0</code> 間，數字愈小愈不透明
     * @param angle 角度 <code>0</code> 為水平，逆時鐘方向為正值，順時鐘為負值。
     */
    public WatermarkHelper(String text, int fontSize, float alpha, int angle) throws ReportException {
        super();

        try {
            BaseFont basefont = BaseFont.createFont(EnvHelper.getFontPath() + EnvHelper.getFontName(), BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            this.font = new Font(basefont, fontSize, Font.BOLD, new GrayColor(alpha));
            this.text = StringUtils.defaultString(text, "Watermark");
            this.angle = angle;
        }
        catch (Exception e) {
            throw new ReportException(e);
        }
    }

    /**
     * 在文件中央顯示文字浮水印（粗體樣式）
     * 
     * @param text 浮水印文字
     * @param fontSize 字體大小
     * @param alpha 透明度 <code>0</code> 至 <code>1.0</code> 間，數字愈小愈不透明
     * @param angle 角度 <code>0</code> 為水平，逆時鐘方向為正值，順時鐘為負值。
     * @param flip 奇偶頁角度是否翻轉
     */
    public WatermarkHelper(String text, int fontSize, float alpha, int angle, boolean flip) throws ReportException {
        super();

        try {
            BaseFont basefont = BaseFont.createFont(EnvHelper.getFontPath() + EnvHelper.getFontName(), BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            this.font = new Font(basefont, fontSize, Font.BOLD, new GrayColor(alpha));
            this.text = StringUtils.defaultString(text, "Watermark");
            this.angle = angle;
            this.flip = flip;
        }
        catch (Exception e) {
            throw new ReportException(e);
        }
    }

    /**
     * 在文件指定位置顯示文字浮水印（粗體樣式）
     * 
     * @param text 浮水印文字
     * @param fontSize 字體大小
     * @param alpha 透明度 <code>0</code> 至 <code>1.0</code> 間，數字愈小愈不透明
     * @param angle 角度 <code>0</code> 為水平，逆時鐘方向為正值，順時鐘為負值。
     * @param flip 奇偶頁角度是否翻轉
     * @param x 浮水印位置 <code>X</code> 軸
     * @param y 浮水印位置 <code>Y</code> 軸
     */
    public WatermarkHelper(String text, int fontSize, float alpha, int angle, boolean flip, int x, int y) throws ReportException {
        super();

        try {
            BaseFont basefont = BaseFont.createFont(EnvHelper.getFontPath() + EnvHelper.getFontName(), BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            this.font = new Font(basefont, fontSize, Font.BOLD, new GrayColor(alpha));
            this.text = StringUtils.defaultString(text, "Watermark");
            this.angle = angle;
            this.flip = flip;
            this.x = x;
            this.y = y;
        }
        catch (Exception e) {
            throw new ReportException(e);
        }
    }

    /**
     * 在文件指定位置顯示文字浮水印
     * 
     * @param font 字體及樣式
     * @param text 浮水印文字
     * @param angle 角度 <code>0</code> 為水平，逆時鐘方向為正值，順時鐘為負值。
     * @param flip 奇偶頁角度是否翻轉
     * @param x 浮水印位置 <code>X</code> 軸
     * @param y 浮水印位置 <code>Y</code> 軸
     */
    public WatermarkHelper(Font font, String text, int angle, boolean flip, int x, int y) {
        super();
        this.font = font;
        this.text = StringUtils.defaultString(text, "Watermark");
        this.angle = angle;
        this.flip = flip;
        this.x = x;
        this.y = y;
    }

    /**
     * 在文件中央顯示圖片浮水印
     * 
     * @param image 要當做浮水印的圖片
     */
    public WatermarkHelper(Image image) {
        super();
        this.image = image;
    }

    /**
     * 在文件中央顯示圖片浮水印
     * 
     * @param image 要當做浮水印的圖片
     * @param scale 縮放百分比，如 <code>50</code>(%)
     */
    public WatermarkHelper(Image image, int scale) {
        super();

        if (scale > 0)
            image.scalePercent(scale);

        this.image = image;
        this.scale = scale;
    }

    /**
     * 在文件指定位置顯示圖片浮水印
     * 
     * @param image 要當做浮水印的圖片
     * @param x 浮水印位置 <code>X</code> 軸
     * @param y 浮水印位置 <code>Y</code> 軸
     */
    public WatermarkHelper(Image image, int x, int y) {
        super();
        this.image = image;
        this.x = x;
        this.y = y;
    }

    /**
     * 在文件指定位置顯示圖片浮水印
     * 
     * @param image 要當做浮水印的圖片
     * @param x 浮水印位置 <code>X</code> 軸
     * @param y 浮水印位置 <code>Y</code> 軸
     * @param scale 縮放百分比如 <code>1</code> - <code>100</code>(%)
     */
    public WatermarkHelper(Image image, int x, int y, int scale) {
        super();

        if (scale > 0)
            image.scalePercent(scale);

        this.image = image;
        this.x = x;
        this.y = y;
        this.scale = scale;
    }

    @Override
    public void onEndPage(PdfWriter writer, Document document) {
        Rectangle size = document.getPageSize();

        if (text != null) {
            // 文字浮水印
            Phrase phrase = new Phrase(text, font);

            if (x == -1 && y == -1) {
                x = (int) size.getWidth() / 2;
                y = (int) size.getHeight() / 2;
            }

            ColumnText.showTextAligned(writer.getDirectContentUnder(), Element.ALIGN_CENTER, phrase, x, y, (flip ? (writer.getPageNumber() % 2 == 1 ? angle : -angle) : angle));
        }
        else {
            if (x == -1 && y == -1) {
                x = Math.abs((int) ((size.getWidth() - image.getScaledWidth()) / 2));
                y = Math.abs((int) ((size.getHeight() - image.getScaledHeight()) / 2));
            }

            // 底色白色變為透明
            image.setTransparency(new int[] { 255, 255, 255, 255, 255, 255 });

            // 圖片位置
            image.setAbsolutePosition(x, y);

            // 圖片浮水印
            try {
                writer.getDirectContentUnder().addImage(image);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
