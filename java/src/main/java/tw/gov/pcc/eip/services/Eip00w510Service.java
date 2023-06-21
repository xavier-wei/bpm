package tw.gov.pcc.eip.services;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tw.gov.pcc.eip.common.cases.Eip00w510Case;
import tw.gov.pcc.eip.dao.OsclassDao;
import tw.gov.pcc.eip.dao.OsformdataDao;
import tw.gov.pcc.eip.domain.Osclass;
import tw.gov.pcc.eip.framework.domain.UserBean;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 意見調查分類列表Service
 * @author Weith
 */
@Service
public class Eip00w510Service extends OpinionSurveyService{
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(Eip00w510Service.class);
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
    public void getClassList(Eip00w510Case caseData) {
        List<Eip00w510Case.OscCase> list = osclassDao.getAll().stream().map(t -> {
            Eip00w510Case.OscCase orcCase = new Eip00w510Case.OscCase();
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
    public void getSingleClass(Eip00w510Case caseData) {
        Osclass osclass = osclassDao.findByPk(Integer.valueOf(caseData.getOsccode()));
        caseData.setOsccode(String.valueOf(osclass.getOsccode()));
        caseData.setOscname(osclass.getOscname());
    }

    /**
     * 新增意見調查分類
     * @param caseData
     */
    public void insertClass(Eip00w510Case caseData) {
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
    public void modifyClass(Eip00w510Case caseData) {
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
    public void deleteClass(Eip00w510Case caseData) {
        List<String>osccodeList = caseData.getOsccodeList().stream().filter(StringUtils::isNotBlank).collect(Collectors.toList());
        for (String osccode:osccodeList) {
            osclassDao.deleteData(Integer.valueOf(osccode));
        }
    }

}
