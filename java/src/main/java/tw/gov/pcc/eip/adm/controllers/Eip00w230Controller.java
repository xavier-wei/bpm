package tw.gov.pcc.eip.adm.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import tw.gov.pcc.eip.adm.cases.Eip00w230Case;
import tw.gov.pcc.eip.framework.spring.controllers.BaseController;
import tw.gov.pcc.eip.services.Eip00w230Service;
import tw.gov.pcc.eip.util.ExceptionUtility;
import tw.gov.pcc.eip.util.ObjectUtility;

/**
 * 單位角色維護
 *
 * @author ivan
 */
@Controller
@SessionAttributes({Eip00w230Controller.CASE_KEY})
@Slf4j
public class Eip00w230Controller extends BaseController {
    public static final String CASE_KEY = "_eip00w230_caseData";
    private static final String EDIT_PAGE = "/eip00w230/eip00w230q";//編輯隸屬頁

    @Autowired
    private Eip00w230Service eip00w230Service;

    @ModelAttribute(CASE_KEY)
    public Eip00w230Case getEip00w230Case() {
        Eip00w230Case caseData = new Eip00w230Case();
        return ObjectUtility.normalizeObject(caseData);
    }

    /**
     * 進入頁面
     *
     * @param caseData case
     * @return modelAndView
     */
    @RequestMapping("/Eip00w230_enter.action")
    public ModelAndView enter(@ModelAttribute(CASE_KEY) Eip00w230Case caseData) {
        try {
            log.debug("導向 Eip00w230_query 設定個人儀表版");
            eip00w230Service.findCheckedList(caseData);
        } catch (Exception e) {
            log.error("查詢失敗 - {}", ExceptionUtility.getStackTrace(e));
            setSystemMessage(getQueryFailMessage());
        }

        return new ModelAndView(EDIT_PAGE);

    }

    /**
     * 更新大數據儀表板
     *
     * @param caseData case
     * @return modelAndView
     */
    @RequestMapping("/Eip00w230_save.action")
    public ModelAndView save(@ModelAttribute(CASE_KEY) Eip00w230Case caseData) {
        try {
            eip00w230Service.deleteAndInsertPwctab(caseData);
            setSystemMessage(getUpdateSuccessMessage());
        } catch (Exception e) {
            log.error("更新失敗 - {}", ExceptionUtility.getStackTrace(e));
            setSystemMessage(getUpdateFailMessage());
        }
        return enter(caseData);
    }


}
