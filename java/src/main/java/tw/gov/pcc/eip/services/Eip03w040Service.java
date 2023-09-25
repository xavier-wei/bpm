package tw.gov.pcc.eip.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tw.gov.pcc.eip.common.cases.Eip03w040Case;
import tw.gov.pcc.eip.common.controllers.Eip03w040Controller;
import tw.gov.pcc.eip.common.report.Eip03w040l00xls;
import tw.gov.pcc.eip.dao.DeptsDao;
import tw.gov.pcc.eip.dao.EipcodeDao;
import tw.gov.pcc.eip.dao.KeepTrkDtlDao;
import tw.gov.pcc.eip.domain.Eipcode;
import tw.gov.pcc.eip.framework.domain.UserBean;
import tw.gov.pcc.eip.util.DateUtility;
import tw.gov.pcc.eip.util.ExceptionUtility;

import java.io.ByteArrayOutputStream;
import java.util.List;

/**
 * 重要列管事項_件數統計
 * @author 2201009
 */
@Service
public class Eip03w040Service {
    private static final Logger log = LoggerFactory.getLogger(Eip03w040Controller.class);


    @Autowired
    private KeepTrkDtlDao keepTrkDtlDao;
    @Autowired
    private EipcodeDao eipcodeDao;
    @Autowired
    private DeptsDao deptsDao;
    @Autowired
    private UserBean userData;

    public void initDataList(Eip03w040Case caseData) {
        List<Eip03w040Case> list = keepTrkDtlDao.selectAll();
        list.forEach(a->{
            a.setSameDept(userData.getDeptId().equals(a.getTrkObj()));
        });
        caseData.setEip03w040CaseList(list);
    }

    /**
     * 查詢結案未結案統計件數
     * @param caseData
     */
    public void queryIssue(Eip03w040Case caseData){
        List<Eip03w040Case> detailList = keepTrkDtlDao.selectDatabytrkObjAndPrcsts(caseData.getTrkObj(),caseData.getStatus());
        //處理狀態：1-待處理 2-待解列 3-已解列
        List<Eipcode> trkStsList = eipcodeDao.findByCodeKind("TRKPRCSTS");

        // 替換 trkSts 為中文字
        detailList.forEach( a-> {
            trkStsList.forEach( b-> {
                if(a.getPrcSts().equals(b.getCodeno())){
                    a.setPrcSts(b.getCodename());
                }
            });
            a.setFmtSupDt(DateUtility.parseLocalDateTimeToChineseDateTime(a.getSupDt(), false));
            if (a.getTrkCont() != null){
                a.setTrkCont(a.getTrkCont().replaceAll("\r\n","<br>"));
            }
            if (a.getRptCont() != null){
                a.setRptCont(a.getRptCont().replaceAll("\r\n","<br>"));
            }
        });

        caseData.setEip03w040CaseDetailList(detailList);
    }

    /**
     * 列印報表
     * @param caseData
     * @return
     */
    public Eip03w040Case print(Eip03w040Case caseData) {
        caseData.setBaos(getEip03w040xls(caseData));
        return caseData;
    }

    /**
     *  產製件數統計報表(excel)
     * @param caseData
     * @return
     */
    private ByteArrayOutputStream getEip03w040xls(Eip03w040Case caseData){
        List<Eip03w040Case> resultList = keepTrkDtlDao.selectDatabytrkObjAndPrcsts(caseData.getTrkObj(),caseData.getStatus());
        //處理狀態：1-待處理 2-待解列 3-已解列
        List<Eipcode> trkStsList = eipcodeDao.findByCodeKind("TRKPRCSTS");

        // 替換 trkSts 為中文字
        resultList.forEach( a-> {
            trkStsList.forEach( b-> {
                if(a.getPrcSts().equals(b.getCodeno())){
                    a.setPrcSts(b.getCodename());
                }
            });
            a.setFmtSupDt(DateUtility.parseLocalDateTimeToChineseDateTime(a.getSupDt(), false));
        });


        if(resultList.size() != 0){
            try {
                Eip03w040l00xls eip03w040l00xls = new Eip03w040l00xls();
                String status = caseData.getStatus().equals("closed")? "已結案" : "未結案";
                eip03w040l00xls.createXls(status, resultList);
                caseData.setTrkObj(deptsDao.findByPk(caseData.getTrkObj()).getDept_name());
                return eip03w040l00xls.getOutputStream();
            }catch (Exception e){
                log.error(ExceptionUtility.getStackTrace(e));
            }
        }
        return null;
    }
}
