package tw.gov.pcc.eip.orderCar.controllers;

import java.io.ByteArrayOutputStream;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import tw.gov.pcc.eip.framework.spring.controllers.BaseController;
import tw.gov.pcc.eip.framework.spring.support.FileOutputView;
import tw.gov.pcc.eip.orderCar.cases.Eip07w040Case;
import tw.gov.pcc.eip.services.Eip07w040Service;
import tw.gov.pcc.eip.util.BeanUtility;
import tw.gov.pcc.eip.util.ExceptionUtility;
import tw.gov.pcc.eip.util.ObjectUtility;

/**
 * 秘書處進行派車作業
 *
 * @author Ivy
 */
@Controller
@SessionAttributes({ Eip07w040Controller.CASE_KEY })
public class Eip07w040Controller extends BaseController {
	public static final String CASE_KEY = "_eip07w040_caseData";
	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(Eip07w040Controller.class);
	private static final String QUERY_PAGE = "/eip07w040/eip07w040q";// 選擇頁
	private static final String DETAIL_PAGE = "/eip07w040/eip07w040x";// 明細頁
	private final static String SEARCH_BY_STATUS234 = "1";
	private final static String SEARCH_BY_CONDITION = "2";
	
	@Autowired
	private Eip07w040Service eip07w040Service;

	@ModelAttribute(CASE_KEY)
	public Eip07w040Case getEip07w040Case() {
		Eip07w040Case caseData = new Eip07w040Case();
		return ObjectUtility.normalizeObject(caseData);
	}

	/**
	 * 秘書處進行派車作業 進入頁面
	 *
	 * @return
	 */
	@RequestMapping({"/Eip07w040_enter.action"})
	public ModelAndView enter(@ModelAttribute(CASE_KEY) Eip07w040Case caseData) {
		log.debug("導向 Eip07w040Case_enter 秘書處進行派車作業");
		try {
			Eip07w040Case newCase = new Eip07w040Case();
			BeanUtility.copyProperties(caseData, newCase);// 進來時清除caseData
			eip07w040Service.getData(caseData,SEARCH_BY_STATUS234);
		} catch (Exception e) {
			log.error("Eip07w040Case_enter查詢失敗" + ExceptionUtility.getStackTrace(e));
		}
		return new ModelAndView(QUERY_PAGE);
	}
	
	/**
	 * 秘書處進行派車作業 回上一頁(查詢資料不清除)
	 *
	 * @return
	 */
	@RequestMapping({"/Eip07w040_back.action"})
	public ModelAndView backToFirst(@ModelAttribute(CASE_KEY) Eip07w040Case caseData) {
		log.debug("導向 Eip07w040Case_enter 秘書處進行派車作業");
		try {
			eip07w040Service.getData(caseData,SEARCH_BY_CONDITION);
		} catch (Exception e) {
			log.error("Eip07w040Case_enter查詢失敗" + ExceptionUtility.getStackTrace(e));
		}
		return new ModelAndView(QUERY_PAGE);
	}

	/**
	 * 秘書處進行派車作業 查詢資料
	 *
	 * @return
	 */
	@RequestMapping("/Eip07w040_query.action")
	public ModelAndView queryData(@ModelAttribute(CASE_KEY) Eip07w040Case caseData) {
		log.debug("導向 Eip07w040_query 查詢明細資料");

		try {
			caseData.setCarBookingList(null);//清除資料
			caseData.setCarno("");//清除資料
			caseData.setShowEmptyStr(false);//清除資料
			caseData.setTimeMK("");
			caseData.setShowButton(false);
			caseData.setUsing("");
			caseData.setStatus("");
			eip07w040Service.getDetailData(caseData);
		} catch (Exception e) {
			log.error("Eip07w040Controller查詢失敗" + ExceptionUtility.getStackTrace(e));
			setSystemMessage("查詢失敗");
			return new ModelAndView(QUERY_PAGE);
		}
		
		return new ModelAndView(DETAIL_PAGE);
	}
	
	
	/**
	 * 秘書處進行派車作業 更新資料
	 *
	 * @param caseData
	 * @param result
	 * @return
	 */
	@RequestMapping("/Eip07w040_getUsingData.action")
	public String updateData(@ModelAttribute(CASE_KEY) Eip07w040Case caseData, BindingResult result) {
		log.debug("導向   Eip07w040  秘書處進行派車作業：更新資料 ");
		eip07w040Service.getUsingData(caseData);
		return DETAIL_PAGE;
	}
	
	
	/**
	 * 秘書處進行派車作業 更新全部資料
	 *
	 * @param caseData
	 * @param result
	 * @return
	 */
	@RequestMapping("/Eip07w040_updateAll.action")
	public String updateAllData(@Validated(Eip07w040Case.Update.class)@ModelAttribute(CASE_KEY) Eip07w040Case caseData, BindingResult result) {
		log.debug("導向   Eip07w040  秘書處進行派車作業：更新全部資料 ");
		if(result.hasErrors()) {
			return DETAIL_PAGE;
		}
		try {
			eip07w040Service.updateAll(caseData);
			eip07w040Service.getData(caseData,SEARCH_BY_CONDITION);
			setSystemMessage("派車成功");
		} catch(RuntimeException re) {
			setSystemMessage("駕駛人資料有誤，派車失敗");
			log.error("Eip07W040Controller駕駛人資料有誤，派車失敗" + ExceptionUtils.getStackTrace(re));
			return DETAIL_PAGE;
		}catch(Exception e) {
			setSystemMessage("派車失敗");
			log.error("Eip07W040Controller派車失敗" + ExceptionUtils.getStackTrace(e));
			return DETAIL_PAGE;
		}
		
		return QUERY_PAGE;
	}
	
	/**
	 * 列印
	 * 
	 * @param caseData
	 * @return
	 */
	@RequestMapping("/Eip07w040_print.action")
	public ModelAndView print(@Validated(Eip07w040Case.Print.class) @ModelAttribute(CASE_KEY)Eip07w040Case caseData, BindingResult result) {
		try {
			if(CollectionUtils.isEmpty(caseData.getApplyids()) && StringUtils.isEmpty(caseData.getReprintApplyid())) {
				setSystemMessage("尚未勾選欲列印的案件",true);
				return new ModelAndView(QUERY_PAGE);
			} else {				
				ByteArrayOutputStream baoOutput = eip07w040Service.getEip07w040LReport(caseData);
				String fileName = "Eip07w040_秘書處進行派車作業.pdf";
				return new ModelAndView(new FileOutputView(baoOutput, fileName, FileOutputView.PDF_FILE));
			}
		} catch (Exception e) {
			log.error("列印失敗，原因:{}", ExceptionUtility.getStackTrace(e));
			setSystemMessage(getReportErrorMessage());
			return new ModelAndView(QUERY_PAGE);
		}
	}
	
	/**
	 * 秘書處進行派車作業 進入頁面
	 *
	 * @return
	 */
	@RequestMapping({"/Eip07w040_search.action"})
	public String searchData(@ModelAttribute(CASE_KEY) Eip07w040Case caseData) {
		log.debug("導向 Eip07w040Case_enter 查詢 ");
		try {
			eip07w040Service.getData(caseData,SEARCH_BY_CONDITION);
		} catch (Exception e) {
			log.error("Eip07w040Case_enter查詢失敗" + ExceptionUtility.getStackTrace(e));
		}
		
		return QUERY_PAGE;
	}
}