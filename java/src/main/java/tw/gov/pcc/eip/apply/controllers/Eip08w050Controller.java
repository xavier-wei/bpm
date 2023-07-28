package tw.gov.pcc.eip.apply.controllers;

import java.io.ByteArrayOutputStream;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import tw.gov.pcc.eip.MessageKey;
import tw.gov.pcc.eip.apply.cases.Eip08w050Case;
import tw.gov.pcc.eip.framework.domain.UserBean;
import tw.gov.pcc.eip.framework.spring.controllers.BaseController;
import tw.gov.pcc.eip.framework.spring.support.FileOutputView;
import tw.gov.pcc.eip.services.Eip08w050Service;
import tw.gov.pcc.eip.util.BeanUtility;
import tw.gov.pcc.eip.util.DateUtility;
import tw.gov.pcc.eip.util.ExceptionUtility;
import tw.gov.pcc.eip.util.FilenameUtility;
import tw.gov.pcc.eip.util.ObjectUtility;

/**
 * 領物單紀錄查詢及列印作業Controller
 *
 * @author Ivy
 */
@Controller
@SessionAttributes({ Eip08w050Controller.CASE_KEY })
public class Eip08w050Controller extends BaseController {
	
	public static final String CASE_KEY = "_Eip08w050_caseData";
	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(Eip08w050Controller.class);
	private static final String QUERY_PAGE = "/eip08w050/eip08w050q";// 選擇頁
	private static final String DETAIL_PAGE = "/eip08w050/eip08w050x";// 選擇頁
	
	@Autowired
	private Eip08w050Service eip08w050Service;
	@Autowired
	private UserBean userData;

	@ModelAttribute(CASE_KEY)
	public Eip08w050Case getRo0w100Case() {
		Eip08w050Case caseData = new Eip08w050Case();
		return ObjectUtility.normalizeObject(caseData);
	}

	/**
	 * 進入頁面 領物單紀錄查詢及列印作業
	 *
	 * @return
	 */
	@RequestMapping("/Eip08w050_enter.action")
	public ModelAndView enter(@ModelAttribute(CASE_KEY) Eip08w050Case caseData) {
		log.debug("導向 Eip08w050_enter 領物單紀錄查詢及列印作業");
		Eip08w050Case newCase = new Eip08w050Case();
		BeanUtility.copyProperties(caseData, newCase);// 進來時清除caseData
		caseData.setApplyYearMonth(DateUtility.getNowChineseYearMonth());
		return new ModelAndView(QUERY_PAGE);
	}

	/**
	 * 進入頁面 領物單紀錄查詢
	 *
	 * @param caseData
	 * @param result
	 * @return
	 */
	@RequestMapping("/Eip08w050_query.action")
	public String query(@Validated(Eip08w050Case.Print.class) @ModelAttribute(CASE_KEY) Eip08w050Case caseData,BindingResult result) {
		log.debug("導向 領物單紀錄查詢及列印作業-查詢畫面");
		if (result.hasErrors()) {
			return QUERY_PAGE;
		}
		eip08w050Service.getEip08w050Report(caseData);
		if (CollectionUtils.isEmpty(caseData.getItemList()) &&  CollectionUtils.isEmpty(caseData.getUnitList())) {
			setSystemMessage("查無資料");
			return QUERY_PAGE;
		} else {
			return DETAIL_PAGE;
		}
	}
	
	
	/**
	 * 列印
	 * 
	 * @param caseData
	 * @return
	 */
	@RequestMapping("/Eip08w050_print.action")
	public ModelAndView print(@ModelAttribute(CASE_KEY) @Validated(Eip08w050Case.Print.class) Eip08w050Case caseData, BindingResult result) {
		if (result.hasErrors()) {
			return new ModelAndView(QUERY_PAGE);
		}
		
		try {
			ByteArrayOutputStream baos = eip08w050Service.getPrintData(caseData);
			if (baos == null) {
				setSystemMessage(getMessage(MessageKey.MSG_QUERY_EMPTY));//無查詢資料
				return new ModelAndView(QUERY_PAGE);
			} else {
				return new ModelAndView(new FileOutputView(baos, FilenameUtility.getFileName(userData.getUserId(), "EIP08W050領物單核發數量統計表", "zip"), FileOutputView.GENERAL_FILE));
			}
		} catch (Exception e) {
			log.error("列印領物單紀錄失敗(eip08w050)，原因:" + ExceptionUtility.getStackTrace(e));
			setSystemMessage(getReportErrorMessage());
			return new ModelAndView(QUERY_PAGE);
		}
	}
}