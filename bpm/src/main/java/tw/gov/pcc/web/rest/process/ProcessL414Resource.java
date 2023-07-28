package tw.gov.pcc.web.rest.process;

import com.google.gson.Gson;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;
import tw.gov.pcc.repository.BpmIsmsL414Repository;
import tw.gov.pcc.service.BpmIsmsL414Service;
import tw.gov.pcc.service.dto.BpmIsmsL414DTO;
import tw.gov.pcc.service.dto.ProcessReqDTO;
import tw.gov.pcc.service.dto.TaskDTO;
import tw.gov.pcc.utils.SeqNumber;

import javax.validation.Valid;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/process")
public class ProcessL414Resource {

    private final Logger log = LoggerFactory.getLogger(ProcessL414Resource.class);

    @Autowired
    private BpmIsmsL414Service bpmIsmsL414Service;

    @Autowired
    private BpmIsmsL414Repository bpmIsmsL414Repository;

    // 測試中若flowable沒在同一個container啟動，記得修改下方port
    // todo: 上線後之後記得要改成自動抓取domain的方式
    private final String START_PROCESS_URL = "http://localhost:8081/process";
    private final RestTemplate restTemplate = new RestTemplate();

    @PostMapping("/startL414")
    public String start(@Valid @RequestBody BpmIsmsL414DTO bpmIsmsL414DTO) {
        log.info("ProcessL414Resource.java - start - 43 :: " + bpmIsmsL414DTO);

        if (bpmIsmsL414DTO != null) {

            // todo: 驗證資料
            String appEmpid = bpmIsmsL414DTO.getAppEmpid();


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
        String processInstanceId = null;
        if (exchange.getStatusCodeValue() == 200) {
            processInstanceId = exchange.getBody();
        } else {
            return "流程引擎忙碌中，請稍候再試";
        }

        bpmIsmsL414DTO.setProcessInstanceId(processInstanceId);

        //取得表單最後的流水號
        String lastFormId = bpmIsmsL414Repository.getMaxFormId().size() > 0 ? bpmIsmsL414Repository.getMaxFormId().get(0).getFormId() : null;
        bpmIsmsL414DTO.setFormId(bpmIsmsL414DTO.getFormName() + "-" + new SeqNumber().getNewSeq(lastFormId));

        //存入table
        bpmIsmsL414DTO.setProcessInstanceId(processInstanceId);
        bpmIsmsL414DTO.setProcessInstanceStatus("0");
        bpmIsmsL414DTO.setUpdateTime(Instant.now());
        bpmIsmsL414DTO.setUpdateUser(bpmIsmsL414DTO.getFilName());
        bpmIsmsL414DTO.setCreateTime(Instant.now());
        bpmIsmsL414DTO.setCreateUser(bpmIsmsL414DTO.getFilName());
        bpmIsmsL414Service.save(bpmIsmsL414DTO);

        return processInstanceId;
    }

    @RequestMapping("/queryTask")
    public List<TaskDTO> queryTask(String id, String formName) {

        HashMap<String, String> map = new HashMap<>();

//        restTemplate


        return null;
    }


}
