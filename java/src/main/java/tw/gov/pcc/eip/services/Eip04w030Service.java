package tw.gov.pcc.eip.services;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import tw.gov.pcc.eip.common.cases.Eip04w030Case;
import tw.gov.pcc.eip.dao.OrformdataDao;
import tw.gov.pcc.eip.dao.OrresultDao;
import tw.gov.pcc.eip.domain.Orformdata;
import tw.gov.pcc.eip.domain.Orresult;
import tw.gov.pcc.eip.framework.domain.UserBean;
import tw.gov.pcc.eip.util.ExceptionUtility;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.chrono.MinguoChronology;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 線上報名Service
 * @author Weith
 */
@Service
public class Eip04w030Service extends OnlineRegService {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(Eip04w030Service.class);
    @Autowired
    UserBean userData;
    @Autowired
    OrresultDao orresultDao;
    @Autowired
    OrformdataDao orformdataDao;
    DateTimeFormatter minguoformatter = DateTimeFormatter.ofPattern("yyy/MM/dd HH:mm")
            .withChronology(MinguoChronology.INSTANCE)
            .withLocale(Locale.TAIWAN);

    /**
     * 取得主畫面清單
     * @param caseData
     */
    public void getAllList(Eip04w030Case caseData) {
        Map<String,String> regstatusMap = getRegstatus();
        String deptno = "D" + userData.getDeptId();
        String jobtitleno = userData.getTitleId();
        List<Eip04w030Case.RegCase> list = orformdataDao.getDataByStatus(Arrays.asList("P", "A"), deptno, jobtitleno).stream().map(t -> {
            Eip04w030Case.RegCase regCase = new Eip04w030Case.RegCase();
            Orresult orresult = orresultDao.getDataByPerson(t.getOrformno(),userData.getUserId());
            List<Orresult>resultList = orresultDao.getDataByOrformno(t.getOrformno(),"D");
            regCase.setOrformno(t.getOrformno());
            regCase.setTopicname(t.getTopicname());
            regCase.setStatus(t.getStatus());//活動本身狀態
            regCase.setIsfull(resultList.size() >= t.getAllowappnum());
            if (orresult == null) {
                regCase.setRegresult(null);//未報名
                regCase.setRegresultVal("-");
            } else {
                regCase.setRegresult(StringUtils.defaultIfEmpty(orresult.getIspass(),"E"));//依審核結果，"E"代表未審
                regCase.setRegresultVal(StringUtils.defaultIfEmpty(orresult.getIspass(),"E"));//依審核結果，"E"代表未審
            }
            regCase.setStatusVal(StringUtils.defaultIfEmpty(regstatusMap.get(regCase.getRegresult()),"未報名"));//畫面上的報名結果
            return regCase;
        }).collect(Collectors.toList());
        List<Eip04w030Case.RegCase> hislist = orformdataDao.getDataByStatus(Arrays.asList("I", "C", "D"), deptno, jobtitleno).stream().map(t -> {
            Eip04w030Case.RegCase regCase = new Eip04w030Case.RegCase();
            Orresult orresult = orresultDao.getDataByPerson(t.getOrformno(),userData.getUserId());
            regCase.setTopicname(t.getTopicname());
            regCase.setOrformno(t.getOrformno());
            if (orresult == null) {
                regCase.setRegresult("E");//未報名
            } else {
                regCase.setRegresult(orresult.getIspass());//依審核結果
            }
            return regCase;
        }).collect(Collectors.toList());
        caseData.setRegList(list);
        caseData.setReghisList(hislist);
    }

    /**
     * 取得內容
     * @param caseData
     */
    public void getContent(Eip04w030Case caseData) throws IOException {
        Orformdata orformdata = orformdataDao.findByPk(caseData.getOrformno());
        List<Orresult>list = orresultDao.getDataByOrformno(caseData.getOrformno(),"D");
        Map<String,String>regisqualMap = getRegisqualAll();
        Map<String,String>allowappwayMap = getAllowappway();
        Eip04w030Case.ContentCase contentCase = new Eip04w030Case.ContentCase();
        StringBuilder regisqualSb = new StringBuilder();
        StringBuilder allowappwaySb = new StringBuilder();
        Arrays.asList(StringUtils.split(orformdata.getRegisqual(),",")).stream().forEach(t -> {
            regisqualSb.append(StringUtils.defaultString(regisqualMap.get(t))).append(", ");
        });
        Arrays.asList(StringUtils.split(orformdata.getAllowappway(),",")).stream().forEach(t -> {
            allowappwaySb.append(allowappwayMap.get(t)).append(", ");
        });
        contentCase.setRegisqual(StringUtils.substring(regisqualSb.toString(), 0, regisqualSb.length()-2));
        contentCase.setCoursecode(orformdata.getCoursecode());
        contentCase.setClasscode(orformdata.getClasscode());
        contentCase.setTopicname(orformdata.getTopicname());
        contentCase.setAddress(orformdata.getAddres());
        contentCase.setRegisdt(orformdata.getRegisfmdt().format(minguoformatter)+" ~ "+orformdata.getRegisendt().format(minguoformatter));
        contentCase.setProdt(orformdata.getProfmdt().format(minguoformatter)+" ~ "+orformdata.getProendt().format(minguoformatter));
        contentCase.setClasshours(String.valueOf(calculateHours(StringUtils.substring(orformdata.getClasshours(),0,orformdata.getClasshours().length()-1),StringUtils.substring(orformdata.getClasshours(),orformdata.getClasshours().length()-1))));
        contentCase.setAllowappnum(String.valueOf(orformdata.getAllowappnum()));
        contentCase.setActualappnum(String.valueOf(list.size()));
        contentCase.setTopicdesc(orformdata.getTopicdesc());
        contentCase.setOrganizer(orformdata.getOrganizer());
        contentCase.setContacter(orformdata.getContacter());
        contentCase.setContactnum(orformdata.getContactnum());
        contentCase.setFax(orformdata.getFax());
        contentCase.setEmail(orformdata.getEmail());
        contentCase.setAllowappway(StringUtils.substring(allowappwaySb.toString(), 0, allowappwaySb.length()-2));
        contentCase.setIsmeals(orformdata.getIsmeals());
        String filedir = eipcodeDao.findByCodeKindCodeNo("FILEDIR", "1").get().getCodename();
        String dir = filedir + "\\線上報名\\" + orformdata.getOrformno();
        File directory = new File(dir);
        if (directory.exists() && directory.isDirectory()) {
            List<String> fileList = new ArrayList<>();
            Files.walk(Paths.get(dir))
                    .filter(Files::isRegularFile)
                    .forEach(f->fileList.add("<a href='#'>"+f.getFileName().toString()+"</a>"));
            contentCase.setFiles(String.join(", ", fileList));
        }
        caseData.setRegContent(contentCase);
    }

    /**
     * 報名
     * @param caseData
     */
    public void register(Eip04w030Case caseData) {
        String seqno = orresultDao.getMaximumSeqno(caseData.getOrformno());
        Orresult orresult = new Orresult();
        orresult.setOrformno(caseData.getOrformno());
        orresult.setSeqno(seqno);
        orresult.setRegisway("I");
        orresult.setRegisname(userData.getUserName());
        orresult.setRegisidn("");
        orresult.setRegissex("");
        orresult.setRegisbrth("");
        orresult.setRegisemail(userData.getEmail());
        //取不到dept名稱就直接exception
        orresult.setDept("D" + userData.getDeptId());
        orresult.setCompany(userData.getOrgId());
        orresult.setJogtitle(userData.getTitleId());
        orresult.setRegisphone(userData.getTel1());
        orresult.setDegreen(6);
        orresult.setCreuser(userData.getUserId());
        orresult.setCredt(LocalDateTime.now());
        orresultDao.insertData(orresult);
    }

    /**
     * 取消報名
     * @param caseData
     */
    public void registerCancel(Eip04w030Case caseData) {
        Orresult orresult = orresultDao.getDataByPerson(caseData.getOrformno(),userData.getUserId());
        orresult.setUpduser(userData.getUserId());
        orresult.setUpddt(LocalDateTime.now());
        orresult.setIspass("D");
        orresultDao.updateData(orresult,orresult.getOrformno(),orresult.getSeqno());
    }

    /**
     * 報名資格驗證
     * @param caseData
     * @param result
     */
    public void validRegister(Eip04w030Case caseData, BindingResult result) {
        Orformdata orformdata = orformdataDao.findByPk(caseData.getOrformno());
        boolean isContains = Arrays.stream(StringUtils.split(orformdata.getRegisqual(),",")).anyMatch(s -> s.equals(userData.getDeptId()));
        if (!isContains) {
            result.reject("","報名資格不符！");
        }
    }

    /**
     * 下載檔案
     * @param caseData
     * @return
     */
    public ByteArrayOutputStream downloadFile(Eip04w030Case caseData) throws IOException {
        String dir = eipcodeDao.findByCodeKindCodeNo("FILEDIR", "1").get().getCodename() + "\\線上報名\\" +caseData.getOrformno() + "\\";
        String filePath = dir + caseData.getFilename();
        File file = new File(filePath);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try (FileInputStream inputStream = new FileInputStream(file); outputStream) {
            if (!file.exists()) {
                return null;
            }
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
