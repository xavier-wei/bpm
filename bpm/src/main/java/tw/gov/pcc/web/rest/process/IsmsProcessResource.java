package tw.gov.pcc.web.rest.process;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import tw.gov.pcc.domain.BpmIsmsL410;
import tw.gov.pcc.domain.BpmIsmsServiceBeanNameEnum;
import tw.gov.pcc.domain.SingerDecisionEnum;
import tw.gov.pcc.domain.User;
import tw.gov.pcc.domain.entity.BpmIsmsAdditional;
import tw.gov.pcc.domain.entity.BpmSignStatus;
import tw.gov.pcc.repository.BpmIsmsAdditionalRepository;
import tw.gov.pcc.repository.BpmIsmsL410Repository;
import tw.gov.pcc.service.BpmIsmsService;
import tw.gov.pcc.service.BpmSignStatusService;
import tw.gov.pcc.service.BpmSignerListService;
import tw.gov.pcc.service.dto.*;
import tw.gov.pcc.service.mapper.BpmIsmsL410Mapper;
import tw.gov.pcc.service.mapper.BpmSignStatusMapper;
import tw.gov.pcc.utils.MapUtils;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/process")
public class IsmsProcessResource {

    private static final Logger log = LoggerFactory.getLogger(IsmsProcessResource.class);

    @Value("${bpm.token}")
    private String TOKEN;

    private final ApplicationContext applicationContext;
    private final Gson gson = new Gson();
    private final String FLOWABLE_PROCESS_URL = "http://localhost:9973/process";
    private final RestTemplate restTemplate = new RestTemplate();
    private final HttpSession httpSession;
    private final BpmSignStatusService bpmSignStatusService;
    private final BpmSignStatusMapper bpmSignStatusMapper;
    private final BpmIsmsAdditionalRepository bpmIsmsAdditionalRepository;
    private final BpmSignerListService bpmSignerListService;

    private final BpmIsmsL410Mapper bpmIsmsL410Mapper;
    private final BpmIsmsL410Repository bpmIsmsL410Repository;

    public IsmsProcessResource(ApplicationContext applicationContext, HttpSession httpSession, BpmSignStatusService bpmSignStatusService, BpmSignStatusMapper bpmSignStatusMapper, BpmIsmsAdditionalRepository bpmIsmsAdditionalRepository, BpmSignerListService bpmSignerListService, BpmIsmsL410Mapper bpmIsmsL410Mapper, BpmIsmsL410Repository bpmIsmsL410Repository) {
        this.applicationContext = applicationContext;
        this.httpSession = httpSession;
        this.bpmSignStatusService = bpmSignStatusService;
        this.bpmSignStatusMapper = bpmSignStatusMapper;
        this.bpmIsmsAdditionalRepository = bpmIsmsAdditionalRepository;
        this.bpmSignerListService = bpmSignerListService;
        this.bpmIsmsL410Mapper = bpmIsmsL410Mapper;
        this.bpmIsmsL410Repository = bpmIsmsL410Repository;
    }

    @PostMapping(path = "/start/{key}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public String start(
        @Valid @RequestPart("form") HashMap<String, String> form,
        @PathVariable String key,
        @Valid @RequestPart(name = "fileDto", required = false) List<BpmUploadFileDTO> dto,
        @RequestPart(name = "appendixFiles", required = false) List<MultipartFile> appendixFiles,
        @RequestPart(name = "bpmIsmsL410", required = false) BpmIsmsL410DTO bpmIsmsL410DTO) throws IOException {
        log.info("IsmsProcessResource.java - start - 70 :: " + bpmIsmsL410DTO);
        log.info("IsmsProcessResource.java - start - 71 :: " + form);
        log.info("IsmsProcessResource.java - start - 72 :: " + key);
        log.info("IsmsProcessResource.java - start - 73 :: " + dto);
        log.info("IsmsProcessResource.java - start - 74 :: " + appendixFiles);
        // 取得存在HttpSession的user資訊
        User userInfo = getUserInfo();

//        // 產生要送給流程引擎的request dto
        ProcessReqDTO processReqDTO = new ProcessReqDTO();
        HashMap<String, Object> variables = new HashMap<>();
        BpmIsmsService service = (BpmIsmsService) applicationContext.getBean(Objects.requireNonNull(BpmIsmsServiceBeanNameEnum.getServiceBeanNameByKey(key)));
        UUID uuid = service.setVariables(variables, form.get(key), userInfo);
        processReqDTO.setFormName(key);
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

            if (!Objects.equals(bpmIsmsL410DTO, null)) {
                log.info("IsmsProcessResource.java - start - 102 :: " + " 安安我來了");
                BpmIsmsL410 bpmIsmsL410 = bpmIsmsL410Mapper.toEntity(bpmIsmsL410DTO);
                bpmIsmsL410Repository.save(bpmIsmsL410);
            }
            service.saveBpm(uuid, processInstanceId, taskDTO, dto, appendixFiles);

        } catch (Exception e) {
            e.printStackTrace();
            // 如果BPM寫入失敗，通知flowable原流程撤銷
            deleteProcessWhenSaveBpmFailed(processInstanceId);
            return "BPM寫入失敗，請聯絡管理員";
        }

        return processInstanceId;
//        return "";

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
        if (Objects.equals(completeReqDTO.getIpt(), true)) {
            int i = formId.indexOf("-");
            String key = formId.substring(0, i);
            BpmIsmsService service = (BpmIsmsService) applicationContext.getBean(Objects.requireNonNull(BpmIsmsServiceBeanNameEnum.getServiceBeanNameByKey(key)));
            service.saveBpmByPatch(completeReqDTO.getForm().get(key));

        }
        ResponseEntity<String> exchange = restTemplate.exchange(FLOWABLE_PROCESS_URL + "/completeTask", HttpMethod.POST, requestEntity, String.class);
        if (exchange.getStatusCodeValue() == 200) {
            BpmSignStatusDTO bpmSignStatusDTO = getBpmSignStatusDTO(completeReqDTO, formId);
            BpmSignStatus bpmSignStatus = bpmSignStatusMapper.toEntity(bpmSignStatusDTO);
            bpmSignStatusService.saveBpmSignStatus(bpmSignStatus);
            return exchange.getBody();
        }
        return "伺服器忙碌中或查無任務，請稍候再試";
    }

    @PutMapping("/receiveEndEvent")
    public void receiveEndEvent(@RequestBody EndEventDTO endEventDTO) {
        log.info("ProcessL414Resource.java - receiveEndEvent - 196 :: " + endEventDTO.getProcessInstanceId());
        if (TOKEN.equals(endEventDTO.getToken())) {
            BpmIsmsService service = (BpmIsmsService) applicationContext.getBean(Objects.requireNonNull(BpmIsmsServiceBeanNameEnum.getServiceBeanNameByKey(endEventDTO.getFormName())));
            service.endForm(endEventDTO);

            return;
        }
        log.warn("ProcessL414Resource.java - receiveEndEvent - 203 ::{} ", "流程發生意外終止");
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

    @PostMapping("/getIsms/{key}/{formId}")
    public Map<String, Object> getIsms(
        @PathVariable String key,
        @PathVariable String formId
    ) {
        BpmIsmsService service = (BpmIsmsService) applicationContext.getBean(Objects.requireNonNull(BpmIsmsServiceBeanNameEnum.getServiceBeanNameByKey(key)));

        return new MapUtils().getNewMap(service.getBpm(formId));
    }

    @RequestMapping("/queryTask")
    public List<Map<String, Object>> queryTask(@Valid @RequestPart(required = false) BpmFormQueryDto bpmFormQueryDto) {
        User userInfo = getUserInfo();
        log.info("ProcessL414Resource.java - queryTask - 193 :: " + userInfo.getUserId());
        log.info("ProcessL414Resource.java - queryTask - 194 :: " + bpmFormQueryDto);

//        String id="ApplyTester";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(userInfo.getUserId(), headers);

        ResponseEntity<String> exchange = restTemplate.exchange(FLOWABLE_PROCESS_URL + "/queryProcessingTask", HttpMethod.POST, requestEntity, String.class);

        if (exchange.getStatusCodeValue() == 200) {
            String body = exchange.getBody();
            Type listType = new TypeToken<ArrayList<TaskDTO>>() {
            }.getType();
            List<TaskDTO> taskDTOS = new Gson().fromJson(body, listType);
            assert taskDTOS != null;
            return taskDTOS.isEmpty() ? null :
                taskDTOS.stream()
                    .map(taskDTO -> {
                        if (taskDTO.getFormName().equals("Additional")) {
                            BpmIsmsAdditional bpmIsmsAdditional = bpmIsmsAdditionalRepository.findByProcessInstanceId(taskDTO.getProcessInstanceId());
                            List<Map<String, Object>> mapList = bpmIsmsAdditionalRepository.findAllByProcessInstanceId(
                                bpmIsmsAdditional.getMainProcessInstanceId(),
                                bpmFormQueryDto.getFormId(),
                                bpmFormQueryDto.getProcessInstanceStatus(),
                                bpmFormQueryDto.getUnit(),
                                bpmFormQueryDto.getAppName(),
                                bpmFormQueryDto.getDateStart(),
                                bpmFormQueryDto.getDateEnd()
                            );

                            if (!mapList.isEmpty()) {
                                Map<String, Object> map = new HashMap<>(new MapUtils().getNewMap(mapList.get(0)));
                                map.put("processInstanceId", taskDTO.getProcessInstanceId());
                                map.put("taskId", taskDTO.getTaskId());
                                map.put("taskName", taskDTO.getTaskName());
                                String decisionByName = SingerDecisionEnum.getDecisionByName(taskDTO.getTaskName());
                                map.put("decisionRole", decisionByName);
                                map.put("additional", true);
                                return map;
                            } else {
                                return null;
                            }
                        } else {
                            //                        BpmIsmsL414DTO dto = bpmIsmsL414Mapper.toDto(bpmIsmsL414Repository.findFirstByProcessInstanceId(taskDTO.getProcessInstanceId()));
//                            BpmIsmsL414DTO dto = bpmIsmsL414Repository.findByBpmIsmsL414(bpmFormQueryDto, taskDTO.getProcessInstanceId());
                            List<Map<String, Object>> mapList = bpmIsmsAdditionalRepository.findAllByProcessInstanceId(
                                taskDTO.getProcessInstanceId(),
                                bpmFormQueryDto.getFormId(),
                                bpmFormQueryDto.getProcessInstanceStatus(),
                                bpmFormQueryDto.getUnit(),
                                bpmFormQueryDto.getAppName(),
                                bpmFormQueryDto.getDateStart(),
                                bpmFormQueryDto.getDateEnd()
                            );

                            if (!mapList.isEmpty()) {
                                Map<String, Object> map = new HashMap<>(new MapUtils().getNewMap(mapList.get(0)));
                                map.put("taskId", taskDTO.getTaskId());
                                map.put("taskName", taskDTO.getTaskName());
                                String decisionByName = SingerDecisionEnum.getDecisionByName(taskDTO.getTaskName());
                                map.put("decisionRole", decisionByName);
                                map.put("additional", false);
                                return map;
                            } else {
                                return null;
                            }
                        }

                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        }

        return null;
    }

    @RequestMapping("/deleteProcessInstance/{processInstanceId}")
    public void deleteProcessInstance(@PathVariable String processInstanceId) {
        log.info("ProcessL414Resource.java - deleteProcessInstance - 206 :: " + processInstanceId);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HashMap<String, String> deleteRequest = new HashMap<>();
        deleteRequest.put("processInstanceId", processInstanceId);
        deleteRequest.put("token", TOKEN);
        HttpEntity<String> requestEntity = new HttpEntity<>(gson.toJson(deleteRequest), headers);
        ResponseEntity<String> exchange = restTemplate.exchange(FLOWABLE_PROCESS_URL + "/deleteProcess", HttpMethod.POST, requestEntity, String.class);

    }

    private User getUserInfo() {
        return (User) httpSession.getAttribute("userInfo");
    }

    @RequestMapping("/notify/queryTask")
    public List<Map<String, Object>> notifyQueryTask(@Valid @RequestPart(required = false) BpmFormQueryDto bpmFormQueryDto) {
        User userInfo = getUserInfo();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(userInfo.getUserId(), headers);

        ResponseEntity<String> exchange = restTemplate.exchange(FLOWABLE_PROCESS_URL + "/getAllTask", HttpMethod.POST, requestEntity, String.class);

        if (exchange.getStatusCodeValue() == 200) {
            String body = exchange.getBody();
            Type listType = new TypeToken<ArrayList<TaskDTO>>() {
            }.getType();
            List<TaskDTO> taskDTOS = new Gson().fromJson(body, listType);

            assert taskDTOS != null;
            return taskDTOS.isEmpty() ? null :
                taskDTOS.stream()
                    .map(taskDTO -> {
                        List<Map<String, Object>> mapList = bpmIsmsAdditionalRepository.findAllByProcessInstanceId(
                            taskDTO.getProcessInstanceId(),
                            bpmFormQueryDto.getFormId(),
                            bpmFormQueryDto.getProcessInstanceStatus(),
                            bpmFormQueryDto.getUnit(),
                            bpmFormQueryDto.getAppName(),
                            bpmFormQueryDto.getDateStart(),
                            bpmFormQueryDto.getDateEnd()
                        );
                        if (!mapList.isEmpty()) {
                            Map<String, Object> map = new HashMap<>(new MapUtils().getNewMap(mapList.get(0)));
                            map.put("taskId", taskDTO.getTaskId());
                            map.put("taskName", taskDTO.getTaskName());
                            map.put("decisionRole", SingerDecisionEnum.getDecisionByName(taskDTO.getTaskName()));
                            map.put("additional", false);
                            return map;
                        } else {
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        }

        return null;
    }


}
