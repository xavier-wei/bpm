package tw.gov.pcc.eip.services;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import tw.gov.pcc.eip.common.cases.Eip00w520Case;
import tw.gov.pcc.eip.common.cases.Eip00w520ThemeCase;
import tw.gov.pcc.eip.dao.OsclassDao;
import tw.gov.pcc.eip.dao.OsformdataDao;
import tw.gov.pcc.eip.domain.*;
import tw.gov.pcc.eip.framework.domain.UserBean;
import tw.gov.pcc.eip.util.DateUtility;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 意見調查主題列表Service
 * @author Weith
 */
@Service
public class Eip00w520Service extends OpinionSurveyService{
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(Eip00w520Service.class);
    @Autowired
    UserBean userData;
    @Autowired
    OsclassDao osclassDao;
    @Autowired
    OsformdataDao osformdataDao;

    /**
     * 初始化畫面所需資料
     * @param caseData
     */
    public void init(Eip00w520Case caseData) {
        Map<Integer,String>osccodeMap = osclassDao.getAll().stream()
                .collect(Collectors.toMap(Osclass::getOsccode, Osclass::getOscname));//TODO 如果有要按照順序排列需要再調整
//        caseData.setStatusCombobox(getOrstatus());
        if (StringUtils.defaultString(caseData.getMode()).matches("A|U")) {
            caseData.setOsccodeCombobox(osccodeMap);
            caseData.setLimitvoteCheckboxU(getLimitvoteDept());
            caseData.setLimitvoteCheckboxE1(getLimitvoteE1());
            caseData.setLimitvoteCheckboxE2(getLimitvoteE2());
            caseData.setLimitvoteCheckboxE3(getLimitvoteE3());
            caseData.setLimitvoteCheckboxE4(getLimitvoteE4());
        }
    }

    /**
     * 取得畫面上的主題列表
     * @param caseData
     */
    public void getOslist(Eip00w520Case caseData) {
        Map<String, String> statusMap = getOsstatus();
        List<Osformdata> osformdataList = new ArrayList<>();

        osformdataList = osformdataDao.getAll();

        List<Eip00w520Case.OsCase> list = osformdataList.stream().map(t -> {
            Eip00w520Case.OsCase osCase = new Eip00w520Case.OsCase();
            osCase.setOsformno(t.getOsformno());
            osCase.setTopicname(t.getTopicname());
            osCase.setOsfmdt(t.getOsfmdt().format(simpleMinguoformatter));
            osCase.setOsendt(t.getOsendt().format(simpleMinguoformatter));
            osCase.setStatus(statusMap.get(t.getStatus()));
            osCase.setStatusVal(t.getStatus());
            return osCase;
        }).collect(Collectors.toList());
        caseData.setOsList(list);
        //清除自動回填
        caseData.setOsformnoList(new ArrayList<>());
    }

    /**
     * 取得要新增的表單資料
     * @param themeCase
     */
    public void getInsertFormData(Eip00w520ThemeCase themeCase) {
        Map<String,String>map = getMail();
        themeCase.setMailsubject(map.get("1"));
        themeCase.setMailmsg(map.get("2"));
    }

    /**
     * 取得要修改的調查主題資料
     * @param caseData
     * @param themeCase
     */
    public void getSingleFormData(Eip00w520Case caseData, Eip00w520ThemeCase themeCase) {
        Osformdata osformdata = osformdataDao.findByPk(caseData.getOsformno());
        themeCase.setOsccode(String.valueOf(osformdata.getOsccode()));
        themeCase.setTopicname(osformdata.getTopicname());
        themeCase.setOsfmdt(osformdata.getOsfmdt().format(minguoformatterForInput));
        themeCase.setOsfmdtHour(StringUtils.substringBefore(osformdata.getOsfmdt().format(timeformatter),":"));
        themeCase.setOsfmdtMinute(StringUtils.substringAfter(osformdata.getOsfmdt().format(timeformatter),":"));
        themeCase.setOsendt(osformdata.getOsendt().format(minguoformatterForInput));
        themeCase.setOsendtHour(StringUtils.substringBefore(osformdata.getOsendt().format(timeformatter),":"));
        themeCase.setOsendtMinute(StringUtils.substringAfter(osformdata.getOsendt().format(timeformatter),":"));
        themeCase.setTopicdesc(osformdata.getTopicdesc());
        themeCase.setOrganizer(osformdata.getOrganizer());
        themeCase.setPromptmsg(osformdata.getPromptmsg());
        themeCase.setIsdisstatic(osformdata.getIsdisstatic());
        themeCase.setIslimitone(osformdata.getIslimitone());
        themeCase.setIsanonymous(osformdata.getIsanonymous());
        themeCase.setLimitvote(Arrays.asList(osformdata.getLimitvote().split(",")));
        themeCase.setMailsubject(osformdata.getMailsubject());
        themeCase.setMailmsg(osformdata.getMailmsg());
        themeCase.setStatus(getOsstatus().get(osformdata.getStatus()));
        themeCase.setCreuser(osformdata.getCreuser());
        themeCase.setCredt(osformdata.getCredt().format(minguoformatter));
        themeCase.setUpduser(osformdata.getUpduser());
        themeCase.setUpddt(ObjectUtils.isNotEmpty(osformdata.getUpddt())?osformdata.getUpddt().format(minguoformatter):"");
    }

    /**
     * 新增意見調查主題資料
     * @param themeCase
     * @param mode
     */
    public void insertUpdateData(Eip00w520ThemeCase themeCase, String mode) {
        Osformdata osformdata = new Osformdata();
        if ("A".equals(mode)) {
            String osformno = osformdataDao.getMaximumOsformno(DateUtility.getNowChineseYearMonth());
            osformdata.setOsformno("OS" + DateUtility.getNowChineseYearMonth() + (StringUtils.isBlank(osformno) ? "0001" :  StringUtils.leftPad(osformno, 4, "0")));
        }
        osformdata.setOsccode(Integer.valueOf(themeCase.getOsccode()));
        osformdata.setTopicname(themeCase.getTopicname());
        if ("Y".equals(themeCase.getOsfmdtChksys())) {
            osformdata.setOsfmdt(LocalDateTime.now());
        } else {
            osformdata.setOsfmdt(LocalDateTime.of(DateUtility.westDateToLocalDate(DateUtility.changeDateTypeToWestDate(themeCase.getOsfmdt())), LocalTime.of(Integer.parseInt(themeCase.getOsfmdtHour()), Integer.parseInt(themeCase.getOsfmdtMinute()))));
        }
        if ("Y".equals(themeCase.getOsendtChksys())) {
            osformdata.setOsendt(LocalDateTime.now());
        } else {
            osformdata.setOsendt(LocalDateTime.of(DateUtility.westDateToLocalDate(DateUtility.changeDateTypeToWestDate(themeCase.getOsendt())), LocalTime.of(Integer.parseInt(themeCase.getOsendtHour()), Integer.parseInt(themeCase.getOsendtMinute()))));
        }
        osformdata.setTopicdesc(themeCase.getTopicdesc());
        osformdata.setOrganizer(themeCase.getOrganizer());
        osformdata.setPromptmsg(themeCase.getPromptmsg());
        osformdata.setIsdisstatic(themeCase.getIsdisstatic());
        osformdata.setIslimitone(themeCase.getIslimitone());
        osformdata.setIsanonymous(themeCase.getIsanonymous());
        osformdata.setLimitvote(StringUtils.join(themeCase.getLimitvote(), ","));
        osformdata.setMailsubject(themeCase.getMailsubject());
        osformdata.setMailmsg(themeCase.getMailmsg());
        //新增
        if ("A".equals(mode)) {
            osformdata.setStatus("N");
            osformdata.setCreuser(userData.getUserId());
            osformdata.setCredt(LocalDateTime.now());
            osformdataDao.insertData(osformdata);
        }
        //更新
        if ("U".equals(mode)) {
            Osformdata dbOsformdata = osformdataDao.findByPk(themeCase.getOsformno());
            osformdata.setStatus(dbOsformdata.getStatus());
            osformdata.setCreuser(dbOsformdata.getCreuser());
            osformdata.setCredt(dbOsformdata.getCredt());
            osformdata.setUpduser(userData.getUserId());
            osformdata.setUpddt(LocalDateTime.now());
            osformdataDao.updateData(osformdata, themeCase.getOsformno());
        }
    }

    /**
     * 處理vaildation無法驗證的欄位
     * @param themeCase
     */
    public void advancedValidate(Eip00w520ThemeCase themeCase, BindingResult result) {
        if (StringUtils.isBlank(themeCase.getOsfmdtChksys())) {
            if (StringUtils.isBlank(themeCase.getOsfmdt())) {
                result.rejectValue(themeCase.getOsfmdt(), "", "「開始時間」為必填");
            }
            if (StringUtils.isBlank(themeCase.getOsfmdtHour())) {
                result.rejectValue(themeCase.getOsfmdtHour(), "", "「開始時間(時)」為必填");
            }
            if (StringUtils.isBlank(themeCase.getOsfmdtMinute())) {
                result.rejectValue(themeCase.getOsfmdtMinute(), "", "「開始時間(分)」為必填");
            }
        }

        if (StringUtils.isBlank(themeCase.getOsendtChksys())) {
            if (StringUtils.isBlank(themeCase.getOsendt())) {
                result.rejectValue(themeCase.getOsendt(), "", "「結束時間」為必填");
            }
            if (StringUtils.isBlank(themeCase.getOsendtHour())) {
                result.rejectValue(themeCase.getOsendtHour(), "", "「結束時間(時)」為必填");
            }
            if (StringUtils.isBlank(themeCase.getOsendtMinute())) {
                result.rejectValue(themeCase.getOsendtMinute(), "", "「結束時間(分)」為必填");
            }
        }

    }

    /**
     * 刪除畫面所選表單
     * @param caseData
     */
    public void deleteCheckedForm(Eip00w520Case caseData) {
        osformdataDao.deleteCheckedForm(caseData.getOsformnoList());
    }
}
