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
import tw.gov.pcc.eip.msg.cases.Eip01w070Case;
import tw.gov.pcc.eip.services.Eip01w070Service;
import tw.gov.pcc.eip.util.ObjectUtility;

/**
 * 業務資訊
 * 
 * @author vita
 *
 */
@Controller
@Slf4j
public class Eip01w070Controller extends BaseController {

    public static final String CASE_KEY = "_eip01w070Controller_casekey";
    private static final String MAIN_PAGE = "/eip01w070/eip01w070q";

    @Autowired
    private UserBean userData;
    @Autowired
    private Eip01w070Service eip01w070Service;

    /**
     * 進入 業務資訊
     * 
     * @param caseData
     * @return
     */
    @RequestMapping("/Eip01w070_enter.action")
    public String enter(@ModelAttribute(CASE_KEY) Eip01w070Case caseData) {
        log.debug("導向 Eip01w070_enter 業務資訊 畫面初始");
        eip01w070Service.initOptions(caseData);
        return MAIN_PAGE;
    }

    /**
     * 透過ajax 查詢明細資料
     * 
     * @param fseq 項次
     * @return
     */
    @RequestMapping("/Eip01w070_getDetail.action")
    @ResponseBody
    public Eip01w070Case getDetail(@RequestParam("attr") String attr) {
        log.debug("導向 Eip01w070_getDetail 業務資訊 明細查詢");
        return ObjectUtility.normalizeObject(eip01w070Service.getContent(attr));
    }
}
