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

    private final String RESPONSE_JSON = "{\n" +
            "  \"apiVersion\": \"1.0\",\n" +
            "  \"txnSq\": %d,\n" +
            "  \"txnTime\": \"%s\",\n" +
            "  \"retCode\": \"%s\",\n" +
            "  \"retMsg\": \"%s\",\n" +
            "  \"data\": [%s]\n" +
            "}" ;
    private final String DATA = "{\n" +
            "      \"orgId\": \"9.99\",\n" +
            "      \"orgName\": \"測試機關一\",\n" +
            "      \"tenderCaseNo\": \"112-RCFR-04-4-033\",\n" +
            "      \"tenderName\": \"來義鄉南和部落社區農路及排水改善工程\",\n" +
            "      \"tenderSq\": \"01\",\n" +
            "      \"uniqueKey\": 70355049,\n" +
            "      \"tenderWay\": \"公開招標\",\n" +
            "      \"awardWay\": \"最低標\",\n" +
            "      \"tenderProcCate\": \"工程類\",\n" +
            "      \"tenderCpcName\": \"5139 - 其他土木工程\",\n" +
            "      \"stServiceList\": [\n" +
            "        {\n" +
            "          \"stServiceTypeCode\": \"10000001\",\n" +
            "          \"stServiceType\": \"建築師(建築師開業證書)\",\n" +
            "          \"stServiceTypeOther\": \"\",\n" +
            "          \"stSiniorityCode\": \"90000001\",\n" +
            "          \"stSiniority\": \"3年以上未滿5年\",\n" +
            "          \"stPersonMonths\": 13,\n" +
            "          \"stSalary\": 35000\n" +
            "        },\n" +
            "        {\n" +
            "          \"stServiceTypeCode\": \"10000002\",\n" +
            "          \"stServiceType\": \"監造工程師(受訓合格)\",\n" +
            "          \"stServiceTypeOther\": \"\",\n" +
            "          \"stSiniorityCode\": \"90000002\",\n" +
            "          \"stSiniority\": \"15年以上未滿20年\",\n" +
            "          \"stPersonMonths\": 7,\n" +
            "          \"stSalary\": 56000\n" +
            "        }\n" +
            "      ],\n" +
            "      \"dig\": \"M2ZjMzIwMzZkYzFl\"\n" +
            "    }";
    @PostMapping(value = "/tps/tpsAgenciesApi/genStServicePage", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> tpsAgenciesApi(@Valid @RequestBody TpsAgenciesDTO tpsAgenciesDTO, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {

            return ResponseEntity.badRequest().body(String.format(RESPONSE_JSON,tpsAgenciesDTO.getTxnSq(),tpsAgenciesDTO.getTxnTime(), "E003", "格式錯誤", ""));
        }

        if (timeComaprison(tpsAgenciesDTO.getAwardNoticeDateFrom(), tpsAgenciesDTO.getAwardNoticeDateTo())) {
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

        return intervalDay >= 0 && intervalDay <= 7;
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
    private Date txnTime;
    @NotNull
    private String awardNoticeDateFrom;
    @NotNull
    private String awardNoticeDateTo;



}
