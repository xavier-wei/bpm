package tw.gov.pcc.eip.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import tw.gov.pcc.eip.common.cases.*;
import tw.gov.pcc.eip.dao.*;
import tw.gov.pcc.eip.domain.*;
import tw.gov.pcc.eip.framework.domain.UserBean;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 線上報名Service
 * @author Weith
 */
@Service
public class Eip00w530Service extends OpinionSurveyService {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(Eip00w530Service.class);
    @Autowired
    UserBean userData;

    @Autowired
    OsformdataDao osformdataDao;

    @Autowired
    OsquestionDao osquestionDao;

    @Autowired
    OsitemDao ositemDao;

    @Autowired
    OsresultDao osresultDao;

    @Autowired
    EipcodeDao eipcodeDao;
    /**
     * 取得主畫面清單
     * @param caseData
     */
    public void getAllList(Eip00w530Case caseData) {
        //TODO 目前userdata還沒有職稱，未來需要補
        String deptno = getLimitvoteDept().containsKey(userData.getDeptId()) ? userData.getDeptId() : userData.getEmpId();
        List<Eip00w530Case.OsCase> list = osformdataDao.getListByStatus(Arrays.asList("P", "I"), deptno).stream().map(t -> {
            Eip00w530Case.OsCase osCase = new Eip00w530Case.OsCase();
            osCase.setOsformno(t.getOsformno());
            osCase.setTopicname(t.getTopicname());
            osCase.setStatus(t.getStatus());//活動本身狀態
            osCase.setIsdisstatic(t.getIsdisstatic());
            if ("Y".equals(t.getIslimitone())) {
                osCase.setIscompleted(osresultDao.getDataByCreuser(t.getOsformno(),userData.getUserId()) == null ? "N" : "Y");
            } else {
                osCase.setIscompleted("N");
            }
            return osCase;
        }).collect(Collectors.toList());
        List<Eip00w530Case.OsCase> hislist = osformdataDao.getListByStatus(Arrays.asList("T", "C"), deptno).stream().map(t -> {
            Eip00w530Case.OsCase osCase = new Eip00w530Case.OsCase();
            osCase.setOsformno(t.getOsformno());
            osCase.setTopicname(t.getTopicname());
            osCase.setStatus(t.getStatus());//活動本身狀態
            osCase.setIsdisstatic(t.getIsdisstatic());
            return osCase;
        }).collect(Collectors.toList());
        caseData.setOsList(list);
        caseData.setHisList(hislist);
    }

    /**
     * 取得調查主題資料
     * @param caseData
     * @param themeCase
     */
    public void getSingleFormData(Eip00w530Case caseData, Eip00w520ThemeCase themeCase) {
        Osformdata osformdata = osformdataDao.findByPk(caseData.getOsformno());
        themeCase.setTopicname(osformdata.getTopicname());
        themeCase.setFullosfmdt(osformdata.getOsfmdt().format(minguoformatter));
        themeCase.setFullosendt(osformdata.getOsendt().format(minguoformatter));
        caseData.setPromptmsg(osformdata.getPromptmsg());
    }

    /**
     * 取得題目資料
     * @param caseData
     */
    public void getTopicData(Eip00w530Case caseData) {
        List<Osquestion>questions = osquestionDao.getAllQuestionByOsformno(caseData.getOsformno());
        for(Osquestion ques : questions) {
            Eip00w520QuestionCase questionCase = new Eip00w520QuestionCase();
            List<Ositem>ositemList = ositemDao.getItemsByOsformnoAndQseqno(caseData.getOsformno(),  String.valueOf(ques.getQseqno()));
            questionCase.setQseqno(String.valueOf(ques.getQseqno()));
            questionCase.setSectitle(ques.getSectitle());
            questionCase.setTopic(ques.getTopic());
            questionCase.setOptiontype(ques.getOptiontype());
            questionCase.setIsrequired(ques.getIsrequired());
            questionCase.setRowspan(ques.getRowspan());
            for (Ositem option : ositemList) {
                Eip00w520OptionCase optionCase = new Eip00w520OptionCase();
                optionCase.setQseqno(String.valueOf(option.getQseqno()));
                optionCase.setIseqno(String.valueOf(option.getIseqno()));
                optionCase.setItemdesc(option.getItemdesc());
                optionCase.setIsaddtext(option.getIsaddtext());
                questionCase.getOptionList().add(optionCase);
            }
            if ("T".equals(ques.getOptiontype())) {
                Eip00w520OptionCase optionCase = new Eip00w520OptionCase();
                optionCase.setIsText("Y");
                questionCase.getOptionList().add(optionCase);
            }
            caseData.getPreviews().add(questionCase);
        }
    }

    /**
     * 處理vaildation無法驗證的欄位
     * @param caseData
     * @param result
     */
    public void advancedValidate(Eip00w530Case caseData, BindingResult result) {
        List<Eip00w530Case.Answer> list = caseData.getWricontent();
        for (int i = 0; i < list.size(); i++) {
            boolean isEmpty = false;
            if ("Y".equals(list.get(i).getIsrequired())) {
                List<Eip00w530Case.Multiple> cList = list.get(i).getOs();
                if ("T".equals(list.get(i).getOptiontype()) && StringUtils.isBlank(list.get(i).getT())) {
                    result.rejectValue("wricontent["+i+"].t","","必填題目不得為空!");
                    break;
                } else if ("S".equals(list.get(i).getOptiontype())) {
                    if(cList == null) {
                        result.rejectValue("wricontent["+i+"].os[0].v","","必填題目選項不得為空!");
                        break;
                    } else {
                        boolean isValid = false;
                        for (Eip00w530Case.Multiple m : list.get(i).getOs()) {
                            if (StringUtils.isNotBlank(m.getV())) {
                                isValid = true;
                            }
                        }
                        if (!isValid) {
                            result.rejectValue("wricontent[" + i + "].os[0].v", "", "必填題目選項不得為空!");
                            break;
                        }
                    }
                } else if ("M".equals(list.get(i).getOptiontype())) {
                    int emptyCount = 0;
                    for (int j = 0; j < cList.size(); j++) {
                        if (StringUtils.isBlank(cList.get(j).getV())) {
                            emptyCount++;
                        }
                    }
                    if (emptyCount == cList.size()) {
                        result.rejectValue("wricontent["+i+"].os[0].v","","必填題目選項不得為空!");
                    }
                }
            }
        }
    }

    /**
     * 新增意見調查結果
     * @param caseData
     * @throws JsonProcessingException
     */
    public void insertResult(Eip00w530Case caseData) throws JsonProcessingException {
        Osresult osresult = new Osresult();
        Integer wriseq = osresultDao.getMaximumWriseq(caseData.getOsformno());
        Eipcode eipcode = eipcodeDao.findByCodeKind("DELETEDUPLICATE").get(0);
        boolean isDelete = true;
        if (eipcode != null){
            isDelete = "Y".equals(eipcode.getCodename());
        }
        osresult.setOsformno(caseData.getOsformno());
        osresult.setWriseq(wriseq);
        osresult.setWrijogtitle(userData.getTitleId());
        osresult.setWriname(userData.getUserName());
        osresult.setWriad(userData.getUserId());
        ObjectMapper objectMapper1 = new ObjectMapper();
        List<Eip00w530Case.RadioContent> radioContents = objectMapper1.readValue(caseData.getRadioContent(), new TypeReference<List<Eip00w530Case.RadioContent>>(){});
        for (Eip00w530Case.Answer a : caseData.getWricontent()) {
            if ("S".equals(a.getOptiontype())) {
                for (Eip00w530Case.RadioContent rc: radioContents) {
                    if (StringUtils.equals(rc.getN(),a.getN())) {
                        List<Eip00w530Case.Multiple> os = new ArrayList<>();
                        Eip00w530Case.Multiple multiple = new Eip00w530Case.Multiple();
                        multiple.setV(rc.getV());
                        if (StringUtils.isNotBlank(rc.getT())) {
                            multiple.setT(rc.getT());
                        }
                        os.add(multiple);
                        a.setOs(os);
                    }
                }
            }
        }
        ObjectMapper objectMapper2 = new ObjectMapper();
        String wricontent = objectMapper2.writeValueAsString(caseData.getWricontent());
        osresult.setWricontent(wricontent);
        osresult.setCreuser(userData.getUserId());
        osresult.setCredt(LocalDateTime.now());
        if (isDelete) {
            osresultDao.deleteDataByCreuser(caseData.getOsformno(), userData.getUserId());
        }
        osresultDao.insertData(osresult);
    }

}
