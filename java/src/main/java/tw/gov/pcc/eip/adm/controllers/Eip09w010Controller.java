package tw.gov.pcc.eip.adm.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import tw.gov.pcc.eip.adm.cases.Eip09w010Case;
import tw.gov.pcc.eip.framework.spring.controllers.BaseController;
import tw.gov.pcc.eip.services.Eip09w010Service;
import tw.gov.pcc.eip.util.ExceptionUtility;
import tw.gov.pcc.eip.util.ObjectUtility;

/**
 * 單位角色維護
 *
 * @author ivan
 */
@Controller
@SessionAttributes({Eip09w010Controller.CASE_KEY})
@Slf4j
public class Eip09w010Controller extends BaseController {
    public static final String CASE_KEY = "_eip09w010_caseData";
    private static final String EDIT_PAGE = "/eip09w010/eip09w010q";//編輯隸屬頁

    @Autowired
    private Eip09w010Service eip09w010Service;

    @ModelAttribute(CASE_KEY)
    public Eip09w010Case getEip09w010Case() {
        Eip09w010Case caseData = new Eip09w010Case();
        return ObjectUtility.normalizeObject(caseData);
    }

    /**
     * 進入頁面
     *
     * @param caseData case
     * @return modelAndView
     */
    @RequestMapping("/Eip09w010_enter.action")
    public ModelAndView enter(@ModelAttribute(CASE_KEY) Eip09w010Case caseData) {
        try {
            log.debug("導向 Eip09w010_query 設定個人儀表版");
            eip09w010Service.findCheckedList(caseData);
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
    @RequestMapping("/Eip09w010_save.action")
    public ModelAndView save(@ModelAttribute(CASE_KEY) Eip09w010Case caseData) {
        try {
            eip09w010Service.deleteAndInsertPwctab(caseData);
            setSystemMessage(getUpdateSuccessMessage());
        } catch (Exception e) {
            log.error("更新失敗 - {}", ExceptionUtility.getStackTrace(e));
            setSystemMessage(getUpdateFailMessage());
        }
        return enter(caseData);
    }


}
