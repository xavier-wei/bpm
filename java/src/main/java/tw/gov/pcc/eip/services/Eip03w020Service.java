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

import java.time.format.DateTimeFormatter;
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
        DateTimeFormatter fmt = DateTimeFormatter.ISO_LOCAL_DATE;

//        列管事項 列管對象
        mixCase.setTrkID(km.getTrkID());
        mixCase.setTrkSts(eipcodeDao.findByCodeKindCodeNo("TRKSTS", km.getTrkSts()).get().getCodename());
        if (km.getTrkCont() != null){
            mixCase.setTrkCont(km.getTrkCont().replaceAll("\r\n","<br>"));
        }
        mixCase.setTrkFrom(km.getTrkFrom());
        mixCase.setAllStDt(km.getAllStDt()); //全案列管日期
        mixCase.setClsDt(km.getClsDt()); //結案日期
        mixCase.setCreDept(km.getCreDept());
        mixCase.setCreUser(km.getCreUser());
        mixCase.setCreDt(km.getCreDt() == null? "": km.getCreDt().format(fmt).replaceAll("-",""));
        mixCase.setUpdDept(km.getUpdDept());
        mixCase.setUpdUser(km.getUpdUser());
        mixCase.setUpdDt(km.getUpdDt() == null? "": km.getUpdDt().format(fmt).replaceAll("-",""));

        List<KeepTrkDtl> kdList = keepTrkDtlDao.selectDataByTrkIDAndTrkObj(caseData.getSelectedTrkID(),"");

        kdList.forEach( a-> {
            a.setPrcSts(String.valueOf(eipcodeDao.findByCodeKindCodeNo("TRKPRCSTS",a.getPrcSts()).get().getCodename()));
            String nn = deptsDao.findByPk(a.getTrkObj()).getDept_name();
            a.setTrkObj(a.getTrkObj() + "-" + deptsDao.findByPk(a.getTrkObj()).getDept_name());

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
            innerMap.put("trkID", list.get(0).getTrkID());      //列管事項編號
            innerMap.put("trkObj", list.get(0).getTrkObj());     //列管對象 (處室)
            innerMap.put("trkObjName", deptsDao.findByPk(list.get(0).getTrkObj()).getDept_name());     //列管對象 (處室)
            innerMap.put("prcSts", eipcodeDao.findByCodeKindCodeNo("TRKPRCSTS", list.get(0).getPrcSts()).get().getCodename());    //處理狀態：1-待處理 2-待解列 3-已解列
            innerMap.put("stDt", list.get(0).getStDt());   //列管起日
            innerMap.put("endDt", list.get(0).getEndDt());     //列管迄日
            if (list.get(0).getRptCont() != null){
                list.get(0).setRptCont(list.get(0).getRptCont().replaceAll("\r\n","<br>"));
            }
            innerMap.put("rptCont", list.get(0).getRptCont());    //辦理情形
            innerMap.put("rptRate", String.valueOf(list.get(0).getRptRate()));    //辦理完成進度(0-100)
//            innerMap.put("rptAskEnd", StringUtils.equals(list.get(0).getRptAskEnd(), "Y")? "是" : "否");  //是否要求解列(Y/N)
            innerMap.put("rptAskEnd", list.get(0).getRptAskEnd());  //是否要求解列(Y/N)
//            innerMap.put("rptDept", list.get(0).getRptDept());   //指定填報單位
            innerMap.put("rptUser", list.get(0).getRptUser());    //指定填報人員
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
            innerMap.put("rptUpdUser", list.get(0).getRptUpdUser()); //填報更新人員
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
//            innerMap.put("isSameDept", trkObjNo.equals(userData.getDeptId())? "Y" : "N");      //TrkObj為登入者部室
            a.setTrkObj(a.getTrkObj() + "-" + eipcodeDao.findByCodeKindCodeNo("TRKOBJ",trkObjNo).get().getCodename());
            doubleMap.put(a.getTrkObj().split("-")[0], innerMap);
        });

        mixCase.setCurrentDept(userData.getDeptId());
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
                }
            }
        }
}
