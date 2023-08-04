package tw.gov.pcc.eip.report;

import com.iisigroup.easyreport.xls.XlsReport;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.poi.hssf.usermodel.HSSFPrintSetup;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import tw.gov.pcc.common.util.DateUtil;
import tw.gov.pcc.eip.domain.Orclass;
import tw.gov.pcc.eip.domain.Orformdata;
import tw.gov.pcc.eip.domain.Orresult;
import tw.gov.pcc.eip.util.NumberUtility;

import java.time.LocalDateTime;
import java.time.chrono.MinguoChronology;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * 報名資料
 * 
 * @author Weith
 */
public class Eip00w420L02 extends XlsReport {

	private Sheet sheet;
	private Font ming18Font;
	private Font ming12Font;
	private CellStyle bodyLeftStyle;
    private CellStyle bodyRightStyle;
    private CellStyle bodyCenterStyle;

    DateTimeFormatter minguoformatter = DateTimeFormatter.ofPattern("yyy/MM/dd HH:mm:ss")
            .withChronology(MinguoChronology.INSTANCE)
            .withLocale(Locale.TAIWAN);

	public Eip00w420L02() {
		super(false);
		sheet = createSheet("");
        HSSFPrintSetup printSetup = (HSSFPrintSetup) sheet.getPrintSetup(); // 設定橫印
        printSetup.setLandscape(true); // 預設列印紙張尺寸
        printSetup.setPaperSize(HSSFPrintSetup.A4_PAPERSIZE);
        
        sheet.setColumnWidth(0, 11 * 256);// 報名序號
        sheet.setColumnWidth(1, 13 * 256);// 身分證號
        sheet.setColumnWidth(2, 13 * 256);// 報名資格
        sheet.setColumnWidth(3, 13 * 256);// 狀態
        sheet.setColumnWidth(4, 13 * 256);// 姓名
        sheet.setColumnWidth(5, 13 * 256);// 生日
        sheet.setColumnWidth(6, 22 * 256);// E-mail
        sheet.setColumnWidth(7, 11 * 256);// 性別
        sheet.setColumnWidth(8, 13 * 256);// 電話
        sheet.setColumnWidth(9, 13 * 256);// 傳真
        sheet.setColumnWidth(10, 13 * 256);// 職稱
        sheet.setColumnWidth(11, 13 * 256);// 公司全銜
        sheet.setColumnWidth(12, 13 * 256);// 部門名稱
        sheet.setColumnWidth(13, 22 * 256);// 聯絡地址
        sheet.setColumnWidth(14, 11 * 256);// 用餐狀況
        sheet.setColumnWidth(15, 13 * 256);// 報名方式
        sheet.setColumnWidth(16, 11 * 256);// 繳費情形
        sheet.setColumnWidth(17, 20 * 256);// 報名日期
        sheet.setColumnWidth(18, 20 * 256);// 上次通知日期
	}
	
	@Override
	public void initFont(Workbook workbook) {
		super.initFont(workbook);
		ming12Font = workbook.createFont();
		ming12Font.setFontHeightInPoints((short) 12);
		ming12Font.setFontName("新細明體");
		ming12Font.setColor(Font.COLOR_NORMAL);
	}

	@Override
    public void initCellStyle(Workbook workbook) {
        super.initCellStyle(workbook);
        bodyLeftStyle = workbook.createCellStyle();
        bodyLeftStyle.setAlignment(HorizontalAlignment.LEFT);
        bodyLeftStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        bodyLeftStyle.setBorderTop(BorderStyle.NONE);
        bodyLeftStyle.setBorderBottom(BorderStyle.NONE);
        bodyLeftStyle.setBorderLeft(BorderStyle.NONE);
        bodyLeftStyle.setBorderRight(BorderStyle.NONE);
        bodyLeftStyle.setFont(ming12Font);

        bodyRightStyle = workbook.createCellStyle();
        bodyRightStyle.setAlignment(HorizontalAlignment.RIGHT);
        bodyRightStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        bodyRightStyle.setBorderTop(BorderStyle.NONE);
        bodyRightStyle.setBorderBottom(BorderStyle.NONE);
        bodyRightStyle.setBorderLeft(BorderStyle.NONE);
        bodyRightStyle.setBorderRight(BorderStyle.NONE);
        bodyRightStyle.setFont(ming12Font);

        bodyCenterStyle = workbook.createCellStyle();
        bodyCenterStyle.setAlignment(HorizontalAlignment.CENTER);
        bodyCenterStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        bodyCenterStyle.setBorderTop(BorderStyle.NONE);
        bodyCenterStyle.setBorderBottom(BorderStyle.NONE);
        bodyCenterStyle.setBorderLeft(BorderStyle.NONE);
        bodyCenterStyle.setBorderRight(BorderStyle.NONE);
        bodyCenterStyle.setFont(ming12Font);
//        DataFormat format= workbook.createDataFormat();
//        bodyRightValueStyle.setDataFormat(format.getFormat("#,##0"));
    }
	
	/**
	 * 增加標題與表頭
	 */
    public void addHeader(Orclass orclass, Orformdata orformdata, List<Orresult> list) {
        Row HeaderRow = createRow(sheet, 0, 15, bodyLeftStyle);
        //合併，index由0開始
        HeaderRow.getSheet().addMergedRegion(new CellRangeAddress(0, 0, 0, 3));
        setCellValue(HeaderRow, 0, "活動分類：" + StringUtils.defaultIfEmpty(orclass.getOrcname(), ""), bodyLeftStyle);
        HeaderRow.getSheet().addMergedRegion(new CellRangeAddress(0, 0, 4, 9));
        setCellValue(HeaderRow, 4, "活動名稱：" + StringUtils.defaultIfEmpty(orformdata.getTopicname(), ""), bodyLeftStyle);
        HeaderRow.getSheet().addMergedRegion(new CellRangeAddress(0, 0, 10, 14));
        setCellValue(HeaderRow, 10, "匯出日期：" + LocalDateTime.now().format(minguoformatter), bodyLeftStyle);

        Row HeaderRow2 = createRow(sheet, 1, 10, bodyLeftStyle);
        HeaderRow2.getSheet().addMergedRegion(new CellRangeAddress(1, 1, 0, 3));
        setCellValue(HeaderRow2, 0, "期別：" + StringUtils.defaultIfEmpty(String.valueOf(orformdata.getPeriod()), ""), bodyLeftStyle);
        HeaderRow2.getSheet().addMergedRegion(new CellRangeAddress(1, 1, 4, 9));
        setCellValue(HeaderRow2, 4, "班別：" + StringUtils.defaultIfEmpty(orformdata.getClasscode(), ""), bodyLeftStyle);

        Row HeaderRow3 = createRow(sheet, 2, 10, bodyLeftStyle);
        HeaderRow3.getSheet().addMergedRegion(new CellRangeAddress(2, 2, 0, 3));
        setCellValue(HeaderRow3, 0, "報名時間：" + orformdata.getRegisfmdt().format(minguoformatter) + " ~ " + orformdata.getRegisendt().format(minguoformatter), bodyLeftStyle);
        HeaderRow3.getSheet().addMergedRegion(new CellRangeAddress(2, 2, 4, 9));
        setCellValue(HeaderRow3, 4, "活動時間：" + orformdata.getProfmdt().format(minguoformatter) + " ~ " + orformdata.getProendt().format(minguoformatter), bodyLeftStyle);

        Row HeaderRow4 = createRow(sheet, 3, 10, bodyLeftStyle);
        HeaderRow4.getSheet().addMergedRegion(new CellRangeAddress(3, 3, 0, 3));
        setCellValue(HeaderRow4, 0, "接受報名人數：" + orformdata.getAcceptappnum() + "人", bodyLeftStyle);
        HeaderRow4.getSheet().addMergedRegion(new CellRangeAddress(3, 3, 4, 9));
        setCellValue(HeaderRow4, 4, "目前報名人數："+ (CollectionUtils.isNotEmpty(list) ? list.size() : "0") + "人", bodyLeftStyle);

        Row HeaderRow6 = createRow(sheet, 5, 19, bodyCenterStyle);
        setCellValue(HeaderRow6, 0, "報名序號", bodyCenterStyle);
        setCellValue(HeaderRow6, 1, "身分證號", bodyCenterStyle);
        setCellValue(HeaderRow6, 2, "報名資格", bodyCenterStyle);
        setCellValue(HeaderRow6, 3, "狀態", bodyCenterStyle);
        setCellValue(HeaderRow6, 4, "姓名", bodyCenterStyle);
        setCellValue(HeaderRow6, 5, "生日", bodyCenterStyle);
        setCellValue(HeaderRow6, 6, "Email", bodyCenterStyle);
        setCellValue(HeaderRow6, 7, "性別", bodyCenterStyle);
        setCellValue(HeaderRow6, 8, "電話", bodyCenterStyle);
        setCellValue(HeaderRow6, 9, "傳真", bodyCenterStyle);
        setCellValue(HeaderRow6, 10, "職稱", bodyCenterStyle);
        setCellValue(HeaderRow6, 11, "公司全銜", bodyCenterStyle);
        setCellValue(HeaderRow6, 12, "部門名稱", bodyCenterStyle);
        setCellValue(HeaderRow6, 13, "聯絡地址", bodyCenterStyle);
        setCellValue(HeaderRow6, 14, "用餐狀況", bodyCenterStyle);
        setCellValue(HeaderRow6, 15, "報名方式", bodyCenterStyle);
        setCellValue(HeaderRow6, 16, "繳費情形", bodyCenterStyle);
        setCellValue(HeaderRow6, 17, "報名日期", bodyCenterStyle);
        setCellValue(HeaderRow6, 18, "上次通知日期", bodyCenterStyle);
    }

    /**
     * 增加內容
     * @param list
     */
    public void addContent(List<Orresult> list) {
        for (Orresult orresult : list) {
            List<Pair<Object, CellStyle>> metadata = new ArrayList<>();
            metadata.add(Pair.of(NumberUtility.toDouble(orresult.getSeqno()), bodyCenterStyle));
            metadata.add(Pair.of(orresult.getRegisidn(), bodyCenterStyle));
            metadata.add(Pair.of(orresult.getDept(), bodyLeftStyle));
            metadata.add(Pair.of(getStatus(orresult.getIspass()), bodyLeftStyle));
            metadata.add(Pair.of(orresult.getRegisname(), bodyLeftStyle));
            metadata.add(Pair.of(DateUtil.formatChineseDateString(orresult.getRegisbrth(),false), bodyCenterStyle));
            metadata.add(Pair.of(orresult.getRegisemail(), bodyLeftStyle));
            metadata.add(Pair.of("G".equals(orresult.getRegissex()) ? "男" : "女", bodyCenterStyle));
            metadata.add(Pair.of(orresult.getRegisphone(), bodyLeftStyle));
            metadata.add(Pair.of(orresult.getFax(), bodyLeftStyle));
            metadata.add(Pair.of(orresult.getJogtitle(), bodyLeftStyle));
            metadata.add(Pair.of(orresult.getCompany(), bodyLeftStyle));
            metadata.add(Pair.of(orresult.getDept(), bodyLeftStyle));
            metadata.add(Pair.of(orresult.getRegisaddres(), bodyLeftStyle));
            metadata.add(Pair.of(getMealStatus(orresult.getMealstatus()), bodyLeftStyle));
            metadata.add(Pair.of(getRegisway(orresult.getRegisway()), bodyLeftStyle));
            metadata.add(Pair.of("Y".equals(orresult.getIspass()) ? "是" : "否", bodyCenterStyle));
            metadata.add(Pair.of(ObjectUtils.isNotEmpty(orresult.getRegisdt()) ? orresult.getRegisdt().format(minguoformatter) : "", bodyCenterStyle));
            metadata.add(Pair.of(ObjectUtils.isNotEmpty(orresult.getLastnotidt()) ? orresult.getLastnotidt().format(minguoformatter) : "", bodyCenterStyle));

            Row row = sheet.createRow(sheet.getLastRowNum() + 1);
            for (int i = 0, l = metadata.size(); i < l; i++) {
                Pair<Object, CellStyle> pair = metadata.get(i);
                Cell cell = row.createCell(i);
                if(i==0){
                    cell.setCellValue((double)pair.getLeft());
                } else {
                    cell.setCellValue((String)pair.getLeft());
                }
                cell.setCellStyle(pair.getRight());
            }
        }
    }

    private String getStatus(String isPass) {
        if (isPass == null) {
            return "未審";
        } else if ("Y".equals(isPass)) {
            return "通過";
        } else if ("N".equals(isPass)) {
            return "未通過";
        } else if ("D".equals(isPass)) {
            return "取消報名";
        }
        return "";
    }

    private String getMealStatus(String mealStatus) {
        if ("N".equals(mealStatus)) {
            return "不用餐";
        } else if ("M".equals(mealStatus)) {
            return "用餐-葷";
        } else if ("V".equals(mealStatus)) {
            return "用餐-素";
        }
        return "";
    }

    private String getRegisway(String regisway) {
        if ("I".equals(regisway)) {
            return "網路";
        } else if ("E".equals(regisway)) {
            return "Email";
        } else if ("F".equals(regisway)) {
            return "傳真";
        } else if ("T".equals(regisway)) {
            return "電話";
        }
        return "";
    }
}
