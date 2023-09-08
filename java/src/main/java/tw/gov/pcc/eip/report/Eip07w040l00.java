package tw.gov.pcc.eip.report;

import java.math.BigDecimal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.iisigroup.easyreport.pdf.exception.ReportException;
import com.iisigroup.easyreport.pdf.utility.NumberFormatUtility;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfPTable;

import tw.gov.pcc.eip.framework.report.PdfReportBase;
import tw.gov.pcc.eip.orderCar.cases.Eip07w040Case;
import tw.gov.pcc.eip.report.vo.Eip07w040L_Vo;

/**
 * 秘書處進行派車作業Report
 * @author Ivy
 */
public class Eip07w040l00 extends PdfReportBase {
	
	private static Log log = LogFactory.getLog(Eip07w040l00.class);
	private int titleFontSize = 18;	   // 標題大小
    private int subTitleFontSizeMid = 12;  // 子標題大小(中型)
	
    public Eip07w040l00() throws ReportException {
        super();
    }

    public Eip07w040l00(String outputFilename) throws ReportException {
        super(outputFilename);

    }
	
    @Override
    protected Document createDocument() {
        // 參數說明: Document(頁面大小, 左邊空白, 右邊空白, 上面空白, 下面空白)
        return new Document(PageSize.A4, 20, 20, 20, 20);
    }
        
    public PdfPTable addData(Eip07w040L_Vo vo) throws DocumentException {
		document.newPage();
		PdfPTable table = new PdfPTable(100);
		table.setWidthPercentage(100f);

        addCell(table, 100, 1, "派車預約單" , titleFontSize, 0, CENTER);
        addCell(table, 100, 4, EMPTY_FIELD , titleFontSize, 0, CENTER);
        
        addCell(table, 30, 4, "申請人："+vo.getApply_user() , subTitleFontSizeMid, 0, LEFT);
        addCell(table, 40, 4, "申請單位："+vo.getApply_dept() , subTitleFontSizeMid, 0, LEFT);
        addCell(table, 30, 4, "申請日期："+vo.getApply_date(), subTitleFontSizeMid, 0, LEFT);
        
        addCell(table, 100, 4, "用車事由："+vo.getApply_memo() , subTitleFontSizeMid, 0, LEFT);
        addCell(table, 100, 4, "目的地："+vo.getDestination() , subTitleFontSizeMid, 0, LEFT);
        
        addCell(table, 100, 4, "人數："+vo.getNum_of_people() , subTitleFontSizeMid, 0, LEFT);
        
        addCell(table, 100, 4, "用車日期："+vo.getUsing_date(), subTitleFontSizeMid, 0, LEFT);
        addCell(table, 100, 4, "用車時間："+vo.getUsing_time_s() + "~" + vo.getUsing_time_e(), subTitleFontSizeMid, 0, LEFT);
        
        if("Y".equals(vo.getCombine_mk())) {
            addCell(table, 50, 4, "併單註記："+vo.getCombine_mk() , subTitleFontSizeMid, 0, LEFT);
            addCell(table, 50, 4, "併單派車單號："+vo.getCombine_applyid() , subTitleFontSizeMid, 0, LEFT);
            addCell(table, 100, 4, "併單原因："+vo.getCombine_reason(), subTitleFontSizeMid, 0, LEFT);
        }

        addCell(table, 100, 4, "派車單號："+vo.getApplyid() , subTitleFontSizeMid, 0, LEFT);
        addCell(table, 50, 4, "駕駛人姓名："+vo.getName() , subTitleFontSizeMid, 0, LEFT);
        addCell(table, 50, 4, "手機號碼："+vo.getCellphone() , subTitleFontSizeMid, 0, LEFT);
        
        addCell(table, 50, 4, "車牌車號："+vo.getCarno1()+"-"+vo.getCarno2() , subTitleFontSizeMid, 0, LEFT);
        addCell(table, 50, 4, "顏色："+vo.getCarcolor() , subTitleFontSizeMid, 0, LEFT);
        addCell(table, 100, 4, "車輛種類："+vo.getApply_car_type() , subTitleFontSizeMid, 0, LEFT);
        document.add(table);
        return table;
    }
    
    public void createPdf(Eip07w040Case caseData) throws Exception  {
        try {
            log.info("執行 秘書處進行派車作業 PDF...");
                document.open();

                for(Eip07w040L_Vo vo : caseData.getReportList()) {
                	addData(vo);
                }

              
        } catch (Exception e) {
            log.debug("產生 秘書處進行派車作業 PDF失敗");
            throw e;
        } finally {
            if (document.isOpen()) {
                document.close();
        	}
        }
    }
    
    /**
     * 格式化傳入的數值, 將其加入三位一撇傳回。若null值則傳回""
     * 
     * @param nNumber 欲格式化的數值
     * @return
     */
    public String formatAmt(BigDecimal nNumber) {
        if (nNumber != null) {
            return NumberFormatUtility.formatDecimal(nNumber,"#,###,##0");
        } else {
            return "";
        }
    }
    
    /**
     * 格式化傳入的數值, 將其加入三位一撇傳回。若null值則傳回""
     * 
     * @param nNumber 欲格式化的數值
     * @return
     */
    public String formatAmt(Integer nNumber) {
        if (nNumber != null) {
            return NumberFormatUtility.formatDecimal(nNumber,"#,###,##0");
        } else {
            return "";
        }
    }

}