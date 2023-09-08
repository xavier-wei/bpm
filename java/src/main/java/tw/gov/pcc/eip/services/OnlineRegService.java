package tw.gov.pcc.eip.services;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tw.gov.pcc.eip.dao.DeptsDao;
import tw.gov.pcc.eip.dao.EipcodeDao;
import tw.gov.pcc.eip.dao.UsersDao;
import tw.gov.pcc.eip.domain.Depts;
import tw.gov.pcc.eip.domain.Eipcode;
import tw.gov.pcc.eip.domain.Users;

import java.io.File;
import java.time.chrono.MinguoChronology;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 線上報名共用Service
 *
 * @author Weith
 */
@Service
public class OnlineRegService {
    @Autowired
    EipcodeDao eipcodeDao;

    @Autowired
    DeptsDao deptsDao;

    @Autowired
    UsersDao usersDao;
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(OnlineRegService.class);

    DateTimeFormatter minguoformatter = DateTimeFormatter.ofPattern("yyy/MM/dd HH:mm")
            .withChronology(MinguoChronology.INSTANCE)
            .withLocale(Locale.TAIWAN);

    DateTimeFormatter minguoformatterForInput = DateTimeFormatter.ofPattern("yyyMMdd")
            .withChronology(MinguoChronology.INSTANCE)
            .withLocale(Locale.TAIWAN);

    DateTimeFormatter westdatetimeformatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");

    DateTimeFormatter timeformatter = DateTimeFormatter.ofPattern("HH:mm");

    /**
     * 取得全部報名資格
     * @return
     */
    public Map<String,String> getRegisqualAll() {
        List<Eipcode>titleList = eipcodeDao.findByCodeKindOrderByScodeno("TITLE");
        List<Depts>deptList = deptsDao.getEip03wDepts("1",null);
        Map<String, String> map = new LinkedHashMap<>();
        titleList.forEach(t -> map.put(t.getCodeno(), StringUtils.isEmpty(t.getCodename()) ? "" : t.getCodename()));
        deptList.forEach(t -> map.put("D" + t.getDept_id(), StringUtils.isEmpty(t.getDept_name()) ? "" : t.getDept_name()));
        return map;
    }

    /**
     * 取得單位列表提供畫面使用
     * @return map
     */
    public Map<String,String> getRegisqualDept() {
        List<Depts>list = deptsDao.getEip03wDepts("1",null);
        Map<String, String> map = new LinkedHashMap<>();
        list.forEach(t -> map.put("D" + t.getDept_id(), StringUtils.isEmpty(t.getDept_name()) ? "" : t.getDept_name()));
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
     * 取得職員列表提供畫面使用
     * @return map
     */
    public Map<String,String> getRegisqualE1() {
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
    public Map<String,String> getRegisqualE2() {
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
    public Map<String,String> getRegisqualE3() {
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
    public Map<String,String> getRegisqualE4() {
        List<Eipcode>list = eipcodeDao.findByCodekindScodekindOrderByCodeno("TITLE","4")
                .stream().sorted(Comparator.comparing(Eipcode::getCodeno))
                .collect(Collectors.toList());
        Map<String, String> map = new LinkedHashMap<>();
        list.forEach(t -> map.put(t.getCodeno(), StringUtils.isEmpty(t.getCodename()) ? "" : t.getCodename()));
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

    /**
     * 計算上課時數
     * @param hours
     * @param unit
     * @return
     */
    public Long calculateHours(String hours, String unit) {
        if ("H".equals(unit)){
            return Long.valueOf(hours);
        } else if ("D".equals(unit)) {
            return Long.parseLong(hours) * 8;
        } else if ("C".equals(unit)) {
            return Long.parseLong(hours) * 18;
        }
        return 0L;
    }

    /**
     * 取得全部報名方式
     * @return
     */
    public Map<String,String> getAllowappway() {
        List<Eipcode>list = eipcodeDao.findByCodeKindOrderByScodeno("APPWAY");
        Map<String, String> map = new LinkedHashMap<>();
        list.forEach(t -> map.put(t.getCodeno(), StringUtils.isEmpty(t.getCodename()) ? "" : t.getCodename()));
        return map;
    }

    /**
     * 取得檔案放置目錄
     * @return
     */
    public String getFilesPath() {
        String apDirectory = System.getProperty("user.dir");
        String serverDrive = apDirectory.substring(0, apDirectory.indexOf(File.separator));
        return serverDrive + "/eip/uploadfile/";
    }

    /**
     * 取得學位提供畫面使用
     * @return map
     */
    public Map<String,String> getDegreen() {
        List<Eipcode>list = eipcodeDao.findByCodekindOrderByCodeno("DEGREEN");
        Map<String, String> map = new LinkedHashMap<>();
        list.forEach(t -> map.put(t.getCodeno(), StringUtils.isEmpty(t.getCodename()) ? "" : t.getCodename()));
        return map;
    }

    /**
     * 取得活動狀態
     * @return
     */
    public Map<String,String> getOrstatus() {
        List<Eipcode>list = eipcodeDao.findByCodeKind("ORSTATUS");
        Map<String, String> map = new LinkedHashMap<>();
        list.forEach(t -> map.put(t.getScodekind(), StringUtils.isEmpty(t.getCodename()) ? "" : t.getCodename()));
        return map;
    }

    /**
     * 取得簽到表格式
     * @return
     */
    public Map<String,String> getSignform() {
        List<Eipcode>list = eipcodeDao.findByCodeKind("SIGNFORM");
        Map<String, String> map = new LinkedHashMap<>();
        list.forEach(t -> map.put(t.getScodekind(), StringUtils.isEmpty(t.getCodename()) ? "" : t.getCodename()));
        return map;
    }

    /**
     * 取得報名狀態
     * @return
     */
    public Map<String,String> getRegstatus() {
        List<Eipcode>list = eipcodeDao.findByCodeKind("REGSTATUS");
        Map<String, String> map = new LinkedHashMap<>();
        list.forEach(t -> map.put(t.getScodekind(), StringUtils.isEmpty(t.getCodename()) ? "" : t.getCodename()));
        return map;
    }

    /**
     * 取得縣市
     * @return
     */
    public Map<String,String> getCountry() {
        List<Eipcode>list = eipcodeDao.findByCodeKind("COUNTRY");
        Map<String, String> map = new LinkedHashMap<>();
        list.forEach(t -> map.put(t.getCodeno(), StringUtils.isEmpty(t.getCodename()) ? "" : t.getCodename()));
        return map;
    }
}
