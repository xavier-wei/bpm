package tw.gov.pcc.web.rest.process;

import com.google.gson.Gson;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import tw.gov.pcc.service.dto.ProcessReqDTO;

import java.util.HashMap;

@RestController
@RequestMapping("/api/process")
public class ProcessL414Resource {

    @RequestMapping("/startL414")
    public String start() {

        // 若flowable沒在同一個container啟動，記得修改下方port
        // fixme:
        String url = "http://localhost:8081/flowable/process/startProcess";

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
        return restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class).getBody();
    }


}
