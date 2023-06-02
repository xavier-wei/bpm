package tw.gov.pcc.eip.adm.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import tw.gov.pcc.eip.MessageKey;
import tw.gov.pcc.eip.adm.cases.Eip00w030Case;
import tw.gov.pcc.eip.adm.service.Eip00w030Service;
import tw.gov.pcc.eip.domain.Systems;
import tw.gov.pcc.eip.framework.domain.UserBean;
import tw.gov.pcc.eip.framework.spring.controllers.BaseController;
import tw.gov.pcc.eip.util.ExceptionUtility;
import tw.gov.pcc.eip.util.ObjectUtility;

/**
 * 系統管理
 *
 * @author ivan
 */
@Controller
@SessionAttributes({Eip00w030Controller.CASE_KEY})
public class Eip00w030Controller extends BaseController {
    public static final String CASE_KEY = "_eip00w030_caseData";
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(Eip00w030Controller.class);
    private static final String QUERY_PAGE = "/eip00w030/eip00w030q";//選擇頁
    private static final String EDIT_PAGE = "/eip00w030/eip00w030x";//修改頁
    private static final String ADD_PAGE = "/eip00w030/eip00w030a";//選擇頁

    @Autowired
    private UserBean userData;
    @Autowired
    private Eip00w030Service eipadm0w030Service;
 
    @ModelAttribute(CASE_KEY)
    public Eip00w030Case getEipadm0w010Case() {
    	Eip00w030Case caseData = new Eip00w030Case();
        return ObjectUtility.normalizeObject(caseData);
    }

    /**
     * 進入頁面
     *
     * @return
     */
    @RequestMapping("/Eip00w030_enter.action")
    public ModelAndView enter(@ModelAttribute(CASE_KEY) Eip00w030Case caseData) {
        log.debug("導向 adm0w030_enter 系統管理");
        eipadm0w030Service.initCase(caseData);
        eipadm0w030Service.settingSystemList(caseData);
        return new ModelAndView(QUERY_PAGE);
    }
    
    /**
     * 進入頁面
     *
     * @return
     */
    @RequestMapping("/Eip00w030_query.action")
    public ModelAndView query(@ModelAttribute(CASE_KEY) Eip00w030Case caseData) {
        try {
			eipadm0w030Service.settingSystemList(caseData);
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
    @RequestMapping("/Eip00w030_back.action")
    public ModelAndView back(@ModelAttribute(CASE_KEY) Eip00w030Case caseData) {
        return enter(caseData);
    }
    
    /**
     * 新增畫面
     *
     * @return
     */
	@RequestMapping("/Eip00w030_add.action")
	public ModelAndView add(@ModelAttribute(CASE_KEY) Eip00w030Case eipadm0w030Case) {
		try {
			return new ModelAndView(ADD_PAGE);
		} catch (Exception e) {
			log.error("查詢失敗 - {}", ExceptionUtility.getStackTrace(e));
			setSystemMessage(getQueryFailMessage());
			return new ModelAndView(QUERY_PAGE);
		}
	}
    
    /**
     * 修改畫面
     *
     * @return
     */
	@RequestMapping("/Eip00w030_edit.action")
	public ModelAndView edit(@ModelAttribute(CASE_KEY) Eip00w030Case eipadm0w030Case) {
		try {
			Systems systems = eipadm0w030Service.selectSystemDataBySysid(eipadm0w030Case);
			if(systems != null) {
				eipadm0w030Case.setQuerySystems(systems);
				return new ModelAndView(EDIT_PAGE);
			}else {
				setSystemMessage(getMessage(MessageKey.NODATA));
				return new ModelAndView(QUERY_PAGE);
			}
			
		} catch (Exception e) {
			log.error("查詢失敗 - {}", ExceptionUtility.getStackTrace(e));
			setSystemMessage(getQueryFailMessage());
			return new ModelAndView(QUERY_PAGE);
		}
	}
	
    /**
     * 新增資料
     *
     * @return
     */
	@RequestMapping("/Eip00w030_insert.action")
	public ModelAndView insert(@Validated @ModelAttribute(CASE_KEY) Eip00w030Case eipadm0w030Case, BindingResult bindingResult) {
		try {
			eipadm0w030Service.validInsert(eipadm0w030Case, bindingResult);
			if(bindingResult.hasErrors()) {
				return new ModelAndView(ADD_PAGE);
			}
			
			eipadm0w030Service.insertSystem(eipadm0w030Case);
			setSystemMessage(getSaveSuccessMessage());
			return enter(eipadm0w030Case);
		} catch (Exception e) {
			log.error("新增失敗 - {}", ExceptionUtility.getStackTrace(e));
			setSystemMessage(getQueryFailMessage());
			return new ModelAndView(QUERY_PAGE);
		}
	}	
	
    /**
     * 更新資料
     *
     * @return
     */
	@RequestMapping("/Eip00w030_update.action")
	public ModelAndView update(@Validated @ModelAttribute(CASE_KEY) Eip00w030Case eipadm0w030Case, BindingResult bindingResult) {
		try {
			eipadm0w030Service.validInsert(eipadm0w030Case, bindingResult);
			if(bindingResult.hasErrors()) {
				return new ModelAndView(EDIT_PAGE);
			}
			
			eipadm0w030Service.updateSystem(eipadm0w030Case);
			setSystemMessage(getSaveSuccessMessage());
			return enter(eipadm0w030Case);
		} catch (Exception e) {
			log.error("更新失敗 - {}", ExceptionUtility.getStackTrace(e));
			setSystemMessage(getQueryFailMessage());
			return new ModelAndView(QUERY_PAGE);
		}
	}	
	
	
    /**
     * 刪除資料
     *
     * @return
     */
	@RequestMapping("/Eip00w030_delete.action")
	public ModelAndView delete(@Validated @ModelAttribute(CASE_KEY) Eip00w030Case eipadm0w030Case) {
		try {

			eipadm0w030Service.deleteSystem(eipadm0w030Case);
			setSystemMessage(getDeleteSuccessMessage());
			return new ModelAndView(QUERY_PAGE);
		} catch (Exception e) {
			log.error("刪除失敗 - {}", ExceptionUtility.getStackTrace(e));
			setSystemMessage(getQueryFailMessage());
			return new ModelAndView(QUERY_PAGE);
		}
	}
	


}
