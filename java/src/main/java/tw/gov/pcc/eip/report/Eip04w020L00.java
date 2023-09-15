package tw.gov.pcc.eip.report;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xwpf.usermodel.*;
import tw.gov.pcc.eip.common.cases.Eip04w020Case;
import tw.gov.pcc.eip.domain.Orresult;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;


public class Eip04w020L00 {

	private String signform;
	private String topicname;
	private String time;
	private String address;
	private List<Orresult> list;
	private Map<String,String> deptMap;
	private Map<String,String> titleMap;
	private int tableRowLimit = 20;
	private int pageCount = 1;
	private int rowCount = 1;

	public Eip04w020L00(String signform, String topicname, String time, String address, List<Orresult>list, Map<String,String> deptMap, Map<String,String> titleMap) {
		this.signform = signform;
		this.topicname = topicname;
		this.time = time;
		this.address = address;
		this.list = list;
		this.deptMap = deptMap;
		this.titleMap = titleMap;
	}

	public ByteArrayOutputStream creatWord(Eip04w020Case eip04w020Case) throws IOException {
		// 創建一個新的XWPFDocument對象
		XWPFDocument document = new XWPFDocument();

		// 創建一個段落並添加到文件中
		XWPFParagraph paragraphA = document.createParagraph();
		XWPFRun runA = paragraphA.createRun();
		paragraphA.setAlignment(ParagraphAlignment.CENTER);
		// 非「會議」就是「課程」或「活動」
		runA.setText(StringUtils.defaultString(topicname) + ("M".equals(signform) ? "會議" : "訓練") + "簽到表");
		runA.setFontFamily("標楷體");
		runA.setFontSize(22);
		runA.setBold(true);
		addDiffBoldRow("◼時間：", StringUtils.substringBefore(time,"~")  + "至", document);
		addDiffBoldRow("　　　　", StringUtils.substringAfter(time,"~"), document);
		addDiffBoldRow("◼地點：", address, document);
		if (!"M".equals(signform)) {
			addDiffBoldRow("◼主題：", topicname, document);
			addDiffBoldRow("◼講座：", "", document);
		}
		// 創建一個表格並添加到文件中
		XWPFTable table = addTableAndHeader(document);
		for (int i = 1; i <= list.size(); i++) {
			addDataRow(table, list.get(i-1));
			if (i == list.size()){
				break;
			}
			if (pageCount > 1) {
				tableRowLimit = 30;
			}
			if (rowCount % tableRowLimit == 0) {
				XWPFParagraph paragraph = document.createParagraph();
				paragraph.createRun().addBreak(BreakType.PAGE);
				table = addTableAndHeader(document);
				pageCount++;
				resetRowCount();
			} else {
				rowCount++;
			}
		}

		// 將文件寫入硬碟
//		FileOutputStream out = new FileOutputStream(new File("D:/wordDev/Word.docx"));
		ByteArrayOutputStream baos = new ByteArrayOutputStream(0);
		document.write(baos);
		baos.close();
		return baos;
	}

	/**
	 * 增加一行前後不同bold樣式的文本
	 */
	private void addDiffBoldRow(String str1,String str2,XWPFDocument document){
		XWPFParagraph paragraph = document.createParagraph();
		paragraph.setAlignment(ParagraphAlignment.LEFT);
		XWPFRun run1 = paragraph.createRun();
		XWPFRun run2 = paragraph.createRun();
		run1.setFontFamily("標楷體");
		run1.setFontSize(16);
		run1.setBold(true);
		run1.setText(str1);
		run2.setFontFamily("標楷體");
		run2.setFontSize(16);
		run2.setBold(false);
		run2.setText(str2);
	}

	private XWPFTable addTableAndHeader(XWPFDocument document) {
		XWPFTable table = document.createTable();
		table.removeRow(0);
		XWPFTableRow row = table.createRow();
		for(int i=0;i<4;i++){
			XWPFTableCell cell = row.createCell();
			XWPFParagraph paragraph = cell.addParagraph();
			XWPFRun run = paragraph.createRun();
			paragraph.setAlignment(ParagraphAlignment.CENTER);
			run.setFontFamily("標楷體");
			run.setBold(true);
			run.setFontSize(16);
			cell.setWidth("2500");
			cell.removeParagraph(0);
			if(i == 0) {
				run.setText("單　　　　位");
			}
			if(i == 1) {
				run.setText("職　　　　稱");
			}
			if(i == 2) {
				run.setText("姓　　　　名");
			}
			if(i == 3) {
				run.setText("簽　　　　名");
			}
		}
		return table;
	}
	private void addDataRow(XWPFTable xwpfTable, Orresult orresult) {
		XWPFTableRow row = xwpfTable.createRow();
		for(int i=0;i<4;i++){
			XWPFTableCell cell = row.getCell(i);
			XWPFParagraph paragraph = cell.addParagraph();
			XWPFRun run = paragraph.createRun();
			paragraph.setAlignment(ParagraphAlignment.CENTER);
			run.setFontFamily("標楷體");
			run.setFontSize(16);
			cell.setWidth("2500");
			cell.removeParagraph(0);
			if(i == 0) {
				run.setText(deptMap.get(orresult.getDept()));
			}
			if(i == 1) {
				run.setText(titleMap.get(orresult.getJogtitle()));
			}
			if(i == 2) {
				run.setText(orresult.getRegisname());
			}
			if(i == 3) {
				run.setText("");
			}
		}
	}

	public void resetRowCount(){
		this.rowCount = 1;
	}
}
