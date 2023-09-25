package tw.gov.pcc.eip.services;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.internal.constraintvalidators.bv.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tw.gov.pcc.common.util.DateUtil;
import tw.gov.pcc.eip.common.cases.*;
import tw.gov.pcc.eip.dao.*;
import tw.gov.pcc.eip.domain.*;
import tw.gov.pcc.eip.framework.domain.UserBean;
import tw.gov.pcc.eip.report.Eip04w020L00;
import tw.gov.pcc.eip.report.Eip04w020L02;
import tw.gov.pcc.eip.util.*;

import java.io.*;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 線上報名列表
 *
 * @author Weith
 */
@Service
public class Eip04w020Service extends OnlineRegService {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(Eip04w020Service.class);
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
    @Autowired
    DeptsDao deptsDao;
    @Autowired
    EipcodeDao eipcodeDao;
    @Autowired
    MailService mailService;

    /**
     * 初始化下拉選單
     * @param caseData
     */
    public void initCombobox(Eip04w020Case caseData) {
        Map<Long,String>orccodeMap = orclassDao.getAll().stream()
                .collect(Collectors.toMap(Orclass::getOrccode, Orclass::getOrcname));//TODO 如果有要按照順序排列需要再調整
        caseData.setStatusCombobox(getOrstatus());
        caseData.setRegstatusCombobox(getRegstatus());
        if (StringUtils.defaultString(caseData.getMode()).matches("A|U")) {
            caseData.setRegisqualCheckboxU(getRegisqualDept());
            caseData.setRegisqualCheckboxE1(getRegisqualE1());
            caseData.setRegisqualCheckboxE2(getRegisqualE2());
            caseData.setRegisqualCheckboxE3(getRegisqualE3());
            caseData.setRegisqualCheckboxE4(getRegisqualE4());
            caseData.setOrccodeCombobox(orccodeMap);
            caseData.setCountryCombobox(getCountry());
        }
        if ("M".equals(caseData.getMode())) {
            caseData.setDegreenCombobox(getDegreen());
        }
    }

    /**
     * 取得畫面上的報名列表
     * @param caseData
     */
    public void getOrlist(Eip04w020Case caseData, boolean isSearch) {
        Map<Long, String> orclassMap = orclassDao.getAll().stream()
                .collect(Collectors.toMap(Orclass::getOrccode, Orclass::getOrcname));
        Map<String, String> statusMap = getOrstatus();
        Map<String, String> deptMap = getRegisqualDept();
        Map<String, String> titleMap = getAllTitle();
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
        List<Eip04w020Case.OrCase> list = orformdataList.stream().map(t -> {
            StringBuilder actualsb = new StringBuilder();
            StringBuilder passsb = new StringBuilder();
            Eip04w020Case.OrCase orCase = new Eip04w020Case.OrCase();
            List<Orresult>resultList = orresultDao.getDataByOrformno(t.getOrformno(),"D");
            List<Orresult>resultPassList = orresultDao.getDataByMultiCondition(t.getOrformno(),"","Y");
            // 該活動開放的報名資格(職稱)
            List<String> regisqualList =
                    eipcodeDao.findByCodeKindAndList("TITLE",Arrays.asList(StringUtils.split(t.getRegisqual(),",")));
            // 找出部門代號(D開頭)
            List<String>regisqulForDept = Arrays.stream(StringUtils.split(t.getRegisqual(),",")).filter(s->StringUtils.startsWith(s,"D")).map(r->StringUtils.substring(r, 1)).collect(Collectors.toList());
            // 該活動開放的報名資格(部門)
            List<String> deptList = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(regisqulForDept)) {
                deptList = deptsDao.findNameByMultiID(regisqulForDept).stream().map(Depts::getDept_name).collect(Collectors.toList());
                regisqualList.addAll(deptList);
            }
            Map<String, Integer> actualappnumMap = new LinkedHashMap<>();
            Map<String, Integer> passnumMap = new LinkedHashMap<>();
            orCase.setOrformno(t.getOrformno());
            orCase.setTopicname(t.getTopicname());
            orCase.setOrcname(orclassMap.get(t.getOrccode()));
            orCase.setStatus(statusMap.get(t.getStatus()));
            orCase.setStatusVal(t.getStatus());
            orCase.setAcceptappnum(t.getAcceptappnum());
            resultList.forEach(r->{
                if (deptMap.containsKey(r.getDept())) {
                    String deptName = deptMap.get(r.getDept());
                    if (!actualappnumMap.containsKey(deptName)) {
                        actualappnumMap.put(deptName,1);
                    } else {
                        actualappnumMap.computeIfPresent(deptName, (key, value) -> value + 1);
                    }
                } else {
                    String titleName = titleMap.get(r.getJogtitle());
                    if (!actualappnumMap.containsKey(titleName)) {
                        actualappnumMap.put(titleName,1);
                    } else {
                        actualappnumMap.computeIfPresent(titleName, (key, value) -> value + 1);
                    }
                }
            });
            //loop有勾選的報名資格
            for (String str : regisqualList) {
                if (actualappnumMap.containsKey(str)) {
                    actualsb.append(str+":"+actualappnumMap.get(str)+"人");
                } else {
                    actualsb.append(str+":0人");
                }
                actualsb.append("&#10;");
            }
            orCase.setActualappnumAbbr(actualsb.toString());
            orCase.setActualappnum(t.getActualappnum());// 讀結果檔的報名人數
            resultPassList.forEach(r->{
                if (deptMap.containsKey(r.getDept())) {
                    String deptName = deptMap.get(r.getDept());
                    if (!passnumMap.containsKey(deptName)) {
                        passnumMap.put(deptName,1);
                    } else {
                        passnumMap.computeIfPresent(deptName, (key, value) -> value + 1);
                    }
                } else {
                    String titleName = titleMap.get(r.getJogtitle());
                    if (!passnumMap.containsKey(titleName)) {
                        passnumMap.put(titleName,1);
                    } else {
                        passnumMap.computeIfPresent(titleName, (key, value) -> value + 1);
                    }
                }
            });
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
    public void getInsertFormData(Eip04w020ModifyCase modifyCase) {
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
    public void getSingleFormData(Eip04w020Case caseData, Eip04w020ModifyCase modifyCase) {
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
        modifyCase.setSubject(orformdata.getSubject());
        modifyCase.setRegisqual(Arrays.asList(orformdata.getRegisqual().split(",")));
        modifyCase.setTopicdesc(orformdata.getTopicdesc());
        modifyCase.setRemark(orformdata.getRemark());
    }

    /**
     * 新增線上報名表單資料
     * @param modifyCaseData
     */
    public void insertUpdateOrfData(Eip04w020ModifyCase modifyCaseData, String mode) throws IOException {
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
        orformdata.setSubject(modifyCaseData.getSubject());
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
            ormodihis.setUserinfo(userData.getUserId());
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
            ormodihis.setUserinfo(userData.getUserId());
            ormodihisDao.insertData(ormodihis);
            orformdataDao.updateData(orformdata, modifyCaseData.getOrformno());
        }
        // 處理檔案上傳，不存在直接exception
        MultipartFile[] files = Arrays.stream(modifyCaseData.getFiles()).filter(f->StringUtils.isNotBlank(f.getOriginalFilename())).toArray(MultipartFile[]::new);
        String filedir = eipcodeDao.findByCodeKindCodeNo("FILEDIR", "1").get().getCodename();
        String fileSeparator = File.separator;
        String saveDirectory = filedir + fileSeparator + "線上報名" + fileSeparator + ("U".equals(mode) ? modifyCaseData.getOrformno() : orformdata.getOrformno());
//        String apDirectory = System.getProperty("user.dir");
//        String serverDrive = apDirectory.substring(0, apDirectory.indexOf(File.separator));
        File savePath = new File(saveDirectory);
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
    public void deleteCheckedForm(Eip04w020Case caseData) {
        orformdataDao.deleteCheckedForm(caseData.getOrformnoList());
    }

    /**
     * 上架畫面所選表單
     * @param caseData
     */
    public void putCheckedForm(Eip04w020Case caseData) {
        orformdataDao.updateStatus(caseData.getOrformnoList(), "P");
        //清除自動回填
        caseData.setOrformnoList(new ArrayList<>());
    }

    /**
     * 停辦畫面所選表單
     * @param caseData
     */
    public void disabledCheckedForm(Eip04w020Case caseData) {
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
        if (StringUtils.isBlank(certihours)){
            return "0";
        }
        if (StringUtils.startsWith(certihours, ",")) {
            certihours = certihours.substring(1);
        }
        List<String>list = Arrays.asList(certihours.split(","));
        Integer sum = list.stream().mapToInt(s -> Integer.parseInt(StringUtils.substringAfter(s,"-").substring(2))).sum();
        return String.valueOf(sum);
    }

    /**
     * 複製線上報名表單
     * @param caseData
     */
    public void copyFormdata(Eip04w020Case caseData) {
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
    public void getHisData(Eip04w020Case caseData) {
        Map<String,String>usersMap = getUsers();
        Map<String,String>deptMap = getRegisqualDept();
        List<Eip04w020HisCase> list = ormodihisDao.getDataByOrformno(caseData.getOrformno()).stream().map(t -> {
            Eip04w020HisCase eip04w020HisCase = new Eip04w020HisCase();
            eip04w020HisCase.setUppdt(t.getCredt().format(minguoformatter));
            eip04w020HisCase.setChgtype(t.getChgtype());
            eip04w020HisCase.setOperator(StringUtils.defaultString(usersMap.get(t.getUserinfo())));
            eip04w020HisCase.setOperatorDept(StringUtils.defaultString(deptMap.get("D" + t.getUserdept())));
            return eip04w020HisCase;
        }).collect(Collectors.toList());
        caseData.setHisList(list);
    }

    /**
     * 取得審核資料
     * @param caseData
     * @param isSearch
     */
    public void getVerifyData(Eip04w020Case caseData, boolean isSearch) {
        Map<String,String> deptMap = getRegisqualDept();
        Map<String,String> companyMap = getCompany();
        List<Orresult> list = new ArrayList<>();
        if (!isSearch) {
            list = orresultDao.getDataByOrformno(caseData.getOrformno(),null);
        } else {
            list = orresultDao.getDataByMultiCondition(caseData.getOrformno(),
                    caseData.getVKeyword(), caseData.getVStatus());
        }
        List<Eip04w020VerifyCase> verifyCaselist = list.stream().map(t -> {
            Eip04w020VerifyCase eip04w020VerifyCase = new Eip04w020VerifyCase();
            eip04w020VerifyCase.setSeqno(t.getSeqno());
            eip04w020VerifyCase.setRegisname(t.getRegisname());
            eip04w020VerifyCase.setRegisphone(t.getRegisphone());
            eip04w020VerifyCase.setRegisemail(t.getRegisemail());
            eip04w020VerifyCase.setCompany(companyMap.get(t.getCompany()));
            eip04w020VerifyCase.setDept(deptMap.get(t.getDept()));
            eip04w020VerifyCase.setIsPass(t.getIspass());
            eip04w020VerifyCase.setIsPay(t.getIspay());
            eip04w020VerifyCase.setIsNotify(t.getIsnotify());
            return eip04w020VerifyCase;
        }).collect(Collectors.toList());
        caseData.setVerList(verifyCaselist);
    }

    /**
     * 更新審核相關資料
     * @param caseData
     */
    public void verify(Eip04w020Case caseData) {
        Orformdata orformdata = orformdataDao.findByPk(caseData.getOrformno());
        List<Orresult>oldResults = orresultDao.getDataByOrformno(caseData.getOrformno(),"D");
        List<Eip04w020VerifyCase>list = caseData.getVerList().stream().filter(t->StringUtils.isNotBlank(t.getSeqno())).collect(Collectors.toList());
        // 審核結果map
        Map<String,String>newResMap = list.stream().collect(Collectors.toMap(Eip04w020VerifyCase::getSeqno, v -> StringUtils.defaultString(v.getIsPass())));
        for (Eip04w020VerifyCase c : list) {
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
        replaceMailContent(orformdata);
        oldResults.forEach(orresult -> {
            String newPass = newResMap.get(orresult.getSeqno());
            if (!StringUtils.equals(newPass, StringUtils.defaultString(orresult.getIspass()))) {
                log.debug("寄出審核結果至:"+orresult.getRegisemail());
                mailService.sendEmailNow(orformdata.getSubject(), orresult.getRegisemail(), "Y".equals(newPass)? orformdata.getPassmsg():orformdata.getRejectmst());
            }
        });

    }

    /**
     * 取得時數認證資料
     * @param caseData
     */
    public void getCertifiedData(Eip04w020Case caseData) {
        String certihours = orformdataDao.findByPk(caseData.getOrformno()).getCertihours();
        caseData.setCertihoursList(Arrays.asList(certihours.split(",")));
        List<Eip04w020VerifyCase> list = orresultDao.getDataByMultiCondition(caseData.getOrformno(), caseData.getCKeyword(), "Y").stream().map(t -> {
            Eip04w020VerifyCase eip04w020VerifyCase = new Eip04w020VerifyCase();
            eip04w020VerifyCase.setSeqno(t.getSeqno());
            eip04w020VerifyCase.setRegisname(t.getRegisname());
            eip04w020VerifyCase.setRegisphone(t.getRegisphone());
            eip04w020VerifyCase.setRegisemail(t.getRegisemail());
            eip04w020VerifyCase.setCertihours(calculateCertiHours(certihours));
            String total = t.getCertiphours() + ',' + t.getCertidhours();
            eip04w020VerifyCase.setCheckList(Arrays.asList(total.split(",")).stream().filter(str -> !"null".equals(str))
                    .collect(Collectors.toList()));
            return eip04w020VerifyCase;
        }).collect(Collectors.toList());
        caseData.setCerList(list);
    }

    /**
     * 更新時數認證
     * @param caseData
     */
    public void updateCertifiedData(Eip04w020Case caseData) {
        for (Eip04w020VerifyCase eip04w020VerifyCase : caseData.getCerList()) {
            Orresult orresult = orresultDao.findByPk(caseData.getOrformno(), eip04w020VerifyCase.getSeqno());
            Orformdata orformdata = orformdataDao.findByPk(caseData.getOrformno());
            String allCertihours = ObjectUtils.defaultIfNull(orformdata.getCertihours(),"");
            String pStr = StringUtils.join(eip04w020VerifyCase.getCheckList().stream()
                    .filter(str -> StringUtils.contains(str,"P")).collect(Collectors.toList()),",");
            String dStr = StringUtils.join(eip04w020VerifyCase.getCheckList().stream()
                    .filter(str -> StringUtils.contains(str,"D")).collect(Collectors.toList()),",");
            if ("Y".equals(eip04w020VerifyCase.getSelAllmk())) {
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
            orresultDao.updateData(orresult, caseData.getOrformno(), eip04w020VerifyCase.getSeqno());
        }
    }

    /**
     * 產製簽到表
     * @param caseData
     */
    public ByteArrayOutputStream prodWord(Eip04w020Case caseData) throws IOException {
        Orformdata orformdata = orformdataDao.findByPk(caseData.getOrformno());
        Orclass orclass = orclassDao.findByPk(orformdata.getOrccode());
        // 通過審核的報名結果清單
        List<Orresult>list = orresultDao.getDataByMultiCondition(orformdata.getOrformno(), "", "Y");
        if (CollectionUtils.isNotEmpty(list)) {
            String timeStr = StringUtils.replace(StringUtils.substringBefore(DateUtil.formatChineseDateTimeString(orformdata.getProfmdt().format(westdatetimeformatter),true),"00")," ","");
            String timeEnd = StringUtils.replace(StringUtils.substringBefore(DateUtil.formatChineseDateTimeString(orformdata.getProendt().format(westdatetimeformatter),true), "00")," ","");
            Eip04w020L00 eip04w020L00 = new Eip04w020L00(orclass.getSignform(), orformdata.getTopicname(),
                    timeStr + "~" + timeEnd , orformdata.getAddres(), list, getRegisqualDept(), getAllTitle());
            return eip04w020L00.creatWord(caseData);
        }
        return null;
    }

    /**
     * 產製學習時數CSV檔
     * @param caseData
     * @return
     */
    public byte[] prodCertihoursCsv(Eip04w020Case caseData) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(0);
        Orformdata orformdata = orformdataDao.findByPk(caseData.getOrformno());
        List<Orresult> list = orresultDao.getDataByMultiCondition(caseData.getOrformno(), "", "Y");
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        StringBuilder sb = new StringBuilder()
                .append("*課程代碼").append(",")
                .append("*期別").append(",")
                .append("*身分證字號").append(",")
                .append("*姓名").append(",")
                .append("*通過").append(",")
                .append("訓練成績").append(",")
                .append("訓練總數").append(",")
                .append("證件字號").append(",")
                .append("出勤上課狀況").append(",")
                .append("生日").append(",")
                .append("*學習性質").append(",")
                .append("*數位時數").append(",")
                .append("*實體時數").append(",")
                .append("*實際上課起始日期").append(",")
                .append("實際上課起始時間").append(",")
                .append("*實際上課結束日期").append(",")
                .append("實際上課結束時間").append(System.getProperty("line.separator"));
        for (Orresult orresult : list) {
            String chours = orformdata.getCertihours();
            String phours = calculateCertiHours(orresult.getCertiphours());
            String dhours = calculateCertiHours(orresult.getCertidhours());
            String isPass = Integer.parseInt(phours) > 0 || Integer.parseInt(dhours) > 0 ? "通過":"不通過";
            String profmt = orformdata.getProfmdt().format(minguoformatter);
            String proend = orformdata.getProendt().format(minguoformatter);
            String type = "";
            if (chours.contains("P") && chours.contains("D")) {
                type = "實體+數位";
            } else if (chours.contains("P")) {
                type = "實體";
            } else if (chours.contains("D")) {
                type = "數位";
            }
            sb.append(StringUtils.trimToEmpty(orformdata.getCoursecode())).append(",")
            .append(StringUtils.trimToEmpty(String.valueOf(orformdata.getPeriod()))).append(",")
            .append(StringUtils.trimToEmpty(orresult.getRegisidn())).append(",")
            .append(StringUtils.trimToEmpty(orresult.getRegisname())).append(",")
            .append(isPass).append(",")
            .append(",")
            .append(StringUtils.trimToEmpty(calculateCertiHours(orformdata.getCertihours()))).append(",")
            .append(",")
            .append(",")
            .append(DateUtility.formatChineseDateTimeString(DateUtility.changeDateTypeToChineseDate(orresult.getRegisbrth()),false)).append(",")
            .append(type).append(",")
            .append(dhours).append(",")
            .append(phours).append(",")
            .append(StringUtils.substringBefore(profmt," ")).append(",")
            .append(StringUtils.substringAfter(profmt," ")).append(",")
            .append(StringUtils.substringBefore(proend," ")).append(",")
            .append(StringUtils.substringAfter(proend," ")).append(",")
            .append(System.getProperty("line.separator"));
        }
        return sb.toString().getBytes(Charset.forName("big5"));
    }

    /**
     * 產製報名資料
     * @param caseData
     */
    public ByteArrayOutputStream prodExcel(Eip04w020Case caseData) {
        Orformdata orformdata = orformdataDao.findByPk(caseData.getOrformno());
        Orclass orclass = orclassDao.findByPk(orformdata.getOrccode());
        List<Orresult> list = orresultDao.getDataByMultiCondition(caseData.getOrformno(), "", "");
        Eip04w020L02 eip04w020L02 = new Eip04w020L02(getRegisqualDept(),getAllTitle(),getCompany());
        eip04w020L02.addHeader(orclass, orformdata, list);
        eip04w020L02.addContent(list);
        return eip04w020L02.getOutputStream();
    }

    /**
     * 人工報名-單筆
     * @param caseData
     */
    public String manualRegSingle(Eip04w020ManualCase caseData) {
        //進行名額檢查
        if (isAtLimit(caseData.getOrformno(),  false)) {
            return "isAtLimit";
        }
        //進行重複檢查
        checkRepeatRegister(caseData.getOrformno(), caseData.getRegisidn());
        //取得所有單位與職稱中文供對照
        Map<String,String> regisqualIdMap = getRegisqualAllForReg();
        //再開始新增報名資料
        Orresult orresult = new Orresult();
        orresult.setOrformno(caseData.getOrformno());
        orresult.setSeqno(orresultDao.getMaximumSeqno(caseData.getOrformno()));
        orresult.setRegisway(caseData.getRegisway());
        orresult.setRegisname(caseData.getRegisname());
        orresult.setRegisidn(caseData.getRegisidn());
        orresult.setRegissex(caseData.getRegissex());
        orresult.setRegisbrth(DateUtility.changeDateTypeToWestDate(caseData.getRegisbrth()));
        orresult.setRegisemail(caseData.getRegisemail());
        orresult.setRegisphone(caseData.getRegisphone());
        orresult.setJogtitle(regisqualIdMap.get(caseData.getJogtitle()));//職稱，取不到就null
        orresult.setDegreen(Integer.valueOf(caseData.getDegreen()));
        orresult.setDept(regisqualIdMap.get(caseData.getDept()));//部門，取不到就null
        orresult.setCompany("360000000G");
        orresult.setRegisaddres(StringUtils.defaultIfEmpty(caseData.getRegisaddres(), null));
        orresult.setMealstatus(caseData.getMealstatus());
        orresult.setCredt(LocalDateTime.now());
        orresult.setCreuser("99999");
        orresult.setRegisdt(orresult.getCredt());
        orresultDao.insertData(orresult);
        return "";
    }

    /**
     * 人工報名-批次
     * @param caseData
     */
    public void manualRegBatch(Eip04w020ManualCase caseData) throws IOException {
        List<List<String>> csvData = new ArrayList<>();
        //取得所有單位與職稱中文供對照
        Map<String,String> regisqualIdMap = getRegisqualAllForReg();
        Map<String,String> companyIdMap = getCompanyForReg();
        int index = 0;
        try (InputStreamReader inputStreamReader = new InputStreamReader(caseData.getFile().getInputStream(), StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(inputStreamReader)) {
            String line;
            Orresult orresult = new Orresult();
            while ((line = reader.readLine()) != null) {
                List<String> rowData = new ArrayList<>(Arrays.asList(line.split(",")));
                //標題列略過
                if (index == 0){
                    index++;
                    continue;
                }
                //先進行重複檢查
                checkRepeatRegister(caseData.getOrformno(), checkAndConvertCsvData(rowData.get(0), "idn", index));
                //再開始新增報名資料
                orresult.setOrformno(caseData.getOrformno());
                orresult.setSeqno(orresultDao.getMaximumSeqno(caseData.getOrformno()));
                orresult.setRegisway(caseData.getRegisway());
                orresult.setRegisidn(checkAndConvertCsvData(rowData.get(0), "idn", index));
                orresult.setRegisname(checkAndConvertCsvData(rowData.get(1),"name", index));
                orresult.setRegisbrth(checkAndConvertCsvData(rowData.get(2),"birth", index));
                orresult.setRegisemail(checkAndConvertCsvData(rowData.get(3),"mail", index));
                orresult.setRegissex(checkAndConvertCsvData(rowData.get(4),"sex", index));
                orresult.setRegisphone(checkAndConvertCsvData(rowData.get(5),"phone", index));
                orresult.setFax(checkAndConvertCsvData(rowData.get(6),"fax", index));
                orresult.setJogtitle(regisqualIdMap.get(checkAndConvertCsvData(rowData.get(7),"jogtitle", index)));
                orresult.setCompany(companyIdMap.get(checkAndConvertCsvData(rowData.get(8),"company", index)));
                orresult.setDept(regisqualIdMap.get(checkAndConvertCsvData(rowData.get(9),"dept", index)));
                orresult.setRegisaddres(checkAndConvertCsvData(rowData.get(10),"address", index));
                orresult.setDegreen(6);
                orresult.setMealstatus(checkAndConvertCsvData(rowData.get(14),"mealstatus", index));
                orresult.setCredt(LocalDateTime.now());
                orresult.setCreuser(userData.getUserId());
                orresult.setRegisdt(orresult.getCredt());
                orresultDao.insertData(orresult);
                index++;
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
        //檢查是否超過上限，如超過透過exception rollback
        if (isAtLimit(caseData.getOrformno(), true)) {
            throw new LimitException("人工報名失敗-欲報名人數將超過名額上限!");
        }
    }

    /**
     * 重複報名就取消報名舊的報名資料
     * @param orformno
     * @param idno
     */
    private void checkRepeatRegister(String orformno, String idno) {
        Orresult chkOrresult = orresultDao.getDataByOrformnoAndIdno(orformno, idno);
        if (chkOrresult != null) {
            chkOrresult.setIspass("D");
            chkOrresult.setUpddt(LocalDateTime.now());
            chkOrresult.setUpduser(userData.getUserId());
            orresultDao.updateData(chkOrresult, orformno, chkOrresult.getSeqno());
        }
    }

    /**
     * 人工報名-下載範本
     * @return
     */
    public ByteArrayOutputStream downloadCsv() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(0);
        InputStream input = Eip04w020Service.class.getResourceAsStream("/report/batch_reg_example.csv");
        try (input;byteArrayOutputStream) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = input.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            log.error(ExceptionUtility.getStackTrace(e));
            return null;
        }
        return byteArrayOutputStream;
    }

    /**
     * 檢核並轉換csv資料，如有錯誤拋出IllegalArgumentException供上層catch
     * @param data
     * @param columName
     * @param index
     * @return
     */
    private String checkAndConvertCsvData(String data, String columName, int index) {
        if ("idn".equals(columName)) {
            if (StringUtils.isBlank(data) || !ValidateUtility.isValidIdnoOrPassportNo(data)) {
                log.debug("csv身分證號格式不正確:");
                throw new IllegalArgumentException(String.format("第%s列身分證號格式不正確", index));
            }
        }
        if ("name".equals(columName)) {
            if (StringUtils.isBlank(data) || data.length() > 33) {
                log.debug("csv姓名格式不正確:"+data);
                throw new IllegalArgumentException(String.format("第%s列姓名格式不正確", index));
            }
        }
        if ("birth".equals(columName)) {
            if (StringUtils.isBlank(data) || data.length()!=7 || !DateUtil.isValidDate(data,true)) {
                log.debug("csv生日格式不正確:"+data);
                throw new IllegalArgumentException(String.format("第%s列生日格式不正確", index));
            }
            return DateUtility.changeDateTypeToWestDate(data);
        }
        if ("mail".equals(columName)) {
            EmailValidator ev = new EmailValidator();
            if (StringUtils.isBlank(data) || !ev.isValid(data,null) || data.length() > 51) {
                log.debug("csv email格式不正確:"+data);
                throw new IllegalArgumentException(String.format("第%s列email格式不正確", index));
            }
        }
        if ("sex".equals(columName)) {
            if (StringUtils.isBlank(data) || !data.matches("G|F") || data.length() > 1) {
                log.debug("csv性別格式不正確:"+data);
                throw new IllegalArgumentException(String.format("第%s列性別格式不正確", index));
            }
        }
        if ("phone".equals(columName)) {
            if (StringUtils.isBlank(data) || data.length() > 20) {
                log.debug("csv電話格式不正確:"+data);
                throw new IllegalArgumentException(String.format("第%s列電話格式不正確", index));
            }
        }
        if ("fax".equals(columName)) {
            if (data.length() > 20) {
                log.debug("csv傳真格式不正確:"+data);
                throw new IllegalArgumentException(String.format("第%s列傳真格式不正確", index));
            }
        }
        if ("jogtitle".equals(columName)) {
            if (StringUtils.isBlank(data) || data.length() > 33) {
                log.debug("csv職稱格式不正確:"+data);
                throw new IllegalArgumentException(String.format("第%s列職稱格式不正確", index));
            }
        }
        if ("company".equals(columName)) {
            if (data.length() > 33) {
                log.debug("csv公司格式不正確:"+data);
                throw new IllegalArgumentException(String.format("第%s列公司格式不正確", index));
            }
        }
        if ("dept".equals(columName)) {
            if (StringUtils.isBlank(data) || data.length() > 33) {
                log.debug("csv部門格式不正確:"+data);
                throw new IllegalArgumentException(String.format("第%s列部門格式不正確", index));
            }
        }
        if ("address".equals(columName)) {
            if (data.length() > 66) {
                log.debug("csv地址格式不正確:"+data);
                throw new IllegalArgumentException(String.format("第%s列地址格式不正確", index));
            }
        }
        if ("mealstatus".equals(columName)) {
            if (StringUtils.isBlank(data) || !data.matches("N|M|V") || data.length() > 1) {
                log.debug("csv用餐狀況格式不正確:"+data);
                throw new IllegalArgumentException(String.format("第%s列用餐狀況格式不正確", index));
            }
        }
        return data;
    }

    /**
     * 取代郵件內容
     * @param orformdata
     * @return
     */
    private void replaceMailContent(Orformdata orformdata) {
        String passmsg = orformdata.getPassmsg();
        String rejectmsg = orformdata.getRejectmst();
        passmsg = StringUtils.replace(passmsg, "○年○月○日", DateUtility.formatChineseDateTimeString(orformdata.getProfmdt().format(minguoformatterForInput),true).trim());
        passmsg = StringUtils.replace(passmsg, "○○○活動", orformdata.getTopicname());
        rejectmsg = StringUtils.replace(rejectmsg, "○年○月○日", DateUtility.formatChineseDateTimeString(orformdata.getProfmdt().format(minguoformatterForInput),true).trim());
        rejectmsg = StringUtils.replace(rejectmsg, "○○○活動", orformdata.getTopicname());
        orformdata.setPassmsg(passmsg);
        orformdata.setRejectmst(rejectmsg);
    }

    /**
     * 根據ad帳號(userid)取得個人資料
     * @param adaccount
     * @return
     */
    public Map<String,String> getUserInfo(String adaccount) {
        Map<String, String> map = new HashMap<>();
        Users users = usersDao.selectByKey(adaccount);
        if (users != null) {
            map.put("name", users.getUser_name());
            map.put("email", users.getEmail());
            map.put("tel", StringUtils.isNotBlank(users.getTel1()) ? users.getTel1() : users.getTel2());
            map.put("title", getAllTitle().get(users.getTitle_id()));
            map.put("dept", getRegisqualDept().get("D" + users.getDept_id()));
        }
        return map;
    }

    /**
     * 判斷報名後是否超過上限
     * @param orformno
     * @param isBatch
     * @return
     */
    private boolean isAtLimit(String orformno, boolean isBatch) {
        Orformdata orformdata = orformdataDao.findByPk(orformno);
        List<Orresult>resultList = orresultDao.getDataByOrformno(orformno,"D");
        // 批次報名資料已暫時新增至result檔，故+0
        return resultList.size() + (isBatch ? 0 : 1) > orformdata.getAcceptappnum();
    }

    /**
     * 達到上限Exception
     */
    public class LimitException extends RuntimeException {
        private static final long serialVersionUID = 1L;

        public LimitException(String s) {
            super(s);
        }
    }
}
