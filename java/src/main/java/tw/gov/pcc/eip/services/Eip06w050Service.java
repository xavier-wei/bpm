package tw.gov.pcc.eip.services;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import tw.gov.pcc.eip.common.cases.Eip06w050Case;
import tw.gov.pcc.eip.common.cases.Eip06w060Case;
import tw.gov.pcc.eip.dao.MeetingCodeDao;
import tw.gov.pcc.eip.domain.MeetingCode;
import tw.gov.pcc.eip.framework.domain.UserBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.isEmpty;

/**
 * 會議室參數維護
 * @author 2207003
 */
@Service
public class Eip06w050Service extends OnlineRegService {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(Eip06w050Service.class);
    @Autowired
    UserBean userData;
    @Autowired
    MeetingCodeDao meetingCodeDao;

    Map<String, String> itemTyp;

    /**
     * 查詢全部參數維護清單
     * @param caseData
     */

    public void getMeetingCodeList(Eip06w050Case caseData) {
        List<Eip06w050Case.MeetingCodeCase> list = meetingCodeDao.selectAllData().stream().map(t -> {
            Eip06w050Case.MeetingCodeCase meetingCodeCase = new Eip06w050Case.MeetingCodeCase();
            meetingCodeCase.setItemTyp(t.getItemTyp());
            meetingCodeCase.setItemId(t.getItemId());
            meetingCodeCase.setItemName(t.getItemName());
            meetingCodeCase.setQty(t.getQty());
            return meetingCodeCase;
        }).collect(Collectors.toList());
        caseData.setMeetingCodeCaseList(list);
    }

    /**
     * 查詢類別清單
     * @param caseData
     */

    public void getMeetingCodeAndItemTypList(Eip06w050Case caseData) {
        List<Eip06w050Case.MeetingCodeCase> list = meetingCodeDao.selectDataByItemTypeF(caseData.getItemTyp()).stream().map(t -> {
            Eip06w050Case.MeetingCodeCase meetingCodeCase = new Eip06w050Case.MeetingCodeCase();
            meetingCodeCase.setItemTyp(t.getItemTyp());
            meetingCodeCase.setItemId(t.getItemId());
            meetingCodeCase.setItemName(t.getItemName());
            meetingCodeCase.setQty(t.getQty());
            return meetingCodeCase;
        }).collect(Collectors.toList());
        caseData.setMeetingCodeCaseList(list);
    }



    /**
     * 取得畫面上的其中一個類別
     * @param caseData
     */
    public void getSingleClass(Eip06w050Case caseData) {
        MeetingCode meetingCode = meetingCodeDao.findByPK(caseData.getItemId());
        caseData.setItemTyp(meetingCode.getItemTyp());
        caseData.setItemId(meetingCode.getItemId());
        caseData.setItemName(meetingCode.getItemName());
        caseData.setQty(meetingCode.getQty());
    }

    /**
     * 查詢部分更新
     * @param caseData
     */
    public String updateClass(Eip06w050Case caseData) {
        MeetingCode meetingCode = meetingCodeDao.findByPK(caseData.getItemId());
        String originalItemName=meetingCode.getItemName();
        String caseItemName=caseData.getItemName();
        if (originalItemName.equals(caseItemName)){
            log.debug("名稱無更新，不需檢查是否重複");
            return "N";
        }
        return "Y";
    }

    /**
     * 新增
     * @param caseData
     */
    public void insertClass(Eip06w050Case caseData) {
        MeetingCode meetingCode = new MeetingCode();
        String itemTyp=caseData.getItemTyp();
        meetingCode.setItemTyp(itemTyp);
        String itemId;
        if("A".equals(itemTyp)) {
            itemId = "A" + caseData.getItemId();
        }else if("B".equals(itemTyp)){
            itemId = "B" + caseData.getItemId();
        }else{
            itemId = "F" + caseData.getItemId();
        }
        meetingCode.setItemId(itemId);
        meetingCode.setItemName(caseData.getItemName());
        meetingCode.setQty(caseData.getQty());
        meetingCodeDao.insertData(meetingCode);
    }

    /**
     * 查詢是否重覆
     * @param caseData
     */
    public void itemIsUse(Eip06w050Case caseData, BindingResult result) {
        int itemId = meetingCodeDao.findByitemId(caseData.getItemTyp(), caseData.getItemId());
        if (StringUtils.equals(caseData.getMode(), "A")) {
            if (itemId == 1) {
                log.debug("輸入itemId(編號)已經存在");
                result.rejectValue("itemId", "W0031", new Object[]{"編號"}, "");//{0}已存在，請重新輸入！
            }
        }
        int itemName = meetingCodeDao.findByitemName(caseData.getItemName());
        if (itemName == 1) {
            log.debug("輸入itemName(名稱)已經存在");
            result.rejectValue("itemName","W0031",new Object[] { "名稱" },"");//{0}已存在，請重新輸入！
        }

    }

    /**
     * 物件類別下拉式選單內容(全部)
     *
     * @param caseData;
     * @return
     */

    public Map<String, String> initializeOptionAll(Eip06w050Case caseData) {
        Map<String, String> itemTyp = new HashMap<>();
        itemTyp.put("A","會議餐點");
        itemTyp.put("D","預約天數");
        itemTyp.put("B","會議物品");
        itemTyp.put("F","會議室");
        itemTyp.put("FX","會議室");
        caseData.setItemTypMap(itemTyp);
        return itemTyp;
    }

    /**
     * 物件類別下拉式選單內容(會議室包含F、FX)
     *
     * @param caseData;
     * @return
     */

    public Map<String, String> initializeOption(Eip06w050Case caseData) {
        Map<String, String> map =  initializeOptionAll(caseData);
        map.entrySet().removeIf(entry -> entry.getKey().equals("FX"));
        return map;
    }

    /**
     * 修改
     * @param caseData
     */
    public void modifyClass(Eip06w050Case caseData) {
        MeetingCode meetingCode = new MeetingCode();
        meetingCode.setItemTyp(caseData.getItemTyp());
        meetingCode.setItemId(caseData.getItemId());
        meetingCode.setItemName(caseData.getItemName());
        meetingCode.setQty(caseData.getQty());
        meetingCodeDao.updateData(meetingCode, caseData.getItemId());
    }

    /**
     * 刪除
     * @param caseData
     */
    public String deleteClass(Eip06w050Case caseData) {
        int itemId=meetingCodeDao.findItemIdByMeetingItem(caseData.getItemId());
        if (itemId == 1) {
            log.debug("參數使用中，無法刪除");
            return "N";
        }else{
            meetingCodeDao.deleteData(caseData.getItemId());
        }
        return null;
    }

    /**
     * 資料檢查
     * @param caseData
     * @param result
     */
    public void validate(Eip06w050Case caseData, BindingResult result) {
        ObjectError error;
        String itemTyp = caseData.getItemTyp();
        if ("B".equals(itemTyp)) {
            if (caseData.getQty() == null || caseData.getQty() <= 0) {
                error = new ObjectError("qty", "「數量」需大於0");
                result.addError(error);
            }
        } else if ("F".equals(itemTyp) || "FX".equals(itemTyp)) {
            if (caseData.getQty() == null || caseData.getQty() <= 0) {
                error = new ObjectError("qty", "「人數」需大於0");
                result.addError(error);
            }
        }
    }


}
