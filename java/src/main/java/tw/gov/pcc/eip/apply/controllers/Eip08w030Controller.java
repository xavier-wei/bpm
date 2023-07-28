package tw.gov.pcc.eip.apply.controllers;

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
import tw.gov.pcc.eip.services.Eip08w030Service;
import tw.gov.pcc.eip.util.BeanUtility;
import tw.gov.pcc.eip.util.DateUtility;
import tw.gov.pcc.eip.util.ExceptionUtility;
import tw.gov.pcc.eip.util.ObjectUtility;

/**
 * 領物單申請複核作業Controller
 *
 * @author Ivy
 */
@Controller
@SessionAttributes({ Eip08w030Controller.CASE_KEY })
public class Eip08w030Controller extends BaseController {
	public static final String CASE_KEY = "_Eip08w030_caseData";
	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(Eip08w030Controller.class);
	private static final String QUERY_PAGE = "/eip08w030/eip08w030q";// 選擇頁
	private static final String LIST_APGE = "/eip08w030/eip08w030x";// 資料列表
	private static final String DETAIL_PAGE = "/eip08w030/eip08w330x";// 明細頁

	@Autowired
	private Eip08w030Service eip08w030Service;

	@ModelAttribute(CASE_KEY)
	public Eip08w030Case getRo0w100Case() {
		Eip08w030Case caseData = new Eip08w030Case();
		return ObjectUtility.normalizeObject(caseData);
	}

	/**
	 * 進入頁面 領物單申請複核作業
	 *
	 * @return
	 */
	@RequestMapping("/Eip08w030_enter.action")
	public ModelAndView enter(@ModelAttribute(CASE_KEY) Eip08w030Case caseData) {
		log.debug("導向 Eip08w030_enter 領物單申請作業");
		Eip08w030Case newCase = new Eip08w030Case();
		BeanUtility.copyProperties(caseData, newCase);// 進來時清除caseData
		caseData.setApplydateStart(DateUtility.getNowChineseDate());
		return new ModelAndView(QUERY_PAGE);
	}

	/**
	 * 領物單申請複核作業 查詢畫面
	 *
	 * @param caseData
	 * @param result
	 * @return
	 */
	@RequestMapping("/Eip08w030_query.action")
	public String query(@Validated(Eip08w030Case.Query.class) @ModelAttribute(CASE_KEY) Eip08w030Case caseData, BindingResult result) {
		log.debug("導向 Eip08w030 領物單申請作業畫面-查詢畫面");
		if (result.hasErrors()) {
			return QUERY_PAGE;
		}
		eip08w030Service.getCaseData(caseData);
		if(CollectionUtils.isEmpty(caseData.getDataList())) {
			setSystemMessage("查無資料");
			return QUERY_PAGE;
		} else {
			return LIST_APGE;
		}
	}
	
	/**
	 * 領物單申請複核作業 查詢畫面
	 *
	 * @param caseData
	 * @param result
	 * @return
	 */
	@RequestMapping("/Eip08w030_detail.action")
	public String detail(@Validated @ModelAttribute(CASE_KEY) Eip08w030Case caseData, BindingResult result) {
		log.debug("導向 Eip08w030 領物單申請複核作業 明細畫面");
		if (result.hasErrors()) {
			return LIST_APGE;
		}

		try {
			eip08w030Service.getApplyItemByApplyno(caseData);
		} catch (Exception e) {
			log.error("Eip08w030Controller查詢明細失敗" + ExceptionUtility.getStackTrace(e));
			setSystemMessage("查詢失敗");
			return LIST_APGE;
		}

		return DETAIL_PAGE;
	}
	
	/**
	 * 領物單申請複核作業 查詢畫面
	 *
	 * @param caseData
	 * @param result
	 * @return
	 */
	@RequestMapping("/Eip08w030_update.action")
	public String update(@Validated(Eip08w030Case.Update.class) @ModelAttribute(CASE_KEY) Eip08w030Case caseData, BindingResult result) {
		log.debug("導向   Eip08w020  領物單申請複核作業：更新資料 ");
		if (result.hasErrors()) {
			return LIST_APGE;
		}

		try {
			eip08w030Service.updateAll(caseData);
		} catch (Exception e) {
			log.error("Eip08w030Controller update失敗" + ExceptionUtility.getStackTrace(e));
			setSystemMessage("查詢失敗");
			return LIST_APGE;
		}
		Eip08w030Case newCase = new Eip08w030Case();
		BeanUtility.copyProperties(caseData, newCase);// 進來時清除caseData
		caseData.setApplydateStart(DateUtility.getNowChineseDate());
		setSystemMessage("複核成功");
		return QUERY_PAGE;
	}
	
}