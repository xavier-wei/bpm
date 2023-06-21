package tw.gov.pcc.eip.services;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tw.gov.pcc.eip.common.cases.Eip06w040Case;
import tw.gov.pcc.eip.common.controllers.Eip06w040Controller;
import tw.gov.pcc.eip.common.report.Eip06w040l00xls;
import tw.gov.pcc.eip.dao.MeetingCodeDao;
import tw.gov.pcc.eip.dao.MeetingDao;
import tw.gov.pcc.eip.dao.MeetingItemDao;
import tw.gov.pcc.eip.domain.*;
import tw.gov.pcc.eip.framework.domain.UserBean;
import tw.gov.pcc.eip.util.DateUtility;
import tw.gov.pcc.eip.util.ExceptionUtility;

import java.io.ByteArrayOutputStream;
import java.util.*;

/**
 * 會議室活動報表列印作業
 * @author 2201009
 */
@Service
public class Eip06w040Service {
    private static final Logger log = LoggerFactory.getLogger(Eip06w040Controller.class);

    @Autowired
    UserBean userData;
    @Autowired
    private MeetingCodeDao meetingCodeDao;
    @Autowired
    private MeetingDao meetingDao;
    @Autowired
    private MeetingItemDao meetingItemDao;

    public Eip06w040Case print(Eip06w040Case caseData) {
        caseData.setBaos(getEip06w040xls(caseData));
        return caseData;
    }

    /**
     * 產製會議室活動報表(excel)
     *
     * @param caseData 畫面參數
     * @return 現況統計表內容內容
     */
    private ByteArrayOutputStream getEip06w040xls(Eip06w040Case caseData) {
        String meetingdt = DateUtility.changeDateTypeToWestDate(caseData.getMeetingdt());
        // get list from Dao
        List<Eip06w040Report> resultList = meetingDao.selectValidMeetingByMeetingdt(meetingdt);
        StringBuilder itemName = new StringBuilder();
        StringBuilder foodName_Qty = new StringBuilder();
        String meetingId = null;
        if (resultList.size() != 0) {
            try {
                Eip06w040l00xls eip06w040l00xls = new Eip06w040l00xls();
                List<Eip06w040Report> newResultList = new ArrayList<>();
                for (int i = 0 ; i < resultList.size() ; i++){
                    if (resultList.get(i).getItemId() != null && resultList.get(i).getItemId().startsWith("A")){
                        foodName_Qty.append(", ").append(resultList.get(i).getItemName()).append("-").append(resultList.get(i).getItemQty());
                    } else if (resultList.get(i).getItemId() != null && resultList.get(i).getItemId().startsWith("B")) {
                        itemName.append(", ").append(resultList.get(i).getItemName());
                    }
                    //暫存此迴圈 meetingId
                    meetingId = resultList.get(i).getMeetingId();
                    if((resultList.size()-1 > i && !StringUtils.equals(meetingId, resultList.get(i+1).getMeetingId()))
                            || (resultList.size()-1 == i)){
                        resultList.get(i).setFoodName(foodName_Qty.toString());
                        resultList.get(i).setItemName(itemName.toString());
                        newResultList.add(resultList.get(i));
                        foodName_Qty.delete(0,foodName_Qty.length());
                        itemName.delete(0,itemName.length());
                    }
                }
                eip06w040l00xls.createXls(newResultList);
                return eip06w040l00xls.getOutputStream();
            } catch (Exception e) {
                log.error(ExceptionUtility.getStackTrace(e));
            }
        }
        return null;
    }
}
