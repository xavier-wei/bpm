package tw.gov.pcc.eip.bpm.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@Slf4j
// @Profiles("dev")
public class Bpm01w010Controller {

    public static final String CASE_KEY = "_bpm01w010Controller_caseData";
    private static final String MAIN_PAGE = "/bpm/Bpm01w010";//主頁



    @RequestMapping("/Bpm01w010_enter.action")
    public ModelAndView test(HttpServletRequest request) {
        String referer = request.getHeader("referer");


        ModelAndView modelAndView = new ModelAndView("/bpm/Bpm01w010");
        if (referer != null) {
            String keyword = "/eip";
            int index = referer.indexOf(keyword); // 获取关键词的索引
            referer = referer.replace(referer.substring(index, referer.length()), "");
            modelAndView.addAllObjects(Map.of("bpmPath", referer + "/bpm"));
        }


        log.info("BPM表單管理:: 導向{}頁面","Bpm01w010 表單申請");
        return modelAndView;
    }


}
