package tw.gov.pcc.eip.services;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tw.gov.pcc.eip.dao.*;
import tw.gov.pcc.eip.domain.*;

import java.time.chrono.MinguoChronology;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 意見調查共用Service
 *
 * @author Weith
 */
@Service
public class OpinionSurveyService {
    @Autowired
    EipcodeDao eipcodeDao;
    @Autowired
    OsquestionDao osquestionDao;
    @Autowired
    OsitemDao ositemDao;
    @Autowired
    DeptsDao deptsDao;
    @Autowired
    UsersDao usersDao;
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(OpinionSurveyService.class);

    DateTimeFormatter minguoformatter = DateTimeFormatter.ofPattern("yyy/MM/dd HH:mm:ss")
            .withChronology(MinguoChronology.INSTANCE)
            .withLocale(Locale.TAIWAN);

    DateTimeFormatter simpleMinguoformatter = DateTimeFormatter.ofPattern("yyy/MM/dd HH:mm")
            .withChronology(MinguoChronology.INSTANCE)
            .withLocale(Locale.TAIWAN);

    DateTimeFormatter minguoformatterForInput = DateTimeFormatter.ofPattern("yyyMMdd")
            .withChronology(MinguoChronology.INSTANCE)
            .withLocale(Locale.TAIWAN);

    DateTimeFormatter timeformatter = DateTimeFormatter.ofPattern("HH:mm");

    /**
     * 取得主題狀態
     * @return
     */
    public Map<String,String> getOsstatus() {
        List<Eipcode>list = eipcodeDao.findByCodeKind("OSSTATUS");
        Map<String, String> map = new LinkedHashMap<>();
        list.forEach(t -> map.put(t.getScodekind(), StringUtils.isEmpty(t.getCodename()) ? "" : t.getCodename()));
        return map;
    }

    /**
     * 取得單位列表提供畫面使用
     * @return map
     */
    public Map<String,String> getUsers() {
        List<Users>list = usersDao.selectAll();
        Map<String, String> map = new LinkedHashMap<>();
        list.forEach(t -> map.put(t.getUser_id(), StringUtils.isEmpty(t.getUser_name()) ? "" : t.getUser_name()));
        return map;
    }

    /**
     * 取得單位列表提供畫面使用
     * @return map
     */
    public Map<String,String> getLimitvoteDept() {
        List<Depts>list = deptsDao.getEip03wDepts("1",null);
        Map<String, String> map = new LinkedHashMap<>();
        list.forEach(t -> map.put("D" + t.getDept_id(), StringUtils.isEmpty(t.getDept_name()) ? "" : t.getDept_name()));
        return map;
    }

    /**
     * 取得職員列表提供畫面使用
     * @return map
     */
    public Map<String,String> getLimitvoteE1() {
        List<Eipcode>list = eipcodeDao.findByCodekindScodekindOrderByCodeno("TITLE","1")
                .stream().sorted(Comparator.comparing(Eipcode::getCodeno))
                .collect(Collectors.toList());
        Map<String, String> map = new LinkedHashMap<>();
        list.forEach(t -> map.put(t.getCodeno(), StringUtils.isEmpty(t.getCodename()) ? "" : t.getCodename()));
        return map;
    }

    /**
     * 取得聘雇人員列表提供畫面使用
     * @return map
     */
    public Map<String,String> getLimitvoteE2() {
        List<Eipcode>list = eipcodeDao.findByCodekindScodekindOrderByCodeno("TITLE","2")
                .stream().sorted(Comparator.comparing(Eipcode::getCodeno))
                .collect(Collectors.toList());
        Map<String, String> map = new LinkedHashMap<>();
        list.forEach(t -> map.put(t.getCodeno(), StringUtils.isEmpty(t.getCodename()) ? "" : t.getCodename()));
        return map;
    }

    /**
     * 取得工友列表提供畫面使用
     * @return map
     */
    public Map<String,String> getLimitvoteE3() {
        List<Eipcode>list = eipcodeDao.findByCodekindScodekindOrderByCodeno("TITLE","3")
                .stream().sorted(Comparator.comparing(Eipcode::getCodeno))
                .collect(Collectors.toList());
        Map<String, String> map = new LinkedHashMap<>();
        list.forEach(t -> map.put(t.getCodeno(), StringUtils.isEmpty(t.getCodename()) ? "" : t.getCodename()));
        return map;
    }

    /**
     * 取得勞基法人員列表提供畫面使用
     * @return map
     */
    public Map<String,String> getLimitvoteE4() {
        List<Eipcode>list = eipcodeDao.findByCodekindScodekindOrderByCodeno("TITLE","4")
                .stream().sorted(Comparator.comparing(Eipcode::getCodeno))
                .collect(Collectors.toList());
        Map<String, String> map = new LinkedHashMap<>();
        list.forEach(t -> map.put(t.getCodeno(), StringUtils.isEmpty(t.getCodename()) ? "" : t.getCodename()));
        return map;
    }

    /**
     * 取得題目map
     * @return map
     */
    public Map<String,String> getQMap(String osformno) {
        List<Osquestion>list = osquestionDao.getAllQuestionByOsformno(osformno);
        Map<String, String> map = new LinkedHashMap<>();
        list.forEach(t -> map.put(String.valueOf(t.getQseqno()), t.getTopic()));
        return map;
    }

    /**
     * 取得題目map
     * @return map
     */
    public Map<String,String> getIMap(String osformno) {
        List<Ositem>list = ositemDao.getAllByOsformno(osformno);
        Map<String, String> map = new LinkedHashMap<>();
        list.forEach(t -> map.put(String.valueOf(t.getIseqno()), t.getItemdesc()));
        return map;
    }

    /**
     * 取得郵件相關資料提供畫面使用
     * @return map
     */
    public Map<String,String> getMail() {
        List<Eipcode>list = eipcodeDao.findByCodeKind("MAIL");
        Map<String, String> map = new LinkedHashMap<>();
        list.forEach(t -> map.put(t.getCodeno(), StringUtils.isEmpty(t.getCodename()) ? "" : t.getCodename()));
        return map;
    }

}
