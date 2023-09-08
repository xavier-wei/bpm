package tw.gov.pcc.eip.bpm.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import tw.gov.pcc.eip.bpm.utils.RefererTemp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@Slf4j
// @Profiles("dev")
public class Bpm01w010Controller {

    public static final String CASE_KEY = "_bpm01w010Controller_caseData";
    private static final String MAIN_PAGE = "/bpm/Bpm01w010";//主頁

    @RequestMapping("/BPM/L414QUERY.bpmAction")
    public ModelAndView l414(HttpServletRequest request) {

        // 確認是否有無bpmLogin資訊
        HttpSession session = request.getSession();
        Boolean isBpmLogin =(Boolean) session.getAttribute("bpmLogin");

        // 無bpmLogin資訊情況
        if (isBpmLogin == null|| !isBpmLogin) {
            log.info("BPM表單管理:: {}導向頁面","/bpm cookies不存在，重導取得");
            session.setAttribute("bpmLogin",true);
            String referer = request.getHeader("referer");
            String keyword = "/eip";
            int index = referer.indexOf(keyword); //
            referer = referer.replace(referer.substring(index, referer.length()), "");
            RefererTemp.refererMap.put("referer", referer);
            StringBuilder path = new StringBuilder(referer)
                                    .append("/bpm/api/loginBpm")
                                    .append("?referer=")
                                    .append(referer)
                                    .append("&path=/BPM/L414QUERY.bpmAction");
            return new ModelAndView("redirect:"+path);
        }

        // 有bpmLogin資訊情況
        String referer=RefererTemp.refererMap.get("referer");

        // todo 本地開發用，未來上線記得拔掉;
        if (request.getServerPort() == 8080) {
            referer = "http://localhost:9000";
        }

        log.info("BPM表單管理:: 導向{}頁面","Bpm01w010 表單申請-l414");
        return new ModelAndView("/bpm/Bpm01w010").addAllObjects(Map.of("bpmPath", referer + "/bpm/l414Query"));
    }

    @RequestMapping("/BPM/L410QUERY.bpmAction")
    public ModelAndView l410(HttpServletRequest request) {

        // 確認是否有無bpmLogin資訊
        HttpSession session = request.getSession();
        Boolean isBpmLogin =(Boolean) session.getAttribute("bpmLogin");

        // 無bpmLogin資訊情況
        if (isBpmLogin == null|| !isBpmLogin) {
            log.info("BPM表單管理:: {}導向頁面","/bpm cookies不存在，重導取得");
            session.setAttribute("bpmLogin",true);
            String referer = request.getHeader("referer");
            String keyword = "/eip";
            int index = referer.indexOf(keyword); //
            referer = referer.replace(referer.substring(index, referer.length()), "");
            RefererTemp.refererMap.put("referer", referer);
            StringBuilder path = new StringBuilder(referer)
                    .append("/bpm/api/loginBpm")
                    .append("?referer=")
                    .append(referer)
                    .append("&path=/BPM/L410QUERY.bpmAction");
            return new ModelAndView("redirect:"+path);
        }

        // 有bpmLogin資訊情況
        String referer=RefererTemp.refererMap.get("referer");

        // todo 本地開發用，未來上線記得拔掉;
        if (request.getServerPort() == 8080) {
            referer = "http://localhost:9000";
        }
        log.info("BPM表單管理:: 導向{}頁面","Bpm01w010 表單申請-l410");
        return new ModelAndView("/bpm/Bpm01w010").addAllObjects(Map.of("bpmPath", referer + "/bpm/l410Query"));
    }

    @RequestMapping("/BPM/PENDING.bpmAction")
    public ModelAndView pending(HttpServletRequest request) {

        // 確認是否有無bpmLogin資訊
        HttpSession session = request.getSession();
        Boolean isBpmLogin =(Boolean) session.getAttribute("bpmLogin");

        // 無bpmLogin資訊情況
        if (isBpmLogin == null|| !isBpmLogin) {
            log.info("BPM表單管理:: {}導向頁面","/bpm cookies不存在，重導取得");
            session.setAttribute("bpmLogin",true);
            String referer = request.getHeader("referer");
            String keyword = "/eip";
            int index = referer.indexOf(keyword); //
            referer = referer.replace(referer.substring(index, referer.length()), "");
            RefererTemp.refererMap.put("referer", referer);
            StringBuilder path = new StringBuilder(referer)
                    .append("/bpm/api/loginBpm")
                    .append("?referer=")
                    .append(referer)
                    .append("&path=/BPM/PENDING.bpmAction");
            return new ModelAndView("redirect:"+path);
        }

        // 有bpmLogin資訊情況
        String referer=RefererTemp.refererMap.get("referer");

        // todo 本地開發用，未來上線記得拔掉;
        if (request.getServerPort() == 8080) {
            referer = "http://localhost:9000";
        }

        log.info("BPM表單管理:: 導向{}頁面","Bpm01w010 待處理表單");
        return new ModelAndView("/bpm/Bpm01w010").addAllObjects(Map.of("bpmPath", referer + "/bpm/pending"));
    }

    @RequestMapping("/BPM/NOTIFY.bpmAction")
    public ModelAndView notify(HttpServletRequest request) {

        // 確認是否有無bpmLogin資訊
        HttpSession session = request.getSession();
        Boolean isBpmLogin =(Boolean) session.getAttribute("bpmLogin");

        // 無bpmLogin資訊情況
        if (isBpmLogin == null|| !isBpmLogin) {
            log.info("BPM表單管理:: {}導向頁面","/bpm cookies不存在，重導取得");
            session.setAttribute("bpmLogin",true);
            String referer = request.getHeader("referer");
            String keyword = "/eip";
            int index = referer.indexOf(keyword); //
            referer = referer.replace(referer.substring(index, referer.length()), "");
            RefererTemp.refererMap.put("referer", referer);
            StringBuilder path = new StringBuilder(referer)
                    .append("/bpm/api/loginBpm")
                    .append("?referer=")
                    .append(referer)
                    .append("&path=/BPM/NOTIFY.bpmAction");
            return new ModelAndView("redirect:"+path);
        }

        // 有bpmLogin資訊情況
        String referer=RefererTemp.refererMap.get("referer");

        // todo 本地開發用，未來上線記得拔掉;
        if (request.getServerPort() == 8080) {
            referer = "http://localhost:9000";
        }

        log.info("BPM表單管理:: 導向{}頁面","Bpm01w010 表單查詢");
        return new ModelAndView("/bpm/Bpm01w010").addAllObjects(Map.of("bpmPath", referer + "/bpm/notify"));
    }
}
