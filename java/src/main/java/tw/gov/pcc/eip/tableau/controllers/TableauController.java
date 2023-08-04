package tw.gov.pcc.eip.tableau.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import tw.gov.pcc.eip.adm.cases.Eip00w070Case;
import tw.gov.pcc.eip.framework.domain.UserBean;
import tw.gov.pcc.eip.framework.spring.controllers.BaseController;
import tw.gov.pcc.eip.tableau.cases.TableauDataCase;
import tw.gov.pcc.eip.tableau.service.TableauService;
import tw.gov.pcc.eip.util.ExceptionUtility;
import tw.gov.pcc.eip.util.ObjectUtility;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * index頁面，tableau儀錶板顯示
 *
 * @author Susan
 */
@Controller
public class TableauController extends BaseController {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(TableauController.class);
    private final UserBean userData;
    private final TableauService tableauService;

    public TableauController(UserBean userData, TableauService tableauService) {
        this.userData = userData;
        this.tableauService = tableauService;
    }

    /**
     * 取得首頁應顯示的各儀表板資訊
     */
    @RequestMapping("/get-tableau-data")
    @ResponseBody
    public List<TableauDataCase> getUserdata() {
        List<TableauDataCase> resultList = new ArrayList<>();
        try {
            resultList = tableauService.findTableauData(userData.getUserId());
        } catch (Exception e) {
            log.error("tableau儀錶板查詢失敗 - " + ExceptionUtility.getStackTrace(e));
            setSystemMessage(getQueryFailMessage());
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
        }
        return ObjectUtility.normalizeObject(map);
    }

}
