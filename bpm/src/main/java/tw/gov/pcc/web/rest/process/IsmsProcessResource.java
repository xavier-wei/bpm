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
import tw.gov.pcc.domain.entity.BpmSignStatus;
import tw.gov.pcc.service.BpmIsmsService;
import tw.gov.pcc.service.BpmSignStatusService;
import tw.gov.pcc.service.dto.*;
import tw.gov.pcc.service.mapper.BpmSignStatusMapper;

import javax.validation.Valid;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

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

    private final BpmSignStatusService bpmSignStatusService;
    private final BpmSignStatusMapper bpmSignStatusMapper;

    public IsmsProcessResource(ApplicationContext applicationContext, BpmSignStatusService bpmSignStatusService, BpmSignStatusMapper bpmSignStatusMapper) {
        this.applicationContext = applicationContext;
        this.bpmSignStatusService = bpmSignStatusService;
        this.bpmSignStatusMapper = bpmSignStatusMapper;
    }

    @PostMapping(path = "/start/{key}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE })
    public String start(
        @Valid @RequestPart("form") HashMap<String, String> form,
        @PathVariable String key,
        @Valid @RequestPart(name = "fileDto", required = false) List<BpmUploadFileDTO> dto,
        @RequestPart(name = "appendixFiles", required = false) List<MultipartFile> appendixFiles) throws IOException {

        // 產生要送給流程引擎的request dto
        ProcessReqDTO processReqDTO = new ProcessReqDTO();
        processReqDTO.setFormName(key);
        HashMap<String, Object> variables = new HashMap<>();
        BpmIsmsService service = (BpmIsmsService) applicationContext.getBean(Objects.requireNonNull(BpmIsmsServiceBeanNameEnum.getServiceBeanNameByKey(key)));
        UUID uuid = service.setVariables(variables, form.get(key));
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
            // 如果BPM寫入失敗，通知flowable原流程撤銷
            deleteProcessWhenSaveBpmFailed(processInstanceId);
            return "BPM寫入失敗，請聯絡管理員";
        }

        return processInstanceId;

    }
    @PostMapping(path = "/startTest/{key}", consumes = {MediaType.APPLICATION_JSON_VALUE })
    public String start(
           @PathVariable String key,
           @RequestBody HashMap<String,String> form) throws IOException {

        // 產生要送給流程引擎的request dto
        ProcessReqDTO processReqDTO = new ProcessReqDTO();
        processReqDTO.setFormName(key);
        HashMap<String, Object> variables = new HashMap<>();
        BpmIsmsService service = (BpmIsmsService) applicationContext.getBean(Objects.requireNonNull(BpmIsmsServiceBeanNameEnum.getServiceBeanNameByKey(key)));
        UUID uuid = service.setVariables(variables, form.get(key));
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
            service.saveBpm(uuid, processInstanceId, taskDTO, null, null);

        } catch (Exception e) {
            // 如果BPM寫入失敗，通知flowable原流程撤銷
            deleteProcessWhenSaveBpmFailed(processInstanceId);
            return "BPM寫入失敗，請聯絡管理員";
        }

        return processInstanceId;

    }
    @PatchMapping(path = "/patch/{key}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public String patch(
        @PathVariable String key,
        @Valid @RequestPart("form") HashMap<String, String> form,
        @Valid @RequestPart(name = "fileDto", required = false) List<BpmUploadFileDTO> dto,
        @RequestPart(name = "appendixFiles", required = false) List<MultipartFile> appendixFiles) throws IOException {
        log.info("ProcessL414Resource.java - start - 60 :: " + dto);
        log.info("ProcessL414Resource.java - start - 61 :: " + appendixFiles);
        BpmIsmsService service = (BpmIsmsService) applicationContext.getBean(Objects.requireNonNull(BpmIsmsServiceBeanNameEnum.getServiceBeanNameByKey(key)));

        return service.saveBpmByPatch(form.get(key), dto, appendixFiles);
    }
    @RequestMapping("/completeTask/{formId}")
    public String completeTask(@RequestBody CompleteReqDTO completeReqDTO, @PathVariable String formId) {
        log.info("ProcessL414Resource.java - completeTask - 183 :: " + completeReqDTO);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(gson.toJson(completeReqDTO), headers);
        ResponseEntity<String> exchange = restTemplate.exchange(FLOWABLE_PROCESS_URL + "/completeTask", HttpMethod.POST, requestEntity, String.class);
        if (exchange.getStatusCodeValue() == 200) {
            BpmSignStatusDTO bpmSignStatusDTO = getBpmSignStatusDTO(completeReqDTO, formId);
            BpmSignStatus bpmSignStatus = bpmSignStatusMapper.toEntity(bpmSignStatusDTO);
            bpmSignStatusService.saveBpmSignStatus(bpmSignStatus);
            if (Objects.equals(completeReqDTO.getIpt(), true)) {
                int i = formId.indexOf("-");
                String key = formId.substring(0, i);
                BpmIsmsService service = (BpmIsmsService) applicationContext.getBean(Objects.requireNonNull(BpmIsmsServiceBeanNameEnum.getServiceBeanNameByKey(key)));
                service.saveBpmByPatch(completeReqDTO.getForm().get(key));
            }
            return exchange.getBody();
        }
        return "伺服器忙碌中或查無任務，請稍候再試";
    }



    private static BpmSignStatusDTO getBpmSignStatusDTO(CompleteReqDTO completeReqDTO, String formId) {
        BpmSignStatusDTO bpmSignStatusDTO = new BpmSignStatusDTO();
        bpmSignStatusDTO.setFormId(formId);
        bpmSignStatusDTO.setProcessInstanceId(completeReqDTO.getProcessInstanceId());
        bpmSignStatusDTO.setTaskId(completeReqDTO.getTaskId());
        bpmSignStatusDTO.setTaskName(completeReqDTO.getTaskName());
        bpmSignStatusDTO.setSignerId(completeReqDTO.getSignerId());
        bpmSignStatusDTO.setSigner(completeReqDTO.getSigner());
        bpmSignStatusDTO.setSignUnit(completeReqDTO.getSignUnit());
        bpmSignStatusDTO.setSigningDatetime(Timestamp.valueOf(LocalDateTime.now()));
        bpmSignStatusDTO.setOpinion(completeReqDTO.getOpinion());
        bpmSignStatusDTO.setDirections(completeReqDTO.getDirections());
        if (completeReqDTO.getVariables().isEmpty()) {
            bpmSignStatusDTO.setSignResult("1");
        } else {
            Set<String> strings = completeReqDTO.getVariables().keySet();
            Iterator<String> iterator = strings.iterator();
            if (iterator.hasNext()) {
                bpmSignStatusDTO.setSignResult((String) completeReqDTO.getVariables().get(iterator.next()));
            }
        }

        return bpmSignStatusDTO;
    }



    /**
     * Delete processInstance when Bpm insert failed
     *
     * @param processInstanceId the id of the prcocessInstance.
     *
     */
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
