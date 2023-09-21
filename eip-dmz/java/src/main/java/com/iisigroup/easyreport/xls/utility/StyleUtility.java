package com.iisigroup.easyreport.xls.utility;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;

/**
 * POI 升級配合修改
 */
public class StyleUtility {
    // 水平位置
    public static final short ALIGN_CENTER = HorizontalAlignment.CENTER.getCode();
    public static final short ALIGN_CENTER_SELECTION = HorizontalAlignment.CENTER_SELECTION.getCode();
    public static final short ALIGN_FILL = HorizontalAlignment.FILL.getCode();
    public static final short ALIGN_GENERAL = HorizontalAlignment.GENERAL.getCode();
    public static final short ALIGN_JUSTIFY = HorizontalAlignment.JUSTIFY.getCode();
    public static final short ALIGN_LEFT = HorizontalAlignment.LEFT.getCode();
    public static final short ALIGN_RIGHT = HorizontalAlignment.RIGHT.getCode();

    // 垂直位置
    public final static short VERTICAL_BOTTOM = VerticalAlignment.BOTTOM.getCode();
    public final static short VERTICAL_CENTER = VerticalAlignment.CENTER.getCode();
    public final static short VERTICAL_JUSTIFY = VerticalAlignment.JUSTIFY.getCode();
    public final static short VERTICAL_TOP = VerticalAlignment.TOP.getCode();

    // 邊框線條寬度
    public final static short BORDER_TYPE_0 = 0;// 上x 下x 左x 右x
    public final static short BORDER_TYPE_1 = 1;// 上o 下x 左x 右x
    public final static short BORDER_TYPE_2 = 2;// 上x 下o 左x 右x
    public final static short BORDER_TYPE_3 = 3;// 上x 下x 左o 右x
    public final static short BORDER_TYPE_4 = 4;// 上x 下x 左x 右o
    public final static short BORDER_TYPE_5 = 5;// 上o 下o 左x 右x
    public final static short BORDER_TYPE_6 = 6;// 上o 下x 左o 右x
    public final static short BORDER_TYPE_7 = 7;// 上o 下x 左x 右o
    public final static short BORDER_TYPE_8 = 8;// 上x 下o 左o 右x
    public final static short BORDER_TYPE_9 = 9;// 上x 下o 左x 右o
    public final static short BORDER_TYPE_10 = 10;// 上x 下x 左o 右o
    public final static short BORDER_TYPE_11 = 11;// 上o 下o 左o 右x
    public final static short BORDER_TYPE_12 = 12;// 上o 下o 左x 右o
    public final static short BORDER_TYPE_13 = 13;// 上x 下o 左o 右o
    public final static short BORDER_TYPE_14 = 14;// 上o 下o 左o 右o

    // 邊框線條寬度
    public final static short BORDER_DASH_DOT = BorderStyle.DASH_DOT.getCode();
    public final static short BORDER_DASH_DOT_DOT = BorderStyle.DASH_DOT_DOT.getCode();
    public final static short BORDER_DASHED = BorderStyle.DASHED.getCode();
    public final static short BORDER_DOTTED = BorderStyle.DOTTED.getCode();
    public final static short BORDER_DOUBLE = BorderStyle.DOUBLE.getCode();
    public final static short BORDER_HAIR = BorderStyle.HAIR.getCode();
    public final static short BORDER_MEDIUM = BorderStyle.MEDIUM.getCode();
    public final static short BORDER_MEDIUM_DASH_DOT = BorderStyle.MEDIUM_DASH_DOT.getCode();
    public final static short BORDER_MEDIUM_DASH_DOT_DOT = BorderStyle.MEDIUM_DASH_DOT_DOT.getCode();
    public final static short BORDER_MEDIUM_DASHED = BorderStyle.MEDIUM_DASHED.getCode();
    public final static short BORDER_NONE = BorderStyle.NONE.getCode();
    public final static short BORDER_SLANTED_DASH_DOT = BorderStyle.SLANTED_DASH_DOT.getCode();
    public final static short BORDER_THICK = BorderStyle.THICK.getCode();
    public final static short BORDER_THIN = BorderStyle.THIN.getCode();

    // 填充格樣式
    public static final short FILL_PATTERN_BRICKS = FillPatternType.BRICKS.getCode();
    public static final short FILL_PATTERN_DIAMONDS = FillPatternType.DIAMONDS.getCode();
    public static final short FILL_PATTERN_FINE_DOTS = FillPatternType.FINE_DOTS.getCode();
    public static final short FILL_PATTERN_LEAST_DOTS = FillPatternType.LEAST_DOTS.getCode();
    public static final short FILL_PATTERN_LESS_DOTS = FillPatternType.LESS_DOTS.getCode();
    public static final short FILL_PATTERN_NO_FILL = FillPatternType.NO_FILL.getCode();
    public static final short FILL_PATTERN_SOLID_FOREGROUND = FillPatternType.SOLID_FOREGROUND.getCode();
    public static final short FILL_PATTERN_SPARSE_DOTS = FillPatternType.SPARSE_DOTS.getCode();
    public static final short FILL_PATTERN_SQUARES = FillPatternType.SQUARES.getCode();
    public static final short FILL_PATTERN_THICK_BACKWARD_DIAG = FillPatternType.THICK_BACKWARD_DIAG.getCode();
    public static final short FILL_PATTERN_THICK_FORWARD_DIAG = FillPatternType.THICK_FORWARD_DIAG.getCode();
    public static final short FILL_PATTERN_THICK_HORZ_BANDS = FillPatternType.THICK_HORZ_BANDS.getCode();
    public static final short FILL_PATTERN_THICK_VERT_BANDS = FillPatternType.THICK_VERT_BANDS.getCode();
    public static final short FILL_PATTERN_THIN_BACKWARD_DIAG = FillPatternType.THIN_BACKWARD_DIAG.getCode();
    public static final short FILL_PATTERN_THIN_FORWARD_DIAG = FillPatternType.THIN_FORWARD_DIAG.getCode();
    public static final short FILL_PATTERN_THIN_HORZ_BANDS = FillPatternType.THIN_HORZ_BANDS.getCode();
    public static final short FILL_PATTERN_THIN_VERT_BANDS = FillPatternType.THIN_VERT_BANDS.getCode();

    // 字型底線樣式
    public static final short U_NONE = Font.U_NONE;
    public static final short U_SINGLE = Font.U_SINGLE;
    public static final short U_SINGLE_ACCOUNTING = Font.U_SINGLE_ACCOUNTING;
    public static final short U_DOUBLE = Font.U_DOUBLE;
    public static final short U_DOUBLE_ACCOUNTING = Font.U_DOUBLE_ACCOUNTING;

    /**
     * 設定 cellstyle
     *
     * @param workbook
     * @param fillPattern
     * @param fillBackgroundColor
     * @param fillForegroundColor
     * @param borderType<br>      0：上x 下x 左x 右x<br>
     *                            1：上o 下x 左x 右x<br>
     *                            2：上x 下o 左x 右x<br>
     *                            3：上x 下x 左o 右x<br>
     *                            4：上x 下x 左x 右o<br>
     *                            5：上o 下o 左x 右x<br>
     *                            6：上o 下x 左o 右x<br>
     *                            7：上o 下x 左x 右o<br>
     *                            8：上x 下o 左o 右x<br>
     *                            9：上x 下o 左x 右o<br>
     *                            10：上x 下x 左o 右o<br>
     *                            11：上o 下o 左o 右x<br>
     *                            12：上o 下o 左x 右o<br>
     *                            13：上x 下o 左o 右o<br>
     *                            14：上o 下o 左o 右o<br>
     *                            <br>
     * @param borderWidth
     * @param autoWarp
     * @param alignment
     * @param vAlignment
     * @param font
     * @param dataFormat
     * @return
     */
    public static CellStyle formatCellStyle(Workbook workbook, short fillPattern, short fillBackgroundColor, short fillForegroundColor, short borderType, short borderWidth, boolean autoWarp, short alignment, short vAlignment, Font font,
                                            String dataFormat) {
        CellStyle styleCell = workbook.createCellStyle();
        styleCell.setFillPattern(FillPatternType.forInt(fillPattern)); // 設置可填充儲存格底色
        styleCell.setFillBackgroundColor(fillBackgroundColor); // 指定圖樣顏色
        styleCell.setFillForegroundColor(fillForegroundColor); // 指定底色
        styleCell.setAlignment(HorizontalAlignment.forInt(alignment)); // 水平置中
        styleCell.setVerticalAlignment(VerticalAlignment.forInt(vAlignment)); // 垂直置中

        // 設定框線
        switch (borderType) {
            case (short) BORDER_TYPE_0:
                styleCell.setBorderTop(BorderStyle.valueOf(BORDER_NONE));
                styleCell.setBorderBottom(BorderStyle.valueOf(BORDER_NONE));
                styleCell.setBorderLeft(BorderStyle.valueOf(BORDER_NONE));
                styleCell.setBorderRight(BorderStyle.valueOf(BORDER_NONE));
            case (short) BORDER_TYPE_1:
                styleCell.setBorderTop(BorderStyle.valueOf(borderWidth));
                styleCell.setBorderBottom(BorderStyle.valueOf(BORDER_NONE));
                styleCell.setBorderLeft(BorderStyle.valueOf(BORDER_NONE));
                styleCell.setBorderRight(BorderStyle.valueOf(BORDER_NONE));
                break;
            case (short) BORDER_TYPE_2:
                styleCell.setBorderTop(BorderStyle.valueOf(BORDER_NONE));
                styleCell.setBorderBottom(BorderStyle.valueOf(borderWidth));
                styleCell.setBorderLeft(BorderStyle.valueOf(BORDER_NONE));
                styleCell.setBorderRight(BorderStyle.valueOf(BORDER_NONE));
                break;
            case (short) BORDER_TYPE_3:
                styleCell.setBorderTop(BorderStyle.valueOf(BORDER_NONE));
                styleCell.setBorderBottom(BorderStyle.valueOf(BORDER_NONE));
                styleCell.setBorderLeft(BorderStyle.valueOf(borderWidth));
                styleCell.setBorderRight(BorderStyle.valueOf(BORDER_NONE));
                break;
            case (short) BORDER_TYPE_4:
                styleCell.setBorderTop(BorderStyle.valueOf(BORDER_NONE));
                styleCell.setBorderBottom(BorderStyle.valueOf(BORDER_NONE));
                styleCell.setBorderLeft(BorderStyle.valueOf(BORDER_NONE));
                styleCell.setBorderRight(BorderStyle.valueOf(borderWidth));
                break;
            case (short) BORDER_TYPE_5:
                styleCell.setBorderTop(BorderStyle.valueOf(borderWidth));
                styleCell.setBorderBottom(BorderStyle.valueOf(borderWidth));
                styleCell.setBorderLeft(BorderStyle.valueOf(BORDER_NONE));
                styleCell.setBorderRight(BorderStyle.valueOf(BORDER_NONE));
                break;
            case (short) BORDER_TYPE_6:
                styleCell.setBorderTop(BorderStyle.valueOf(borderWidth));
                styleCell.setBorderBottom(BorderStyle.valueOf(BORDER_NONE));
                styleCell.setBorderLeft(BorderStyle.valueOf(borderWidth));
                styleCell.setBorderRight(BorderStyle.valueOf(BORDER_NONE));
                break;
            case (short) BORDER_TYPE_7:
                styleCell.setBorderTop(BorderStyle.valueOf(borderWidth));
                styleCell.setBorderBottom(BorderStyle.valueOf(BORDER_NONE));
                styleCell.setBorderLeft(BorderStyle.valueOf(BORDER_NONE));
                styleCell.setBorderRight(BorderStyle.valueOf(borderWidth));
                break;
            case (short) BORDER_TYPE_8:
                styleCell.setBorderTop(BorderStyle.valueOf(BORDER_NONE));
                styleCell.setBorderBottom(BorderStyle.valueOf(borderWidth));
                styleCell.setBorderLeft(BorderStyle.valueOf(borderWidth));
                styleCell.setBorderRight(BorderStyle.valueOf(BORDER_NONE));
                break;
            case (short) BORDER_TYPE_9:
                styleCell.setBorderTop(BorderStyle.valueOf(BORDER_NONE));
                styleCell.setBorderBottom(BorderStyle.valueOf(borderWidth));
                styleCell.setBorderLeft(BorderStyle.valueOf(BORDER_NONE));
                styleCell.setBorderRight(BorderStyle.valueOf(borderWidth));
                break;
            case (short) BORDER_TYPE_10:
                styleCell.setBorderTop(BorderStyle.valueOf(BORDER_NONE));
                styleCell.setBorderBottom(BorderStyle.valueOf(BORDER_NONE));
                styleCell.setBorderLeft(BorderStyle.valueOf(borderWidth));
                styleCell.setBorderRight(BorderStyle.valueOf(borderWidth));
                break;
            case (short) BORDER_TYPE_11:
                styleCell.setBorderTop(BorderStyle.valueOf(borderWidth));
                styleCell.setBorderBottom(BorderStyle.valueOf(borderWidth));
                styleCell.setBorderLeft(BorderStyle.valueOf(borderWidth));
                styleCell.setBorderRight(BorderStyle.valueOf(BORDER_NONE));
                break;
            case (short) BORDER_TYPE_12:
                styleCell.setBorderTop(BorderStyle.valueOf(borderWidth));
                styleCell.setBorderBottom(BorderStyle.valueOf(borderWidth));
                styleCell.setBorderLeft(BorderStyle.valueOf(BORDER_NONE));
                styleCell.setBorderRight(BorderStyle.valueOf(borderWidth));
                break;
            case (short) BORDER_TYPE_13:
                styleCell.setBorderTop(BorderStyle.valueOf(BORDER_NONE));
                styleCell.setBorderBottom(BorderStyle.valueOf(borderWidth));
                styleCell.setBorderLeft(BorderStyle.valueOf(borderWidth));
                styleCell.setBorderRight(BorderStyle.valueOf(borderWidth));
                break;
            case (short) BORDER_TYPE_14:
                styleCell.setBorderTop(BorderStyle.valueOf(borderWidth));
                styleCell.setBorderBottom(BorderStyle.valueOf(borderWidth));
                styleCell.setBorderLeft(BorderStyle.valueOf(borderWidth));
                styleCell.setBorderRight(BorderStyle.valueOf(borderWidth));
                break;
        }

        // 自動換行
        styleCell.setWrapText(autoWarp);

        // 字型設定
        if (font != null) {
            styleCell.setFont(font); // 設定字體
        }

        // 設定資料格式
        if (StringUtils.isNotBlank(dataFormat)) {
            DataFormat format = workbook.createDataFormat();
            styleCell.setDataFormat(format.getFormat(dataFormat));
        }

        return styleCell;
    }

    /**
     * 設定 dataFormat 格式
     *
     * @param workbook
     * @param cellStyle
     * @param dataFormat
     * @return
     */
    public static CellStyle setDataFormat(Workbook workbook, CellStyle cellStyle, String dataFormat) {
        if (StringUtils.isNotBlank(dataFormat)) {
            DataFormat format = workbook.createDataFormat();
            cellStyle.setDataFormat(format.getFormat(dataFormat));
        }
        return cellStyle;
    }

    /**
     * 設定字形樣式便於設定CellStyle使用
     *
     * @param workbook
     * @param fontName
     * @param fontSize
     * @param fontColor
     * @param isBold
     * @param isItalic
     * @param isStrikeout
     * @param underline
     * @return
     */
    public static Font formatFont(Workbook workbook, String fontName, short fontSize, short fontColor, boolean isBold, boolean isItalic, boolean isStrikeout, byte underline) {
        Font font = workbook.createFont();
        font.setFontName(fontName); // 設定字體
        font.setFontHeightInPoints(fontSize);// 設定字體大小
        font.setColor(fontColor); // 顏色
        font.setBold(isBold);
        font.setItalic(isItalic);// 斜體
        font.setStrikeout(isStrikeout);// 刪除線
        font.setUnderline(underline);// 底線
        return font;
    }
}
