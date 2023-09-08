package tw.gov.pcc.eip.orderCar.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import tw.gov.pcc.eip.framework.spring.controllers.BaseController;
import tw.gov.pcc.eip.orderCar.cases.Eip07w060Case;
import tw.gov.pcc.eip.services.Eip07w060Service;
import tw.gov.pcc.eip.util.BeanUtility;
import tw.gov.pcc.eip.util.ExceptionUtility;
import tw.gov.pcc.eip.util.ObjectUtility;

/**
 * 駕駛鍵入里程表作業
 *
 * @author Ivan
 */
@Controller
@SessionAttributes({ Eip07w060Controller.CASE_KEY })
public class Eip07w060Controller extends BaseController {
	public static final String CASE_KEY = "_eip07w060_caseData";
	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(Eip07w060Controller.class);
	private static final String QUERY_PAGE = "/eip07w060/eip07w060q";// 選擇頁
	private static final String DATA_PAGE = "/eip07w060/eip07w060x";// 審核頁

	@Autowired
	private Eip07w060Service eip07w060Service;

	@ModelAttribute(CASE_KEY)
	public Eip07w060Case getEip07w060Case() {
		Eip07w060Case caseData = new Eip07w060Case();
		return ObjectUtility.normalizeObject(caseData);
	}

	/**
	 * 進入頁面
	 *
	 * @return
	 */
	@RequestMapping("/Eip07w060_enter.action")
	public ModelAndView enter(@ModelAttribute(CASE_KEY) Eip07w060Case caseData) {
		log.debug("導向 Eip07w060Case_enter 駕駛鍵入里程表作業");
		eip07w060Service.initCase(caseData);
		return new ModelAndView(QUERY_PAGE);
	}

	/**
	 * 查詢資料
	 *
	 * @return
	 */
	@RequestMapping("/Eip07w060_query.action")
	public ModelAndView queryData(@ModelAttribute(CASE_KEY) Eip07w060Case caseData, BindingResult bindingResult) {
		log.debug("導向 Eip07w060_query 查詢資料");

		try {
			eip07w060Service.validQuery(caseData, bindingResult);
			if(bindingResult.hasErrors()) {
				return new ModelAndView(QUERY_PAGE);
			}
			
			eip07w060Service.setData(caseData);
			return new ModelAndView(DATA_PAGE);
		} catch (Exception e) {
			log.error("Eip07w060Controller查詢失敗" + ExceptionUtility.getStackTrace(e));
			setSystemMessage(getQueryFailMessage());
			return new ModelAndView(QUERY_PAGE);
		}
		
	}
	
	/**
	 * 更新資料
	 *
	 * @return
	 */
	@RequestMapping("/Eip07w060_update.action")
	public ModelAndView updateData(@ModelAttribute(CASE_KEY) Eip07w060Case caseData, BindingResult bindingResult) {
		log.debug("導向 Eip07w060_update 更新資料");

		try {
			eip07w060Service.validUpdate(caseData, bindingResult);
			if(bindingResult.hasErrors()) {
				return new ModelAndView(DATA_PAGE);
			}
			
			eip07w060Service.updData(caseData);
			setSystemMessage(getUpdateSuccessMessage());
			return enter(caseData);
		} catch (Exception e) {
			log.error("Eip07w060Controller查詢失敗" + ExceptionUtility.getStackTrace(e));
			setSystemMessage(getUpdateFailMessage());
			return enter(caseData);
		}
		
	}
	

}