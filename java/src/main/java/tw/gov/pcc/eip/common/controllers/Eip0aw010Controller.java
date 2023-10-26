package tw.gov.pcc.eip.common.controllers;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import tw.gov.pcc.eip.common.cases.Eip0aw010Case;
import tw.gov.pcc.eip.services.Eip0aw010Service;
import tw.gov.pcc.eip.util.ObjectUtility;

import javax.servlet.http.HttpSession;

/**
 * 讀取 TITLE 介接差勤
 *
 * @author swho
 */
@Controller
@Slf4j
@AllArgsConstructor
public class Eip0aw010Controller {

    public static final String CASE_KEY = "eip0aw010Case";
    private final Eip0aw010Service eip0aw010Service;

    @RequestMapping(value = "/Common_getEip0aw010Case.action")
    @ResponseBody
    public Eip0aw010Case getEip0aw010Case(HttpSession httpSession) {
        Eip0aw010Case eip0aw010Case = ObjectUtility.normalizeObject(eip0aw010Service.getEip0aw010Case());
        httpSession.setAttribute(CASE_KEY, eip0aw010Case);
        return eip0aw010Case;
    }

}
