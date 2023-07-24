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

import java.util.HashMap;

@RestController
@RequestMapping("/api/process")
public class ProcessL414Resource {

    // 測試中若flowable沒在同一個container啟動，記得修改下方port
    // todo: 上線後之後記得要改成自動抓取domain的方式
    private String START_PROCESS_URL = "http://localhost:8081/process/startProcess";

    @RequestMapping("/startL414")
    public String start(@RequestBody EipBpmIsmsL414DTO eipBpmIsmsL414DTO) {

//        if (eipBpmIsmsL414DTO != null) {
//            String appEmpid = eipBpmIsmsL414DTO.getAppEmpid();
//            // todo: check appEmpId exist or not
//
//
//        }else {
//
//
//
//        }

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
        return restTemplate.exchange(START_PROCESS_URL, HttpMethod.POST, requestEntity, String.class).getBody();
    }


}
