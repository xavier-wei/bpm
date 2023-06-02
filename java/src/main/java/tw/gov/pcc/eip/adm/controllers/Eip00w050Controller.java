package tw.gov.pcc.eip.adm.controllers;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import tw.gov.pcc.eip.adm.cases.Eip00w050Case;
import tw.gov.pcc.eip.adm.service.ItemService;
import tw.gov.pcc.eip.adm.view.dynatree.DynaTreeBuilder;
import tw.gov.pcc.eip.adm.view.dynatree.parser.ItemParser;
import tw.gov.pcc.eip.dao.ItemsDao;
import tw.gov.pcc.eip.dao.PortalMenuAclDao;
import tw.gov.pcc.eip.dao.Role_aclDao;
import tw.gov.pcc.eip.dao.SystemsDao;
import tw.gov.pcc.eip.domain.CursorDeptAcl;
import tw.gov.pcc.eip.domain.Items;
import tw.gov.pcc.eip.framework.domain.UserBean;
import tw.gov.pcc.eip.framework.spring.controllers.BaseController;
import tw.gov.pcc.eip.util.ExceptionUtility;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 功能管理
 *
 * @author swho
 */
@Controller
@Slf4j
public class Eip00w050Controller extends BaseController {
    public static final String CASE_KEY = "_eip00w050_caseData";
    private static final String QUERY_PAGE = "/eip00w050/eip00w050q";//選擇頁
    private static final String LIST_PAGE = "/eip00w050/eip00w050x";//編輯頁

    private final SystemsDao systemsDao;
    private final PortalMenuAclDao portalMenuAclDao;
    private final ItemsDao itemsDao;
    private final ItemService itemService;
    private final UserBean userData;

    public Eip00w050Controller(SystemsDao systemsDao, PortalMenuAclDao portalMenuAclDao, ItemsDao itemsDao, ItemService itemService, UserBean userData, Role_aclDao roleAclDao) {
        this.systemsDao = systemsDao;
        this.portalMenuAclDao = portalMenuAclDao;
        this.itemsDao = itemsDao;
        this.itemService = itemService;
        this.userData = userData;
    }

    /**
     * 進入頁面
     *
     * @return 頁面
     */
    @RequestMapping(value = "/Eip00w050_enter.action")
    public String enter(ModelMap m, @ModelAttribute(CASE_KEY) Eip00w050Case caseData) {
        log.debug("導向 Eip00w050_enter ");
        m.put("systems", systemsDao.selectOwnSystem(userData.getUserId()));
        return QUERY_PAGE;
    }


    @RequestMapping(value = "/Eip00w050_list.action")
    public String list(@ModelAttribute(CASE_KEY) Eip00w050Case caseData, ModelMap m) {
        try {
            List<CursorDeptAcl> da = portalMenuAclDao.findDeptAcl(caseData.getSys_id(), "");
            m.put("sys_id", caseData.getSys_id());
            m.put("items", DynaTreeBuilder.build(ItemParser.parser(da)));
        } catch (Exception e) {
            log.error("功能管理查詢失敗 - {}", ExceptionUtility.getStackTrace(e));
        }
        return LIST_PAGE;
    }

    @RequestMapping(value = "/Eip00w050_info.action", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, String> info(@RequestBody Eip00w050Case caseData) throws IOException {
        Items item = itemsDao.selectByKey(caseData.getItem_id());
        return Map.of(
                "item_id", StringUtils.defaultString(item.getItem_id()),
                "item_name", StringUtils.defaultString(item.getItem_name()),
                "hyperlink", StringUtils.defaultString(item.getHyperlink()),
                "sub_link", StringUtils.defaultString(item.getSub_link()),
                "open_window", StringUtils.defaultString(item.getIs_openwindow()),
                "sort", StringUtils.defaultString(item.getSort_order()
                        .toString()),
                "disable", StringUtils.defaultString(item.getDisable())
        );
    }


    @RequestMapping(value = "/Eip00w050_save.action", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String save(String sys_id, Items item, String pid)
            throws IOException {
        try {
            LocalDateTime today = LocalDateTime.now();
            List<CursorDeptAcl> da = portalMenuAclDao.findDeptAcl(sys_id, "");
            String newItemId = itemService.getNewItemIdByLevel(da, new BigDecimal((pid
                    .length() / 2) + 1), pid);
            item.setItem_id(newItemId);
            item.setItem_id_p(pid);
            item.setCreate_user_id(userData.getUserId());
            item.setCreate_timestamp(today);
            itemService.saveOrUpdate(item);
            setSystemMessage(getUpdateSuccessMessage());
        } catch (Exception e) {
            log.error("功能管理儲存失敗 - {}", ExceptionUtility.getStackTrace(e));
            setSystemMessage(getUpdateFailMessage());
        }
        return ajaxTree(sys_id);
    }


    @RequestMapping(value = "/Eip00w050_update.action", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String update(String sysId, Items item) throws IOException {
        try {
            Items i = itemsDao.selectByKey(item.getItem_id());
            i.setItem_name(item.getItem_name());
            i.setHyperlink(item.getHyperlink());
            i.setSort_order(item.getSort_order());
            i.setDisable(item.getDisable());
            i.setSub_link(item.getSub_link());
            i.setIs_openwindow(item.getIs_openwindow());
            i.setModify_user_id(userData.getUserId());
            i.setModify_timestamp(LocalDateTime.now());
            itemService.saveOrUpdate(i);
            setSystemMessage(getUpdateSuccessMessage());
        } catch (Exception e) {
            log.error("功能管理更新失敗 - {}", ExceptionUtility.getStackTrace(e));
            setSystemMessage(getUpdateFailMessage());
        }
        return ajaxTree(sysId);
    }


    @RequestMapping(value = "/Eip00w050_delete.action", produces = MediaType.APPLICATION_JSON_VALUE)
    public String delete(String item_id,
                         ModelMap m, @ModelAttribute(CASE_KEY) Eip00w050Case caseData) {
        try {
            List<Items> items = itemsDao.findItemAndChild(item_id);
            this.itemService.deleteItemsAndRoleAcls(items);
            setSystemMessage(getUpdateSuccessMessage());
            clean(caseData);
        } catch (Exception e) {
            log.error("功能管理刪除失敗 - {}", ExceptionUtility.getStackTrace(e));
            setSystemMessage(getUpdateFailMessage());
        }
        return list(caseData, m);
    }


    private String ajaxTree(String sys_id) {
        List<CursorDeptAcl> da = portalMenuAclDao.findDeptAcl(sys_id, "");
        List<String> tree = DynaTreeBuilder.build(ItemParser.parser(da));
        return String.join("\n", tree);
    }

    private void clean(Eip00w050Case eip00w050Case) {
        eip00w050Case.setItem_name(StringUtils.EMPTY);
        eip00w050Case.setHyperlink(StringUtils.EMPTY);
        eip00w050Case.setSort_order("0");
        eip00w050Case.setSub_link(StringUtils.EMPTY);
        eip00w050Case.setDisable(StringUtils.EMPTY);
    }
}
