package tw.gov.pcc.eip.orderCar.controllers;

import java.io.ByteArrayOutputStream;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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
	private static final String CANCEL_PAGE = "/eip07w040/eip07w041x";// 取消的明細頁
	private static final String CHANGE_CAR_PAGE = "/eip07w040/eip07w042x";// 取消的明細頁
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
			eip07w040Service.getDefaultData(caseData);
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
			eip07w040Service.getDefaultData(caseData);
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
			eip07w040Service.getDetailData(caseData,"eip07w040x");
			eip07w040Service.getTimeList(caseData);
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
	public ModelAndView updateData(@ModelAttribute(CASE_KEY) Eip07w040Case caseData, BindingResult result) {
		log.debug("導向   Eip07w040  秘書處進行派車作業：更新資料 ");
		eip07w040Service.getUsingData(caseData);
		return new ModelAndView(DETAIL_PAGE);
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
			eip07w040Service.getDefaultData(caseData);
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
	public String searchData(@Validated(Eip07w040Case.Query.class) @ModelAttribute(CASE_KEY) Eip07w040Case caseData, BindingResult result) {
		log.debug("導向 Eip07w040Case_enter 查詢 ");
		try {
			if(result.hasErrors()) {
				return QUERY_PAGE;
			}
			eip07w040Service.getDataByCondition(caseData);
		} catch (Exception e) {
			log.error("Eip07w040Case_enter查詢失敗" + ExceptionUtility.getStackTrace(e));
		}
		
		return QUERY_PAGE;
	}
	
	/**
	 * 秘書處進行派車作業 進入頁面
	 *
	 * @return
	 */
	@RequestMapping({"/Eip07w040_detail.action"})
	public ModelAndView cancelData(@ModelAttribute(CASE_KEY) Eip07w040Case caseData) {
		log.debug("導向 臨時取消派車  ");
		try {
			caseData.setCarBookingList(null);//清除資料
			caseData.setCarno("");//清除資料
			caseData.setShowEmptyStr(false);//清除資料
			caseData.setTimeMK("");
			caseData.setShowButton(false);
			caseData.setUsing("");
			caseData.setStatus("");
			eip07w040Service.getDetailData(caseData,"eip07w041x");
		} catch (Exception e) {
			log.error("Eip07w040Controller查詢失敗" + ExceptionUtility.getStackTrace(e));
			setSystemMessage("查詢失敗");
			return new ModelAndView(QUERY_PAGE);
		}
		
		return new ModelAndView(CANCEL_PAGE);
	}
	
	/**
	 * 秘書處進行派車作業 進入頁面
	 *
	 * @return
	 */
	@RequestMapping({"/Eip07w040_delete.action"})
	public ModelAndView delete(@ModelAttribute(CASE_KEY) Eip07w040Case caseData) {
		log.debug("導向 臨時取消派車  ");
		try {
			eip07w040Service.cancelData(caseData);
			eip07w040Service.getDefaultData(caseData);
		} catch (Exception e) {
			log.error("Eip07w040Controller臨時取消失敗" + ExceptionUtility.getStackTrace(e));
			setSystemMessage("臨時取消失敗");
			return new ModelAndView(CANCEL_PAGE);
		}
		setSystemMessage("臨時取消成功");
		return new ModelAndView(QUERY_PAGE);
	}
	
	/**
	 * AJAX以時間取得核定時間區間
	 * 
	 * @param caseData
	 * @return integer
	 */
	@RequestMapping(path = "/Eip07w040_getTime.action", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Map<String, String> getTime(@RequestBody Eip07w040Case caseData) {
		log.debug("AJAX以時間取得核定時間區間");
		Map<String, String> map = eip07w040Service.ajaxGetTime(caseData);
		return map;
	}
	
	
	/**
	 * 秘書處進行派車作業 改派其他車 進入頁面
	 *
	 * @return
	 */
	@RequestMapping({"/Eip07w040_changecar.action"})
	public ModelAndView otherCar(@ModelAttribute(CASE_KEY) Eip07w040Case caseData) {
		log.debug("導向 臨時取消派車  ");
		try {
			caseData.setCarBookingList(null);//清除資料
			caseData.setCarno("");//清除資料
			caseData.setShowEmptyStr(false);//清除資料
			caseData.setTimeMK("");
			caseData.setShowButton(false);
			caseData.setUsing("");
			caseData.setStatus("");
			eip07w040Service.getDetailData(caseData,"eip07w041x");
		} catch (Exception e) {
			log.error("Eip07w040Controller查詢失敗" + ExceptionUtility.getStackTrace(e));
			setSystemMessage("查詢失敗");
			return new ModelAndView(QUERY_PAGE);
		}
		
		return new ModelAndView(CHANGE_CAR_PAGE);
	}
	
	/**
	 * 秘書處進行派車作業：執行改派他車
	 *
	 * 
	 */
	@RequestMapping({"/Eip07w040_confirmchange.action"})
	public ModelAndView confirmChangeCar(@ModelAttribute(CASE_KEY) Eip07w040Case caseData) {
		log.debug("導向 執行改派他車  ");
		try {
			eip07w040Service.changeCarData(caseData);
			eip07w040Service.getDefaultData(caseData);
		} catch (Exception e) {
			log.error("Eip07w040Controller執行改派他車失敗" + ExceptionUtility.getStackTrace(e));
			setSystemMessage("執行改派他車失敗");
			return new ModelAndView(CHANGE_CAR_PAGE);
		}
		setSystemMessage("執行改派他車成功");
		return new ModelAndView(QUERY_PAGE);
	}
	
	
	/**
	 * 秘書處進行派車作業 不須派車
	 *
	 * @param caseData
	 * @param result
	 * @return
	 */
	@RequestMapping("/Eip07w040_noneed.action")
	public String noneedcar(@Validated(Eip07w040Case.Update.class)@ModelAttribute(CASE_KEY) Eip07w040Case caseData, BindingResult result) {
		log.debug("導向   Eip07w040  秘書處進行派車作業：更新全部資料 ");
		if(result.hasErrors()) {
			return DETAIL_PAGE;
		}
		try {
			eip07w040Service.updateAll(caseData);
			eip07w040Service.getDefaultData(caseData);
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
	 * 秘書處進行派車作業 不派車
	 *
	 * @param caseData
	 * @param result
	 * @return
	 */
	@RequestMapping("/Eip07w040_nocar.action")
	public ModelAndView nocartouse(@ModelAttribute(CASE_KEY) Eip07w040Case caseData, BindingResult result) {
		log.debug("導向   Eip07w040  秘書處進行派車作業：更新資料 ");
		try {
			eip07w040Service.nocartouse(caseData);
			eip07w040Service.getDefaultData(caseData);
		} catch (Exception e) {
			log.error("Eip07W040Controller不派車處理失敗" + ExceptionUtils.getStackTrace(e));
		}
		setSystemMessage("不派車成功");
		
		return new ModelAndView(QUERY_PAGE);
	}
}