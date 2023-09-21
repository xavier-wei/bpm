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
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import tw.gov.pcc.eip.dao.EipcodeDao;
import tw.gov.pcc.eip.dao.MsgdataDao;
import tw.gov.pcc.eip.dao.MsgdepositDao;
import tw.gov.pcc.eip.dao.MsgdepositdirDao;
import tw.gov.pcc.eip.domain.Msgdata;
import tw.gov.pcc.eip.domain.Msgdeposit;
import tw.gov.pcc.eip.domain.Msgdepositdir;
import tw.gov.pcc.eip.msg.cases.Eip01w040Case;
import tw.gov.pcc.eip.msg.cases.Eip01wPopCase;
import tw.gov.pcc.eip.util.DateUtility;
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
    private EipcodeDao eipcodeDao;
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
                } else {
                    treeStr.add("<li id=\"" + current + "\" class=\"folder\">" + tree.get(i).getFilename());
                    treeStr.add("</ul>");
                    j -= 1;
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
        List<Eip01wPopCase> qryList = msgdataDao.getbyPath(deptId, caseData.getPath()).stream().map(m -> {
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
        deptId = "8";
        String path = msgdepositdirDao.getTree(deptId).stream()
                .filter(f -> StringUtils.equals(caseData.getKey(), StringUtils.trim(f.getExisthier())))
                .map(m -> m.getFilepath()).findAny().orElse("");
        List<Eip01wPopCase> qryList = msgdataDao.getbyPath(deptId, path).stream().map(m -> {
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
        deptId = "8";
        String keyword = caseData.getKeyword();
        if (!"".equals(keyword)) {
            List<Eip01wPopCase> qryList = msgdataDao.getbyKeyword(deptId, keyword).stream().map(m -> {
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
        Eip01wPopCase detail = new Eip01wPopCase();
        Msgdata m = msgdataDao.findbyfseq(fseq);
        if (m != null) {
            detail.setFseq(m.getFseq());
            detail.setMsgtype(eipcodeDao.findByCodeKindCodeNo("MESSAGETYPE", m.getMsgtype()).get().getCodename());
            detail.setContactunit(m.getContactunit());
            detail.setSubject(m.getSubject());
            detail.setMcontent(m.getMcontent());
            detail.setUpddt(m.getUpddt() == null ? "" : m.getUpddt().format(minguoformatter));
            detail.setFile(msgdepositDao.findbyfseq(Arrays.asList(fseq)).stream()
                    .collect(Collectors.toMap(Msgdeposit::getSeq, Msgdeposit::getAttachfile)));
            detail.setContactperson(m.getContactperson());
            detail.setContacttel(m.getContacttel());
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
        String dir = eipcodeDao.findByCodeKindCodeNo("FILEDIR", "1").get().getCodename() + "\\"; // 檔案所在資料夾
        String seq = caseData.getSeq();
        if (StringUtils.contains(seq, ",")) { // 多檔
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ZipOutputStream zipStream = new ZipOutputStream(outputStream);
            List<Msgdeposit> files = msgdepositDao.findbyFseqSeq(caseData.getFseq(), Arrays.asList(seq.split(",")));
            int emptyCnt = 0;
            try (outputStream; zipStream) {
                for (Msgdeposit origin : files) {
                    File file = new File(dir + origin.getRealfilename());
                    if (!file.exists()) {
                        emptyCnt++;
                        continue;
                    }
                    ByteArrayOutputStream fileOutputStream = new ByteArrayOutputStream();
                    try (FileInputStream inputStream = new FileInputStream(file); fileOutputStream) {
                        byte[] buffer = new byte[1024];
                        int bytesRead;
                        while ((bytesRead = inputStream.read(buffer)) != -1) {
                            fileOutputStream.write(buffer, 0, bytesRead);
                        }
                        if (fileOutputStream != null) {
                            ZipEntry zipEntry = new ZipEntry(origin.getAttachfile());
                            zipStream.putNextEntry(zipEntry);
                            zipStream.write(fileOutputStream.toByteArray());
                            zipStream.closeEntry();
                        }
                    } catch (IOException e) {
                        log.error(origin.getRealfilename() + " 取檔異常: " + ExceptionUtility.getStackTrace(e));
                    }
                }
            } catch (IOException e) {
                log.error(" 壓縮檔產製異常: " + ExceptionUtility.getStackTrace(e));
                return null;
            }
            if (emptyCnt == files.size()) {
                return null;
            }
            caseData.setFilename(caseData.getSubject() + "_" + DateUtility.getNowChineseDate() + ".zip");
            return outputStream;
        } else { // 單檔
            Msgdeposit origin = msgdepositDao.findbyFseqSeq(caseData.getFseq(), Arrays.asList(seq)).get(0);
            if (origin == null) {
                return null;
            }
            File file = new File(dir + origin.getRealfilename()); // 格式化檔名
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
                log.error(origin.getRealfilename() + " 取檔異常: " + ExceptionUtility.getStackTrace(e));
                return null;
            }
            caseData.setFilename(origin.getAttachfile()); // 原始檔名
            return outputStream;
        }
    }
}
