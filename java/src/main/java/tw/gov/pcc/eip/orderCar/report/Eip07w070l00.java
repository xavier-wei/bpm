package tw.gov.pcc.eip.orderCar.report;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.iisigroup.easyreport.pdf.exception.ReportException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfPTable;

import tw.gov.pcc.eip.domain.CarBooking;
import tw.gov.pcc.eip.framework.report.PdfReportBase;
import tw.gov.pcc.eip.orderCar.cases.Eip07w070Case;
import tw.gov.pcc.eip.util.DateUtility;
import tw.gov.pcc.eip.util.ExceptionUtility;

/**
 * 派車記錄查詢及列印作業Report
 * @author Ivy
 */
public class Eip07w070l00 extends PdfReportBase {
	
	private static Log log = LogFactory.getLog(Eip07w070l00.class);
	private int titleFontSize = 18;	   // 標題大小
    private int subTitleFontSizeMid = 12;  // 子標題大小(中型)
    private PdfPTable table;
	
    public Eip07w070l00() throws ReportException {
        super();
    }

    public Eip07w070l00(String outputFilename) throws ReportException {
        super(outputFilename);

    }
	
    @Override
    protected Document createDocument() {
        // 參數說明: Document(頁面大小, 左邊空白, 右邊空白, 上面空白, 下面空白)
        return new Document(PageSize.A4, 20, 20, 20, 20);
    }
        
    public PdfPTable addHeader(Eip07w070Case caseData) throws DocumentException {
		document.newPage();
		PdfPTable table = new PdfPTable(100);
		table.setWidthPercentage(100f);
		if(StringUtils.isEmpty(caseData.getUsing_date_e())) {
        	int lastDay = DateUtility.lastDay(DateUtility.getNowChineseDate(),false);
        	caseData.setUsing_date_e(DateUtility.getNowChineseYearMonth()+String.valueOf(lastDay));
		}
		if("1".equals(caseData.getOrderCondition())){
			addCell(table, 100, 1, "派車記錄表(依用車日期排序)" , titleFontSize, 0, CENTER);
			addCell(table, 100, 1, "用車日期："+caseData.getUsing_date_s() +"至"+caseData.getUsing_date_e() , subTitleFontSizeMid, 0, LEFT);
			addCellAssignVAlignmentAndAllPadding(table, 18, 1, "派車單號", subTitleFontSizeMid, 1, CENTER, MIDDLE, 0, 3);
			addCellAssignVAlignmentAndAllPadding(table, 16, 1, "用車日期起迄\r\n用車時間起迄", subTitleFontSizeMid, 1, CENTER, MIDDLE, 0, 3);
			addCellAssignVAlignmentAndAllPadding(table, 10, 1, "車牌號碼", subTitleFontSizeMid, 1, CENTER, MIDDLE, 0, 3);
			addCellAssignVAlignmentAndAllPadding(table, 12, 1, "駕駛人姓名", subTitleFontSizeMid, 1, CENTER, MIDDLE, 0, 3);
			addCellAssignVAlignmentAndAllPadding(table, 44, 1, "用車事由", subTitleFontSizeMid, 1, CENTER, MIDDLE, 0, 3);
		}
		
		if("2".equals(caseData.getOrderCondition())){
			addCell(table, 100, 1, "派車記錄表(依車牌號碼排序)" , titleFontSize, 0, CENTER);
			addCell(table, 100, 1, "用車日期："+caseData.getUsing_date_s() +"至"+caseData.getUsing_date_e() , subTitleFontSizeMid, 0, LEFT);
			addCellAssignVAlignmentAndAllPadding(table, 10, 1, "車牌號碼", subTitleFontSizeMid, 1, CENTER, MIDDLE, 0, 3);
			addCellAssignVAlignmentAndAllPadding(table, 18, 1, "派車單號", subTitleFontSizeMid, 1, CENTER, MIDDLE, 0, 3);
			addCellAssignVAlignmentAndAllPadding(table, 16, 1, "用車日期起迄\r\n用車時間起迄", subTitleFontSizeMid, 1, CENTER, MIDDLE, 0, 3);
			addCellAssignVAlignmentAndAllPadding(table, 12, 1, "駕駛人姓名", subTitleFontSizeMid, 1, CENTER, MIDDLE, 0, 3);
			addCellAssignVAlignmentAndAllPadding(table, 44, 1, "用車事由", subTitleFontSizeMid, 1, CENTER, MIDDLE, 0, 3);
		}
        return table;
    }
    
    /**
     * 插入 資料列
     * @param vo
     * @throws Exception
     */
    public void addDataRow(CarBooking item,Eip07w070Case caseData) throws Exception  {
    	if("1".equals(caseData.getOrderCondition())){
            addCellAssignVAlignmentAndAllPadding(table, 18, 1, item.getApplyid(), subTitleFontSizeMid, 1, CENTER, MIDDLE, 0, 3);
            addCellAssignVAlignmentAndAllPadding(table, 16, 1, DateUtility.changeDateType(item.getUsing_date())+"\r\n"+
            		item.getUsing_time_s()+"~"+item.getUsing_time_e(), subTitleFontSizeMid, 1, CENTER, MIDDLE, 0, 3);
            addCellAssignVAlignmentAndAllPadding(table, 10, 1, item.getCarno1()+"-"+item.getCarno2(), subTitleFontSizeMid, 1, CENTER, MIDDLE, 0, 3);
            addCellAssignVAlignmentAndAllPadding(table, 12, 1, item.getName(), subTitleFontSizeMid, 1, CENTER, MIDDLE, 0, 3);
            addCellAssignVAlignmentAndAllPadding(table, 44, 1, item.getApply_memo(), subTitleFontSizeMid, 1, LEFT, MIDDLE, 0, 3);
    	}
    	
    	if("2".equals(caseData.getOrderCondition())){
    		addCellAssignVAlignmentAndAllPadding(table, 10, 1, item.getCarno1()+"-"+item.getCarno2(), subTitleFontSizeMid, 1, CENTER, MIDDLE, 0, 3);
            addCellAssignVAlignmentAndAllPadding(table, 18, 1, item.getApplyid(), subTitleFontSizeMid, 1, CENTER, MIDDLE, 0, 3);
            addCellAssignVAlignmentAndAllPadding(table, 16, 1, DateUtility.changeDateType(item.getUsing_date())+"\r\n"+
            		item.getUsing_time_s()+"~"+item.getUsing_time_e(), subTitleFontSizeMid, 1, CENTER, MIDDLE, 0, 3);
            addCellAssignVAlignmentAndAllPadding(table, 12, 1, item.getName(), subTitleFontSizeMid, 1, CENTER, MIDDLE, 0, 3);
            addCellAssignVAlignmentAndAllPadding(table, 44, 1, item.getApply_memo(), subTitleFontSizeMid, 1, LEFT, MIDDLE, 0, 3);
    	}

    }
    
    
    public void createPdf(Eip07w070Case caseData) throws Exception  {
        try {
            log.info("執行 派車記錄查詢及列印作業 PDF...");
                document.open();

                for(int i=1; i<=caseData.getDataList().size(); i++) {
                	CarBooking item = caseData.getDataList().get(i-1);
                	if(i==1) {
                		table = addHeader(caseData);
                	}
                	
                	if(i%38==0 && i!=caseData.getDataList().size()) {
                		document.add(table);
                		table = addHeader(caseData);
                	}
                	addDataRow(item,caseData);
                }
                
                document.add(table);
              
        } catch (Exception e) {
            log.debug("產生 派車記錄查詢及列印作業 PDF失敗" + ExceptionUtility.getStackTrace(e));
        } finally {
            if (document.isOpen()) {
                document.close();
        	}
        }
    }

}