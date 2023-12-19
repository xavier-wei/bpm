package tw.gov.pcc.eip.services;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tw.gov.pcc.eip.common.cases.Eip03w030Case;
import tw.gov.pcc.eip.common.cases.Eip03w030MixCase;
import tw.gov.pcc.eip.common.controllers.Eip03w030Controller;
import tw.gov.pcc.eip.dao.*;
import tw.gov.pcc.eip.domain.*;
import tw.gov.pcc.eip.framework.domain.UserBean;
import tw.gov.pcc.eip.util.DateUtility;

import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;


/**
 * 重要列管事項_解除列管
 * @author 2201009
 */
@Service
public class Eip03w030Service {
    private static final Logger log = LoggerFactory.getLogger(Eip03w030Controller.class);

    @Autowired
    private KeepTrkMstDao keepTrkMstDao;
    @Autowired
    private KeepTrkDtlDao keepTrkDtlDao;
    @Autowired
    private EipcodeDao eipcodeDao;
    @Autowired
    private DeptsDao deptsDao;
    @Autowired
    private UsersDao usersDao;
    @Autowired
    private MailService mailService;
    public void initDataList(Eip03w030Case caseData) {
        List<Eip03w030Case> keepTrkMstList = keepTrkMstDao.selectByColumnsForCaclControl(null,null,null,null, "1", "2");
        List<Eipcode> trkStsList = eipcodeDao.findByCodeKind("TRKSTS");

        // 替換 trkSts 為中文字
        keepTrkMstList.forEach( a-> {
            trkStsList.forEach( b-> {
                if(a.getTrkSts().equals(b.getCodeno())){
                    a.setTrkSts(b.getCodename());
                }
            });
        });
        //下拉式選單
        trkStsList.removeIf(a -> {
            return a.getCodeno().equals("0");
        });

        caseData.setKeepTrkMstList(keepTrkMstList);
        caseData.setTrkStsList(trkStsList);
        caseData.setTrkSts("1");
        caseData.setPrcSts("2");

    }

    /**
     * 依條件查詢重要列管事項
     *
     * @param caseData
     */
    public void queryKeepTrk(Eip03w030Case caseData){

        caseData.setAllStDtSt(caseData.getAllStDtSt() != null ? DateUtility.changeDateTypeToWestDate(caseData.getAllStDtSt()) : "");
        caseData.setAllStDtEnd(caseData.getAllStDtEnd() != null ? DateUtility.changeDateTypeToWestDate(caseData.getAllStDtEnd()) : "");
        List<Eip03w030Case> resultList = keepTrkMstDao.selectByColumnsForCaclControl(caseData.getTrkID(),caseData.getTrkCont(),caseData.getAllStDtSt(),
                                                                       caseData.getAllStDtEnd(), caseData.getTrkSts(), caseData.getPrcSts());
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
        //下拉式選單
        trkStsList.removeIf(a -> {
            return a.getCodeno().equals("0");
        });
        if (caseData.getAllStDtSt() != null) {
            caseData.setAllStDtSt(DateUtility.changeDateTypeToChineseDate(caseData.getAllStDtSt()));
        }
        if (caseData.getAllStDtEnd() != null) {
            caseData.setAllStDtEnd(DateUtility.changeDateTypeToChineseDate(caseData.getAllStDtEnd()));
        }
        caseData.setKeepTrkMstList(resultList);
        caseData.setTrkStsList(trkStsList);
    }


    /**
     * 依查詢重要列管事項 for detail
     *
     * @param caseData
     */
    public void queryKeepTrkDetail(Eip03w030Case caseData,  Eip03w030MixCase mixCase){
        KeepTrkMst km = new KeepTrkMst();
        km.setTrkID(caseData.getSelectedTrkID());
        km = keepTrkMstDao.selectDataByPrimaryKey(km);
        DateTimeFormatter fmt = DateTimeFormatter.ISO_LOCAL_DATE;

        Map<String, String> deptsNameMap = deptsDao.findNameByMultiID(Arrays.asList(km.getCreDept(), km.getUpdDept()))
                .stream()
                .collect(Collectors.toMap(Depts::getDept_id, Depts::getDept_name));

        //列管事項 列管對象
        mixCase.setTrkID(km.getTrkID());
        mixCase.setTrkSts(eipcodeDao.findByCodeKindCodeNo("TRKSTS", km.getTrkSts()).get().getCodename());
        if (km.getTrkCont() != null){
            mixCase.setTrkCont(km.getTrkCont().replaceAll("\r\n","<br>"));
        }
        mixCase.setTrkFrom(km.getTrkFrom());
        mixCase.setAllStDt(km.getAllStDt()); //全案列管日期
        mixCase.setClsDt(km.getClsDt()); //結案日期
        mixCase.setCreDept(km.getCreDept() != null? deptsNameMap.get(km.getCreDept()) : "");
        mixCase.setCreUser(km.getCreUser() != null? getUserName(km.getCreUser()) : "");
//        mixCase.setCreDt(km.getCreDt() == null? "": km.getCreDt().format(fmt).replaceAll("-",""));
        String creDt = DateUtility.parseLocalDateTimeToChineseDateTime(km.getCreDt(),true);
        creDt = DateUtility.formatChineseDateTimeString(creDt,false);
        mixCase.setCreDt(creDt);
//        mixCase.setUpdDept(deptsDao.findByPk(km.getUpdDept()).getDept_name());
//        mixCase.setRptUpdUser(usersDao.selectByKey(km.getUpdUser()).getUser_name());
//        mixCase.setUpdDt(km.getUpdDt() == null? "": km.getUpdDt().format(fmt).replaceAll("-",""));
        if (km.getUpdDt() != null){
            mixCase.setUpdDept(deptsNameMap.get(km.getUpdDept()));
            mixCase.setUpdUser(getUserName(km.getUpdUser()));
            String updDt = DateUtility.parseLocalDateTimeToChineseDateTime(km.getUpdDt(),true);
            updDt = DateUtility.formatChineseDateTimeString(updDt,false);
            mixCase.setUpdDt(updDt);
        }
        List<KeepTrkDtl> kdList = keepTrkDtlDao.selectDataByTrkIDAndTrkObj(caseData.getSelectedTrkID(),"");
        List<Eipcode> trkStsList = eipcodeDao.findByCodeKind("TRKPRCSTS");

        kdList.forEach( a-> {
            // 替換 trkSts 為中文字
            trkStsList.forEach( b-> {
                if(a.getPrcSts().equals(b.getCodeno())){
                    a.setPrcSts(b.getCodename());
                }
            });
            //取得所有列管對象
            mixCase.getTrkObjList().add(a.getTrkObj());
        });
        mixCase.setKdList(kdList);

        //填報辦理進度 解除列管
        Map<String, Map<String,String>> doubleMap = new HashMap<>();
        kdList.forEach(a -> {
            List<KeepTrkDtl> list = keepTrkDtlDao.selectDataByTrkIDAndTrkObj(a.getTrkID(), a.getTrkObj());
            Map<String,String> innerMap = new HashMap<>();
            KeepTrkDtl ktdItem = list.get(0);
            Map<String, Depts> deptsMap = deptsDao.findNameByMultiID(Arrays.asList(ktdItem.getTrkObj(), ktdItem.getSupDept()))
                                                    .stream().collect(Collectors.toMap(Depts::getDept_id, a1 -> a1));

            innerMap.put("trkID", ktdItem.getTrkID());      //列管事項編號
            innerMap.put("trkObj", ktdItem.getTrkObj());     //列管對象 (處室)
            innerMap.put("trkObjName", deptsMap.get(ktdItem.getTrkObj()).getDept_name());
            innerMap.put("prcSts", eipcodeDao.findByCodeKindCodeNo("TRKPRCSTS", ktdItem.getPrcSts()).get().getCodename());    //處理狀態：1-待處理 2-待解列 3-已解列
            innerMap.put("stDt", ktdItem.getStDt());   //列管起日
            innerMap.put("endDt", ktdItem.getEndDt());     //列管迄日
            if (ktdItem.getRptCont() != null){
                ktdItem.setRptCont(ktdItem.getRptCont().replaceAll("\r\n","<br>"));
            }
            innerMap.put("rptCont", ktdItem.getRptCont());    //辦理情形
            innerMap.put("rptRate", String.valueOf(ktdItem.getRptRate()));    //辦理完成進度(0-100)
            innerMap.put("rptAskEnd", StringUtils.equals(ktdItem.getRptAskEnd(), "Y")? "是" : "否");  //是否要求解列(Y/N)
            innerMap.put("rptDept", ktdItem.getRptDept());   //指定填報單位
            innerMap.put("rptUser", ktdItem.getRptUser());    //指定填報人員
            innerMap.put("rptUpdUser", ktdItem.getRptUpdUser() != null? getUserName(ktdItem.getRptUpdUser()) : ""); //填報更新人員

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

            String rptUpdDt = DateUtility.parseLocalDateTimeToChineseDateTime(ktdItem.getRptUpdDt(), true);
            if(StringUtils.isNotBlank(rptUpdDt)){
                rptUpdDt = rptUpdDt.substring(0,3) + "/" + rptUpdDt.substring(3,5) + "/" + rptUpdDt.substring(5,7) + "  " + rptUpdDt.substring(7,9) + ":" + rptUpdDt.substring(9,11) + ":" + rptUpdDt.substring(11);
            }

            innerMap.put("rptUpdDt", rptUpdDt);   //填報更新日期時間
            innerMap.put("supCont", ktdItem.getSupCont());    //回應內容
            innerMap.put("supAgree", ktdItem.getSupAgree());   //是否同意解列(Y/N)
            innerMap.put("supDept", ktdItem.getSupDept()!= null? deptsMap.get(ktdItem.getSupDept()).getDept_name():"");    //回應人員所屬部門
            innerMap.put("supUser", ktdItem.getSupUser()!= null? getUserName(ktdItem.getSupUser()):"");    //回應人員

            String supDt = DateUtility.parseLocalDateTimeToChineseDateTime(ktdItem.getSupDt(), true);
            if(StringUtils.isNotBlank(supDt)){
                supDt = supDt.substring(0,3) + "/" + supDt.substring(3,5) + "/" + supDt.substring(5,7) + "  " + supDt.substring(7,9) + ":" + supDt.substring(9,11) + ":" + supDt.substring(11);
            }
            innerMap.put("supDt", supDt);      //回應日期時間

            a.setTrkObj(a.getTrkObj() + "-" + deptsDao.findByPk(a.getTrkObj()).getDept_name());
            doubleMap.put(a.getTrkObj().split("-")[0], innerMap);
        });
        mixCase.setDoubleMap(doubleMap);
    }


    /**
     * 更新
     * @param userData
     * @param mixCase
     */
    public void update(UserBean userData, Eip03w030MixCase mixCase){
            if ( mixCase.getDoubleMap().size() > 0){
                for (String outerKey: mixCase.getDoubleMap().keySet()) {
                    KeepTrkDtl ktd = keepTrkDtlDao.selectDataByTrkIDAndTrkObj(mixCase.getTrkID(), outerKey).get(0);
                    KeepTrkDtl newKtd = new KeepTrkDtl();
                    BeanUtils.copyProperties(ktd, newKtd);
                    Map<String, String> innerMap = mixCase.getDoubleMap().get(outerKey);
                    newKtd.setSupCont(innerMap.get("supCont"));
                    newKtd.setSupAgree(innerMap.get("supAgree"));
                    newKtd.setSupDept(userData.getDeptId());
                    newKtd.setSupUser(userData.getUserId());
                    newKtd.setSupDt(DateUtility.getNowDateTimeAsTimestamp().toLocalDateTime());
                    keepTrkDtlDao.closeByTrkIDAndTrkObj(newKtd);

                    if (innerMap.get("supAgree").equals("Y")) {
                        int num = keepTrkDtlDao.selectDoingCase(mixCase.getTrkID());
                        if(num == 0){  //表示均已解列，可結案
                            KeepTrkMst ktm = new KeepTrkMst();
                            ktm.setTrkID(mixCase.getTrkID());
                            ktm = keepTrkMstDao.selectDataByPrimaryKey(ktm);
                            ktm.setClsDt(DateUtility.getNowWestDate());
                            ktm.setTrkSts("9");
                            ktm.setUpdDept(userData.getDeptId());
                            ktm.setUpdUser(userData.getUserId());
                            ktm.setUpdDt(DateUtility.getNowDateTimeAsTimestamp().toLocalDateTime());
                            keepTrkMstDao.closeByTrkID(ktm);

                            List<String> receiverIDList = new ArrayList<>();
                            receiverIDList.add(ktm.getCreDept());
                            sendMail(receiverIDList, "005", ktd);
                        }
                    } else {
//                    依據狀態決定通知對象及內文：
//                    不同意解列：使用004文，通知該列管對象(KEEPTRKDTL.TRKOBJ)。

//                        有指定填報人員： rptUser
//                        指定填報人員 rptUser
//                        無指定填報人員rptUser，有指定填報單位 rptDept：
//                        指定填報單位(主處室/科室)之聯絡窗口
//                        填報人員/單位均未指定：
//                        主處室聯絡窗口
                        List<Users> emailList = new ArrayList<>();
                        List<String> sentDept = new ArrayList<>(); //已寄通知部門
                        Map<String, String> rptDeptEmail = eipcodeDao.getCodeNameList(
                                        Arrays.stream(newKtd.getRptDept().split(";"))
                                                .collect(Collectors.toList()))
                                .stream()
                                .collect(Collectors.toMap(Eipcode::getCodeno, Eipcode::getCodename));; //指定填報單位 + email

                        if (newKtd.getRptUser() != null && !newKtd.getRptUser().equals("")){ //有指定填報人員
                            emailList = usersDao.getEmailList(Arrays.stream(newKtd.getRptUser().split(";"))
                                    .collect(Collectors.toList()));
                            for (Users users : emailList) {
                                log.debug("發送郵件至:" + users.getEmail());
                                List<Eipcode> content = eipcodeDao.findByCodeKindScodeno("TRKMAILMSG", "004");
                                String trkObj = deptsDao.findByPk(ktd.getTrkObj()).getDept_name();
                                String subject = content.get(0).getCodename().replace("@TrkId@", ktd.getTrkID())
                                        .replace("@TrkObj@", trkObj);

                                String mailMsg = content.get(1).getCodename().replaceAll("@TrkId@", ktd.getTrkID())
                                        .replace("@TrkObj@", trkObj);

                                mailService.sendEmailNow(subject, users.getEmail(), mailMsg);
                                sentDept.add(users.getDept_id());
                            }
                            //去除掉該員隸屬部門 避免重複寄信
                            for (String sent: sentDept) {
                                rptDeptEmail.remove(sent);
                            }
                        }

                        // 無指定填報人員rptUser，有指定填報單位 rptDept
                        if(rptDeptEmail.size() > 0) {
                            for (String dept : rptDeptEmail.keySet()) {
                                log.debug("發送郵件至:" + dept);
                                List<Eipcode> content = eipcodeDao.findByCodeKindScodeno("TRKMAILMSG", "004");
                                String subject = content.get(0).getCodename().replace("@TrkId@", ktd.getTrkID())
                                        .replace("@TrkObj@", dept);
                                String mailMsg = content.get(1).getCodename().replaceAll("@TrkId@", ktd.getTrkID())
                                        .replace("@TrkObj@", dept);
                                mailService.sendEmailNow(subject, rptDeptEmail.get(dept), mailMsg);
                            }
                        }

                        // 填報人員/單位均未指定： 主處室聯絡窗口
                        if((newKtd.getRptUser() == null || newKtd.getRptUser().equals(""))
                             && (newKtd.getRptDept() == null || newKtd.getRptDept().equals(""))){
                             List<String> receiverIDList = new ArrayList<>();
                             receiverIDList.add(newKtd.getTrkObj());
                             sendMail(receiverIDList, "004", ktd);
                        }
                    }
                }
            }
        }

    //取得相關部門email後寄發
    public void sendMail(List<String> receiverIDList, String trkStatus, KeepTrkDtl ktd ){
        List<Eipcode> codeNameList = eipcodeDao.getCodeNameList(receiverIDList);
        List<String> newCodeNameList = new ArrayList<>();
        for (Eipcode eipcode : codeNameList){
            if (eipcode.getCodename() != null){
                newCodeNameList.addAll(Arrays.asList(eipcode.getCodename().split(",")));
            }
        }
        if (newCodeNameList.size() > 0){
            List<Users> emailList = usersDao.getEmailList(newCodeNameList);
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
