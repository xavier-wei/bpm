package tw.gov.pcc.common.services;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import tw.gov.pcc.common.domain.SystemFunction;
import tw.gov.pcc.common.helper.EnvFacadeHelper;
import tw.gov.pcc.common.util.ExceptionUtil;
import tw.gov.pcc.eip.util.StringUtility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Framework 初始化用的 Service
 *
 * @author Goston
 */
@Service
public class SysfuncService {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(SysfuncService.class);

    /**
     * 建立系統功能 url pattern map，讓原本底層檢核程式使用
     *
     * @param menuMapList 選單MAP(key = url)
     * @return map
     */
    public Map<String, SystemFunction> buildSystemFunctionMap(List<HashMap<String, String>> menuMapList) {
        // non-portal functions
        SystemFunction login = new SystemFunction();
        login.setItemId(EnvFacadeHelper.getSystemId());
        login.setItemName(EnvFacadeHelper.getSystemName());
        login.setUrl("/Login.action");
        login.setUrlDesc("系統登入");
        login.setUrlPattern(Pattern.compile("^/Login\\.action$", Pattern.CASE_INSENSITIVE));
        SystemFunction forward = new SystemFunction();
        forward.setItemId(EnvFacadeHelper.getSystemId());
        forward.setItemName(EnvFacadeHelper.getSystemName());
        forward.setUrl("/LoginForward.action");
        forward.setUrlDesc("系統登入成功");
        forward.setUrlPattern(Pattern.compile("^/LoginForward\\.action$", Pattern.CASE_INSENSITIVE));
        SystemFunction common = new SystemFunction();
        common.setItemId(EnvFacadeHelper.getSystemId());
        common.setItemName(EnvFacadeHelper.getSystemName());
        common.setUrl("/Common_enter.action");
        common.setUrlDesc("通用功能");
        common.setUrlPattern(Pattern.compile("^/Common_[a-zA-Z0-9]*\\.action$", Pattern.CASE_INSENSITIVE));
        List<SystemFunction> list = new ArrayList<>();
        list.add(login);
        list.add(forward);
        list.add(common);
        try {
            // build urls' pattern
            menuMapList.forEach(map -> {
                SystemFunction systemFunction = new SystemFunction();
                systemFunction.setItemId(map.get("funcId")); //以 funcId當作固定itemId
                systemFunction.setItemName(map.get("itemName"));
                systemFunction.setUrl(map.get("url"));
                String urlRegex = systemFunction.getUrl();
                if (StringUtils.contains(systemFunction.getUrl(), "_") && StringUtils.contains(systemFunction.getUrl(), ".action")) {
                    urlRegex = StringUtils.substringBeforeLast(urlRegex, "_") + "_*." + StringUtils.substringAfterLast(urlRegex, ".");
                }
                urlRegex = "^" + StringUtils.replaceEach(urlRegex, new String[]{"/", "*", "."}, new String[]{"\\/", "[a-zA-Z0-9]*", "\\."}) + "$";
                urlRegex = StringUtility.normalizeString(urlRegex);
                Pattern urlPattern = Pattern.compile(urlRegex, Pattern.CASE_INSENSITIVE);
                systemFunction.setUrlPattern(urlPattern);
                systemFunction.setUrlDesc(systemFunction.getItemName());
                list.add(systemFunction);
            });
        } catch (Exception e) {
            log.error("讀取Portal功能項錯誤:" + ExceptionUtil.getStackTrace(e));
        }
        return list.stream()
                .filter(x -> StringUtils.isNotBlank(x.getUrl()))
                .collect(Collectors.toMap(SystemFunction::getUrl, x -> x, (fun1, fun2) -> {
                    log.info(String.format("功能出現重複定義Function ID(%s): %s <-> %s ", fun1.getItemId(), fun1.getItemName(), fun1.getItemName()));
                    return fun1;
                }));
    }
}
