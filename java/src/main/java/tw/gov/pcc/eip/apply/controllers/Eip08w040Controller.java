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

import com.cronutils.utils.StringUtils;

import tw.gov.pcc.eip.apply.cases.Eip08w040Case;
import tw.gov.pcc.eip.framework.domain.UserBean;
import tw.gov.pcc.eip.framework.spring.controllers.BaseController;
import tw.gov.pcc.eip.services.Eip08w040Service;
import tw.gov.pcc.eip.util.BeanUtility;
import tw.gov.pcc.eip.util.DateUtility;
import tw.gov.pcc.eip.util.ExceptionUtility;
import tw.gov.pcc.eip.util.ObjectUtility;

/**
 * 秘書處進行領物單核發作業Controller
 *
 * @author Ivy
 */
@Controller
@SessionAttributes({ Eip08w040Controller.CASE_KEY })
public class Eip08w040Controller extends BaseController {
	public static final String CASE_KEY = "_Eip08w040_caseData";
	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(Eip08w040Controller.class);
	private static final String QUERY_PAGE = "/eip08w040/eip08w040q";// 選擇頁
	private static final String LIST_APGE = "/eip08w040/eip08w040x";// 資料列表
	private static final String DETAIL_PAGE = "/eip08w040/eip08w410x";// 明細頁

	@Autowired
	private Eip08w040Service eip08w040Service;
	@Autowired
	private UserBean userData;

	@ModelAttribute(CASE_KEY)
	public Eip08w040Case getRo0w100Case() {
		Eip08w040Case caseData = new Eip08w040Case();
		return ObjectUtility.normalizeObject(caseData);
	}

	/**
	 * 進入頁面 秘書處進行領物單核發作業
	 *
	 * @return
	 */
	@RequestMapping("/Eip08w040_enter.action")
	public ModelAndView enter(@ModelAttribute(CASE_KEY) Eip08w040Case caseData) {
		log.debug("導向 Eip08w040_enter 秘書處進行領物單核發作業");
		Eip08w040Case newCase = new Eip08w040Case();
		BeanUtility.copyProperties(caseData, newCase);// 進來時清除caseData
		caseData.setApply_dateStart(DateUtility.getNowChineseDate());
		return new ModelAndView(QUERY_PAGE);
	}

	/**
	 * 秘書處進行領物單核發作業 查詢畫面
	 *
	 * @param caseData
	 * @param result
	 * @return
	 */
	@RequestMapping("/Eip08w040_query.action")
	public String query(@Validated(Eip08w040Case.Query.class) @ModelAttribute(CASE_KEY) Eip08w040Case caseData,
			BindingResult result) {
		log.debug("導向Eip08w040秘書處進行領物單核發作業-查詢畫面");
		if (StringUtils.isEmpty(caseData.getApply_dateEnd())) {
			caseData.setApply_dateEnd(DateUtility.getNowChineseDate());
		}
		if (result.hasErrors()) {
			return QUERY_PAGE;
		}
		
		eip08w040Service.getCaseData(caseData);
		
		if (CollectionUtils.isEmpty(caseData.getDataList())) {
			setSystemMessage("查無資料");
			return QUERY_PAGE;
		} else {
			return LIST_APGE;
		}
	}

	/**
	 * 秘書處進行領物單核發作業 查詢畫面
	 *
	 * @param caseData
	 * @param result
	 * @return
	 */
	@RequestMapping("/Eip08w040_detail.action")
	public String detail(@Validated @ModelAttribute(CASE_KEY) Eip08w040Case caseData, BindingResult result) {
		log.debug("導向   Eip08w040 秘書處進行領物單核發作業-查詢明細畫面");
		if (result.hasErrors()) {
			return LIST_APGE;
		}

		try {
			eip08w040Service.setDetailData(caseData);
		} catch (Exception e) {
			log.error("Eip08w040Controller查詢資料明細失敗" + ExceptionUtility.getStackTrace(e));
			setSystemMessage("查詢失敗");
			return LIST_APGE;
		}

		return DETAIL_PAGE;
	}

	/**
	 * 秘書處進行領物單核發作業 核發畫面
	 *
	 * @param caseData
	 * @param result
	 * @return
	 */
	@RequestMapping("/Eip08w040_update.action")
	public String update(@Validated @ModelAttribute(CASE_KEY) Eip08w040Case caseData, BindingResult result) {
		log.debug("導向 Eip08w040 秘書處進行領物單核發作業-查詢更正作業");
		if (result.hasErrors()) {
			return LIST_APGE;
		}

		try {
			eip08w040Service.updateAll(caseData, userData);
		} catch (Exception e) {
			log.error("Eip08w040Controller update失敗" + ExceptionUtility.getStackTrace(e));
			setSystemMessage("資料更新失敗");
			return LIST_APGE;
		}
		
		eip08w040Service.getCaseData(caseData);
		setSystemMessage("核發成功");
		
		if (CollectionUtils.isEmpty(caseData.getDataList())) {
			return QUERY_PAGE;
		} else {
			return LIST_APGE;
		}

	}

}