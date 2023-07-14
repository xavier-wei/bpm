package tw.gov.pcc.eip.adm.controllers;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import tw.gov.pcc.eip.adm.cases.Eip00w050Case;
import tw.gov.pcc.eip.services.ItemService;
import tw.gov.pcc.eip.adm.view.dynatree.DynaTreeBuilder;
import tw.gov.pcc.eip.adm.view.dynatree.parser.ItemParser;
import tw.gov.pcc.eip.dao.ItemsDao;
import tw.gov.pcc.eip.dao.PortalMenuAclDao;
import tw.gov.pcc.eip.dao.SystemsDao;
import tw.gov.pcc.eip.domain.CursorAcl;
import tw.gov.pcc.eip.domain.Items;
import tw.gov.pcc.eip.framework.domain.UserBean;
import tw.gov.pcc.eip.framework.spring.controllers.BaseController;
import tw.gov.pcc.eip.util.ExceptionUtility;
import tw.gov.pcc.eip.util.ObjectUtility;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    public Eip00w050Controller(SystemsDao systemsDao, PortalMenuAclDao portalMenuAclDao, ItemsDao itemsDao, ItemService itemService, UserBean userData) {
        this.systemsDao = systemsDao;
        this.portalMenuAclDao = portalMenuAclDao;
        this.itemsDao = itemsDao;
        this.itemService = itemService;
        this.userData = userData;
    }


    @ModelAttribute(CASE_KEY)
    public Eip00w050Case getEip00w050Case() {
        Eip00w050Case caseData = new Eip00w050Case();
        return ObjectUtility.normalizeObject(caseData);
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
            List<CursorAcl> da = portalMenuAclDao.findAllAcl(caseData.getSys_id());
            m.put("sys_id", caseData.getSys_id());
            m.put("items", DynaTreeBuilder.build(ItemParser.parser(da)
                    .stream()
                    .peek(x -> {
                        if (StringUtils.isNotBlank(caseData.getItem_id()) && StringUtils.equalsIgnoreCase(caseData.getItem_id(), x.getId())) {
                            x.setChecked(true);
                        }
                    })
                    .collect(Collectors.toList())));
        } catch (Exception e) {
            log.error("功能管理查詢失敗 - {}", ExceptionUtility.getStackTrace(e));
        }
        return LIST_PAGE;
    }

    @RequestMapping(value = "/Eip00w050_info.action", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, String> info(@RequestBody Eip00w050Case caseData) throws IOException {
        Items item = itemsDao.selectByKey(caseData.getItem_id());
        return ObjectUtility.normalizeObject(Map.of(
                "item_id", StringUtils.defaultString(item.getItem_id()),
                "item_name", StringUtils.defaultString(item.getItem_name()),
                "hyperlink", StringUtils.defaultString(item.getHyperlink()),
                "sub_link", StringUtils.defaultString(item.getSub_link()),
                "sort", StringUtils.defaultString(item.getSort_order()
                        .toString()),
                "disable", StringUtils.defaultString(item.getDisable())
        ));
    }


    @RequestMapping(value = "/Eip00w050_save.action")
    public String save(@Validated @ModelAttribute(CASE_KEY) Eip00w050Case eip00w050Case, BindingResult bindingResult, ModelMap m)
            throws IOException {
        try {
            if (bindingResult.hasErrors()) {
                return list(eip00w050Case, m);
            }
            LocalDateTime today = LocalDateTime.now();
            List<CursorAcl> da = portalMenuAclDao.findAllAcl(eip00w050Case.getSys_id());
            String newItemId = itemService.getNewItemIdByLevel(da, new BigDecimal((eip00w050Case.getPid()
                    .length() / 2) + 1), eip00w050Case.getPid());
            Items items = new Items();
            items.setItem_id(newItemId);
            items.setItem_id_p(eip00w050Case.getPid());
            items.setItem_name(eip00w050Case.getItem_name());
            items.setDisable(eip00w050Case.getDisable());
            items.setHyperlink(eip00w050Case.getHyperlink());
            items.setSub_link(eip00w050Case.getSub_link());
            items.setSort_order(new BigDecimal(eip00w050Case.getSort_order()));
            items.setCreate_user_id(userData.getUserId());
            items.setCreate_timestamp(today);
            itemService.saveOrUpdate(items);
            setSystemMessage(getUpdateSuccessMessage());
        } catch (Exception e) {
            log.error("功能管理儲存失敗 - {}", ExceptionUtility.getStackTrace(e));
            setSystemMessage(getUpdateFailMessage());
        }
        return list(eip00w050Case, m);
    }


    @RequestMapping(value = "/Eip00w050_update.action")
    public String update(@Validated @ModelAttribute(CASE_KEY) Eip00w050Case eip00w050Case, BindingResult bindingResult, ModelMap m) throws IOException {
        try {
            if (bindingResult.hasErrors()) {
                return list(eip00w050Case, m);
            }
            Items items = itemsDao.selectByKey(eip00w050Case.getItem_id());
            items.setItem_name(eip00w050Case.getItem_name());
            items.setHyperlink(eip00w050Case.getHyperlink());
            items.setSort_order(new BigDecimal(eip00w050Case.getSort_order()));
            items.setDisable(eip00w050Case.getDisable());
            items.setSub_link(eip00w050Case.getSub_link());
            items.setModify_user_id(userData.getUserId());
            items.setModify_timestamp(LocalDateTime.now());
            itemService.saveOrUpdate(items);

            eip00w050Case.setPid(items.getItem_id_p()); //返回頁面選擇之前的節點
            setSystemMessage(getUpdateSuccessMessage());
        } catch (Exception e) {
            log.error("功能管理更新失敗 - {}", ExceptionUtility.getStackTrace(e));
            setSystemMessage(getUpdateFailMessage());
        }
        return list(eip00w050Case, m);
    }


    @RequestMapping(value = "/Eip00w050_delete.action", produces = MediaType.APPLICATION_JSON_VALUE)
    public String delete(String item_id,
                         ModelMap m, @ModelAttribute(CASE_KEY) Eip00w050Case caseData, BindingResult bindingResult) {
        try {
            List<Items> items = itemsDao.findItemAndChild(item_id);
            if (items.stream()
                    .anyMatch(x -> StringUtils.equals(x.getItem_id_p(), "root"))) {
                bindingResult.addError(new ObjectError("item_id", "最高層功能無法刪除"));
            }
            if (bindingResult.hasErrors()) {
                return list(caseData, m);
            }
            this.itemService.deleteItemsAndRoleAcls(items);
            setSystemMessage(getUpdateSuccessMessage());
            clean(caseData);
        } catch (Exception e) {
            log.error("功能管理刪除失敗 - {}", ExceptionUtility.getStackTrace(e));
            setSystemMessage(getUpdateFailMessage());
        }
        return list(caseData, m);
    }

    private void clean(Eip00w050Case eip00w050Case) {
        eip00w050Case.setItem_name(StringUtils.EMPTY);
        eip00w050Case.setHyperlink(StringUtils.EMPTY);
        eip00w050Case.setSort_order("0");
        eip00w050Case.setSub_link(StringUtils.EMPTY);
        eip00w050Case.setDisable(StringUtils.EMPTY);
    }
}
