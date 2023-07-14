package tw.gov.pcc.eip.services;

import static org.apache.commons.lang3.StringUtils.trimToNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import tw.gov.pcc.eip.dao.DeptsDao;
import tw.gov.pcc.eip.dao.EipcodeDao;
import tw.gov.pcc.eip.dao.MsgdataDao;
import tw.gov.pcc.eip.dao.UsersDao;
import tw.gov.pcc.eip.domain.Eipcode;
import tw.gov.pcc.eip.msg.cases.Eip01w020Case;
import tw.gov.pcc.eip.msg.cases.Eip01w020PageCase;

/**
 * 訊息篇數統計
 * 
 * @author vita
 *
 */
@Service
@Slf4j
public class Eip01w020Service {

    @Autowired
    private EipcodeDao eipCodeDao;
    @Autowired
    private MsgdataDao msgdataDao;
    @Autowired
    private DeptsDao deptsDao;
    @Autowired
    private UsersDao usersDao;

    /**
     * 初始下拉選單
     * 
     * @param caseData
     */
    public void initOptions(Eip01w020Case caseData) {
        // 分類名稱 訊息類別
        List<Eip01w020Case.Option> msgtypes = eipCodeDao.findByCodeKind("MESSAGETYPE").stream()
                .sorted(Comparator.comparing(Eipcode::getScodekind).thenComparing(Eipcode::getCodeno))
                .map(m -> {
                    Eip01w020Case.Option option = new Eip01w020Case.Option();
                    option.setCodeno(m.getScodekind() + m.getCodeno()); // codeno[0]:attributype codeno[1]:msgtype
                    option.setCodename(m.getCodename());
                    return option;
                }).collect(Collectors.toCollection(ArrayList::new));
        caseData.setMsgtypes(msgtypes);
        // 聯絡單位
        List<Eip01w020Case.Option> contactunits = deptsDao.getEip01wDepts().stream()
                .filter(f -> !"00".equals(f.getDept_id())).map(m -> {
                    Eip01w020Case.Option option = new Eip01w020Case.Option();
                    option.setCodeno(m.getDept_id());
                    option.setCodename(m.getDept_name());
                    return option;
                }).collect(Collectors.toCollection(ArrayList::new));
        caseData.setContactunits(contactunits);
        // 建立人員
        List<Eip01w020Case.Option> users = usersDao.selectAll().stream().filter(f -> "Y".equals(f.getAcnt_is_valid()))
                .map(m -> {
                    Eip01w020Case.Option option = new Eip01w020Case.Option();
                    option.setCodeno(m.getUser_id());
                    option.setCodename(m.getUser_name());
                    return option;
                }).collect(Collectors.toCollection(ArrayList::new));
        caseData.setCreators(users);
        // 更新人員
        caseData.setUpdaters(users);
    }

    /**
     * 取得統計資料
     * 
     * @param caseData
     */
    public void getStatistics(Eip01w020Case caseData) {
        String str = caseData.getMsgtype();// 分類名稱
        String msgtype = trimToNull(StringUtils.substring(str, 1, 2)); // 第二碼訊息類別
        String attributype = trimToNull(StringUtils.substring(str, 0, 1)); // 第一碼屬性
        String subject = trimToNull(caseData.getSubject()); // 主旨/連結網址
        String contactunit = trimToNull(caseData.getContactunit()); // 連絡單位
        String creatid = trimToNull(caseData.getCreatid()); // 建立人員
        String updid = trimToNull(caseData.getUpdid()); // 更新人員
        String releasedts = trimToNull(caseData.getReleasedts()); // 架時間起
        String releasedte = trimToNull(caseData.getReleasedte()); // 上架時間迄

        List<Eip01w020PageCase> data = msgdataDao.getEip01w020DataList(msgtype, attributype, subject, contactunit,
                creatid, updid, releasedts, releasedte);

        caseData.setQryList(data);
    }
}
