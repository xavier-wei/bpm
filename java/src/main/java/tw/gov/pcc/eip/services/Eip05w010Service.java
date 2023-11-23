package tw.gov.pcc.eip.services;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tw.gov.pcc.eip.common.cases.Eip05w010Case;
import tw.gov.pcc.eip.dao.OsclassDao;
import tw.gov.pcc.eip.dao.OsformdataDao;
import tw.gov.pcc.eip.domain.Osclass;
import tw.gov.pcc.eip.framework.domain.UserBean;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 意見調查分類列表Service
 * @author Weith
 */
@Service
public class Eip05w010Service extends OpinionSurveyService{
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(Eip05w010Service.class);
    @Autowired
    UserBean userData;
    @Autowired
    OsclassDao osclassDao;
    @Autowired
    OsformdataDao osformdataDao;

    /**
     * 取得畫面上的分類列表
     * @param caseData
     */
    public void getClassList(Eip05w010Case caseData) {
        List<Eip05w010Case.OscCase> list = osclassDao.getAll().stream().map(t -> {
            Eip05w010Case.OscCase orcCase = new Eip05w010Case.OscCase();
            orcCase.setOsccode(String.valueOf(t.getOsccode()));
            orcCase.setOscname(t.getOscname());
            orcCase.setUpddt(ObjectUtils.isEmpty(t.getUpddt()) ? "" : t.getUpddt().format(minguoformatter));
            orcCase.setStarting(CollectionUtils.isNotEmpty(osformdataDao.getStartingData(t.getOsccode())));
            return orcCase;
        }).collect(Collectors.toList());
        caseData.setOscList(list);
    }

    /**
     * 取得畫面上的其中一個類別
     * @param caseData
     */
    public void getSingleClass(Eip05w010Case caseData) {
        Osclass osclass = osclassDao.findByPk(Integer.valueOf(caseData.getOsccode()));
        caseData.setOsccode(String.valueOf(osclass.getOsccode()));
        caseData.setOscname(osclass.getOscname());
    }

    /**
     * 新增意見調查分類
     * @param caseData
     */
    public void insertClass(Eip05w010Case caseData) {
        Osclass osclass = new Osclass();
        osclass.setOsccode(osclassDao.getOsccode());
        osclass.setOscname(StringUtils.trim(caseData.getOscname()));
        osclass.setCreuser(userData.getUserId());
        osclass.setCredt(LocalDateTime.now());
        osclassDao.insertData(osclass);
    }

    /**
     * 修改意見調查分類
     * @param caseData
     */
    public void modifyClass(Eip05w010Case caseData) {
        Osclass osclass = osclassDao.findByPk(Integer.valueOf(caseData.getOsccode()));
        osclass.setOscname(StringUtils.trim(caseData.getOscname()));
        osclass.setUpddt(LocalDateTime.now());
        osclass.setUpduser(userData.getUserId());
        osclassDao.updateData(osclass, Integer.valueOf(caseData.getOsccode()));
    }

    /**
     * 刪除活動分類
     * @param caseData
     */
    public void deleteClass(Eip05w010Case caseData) {
        String[] osccodeArray = caseData.getOsccodeList().split(",");
        for (String osccode:osccodeArray) {
            osclassDao.deleteData(Integer.valueOf(osccode));
        }
    }

}
