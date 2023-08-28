package tw.gov.pcc.eip.apply.controllers;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import tw.gov.pcc.eip.apply.cases.Eip08w010Case;
import tw.gov.pcc.eip.domain.Itemcode;
import tw.gov.pcc.eip.framework.spring.controllers.BaseController;
import tw.gov.pcc.eip.services.Eip08w010Service;
import tw.gov.pcc.eip.util.ExceptionUtility;
import tw.gov.pcc.eip.util.ObjectUtility;

/**
 * 領物單基本資料建置作業
 *
 * @author ivan
 */
@Controller
@SessionAttributes({Eip08w010Controller.CASE_KEY})
public class Eip08w010Controller extends BaseController {
    public static final String CASE_KEY = "_eip08w010_caseData";
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(Eip08w010Controller.class);
    private static final String QUERY_PAGE = "/eip08w010/eip08w010q";//選擇頁
    private static final String MAINLIST_PAGE = "/eip08w010/eip08w010x";//
    private static final String DETAILLIST_PAGE = "/eip08w010/eip08w011x";//

    @Autowired
    private Eip08w010Service eip08w010Service;
 
    @ModelAttribute(CASE_KEY)
    public Eip08w010Case getEip08w010Case() {
    	Eip08w010Case caseData = new Eip08w010Case();
        return ObjectUtility.normalizeObject(caseData);
    }

    /**
     * 進入頁面
     *
     * @return
     */
    @RequestMapping("/Eip08w010_enter.action")
    public ModelAndView enter(@ModelAttribute(CASE_KEY) Eip08w010Case caseData) {
        log.debug("導向 Eip08w010_enter 領物單基本資料建置作業");
        eip08w010Service.initCase(caseData);
        caseData.setMainKindList(eip08w010Service.findMainKindList());
        return new ModelAndView(QUERY_PAGE);
    }
    
	/**
	 * 依頁面品名大類查詢品名ajax
	 * 
	 */
	@RequestMapping("/Eip08w010_findDetalKindList.action")
	@ResponseBody
	public Map<String,String> findDetalKindList(@RequestBody String mainkind) {
		List<Itemcode> list = eip08w010Service.findDetailKindList(mainkind);
		Map<String,String> datamap = new LinkedHashMap<String,String>();
		for(Itemcode itemcode:list) {
			datamap.put(itemcode.getItemno(), itemcode.getItemname());
		}
		return datamap;
	}
	
	/**
	 * 查詢mainitemcode
	 *
	 * @return
	 */
	@RequestMapping("/Eip08w010_query.action")
	public ModelAndView queryList(@ModelAttribute(CASE_KEY) Eip08w010Case caseData) {
	       
	     try {
	    	List<Itemcode> list = eip08w010Service.findMainKindList(caseData.getMainkindno());
	    	if(CollectionUtils.isNotEmpty(list)) {
	    		caseData.setResultKindList(list);
	    		return new ModelAndView(MAINLIST_PAGE);
	    	}else {
	    		setSystemMessage(getQueryEmptyMessage());
	    		return new ModelAndView(QUERY_PAGE);
	    	}
	     	
		} catch (Exception e) {
			log.error("查詢失敗 - {}", ExceptionUtility.getStackTrace(e));
			setSystemMessage(getQueryFailMessage());
			return new ModelAndView(QUERY_PAGE);
		}
	 }
	
	/**
	 * 新增mainitemcode
	 *
	 * @return
	 */
	@RequestMapping("/Eip08w010_add.action")
	public ModelAndView insertMainKind(@ModelAttribute(CASE_KEY) Eip08w010Case caseData,  BindingResult bindingResult) {
	       
	     try {
    	 	//驗證
    	 	eip08w010Service.validInsertMainItemcode(caseData, bindingResult);
    	 	if(bindingResult.hasErrors()) {
    	 		return new ModelAndView(MAINLIST_PAGE);
    	 	}
    	 	//新增
    	 	eip08w010Service.insertMainItemcode(caseData);
    	 	//查詢畫面資料
    	 	List<Itemcode> list = eip08w010Service.findMainKindList(caseData.getMainkindno());
    		caseData.setResultKindList(list);
    		caseData.setAddMainItemname("");
    		caseData.setAddMainItemno("");
    		setSystemMessage(getSaveSuccessMessage());
    		return new ModelAndView(MAINLIST_PAGE);		    	
	    	 	
		} catch (Exception e) {
			log.error("新增失敗 - {}", ExceptionUtility.getStackTrace(e));
			setSystemMessage(getQueryFailMessage());
			return new ModelAndView(QUERY_PAGE);
		}
	 }
	
	/**
	 * 刪除mainitemcode
	 *
	 * @return
	 */
	@RequestMapping("/Eip08w010_deleteMain.action")
	public ModelAndView deleteMainKind(@ModelAttribute(CASE_KEY) Eip08w010Case caseData,  BindingResult bindingResult) {
	       
	     try {
	    	//驗證
	    	eip08w010Service.validDelMainItemcode(caseData, bindingResult);
	    	if(bindingResult.hasErrors()) {
	    	 	return new ModelAndView(MAINLIST_PAGE);
	    	}
	    	
    	 	//刪除
	    	eip08w010Service.deleteMainItemcode(caseData);
    	 	//查詢畫面資料
    	 	List<Itemcode> list = eip08w010Service.findMainKindList(caseData.getMainkindno());
    		caseData.setResultKindList(list);
    		setSystemMessage(getDeleteSuccessMessage());
    		return new ModelAndView(MAINLIST_PAGE);		    	
	    	 	
		} catch (Exception e) {
			log.error("刪除失敗 - {}", ExceptionUtility.getStackTrace(e));
			setSystemMessage(getDeleteFailMessage());
			return new ModelAndView(MAINLIST_PAGE);
		}
	 }
	
	/**
	 * 查詢detalitemcode
	 *
	 * @return
	 */
	@RequestMapping("/Eip08w010_detailQuery.action")
	public ModelAndView queryDetailList(@ModelAttribute(CASE_KEY) Eip08w010Case caseData) {
	       
	     try {
	    	List<Itemcode> list = eip08w010Service.findDetailKindList(caseData);
	    	caseData.setResultKindList(list);
	    	return new ModelAndView(DETAILLIST_PAGE);
		} catch (Exception e) {
			log.error("查詢失敗 - {}", ExceptionUtility.getStackTrace(e));
			setSystemMessage(getQueryFailMessage());
			return new ModelAndView(MAINLIST_PAGE);
		}
	 }
	
	/**
	 * 新增detailitemcode
	 *
	 * @return
	 */
	@RequestMapping("/Eip08w010_addDetail.action")
	public ModelAndView insertDetailKind(@ModelAttribute(CASE_KEY) Eip08w010Case caseData,  BindingResult bindingResult) {
	       
	     try {
    	 	//驗證
    	 	eip08w010Service.validInsertDetailItemcode(caseData, bindingResult);
    	 	if(bindingResult.hasErrors()) {
    	 		return new ModelAndView(DETAILLIST_PAGE);
    	 	}
    	 	//新增
    	 	int result = eip08w010Service.insertDetailItemcode(caseData);
    	 	//查詢畫面資料
    	 	if(result == 1) {
    	 		caseData.setAddDetailItemname(null);
    	 		caseData.setAddDetailItemno(null);
    	 	}
    	 	List<Itemcode> list = eip08w010Service.findDetailKindList(caseData.getMainkindno());
    		caseData.setResultKindList(list);
    		setSystemMessage(getSaveSuccessMessage());
    		return new ModelAndView(DETAILLIST_PAGE);		    	
	    	 	
		} catch (Exception e) {
			log.error("新增失敗 - {}", ExceptionUtility.getStackTrace(e));
			setSystemMessage(getQueryFailMessage());
			return new ModelAndView(DETAILLIST_PAGE);
		}
	 }
		
	/**
	 * 修改mainitemcode
	 *
	 * @return
	 */
	@RequestMapping("/Eip08w010_editMain.action")
	public ModelAndView editMainKind(@ModelAttribute(CASE_KEY) Eip08w010Case caseData,  BindingResult bindingResult) {
	       
	     try {
   	 
    	 	//驗證
    	 	eip08w010Service.validEditMainItemcode(caseData, bindingResult);
    	 	if(bindingResult.hasErrors()) {
    	 		return new ModelAndView(MAINLIST_PAGE);
    	 	}
	    	 
    	 	//修改
	    	eip08w010Service.editMainItemcode(caseData);
    	 	//查詢畫面資料
    	 	List<Itemcode> list = eip08w010Service.findMainKindList(caseData.getMainkindno());
    		caseData.setResultKindList(list);
    		setSystemMessage(getUpdateSuccessMessage());
    		return new ModelAndView(MAINLIST_PAGE);		    	    	
	    	 	
		} catch (Exception e) {
			log.error("修改失敗 - {}", ExceptionUtility.getStackTrace(e));
			setSystemMessage(getUpdateFailMessage());
			return new ModelAndView(MAINLIST_PAGE);
		}
	 }
	
	/**
	 * 修改detailitemcode
	 *
	 * @return
	 */
	@RequestMapping("/Eip08w010_editDetail.action")
	public ModelAndView editDetailKind(@ModelAttribute(CASE_KEY) Eip08w010Case caseData,  BindingResult bindingResult) {
	       
	     try {
    	 	//驗證
    	 	eip08w010Service.validEditDetailItemcode(caseData, bindingResult);
    	 	if(bindingResult.hasErrors()) {
    	 		return new ModelAndView(DETAILLIST_PAGE);
    	 	}
	    	 
    	 	//修改
	    	eip08w010Service.editDetailItemcode(caseData);
    	 	//查詢畫面資料
    	 	List<Itemcode> list = eip08w010Service.findDetailKindList(caseData.getMainkindno());
    		caseData.setResultKindList(list);
    		setSystemMessage(getUpdateSuccessMessage());
    		return new ModelAndView(DETAILLIST_PAGE);		    	
	    	 	
		} catch (Exception e) {
			log.error("修改失敗 - {}", ExceptionUtility.getStackTrace(e));
			setSystemMessage(getUpdateFailMessage());
			return new ModelAndView(DETAILLIST_PAGE);
		}
	 }
		
	/**
	 * 刪除detailitemcode
	 *
	 * @return
	 */
	@RequestMapping("/Eip08w010_deleteDetail.action")
	public ModelAndView deleteDetailKind(@ModelAttribute(CASE_KEY) Eip08w010Case caseData,  BindingResult bindingResult) {
	       
	     try {
    	 	//驗證
    	 	eip08w010Service.validDeleteDetailItemcode(caseData, bindingResult);
    	 	if(bindingResult.hasErrors()) {
    	 		return new ModelAndView(DETAILLIST_PAGE);
    	 	}
	    	 
    	 	//刪除
	    	eip08w010Service.deleteDetailItemcode(caseData);
    	 	//查詢畫面資料
    	 	List<Itemcode> list = eip08w010Service.findDetailKindList(caseData.getMainkindno());
    		caseData.setResultKindList(list);
    		setSystemMessage(getDeleteSuccessMessage());
    		return new ModelAndView(DETAILLIST_PAGE);		    	
	    	 	
		} catch (Exception e) {
			log.error("刪除失敗 - {}", ExceptionUtility.getStackTrace(e));
			setSystemMessage(getDeleteFailMessage());
			return new ModelAndView(DETAILLIST_PAGE);
		}
	 }
 

}
