package tw.gov.pcc.eip.report;

import com.iisigroup.easyreport.xls.XlsReport;
import org.apache.poi.hssf.usermodel.HSSFPrintSetup;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import tw.gov.pcc.eip.common.cases.Eip00w520Case;

import java.util.List;

/**
 * 意見調查填寫內容
 * 
 * @author Weith
 */
public class Eip00w520L01 extends XlsReport {

	private Sheet sheet;
	private Font kai20Font;
	private Font kai12Font;
    private CellStyle headerCenterStyle;
	private CellStyle bodyLeftStyle;
    private CellStyle bodyLeftNoBorderStyle;
    private CellStyle bodyRightStyle;
    private CellStyle bodyCenterStyle;
    private CellStyle bodyLastStyle;

    public Eip00w520L01(List<Integer> lengthList) {
        super(false);
        sheet = createSheet("意見調查填寫內容");
        HSSFPrintSetup printSetup = (HSSFPrintSetup) sheet.getPrintSetup(); // 設定橫印
        printSetup.setLandscape(true); // 預設列印紙張尺寸
        printSetup.setPaperSize(HSSFPrintSetup.A4_PAPERSIZE);
        sheet.setColumnWidth(0, 10 * 256);
        for (int i = 0; i < lengthList.size(); i++) {
            sheet.setColumnWidth(i + 1, (lengthList.get(i) * 2 + 6) * 256);
        }
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
        List<String> titleList = caseData.getWriteContentTitle();
        Row headerRow = createRow(sheet, 0, 5, headerCenterStyle);
        //合併，index由0開始
        headerRow.getSheet().addMergedRegion(new CellRangeAddress(0, 0, 0, 4));
        setCellValue(headerRow, 0, "意見調查填寫內容", headerCenterStyle);
        Row headerRow2 = createRow(sheet, 2, 5, bodyLeftNoBorderStyle);
        setCellValue(headerRow2,0,"主題：", bodyLeftNoBorderStyle);
        setCellValue(headerRow2,1,caseData.getThemeCase().getTopicname(), bodyLeftNoBorderStyle);
        Row headerRow3 = createRow(sheet, 3, 5, bodyLeftNoBorderStyle);
        setCellValue(headerRow3,0,"填寫時間：", bodyLeftNoBorderStyle);
        setCellValue(headerRow3,1,caseData.getThemeCase().getFullosfmdt()+" ~ "+caseData.getThemeCase().getFullosendt(), bodyLeftNoBorderStyle);
        Row tableHeaderRow = createRow(sheet, 5, titleList.size(), bodyCenterStyle);
        for (int i = 0; i < titleList.size(); i++) {
            setCellValue(tableHeaderRow, i, titleList.get(i), bodyCenterStyle);
        }
    }

    /**
     * 增加內容
     * @param
     */
    public void addContent(Eip00w520Case caseData) {
        List<List<String>> list = caseData.getWriteContentData();
        for (int i = 0; i < list.size(); i++) {
            Row row = createRow(sheet, i+6, list.get(i).size()+1, bodyCenterStyle);
            setCellValue(row, 0, String.valueOf(i+1), bodyCenterStyle);
            for (int j = 0; j < list.get(i).size(); j++) {
                setCellValue(row, j+1, list.get(i).get(j), bodyLeftStyle);
            }
        }

    }

}
