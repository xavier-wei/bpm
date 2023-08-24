package tw.gov.pcc.eip.tableau.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import tw.gov.pcc.eip.framework.domain.UserBean;
import tw.gov.pcc.eip.framework.spring.controllers.BaseController;
import tw.gov.pcc.eip.tableau.cases.TableauDataCase;
import tw.gov.pcc.eip.tableau.service.TableauService;
import tw.gov.pcc.eip.util.ExceptionUtility;
import tw.gov.pcc.eip.util.ObjectUtility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * index頁面，tableau儀錶板顯示
 *
 * @author Susan
 */
@Controller
public class TableauController extends BaseController {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(TableauController.class);
    private static final String QUERY_PAGE = "/tableau/tableauPage";
    private final UserBean userData;
    private final TableauService tableauService;

    public TableauController(UserBean userData, TableauService tableauService) {
        this.userData = userData;
        this.tableauService = tableauService;
    }

    /**
     * 取得首頁應顯示的各儀表板資訊
     */
    @RequestMapping("/get-tableau-data-by-user")
    @ResponseBody
    public List<TableauDataCase> getUserdata() {
        List<TableauDataCase> resultList = new ArrayList<>();
        try {
            resultList = tableauService.findTableauDataByUser(userData.getUserId());
        } catch (Exception e) {
            log.error("tableau儀錶板查詢失敗 - " + ExceptionUtility.getStackTrace(e));
            setSystemMessage(getQueryFailMessage());
            return ObjectUtility.normalizeObject(resultList);
        }
        return ObjectUtility.normalizeObject(resultList);
    }


    /**
     * 取得 tableau ticket
     */
    @RequestMapping(path = "/get-ticket")
    @ResponseBody
    public Map<String, String> getTrustedTicket() {
        Map<String, String> map = new HashMap<>();
        try {
            map = tableauService.getTrustedTicket();
        } catch (Exception e) {
            log.error("tableau ticket error - " + ExceptionUtility.getStackTrace(e));
            setSystemMessage(getQueryFailMessage());
            map.put("ticket","no");
            return ObjectUtility.normalizeObject(map);
        }
        return ObjectUtility.normalizeObject(map);
    }


    /**
     * 取得首頁應顯示的各儀表板資訊
     */
    @RequestMapping("/get-tableau-data")
    @ResponseBody
    public List<TableauDataCase> getAllTableauData() {
        List<TableauDataCase> resultList = new ArrayList<>();
        try {
            resultList = tableauService.findTableauData();
        } catch (Exception e) {
            log.error("tableau儀錶板查詢失敗 - " + ExceptionUtility.getStackTrace(e));
            setSystemMessage(getQueryFailMessage());
        }
        return ObjectUtility.normalizeObject(resultList);

    }


    /**
     * 取得點選menu轉導的頁面，用來window.open相對應的tableau url
     */
    @RequestMapping("/tableau.action")
    public ModelAndView showTableauPage(
            @RequestParam(name = "tableauId") String tableauId
    ) {
        log.info("===========/tableau.action with tableauId");
        return new ModelAndView(QUERY_PAGE);
    }


}
