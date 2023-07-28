package tw.gov.pcc.eip.apply.report;

import java.math.BigDecimal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.iisigroup.easyreport.pdf.exception.ReportException;
import com.iisigroup.easyreport.pdf.utility.NumberFormatUtility;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfPTable;

import tw.gov.pcc.eip.apply.cases.Eip08w050Case;
import tw.gov.pcc.eip.domain.Applyitem;
import tw.gov.pcc.eip.framework.report.PdfReportBase;

/**
 * 領物單紀錄查詢及列印作業(依品名顯示)Report
 * @author Ivy
 */
public class Eip08w050l01 extends PdfReportBase {
	
	private static Log log = LogFactory.getLog(Eip08w050l01.class);
	private int titleFontSize = 18;	   // 標題大小
    private int subTitleFontSizeMid = 12;  // 子標題大小(中型)
    private PdfPTable table;
	
    public Eip08w050l01() throws ReportException {
        super();
    }

    public Eip08w050l01(String outputFilename) throws ReportException {
        super(outputFilename);

    }
	
    @Override
    protected Document createDocument() {
        // 參數說明: Document(頁面大小, 左邊空白, 右邊空白, 上面空白, 下面空白)
        return new Document(PageSize.A4, 20, 20, 20, 20);
    }
        
    public PdfPTable addHeader(Eip08w050Case caseData) throws DocumentException {
		document.newPage();
		PdfPTable table = new PdfPTable(100);
		table.setWidthPercentage(100f);

        String yearMonth = caseData.getApplyYearMonth().substring(0, 3)+"年"+caseData.getApplyYearMonth().substring(3, 5)+"月";
        addCell(table, 100, 1, yearMonth+"領物單核發數量統計表(依品名顯示)" , titleFontSize, 0, CENTER);
        addCellAssignVAlignmentAndAllPadding(table, 34, 1, "品名大類", subTitleFontSizeMid, 1, CENTER, MIDDLE, 0, 3);
        addCellAssignVAlignmentAndAllPadding(table, 33, 1, "品名", subTitleFontSizeMid, 1, CENTER, MIDDLE, 0, 3);
        addCellAssignVAlignmentAndAllPadding(table, 33, 1, "核發數量", subTitleFontSizeMid, 1, CENTER, MIDDLE, 0, 3);
        return table;
    }
    

    
    /**
     * 插入 資料列
     * @param vo
     * @throws Exception
     */
    public void addDataRow(Applyitem applyitem) throws Exception  {
        addCellAssignVAlignmentAndAllPadding(table, 34, 1, applyitem.getItemkind_nm(), subTitleFontSizeMid, 1, LEFT, MIDDLE, 0, 3);
        addCellAssignVAlignmentAndAllPadding(table, 33, 1, applyitem.getItemno_nm(), subTitleFontSizeMid, 1, LEFT, MIDDLE, 0, 3);
        addCellAssignVAlignmentAndAllPadding(table, 33, 1, formatAmt(applyitem.getApprove_cnt()), subTitleFontSizeMid, 1, RIGHT, MIDDLE, 0, 3);
    }
    
    
    public void createPdf(Eip08w050Case caseData) throws Exception  {
        try {
            log.info("執行 領物單紀錄查詢及列印作業(依品名顯示) PDF...");
                document.open();

            
                for(int i=1; i<=caseData.getItemList().size(); i++) {
                	Applyitem item = caseData.getItemList().get(i-1);
                	if(i==1) {
                		table = addHeader(caseData);
                	}
                	
                	if(i%38==0 && i!=caseData.getItemList().size()) {
                		document.add(table);
                		table = addHeader(caseData);
                	}
                	addDataRow(item);
                }
                
                document.add(table);
              
        } catch (Exception e) {
            log.debug("產生 領物單紀錄查詢及列印作業 PDF失敗");
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