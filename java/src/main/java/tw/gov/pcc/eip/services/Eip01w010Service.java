package tw.gov.pcc.eip.services;

import static org.apache.commons.lang3.StringUtils.trim;
import static org.apache.commons.lang3.StringUtils.trimToNull;

import java.io.File;
import java.time.LocalDateTime;
import java.time.chrono.MinguoChronology;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;
import tw.gov.pcc.eip.dao.DeptsDao;
import tw.gov.pcc.eip.dao.EipcodeDao;
import tw.gov.pcc.eip.dao.MsgavaildepDao;
import tw.gov.pcc.eip.dao.MsgdataDao;
import tw.gov.pcc.eip.dao.MsgdepositDao;
import tw.gov.pcc.eip.dao.MsgdepositdirDao;
import tw.gov.pcc.eip.dao.StoredProcedureDao;
import tw.gov.pcc.eip.dao.UsersDao;
import tw.gov.pcc.eip.domain.Depts;
import tw.gov.pcc.eip.domain.Eipcode;
import tw.gov.pcc.eip.domain.Msgavaildep;
import tw.gov.pcc.eip.domain.Msgdata;
import tw.gov.pcc.eip.domain.Msgdeposit;
import tw.gov.pcc.eip.domain.Msgdepositdir;
import tw.gov.pcc.eip.domain.Users;
import tw.gov.pcc.eip.framework.domain.UserBean;
import tw.gov.pcc.eip.msg.cases.Eip01w010Case;
import tw.gov.pcc.eip.util.DateUtility;
import tw.gov.pcc.eip.util.ExceptionUtility;
import tw.gov.pcc.eip.util.ObjectUtility;

/**
 * 訊息上稿
 * 
 * @author vita
 *
 */
@Service
@Slf4j
public class Eip01w010Service {

    @Autowired
    private EipcodeDao eipCodeDao;
    @Autowired
    private DeptsDao deptsDao;
    @Autowired
    private UsersDao usersDao;
    @Autowired
    private MsgdataDao msgdataDao;
    @Autowired
    private MsgavaildepDao msgavaildepDao;
    @Autowired
    private MsgdepositDao msgdepositDao;
    @Autowired
    private MsgdepositdirDao msgdepositdirDao;
    @Autowired
    private StoredProcedureDao storedProcedureDao;
    DateTimeFormatter minguoDateTimeformatter = DateTimeFormatter.ofPattern("yyy/MM/dd HH:mm:ss")
            .withChronology(MinguoChronology.INSTANCE).withLocale(Locale.TAIWAN);
    DateTimeFormatter minguoDateformatter = DateTimeFormatter.ofPattern("yyy/MM/dd")
            .withChronology(MinguoChronology.INSTANCE).withLocale(Locale.TAIWAN);

    /**
     * 初始化下拉選單
     * 
     * @param caseData
     */
    public void initOptions(Eip01w010Case caseData) {
        Comparator<Eipcode> customComparator = new Comparator<Eipcode>() {
            @Override
            public int compare(Eipcode o1, Eipcode o2) {
                if (Integer.parseInt(o1.getCodeno()) > Integer.parseInt(o2.getCodeno()))
                    return 1;
                else
                    return -1;
            }
        };

        // 頁面型態
        List<Eip01w010Case.Option> pagetypes = eipCodeDao.findByCodeKind("PAGETYPE").stream()
                .sorted(Comparator.comparing(Eipcode::getCodeno)).map(m -> {
                    Eip01w010Case.Option option = new Eip01w010Case.Option();
                    option.setCodeno(m.getCodeno());
                    option.setCodename(m.getCodename());
                    return option;
                }).collect(Collectors.toCollection(ArrayList::new));
        caseData.setPagetypes(pagetypes);

        // 狀態
        List<Eipcode> status = eipCodeDao.findByCodeKind("MSGSTATUS");
        List<Eip01w010Case.Option> statuses1 = status.stream()
                .filter(f -> !StringUtils.equalsAny(f.getCodeno(), "2", "3", "5", "X"))
                .sorted(Comparator.comparing(Eipcode::getCodeno)).map(m -> {
                    Eip01w010Case.Option option = new Eip01w010Case.Option();
                    option.setCodeno(m.getCodeno());
                    option.setCodename(m.getCodename());
                    return option;
                }).collect(Collectors.toCollection(ArrayList::new));
        caseData.setStatuses1(statuses1);

        // 狀態
        List<Eip01w010Case.Option> statuses2 = status.stream()
                .filter(f -> StringUtils.equalsAny(f.getCodeno(), "4", "X"))
                .sorted(Comparator.comparing(Eipcode::getCodeno)).map(m -> {
                    Eip01w010Case.Option option = new Eip01w010Case.Option();
                    option.setCodeno(m.getCodeno());
                    option.setCodename(m.getCodename());
                    return option;
                }).collect(Collectors.toCollection(ArrayList::new));
        caseData.setStatuses2(statuses2);

        // 屬性
        List<Eip01w010Case.Option> attrtypes = eipCodeDao.findByCodeKind("ATTRIBUTYPE").stream()
                .sorted(customComparator).map(m -> {
                    Eip01w010Case.Option option = new Eip01w010Case.Option();
                    option.setCodeno(m.getCodeno());
                    option.setCodename(m.getCodename());
                    return option;
                }).collect(Collectors.toCollection(ArrayList::new));
        caseData.setAttrtypes(attrtypes);

        // 顯示位置
        List<Eip01w010Case.Option> locateareas = eipCodeDao.findByCodeKind("LOCATEAREA").stream()
                .sorted(Comparator.comparing(Eipcode::getCodeno)).map(m -> {
                    Eip01w010Case.Option option = new Eip01w010Case.Option();
                    option.setCodeno(m.getCodeno());
                    option.setCodename(m.getCodename());
                    return option;
                }).collect(Collectors.toCollection(ArrayList::new));
        caseData.setLocateareas(locateareas);

        // 分眾
        List<Depts> depts = deptsDao.getEip01wDepts();
        List<Eip01w010Case.Option> availabledeps = depts.stream().map(m -> {
            Eip01w010Case.Option option = new Eip01w010Case.Option();
            option.setCodeno(m.getDept_id());
            option.setCodename(m.getDept_name());
            return option;
        }).collect(Collectors.toCollection(ArrayList::new));
        caseData.setAvailabledeps(availabledeps);

        // 聯絡單位
        List<Eip01w010Case.Option> contactunits = depts.stream().filter(f -> !"00".equals(f.getDept_id())).map(m -> {
            Eip01w010Case.Option option = new Eip01w010Case.Option();
            option.setCodeno(m.getDept_id());
            option.setCodename(m.getDept_name());
            return option;
        }).collect(Collectors.toCollection(ArrayList::new));
        caseData.setContactunits(contactunits);

        // 聯絡人
        List<Eip01w010Case.Option> users = usersDao.selectAll().stream().filter(f -> "Y".equals(f.getAcnt_is_valid()))
                .map(m -> {
                    Eip01w010Case.Option option = new Eip01w010Case.Option();
                    option.setCodeno(m.getUser_id());
                    option.setCodename(m.getUser_name());
                    return option;
                }).collect(Collectors.toCollection(ArrayList::new));
        caseData.setContactpersons(users);
    }

    /**
     * 依畫面選擇的屬性 查詢訊息類別
     * 
     * @param attr
     * @return
     */
    public Map<String, String> getMsgtype(String attr) {
        // 訊息類別
        Map<String, String> map = eipCodeDao.findByCodekindScodekindOrderByCodeno("MESSAGETYPE", attr).stream()
                .collect(Collectors.toMap(Eipcode::getCodeno, Eipcode::getCodename));
        return ObjectUtility.normalizeObject(map);
    }

    /**
     * 填入欄位預設值
     * 
     * @param caseData
     */
    public void setDefaultValue(Eip01w010Case caseData, UserBean userData) {
        String mode = caseData.getMode();
        if ("".equals(mode)) {
            caseData.setCreatid(""); // 建立人員
            caseData.setPagetype(""); // 頁面型態
            caseData.setSubject(""); // 主旨
            caseData.setStatus(""); // 狀態
        } else if ("I".equals(mode)) {
            caseData.setStatus("0");
            caseData.setStatusText("0-處理中");
            caseData.setLocatearea("");
            caseData.setAvailabledep("");
            caseData.setIssearch("2");
            caseData.setIstop("2");
            caseData.setIsfront("2");
            caseData.setContactunit(userData.getDeptId());
            caseData.setContactperson(userData.getUserId());
            caseData.setContacttel(userData.getTel2());
            caseData.setUserId(userData.getUserId());
            caseData.setTmpContactunit(userData.getDeptId());
            caseData.setTmpContacttel(userData.getTel2());
        }
    }

    /**
     * 查詢列表 若只有一筆則導向明細頁
     * 
     * @param caseData
     */
    public void getQueryList(Eip01w010Case caseData) {
        String p1id = caseData.getP1id();
        String p1page = StringUtils.trimToEmpty(caseData.getP1page());
        String p1title = caseData.getP1title();
        String status = caseData.getP1status();
        String attributype = caseData.getP1attributype();
        List<Msgdata> result = msgdataDao.findbyCreatidPagetype(p1id, p1page, p1title, status, attributype);
        // 0筆 1筆 多筆
        if (!CollectionUtils.isEmpty(result)) {
            List<Eip01w010Case.Detail> details = result.stream().map(m -> {
                Eip01w010Case.Detail detail = new Eip01w010Case.Detail();
                detail.setCreatid(m.getCreatid());
                detail.setFseq(m.getFseq());
                detail.setPagetype("A".equals(m.getPagetype()) ? "文章" : "連結");
                detail.setSubject(m.getSubject());
                detail.setIsfront("1".equals(m.getIsfront()) ? "是" : "否");
                detail.setReleasedt(m.getReleasedt());
                detail.setOfftime(m.getOfftime());
                detail.setStatus(m.getStatus());
                detail.setStatusText(changeStatusText(m.getStatus(), false));
                detail.setAttributype(m.getAttributype());
                Optional<Eipcode> opt = eipCodeDao.findByCodeKindCodeNo("ATTRIBUTYPE", m.getAttributype());
                if (opt.isPresent()) {
                    detail.setAttributypeText(opt.get().getCodename());
                }
                detail.setCreatname(m.getCreatname());
                return detail;
            }).collect(Collectors.toCollection(ArrayList::new));
            caseData.setQueryList(details);
        } else {
            caseData.setQueryList(null);
        }
    }

    /**
     * 取得單一資料明細
     * 
     * @param caseData
     */
    public void getOne(Eip01w010Case caseData) {
        String fseq = caseData.getFseq();
        Msgdata msgdata = msgdataDao.findbyfseq(fseq);
        caseData.setFseq(msgdata.getFseq()); // 項次
        caseData.setPagetype(msgdata.getPagetype()); // 頁面型態
        caseData.setStatus(msgdata.getStatus()); // 狀態
        caseData.setStatusText(changeStatusText(msgdata.getStatus(), true)); // 狀態
        caseData.setAttributype(msgdata.getAttributype()); // 屬性
        caseData.setMsgtype(msgdata.getMsgtype()); // 訊息類別
        caseData.setLocatearea(msgdata.getLocatearea()); // 顯示位置
        // 分眾
        String ava = msgavaildepDao.findbyfseq(fseq).stream().map(m -> StringUtils.trim(m.getAvailabledep()))
                .collect(Collectors.joining(","));
        caseData.setAvailabledep(ava);
        caseData.setIssearch(msgdata.getIssearch()); // 是否提供外部查詢
        caseData.setShoworder(msgdata.getShoworder()); // 顯示順序
        caseData.setIstop(msgdata.getIstop()); // 是否置頂
        caseData.setIsfront(msgdata.getIsfront()); // 前台是否顯示
        caseData.setSubject(msgdata.getSubject()); // 主旨/連結網址
        if ("A".equals(msgdata.getPagetype())) {
            caseData.setMcontent(msgdata.getMcontent()); // 內文
        } else {
            caseData.setMlink(msgdata.getMcontent()); // 連結網址
        }
        caseData.setIndir(msgdata.getIndir()); // 存放目錄

        caseData.setOplink(msgdata.getOplink()); // 是否需要另開視窗
        caseData.setReleasedt(DateUtility.changeDateTypeToChineseDate(StringUtils.trimToNull(msgdata.getReleasedt()))); // 上架日期
        caseData.setOfftime(DateUtility.changeDateTypeToChineseDate(StringUtils.trimToNull(msgdata.getOfftime()))); // 下架日期
        caseData.setContactunit(trim(msgdata.getContactunit())); // 連絡單位
        caseData.setContactperson(trim(msgdata.getContactperson())); // 聯絡人
        caseData.setContacttel(trim(msgdata.getContacttel())); // 連絡電話
        caseData.setMemo(msgdata.getMemo()); // 備註
        caseData.setOffreason(msgdata.getOffreason()); // 下架原因

        Function<String, String> userName = (userId) -> {
            if (userId != "") {
                Optional<Users> opt = Optional.ofNullable(usersDao.selectByKey(userId));
                if (opt.isPresent()) {
                    return opt.get().getUser_name();
                }
            }
            return "";
        };

        caseData.setCreatid(userName.apply(trim(msgdata.getCreatid()))); // 建立人員
        caseData.setCreatdt(msgdata.getCreatdt() == null ? "" : msgdata.getCreatdt().format(minguoDateTimeformatter)); // 建立時間
        caseData.setUpdid(userName.apply(trim(msgdata.getUpdid()))); // 更新人員
        caseData.setUpddt(msgdata.getUpddt() == null ? "" : msgdata.getUpddt().format(minguoDateTimeformatter)); // 更新時間
        // 取得附檔清單
        List<Msgdeposit> fileList = msgdepositDao.findbyfseq(Arrays.asList(msgdata.getFseq()));
        Map<String, String> image = fileList.stream().filter(f -> "2".equals(f.getFiletype()))
                .collect(Collectors.toMap(Msgdeposit::getSeq, Msgdeposit::getAttachfile, (n, o) -> n, LinkedHashMap::new));
        Map<String, String> file = fileList.stream().filter(f -> "1".equals(f.getFiletype()))
                .collect(Collectors.toMap(Msgdeposit::getSeq, Msgdeposit::getAttachfile, (n, o) -> n, LinkedHashMap::new));
        caseData.setImageFileNameMap(image);
        caseData.setFileNameMap(file);
    }

    /**
     * 取得選擇路徑按鈕的樹狀節點資料
     * 
     * @param attr
     * @return
     */
    public Eip01w010Case.Tree getTree(String attr, String deptId) {
        Eip01w010Case.Tree newTree = new Eip01w010Case.Tree();
        Msgdepositdir dir = msgdepositdirDao.getDefaultPath(attr, deptId);
        if (dir != null) {
            newTree.setDefaultKey(trim(dir.getExisthier()));
            newTree.setDefaultPath(dir.getFilepath());
        }
        List<Msgdepositdir> tree = msgdepositdirDao.getTreeByAttr(attr, "");

        int j = 1;
        List<String> treeStr = new ArrayList<>();
        treeStr.add("<ul>");
        for (int i = 0; i < tree.size(); i++) { // 前提:無重複路徑且已排序
            String current = StringUtils.trim(tree.get(i).getExisthier());
            if (i == tree.size() - 1) { // 最後一層 與上層比對
                String prev = StringUtils.trim(tree.get(i - 1).getExisthier());
                if (current.length() > prev.length() && StringUtils.contains(current, prev)) { // 子層
                    treeStr.add("<li id=\"" + current + "\" class=\"folder\" data=\"filepath:'"
                            + tree.get(i).getFilepath() + "'\">" + tree.get(i).getFilename());
                    treeStr.add("<ul>");
                } else if (prev.length() == current.length()) { // 同層
                    treeStr.add("<li id=\"" + current + "\" class=\"folder\" data=\"filepath:'"
                            + tree.get(i).getFilepath() + "'\">" + tree.get(i).getFilename());
                }
            } else { // 與下層比對
                String next = StringUtils.trim(tree.get(i + 1).getExisthier());
                if (next.length() > current.length() && StringUtils.contains(next, current)) { // 子層
                    treeStr.add("<li id=\"" + current + "\" class=\"folder\" data=\"filepath:'"
                            + tree.get(i).getFilepath() + "'\">" + tree.get(i).getFilename());
                    treeStr.add("<ul>");
                    j += 1;
                } else if (next.length() == current.length()) { // 同層
                    treeStr.add("<li id=\"" + current + "\" class=\"folder\" data=\"filepath:'"
                            + tree.get(i).getFilepath() + "'\">" + tree.get(i).getFilename());
                } else { // 上?層
                    int level = current.length() - next.length();
                    treeStr.add("<li id=\"" + current + "\" class=\"folder\" data=\"filepath:'"
                            + tree.get(i).getFilepath() + "'\">" + tree.get(i).getFilename());
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
        newTree.setTreeList(treeStr);
        return newTree;
    }

    /**
     * 一個聯絡單位只能新增一筆單位簡介(6)或業務資訊(7)
     * 
     * @param caseData
     * @param result
     */
    public void checkOptions(Eip01w010Case caseData, BindingResult result) {
        String fseq = caseData.getFseq();
        String pagetype = caseData.getPagetype();
        String attr = caseData.getAttributype();
        String mcontent = caseData.getMcontent();
        String mlink = caseData.getMlink();
        String contactunit = caseData.getContactunit();
        int cnt = msgdataDao.getEffectiveCount(attr, contactunit);
        if ("".equals(fseq)) {
            if ("6".equals(attr)) {
                if (cnt != 0) {
                    result.rejectValue("attributype", "", "該聯絡單位已存在一筆單位簡介資料，請修改「屬性」");
                }
            }
            if ("7".equals(attr)) {
                if (cnt != 0) {
                    result.rejectValue("attributype", "", "該聯絡單位已存在一筆業務資訊資料，請修改「屬性」");
                }
            }
        } else {
            Msgdata m = msgdataDao.findbyfseq(fseq);
            boolean isOwn = (m != null && StringUtils.equals(contactunit, trim(m.getContactunit())) && cnt == 1) ? true
                    : false;
            if ("6".equals(attr)) {
                if (cnt != 0 && !isOwn) {
                    result.rejectValue("attributype", "", "該聯絡單位已存在一筆單位簡介資料，請修改「屬性」");
                }
            }
            if ("7".equals(attr)) {
                if (cnt != 0 && !isOwn) {
                    result.rejectValue("attributype", "", "該聯絡單位已存在一筆業務資訊資料，請修改「屬性」");
                }
            }
        }
        if ("A".equals(pagetype) && StringUtils.isEmpty(mcontent)) {
            result.rejectValue("mcontent", "", "「內文」為必填");
        } else if ("B".equals(pagetype) && StringUtils.isEmpty(mlink)) {
            result.rejectValue("mlink", "", "「連結網址」為必填");
        }
    }
    /**
     * 新增資料
     * 
     * @param caseData
     */
    public void insert(Eip01w010Case caseData, String userId) {
        Msgdata m = new Msgdata();
        String newFseq = msgdataDao.getNextFseq();
        caseData.setFseq(newFseq);
        m.setFseq(newFseq); // 項次
        m.setPagetype(caseData.getPagetype()); // 頁面型態
        m.setStatus(caseData.getStatus()); // 狀態
        m.setAttributype(caseData.getAttributype()); // 屬性
        m.setMsgtype(caseData.getMsgtype()); // 訊息類別
        m.setLocatearea(caseData.getLocatearea()); // 顯示位置

        String ava = caseData.getAvailabledep(); // 分眾
        if (StringUtils.isNotBlank(ava)) {
            String[] avaArray = ava.split(",");
            for (String str : avaArray) {
                Msgavaildep a = new Msgavaildep();
                a.setFseq(newFseq);
                a.setAvailabledep(str);
                msgavaildepDao.insert(a);
            }
        }
        m.setIssearch(caseData.getIssearch()); // 是否提供外部查詢
        m.setShoworder(caseData.getShoworder()); // 顯示順序
        m.setIstop(caseData.getIstop()); // 是否置頂
        m.setIsfront(caseData.getIsfront()); // 前台是否顯示
        m.setSubject(caseData.getSubject()); // 主旨/連結網址
        m.setMcontent("A".equals(caseData.getPagetype()) ? caseData.getMcontent() : caseData.getMlink()); // 內文
        m.setOplink(caseData.getOplink()); // 是否需要另開視窗
        if ("A".equals(caseData.getPagetype()) && "4".equals(caseData.getAttributype())) {
            m.setIndir(caseData.getIndir()); // 存放目錄
            // 存放目錄
            insertMsgdepositdir(caseData.getAttributype(), caseData.getTmpPath(), caseData.getIndir());
        } else {
            m.setIndir(null);
        }

        m.setReleasedt(DateUtility.changeDateTypeToWestDate(caseData.getReleasedt())); // 上架時間
        m.setOfftime(DateUtility.changeDateTypeToWestDate(caseData.getOfftime())); // 下架時間
        m.setContactunit(caseData.getContactunit()); // 連絡單位
        m.setContactperson(caseData.getContactperson()); // 聯絡人
        m.setContacttel(caseData.getContacttel()); // 連絡電話
        m.setMemo(trimToNull(caseData.getMemo())); // 備註
        m.setOffreason(null); // 下架原因

        m.setCreatid(userId); // 建立人員
        LocalDateTime ldt = LocalDateTime.now();
        m.setCreatdt(ldt); // 建立時間
        m.setUpdid(null); // 更新人員
        m.setUpddt(null); // 更新時間
        msgdataDao.insert(m);

        Users user = usersDao.selectByKey(userId);
        caseData.setCreatid(user == null ? "" : user.getUser_name());
        caseData.setCreatdt(ldt.format(minguoDateTimeformatter));
    }

    /**
     * 取得路徑代號及新增 Msgdepositdir
     * 
     * @param attr
     * @param tmpPath
     * @param indir
     */
    public void insertMsgdepositdir(String attr, String tmpPath, String indir) {
        if (StringUtils.contains(tmpPath, "_")) {
            String newkey = "";
            String newsort = "";
            String newdir = "";
            String filename = "";
            String tmpArray[] = indir.split("/");
            String existPath = StringUtils.substring(tmpPath, 0, StringUtils.indexOf(tmpPath, "_"));
            List<Msgdepositdir> tree = msgdepositdirDao.getTreeByAttr(attr, existPath); // 查詢下層路徑
            if (CollectionUtils.isEmpty(tree)) { // 已存在的路徑下再新增的資料夾
                newkey = existPath + "A";
                // 取得上一層的最大的排序值
                String preMaxOrder = msgdepositdirDao
                        .getTreeByAttr(attr, StringUtils.substring(existPath, 0, existPath.length() - 1)).stream()
                        .sorted(Comparator.comparing(Msgdepositdir::getSortorder).reversed()).findFirst()
                        .map(m -> m.getSortorder()).orElse("");
                newsort = StringUtils.substring(preMaxOrder, 0, existPath.length() - 1) + "10"; // +1位數
                newdir = StringUtils.join(tmpArray, "/", 0, newkey.length() + 1);
                filename = tmpArray[newkey.length()];
            } else {
                // 找出該層目錄目前最後一個階層代號
                char lastChar = tree.stream().sorted(Comparator.comparing(Msgdepositdir::getExisthier).reversed())
                        .findFirst().map(m -> {
                            String existhier = StringUtils.trim(m.getExisthier());
                            return existhier.charAt(existhier.length() - 1);
                        }).get();
                newkey = existPath + (char) ((int) lastChar + 1);
                // 目前最大的排序值+10
                int maxOrder = tree.stream().sorted(Comparator.comparing(Msgdepositdir::getSortorder).reversed())
                        .findFirst().map(m -> Integer.valueOf(m.getSortorder())).orElse(0);
                newsort = String.valueOf(maxOrder + 10);
                newdir = StringUtils.join(tmpArray, "/", 0, newkey.length() + 1);
                filename = tmpArray[newkey.length()];
            }
            insertMsgdepositdir(attr, newkey, newsort, newdir, filename);
            int count = StringUtils.countMatches(tmpPath, "_"); // 還有?個新增的資料夾
            while (count > 1) {
                newsort = StringUtils.substring(newsort, 0, newkey.length() - 1) + "10"; // 子層 多一層編號
                newkey = newkey + "A";
                newdir = StringUtils.join(tmpArray, "/", 0, newkey.length() + 1);
                filename = tmpArray[newkey.length()];
                insertMsgdepositdir(attr, newkey, newsort, newdir, filename);
                count--;
            }
        }
    }

    /**
     * 新增 Msgdepositdir
     * 
     * @param attr
     * @param existhier
     * @param sortorder
     * @param filepath
     * @param filename
     */
    private void insertMsgdepositdir(String attr, String existhier, String sortorder, String filepath,
            String filename) {
        Msgdepositdir msgdepositdir = new Msgdepositdir();
        msgdepositdir.setFileseq(msgdepositdirDao.getNextFseq());
        msgdepositdir.setAttributype(attr);
        msgdepositdir.setExisthier(existhier);
        msgdepositdir.setSortorder(sortorder);
        msgdepositdir.setFilepath(filepath);
        msgdepositdir.setFilename(filename);
        msgdepositdir.setDisable(null);
        msgdepositdir.setCreatid(null);
        msgdepositdir.setCreatdt(null);
        msgdepositdirDao.insert(msgdepositdir);
    }

    /**
     * 更新資料
     * 
     * @param caseData
     * @param userId
     */
    public void update(Eip01w010Case caseData, String userId) {
        String dir = eipCodeDao.findByCodeKindCodeNo("FILEDIR", "1").get().getCodename() + "\\"; // 檔案所在資料夾

        String fseq = caseData.getFseq();
        Msgdata m = msgdataDao.findbyfseq(fseq);

        m.setStatus(caseData.getStatus()); // 狀態
        m.setAttributype(caseData.getAttributype()); // 屬性
        m.setMsgtype(caseData.getMsgtype()); // 訊息類別
        m.setLocatearea(caseData.getLocatearea()); // 顯示位置

        String ava = caseData.getAvailabledep(); // 分眾
        if (StringUtils.isNotBlank(ava)) {
            Msgavaildep mav = new Msgavaildep();
            mav.setFseq(fseq);
            msgavaildepDao.delete(mav);
            String[] avaArray = ava.split(",");
            for (String str : avaArray) {
                Msgavaildep a = new Msgavaildep();
                a.setFseq(fseq);
                a.setAvailabledep(str);
                msgavaildepDao.insert(a);
            }
        }
        m.setIssearch(caseData.getIssearch()); // 是否提供外部查詢
        m.setShoworder(caseData.getShoworder()); // 顯示順序
        m.setIstop(caseData.getIstop()); // 是否置頂
        m.setIsfront(caseData.getIsfront()); // 前台是否顯示
        m.setSubject(caseData.getSubject()); // 主旨/連結網址
        if ("B".equals(caseData.getPagetype())) {
            m.setMcontent(caseData.getMlink()); // 內文
            m.setIndir(null); // 存放目錄

            if ("A".equals(m.getPagetype())) {
                List<Msgdeposit> files = msgdepositDao.findbyfseq(Arrays.asList(fseq));
                files.stream().forEach(origin -> {
                    File file = new File(dir + origin.getRealfilename());
                    file.delete();
                    Msgdeposit ms = new Msgdeposit();
                    ms.setFseq(origin.getFseq());
                    ms.setSeq(origin.getSeq());
                    msgdepositDao.delete(ms); // 刪附檔
                });
            }
        } else {
            m.setMcontent(trimToNull(caseData.getMcontent())); // 內文
        }
        if ("4".equals(caseData.getAttributype())) {
            m.setIndir(caseData.getIndir()); // 存放目錄
            // 新增存放目錄
            insertMsgdepositdir(caseData.getAttributype(), caseData.getTmpPath(), caseData.getIndir());
        }
        m.setPagetype(caseData.getPagetype()); // 頁面型態
        m.setOplink(caseData.getOplink()); // 是否需要另開視窗

        m.setReleasedt(DateUtility.changeDateTypeToWestDate(caseData.getReleasedt())); // 上架時間
        m.setOfftime(DateUtility.changeDateTypeToWestDate(caseData.getOfftime())); // 下架時間
        m.setContactunit(caseData.getContactunit()); // 連絡單位
        m.setContactperson(caseData.getContactperson()); // 聯絡人
        m.setContacttel(caseData.getContacttel()); // 連絡電話
        m.setMemo(trimToNull(caseData.getMemo())); // 備註
        m.setOffreason(trimToNull(caseData.getOffreason())); // 下架原因

        m.setUpdid(userId); // 更新人員
        LocalDateTime ldt = LocalDateTime.now();
        m.setUpddt(ldt); // 更新時間
        msgdataDao.update(m, fseq);

        Users user = usersDao.selectByKey(userId);
        caseData.setUpdid(user == null ? "" : user.getUser_name());
        caseData.setUpddt(ldt.format(minguoDateTimeformatter));
        if ("D".equals(caseData.getMode())) {
            getQueryList(caseData);
            caseData.setKeep((caseData.getQueryList() != null && caseData.getQueryList().size() == 1) ? true : false);
        }
    }

    /**
     * 更新狀態
     * 
     * @param caseData
     */
    public void updStatus(Eip01w010Case caseData, String status) {
        List<String> fseqs = null;
        if ("".equals(caseData.getFseq())) { // 列表頁 批次修改狀態
            List<Eip01w010Case.Detail> qry = caseData.getQueryList();
            fseqs = qry.stream().filter(f -> f.isCheck()).map(m -> m.getFseq())
                    .collect(Collectors.toCollection(ArrayList::new));
        } else { // 明細頁
            fseqs = Arrays.asList(caseData.getFseq());
        }
        if (!"X".equals(status)) {
            // 更新
            msgdataDao.updateStatus(fseqs, status);
        } else {
            // 實體刪除
            String dir = eipCodeDao.findByCodeKindCodeNo("FILEDIR", "1").get().getCodename() + "\\"; // 檔案所在資料夾
            fseqs.stream().forEach(f -> {
                Msgdata data = new Msgdata();
                data.setFseq(f);
                msgdataDao.delete(data); // 刪文章
                Msgavaildep dep = new Msgavaildep();
                dep.setFseq(f);
                msgavaildepDao.delete(dep); // 刪分眾
                List<Msgdeposit> files = msgdepositDao.findbyfseq(Arrays.asList(f));
                files.stream().forEach(origin -> {
                    File file = new File(dir + origin.getRealfilename());
                    file.delete();
                    Msgdeposit m = new Msgdeposit();
                    m.setFseq(origin.getFseq());
                    m.setSeq(origin.getSeq());
                    msgdepositDao.delete(m); // 刪附檔
                });
            });
        }
        // 返回
        getQueryList(caseData);
        caseData.setKeep((caseData.getQueryList() != null && caseData.getQueryList().size() == 1) ? true : false);
        caseData.setStatus(status);
        caseData.setStatusText(changeStatusText(status, true));
    }

    private String changeStatusText(String status, boolean addNum) {
        String statusText = "";
        switch (status) {
        case "0":
            statusText = "處理中";
            break;
        case "1":
            statusText = "已上稿";
            break;
        case "4":
            statusText = "已上架";
            break;
        case "5":
            statusText = "已下架";
            break;
        case "X":
            statusText = "註銷";
            break;
        }
        return addNum ? status + "-" + statusText : statusText;
    }

    /**
     * 新增&上傳附檔
     * 
     * @param caseData
     */
    public void insertDataUploadFiles(Eip01w010Case caseData) {
        if ("B".equals(caseData.getPagetype())) return;
        String fseq = caseData.getFseq();
        List<MultipartFile> imagesList = Arrays.stream(caseData.getImages())
                .filter(f -> StringUtils.isNotBlank(f.getOriginalFilename())).collect(Collectors.toList());
        List<MultipartFile> filesList = Arrays.stream(caseData.getFiles())
                .filter(f -> StringUtils.isNotBlank(f.getOriginalFilename())).collect(Collectors.toList());

        // 先找已上傳數量 再找最大編號往下編
        Msgdeposit i = msgdepositDao.getCnt(fseq, "2");
        Msgdeposit e = msgdepositDao.getCnt(fseq, "1");
        uploadFiles(fseq, i.getCnt(), imagesList);
        uploadFiles(fseq, e.getCnt(), filesList);
        // 重新取得附檔清單
        List<Msgdeposit> fileList = msgdepositDao.findbyfseq(Arrays.asList(fseq));
        Map<String, String> image = fileList.stream().filter(f -> "2".equals(f.getFiletype()))
                .collect(Collectors.toMap(Msgdeposit::getSeq, Msgdeposit::getAttachfile, (n, o) -> n, LinkedHashMap::new));
        Map<String, String> file = fileList.stream().filter(f -> "1".equals(f.getFiletype()))
                .collect(Collectors.toMap(Msgdeposit::getSeq, Msgdeposit::getAttachfile, (n, o) -> n, LinkedHashMap::new));
        caseData.setImageFileNameMap(image);
        caseData.setFileNameMap(file);
    }

    /**
     * 上傳檔案
     * 
     * @param fseq
     * @param cnt
     * @param seq
     * @param fileList
     */
    private void uploadFiles(String fseq, int cnt, List<MultipartFile> fileList) {
        String dir = eipCodeDao.findByCodeKindCodeNo("FILEDIR", "1").get().getCodename() + File.separator; // 檔案所在資料夾

        Comparator<Msgdeposit> customComparator = new Comparator<Msgdeposit>() {
            @Override
            public int compare(Msgdeposit o1, Msgdeposit o2) {
                if (Integer.parseInt(o1.getSeq()) < Integer.parseInt(o2.getSeq()))
                    return 1;
                else
                    return -1;
            }
        };
        int seq = msgdepositDao.findbyfseq(Arrays.asList(fseq)).stream()
                .sorted(customComparator).findFirst()
                .map(m -> Integer.valueOf(m.getSeq())).orElse(0);
        for (MultipartFile f : fileList) {
            String originFileName = f.getOriginalFilename();
            String fileExtension = StringUtils.substring(originFileName, originFileName.indexOf(".") + 1,
                    originFileName.length());

            if (cnt < 20) {
                String formatFileName = fseq + "_" + (++seq) + "_" + originFileName;
                Msgdeposit m = new Msgdeposit();
                m.setFseq(fseq);
                m.setSeq(String.valueOf(seq));
                m.setFiletype(StringUtils.contains(f.getContentType(), "image") ? "2" : "1");
                m.setAttachfile(originFileName); // 原始檔名
                m.setAttachtype(fileExtension); // 副檔名
                m.setRealfilename(formatFileName); // 格式化檔名
                msgdepositDao.insert(m);
                try {
                    File file = new File(dir + formatFileName);
                    f.transferTo(file);
                } catch (Exception e) {
                    log.error(dir + formatFileName + "檔案上傳失敗", ExceptionUtility.getStackTrace(e));
                }
            }
            cnt++;
        }
    }

    /**
     * 刪除附檔
     * 
     * @param fseq
     * @param seq
     */
    public void deleteFile(String fseq, String seq) {
        Msgdeposit origin = msgdepositDao.findbyPk(fseq, seq);
        String dir = eipCodeDao.findByCodeKindCodeNo("FILEDIR", "1").get().getCodename() + File.separator; // 檔案所在資料夾
        File file = new File(dir + origin.getRealfilename());
        file.delete();

        Msgdeposit m = new Msgdeposit();
        m.setFseq(fseq);
        m.setSeq(seq);
        msgdepositDao.delete(m);
    }

    /**
     * 取得預覽資料
     * 
     * @param fseq
     * @return
     */
    public Eip01w010Case.preView query(String fseq) {
        Msgdata m = msgdataDao.findbyfseq(fseq);
        if (m != null) {
            Eip01w010Case.preView detail = new Eip01w010Case.preView();
            String attr = "";
            Optional<Eipcode> opt = eipCodeDao.findByCodeKindCodeNo("ATTRIBUTYPE", m.getAttributype());
            if (opt.isPresent()) {
                attr = opt.get().getCodename();
            }
            detail.setAttributype(attr);
            detail.setSubject(m.getSubject());
            detail.setMcontent(StringUtils.replace(m.getMcontent(), "\r\n", "<br>"));
            Depts dept = deptsDao.findByPk(trim(m.getContactunit()));
            detail.setContactunit(dept == null ? "" : dept.getDept_name());
            detail.setUpddt(m.getUpddt() == null ? "" : m.getUpddt().format(minguoDateformatter));
            Users user = usersDao.selectByKey(trim(m.getContactperson()));
            detail.setContactperson(user == null ? "" : user.getUser_name());
            detail.setContacttel(trim(m.getContacttel()));
            // 附檔
            List<Msgdeposit> files = msgdepositDao.findbyfseq(Arrays.asList(fseq));
            if (!CollectionUtils.isEmpty(files)) {
                detail.setFile(files.stream().collect(Collectors.toMap(Msgdeposit::getSeq, Msgdeposit::getAttachfile,
                        (n, o) -> n, LinkedHashMap::new)));
            }
            detail.setPagetype(m.getPagetype());
            return detail;
        }
        return null;
    }

    /**
     * msgdata更新後呼叫排程檢查是否狀態應為上架或下架
     */
    public void callSp_Eip01w010() {
        SqlParameterSource param = new MapSqlParameterSource();
        storedProcedureDao.callProcedure("USP_EIP01W010B", param);
    }
}
