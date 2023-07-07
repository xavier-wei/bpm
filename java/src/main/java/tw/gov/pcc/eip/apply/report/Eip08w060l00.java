package tw.gov.pcc.eip.apply.report;


import com.iisigroup.easyreport.pdf.exception.ReportException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfPTable;
import tw.gov.pcc.eip.Global;
import tw.gov.pcc.eip.apply.cases.Eip08w060Case;
import tw.gov.pcc.eip.framework.report.PdfReportBase;
import tw.gov.pcc.eip.util.DateUtility;
import tw.gov.pcc.eip.util.StringUtility;

import java.text.DecimalFormat;


public class Eip08w060l00 extends PdfReportBase {
	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(Eip08w060l00.class);
	public static final String TITLE_NAME = "行政院公共工程委員會";
	private static final float PAGEWIDTH = 100.0F;
	private static final int COLALL = 100;
	private static final int COL1 = 2;
	private static final int COL2 = 7;//項目編號
	private static final int COL3 = 20;//品  名 及 規 格
	private static final int COL4 = 25;//用途說明
	private static final int COL5 = 7;//數量
	private static final int COL6 = 13;//單位
	private static final int COL7 = 7;//單價
	private static final int COL8 = 7;//總價
	private static final int COL9 = 10;//預算科目
	private static final int COL10 = 2;

	private static final int TITLE_FONT_SIZE = 20;//14
	private static final int SECOND_TITLE_FONT_SIZE = 14;//14
	private static final int SIZE_12 = 12;//12
	private static final int SIZE_10 = 12;//12
	private PdfPTable table = new PdfPTable(COLALL); //新建table

	public Eip08w060l00() throws ReportException {
		super();
	}

	public Eip08w060l00(String outputFilename) throws ReportException {
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
	 * @param caseData
	 * @throws Exception
	 */
	public void createEip08w060DataPdf(Eip08w060Case caseData) throws Exception {
		try {
			document.open();
			table = new PdfPTable(COLALL); // 新建table
			table.setWidthPercentage(PAGEWIDTH);
			printSinglePage(caseData);
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
	private void printSinglePage(Eip08w060Case caseData) throws DocumentException {

		int totalRow = 35;
		int FooterROW=24;
		int applyTp ="P".equals(caseData.getApplyTpNm().substring(0,1))?0:3;
		double dateSize=0;//算memo大小
		int number =0;//算項數
		for (Eip08w060Case size : caseData.getEip08w060CaseList()) {
			double memo=Math.ceil((StringUtility.stringRealLength(size.getDesc_memo())/22));//11個字一行
			double item=Math.ceil((StringUtility.stringRealLength(size.getItem())/18));//9個字一行
			if (memo>item){
				dateSize=dateSize+memo;
			}else{
				dateSize=dateSize+item;
			}


		}

		String totalPag = dateSize<11?"01":"02";
		addHeader(caseData,totalPag);
		for (Eip08w060Case row : caseData.getEip08w060CaseList()) {
			addRow(row,++number);
		}
		if ("01".equals(totalPag)){
			addNewRow((int) (totalRow-FooterROW-dateSize+applyTp-caseData.getEip08w060CaseList().size()));
		}else{

			addNewRow((int) (totalRow-dateSize)-1);
			document.add(table);
			document.newPage();
			table = new PdfPTable(COLALL); // 新建table
			table.setWidthPercentage(PAGEWIDTH);
			addHeader(caseData,totalPag);
			addNewRow(totalRow-FooterROW+applyTp);
		}
		addSum(caseData);
		addFooter(caseData);
		addSignatureArea(caseData);
	}

	/**
	 * 報表HEADER
	 * @param caseData
	 */
	private void addHeader(Eip08w060Case caseData,String totlaPage) {


		//title1
		addCell(table, 100, 1, TITLE_NAME+caseData.getApplyTpNm().substring(2), TITLE_FONT_SIZE, 0, Element.ALIGN_CENTER);
		addCell(table, 60, 1, "民國"+DateUtility.formatChineseDateString(caseData.getApplyDate(), true), SECOND_TITLE_FONT_SIZE, 0, Element.ALIGN_RIGHT);
		addCell(table, 40, 1, "頁 次: "+ String.valueOf(new DecimalFormat("00").format(getCurrentPageNumber()))+"/"+totlaPage , SIZE_12, 0, Element.ALIGN_RIGHT);
		addCell(table, 100, 1, EMPTY_FIELD, SIZE_12, 0, Element.ALIGN_CENTER); //把表格推下去一些

		rowHeader();
	}

	private void rowHeader() {
		addCell(table, COL1, 1, "", 0, 0, Element.ALIGN_CENTER);
		addCellAssignVAlignment(table, COL2, 1, "項 目\r\n\n編 號", SIZE_12, 0.5F, Element.ALIGN_CENTER, MIDDLE);
		addCellAssignVAlignment(table, COL3, 1, "品 名 及 規 格", SIZE_12, 0.5F, Element.ALIGN_CENTER, MIDDLE);
		addCellAssignVAlignment(table, COL4, 1, "用 途 說 明", SIZE_12, 0.5F, Element.ALIGN_CENTER, MIDDLE);
		addCellAssignVAlignment(table, COL5, 1, "數 量", SIZE_12, 0.5F, Element.ALIGN_CENTER, MIDDLE);
		addCellAssignVAlignment(table, COL6, 1, "單 位", SIZE_12, 0.5F, Element.ALIGN_CENTER, MIDDLE);
		addCellAssignVAlignment(table, COL7, 1, "單 價", SIZE_12, 0.5F, Element.ALIGN_CENTER, MIDDLE);
		addCellAssignVAlignment(table, COL8, 1, "總 價", SIZE_12, 0.5F, Element.ALIGN_CENTER, MIDDLE);
		addCellAssignVAlignment(table, COL9, 1, "預 算\r\n\n科 目", SIZE_12, 0.5F, Element.ALIGN_CENTER, MIDDLE);
		addCell(table, COL10, 1, "", 0, 0, Element.ALIGN_CENTER);
	}

	/**
	 * 報表BODY
	 * @param row
	 */
	private void addRow(Eip08w060Case row,int number) {
		addCell(table, COL1, 1, "", 0, 0, Element.ALIGN_CENTER);
		addCellAssignVAlignment(table, COL2, 1, String.valueOf(number), SIZE_12, 0.5F, Element.ALIGN_CENTER, MIDDLE);
		addCellAssignVAlignment(table, COL3, 1,row.getItem(), SIZE_12, 0.5F, Element.ALIGN_CENTER, MIDDLE);
		addCellAssignVAlignment(table, COL4, 1,row.getDesc_memo(), SIZE_12, 0.5F, Element.ALIGN_LEFT, MIDDLE);
		addCellAssignVAlignment(table, COL5, 1,row.getCnt(), SIZE_12, 0.5F, Element.ALIGN_CENTER, MIDDLE);
		addCellAssignVAlignment(table, COL6, 1,row.getUnit(), SIZE_12, 0.5F, Element.ALIGN_CENTER, MIDDLE);
		addCellAssignVAlignment(table, COL7, 1,"", SIZE_12, 0.5F, Element.ALIGN_CENTER, MIDDLE);
		addCellAssignVAlignment(table, COL8, 1, "", SIZE_12, 0.5F, Element.ALIGN_CENTER, MIDDLE);
		addCellAssignVAlignment(table, COL9, 1, "", SIZE_12, 0.5F, Element.ALIGN_CENTER, MIDDLE);
		addCell(table, COL10, 1, "", 0, 0, Element.ALIGN_CENTER);
	}
//合計
	private void addSum(Eip08w060Case caseData) {

		addCell(table, COL1, 1, "", 0, 0, Element.ALIGN_CENTER);
		addCellAssignVAlignment(table, (COL2+COL3), 1, "\n合  計\n ", SIZE_12, 0.5F, Element.ALIGN_CENTER, MIDDLE);
		addCellAssignVAlignment(table, COL4, 1,"", SIZE_12, 0.5F, Element.ALIGN_CENTER, MIDDLE);
		addCellAssignVAlignment(table, COL5, 1,caseData.getCnt(), SIZE_12, 0.5F, Element.ALIGN_CENTER, MIDDLE);
		addCellAssignVAlignment(table, COL6, 1,caseData.getCnt(), SIZE_12, 0.5F, Element.ALIGN_CENTER, MIDDLE);
		addCellAssignVAlignment(table, COL7, 1,"", SIZE_12, 0.5F, Element.ALIGN_CENTER, MIDDLE);
		addCellAssignVAlignment(table, COL8, 1, "", SIZE_12, 0.5F, Element.ALIGN_CENTER, MIDDLE);
		addCellAssignVAlignment(table, COL9, 1, "", SIZE_12, 0.5F, Element.ALIGN_CENTER, MIDDLE);
		addCell(table, COL10, 1, "", 0, 0, Element.ALIGN_CENTER);
	}

	//會簽意見
	private void addFooter(Eip08w060Case caseData) {

		addCell(table, COL1, 1, "", 0, 0, Element.ALIGN_CENTER);
		addCellAssignVAlignment(table, COL2, 1, "會\n\n簽\n\n意\n\n見", SIZE_12, 0.5F, Element.ALIGN_CENTER, MIDDLE);

		addCellAssignVAlignmentAndLinespace(table, COL3+COL4+COL5+COL6+COL7+COL8+COL9, 1,
				    "1.本採購第   項目 □是□否 為綠色採購範疇，□有□無 環標產品。（非綠色採購範疇或無環標產品，"+
						"則免填第2點。\r\n"+
						"2.本採購 □採購□不採購 環保產品。不採購原因為：□產品規格不符，\n於    年    月    日綠色生活"+
						"資訊網「產品查詢」，查詢確無此產品環保標章證號（查詢畫面如附）；□執行業務需要，擬採"+
						"購非環保標章產品。\n"+
						"3.本採購項目，目前□是□否符合「優先採購身心障礙福利機構團體或庇護工場生產物品及服務"+
						"辦法」第3條所訂之商品，□是□否向身心障礙福利機構團體或庇護工場採購。\n"
				, SIZE_12, 0.5F, Element.ALIGN_LEFT, MIDDLE,2);

		addCell(table, COL10, 1, "", 0, 0, Element.ALIGN_CENTER);
	}

	private void addSignatureArea(Eip08w060Case caseData) {

		addCell(table, COL1, 1, "", 0, 0, Element.ALIGN_CENTER);
		addCellAssignVAlignment(table, (100-COL1-COL10)/4, 1,"", SIZE_12, 0.5F, Element.ALIGN_CENTER, MIDDLE);
		addCellAssignVAlignment(table, (100-COL1-COL10)/4, 1, "請 購 人 / 經 辦 ", SIZE_12, 0.5F, Element.ALIGN_CENTER, MIDDLE);
		addCellAssignVAlignment(table, (100-COL1-COL10)/4, 1,"科 長 ", SIZE_12, 0.5F, Element.ALIGN_CENTER, MIDDLE);
		addCellAssignVAlignment(table, (100-COL1-COL10)/4, 1,"單 位 主 管 ", SIZE_12, 0.5F, Element.ALIGN_CENTER, MIDDLE);
		addCell(table, COL10, 1, "", 0, 0, Element.ALIGN_CENTER);

		addCell(table, COL1, 1, "", 0, 0, Element.ALIGN_CENTER);
		addCellAssignVAlignment(table, (100-COL1-COL10)/4, 1,"\n請  購  單  位\n  ", SIZE_12, 0.5F, Element.ALIGN_LEFT, MIDDLE);
		addCellAssignVAlignment(table, (100-COL1-COL10)/4, 1, "", SIZE_12, 0.5F, Element.ALIGN_CENTER, MIDDLE);
		addCellAssignVAlignment(table, (100-COL1-COL10)/4, 1,"", SIZE_12, 0.5F, Element.ALIGN_CENTER, MIDDLE);
		addCellAssignVAlignment(table, (100-COL1-COL10)/4, 1,"", SIZE_12, 0.5F, Element.ALIGN_CENTER, MIDDLE);
		addCell(table, COL10, 1, "", 0, 0, Element.ALIGN_CENTER);

		if ("P".equals(caseData.getApplyTpNm().substring(0,1))) {
		//軟(硬)體請購單才有
		addCell(table, COL1, 1, "", 0, 0, Element.ALIGN_CENTER);
		addCellAssignVAlignment(table, (100 - COL1 - COL10) / 4, 1, "\n資 訊 推 動 小 組\n  ", SIZE_12, 0.5F, Element.ALIGN_LEFT, MIDDLE);
		addCellAssignVAlignment(table, (100 - COL1 - COL10) / 4, 1, "", SIZE_12, 0.5F, Element.ALIGN_CENTER, MIDDLE);
		addCellAssignVAlignment(table, (100 - COL1 - COL10) / 4, 1, "", SIZE_12, 0.5F, Element.ALIGN_CENTER, MIDDLE);
		addCellAssignVAlignment(table, (100 - COL1 - COL10) / 4, 1, "", SIZE_12, 0.5F, Element.ALIGN_CENTER, MIDDLE);
		addCell(table, COL10, 1, "", 0, 0, Element.ALIGN_CENTER);
		}

		addCell(table, COL1, 1, "", 0, 0, Element.ALIGN_CENTER);
		addCellAssignVAlignment(table, (100-COL1-COL10)/4, 1,"\n秘    書    處\n  ", SIZE_12, 0.5F, Element.ALIGN_LEFT, MIDDLE);
		addCellAssignVAlignment(table, (100-COL1-COL10)/4, 1, "", SIZE_12, 0.5F, Element.ALIGN_CENTER, MIDDLE);
		addCellAssignVAlignment(table, (100-COL1-COL10)/4, 1,"", SIZE_12, 0.5F, Element.ALIGN_CENTER, MIDDLE);
		addCellAssignVAlignment(table, (100-COL1-COL10)/4, 1,"", SIZE_12, 0.5F, Element.ALIGN_CENTER, MIDDLE);
		addCell(table, COL10, 1, "", 0, 0, Element.ALIGN_CENTER);


		addCell(table, COL1, 1, "", 0, 0, Element.ALIGN_CENTER);
		addCellAssignVAlignment(table, (100-COL1-COL10)/4, 1,"\n主    計    室\n（新臺幣1萬元以上或涉及簽約之案件）\n  ", SIZE_12, 0.5F, Element.ALIGN_LEFT, MIDDLE);
		addCellAssignVAlignment(table, (100-COL1-COL10)/4, 1, "", SIZE_12, 0.5F, Element.ALIGN_CENTER, MIDDLE);
		addCellAssignVAlignment(table, (100-COL1-COL10)/4, 1,"", SIZE_12, 0.5F, Element.ALIGN_CENTER, MIDDLE);
		addCellAssignVAlignment(table, (100-COL1-COL10)/4, 1,"", SIZE_12, 0.5F, Element.ALIGN_CENTER, MIDDLE);
		addCell(table, COL10, 1, "", 0, 0, Element.ALIGN_CENTER);

		addCell(table, COL1, 1, "", 0, 0, Element.ALIGN_CENTER);
		addCellAssignVAlignment(table, (100-COL1-COL10)/4, 1,"\n機  關  首  長  或\n授  權  代  簽  人\n  ", SIZE_12, 0.5F, Element.ALIGN_LEFT, MIDDLE);
		addCellAssignVAlignment(table, (100-COL1-COL10)/4, 1, "", SIZE_12, 0.5F, Element.ALIGN_CENTER, MIDDLE);
		addCellAssignVAlignment(table, (100-COL1-COL10)/4, 1,"", SIZE_12, 0.5F, Element.ALIGN_CENTER, MIDDLE);
		addCellAssignVAlignment(table, (100-COL1-COL10)/4, 1,"", SIZE_12, 0.5F, Element.ALIGN_CENTER, MIDDLE);
		addCell(table, COL10, 1, "", 0, 0, Element.ALIGN_CENTER);


		addCellAssignVAlignment(table, 100, 1,"", SIZE_12, 0, Element.ALIGN_LEFT, MIDDLE);//向下一格
		addCellAssignVAlignment(table, 100, 1,"        秘   字第       號", SIZE_12, 0, Element.ALIGN_LEFT, MIDDLE);
	}

	private void addNewRow(int count) {

		for (int i=1;i<=count;i++){
			addCell(table, COL1, 1, "", 0, 0, Element.ALIGN_CENTER);
			addCellAssignVAlignment(table, COL2, 1," ", SIZE_12, 0.5F, Element.ALIGN_CENTER, MIDDLE);
			addCellAssignVAlignment(table, COL3, 1, "  ", SIZE_12, 0.5F, Element.ALIGN_CENTER, MIDDLE);
			addCellAssignVAlignment(table, COL4, 1, "  ", SIZE_12, 0.5F, Element.ALIGN_CENTER, MIDDLE);
			addCellAssignVAlignment(table, COL5, 1, "  ", SIZE_12, 0.5F, Element.ALIGN_CENTER, MIDDLE);
			addCellAssignVAlignment(table, COL6, 1, "  ", SIZE_12, 0.5F, Element.ALIGN_CENTER, MIDDLE);
			addCellAssignVAlignment(table, COL7, 1, "  ", SIZE_12, 0.5F, Element.ALIGN_CENTER, MIDDLE);
			addCellAssignVAlignment(table, COL8, 1, "  ", SIZE_12, 0.5F, Element.ALIGN_CENTER, MIDDLE);
			addCellAssignVAlignment(table, COL9, 1, "  ", SIZE_12, 0.5F, Element.ALIGN_CENTER, MIDDLE);
			addCell(table, COL10, 1, "", 0, 0, Element.ALIGN_CENTER);
		}
	}


}
