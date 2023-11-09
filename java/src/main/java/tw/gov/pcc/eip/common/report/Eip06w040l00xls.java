package tw.gov.pcc.eip.common.report;

import com.iisigroup.easyreport.pdf.exception.ReportException;
import com.iisigroup.easyreport.xls.XlsReport;
import org.apache.poi.hssf.usermodel.HSSFHeader;
import org.apache.poi.hssf.usermodel.HSSFPrintSetup;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import tw.gov.pcc.eip.domain.Eip06w040Report;
import tw.gov.pcc.eip.util.DateUtility;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.iisigroup.easyreport.pdf.ReportBase.EMPTY_FIELD;

/**
 * 會議室活動報表 excel
 *
 * @author 2201009
 *
 */
public class Eip06w040l00xls extends XlsReport {

    private Sheet sheet;

    private final CellStyle titleStyle;
    private final CellStyle alignLeft;
    private final CellStyle alignRight;
    private final CellStyle alignCenter;
    private final CellStyle alignThinLeft;
    private final CellStyle alignThinRight;
    private final CellStyle alignCenterTitle;
    private final CellStyle alignLeftHeader;
    private final CellStyle alignTopBorder;

    // 設定欄位數量
    private static final int cellAmountSheet = 8;

    // 設定框線
    private static final short borderWidth = 1;

    public Eip06w040l00xls() throws ReportException {
        super(false);
    }
    
    {
        // 設定字體
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 10);
        font.setBold(true);
        font.setFontName("標楷體");

        Font titleFont = workbook.createFont();
        titleFont.setFontHeightInPoints((short) 14);
        titleFont.setBold(true);
        titleFont.setFontName("標楷體");

        // 設定 title 格式
        titleStyle = workbook.createCellStyle();
        titleStyle.setAlignment(HorizontalAlignment.CENTER);
        titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        titleStyle.setFont(titleFont);

        // 設定 置左對齊 表頭
        alignLeftHeader = workbook.createCellStyle();
        alignLeftHeader.setAlignment(HorizontalAlignment.LEFT);
        alignLeftHeader.setVerticalAlignment(VerticalAlignment.CENTER);
        alignLeftHeader.setBorderBottom(BorderStyle.DOUBLE);
        alignLeftHeader.setBorderTop(BorderStyle.valueOf(borderWidth));
        alignLeftHeader.setFont(font);
        // 設定 Top Border 表頭
        alignTopBorder = workbook.createCellStyle();
        alignTopBorder.setBorderTop(BorderStyle.valueOf(borderWidth));

        // 設定 靠左對齊文字 粗體 格式
        alignLeft = workbook.createCellStyle();
        alignLeft.setAlignment(HorizontalAlignment.LEFT);
        alignLeft.setVerticalAlignment(VerticalAlignment.CENTER);
        alignLeft.setFont(font);
        alignLeft.setWrapText(true);

        // 設定 靠左對齊文字 格式
        alignThinLeft = workbook.createCellStyle();
        alignThinLeft.setAlignment(HorizontalAlignment.LEFT);
        alignThinLeft.setVerticalAlignment(VerticalAlignment.CENTER);
        alignThinLeft.setFont(font);

        // 設定 靠右對齊 粗體 數字格式
        alignRight = workbook.createCellStyle();
        alignRight.setAlignment(HorizontalAlignment.RIGHT);
        alignRight.setVerticalAlignment(VerticalAlignment.CENTER);
        alignRight.setFont(font);

        // 設定 靠右對齊 數字格式
        alignThinRight = workbook.createCellStyle();
        alignThinRight.setAlignment(HorizontalAlignment.RIGHT);
        alignThinRight.setVerticalAlignment(VerticalAlignment.CENTER);
        alignThinRight.setFont(font);

        // 設定 置中標題文字 + 下邊框 格式
        alignCenterTitle = workbook.createCellStyle();
        alignCenterTitle.setAlignment(HorizontalAlignment.CENTER);
        alignCenterTitle.setVerticalAlignment(VerticalAlignment.CENTER);
        alignCenterTitle.setBorderBottom(BorderStyle.DOUBLE);
        alignCenterTitle.setBorderTop(BorderStyle.valueOf(borderWidth));
        alignCenterTitle.setFont(font);

        // 設定 置中對齊文字 格式
        alignCenter = workbook.createCellStyle();
        alignCenter.setAlignment(HorizontalAlignment.CENTER);
        alignCenter.setVerticalAlignment(VerticalAlignment.CENTER);
        alignCenter.setFont(font);
    }

    /**
     * 產生 excel 檔案
     *
     * @param resultList 資料參數
     */
    public void createXls(List<Eip06w040Report> resultList, String meetingdt) {
        createSheet(resultList, meetingdt);
    }

    /**
     * 產生頁籤
     * @param list 統計資料 list
     */
    public void createSheet(List<Eip06w040Report> list, String meetingdt) {
        // 設定列印格式
        String formatChineseDate = DateUtility.formatChineseDateString(meetingdt, true);
        this.sheet = createSheet(formatChineseDate);
        PrintSetup printSetup = sheet.getPrintSetup();
        printSetup.setLandscape(true);
        printSetup.setPaperSize(HSSFPrintSetup.A4_PAPERSIZE);

        // 建立表頭
//        Row row0 = createRow(sheet, 0, cellAmountSheet, titleStyle);
//        row0.getSheet().addMergedRegion(new CellRangeAddress(0, 0, 0, 7));
//        row0.setHeightInPoints(20f);
        int dayOfWeek = DateUtility.getDayOfWeek(meetingdt, false) ;

        HSSFHeader xhd = (HSSFHeader)sheet.getHeader();
        xhd.setCenter(HSSFHeader.font("標楷體", "regular") +
                HSSFHeader.fontSize((short) 14) + HSSFHeader.startBold() + formatChineseDate + " " + "星期" + "日一二三四五六".charAt(dayOfWeek) + " " + "會議活動" +
                HSSFHeader.endBold());

        // 報表資料標題
        Row row0 = createRow(sheet, 0, cellAmountSheet, alignCenterTitle);
        row0.setHeightInPoints(20f);
        setColumnWidth(sheet, 0, 20);
        setColumnWidth(sheet, 1, 8);
        setColumnWidth(sheet, 2, 20);
        setColumnWidth(sheet, 3, 18);
        setColumnWidth(sheet, 4, 18);
        setColumnWidth(sheet, 5, 8);
        setColumnWidth(sheet, 6, 18);
        setColumnWidth(sheet, 7, 18);

        setCellValue(row0, 0, "地點", alignCenterTitle);
        setCellValue(row0, 1, "時間", alignCenterTitle);
        setCellValue(row0, 2, "會議名稱", alignCenterTitle);
        setCellValue(row0, 3, "主持人", alignCenterTitle);
        setCellValue(row0, 4, "承辦單位", alignCenterTitle);
        setCellValue(row0, 5, "人數", alignCenterTitle);
        setCellValue(row0, 6, "餐點", alignCenterTitle);
        setCellValue(row0, 7, "設備", alignCenterTitle);

        // 設定列印時重複顯示表頭
        sheet.setRepeatingRows(new CellRangeAddress(0, 0, 0, 7));

        // 資料 list 內容
        // 依照不同會議室分群
        Map<String, List<Eip06w040Report>> roomMap = list.stream()
                .collect(Collectors.groupingBy(Eip06w040Report::getRoomName, LinkedHashMap::new, Collectors.toList()));
        int rowCount = 1;
//        int index = 1;
        for(List<Eip06w040Report> details: roomMap.values()) {
            Row row = createRow(sheet, rowCount, cellAmountSheet, alignLeft);
            row.setHeightInPoints(20f);
            for(Eip06w040Report data : details){
                setCellValue(row, 0, data.getRoomName(), alignLeftHeader);
                setCellValue(row, 1, EMPTY_FIELD, alignTopBorder);
                setCellValue(row, 2, EMPTY_FIELD, alignTopBorder);
                setCellValue(row, 3, EMPTY_FIELD, alignTopBorder);
                setCellValue(row, 4, EMPTY_FIELD, alignTopBorder);
                setCellValue(row, 5, EMPTY_FIELD, alignTopBorder);
                setCellValue(row, 6, EMPTY_FIELD, alignTopBorder);
                setCellValue(row, 7, EMPTY_FIELD, alignTopBorder);

                //印完地點後換行
                rowCount++;
                Row rowData = createRow(sheet, rowCount, cellAmountSheet, alignLeft);

                setCellValue(rowData, 0, EMPTY_FIELD, alignLeft);
                setCellValue(rowData, 1, data.getMeetingBegin(), alignLeft);
                setCellValue(rowData, 2, data.getMeetingName(), alignLeft);
                setCellValue(rowData, 3, data.getChairman(), alignCenter);
                setCellValue(rowData, 4, data.getOrganizerId(), alignCenter);
                setCellValue(rowData, 5, data.getMeetingQty(), alignCenter);
                setCellValue(rowData, 6, data.getFoodName().length() > 1 ? data.getFoodName().substring(2) : "", alignLeft); //餐點_數量
                setCellValue(rowData, 7, data.getItemName().length() > 1 ? data.getItemName().substring(2) : "", alignLeft); //設備
            }
            rowCount++;
        }
    }
}
