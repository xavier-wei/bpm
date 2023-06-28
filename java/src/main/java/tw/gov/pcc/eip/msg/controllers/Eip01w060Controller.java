package tw.gov.pcc.eip.msg.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.slf4j.Slf4j;
import tw.gov.pcc.eip.framework.domain.UserBean;
import tw.gov.pcc.eip.framework.spring.controllers.BaseController;
import tw.gov.pcc.eip.msg.cases.Eip01w060Case;
import tw.gov.pcc.eip.services.Eip01w060Service;
import tw.gov.pcc.eip.util.ObjectUtility;

/**
 * 單位簡介
 * 
 * @author vita
 *
 */
@Controller
@Slf4j
public class Eip01w060Controller extends BaseController {

    public static final String CASE_KEY = "_eip01w060Controller_casekey";
    private static final String MAIN_PAGE = "/eip01w060/eip01w060q";

    @Autowired
    private UserBean userData;
    @Autowired
    private Eip01w060Service eip01w060Service;

    /**
     * 進入 單位簡介
     * 
     * @param caseData
     * @return
     */
    @RequestMapping("/Eip01w060_enter.action")
    public String enter(@ModelAttribute(CASE_KEY) Eip01w060Case caseData) {
        log.debug("導向 Eip01w060_enter 單位簡介 畫面初始");
        eip01w060Service.initOptions(caseData);
        return MAIN_PAGE;
    }
    
    /**
     * 透過ajax 查詢明細資料
     * 
     * @param fseq 項次
     * @return
     */
    @RequestMapping("/Eip01w060_getDetail.action")
    @ResponseBody
    public Eip01w060Case getDetail(@RequestParam("attr") String attr) {
        log.debug("導向 Eip01w060_getDetail 單位簡介 明細查詢");
        return ObjectUtility.normalizeObject(eip01w060Service.getContent(attr));
    }
}
