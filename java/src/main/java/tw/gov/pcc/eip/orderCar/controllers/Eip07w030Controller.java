package tw.gov.pcc.eip.orderCar.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import tw.gov.pcc.eip.framework.domain.UserBean;
import tw.gov.pcc.eip.framework.spring.controllers.BaseController;
import tw.gov.pcc.eip.orderCar.cases.Eip07w030Case;
import tw.gov.pcc.eip.services.Eip07w030Service;
import tw.gov.pcc.eip.util.BeanUtility;
import tw.gov.pcc.eip.util.DateUtility;
import tw.gov.pcc.eip.util.ExceptionUtility;
import tw.gov.pcc.eip.util.ObjectUtility;

/**
 * 派車預約審核作業
 *
 * @author Ivy
 */
@Controller
@SessionAttributes({ Eip07w030Controller.CASE_KEY })
public class Eip07w030Controller extends BaseController {
	public static final String CASE_KEY = "_eip07w030_caseData";
	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(Eip07w030Controller.class);
	private static final String QUERY_PAGE = "/eip07w030/eip07w030q";// 選擇頁

	@Autowired
	private Eip07w030Service eip07w030Service;
	
	@Autowired
	private UserBean userData;
	
	@ModelAttribute(CASE_KEY)
	public Eip07w030Case getEip07w030Case() {
		Eip07w030Case caseData = new Eip07w030Case();
		return ObjectUtility.normalizeObject(caseData);
	}

	/**
	 * 派車預約審核作業 進入頁面
	 *
	 * @return
	 */
	@RequestMapping("/Eip07w030_enter.action")
	public ModelAndView enter(@ModelAttribute(CASE_KEY) Eip07w030Case caseData) {
		log.debug("導向 Eip07w030Case_enter 派車預約審核作業");
		Eip07w030Case newCase = new Eip07w030Case();
		BeanUtility.copyProperties(caseData, newCase);// 進來時清除caseData
		caseData.setApply_dept(userData.getDeptId());
		try {
			eip07w030Service.getData(caseData);
		} catch (Exception e) {
			log.error("Eip07w030Controller查詢失敗" + ExceptionUtility.getStackTrace(e));
			setSystemMessage("查詢失敗");
		}
		return new ModelAndView(QUERY_PAGE);
	}

	/**
	 * 派車預約審核作業 查詢資料
	 *
	 * @return
	 */
	@RequestMapping("/Eip07w030_query.action")
	public ModelAndView queryData(@ModelAttribute(CASE_KEY) Eip07w030Case caseData) {
		log.debug("導向 Eip07w030_query 查詢資料");

		try {
			eip07w030Service.getData(caseData);
			if(CollectionUtils.isEmpty(caseData.getDataList())) {
				setSystemMessage("查無資料");
				return new ModelAndView(QUERY_PAGE);
			} 
		} catch (Exception e) {
			log.error("Eip07w030Controller查詢失敗" + ExceptionUtility.getStackTrace(e));
			setSystemMessage("查詢失敗");
		}
		
		return new ModelAndView(QUERY_PAGE);
	}
	
	/**
	 * 派車預約審核作業 更新資料
	 *
	 * @param caseData
	 * @param result
	 * @return
	 */
	@RequestMapping("/Eip07w030_update.action")
	public String updateData(@Validated(Eip07w030Case.Update.class) @ModelAttribute(CASE_KEY) Eip07w030Case caseData, BindingResult result) {
		log.debug("導向   Eip07w030  派車預約審核作業：更新資料 ");
		if (result.hasErrors()) {
			return QUERY_PAGE;
		}

		try {
			List<String> applyids = caseData.getDataList().stream()
					.filter(it -> it.isCheck() && StringUtils.isNotEmpty(it.getApplyid())).map(e -> e.getApplyid())
					.collect(Collectors.toList());
			if(CollectionUtils.isEmpty(applyids)) {
				setSystemMessage("請至少勾選一項案件",true);
				return QUERY_PAGE;
			}
			
			caseData.setApplyIdList(applyids);
			eip07w030Service.updateAll(caseData);
			eip07w030Service.getData(caseData);
			if(CollectionUtils.isNotEmpty(caseData.getDataList())) {//若同一時間區間仍有未複合的案件則返回資料列表頁
				setSystemMessage("複核成功");
				return QUERY_PAGE;
			}
		} catch (Exception e) {
			log.error("Eip07w030Controller update失敗" + ExceptionUtility.getStackTrace(e));
			setSystemMessage("複核失敗");
			return QUERY_PAGE;
		}
		Eip07w030Case newCase = new Eip07w030Case();
		BeanUtility.copyProperties(caseData, newCase);// 進來時清除caseData
		caseData.setApplydateStart(DateUtility.getNowChineseDate());
		setSystemMessage("複核成功");
		return QUERY_PAGE;
	}

}