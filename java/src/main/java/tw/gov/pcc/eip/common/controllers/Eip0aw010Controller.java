package tw.gov.pcc.eip.common.controllers;


import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.PropertyPlaceholderHelper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import tw.gov.pcc.eip.dao.EipcodeDao;
import tw.gov.pcc.eip.dao.View_flowDao;
import tw.gov.pcc.eip.domain.Eipcode;
import tw.gov.pcc.eip.domain.View_flow;
import tw.gov.pcc.eip.framework.domain.UserBean;
import tw.gov.pcc.eip.util.BeanUtility;
import tw.gov.pcc.eip.util.ExceptionUtility;
import tw.gov.pcc.eip.util.ObjectUtility;

/**
 * 讀取 TITLE 介接其他系統API資料
 *
 * @author swho
 */
@Controller
@Slf4j
@AllArgsConstructor
public class Eip0aw010Controller {

    private static final PropertyPlaceholderHelper helper = new PropertyPlaceholderHelper("${", "}");
    private final UserBean userData;
    private final EipcodeDao eipcodeDao;
    private final View_flowDao viewFlowDao;


    @RequestMapping(value = "/Common_getSysApi.action", method = RequestMethod.POST)
    @ResponseBody
    public TitleResult getSys_Api() {
        if (Optional.ofNullable(userData)
                .map(UserBean::getUserId)
                .isEmpty()) {
            return null;
        }
        List<ApiResult> result = new ArrayList<>();
        //第一個一定是差勤
        try {
            result.add(getIrtSql());
        } catch (Exception e) {
            log.error("取得差勤資料錯誤{}。",ExceptionUtility.getStackTrace(e));
        }

        IntStream.range(2, 5)
                .mapToObj(x -> getSys_api(String.valueOf(x)))
                .takeWhile(Objects::nonNull)
                .forEach(sysApi -> {
                    result.add(doApi(sysApi));
                });
        return ObjectUtility.normalizeObject(TitleResult.builder()
                .apiResultList(result)
                .interval(eipcodeDao.findByCodeKindCodeNo("SYS_API", "INTERVAL")
                        .map(Eipcode::getCodename)
                        .orElse("60"))
                .build());
    }

    private ApiResult getIrtSql() {
        String url = eipcodeDao.findByCodeKindCodeNo("SYS_API", "1_CLICK_URL").map(Eipcode::getCodename).orElse(StringUtils.EMPTY);
        return ApiResult.builder()
            .click_url(url)
            .cnt(viewFlowDao.selectCountByNext_person_id(View_flow.builder().next_person_id(userData.getUserId()).build()).toString()).build();
    }

    private ApiParams getSys_api(String apiNumber) {
        ApiParams apiParam = null;
        try {
            List<Eipcode> list = eipcodeDao.findByCodekindScodekindOrderByCodeno("SYS_API", apiNumber);
            Map<String, Eipcode> map = list.stream()
                    .collect(Collectors.toMap(x -> StringUtils.substringAfter(x.getCodeno(), "_"), x -> x));
            if (!map.isEmpty()) {
                apiParam = ApiParams.builder()
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

    private ApiResult doApi(ApiParams apiParams) {
        ApiResult apiResult = new ApiResult();
        try {
            apiResult.click_url = helper.replacePlaceholders(apiParams.click_url, x -> Objects.requireNonNull(BeanUtility.getBeanProperty(userData, x))
                    .toString());

            RestTemplate restTemplate = new RestTemplate();
            apiParams.url = helper.replacePlaceholders(apiParams.url, x -> Objects.requireNonNull(BeanUtility.getBeanProperty(userData, x))
                    .toString());
            ParameterizedTypeReference<Map<String, Object>> responseType = new ParameterizedTypeReference<>() {
            };
            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(apiParams.url, HttpMethod.GET, null, responseType);
            apiResult.setCnt(Objects.requireNonNull(response.getBody())
                    .get(apiParams.res)
                    .toString());
        } catch (RestClientException e) {
            log.error("SYS_API 呼叫{} 錯誤 {}", ObjectUtility.normalizeObject(apiParams), ExceptionUtility.getStackTrace(e));
        }
        return apiResult;
    }


    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ApiParams {
        private String url;
        private String res;
        private String click_url;
    }


    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ApiResult {
        private String cnt;
        private String click_url;
    }

    @Data
    @Builder
    public static class TitleResult {
        private List<ApiResult> apiResultList;
        private String interval;
    }
}
