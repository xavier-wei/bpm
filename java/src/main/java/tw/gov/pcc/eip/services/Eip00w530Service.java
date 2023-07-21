package tw.gov.pcc.eip.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import tw.gov.pcc.eip.common.cases.*;
import tw.gov.pcc.eip.dao.OsformdataDao;
import tw.gov.pcc.eip.dao.OsitemDao;
import tw.gov.pcc.eip.dao.OsquestionDao;
import tw.gov.pcc.eip.dao.OsresultDao;
import tw.gov.pcc.eip.domain.Osformdata;
import tw.gov.pcc.eip.domain.Ositem;
import tw.gov.pcc.eip.domain.Osquestion;
import tw.gov.pcc.eip.domain.Osresult;
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
        themeCase.setFullosfmdt(osformdata.getOsfmdt().format(simpleMinguoformatter));
        themeCase.setFullosendt(osformdata.getOsendt().format(simpleMinguoformatter));
        caseData.setPromptmsg(osformdata.getPromptmsg());
    }

    public void getPreviewData(Eip00w530Case caseData) {
        List<Osquestion>questions = osquestionDao.getAllQuestionByOsformno(caseData.getOsformno());
        for(Osquestion ques : questions) {
            Eip00w520QuestionCase questionCase = new Eip00w520QuestionCase();
            List<Ositem>ositemList = ositemDao.getItemsByOsformnoAndQseqno(caseData.getOsformno(),  String.valueOf(ques.getQseqno()));
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
                List<Eip00w530Case.Multiple> cList = list.get(i).getCheckboxList();
                if ("T".equals(list.get(i).getOptiontype()) && StringUtils.isBlank(list.get(i).getText())) {
                    result.rejectValue("wricontent["+i+"].text","","必填題目不得為空!");
                    break;
                } else if ("S".equals(list.get(i).getOptiontype())) {
                    if(cList == null) {
                        result.rejectValue("wricontent["+i+"].checkboxList[0].checkVal","","必填題目選項不得為空!");
                        break;
                    }
                } else if ("M".equals(list.get(i).getOptiontype())) {
                    int emptyCount = 0;
                    for (int j = 0; j < cList.size(); j++) {
                        if (StringUtils.isBlank(cList.get(j).getCheckVal())) {
                            emptyCount++;
                        }
                    }
                    if (emptyCount == cList.size()) {
                        result.rejectValue("wricontent["+i+"].checkboxList[0].checkVal","","必填題目選項不得為空!");
                    }
                }
            }
        }
    }

    public void insertResult(Eip00w530Case caseData) throws JsonProcessingException {
        Osresult osresult = new Osresult();
        Integer wriseq = osresultDao.getMaximumWriseq(caseData.getOsformno());
        osresult.setOsformno(caseData.getOsformno());
        osresult.setWriseq(wriseq);
        osresult.setWrijogtitle(userData.getTitleId());
        osresult.setWriname(userData.getUserName());
        osresult.setWriad(userData.getUserId());
        ObjectMapper objectMapper = new ObjectMapper();
        String wricontent = objectMapper.writeValueAsString(caseData.getWricontent());
        osresult.setWricontent(wricontent);
        osresult.setCreuser(userData.getUserId());
        osresult.setCredt(LocalDateTime.now());
        osresultDao.insertData(osresult);
    }

}
