package tw.gov.pcc.web.rest.process;

import com.google.gson.Gson;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import tw.gov.pcc.service.dto.EipBpmIsmsL414DTO;
import tw.gov.pcc.service.dto.ProcessReqDTO;
import tw.gov.pcc.service.dto.TaskDTO;
import tw.gov.pcc.utils.SeqNumber;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/process")
public class ProcessL414Resource {

    // 測試中若flowable沒在同一個container啟動，記得修改下方port
    // todo: 上線後之後記得要改成自動抓取domain的方式
    private final String START_PROCESS_URL = "http://localhost:8081/process/startProcess";
    private final RestTemplate restTemplate = new RestTemplate();

    public static void main(String[] args) {
        String formId = "L414-112060001";
        System.out.println(new SeqNumber().getNewSeq(formId));

    }

    @RequestMapping("/startL414")
    public String start(@RequestBody EipBpmIsmsL414DTO eipBpmIsmsL414DTO) {


        if (eipBpmIsmsL414DTO != null) {

            // todo: 驗證資料
            String appEmpid = eipBpmIsmsL414DTO.getAppEmpid();


        } else {

            return "";
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

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(json, headers);

        ResponseEntity<String> exchange = restTemplate.exchange(START_PROCESS_URL + "/startProcess", HttpMethod.POST, requestEntity, String.class);
        String processInstanceId=null;
        if (exchange.getStatusCodeValue()==200) {
            processInstanceId = exchange.getBody();
        }else {
            return "流程引擎忙碌中，請稍候再試";
        }

        eipBpmIsmsL414DTO.setProcessInstanceId(processInstanceId);


        // todo: 取得表單最後的流水號

        String lastFormId = "L414-112070222"; // 之後可刪

        eipBpmIsmsL414DTO.setFormId(eipBpmIsmsL414DTO.getFormName()+"-"+new SeqNumber().getNewSeq(lastFormId));




        // todo: 存入table

        return processInstanceId;
    }

    @RequestMapping("/queryTask")
    public List<TaskDTO> queryTask(String id , String formName) {

        HashMap<String, String> map = new HashMap<>();

//        restTemplate


        return null;
    }


}
