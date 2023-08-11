package tw.gov.pcc.eip.adm.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import tw.gov.pcc.eip.adm.cases.Eip00w210Case;
import tw.gov.pcc.eip.framework.spring.controllers.BaseController;
import tw.gov.pcc.eip.services.Eip00w210Service;
import tw.gov.pcc.eip.util.ExceptionUtility;
import tw.gov.pcc.eip.util.ObjectUtility;

import java.util.Arrays;

/**
 * 網站導覽管理
 *
 * @author swho
 */
@Controller
@SessionAttributes({Eip00w210Controller.CASE_KEY})
@AllArgsConstructor
@Slf4j
public class Eip00w210Controller extends BaseController {
    public static final String CASE_KEY = "_eip00w210_caseData";
    private static final String QUERY_PAGE = "/eip00w210/eip00w210q";//選擇頁
    private Eip00w210Service eip00w210Service;

    @ModelAttribute(CASE_KEY)
    public Eip00w210Case getEip00w210Case() {
        Eip00w210Case caseData = new Eip00w210Case();
        caseData.setLevelList(Arrays.asList(Pair.of("一層", "1"), Pair.of("二層", "2")));
        caseData.setLevel(eip00w210Service.getLevel());
        return ObjectUtility.normalizeObject(caseData);
    }

    /**
     * 進入頁面
     *
     * @return
     */
    @RequestMapping("/Eip00w210_enter.action")
    public ModelAndView enter(@ModelAttribute(CASE_KEY) Eip00w210Case caseData) {
        return new ModelAndView(QUERY_PAGE);
    }

    /**
     * 存檔
     *
     * @return
     */
    @RequestMapping("/Eip00w210_save.action")
    public ModelAndView save(@ModelAttribute(CASE_KEY) Eip00w210Case caseData, BindingResult result) {
        try {
            if (result.hasErrors()) {
                return new ModelAndView(QUERY_PAGE);
            }
            eip00w210Service.saveLevel(caseData.getLevel());
            setSystemMessage(getUpdateSuccessMessage());
        } catch (Exception e) {
            log.error("儲存失敗 - {}", ExceptionUtility.getStackTrace(e));
            setSystemMessage(getUpdateFailMessage());
        }
        return new ModelAndView(QUERY_PAGE);
    }
}
