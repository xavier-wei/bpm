package tw.gov.pcc.eip.apply.report;


import com.iisigroup.easyreport.pdf.exception.ReportException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfPTable;
import org.apache.commons.lang3.StringUtils;
import tw.gov.pcc.eip.Global;
import tw.gov.pcc.eip.domain.CarBooking;
import tw.gov.pcc.eip.framework.report.PdfReportBase;
import tw.gov.pcc.eip.orderCar.cases.Eip07w020Case;


public class Eip07w020l00 extends PdfReportBase {
	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(Eip07w020l00.class);
	private static final float PAGEWIDTH = 100.0F;
	private static final int COLALL = 100;
	private static final int TITLE_FONT_SIZE = 22;

	private static final int SECOND_TITLE_FONT_SIZE = 14;//14

	private static final int SIZE_12 = 12;//12

	private PdfPTable table = new PdfPTable(COLALL); //新建table

	public Eip07w020l00() throws ReportException {
		super();
	}

	public Eip07w020l00(String outputFilename) throws ReportException {
		super(outputFilename);
	}

	@Override
	public Document createDocument() {
		// 參數說明: Document(頁面大小, 左邊空白, 右邊空白, 上面空白, 下面空白)
		return new Document(PageSize.A4, 15, 15, 50, 30);
	}

	@Override
	protected void initFont() throws ReportException {
		initFont(Global.CNS11643_KAI_FILENAME); // 自訂 BaseFont
	}

	/**
	 * 主程式
	 * @param carBooking
	 * @throws Exception
	 */
	public void createEip07w020DataPdf(CarBooking carBooking,Eip07w020Case caseData) throws Exception {
		try {
			document.open();
			table = new PdfPTable(COLALL); // 新建table
			table.setWidthPercentage(PAGEWIDTH);
			printSinglePage(carBooking,caseData);
			document.add(table);
		} catch (Exception e) {
			log.debug("產生 PDF檔失敗");
			throw e;
		} finally {
			if (document.isOpen()) document.close();
		}
	}

	/**
	 * 列印單頁:單一印表頁次
	 */
	private void printSinglePage(CarBooking carBooking,Eip07w020Case caseData) throws DocumentException {
		addHeader(carBooking,caseData);
		addApplyData(carBooking);
		if ("Y".equals(carBooking.getCombine_mk())){
			combineMk(carBooking);
		}
		addCarData(carBooking);
	}

	/**
	 * 報表HEADER
	 * @param carBooking
	 */
	private void addHeader(CarBooking carBooking,Eip07w020Case caswDate) {
		addCell(table, 100, 1, "派車預約單", TITLE_FONT_SIZE, 0, Element.ALIGN_CENTER);
		addCell(table, 30, 1, "程式代號: EIP07W020L", SECOND_TITLE_FONT_SIZE, 0, Element.ALIGN_LEFT);
		addCell(table, 70, 1, "", SECOND_TITLE_FONT_SIZE, 0, Element.ALIGN_LEFT);
		addCell(table, 30, 1, "申請人:"+caswDate.getRpApplyNm(), SECOND_TITLE_FONT_SIZE, 0, Element.ALIGN_LEFT);
		addCell(table, 40, 1, "申請單位:"+caswDate.getRpApplyUnitNm(), SECOND_TITLE_FONT_SIZE, 0, Element.ALIGN_LEFT);
		addCell(table, 30, 1, "申請日期:"+StringUtils.substring(carBooking.getApply_date(),0,3)+"/"+StringUtils.substring(carBooking.getApply_date(),3,5)+"/"+StringUtils.substring(carBooking.getApply_date(),5), SECOND_TITLE_FONT_SIZE, 0, Element.ALIGN_LEFT);
		addCell(table, 100, 1, EMPTY_FIELD, SIZE_12, 0, Element.ALIGN_CENTER); //把表格推下去一些
	}

	private void addApplyData(CarBooking caseData) {
		addCell(table, 100, 1, "[申請相關資料]:", SECOND_TITLE_FONT_SIZE, 0, Element.ALIGN_LEFT);
		addCell(table, 100, 1, "用車事由:"+caseData.getApply_memo(), SECOND_TITLE_FONT_SIZE, 0, Element.ALIGN_LEFT);
		addCell(table, 100, 1, "目的地:"+caseData.getDestination(), SECOND_TITLE_FONT_SIZE, 0, Element.ALIGN_LEFT);
		addCell(table, 30, 1, "車輛總類:"+ carty(caseData.getApply_car_type()), SECOND_TITLE_FONT_SIZE, 0, Element.ALIGN_LEFT);
		addCell(table, 70, 1, "人數:"+caseData.getNum_of_people()+"人", SECOND_TITLE_FONT_SIZE, 0, Element.ALIGN_LEFT);
		addCell(table, 100, 1, "用車日期:"+StringUtils.substring(caseData.getUsing_date(),0,3)+"/"+StringUtils.substring(caseData.getUsing_date(),3,5)+"/"+StringUtils.substring(caseData.getUsing_date(),5), SECOND_TITLE_FONT_SIZE, 0, Element.ALIGN_LEFT);
		addCell(table, 100, 1, "用車時間(起):  "+ StringUtils.substring(caseData.getUsing_time_s(),0,2)+":"+StringUtils.substring(caseData.getUsing_time_s(),2), SECOND_TITLE_FONT_SIZE, 0, Element.ALIGN_LEFT);
		addCell(table, 100, 1, "        (迄):  "+StringUtils.substring(caseData.getUsing_time_e(),0,2)+":"+StringUtils.substring(caseData.getUsing_time_e(),2), SECOND_TITLE_FONT_SIZE, 0, Element.ALIGN_LEFT);
		addCell(table, 100, 1, "表單狀態:"+caseData.getCodeName(), SECOND_TITLE_FONT_SIZE, 0, Element.ALIGN_LEFT);
		addCell(table, 100, 1, EMPTY_FIELD, SIZE_12, 0, Element.ALIGN_CENTER); //把表格推下去一些
	}

	/**
	 * 報表BODY
	 * @param CarBooking
	 */
	private void combineMk(CarBooking caseData) {
		addCell(table, 30, 1, "併單註記:"+caseData.getCombine_mk(), SECOND_TITLE_FONT_SIZE, 0, Element.ALIGN_LEFT);
		addCell(table, 70, 1, "併單派車單號:"+caseData.getCombine_applyid(), SECOND_TITLE_FONT_SIZE, 0, Element.ALIGN_LEFT);
		addCell(table, 100, 1, "併單原因:"+caseData.getCombine_reason(), SECOND_TITLE_FONT_SIZE, 0, Element.ALIGN_LEFT);
		addCell(table, 100, 1, EMPTY_FIELD, SIZE_12, 0, Element.ALIGN_CENTER); //把表格推下去一些
	}

	private void addCarData(CarBooking caseData) {
		addCell(table, 100, 1, "[車輛相關資料]:", SECOND_TITLE_FONT_SIZE, 0, Element.ALIGN_LEFT);
		addCell(table, 100, 1, "派車單號:"+caseData.getApplyid(), SECOND_TITLE_FONT_SIZE, 0, Element.ALIGN_LEFT);
		addCell(table, 30, 1, "駕駛人姓名:"+caseData.getName(), SECOND_TITLE_FONT_SIZE, 0, Element.ALIGN_LEFT);
		addCell(table, 70, 1, "手機號碼:"+caseData.getCellphone(), SECOND_TITLE_FONT_SIZE, 0, Element.ALIGN_LEFT);
		addCell(table, 30, 1, "車牌車號:"+caseData.getCarno1()+"-"+caseData.getCarno2(), SECOND_TITLE_FONT_SIZE, 0, Element.ALIGN_LEFT);
		addCell(table, 40, 1, "車輛種類:"+ carty(caseData.getCartype()), SECOND_TITLE_FONT_SIZE, 0, Element.ALIGN_LEFT);
		addCell(table, 30, 1, "顏色:"+caseData.getCarcolor(), SECOND_TITLE_FONT_SIZE, 0, Element.ALIGN_LEFT);
		addCell(table, 100, 1, EMPTY_FIELD, SIZE_12, 0, Element.ALIGN_CENTER); //把表格推下去一些
	}

	public  String carty(String carty ) {
	switch(carty){
		case "1":
			carty="4人座";
	        break;
		case "2":
			carty="7人座";
			break;
		case "3":
			carty="其他";
			break;
	}
		return carty;
	}

}
