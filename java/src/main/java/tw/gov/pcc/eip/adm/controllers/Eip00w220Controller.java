package tw.gov.pcc.eip.adm.controllers;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import tw.gov.pcc.common.domain.FrameworkUserInfoBean;
import tw.gov.pcc.common.domain.Menu;
import tw.gov.pcc.common.domain.MenuItem;
import tw.gov.pcc.common.helper.UserSessionHelper;
import tw.gov.pcc.eip.adm.cases.Eip00w220Case;
import tw.gov.pcc.eip.framework.spring.controllers.BaseController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 網頁導覽
 *
 * @author swho
 */
@Controller
public class Eip00w220Controller extends BaseController {
    public static final String CASE_KEY = "_eip00w220_caseData";
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(Eip00w220Controller.class);
    private static final String QUERY_PAGE = "/eip00w220/eip00w220q";//選擇頁
    private final String templateL2 = "<div class=\"card-body p-3\">\n" +
            "                            <div class=\"d-flex flex-wrap col-12 p-0 col\">\n" +

            "%s" +
            "                            </div>\n" +
            "                        </div>";
    private final String templateL1 = "<div class=\"pl-0 pr-9 container\">\n" +
            "                <div class=\"col-12 p-0 mb-3 col\">\n" +
            "                    <div class=\"card\">\n" +
            "                        <div class=\"card-header py-1 px-3\">\n" +
            "                            <h4 class=\"m-0\">%s</h4>\n" +
            "                        </div>\n" +
            templateL2 +
            "                    </div>\n" +
            "                </div>\n" +
            "            </div>";

    private final String templateL3 = "<div class=\"px-0 pt-2 pb-2 col col-4\"><a\n" +
            "                                            href=\"%s\"\n" +
            "                                            class=\"\">%s</a>\n" +
            "                                    </div>";


    /**
     * 進入頁面
     *
     * @return 頁面
     */
    @RequestMapping(value = "/Common_sitemap.action")
    public String enter(ModelMap m, HttpServletRequest request, @ModelAttribute(CASE_KEY) Eip00w220Case eip00w220Case) {
        log.debug("導向 Common_sitemap ");
        FrameworkUserInfoBean frameworkUserData = UserSessionHelper.getFrameworkUserData(request);
        Menu menu = frameworkUserData.getMenu();
        List<MenuItem> menuList = menu.getMenuItemList();
        eip00w220Case.setRawResult(findMenuList(menuList));
        return QUERY_PAGE;
    }

    private String findMenuList(List<MenuItem> menuItemList) {
        StringBuilder sb = new StringBuilder();
        menuItemList
                .forEach(x -> {
                    sb.append(findMenuItem(x));
                });
        return sb.toString();
    }

    private String findMenuItem(MenuItem x) {
        StringBuilder sb = new StringBuilder();
        if (CollectionUtils.isEmpty(x.getSub())) {
            if (x.getLevelNo()
                    .equals("2")) {

                if (StringUtils.isNotEmpty(x.getUrl())) {
                    sb.append(String.format(templateL1, x.getFunctionName(), String.format(templateL3, x.getUrl(), x.getFunctionName())));
                } else {
                    sb.append(String.format(templateL1, x.getFunctionName(), ""));
                }
            } else {
                sb.append(String.format(templateL3, x.getUrl(), x.getFunctionName()));
            }
        } else {
            sb.append(String.format(templateL1, x.getFunctionName(), findMenuList(x.getSub())));
        }
        return sb.toString();
    }


}
