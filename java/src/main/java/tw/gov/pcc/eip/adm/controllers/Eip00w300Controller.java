package tw.gov.pcc.eip.adm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import tw.gov.pcc.eip.MessageKey;
import tw.gov.pcc.eip.adm.cases.Eip00w300Case;
import tw.gov.pcc.eip.services.Eip00w300Service;
import tw.gov.pcc.eip.domain.User_auth_dept;
import tw.gov.pcc.eip.framework.spring.controllers.BaseController;
import tw.gov.pcc.eip.util.ExceptionUtility;
import tw.gov.pcc.eip.util.ObjectUtility;

import java.util.Map;

/**
 * 授權審核部門設定
 *
 * @author ivan
 */
@Controller
@SessionAttributes({Eip00w300Controller.CASE_KEY})
public class Eip00w300Controller extends BaseController {
    public static final String CASE_KEY = "_eip00w300_caseData";
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(Eip00w300Controller.class);
    private static final String QUERY_PAGE = "/eip00w300/eip00w300q";//選擇頁
    private static final String EDIT_PAGE = "/eip00w300/eip00w300x";//修改頁
    private static final String ADD_PAGE = "/eip00w300/eip00w300a";//新增頁


    @Autowired
    private Eip00w300Service eipadm0w300Service;
 
    @ModelAttribute(CASE_KEY)
    public Eip00w300Case getEipadm0w300Case() {
    	Eip00w300Case caseData = new Eip00w300Case();
        return ObjectUtility.normalizeObject(caseData);
    }

    /**
     * 進入頁面
     *
     * @return
     */
    @RequestMapping("/Eip00w300_enter.action")
    public ModelAndView enter(@ModelAttribute(CASE_KEY) Eip00w300Case caseData) {
        log.debug("導向 Eip00w300_enter 授權審核部門設定");
        eipadm0w300Service.initcase(caseData);
		caseData.setUser_auth_deptList(eipadm0w300Service.queryUser_auth_dept(caseData.getUser_id(), caseData.getDept_id()));
        return new ModelAndView(QUERY_PAGE);
    }
    
    /**
     * 進入頁面查詢
     *
     * @return
     */
    @RequestMapping("/Eip00w300_query.action")
    public ModelAndView query(@ModelAttribute(CASE_KEY) Eip00w300Case caseData) {
        try {
        	caseData.setUser_auth_deptList(eipadm0w300Service.queryUser_auth_dept(caseData.getUser_id(), caseData.getDept_id()));
			return new ModelAndView(QUERY_PAGE);
		} catch (Exception e) {
			log.error("查詢失敗 - {}", ExceptionUtility.getStackTrace(e));
			setSystemMessage(getQueryFailMessage());
			return new ModelAndView(QUERY_PAGE);
		}
    }
   
    /**
     * 進入頁面
     *
     * @return
     */
    @RequestMapping("/Eip00w300_back.action")
    public ModelAndView back(@ModelAttribute(CASE_KEY) Eip00w300Case eipadm0w300Case) {
        return enter(eipadm0w300Case);
    }
    
    /**
     * 新增畫面
     *
     * @return
     */
	@RequestMapping("/Eip00w300_add.action")
	public ModelAndView add(@ModelAttribute(CASE_KEY) Eip00w300Case eipadm0w300Case) {
		try {
			eipadm0w300Service.initOptions(eipadm0w300Case);
			return new ModelAndView(ADD_PAGE);
		} catch (Exception e) {
			log.error("新增畫面失敗 - {}", ExceptionUtility.getStackTrace(e));
			setSystemMessage(getQueryFailMessage());
			return new ModelAndView(QUERY_PAGE);
		}
	}

    /**
     * 新增資料
     *
     * @return
     */
	@RequestMapping("/Eip00w300_insert.action")
	public ModelAndView insert(@Validated @ModelAttribute(CASE_KEY) Eip00w300Case eipadm0w300Case, BindingResult bindingResult) {
		try {
			//驗證
			eipadm0w300Service.validInsert(eipadm0w300Case, bindingResult);
			if(bindingResult.hasErrors()) {
				return new ModelAndView(ADD_PAGE);
			}
			//新增
			eipadm0w300Service.insertUser_auth_dept(eipadm0w300Case);
			setSystemMessage(getSaveSuccessMessage());
			return enter(eipadm0w300Case);
		} catch (Exception e) {
			log.error("新增失敗 - {}", ExceptionUtility.getStackTrace(e));
			setSystemMessage(getSaveFailMessage());
			return new ModelAndView(QUERY_PAGE);
		}
	}	

    /**
     * 刪除資料
     *
     * @return
     */
	@RequestMapping("/Eip00w300_delete.action")
	public ModelAndView delete(@Validated @ModelAttribute(CASE_KEY) Eip00w300Case eipadm0w300Case, BindingResult bindingResult) {
		try {
			//刪除
			eipadm0w300Service.deleteDepts(eipadm0w300Case);
			setSystemMessage(getDeleteSuccessMessage());
			return enter(eipadm0w300Case);
		} catch (Exception e) {
			log.error("刪除失敗 - {}", ExceptionUtility.getStackTrace(e));
			setSystemMessage(getDeleteFailMessage());
			return new ModelAndView(QUERY_PAGE);
		}
	}

	@RequestMapping("/Eip00w300_getUsersData.action")
	@ResponseBody
	public Map<String, String> getUsersData(@RequestParam("deptid") String deptid) {
		log.info("透過ajax 依畫面選擇的部門 查詢使用者選單");
		return ObjectUtility.normalizeObject(eipadm0w300Service.getUsers(deptid));
	}

}
