package tw.gov.pcc.eip.bpm.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import tw.gov.pcc.eip.bpm.utils.AESEncryptionService;
import tw.gov.pcc.eip.bpm.utils.RefererTemp;
import tw.gov.pcc.eip.framework.domain.UserBean;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@Slf4j
public class Bpm01w040Controller {

    public static final String CASE_KEY = "_bpm01w040Controller_caseData";
    private static final String MAIN_PAGE = "/bpm/Bpm01w040";//主頁
    private final String MAPPING_PATH = "/Bpm01w040_enter.action";
    private final UserBean userData;
    private final AESEncryptionService aesEncryptionService;
    public Bpm01w040Controller(UserBean userData, AESEncryptionService aesEncryptionService) {
        this.userData = userData;
        this.aesEncryptionService = aesEncryptionService;
    }

    @RequestMapping(MAPPING_PATH)
    public ModelAndView notify(HttpServletRequest request) {

        boolean isBpmLogin=Boolean.valueOf(request.getParameter("bpm"));
        System.out.println("isBpmLogin = " + isBpmLogin);
        String token = null;
        try {
            token = aesEncryptionService.encrypt(userData.getUserId());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // 無bpmLogin資訊情況
        if (!isBpmLogin) {
            String referer = request.getRequestURL().toString();
            String keyword = "/eip"+MAPPING_PATH;
            referer = referer.replace(keyword, "");
            RefererTemp.REFERER_MAP.put("referer", referer);
            StringBuilder path = new StringBuilder(referer)
                    .append("/bpm/api/loginBpm")
                    .append("?referer=")
                    .append(referer)
                    .append("&path=/Bpm01w040_enter.action&token=")
                    .append(token);
            return new ModelAndView("redirect:"+path);
        }

        // 有bpmLogin資訊情況
        String referer=RefererTemp.REFERER_MAP.get("referer");

        // todo 本地開發用，未來上線記得拔掉;
        if (request.getServerPort() == 8080) {
            referer = "http://localhost:9000";
        }


        log.info("BPM表單管理:: 導向{}頁面","Bpm01w010 表單查詢");
        return new ModelAndView(MAIN_PAGE).addAllObjects(Map.of("bpmPath", referer + "/bpm/notify"));
    }
}
