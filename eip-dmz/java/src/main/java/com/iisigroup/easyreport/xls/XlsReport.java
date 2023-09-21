package com.iisigroup.easyreport.xls;

import com.iisigroup.easyreport.xls.utility.StyleUtility;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import tw.gov.pcc.eip.Global;
import tw.gov.pcc.eip.util.StringUtility;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;

public class XlsReport {
    /**
     * align 水平位置<br>
     * <br>
     * CENTER：水平置中 <br>
     * RIGHT：水平置右<br>
     * LEFT：水平置左<br>
     * CENTER_SELECTION：<br>
     * FILL：<br>
     * GENERAL：<br>
     * JUSTIFY：<br>
     */
    public static final int ALIGN_CENTER = StyleUtility.ALIGN_CENTER; // 水平置中
    public static final int ALIGN_RIGHT = StyleUtility.ALIGN_RIGHT; // 水平置右
    public static final int ALIGN_LEFT = StyleUtility.ALIGN_LEFT; // 水平置左
    public static final int ALIGN_CENTER_SELECTION = StyleUtility.ALIGN_CENTER_SELECTION;
    public static final int ALIGN_FILL = StyleUtility.ALIGN_FILL;
    public static final int ALIGN_GENERAL = StyleUtility.ALIGN_GENERAL;
    public static final int ALIGN_JUSTIFY = StyleUtility.ALIGN_JUSTIFY;
    /**
     * valign 垂直位置<br>
     * <br>
     * TOP：垂直置頂 <br>
     * MIDDLE：垂直置中<br>
     * BOTTON：垂直置底<br>
     * VJUSTIFY：<br>
     */
    public static final int VERTICAL_TOP = StyleUtility.VERTICAL_TOP; // 垂直置頂
    public static final int VERTICAL_MIDDLE = StyleUtility.VERTICAL_CENTER; // 垂直置中
    public static final int VERTICAL_BOTTON = StyleUtility.VERTICAL_BOTTOM; // 垂直置底
    public static final int VERTICAL_VJUSTIFY = StyleUtility.VERTICAL_JUSTIFY;
    public static final int UNDEFINED = -999999999; // 不定義
    /**
     * borderType 邊框樣式 <br>
     * <br>
     * 0：無框線 <br>
     * 1：上 <br>
     * 2：下 <br>
     * 3：上下 <br>
     * 4：左 <br>
     * 5：上左 <br>
     * 6：下左 <br>
     * 7：上下左 <br>
     * 8：右 <br>
     * 9：上右 <br>
     * 10：下右 <br>
     * 11：上下右 <br>
     * 12：左右 <br>
     * 13：上左右 <br>
     * 14：下左右 <br>
     * 15：上下左右 <br>
     */
    public static final int BORDER_TYPE_0 = 0; // 無框線
    public static final int BORDER_TYPE_1 = 1; // 上
    public static final int BORDER_TYPE_2 = 2; // 下
    public static final int BORDER_TYPE_3 = 3; // 上下
    public static final int BORDER_TYPE_4 = 4; // 左
    public static final int BORDER_TYPE_5 = 5; // 上左
    public static final int BORDER_TYPE_6 = 6; // 下左
    public static final int BORDER_TYPE_7 = 7; // 上下左
    public static final int BORDER_TYPE_8 = 8; // 右
    public static final int BORDER_TYPE_9 = 9; // 上右
    public static final int BORDER_TYPE_10 = 10; // 下右
    public static final int BORDER_TYPE_11 = 11; // 上下右
    public static final int BORDER_TYPE_12 = 12; // 左右
    public static final int BORDER_TYPE_13 = 13; // 上左右
    public static final int BORDER_TYPE_14 = 14; // 下左右
    public static final int BORDER_TYPE_15 = 15; // 上下左右
    /**
     * BorderSize <br>
     * <br>
     * BORDER_NONE：無<br>
     * BORDER_THIN：細邊線 (預設)<br>
     * BORDER_THICK：粗邊線<br>
     * BORDER_DOUBLE：雙邊線<br>
     * BORDER_MEDIUM：中等邊線<br>
     * BORDER_MEDIUM_DASHED：中等虛線邊線<br>
     * BORDER_MEDIUM_DASH_DOT：中等線點虛線邊線<br>
     * BORDER_MEDIUM_DASH_DOT_DOT：中等線點點虛線邊線<br>
     * BORDER_HAIR：小圓點虛線邊線<br>
     * BORDER_DASHED：虛線邊線<br>
     * BORDER_DASH_DOT：線點虛線邊線<br>
     * BORDER_DASH_DOT_DOT：線點點虛線邊線<br>
     * BORDER_DOTTED：大圓點虛線邊線<br>
     * BORDER_SLANTED_DASH_DOT：粗線點虛線邊線<br>
     */
    public static final int BORDER_NONE = StyleUtility.BORDER_NONE; // 無
    public static final int BORDER_THIN = StyleUtility.BORDER_THIN; // 細邊線 (預設)
    public static final int BORDER_THICK = StyleUtility.BORDER_THICK; // 粗邊線
    public static final int BORDER_DOUBLE = StyleUtility.BORDER_DOUBLE; // 雙邊線
    public static final int BORDER_MEDIUM = StyleUtility.BORDER_MEDIUM; // 中等邊線
    public static final int BORDER_MEDIUM_DASHED = StyleUtility.BORDER_MEDIUM_DASHED; // 中等虛線邊線
    public static final int BORDER_MEDIUM_DASH_DOT = StyleUtility.BORDER_MEDIUM_DASH_DOT; // 中等線點虛線邊線
    public static final int BORDER_MEDIUM_DASH_DOT_DOT = StyleUtility.BORDER_MEDIUM_DASH_DOT_DOT; // 中等線點點虛線邊線
    public static final int BORDER_HAIR = StyleUtility.BORDER_HAIR; // 小圓點虛線邊線
    public static final int BORDER_DASHED = StyleUtility.BORDER_DASHED; // 虛線邊線
    public static final int BORDER_DASH_DOT = StyleUtility.BORDER_DASH_DOT; // 線點虛線邊線
    public static final int BORDER_DASH_DOT_DOT = StyleUtility.BORDER_DASH_DOT_DOT; // 線點點虛線邊線
    public static final int BORDER_DOTTED = StyleUtility.BORDER_DOTTED; // 大圓點虛線邊線
    public static final int BORDER_SLANTED_DASH_DOT = StyleUtility.BORDER_SLANTED_DASH_DOT; // 粗線點虛線邊線
    /**
     * CellType
     */
    public final int CELL_TYPE_NUMERIC = CellType.NUMERIC.getCode(); // Cell型態: 數字
    public final int CELL_TYPE_STRING = CellType.STRING.getCode(); // Cell型態: 文字
    public final int CELL_TYPE_FORMULA = CellType.FORMULA.getCode(); // Cell型態: 公式
    public final int CELL_TYPE_BLANK = CellType.BLANK.getCode(); // Cell型態:
    public final int CELL_TYPE_BOOLEAN = CellType.BOOLEAN.getCode(); // Cell型態:
    public final int CELL_TYPE_ERROR = CellType.ERROR.getCode(); // Cell型態:
    /**
     * picture type
     */
    public final int PICTURE_TYPE_DIB = Workbook.PICTURE_TYPE_DIB; // Device independent bitmap
    public final int PICTURE_TYPE_EMF = Workbook.PICTURE_TYPE_EMF; // Extended windows meta file
    public final int PICTURE_TYPE_JPEG = Workbook.PICTURE_TYPE_JPEG; // JPEG format
    public final int PICTURE_TYPE_PICT = Workbook.PICTURE_TYPE_PICT; // Mac PICT format
    public final int PICTURE_TYPE_PNG = Workbook.PICTURE_TYPE_PNG; // PNG format
    public final int PICTURE_TYPE_WMF = Workbook.PICTURE_TYPE_WMF; // Windows Meta File
    protected String resourcePath = "";
    protected Workbook workbook;
    protected CellStyle cellStyle;
    protected Font font;
    protected ByteArrayOutputStream bao;
    private String fileName = "";// 報表名稱
    private int[] columnType;// 欄位型態
    private int columnCount = 12;// 表格欄數
    private int headSize = 0; // 報表表頭row數
    private int bodySize = 30;// 每頁excel的內容row筆數
    private int footSize = 0; // 報表表底row數
    private int pageSize = 36; // 每頁excel row筆數
    private int currentPage = 0; // 目前頁數
    private int currentRow = 0; // 目前筆數(從0開始)
    private boolean isEject = false;// 是否分頁
    private boolean isPrintHead = false;// 是否每頁印表體表頭(true:每頁印;false:第一頁印)
    private boolean isPrintFoot = false;// 是否每頁印表體表底(true:每頁印:false:最後一頁印)

    /**
     * 產生workbook (xls格式)
     *
     * @param
     */
    public XlsReport() {
        try {
            workbook = new HSSFWorkbook();
            initFont(workbook);
            initCellStyle(workbook);
            bao = new ByteArrayOutputStream();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 產生workbook
     *
     * @param fileFlag true:xlsx, false:xls
     */
    public XlsReport(boolean fileFlag) {
        try {
            if (fileFlag) {
                workbook = new XSSFWorkbook();
            } else {
                workbook = new HSSFWorkbook();
            }
            initFont(workbook);
            initCellStyle(workbook);
            bao = new ByteArrayOutputStream();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 讀入已存在之EXCEL檔
     *
     * @param filePath
     */
    public XlsReport(String filePath) {
        try {
            // File templet = new File(filePath);
            // FileInputStream fis = new FileInputStream(templet);
            // POIFSFileSystem pfs = new POIFSFileSystem(fis);
            workbook = WorkbookFactory.create(new File(FilenameUtils.getBaseName(filePath)));
            bao = new ByteArrayOutputStream();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 將BigDecimal 轉成 double
     *
     * @param nNumber
     * @return
     */
    public static double formatBigDecimalToDouble(BigDecimal nNumber) {
        double returnValue = 0;

        if (nNumber != null) {
            returnValue = nNumber.doubleValue();
        }

        return returnValue;
    }

    /**
     * 將 BigDecimal 的值轉成字串
     *
     * @param nNumber
     * @return
     */
    public static String formatBigDecimalToString(BigDecimal nNumber) {
        if (nNumber != null)
            return nNumber.toPlainString();
        else
            return "";
    }

    public static String getDefaultStr(String str) {
        if (StringUtils.isNotBlank(str)) {
            return str;
        } else {
            return "";
        }
    }

    /**
     * 輸出EXCEL檔至指定路徑
     *
     * @param filePath
     */
    public void outputXlsReport(String outfilePath) {
        FileOutputStream fos = null;
        try {
            try {
                fos = new FileOutputStream(FilenameUtils.getBaseName(outfilePath));
                workbook.write(fos);
            } finally {
                if (fos != null)
                    fos.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 如果非直接產製為檔案<br>
     * 可在報表產製完成取得 ByteArrayOutputStream 進行後續處理
     *
     * @return
     */
    public ByteArrayOutputStream getOutputStream() {
        try {
            workbook.write(bao);
            return bao;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 設定字型
     *
     * @param workbook HSSFWorkbook
     */
    public void initFont(Workbook workbook) {
        font = workbook.createFont();
        font.setBold(false);
        font.setFontHeightInPoints((short) 12);
        font.setFontName(Global.CNS11643_SUNG_FULLNAME);
        font.setColor(Font.COLOR_NORMAL);
    }

    /**
     * 設定Cell Style
     *
     * @param workbook HSSFWorkbook
     */
    public void initCellStyle(Workbook workbook) {
        cellStyle = workbook.createCellStyle();
        cellStyle.setBorderBottom(BorderStyle.valueOf(StyleUtility.BORDER_THIN));
        cellStyle.setBorderLeft(BorderStyle.valueOf(StyleUtility.BORDER_THIN));
        cellStyle.setBorderRight(BorderStyle.valueOf(StyleUtility.BORDER_THIN));
        cellStyle.setBorderTop(BorderStyle.valueOf(StyleUtility.BORDER_THIN));
        if (font != null) {
            cellStyle.setFont(font);
        }
    }

    public String getResourcePath() {
        return resourcePath;
    }

    /**
     * 報表path
     *
     * @param resourcePath String
     */
    public void setResourcePath(String resourcePath) {
        this.resourcePath = resourcePath;
    }

    public CellStyle getCellStyle() {
        return cellStyle;
    }

    public void setCellStyle(CellStyle cellStyle) {
        this.cellStyle = cellStyle;
    }

    public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
        this.font = font;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getHeadSize() {
        return headSize;
    }

    public void setHeadSize(int headSize) {
        this.headSize = headSize;
    }

    public int getFootSize() {
        return footSize;
    }

    public void setFootSize(int footSize) {
        this.footSize = footSize;
    }

    public int getBodySize() {
        return bodySize;
    }

    public void setBodySize(int bodySize) {
        this.bodySize = bodySize;
    }

    public int getColumnCount() {
        return columnCount;
    }

    public void setColumnCount(int columnCount) {
        this.columnCount = columnCount;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public void addRowCount(int count) {
        this.currentRow += count;
    }

    public boolean getIsEject() {
        return isEject;
    }

    public void setIsEject(boolean isEject) {
        this.isEject = isEject;
    }

    public boolean getIsPrintHead() {
        return isPrintHead;
    }

    public void setIsPrintHead(boolean isPrintHead) {
        this.isPrintHead = isPrintHead;
    }

    public boolean getIsPrintFoot() {
        return isPrintFoot;
    }

    public void setIsPrintFoot(boolean isPrintFoot) {
        this.isPrintFoot = isPrintFoot;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    // /**
    // * 取得 xls 目錄中指定檔名之實際路徑
    // *
    // * @param fileName
    // * @return
    // */
    // public String getXlsTempletPath(String fileName) {
    // String resourcePath = EnvHelper.webpath;
    // if (!StringUtils.endsWithIgnoreCase(resourcePath, System.getProperty("file.separator")))
    // resourcePath = resourcePath + System.getProperty("file.separator");
    // resourcePath = resourcePath + "xls" + System.getProperty("file.separator") + fileName;
    // return resourcePath;
    // }

    public int[] getColumnType() {
        if (columnType != null)
            return columnType.clone();
        else
            return null;
    }

    public void setColumnType(int[] columnType) {
        if (columnType != null)
            this.columnType = columnType.clone();
        else
            this.columnType = null;
    }

    public Workbook getWorkbook() {
        return workbook;
    }

    // -------------------------------------------------------------------------------------
    // 新增多個row
    // -------------------------------------------------------------------------------------

    /**
     * 新增多個sheet
     *
     * @param workbook     工作表
     * @param sheetsAmount 欲新增的sheet數目
     * @param sheetsName   欲新增的sheets名稱
     */
    public void createSheets(Integer sheetsAmount, String[] sheetsName) {
        for (int i = 0; i < sheetsAmount; i++) {
            String sheetName = "";
            if (sheetsName != null & i + 1 <= sheetsName.length) {
                sheetName = sheetsName[i];
            }
            createSheet(sheetName);
        }
    }

    /**
     * 新增單一sheet
     *
     * @return sheet
     **/
    public Sheet createSheet() {
        return createSheet(null);
    }

    /**
     * 新增單一sheet
     *
     * @param sheetName 欲新增的sheet名稱
     * @return sheet
     **/
    public Sheet createSheet(String sheetName) {
        Sheet newSheet = null;
        if (StringUtils.isNotEmpty(sheetName)) {
            newSheet = workbook.createSheet(sheetName);
        } else {
            newSheet = workbook.createSheet();
        }

        return newSheet;
    }

    /**
     * 新增多個row
     *
     * @param sheet       欲新增row的sheet
     * @param rowPosition row位置
     * @param rowAmount   欲新增的row數目
     * @param cellAmount  欲新增的cell數目
     */
    public void createRows(Sheet sheet, Integer rowPosition, Integer rowAmount, Integer cellAmount) {
        for (int i = 0; i < rowAmount; i++) {
            createRowAll(sheet, rowPosition + i, null, cellAmount, null, null, null, null, null);
        }
    }

    /**
     * 新增多個row
     *
     * @param sheet       欲新增row的sheet
     * @param rowPosition row位置
     * @param rowAmount   欲新增的row數目
     * @param cellAmount  欲新增的cell數目
     * @param cellStyle   cell樣式
     */
    public void createRows(Sheet sheet, Integer rowPosition, Integer rowAmount, Integer cellAmount, CellStyle cellStyle) {
        // 開始新增row
        for (int i = 0; i < rowAmount; i++) {
            createRowAll(sheet, rowPosition + i, null, cellAmount, cellStyle, null, null, null, null);
        }
    }

    /**
     * 新增多個row
     *
     * @param sheet       欲新增row的sheet
     * @param rowPosition row位置
     * @param rowAmount   欲新增的row數目
     * @param rowHeight   欲新增的row高度
     * @param cellAmount  欲新增的cell數目
     */
    public void createRowsWithHeight(Sheet sheet, Integer rowPosition, Integer rowAmount, Integer rowHeight, Integer cellAmount) {
        for (int i = 0; i < rowAmount; i++) {
            createRowAll(sheet, rowPosition + i, rowHeight, cellAmount, null, null, null, null, null);
        }
    }

    // -------------------------------------------------------------------------------------
    // 新增單一row
    // -------------------------------------------------------------------------------------

    /**
     * 新增多個row
     *
     * @param sheet       欲新增row的sheet
     * @param rowPosition row位置
     * @param rowAmount   欲新增的row數目
     * @param rowHeight   欲新增的row高度
     * @param cellAmount  欲新增的cell數目
     * @param cellStyle   cell樣式
     */
    public void createRowsWithHeight(Sheet sheet, Integer rowPosition, Integer rowAmount, Integer rowHeight, Integer cellAmount, CellStyle cellStyle) {
        // 開始新增row
        for (int i = 0; i < rowAmount; i++) {
            createRowAll(sheet, rowPosition + i, rowHeight, cellAmount, cellStyle, null, null, null, null);
        }
    }

    /**
     * 新增多個row
     *
     * @param sheet       欲新增row的sheet
     * @param rowPosition row位置
     * @param rowAmount   欲新增的row數目
     * @param cellAmount  欲新增的cell數目
     * @param align       水平位置
     * @param valign      垂直位置
     * @param borderType  框線樣式
     * @param borderSize  框線尺寸
     */
    public void createRowsWithAlign(Sheet sheet, Integer rowPosition, Integer rowAmount, Integer cellAmount, Integer align, Integer valign, Integer borderType, Integer borderSize) {
        // 開始新增row
        for (int i = 0; i < rowAmount; i++) {
            createRowAll(sheet, rowPosition + i, null, cellAmount, null, align, valign, borderType, borderSize);
        }
    }

    /**
     * 新增多個row
     *
     * @param sheet       欲新增row的sheet
     * @param rowPosition row位置
     * @param rowAmount   欲新增的row數目
     * @param rowHeight   row高度
     * @param cellAmount  欲新增的cell數目
     * @param align       水平位置
     * @param valign      垂直位置
     * @param borderType  框線樣式
     * @param borderSize  框線尺寸
     */
    public void createRowsWithAlign(Sheet sheet, Integer rowPosition, Integer rowAmount, Integer rowHeight, Integer cellAmount, Integer align, Integer valign, Integer borderType, Integer borderSize) {
        // 開始新增row
        for (int i = 0; i < rowAmount; i++) {
            createRowAll(sheet, rowPosition + i, rowHeight, cellAmount, null, align, valign, borderType, borderSize);
        }
    }

    /**
     * 新增單一row
     *
     * @param sheet       欲新增row的sheet
     * @param rowPosition row位置
     * @return row
     */
    public Row createRow(Sheet sheet, Integer rowPosition) {
        return createRowAll(sheet, rowPosition, null, null, null, null, null, null, null);
    }

    /**
     * 新增單一row及包含的cell數目
     *
     * @param sheet       欲新增row的sheet
     * @param rowPosition row位置
     * @param cellAmount  欲新增的cell數目
     * @return row
     */
    public Row createRow(Sheet sheet, Integer rowPosition, Integer cellAmount) {
        return createRowAll(sheet, rowPosition, null, cellAmount, null, null, null, null, null);
    }

    /**
     * 新增單一row及包含的cell數目
     *
     * @param sheet       欲新增row的sheet
     * @param rowPosition row位置
     * @param cellAmount  欲新增的cell數目
     * @param cellStyle   cell樣式
     * @return row
     */
    public Row createRow(Sheet sheet, Integer rowPosition, Integer cellAmount, CellStyle cellStyle) {
        return createRowAll(sheet, rowPosition, null, cellAmount, cellStyle, null, null, null, null);
    }

    /**
     * 新增單一row及包含的cell數目
     *
     * @param sheet       欲新增row的sheet
     * @param rowPosition row位置
     * @param rowHeight   row高度
     * @param cellAmount  欲新增的cell數目
     * @return row
     */
    public Row createRowWithHeight(Sheet sheet, Integer rowPosition, Integer rowHeight, Integer cellAmount) {
        return createRowAll(sheet, rowPosition, rowHeight, cellAmount, null, null, null, null, null);
    }

    /**
     * 新增單一row及包含的cell數目
     *
     * @param sheet       欲新增row的sheet
     * @param rowPosition row位置
     * @param rowHeight   row高度
     * @param cellAmount  欲新增的cell數目
     * @param cellStyle   cell樣式
     * @return row
     */
    public Row createRowWithHeight(Sheet sheet, Integer rowPosition, Integer rowHeight, Integer cellAmount, CellStyle cellStyle) {
        return createRowAll(sheet, rowPosition, rowHeight, cellAmount, cellStyle, null, null, null, null);
    }

    // /**
    // * 新增cell欄位 with double value
    // *
    // * @param row 指定的row
    // * @param cellPosition cell位置
    // * @param cellValue cell欄位值
    // * @param cellStyle cell樣式
    // * @return cell
    // */
    // public Cell createCell(Row row, Integer cellPosition, Double cellValue, CellStyle cellStyle) {
    // return createCell(row, cellPosition, CELL_TYPE_NUMERIC, cellValue, cellStyle);
    // }
    //
    // /**
    // * 新增cell欄位 with String value
    // *
    // * @param row 指定的row
    // * @param cellPosition cell位置
    // * @param cellValue cell欄位值
    // * @param cellStyle cell樣式
    // * @return cell
    // */
    // public Cell createCell(Row row, Integer cellPosition, String cellValue, CellStyle cellStyle) {
    // return createCell(row, cellPosition, CELL_TYPE_STRING, cellValue, cellStyle);
    // }
    //
    // /**
    // * 新增cell欄位 with Formula value
    // *
    // * @param row 指定的row
    // * @param cellPosition cell位置
    // * @param cellValue cell欄位值
    // * @param cellStyle cell樣式
    // * @return cell
    // */
    // public Cell createCellFormula(Row row, Integer cellPosition, String formula, CellStyle cellStyle) {
    // return createCell(row, cellPosition, CELL_TYPE_FORMULA, formula, cellStyle);
    // }
    //
    // /**
    // * 新增cell欄位 with Error value
    // *
    // * @param row 指定的row
    // * @param cellPosition cell位置
    // * @param cellValue cell欄位值
    // * @param cellStyle cell樣式
    // * @return cell
    // */
    // public Cell createCell(Row row, Integer cellPosition, Byte cellValue, CellStyle cellStyle) {
    // return createCell(row, cellPosition, CELL_TYPE_ERROR, cellValue, cellStyle);
    // }
    //
    // /**
    // * 新增cell欄位 with boolean value
    // *
    // * @param row 指定的row
    // * @param cellPosition cell位置
    // * @param cellValue cell欄位值
    // * @param cellStyle cell樣式
    // * @return cell
    // */
    // public Cell createCell(Row row, Integer cellPosition, boolean cellValue, CellStyle cellStyle) {
    // return createCell(row, cellPosition, CELL_TYPE_BOOLEAN, cellValue, cellStyle);
    // }
    //
    // /**
    // * 新增cell欄位 with Date value
    // *
    // * @param row 指定的row
    // * @param cellPosition cell位置
    // * @param cellValue cell欄位值
    // * @param cellStyle cell樣式
    // * @return cell
    // */
    // public Cell createCell(Row row, Integer cellPosition, Date cellValue, CellStyle cellStyle) {
    // return createCell(row, cellPosition, CELL_TYPE_BLANK, cellValue, cellStyle);
    // }
    //
    // /**
    // * 新增cell欄位 with Calendar value
    // *
    // * @param row 指定的row
    // * @param cellPosition cell位置
    // * @param cellValue cell欄位值
    // * @param cellStyle cell樣式
    // * @return cell
    // */
    // public Cell createCell(Row row, Integer cellPosition, Calendar cellValue, CellStyle cellStyle) {
    // return createCell(row, cellPosition, CELL_TYPE_BLANK, cellValue, cellStyle);
    // }

    // -------------------------------------------------------------------------------------
    // 新增cell欄位 with value
    // -------------------------------------------------------------------------------------

    /**
     * 新增單一row及包含的cell數目
     *
     * @param sheet       欲新增row的sheet
     * @param rowPosition row位置
     * @param cellAmount  欲新增的cell數目
     * @param align       水平位置
     * @param valign      垂直位置
     * @param borderType  框線樣式
     * @param borderSize  框線尺寸
     * @return row
     */
    public Row createRowWithAlign(Sheet sheet, Integer rowPosition, Integer cellAmount, Integer align, Integer valign, Integer borderType, Integer borderSize) {
        return createRowAll(sheet, rowPosition, null, cellAmount, null, align, valign, borderType, borderSize);
    }

    /**
     * 新增單一row及包含的cell數目
     *
     * @param sheet       欲新增row的sheet
     * @param rowPosition row位置
     * @param rowHeight   row高度
     * @param cellAmount  欲新增的cell數目
     * @param align       水平位置
     * @param valign      垂直位置
     * @param borderType  框線樣式
     * @param borderSize  框線尺寸
     * @return row
     */
    public Row createRowWithAlign(Sheet sheet, Integer rowPosition, Integer rowHeight, Integer cellAmount, Integer align, Integer valign, Integer borderType, Integer borderSize) {
        return createRowAll(sheet, rowPosition, rowHeight, cellAmount, null, align, valign, borderType, borderSize);
    }

    /**
     * 新增單一row及包含的cell數目
     *
     * @param sheet       欲新增row的sheet
     * @param rowPosition row位置
     * @param rowHeight   row高度
     * @param cellAmount  欲新增的cell數目
     * @param cellStyle   cell樣式
     * @param align       水平位置
     * @param valign      垂直位置
     * @param borderType  框線樣式
     * @param borderSize  框線尺寸
     * @return row
     */
    private Row createRowAll(Sheet sheet, Integer rowPosition, Integer rowHeight, Integer cellAmount, CellStyle cellStyle, Integer align, Integer valign, Integer borderType, Integer borderSize) {
        try {
            Row row = null;
            Integer rowNum = null;

            // 新增row
            if (rowPosition != null) {
                rowNum = rowPosition;
            } else {
                // 取得目前總行數及最後一行行數
                int pnr = sheet.getPhysicalNumberOfRows();
                int i = sheet.getLastRowNum();
                rowNum = i + ((pnr == 0) ? 0 : 1);
            }
            if (sheet.getRow(rowNum) == null) {
                row = sheet.createRow(rowNum);
            } else {
                row = sheet.getRow(rowNum);
            }

            // 設定row高
            if (rowHeight != null) {
                row.setHeight(rowHeight.shortValue());
            }

            // 新增cell
            if (cellAmount != null && cellAmount != 0) {
                Cell targetCell = null;
                for (int k = 0; k < cellAmount; k++) {
                    targetCell = row.createCell(k);

                    // 設定cell style
                    if (cellStyle == null && align == null && valign == null && borderType == null && borderSize == null) {
                        targetCell.setCellStyle(this.cellStyle);
                    } else if (cellStyle != null && (align == null && valign == null && borderType == null && borderSize == null)) {
                        targetCell.setCellStyle(cellStyle);
                    } else if (cellStyle == null && !(align == null && valign == null && borderType == null && borderSize == null)) {
                        targetCell.setCellStyle(createStyle(null, align, valign, borderType, borderSize));
                    }
                }
            }

            return row;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 新增cell欄位 with double value<br>
     * 當需要同時設定多項欄位樣式時<br>
     * 可自行將需要的參數填入cellStyle後<br>
     * 在設定cellValue時同時傳入<br>
     * 若只需要簡單設定水平位置、框線<br>
     * 可呼叫其他方法(名稱相同參數不同)<br>
     *
     * @param row          指定的row
     * @param cellPosition cell位置
     * @param cellValue    cell欄位值
     * @param cellStyle    cell樣式
     */
    public Cell createCell(Row row, Integer cellPosition, Double cellValue, CellStyle cellStyle) {
        return createCell(row, cellPosition, CELL_TYPE_NUMERIC, cellValue, cellStyle, null, null, null, null);
    }

    /**
     * 新增cell欄位 with double value
     *
     * @param row          指定的row
     * @param cellPosition cell位置
     * @param cellValue    cell欄位值
     * @param cellStyle    cell樣式
     * @param align        水平位置
     */
    public Cell createCell(Row row, Integer cellPosition, Double cellValue, Integer align) {
        return createCell(row, cellPosition, CELL_TYPE_NUMERIC, cellValue, null, align, null, null, null);
    }

    /**
     * 新增cell欄位 with double value
     *
     * @param row          指定的row
     * @param cellPosition cell位置
     * @param cellValue    cell欄位值
     * @param cellStyle    cell樣式
     * @param align        水平位置
     * @param borderType   框線樣式
     */
    public Cell createCell(Row row, Integer cellPosition, Double cellValue, Integer align, Integer borderType) {
        return createCell(row, cellPosition, CELL_TYPE_NUMERIC, cellValue, null, align, null, borderType, null);
    }

    /**
     * 新增cell欄位 with double value
     *
     * @param row          指定的row
     * @param cellPosition cell位置
     * @param cellValue    cell欄位值
     * @param cellStyle    cell樣式
     * @param align        水平位置
     * @param borderType   框線樣式
     * @param borderSize   框線尺寸
     */
    public Cell createCell(Row row, Integer cellPosition, Double cellValue, Integer align, Integer borderType, Integer borderSize) {
        return createCell(row, cellPosition, CELL_TYPE_NUMERIC, cellValue, null, align, null, borderType, null);
    }

    /**
     * 新增cell欄位 with double value
     *
     * @param row          指定的row
     * @param cellPosition cell位置
     * @param cellValue    cell欄位值
     * @param cellStyle    cell樣式
     * @param align        水平位置
     * @param valign       垂直位置
     * @param borderType   框線樣式
     * @param borderSize   框線尺寸
     */
    public Cell createCell(Row row, Integer cellPosition, Double cellValue, Integer align, Integer valign, Integer borderType, Integer borderSize) {
        return createCell(row, cellPosition, CELL_TYPE_NUMERIC, cellValue, null, align, valign, borderType, borderSize);
    }

    /**
     * 新增cell欄位 with String value<br>
     * 當需要同時設定多項欄位樣式時<br>
     * 可自行將需要的參數填入cellStyle後<br>
     * 在設定cellValue時同時傳入<br>
     * 若只需要簡單設定水平位置、框線<br>
     * 可呼叫其他方法(名稱相同參數不同)<br>
     *
     * @param row          指定的row
     * @param cellPosition cell位置
     * @param cellValue    cell欄位值
     * @param cellStyle    cell樣式
     */
    public Cell createCell(Row row, Integer cellPosition, String cellValue, CellStyle cellStyle) {
        return createCell(row, cellPosition, CELL_TYPE_STRING, getDefaultStr(cellValue), cellStyle, null, null, null, null);
    }

    /**
     * 新增cell欄位 with String value
     *
     * @param row          指定的row
     * @param cellPosition cell位置
     * @param cellValue    cell欄位值
     * @param cellStyle    cell樣式
     * @param align        水平位置
     */
    public Cell createCell(Row row, Integer cellPosition, String cellValue, Integer align) {
        return createCell(row, cellPosition, CELL_TYPE_STRING, cellValue, null, align, null, null, null);
    }

    /**
     * 新增cell欄位 with String value
     *
     * @param row          指定的row
     * @param cellPosition cell位置
     * @param cellValue    cell欄位值
     * @param cellStyle    cell樣式
     * @param align        水平位置
     * @param borderType   框線樣式
     */
    public Cell createCell(Row row, Integer cellPosition, String cellValue, Integer align, Integer borderType) {
        return createCell(row, cellPosition, CELL_TYPE_STRING, cellValue, null, align, null, borderType, null);
    }

    /**
     * 新增cell欄位 with String value
     *
     * @param row          指定的row
     * @param cellPosition cell位置
     * @param cellValue    cell欄位值
     * @param cellStyle    cell樣式
     * @param align        水平位置
     * @param borderType   框線樣式
     * @param borderSize   框線尺寸
     */
    public Cell createCell(Row row, Integer cellPosition, String cellValue, Integer align, Integer borderType, Integer borderSize) {
        return createCell(row, cellPosition, CELL_TYPE_STRING, cellValue, null, align, null, borderType, borderSize);
    }

    /**
     * 新增cell欄位 with String value
     *
     * @param row          指定的row
     * @param cellPosition cell位置
     * @param cellValue    cell欄位值
     * @param cellStyle    cell樣式
     * @param align        水平位置
     * @param valign       垂直位置
     * @param borderType   框線樣式
     * @param borderSize   框線尺寸
     */
    public Cell createCell(Row row, Integer cellPosition, String cellValue, Integer align, Integer valign, Integer borderType, Integer borderSize) {
        return createCell(row, cellPosition, CELL_TYPE_STRING, cellValue, null, align, valign, borderType, borderSize);
    }

    /**
     * 新增cell欄位 with Formula value<br>
     * 當需要同時設定多項欄位樣式時<br>
     * 可自行將需要的參數填入cellStyle後<br>
     * 在設定cellValue時同時傳入<br>
     * 若只需要簡單設定水平位置、框線<br>
     * 可呼叫其他方法(名稱相同參數不同)<br>
     *
     * @param row          指定的row
     * @param cellPosition cell位置
     * @param formula      cell欄位值
     * @param cellStyle    cell樣式
     */
    public Cell createCellFormula(Row row, Integer cellPosition, String formula, CellStyle cellStyle) {
        return createCell(row, cellPosition, CELL_TYPE_FORMULA, formula, cellStyle, null, null, null, null);
    }

    /**
     * 新增cell欄位 with Formula value
     *
     * @param row          指定的row
     * @param cellPosition cell位置
     * @param formula      cell欄位值
     * @param cellStyle    cell樣式
     * @param align        水平位置
     */
    public Cell createCellFormula(Row row, Integer cellPosition, String formula, Integer align) {
        return createCell(row, cellPosition, CELL_TYPE_FORMULA, formula, null, align, null, null, null);
    }

    /**
     * 新增cell欄位 with Formula value
     *
     * @param row          指定的row
     * @param cellPosition cell位置
     * @param formula      cell欄位值
     * @param cellStyle    cell樣式
     * @param align        水平位置
     * @param borderType   框線樣式
     */
    public Cell createCellFormula(Row row, Integer cellPosition, String formula, Integer align, Integer borderType) {
        return createCell(row, cellPosition, CELL_TYPE_FORMULA, formula, null, align, null, borderType, null);
    }

    /**
     * 新增cell欄位 with Formula value
     *
     * @param row          指定的row
     * @param cellPosition cell位置
     * @param formula      cell欄位值
     * @param cellStyle    cell樣式
     * @param align        水平位置
     * @param borderType   框線樣式
     * @param borderSize   框線尺寸
     */
    public Cell createCellFormula(Row row, Integer cellPosition, String formula, Integer align, Integer borderType, Integer borderSize) {
        return createCell(row, cellPosition, CELL_TYPE_FORMULA, formula, null, align, null, borderType, null);
    }

    /**
     * 新增cell欄位 with Formula value
     *
     * @param row          指定的row
     * @param cellPosition cell位置
     * @param formula      cell欄位值
     * @param cellStyle    cell樣式
     * @param align        水平位置
     * @param valign       垂直位置
     * @param borderType   框線樣式
     * @param borderSize   框線尺寸
     */
    public Cell createCellFormula(Row row, Integer cellPosition, String formula, Integer align, Integer valign, Integer borderType, Integer borderSize) {
        return createCell(row, cellPosition, CELL_TYPE_FORMULA, formula, null, align, valign, borderType, borderSize);
    }

    /**
     * 新增cell欄位 with Error value<br>
     * 當需要同時設定多項欄位樣式時<br>
     * 可自行將需要的參數填入cellStyle後<br>
     * 在設定cellValue時同時傳入<br>
     * 若只需要簡單設定水平位置、框線<br>
     * 可呼叫其他方法(名稱相同參數不同)<br>
     *
     * @param row          指定的row
     * @param cellPosition cell位置
     * @param cellValue    cell欄位值
     * @param cellStyle    cell樣式
     */
    public Cell createCell(Row row, Integer cellPosition, Byte cellValue, CellStyle cellStyle) {
        return createCell(row, cellPosition, CELL_TYPE_ERROR, cellValue, cellStyle, null, null, null, null);
    }

    /**
     * 新增cell欄位 with Error value
     *
     * @param row          指定的row
     * @param cellPosition cell位置
     * @param cellValue    cell欄位值
     * @param cellStyle    cell樣式
     * @param align        水平位置
     */
    public Cell createCell(Row row, Integer cellPosition, Byte cellValue, Integer align) {
        return createCell(row, cellPosition, CELL_TYPE_ERROR, cellValue, null, align, null, null, null);
    }

    /**
     * 新增cell欄位 with Error value
     *
     * @param row          指定的row
     * @param cellPosition cell位置
     * @param cellValue    cell欄位值
     * @param cellStyle    cell樣式
     * @param align        水平位置
     * @param borderType   框線樣式
     */
    public Cell createCell(Row row, Integer cellPosition, Byte cellValue, Integer align, Integer borderType) {
        return createCell(row, cellPosition, CELL_TYPE_ERROR, cellValue, null, align, null, borderType, null);
    }

    /**
     * 新增cell欄位 with Error value
     *
     * @param row          指定的row
     * @param cellPosition cell位置
     * @param cellValue    cell欄位值
     * @param cellStyle    cell樣式
     * @param align        水平位置
     * @param borderType   框線樣式
     * @param borderSize   框線尺寸
     */
    public Cell createCell(Row row, Integer cellPosition, Byte cellValue, Integer align, Integer borderType, Integer borderSize) {
        return createCell(row, cellPosition, CELL_TYPE_ERROR, cellValue, null, align, null, borderType, null);
    }

    /**
     * 新增cell欄位 with Error value
     *
     * @param row          指定的row
     * @param cellPosition cell位置
     * @param cellValue    cell欄位值
     * @param cellStyle    cell樣式
     * @param align        水平位置
     * @param valign       垂直位置
     * @param borderType   框線樣式
     * @param borderSize   框線尺寸
     */
    public Cell createCell(Row row, Integer cellPosition, Byte cellValue, Integer align, Integer valign, Integer borderType, Integer borderSize) {
        return createCell(row, cellPosition, CELL_TYPE_ERROR, cellValue, null, align, valign, borderType, borderSize);
    }

    /**
     * 新增cell欄位 with boolean value<br>
     * 當需要同時設定多項欄位樣式時<br>
     * 可自行將需要的參數填入cellStyle後<br>
     * 在設定cellValue時同時傳入<br>
     * 若只需要簡單設定水平位置、框線<br>
     * 可呼叫其他方法(名稱相同參數不同)<br>
     *
     * @param row          指定的row
     * @param cellPosition cell位置
     * @param cellValue    cell欄位值
     * @param cellStyle    cell樣式
     */
    public Cell createCell(Row row, Integer cellPosition, boolean cellValue, CellStyle cellStyle) {
        return createCell(row, cellPosition, CELL_TYPE_BOOLEAN, cellValue, cellStyle, null, null, null, null);
    }

    /**
     * 新增cell欄位 with boolean value
     *
     * @param row          指定的row
     * @param cellPosition cell位置
     * @param cellValue    cell欄位值
     * @param cellStyle    cell樣式
     * @param align        水平位置
     */
    public Cell createCell(Row row, Integer cellPosition, boolean cellValue, Integer align) {
        return createCell(row, cellPosition, CELL_TYPE_BOOLEAN, cellValue, null, align, null, null, null);
    }

    /**
     * 新增cell欄位 with boolean value
     *
     * @param row          指定的row
     * @param cellPosition cell位置
     * @param cellValue    cell欄位值
     * @param cellStyle    cell樣式
     * @param align        水平位置
     * @param borderType   框線樣式
     */
    public Cell createCell(Row row, Integer cellPosition, boolean cellValue, Integer align, Integer borderType) {
        return createCell(row, cellPosition, CELL_TYPE_BOOLEAN, cellValue, null, align, null, borderType, null);
    }

    /**
     * 新增cell欄位 with boolean value
     *
     * @param row          指定的row
     * @param cellPosition cell位置
     * @param cellValue    cell欄位值
     * @param cellStyle    cell樣式
     * @param align        水平位置
     * @param borderType   框線樣式
     * @param borderSize   框線尺寸
     */
    public Cell createCell(Row row, Integer cellPosition, boolean cellValue, Integer align, Integer borderType, Integer borderSize) {
        return createCell(row, cellPosition, CELL_TYPE_BOOLEAN, cellValue, null, align, null, borderType, null);
    }

    /**
     * 新增cell欄位 with boolean value
     *
     * @param row          指定的row
     * @param cellPosition cell位置
     * @param cellValue    cell欄位值
     * @param cellStyle    cell樣式
     * @param align        水平位置
     * @param valign       垂直位置
     * @param borderType   框線樣式
     * @param borderSize   框線尺寸
     */
    public Cell createCell(Row row, Integer cellPosition, boolean cellValue, Integer align, Integer valign, Integer borderType, Integer borderSize) {
        return createCell(row, cellPosition, CELL_TYPE_BOOLEAN, cellValue, null, align, valign, borderType, borderSize);
    }

    /**
     * 新增cell欄位 with Calendar value<br>
     * 當需要同時設定多項欄位樣式時<br>
     * 可自行將需要的參數填入cellStyle後<br>
     * 在設定cellValue時同時傳入<br>
     * 若只需要簡單設定水平位置、框線<br>
     * 可呼叫其他方法(名稱相同參數不同)<br>
     *
     * @param row          指定的row
     * @param cellPosition cell位置
     * @param cellValue    cell欄位值
     * @param cellStyle    cell樣式
     */
    public Cell createCell(Row row, Integer cellPosition, Calendar cellValue, CellStyle cellStyle) {
        return createCell(row, cellPosition, CELL_TYPE_BLANK, cellValue, cellStyle, null, null, null, null);
    }

    /**
     * 新增cell欄位 with Calendar value
     *
     * @param row          指定的row
     * @param cellPosition cell位置
     * @param cellValue    cell欄位值
     * @param cellStyle    cell樣式
     * @param align        水平位置
     */
    public Cell createCell(Row row, Integer cellPosition, Calendar cellValue, Integer align) {
        return createCell(row, cellPosition, CELL_TYPE_BLANK, cellValue, null, align, null, null, null);
    }

    /**
     * 新增cell欄位 with Calendar value
     *
     * @param row          指定的row
     * @param cellPosition cell位置
     * @param cellValue    cell欄位值
     * @param cellStyle    cell樣式
     * @param align        水平位置
     * @param borderType   框線樣式
     */
    public Cell createCell(Row row, Integer cellPosition, Calendar cellValue, Integer align, Integer borderType) {
        return createCell(row, cellPosition, CELL_TYPE_BLANK, cellValue, null, align, null, borderType, null);
    }

    /**
     * 新增cell欄位 with Calendar value
     *
     * @param row          指定的row
     * @param cellPosition cell位置
     * @param cellValue    cell欄位值
     * @param cellStyle    cell樣式
     * @param align        水平位置
     * @param borderType   框線樣式
     * @param borderSize   框線尺寸
     */
    public Cell createCell(Row row, Integer cellPosition, Calendar cellValue, Integer align, Integer borderType, Integer borderSize) {
        return createCell(row, cellPosition, CELL_TYPE_BLANK, cellValue, null, align, null, borderType, null);
    }

    /**
     * 新增cell欄位 with Calendar value
     *
     * @param row          指定的row
     * @param cellPosition cell位置
     * @param cellValue    cell欄位值
     * @param cellStyle    cell樣式
     * @param align        水平位置
     * @param valign       垂直位置
     * @param borderType   框線樣式
     * @param borderSize   框線尺寸
     */
    public Cell createCell(Row row, Integer cellPosition, Calendar cellValue, Integer align, Integer valign, Integer borderType, Integer borderSize) {
        return createCell(row, cellPosition, CELL_TYPE_BLANK, cellValue, null, align, valign, borderType, borderSize);
    }

    /**
     * 新增cell欄位 with Date value<br>
     * 當需要同時設定多項欄位樣式時<br>
     * 可自行將需要的參數填入cellStyle後<br>
     * 在設定cellValue時同時傳入<br>
     * 若只需要簡單設定水平位置、框線<br>
     * 可呼叫其他方法(名稱相同參數不同)<br>
     *
     * @param row          指定的row
     * @param cellPosition cell位置
     * @param cellValue    cell欄位值
     * @param cellStyle    cell樣式
     */
    public Cell createCell(Row row, Integer cellPosition, Date cellValue, CellStyle cellStyle) {
        return createCell(row, cellPosition, CELL_TYPE_BLANK, cellValue, cellStyle, null, null, null, null);
    }

    /**
     * 新增cell欄位 with Date value
     *
     * @param row          指定的row
     * @param cellPosition cell位置
     * @param cellValue    cell欄位值
     * @param cellStyle    cell樣式
     * @param align        水平位置
     */
    public Cell createCell(Row row, Integer cellPosition, Date cellValue, Integer align) {
        return createCell(row, cellPosition, CELL_TYPE_BLANK, cellValue, null, align, null, null, null);
    }

    /**
     * 新增cell欄位 with Date value
     *
     * @param row          指定的row
     * @param cellPosition cell位置
     * @param cellValue    cell欄位值
     * @param cellStyle    cell樣式
     * @param align        水平位置
     * @param borderType   框線樣式
     */
    public Cell createCell(Row row, Integer cellPosition, Date cellValue, Integer align, Integer borderType) {
        return createCell(row, cellPosition, CELL_TYPE_BLANK, cellValue, null, align, null, borderType, null);
    }

    /**
     * 新增cell欄位 with Date value
     *
     * @param row          指定的row
     * @param cellPosition cell位置
     * @param cellValue    cell欄位值
     * @param cellStyle    cell樣式
     * @param align        水平位置
     * @param borderType   框線樣式
     * @param borderSize   框線尺寸
     */
    public Cell createCell(Row row, Integer cellPosition, Date cellValue, Integer align, Integer borderType, Integer borderSize) {
        return createCell(row, cellPosition, CELL_TYPE_BLANK, cellValue, null, align, null, borderType, null);
    }

    /**
     * 新增cell欄位 with Date value
     *
     * @param row          指定的row
     * @param cellPosition cell位置
     * @param cellValue    cell欄位值
     * @param cellStyle    cell樣式
     * @param align        水平位置
     * @param valign       垂直位置
     * @param borderType   框線樣式
     * @param borderSize   框線尺寸
     */
    public Cell createCell(Row row, Integer cellPosition, Date cellValue, Integer align, Integer valign, Integer borderType, Integer borderSize) {
        return createCell(row, cellPosition, CELL_TYPE_BLANK, cellValue, null, align, valign, borderType, borderSize);
    }

    /**
     * 新增多個cell欄位 with Values<br>
     * 當需要同時設定多項欄位樣式時<br>
     * 可自行將需要的參數填入cellStyle後<br>
     * 在設定cellValue時同時傳入<br>
     * 若只需要簡單設定水平位置、框線<br>
     * 可呼叫其他方法(名稱相同參數不同)<br>
     *
     * @param row          指定的row
     * @param cellPosition cell位置
     * @param cellValue    cell欄位值
     * @param cellStyle    cell樣式
     */
    public void createCells(Row row, Integer startCellNum, Integer endCellNum, Object[] cellValues, CellStyle cellStyle) {
        int valIdx = 0;// cellValues index
        Object cellValue = null;
        for (int i = startCellNum; i <= endCellNum; i++) {
            if (row.getCell(i) == null) {
                row.createCell(i);
            }

            if (valIdx + 1 <= cellValues.length) {
                cellValue = cellValues[valIdx];
            } else {
                cellValue = "";
            }

            createCell(row, i, null, cellValue, cellStyle, null, null, null, null);
            valIdx++;
        }
    }

    /**
     * 新增多個cell欄位 with Values
     *
     * @param row          指定的row
     * @param cellPosition cell位置
     * @param cellValue    cell欄位值
     * @param cellStyle    cell樣式
     * @param align        水平位置
     */
    public void createCells(Row row, Integer startCellNum, Integer endCellNum, Object[] cellValues, Integer align) {
        int valIdx = 0;// cellValues index
        Object cellValue = null;
        for (int i = startCellNum; i <= endCellNum; i++) {
            if (row.getCell(i) == null) {
                row.createCell(i);
            }

            if (valIdx + 1 <= cellValues.length) {
                cellValue = cellValues[valIdx];
            } else {
                cellValue = "";
            }

            createCell(row, i, null, cellValue, null, align, null, null, null);
            valIdx++;
        }
    }

    /**
     * 新增多個cell欄位 with Values
     *
     * @param row          指定的row
     * @param cellPosition cell位置
     * @param cellValue    cell欄位值
     * @param cellStyle    cell樣式
     * @param align        水平位置
     * @param borderType   框線樣式
     */
    public void createCells(Row row, Integer startCellNum, Integer endCellNum, Object[] cellValues, Integer align, Integer borderType) {
        int valIdx = 0;// cellValues index
        Object cellValue = null;
        for (int i = startCellNum; i <= endCellNum; i++) {
            if (row.getCell(i) == null) {
                row.createCell(i);
            }

            if (valIdx + 1 <= cellValues.length) {
                cellValue = cellValues[valIdx];
            } else {
                cellValue = "";
            }

            createCell(row, i, null, cellValue, null, align, null, borderType, null);
            valIdx++;
        }
    }

    // -------------------------------------------------------------------------------------
    // 填入cell欄位 with value
    // -------------------------------------------------------------------------------------

    /**
     * 新增多個cell欄位 with Values
     *
     * @param row          指定的row
     * @param cellPosition cell位置
     * @param cellValue    cell欄位值
     * @param cellStyle    cell樣式
     * @param align        水平位置
     * @param borderType   框線樣式
     * @param borderSize   框線尺寸
     */
    public void createCells(Row row, Integer startCellNum, Integer endCellNum, Object[] cellValues, Integer align, Integer borderType, Integer borderSize) {
        int valIdx = 0;// cellValues index
        Object cellValue = null;
        for (int i = startCellNum; i <= endCellNum; i++) {
            if (row.getCell(i) == null) {
                row.createCell(i);
            }

            if (valIdx + 1 <= cellValues.length) {
                cellValue = cellValues[valIdx];
            } else {
                cellValue = "";
            }

            createCell(row, i, null, cellValue, null, align, null, borderType, borderSize);
            valIdx++;
        }
    }

    /**
     * 新增多個cell欄位 with Values
     *
     * @param row          指定的row
     * @param cellPosition cell位置
     * @param cellValue    cell欄位值
     * @param cellStyle    cell樣式
     * @param align        水平位置
     * @param valign       垂直位置
     * @param borderType   框線樣式
     * @param borderSize   框線尺寸
     */
    public void createCells(Row row, Integer startCellNum, Integer endCellNum, Object[] cellValues, Integer align, Integer valign, Integer borderType, Integer borderSize) {
        int valIdx = 0;// cellValues index
        Object cellValue = null;
        for (int i = startCellNum; i <= endCellNum; i++) {
            if (row.getCell(i) == null) {
                row.createCell(i);
            }

            if (valIdx + 1 <= cellValues.length) {
                cellValue = cellValues[valIdx];
            } else {
                cellValue = "";
            }

            createCell(row, i, null, cellValue, null, align, valign, borderType, borderSize);
            valIdx++;
        }
    }

    /**
     * 新增cell欄位
     *
     * @param row          指定的row
     * @param cellPosition cell位置
     * @param cellType     cell類別
     * @param cellValue    cell欄位值
     * @param align        水平位置
     * @param valign       垂直位置
     * @param borderType   框線樣式
     * @param borderSize   框線尺寸
     * @return cell
     */
    private Cell createCell(Row row, Integer cellPosition, Integer cellType, Object cellValue, CellStyle cellStyle, Integer align, Integer valign, Integer borderType, Integer borderSize) {
        try {
            // 新增cell
            Cell targetCell = null;
            Integer cellNum = null;
            if (cellPosition != null) {
                cellNum = cellPosition;
            } else {
                cellNum = Integer.valueOf(row.getLastCellNum()) + 1;
            }

            if (row.getCell(cellNum) == null) {
                targetCell = row.createCell(cellNum);
            }
            // else {
            // targetCell = row.getCell(cellNum);
            // }
            // 設定 cell 類型
            // if (cellType != null) {
            // targetCell.setCellType(cellType);
            // }
            // // 設定 cell 樣式
            // if (cellStyle != null) {
            // targetCell.setCellStyle(cellStyle);
            // }

            // 填入cell值
            setCellValueAll(row, cellNum, cellType, cellValue, cellStyle, align, valign, borderType, borderSize);
            targetCell = row.getCell(cellNum);
            return targetCell;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 填入cell欄位 with double value<br>
     * 當需要同時設定多項欄位樣式時<br>
     * 可自行將需要的參數填入cellStyle後<br>
     * 在設定cellValue時同時傳入<br>
     * 若只需要簡單設定水平位置、框線<br>
     * 可呼叫其他方法(名稱相同參數不同)<br>
     *
     * @param row          指定的row
     * @param cellPosition cell位置
     * @param cellValue    cell欄位值
     * @param cellStyle    cell樣式
     */
    public void setCellValue(Row row, Integer cellPosition, Double cellValue, CellStyle cellStyle) {
        setCellValueAll(row, cellPosition, CELL_TYPE_NUMERIC, cellValue, cellStyle, null, null, null, null);
    }

    /**
     * 填入cell欄位 with double value
     *
     * @param row          指定的row
     * @param cellPosition cell位置
     * @param cellValue    cell欄位值
     * @param cellStyle    cell樣式
     * @param align        水平位置
     */
    public void setCellValue(Row row, Integer cellPosition, Double cellValue, Integer align) {
        setCellValueAll(row, cellPosition, CELL_TYPE_NUMERIC, cellValue, null, align, null, null, null);
    }

    /**
     * 填入cell欄位 with double value
     *
     * @param row          指定的row
     * @param cellPosition cell位置
     * @param cellValue    cell欄位值
     * @param cellStyle    cell樣式
     * @param align        水平位置
     * @param borderType   框線樣式
     */
    public void setCellValue(Row row, Integer cellPosition, Double cellValue, Integer align, Integer borderType) {
        setCellValueAll(row, cellPosition, CELL_TYPE_NUMERIC, cellValue, null, align, null, borderType, null);
    }

    /**
     * 填入cell欄位 with double value
     *
     * @param row          指定的row
     * @param cellPosition cell位置
     * @param cellValue    cell欄位值
     * @param cellStyle    cell樣式
     * @param align        水平位置
     * @param borderType   框線樣式
     * @param borderSize   框線尺寸
     */
    public void setCellValue(Row row, Integer cellPosition, Double cellValue, Integer align, Integer borderType, Integer borderSize) {
        setCellValueAll(row, cellPosition, CELL_TYPE_NUMERIC, cellValue, null, align, null, borderType, null);
    }

    /**
     * 填入cell欄位 with double value
     *
     * @param row          指定的row
     * @param cellPosition cell位置
     * @param cellValue    cell欄位值
     * @param cellStyle    cell樣式
     * @param align        水平位置
     * @param valign       垂直位置
     * @param borderType   框線樣式
     * @param borderSize   框線尺寸
     */
    public void setCellValue(Row row, Integer cellPosition, Double cellValue, Integer align, Integer valign, Integer borderType, Integer borderSize) {
        setCellValueAll(row, cellPosition, CELL_TYPE_NUMERIC, cellValue, null, align, valign, borderType, borderSize);
    }

    /**
     * 填入cell欄位 with String value<br>
     * 當需要同時設定多項欄位樣式時<br>
     * 可自行將需要的參數填入cellStyle後<br>
     * 在設定cellValue時同時傳入<br>
     * 若只需要簡單設定水平位置、框線<br>
     * 可呼叫其他方法(名稱相同參數不同)<br>
     *
     * @param row          指定的row
     * @param cellPosition cell位置
     * @param cellValue    cell欄位值
     * @param cellStyle    cell樣式
     */
    public void setCellValue(Row row, Integer cellPosition, String cellValue, CellStyle cellStyle) {
        setCellValueAll(row, cellPosition, CELL_TYPE_STRING, getDefaultStr(cellValue), cellStyle, null, null, null, null);
    }

    /**
     * 填入cell欄位 with String value
     *
     * @param row          指定的row
     * @param cellPosition cell位置
     * @param cellValue    cell欄位值
     * @param cellStyle    cell樣式
     * @param align        水平位置
     */
    public void setCellValue(Row row, Integer cellPosition, String cellValue, Integer align) {
        setCellValueAll(row, cellPosition, CELL_TYPE_STRING, cellValue, null, align, null, null, null);
    }

    /**
     * 填入cell欄位 with String value
     *
     * @param row          指定的row
     * @param cellPosition cell位置
     * @param cellValue    cell欄位值
     * @param cellStyle    cell樣式
     * @param align        水平位置
     * @param borderType   框線樣式
     */
    public void setCellValue(Row row, Integer cellPosition, String cellValue, Integer align, Integer borderType) {
        setCellValueAll(row, cellPosition, CELL_TYPE_STRING, cellValue, null, align, null, borderType, null);
    }

    /**
     * 填入cell欄位 with String value
     *
     * @param row          指定的row
     * @param cellPosition cell位置
     * @param cellValue    cell欄位值
     * @param cellStyle    cell樣式
     * @param align        水平位置
     * @param borderType   框線樣式
     * @param borderSize   框線尺寸
     */
    public void setCellValue(Row row, Integer cellPosition, String cellValue, Integer align, Integer borderType, Integer borderSize) {
        setCellValueAll(row, cellPosition, CELL_TYPE_STRING, cellValue, null, align, null, borderType, null);
    }

    /**
     * 填入cell欄位 with String value
     *
     * @param row          指定的row
     * @param cellPosition cell位置
     * @param cellValue    cell欄位值
     * @param cellStyle    cell樣式
     * @param align        水平位置
     * @param valign       垂直位置
     * @param borderType   框線樣式
     * @param borderSize   框線尺寸
     */
    public void setCellValue(Row row, Integer cellPosition, String cellValue, Integer align, Integer valign, Integer borderType, Integer borderSize) {
        setCellValueAll(row, cellPosition, CELL_TYPE_STRING, cellValue, null, align, valign, borderType, borderSize);
    }

    /**
     * 填入cell欄位 with Formula value<br>
     * 當需要同時設定多項欄位樣式時<br>
     * 可自行將需要的參數填入cellStyle後<br>
     * 在設定cellValue時同時傳入<br>
     * 若只需要簡單設定水平位置、框線<br>
     * 可呼叫其他方法(名稱相同參數不同)<br>
     *
     * @param row          指定的row
     * @param cellPosition cell位置
     * @param formula      cell欄位值
     * @param cellStyle    cell樣式
     */
    public void setCellFormula(Row row, Integer cellPosition, String formula, CellStyle cellStyle) {
        setCellValueAll(row, cellPosition, CELL_TYPE_FORMULA, formula, cellStyle, null, null, null, null);
    }

    /**
     * 填入cell欄位 with Formula value
     *
     * @param row          指定的row
     * @param cellPosition cell位置
     * @param formula      cell欄位值
     * @param cellStyle    cell樣式
     * @param align        水平位置
     */
    public void setCellFormula(Row row, Integer cellPosition, String formula, Integer align) {
        setCellValueAll(row, cellPosition, CELL_TYPE_FORMULA, formula, null, align, null, null, null);
    }

    /**
     * 填入cell欄位 with Formula value
     *
     * @param row          指定的row
     * @param cellPosition cell位置
     * @param formula      cell欄位值
     * @param cellStyle    cell樣式
     * @param align        水平位置
     * @param borderType   框線樣式
     */
    public void setCellFormula(Row row, Integer cellPosition, String formula, Integer align, Integer borderType) {
        setCellValueAll(row, cellPosition, CELL_TYPE_FORMULA, formula, null, align, null, borderType, null);
    }

    /**
     * 填入cell欄位 with Formula value
     *
     * @param row          指定的row
     * @param cellPosition cell位置
     * @param formula      cell欄位值
     * @param cellStyle    cell樣式
     * @param align        水平位置
     * @param borderType   框線樣式
     * @param borderSize   框線尺寸
     */
    public void setCellFormula(Row row, Integer cellPosition, String formula, Integer align, Integer borderType, Integer borderSize) {
        setCellValueAll(row, cellPosition, CELL_TYPE_FORMULA, formula, null, align, null, borderType, null);
    }

    /**
     * 填入cell欄位 with Formula value
     *
     * @param row          指定的row
     * @param cellPosition cell位置
     * @param formula      cell欄位值
     * @param cellStyle    cell樣式
     * @param align        水平位置
     * @param valign       垂直位置
     * @param borderType   框線樣式
     * @param borderSize   框線尺寸
     */
    public void setCellFormula(Row row, Integer cellPosition, String formula, Integer align, Integer valign, Integer borderType, Integer borderSize) {
        setCellValueAll(row, cellPosition, CELL_TYPE_FORMULA, formula, null, align, valign, borderType, borderSize);
    }

    /**
     * 填入cell欄位 with Error value<br>
     * 當需要同時設定多項欄位樣式時<br>
     * 可自行將需要的參數填入cellStyle後<br>
     * 在設定cellValue時同時傳入<br>
     * 若只需要簡單設定水平位置、框線<br>
     * 可呼叫其他方法(名稱相同參數不同)<br>
     *
     * @param row          指定的row
     * @param cellPosition cell位置
     * @param cellValue    cell欄位值
     * @param cellStyle    cell樣式
     */
    public void setCellValue(Row row, Integer cellPosition, Byte cellValue, CellStyle cellStyle) {
        setCellValueAll(row, cellPosition, CELL_TYPE_ERROR, cellValue, cellStyle, null, null, null, null);
    }

    /**
     * 填入cell欄位 with Error value
     *
     * @param row          指定的row
     * @param cellPosition cell位置
     * @param cellValue    cell欄位值
     * @param cellStyle    cell樣式
     * @param align        水平位置
     */
    public void setCellValue(Row row, Integer cellPosition, Byte cellValue, Integer align) {
        setCellValueAll(row, cellPosition, CELL_TYPE_ERROR, cellValue, null, align, null, null, null);
    }

    /**
     * 填入cell欄位 with Error value
     *
     * @param row          指定的row
     * @param cellPosition cell位置
     * @param cellValue    cell欄位值
     * @param cellStyle    cell樣式
     * @param align        水平位置
     * @param borderType   框線樣式
     */
    public void setCellValue(Row row, Integer cellPosition, Byte cellValue, Integer align, Integer borderType) {
        setCellValueAll(row, cellPosition, CELL_TYPE_ERROR, cellValue, null, align, null, borderType, null);
    }

    /**
     * 填入cell欄位 with Error value
     *
     * @param row          指定的row
     * @param cellPosition cell位置
     * @param cellValue    cell欄位值
     * @param cellStyle    cell樣式
     * @param align        水平位置
     * @param borderType   框線樣式
     * @param borderSize   框線尺寸
     */
    public void setCellValue(Row row, Integer cellPosition, Byte cellValue, Integer align, Integer borderType, Integer borderSize) {
        setCellValueAll(row, cellPosition, CELL_TYPE_ERROR, cellValue, null, align, null, borderType, null);
    }

    /**
     * 填入cell欄位 with Error value
     *
     * @param row          指定的row
     * @param cellPosition cell位置
     * @param cellValue    cell欄位值
     * @param cellStyle    cell樣式
     * @param align        水平位置
     * @param valign       垂直位置
     * @param borderType   框線樣式
     * @param borderSize   框線尺寸
     */
    public void setCellValue(Row row, Integer cellPosition, Byte cellValue, Integer align, Integer valign, Integer borderType, Integer borderSize) {
        setCellValueAll(row, cellPosition, CELL_TYPE_ERROR, cellValue, null, align, valign, borderType, borderSize);
    }

    /**
     * 填入cell欄位 with boolean value<br>
     * 當需要同時設定多項欄位樣式時<br>
     * 可自行將需要的參數填入cellStyle後<br>
     * 在設定cellValue時同時傳入<br>
     * 若只需要簡單設定水平位置、框線<br>
     * 可呼叫其他方法(名稱相同參數不同)<br>
     *
     * @param row          指定的row
     * @param cellPosition cell位置
     * @param cellValue    cell欄位值
     * @param cellStyle    cell樣式
     */
    public void setCellValue(Row row, Integer cellPosition, boolean cellValue, CellStyle cellStyle) {
        setCellValueAll(row, cellPosition, CELL_TYPE_BOOLEAN, cellValue, cellStyle, null, null, null, null);
    }

    /**
     * 填入cell欄位 with boolean value
     *
     * @param row          指定的row
     * @param cellPosition cell位置
     * @param cellValue    cell欄位值
     * @param cellStyle    cell樣式
     * @param align        水平位置
     */
    public void setCellValue(Row row, Integer cellPosition, boolean cellValue, Integer align) {
        setCellValueAll(row, cellPosition, CELL_TYPE_BOOLEAN, cellValue, null, align, null, null, null);
    }

    /**
     * 填入cell欄位 with boolean value
     *
     * @param row          指定的row
     * @param cellPosition cell位置
     * @param cellValue    cell欄位值
     * @param cellStyle    cell樣式
     * @param align        水平位置
     * @param borderType   框線樣式
     */
    public void setCellValue(Row row, Integer cellPosition, boolean cellValue, Integer align, Integer borderType) {
        setCellValueAll(row, cellPosition, CELL_TYPE_BOOLEAN, cellValue, null, align, null, borderType, null);
    }

    /**
     * 填入cell欄位 with boolean value
     *
     * @param row          指定的row
     * @param cellPosition cell位置
     * @param cellValue    cell欄位值
     * @param cellStyle    cell樣式
     * @param align        水平位置
     * @param borderType   框線樣式
     * @param borderSize   框線尺寸
     */
    public void setCellValue(Row row, Integer cellPosition, boolean cellValue, Integer align, Integer borderType, Integer borderSize) {
        setCellValueAll(row, cellPosition, CELL_TYPE_BOOLEAN, cellValue, null, align, null, borderType, null);
    }

    /**
     * 填入cell欄位 with boolean value
     *
     * @param row          指定的row
     * @param cellPosition cell位置
     * @param cellValue    cell欄位值
     * @param cellStyle    cell樣式
     * @param align        水平位置
     * @param valign       垂直位置
     * @param borderType   框線樣式
     * @param borderSize   框線尺寸
     */
    public void setCellValue(Row row, Integer cellPosition, boolean cellValue, Integer align, Integer valign, Integer borderType, Integer borderSize) {
        setCellValueAll(row, cellPosition, CELL_TYPE_BOOLEAN, cellValue, null, align, valign, borderType, borderSize);
    }

    /**
     * 填入cell欄位 with Calendar value<br>
     * 當需要同時設定多項欄位樣式時<br>
     * 可自行將需要的參數填入cellStyle後<br>
     * 在設定cellValue時同時傳入<br>
     * 若只需要簡單設定水平位置、框線<br>
     * 可呼叫其他方法(名稱相同參數不同)<br>
     *
     * @param row          指定的row
     * @param cellPosition cell位置
     * @param cellValue    cell欄位值
     * @param cellStyle    cell樣式
     */
    public void setCellValue(Row row, Integer cellPosition, Calendar cellValue, CellStyle cellStyle) {
        setCellValueAll(row, cellPosition, CELL_TYPE_BLANK, cellValue, cellStyle, null, null, null, null);
    }

    /**
     * 填入cell欄位 with Calendar value
     *
     * @param row          指定的row
     * @param cellPosition cell位置
     * @param cellValue    cell欄位值
     * @param cellStyle    cell樣式
     * @param align        水平位置
     */
    public void setCellValue(Row row, Integer cellPosition, Calendar cellValue, Integer align) {
        setCellValueAll(row, cellPosition, CELL_TYPE_BLANK, cellValue, null, align, null, null, null);
    }

    /**
     * 填入cell欄位 with Calendar value
     *
     * @param row          指定的row
     * @param cellPosition cell位置
     * @param cellValue    cell欄位值
     * @param cellStyle    cell樣式
     * @param align        水平位置
     * @param borderType   框線樣式
     */
    public void setCellValue(Row row, Integer cellPosition, Calendar cellValue, Integer align, Integer borderType) {
        setCellValueAll(row, cellPosition, CELL_TYPE_BLANK, cellValue, null, align, null, borderType, null);
    }

    /**
     * 填入cell欄位 with Calendar value
     *
     * @param row          指定的row
     * @param cellPosition cell位置
     * @param cellValue    cell欄位值
     * @param cellStyle    cell樣式
     * @param align        水平位置
     * @param borderType   框線樣式
     * @param borderSize   框線尺寸
     */
    public void setCellValue(Row row, Integer cellPosition, Calendar cellValue, Integer align, Integer borderType, Integer borderSize) {
        setCellValueAll(row, cellPosition, CELL_TYPE_BLANK, cellValue, null, align, null, borderType, null);
    }

    /**
     * 填入cell欄位 with Calendar value
     *
     * @param row          指定的row
     * @param cellPosition cell位置
     * @param cellValue    cell欄位值
     * @param cellStyle    cell樣式
     * @param align        水平位置
     * @param valign       垂直位置
     * @param borderType   框線樣式
     * @param borderSize   框線尺寸
     */
    public void setCellValue(Row row, Integer cellPosition, Calendar cellValue, Integer align, Integer valign, Integer borderType, Integer borderSize) {
        setCellValueAll(row, cellPosition, CELL_TYPE_BLANK, cellValue, null, align, valign, borderType, borderSize);
    }

    /**
     * 填入cell欄位 with Date value<br>
     * 當需要同時設定多項欄位樣式時<br>
     * 可自行將需要的參數填入cellStyle後<br>
     * 在設定cellValue時同時傳入<br>
     * 若只需要簡單設定水平位置、框線<br>
     * 可呼叫其他方法(名稱相同參數不同)<br>
     *
     * @param row          指定的row
     * @param cellPosition cell位置
     * @param cellValue    cell欄位值
     * @param cellStyle    cell樣式
     */
    public void setCellValue(Row row, Integer cellPosition, Date cellValue, CellStyle cellStyle) {
        setCellValueAll(row, cellPosition, CELL_TYPE_BLANK, cellValue, cellStyle, null, null, null, null);
    }

    /**
     * 填入cell欄位 with Date value
     *
     * @param row          指定的row
     * @param cellPosition cell位置
     * @param cellValue    cell欄位值
     * @param cellStyle    cell樣式
     * @param align        水平位置
     */
    public void setCellValue(Row row, Integer cellPosition, Date cellValue, Integer align) {
        setCellValueAll(row, cellPosition, CELL_TYPE_BLANK, cellValue, null, align, null, null, null);
    }

    /**
     * 填入cell欄位 with Date value
     *
     * @param row          指定的row
     * @param cellPosition cell位置
     * @param cellValue    cell欄位值
     * @param cellStyle    cell樣式
     * @param align        水平位置
     * @param borderType   框線樣式
     */
    public void setCellValue(Row row, Integer cellPosition, Date cellValue, Integer align, Integer borderType) {
        setCellValueAll(row, cellPosition, CELL_TYPE_BLANK, cellValue, null, align, null, borderType, null);
    }

    /**
     * 填入cell欄位 with Date value
     *
     * @param row          指定的row
     * @param cellPosition cell位置
     * @param cellValue    cell欄位值
     * @param cellStyle    cell樣式
     * @param align        水平位置
     * @param borderType   框線樣式
     * @param borderSize   框線尺寸
     */
    public void setCellValue(Row row, Integer cellPosition, Date cellValue, Integer align, Integer borderType, Integer borderSize) {
        setCellValueAll(row, cellPosition, CELL_TYPE_BLANK, cellValue, null, align, null, borderType, null);
    }

    /**
     * 填入cell欄位 with Date value
     *
     * @param row          指定的row
     * @param cellPosition cell位置
     * @param cellValue    cell欄位值
     * @param cellStyle    cell樣式
     * @param align        水平位置
     * @param valign       垂直位置
     * @param borderType   框線樣式
     * @param borderSize   框線尺寸
     */
    public void setCellValue(Row row, Integer cellPosition, Date cellValue, Integer align, Integer valign, Integer borderType, Integer borderSize) {
        setCellValueAll(row, cellPosition, CELL_TYPE_BLANK, cellValue, null, align, valign, borderType, borderSize);
    }

    /**
     * 一次填入多個 cell 欄位<br>
     * 當需要同時設定多項欄位樣式時<br>
     * 可自行將需要的參數填入cellStyle後<br>
     * 在設定cellValue時同時傳入<br>
     * 若只需要簡單設定水平位置、框線<br>
     * 可呼叫其他方法(名稱相同參數不同)<br>
     *
     * @param row          指定的row
     * @param cellPosition cell位置
     * @param cellValue    cell欄位值
     * @param cellStyle    cell樣式
     */
    public void setCellValues(Row row, Integer startCellNum, Integer endCellNum, Object[] cellValues, CellStyle cellStyle) {
        int valIdx = 0;// cellValues index
        Object cellValue = null;
        for (int i = startCellNum; i <= endCellNum; i++) {
            if (row.getCell(i) == null) {
                row.createCell(i);
            }
            if (valIdx + 1 <= cellValues.length) {
                cellValue = cellValues[valIdx];
            } else {
                cellValue = "";
            }
            setCellValueAll(row, i, null, cellValue, cellStyle, null, null, null, null);
            valIdx++;
        }
    }

    /**
     * 一次填入多個 cell 欄位
     *
     * @param row          指定的row
     * @param cellPosition cell位置
     * @param cellValue    cell欄位值
     * @param cellStyle    cell樣式
     * @param align        水平位置
     */
    public void setCellValues(Row row, Integer startCellNum, Integer endCellNum, Object[] cellValues, Integer align) {
        int valIdx = 0;// cellValues index
        Object cellValue = null;
        for (int i = startCellNum; i <= endCellNum; i++) {
            if (row.getCell(i) == null) {
                row.createCell(i);
            }
            if (valIdx + 1 <= cellValues.length) {
                cellValue = cellValues[valIdx];
            } else {
                cellValue = "";
            }
            setCellValueAll(row, i, null, cellValue, null, align, null, null, null);
            valIdx++;
        }
    }

    // -------------------------------------------------------------------------------------

    // /**
    // * 填入cell欄位值
    // *
    // * @param row 指定的row
    // * @param cellPosition cell位置
    // * @param cellType cell類別
    // * @param cellValue cell欄位值
    // * @param cellStyle cell樣式
    // */
    // private void setCellValue(Row row, Integer cellPosition, Integer cellType, Object cellValue, CellStyle cellStyle) {
    // try {
    // Cell cell = row.getCell(cellPosition);
    // if (cell == null) {
    // throw new NullPointerException("Cell不存在");
    // }
    //
    // // 設定cell style
    // if (cellStyle != null) {
    // cell.setCellStyle(cellStyle);
    // }
    //
    // // 填入cell類型
    // if (cellType != null) {
    // cell.setCellType(cellType);
    // }
    // // 填入cell值
    // if (cellValue instanceof Boolean) {
    // cell.setCellType(CELL_TYPE_BOOLEAN);
    // cell.setCellValue((Boolean) cellValue);
    // }
    // else if (cellValue instanceof Byte) {
    // cell.setCellErrorValue((Byte) cellValue);
    // }
    // else if (cellValue instanceof Double) {
    // cell.setCellValue((Double) cellValue);
    // }
    // else if (cellValue instanceof String) {
    // if (cell.getCellType() == CELL_TYPE_FORMULA) {
    // cell.setCellFormula((String) cellValue);
    // }
    // else {
    // RichTextString rts = null;
    // if ((row.getSheet()).getWorkbook() instanceof XSSFWorkbook) {
    // rts = new XSSFRichTextString((String) cellValue);
    // }
    // else {
    // rts = new HSSFRichTextString((String) cellValue);
    // }
    // cell.setCellValue(rts);
    // }
    // }
    // else if (cellValue instanceof Date) {
    // cell.setCellValue((Date) cellValue);
    // }
    // else if (cellValue instanceof Calendar) {
    // cell.setCellValue((String) cellValue);
    // }
    // else {
    // cell.setCellValue(new HSSFRichTextString(cellValue.toString()));
    // }
    // }
    // catch (Exception e) {
    // e.printStackTrace();
    // }
    // }

    /**
     * 一次填入多個 cell 欄位
     *
     * @param row          指定的row
     * @param cellPosition cell位置
     * @param cellValue    cell欄位值
     * @param cellStyle    cell樣式
     * @param align        水平位置
     * @param valign       垂直位置
     * @param borderType   框線樣式
     * @param borderSize   框線尺寸
     */
    public void setCellValues(Row row, Integer startCellNum, Integer endCellNum, Object[] cellValues, Integer align, Integer borderType) {
        int valIdx = 0;// cellValues index
        Object cellValue = null;
        for (int i = startCellNum; i <= endCellNum; i++) {
            if (row.getCell(i) == null) {
                row.createCell(i);
            }
            if (valIdx + 1 <= cellValues.length) {
                cellValue = cellValues[valIdx];
            } else {
                cellValue = "";
            }
            setCellValueAll(row, i, null, cellValue, null, align, null, borderType, null);
            valIdx++;
        }
    }

    /**
     * 一次填入多個 cell 欄位
     *
     * @param row          指定的row
     * @param cellPosition cell位置
     * @param cellValue    cell欄位值
     * @param cellStyle    cell樣式
     * @param align        水平位置
     * @param valign       垂直位置
     * @param borderType   框線樣式
     * @param borderSize   框線尺寸
     */
    public void setCellValues(Row row, Integer startCellNum, Integer endCellNum, Object[] cellValues, Integer align, Integer borderType, Integer borderSize) {
        int valIdx = 0;// cellValues index
        Object cellValue = null;
        for (int i = startCellNum; i <= endCellNum; i++) {
            if (row.getCell(i) == null) {
                row.createCell(i);
            }
            if (valIdx + 1 <= cellValues.length) {
                cellValue = cellValues[valIdx];
            } else {
                cellValue = "";
            }
            setCellValueAll(row, i, null, cellValue, null, align, null, borderType, borderSize);
            valIdx++;
        }
    }

    /**
     * 一次填入多個 cell 欄位
     *
     * @param row          指定的row
     * @param cellPosition cell位置
     * @param cellValue    cell欄位值
     * @param cellStyle    cell樣式
     * @param align        水平位置
     * @param valign       垂直位置
     * @param borderType   框線樣式
     * @param borderSize   框線尺寸
     */
    public void setCellValues(Row row, Integer startCellNum, Integer endCellNum, Object[] cellValues, Integer align, Integer valign, Integer borderType, Integer borderSize) {
        int valIdx = 0;// cellValues index
        Object cellValue = null;
        for (int i = startCellNum; i <= endCellNum; i++) {
            if (row.getCell(i) == null) {
                row.createCell(i);
            }
            if (valIdx + 1 <= cellValues.length) {
                cellValue = cellValues[valIdx];
            } else {
                cellValue = "";
            }
            setCellValueAll(row, i, null, cellValue, null, align, valign, borderType, borderSize);
            valIdx++;
        }
    }

    /**
     * 填入cell欄位值
     *
     * @param row          指定的row
     * @param cellPosition cell位置
     * @param cellType     cell類別
     * @param cellValue    cell欄位值
     * @param cellType     cell類別
     * @param align        水平位置
     * @param valign       垂直位置
     * @param borderType   框線樣式
     * @param borderSize   框線尺寸
     */
    private void setCellValueAll(Row row, Integer cellPosition, Integer cellType, Object cellValue, CellStyle cellStyle, Integer align, Integer valign, Integer borderType, Integer borderSize) {
        try {
            Cell cell = row.getCell(cellPosition);
            if (cell == null) {
                throw new NullPointerException("Cell不存在");
            }

            // 設定cell style
            if (cellStyle == null && align == null && valign == null && borderType == null && borderSize == null) {
                if (cell.getCellStyle() == null) {
                    cell.setCellStyle(this.cellStyle);
                }
            } else if (cellStyle != null && (align == null && valign == null && borderType == null && borderSize == null)) {
                cell.setCellStyle(cellStyle);
            } else if (cellStyle == null && !(align == null && valign == null && borderType == null && borderSize == null)) {
                cell.setCellStyle(createStyle(null, align, valign, borderType, borderSize));
            }

            // 填入cell類型
            if (cellType != null) {
                cell.setCellType(CellType.forInt(cellType));
            }
            // 填入cell值
            if (cellValue instanceof Boolean) {
                cell.setCellValue((Boolean) cellValue);
            } else if (cellValue instanceof Byte) {
                cell.setCellErrorValue((Byte) cellValue);
            } else if (cellValue instanceof Double) {
                cell.setCellValue((Double) cellValue);
            } else if (cellValue instanceof String) {
                if (cell.getCellType()
                        .getCode() == CELL_TYPE_FORMULA) {
                    cell.setCellFormula(StringUtility.normalizeString((String) cellValue));
                } else {
                    RichTextString rts = null;
                    if ((row.getSheet()).getWorkbook() instanceof XSSFWorkbook) {
                        rts = new XSSFRichTextString((String) cellValue);
                    } else {
                        rts = new HSSFRichTextString((String) cellValue);
                    }
                    cell.setCellValue(rts);
                }
            } else if (cellValue instanceof Date) {
                cell.setCellValue((Date) cellValue);
            } else if (cellValue instanceof Calendar) {
                cell.setCellValue((String) cellValue);
            } else {
                cell.setCellValue(new HSSFRichTextString(cellValue.toString()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 複製行
     *
     * @param sourceSheetName 來源sheet名稱
     * @param targetSheetName 目標sheet名稱
     * @param sourceStartRow  來源開始row位置
     * @param sourceEndRow    來源結束row位置
     * @param targetPosition  欲複製過去的sheet中，row的開始位置
     * @param columnCount     複製的cell數目長度
     */
    public void copyRows(String sourceSheetName, String targetSheetName, int sourceStartRow, int sourceEndRow, int targetPosition, int columnCount) {
        Row sourceRow = null;
        Row targetRow = null;
        Cell sourceCell = null;
        Cell targetCell = null;
        Sheet sourceSheet = null;
        Sheet targetSheet = null;
        CellRangeAddress region = null;
        CellType cType;
        int i;
        int j;
        int targetRowFrom;
        int targetRowTo;

        if ((sourceStartRow == -1) || (sourceEndRow == -1)) {
            return;
        }
        sourceSheet = workbook.getSheet(sourceSheetName);
        targetSheet = workbook.getSheet(targetSheetName);
        // 拷貝合並的單元格
        for (i = 0; i < sourceSheet.getNumMergedRegions(); i++) {
            region = sourceSheet.getMergedRegion(i);
            if ((region.getFirstRow() >= sourceStartRow) && (region.getLastRow() <= sourceEndRow)) {
                targetRowFrom = region.getFirstRow() - sourceStartRow + targetPosition;
                targetRowTo = region.getLastRow() - sourceStartRow + targetPosition;
                region.setFirstRow(targetRowFrom);
                region.setLastRow(targetRowTo);
                targetSheet.addMergedRegion(region);
            }
        }
        // 設置列寬
        for (i = sourceStartRow; i <= sourceEndRow; i++) {
            sourceRow = sourceSheet.getRow(i);
            if (sourceRow != null) {
                for (j = 0; j < columnCount; j++) {
                    if (sourceSheet.getColumnWidth(j) <= 8) {
                        targetSheet.setColumnWidth(j, (short) 2500);
                    } else {
                        targetSheet.setColumnWidth(j, sourceSheet.getColumnWidth(j));
                    }
                }
                break;
            }
        }
        // 拷貝行並填充數据
        for (; i <= sourceEndRow; i++) {
            sourceRow = sourceSheet.getRow(i);
            if (sourceRow == null) {
                continue;
            }
            targetRow = targetSheet.createRow(i - sourceStartRow + targetPosition);
            targetRow.setHeight(sourceRow.getHeight());
            for (j = 0; j < columnCount; j++) {
                sourceCell = sourceRow.getCell(j);
                if (sourceCell == null) {
                    continue;
                }
                targetCell = targetRow.createCell(j);
                // targetCell.setEncoding(sourceCell.getEncoding());
                targetCell.setCellStyle(sourceCell.getCellStyle());
                CellStyle cellStyle = sourceCell.getCellStyle();
                cType = sourceCell.getCellType();
                targetCell.setCellType(cType);
                switch (cType) {
                    case BOOLEAN:
                        targetCell.setCellValue(sourceCell.getBooleanCellValue());
                        break;
                    case ERROR:
                        targetCell.setCellErrorValue(sourceCell.getErrorCellValue());
                        break;
                    case FORMULA:
                        // parseFormula這個函數的用途在后面說明
                        targetCell.setCellFormula(parseFormula(sourceCell.getCellFormula()));
                        break;
                    case NUMERIC:
                        targetCell.setCellValue(sourceCell.getNumericCellValue());
                        break;
                    case STRING:
                        targetCell.setCellValue(sourceCell.getRichStringCellValue());
                        break;
                }
            }
        }
    }

    /**
     * 合併儲存格
     *
     * @param sheet     指定的sheet
     * @param startRow  開始的row位置
     * @param endtRow   結束的row位置
     * @param startCell 開始的cell位置
     * @param endCell   結束的cell位置
     */
    public void setMergeCellValue(Sheet sheet, Integer startRow, Integer endtRow, Integer startCell, Integer endCell, Object cellValue) {
        Row row = sheet.getRow(startRow);
        Cell cell = row.getCell(startCell);
        CellStyle cellstyle = cell.getCellStyle();

        sheet.addMergedRegion(new CellRangeAddress(startRow, endtRow, startCell, endCell));

        if (cellstyle != null) {
            cell.setCellStyle(cellstyle);
        }

        if (cellValue != null) {
            setCellValueAll(row, startCell, null, cellValue, null, null, null, null, null);
        }
    }

    /**
     * 公式的問題。POI對Excel公式的支持是相當好的，<br>
     * 但是我發現一個問題，如果公式里面的函數不帶參數， <br>
     * 比如now()或today()，那麼你通過getCellFormula()取出來的值就是now(ATTR(semiVolatile)) <br>
     * 和today(ATTR(semiVolatile))，這樣的值寫入Excel是會出錯的， <br>
     * 這也是上面copyRow的函數在寫入公式前要調用parseFormula的原因， <br>
     * parseFormula這個函數的功能很簡單，就是把ATTR(semiVolatile)刪掉
     *
     * @param pPOIFormula String
     * @return String
     */
    private String parseFormula(String pPOIFormula) {
        final String cstReplaceString = "ATTR(semiVolatile)"; //$NON-NLS-1$
        StringBuffer result = null;
        int index;

        result = new StringBuffer();
        index = pPOIFormula.indexOf(cstReplaceString);
        if (index >= 0) {
            result.append(pPOIFormula, 0, index);
            result.append(pPOIFormula.substring(index + cstReplaceString.length()));
        } else {
            result.append(pPOIFormula);
        }

        return result.toString();
    }

    /**
     * 添加圖片
     *
     * @param sheet       指定的sheet
     * @param picFilePath 圖片文件名稱（全路徑）
     * @param picType     圖片類型
     * @param startRow    圖片所在的起始行
     * @param startCol    圖片所在的起始列
     * @param endRow      圖片所在的結束行
     * @param endCol      圖片所在的結束列
     */
    public void addPicture(Sheet sheet, String picFilePath, int picType, int startRow, int startCol, int endRow, int endCol) {
        InputStream is = null;
        try {
            // 讀取圖片
            is = new FileInputStream(FilenameUtils.getBaseName(picFilePath));
            byte[] bytes = IOUtils.toByteArray(is);
            int pictureIdx = workbook.addPicture(bytes, picType);
            is.close();

            // 寫圖片
            CreationHelper helper = workbook.getCreationHelper();
            Drawing drawing = sheet.createDrawingPatriarch();
            ClientAnchor anchor = helper.createClientAnchor();

            // 設置圖片的位置
            anchor.setRow1(startRow);
            anchor.setCol1(startCol);
            anchor.setRow2(endRow + 1);
            anchor.setCol2(endCol + 1);

            Picture pict = drawing.createPicture(anchor, pictureIdx);

            // 圖片預設為指定的欄位範圍大小
            pict.resize(1.0, 1.0);
        } catch (Exception e) {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    /**
     * 取得單一row之所有欄位值
     *
     * @param row 指定的row
     * @return list:value為該row所有cell欄位值
     */
    public List<String> parseRowCellValue(Row row) {
        List<String> resultList = new ArrayList<String>();
        Cell cell = null;
        if (row != null) {
            short firstCellNum = row.getFirstCellNum();
            short lastCellNum = row.getLastCellNum();
            resultList = parseRowCellValue(row, firstCellNum, lastCellNum);
        }
        return resultList;
    }

    /**
     * 取得單一row中特定範圍欄位值
     *
     * @param row          指定的row
     * @param firstCellNum 開始的 cell 位置
     * @param lastCellNum  結束的 cell 位置
     * @return list:value為該row特定範圍cell欄位值
     */
    public List<String> parseRowCellValue(Row row, short firstCellNum, short lastCellNum) {
        List<String> resultList = new ArrayList<String>();
        Cell cell = null;
        if (row != null) {
            for (int i = firstCellNum; i <= lastCellNum; i++) {
                cell = row.getCell(i);
                if (cell != null) {
                    String cellValue = parseCellValue(cell);
                    resultList.add(cellValue);
                }
            }
        }
        return resultList;
    }

    /**
     * 依據輸入參數取的Cell欄位值
     *
     * @param sheet 指定的sheet
     * @return map:key為rowNum, value為該row所有cell欄位值
     */
    public Map<Integer, List<String>> parseRowsCellValue(Sheet sheet) {
        return parseRowsCellValue(sheet, sheet.getFirstRowNum(), sheet.getLastRowNum());
    }

    /**
     * 依據輸入參數取的Cell欄位值
     *
     * @param sheet       指定的sheet
     * @param startRowNum 起始row編號
     * @param endRowNum   起始row編號
     * @return map:key為rowNum, value為該row所有cell欄位值
     */
    public Map<Integer, List<String>> parseRowsCellValue(Sheet sheet, Integer startRowNum, Integer endRowNum) {
        Map<Integer, List<String>> map = new TreeMap<Integer, List<String>>();
        Row row = null;
        Cell cell = null;

        for (int i = startRowNum; i <= endRowNum; i++) {
            row = sheet.getRow(i);
            if (row != null) {
                row.getFirstCellNum();
                Map<Integer, List<String>> tempMap = new TreeMap<Integer, List<String>>();
                tempMap = parseRowsCellValue(sheet, i, i, (int) row.getFirstCellNum(), (int) row.getLastCellNum());
                map.putAll(tempMap);
            }
        }

        return map;
    }

    /**
     * 依據輸入參數取得Cell欄位值
     *
     * @param sheet        指定的sheet
     * @param startRowNum  開始row編號
     * @param endRowNum    結束row編號
     * @param startCellNum 開始cell編號
     * @param endCellNum   結束cell編號
     * @return map:key為rowNum, value為該row所有cell欄位值
     */
    public Map<Integer, List<String>> parseRowsCellValue(Sheet sheet, Integer startRowNum, Integer endRowNum, Integer startCellNum, Integer endCellNum) {
        Map<Integer, List<String>> map = new TreeMap<Integer, List<String>>();
        Row row = null;
        Cell cell = null;
        for (int i = startRowNum; i <= endRowNum; i++) {
            row = sheet.getRow(i);
            if (row != null) {
                List<String> resultList = new ArrayList<String>();
                for (int j = startCellNum; j <= endCellNum; j++) {
                    cell = row.getCell(j);
                    if (cell != null) {
                        String cellValue = parseCellValue(cell);
                        resultList.add(cellValue);
                    }
                }
                map.put(i, resultList);
            }
        }
        return map;
    }

    /**
     * 讀取cell的欄位值
     *
     * @param cell 指定的cell
     * @return
     */
    public String parseCellValue(Cell cell) {
        try {
            String cellValue = "";
            if (cell != null) {
                switch (cell.getCellType()) {
                    case BOOLEAN:
                        cellValue = Boolean.toString(cell.getBooleanCellValue());
                        break;
                    case ERROR:
                        cellValue = Byte.toString(cell.getErrorCellValue());
                        break;
                    case FORMULA:
                        cellValue = cell.getCellFormula();
                        break;
                    case NUMERIC:
                        if (DateUtil.isCellDateFormatted(cell)) {
                            // Date date = DateUtil.getJavaDate(cell.getNumericCellValue());
                            // DateFormat df = new SimpleDateFormat(cell.getCellStyle().getDataFormatString(), Locale.TAIWAN);
                            // cellValue = df.format(date);
                            cellValue = DateUtil.getJavaDate(cell.getNumericCellValue())
                                    .toString();
                        } else {
                            cellValue = Double.toString(cell.getNumericCellValue());
                        }
                        break;
                    case STRING:
                        cellValue = cell.getRichStringCellValue()
                                .toString();
                        break;
                }
            }
            return cellValue;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 設定欄位寬度
     *
     * @param sheet    指定的sheet
     * @param startCol 開始欄位位置
     * @param endCol   結束欄位位置
     * @param colWidth 欄位寬度
     * @param autoSize 是否根據欄位內容長度自動調整欄位寬
     */
    public void setColumeWidth(Sheet sheet, Integer startCol, Integer endCol, Integer colWidth, boolean autoSize) {
        if (sheet != null) {
            for (int i = startCol; i <= endCol; i++) {
                sheet.setColumnWidth(i, colWidth);

                if (autoSize) {
                    sheet.autoSizeColumn(i);
                }
            }
        }
    }

    /**
     * 設定頁首資訊 - 左
     *
     * @param sheet  要設定頁首的sheet
     * @param string 頁首內容
     */
    public void setPageHeaderLeft(Sheet sheet, String string) {
        Header header = sheet.getHeader();
        header.setLeft(string);
    }

    /**
     * 設定頁首資訊 - 中
     *
     * @param sheet  要設定頁首的sheet
     * @param string 頁首內容
     */
    public void setPageHeaderCenter(Sheet sheet, String string) {
        Header header = sheet.getHeader();
        header.setCenter(string);
    }

    /**
     * 設定頁首資訊 - 右
     *
     * @param sheet  要設定頁首的sheet
     * @param string 頁首內容
     */
    public void setPageHeaderRight(Sheet sheet, String string) {
        Header header = sheet.getHeader();
        header.setRight(string);
    }

    /**
     * 設定頁尾資訊 - 左
     *
     * @param sheet  要設定頁尾的sheet
     * @param string 頁尾內容
     */
    public void setPageFooterLeft(Sheet sheet, String string) {
        Footer footer = sheet.getFooter();
        footer.setLeft(string);
    }

    /**
     * 設定頁尾資訊 - 中
     *
     * @param sheet  要設定頁尾的sheet
     * @param string 頁尾內容
     */
    public void setPageFooterCenter(Sheet sheet, String string) {
        Footer footer = sheet.getFooter();
        footer.setCenter(string);
    }

    /**
     * 設定頁尾資訊 - 右
     *
     * @param sheet  要設定頁尾的sheet
     * @param string 頁尾內容
     */
    public void setPageFooterRight(Sheet sheet, String string) {
        Footer footer = sheet.getFooter();
        footer.setRight(string);
    }

    /**
     * 新增 row
     *
     * @param sheet
     * @param r
     * @return
     */
    public Row incrementRow(Sheet sheet, Row r) {
        int rowNum = r.getRowNum();
        rowNum++;
        r = sheet.getRow(rowNum);
        if (r == null) {
            r = sheet.createRow(rowNum);
        }
        return r;
    }

    /**
     * 設定cell邊框樣式
     *
     * @param cellStyle
     * @param align      水平位置
     * @param valign     垂直位置
     * @param borderType 框線樣式
     * @param borderSize 框線尺寸
     */
    private CellStyle createStyle(CellStyle cellStyle, Integer align, Integer valign, Integer borderType, Integer borderSize) {
        int defaultType = BORDER_TYPE_0;
        int defaultSize = StyleUtility.BORDER_THIN;
        CellStyle defaultCellStyle = null;
        // 若原本的cellStyle為空
        // 則給予初始值(僅設定字型)
        if (cellStyle != null) {
            defaultCellStyle = cellStyle;
        } else {
            defaultCellStyle = workbook.createCellStyle();
            defaultCellStyle.setFont(font);
        }
        // 設定水平位置
        if (align != null) {
            defaultCellStyle.setAlignment(HorizontalAlignment.forInt(align.shortValue()));
        }
        // 設定垂直位置
        if (valign != null) {
            defaultCellStyle.setVerticalAlignment(VerticalAlignment.forInt(valign.shortValue()));
        }

        // 設定邊框樣式及尺寸
        if (borderType != null && borderSize != null) {
            defaultType = borderType.intValue();
            defaultSize = borderSize.intValue();
        } else if (borderType == null && borderSize != null) {
            defaultType = BORDER_TYPE_15;
            defaultSize = borderSize.intValue();
        } else if (borderType != null && borderSize == null) {
            defaultType = borderType.intValue();
            defaultSize = StyleUtility.BORDER_THIN;
        }

        if (defaultType != BORDER_TYPE_0) {
            if (defaultType == BORDER_TYPE_1 || defaultType == BORDER_TYPE_3 || defaultType == BORDER_TYPE_5 || defaultType == BORDER_TYPE_7 || defaultType == BORDER_TYPE_9 || defaultType == BORDER_TYPE_11 || defaultType == BORDER_TYPE_13 || defaultType == BORDER_TYPE_15) {
                defaultCellStyle.setBorderTop(BorderStyle.valueOf((short) defaultSize));// 上
            }
            if (defaultType == BORDER_TYPE_2 || defaultType == BORDER_TYPE_3 || defaultType == BORDER_TYPE_6 || defaultType == BORDER_TYPE_7 || defaultType == BORDER_TYPE_10 || defaultType == BORDER_TYPE_11 || defaultType == BORDER_TYPE_14 || defaultType == BORDER_TYPE_15) {
                defaultCellStyle.setBorderBottom(BorderStyle.valueOf((short) defaultSize)); // 下
            }
            if (defaultType == BORDER_TYPE_4 || defaultType == BORDER_TYPE_5 || defaultType == BORDER_TYPE_6 || defaultType == BORDER_TYPE_7 || defaultType == BORDER_TYPE_12 || defaultType == BORDER_TYPE_13 || defaultType == BORDER_TYPE_14 || defaultType == BORDER_TYPE_15) {
                defaultCellStyle.setBorderLeft(BorderStyle.valueOf((short) defaultSize)); // 左
            }
            if (defaultType == BORDER_TYPE_8 || defaultType == BORDER_TYPE_9 || defaultType == BORDER_TYPE_10 || defaultType == BORDER_TYPE_11 || defaultType == BORDER_TYPE_12 || defaultType == BORDER_TYPE_13 || defaultType == BORDER_TYPE_14 || defaultType == BORDER_TYPE_15) {
                defaultCellStyle.setBorderRight(BorderStyle.valueOf((short) defaultSize)); // 右
            }
        }

        return defaultCellStyle;
    }

    /**
     * 設定欄寬<br>
     * <br>
     * POI裡欄寬的單位是1/256px<br>
     * 如寬度要設1px,則需傳入256<br>
     * 故為了方便使用<br>
     * 此方法傳入之width會自動乘上256<br>
     * <br>
     * 最大寬度為255個<br>
     * 若超過255個則設為255px寬<br>
     * <br>
     *
     * @param sheet
     * @param colPosition 欲變更欄寬之欄位位置
     * @param width       欄寬
     */
    public void setColumnWidth(Sheet sheet, int colPosition, float width) {
        if (width > 0 && width <= 255) {
            sheet.setColumnWidth(colPosition, (short) (256 * width));
        } else if (width > 255) {
            sheet.setColumnWidth(colPosition, (short) (256 * 255));
        }
    }

    /**
     * 設定列高<br>
     * <br>
     * POI裡列高的單位是1/20px<br>
     * 如寬度要設1px,則需傳入20<br>
     * 故為了方便使用<br>
     * 此方法傳入之width會自動乘上20<br>
     *
     * @param row
     * @param height 列高
     */
    public void setRowHeight(Row row, float height) {
        if (height > 0) {
            row.setHeight((short) (20 * height));
        }
    }

}
