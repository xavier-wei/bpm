package tw.gov.pcc.eip.adm.controllers;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import tw.gov.pcc.eip.adm.cases.Eip00w050Case;
import tw.gov.pcc.eip.adm.cases.Eip00w070Case;
import tw.gov.pcc.eip.adm.service.Eip00w070Service;
import tw.gov.pcc.eip.adm.view.dynatree.DynaTreeBuilder;
import tw.gov.pcc.eip.adm.view.dynatree.parser.ItemParser;
import tw.gov.pcc.eip.dao.ItemsDao;
import tw.gov.pcc.eip.domain.CursorDeptAcl;
import tw.gov.pcc.eip.domain.Items;
import tw.gov.pcc.eip.framework.spring.controllers.BaseController;
import tw.gov.pcc.eip.util.ExceptionUtility;
import tw.gov.pcc.eip.util.ObjectUtility;

/**
 * 單位角色維護
 *
 * @author ivan
 */
@Controller
@SessionAttributes({Eip00w070Controller.CASE_KEY})
public class Eip00w070Controller extends BaseController {
    public static final String CASE_KEY = "_eip00w070_caseData";
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(Eip00w070Controller.class);
    private static final String QUERY_PAGE = "/eip00w070/eip00w070q";//選擇頁
    private static final String ADD_PAGE = "/eip00w070/eip00w070a";//新增頁
    private static final String EDITMENU_PAGE = "/eip00w070/eip00w070x";//編輯角色頁
    private static final String EDITMEMBER_PAGE = "/eip00w070/eip00w071x";//編輯隸屬頁

    @Autowired
    private Eip00w070Service eipadm0w070Service;

 
    @ModelAttribute(CASE_KEY)
    public Eip00w070Case getEipadm0w070Case() {
    	Eip00w070Case caseData = new Eip00w070Case();
        return ObjectUtility.normalizeObject(caseData);
    }

    /**
     * 進入頁面
     *
     * @return
     */
    @RequestMapping("/Eip00w070_enter.action")
    public ModelAndView enter(@ModelAttribute(CASE_KEY) Eip00w070Case caseData) {
        log.debug("導向 adm0w070_enter 單位角色維護"); 
        caseData.setRolesList(eipadm0w070Service.findRolesList(caseData.getRole_id()));
        return new ModelAndView(QUERY_PAGE);
    }
    
    /**
     * 新增
     *
     * @return
     */
    @RequestMapping("/Eip00w070_add.action")
    public ModelAndView add(@ModelAttribute(CASE_KEY) Eip00w070Case caseData, ModelMap m) {
        try {
			log.debug("導向 adm0w070_enter 單位角色維護"); 
			caseData.setRole_id(null);
			caseData.setRole_desc(null);
			List<CursorDeptAcl> da = eipadm0w070Service.findMenu();
			return new ModelAndView(ADD_PAGE).addObject("items", DynaTreeBuilder.build(ItemParser.parser(da)));
		} catch (Exception e) {
			log.error("新增失敗 - {}", ExceptionUtility.getStackTrace(e));
			super.setSystemMessage(super.getSaveFailMessage());
			return new ModelAndView(ADD_PAGE);
		}
    }
    
    /**
     * 編輯功能清單
     *
     * @return
     */
    @RequestMapping("/Eip00w070_editmenu.action")
    public ModelAndView editMenu(@ModelAttribute(CASE_KEY) Eip00w070Case caseData, ModelMap m) {
        try {
			List<CursorDeptAcl> da = eipadm0w070Service.findMenu();
			return new ModelAndView(EDITMENU_PAGE).addObject("items", DynaTreeBuilder.build(ItemParser.parser(da)));
		} catch (Exception e) {
			log.error("查詢失敗 - {}", ExceptionUtility.getStackTrace(e));
			super.setSystemMessage(super.getQueryFailMessage());
			return new ModelAndView(QUERY_PAGE);
		}
    }
    
    /**
     * 編輯隸屬人員
     *
     * @return
     */
    @RequestMapping("/Eip00w070_editmember.action")
    public ModelAndView editMember(@ModelAttribute(CASE_KEY) Eip00w070Case caseData, ModelMap m) {
    	try {
			eipadm0w070Service.findMember(caseData);
			return new ModelAndView(EDITMEMBER_PAGE);
		} catch (Exception e) {
			log.error("查詢失敗 - {}", ExceptionUtility.getStackTrace(e));
			super.setSystemMessage(super.getQueryFailMessage());
			return new ModelAndView(QUERY_PAGE);
		}
    }
        

}
