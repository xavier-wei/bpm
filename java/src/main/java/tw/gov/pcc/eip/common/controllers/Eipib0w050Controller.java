package tw.gov.pcc.eip.common.controllers;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import tw.gov.pcc.eip.common.cases.Eipib0w050Case;
import tw.gov.pcc.eip.common.services.Eipib0w050Service;
import tw.gov.pcc.eip.framework.domain.UserBean;
import tw.gov.pcc.eip.framework.spring.controllers.BaseController;
import tw.gov.pcc.eip.util.ExceptionUtility;
import tw.gov.pcc.eip.util.ObjectUtility;

/**
 * 參數代號維護作業
 *
 * @author vita
 */
@Controller
public class Eipib0w050Controller extends BaseController {
    public static final String CASE_KEY = "_eipib0w050Controller_caseData";
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(Eipib0w050Controller.class);
    private static final String QUERY_PAGE = "/eipib0w050/eipib0w050q";//選擇頁
    private static final String DETAIL_PAGE = "/eipib0w050/eipib0w050x";//明細頁
    private static final String UPDATE_PAGE = "/eipib0w050/eipib0w050c";//修改頁

    private UserBean userData;
    @Autowired
    private Eipib0w050Service eipib0W050Service;

    @ModelAttribute(CASE_KEY)
    public Eipib0w050Case getBeib0w050Case() {
        Eipib0w050Case caseData = new Eipib0w050Case();
        eipib0W050Service.initSelectList(caseData);
        return ObjectUtility.normalizeObject(caseData);
    }

    /**
     * 進入頁面
     *
     * @return
     */
    @RequestMapping("/Code_enter.action")
    public String enter() {
        log.debug("導向    Code_enter 參數代號維護作業");
        return QUERY_PAGE;
    }

    @RequestMapping("/Code_query.action")
    public String query(@ModelAttribute(CASE_KEY) Eipib0w050Case caseData) {
        eipib0W050Service.getCodeDetail(caseData);
        return DETAIL_PAGE;
    }

    @RequestMapping("/Code_update.action")
    public String update(@ModelAttribute(CASE_KEY) Eipib0w050Case caseData) {
        if (caseData.isDoUpdate()) {
            try {
                eipib0W050Service.updateCode(caseData);
                setSystemMessage(getUpdateSuccessMessage());
                eipib0W050Service.getCodeDetail(caseData);
                return DETAIL_PAGE;
            } catch (RuntimeException r) {
                log.error("資料修改失敗 - 主代號類別已存在" + ExceptionUtility.getStackTrace(r));
                setSystemMessage(getUpdateFailMessage());
            }
        }
        return UPDATE_PAGE;
    }

    @RequestMapping("/Code_insert.action")
    public String insert(@ModelAttribute(CASE_KEY) Eipib0w050Case caseData) {
        //導向新增明細頁(無資料)
        if ("0".equals(caseData.getCodekind())) {
            caseData.setCodekind(null);
            return DETAIL_PAGE;
        }
        //導向新增明細頁(有資料)
        if (StringUtils.isEmpty(caseData.getCodeno())) {
            eipib0W050Service.getCodeDetail(caseData);
            return DETAIL_PAGE;
        }
        //執行新增功能
        try {
            eipib0W050Service.insertCode(caseData);
        } catch (RuntimeException r) {
            log.error("資料新增失敗 - 主代號類別和主代號已存在" + ExceptionUtility.getStackTrace(r));
            setSystemMessage(getSaveFailMessage());
            eipib0W050Service.getCodeDetail(caseData);
            return DETAIL_PAGE;
        }
        setSystemMessage(getSaveSuccessMessage());
        eipib0W050Service.getCodeDetail(caseData);
        return DETAIL_PAGE;
    }

    @RequestMapping("/Code_delete.action")
    public String delete(@ModelAttribute(CASE_KEY) Eipib0w050Case caseData) {
        eipib0W050Service.deleteCode(caseData);//delete
        eipib0W050Service.getCodeDetail(caseData);//停留在明細頁
        return DETAIL_PAGE;
    }
}
