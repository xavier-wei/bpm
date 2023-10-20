package tw.gov.pcc.flowable.rest;


import lombok.Data;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
public class TpsApiResourceTest {

    @PostMapping(value = "/tps/tpsAgenciesApi/genStServicePage", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> tpsAgenciesApi(@Valid @RequestBody TpsAgenciesDTO tpsAgenciesDTO, BindingResult bindingResult) {

        String RESPONSE_JSON = "{\n" +
                "  \"apiVersion\": \"1.0\",\n" +
                "  \"txnSq\": %d,\n" +
                "  \"txnTime\": \"%s\",\n" +
                "  \"retCode\": \"%s\",\n" +
                "  \"retMsg\": \"%s\",\n" +
                "  \"data\": [%s]\n" +
                "}";
        if (bindingResult.hasErrors()) {

            return ResponseEntity.badRequest().body(String.format(RESPONSE_JSON,tpsAgenciesDTO.getTxnSq(),tpsAgenciesDTO.getTxnTime(), "E003", "格式錯誤", ""));
        }

        if (timeComaprison(tpsAgenciesDTO.getAwardNoticeDateFrom(), tpsAgenciesDTO.getAwardNoticeDateTo())) {
            String DATA = "{\n" +
                    "            \"orgId\": \"9.99\",\n" +
                    "            \"orgName\": \"測試機關一\",\n" +
                    "            \"tenderCaseNo\": \"20231002ST003_2\",\n" +
                    "            \"tenderName\": \"技術服務薪資測試\",\n" +
                    "            \"tenderSq\": \"01\",\n" +
                    "            \"uniqueKey\": 60008566,\n" +
                    "            \"tenderWay\": \"經公開評選或公開徵求之限制性招標\",\n" +
                    "            \"awardWay\": \"最低標\",\n" +
                    "            \"tenderProcCate\": \"勞務類\",\n" +
                    "            \"tenderCpcName\": \"綜合工程服務\",\n" +
                    "            \"stServiceList\": [\n" +
                    "                {\n" +
                    "                    \"stServiceTypeCode\": \"99999999\",\n" +
                    "                    \"stServiceType\": \"其他\",\n" +
                    "                    \"stServiceTypeOther\": \"其他工程人員\",\n" +
                    "                    \"stSeniorityCode\": null,\n" +
                    "                    \"stSeniority\": null,\n" +
                    "                    \"stPersonMonths\": 1,\n" +
                    "                    \"stSalary\": 40000\n" +
                    "                },\n" +
                    "                {\n" +
                    "                    \"stServiceTypeCode\": \"3\",\n" +
                    "                    \"stServiceType\": \"組長—測試\",\n" +
                    "                    \"stServiceTypeOther\": null,\n" +
                    "                    \"stSeniorityCode\": \"1\",\n" +
                    "                    \"stSeniority\": \"3年以上未滿5年—測試\",\n" +
                    "                    \"stPersonMonths\": 2,\n" +
                    "                    \"stSalary\": 50000\n" +
                    "                },\n" +
                    "                {\n" +
                    "                    \"stServiceTypeCode\": \"1\",\n" +
                    "                    \"stServiceType\": \"計畫主持人—測試\",\n" +
                    "                    \"stServiceTypeOther\": null,\n" +
                    "                    \"stSeniorityCode\": \"1\",\n" +
                    "                    \"stSeniority\": \"3年以上未滿5年—測試\",\n" +
                    "                    \"stPersonMonths\": 3,\n" +
                    "                    \"stSalary\": 60000\n" +
                    "                }\n" +
                    "            ],\n" +
                    "            \"dig\": \"2o4Sz32y4zHZVhJU\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"orgId\": \"9.99\",\n" +
                    "            \"orgName\": \"測試機關一\",\n" +
                    "            \"tenderCaseNo\": \"20231002ST001_1\",\n" +
                    "            \"tenderName\": \"技術服務薪資測試8671\",\n" +
                    "            \"tenderSq\": \"01\",\n" +
                    "            \"uniqueKey\": 60008562,\n" +
                    "            \"tenderWay\": \"公開取得報價單或企劃書\",\n" +
                    "            \"awardWay\": \"最低標\",\n" +
                    "            \"tenderProcCate\": \"勞務類\",\n" +
                    "            \"tenderCpcName\": \"建築服務\",\n" +
                    "            \"stServiceList\": [\n" +
                    "                {\n" +
                    "                    \"stServiceTypeCode\": \"4\",\n" +
                    "                    \"stServiceType\": \"建築師—測試(建築師開業證書)\",\n" +
                    "                    \"stServiceTypeOther\": null,\n" +
                    "                    \"stSeniorityCode\": \"1\",\n" +
                    "                    \"stSeniority\": \"3年以上未滿5年—測試\",\n" +
                    "                    \"stPersonMonths\": 1,\n" +
                    "                    \"stSalary\": 40000\n" +
                    "                }\n" +
                    "            ],\n" +
                    "            \"dig\": \"xz5gOQFBPi0wRzvg\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"orgId\": \"9.99\",\n" +
                    "            \"orgName\": \"測試機關一\",\n" +
                    "            \"tenderCaseNo\": \"20231002ST005_2\",\n" +
                    "            \"tenderName\": \"技術服務薪資測試\",\n" +
                    "            \"tenderSq\": \"01\",\n" +
                    "            \"uniqueKey\": 60008568,\n" +
                    "            \"tenderWay\": \"選擇性招標(建立合格廠商名單後續邀標)\",\n" +
                    "            \"awardWay\": \"最低標\",\n" +
                    "            \"tenderProcCate\": \"勞務類\",\n" +
                    "            \"tenderCpcName\": \"與科技工程有關之顧問服務\",\n" +
                    "            \"stServiceList\": [\n" +
                    "                {\n" +
                    "                    \"stServiceTypeCode\": \"3\",\n" +
                    "                    \"stServiceType\": \"組長—測試\",\n" +
                    "                    \"stServiceTypeOther\": null,\n" +
                    "                    \"stSeniorityCode\": \"1\",\n" +
                    "                    \"stSeniority\": \"3年以上未滿5年—測試\",\n" +
                    "                    \"stPersonMonths\": 1,\n" +
                    "                    \"stSalary\": 40000\n" +
                    "                }\n" +
                    "            ],\n" +
                    "            \"dig\": \"oWR3gEqa107paQTZ\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"orgId\": \"9.99\",\n" +
                    "            \"orgName\": \"測試機關一\",\n" +
                    "            \"tenderCaseNo\": \"20231002ST007_3\",\n" +
                    "            \"tenderName\": \"技術服務薪資測試\",\n" +
                    "            \"tenderSq\": \"00\",\n" +
                    "            \"uniqueKey\": 60008561,\n" +
                    "            \"tenderWay\": \"限制性招標(未經公開評選或公開徵求)\",\n" +
                    "            \"awardWay\": \"最低標\",\n" +
                    "            \"tenderProcCate\": \"勞務類\",\n" +
                    "            \"tenderCpcName\": \"建築服務\",\n" +
                    "            \"stServiceList\": [\n" +
                    "                {\n" +
                    "                    \"stServiceTypeCode\": \"3\",\n" +
                    "                    \"stServiceType\": \"組長—測試\",\n" +
                    "                    \"stServiceTypeOther\": null,\n" +
                    "                    \"stSeniorityCode\": \"1\",\n" +
                    "                    \"stSeniority\": \"3年以上未滿5年—測試\",\n" +
                    "                    \"stPersonMonths\": 1,\n" +
                    "                    \"stSalary\": 40000\n" +
                    "                },\n" +
                    "                {\n" +
                    "                    \"stServiceTypeCode\": \"99999999\",\n" +
                    "                    \"stServiceType\": \"其他\",\n" +
                    "                    \"stServiceTypeOther\": \"工程專業人員\",\n" +
                    "                    \"stSeniorityCode\": null,\n" +
                    "                    \"stSeniority\": null,\n" +
                    "                    \"stPersonMonths\": 3,\n" +
                    "                    \"stSalary\": 60000\n" +
                    "                }\n" +
                    "            ],\n" +
                    "            \"dig\": \"wHVRVViLwBF9Wjvc\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"orgId\": \"9.99\",\n" +
                    "            \"orgName\": \"測試機關一\",\n" +
                    "            \"tenderCaseNo\": \"20231002ST002_1\",\n" +
                    "            \"tenderName\": \"技術服務薪資測試8671\",\n" +
                    "            \"tenderSq\": \"01\",\n" +
                    "            \"uniqueKey\": 60008563,\n" +
                    "            \"tenderWay\": \"公開招標\",\n" +
                    "            \"awardWay\": \"最低標\",\n" +
                    "            \"tenderProcCate\": \"勞務類\",\n" +
                    "            \"tenderCpcName\": \"工程服務\",\n" +
                    "            \"stServiceList\": [\n" +
                    "                {\n" +
                    "                    \"stServiceTypeCode\": \"1\",\n" +
                    "                    \"stServiceType\": \"計畫主持人—測試\",\n" +
                    "                    \"stServiceTypeOther\": null,\n" +
                    "                    \"stSeniorityCode\": \"1\",\n" +
                    "                    \"stSeniority\": \"3年以上未滿5年—測試\",\n" +
                    "                    \"stPersonMonths\": 5,\n" +
                    "                    \"stSalary\": 80000\n" +
                    "                },\n" +
                    "                {\n" +
                    "                    \"stServiceTypeCode\": \"99999999\",\n" +
                    "                    \"stServiceType\": \"其他\",\n" +
                    "                    \"stServiceTypeOther\": \"工程專業人員\",\n" +
                    "                    \"stSeniorityCode\": null,\n" +
                    "                    \"stSeniority\": null,\n" +
                    "                    \"stPersonMonths\": 1,\n" +
                    "                    \"stSalary\": 40000\n" +
                    "                }\n" +
                    "            ],\n" +
                    "            \"dig\": \"YC3jQ9v7upRO7Jo8\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"orgId\": \"9.99\",\n" +
                    "            \"orgName\": \"測試機關一\",\n" +
                    "            \"tenderCaseNo\": \"20231002ST002_2\",\n" +
                    "            \"tenderName\": \"技術服務薪資測試\",\n" +
                    "            \"tenderSq\": \"01\",\n" +
                    "            \"uniqueKey\": 60008564,\n" +
                    "            \"tenderWay\": \"公開招標\",\n" +
                    "            \"awardWay\": \"最低標\",\n" +
                    "            \"tenderProcCate\": \"勞務類\",\n" +
                    "            \"tenderCpcName\": \"工程服務\",\n" +
                    "            \"stServiceList\": [\n" +
                    "                {\n" +
                    "                    \"stServiceTypeCode\": \"3\",\n" +
                    "                    \"stServiceType\": \"組長—測試\",\n" +
                    "                    \"stServiceTypeOther\": null,\n" +
                    "                    \"stSeniorityCode\": \"1\",\n" +
                    "                    \"stSeniority\": \"3年以上未滿5年—測試\",\n" +
                    "                    \"stPersonMonths\": 1,\n" +
                    "                    \"stSalary\": 40000\n" +
                    "                },\n" +
                    "                {\n" +
                    "                    \"stServiceTypeCode\": \"5\",\n" +
                    "                    \"stServiceType\": \"技師-土木工程科—測試(技師執業執照)\",\n" +
                    "                    \"stServiceTypeOther\": null,\n" +
                    "                    \"stSeniorityCode\": \"1\",\n" +
                    "                    \"stSeniority\": \"3年以上未滿5年—測試\",\n" +
                    "                    \"stPersonMonths\": 3,\n" +
                    "                    \"stSalary\": 60000\n" +
                    "                }\n" +
                    "            ],\n" +
                    "            \"dig\": \"49dVSZgazmAmZlKB\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"orgId\": \"9.99\",\n" +
                    "            \"orgName\": \"測試機關一\",\n" +
                    "            \"tenderCaseNo\": \"20231002ST006_1\",\n" +
                    "            \"tenderName\": \"技術服務薪資測試\",\n" +
                    "            \"tenderSq\": \"01\",\n" +
                    "            \"uniqueKey\": 60008569,\n" +
                    "            \"tenderWay\": \"選擇性招標(個案)\",\n" +
                    "            \"awardWay\": \"最低標\",\n" +
                    "            \"tenderProcCate\": \"勞務類\",\n" +
                    "            \"tenderCpcName\": \"技術檢定與分析服務\",\n" +
                    "            \"stServiceList\": [\n" +
                    "                {\n" +
                    "                    \"stServiceTypeCode\": \"1\",\n" +
                    "                    \"stServiceType\": \"計畫主持人—測試\",\n" +
                    "                    \"stServiceTypeOther\": null,\n" +
                    "                    \"stSeniorityCode\": \"1\",\n" +
                    "                    \"stSeniority\": \"3年以上未滿5年—測試\",\n" +
                    "                    \"stPersonMonths\": 1,\n" +
                    "                    \"stSalary\": 40000\n" +
                    "                },\n" +
                    "                {\n" +
                    "                    \"stServiceTypeCode\": \"5\",\n" +
                    "                    \"stServiceType\": \"技師-土木工程科—測試(技師執業執照)\",\n" +
                    "                    \"stServiceTypeOther\": null,\n" +
                    "                    \"stSeniorityCode\": \"1\",\n" +
                    "                    \"stSeniority\": \"3年以上未滿5年—測試\",\n" +
                    "                    \"stPersonMonths\": 2,\n" +
                    "                    \"stSalary\": 50000\n" +
                    "                }\n" +
                    "            ],\n" +
                    "            \"dig\": \"yztjEY8oKkLdGjlm\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"orgId\": \"9.99\",\n" +
                    "            \"orgName\": \"測試機關一\",\n" +
                    "            \"tenderCaseNo\": \"20231002ST006_2\",\n" +
                    "            \"tenderName\": \"技術服務薪資測試\",\n" +
                    "            \"tenderSq\": \"01\",\n" +
                    "            \"uniqueKey\": 60008570,\n" +
                    "            \"tenderWay\": \"選擇性招標(個案)\",\n" +
                    "            \"awardWay\": \"最低標\",\n" +
                    "            \"tenderProcCate\": \"勞務類\",\n" +
                    "            \"tenderCpcName\": \"技術檢定與分析服務\",\n" +
                    "            \"stServiceList\": [\n" +
                    "                {\n" +
                    "                    \"stServiceTypeCode\": \"99999999\",\n" +
                    "                    \"stServiceType\": \"其他\",\n" +
                    "                    \"stServiceTypeOther\": \"工程人員\",\n" +
                    "                    \"stSeniorityCode\": null,\n" +
                    "                    \"stSeniority\": null,\n" +
                    "                    \"stPersonMonths\": 1,\n" +
                    "                    \"stSalary\": 40000\n" +
                    "                },\n" +
                    "                {\n" +
                    "                    \"stServiceTypeCode\": \"3\",\n" +
                    "                    \"stServiceType\": \"組長—測試\",\n" +
                    "                    \"stServiceTypeOther\": null,\n" +
                    "                    \"stSeniorityCode\": \"1\",\n" +
                    "                    \"stSeniority\": \"3年以上未滿5年—測試\",\n" +
                    "                    \"stPersonMonths\": 2,\n" +
                    "                    \"stSalary\": 50000\n" +
                    "                }\n" +
                    "            ],\n" +
                    "            \"dig\": \"MXQXcUkWTMYLajvw\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"orgId\": \"9.99\",\n" +
                    "            \"orgName\": \"測試機關一\",\n" +
                    "            \"tenderCaseNo\": \"20231002ST007_1\",\n" +
                    "            \"tenderName\": \"技術服務薪資測試\",\n" +
                    "            \"tenderSq\": \"00\",\n" +
                    "            \"uniqueKey\": 60008559,\n" +
                    "            \"tenderWay\": \"限制性招標(未經公開評選或公開徵求)\",\n" +
                    "            \"awardWay\": \"最低標\",\n" +
                    "            \"tenderProcCate\": \"勞務類\",\n" +
                    "            \"tenderCpcName\": \"建築服務\",\n" +
                    "            \"stServiceList\": [\n" +
                    "                {\n" +
                    "                    \"stServiceTypeCode\": \"5\",\n" +
                    "                    \"stServiceType\": \"技師-土木工程科—測試(技師執業執照)\",\n" +
                    "                    \"stServiceTypeOther\": null,\n" +
                    "                    \"stSeniorityCode\": \"1\",\n" +
                    "                    \"stSeniority\": \"3年以上未滿5年—測試\",\n" +
                    "                    \"stPersonMonths\": 1,\n" +
                    "                    \"stSalary\": 40000\n" +
                    "                }\n" +
                    "            ],\n" +
                    "            \"dig\": \"eUmHgUoruJICQri2\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"orgId\": \"9.99\",\n" +
                    "            \"orgName\": \"測試機關一\",\n" +
                    "            \"tenderCaseNo\": \"20231003ST001_2\",\n" +
                    "            \"tenderName\": \"未到期技術服務測試\",\n" +
                    "            \"tenderSq\": \"00\",\n" +
                    "            \"uniqueKey\": 60008572,\n" +
                    "            \"tenderWay\": \"限制性招標(未經公開評選或公開徵求)\",\n" +
                    "            \"awardWay\": \"最低標\",\n" +
                    "            \"tenderProcCate\": \"勞務類\",\n" +
                    "            \"tenderCpcName\": \"建築服務\",\n" +
                    "            \"stServiceList\": [],\n" +
                    "            \"dig\": \"AXaugv0J7COuTREo\"\n" +
                    "        }";
            return ResponseEntity.ok().body(String.format(RESPONSE_JSON,tpsAgenciesDTO.getTxnSq(),tpsAgenciesDTO.getTxnTime(), "0000", "成功", DATA));
        } else {
            return ResponseEntity.badRequest().body(String.format(RESPONSE_JSON,tpsAgenciesDTO.getTxnSq(),tpsAgenciesDTO.getTxnTime(), "E002", "參數錯誤或查詢起迄日期超過允許區間", ""));

        }

    }


    private boolean timeComaprison(String awardNoticeDateFrom,String awardNoticeDateTo) {

        Date fromDate = null;
        Date toDate = null;
        try {
            fromDate = dateFormat(awardNoticeDateFrom);
            toDate = dateFormat(awardNoticeDateTo);
        } catch (ParseException e) {
            return false;
        }
        long intervalDay = (toDate.getTime() - fromDate.getTime()) / (1000 * 60 * 60 * 24);

        return intervalDay >= 0 && intervalDay < 7;
    }


    private Date dateFormat(String date) throws ParseException {
        return new SimpleDateFormat("yyyyMMdd").parse(date);
    }
}

@Data
class TpsAgenciesDTO implements Serializable {

    @NotNull
    private String apiVersion;
    @NotNull
    private int txnSq;
    @NotNull
    private String txnTime;
    @NotNull
    private String awardNoticeDateFrom;
    @NotNull
    private String awardNoticeDateTo;



}
