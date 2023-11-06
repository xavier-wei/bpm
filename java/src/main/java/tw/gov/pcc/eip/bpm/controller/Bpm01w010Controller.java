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
public class Bpm01w010Controller {

    public static final String CASE_KEY = "_bpm01w020Controller_caseData";
    private static final String MAPPING_PATH = "/Bpm01w010_enter.action";
    private static final String MAIN_PAGE = "/bpm/Bpm01w010";//主頁
    private final UserBean userData;
    private final AESEncryptionService aesEncryptionService;

    public Bpm01w010Controller(UserBean userData, AESEncryptionService aesEncryptionService) {
        this.userData = userData;
        this.aesEncryptionService = aesEncryptionService;
    }

    @RequestMapping(MAPPING_PATH)
    public ModelAndView l410(HttpServletRequest request) {

        boolean isBpmLogin=Boolean.parseBoolean(request.getParameter("bpm"));
        String token;
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
                    .append("&path=/Bpm01w010_enter.action&token=")
                    .append(token);
            return new ModelAndView("redirect:"+path);
        }

        // 有bpmLogin資訊情況
        String referer=RefererTemp.REFERER_MAP.get("referer");

        // todo 本地開發用，未來上線記得拔掉;
        if (request.getServerPort() == 8080) {
            referer = "http://localhost:9000";
        }
        log.info("BPM表單管理:: 導向{}頁面","Bpm01w010 表單申請-l410");
        return new ModelAndView(MAIN_PAGE).addAllObjects(Map.of("bpmPath", referer + "/bpm/l410Query"));
    }
}
