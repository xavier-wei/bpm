package tw.gov.pcc.eip.adm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import tw.gov.pcc.eip.adm.cases.Eip00w020Case;
import tw.gov.pcc.eip.services.Eip00w020Service;
import tw.gov.pcc.eip.domain.Users;
import tw.gov.pcc.eip.framework.spring.controllers.BaseController;
import tw.gov.pcc.eip.util.ExceptionUtility;
import tw.gov.pcc.eip.util.ObjectUtility;

/**
 * 使用者資料維護
 *
 * @author ivan
 */
@Controller
@SessionAttributes({Eip00w020Controller.CASE_KEY})
public class Eip00w020Controller extends BaseController {
    public static final String CASE_KEY = "_eip00w020_caseData";
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(Eip00w020Controller.class);
    private static final String QUERY_PAGE = "/eip00w020/eip00w020q";//選擇頁
    private static final String EDIT_PAGE = "/eip00w020/eip00w020x";//選擇頁

    @Autowired
    private Eip00w020Service eip00w020Service;
 
    @ModelAttribute(CASE_KEY)
    public Eip00w020Case getEipadm0w02Case() {	
    	Eip00w020Case caseData = new Eip00w020Case();
        return ObjectUtility.normalizeObject(caseData);
    }

    /**
     * 使用者資料維護
     * 進入主畫面
     *
     * @return
     */
    @RequestMapping("/Eip00w020_enter.action")
    public ModelAndView enter(@ModelAttribute(CASE_KEY) Eip00w020Case caseData) {
        log.debug("導向 Eip00w020_enter 使用者資料維護");
        eip00w020Service.init(caseData);
        eip00w020Service.findUserList(caseData);
        return new ModelAndView(QUERY_PAGE);
    }
    
    /**
     * 使用者資料維護
     * 查詢結果主畫面
     *
     * @return
     */
    @RequestMapping("/Eip00w020_query.action")
    public ModelAndView query(@ModelAttribute(CASE_KEY) Eip00w020Case caseData) {
        log.debug("導向 Eip00w020_enter 使用者資料維護");
        eip00w020Service.findUserList(caseData);
    
        return new ModelAndView(QUERY_PAGE);
    }
    

    /**
     * 使用者資料維護
     * 修改主畫面
     * 
     * @return
     */
    @RequestMapping("/Eip00w020_edit.action")
    public ModelAndView edit(@ModelAttribute(CASE_KEY) Eip00w020Case caseData) {
          
        try {
        	Users users = eip00w020Service.findUser(caseData);
        	if(users != null) {
        		eip00w020Service.settingCase(caseData, users);
        		return new ModelAndView(EDIT_PAGE);
        	}else {
        		super.setSystemMessage(getQueryEmptyMessage());
        		return enter(caseData);
        	}
        	
		} catch (Exception e) {
			log.error("查詢失敗 - {}", ExceptionUtility.getStackTrace(e));
			super.setSystemMessage(getQueryFailMessage());
			return new ModelAndView(QUERY_PAGE);
		}
    }

    /**
    * 修改資料更新
    *
    * @return
    */
   @RequestMapping("/Eip00w020_update.action")
   public ModelAndView update(@ModelAttribute(CASE_KEY) Eip00w020Case caseData) {
         
        try {
        	eip00w020Service.updateUsers(caseData);
        	super.setSystemMessage(getUpdateSuccessMessage());
        	return enter(caseData);
		} catch (Exception e) {
			log.error("更新失敗 - {}", ExceptionUtility.getStackTrace(e));
			super.setSystemMessage(getUpdateFailMessage());
			return new ModelAndView(QUERY_PAGE);
		}
   }
   
   /**
   * 刪除帳戶角色
   *
   * @return
   */
  @RequestMapping("/Eip00w020_delUserRole.action")
  public ModelAndView delUserRole(@ModelAttribute(CASE_KEY) Eip00w020Case caseData) {
        
         try {
	       	 eip00w020Service.deleteUserRoles(caseData);
	       	 super.setSystemMessage(getUpdateSuccessMessage());
	       	 return edit(caseData);
		 } catch (Exception e) {
			 log.error("角色刪除失敗 - {}", ExceptionUtility.getStackTrace(e));
			 super.setSystemMessage(getUpdateFailMessage());
			 return edit(caseData);
		 }
   }
  
  /**
  * 新增帳戶角色
  *
  * @return
  */
  @RequestMapping("/Eip00w020_addUserRole.action")
  public ModelAndView addUserRole(@ModelAttribute(CASE_KEY) Eip00w020Case caseData) {
       
        try {
	      	eip00w020Service.addUserRoles(caseData);
	      	super.setSystemMessage(getUpdateSuccessMessage());
	      	return edit(caseData);
		} catch (Exception e) {
			log.error("角色新增失敗 - {}", ExceptionUtility.getStackTrace(e));
			super.setSystemMessage(getUpdateFailMessage());
			return edit(caseData);
		}
  }
}
