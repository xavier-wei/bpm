package tw.gov.pcc.eip.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tw.gov.pcc.eip.common.cases.*;
import tw.gov.pcc.eip.dao.*;
import tw.gov.pcc.eip.domain.*;
import tw.gov.pcc.eip.framework.domain.UserBean;
import tw.gov.pcc.eip.report.Eip05w020L00;
import tw.gov.pcc.eip.report.Eip05w020L01;
import tw.gov.pcc.eip.util.BeanUtility;
import tw.gov.pcc.eip.util.DateUtility;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 意見調查主題列表Service
 * @author Weith
 */
@Service
public class Eip05w020Service extends OpinionSurveyService{
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(Eip05w020Service.class);
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
    @Autowired
    OsresultDao osresultDao;

    /**
     * 初始化畫面所需資料
     * @param caseData
     */
    public void init(Eip05w020Case caseData) {
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
    public void getOslist(Eip05w020Case caseData) {
        Map<String, String> statusMap = getOsstatus();
        List<Osformdata> osformdataList = new ArrayList<>();
        osformdataList = osformdataDao.getAll();
        List<Eip05w020Case.OsCase> list = osformdataList.stream().map(t -> {
            Eip05w020Case.OsCase osCase = new Eip05w020Case.OsCase();
            osCase.setOsformno(t.getOsformno());
            osCase.setTopicname(t.getTopicname());
            osCase.setOsfmdt(t.getOsfmdt().format(simpleMinguoformatter));
            osCase.setOsendt(t.getOsendt().format(simpleMinguoformatter));
            osCase.setStatus(statusMap.get(t.getStatus()));
            osCase.setStatusVal(t.getStatus());
            osCase.setIsanonymous(t.getIsanonymous());
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
    public void getPartList(Eip05w020Case caseData) {
        List<Eip05w020PartCase>list = osquestionDao.getListByOsformno(caseData.getOsformno()).stream().map(t->{
            Eip05w020PartCase eip05w020PartCase = new Eip05w020PartCase();
            eip05w020PartCase.setSectitle(t.getSectitle());
            eip05w020PartCase.setQseqno(String.valueOf(t.getQseqno()));
            eip05w020PartCase.setSectitleseq(String.valueOf(t.getSectitleseq()));
            return eip05w020PartCase;
        }).collect(Collectors.toList());
        caseData.setPartList(list);
    }

    /**
     * 取得子部分列表
     * @param caseData
     */
    public void getQuestionList(Eip05w020Case caseData) {
        List<Eip05w020QuestionCase>list = osquestionDao.getQuestionsByOsformnoAndSectitleseq(caseData.getOsformno(), caseData.getSectitleseq()).stream().map(t->{
            Eip05w020QuestionCase eip05w020QuestionCase = new Eip05w020QuestionCase();
            eip05w020QuestionCase.setSectitle(t.getSectitle());
            eip05w020QuestionCase.setQseqno(String.valueOf(t.getQseqno()));
            eip05w020QuestionCase.setTopicname(caseData.getTopicname());
            eip05w020QuestionCase.setTopicseq(String.valueOf(t.getTopicseq()));
            eip05w020QuestionCase.setTopic(t.getTopic());
            eip05w020QuestionCase.setOptiontype(t.getOptiontype());
            return eip05w020QuestionCase;
        }).collect(Collectors.toList());
        caseData.setQuestionList(list);
    }

    /**
     * 取得選項資料列表
     * @param caseData
     * @param optionCase
     */
    public void getOptionList(Eip05w020Case caseData, Eip05w020OptionCase optionCase) {
        List<Eip05w020OptionCase>list = ositemDao.getItemsByOsformnoAndQseqno(caseData.getOsformno(), caseData.getQseqno()).stream().map(t->{
            Eip05w020OptionCase eip05w020OptionCase = new Eip05w020OptionCase();
            eip05w020OptionCase.setIseqno(String.valueOf(t.getIseqno()));
            eip05w020OptionCase.setItemseq(String.valueOf(t.getItemseq()));
            eip05w020OptionCase.setItemdesc(t.getItemdesc());
            eip05w020OptionCase.setIsaddtext(t.getIsaddtext());
            return eip05w020OptionCase;
        }).collect(Collectors.toList());
        caseData.setOptionList(list);
    }

    /**
     * 取得要修改的調查主題資料
     * @param caseData
     * @param themeCase
     */
    public void getSingleFormData(Eip05w020Case caseData, Eip05w020ThemeCase themeCase) {
        Osformdata osformdata = osformdataDao.findByPk(caseData.getOsformno());
        Map<String,String> usersMap = getUsers();
        themeCase.setOsccode(String.valueOf(osformdata.getOsccode()));
        themeCase.setTopicname(osformdata.getTopicname());
        themeCase.setOsfmdt(osformdata.getOsfmdt().format(minguoformatterForInput));
        themeCase.setOsfmdtHour(StringUtils.substringBefore(osformdata.getOsfmdt().format(timeformatter),":"));
        themeCase.setOsfmdtMinute(StringUtils.substringAfter(osformdata.getOsfmdt().format(timeformatter),":"));
        themeCase.setOsendt(osformdata.getOsendt().format(minguoformatterForInput));
        themeCase.setOsendtHour(StringUtils.substringBefore(osformdata.getOsendt().format(timeformatter),":"));
        themeCase.setOsendtMinute(StringUtils.substringAfter(osformdata.getOsendt().format(timeformatter),":"));
        themeCase.setFullosfmdt(osformdata.getOsfmdt().format(minguoformatter));
        themeCase.setFullosendt(osformdata.getOsendt().format(minguoformatter));
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
        themeCase.setCreuser(StringUtils.defaultString(usersMap.get(osformdata.getCreuser())));
        themeCase.setCredt(osformdata.getCredt().format(minguoformatter));
        themeCase.setUpduser(StringUtils.defaultString(usersMap.get(osformdata.getUpduser())));
        themeCase.setUpddt(ObjectUtils.isNotEmpty(osformdata.getUpddt())?osformdata.getUpddt().format(minguoformatter):"");
    }

    /**
     * 取得要修改的部分標題資料
     * @param caseData
     * @param partCase
     */
    public void getSinglePartData(Eip05w020Case caseData, Eip05w020PartCase partCase) {
        Osquestion osquestion = osquestionDao.findByPk(caseData.getOsformno(),Integer.valueOf(partCase.getQseqno()));
        partCase.setSectitleseq(String.valueOf(osquestion.getSectitleseq()));
        partCase.setSectitle(osquestion.getSectitle());
    }

    /**
     * 取得要修改的題目資料
     * @param caseData
     * @param questionCase
     */
    public void getSingleQuestionData(Eip05w020Case caseData, Eip05w020QuestionCase questionCase) {
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
    public void getInsertFormData(Eip05w020ThemeCase themeCase) {
        Map<String,String>map = getMail();
        themeCase.setMailsubject(map.get("4"));
        themeCase.setMailmsg(map.get("5"));
    }

    /**
     * 取得要新增的選項資料
     * @param caseData
     * @param optionCase
     */
    public void getInsertOptionData(Eip05w020Case caseData, Eip05w020OptionCase optionCase) {
        Osquestion osquestion = osquestionDao.getSingleQuestionData(caseData.getOsformno(), caseData.getQseqno());
        optionCase.setTopic(osquestion.getTopic());
        optionCase.setTopicseq(String.valueOf(osquestion.getTopicseq()));
    }

    /**
     * 新增意見調查主題資料
     * @param themeCase
     * @param mode
     */
    public void insertUpdateData(Eip05w020ThemeCase themeCase, String mode) {
        Osformdata osformdata = new Osformdata();
        if ("A".equals(mode)) {
            String osformno = osformdataDao.getMaximumOsformno(DateUtility.getNowChineseYearMonth());
            osformdata.setOsformno("OS" + DateUtility.getNowChineseYearMonth() + (StringUtils.isBlank(osformno) ? "0001" :  StringUtils.leftPad(osformno, 4, "0")));
        }
        osformdata.setOsccode(Integer.valueOf(themeCase.getOsccode()));
        osformdata.setTopicname(themeCase.getTopicname());
        osformdata.setOsfmdt(LocalDateTime.of(Objects.requireNonNull(DateUtility.westDateToLocalDate(DateUtility.changeDateTypeToWestDate(themeCase.getOsfmdt()))), LocalTime.of(Integer.parseInt(themeCase.getOsfmdtHour()), Integer.parseInt(themeCase.getOsfmdtMinute()))));
        osformdata.setOsendt(LocalDateTime.of(Objects.requireNonNull(DateUtility.westDateToLocalDate(DateUtility.changeDateTypeToWestDate(themeCase.getOsendt()))), LocalTime.of(Integer.parseInt(themeCase.getOsendtHour()), Integer.parseInt(themeCase.getOsendtMinute()))));
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
    public String insertUpdatePartData(Eip05w020PartCase partCase, String mode) {
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
                //判斷題目流水號是否不同筆，不同筆的話才更新其他序號+1
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
    public void insertUpdateQuestionData(Eip05w020QuestionCase questionCase, String mode) {
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
            //判斷輸入序號是否重複，若有重複，將後面的序號全部+1
            if (osquestionDao.getSingleQuestionData(questionCase.getOsformno(),questionCase.getSectitleseq(),questionCase.getTopicseq()) != null) {
                osquestionDao.updateBatchTopicseq(questionCase.getOsformno(),questionCase.getSectitleseq(),questionCase.getTopicseq(),"",true);
            }
            osquestionDao.insertData(osquestion);
        }
        //更新
        if ("U".equals(mode)) {
            Osquestion dbOsquestion = osquestionDao.getSingleQuestionData(questionCase.getOsformno(),questionCase.getQseqno());
            Osquestion targetQuestion = osquestionDao.getSingleQuestionData(questionCase.getOsformno(),questionCase.getSectitleseq(),questionCase.getTopicseq());
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
            if (targetQuestion != null) {
                //判斷題目流水號是否不同筆，不同筆的話才更新其他序號+1
                if (dbOsquestion.getQseqno() != targetQuestion.getQseqno()) {
                    //目標題目序號大於當前題目序號
                    if (targetQuestion.getTopicseq() > dbOsquestion.getTopicseq()) {
                        //更新其他受影響序號
                        osquestionDao.updateBatchTopicseq(questionCase.getOsformno(), questionCase.getSectitleseq(), questionCase.getTopicseq(), "", true);
                    //目標序號小於當前序號
                    } else {
                        //將小於當前序號與大於等於目標序號都+1
                        osquestionDao.updateBatchTopicseq(questionCase.getOsformno(), questionCase.getSectitleseq(), String.valueOf(dbOsquestion.getTopicseq()), questionCase.getTopicseq(), false);
                    }
                }
            }
            osquestionDao.updateData(osquestion, osquestion.getOsformno(), osquestion.getQseqno());
        }
    }

    /**
     * 新增修改選項
     * @param optionCase
     * @param mode
     */
    public void insertUpdateOptionData(Eip05w020OptionCase optionCase, String mode) {
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
            //判斷輸入序號是否重複，若有重複，將後面的序號全部+1
            if (ositemDao.getSingleOptionData(optionCase.getOsformno(), optionCase.getQseqno(), optionCase.getItemseq()) != null) {
                ositemDao.updateBatchItemseq(optionCase.getOsformno(), optionCase.getQseqno(), optionCase.getItemseq(), "", true);
            }
            ositemDao.insertData(ositem);
        }
        //更新
        if ("U".equals(mode)) {
            Ositem dbOsitem = ositemDao.findByPk(optionCase.getOsformno(), Integer.valueOf(optionCase.getIseqno()));
            Ositem ositem = new Ositem();
            Ositem targetitem = ositemDao.getSingleOptionData(optionCase.getOsformno(), optionCase.getQseqno(), optionCase.getItemseq());
            BeanUtility.copyProperties(ositem,dbOsitem);
            ositem.setItemseq(Integer.valueOf(optionCase.getItemseq()));
            ositem.setItemdesc(optionCase.getItemdesc());
            ositem.setIsaddtext(optionCase.getIsaddtext());
            ositem.setUpddt(LocalDateTime.now());
            ositem.setUpduser(userData.getUserId());
            if (targetitem != null) {
                //判斷選項序號是否不同筆，不同筆的話才更新其他序號+1
                if (dbOsitem.getIseqno() != targetitem.getIseqno()) {
                    //目標題目序號大於當前題目序號
                    if (targetitem.getItemseq() > dbOsitem.getItemseq()) {
                        //更新其他受影響序號
                        ositemDao.updateBatchItemseq(optionCase.getOsformno(), optionCase.getQseqno(), optionCase.getItemseq(), "", true);
                    //目標序號小於當前序號
                    } else {
                        //將小於當前序號與大於等於目標序號都+1
                        ositemDao.updateBatchItemseq(optionCase.getOsformno(), optionCase.getQseqno(), String.valueOf(dbOsitem.getItemseq()), optionCase.getItemseq(), false);
                    }
                }
            }
            ositemDao.updateData(ositem, optionCase.getOsformno(), Integer.valueOf(optionCase.getIseqno()));
        }
    }

    /**
     * 取得預覽資料
     * @param caseData
     */
    public void getPreviewData(Eip05w020Case caseData) {
        List<Osquestion>questions = osquestionDao.getAllQuestionByOsformno(caseData.getOsformno());
        for(Osquestion ques : questions) {
            Eip05w020QuestionCase questionCase = new Eip05w020QuestionCase();
            List<Ositem>ositemList = ositemDao.getItemsByOsformnoAndQseqno(caseData.getOsformno(),  String.valueOf(ques.getQseqno()));
            questionCase.setSectitle(ques.getSectitle());
            questionCase.setTopic(ques.getTopic());
            questionCase.setOptiontype(ques.getOptiontype());
            questionCase.setIsrequired(ques.getIsrequired());
            questionCase.setRowspan(ques.getRowspan());
            for (Ositem option : ositemList) {
                Eip05w020OptionCase optionCase = new Eip05w020OptionCase();
                optionCase.setQseqno(String.valueOf(option.getQseqno()));
                optionCase.setIseqno(String.valueOf(option.getIseqno()));
                optionCase.setItemdesc(option.getItemdesc());
                optionCase.setIsaddtext(option.getIsaddtext());
                questionCase.getOptionList().add(optionCase);
            }
            if ("T".equals(ques.getOptiontype())) {
                Eip05w020OptionCase optionCase = new Eip05w020OptionCase();
                optionCase.setIsText("Y");
                questionCase.getOptionList().add(optionCase);
            }
            caseData.getPreviews().add(questionCase);
        }
    }

    /**
     * 取得填寫內容查詢列表
     * @param caseData
     */
    public void getContentQuery(Eip05w020Case caseData) {
        List<Osquestion>questions = osquestionDao.getAllQuestionByOsformno(caseData.getOsformno());
        for(Osquestion ques : questions) {
            Eip05w020QuestionCase questionCase = new Eip05w020QuestionCase();
            List<Ositem>ositemList = ositemDao.getItemsByOsformnoAndQseqno(caseData.getOsformno(),  String.valueOf(ques.getQseqno()));
            questionCase.setSectitle(ques.getSectitle());
            questionCase.setTopic(ques.getTopic());
            questionCase.setRowspan(ques.getRowspan());
            for (Ositem option : ositemList) {
                Eip05w020OptionCase optionCase = new Eip05w020OptionCase();
                optionCase.setQseqno(String.valueOf(option.getQseqno()));
                optionCase.setIseqno(String.valueOf(option.getIseqno()));
                optionCase.setItemdesc(option.getItemdesc());
                optionCase.setIsaddtext(option.getIsaddtext());
                questionCase.getOptionList().add(optionCase);
            }
            if ("T".equals(ques.getOptiontype())) {
                Eip05w020OptionCase optionCase = new Eip05w020OptionCase();
                optionCase.setIsText("Y");
                optionCase.setQseqno(String.valueOf(ques.getQseqno()));
                questionCase.getOptionList().add(optionCase);
            }
            caseData.getContents().add(questionCase);
        }
    }

    /**
     * 取得統計表欲產生內容
     * V1.0:會根據畫面勾選決定內容
     * V1.1:註解勾選部分邏輯，全部都顯示，未來如需要將註解打開
     * @param caseData
     */
    public void getStatistics(Eip05w020Case caseData) {
//        //有勾沒輸入文字，另外取出，之後特別計算該輸入種類數量
//        List<String> qseqnoEmptyTextList = caseData.getReviewTextcontents().stream()
//                .filter(f->StringUtils.isNotBlank(f.getQseqno()) && StringUtils.isBlank(f.getText()))
//                .map(Eip05w020Case.Textcontent::getQseqno).collect(Collectors.toList());
//        //有勾有輸入文字，另外取出
//        Map<String,String> qseqnoTextMap = caseData.getReviewTextcontents().stream()
//                .filter(f->StringUtils.isNotBlank(f.getQseqno()) && StringUtils.isNotBlank(f.getText()))
//                .collect(Collectors.toMap(Eip05w020Case.Textcontent::getQseqno, Eip05w020Case.Textcontent::getText));
//        //有勾，知道畫面上需出現該題目
//        List<String> qseqnoList = caseData.getReviewTextcontents().stream()
//                .map(Eip05w020Case.Textcontent::getQseqno)
//                .filter(StringUtils::isNotBlank).collect(Collectors.toList());
//        List<Ositem>checkitems = ositemDao.getAllByIseqnoAndQseqnoList(caseData.getOsformno(), caseData.getReviewMultiplecontents(), qseqnoList);
        List<Ositem>checkitems = ositemDao.getTopicByOsformno(caseData.getOsformno());
        Map<String,Integer> titleRowspanMap = new HashMap<>();
        Map<String,Integer> quesRowspanMap = new HashMap<>();
        for(Ositem item : checkitems) {
            Eip05w020Case.MixCase mixCase = new Eip05w020Case.MixCase();
            mixCase.setSectitle(item.getSectitle());
            mixCase.setSectitleseq(item.getSectitleseq());
            mixCase.setItemname(item.getItemname());//題目名稱(文字框)或選項名稱
            mixCase.setQseqno(String.valueOf(item.getQseqno()));
            mixCase.setNo(item.getNo());
            mixCase.setTopic(item.getTopic());
            mixCase.setTopicseq(item.getTopicseq());
            caseData.getReviews().add(mixCase);
            titleRowspanMap.putIfAbsent(String.valueOf(item.getSectitleseq()),0);
            quesRowspanMap.putIfAbsent(String.valueOf(item.getQseqno()),0);
            titleRowspanMap.computeIfPresent(String.valueOf(item.getSectitleseq()), (key, value) -> value + 1);
            quesRowspanMap.computeIfPresent(String.valueOf(item.getQseqno()), (key, value) -> value + 1);
        }
//        for (String qseqno : qseqnoEmptyTextList) {
//            Osquestion osquestion = osquestionDao.findByPk(caseData.getOsformno(), Integer.valueOf(qseqno));
//            Integer sectitleCount = CollectionUtils.isEmpty(caseData.getTextUiMap().get(qseqno)) ? 1 : caseData.getTextUiMap().get(qseqno).size();
//            //減1是因為原先存在rowspanmap的填空題一定是計1次，故減1將其扣除
//            titleRowspanMap.computeIfPresent(String.valueOf(osquestion.getSectitleseq()), (key, value) -> value + (sectitleCount - 1));
//        }
        List<Integer>qseqnoTextList = checkitems.stream().filter(t->t.getItemseq() == 999).map(Ositem::getQseqno).collect(Collectors.toList());
        for (Integer qseqno : qseqnoTextList) {
            Osquestion osquestion = osquestionDao.findByPk(caseData.getOsformno(), qseqno);
            Integer sectitleCount = CollectionUtils.isEmpty(caseData.getTextUiMap().get(String.valueOf(qseqno))) ? 1 : caseData.getTextUiMap().get(String.valueOf(qseqno)).size();
            //減1是因為原先存在rowspanmap的填空題一定是計1次，故減1將其扣除
            titleRowspanMap.computeIfPresent(String.valueOf(osquestion.getSectitleseq()), (key, value) -> value + (sectitleCount - 1));
        }
        caseData.setTitleRowspanMap(titleRowspanMap);
        caseData.setQuesRowspanMap(quesRowspanMap);
        log.debug("===titleRowspanMap===:"+titleRowspanMap);
        log.debug("===quesRowspanMap===:"+quesRowspanMap);
//        caseData.setQseqnoTextMap(qseqnoTextMap);
    }

    /**
     * 取得填寫內容
     * @param caseData
     * @throws JsonProcessingException
     */
    public String getWriteContents(Eip05w020Case caseData) throws JsonProcessingException {
        List<Osresult>osresultList = osresultDao.getListByOsformno(caseData.getOsformno());
        List<Integer>wriseqList = new ArrayList<>();
        List<String>topicList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(caseData.getReviewTextcontents())) {
            caseData.getReviewTextcontents().forEach(s->{
                if (StringUtils.isNotBlank(s.getQseqno())) {
                    topicList.add(String.valueOf(s.getQseqno()));
                }
            });
        }
        if (!CollectionUtils.isEmpty(caseData.getReviewMultiplecontents())) {
            caseData.getReviewMultiplecontents().forEach(s->{
                Ositem ositem = ositemDao.findByPk(caseData.getOsformno(),Integer.valueOf(s));
                if (ositem != null) {
                    if (!topicList.contains(String.valueOf(ositem.getQseqno()))) {
                        topicList.add(String.valueOf(ositem.getQseqno()));
                    }
                }
            });
        }
        List<String>sortTopicList = topicList.stream().map(t->{
            return osquestionDao.findByPk(caseData.getOsformno(),Integer.valueOf(t));
        }).sorted(Comparator.comparing(Osquestion::getSectitleseq).thenComparing(Osquestion::getTopicseq)).map(t -> String.valueOf(t.getQseqno())).collect(Collectors.toList());
        Map<String,String> qMap = getQMap(caseData.getOsformno());
        Map<String,String> iMap = getIMap(caseData.getOsformno());
        if (!CollectionUtils.isEmpty(caseData.getReviewMultiplecontents())) {
            for (String iseqno : caseData.getReviewMultiplecontents()) {
                for (Osresult osresult : osresultList) {
                    if (StringUtils.contains(osresult.getWricontent(), "\"v\":\"" + iseqno + "\"")) {
                        if (!wriseqList.contains(osresult.getWriseq())) {
                            wriseqList.add(osresult.getWriseq());
                        }
                    }
                }
            }
        }
        if (!CollectionUtils.isEmpty(caseData.getReviewTextcontents())) {
            boolean isBreak = false;
            for (Eip05w020Case.Textcontent textcontent : caseData.getReviewTextcontents()) {
                for (Osresult osresult : osresultList) {
                    if (StringUtils.isNotBlank(textcontent.getQseqno()) && StringUtils.isBlank(textcontent.getText())) {
                        wriseqList = osresultDao.getListByOsformno(caseData.getOsformno()).stream()
                                .map(Osresult::getWriseq).collect(Collectors.toList());
                        isBreak = true;
                        break;
                    } else if (StringUtils.isNotBlank(textcontent.getQseqno()) && StringUtils.isNotBlank(textcontent.getText())) {
                        if (StringUtils.contains(osresult.getWricontent(), "\"n\":\"" + textcontent.getQseqno() + "\"" + ",\"t\":\"" + textcontent.getText() + "\"")) {
                            if (!wriseqList.contains(osresult.getWriseq())) {
                                wriseqList.add(osresult.getWriseq());
                            }
                        }
                    }
                }
                if (isBreak) {
                    break;
                }
            }
        }
        if (!wriseqList.isEmpty()) {
            List<Osresult>uiOsresultList = osresultDao.getListByOsformnoAndList(caseData.getOsformno(), wriseqList);
            //填寫內容表標題
            List<String>tableTitles = new ArrayList<>(Arrays.asList("序號","填寫人"));
            //填寫內容表內容
            List<List<String>>tableData = new ArrayList<>();
            for (String topic : sortTopicList) {
                //如果勾選的選項有包含這一題就加入表內
                tableTitles.add(qMap.get(topic));
            }
            for (Osresult osresult : uiOsresultList) {
                ObjectMapper objectMapper = new ObjectMapper();
                List<List<Eip05w020Case.Answer>>allAnswer = new ArrayList<>();
                StringBuilder sb = new StringBuilder(osresult.getWriname()+",");
                //某個人的所有答案
                List<Eip05w020Case.Answer> answers = objectMapper.readValue(osresult.getWricontent(), new TypeReference<List<Eip05w020Case.Answer>>(){});
                for (Eip05w020Case.Answer a : answers) {
                    if (topicList.contains(a.getN())){
                        //組合單筆填寫內容
                        compositionWriteContent(sb, a, iMap);
                    }
                }
                if (sb.length()>1) {
                    // 删除最後一個逗號
                    sb.deleteCharAt(sb.length() - 1);
                }
                tableData.add(Arrays.asList(StringUtils.split(sb.toString(),",")));
            }
            caseData.setWriteContentTitle(tableTitles);
            caseData.setWriteContentData(tableData);
        } else {
            return "isEmpty";
//            caseData.setWriteContentTitle(new ArrayList<>(Arrays.asList("序號","填寫人")));
//            List<String>str = Arrays.asList("無符合資料");
//            caseData.getWriteContentData().add(str);
        }
//        log.debug("XXXX"+wriseqList.toString());
        return "";
    }

    /**
     * 取得所有填寫內容(EXCEL使用)
     * @param caseData
     * @return
     * @throws JsonProcessingException
     */
    public String getAllWriteContents(Eip05w020Case caseData) throws JsonProcessingException {
        Map<String,String> qMap = getQMap(caseData.getOsformno());
        Map<String,String> iMap = getIMap(caseData.getOsformno());
        List<Osresult>osresultList = osresultDao.getListByOsformno(caseData.getOsformno());
        if (!osresultList.isEmpty()) {
            //填寫內容表標題
            List<String>tableTitles = new ArrayList<>(Arrays.asList("序號","填寫人"));
            List<Integer>topicList = ositemDao.getTopicByOsformno(caseData.getOsformno()).stream().map(Ositem::getQseqno).distinct().collect(Collectors.toList());
            for (Integer topic : topicList) {
                //如果勾選的選項有包含這一題就加入表內
                tableTitles.add(qMap.get(String.valueOf(topic)));
            }
            //填寫內容表內容
            List<List<String>>tableData = new ArrayList<>();
            for (Osresult osresult : osresultList) {
                ObjectMapper objectMapper = new ObjectMapper();
                List<List<Eip05w020Case.Answer>>allAnswer = new ArrayList<>();
                StringBuilder sb = new StringBuilder(osresult.getWriname()+",");
                //某個人的所有答案
                List<Eip05w020Case.Answer> answers = objectMapper.readValue(osresult.getWricontent(), new TypeReference<List<Eip05w020Case.Answer>>(){});
                for (Eip05w020Case.Answer a : answers) {
                    //組合單筆填寫內容
                    compositionWriteContent(sb, a, iMap);
                }
                if (sb.length()>1) {
                    // 删除最後一個逗號
                    sb.deleteCharAt(sb.length() - 1);
                }
                tableData.add(Arrays.asList(StringUtils.split(sb.toString(),",")));
            }
            caseData.setWriteContentTitle(tableTitles);
            caseData.setWriteContentData(tableData);
        } else {
            return "isEmpty";
//            caseData.setWriteContentTitle(new ArrayList<>(Arrays.asList("序號","填寫人")));
//            List<String>str = Arrays.asList("無符合資料");
//            caseData.getWriteContentData().add(str);
        }
        return "";
    }

    /**
     * 組合單筆填寫內容
     * @param sb
     * @param answer
     * @param iMap
     * @return
     */
    private void compositionWriteContent(StringBuilder sb, Eip05w020Case.Answer answer, Map<String,String> iMap) {
        //填空題
        if (CollectionUtils.isEmpty(answer.getOs())) {
            sb.append(StringUtils.defaultIfEmpty(answer.getT()," ")).append(",");
            //選擇題
        } else if (!CollectionUtils.isEmpty(answer.getOs())) {
            //單選
            if (answer.getOs().size() == 1) {
                sb.append(iMap.get(answer.getOs().get(0).getV()));
                if (StringUtils.isNotBlank(answer.getOs().get(0).getT())) {
                    sb.append("(").append(answer.getOs().get(0).getT()).append(")");
                }
                sb.append(",");
            //多選
            } else {
                answer.getOs().forEach(m->{
                    if (StringUtils.isNotBlank(m.getV())) {
                        sb.append(iMap.get(m.getV()));
                        if (StringUtils.isNotBlank(m.getT())) {
                            sb.append("(").append(m.getT()).append(")");
                        }
                        sb.append("、");
                    }
                });
                // 如果最後一個字不是"、"，代表多選題沒有選擇答案，將填寫內容補" "
                if (sb.length() > 1 && !"、".equals(sb.substring(sb.length() - 1, sb.length()))) {
                    sb.append(" ");
                } else if (sb.length() > 1) {
                    // 删除最後一個、
                    sb.deleteCharAt(sb.length() - 1);
                }
                sb.append(",");
            }
        }
    }

    /**
     * 檢視統計表
     * @param caseData
     */
    public String getReviewStatistics(Eip05w020Case caseData) throws JsonProcessingException {
        List<String>wricontentList = osresultDao.getListByOsformno(caseData.getOsformno()).stream().map(Osresult::getWricontent).collect(Collectors.toList());
        List<Ositem>ositems = ositemDao.getAllByOsformno(caseData.getOsformno());
        List<Osquestion>osquestions = osquestionDao.getAllQuestionByOsformno(caseData.getOsformno());
        //key為選項序號iseqno，值為出現次數
        Map<String,Integer> multipleCountMap = new LinkedHashMap<>();
        //key為問題序號qseqno，值為分母
        Map<String,Integer> totalMap = new LinkedHashMap<>();
        //key為問題序號qseqno，值為text，紀錄有哪些文字內容(不重複)，供畫面使用
        Map<String,List<String>> textUiMap = new LinkedHashMap<>();
        //key為問題序號qseqno，值為text，紀錄有哪些文字內容(重複)
        Map<String,List<String>> textTotalMap = new LinkedHashMap<>();
        //key為text，值為出現次數與比例，供畫面使用
        Map<String, Eip05w020Case.Statistics> textDataMap = new LinkedHashMap<>();
        Map<String, Eip05w020Case.Statistics> multipleDataMap = new LinkedHashMap<>();
        if (wricontentList.isEmpty()) {
            return "isEmpty";
        }
        //處理文字前置資料
        ObjectMapper objectMapper = new ObjectMapper();
        List<List<Eip05w020Case.Answer>>allAnswer = new ArrayList<>();
        for (String json : wricontentList) {
            //某個人的所有答案
            List<Eip05w020Case.Answer> answers = objectMapper.readValue(json, new TypeReference<List<Eip05w020Case.Answer>>(){});
            for (Eip05w020Case.Answer a : answers) {
                if (StringUtils.isNotBlank(a.getT())) {
                    textUiMap.putIfAbsent(a.getN(), new ArrayList<>(Arrays.asList(a.getT())));
                    textTotalMap.putIfAbsent(a.getN(), new ArrayList<>());
                    if (textUiMap.containsKey(a.getN())) {
                        List<String>list = textUiMap.get(a.getN());
                        if(!list.contains(a.getT())) {
                            list.add(a.getT());
                        }
                    }
                    List<String>list = textTotalMap.get(a.getN());
                    list.add(a.getT());
                } else {
                    textUiMap.putIfAbsent(a.getN(), new ArrayList<>(Arrays.asList(" ")));
                    textTotalMap.putIfAbsent(a.getN(), new ArrayList<>());
                    if (textUiMap.containsKey(a.getN())) {
                        List<String>list = textUiMap.get(a.getN());
                        if(!list.contains(" ")) {
                            list.add(" ");
                        }
                    }
                    List<String>list = textTotalMap.get(a.getN());
                    list.add(" ");
                }
            }
        }

        //組成文字統計資料
        for (Map.Entry<String, List<String>> entry : textUiMap.entrySet()) {
            String key = entry.getKey();
            List<String> value = entry.getValue();
            for (String text : value) {
                double frequency = (double) Collections.frequency(textTotalMap.get(key), text);
                double all = textTotalMap.get(key).size();
                log.debug(text + " -> 次數:" + frequency + "分母:" + textTotalMap.get(key).size() + "比率:" + Math.round(frequency / all * 100) + "%");
                Eip05w020Case.Statistics statistics = new Eip05w020Case.Statistics();
                statistics.setCount(String.valueOf(Math.round(frequency)));
                statistics.setRate(Math.round(frequency / all * 100) + "%");
                textDataMap.put(key + "-" + text, statistics);
            }
        }

        //處理選擇題前置資料
        for (Ositem i : ositems) {
            multipleCountMap.putIfAbsent(String.valueOf(i.getIseqno()), 0);
            totalMap.putIfAbsent(String.valueOf(i.getQseqno()), 0);
            for (String json : wricontentList) {
                // 計算單選題及多選題的各個選項出現次數
                if (StringUtils.contains(json, "\"v\":\"" + String.valueOf(i.getIseqno()) + "\"")) {
                    multipleCountMap.computeIfPresent(String.valueOf(i.getIseqno()), (key, value) -> value + 1);
                }
                // 計算多選題的分母，如JSON有包含"q":"1","v":"1"，沒有填就不會有v
                if (StringUtils.contains(json, "\"q\":\"" + String.valueOf(i.getQseqno()) + "\"" + ",\"v\":\"" + String.valueOf(i.getIseqno()) + "\"")) {
                    totalMap.computeIfPresent(String.valueOf(i.getQseqno()), (key, value) -> value + 1);
                }
            }
        }
        for (Osquestion osquestion : osquestions) {
            // 計算單選題的分母，如JSON有包含"n":"7","os":[{"v"，沒有填就不會有os
            for (String json : wricontentList) {
                if (StringUtils.contains(json, "\"n\":\"" + String.valueOf(osquestion.getQseqno()) + "\"" + ",\"os\":[{\"v\"")) {
                    totalMap.computeIfPresent(String.valueOf(osquestion.getQseqno()), (key, value) -> value + 1);
                }
            }
        }

        //組成選擇題統計資料
        for (Ositem i : ositems) {
            Eip05w020Case.Statistics statistics = new Eip05w020Case.Statistics();
            double all = 0;
            double frequency = multipleCountMap.get(String.valueOf(i.getIseqno()));
            statistics.setCount(String.valueOf(multipleCountMap.get(String.valueOf(i.getIseqno()))));
            all = totalMap.get(String.valueOf(i.getQseqno()));
            statistics.setRate(Math.round(frequency/all*100)+"%");
            multipleDataMap.put(String.valueOf(i.getIseqno()), statistics);
        }
        log.debug("====multipleCountMap====:"+multipleCountMap.toString());
        log.debug("====totalMap====:"+totalMap.toString());
        log.debug("====textUiMap====:"+textUiMap.toString());
        log.debug("====textTotalMap====:"+textTotalMap.toString());
        log.debug("====textDataMap====:"+textDataMap.toString());
        log.debug("====multipleDataMap====:"+multipleDataMap.toString());
//        log.debug("====rowspanMap====:"+rowspanMap.toString());
        caseData.setMultipleDataMap(multipleDataMap);
        caseData.setTextDataMap(textDataMap);
        caseData.setTextUiMap(textUiMap);
        getStatistics(caseData);
//        getWriteContents(caseData);
        return "";
    }

    /**
     * 產製統計表
     * @param caseData
     */
    public ByteArrayOutputStream prodStatistics(Eip05w020Case caseData) {
        Eip05w020L00 eip05w020L00 = new Eip05w020L00();
        eip05w020L00.addHeader(caseData);
        eip05w020L00.addContent(caseData);
        return eip05w020L00.getOutputStream();
    }

    /**
     * 產製填寫內容
     * @param caseData
     */
    public ByteArrayOutputStream prodWriteContent(Eip05w020Case caseData) {
        //每個題目的最大長度List，用於設定寬度，size等於題目數量(序號除外)
        List<Integer>lengthList = new ArrayList<>();
        List<String> titles = caseData.getWriteContentTitle();
        List<List<String>> writeContent = caseData.getWriteContentData();
        for (String title : titles) {
            lengthList.add(title.length());
        }
        //移除序號的值
        lengthList.remove(0);
        //計算每個題目的最大長度依序放入
        for (List<String> list : writeContent) {
            for (int j = 0; j < list.size(); j++) {
                //開始比較長度並放入
                if (lengthList.get(j) < list.get(j).length()) {
                    lengthList.set(j, list.get(j).length());
                }
            }
        }
        Eip05w020L01 eip05w020L01 = new Eip05w020L01(lengthList);
        eip05w020L01.addHeader(caseData);
        eip05w020L01.addContent(caseData);
        return eip05w020L01.getOutputStream();
    }

    /**
     * 刪除畫面所選表單
     * @param caseData
     */
    public void deleteCheckedForm(Eip05w020Case caseData, String delType) {
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

    public void copyFormData(Eip05w020Case caseData) {
        Osformdata dbOsformdata = osformdataDao.findByPk(caseData.getOsformno());
        Osformdata newOsformdata = new Osformdata();
        BeanUtility.copyProperties(newOsformdata, dbOsformdata);
        String osformno = osformdataDao.getMaximumOsformno(DateUtility.getNowChineseYearMonth());
        String newOsformno = "OS" + DateUtility.getNowChineseYearMonth() + (StringUtils.isBlank(osformno) ? "0001" :  StringUtils.leftPad(osformno, 4, "0"));
        newOsformdata.setOsformno(newOsformno);
        newOsformdata.setTopicname(dbOsformdata.getTopicname()+"-複製");
        newOsformdata.setStatus("N");
        newOsformdata.setCreuser(userData.getUserId());
        newOsformdata.setCredt(LocalDateTime.now());
        newOsformdata.setUpduser(null);
        newOsformdata.setUpddt(null);
        osformdataDao.insertData(newOsformdata);

        List<Osquestion> dbOsquestionList = osquestionDao.getAllByOsformno(caseData.getOsformno());
        dbOsquestionList.stream().forEach(t->{
            Osquestion newOsquestion = new Osquestion();
            BeanUtility.copyProperties(newOsquestion, t);
            newOsquestion.setOsformno(newOsformno);
            newOsquestion.setCreuser(userData.getUserId());
            newOsquestion.setCredt(LocalDateTime.now());
            newOsquestion.setUpduser(null);
            newOsquestion.setUpddt(null);
            osquestionDao.insertData(newOsquestion);
        });

        List<Ositem> dbOsitemList = ositemDao.getAllByOsformno(caseData.getOsformno());
        dbOsitemList.stream().forEach(t->{
            Ositem newOsitem = new Ositem();
            BeanUtility.copyProperties(newOsitem, t);
            newOsitem.setOsformno(newOsformno);
            newOsitem.setCreuser(userData.getUserId());
            newOsitem.setCredt(LocalDateTime.now());
            newOsitem.setUpduser(null);
            newOsitem.setUpddt(null);
            ositemDao.insertData(newOsitem);
        });
    }

    /**
     * 上架畫面所選表單
     * @param caseData
     */
    public void putForm(Eip05w020Case caseData) {
        List<String>list = Collections.singletonList(caseData.getOsformno());
        osformdataDao.updateStatus(list, "P");
    }

    /**
     * 下架畫面所選表單
     * @param caseData
     */
    public void offForm(Eip05w020Case caseData) {
        List<String>list = Collections.singletonList(caseData.getOsformno());
        osformdataDao.updateStatus(list, "T");
    }
}
