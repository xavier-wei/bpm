package tw.gov.pcc.eip.apply.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import tw.gov.pcc.eip.apply.cases.Eip08w020Case;
import tw.gov.pcc.eip.domain.Itemcode;
import tw.gov.pcc.eip.framework.domain.UserBean;
import tw.gov.pcc.eip.framework.spring.annotation.SkipCSRFVerify;
import tw.gov.pcc.eip.framework.spring.controllers.BaseController;
import tw.gov.pcc.eip.services.Eip08w020Service;
import tw.gov.pcc.eip.util.DateUtility;
import tw.gov.pcc.eip.util.ExceptionUtility;
import tw.gov.pcc.eip.util.ObjectUtility;

/**
 * 領物單申請作業Controller
 *
 * @author Ivy
 */
@Controller
@SessionAttributes({ Eip08w020controller.CASE_KEY })
public class Eip08w020controller extends BaseController {
	public static final String CASE_KEY = "_Eip08w020_caseData";
	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(Eip08w020controller.class);
	private static final String QUERY_PAGE = "/eip08w020/eip08w020q";// 選擇頁
	private static final String ADD_APGE = "/eip08w020/eip08w020x";// 新增頁
	
	@Autowired
	private UserBean userData;
	@Autowired
	private Eip08w020Service eip08w020Service;

	@ModelAttribute(CASE_KEY)
	public Eip08w020Case getRo0w100Case() {
		Eip08w020Case caseData = new Eip08w020Case();
		return ObjectUtility.normalizeObject(caseData);
	}

	/**
	 * 進入頁面
	 *
	 * @return
	 */
	@RequestMapping("/Eip08w020_enter.action")
	public ModelAndView enter(@ModelAttribute(CASE_KEY) Eip08w020Case caseData) {
		log.debug("導向 Eip08w020_enter 領物單申請作業");
		List<Eip08w020Case> newData = new ArrayList<>();
		caseData.setAllData(newData);
		caseData.setApply_date(DateUtility.getNowChineseDate());// 申請日期(預設系統日)
		caseData.setApply_dept(userData.getDeptId());// 申請單位
		caseData.setApply_user(userData.getUserId());// 申請人

		return new ModelAndView(QUERY_PAGE);
	}

	/**
	 * 領物單申請作業畫面 - 新增作業
	 *
	 * @param caseData
	 * @param result
	 * @return
	 */
	@RequestMapping("/Eip08w020_add.action")
	public String add(@Validated @ModelAttribute(CASE_KEY) Eip08w020Case caseData, BindingResult result) {
		log.debug("導向   Eip08w020   領物單申請作業畫面-新增作業");
		if (result.hasErrors()) {
			return QUERY_PAGE;
			}
		eip08w020Service.setCaseData(caseData);
		return ADD_APGE;
	}

	
	/**
	 * AJAX以品名大類取得品名
	 * @param caseData
	 * @return list
	 */
	@SkipCSRFVerify
	@RequestMapping(path = "/Eip08w020_getItemCodekind.action", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<Itemcode> getItemCodekind(@RequestBody Eip08w020Case caseData) {
		log.debug("Eip08w020：AJAX以品名大類取得品名");
		List<Itemcode> list = eip08w020Service.getItemCodekind(caseData);
		return ObjectUtility.normalizeObject(list);
	}
	
	/**
	 * AJAX以品名取得庫存數量
	 * @param caseData
	 * @return integer
	 */
	@RequestMapping(path = "/Eip08w020_getWithholdCnt.action", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Map<String,Integer> getWithhold_cnt(@RequestBody Eip08w020Case caseData) {
		log.debug("AJAX以品名取得庫存數量");
		Itemcode data = eip08w020Service.getWithhold_cntAndBook_cnt(caseData.getItemkind(),caseData.getItemno());
		Map<String,Integer> map = new HashMap<>();
		map.put("book_cnt", data.getBook_cnt()==null? 0 : data.getBook_cnt());
		map.put("withhold_cnt", data.getWithhold_cnt()==null? 0 : data.getWithhold_cnt());
		return map;
	}
	
	/**
	 * 領物單申請作業畫面 - 新增作業
	 *
	 * @param caseData
	 * @param result
	 * @return
	 */
	@RequestMapping("/Eip08w020_insert.action")
	public String insertData(@Validated(Eip08w020Case.Insert.class) @ModelAttribute(CASE_KEY) Eip08w020Case caseData, BindingResult result) {
		log.debug("導向   Eip08w020 ");
		if(result.hasErrors()) {
			return ADD_APGE;
		}
		
		try {
			eip08w020Service.insertApplyItem(caseData);
			setSystemMessage("申請成功");
		} catch(Exception e) {
			log.error("Eip08w020Controller新增Applyitem失敗"+ ExceptionUtility.getStackTrace(e));
			setSystemMessage("申請失敗");
			return ADD_APGE;
		}
		
		return QUERY_PAGE;
	}
	
	
	
}