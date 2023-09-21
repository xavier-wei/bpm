package tw.gov.pcc.eip.adm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import tw.gov.pcc.eip.adm.cases.Eip00w010Case;
import tw.gov.pcc.eip.services.Eip00w010Service;
import tw.gov.pcc.eip.framework.spring.controllers.BaseController;
import tw.gov.pcc.eip.util.ExceptionUtility;
import tw.gov.pcc.eip.util.ObjectUtility;

/**
 * 設定系統管理員
 *
 * @author ivan
 */
@Controller
@SessionAttributes({Eip00w010Controller.CASE_KEY})
public class Eip00w010Controller extends BaseController {
    public static final String CASE_KEY = "_eip00w010_caseData";
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(Eip00w010Controller.class);
    private static final String QUERY_PAGE = "/eip00w010/eip00w010q";//選擇頁

    @Autowired
    private Eip00w010Service eipadm0w010Service;
 
    @ModelAttribute(CASE_KEY)
    public Eip00w010Case getEipadm0w010Case() {
    	Eip00w010Case caseData = new Eip00w010Case();
        return ObjectUtility.normalizeObject(caseData);
    }

    /**
     * 進入頁面
     *
     * @return
     */
    @RequestMapping("/Eip00w010_enter.action")
    public ModelAndView enter(@ModelAttribute(CASE_KEY) Eip00w010Case caseData) {
        log.debug("導向 adm0w010_enter 設定系統管理員");
        caseData.setUserList(eipadm0w010Service.findAdminUserList());   
        return new ModelAndView(QUERY_PAGE);
    }
    

    /**
     * 刪除管理員
     *
     * @return
     */
    @RequestMapping("/Eip00w010_delete.action")
    public ModelAndView delete(@ModelAttribute(CASE_KEY) Eip00w010Case caseData, BindingResult bindingResult) {
          
        try {
        	eipadm0w010Service.deleteValidate(caseData,bindingResult);
        	if(bindingResult.hasErrors()) {
        		return new ModelAndView(QUERY_PAGE);
        	}else {
        		eipadm0w010Service.deleteAdmin(caseData,bindingResult);
        		return enter(caseData);
        	}
        	
		} catch (Exception e) {
			log.error("刪除失敗 - {}", ExceptionUtility.getStackTrace(e));
			return new ModelAndView(QUERY_PAGE);
		}
    }
    
    /**
     * 新增管理員
     *
     * @return
     */
    @RequestMapping("/Eip00w010_add.action")
    public ModelAndView add(@ModelAttribute(CASE_KEY) Eip00w010Case caseData, BindingResult bindingResult) {
          
        try {
        	eipadm0w010Service.insertValidate(caseData,bindingResult);
        	if(bindingResult.hasErrors()) {
        		return new ModelAndView(QUERY_PAGE);
        	}else {
        		eipadm0w010Service.insertAdmin(caseData);
        		return enter(caseData);
        	}
		} catch (Exception e) {
			log.error("查詢失敗 - {}", ExceptionUtility.getStackTrace(e));
			return new ModelAndView(QUERY_PAGE);
		}
    }


}
