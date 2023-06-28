package tw.gov.pcc.eip.adm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import tw.gov.pcc.eip.MessageKey;
import tw.gov.pcc.eip.adm.cases.Eip00w040Case;
import tw.gov.pcc.eip.adm.service.Eip00w040Service;
import tw.gov.pcc.eip.domain.Depts;
import tw.gov.pcc.eip.domain.Items;
import tw.gov.pcc.eip.framework.spring.controllers.BaseController;
import tw.gov.pcc.eip.util.ExceptionUtility;
import tw.gov.pcc.eip.util.ObjectUtility;

/**
 * 部門管理
 *
 * @author ivan
 */
@Controller
@SessionAttributes({Eip00w040Controller.CASE_KEY})
public class Eip00w040Controller extends BaseController {
    public static final String CASE_KEY = "_eip00w040_caseData";
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(Eip00w040Controller.class);
    private static final String QUERY_PAGE = "/eip00w040/eip00w040q";//選擇頁
    private static final String EDIT_PAGE = "/eip00w040/eip00w040x";//修改頁
    private static final String ADD_PAGE = "/eip00w040/eip00w040a";//新增頁
    private static final String DETAIL_PAGE = "/eip00w040/eip00w041x";//新增頁


    @Autowired
    private Eip00w040Service eipadm0w040Service;
 
    @ModelAttribute(CASE_KEY)
    public Eip00w040Case getEipadm0w010Case() {
    	Eip00w040Case caseData = new Eip00w040Case();
        return ObjectUtility.normalizeObject(caseData);
    }

    /**
     * 進入頁面
     *
     * @return
     */
    @RequestMapping("/Eip00w040_enter.action")
    public ModelAndView enter(@ModelAttribute(CASE_KEY) Eip00w040Case caseData) {
        log.debug("導向 Eip00w040_enter 部門管理");
        eipadm0w040Service.initcase(caseData);
        caseData.setDeptList(eipadm0w040Service.queryDepts(caseData.getDept_id()));
        return new ModelAndView(QUERY_PAGE);
    }
    
    /**
     * 進入頁面查詢
     *
     * @return
     */
    @RequestMapping("/Eip00w040_query.action")
    public ModelAndView query(@ModelAttribute(CASE_KEY) Eip00w040Case caseData) {
        try {
        	caseData.setDeptList(eipadm0w040Service.queryDepts(caseData.getDept_id()));
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
    @RequestMapping("/Eip00w040_back.action")
    public ModelAndView back(@ModelAttribute(CASE_KEY) Eip00w040Case eipadm0w040Case) {
        return enter(eipadm0w040Case);
    }
    
    /**
     * 新增畫面
     *
     * @return
     */
	@RequestMapping("/Eip00w040_add.action")
	public ModelAndView add(@ModelAttribute(CASE_KEY) Eip00w040Case eipadm0w040Case) {
		try {
			return new ModelAndView(ADD_PAGE);
		} catch (Exception e) {
			log.error("新增畫面失敗 - {}", ExceptionUtility.getStackTrace(e));
			setSystemMessage(getQueryFailMessage());
			return new ModelAndView(QUERY_PAGE);
		}
	}
    
    /**
     * 修改畫面
     *
     * @return
     */
	@RequestMapping("/Eip00w040_edit.action")
	public ModelAndView edit(@ModelAttribute(CASE_KEY) Eip00w040Case eipadm0w040Case) {
		try {
			Depts depts = eipadm0w040Service.queryDepts(eipadm0w040Case.getDept_id()).get(0);
			if(depts != null) {
				eipadm0w040Service.buildEditCaseData(eipadm0w040Case, depts);
				return new ModelAndView(EDIT_PAGE);
			}else {
				setSystemMessage(getMessage(MessageKey.NODATA));
				return new ModelAndView(QUERY_PAGE);
			}
			
		} catch (Exception e) {
			log.error("修改畫面失敗 - {}", ExceptionUtility.getStackTrace(e));
			setSystemMessage(getQueryFailMessage());
			return new ModelAndView(QUERY_PAGE);
		}
	}
	
    /**
     * 修改畫面
     *
     * @return
     */
	@RequestMapping("/Eip00w040_detail.action")
	public ModelAndView detailquery(@ModelAttribute(CASE_KEY) Eip00w040Case eipadm0w040Case) {
		try {
			Depts depts = eipadm0w040Service.queryDepts(eipadm0w040Case.getDept_id()).get(0);
			if(depts != null) {
				eipadm0w040Case.setQueryDepts(depts);
				return new ModelAndView(DETAIL_PAGE);
			}else {
				setSystemMessage(getMessage(MessageKey.NODATA));
				return new ModelAndView(QUERY_PAGE);
			}
			
		} catch (Exception e) {
			log.error("修改畫面失敗 - {}", ExceptionUtility.getStackTrace(e));
			setSystemMessage(getQueryFailMessage());
			return new ModelAndView(QUERY_PAGE);
		}
	}
	
    /**
     * 新增資料
     *
     * @return
     */
	@RequestMapping("/Eip00w040_insert.action")
	public ModelAndView insert(@Validated @ModelAttribute(CASE_KEY) Eip00w040Case eipadm0w040Case, BindingResult bindingResult) {
		try {
			//驗證
			eipadm0w040Service.validInsert(eipadm0w040Case, bindingResult);
			if(bindingResult.hasErrors()) {
				return new ModelAndView(ADD_PAGE);
			}
			//新增
			eipadm0w040Service.insertDepts(eipadm0w040Case);
			setSystemMessage(getSaveSuccessMessage());
			return enter(eipadm0w040Case);
		} catch (Exception e) {
			log.error("新增失敗 - {}", ExceptionUtility.getStackTrace(e));
			setSystemMessage(getSaveFailMessage());
			return new ModelAndView(QUERY_PAGE);
		}
	}	
	
    /**
     * 更新資料
     *
     * @return
     */
	@RequestMapping("/Eip00w040_update.action")
	public ModelAndView update(@Validated @ModelAttribute(CASE_KEY) Eip00w040Case eipadm0w040Case, BindingResult bindingResult) {
		try {
			//驗證
			eipadm0w040Service.validUpdate(eipadm0w040Case, bindingResult);
			if(bindingResult.hasErrors()) {
				return new ModelAndView(EDIT_PAGE);
			}
			//更新
			eipadm0w040Service.updateDepts(eipadm0w040Case);
			setSystemMessage(getUpdateSuccessMessage());
			return enter(eipadm0w040Case);
		} catch (Exception e) {
			log.error("更新失敗 - {}", ExceptionUtility.getStackTrace(e));
			setSystemMessage(getUpdateFailMessage());
			return new ModelAndView(QUERY_PAGE);
		}
	}	
	
    /**
     * 更新資料
     *
     * @return
     */
	@RequestMapping("/Eip00w040_delete.action")
	public ModelAndView delete(@Validated @ModelAttribute(CASE_KEY) Eip00w040Case eipadm0w040Case, BindingResult bindingResult) {
		try {
			//驗證
			eipadm0w040Service.validDelete(eipadm0w040Case, bindingResult);
			if(bindingResult.hasErrors()) {
				return new ModelAndView(EDIT_PAGE);
			}
			//更新
			eipadm0w040Service.deleteDepts(eipadm0w040Case);
			setSystemMessage(getDeleteSuccessMessage());
			return enter(eipadm0w040Case);
		} catch (Exception e) {
			log.error("刪除失敗 - {}", ExceptionUtility.getStackTrace(e));
			setSystemMessage(getDeleteFailMessage());
			return new ModelAndView(QUERY_PAGE);
		}
	}	

}
