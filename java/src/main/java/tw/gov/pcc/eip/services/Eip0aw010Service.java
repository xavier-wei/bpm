package tw.gov.pcc.eip.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.PropertyPlaceholderHelper;
import org.springframework.web.client.RestTemplate;
import tw.gov.pcc.eip.common.cases.Eip0aw010Case;
import tw.gov.pcc.eip.dao.EipcodeDao;
import tw.gov.pcc.eip.dao.WEBITR_View_flowDao;
import tw.gov.pcc.eip.domain.Eipcode;
import tw.gov.pcc.eip.domain.View_flow;
import tw.gov.pcc.eip.framework.domain.UserBean;
import tw.gov.pcc.eip.util.BeanUtility;
import tw.gov.pcc.eip.util.ExceptionUtility;
import tw.gov.pcc.eip.util.ObjectUtility;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 清空並同步 cpap.dbo.view_cpape05m -> eip.dbo.view_cpape05m
 * 清空並同步 webitr.dbo.position -> eip.dbo.position
 * 轉換 ldap 資料 並新增到 eip.dbo.users
 * 同步 使用者資料
 * 同步 部門資料
 * 同步 職稱資料
 *
 * @author swho
 */
@Service
@AllArgsConstructor
@Slf4j
public class Eip0aw010Service {

    private static final PropertyPlaceholderHelper helper = new PropertyPlaceholderHelper("${", "}");
    private final Eip0aw020Service eip0aw020Service;
    private final Eip0aw030Service eip0aw030Service;
    private final Eip0aw040Service eip0aw040Service;
    private final Eip0aw050Service eip0aw050Service;
    private final Eip0aw060Service eip0aw060Service;
    private final Eip0aw070Service eip0aw070Service;
    private final Eip0aw080Service eip0aw080Service;
    private final UserBean userData;
    private final EipcodeDao eipcodeDao;
    private final WEBITR_View_flowDao viewFlowDao;

    public void syncFromLdapAndItr() {
        eip0aw060Service.replaceAllView_cpape05m();
        eip0aw070Service.replaceAllPosition();
        eip0aw080Service.replaceAllView_oup_unit();
        eip0aw020Service.insertUsersFromLdap();
        eip0aw030Service.updateAllUsersFromView_cpape05m();
        eip0aw040Service.updateAllDeptsFromView_oup_unit();
        eip0aw050Service.updateAllTitleFromView_cpape05m();
    }

    public Eip0aw010Case getEmptyEip0aw010Case(String index) {
        Eip0aw010Case.ApiResult apiResult = Eip0aw010Case.ApiResult.builder().cnt(StringUtils.EMPTY).click_url(eipcodeDao.findByCodeKindCodeNo("SYS_API", index + "_CLICK_URL").map(Eipcode::getCodename).orElse(StringUtils.EMPTY)).build();
        return ObjectUtility.normalizeObject(Eip0aw010Case.builder()
                .apiResult(apiResult)
                .interval(eipcodeDao.findByCodeKindCodeNo("SYS_API", "INTERVAL")
                        .map(Eipcode::getCodename)
                        .orElse("60"))
                .build());
    }

    public Eip0aw010Case getEip0aw010Case() {
        Eip0aw010Case eip0aw010Case = getEmptyEip0aw010Case("1");
        Eip0aw010Case.ApiResult apiResult = getIrtSql();
        eip0aw010Case.getApiResult().setCnt(apiResult.getCnt());
        return eip0aw010Case;
    }

    private Eip0aw010Case.ApiResult getIrtSql() {
        String url = StringUtils.EMPTY;
        try {
            return Eip0aw010Case.ApiResult.builder()
                    .cnt(viewFlowDao.selectCountByNext_card_id(View_flow.builder().next_card_id(userData.getUserId()).build()).toString()).build();
        } catch (Exception e) {
            log.error("取得差勤資料錯誤{}。", ExceptionUtility.getStackTrace(e));
        }
        return Eip0aw010Case.ApiResult.builder().cnt("0").click_url(url).build();
    }

    public Eip0aw010Case getEip0aw011Case() {
        Eip0aw010Case eip0aw010Case = getEmptyEip0aw010Case("2");
        Eip0aw010Case.ApiResult apiResult = getDocApi();
        eip0aw010Case.getApiResult().setCnt(apiResult.getCnt());
        return eip0aw010Case;
    }

    private Eip0aw010Case.ApiResult getDocApi() {
        return doApi(getApiParams("2"));
    }

    private Eip0aw010Case.ApiParams getApiParams(String index) {
        Eip0aw010Case.ApiParams apiParam = null;
        try {
            List<Eipcode> list = eipcodeDao.findByCodekindScodekindOrderByCodeno("SYS_API", index);
            Map<String, Eipcode> map = list.stream()
                    .collect(Collectors.toMap(x -> StringUtils.substringAfter(x.getCodeno(), "_"), x -> x));
            if (!map.isEmpty()) {
                apiParam = Eip0aw010Case.ApiParams.builder()
                        .url(map.get("URL")
                                .getCodename())
                        .res(map.get("URL")
                                .getScodeno())
                        .click_url(map.get("CLICK_URL")
                                .getCodename())
                        .build();
            }
        } catch (Exception e) {
            log.error("SYS_API 參數讀取錯誤 {}", ExceptionUtility.getStackTrace(e));
        }
        return apiParam;
    }

    private Eip0aw010Case.ApiResult doApi(Eip0aw010Case.ApiParams apiParams) {
        Eip0aw010Case.ApiResult apiResult = new Eip0aw010Case.ApiResult();
        ResponseEntity<Map<String, Object>> response = null;
        if (Optional.ofNullable(apiParams).isEmpty()) {
            return apiResult;
        }
        try {
            apiResult.setClick_url(helper.replacePlaceholders(apiParams.getClick_url(), x -> Objects.requireNonNull(BeanUtility.getBeanProperty(userData, x))
                    .toString()));

            RestTemplate restTemplate = new RestTemplate();

            apiParams.setReq(helper.replacePlaceholders(StringUtils.substringAfter(apiParams.getUrl(), ","), x -> Objects.requireNonNull(BeanUtility.getBeanProperty(userData, x))
                    .toString()));

            apiParams.setUrl(helper.replacePlaceholders(StringUtils.substringBefore(apiParams.getUrl(), ","), x -> Objects.requireNonNull(BeanUtility.getBeanProperty(userData, x))
                    .toString()));

            ParameterizedTypeReference<Map<String, Object>> responseType = new ParameterizedTypeReference<>() {
            };
            HttpEntity<String> requestEntity = null;
            if (StringUtils.isNotBlank(apiParams.getReq())) {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                requestEntity = new HttpEntity<>(apiParams.getReq(), headers);
            }

            response = restTemplate.exchange(apiParams.getUrl(), HttpMethod.POST, requestEntity, responseType);
            log.debug(response.toString());
            apiResult.setCnt(Objects.toString((Objects.requireNonNull(response.getBody())
                    .get(apiParams.getRes())), "0"));
        } catch (Exception e) {
            apiResult.setCnt("0");
            log.error("SYS_API 呼叫{} 傳回{} 錯誤 {}", ObjectUtility.normalizeObject(apiParams), Objects.toString(response, StringUtils.EMPTY), ExceptionUtility.getStackTrace(e));
        }
        return apiResult;
    }


}
