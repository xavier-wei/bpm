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

        //列管事項 列管對象
        mixCase.setTrkID(km.getTrkID());
        mixCase.setTrkSts(eipcodeDao.findByCodeKindCodeNo("TRKSTS", km.getTrkSts()).get().getCodename());
        if (km.getTrkCont() != null){
            mixCase.setTrkCont(km.getTrkCont().replaceAll("\r\n","<br>"));
        }
        mixCase.setTrkFrom(km.getTrkFrom());
        mixCase.setAllStDt(km.getAllStDt()); //全案列管日期
        mixCase.setClsDt(km.getClsDt()); //結案日期
        mixCase.setCreDept(deptsDao.findByPk(km.getCreDept()).getDept_name());
        mixCase.setCreUser(usersDao.selectByKey(km.getCreUser()).getUser_name());
        mixCase.setCreDt(km.getCreDt() == null? "": km.getCreDt().format(fmt).replaceAll("-",""));
        mixCase.setUpdDept(deptsDao.findByPk(km.getUpdDept()).getDept_name());
        mixCase.setRptUpdUser(usersDao.selectByKey(km.getUpdUser()).getUser_name());
        mixCase.setUpdDt(km.getUpdDt() == null? "": km.getUpdDt().format(fmt).replaceAll("-",""));

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
            innerMap.put("trkID", list.get(0).getTrkID());      //列管事項編號
            innerMap.put("trkObj", list.get(0).getTrkObj());     //列管對象 (處室)
            innerMap.put("trkObjName", deptsDao.findByPk(list.get(0).getTrkObj()).getDept_name());
//            innerMap.put("trkObj", eipcodeDao.findByCodeKindCodeNo("TRKOBJ",list.get(0).getTrkObj()).get().getCodename());     //列管對象 (處室)
            innerMap.put("prcSts", eipcodeDao.findByCodeKindCodeNo("TRKPRCSTS", list.get(0).getPrcSts()).get().getCodename());    //處理狀態：1-待處理 2-待解列 3-已解列
            innerMap.put("stDt", list.get(0).getStDt());   //列管起日
            innerMap.put("endDt", list.get(0).getEndDt());     //列管迄日
            if (list.get(0).getRptCont() != null){
                list.get(0).setRptCont(list.get(0).getRptCont().replaceAll("\r\n","<br>"));
            }
            innerMap.put("rptCont", list.get(0).getRptCont());    //辦理情形
            innerMap.put("rptRate", String.valueOf(list.get(0).getRptRate()));    //辦理完成進度(0-100)
            innerMap.put("rptAskEnd", StringUtils.equals(list.get(0).getRptAskEnd(), "Y")? "是" : "否");  //是否要求解列(Y/N)
            innerMap.put("rptDept", list.get(0).getRptDept());   //指定填報單位
            innerMap.put("rptUser", list.get(0).getRptUser());    //指定填報人員
            innerMap.put("rptUpdUser", list.get(0).getRptUpdUser()); //填報更新人員
            List<Depts> rptDeptNameList = new ArrayList<>();
            List<Users> rptUserNameList = new ArrayList<>();
            if (list.get(0).getRptDept() != null){
                rptDeptNameList = deptsDao.findNameByMultiID(Arrays.stream(list.get(0).getRptDept().split(";")).collect(Collectors.toList()));
            }
            if (list.get(0).getRptUser() != null){
                rptUserNameList = usersDao.findNameByMultiID(Arrays.stream(list.get(0).getRptUser().split(";")).collect(Collectors.toList()));
            }

            StringBuilder rptDeptName = new StringBuilder();
            for (Depts depts : rptDeptNameList) {
                if(depts != null){
                    rptDeptName.append(depts.getDept_name()).append(";");
                }
            }

            StringBuilder rptUserName = new StringBuilder();
            for (Users users : rptUserNameList) {
                if(users != null){
                    rptUserName.append(users.getUser_name()).append(";");
                }
            }

            innerMap.put("rptDeptName", rptDeptName.toString());   //指定填報單位
            innerMap.put("rptUserName", rptUserName.toString());    //指定填報人員
            String rptUpdDt = DateUtility.parseLocalDateTimeToChineseDateTime(list.get(0).getRptUpdDt(), true);
            if(StringUtils.isNotBlank(rptUpdDt)){
                rptUpdDt = rptUpdDt.substring(0,3) + "/" + rptUpdDt.substring(3,5) + "/" + rptUpdDt.substring(5,7) + "  " + rptUpdDt.substring(7,9) + ":" + rptUpdDt.substring(9,11) + ":" + rptUpdDt.substring(11);
            }

            innerMap.put("rptUpdDt", rptUpdDt);   //填報更新日期時間
            innerMap.put("supCont", list.get(0).getSupCont());    //回應內容
            innerMap.put("supAgree", list.get(0).getSupAgree());   //是否同意解列(Y/N)
            innerMap.put("supDept", list.get(0).getSupDept());    //回應人員所屬部門
            innerMap.put("supUser", list.get(0).getSupUser());    //回應人員

            String supDt = DateUtility.parseLocalDateTimeToChineseDateTime(list.get(0).getSupDt(), true);
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
                        }
                    }
                }
            }
        }
}
