package tw.gov.pcc.eip.orderCar.controllers;

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
import tw.gov.pcc.eip.framework.domain.UserBean;
import tw.gov.pcc.eip.framework.spring.controllers.BaseController;
import tw.gov.pcc.eip.framework.spring.support.FileOutputView;
import tw.gov.pcc.eip.orderCar.cases.Eip07w070Case;
import tw.gov.pcc.eip.services.Eip07w070Service;
import tw.gov.pcc.eip.util.BeanUtility;
import tw.gov.pcc.eip.util.DateUtility;
import tw.gov.pcc.eip.util.ExceptionUtility;
import tw.gov.pcc.eip.util.FilenameUtility;
import tw.gov.pcc.eip.util.ObjectUtility;

/**
 * 派車記錄查詢及列印作業Controller
 *
 * @author Ivy
 */
@Controller
@SessionAttributes({ Eip07w070Controller.CASE_KEY })
public class Eip07w070Controller extends BaseController {
	public static final String CASE_KEY = "_eip07w070_caseData";
	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(Eip07w070Controller.class);
	private static final String QUERY_PAGE = "/eip07w070/eip07w070q";// 查詢頁
	private static final String DATA_PAGE = "/eip07w070/eip07w070x";// 明細頁

	@Autowired
	private Eip07w070Service eip07w070Service;
	@Autowired
	private UserBean userData;

	@ModelAttribute(CASE_KEY)
	public Eip07w070Case getEip07w070Case() {
		Eip07w070Case caseData = new Eip07w070Case();
		return ObjectUtility.normalizeObject(caseData);
	}

	/**
	 * 進入頁面
	 *
	 * @return
	 */
	@RequestMapping("/Eip07w070_enter.action")
	public ModelAndView enter(@ModelAttribute(CASE_KEY) Eip07w070Case caseData) {
		log.debug("導向 Eip07w070Case_enter 派車記錄查詢及列印作業");
		Eip07w070Case newCase = new Eip07w070Case();
		BeanUtility.copyProperties(caseData, newCase);// 進來時清除caseData
		caseData.setUsing_date_s(DateUtility.getNowChineseYearMonth()+"01");
		caseData.setUsing_date_e(DateUtility.getNowChineseDate());
		eip07w070Service.getCarno(caseData);
		return new ModelAndView(QUERY_PAGE);
	}


	
	/**
	 * 派車記錄查詢及列印作業 查詢畫面
	 *
	 * @param caseData
	 * @param result
	 * @return
	 */
	@RequestMapping("/Eip07w070_search.action")
	public String searchData(@Validated(Eip07w070Case.Query.class) @ModelAttribute(CASE_KEY) Eip07w070Case caseData, BindingResult result) {
		log.debug("導向   Eip07w070  派車記錄查詢及列印作業：查詢資料 ");
		if (result.hasErrors()) {
			return QUERY_PAGE;
		}
		
		eip07w070Service.setQueryData(caseData);
		if(CollectionUtils.isEmpty(caseData.getDataList())) {
			setSystemMessage("查無資料");
			return QUERY_PAGE;
		} else {
			return DATA_PAGE;
		}
		
	}
	
	/**
	 * 列印
	 * 
	 * @param caseData
	 * @return
	 */
	@RequestMapping("/Eip07w070_print.action")
	public ModelAndView print(@Validated(Eip07w070Case.Print.class)@ModelAttribute(CASE_KEY)  Eip07w070Case caseData, BindingResult result) {
		if (result.hasErrors()) {
			return new ModelAndView(QUERY_PAGE);
		}
		
		try {
			ByteArrayOutputStream baos = eip07w070Service.getPrintData(caseData);
			if (baos == null) {
				setSystemMessage(getMessage(MessageKey.MSG_QUERY_EMPTY));//無查詢資料
				return new ModelAndView(QUERY_PAGE);
			} else {
				String orderCondition = "1".equals(caseData.getOrderCondition())? "用車日期" : "車牌號碼";
				return new ModelAndView(new FileOutputView(baos, FilenameUtility.getFileName(userData.getUserId(), "EIP08W050派車記錄表(依"+orderCondition+"排序)", "pdf"), FileOutputView.PDF_FILE));
			}
		} catch (Exception e) {
			log.error("列印派車記錄表失敗(eip08w050)，原因:" + ExceptionUtility.getStackTrace(e));
			setSystemMessage(getReportErrorMessage());
			return new ModelAndView(QUERY_PAGE);
		}
	}

}