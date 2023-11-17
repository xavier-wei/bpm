package tw.gov.pcc.eip.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import tw.gov.pcc.eip.common.cases.Eip03w010Case;
import tw.gov.pcc.eip.common.cases.Eip03w010MixCase;
import tw.gov.pcc.eip.common.cases.Eip03w030Case;
import tw.gov.pcc.eip.common.cases.Eip03w030MixCase;
import tw.gov.pcc.eip.common.controllers.Eip03w010Controller;
import tw.gov.pcc.eip.common.controllers.Eip03w030Controller;
import tw.gov.pcc.eip.dao.*;
import tw.gov.pcc.eip.domain.*;
import tw.gov.pcc.eip.framework.domain.UserBean;
import tw.gov.pcc.eip.util.DateUtility;

import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.isEmpty;

/**
 * 重要列管事項_重要列管事項維護
 * @author 2201009
 */
@Service
public class Eip03w010Service {
    private static final Logger log = LoggerFactory.getLogger(Eip03w010Controller.class);

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
    private UserBean userData;
    @Autowired
    private MailService mailService;
    public void initDataList(Eip03w010Case caseData) {
        List<Eip03w010Case> keepTrkMstList = keepTrkMstDao.selectByColumns(null,null,null,null, null);
        List<Eipcode> trkStsList = eipcodeDao.findByCodeKind("TRKSTS");

        // 替換 trkSts 為中文字
        keepTrkMstList.forEach( a-> {
            trkStsList.forEach( b-> {
                if(a.getTrkSts().equals(b.getCodeno())){
                    a.setTrkSts(b.getCodename());
                }
            });
            if (a.getTrkCont() != null){
                a.setTrkCont(a.getTrkCont().replaceAll("\r\n", "<br>"));
            }
            String cnAllStDt = DateUtility.changeDateTypeToChineseDate(a.getAllStDt());
            a.setAllStDt(cnAllStDt.substring(0,3) + "/" + cnAllStDt.substring(3,5) + "/" + cnAllStDt.substring(5));
        });
        caseData.setTrkStsList(trkStsList);
        caseData.setKeepTrkMstList(keepTrkMstList);
    }

    /**
     * 依條件查詢重要列管事項
     *
     * @param caseData
     */
    public void queryKeepTrk(Eip03w010Case caseData){

        caseData.setAllStDtSt(caseData.getAllStDtSt() != null ? DateUtility.changeDateTypeToWestDate(caseData.getAllStDtSt()) : "");
        caseData.setAllStDtEnd(caseData.getAllStDtEnd() != null ? DateUtility.changeDateTypeToWestDate(caseData.getAllStDtEnd()) : "");
        List<Eip03w010Case> resultList = keepTrkMstDao.selectByColumns(caseData.getTrkID(),caseData.getTrkCont(),caseData.getAllStDtSt(),
                                                                       caseData.getAllStDtEnd(), caseData.getTrkSts());
        List<Eipcode> trkStsList = eipcodeDao.findByCodeKind("TRKSTS");

        // 替換 trkSts 為中文字
        resultList.forEach( a-> {
            trkStsList.forEach( b-> {
                if(a.getTrkSts().equals(b.getCodeno())){
                    a.setTrkSts(b.getCodename());
                }
            });
            if (a.getTrkCont() != null){
                a.setTrkCont(a.getTrkCont().replaceAll("\r\n", "<br>"));
            }
            String cnAllStDt = DateUtility.changeDateTypeToChineseDate(a.getAllStDt());
            a.setAllStDt(cnAllStDt.substring(0,3) + "/" + cnAllStDt.substring(3,5) + "/" + cnAllStDt.substring(5));
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
     * 取得所有列管對象下拉式選單
     * @return
     */
    public Map<String, String> getAllTrkObj(){
//        List<Eipcode>list = eipcodeDao.findByCodekindOrderByCodeno("TRKOBJ");
        List<Depts> list = deptsDao.getEip03wDepts("1","");
        Map<String, String> map = new LinkedHashMap<>();
        list.forEach(t -> map.put(t.getDept_id(), StringUtils.isEmpty(t.getDept_name()) ? "" : t.getDept_name()));
        return map;
    }

    /**
     * 取得所有交辦來源下拉式選單
     * @return
     */
    public Map<String, String> getAllTrkFrom(){
        List<Eipcode>list = eipcodeDao.findByCodekindOrderByCodeno("TRKFROM");
        Map<String, String> map = new LinkedHashMap<>();
        list.forEach(t -> map.put(t.getCodeno(), StringUtils.isEmpty(t.getCodename()) ? "" : t.getCodename()));
        return map;
    }

    /**
     * 新增列管事項
     * @return
     */
    public void insert(Eip03w010Case caseData, UserBean userData, BindingResult result) throws JsonProcessingException {
        KeepTrkMst ktm = new KeepTrkMst();
        KeepTrkDtl ktd;
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Map<String, String>> jsonMap = objectMapper.readValue(caseData.getJsonMap(), Map.class);
        List<String> deptIDList = new ArrayList<>();
        if(jsonMap.size() > 1){

            ktm.setTrkID(caseData.getTrkID());
            ktm.setTrkCont(caseData.getTrkCont());
            ktm.setTrkFrom(caseData.getTrkFrom().equals("others")? caseData.getOtherTrkFrom() : eipcodeDao.findByCodeKindCodeNo("TRKFROM", caseData.getTrkFrom()).get().getCodename());   //畫面選擇之交辦來源，存入中文
            ktm.setTrkSts(caseData.getTemp());  //點暫存為0，點儲存為1
            ktm.setAllStDt(DateUtility.changeDateTypeToWestDate(caseData.getAllStDt())); //'畫面輸入之全案列管日期'
            ktm.setCreDept(userData.getDeptId());
            ktm.setCreUser(userData.getUserId());
            ktm.setCreDt(DateUtility.getNowDateTimeAsTimestamp().toLocalDateTime());

            //判斷編號是否已存在
           int num =  keepTrkMstDao.findByTrkID(caseData.getTrkID());
           if(num > 0){
               ObjectError error;
               error = new ObjectError("trkID","列管編號重複，請重新存檔。");
               result.addError(error);
               return;
           }
           keepTrkMstDao.insert(ktm);

           for(String k : jsonMap.keySet()){
               if(StringUtils.isNotBlank(k)){
                   Map<String, String> innerJsonMap = jsonMap.get(k);
                   String stDt = innerJsonMap.get("stDt").replace("/","");
                   String endDt = innerJsonMap.get("endDt").replace("/","");;

                   ktd = new KeepTrkDtl();
                   ktd.setTrkID(caseData.getTrkID());
                   ktd.setTrkObj(k);
                   ktd.setPrcSts("1");
                   ktd.setStDt(DateUtility.changeDateTypeToWestDate(stDt));
                   ktd.setEndDt(DateUtility.changeDateTypeToWestDate(endDt));
                   ktd.setRptRate(0);
                   ktd.setRptAskEnd("N");
                   ktd.setSupAgree("N");
                   deptIDList.add(k);

                   keepTrkDtlDao.insert(ktd);
               }
           }
        }
        // 使用者點選送出後，寄email通知相關單位
        if(caseData.getTemp().equals("1")){
            sendMail(deptIDList, "001", ktm);
        }
    }

    /**
     * 依trkID 查詢KeepTrkMst/ KeepTrkDtl
      * @param caseData
     */
    public void queryKeepTrkDetailByTrkID(Eip03w010Case caseData){
        //KeepTrkMst
        KeepTrkMst ktm = new KeepTrkMst();
        ktm.setTrkID(caseData.getSelectedTrkID());
        ktm = keepTrkMstDao.selectDataByPrimaryKey(ktm);

        caseData.setTrkID(ktm.getTrkID());
        caseData.setTrkSts(eipcodeDao.findByCodeKindCodeNo("TRKSTS",ktm.getTrkSts()).get().getCodename());
        caseData.setTrkCont(ktm.getTrkCont());
        Map<String, String> trkFromCombobox = getAllTrkFrom();
        Map<String, String> newTkFromCombobox = new HashMap<>();
        newTkFromCombobox.put("00",ktm.getTrkFrom());
        for(String key : trkFromCombobox.keySet()){
            // 排除value重複的
           if(newTkFromCombobox.get(key) == null && !newTkFromCombobox.get("00").equals(trkFromCombobox.get(key)) ) {
               newTkFromCombobox.put(key, trkFromCombobox.get(key));
           }
        }
        caseData.setTrkFromCombobox(newTkFromCombobox);
        caseData.setAllStDt(DateUtility.changeDateTypeToChineseDate(ktm.getAllStDt()));
//        caseData.setCreUser(ktm.getCreUser());
//        caseData.setCreDept(ktm.getCreDept());
        caseData.setCreDept(deptsDao.findByPk(ktm.getCreDept()).getDept_name());
        caseData.setCreUser(getUserName(ktm.getCreUser()));
        String creDt = DateUtility.parseLocalDateTimeToChineseDateTime(ktm.getCreDt(),true);
        creDt = DateUtility.formatChineseDateTimeString(creDt,false);
        caseData.setCreDt(creDt);
//        caseData.setUpdUser(ktm.getUpdUser());
//        caseData.setUpdDept(ktm.getUpdDept());
//        caseData.setUpdUser(ktm.getUpdUser() != null? usersDao.selectByKey(ktm.getUpdUser()).getUser_name() : "");
//        caseData.setUpdDept(ktm.getUpdDept() != null? deptsDao.findByPk(ktm.getUpdDept()).getDept_name() : "");
        if(ktm.getUpdUser() != null && !ktm.getUpdUser().equals("")){
            caseData.setUpdUser(getUserName(ktm.getUpdUser()));
        }
        if(ktm.getUpdDept() != null && !ktm.getUpdDept().equals("")){
            caseData.setUpdDept(deptsDao.findByPk(ktm.getUpdDept()).getDept_name());
        }
        
        //        updDt = updDt.substring(0, 3) + "/" + updDt.substring(3, 5) + "/" + updDt.substring(5, 7) + " " + updDt.substring(8);
        String updDt = DateUtility.parseLocalDateTimeToChineseDateTime(ktm.getUpdDt(),true);
        updDt = DateUtility.formatChineseDateTimeString(updDt,false);
        caseData.setUpdDt(updDt);

        //KeepTrkDtl
        List<KeepTrkDtl> keepTrkDtlList = keepTrkDtlDao.selectDataByTrkIDAndTrkObj(caseData.getSelectedTrkID(),"");

        Map<String, String> trkObjMap = getAllTrkObj();
        // 獲取所有匹配的 trkObj 值
        List<String> matchedTrkObjList = keepTrkDtlList.stream()
                .map(KeepTrkDtl::getTrkObj)
                .collect(Collectors.toList());
        // 删除匹配的键值对
        trkObjMap.keySet().removeIf(matchedTrkObjList::contains);

        caseData.setTrkObjCombobox(trkObjMap);

        keepTrkDtlList.forEach(a -> {
//            a.setTrkObj(a.getTrkObj() + "-" + String.valueOf(eipcodeDao.findByCodeKindCodeNo("TRKOBJ",a.getTrkObj()).get().getCodename()));
            a.setTrkObj(a.getTrkObj() + "-" + deptsDao.findByPk(a.getTrkObj()).getDept_name());
            a.setPrcSts(String.valueOf(eipcodeDao.findByCodeKindCodeNo("TRKPRCSTS",a.getPrcSts()).get().getCodename()));
            a.setStDt(DateUtility.changeDateTypeToChineseDate(a.getStDt()));
            a.setEndDt(DateUtility.changeDateTypeToChineseDate(a.getEndDt()));
        });
        caseData.setKeepTrkDtlList(keepTrkDtlList);

    }

    /**
     * 更新列管事項
     * @return
     */
    public void update(Eip03w010Case caseData, UserBean userData) throws JsonProcessingException {
        //更新主檔
        KeepTrkMst ktm = new KeepTrkMst();
        ktm.setTrkID(caseData.getSelectedTrkID());
        ktm = keepTrkMstDao.selectDataByPrimaryKey(ktm);
        KeepTrkMst newKtm = new KeepTrkMst();
        List<String> receiverIDList = new ArrayList<>();
        BeanUtils.copyProperties(ktm, newKtm);

        newKtm.setTrkCont(caseData.getTrkCont()); // 畫面輸入之內容
        String trkFrom = null;
        if(caseData.getTrkFrom().equals("00")){  //user沒有變更來源
            trkFrom = ktm.getTrkFrom();
        } else if (caseData.getTrkFrom().equals("others")) {
            trkFrom = caseData.getOtherTrkFrom();
        } else {
            trkFrom = eipcodeDao.findByCodeKindCodeNo("TRKFROM", caseData.getTrkFrom()).get().getCodename();
        }
        newKtm.setTrkFrom(trkFrom);   //畫面選擇之交辦來源，存入中文
        newKtm.setTrkSts(caseData.getTemp());  //點暫存為0，點儲存為1
        newKtm.setAllStDt(DateUtility.changeDateTypeToWestDate(caseData.getAllStDt())); //畫面輸入之全案列管日期
        newKtm.setUpdDept(userData.getDeptId()); //登入者之部室
        newKtm.setUpdUser(userData.getUserId()); //登入者之員編
        newKtm.setUpdDt(DateUtility.getNowDateTimeAsTimestamp().toLocalDateTime()); //CURRENT_TIMESTAMP
        keepTrkMstDao.updateByTrkID(newKtm);

        //更新明細檔
        KeepTrkDtl ktd;
        KeepTrkDtl newktd;
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Map<String, String>> jsonMap = objectMapper.readValue(caseData.getJsonMap(), Map.class);

        //刪除 (先撈出此ID現有全部的trkobj後，利用此字串為畫面回傳Map的key，如果是null->使用者刪除此obj
        List<KeepTrkDtl> dtlList =  keepTrkDtlDao.selectDataByTrkIDAndTrkObj(caseData.getSelectedTrkID(),"");
        dtlList.forEach(a->{
            if(jsonMap.get(a.getTrkObj()) == null){
                keepTrkDtlDao.deleteByTrkIDAndTrkObj(a.getTrkID(), a.getTrkObj());
            }
        });

        //新增 修改
        boolean closeIssueFlag = true;
        if(jsonMap.size() > 1){
            for(String k : jsonMap.keySet()){
                if(StringUtils.isNotBlank(k)){
                    ktd = new KeepTrkDtl();
                    ktd.setTrkID(caseData.getSelectedTrkID());
                    ktd.setTrkObj(k);
                    ktd =  keepTrkDtlDao.selectDataByPrimaryKey(ktd);
                    newktd = new KeepTrkDtl();
                    Map<String, String> innerJsonMap = jsonMap.get(k);
                    String stDt = innerJsonMap.get("stDt").replace("/","");
                    String endDt = innerJsonMap.get("endDt").replace("/","");
                    receiverIDList.add(k);

                    if(ktd!=null){
                        BeanUtils.copyProperties(ktd, newktd);
                        newktd.setStDt(DateUtility.changeDateTypeToWestDate(stDt));
                        newktd.setEndDt(DateUtility.changeDateTypeToWestDate(endDt));
                        keepTrkDtlDao.updateByTrkIDAndTrkObj(newktd);
                    }else {
                        newktd.setTrkID(caseData.getTrkID());
                        newktd.setTrkObj(k);
                        newktd.setPrcSts("1");
                        newktd.setStDt(DateUtility.changeDateTypeToWestDate(stDt));
                        newktd.setEndDt(DateUtility.changeDateTypeToWestDate(endDt));
                        newktd.setRptRate(0);
                        newktd.setRptAskEnd("N");
                        newktd.setSupAgree("N");

                        keepTrkDtlDao.insert(newktd);
                    }

                    if (!jsonMap.get(k).get("prcSts").equals("已解列")){
                        closeIssueFlag = false;
                    }
                }
            }
        }

        if(StringUtils.equals(caseData.getTemp(), "1") && closeIssueFlag){
            KeepTrkMst closektm = new KeepTrkMst();
            closektm.setTrkID(caseData.getSelectedTrkID());
            closektm.setClsDt(DateUtility.getNowWestDate());
            closektm.setTrkSts("9");
            closektm.setUpdDept(userData.getDeptId());
            closektm.setUpdUser(userData.getUserId());
            closektm.setUpdDt(DateUtility.getNowDateTimeAsTimestamp().toLocalDateTime());
            keepTrkMstDao.closeByTrkID(closektm);

        }

        // 使用者點選送出後，寄email通知相關單位
        if(caseData.getTemp().equals("1")){
            //原資料KeepTrkMst.TrkSts為空值，為首次儲存：使用001文。
            //原資料KeepTrkMst.TrkSts不為空值，是為修改：使用002文。
            sendMail(receiverIDList, ktm.getTrkSts() == null || ktm.getTrkSts().equals("0") ? "001" : "002", newKtm);
        }
    }


    /**
     * 依查詢重要列管事項 for detail
     *
     * @param caseData
     */
    public void queryKeepTrkDetail(Eip03w010Case caseData,  Eip03w010MixCase mixCase){
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
//        mixCase.setAllStDt(km.getAllStDt()); //全案列管日期
        mixCase.setAllStDt(DateUtility.formatChineseDateString(DateUtility.changeDateTypeToChineseDate(km.getAllStDt()), false));
        mixCase.setClsDt(km.getClsDt()); //結案日期
//        mixCase.setCreDept(km.getCreDept());
//        mixCase.setCreUser(km.getCreUser());
        mixCase.setCreDept(deptsDao.findByPk(km.getCreDept()).getDept_name());
        mixCase.setCreUser(getUserName(km.getCreUser()));
//        mixCase.setCreDt(km.getCreDt() == null? "": km.getCreDt().format(fmt).replaceAll("-",""));
        String creDt = DateUtility.parseLocalDateTimeToChineseDateTime(km.getCreDt(),true);
        creDt = DateUtility.formatChineseDateTimeString(creDt,false);
        mixCase.setCreDt(creDt);
//        mixCase.setUpdDept(km.getUpdDept());
//        mixCase.setUpdUser(km.getUpdUser());
        if(km.getUpdDept() != null && !km.getUpdDept().equals("")){
            mixCase.setUpdDept(deptsDao.findByPk(km.getUpdDept()).getDept_name());
        }
        if(km.getUpdUser() != null && !km.getUpdUser().equals("")){
            mixCase.setUpdUser(getUserName(km.getUpdUser()));
        }
        //        mixCase.setUpdDt(km.getUpdDt() == null? "": km.getUpdDt().format(fmt).replaceAll("-",""));
        String updDt = DateUtility.parseLocalDateTimeToChineseDateTime(km.getUpdDt(),true);
        updDt = DateUtility.formatChineseDateTimeString(updDt,false);
        mixCase.setUpdDt(updDt);

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
            a.setStDt(DateUtility.formatChineseDateString(a.getStDt(), false));
            a.setEndDt(DateUtility.formatChineseDateString(a.getEndDt(), false));
        });
        mixCase.setKdList(kdList);

        //填報辦理進度 解除列管
        Map<String, Map<String,String>> doubleMap = new HashMap<>();
        kdList.forEach(a -> {
            List<KeepTrkDtl> list = keepTrkDtlDao.selectDataByTrkIDAndTrkObj(a.getTrkID(), a.getTrkObj());
            Map<String,String> innerMap = new HashMap<>();
            innerMap.put("trkID", list.get(0).getTrkID());      //列管事項編號
            innerMap.put("trkObj", list.get(0).getTrkObj());     //列管對象 (處室)
            innerMap.put("trkObjName", deptsDao.findByPk(list.get(0).getTrkObj()).getDept_name());     //列管對象 (處室)
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
            innerMap.put("rptUpdUser", list.get(0).getRptUpdUser() != null? getUserName(list.get(0).getRptUpdUser()) : ""); //填報更新人員
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
            innerMap.put("supDept", list.get(0).getSupDept() != null? deptsDao.findByPk(list.get(0).getSupDept()).getDept_name():"");    //回應人員所屬部門
            innerMap.put("supUser", list.get(0).getSupUser() != null? getUserName(list.get(0).getSupUser()):"");    //回應人員

            String supDt = DateUtility.parseLocalDateTimeToChineseDateTime(list.get(0).getSupDt(), true);
            if(StringUtils.isNotBlank(supDt)){
                supDt = supDt.substring(0,3) + "/" + supDt.substring(3,5) + "/" + supDt.substring(5,7) + "  " + supDt.substring(7,9) + ":" + supDt.substring(9,11) + ":" + supDt.substring(11);
            }
            innerMap.put("supDt", supDt);      //回應日期時間

//            a.setTrkObj(a.getTrkObj() + "-" + eipcodeDao.findByCodeKindCodeNo("TRKOBJ",a.getTrkObj()).get().getCodename());
            a.setTrkObj(a.getTrkObj() + "-" + deptsDao.findByPk(a.getTrkObj()).getDept_name());
            doubleMap.put(a.getTrkObj().split("-")[0], innerMap);
        });
        mixCase.setDoubleMap(doubleMap);
    }


    /**
     * 刪除暫存資料
     * @param trkID
     */
    public void deleteMstAndDtl(String trkID){
        keepTrkMstDao.deleteByTrkID(trkID);
        keepTrkDtlDao.deleteByTrkIDAndTrkObj(trkID,"");
    }

    /**
     * 作廢資料
     * @param trkID
     */
    public void cancelMst(String trkID){
        KeepTrkMst ktm = new KeepTrkMst();
        KeepTrkMst newKtm = new KeepTrkMst();
        ktm.setTrkID(trkID);
        ktm = keepTrkMstDao.selectDataByPrimaryKey(ktm);
        BeanUtils.copyProperties(ktm, newKtm);
        newKtm.setTrkSts("D");
        newKtm.setClsDt(DateUtility.getNowWestDate());
        newKtm.setUpdDept(userData.getDeptId()); //登入者之部室
        newKtm.setUpdUser(userData.getUserId()); //登入者之員編
        newKtm.setUpdDt(DateUtility.getNowDateTimeAsTimestamp().toLocalDateTime()); //CURRENT_TIMESTAMP
        keepTrkMstDao.updateByTrkID(newKtm);
    }

    //取得相關部門email後寄發
    //原資料KeepTrkMst.TrkSts為空值，為首次儲存：使用001文。
    //原資料KeepTrkMst.TrkSts不為空值，是為修改：使用002文。
    public void sendMail(List<String> receiverIDList, String trkStatus, KeepTrkMst ktm ){
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
                String subject = content.get(0).getCodename().replace("@TrkId@", ktm.getTrkID());

                StringBuilder trkObjs = new StringBuilder();
                for(int i = 0 ; i<receiverIDList.size() ; i++){
                    if(i != (receiverIDList.size()-1)){
                        trkObjs.append(deptsDao.findByPk(receiverIDList.get(i)).getDept_name()).append(",");
                    }else {
                        trkObjs.append(deptsDao.findByPk(receiverIDList.get(i)).getDept_name());
                    }
                }

                String mailMsg = content.get(1).getCodename().replaceAll("@TrkId@", ktm.getTrkID())
                                                             .replace("@TrkFrom@",ktm.getTrkFrom())
                                                             .replace("@TrkCont@", ktm.getTrkCont())
                                                             .replace("@TrkObjList@",trkObjs);

                mailService.sendEmailNow(subject, users.getEmail(), mailMsg);
            }
        }
    }


    /**
     * 恢復原TABLE內容
     * @param caseData
     */
    public void restoreOnError(Eip03w010Case caseData) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Map<String, String>> jsonMap = objectMapper.readValue(caseData.getJsonMap(), Map.class);
        KeepTrkDtl newKeepTrkDtl;
        List<KeepTrkDtl> newKeepTrkDtlList = new ArrayList<>();
        if(jsonMap.size() > 1){
            for(String k : jsonMap.keySet()){
                if(StringUtils.isNotBlank(k)){
                    Map<String, String> innerJsonMap = jsonMap.get(k);
                    String stDt = innerJsonMap.get("stDt").replace("/","");
                    String endDt = innerJsonMap.get("endDt").replace("/","");;

                    newKeepTrkDtl = new KeepTrkDtl();
                    newKeepTrkDtl.setTrkID(caseData.getTrkID());
                    newKeepTrkDtl.setTrkObj(k);
                    newKeepTrkDtl.setPrcSts(innerJsonMap.get("prcSts"));
                    newKeepTrkDtl.setStDt(DateUtility.changeDateTypeToWestDate(stDt));
                    newKeepTrkDtl.setEndDt(DateUtility.changeDateTypeToWestDate(endDt));

                    newKeepTrkDtlList.add(newKeepTrkDtl);
                }
            }
        }
        Map<String, String> trkObjMap = getAllTrkObj();
        // 獲取所有匹配的 trkObj 值
        List<String> matchedTrkObjList = newKeepTrkDtlList.stream()
                .map(KeepTrkDtl::getTrkObj)
                .collect(Collectors.toList());
        // 删除匹配的键值对
        trkObjMap.keySet().removeIf(matchedTrkObjList::contains);

        caseData.setTrkObjCombobox(trkObjMap);

        newKeepTrkDtlList.forEach(a -> {
            a.setTrkObj(a.getTrkObj() + "-" + deptsDao.findByPk(a.getTrkObj()).getDept_name());
            a.setStDt(DateUtility.changeDateTypeToChineseDate(a.getStDt()));
            a.setEndDt(a.getEndDt().equals("")? " " : DateUtility.changeDateTypeToChineseDate(a.getEndDt()));
        });

        caseData.setStDt("");
        caseData.setEndDt("");
        caseData.setKeepTrkDtlList(newKeepTrkDtlList);
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
