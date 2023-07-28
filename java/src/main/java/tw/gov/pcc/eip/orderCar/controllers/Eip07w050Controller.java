package tw.gov.pcc.eip.orderCar.controllers;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import tw.gov.pcc.eip.apply.cases.Eip08w030Case;
import tw.gov.pcc.eip.framework.spring.controllers.BaseController;
import tw.gov.pcc.eip.orderCar.cases.Eip07w050Case;
import tw.gov.pcc.eip.services.Eip07w050Service;
import tw.gov.pcc.eip.util.BeanUtility;
import tw.gov.pcc.eip.util.DateUtility;
import tw.gov.pcc.eip.util.ExceptionUtility;
import tw.gov.pcc.eip.util.ObjectUtility;

/**
 * 秘書處主管核派作業
 *
 * @author Ivy
 */
@Controller
@SessionAttributes({ Eip07w050Controller.CASE_KEY })
public class Eip07w050Controller extends BaseController {
	public static final String CASE_KEY = "_eip07w050_caseData";
	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(Eip07w050Controller.class);
	private static final String QUERY_PAGE = "/eip07w050/eip07w050q";// 選擇頁
	private static final String DATA_PAGE = "/eip07w050/eip07w050x";// 審核頁

	@Autowired
	private Eip07w050Service eip07w050Service;

	@ModelAttribute(CASE_KEY)
	public Eip07w050Case getEip07w050Case() {
		Eip07w050Case caseData = new Eip07w050Case();
		return ObjectUtility.normalizeObject(caseData);
	}

	/**
	 * 進入頁面
	 *
	 * @return
	 */
	@RequestMapping("/Eip07w050_enter.action")
	public ModelAndView enter(@ModelAttribute(CASE_KEY) Eip07w050Case caseData) {
		log.debug("導向 Eip07w050Case_enter 秘書處主管核派作業");
		Eip07w050Case newCase = new Eip07w050Case();
		BeanUtility.copyProperties(caseData, newCase);// 進來時清除caseData
		return new ModelAndView(QUERY_PAGE);
	}

	/**
	 * 查詢資料
	 *
	 * @return
	 */
	@RequestMapping("/Eip07w050_query.action")
	public ModelAndView queryData(@ModelAttribute(CASE_KEY) Eip07w050Case caseData) {
		log.debug("導向 Eip07w050_query 查詢資料");

		try {
			eip07w050Service.getData(caseData);
			if(CollectionUtils.isEmpty(caseData.getDataList())) {
				setSystemMessage("查無資料");
			} else {
				return new ModelAndView(DATA_PAGE);
			}
		} catch (Exception e) {
			log.error("Eip07w050Controller查詢失敗" + ExceptionUtility.getStackTrace(e));
			setSystemMessage("查詢失敗");
		}
		
		return new ModelAndView(DATA_PAGE);
	}
	
	/**
	 * 秘書處主管核派作業 查詢畫面
	 *
	 * @param caseData
	 * @param result
	 * @return
	 */
	@RequestMapping("/Eip07w050_update.action")
	public String updateData(@Validated(Eip08w030Case.Update.class) @ModelAttribute(CASE_KEY) Eip07w050Case caseData, BindingResult result) {
		log.debug("導向   Eip07w050  秘書處主管核派作業：更新資料 ");
		if (result.hasErrors()) {
			return DATA_PAGE;
		}

		try {
			eip07w050Service.updateAll(caseData);
			eip07w050Service.getData(caseData);
			if(CollectionUtils.isNotEmpty(caseData.getDataList())) {//若同一時間區間仍有未複合的案件則返回資料列表頁
				setSystemMessage("複核成功");
				return DATA_PAGE;
			}
		} catch (Exception e) {
			log.error("Eip07w050Controller update失敗" + ExceptionUtility.getStackTrace(e));
			setSystemMessage("複核失敗");
			return DATA_PAGE;
		}
		Eip08w030Case newCase = new Eip08w030Case();
		BeanUtility.copyProperties(caseData, newCase);// 進來時清除caseData
		caseData.setApplydateStart(DateUtility.getNowChineseDate());
		setSystemMessage("複核成功");
		return QUERY_PAGE;
	}

}