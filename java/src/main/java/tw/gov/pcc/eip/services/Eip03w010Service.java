package tw.gov.pcc.eip.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tw.gov.pcc.eip.common.cases.Eip03w010Case;
import tw.gov.pcc.eip.common.cases.Eip03w010MixCase;
import tw.gov.pcc.eip.common.cases.Eip03w030Case;
import tw.gov.pcc.eip.common.cases.Eip03w030MixCase;
import tw.gov.pcc.eip.common.controllers.Eip03w010Controller;
import tw.gov.pcc.eip.common.controllers.Eip03w030Controller;
import tw.gov.pcc.eip.dao.EipcodeDao;
import tw.gov.pcc.eip.dao.KeepTrkDtlDao;
import tw.gov.pcc.eip.dao.KeepTrkMstDao;
import tw.gov.pcc.eip.domain.Eipcode;
import tw.gov.pcc.eip.domain.KeepTrkDtl;
import tw.gov.pcc.eip.domain.KeepTrkMst;
import tw.gov.pcc.eip.framework.domain.UserBean;
import tw.gov.pcc.eip.util.DateUtility;

import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

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
    private UserBean userData;
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
        List<Eipcode>list = eipcodeDao.findByCodekindOrderByCodeno("TRKOBJ");
        Map<String, String> map = new LinkedHashMap<>();
        list.forEach(t -> map.put(t.getCodeno(), StringUtils.isEmpty(t.getCodename()) ? "" : t.getCodename()));
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
    public void insert(Eip03w010Case caseData, UserBean userData) throws JsonProcessingException {
        KeepTrkMst ktm = new KeepTrkMst();
        KeepTrkDtl ktd;
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Map<String, String>> jsonMap = objectMapper.readValue(caseData.getJsonMap(), Map.class);
        if(jsonMap.size() > 1){
            for(String k : jsonMap.keySet()){
                if(StringUtils.isNotBlank(k)){
                    Map<String, String> innerJsonMap = jsonMap.get(k);
                    String stDt = innerJsonMap.get("stDt");
                    String endDt = innerJsonMap.get("endDt");

                    ktd = new KeepTrkDtl();
                    ktd.setTrkID(caseData.getTrkID());
                    ktd.setTrkObj(k);
                    ktd.setPrcSts("1");
                    ktd.setStDt(DateUtility.changeDateTypeToWestDate(stDt));
                    ktd.setEndDt(DateUtility.changeDateTypeToWestDate(endDt));
                    ktd.setRptRate(0);
                    ktd.setRptAskEnd("N");
                    ktd.setSupAgree("N");

                    keepTrkDtlDao.insert(ktd);
                }
            }
            ktm.setTrkID(caseData.getTrkID());
            ktm.setTrkCont(caseData.getTrkCont());
            ktm.setTrkFrom(caseData.getTrkFrom().equals("others")? caseData.getOtherTrkFrom() : eipcodeDao.findByCodeKindCodeNo("TRKFROM", caseData.getTrkFrom()).get().getCodename());   //畫面選擇之交辦來源，存入中文
            ktm.setTrkSts(caseData.getTemp());  //點暫存為0，點儲存為1
            ktm.setAllStDt(DateUtility.changeDateTypeToWestDate(caseData.getAllStDt())); //'畫面輸入之全案列管日期'
            ktm.setCreDept(userData.getDeptId());
            ktm.setCreUser(userData.getUserId());
            ktm.setCreDt(DateUtility.getNowDateTimeAsTimestamp().toLocalDateTime());

            keepTrkMstDao.insert(ktm);
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
        caseData.setCreUser(ktm.getCreUser());
        caseData.setCreDept(ktm.getCreDept());
        caseData.setCreDt(ktm.getCreDt());
        caseData.setUpdUser(ktm.getUpdUser());
        caseData.setUpdDept(ktm.getUpdDept());
        caseData.setUpdDt(ktm.getUpdDt());

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
            a.setTrkObj(a.getTrkObj() + "-" + String.valueOf(eipcodeDao.findByCodeKindCodeNo("TRKOBJ",a.getTrkObj()).get().getCodename()));
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

        //刪除 (先撈出此ID現有全部的trkobj後，利用此字串為畫面回傳Map的keu，如果是null->使用者刪除此obj
        List<KeepTrkDtl> dtlList =  keepTrkDtlDao.selectDataByTrkIDAndTrkObj(caseData.getSelectedTrkID(),"");
        dtlList.forEach(a->{
            if(jsonMap.get(a.getTrkObj()) == null){
                keepTrkDtlDao.deleteByTrkIDAndTrkObj(a.getTrkID(), a.getTrkObj());
            }
        });

        //新增 修改
        if(jsonMap.size() > 1){
            for(String k : jsonMap.keySet()){
                if(StringUtils.isNotBlank(k)){
                    ktd = new KeepTrkDtl();
                    ktd.setTrkID(caseData.getSelectedTrkID());
                    ktd.setTrkObj(k);
                    ktd =  keepTrkDtlDao.selectDataByPrimaryKey(ktd);
                    newktd = new KeepTrkDtl();
                    Map<String, String> innerJsonMap = jsonMap.get(k);
                    String stDt = innerJsonMap.get("stDt");
                    String endDt = innerJsonMap.get("endDt");

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
                }
            }
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
        mixCase.setAllStDt(km.getAllStDt()); //全案列管日期  結案日期
        mixCase.setCreDept(km.getCreDept());
        mixCase.setCreUser(km.getCreUser());
        mixCase.setCreDt(km.getCreDt() == null? "": km.getCreDt().format(fmt).replaceAll("-",""));
        mixCase.setUpdDept(km.getUpdDept());
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
//            innerMap.put("trkObj", eipcodeDao.findByCodeKindCodeNo("TRKOBJ",list.get(0).getTrkObj()).get().getCodename());     //列管對象 (處室)
            innerMap.put("prcSts", eipcodeDao.findByCodeKindCodeNo("TRKPRCSTS", list.get(0).getPrcSts()).get().getCodename());    //處理狀態：1-待處理 2-待解列 3-已解列
            innerMap.put("stDt", list.get(0).getStDt());   //列管起日
            innerMap.put("endDt", list.get(0).getEndDt());     //列管迄日
            if (list.get(0).getRptCont() != null){
                list.get(0).setRptCont(list.get(0).getRptCont().replaceAll("\r\n","<br>"));
            }
            innerMap.put("rptCont", list.get(0).getRptCont());    //辦理情形
            innerMap.put("RptRate", String.valueOf(list.get(0).getRptRate()) + " %");    //辦理完成進度(0-100)
            innerMap.put("rptAskEnd", StringUtils.equals(list.get(0).getRptAskEnd(), "Y")? "是" : "否");  //是否要求解列(Y/N)
            innerMap.put("rptDept", list.get(0).getRptDept());   //指定填報單位
            innerMap.put("rptUser", list.get(0).getRptUser());    //指定填報人員
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

            a.setTrkObj(a.getTrkObj() + "-" + eipcodeDao.findByCodeKindCodeNo("TRKOBJ",a.getTrkObj()).get().getCodename());
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
        keepTrkMstDao.updateByTrkID(newKtm);
    }
}
