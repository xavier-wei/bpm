package tw.gov.pcc.eip.services;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import tw.gov.pcc.eip.common.cases.*;
import tw.gov.pcc.eip.dao.OsclassDao;
import tw.gov.pcc.eip.dao.OsformdataDao;
import tw.gov.pcc.eip.dao.OsitemDao;
import tw.gov.pcc.eip.dao.OsquestionDao;
import tw.gov.pcc.eip.domain.*;
import tw.gov.pcc.eip.framework.domain.UserBean;
import tw.gov.pcc.eip.util.BeanUtility;
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
    @Autowired
    OsquestionDao osquestionDao;
    @Autowired
    OsitemDao ositemDao;

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
     * 取得部分調查列表
     * @param caseData
     */
    public void getPartList(Eip00w520Case caseData) {
        List<Eip00w520PartCase>list = osquestionDao.getListByOsformno(caseData.getOsformno()).stream().map(t->{
            Eip00w520PartCase eip00w520PartCase = new Eip00w520PartCase();
            eip00w520PartCase.setSectitle(t.getSectitle());
            eip00w520PartCase.setQseqno(String.valueOf(t.getQseqno()));
            eip00w520PartCase.setSectitleseq(String.valueOf(t.getSectitleseq()));
            return eip00w520PartCase;
        }).collect(Collectors.toList());
        caseData.setPartList(list);
    }

    /**
     * 取得子部分列表
     * @param caseData
     */
    public void getQuestionList(Eip00w520Case caseData) {
        List<Eip00w520QuestionCase>list = osquestionDao.getQuestionsByOsformnoAndSectitleseq(caseData.getOsformno(), caseData.getSectitleseq()).stream().map(t->{
            Eip00w520QuestionCase eip00w520QuestionCase = new Eip00w520QuestionCase();
            eip00w520QuestionCase.setSectitle(t.getSectitle());
            eip00w520QuestionCase.setQseqno(String.valueOf(t.getQseqno()));
            eip00w520QuestionCase.setTopicname(caseData.getTopicname());
            eip00w520QuestionCase.setTopicseq(String.valueOf(t.getTopicseq()));
            eip00w520QuestionCase.setTopic(t.getTopic());
            eip00w520QuestionCase.setOptiontype(t.getOptiontype());
            return eip00w520QuestionCase;
        }).collect(Collectors.toList());
        caseData.setQuestionList(list);
    }

    /**
     * 取得選項資料列表
     * @param caseData
     * @param optionCase
     */
    public void getOptionList(Eip00w520Case caseData, Eip00w520OptionCase optionCase) {
        List<Eip00w520OptionCase>list = ositemDao.getItemsByOsformnoAndQseqno(caseData.getOsformno(), caseData.getQseqno()).stream().map(t->{
            Eip00w520OptionCase eip00w520OptionCase = new Eip00w520OptionCase();
            eip00w520OptionCase.setIseqno(String.valueOf(t.getIseqno()));
            eip00w520OptionCase.setItemseq(String.valueOf(t.getItemseq()));
            eip00w520OptionCase.setItemdesc(t.getItemdesc());
            eip00w520OptionCase.setIsaddtext(t.getIsaddtext());
            return eip00w520OptionCase;
        }).collect(Collectors.toList());
        caseData.setOptionList(list);
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
     * 取得要修改的部分標題資料
     * @param caseData
     * @param partCase
     */
    public void getSinglePartData(Eip00w520Case caseData, Eip00w520PartCase partCase) {
        Osquestion osquestion = osquestionDao.findByPk(caseData.getOsformno(),Integer.valueOf(partCase.getQseqno()));
        partCase.setSectitleseq(String.valueOf(osquestion.getSectitleseq()));
        partCase.setSectitle(osquestion.getSectitle());
    }

    /**
     * 取得要修改的題目資料
     * @param caseData
     * @param questionCase
     */
    public void getSingleQuestionData(Eip00w520Case caseData, Eip00w520QuestionCase questionCase) {
        Osquestion osquestion = osquestionDao.findByPk(caseData.getOsformno(),Integer.valueOf(questionCase.getQseqno()));
        questionCase.setSectitleseq(String.valueOf(osquestion.getSectitleseq()));
        questionCase.setSectitle(osquestion.getSectitle());
        questionCase.setTopicseq(String.valueOf(osquestion.getTopicseq()));
        questionCase.setTopic(osquestion.getTopic());
        questionCase.setOptiontype(osquestion.getOptiontype());
        questionCase.setIsrequired(osquestion.getIsrequired());
    }

    /**
     * 取得要新增的表單資料
     * @param themeCase
     */
    public void getInsertFormData(Eip00w520ThemeCase themeCase) {
        Map<String,String>map = getMail();
        themeCase.setMailsubject(map.get("4"));
        themeCase.setMailmsg(map.get("5"));
    }

    /**
     * 取得要新增的選項資料
     * @param caseData
     * @param optionCase
     */
    public void getInsertOptionData(Eip00w520Case caseData, Eip00w520OptionCase optionCase) {
        Osquestion osquestion = osquestionDao.getSingleQuestionData(caseData.getOsformno(), caseData.getQseqno());
        optionCase.setTopic(osquestion.getTopic());
        optionCase.setTopicseq(String.valueOf(osquestion.getTopicseq()));
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
     * 新增修改意見調查主題資料
     * @param partCase
     * @param mode
     */
    public String insertUpdatePartData(Eip00w520PartCase partCase, String mode) {
        //新增
        if ("A".equals(mode)) {
            Integer qseqno = osquestionDao.getMaximumQseqno(partCase.getOsformno());//部分標題與題目的流水號
            List<Osquestion>osquestionList = osquestionDao.getListByOsformno(partCase.getOsformno());//部分標題的數量
            if (qseqno == 100 || osquestionList.size() == 99) {
                return "部分標題已達上限，新增失敗。";
            }
            Osquestion osquestion = new Osquestion();
            osquestion.setOsformno(partCase.getOsformno());
            osquestion.setQseqno(qseqno);
            osquestion.setSectitleseq(Integer.valueOf(partCase.getSectitleseq()));
            osquestion.setSectitle(partCase.getSectitle());
            osquestion.setCreuser(userData.getUserId());
            osquestion.setCredt(LocalDateTime.now());
            //判斷輸入序號是否重複，若有重複，將後面的序號全部+1
            if (osquestionDao.getSinglePartData(partCase.getOsformno(), partCase.getSectitleseq()) != null) {
                osquestionDao.updateBatchSectitleseq(partCase.getOsformno(), partCase.getSectitleseq(), "", true);
            }
            osquestionDao.insertData(osquestion);
        }
        //更新
        if ("U".equals(mode)) {
            Osquestion dbPart = osquestionDao.findByPk(partCase.getOsformno(),Integer.valueOf(partCase.getQseqno()));
            //取得可能會被影響到的題目列表
            List<Osquestion> dbQsquestions = osquestionDao.getQuestionsByOsformnoAndSectitleseq(partCase.getOsformno(), String.valueOf(dbPart.getSectitleseq()));
            Osquestion targetPart = osquestionDao.getSinglePartData(partCase.getOsformno(), partCase.getSectitleseq());
            Osquestion newPart = new Osquestion();
            LocalDateTime now = LocalDateTime.now();
            BeanUtility.copyProperties(newPart,dbPart);
            newPart.setSectitleseq(Integer.valueOf(partCase.getSectitleseq()));
            newPart.setSectitle(partCase.getSectitle());
            newPart.setUpduser(userData.getUserId());
            newPart.setUpddt(now);
            //標題有異動時，更新自己部份內的所有題目
            if (!StringUtils.equals(dbPart.getSectitle(), partCase.getSectitle())) {
                dbQsquestions.stream().forEach(t->{
                    t.setSectitle(partCase.getSectitle());
                    t.setUpduser(userData.getUserId());
                    t.setUpddt(now);
                    osquestionDao.updateData(t,t.getOsformno(),t.getQseqno());
                });
            }
            if (targetPart != null) {
                //判斷題目流水號是否不同筆，不同筆的話才更新後面的序號+1
                if (dbPart.getQseqno() != targetPart.getQseqno()) {
                    //目標序號大於當前序號
                    if (targetPart.getSectitleseq() > dbPart.getSectitleseq()) {
                        //更新其他受影響序號
                        osquestionDao.updateBatchSectitleseq(partCase.getOsformno(), partCase.getSectitleseq(), "", true);
                        List<Osquestion> osquestions = osquestionDao.getQuestionsByOsformnoAndSectitleseq(partCase.getOsformno(), String.valueOf(dbPart.getSectitleseq()));
                        //更新自己部份內的所有題目
                        osquestions.stream().forEach(t->{
                            t.setSectitleseq(newPart.getSectitleseq());
                            t.setUpduser(userData.getUserId());
                            t.setUpddt(now);
                            osquestionDao.updateData(t,t.getOsformno(),t.getQseqno());
                        });
                    //目標序號小於當前序號
                    } else {
                        //將小於當前序號與大於等於目標序號都+1
                        osquestionDao.updateBatchSectitleseq(partCase.getOsformno(), String.valueOf(dbPart.getSectitleseq()), partCase.getSectitleseq(), false);
                        //更新自己部份內的所有題目
                        dbQsquestions.stream().forEach(t->{
                            t.setSectitleseq(newPart.getSectitleseq());
                            t.setUpduser(userData.getUserId());
                            t.setUpddt(now);
                            osquestionDao.updateData(t,t.getOsformno(),t.getQseqno());
                        });
                    }
                }
            } else {
                //原先不存在，更新自己部份內所有題目的序號
                dbQsquestions.stream().forEach(t->{
                    t.setSectitleseq(newPart.getSectitleseq());
                    t.setUpduser(userData.getUserId());
                    t.setUpddt(now);
                    osquestionDao.updateData(t,t.getOsformno(),t.getQseqno());
                });
            }
            //更新該筆部分標題
            osquestionDao.updateData(newPart, newPart.getOsformno(), newPart.getQseqno());
        }
        return "";
    }

    /**
     * 新增修改題目
     * @param questionCase
     * @param mode
     */
    public void insertUpdateQuestionData(Eip00w520QuestionCase questionCase, String mode) {
        //新增
        if ("A".equals(mode)) {
            Integer qseqno = osquestionDao.getMaximumQseqno(questionCase.getOsformno());
            Osquestion osquestion = new Osquestion();
            osquestion.setOsformno(questionCase.getOsformno());
            osquestion.setQseqno(qseqno);
            osquestion.setSectitleseq(Integer.valueOf(questionCase.getSectitleseq()));
            osquestion.setSectitle(questionCase.getSectitle());
            osquestion.setTopicseq(Integer.valueOf(questionCase.getTopicseq()));
            osquestion.setTopic(questionCase.getTopic());
            osquestion.setOptiontype(questionCase.getOptiontype());
            osquestion.setIsrequired(questionCase.getIsrequired());
            osquestion.setCreuser(userData.getUserId());
            osquestion.setCredt(LocalDateTime.now());
            osquestionDao.insertData(osquestion);
        }
        //更新
        if ("U".equals(mode)) {
            Osquestion dbOsquestion = osquestionDao.getSingleQuestionData(questionCase.getOsformno(),questionCase.getQseqno());
            Osquestion osquestion = new Osquestion();
            BeanUtility.copyProperties(osquestion,dbOsquestion);
            osquestion.setSectitleseq(Integer.valueOf(questionCase.getSectitleseq()));
            osquestion.setSectitle(questionCase.getSectitle());
            osquestion.setTopicseq(Integer.valueOf(questionCase.getTopicseq()));
            osquestion.setTopic(questionCase.getTopic());
            osquestion.setOptiontype(questionCase.getOptiontype());
            osquestion.setIsrequired(questionCase.getIsrequired());
            osquestion.setUpduser(userData.getUserId());
            osquestion.setUpddt(LocalDateTime.now());
            osquestionDao.updateData(osquestion, osquestion.getOsformno(), osquestion.getQseqno());
        }
    }

    /**
     * 新增修改選項
     * @param optionCase
     * @param mode
     */
    public void insertUpdateOptionData(Eip00w520OptionCase optionCase, String mode) {
        //新增
        if ("A".equals(mode)) {
            String iseqno = ositemDao.getMaximumIseqno(optionCase.getOsformno());
            Ositem ositem = new Ositem();
            ositem.setOsformno(optionCase.getOsformno());
            ositem.setIseqno(Integer.valueOf(iseqno));
            ositem.setQseqno(Integer.valueOf(optionCase.getQseqno()));
            ositem.setItemseq(Integer.valueOf(optionCase.getItemseq()));
            ositem.setItemdesc(optionCase.getItemdesc());
            ositem.setIsaddtext(optionCase.getIsaddtext());
            ositem.setCreuser(userData.getUserId());
            ositem.setCredt(LocalDateTime.now());
            ositemDao.insertData(ositem);
        }
        //更新
        if ("U".equals(mode)) {
            Ositem dbOsitem = ositemDao.findByPk(optionCase.getOsformno(), Integer.valueOf(optionCase.getIseqno()));
            Ositem ositem = new Ositem();
            BeanUtility.copyProperties(ositem,dbOsitem);
            ositem.setItemseq(Integer.valueOf(optionCase.getItemseq()));
            ositem.setItemdesc(optionCase.getItemdesc());
            ositem.setIsaddtext(optionCase.getIsaddtext());
            ositem.setUpddt(LocalDateTime.now());
            ositem.setUpduser(userData.getUserId());
            ositemDao.updateData(ositem, optionCase.getOsformno(), Integer.valueOf(optionCase.getIseqno()));
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
    public void deleteCheckedForm(Eip00w520Case caseData, String delType) {
        if ("F".equals(delType)) {
            osformdataDao.deleteByOsformnoList(caseData.getOsformnoList());
            osquestionDao.deleteByOsformnoList(caseData.getOsformnoList());
            ositemDao.deleteByOsformnoList(caseData.getOsformnoList());
        } else if ("P".equals(delType)) {
            List<Osquestion>osquestions = osquestionDao.getQuestionsByOsformnoAndSectitleseq(caseData.getOsformno(), caseData.getSectitleseqList());
            osquestionDao.deleteByOsformnoAndSectitleseqList(caseData.getOsformno(), caseData.getSectitleseqList());
            osquestions.forEach(o->{
                ositemDao.deleteDataByQseqno(o.getOsformno(),o.getQseqno());
            });
        }else if ("Q".equals(delType)) {
            osquestionDao.deleteByOsformnoAndQseqnoList(caseData.getOsformno(),caseData.getQseqnoList());
            caseData.getQseqnoList().stream().filter(StringUtils::isNotBlank).forEach(q->{
                ositemDao.deleteDataByQseqno(caseData.getOsformno(),Integer.valueOf(q));
            });
        } else if ("I".equals(delType)) {
            ositemDao.deleteByOsformnoAndIseqnoList(caseData.getOsformno(), caseData.getIseqnoList());
        }

    }

    /**
     * 上架畫面所選表單
     * @param caseData
     */
    public void putForm(Eip00w520Case caseData) {
        List<String>list = Collections.singletonList(caseData.getOsformno());
        osformdataDao.updateStatus(list, "P");
    }

    /**
     * 下架畫面所選表單
     * @param caseData
     */
    public void offForm(Eip00w520Case caseData) {
        List<String>list = Collections.singletonList(caseData.getOsformno());
        osformdataDao.updateStatus(list, "T");
    }
}
