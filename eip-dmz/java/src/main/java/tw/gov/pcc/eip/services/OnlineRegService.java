package tw.gov.pcc.eip.services;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tw.gov.pcc.eip.dao.EipcodeDao;
import tw.gov.pcc.eip.domain.Eipcode;

import java.io.File;
import java.util.*;

/**
 * 線上報名共用Service
 *
 * @author Weith
 */
@Service
public class OnlineRegService {
    @Autowired
    EipcodeDao eipcodeDao;
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(OnlineRegService.class);

    /**
     * 取得全部報名資格
     * @return
     */
    public Map<String,String> getRegisqualAll() {
        List<Eipcode>list = eipcodeDao.findByCodeKindOrderByScodeno("REGISQUAL");
        Map<String, String> map = new LinkedHashMap<>();
        list.forEach(t -> map.put(t.getCodeno(), StringUtils.isEmpty(t.getCodename()) ? "" : t.getCodename()));
        return map;
    }

    /**
     * 取得單位列表提供畫面使用
     * @return map
     */
    public Map<String,String> getRegisqualDept() {
        List<Eipcode>list = eipcodeDao.findByCodekindScodekindOrderByCodeno("REGISQUAL","U");
        Map<String, String> map = new LinkedHashMap<>();
        list.forEach(t -> map.put(t.getCodeno(), StringUtils.isEmpty(t.getCodename()) ? "" : t.getCodename()));
        return map;
    }

    /**
     * 取得所有職稱列表
     * @return map
     */
    public Map<String,String> getRegisqualJobTitle() {
        List<Eipcode>list = eipcodeDao.findByCodekindScodekindOrderByCodeno("REGISQUAL","U");
        Map<String, String> map = new LinkedHashMap<>();
        list.forEach(t -> map.put(t.getCodeno(), StringUtils.isEmpty(t.getCodename()) ? "" : t.getCodename()));
        return map;
    }

    /**
     * 取得職員列表提供畫面使用
     * @return map
     */
    public Map<String,String> getRegisqualE1() {
        List<Eipcode>list = eipcodeDao.findByCodekindScodekindOrderByCodeno("REGISQUAL","E1");
        Map<String, String> map = new LinkedHashMap<>();
        list.forEach(t -> map.put(t.getCodeno(), StringUtils.isEmpty(t.getCodename()) ? "" : t.getCodename()));
        return map;
    }

    /**
     * 取得聘雇人員列表提供畫面使用
     * @return map
     */
    public Map<String,String> getRegisqualE2() {
        List<Eipcode>list = eipcodeDao.findByCodekindScodekindOrderByCodeno("REGISQUAL","E2");
        Map<String, String> map = new LinkedHashMap<>();
        list.forEach(t -> map.put(t.getCodeno(), StringUtils.isEmpty(t.getCodename()) ? "" : t.getCodename()));
        return map;
    }

    /**
     * 取得工友列表提供畫面使用
     * @return map
     */
    public Map<String,String> getRegisqualE3() {
        List<Eipcode>list = eipcodeDao.findByCodekindScodekindOrderByCodeno("REGISQUAL","E3");
        Map<String, String> map = new LinkedHashMap<>();
        list.forEach(t -> map.put(t.getCodeno(), StringUtils.isEmpty(t.getCodename()) ? "" : t.getCodename()));
        return map;
    }

    /**
     * 取得勞基法人員列表提供畫面使用
     * @return map
     */
    public Map<String,String> getRegisqualE4() {
        List<Eipcode>list = eipcodeDao.findByCodekindScodekindOrderByCodeno("REGISQUAL","E4");
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
}
