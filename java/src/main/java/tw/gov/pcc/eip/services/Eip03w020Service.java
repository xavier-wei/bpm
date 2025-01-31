package tw.gov.pcc.eip.services;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tw.gov.pcc.eip.common.cases.Eip03w020Case;
import tw.gov.pcc.eip.common.cases.Eip03w020MixCase;
import tw.gov.pcc.eip.common.controllers.Eip03w020Controller;
import tw.gov.pcc.eip.dao.*;
import tw.gov.pcc.eip.domain.*;
import tw.gov.pcc.eip.framework.domain.UserBean;
import tw.gov.pcc.eip.util.DateUtility;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 重要列管事項_解除列管
 * @author 2201009
 */
@Service
public class Eip03w020Service {
    private static final Logger log = LoggerFactory.getLogger(Eip03w020Controller.class);

    @Autowired
    private KeepTrkMstDao keepTrkMstDao;
    @Autowired
    private KeepTrkDtlDao keepTrkDtlDao;
    @Autowired
    private EipcodeDao eipcodeDao;
    @Autowired
    private UsersDao usersDao;
    @Autowired
    private DeptsDao deptsDao;
    @Autowired
    private UserBean userData;
    @Autowired
    private MailService mailService;

    public void initDataList(Eip03w020Case caseData) {
        //預設撈取 TrkSts = 1-未完成 PrcSts = 1-待處理
        List<Eip03w020Case> keepTrkMstList = keepTrkMstDao.selectByColumnsForFillInProgress(null,null,null,null,
                                                                                            "1", "1", userData.getDeptId());
        List<Eipcode> trkStsList = eipcodeDao.findByCodeKind("TRKSTS");

        // 替換 trkSts 為中文字
        keepTrkMstList.forEach( a-> {
            trkStsList.forEach( b-> {
                if(a.getTrkSts().equals(b.getCodeno())){
                    a.setTrkSts(b.getCodename());
                }
            });
        });
        caseData.setKeepTrkMstList(keepTrkMstList);

        //下拉式選單
        caseData.setTrkStsCombobox(getAllTrkSts());
        caseData.setPrcStsCombobox(getAllPrcSts());

        caseData.setTrkSts("1");  //預設撈取"1-未完成"
        caseData.setPrcSts("1");  //預設撈取"1-待處理"

    }

    /**
     * 取得所有列管狀態下拉式選單
     * @return
     */
    public Map<String, String> getAllTrkSts(){
        List<Eipcode> list = eipcodeDao.findByCodekindOrderByCodeno("TRKSTS");
        Map<String, String> map = new LinkedHashMap<>();
        list.removeIf(a->{
            return a.getCodeno().equals("0");
        });
        list.forEach(t -> map.put(t.getCodeno(), StringUtils.isEmpty(t.getCodename()) ? "" : t.getCodename()));
        return map;
    }

    /**
     * 取得所有處理狀態下拉式選單
     * @return
     */
    public Map<String, String> getAllPrcSts(){
        List<Eipcode> list = eipcodeDao.findByCodekindOrderByCodeno("TRKPRCSTS");
        Map<String, String> map = new LinkedHashMap<>();

        list.forEach(t -> map.put(t.getCodeno(), StringUtils.isEmpty(t.getCodename()) ? "" : t.getCodename()));
        return map;
    }


    /**
     * 依條件查詢重要列管事項
     *
     * @param caseData
     */
    public void queryKeepTrk(Eip03w020Case caseData){

        caseData.setAllStDtSt(caseData.getAllStDtSt() != null ? DateUtility.changeDateTypeToWestDate(caseData.getAllStDtSt()) : "");
        caseData.setAllStDtEnd(caseData.getAllStDtEnd() != null ? DateUtility.changeDateTypeToWestDate(caseData.getAllStDtEnd()) : "");

        List<Eip03w020Case> resultList = keepTrkMstDao.selectByColumnsForFillInProgress(caseData.getTrkID(),caseData.getTrkCont(),caseData.getAllStDtSt(),
                                                                        caseData.getAllStDtEnd(), caseData.getTrkSts(), caseData.getPrcSts(), userData.getDeptId());

        List<Eipcode> trkStsList = eipcodeDao.findByCodeKind("TRKSTS");

        // 替換 trkSts 為中文字
        resultList.forEach( a-> {
            trkStsList.forEach( b-> {
                if(a.getTrkSts().equals(b.getCodeno())){
                    a.setTrkSts(b.getCodename());
                }
            });
            if (a.getTrkCont() != null){
                a.setTrkCont(a.getTrkCont().replaceAll("\r\n","<br>"));
            }
        });
        caseData.setKeepTrkMstList(resultList);

        //下拉式選單
        caseData.setTrkStsCombobox(getAllTrkSts());
        caseData.setPrcStsCombobox(getAllPrcSts());
        if (caseData.getAllStDtSt() != null) {
            caseData.setAllStDtSt(DateUtility.changeDateTypeToChineseDate(caseData.getAllStDtSt()));
        }
        if (caseData.getAllStDtEnd() != null) {
            caseData.setAllStDtEnd(DateUtility.changeDateTypeToChineseDate(caseData.getAllStDtEnd()));
        }
    }


    /**
     * 依查詢重要列管事項 for detail
     *
     * @param caseData
     */
    public void queryKeepTrkDetail(Eip03w020Case caseData,  Eip03w020MixCase mixCase){
        KeepTrkMst km = new KeepTrkMst();
        km.setTrkID(caseData.getSelectedTrkID());
        km = keepTrkMstDao.selectDataByPrimaryKey(km);

//        列管事項 列管對象
        mixCase.setTrkID(km.getTrkID());
        mixCase.setTrkSts(eipcodeDao.findByCodeKindCodeNo("TRKSTS", km.getTrkSts()).get().getCodename());
        if (km.getTrkCont() != null){
            mixCase.setTrkCont(km.getTrkCont().replaceAll("\r\n","<br>"));
        }
        mixCase.setTrkFrom(km.getTrkFrom());
        mixCase.setAllStDt(km.getAllStDt()); //全案列管日期
        mixCase.setClsDt(km.getClsDt()); //結案日期
//        km.getCreDept(), km.getUpdDept()

        Map<String, String> deptsNameMap = deptsDao.findNameByMultiID(Arrays.asList(km.getCreDept(), km.getUpdDept()))
                                                    .stream()
                                                    .collect(Collectors.toMap(Depts::getDept_id, Depts::getDept_name));
        mixCase.setCreDept(km.getCreDept() != null? deptsNameMap.get(km.getCreDept()) : "");
        mixCase.setCreUser(km.getCreUser() != null? getUserName(km.getCreUser()) : "");
        String creDt = DateUtility.parseLocalDateTimeToChineseDateTime(km.getCreDt(),true);
        creDt = DateUtility.formatChineseDateTimeString(creDt,false);
        mixCase.setCreDt(creDt);
//        mixCase.setUpdDt(km.getUpdDt() == null? "": km.getUpdDt().format(fmt).replaceAll("-",""));
        if (km.getUpdDt() != null){
            mixCase.setUpdDept(deptsNameMap.get(km.getUpdDept()));
            mixCase.setUpdUser(getUserName(km.getUpdUser()));
            String updDt = DateUtility.parseLocalDateTimeToChineseDateTime(km.getUpdDt(),true);
            updDt = DateUtility.formatChineseDateTimeString(updDt,false);
            mixCase.setUpdDt(updDt);
        }

        List<KeepTrkDtl> kdList = keepTrkDtlDao.selectDataByTrkIDAndTrkObj(caseData.getSelectedTrkID(),"");

        kdList.forEach( a-> {
            a.setPrcSts(String.valueOf(eipcodeDao.findByCodeKindCodeNo("TRKPRCSTS",a.getPrcSts()).get().getCodename()));
            a.setTrkObj(a.getTrkObj() + "-" + deptsDao.findByPk(a.getTrkObj()).getDept_name() + "-" + deptsDao.findByPk(a.getTrkObj()).getDept_id_p());

//            取得所有列管對象
            mixCase.getTrkObjList().add(a.getTrkObj());
        });
        mixCase.setKdList(kdList);

        //填報辦理進度 填報辦理進度
        Map<String, Map<String,String>> doubleMap = new HashMap<>();
        kdList.forEach(a -> {
            String trkObjNo = a.getTrkObj().split("-")[0];
            List<KeepTrkDtl> list = keepTrkDtlDao.selectDataByTrkIDAndTrkObj(a.getTrkID(), trkObjNo);
            Map<String,String> innerMap = new HashMap<>();
            KeepTrkDtl ktdItem = list.get(0);
            innerMap.put("trkID", ktdItem.getTrkID());      //列管事項編號
            innerMap.put("trkObj", ktdItem.getTrkObj());     //列管對象 (處室)

//            Depts depts = deptsDao.findByPk();
//            caseData.setResultList(resultList.stream().map(Eip06w010Case::new).collect(Collectors.toList()));

            Map<String, Depts> deptsMap = deptsDao.findNameByMultiID(Arrays.asList(ktdItem.getTrkObj(), ktdItem.getSupDept()))
                                                    .stream().collect(Collectors.toMap(Depts::getDept_id, a1 -> a1));

            innerMap.put("trkObjRoot", deptsMap.get(ktdItem.getTrkObj()).getDept_id_p());     //列管對象 (上層部門)
            innerMap.put("trkObjName", deptsMap.get(ktdItem.getTrkObj()).getDept_name());     //列管對象 (處室)
            innerMap.put("prcSts", eipcodeDao.findByCodeKindCodeNo("TRKPRCSTS", ktdItem.getPrcSts()).get().getCodename());    //處理狀態：1-待處理 2-待解列 3-已解列
            innerMap.put("stDt", ktdItem.getStDt());   //列管起日
            innerMap.put("endDt", ktdItem.getEndDt());     //列管迄日
            // 辦理情形: 當狀態為不可編輯時才需替換，textarea 無須替換
            // 若為[KeepTrkDtl.Prcsts!=3(已解列)且KeepTrkDtl.TrkObj為登入者部室]，則綠字欄位開放編輯
            boolean isEditable = (!Objects.equals(ktdItem.getPrcSts(), "3")) && ktdItem.getTrkObj().equals(userData.getDeptId());
            if (ktdItem.getRptCont() != null && !isEditable){
                innerMap.put("rptCont", ktdItem.getRptCont().replaceAll("\r\n","<br>"));
            }else {
                innerMap.put("rptCont", ktdItem.getRptCont());
            }
            innerMap.put("rptRate", String.valueOf(ktdItem.getRptRate()));    //辦理完成進度(0-100)
            innerMap.put("rptAskEnd", ktdItem.getRptAskEnd());  //是否要求解列(Y/N)
            innerMap.put("rptDept", ktdItem.getRptDept());   //指定填報單位
            innerMap.put("rptUser", ktdItem.getRptUser());    //指定填報人員

            if (ktdItem.getRptDept() != null){  //指定填報單位
                innerMap.put("rptDeptName", deptsDao.findNameByMultiID(Arrays.stream(ktdItem.getRptDept().split(";")).collect(Collectors.toList())).stream()
                        .filter(Objects::nonNull)
                        .map(Depts::getDept_name)
                        .collect(Collectors.joining(";")));
            }
            if (ktdItem.getRptUser() != null && !ktdItem.getRptUser().equals("")){ //指定填報人員
                innerMap.put("rptUserName", usersDao.findNameByMultiID(Arrays.stream(ktdItem.getRptUser().split(";")).collect(Collectors.toList())).stream()
                        .filter(Objects::nonNull)
                        .map(Users::getUser_name)
                        .collect(Collectors.joining(";")));
            }

            innerMap.put("rptUpdUser", ktdItem.getRptUpdUser() != null? getUserName(ktdItem.getRptUpdUser()) : ""); //填報更新人員
            String rptUpdDt = DateUtility.parseLocalDateTimeToChineseDateTime(ktdItem.getRptUpdDt(), true);
            if(StringUtils.isNotBlank(rptUpdDt)){
                rptUpdDt = rptUpdDt.substring(0,3) + "/" + rptUpdDt.substring(3,5) + "/" + rptUpdDt.substring(5,7) + "  " + rptUpdDt.substring(7,9) + ":" + rptUpdDt.substring(9,11) + ":" + rptUpdDt.substring(11);
            }

            innerMap.put("rptUpdDt", rptUpdDt);   //填報更新日期時間
            innerMap.put("supCont", ktdItem.getSupCont());    //回應內容
            innerMap.put("supAgree", ktdItem.getSupAgree());   //是否同意解列(Y/N)
            innerMap.put("supDept", ktdItem.getSupDept() != null? deptsMap.get(ktdItem.getSupDept()).getDept_name():"");    //回應人員所屬部門
            innerMap.put("supUser", ktdItem.getSupUser()!= null? getUserName(ktdItem.getSupUser()):"");    //回應人員

            String supDt = DateUtility.parseLocalDateTimeToChineseDateTime(ktdItem.getSupDt(), true);
            if(StringUtils.isNotBlank(supDt)){
                supDt = supDt.substring(0,3) + "/" + supDt.substring(3,5) + "/" + supDt.substring(5,7) + "  " + supDt.substring(7,9) + ":" + supDt.substring(9,11) + ":" + supDt.substring(11);
            }
            innerMap.put("supDt", supDt);      //回應日期時間
//            a.setTrkObj(a.getTrkObj() + "-" + deptsDao.findByPk(trkObjNo).getDept_name());
            doubleMap.put(a.getTrkObj().split("-")[0], innerMap);
        });

        mixCase.setCurrentDept(userData.getDeptId());
        mixCase.setCurrentRoot(deptsDao.findByPk(userData.getDeptId()).getDept_id_p());
        mixCase.setDoubleMap(doubleMap);
    }


    /**
     * 更新
     * @param mixCase
     */
    public void update(Eip03w020MixCase mixCase){
            if ( mixCase.getDoubleMap().size() > 0){
                for (String outerKey: mixCase.getDoubleMap().keySet()) {
                    KeepTrkDtl ktd = new KeepTrkDtl();
                    Map<String, String> innerMap = mixCase.getDoubleMap().get(outerKey);
                    ktd = keepTrkDtlDao.selectDataByTrkIDAndTrkObj(mixCase.getSelectedTrkID(), outerKey).get(0);

                    ktd.setRptCont(innerMap.get("rptCont"));
                    ktd.setRptAskEnd(innerMap.get("rptAskEnd"));
                    ktd.setRptRate(Integer.parseInt(innerMap.get("rptRate")));
                    ktd.setRptDept(mixCase.getDeptCodes());
                    ktd.setRptUser(mixCase.getUsersCodes());
                    ktd.setRptUpdUser(userData.getUserId());
                    ktd.setRptUpdDt(DateUtility.getNowDateTimeAsTimestamp().toLocalDateTime());
                    keepTrkDtlDao.updateForApplyProgress(ktd);

                    // 若為要求解列(即KeepTrkDtl.RptAskEnd=Y者)，須通知解列人員
                    //1108 申請解列時，改通知KEEPTRKMST.CREUSER」
                    if(innerMap.get("rptAskEnd").equals("Y")){
                        List<String> receiverIDList = new ArrayList<>();
                        KeepTrkMst ktm = new KeepTrkMst();
                        ktm.setTrkID(ktd.getTrkID());
                        ktm = keepTrkMstDao.selectDataByPrimaryKey(ktm);
                        receiverIDList.add(ktm.getCreUser());
                        sendMail(receiverIDList, "003", ktd);
                    }
                }
            }
        }

    //取得相關部門email後寄發
    public void sendMail(List<String> receiverIDList, String trkStatus, KeepTrkDtl ktd ){
        if (receiverIDList.size() > 0){
            List<Users> emailList = usersDao.getEmailList(receiverIDList);
            for (Users users : emailList) {
                log.debug("發送郵件至:" + users.getEmail());
                List<Eipcode> content = eipcodeDao.findByCodeKindScodeno("TRKMAILMSG", trkStatus);
                String trkObj = deptsDao.findByPk(ktd.getTrkObj()).getDept_name();
                String subject = content.get(0).getCodename().replace("@TrkId@", ktd.getTrkID())
                                                             .replace("@TrkObj@", trkObj);

                String mailMsg = content.get(1).getCodename().replaceAll("@TrkId@", ktd.getTrkID())
                                                             .replace("@TrkObj@", trkObj);

                mailService.sendEmailNow(subject, users.getEmail(), mailMsg);
            }
        }
    }

    /**
     * userDao 依userCode查詢userName
     * 查詢成功返回userName 若無則返回員工代碼
     * @param userCode
     * @return
     */
    public String getUserName (String userCode){
        return usersDao.selectByKey(userCode) == null? userCode : usersDao.selectByKey(userCode).getUser_name();
    }
}
