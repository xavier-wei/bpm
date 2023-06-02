package tw.gov.pcc.eip.services;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import tw.gov.pcc.common.util.DateUtil;
import tw.gov.pcc.eip.common.cases.*;
import tw.gov.pcc.eip.dao.OrclassDao;
import tw.gov.pcc.eip.dao.OrformdataDao;
import tw.gov.pcc.eip.dao.OrmodihisDao;
import tw.gov.pcc.eip.dao.OrresultDao;
import tw.gov.pcc.eip.domain.*;
import tw.gov.pcc.eip.framework.domain.UserBean;
import tw.gov.pcc.eip.report.Eip00w420L00;
import tw.gov.pcc.eip.report.Eip00w420L02;
import tw.gov.pcc.eip.util.BeanUtility;
import tw.gov.pcc.eip.util.DateUtility;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.chrono.MinguoChronology;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 線上報名列表
 *
 * @author Weith
 */
@Service
public class Eip00w420Service extends OnlineRegService {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(Eip00w420Service.class);
    @Autowired
    UserBean userData;
    @Autowired
    OrformdataDao orformdataDao;
    @Autowired
    OrclassDao orclassDao;
    @Autowired
    OrmodihisDao ormodihisDao;
    @Autowired
    OrresultDao orresultDao;
    DateTimeFormatter minguoformatter = DateTimeFormatter.ofPattern("yyy/MM/dd HH:mm")
            .withChronology(MinguoChronology.INSTANCE)
            .withLocale(Locale.TAIWAN);

    DateTimeFormatter minguoformatterForInput = DateTimeFormatter.ofPattern("yyyMMdd")
            .withChronology(MinguoChronology.INSTANCE)
            .withLocale(Locale.TAIWAN);

    DateTimeFormatter westdatetimeformatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");

    DateTimeFormatter timeformatter = DateTimeFormatter.ofPattern("HH:mm");

    /**
     * 初始化下拉選單
     * @param caseData
     */
    public void initCombobox(Eip00w420Case caseData) {
        Map<Long,String>orccodeMap = orclassDao.getAll().stream()
                .collect(Collectors.toMap(Orclass::getOrccode, Orclass::getOrcname));//TODO 如果有要按照順序排列需要再調整
        caseData.setStatusCombobox(getStatusMap());
        caseData.setRegstatusCombobox(getRegStatusMap());
        if (StringUtils.defaultString(caseData.getMode()).matches("A|U")) {
            caseData.setRegisqualCheckboxU(getRegisqualDept());
            caseData.setRegisqualCheckboxE1(getRegisqualE1());
            caseData.setRegisqualCheckboxE2(getRegisqualE2());
            caseData.setRegisqualCheckboxE3(getRegisqualE3());
            caseData.setRegisqualCheckboxE4(getRegisqualE4());
            caseData.setOrccodeCombobox(orccodeMap);
        }
    }

    /**
     * 取得畫面上的報名列表
     * @param caseData
     */
    public void getOrlist(Eip00w420Case caseData, boolean isSearch) {
        Map<Long, String> orclassMap = orclassDao.getAll().stream()
                .collect(Collectors.toMap(Orclass::getOrccode, Orclass::getOrcname));
        Map<String, String> statusMap = getStatusMap();
        List<Orformdata> orformdataList = new ArrayList<>();
        if (isSearch) {
            Orformdata orformdata = new Orformdata();
            orformdata.setTopicname(caseData.getQKeyword());
            orformdata.setStatus(StringUtils.defaultIfEmpty(caseData.getQStatus(), null));
            orformdata.setRegisfmdtStr(DateUtility.changeChineseYearMonthType(caseData.getQStartYear()+caseData.getQStartMonth()));
            orformdata.setRegisendtStr(DateUtility.changeChineseYearMonthType(caseData.getQEndYear()+caseData.getQEndMonth()));
            orformdataList = orformdataDao.getListByMultiCondition(orformdata);
        } else {
            orformdataList = orformdataDao.getAll();
        }
        List<Eip00w420Case.OrCase> list = orformdataList.stream().map(t -> {
            StringBuilder actualsb = new StringBuilder();
            StringBuilder passsb = new StringBuilder();
            Eip00w420Case.OrCase orCase = new Eip00w420Case.OrCase();
            List<Orresult>resultList = orresultDao.getDataByOrformno(t.getOrformno(),"D");
            List<Orresult>resultPassList = orresultDao.getDataByMultiCondition(t.getOrformno(),"","Y");
            // 開放的報名資格LIST
            List<String> regisqualList =
                    eipcodeDao.findByCodeKindAndList("REGISQUAL",Arrays.asList(StringUtils.split(t.getRegisqual(),",")));
            Map<String, String> actualappnumMap = new LinkedHashMap<>();
            Map<String, String> passnumMap = new LinkedHashMap<>();
            orCase.setOrformno(t.getOrformno());
            orCase.setTopicname(t.getTopicname());
            orCase.setOrcname(orclassMap.get(t.getOrccode()));
            orCase.setStatus(statusMap.get(t.getStatus()));
            orCase.setStatusVal(t.getStatus());
            orCase.setAcceptappnum(t.getAcceptappnum());
            actualappnumMap = resultList.stream()
                    .collect(Collectors.groupingBy(
                            Orresult::getDept,
                            Collectors.collectingAndThen(
                                    Collectors.counting(),
                                    count -> String.valueOf(count))));
            for (String str : regisqualList) {
                if (ObjectUtils.isNotEmpty(actualappnumMap.get(str))) {
                    actualsb.append(str+":"+actualappnumMap.get(str)+"人");
                } else {
                    actualsb.append(str+":0人");
                }
                actualsb.append("&#10;");
            }
            orCase.setActualappnumAbbr(actualsb.toString());
            orCase.setActualappnum(t.getActualappnum());// 讀結果檔的報名人數
            passnumMap = resultPassList.stream()
                    .collect(Collectors.groupingBy(
                            Orresult::getDept,
                            Collectors.collectingAndThen(
                                    Collectors.counting(),
                                    count -> String.valueOf(count))));
            for (String str : regisqualList) {
                if (ObjectUtils.isNotEmpty(passnumMap.get(str))) {
                    passsb.append(str+":"+passnumMap.get(str)+"人");
                } else {
                    passsb.append(str+":0人");
                }
                passsb.append("&#10;");
            }
            orCase.setPassnumAbbr(passsb.toString());
            orCase.setPassnum(t.getPassnum());// 讀結果檔的審核通過人數
            orCase.setRegisfmdt(t.getRegisfmdt().format(minguoformatter));
            orCase.setRegisendt(t.getRegisendt().format(minguoformatter));
            return orCase;
        }).collect(Collectors.toList());
        caseData.setOrList(list);
        //清除自動回填
        caseData.setOrformnoList(new ArrayList<>());
    }

    /**
     * 取得要新增的表單資料
     * @param modifyCase
     */
    public void getInsertFormData(Eip00w420ModifyCase modifyCase) {
        Map<String,String>map = getMail();
        modifyCase.setSubject(map.get("1"));
        modifyCase.setPassmsg(map.get("2"));
        modifyCase.setRejectmst(map.get("3"));
    }

    /**
     * 取得要修改的表單資料
     * @param caseData
     * @param modifyCase
     */
    public void getSingleFormData(Eip00w420Case caseData, Eip00w420ModifyCase modifyCase) {
        Orformdata orformdata = orformdataDao.findByPk(caseData.getOrformno());
        modifyCase.setOrformno(caseData.getOrformno());
        modifyCase.setOrccode(String.valueOf(orformdata.getOrccode()));
        modifyCase.setCourseclacode(String.valueOf(orformdata.getCourseclacode()));
        modifyCase.setCoursecode(orformdata.getCoursecode());
        modifyCase.setClasscode(orformdata.getClasscode());
        modifyCase.setPeriod(String.valueOf(orformdata.getPeriod()));
        modifyCase.setTopicname(orformdata.getTopicname());
        modifyCase.setRegisfmdt(orformdata.getRegisfmdt().format(minguoformatterForInput));
        modifyCase.setRegisfmdtHour(StringUtils.substringBefore(orformdata.getRegisfmdt().format(timeformatter),":"));
        modifyCase.setRegisfmdtMinute(StringUtils.substringAfter(orformdata.getRegisfmdt().format(timeformatter),":"));
        modifyCase.setRegisendt(orformdata.getRegisendt().format(minguoformatterForInput));
        modifyCase.setRegisendtHour(StringUtils.substringBefore(orformdata.getRegisendt().format(timeformatter),":"));
        modifyCase.setRegisendtMinute(StringUtils.substringAfter(orformdata.getRegisendt().format(timeformatter),":"));
        modifyCase.setOrganizer(orformdata.getOrganizer());
        modifyCase.setContacter(orformdata.getContacter());
        modifyCase.setContactnum(orformdata.getContactnum());
        modifyCase.setFax(orformdata.getFax());
        modifyCase.setEmail(orformdata.getEmail());
        modifyCase.setAddres(orformdata.getAddres());
        modifyCase.setCountry(orformdata.getCountry());
        modifyCase.setProfmdt(orformdata.getProfmdt().format(minguoformatterForInput));
        modifyCase.setProfmdtHour(StringUtils.substringBefore(orformdata.getProfmdt().format(timeformatter),":"));
        modifyCase.setProfmdtMinute(StringUtils.substringAfter(orformdata.getProfmdt().format(timeformatter),":"));
        modifyCase.setProendt(orformdata.getProendt().format(minguoformatterForInput));
        modifyCase.setProendtHour(StringUtils.substringBefore(orformdata.getProendt().format(timeformatter),":"));
        modifyCase.setProendtMinute(StringUtils.substringAfter(orformdata.getProendt().format(timeformatter),":"));
        modifyCase.setAcceptappnum(String.valueOf(orformdata.getAcceptappnum()));
        modifyCase.setAllowappnum(String.valueOf(orformdata.getAllowappnum()));
        modifyCase.setAllowappway(Arrays.asList(orformdata.getAllowappway().split(",")));
        modifyCase.setFee(String.valueOf(ObjectUtils.defaultIfNull(orformdata.getFee(), "")));
        modifyCase.setAccount(orformdata.getAccount());
        modifyCase.setIsmeals(orformdata.getIsmeals());
        modifyCase.setClasshours(StringUtils.substring(orformdata.getClasshours(),0,orformdata.getClasshours().length() - 1));
        modifyCase.setClasshoursUnit(StringUtils.substring(orformdata.getClasshours(),orformdata.getClasshours().length() - 1));
        modifyCase.setCertihours(orformdata.getCertihours());
        modifyCase.setLecturercode(orformdata.getLecturercode());
        modifyCase.setPassmsg(orformdata.getPassmsg());
        modifyCase.setRejectmst(orformdata.getRejectmst());
        modifyCase.setRegisqual(Arrays.asList(orformdata.getRegisqual().split(",")));
        modifyCase.setTopicdesc(orformdata.getTopicdesc());
        modifyCase.setRemark(orformdata.getRemark());
    }

    /**
     * 新增線上報名表單資料
     * @param modifyCaseData
     */
    public void insertUpdateOrfData(Eip00w420ModifyCase modifyCaseData, String mode) throws IOException {
        Orformdata orformdata = new Orformdata();
        Ormodihis ormodihis = new Ormodihis();
        if ("A".equals(mode)) {
            String orformno = orformdataDao.getMaximumOrformno(DateUtility.getNowChineseYearMonth());
            orformdata.setOrformno("OR" + DateUtility.getNowChineseYearMonth() + (StringUtils.isBlank(orformno) ? "0001" :  StringUtils.leftPad(orformno, 4, "0")));
        }
        orformdata.setOrccode(Long.valueOf(modifyCaseData.getOrccode()));
        orformdata.setCourseclacode(Long.valueOf(modifyCaseData.getCourseclacode()));
        orformdata.setCoursecode(modifyCaseData.getCoursecode());
        orformdata.setClasscode(modifyCaseData.getClasscode());
        orformdata.setPeriod(Long.valueOf(modifyCaseData.getPeriod()));
        orformdata.setTopicname(modifyCaseData.getTopicname());
        orformdata.setRegisfmdt(LocalDateTime.of(DateUtility.westDateToLocalDate(modifyCaseData.getRegfyyyymmdd()), LocalTime.of(Integer.parseInt(modifyCaseData.getRegisfmdtHour()),Integer.parseInt(modifyCaseData.getRegisfmdtMinute()))));
        orformdata.setRegisendt(LocalDateTime.of(DateUtility.westDateToLocalDate(modifyCaseData.getRegeyyyymmdd()), LocalTime.of(Integer.parseInt(modifyCaseData.getRegisendtHour()),Integer.parseInt(modifyCaseData.getRegisendtMinute()))));
        orformdata.setOrganizer(modifyCaseData.getOrganizer());
        orformdata.setContacter(modifyCaseData.getContacter());
        orformdata.setContactnum(modifyCaseData.getContactnum());
        orformdata.setFax(modifyCaseData.getFax());
        orformdata.setEmail(modifyCaseData.getEmail());
        orformdata.setAddres(modifyCaseData.getAddres());
        orformdata.setCountry(modifyCaseData.getCountry());
        orformdata.setProfmdt(LocalDateTime.of(DateUtility.westDateToLocalDate(modifyCaseData.getProfyyyymmdd()), LocalTime.of(Integer.parseInt(modifyCaseData.getProfmdtHour()),Integer.parseInt(modifyCaseData.getProfmdtMinute()))));
        orformdata.setProendt(LocalDateTime.of(DateUtility.westDateToLocalDate(modifyCaseData.getProeyyyymmdd()), LocalTime.of(Integer.parseInt(modifyCaseData.getProendtHour()),Integer.parseInt(modifyCaseData.getProendtMinute()))));
        orformdata.setAcceptappnum(Long.valueOf(modifyCaseData.getAcceptappnum()));
        orformdata.setAllowappnum(Long.valueOf(modifyCaseData.getAllowappnum()));
        orformdata.setAllowappway(StringUtils.join(modifyCaseData.getAllowappway(), ","));
        orformdata.setFee(StringUtils.isBlank(modifyCaseData.getFee()) ? null : new BigDecimal(modifyCaseData.getFee()));
        orformdata.setAccount(modifyCaseData.getAccount());
        orformdata.setIsmeals(modifyCaseData.getIsmeals());
        orformdata.setClasshours(modifyCaseData.getClasshours() + modifyCaseData.getClasshoursUnit());
        orformdata.setCertihours(modifyCaseData.getCertihours());
        orformdata.setLecturercode(modifyCaseData.getLecturercode());
        orformdata.setPassmsg(modifyCaseData.getPassmsg());
        orformdata.setRejectmst(modifyCaseData.getRejectmst());
        orformdata.setRegisqual(StringUtils.join(modifyCaseData.getRegisqual(), ","));
        orformdata.setTopicdesc(modifyCaseData.getTopicdesc());
        orformdata.setRemark(modifyCaseData.getRemark());

        String seqno = ormodihisDao.getMaxSeqno(modifyCaseData.getOrformno());
        ormodihis.setSeqno(StringUtils.isBlank(seqno) ? "1" : seqno);
        ormodihis.setChgtype(mode);
        //新增
        if ("A".equals(mode)) {
            orformdata.setStatus("N");
            orformdata.setCreuser(userData.getUserId());
            orformdata.setCredt(LocalDateTime.now());
            //歷程
            ormodihis.setOrformno(orformdata.getOrformno());
            ormodihis.setCreuser(userData.getUserId());
            ormodihis.setCredt(LocalDateTime.now());
            ormodihis.setUserdept(userData.getDeptId());
            ormodihis.setUserinfo(userData.getUserId() + "-" + userData.getUserName());
            ormodihisDao.insertData(ormodihis);
            orformdataDao.insertData(orformdata);
        }
        //更新
        if ("U".equals(mode)) {
            Orformdata dbOrformdata = orformdataDao.findByPk(modifyCaseData.getOrformno());
            orformdata.setStatus(dbOrformdata.getStatus());
            orformdata.setCreuser(dbOrformdata.getCreuser());
            orformdata.setCredt(dbOrformdata.getCredt());
            orformdata.setUpduser(userData.getUserId());
            orformdata.setUpddt(LocalDateTime.now());
            //歷程
            ormodihis.setOrformno(modifyCaseData.getOrformno());
            ormodihis.setCreuser(userData.getUserId());
            ormodihis.setCredt(LocalDateTime.now());
            ormodihis.setUserdept(userData.getDeptId());
            ormodihis.setUserinfo(userData.getUserId() + "-" + userData.getUserName());
            ormodihisDao.insertData(ormodihis);
            orformdataDao.updateData(orformdata, modifyCaseData.getOrformno());
        }
        // 處理檔案上傳
        MultipartFile[] files = Arrays.stream(modifyCaseData.getFiles()).filter(f->StringUtils.isNotBlank(f.getOriginalFilename())).toArray(MultipartFile[]::new);
        String saveDirectory = "/eip/uploadfile/" + ("U".equals(mode) ? modifyCaseData.getOrformno() : orformdata.getOrformno());
        String apDirectory = System.getProperty("user.dir");
        String serverDrive = apDirectory.substring(0, apDirectory.indexOf(File.separator));
        File savePath = new File(serverDrive + saveDirectory);
        // 目錄不存在則創建
        if (!savePath.exists()) {
            savePath.mkdirs();
        }
        for (MultipartFile file : files) {
            String fileName = file.getOriginalFilename();
            File targetFile = new File(savePath, fileName);
            file.transferTo(targetFile);
        }
    }

    /**
     * 刪除畫面所選表單
     * @param caseData
     */
    public void deleteCheckedForm(Eip00w420Case caseData) {
        orformdataDao.deleteCheckedForm(caseData.getOrformnoList());
    }

    /**
     * 上架畫面所選表單
     * @param caseData
     */
    public void putCheckedForm(Eip00w420Case caseData) {
        orformdataDao.updateStatus(caseData.getOrformnoList(), "P");
        //清除自動回填
        caseData.setOrformnoList(new ArrayList<>());
    }

    /**
     * 停辦畫面所選表單
     * @param caseData
     */
    public void disabledCheckedForm(Eip00w420Case caseData) {
        orformdataDao.updateStatus(caseData.getOrformnoList(), "D");
        //清除自動回填
        caseData.setOrformnoList(new ArrayList<>());
    }

    /**
     * 計算認證時數
     * @param certihours
     * @return
     */
    public String calculateCertiHours(String certihours) {
        List<String>list = Arrays.asList(certihours.split(","));
        Integer sum = list.stream().mapToInt(s -> Integer.parseInt(s.substring(2))).sum();
        return String.valueOf(sum);
    }

    /**
     * 複製線上報名表單
     * @param caseData
     */
    public void copyFormdata(Eip00w420Case caseData) {
        Orformdata dbOrformdata = orformdataDao.findByPk(caseData.getOrformno());
        Orformdata newOrformdata = new Orformdata();
        BeanUtility.copyProperties(newOrformdata, dbOrformdata);
        String orformno = orformdataDao.getMaximumOrformno(DateUtility.getNowChineseYearMonth());
        newOrformdata.setOrformno("OR" + DateUtility.getNowChineseYearMonth() + (StringUtils.isBlank(orformno) ? "0001" :  StringUtils.leftPad(orformno, 4, "0")));
        newOrformdata.setTopicname(dbOrformdata.getTopicname()+"-複製");
        newOrformdata.setStatus("N");
        newOrformdata.setCreuser(userData.getUserId());
        newOrformdata.setCredt(LocalDateTime.now());
        newOrformdata.setUpduser(null);
        newOrformdata.setUpddt(null);
        orformdataDao.insertData(newOrformdata);
    }

    /**
     * 取得歷程資料
     * @param caseData
     */
    public void getHisData(Eip00w420Case caseData) {
        List<Eip00w420HisCase> list = ormodihisDao.getDataByOrformno(caseData.getOrformno()).stream().map(t -> {
            Eip00w420HisCase eip00w420HisCase = new Eip00w420HisCase();
            eip00w420HisCase.setUppdt(t.getCredt().format(minguoformatter));
            eip00w420HisCase.setChgtype(t.getChgtype());
            eip00w420HisCase.setOperator(t.getUserinfo());
            eip00w420HisCase.setOperatorDept(t.getUserdept());
            return eip00w420HisCase;
        }).collect(Collectors.toList());
        caseData.setHisList(list);
    }

    /**
     * 取得審核資料
     * @param caseData
     * @param isSearch
     */
    public void getVerifyData(Eip00w420Case caseData, boolean isSearch) {
        List<Orresult> list = new ArrayList<>();
        if (!isSearch) {
            list = orresultDao.getDataByOrformno(caseData.getOrformno(),null);
        } else {
            list = orresultDao.getDataByMultiCondition(caseData.getOrformno(),
                    caseData.getVKeyword(), caseData.getVStatus());
        }
        List<Eip00w420VerifyCase> verifyCaselist = list.stream().map(t -> {
            Eip00w420VerifyCase eip00w420VerifyCase = new Eip00w420VerifyCase();
            eip00w420VerifyCase.setSeqno(t.getSeqno());
            eip00w420VerifyCase.setRegisname(t.getRegisname());
            eip00w420VerifyCase.setRegisphone(t.getRegisphone());
            eip00w420VerifyCase.setRegisemail(t.getRegisemail());
            eip00w420VerifyCase.setCompany(t.getCompany());
            eip00w420VerifyCase.setDept(t.getDept());
            eip00w420VerifyCase.setIsPass(t.getIspass());
            eip00w420VerifyCase.setIsPay(ObjectUtils.defaultIfNull(t.getIspay(),""));
            eip00w420VerifyCase.setIsNotify(t.getIsnotify());
            return eip00w420VerifyCase;
        }).collect(Collectors.toList());
        caseData.setVerList(verifyCaselist);
    }

    /**
     * 更新審核相關資料
     * @param caseData
     */
    public void verify(Eip00w420Case caseData) {
        List<Eip00w420VerifyCase>list = caseData.getVerList().stream().filter(t->StringUtils.isNotBlank(t.getSeqno())).collect(Collectors.toList());
        for (Eip00w420VerifyCase c : list) {
            Orresult orresult = orresultDao.findByPk(caseData.getOrformno(),c.getSeqno());
            if (!"D".equals(orresult.getIspass())) {
                orresult.setIspay(c.getIsPay());
                orresult.setIsnotify(c.getIsNotify());
                orresult.setIspass(c.getIsPass());
                orresult.setUpddt(LocalDateTime.now());
                orresult.setUpduser(userData.getUserId());
                orresultDao.updateData(orresult, caseData.getOrformno(),c.getSeqno());
            }
        }
    }

    /**
     * 取得時數認證資料
     * @param caseData
     */
    public void getCertifiedData(Eip00w420Case caseData) {
        String certihours = orformdataDao.findByPk(caseData.getOrformno()).getCertihours();
        caseData.setCertihoursList(Arrays.asList(certihours.split(",")));
        List<Eip00w420VerifyCase> list = orresultDao.getDataByMultiCondition(caseData.getOrformno(), caseData.getCKeyword(), "Y").stream().map(t -> {
            Eip00w420VerifyCase eip00w420VerifyCase = new Eip00w420VerifyCase();
            eip00w420VerifyCase.setSeqno(t.getSeqno());
            eip00w420VerifyCase.setRegisname(t.getRegisname());
            eip00w420VerifyCase.setRegisphone(t.getRegisphone());
            eip00w420VerifyCase.setRegisemail(t.getRegisemail());
            eip00w420VerifyCase.setCertihours(calculateCertiHours(certihours));
            String total = t.getCertiphours() + ',' + t.getCertidhours();
            eip00w420VerifyCase.setCheckList(Arrays.asList(total.split(",")).stream().filter(str -> !"null".equals(str))
                    .collect(Collectors.toList()));
            return eip00w420VerifyCase;
        }).collect(Collectors.toList());
        caseData.setCerList(list);
    }

    /**
     * 更新時數認證
     * @param caseData
     */
    public void updateCertifiedData(Eip00w420Case caseData) {
        for (Eip00w420VerifyCase eip00w420VerifyCase : caseData.getCerList()) {
            Orresult orresult = orresultDao.findByPk(caseData.getOrformno(), eip00w420VerifyCase.getSeqno());
            Orformdata orformdata = orformdataDao.findByPk(caseData.getOrformno());
            String allCertihours = ObjectUtils.defaultIfNull(orformdata.getCertihours(),"");
            String pStr = StringUtils.join(eip00w420VerifyCase.getCheckList().stream()
                    .filter(str -> StringUtils.contains(str,"P")).collect(Collectors.toList()),",");
            String dStr = StringUtils.join(eip00w420VerifyCase.getCheckList().stream()
                    .filter(str -> StringUtils.contains(str,"D")).collect(Collectors.toList()),",");
            if ("Y".equals(eip00w420VerifyCase.getSelAllmk())) {
                pStr = StringUtils.join(Arrays.asList(orformdata.getCertihours().split(",")).stream()
                        .filter(str -> StringUtils.contains(str,"P"))
                        .collect(Collectors.toList()),",");
                dStr = StringUtils.join(Arrays.asList(orformdata.getCertihours().split(",")).stream()
                        .filter(str -> StringUtils.contains(str,"D"))
                        .collect(Collectors.toList()),",");
            }
            orresult.setCertidhours(dStr);
            orresult.setCertiphours(pStr);
            orresult.setUpduser(userData.getUserId());
            orresult.setUpddt(LocalDateTime.now());
            orresultDao.updateData(orresult, caseData.getOrformno(), eip00w420VerifyCase.getSeqno());
        }
    }

    /**
     * 產製簽到表
     * @param caseData
     */
    public ByteArrayOutputStream prodWord(Eip00w420Case caseData) throws IOException {
        Orformdata orformdata = orformdataDao.findByPk(caseData.getOrformno());
        Orclass orclass = orclassDao.findByPk(orformdata.getOrccode());
        // 通過審核的報名結果清單
        List<Orresult>list = orresultDao.getDataByMultiCondition(orformdata.getOrformno(), "", "Y");
        if (CollectionUtils.isNotEmpty(list)) {
            String timeStr = StringUtils.replace(StringUtils.substringBefore(DateUtil.formatChineseDateTimeString(orformdata.getProfmdt().format(westdatetimeformatter),true),"00")," ","");
            String timeEnd = StringUtils.replace(StringUtils.substringBefore(DateUtil.formatChineseDateTimeString(orformdata.getProendt().format(westdatetimeformatter),true), "00")," ","");
            Eip00w420L00 eip00w420L00 = new Eip00w420L00(orclass.getSignform(), orformdata.getTopicname(),
                    timeStr + "~" + timeEnd , orformdata.getAddres(), list);
            return eip00w420L00.creatWord(caseData);
        }
        return null;
    }

    /**
     * 產製學習時數CSV檔
     * @param caseData
     * @return
     */
    public byte[] prodCertihoursCsv(Eip00w420Case caseData) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(0);
        Orformdata orformdata = orformdataDao.findByPk(caseData.getOrformno());
        List<Orresult> list = orresultDao.getDataByMultiCondition(caseData.getOrformno(), "", "Y");
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        StringBuilder sb = new StringBuilder()
                .append("身分證字號(*)").append(",")
                .append("課程名稱(*)").append(",")
                .append("課程開始日期(*)").append(",")
                .append("課程結束時間(*)").append(",")
                .append("姓名(*)").append(",")
                .append("課程類別代碼(*)").append(",")
                .append("上課縣市(*)").append(",")
                .append("期別(*)").append(",")
                .append("訓練總數(即認證時數)(*)").append(",")
                .append("生日").append(",")
                .append("實體時數(即核可時數)(*)").append(",")
                .append("課程代碼").append(System.getProperty("line.separator"));
        for (Orresult orresult : list) {
            sb.append(StringUtils.trimToEmpty(orresult.getRegisidn())).append(",")
                    .append(StringUtils.trimToEmpty(orformdata.getTopicname())).append(",")
                    .append(StringUtils.trimToEmpty(orformdata.getProfmdt().format(minguoformatter))).append(",")
                    .append(StringUtils.trimToEmpty(orformdata.getProendt().format(minguoformatter))).append(",")
                    .append(StringUtils.trimToEmpty(orresult.getRegisname())).append(",")
                    .append(StringUtils.trimToEmpty(String.valueOf(orformdata.getCourseclacode()))).append(",")
                    .append(StringUtils.trimToEmpty(orformdata.getCountry())).append(",")
                    .append(StringUtils.trimToEmpty(String.valueOf(orformdata.getPeriod()))).append(",")
                    .append(StringUtils.trimToEmpty(calculateCertiHours(orformdata.getCertihours()))).append(",")
                    .append(StringUtils.trimToEmpty(DateUtility.changeDateTypeToChineseDate(orresult.getRegisbrth()))).append(",")
                    .append(StringUtils.trimToEmpty(calculateCertiHours(StringUtils.defaultString(orresult.getCertiphours()) + "," + StringUtils.defaultString(orresult.getCertidhours())))).append(",")
                    .append(StringUtils.trimToEmpty(orformdata.getCoursecode()))
                    .append(System.getProperty("line.separator"));
        }
        return sb.toString().getBytes(Charset.forName("big5"));
    }

    /**
     * 產製報名資料
     * @param caseData
     */
    public ByteArrayOutputStream prodExcel(Eip00w420Case caseData) {
        Orformdata orformdata = orformdataDao.findByPk(caseData.getOrformno());
        Orclass orclass = orclassDao.findByPk(orformdata.getOrccode());
        List<Orresult> list = orresultDao.getDataByMultiCondition(caseData.getOrformno(), "", "");
        Eip00w420L02 eip00w420L02 = new Eip00w420L02();
        eip00w420L02.addHeader(orclass, orformdata, list);
        eip00w420L02.addContent(list);
        return eip00w420L02.getOutputStream();
    }

}
