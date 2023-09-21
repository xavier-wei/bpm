package tw.gov.pcc.eip.msg.controllers;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;
import tw.gov.pcc.eip.framework.domain.UserBean;
import tw.gov.pcc.eip.framework.spring.controllers.BaseController;
import tw.gov.pcc.eip.msg.cases.Eip01w020Case;
import tw.gov.pcc.eip.services.Eip01w020Service;
import tw.gov.pcc.eip.util.ExceptionUtility;

/**
 * 訊息篇數統計
 * 
 * @author vita
 *
 */
@Controller
@Slf4j
public class Eip01w020Controller extends BaseController {

    public static final String CASE_KEY = "_eip01w020Controller_casekey";
    private static final String MAIN_PAGE = "/eip01w020/eip01w020q";

    @Autowired
    private UserBean userData;
    @Autowired
    private Eip01w020Service eip01w020Service;

    @ModelAttribute(CASE_KEY)
    public Eip01w020Case getTsif0w710Case() {
        Eip01w020Case caseData = new Eip01w020Case();
        eip01w020Service.initOptions(caseData);
        return caseData;
    }

    /**
     * 進入頁面
     *
     * @return
     */
    @RequestMapping("/Eip01w020_enter.action")
    public String enter() {
        log.debug("導向 Eip01w020_enter 訊息篇數統計");
        return MAIN_PAGE;
    }

    /**
     * 取得統計資料
     * 
     * @param caseData
     * @return
     */
    @RequestMapping("/Eip01w020_count.action")
    public ModelAndView count(@ModelAttribute(CASE_KEY) Eip01w020Case caseData) {
        log.debug("導向 Eip01w020_enter 訊息篇數統計");
        eip01w020Service.initOptions(caseData);
        try {
            eip01w020Service.getStatistics(caseData);
            if (!CollectionUtils.isEmpty(caseData.getQryList())) {
                setSystemMessage(getQuerySuccessMessage());
            } else {
                setSystemMessage(getQueryEmptyMessage());
            }
        } catch (Exception e) {
            log.error("訊息篇數統計 - " + ExceptionUtility.getStackTrace(e));
            setSystemMessage(getQueryFailMessage());
        }
        return new ModelAndView(MAIN_PAGE);
    }
}
