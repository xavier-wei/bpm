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

import java.util.*;
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
    private final UserBean userData;
    private final EipcodeDao eipcodeDao;
    private final WEBITR_View_flowDao viewFlowDao;

    public void syncFromLdapAndItr() {
        eip0aw060Service.replaceAllView_cpape05m();
        eip0aw070Service.replaceAllPosition();
        eip0aw020Service.insertUsersFromLdap();
        eip0aw030Service.updateAllUsersFromView_cpape05m();
        eip0aw040Service.updateAllDeptsFromView_out_unit();
        eip0aw050Service.updateAllTitleFromView_cpape05m();
    }

    public Eip0aw010Case getEip0aw010Case() {
        if (Optional.ofNullable(userData)
                .map(UserBean::getUserId)
                .isEmpty()) {
            return null;
        }
        List<Eip0aw010Case.ApiResult> result = new ArrayList<>();
        //第一個一定是差勤
        try {
            result.add(getIrtSql());
        } catch (Exception e) {
            log.error("取得差勤資料錯誤{}。", ExceptionUtility.getStackTrace(e));
        }

        result.add(doApi(getSys_api(String.valueOf(2))));

        return ObjectUtility.normalizeObject(Eip0aw010Case.builder()
                .apiResultList(result)
                .interval(eipcodeDao.findByCodeKindCodeNo("SYS_API", "INTERVAL")
                        .map(Eipcode::getCodename)
                        .orElse("60"))
                .build());
    }

    private Eip0aw010Case.ApiResult getIrtSql() {
        String url = eipcodeDao.findByCodeKindCodeNo("SYS_API", "1_CLICK_URL").map(Eipcode::getCodename).orElse(StringUtils.EMPTY);
        return Eip0aw010Case.ApiResult.builder()
                .click_url(url)
                .cnt(viewFlowDao.selectCountByNext_card_id(View_flow.builder().next_card_id(userData.getUserId()).build()).toString()).build();
    }

    private Eip0aw010Case.ApiParams getSys_api(String apiNumber) {
        Eip0aw010Case.ApiParams apiParam = null;
        try {
            List<Eipcode> list = eipcodeDao.findByCodekindScodekindOrderByCodeno("SYS_API", apiNumber);
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
        if(Optional.ofNullable(apiParams).isEmpty()){
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

            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(apiParams.getUrl(), HttpMethod.POST, requestEntity, responseType);
            log.debug(response.toString());
            apiResult.setCnt(Objects.requireNonNull(response.getBody())
                    .get(apiParams.getRes())
                    .toString());
        } catch (Exception e) {
            log.error("SYS_API 呼叫{} 錯誤 {}", ObjectUtility.normalizeObject(apiParams), ExceptionUtility.getStackTrace(e));
        }
        return apiResult;
    }


}
