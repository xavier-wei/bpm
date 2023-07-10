package tw.gov.pcc.eip.services;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.chrono.MinguoChronology;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import tw.gov.pcc.eip.dao.MsgdataDao;
import tw.gov.pcc.eip.dao.MsgdepositDao;
import tw.gov.pcc.eip.dao.MsgdepositdirDao;
import tw.gov.pcc.eip.domain.Msgdata;
import tw.gov.pcc.eip.domain.Msgdeposit;
import tw.gov.pcc.eip.domain.Msgdepositdir;
import tw.gov.pcc.eip.msg.cases.Eip01w040Case;
import tw.gov.pcc.eip.util.ExceptionUtility;

/**
 * 下載專區
 * 
 * @author vita
 *
 */
@Service
@Slf4j
public class Eip01w040Service {

    @Autowired
    private MsgdataDao msgdataDao;
    @Autowired
    private MsgdepositDao msgdepositDao;
    @Autowired
    private MsgdepositdirDao msgdepositdirDao;
    DateTimeFormatter minguoformatter = DateTimeFormatter.ofPattern("yyy/MM/dd")
            .withChronology(MinguoChronology.INSTANCE).withLocale(Locale.TAIWAN);

//    private void getTree(Eip01w040Case caseData, String deptId) {
//        deptId = "8";
//        List<Msgdepositdir> tree = msgdepositdirDao.getTree(deptId); // 取第一筆資料為預設路徑
//        v1
//        String jsonStr = tree.stream().map(m -> {
//            return "{ \"title\": \"" + m.getFilename() + "\", \"key\": \"" + StringUtils.trim(m.getExisthier())
//                    + "\", \"isFolder\":\"true\" }";
//        }).collect(Collectors.joining(",", "[", "]"));
//        caseData.setTreenode(jsonStr);
//        v2
//        String jsonStr = "";
//        List<Node> treelist = new ArrayList<>();
//        for (int i = 0; i < tree.size(); i++) {
//            Msgdepositdir m = tree.get(i);
//            Node node = new Node();
//            node.setTitle(m.getFilename());
//            node.setKey(StringUtils.trim(m.getExisthier()));
//            node.setIsFolder("true");
//            // 巢狀node ???
//            treelist.add(node);
//        }
//        try {
//            jsonStr = new ObjectMapper().writeValueAsString(treelist); // 物件 title key isFolder ..
//        } catch (JsonProcessingException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        System.out.println("json:" + jsonStr);
//        caseData.setTreenode(jsonStr);
//        System.out.println(jsonStr);
//        String defaultpath = tree.stream().findFirst().map(m -> m.getFilepath()).orElse("");
//        caseData.setPath(defaultpath);
//    }

    /**
     * 取得樹
     * 
     * @param caseData
     * @param deptId
     */
    public List<String> getTree(Eip01w040Case caseData, String deptId) {
        deptId = "8";
        List<Msgdepositdir> tree = msgdepositdirDao.getTree(deptId);

        int j = 0;
        List<String> treeStr = new ArrayList<>();
        for (int i = 0; i < tree.size(); i++) { // 前提:無重複路徑且已排序
            String current = StringUtils.trim(tree.get(i).getExisthier());
            if (i == tree.size() - 1) { // 最後一層 與上層比對
                String prev = StringUtils.trim(tree.get(i - 1).getExisthier());
                if (current.length() > prev.length() && StringUtils.contains(current, prev)) { // 子層
                    treeStr.add("<li id=\"" + current + "\" class=\"folder\">" + tree.get(i).getFilename());
                    treeStr.add("<ul>");
                } else if (prev.length() == current.length()) { // 同層
                    treeStr.add("<li id=\"" + current + "\" class=\"folder\">" + tree.get(i).getFilename());
                }
            } else { // 與下層比對
                String next = StringUtils.trim(tree.get(i + 1).getExisthier());
                if (next.length() > current.length() && StringUtils.contains(next, current)) { // 子層
                    treeStr.add("<li id=\"" + current + "\" class=\"folder\">" + tree.get(i).getFilename());
                    treeStr.add("<ul>");
                    j += 1;
                } else if (next.length() == current.length()) { // 同層
                    treeStr.add("<li id=\"" + current + "\" class=\"folder\">" + tree.get(i).getFilename());
                    treeStr.add("<li id=\"" + next + "\" class=\"folder\">" + tree.get(i + 1).getFilename());
                } else {
                    treeStr.add("<li id=\"" + current + "\" class=\"folder\">" + tree.get(i).getFilename());
                    treeStr.add("</ul>");
                    j -= 1;
                }
            }
        }
        while (j > 0) {
            treeStr.add("</ul>\n");
            j--;
        }
        String key = caseData.getKey(); // 紀錄上一次選中路徑
        if (!"".equals(key)) {
            List<String> parentId = new ArrayList<>();
            for (int i = 0; i < key.length(); i++) { // 找出所有父層
                parentId.add(key.substring(0, i + 1));
            }
            Pattern pattern = Pattern.compile("id=\"(\\w+)\"", Pattern.MULTILINE);
            for (int k = 0; k < parentId.size(); k++) {
                String text = (k == parentId.size() - 1) ? "folder active" : "folder expanded"; // 父層展開 最底層選中
                String searchSeq = parentId.get(k);
                for (int l = 0; l < treeStr.size(); l++) {
                    Matcher matcher = pattern.matcher(treeStr.get(l));
                    if (matcher.find() && StringUtils.equals(searchSeq, matcher.group(1))) {
                        treeStr.set(l, treeStr.get(l).replace("folder", text));
                        continue;
                    }
                }
            }
        } else {
            treeStr.set(0, treeStr.get(0).replace("folder", "folder expanded")); // 預設展開第一層
        }
        String defaultpath = tree.stream().findFirst().map(m -> m.getFilepath()).orElse(""); // 取第一筆資料為預設路徑
        caseData.setPath(defaultpath);
        return treeStr;
    }

    /**
     * 預設路徑查詢
     * 
     * @param caseData
     * @param deptId
     */
    public void defaultQuery(Eip01w040Case caseData, String deptId) {
        deptId = "8";
        List<Eip01w040Case.Detail> qryList = msgdataDao.getbyDefaultPath(deptId, "/人事室/訓練專區/體能訓練/重訓課程"/*caseData.getPath()*/).stream().map(m -> {
            Eip01w040Case.Detail data = new Eip01w040Case.Detail();
            data.setFseq(m.getFseq());
            data.setSubject(m.getSubject());
            data.setUpddt(m.getUpddt() == null ? "" : m.getUpddt().format(minguoformatter));
            return data;
        }).collect(Collectors.toCollection(ArrayList::new));
        caseData.setQryList(qryList);
    }

    /**
     * 選擇路徑查詢
     * 
     * @param caseData
     * @param deptId
     */
    public void pathQuery(Eip01w040Case caseData, String deptId) {
        deptId = "8";
        String path = msgdepositdirDao.getTree(deptId).stream()
                .filter(f -> StringUtils.equals(caseData.getKey(), StringUtils.trim(f.getExisthier()))).map(m -> m.getFilepath())
                .findAny().orElse("");
        List<Eip01w040Case.Detail> qryList = msgdataDao.getbyDefaultPath(deptId, path).stream().map(m -> {
            Eip01w040Case.Detail data = new Eip01w040Case.Detail();
            data.setFseq(m.getFseq());
            data.setSubject(m.getSubject());
            data.setUpddt(m.getUpddt() == null ? "" : m.getUpddt().format(minguoformatter));
            return data;
        }).collect(Collectors.toCollection(ArrayList::new));
        caseData.setQryList(qryList);
    }

    /**
     * 關鍵字查詢
     * 
     * @param caseData
     * @param deptId
     */
    public void keywordQuery(Eip01w040Case caseData, String deptId) {
        deptId = "8";
        List<Eip01w040Case.Detail> qryList = msgdataDao.getbyKeyword(deptId, caseData.getKeyword()).stream().map(m -> {
            Eip01w040Case.Detail data = new Eip01w040Case.Detail();
            data.setFseq(m.getFseq());
            data.setSubject(m.getSubject());
            data.setUpddt(m.getUpddt() == null ? "" : m.getUpddt().format(minguoformatter));
            return data;
        }).collect(Collectors.toCollection(ArrayList::new));
        caseData.setQryList(qryList);
    }

    /**
     * 取得明細資料
     * 
     * @param fseq
     * @return
     */
    public Eip01w040Case.Detail getDetail(String fseq) {
        Eip01w040Case.Detail detail = new Eip01w040Case.Detail();
        Msgdata m = msgdataDao.findbyfseq(fseq);
        if (m != null) {
            detail.setFseq(m.getFseq());
            detail.setSubject(m.getSubject());
            detail.setUpddt(m.getUpddt() == null ? "" : m.getUpddt().format(minguoformatter));
            detail.setFile(msgdepositDao.findbyfseq(Arrays.asList(fseq)).stream()
                    .collect(Collectors.toMap(Msgdeposit::getSeq, Msgdeposit::getAttachfile)));
        }
        return detail;
    }
    
    /**
     * 公告事項 檔案下載
     * 
     * @param caseData
     * @return
     */
    public ByteArrayOutputStream getFile(Eip01w040Case caseData) {
        File file = null;
        if (!file.exists()) {
            return null;
        }
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try (FileInputStream inputStream = new FileInputStream(file); outputStream) {

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            log.error(ExceptionUtility.getStackTrace(e));
            return null;
        }
        return outputStream;
    }
}
