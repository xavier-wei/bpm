package tw.gov.pcc.eip.common.report;

import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTFonts;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTRPr;
import tw.gov.pcc.eip.common.cases.Eip06w010Case;
import tw.gov.pcc.eip.util.DateUtility;

import javax.servlet.ServletContext;
import java.io.*;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Eip06w010l00 {

    //文檔地址


    public XWPFDocument createNewFile(Eip06w010Case caseData, Map<String, Integer> mtItemMap) throws IOException {

        XWPFDocument doc = new XWPFDocument(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("report/會議室使用申請表_會議室簽呈.docx")));
        // 設定字體及大小
//        CTRPr ctrpr = CTRPr.Factory.newInstance();
//        CTFonts runFonts = ctrpr.addNewRFonts();
//        runFonts.setAscii("標楷體");
//        runFonts.setCs("標楷體");
//        runFonts.setEastAsia("標楷體");
//        runFonts.setHAnsi("標楷體");
//        ctrpr.addNewSz().setVal(new BigInteger("16"));
//        ctrpr.addNewSzCs().setVal(new BigInteger("16"));


        //獲取所有段落訊息
        List<XWPFParagraph> paras = doc.getParagraphs();
        List<XWPFRun> runs = paras.get(1).getRuns();
        //申請日期
        String printDt = DateUtility.getNowChineseDate();
        printDt = printDt.substring(0,3) +"年" + printDt.substring(3,5) + "月" + printDt.substring(5) + "日";
        runs.get(4).setText(printDt);
        //獲取文檔所有表格
        List<XWPFTable> tables = doc.getTables();
        for(XWPFTable table : tables){
            XWPFTableRow row = table.getRow(0);
            //單元格內容
            row.getCell(1).setText(caseData.getMeetingName()); //meetingName
            row.getCell(3).setText(caseData.getChairman()); //Chairman
            row = table.getRow(1);
            String meetingDt = caseData.getMeetingdt().toString();
            String meetingYear = String.valueOf(Integer.parseInt( meetingDt.substring(0,4)) - 1911);
            row.getCell(1).setText(meetingYear + "年" + meetingDt.substring(4,6) + "月" + meetingDt.substring(6,8) + "日"
                    + caseData.getMeetingBegin().substring(0,2) + "時"+ caseData.getMeetingBegin().substring(2) + "分至"
                    + caseData.getMeetingEnd().substring(0,2) + "時" + caseData.getMeetingEnd().substring(2) + "分"); //會議時間
            row = table.getRow(2);
            row.getCell(1).setText(caseData.getRoomId()); //RoomId
            row.getCell(3).setText(caseData.getQty() + "人"); //開會人數
            row = table.getRow(8);
            row.getCell(1).setText(mtItemMap.get("A01") != null? mtItemMap.get("A01").toString() + "份":"" ); //A01　茶水
            row = table.getRow(11);
            row.getCell(0).setText(mtItemMap.get("A02") != null? mtItemMap.get("A02").toString() + "份":"" ); //A02 早餐(便餐)
            row.getCell(1).setText(mtItemMap.get("A03") != null? mtItemMap.get("A03").toString() + "份":"" ); //A03 早餐(餐盒)
            row.getCell(2).setText(mtItemMap.get("A04") != null? mtItemMap.get("A04").toString() + "份":"" ); //A04 午餐(便餐)
            row.getCell(3).setText(mtItemMap.get("A05") != null? mtItemMap.get("A05").toString() + "份":"" ); //A05 午餐(餐盒)
            row.getCell(4).setText(mtItemMap.get("A06") != null? mtItemMap.get("A06").toString() + "份":"" ); //A06 晚餐(便餐)
            row.getCell(5).setText(mtItemMap.get("A07") != null? mtItemMap.get("A07").toString() + "份":"" ); //A07 晚餐(餐盒)

//            table.getRows().forEach(a->{
//                a.getTableCells().forEach(b->{
//                    b.getParagraphs().forEach(c->{
//                        c.getRuns().forEach(d->{
//                            System.out.println(d.getFontFamily() + "+" +  d.getFontName() + "+" + d.getFontSize());
//                            d.setFontFamily("標楷體");
//                            d.setFontSize(16);
//                        });
//                    });
//                });
//            });
        }
        return doc;
    }
}
