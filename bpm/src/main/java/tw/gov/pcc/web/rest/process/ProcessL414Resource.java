package tw.gov.pcc.web.rest.process;

import com.google.gson.Gson;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import tw.gov.pcc.service.dto.EipBpmIsmsL414DTO;
import tw.gov.pcc.service.dto.ProcessReqDTO;
import tw.gov.pcc.utils.FlowNumber;

import java.util.HashMap;

@RestController
@RequestMapping("/api/process")
public class ProcessL414Resource {

    // 測試中若flowable沒在同一個container啟動，記得修改下方port
    // todo: 上線後之後記得要改成自動抓取domain的方式
    private String START_PROCESS_URL = "http://localhost:8080/flowable/process/startProcess";

    public static void main(String[] args) {
        String formId = "112070001";
        System.out.println(new FlowNumber().getFlowNum(formId));
//        String[] lastNum = formId.split("-");
//        System.out.println(lastNum[0]+"-"+FlowNumber.getFlowNum(lastNum[1]));

    }

    @RequestMapping("/startL414")
    public String start(@RequestBody EipBpmIsmsL414DTO eipBpmIsmsL414DTO) {


        if (eipBpmIsmsL414DTO != null) {

            // todo: 驗證資料
            String appEmpid = eipBpmIsmsL414DTO.getAppEmpid();


        } else {

            return null;
        }



        ProcessReqDTO processReqDTO = new ProcessReqDTO();
        processReqDTO.setFormName("L414");
        HashMap<String, Object> variables = new HashMap<>();

        variables.put("applier", "ApplyTester");
        variables.put("isSubmit", 1);
        variables.put("sectionChief", "ChiefTester");
        variables.put("director", "DirectorTester");
        variables.put("infoGroup", "InfoTester");
        processReqDTO.setVariables(variables);
        Gson gson = new Gson();
        String json = gson.toJson(processReqDTO);

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(json, headers);

        String processInstanceId = restTemplate.exchange(START_PROCESS_URL, HttpMethod.POST, requestEntity, String.class).getBody();
        // todo: 取得表單最後的流水號
        String lastFormId = null;
        if (lastFormId == null) {
            eipBpmIsmsL414DTO.setFormId(eipBpmIsmsL414DTO.getFormName() + "-" + new FlowNumber().getFlowNum(null));
        } else {
            String[] lastNum = lastFormId.split("-");
            eipBpmIsmsL414DTO.setFormId(lastNum[0] + "-" + new FlowNumber().getFlowNum(lastNum[1]));
        }
        eipBpmIsmsL414DTO.setProcessInstanceId(processInstanceId);

        // todo: 存入table

        return processInstanceId;
    }


}
