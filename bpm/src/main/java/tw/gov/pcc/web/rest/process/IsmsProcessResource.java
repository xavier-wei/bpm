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
import org.springframework.web.server.ResponseStatusException;
import tw.gov.pcc.domain.BpmIsmsL410;
import tw.gov.pcc.domain.BpmIsmsServiceBeanNameEnum;
import tw.gov.pcc.domain.SingerDecisionEnum;
import tw.gov.pcc.domain.User;
import tw.gov.pcc.domain.entity.BpmIsmsAdditional;
import tw.gov.pcc.domain.entity.BpmSignStatus;
import tw.gov.pcc.eip.impl.EipcodeDaoImpl;
import tw.gov.pcc.repository.BpmIsmsAdditionalRepository;
import tw.gov.pcc.repository.BpmIsmsL410Repository;
import tw.gov.pcc.service.BpmIsmsService;
import tw.gov.pcc.service.BpmSignStatusService;
import tw.gov.pcc.service.SubordinateTaskService;
import tw.gov.pcc.service.dto.*;
import tw.gov.pcc.service.mapper.BpmIsmsL410Mapper;
import tw.gov.pcc.service.mapper.BpmSignStatusMapper;
import tw.gov.pcc.utils.MapUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    private final String token;
    private static final String PROCESS_INSTANCE_ID = "processInstanceId";
    private static final String TASK_ID = "taskId";
    private final ApplicationContext applicationContext;
    private final Gson gson = new Gson();

    @Value("${flowable.url}")
    private String flowableProcessUrl;
    private final RestTemplate restTemplate = new RestTemplate();
    private final HttpSession httpSession;
    private final BpmSignStatusService bpmSignStatusService;
    private final BpmSignStatusMapper bpmSignStatusMapper;
    private final BpmIsmsAdditionalRepository bpmIsmsAdditionalRepository;
    private final BpmIsmsL410Mapper bpmIsmsL410Mapper;
    private final BpmIsmsL410Repository bpmIsmsL410Repository;

    private final SubordinateTaskService subordinateTaskService;

    public IsmsProcessResource(ApplicationContext applicationContext, HttpSession httpSession, BpmSignStatusService bpmSignStatusService, BpmSignStatusMapper bpmSignStatusMapper, BpmIsmsAdditionalRepository bpmIsmsAdditionalRepository, BpmIsmsL410Mapper bpmIsmsL410Mapper, BpmIsmsL410Repository bpmIsmsL410Repository, SubordinateTaskService subordinateTaskService, EipcodeDaoImpl eipcodeDao) {
        this.applicationContext = applicationContext;
        this.httpSession = httpSession;
        this.bpmSignStatusService = bpmSignStatusService;
        this.bpmSignStatusMapper = bpmSignStatusMapper;
        this.bpmIsmsAdditionalRepository = bpmIsmsAdditionalRepository;
        this.bpmIsmsL410Mapper = bpmIsmsL410Mapper;
        this.bpmIsmsL410Repository = bpmIsmsL410Repository;
        this.subordinateTaskService = subordinateTaskService;
        this.token = eipcodeDao.findByCodeKindOrderByScodeno("BPM_TOKEN").get(0).getCodename();
    }

    @PostMapping(path = "/start/{key}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<String> start(
        @Valid @RequestPart("form") Map<String, String> form,
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
        ResponseEntity<String> exchange = restTemplate.exchange(flowableProcessUrl + "/startProcess", HttpMethod.POST, requestEntity, String.class);
        String processInstanceId;
        TaskDTO taskDTO;
        if (exchange.getStatusCodeValue() == 200) {
            taskDTO = gson.fromJson(exchange.getBody(), TaskDTO.class);
            processInstanceId = taskDTO.getProcessInstanceId();
        } else {
            log.error("flowableProcess - startProcess - 90 :: {} ", "flowableConnectionError");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("流程引擎連線異常，請聯絡管理員");
        }

        try {

            if (!Objects.equals(bpmIsmsL410DTO, null)) {
                BpmIsmsL410 bpmIsmsL410 = bpmIsmsL410Mapper.toEntity(bpmIsmsL410DTO);
                bpmIsmsL410Repository.save(bpmIsmsL410);
            }
            service.saveBpm(uuid, processInstanceId, taskDTO, dto, appendixFiles);

        } catch (ResponseStatusException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            log.error("BpmSaveError - startProcess - 90 :: {} ", "BpmSaveError");
            // 如果BPM寫入失敗，通知flowable原流程撤銷
            deleteProcessWhenSaveBpmFailed(processInstanceId);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("表單寫入失敗，流程已取消，請稍候再試");
        }
        log.info("IsmsProcessResource.java - start - 102 :: " + processInstanceId);
        return ResponseEntity.ok(processInstanceId);

    }

    @PatchMapping(path = "/patch/{key}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public String patch(
        @PathVariable String key,
        @Valid @RequestPart("form") HashMap<String, String> form,
        @Valid @RequestPart(name = "fileDto", required = false) List<BpmUploadFileDTO> dto,
        @RequestPart(name = "appendixFiles", required = false) List<MultipartFile> appendixFiles) throws IOException {

        BpmIsmsService service = (BpmIsmsService) applicationContext.getBean(Objects.requireNonNull(BpmIsmsServiceBeanNameEnum.getServiceBeanNameByKey(key)));

        return service.saveBpmByPatch(form.get(key), dto, appendixFiles);
    }

    @PostMapping("/completeTask/{formId}")
    public ResponseEntity<String> completeTask(@RequestBody CompleteReqDTO completeReqDTO, @PathVariable String formId) {
        log.info("ProcessL414Resource.java - completeTask - 183 :: " + completeReqDTO);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(gson.toJson(completeReqDTO), headers);

        //判斷是否是資推或機房的，如果是就去更新資料
        if (Objects.equals(completeReqDTO.getIpt(), true)) {
            int i = formId.indexOf("-");
            String key = formId.substring(0, i);
            BpmIsmsService service = (BpmIsmsService) applicationContext.getBean(Objects.requireNonNull(BpmIsmsServiceBeanNameEnum.getServiceBeanNameByKey(key)));
            service.saveBpmByPatch(completeReqDTO.getForm().get(key));
        }

        //判斷variables裡的value，2代表前端送補件過來，需要把表單的IS_SUBMIT改回0 補件的人才能編輯
        if (completeReqDTO.getVariables() != null && completeReqDTO.getVariables().containsValue("2")) {
            int i = formId.indexOf("-");
            String key = formId.substring(0, i);
            BpmIsmsService service = (BpmIsmsService) applicationContext.getBean(Objects.requireNonNull(BpmIsmsServiceBeanNameEnum.getServiceBeanNameByKey(key)));
            service.saveBpmByPatchToIsSubmit(completeReqDTO.getProcessInstanceId());
        }


        ResponseEntity<String> exchange = restTemplate.exchange(flowableProcessUrl + "/completeTask", HttpMethod.POST, requestEntity, String.class);
        if (exchange.getStatusCodeValue() == 200) {
            BpmSignStatusDTO bpmSignStatusDTO = getBpmSignStatusDTO(completeReqDTO, formId);
            BpmSignStatus bpmSignStatus = bpmSignStatusMapper.toEntity(bpmSignStatusDTO);
            bpmSignStatusService.saveBpmSignStatus(bpmSignStatus);
            return ResponseEntity.ok(exchange.getBody());
        }
        log.error("flowableProcess - startProcess - 90 :: {} ", "flowableConnectionError");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("流程引擎連線異常，請聯絡管理員");
    }

    @RequestMapping("/receiveEndEvent")
    public void receiveEndEvent(@RequestBody EndEventDTO endEventDTO, HttpServletRequest request) {
        log.info("ProcessL414Resource.java - receiveEndEvent - 196 :: " + endEventDTO.getProcessInstanceId());
        if (token.equals(request.getHeader("flowableToken"))) {
            BpmIsmsService service = (BpmIsmsService) applicationContext.getBean(Objects.requireNonNull(BpmIsmsServiceBeanNameEnum.getServiceBeanNameByKey(endEventDTO.getFormName())));
            service.endForm(endEventDTO);

            return;
        }
        log.warn("ProcessL414Resource.java - receiveEndEvent - 203 ::{} ", "流程發生意外終止");

//        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"流程發生意外終止");
    }


    /**
     * Delete processInstance when Bpm insert failed
     *
     * @param processInstanceId the id of the prcocessInstance.
     */
    public void deleteProcessWhenSaveBpmFailed(String processInstanceId) {
        log.info("ProcessL414Resource.java - deleteProcessWhenSaveBpmFailed - 206 :: " + processInstanceId);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HashMap<String, String> deleteRequest = new HashMap<>();
        deleteRequest.put(PROCESS_INSTANCE_ID, processInstanceId);
        deleteRequest.put("token", token);
        HttpEntity<String> requestEntity = new HttpEntity<>(gson.toJson(deleteRequest), headers);
        restTemplate.exchange(flowableProcessUrl + "/deleteProcess", HttpMethod.POST, requestEntity, String.class);

    }

    @PostMapping("/getIsms/{key}/{formId}")
    public Map<String, Object> getIsms(
        @PathVariable String key,
        @PathVariable String formId
    ) {
        BpmIsmsService service = (BpmIsmsService) applicationContext.getBean(Objects.requireNonNull(BpmIsmsServiceBeanNameEnum.getServiceBeanNameByKey(key)));

        return new MapUtils().getNewMap(service.getBpm(formId));
    }

    @GetMapping("/flowImage/{processInstanceId}")
    public String getImage(@PathVariable String processInstanceId, HttpServletResponse response) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(gson.toJson(processInstanceId), headers);
        ResponseEntity<String> exchange = restTemplate.exchange(flowableProcessUrl + "/pic?processId=" + processInstanceId, HttpMethod.GET, requestEntity, String.class);
        return exchange.getBody();
    }

    @RequestMapping("/queryTask")
    public List<Map<String, Object>> queryTask(@Valid @RequestPart(required = false) BpmFormQueryDto bpmFormQueryDto) {
        User userInfo = getUserInfo();
        log.info("ProcessL414Resource.java - queryTask - 193 :: " + userInfo.getUserId());
        log.info("ProcessL414Resource.java - queryTask - 194 :: " + bpmFormQueryDto);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(userInfo.getUserId(), headers);

        ResponseEntity<String> exchange = restTemplate.exchange(flowableProcessUrl + "/queryProcessingTask", HttpMethod.POST, requestEntity, String.class);

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
                                map.put(PROCESS_INSTANCE_ID, taskDTO.getProcessInstanceId());
                                map.put(TASK_ID, taskDTO.getTaskId());
                                map.put("taskName", "加簽-" + bpmIsmsAdditional.getTaskName());
                                String decisionByName = SingerDecisionEnum.getDecisionByName(taskDTO.getTaskName());
                                map.put("decisionRole", decisionByName);
                                map.put("additional", true);
                                return map;
                            } else {
                                return null;
                            }
                        } else {
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
                                map.put(TASK_ID, taskDTO.getTaskId());
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

        return Collections.emptyList();
    }

    @PostMapping("/queryProcessingTaskNumbers")
    public Integer queryProcessingTaskNumbers(@RequestBody String id) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(id, headers);

        ResponseEntity<String> exchange = restTemplate.exchange(flowableProcessUrl + "/queryProcessingTaskNumbers", HttpMethod.POST, requestEntity, String.class);

        if (exchange.getStatusCodeValue() == 200) {
            return Integer.parseInt(exchange.getBody());
        }
        return 0;
    }

    @RequestMapping("/deleteProcessInstance")
    public void deleteProcessInstance(@RequestBody ProcessInstanceIdRequestDTO Request) {
        log.info("ProcessL414Resource.java - deleteProcessInstance - 206 :: " + Request.getProcessInstanceId());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HashMap<String, String> deleteRequest = new HashMap<>();
        deleteRequest.put(PROCESS_INSTANCE_ID, Request.getProcessInstanceId());
        deleteRequest.put("token", token);
        HttpEntity<String> requestEntity = new HttpEntity<>(gson.toJson(deleteRequest), headers);
        restTemplate.exchange(flowableProcessUrl + "/deleteProcess", HttpMethod.POST, requestEntity, String.class);
        BpmIsmsService service = (BpmIsmsService) applicationContext.getBean(Objects.requireNonNull(BpmIsmsServiceBeanNameEnum.getServiceBeanNameByKey(Request.getKey())));
        //註銷流程後，需要把表單內的ProcessInstanceStatus改成3,來判斷此表單已註銷
        service.cancel(Request.getProcessInstanceId());
    }

    @PostMapping("/getAllSubordinateTask")
    public List<Map<String, Object>> getAllSubordinateTask(@Valid @RequestPart(required = false) BpmFormQueryDto bpmFormQueryDto) {
        User user = getUserInfo();
        String titleName = user.getTitleName();
        if ("處長".equals(titleName) || "副處長".equals(titleName) || "主任".equals(titleName)) {
            List<String> allSubordinate = subordinateTaskService.findAllSubordinate(user.getUserId());
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> requestEntity = new HttpEntity<>(gson.toJson(allSubordinate), headers);
            ResponseEntity<String> response = restTemplate.exchange(flowableProcessUrl + "/getAllSubordinateTask", HttpMethod.POST, requestEntity, String.class);
            if (response.getStatusCodeValue() == 200) {
                String body = response.getBody();
                Type listType = new TypeToken<ArrayList<TaskDTO>>() {
                }.getType();
                List<TaskDTO> taskDTOS = gson.fromJson(body, listType);
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
                                    map.put(PROCESS_INSTANCE_ID, taskDTO.getProcessInstanceId());
                                    map.put(TASK_ID, taskDTO.getTaskId());
                                    map.put("taskName", "加簽-" + bpmIsmsAdditional.getTaskName());
                                    String decisionByName = SingerDecisionEnum.getDecisionByName(taskDTO.getTaskName());
                                    map.put("decisionRole", decisionByName);
                                    map.put("additional", true);
                                    return map;
                                } else {
                                    return null;
                                }
                            } else {
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
                                    map.put(TASK_ID, taskDTO.getTaskId());
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
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "權限不足");
    }


    @RequestMapping("/notify/queryTask")
    public List<Map<String, Object>> notifyQueryTask(@Valid @RequestPart(required = false) BpmFormQueryDto bpmFormQueryDto) {
        User userInfo = getUserInfo();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(userInfo.getUserId(), headers);

        ResponseEntity<String> exchange = restTemplate.exchange(flowableProcessUrl + "/getAllTask", HttpMethod.POST, requestEntity, String.class);

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
                            map.put(TASK_ID, taskDTO.getTaskId());
                            map.put("taskName", taskDTO.getTaskName());
                            map.put("decisionRole", SingerDecisionEnum.getDecisionByName(taskDTO.getTaskName()));
                            map.put("additional", false);
                            map.put("pendingUserId", taskDTO.getPendingUserId());
                            return map;
                        } else {
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        }

        return Collections.emptyList();
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

    private User getUserInfo() {
        return (User) httpSession.getAttribute("userInfo");
    }

}
