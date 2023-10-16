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
            String DATA ="{\n" +
                    "\t\t\t\"orgId\":\"3.76.53.82\",\n" +
                    "\t\t\t\"orgName\":\"屏東縣獅子鄉公所\",\n" +
                    "\t\t\t\"tenderCaseNo\":\"103020\",\n" +
                    "\t\t\t\"tenderName\":\"獅子鄉103年度觀光業務工程委託設計及監造開口契約(新的標案)\",\n" +
                    "\t\t\t\"tenderSq\":\"01\",\n" +
                    "\t\t\t\"uniqueKey\":51247230,\n" +
                    "\t\t\t\"tenderWay\":\"公開招標\",\n" +
                    "\t\t\t\"awardWay\":\"最低標\",\n" +
                    "\t\t\t\"tenderProcCate\":\"工程類\",\n" +
                    "\t\t\t\"tenderCpcName\":\"5139 - 其他土木工程\",\n" +
                    "\t\t\t\"stServiceList\":[\n" +
                    "\t\t\t\t{\n" +
                    "\t\t\t\t\t\"stServiceTypeCode\":\"10000001\",\n" +
                    "\t\t\t\t\t\"stServiceType\":\"計畫主持人\",\n" +
                    "\t\t\t\t\t\"stServiceTypeOther\":\"\",\n" +
                    "\t\t\t\t\t\"stSeniorityCode\":\"10000002\",\n" +
                    "\t\t\t\t\t\"stSeniority\":\"未滿10年\",\n" +
                    "\t\t\t\t\t\"stPersonMonths\":13,\n" +
                    "\t\t\t\t\t\"stSalary\":75000\n" +
                    "\t\t\t\t},\n" +
                    "                {\n" +
                    "\t\t\t\t\t\"stServiceTypeCode\":\"10000007\",\n" +
                    "\t\t\t\t\t\"stServiceType\":\"監造工程師(受訓合格)\",\n" +
                    "\t\t\t\t\t\"stServiceTypeOther\":\"\",\n" +
                    "\t\t\t\t\t\"stSeniorityCode\":\"10000005\",\n" +
                    "\t\t\t\t\t\"stSeniority\":\"滿10年以上未滿15年\",\n" +
                    "\t\t\t\t\t\"stPersonMonths\":7,\n" +
                    "\t\t\t\t\t\"stSalary\":66000\n" +
                    "\t\t\t\t}\n" +
                    "\t\t\t],\n" +
                    "\t\t\t\"dig\":\"M2ZjMzIwMzZkYzFl\"\n" +
                    "\t\t},\n" +
                    "\t\t{\n" +
                    "\t\t\t\"orgId\":\"3.15.18.3\",\n" +
                    "\t\t\t\"orgName\":\"交通部臺灣鐵路管理局\",\n" +
                    "\t\t\t\"tenderCaseNo\":\"1009210525-1\",\n" +
                    "\t\t\t\"tenderName\":\"「山佳車站暨浮洲車站新建工程」公共藝術設置(舊的標案)\",\n" +
                    "\t\t\t\"tenderSq\":\"01\",\n" +
                    "\t\t\t\"uniqueKey\":51646290,\n" +
                    "\t\t\t\"tenderWay\":\"公開招標\",\n" +
                    "\t\t\t\"awardWay\":\"最低標\",\n" +
                    "\t\t\t\"tenderProcCate\":\"工程類\",\n" +
                    "\t\t\t\"tenderCpcName\":\"5139 - 其他土木工程\",\n" +
                    "\t\t\t\"stServiceList\":[\n" +
                    "\t\t\t\t{\n" +
                    "\t\t\t\t\t\"stServiceTypeCode\":\"10000001\",\n" +
                    "\t\t\t\t\t\"stServiceType\":\"計畫主持人\",\n" +
                    "\t\t\t\t\t\"stServiceTypeOther\":\"\",\n" +
                    "\t\t\t\t\t\"stSeniorityCode\":\"10000007\",\n" +
                    "\t\t\t\t\t\"stSeniority\":\"滿20年以上\",\n" +
                    "\t\t\t\t\t\"stPersonMonths\":24,\n" +
                    "\t\t\t\t\t\"stSalary\":95000\n" +
                    "\t\t\t\t},\n" +
                    "                {\n" +
                    "\t\t\t\t\t\"stServiceTypeCode\":\"10000016\",\n" +
                    "\t\t\t\t\t\"stServiceType\":\"技師【土木工程科】(技師執業執照)\",\n" +
                    "\t\t\t\t\t\"stServiceTypeOther\":\"\",\n" +
                    "\t\t\t\t\t\"stSeniorityCode\":\"10000005\",\n" +
                    "\t\t\t\t\t\"stSeniority\":\"滿10年以上未滿15年\",\n" +
                    "\t\t\t\t\t\"stPersonMonths\":10,\n" +
                    "\t\t\t\t\t\"stSalary\":76000\n" +
                    "\t\t\t\t},\n" +
                    "                {\n" +
                    "\t\t\t\t\t\"stServiceTypeCode\":\"99999999\",\n" +
                    "\t\t\t\t\t\"stServiceType\":\"其他\",\n" +
                    "\t\t\t\t\t\"stServiceTypeOther\":\"古蹟遷移計師\",\n" +
                    "\t\t\t\t\t\"stSeniorityCode\":\"10000002\",\n" +
                    "\t\t\t\t\t\"stSeniority\":\"未滿10年\",\n" +
                    "\t\t\t\t\t\"stPersonMonths\":3,\n" +
                    "\t\t\t\t\t\"stSalary\":56000\n" +
                    "\t\t\t\t}\n" +
                    "\t\t\t],\n" +
                    "\t\t\t\"dig\":\"M2ZjMzIwMzZkYzFl\"\n" +
                    "\t\t}\t\t";
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
