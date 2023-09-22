package tw.gov.pcc.eip.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import tw.gov.pcc.common.domain.FrameworkUserInfoBean;
import tw.gov.pcc.common.domain.MenuItem;
import tw.gov.pcc.common.helper.HttpHelper;
import tw.gov.pcc.common.helper.UserSessionHelper;
import tw.gov.pcc.eip.adm.cases.Eip00w230Case;
import tw.gov.pcc.eip.dao.Pwc_tb_tableau_user_infoDao;
import tw.gov.pcc.eip.domain.Pwc_tb_tableau_user_info;
import tw.gov.pcc.eip.framework.domain.UserBean;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;


/**
 * 設定個人儀表板 service
 *
 * @author swho
 */
@Service
@Slf4j
@AllArgsConstructor
public class Eip00w230Service {

    private static final String THIS_ENTER_ACTION = "/Eip00w230_enter.action"; //大數據儀表板設定功能
    private static final String TABLEAU_ENTER_ACTION = "/tableau_enter.action/"; //抓取儀錶版功能前綴
    private final UserBean userData;
    private final Pwc_tb_tableau_user_infoDao pwc_tb_tableau_user_infoDao;

    public void findCheckedList(Eip00w230Case eip00W230Case, String userId) {
        List<Eip00w230Case.TabCase> tabCaseList = new ArrayList<>();
        List<Eip00w230Case.TabCase> pickTabCaseList = new ArrayList<>();
        List<Eip00w230Case.TabCase> fullTabCaseList = new ArrayList<>();

        eip00W230Case.setTabList(tabCaseList);
        eip00W230Case.setPickTabList(pickTabCaseList);
        eip00W230Case.setFullTabList(fullTabCaseList);

        FrameworkUserInfoBean frameworkUserData = UserSessionHelper.getFrameworkUserData(HttpHelper.getHttpRequest());
        //平面化
        List<MenuItem> tabList = frameworkUserData.getMenu().getMenuItemList().stream()
                .flatMap(x -> deepFind(x).stream())
                .collect(Collectors.toList());
        //取得目前功能位置
        Optional<MenuItem> thisFunc = tabList.stream()
                .filter(x -> StringUtils.startsWithIgnoreCase(x.getUrl(), THIS_ENTER_ACTION))
                .findFirst();
        if (thisFunc.isEmpty()) {
            return;
        }
        //往後找同階或更底階
        tabList = tabList.stream().skip(tabList.indexOf(thisFunc.get()))
                .takeWhile(x -> new BigDecimal(x.getLevelNo()).compareTo(new BigDecimal(thisFunc.get().getLevelNo())) >= 0)
                .filter(x -> StringUtils.startsWithIgnoreCase(x.getUrl(), TABLEAU_ENTER_ACTION))
                .collect(Collectors.toList());
        //轉換
        fullTabCaseList.addAll(convertMenuItemToTabCase(tabList));
        //取得已選定功能
        pickTabCaseList.addAll(convertPwcTabToTabCase(fullTabCaseList, pwc_tb_tableau_user_infoDao.findByUserId(userId)));
        //minus
        tabCaseList.addAll(CollectionUtils.removeAll(fullTabCaseList, pickTabCaseList));
    }

    private List<Eip00w230Case.TabCase> convertPwcTabToTabCase(List<Eip00w230Case.TabCase> tabCaseList, List<Pwc_tb_tableau_user_info> tabList) {
        Map<String, String> tabMap = tabCaseList.stream().collect(Collectors.toMap(Eip00w230Case.TabCase::getDashboard_fig_id, Eip00w230Case.TabCase::getItem_name));
        return tabList.stream().filter(x -> tabMap.containsKey(x.getDashboard_fig_id()))
                .map(x -> Eip00w230Case.TabCase.builder().item_name(tabMap.get(x.getDashboard_fig_id())).dashboard_fig_id(x.getDashboard_fig_id()).build())
                .collect(Collectors.toList());
    }

    private List<Eip00w230Case.TabCase> convertMenuItemToTabCase(List<MenuItem> tabList) {
        //20230915 大數據功能修改樣式，導致抓取錯誤。
        //TABLEAU_ENTER_ACTION/xxxx => TABLEAU_ENTER_ACTION/xxxx.action
        return tabList.stream().map(x -> Eip00w230Case.TabCase.builder().item_name(x.getFunctionName()).dashboard_fig_id(StringUtils.substringBefore(StringUtils.substringAfterLast(x.getUrl(), "/"),".")).build()).collect(Collectors.toList());
    }

    private List<MenuItem> deepFind(MenuItem menuItem) {
        return CollectionUtils.isEmpty(menuItem.getSub()) ? List.of(menuItem) : menuItem.getSub().stream().flatMap(x -> deepFind(x).stream()).collect(Collectors.toList());
    }

    public void deleteAndInsertPwctab(Eip00w230Case eip00W230Case) {
        List<String> pickTabList = Arrays.asList(StringUtils.split(eip00W230Case.getPickTabListString(), ","));
        //清空
        eip00W230Case.getFullTabList().stream().map(x->convertTabCaseToPwcTab(x,new AtomicReference<>(BigDecimal.ZERO))).forEach(pwc_tb_tableau_user_infoDao::deleteByKey);
        //新增
        Map<String, Eip00w230Case.TabCase> tabCaseMap = eip00W230Case.getFullTabList().stream()
                .collect(Collectors.toMap(Eip00w230Case.TabCase::getDashboard_fig_id, x -> x));
        AtomicReference<BigDecimal> order = new AtomicReference<>(BigDecimal.ZERO);
        pickTabList.forEach(tabId -> insertPwcTab(tabCaseMap.get(tabId), order));
    }

    private void insertPwcTab(Eip00w230Case.TabCase tabCase, AtomicReference<BigDecimal> order) {
        Pwc_tb_tableau_user_info pwcTab = convertTabCaseToPwcTab(tabCase, order);
        pwc_tb_tableau_user_infoDao.insert(pwcTab);
    }

    public Pwc_tb_tableau_user_info convertTabCaseToPwcTab(Eip00w230Case.TabCase x, AtomicReference<BigDecimal> order) {
        return Pwc_tb_tableau_user_info.builder()
                .user_id(userData.getUserId())
                .create_time(LocalDateTime.now())
                .update_time(LocalDateTime.now())
                .sort_order(order.accumulateAndGet(BigDecimal.ONE, BigDecimal::add))
                .department_id(userData.getDeptId())
                .dashboard_fig_id(x.getDashboard_fig_id())
                .build();
    }


}
