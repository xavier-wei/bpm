package tw.gov.pcc.eip.report;

import com.iisigroup.easyreport.xls.XlsReport;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFPrintSetup;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import tw.gov.pcc.eip.common.cases.Eip00w520Case;

import java.util.*;

/**
 * 意見調查統計表
 * 
 * @author Weith
 */
public class Eip00w520L00 extends XlsReport {

	private Sheet sheet;
	private Font kai20Font;
	private Font kai12Font;
    private CellStyle headerCenterStyle;
	private CellStyle bodyLeftStyle;
    private CellStyle bodyLeftNoBorderStyle;
    private CellStyle bodyRightStyle;
    private CellStyle bodyCenterStyle;
    private CellStyle bodyLastStyle;

	public Eip00w520L00() {
		super(false);
		sheet = createSheet("意見調查統計表");
        HSSFPrintSetup printSetup = (HSSFPrintSetup) sheet.getPrintSetup(); // 設定橫印
        printSetup.setLandscape(true); // 預設列印紙張尺寸
        printSetup.setPaperSize(HSSFPrintSetup.A4_PAPERSIZE);
        
        sheet.setColumnWidth(0, 15 * 256);// 標題
        sheet.setColumnWidth(1, 30 * 256);// 題目
        sheet.setColumnWidth(2, 20 * 256);// 選項
        sheet.setColumnWidth(3, 14 * 256);// 得票數
        sheet.setColumnWidth(4, 14 * 256);// 得票率
	}
	
	@Override
	public void initFont(Workbook workbook) {
		super.initFont(workbook);
		kai12Font = workbook.createFont();
		kai12Font.setFontHeightInPoints((short) 12);
		kai12Font.setFontName("標楷體");
		kai12Font.setColor(Font.COLOR_NORMAL);
        kai20Font = workbook.createFont();
        kai20Font.setFontHeightInPoints((short) 20);
        kai20Font.setFontName("標楷體");
        kai20Font.setColor(Font.COLOR_NORMAL);
        kai20Font.setBold(true);
	}

	@Override
    public void initCellStyle(Workbook workbook) {
        super.initCellStyle(workbook);
        headerCenterStyle = workbook.createCellStyle();
        headerCenterStyle.setAlignment(HorizontalAlignment.CENTER);
        headerCenterStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        headerCenterStyle.setBorderTop(BorderStyle.NONE);
        headerCenterStyle.setBorderBottom(BorderStyle.NONE);
        headerCenterStyle.setBorderLeft(BorderStyle.NONE);
        headerCenterStyle.setBorderRight(BorderStyle.NONE);
        headerCenterStyle.setFont(kai20Font);

        bodyLeftStyle = workbook.createCellStyle();
        bodyLeftStyle.setAlignment(HorizontalAlignment.LEFT);
        bodyLeftStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        bodyLeftStyle.setBorderTop(BorderStyle.THIN);
        bodyLeftStyle.setBorderBottom(BorderStyle.THIN);
        bodyLeftStyle.setBorderLeft(BorderStyle.THIN);
        bodyLeftStyle.setBorderRight(BorderStyle.THIN);
        bodyLeftStyle.setFont(kai12Font);

        bodyLeftNoBorderStyle = workbook.createCellStyle();
        bodyLeftNoBorderStyle.setAlignment(HorizontalAlignment.LEFT);
        bodyLeftNoBorderStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        bodyLeftNoBorderStyle.setBorderTop(BorderStyle.NONE);
        bodyLeftNoBorderStyle.setBorderBottom(BorderStyle.NONE);
        bodyLeftNoBorderStyle.setBorderLeft(BorderStyle.NONE);
        bodyLeftNoBorderStyle.setBorderRight(BorderStyle.NONE);
        bodyLeftNoBorderStyle.setFont(kai12Font);

        bodyRightStyle = workbook.createCellStyle();
        bodyRightStyle.setAlignment(HorizontalAlignment.RIGHT);
        bodyRightStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        bodyRightStyle.setBorderTop(BorderStyle.THIN);
        bodyRightStyle.setBorderBottom(BorderStyle.THIN);
        bodyRightStyle.setBorderLeft(BorderStyle.THIN);
        bodyRightStyle.setBorderRight(BorderStyle.THIN);
        bodyRightStyle.setFont(kai12Font);

        bodyCenterStyle = workbook.createCellStyle();
        bodyCenterStyle.setAlignment(HorizontalAlignment.CENTER);
        bodyCenterStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        bodyCenterStyle.setBorderTop(BorderStyle.THIN);
        bodyCenterStyle.setBorderBottom(BorderStyle.THIN);
        bodyCenterStyle.setBorderLeft(BorderStyle.THIN);
        bodyCenterStyle.setBorderRight(BorderStyle.THIN);
        bodyCenterStyle.setFont(kai12Font);

        bodyLastStyle = workbook.createCellStyle();
        bodyLastStyle.setAlignment(HorizontalAlignment.LEFT);
        bodyLastStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        bodyLastStyle.setBorderTop(BorderStyle.THIN);
        bodyLastStyle.setBorderBottom(BorderStyle.MEDIUM);
        bodyLastStyle.setBorderLeft(BorderStyle.THIN);
        bodyLastStyle.setBorderRight(BorderStyle.THIN);
        bodyLastStyle.setFont(kai12Font);
    }
	
	/**
	 * 增加標題與表頭
	 */
    public void addHeader(Eip00w520Case caseData) {
        Row headerRow = createRow(sheet, 0, 5, headerCenterStyle);
        //合併，index由0開始
        headerRow.getSheet().addMergedRegion(new CellRangeAddress(0, 0, 0, 4));
        setCellValue(headerRow, 0, "意見調查統計表", headerCenterStyle);
        Row headerRow2 = createRow(sheet, 2, 5, bodyLeftNoBorderStyle);
        setCellValue(headerRow2,0,"主題：", bodyLeftNoBorderStyle);
        setCellValue(headerRow2,1,caseData.getThemeCase().getTopicname(), bodyLeftNoBorderStyle);
        Row headerRow3 = createRow(sheet, 3, 5, bodyLeftNoBorderStyle);
        setCellValue(headerRow3,0,"填寫時間：", bodyLeftNoBorderStyle);
        setCellValue(headerRow3,1,caseData.getThemeCase().getFullosfmdt()+" ~ "+caseData.getThemeCase().getFullosendt(), bodyLeftNoBorderStyle);
        Row tableHeaderRow = createRow(sheet, 5, 5, bodyCenterStyle);
        setCellValue(tableHeaderRow,0,"標題", bodyCenterStyle);
        setCellValue(tableHeaderRow,1,"題目", bodyCenterStyle);
        setCellValue(tableHeaderRow,2,"選項", bodyCenterStyle);
        setCellValue(tableHeaderRow,3,"得票數", bodyCenterStyle);
        setCellValue(tableHeaderRow,4,"得票率", bodyCenterStyle);

    }

    /**
     * 增加內容
     * @param
     */
    public void addContent(Eip00w520Case caseData) {
        List<Eip00w520Case.MixCase>reviews = caseData.getReviews();
        Map<String, Eip00w520Case.Statistics>multipleDataMap = caseData.getMultipleDataMap();
        Map<String,List<String>> textUiMap = caseData.getTextUiMap();
        Map<String, Eip00w520Case.Statistics>textDataMap = caseData.getTextDataMap();
        int rowindex = 6;
        for (int i = 0; i < reviews.size(); i++) {
            Eip00w520Case.MixCase c = reviews.get(i);
            if ("999".equals(c.getNo())) {
                String sectitle = c.getSectitle();
                String topic = c.getItemname();
                List<String>textList = textUiMap.get(c.getQseqno());
                for (String str : textList) {
                    Row row = createRow(sheet, rowindex, 5, bodyLeftStyle);
                    String key = c.getQseqno()+"-"+str;
                    setCellValue(row, 0, sectitle, bodyCenterStyle);
                    setCellValue(row, 1, topic, bodyLeftStyle);
                    setCellValue(row, 2, str, bodyLeftStyle);
                    setCellValue(row, 3, Double.valueOf(textDataMap.get(key).getCount()), bodyRightStyle);
                    setCellValue(row, 4, textDataMap.get(key).getRate(), bodyRightStyle);
                    rowindex++;
                }
            } else {
                Row row = createRow(sheet, rowindex, 5, bodyLeftStyle);
                setCellValue(row, 0, c.getSectitle(), bodyCenterStyle);
                setCellValue(row, 1, c.getTopic(), bodyLeftStyle);
                setCellValue(row, 2, c.getItemname(), bodyLeftStyle);
                setCellValue(row, 3, Double.valueOf(multipleDataMap.get(c.getNo()).getCount()), bodyRightStyle);
                setCellValue(row, 4, multipleDataMap.get(c.getNo()).getRate(), bodyRightStyle);
                rowindex++;
            }

        }
        mergeRows(sheet,0);
        mergeRows(sheet,1);
    }

//    private void mergeRows(Sheet sheet, int colIndex) {
//        int lastRowNum = sheet.getLastRowNum();
//        String preVal = "";
//        for (int rowIndex = 6; rowIndex < lastRowNum; rowIndex++) {
//            Row currentRow = sheet.getRow(rowIndex);
//            if (currentRow == null) {
//                break;
//            }
//            Cell currentCell = currentRow.getCell(colIndex);
//            if (StringUtils.equals(currentCell.getStringCellValue(), preVal)) {
//                continue;
//            }
//            sheet.addMergedRegion(new CellRangeAddress(currentRow.getRowNum(), currentRow.getRowNum() + mergeMap.get(currentCell.getStringCellValue())-1, colIndex, colIndex));
//            preVal = currentCell.getStringCellValue();
//        }
//    }

    /**
     * 合併列，將相同內容的儲存格合併
     * @param sheet
     * @param colIndex
     */
    private void mergeRows(Sheet sheet, int colIndex) {
        int lastRowNum = sheet.getLastRowNum()+1;
        int count = 0;
        String preVal = "";
        for (int rowIndex = 6; rowIndex < lastRowNum; rowIndex++) {
            Row currentRow = sheet.getRow(rowIndex);
            Row nextRow = sheet.getRow(rowIndex + 1);
            if (currentRow == null) {
                break;
            }
            Cell currentCell = currentRow.getCell(colIndex);
            if (nextRow == null) {
                if (count > 0) {
                    sheet.addMergedRegion(new CellRangeAddress(currentRow.getRowNum() - count, currentRow.getRowNum(), colIndex, colIndex));
                }
                break;
            }
            Cell nextCell = nextRow.getCell(colIndex);
            if (StringUtils.equals(currentCell.getStringCellValue(), nextCell.getStringCellValue())) {
                count++;
            } else {
                if (count > 0) {
                    sheet.addMergedRegion(new CellRangeAddress(currentRow.getRowNum() - count, currentRow.getRowNum(), colIndex, colIndex));
                }
                count = 0;
            }
            preVal = currentCell.getStringCellValue();
        }
    }

//    public static void main(String[] args) {
//        Eip00w520L00 eip00w520L00 = new Eip00w520L00();
////        eip00w520L00.addHeader();
//        eip00w520L00.addContent();
//        byte[] byteArray = eip00w520L00.getOutputStream().toByteArray();
//        String filePath = "D:/xlsDev/xls.xls";
//        try (FileOutputStream fileOutputStream = new FileOutputStream(new File(filePath))) {
//            fileOutputStream.write(byteArray);
//            System.out.println("File saved successfully to " + filePath);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}
