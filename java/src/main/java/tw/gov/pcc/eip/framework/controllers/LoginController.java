package tw.gov.pcc.eip.framework.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.keycloak.KeycloakSecurityContext;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import tw.gov.pcc.common.helper.UserSessionHelper;
import tw.gov.pcc.common.services.LoginService;
import tw.gov.pcc.eip.common.cases.IndexCase;
import tw.gov.pcc.eip.dao.EipcodeDao;
import tw.gov.pcc.eip.dao.MsgdataDao;
import tw.gov.pcc.eip.dao.MsgdepositdirDao;
import tw.gov.pcc.eip.domain.Eipcode;
import tw.gov.pcc.eip.framework.cases.DatatableCase;
import tw.gov.pcc.eip.framework.domain.UserBean;
import tw.gov.pcc.eip.framework.spring.controllers.BaseController;
import tw.gov.pcc.eip.framework.spring.support.FileOutputView;
import tw.gov.pcc.eip.msg.cases.Eip01w030Case;
import tw.gov.pcc.eip.msg.cases.Eip01w040Case;
import tw.gov.pcc.eip.msg.cases.Eip01wPopCase;
import tw.gov.pcc.eip.msg.controllers.Eip01w030Controller;
import tw.gov.pcc.eip.msg.controllers.Eip01w040Controller;
import tw.gov.pcc.eip.services.Eip01w040Service;
import tw.gov.pcc.eip.tableau.cases.TableauDataCase;
import tw.gov.pcc.eip.tableau.controllers.TableauController;
import tw.gov.pcc.eip.tableau.service.TableauService;
import tw.gov.pcc.eip.util.BeanUtility;
import tw.gov.pcc.eip.util.DateUtility;
import tw.gov.pcc.eip.util.ExceptionUtility;
import tw.gov.pcc.eip.util.ObjectUtility;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 使用者登入
 * 顯示首頁相關資料
 *
 * @author swho
 */
@Controller
@AllArgsConstructor
@Slf4j
public class LoginController extends BaseController {
    public static final String CASE_KEY = "_login_caseData";
    private static final String INDEX_PAGE = "/index";
    private static final String ERROR = "/unauthorized";
    private static final String LOGOUT_PAGE = "/logout";
    private static final String MSG_AUTO_LOGIN = "msg.framework.autoReLogin";
    private final LoginService loginService;
    private final MsgdataDao msgdataDao;
    private final UserBean userData;
    private final Eip01w040Service eip01w040Service;
    private final EipcodeDao eipcodeDao;
    private final MsgdepositdirDao msgdepositdirDao;
    private final Eip01w030Controller eip01w030Controller;
    private final Eip01w040Controller eip01w040Controller;
    private final TableauController tableauController;
    private final TableauService tableauService;


    private static Comparator<Eip01wPopCase> getEip01wPopCaseComparator(DatatableCase<Eip01wPopCase> datatableCase) {
        return (r1, r2) -> {
            if (CollectionUtils.isEmpty(datatableCase.getOrder())) {
                return 0;
            }
            DatatableCase.Order order = datatableCase.getOrder()
                    .stream()
                    .findFirst()
                    .orElse(new DatatableCase.Order());

            String columnValue1 = Optional.ofNullable(BeanUtility.getBeanProperty(r1, order.getColumn()))
                    .orElse(StringUtils.EMPTY)
                    .toString();
            String columnValue2 = Optional.ofNullable(BeanUtility.getBeanProperty(r2, order.getColumn()))
                    .orElse(StringUtils.EMPTY)
                    .toString();
            int sortOrder = order.getDir() == DatatableCase.Order.DIR.desc ? -1 : 1;
            return sortOrder * columnValue1.compareTo(columnValue2);
        };
    }

    @RequestMapping({"/Login.action", "/"})
    public String login(HttpServletRequest request) {
        try {
            if (loginService.getUserLoginData(userData, request)) {
                // 取得 登入日期 及 登入時間
                String loginDateTime = DateUtility.getNowChineseDateTime(false);
                userData.setLoginDate(DateUtility.splitChineseDateTime(loginDateTime, true));
                userData.setLoginTime(DateUtility.splitChineseDateTime(loginDateTime, false));
                // 將使用者資料存入 Session
                UserSessionHelper.setUserData(request, userData);
                // 如果是 自動重新登入, 則於訊息區顯示自動重新登入訊息

                String reLogin = request.getParameter("reLogin");
                if (StringUtils.isNotBlank(reLogin)) {
                    setSystemMessage(getMessage(MSG_AUTO_LOGIN));
                }

                return "redirect:/LoginForward.action";
            } else {
                return ERROR;
            }
        } catch (Exception e) {
            log.error("使用者登入失敗 - {}", ExceptionUtility.getStackTrace(e));
            return ERROR;
        }
    }

    @RequestMapping("/LoginForward.action")
    public String loginForward(@ModelAttribute(CASE_KEY) IndexCase indexCase) {
        return INDEX_PAGE;
    }
    
    @RequestMapping("/Logout.action")
    public String logout(HttpServletRequest request, HttpSession session) {
        try {
            KeycloakSecurityContext attribute = (KeycloakSecurityContext) session.getAttribute(KeycloakSecurityContext.class.getName());
            String keycloakLogoutUrl = attribute
                    .getIdToken()
                    .getIssuer() + "/protocol/openid-connect/logout";
            
            UserSessionHelper.logoutUser(request);
            return "redirect:" + keycloakLogoutUrl;
        } catch (Exception e) {
            log.error("使用者登出失敗 - {}", ExceptionUtility.getStackTrace(e));
            return LOGOUT_PAGE;
        }
    }

    /**
     * 公告
     *
     * @return
     */
    @RequestMapping(value = "/Common_getMsgdata.action", method = RequestMethod.POST)
    @ResponseBody
    public DatatableCase<Eip01wPopCase> getMsgdata(@RequestBody DatatableCase<Eip01wPopCase> datatableCase) throws InterruptedException {
        List<Eip01wPopCase> dataList = msgdataDao.getEip01w030DataList(userData.getDeptId(), null, null);
        List<Eip01wPopCase> filterList = dataList.stream()
                .filter(x -> StringUtils.containsAnyIgnoreCase(x.getSubject() + "@" + x.getMsgtype() + "@" + x.getReleasedt() + "@" + x.getContactunit(), datatableCase.getSearch()
                        .getValue()))
                .sorted(getEip01wPopCaseComparator(datatableCase))
                .collect(Collectors.toList());
        BigDecimal recordsTotal = new BigDecimal(dataList.size());
        BigDecimal recordsFiltered = new BigDecimal(filterList.size());
        BigDecimal length = Optional.ofNullable(datatableCase.getLength())
                .filter(x -> x.compareTo(BigDecimal.ZERO) > 0)
                .orElse(new BigDecimal(filterList.size()));

        List<Eip01wPopCase> subList = filterList.stream()
                .skip(datatableCase.getStart()
                        .intValue())
                .limit(length.intValue())
                .collect(Collectors.toList());

        datatableCase.setData(subList);
        datatableCase.setRecordsTotal(recordsTotal);
        datatableCase.setRecordsFiltered(recordsFiltered);
        return ObjectUtility.normalizeObject(datatableCase);
    }


    /**
     * 透過ajax 查詢明細資料 公告事項
     *
     * @param fseq 項次
     * @return
     */
    @RequestMapping("/Common_Eip01w030GetDetail.action")
    @ResponseBody
    public Eip01wPopCase eip01w030GetDetail(@RequestParam("fseq") String fseq) {
        return eip01w030Controller.getDetail(fseq);
    }


    /**
     * 透過ajax 查詢明細資料 下載專區
     *
     * @param fseq 項次
     * @return
     */
    @RequestMapping("/Common_Eip0w040Detail.action")
    @ResponseBody
    public Eip01wPopCase eip01w040Detail(@RequestParam("fseq") String fseq) {
        return eip01w040Controller.detail(fseq);
    }

    /**
     * 公告事項 檔案下載
     *
     * @param caseData
     * @return
     */
    @RequestMapping("/Common_eip01w030GetFile.action")
    public ModelAndView download(@ModelAttribute(CASE_KEY) IndexCase caseData) {
        Eip01w030Case eip01w030Case = new Eip01w030Case();
        BeanUtility.copyProperties(eip01w030Case, caseData);
        return Optional.of(eip01w030Controller.download(eip01w030Case))
                .filter(x -> Optional.ofNullable(x.getView()).map(view -> view.getClass().isAssignableFrom(FileOutputView.class)).orElse(false))
                .orElse(new ModelAndView(INDEX_PAGE));
    }

    /**
     * 下載
     *
     * @return
     */
    @RequestMapping(value = "/Common_getDownloaddata.action", method = RequestMethod.POST)
    @ResponseBody
    public DatatableCase<Eip01wPopCase> getDownloaddata(@RequestBody DatatableCase<Eip01wPopCase> datatableCase) throws InterruptedException {

        String deptId = userData.getDeptId();
        List<Eip01wPopCase> dataList = msgdepositdirDao.getTree(deptId)
                .parallelStream()
                .map(x -> {
                    Eip01w040Case pathCaseData = new Eip01w040Case();
                    pathCaseData.setKey(StringUtils.trimToEmpty(x.getExisthier()));
                    eip01w040Service.pathQuery(pathCaseData, deptId);
                    return pathCaseData.getQryList()
                            .stream()
                            .peek(
                                    d -> d.setContactunit(x.getFilepath())
                            )
                            .collect(Collectors.toList());
                })
                .flatMap(List::stream)
                .collect(Collectors.toList());

        List<Eip01wPopCase> filterList = dataList.stream()
                .filter(x -> StringUtils.containsAnyIgnoreCase(x.getUpddt() + "@" + x.getSubject() + "@" + x.getMsgtype() + "@" + x.getReleasedt() + "@" + x.getContactunit() + "@" + x.getMcontent(), datatableCase.getSearch()
                        .getValue()))
                .sorted(getEip01wPopCaseComparator(datatableCase))
                .collect(Collectors.toList());
        BigDecimal recordsTotal = new BigDecimal(dataList.size());
        BigDecimal recordsFiltered = new BigDecimal(filterList.size());
        BigDecimal length = Optional.ofNullable(datatableCase.getLength())
                .filter(x -> x.compareTo(BigDecimal.ZERO) > 0)
                .orElse(new BigDecimal(filterList.size()));

        List<Eip01wPopCase> subList = filterList.stream()
                .skip(datatableCase.getStart()
                        .intValue())
                .limit(length.intValue())
                .collect(Collectors.toList());

        datatableCase.setData(subList);
        datatableCase.setRecordsTotal(recordsTotal);
        datatableCase.setRecordsFiltered(recordsFiltered);
        return ObjectUtility.normalizeObject(datatableCase);
    }


    @ModelAttribute("sys_site")
    public List<Eipcode> getSys_site() {
        return ObjectUtility.normalizeObject(eipcodeDao.findByCodeKindOrderByScodeno("SYS_SITE"));
    }



    /**
     * 取得首頁應顯示的各儀表板資訊
     */
    @RequestMapping("/Common_getTableauDataByUser.action")
    @ResponseBody
    public List<TableauDataCase> getTableauUserdata( ) {
        return tableauController.getUserdata();

    }

    /**
     * 取得 tableau ticket
     */
    @RequestMapping("/Common_getTableauTicket.action")
    @ResponseBody
    public Map<String, String> getTableauTicket( ) {
        return tableauController.getTrustedTicket();
    }


    /**
     * 取得首頁應顯示的各儀表板資訊
     */
    @RequestMapping("/Common_getAllTableauData.action")
    @ResponseBody
    public List<TableauDataCase> getAllTableauData( ) {
        return tableauController.getAllTableauData();
    }


    /**
     * 取得儀錶板圖片
     */
    @RequestMapping(path = "/Common_getTableauPicture.action", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public TableauDataCase getTableauPicture(@RequestBody TableauDataCase caseData) throws IOException {
        return tableauService.getTableauPicture(caseData.getDashboardFigId());
    }
}
