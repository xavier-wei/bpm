package tw.gov.pcc.eip.services;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tw.gov.pcc.eip.common.cases.Eip04w010Case;
import tw.gov.pcc.eip.dao.OrclassDao;
import tw.gov.pcc.eip.dao.OrformdataDao;
import tw.gov.pcc.eip.domain.Orclass;
import tw.gov.pcc.eip.framework.domain.UserBean;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 線上報名分類管理Service
 * @author Weith
 */
@Service
public class Eip04w010Service extends OnlineRegService {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(Eip04w010Service.class);
    @Autowired
    UserBean userData;
    @Autowired
    OrclassDao orclassDao;
    @Autowired
    OrformdataDao orformdataDao;

    /**
     * 取得畫面上的分類列表
     * @param caseData
     */
    public void getOrclassList(Eip04w010Case caseData) {
        Map<String, String> signformMap = getSignform();
        List<Eip04w010Case.OrcCase> list = orclassDao.getAll().stream().map(t -> {
            Eip04w010Case.OrcCase orcCase = new Eip04w010Case.OrcCase();
            orcCase.setOrccode(t.getOrccode());
            orcCase.setOrcname(t.getOrcname());
            orcCase.setIscourse(StringUtils.equals(t.getIscourse(), "Y") ? "是" : "否");
            orcCase.setSignform(signformMap.get(t.getSignform()));
            orcCase.setStarting(CollectionUtils.isNotEmpty(orformdataDao.getOrstartingData(t.getOrccode())));
            return orcCase;
        }).collect(Collectors.toList());
        caseData.setOrcList(list);
    }

    /**
     * 取得畫面上的其中一個活動類別
     * @param caseData
     */
    public void getSingleClass(Eip04w010Case caseData) {
        Orclass orclass = orclassDao.findByPk(caseData.getOrccode());
        caseData.setOrccode(orclass.getOrccode());
        caseData.setOrcname(orclass.getOrcname());
        caseData.setIscourse(orclass.getIscourse());
        caseData.setSignform(orclass.getSignform());
    }

    /**
     * 新增活動分類
     * @param caseData
     */
    public void insertClass(Eip04w010Case caseData) {
        Orclass orclass = new Orclass();
        orclass.setOrccode(orclassDao.getOrccode());
        orclass.setOrcname(StringUtils.trim(caseData.getOrcname()));
        orclass.setSignform(caseData.getSignform());
        orclass.setIscourse(caseData.getIscourse());
        orclass.setCreuser(userData.getUserId());
        orclass.setCredt(LocalDateTime.now());
        orclassDao.insertData(orclass);
    }

    /**
     * 修改活動分類
     * @param caseData
     */
    public void modifyClass(Eip04w010Case caseData) {
        Orclass orclass = new Orclass();
        orclass.setOrcname(StringUtils.trim(caseData.getOrcname()));
        orclass.setSignform(caseData.getSignform());
        orclass.setIscourse(caseData.getIscourse());
        orclass.setUpddt(LocalDateTime.now());
        orclass.setUpduser(userData.getUserId());
        orclassDao.updateData(orclass, caseData.getOrccode());
    }

    /**
     * 刪除活動分類
     * @param caseData
     */
    public void deleteClass(Eip04w010Case caseData) {
        String[] orccodeArray = caseData.getOrccodeList().split(",");
        for (String orccode : orccodeArray) {
            orclassDao.deleteData(Long.parseLong(orccode));
        }
    }

}
