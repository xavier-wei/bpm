package tw.gov.pcc.eip.common.report;

import com.iisigroup.easyreport.pdf.exception.ReportException;
import com.iisigroup.easyreport.xls.XlsReport;
import org.apache.poi.hssf.usermodel.HSSFPrintSetup;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import tw.gov.pcc.eip.common.cases.Eip03w040Case;
import tw.gov.pcc.eip.util.DateUtility;

import java.util.List;

/**
 * 件數統計 excel
 *
 * @author 2201009
 *
 */
public class Eip03w040l00xls extends XlsReport {

    private Sheet sheet;

    private final CellStyle titleStyle;
    private final CellStyle alignLeft;
//    private final CellStyle alignRight;
    private final CellStyle alignCenter;
//    private final CellStyle alignThinLeft;
//    private final CellStyle alignThinRight;
    private final CellStyle alignCenterTitle;
//    private final CellStyle alignCenterHeader;
//    private final CellStyle alignTopBorder;

    // 設定欄位數量
    private static final int cellAmountSheet = 8;

    // 設定框線
    private static final short borderWidth = 1;

    public Eip03w040l00xls() throws ReportException {
        super(false);
    }
    
    {
        // 設定字體
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 12);
        font.setFontName("標楷體");

        Font boldFont = workbook.createFont();
        boldFont.setFontHeightInPoints((short) 12);
        boldFont.setBold(true);
        boldFont.setFontName("標楷體");

        Font titleFont = workbook.createFont();
        titleFont.setFontHeightInPoints((short) 12);
        titleFont.setBold(true);
        titleFont.setFontName("標楷體");

        // 設定 title 格式
        titleStyle = workbook.createCellStyle();
        titleStyle.setAlignment(HorizontalAlignment.CENTER);
        titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        titleStyle.setFont(titleFont);

        // 設定 置中對齊 表頭
//        alignCenterHeader = workbook.createCellStyle();
//        alignCenterHeader.setAlignment(HorizontalAlignment.CENTER);
//        alignCenterHeader.setVerticalAlignment(VerticalAlignment.CENTER);
//        alignCenterHeader.setBorderBottom(BorderStyle.DOUBLE);
//        alignCenterHeader.setBorderTop(BorderStyle.valueOf(borderWidth));
//        alignCenterHeader.setFont(boldFont);
        // 設定 Top Border 表頭
//        alignTopBorder = workbook.createCellStyle();
//        alignTopBorder.setBorderTop(BorderStyle.valueOf(borderWidth));

        // 設定 靠左對齊文字 粗體 格式
        alignLeft = workbook.createCellStyle();
        alignLeft.setAlignment(HorizontalAlignment.LEFT);
        alignLeft.setVerticalAlignment(VerticalAlignment.CENTER);
        alignLeft.setBorderLeft(BorderStyle.valueOf(borderWidth));
        alignLeft.setBorderRight(BorderStyle.valueOf(borderWidth));
        alignLeft.setBorderBottom(BorderStyle.valueOf(borderWidth));
        alignLeft.setBorderTop(BorderStyle.valueOf(borderWidth));
        alignLeft.setFont(font);
        alignLeft.setWrapText(true);

        // 設定 靠左對齊文字 格式
//        alignThinLeft = workbook.createCellStyle();
//        alignThinLeft.setAlignment(HorizontalAlignment.LEFT);
//        alignThinLeft.setVerticalAlignment(VerticalAlignment.CENTER);
//        alignThinLeft.setFont(font);

        // 設定 靠右對齊 粗體 數字格式
//        alignRight = workbook.createCellStyle();
//        alignRight.setAlignment(HorizontalAlignment.RIGHT);
//        alignRight.setVerticalAlignment(VerticalAlignment.CENTER);
//        alignRight.setFont(boldFont);

        // 設定 靠右對齊 數字格式
//        alignThinRight = workbook.createCellStyle();
//        alignThinRight.setAlignment(HorizontalAlignment.RIGHT);
//        alignThinRight.setVerticalAlignment(VerticalAlignment.CENTER);
//        alignThinRight.setFont(font);

        // 設定 置中標題文字 + 下邊框 格式
        alignCenterTitle = workbook.createCellStyle();
        alignCenterTitle.setAlignment(HorizontalAlignment.CENTER);
        alignCenterTitle.setVerticalAlignment(VerticalAlignment.CENTER);
        alignCenterTitle.setBorderLeft(BorderStyle.valueOf(borderWidth));
        alignCenterTitle.setBorderRight(BorderStyle.valueOf(borderWidth));
        alignCenterTitle.setBorderBottom(BorderStyle.valueOf(borderWidth));
        alignCenterTitle.setBorderTop(BorderStyle.valueOf(borderWidth));
        alignCenterTitle.setFont(boldFont);

        // 設定 置中對齊文字 格式
        alignCenter = workbook.createCellStyle();
        alignCenter.setAlignment(HorizontalAlignment.CENTER);
        alignCenter.setVerticalAlignment(VerticalAlignment.CENTER);
        alignCenter.setBorderLeft(BorderStyle.valueOf(borderWidth));
        alignCenter.setBorderRight(BorderStyle.valueOf(borderWidth));
        alignCenter.setBorderBottom(BorderStyle.valueOf(borderWidth));
        alignCenter.setBorderTop(BorderStyle.valueOf(borderWidth));
        alignCenter.setFont(font);
    }

    /**
     * 產生 excel 檔案
     *
     * @param status 結案狀態
     * @param resultList 資料參數
     */
    public void createXls(String status, List<Eip03w040Case> resultList) {
        createSheet(status, resultList);
    }

    /**
     * 產生頁籤
     * @param status 結案狀態
     * @param resultList 統計資料 list
     */
    public void createSheet(String status, List<Eip03w040Case> resultList) {
        // 設定列印格式
        this.sheet = createSheet(DateUtility.getNowChineseDate());
        PrintSetup printSetup = sheet.getPrintSetup();
        printSetup.setLandscape(true);
        printSetup.setPaperSize(HSSFPrintSetup.A4_PAPERSIZE);

        // 建立表頭
        Row row0 = createRow(sheet, 0, cellAmountSheet, titleStyle);
        row0.getSheet().addMergedRegion(new CellRangeAddress(0, 0, 0, 7));
        row0.setHeightInPoints(20f);
        setCellValue(row0, 0, status + "件數統計" , titleStyle);

        // 報表資料標題
        Row row1 = createRow(sheet, 1, cellAmountSheet, alignCenterTitle);
        row1.setHeightInPoints(20f);
        setColumnWidth(sheet, 0, 14);
        setColumnWidth(sheet, 1, 18);
        setColumnWidth(sheet, 2, 25);
        setColumnWidth(sheet, 3, 14);
        setColumnWidth(sheet, 4, 10);
        setColumnWidth(sheet, 5, 25);
        setColumnWidth(sheet, 6, 12);
        setColumnWidth(sheet, 7, 12);

        setCellValue(row1, 0, "列管編號", alignCenterTitle);
        setCellValue(row1, 1, "交辦來源", alignCenterTitle);
        setCellValue(row1, 2, "列管事項", alignCenterTitle);
        setCellValue(row1, 3, "處室", alignCenterTitle);
        setCellValue(row1, 4, "處理狀態", alignCenterTitle);
        setCellValue(row1, 5, "辦理情形", alignCenterTitle);
        setCellValue(row1, 6, "列管迄日", alignCenterTitle);
        setCellValue(row1, 7, "解列日期", alignCenterTitle);

        int rowCount = 2;
//        int index = 1;
        for (Eip03w040Case data : resultList) {
            Row row = createRow(sheet, rowCount, cellAmountSheet, alignLeft);
            row.setHeightInPoints(33f);
            setCellValue(row, 0, data.getTrkId() != null? data.getTrkId() : "", alignCenter);
            setCellValue(row, 1, data.getTrkFrom() != null? data.getTrkFrom() : "", alignLeft);
            setCellValue(row, 2, data.getTrkCont() != null? data.getTrkCont() : "", alignLeft);
            setCellValue(row, 3, data.getDept_name() != null? data.getDept_name() : "", alignCenter);
            setCellValue(row, 4, data.getPrcSts() != null?  data.getPrcSts() : "", alignCenter);
            setCellValue(row, 5, data.getRptCont() != null? data.getRptCont() : "", alignLeft);
            String endDt = DateUtility.changeDateTypeToChineseDate(data.getEndDt());
            endDt = DateUtility.formatChineseDateString(endDt, false);
            setCellValue(row, 6, data.getEndDt() != null? endDt : "", alignCenter);
//            解列日期：固定文字，若KeepTrkDtl.SupAgree為Y，則KeepTrkDtl.SupDt，否則為空。
            if(data.getSupAgree().equals("Y") && data.getFmtSupDt() != null){
                String fmtSupDt = DateUtility.changeDateTypeToChineseDate(data.getFmtSupDt());
                fmtSupDt = DateUtility.formatChineseDateString(fmtSupDt, false);
//                String fmtSupDt = data.getFmtSupDt() != null? DateUtility.formatChineseDateString(data.getFmtSupDt(), false) : "";
                setCellValue(row, 7, fmtSupDt, alignCenter);
            }else {
                setCellValue(row, 7, "", alignCenter);
            }
            //印完地點後換行
            rowCount++;
        }
    }
}
