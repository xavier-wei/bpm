package tw.gov.pcc.web.rest.process;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import tw.gov.pcc.domain.BpmIsmsServiceBeanNameEnum;
import tw.gov.pcc.service.BpmIsmsService;
import tw.gov.pcc.service.dto.BpmUploadFileDTO;
import tw.gov.pcc.service.dto.ProcessReqDTO;
import tw.gov.pcc.service.dto.TaskDTO;

import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("/api/process")
public class IsmsProcessResource {

    private static final Logger log = LoggerFactory.getLogger(IsmsProcessResource.class);

    @Value("${bpm.token}")
    private String TOKEN;

    private ApplicationContext applicationContext;
    private final Gson gson = new Gson();
    private final String FLOWABLE_PROCESS_URL = "http://localhost:8081/process";
    private final RestTemplate restTemplate = new RestTemplate();

    public IsmsProcessResource(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @PostMapping(path = "/start/{key}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public String start(
        @Valid @RequestPart("form") HashMap<String,String> form,
        @PathVariable String key,
        @Valid @RequestPart(name = "fileDto", required = false) List<BpmUploadFileDTO> dto,
        @RequestPart(name = "appendixFiles", required = false) List<MultipartFile> appendixFiles) throws IOException {

        // 產生要送給流程引擎的request dto
        ProcessReqDTO processReqDTO = new ProcessReqDTO();
        processReqDTO.setFormName(key);
        HashMap<String, Object> variables = new HashMap<>();
        BpmIsmsService service = (BpmIsmsService) applicationContext.getBean(Objects.requireNonNull(BpmIsmsServiceBeanNameEnum.getServiceBeanNameByKey(key)));
        UUID uuid = service.setVariables(variables, form.get(key));

        // todo 判斷上級
        variables.put("sectionChief", "ChiefTester");
        variables.put("director", "DirectorTester");

        // todo 依據表單取得固定簽核人員
        variables.put("infoGroup", "InfoTester");
        variables.put("seniorTechSpecialist", "seniorTechSpecialistTester");
        variables.put("serverRoomOperator", "serverRoomOperatorTester");
        variables.put("reviewStaff", "reviewStaffTester");
        variables.put("serverRoomManager", "serverRoomManagerTester");
        processReqDTO.setVariables(variables);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(gson.toJson(processReqDTO), headers);
        ResponseEntity<String> exchange = restTemplate.exchange(FLOWABLE_PROCESS_URL + "/startProcess", HttpMethod.POST, requestEntity, String.class);
        String processInstanceId;
        TaskDTO taskDTO;
        if (exchange.getStatusCodeValue() == 200) {
            taskDTO = gson.fromJson(exchange.getBody(), TaskDTO.class);
            processInstanceId = taskDTO.getProcessInstanceId();
        } else {
            return "流程引擎忙碌中，請稍候再試";
        }

        try {
            service.saveBpm(uuid, processInstanceId, taskDTO, dto, appendixFiles);

        } catch (Exception e) {
            deleteProcessWhenSaveBpmFailed(processInstanceId);
            return "BPM寫入失敗，請聯絡管理員";
        }

        return processInstanceId;

    }

    public void deleteProcessWhenSaveBpmFailed(String processInstanceId) {
        log.info("ProcessL414Resource.java - deleteProcessInstance - 206 :: " + processInstanceId);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HashMap<String, String> deleteRequest = new HashMap<>();
        deleteRequest.put("processInstanceId", processInstanceId);
        deleteRequest.put("token", TOKEN);
        HttpEntity<String> requestEntity = new HttpEntity<>(gson.toJson(deleteRequest), headers);
        ResponseEntity<String> exchange = restTemplate.exchange(FLOWABLE_PROCESS_URL + "/deleteProcess", HttpMethod.POST, requestEntity, String.class);

    }
}
