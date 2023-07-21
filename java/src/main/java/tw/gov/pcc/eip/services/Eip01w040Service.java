package tw.gov.pcc.eip.services;

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
import tw.gov.pcc.eip.domain.Msgdeposit;
import tw.gov.pcc.eip.domain.Msgdepositdir;
import tw.gov.pcc.eip.msg.cases.Eip01w040Case;
import tw.gov.pcc.eip.msg.cases.Eip01wPopCase;

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

    /**
     * 取得樹
     * 
     * @param caseData
     * @param deptId
     */
    public List<String> getTree(Eip01w040Case caseData, String deptId) {
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
                } else { // 上?層
                    int level = current.length() - next.length();
                    treeStr.add("<li id=\"" + current + "\" class=\"folder\">" + tree.get(i).getFilename());
                    while (level > 0) {
                        treeStr.add("</ul>");
                        level--;
                        j -= 1;
                    }
                }
            }
        }
        while (j > 0) {
            treeStr.add("</ul>");
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
        List<Eip01wPopCase> qryList = msgdataDao.getEip01w040byPath(deptId, caseData.getPath()).stream().map(m -> {
            Eip01wPopCase data = new Eip01wPopCase();
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
        String path = msgdepositdirDao.getTree(deptId).stream()
                .filter(f -> StringUtils.equals(caseData.getKey(), StringUtils.trim(f.getExisthier())))
                .map(m -> m.getFilepath()).findAny().orElse("");
        List<Eip01wPopCase> qryList = msgdataDao.getEip01w040byPath(deptId, path).stream().map(m -> {
            Eip01wPopCase data = new Eip01wPopCase();
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
        String keyword = caseData.getKeyword();
        if (!"".equals(keyword)) {
            List<Eip01wPopCase> qryList = msgdataDao.getEip01w040byKeyword(deptId, keyword).stream().map(m -> {
                Eip01wPopCase data = new Eip01wPopCase();
                data.setFseq(m.getFseq());
                data.setSubject(m.getSubject());
                data.setUpddt(m.getUpddt() == null ? "" : m.getUpddt().format(minguoformatter));
                return data;
            }).collect(Collectors.toCollection(ArrayList::new));
            caseData.setQryList(qryList);
        } else {
            pathQuery(caseData, deptId);
        }
    }

    /**
     * 取得明細資料
     * 
     * @param fseq
     * @return
     */
    public Eip01wPopCase getDetail(String fseq) {
        Eip01wPopCase detail = msgdataDao.getEip01wDetail(fseq, "4");
        if (detail != null) {
            detail.setFile(msgdepositDao.findbyfseq(Arrays.asList(fseq)).stream()
                    .collect(Collectors.toMap(Msgdeposit::getSeq, Msgdeposit::getAttachfile)));
        }
        return detail;
    }
}
