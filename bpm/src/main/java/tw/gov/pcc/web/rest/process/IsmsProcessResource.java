package tw.gov.pcc.web.rest.process;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import tw.gov.pcc.domain.BpmIsmsServiceBeanNameEnum;
import tw.gov.pcc.domain.MailInfo;
import tw.gov.pcc.domain.SignerDecisionEnum;
import tw.gov.pcc.domain.User;
import tw.gov.pcc.domain.entity.BpmIsmsAdditional;
import tw.gov.pcc.service.*;
import tw.gov.pcc.service.dto.*;
import tw.gov.pcc.utils.CommonUtils;
import tw.gov.pcc.utils.MapUtils;
import tw.gov.pcc.utils.ParameterUtil;
import tw.gov.pcc.web.rest.io.FileMediaType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/process")
public class IsmsProcessResource {

    private static final Logger log = LoggerFactory.getLogger(IsmsProcessResource.class);
    private static final String PROCESS_INSTANCE_ID = "processInstanceId";
    private static final String TASK_ID = "taskId";
    private final ApplicationContext applicationContext;
    private final Gson gson = new Gson();
    private static final HttpHeaders HTTP_HEADERS = new HttpHeaders();

    @Value("${flowable.url}")
    private String flowableProcessUrl;
    private final RestTemplate restTemplate = new RestTemplate();


    private final HttpSession httpSession;
    private final BpmSignStatusService bpmSignStatusService;
    private final BpmSignerListService bpmSignerListService;
    private final SubordinateTaskService subordinateTaskService;

    private final BpmUploadFileService bpmUploadFileService;
    private final MailService mailService;

    private final BpmIsmsAdditionalService bpmIsmsAdditionalService;

    private final String[] fileTypeLimit = {
        FileMediaType.IMAGE_JPEG_VALUE,
        FileMediaType.IMAGE_PNG_VALUE,
        FileMediaType.IMAGE_GIF_VALUE,
        FileMediaType.APPLICATION_PDF_VALUE,
        FileMediaType.APPLICATION_DOC_VALUE,
        FileMediaType.APPLICATION_DOCX_VALUE,
        FileMediaType.APPLICATION_ODT_VALUE,
        FileMediaType.APPLICATION_XLS_VALUE,
        FileMediaType.APPLICATION_XLSX_VALUE,
        FileMediaType.APPLICATION_ODS_VALUE,
    };

    public IsmsProcessResource(HttpSession httpSession, BpmSignStatusService bpmSignStatusService, SubordinateTaskService subordinateTaskService, BpmSignerListService bpmSignerListService, ApplicationContext applicationContext, BpmUploadFileService bpmUploadFileService, MailService mailService, BpmIsmsAdditionalService bpmIsmsAdditionalService) {
        this.httpSession = httpSession;
        this.bpmSignStatusService = bpmSignStatusService;
        this.subordinateTaskService = subordinateTaskService;
        this.bpmSignerListService = bpmSignerListService;
        this.applicationContext = applicationContext;
        this.bpmUploadFileService = bpmUploadFileService;
        this.mailService = mailService;
        this.bpmIsmsAdditionalService = bpmIsmsAdditionalService;
    }

    @PostMapping(path = "/start/{key}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<String> start(
        @Valid @RequestPart("form") Map<String, String> form,
        @PathVariable String key,
        @Valid @RequestPart(name = "fileDto", required = false) List<BpmUploadFileDTO> dto,
        @RequestPart(name = "appendixFiles", required = false) List<MultipartFile> appendixFiles) {
        log.info("IsmsProcessResource.java - start - 71 :: " + form);
        log.info("IsmsProcessResource.java - start - 72 :: " + key);
        log.info("IsmsProcessResource.java - start - 73 :: " + dto);
        log.info("IsmsProcessResource.java - start - 74 :: " + appendixFiles);

        validateAppendixFilesSize(appendixFiles);

        // 取得存在HttpSession的user資訊
        User userInfo = getUserInfo();

        // 產生要送給流程引擎的request dto
        ProcessReqDTO processReqDTO = new ProcessReqDTO();
        // 產生要送給流程引擎的variables(解析成流程定義的各種參數用)
        HashMap<String, Object> variables = new HashMap<>();
        // 由前端傳來的key，取得對應的service
        BpmIsmsCommonService service = (BpmIsmsCommonService) applicationContext.getBean(Objects.requireNonNull(BpmIsmsServiceBeanNameEnum.getServiceBeanNameByKey(key)));

        // 產生一個UUID，用來當作key，存放在DTO_HOLDER裡，等待流程引擎回傳task dto時，再取出來用
        UUID uuid = service.setVariables(variables, form.get(key), userInfo);

        processReqDTO.setFormName(key);
        processReqDTO.setVariables(variables);

        // 送出request dto
        ResponseEntity<String> exchange = sendRequestEntity(gson.toJson(processReqDTO), "/startProcess", HttpMethod.POST);

        String processInstanceId;
        TaskDTO taskDTO;

        // 如果流程引擎回傳200，代表成功，取得task dto
        if (exchange.getStatusCodeValue() == 200) {
            taskDTO = gson.fromJson(exchange.getBody(), TaskDTO.class);
            processInstanceId = taskDTO.getProcessInstanceId();
        } else {
            log.error("flowableProcess - startProcess - 90 :: {} ", "flowableConnectionError");
            // 如果流程引擎回傳非200，代表連線異常，通知前端，並刪除DTO_HOLDER(VARIABLE_HOLDER)裡的資料
            service.removeHolder(uuid);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("流程引擎連線異常，請聯絡管理員");

        }

        // 如果流程引擎回傳200，代表成功，將表單資料存入資料庫
        try {

            service.saveBpm(uuid, processInstanceId, taskDTO, dto, appendixFiles);

        } catch (ResponseStatusException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            log.error("BpmSaveError - startProcess - 90 :: {} ", "BpmSaveError");
            // 如果BPM寫入失敗，通知flowable原流程撤銷
            deleteProcessWhenSaveBpmFailed(processInstanceId);
            // 刪除DTO_HOLDER(VARIABLE_HOLDER)裡的資料
            service.removeHolder(uuid);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("表單寫入失敗，流程已取消，請稍候再試");
        }
        log.info("IsmsProcessResource.java - start - 102 :: " + processInstanceId);
        return ResponseEntity.ok(processInstanceId);

    }


    @PatchMapping(path = "/patch/{key}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public String patch(
        @PathVariable String key,
        @Valid @RequestPart("form") Map<String, String> form,
        @Valid @RequestPart(name = "fileDto", required = false) List<BpmUploadFileDTO> dto,
        @RequestPart(name = "appendixFiles", required = false) List<MultipartFile> appendixFiles) {

        //驗證上傳檔案的大小與格式
        validateAppendixFilesSize(appendixFiles);

        BpmIsmsPatchService service = (BpmIsmsPatchService) applicationContext.getBean(Objects.requireNonNull(BpmIsmsServiceBeanNameEnum.getServiceBeanNameByKey(key)));

        return service.saveBpmByPatch(form.get(key), dto, appendixFiles);

    }


    // 僅有L410可打這支API，其他不要打，欸出歹事
    @PostMapping(path = "/edit/{key}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<String> editProcess(
        @PathVariable String key,
        @Valid @RequestPart("form") Map<String, String> form,
        @Valid @RequestPart(name = "fileDto", required = false) List<BpmUploadFileDTO> dto,
        @RequestPart(name = "appendixFiles", required = false) List<MultipartFile> appendixFiles
    ) {
        //驗證上傳檔案的大小與格式
        validateAppendixFilesSize(appendixFiles);

        BpmIsmsL410DTO bpmIsmsL410DTO = gson.fromJson(form.get(key), BpmIsmsL410DTO.class);
        User userInfo = getUserInfo();

        ProcessReqDTO processReqDTO = new ProcessReqDTO();
        HashMap<String, Object> variables = new HashMap<>();
        BpmIsmsCommonService service = (BpmIsmsCommonService) applicationContext.getBean(Objects.requireNonNull(BpmIsmsServiceBeanNameEnum.getServiceBeanNameByKey(key)));
        UUID uuid = service.setVariables(variables, form.get(key), userInfo);
        processReqDTO.setFormName(key);
        processReqDTO.setVariables(variables);

        ResponseEntity<String> exchange = sendRequestEntity(gson.toJson(processReqDTO), "/startProcess", HttpMethod.POST);

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
            bpmSignerListService.deleteAllByFormId(bpmIsmsL410DTO.getFormId());
            service.saveBpm(uuid, processInstanceId, taskDTO, dto, appendixFiles);
            deleteProcessWhenSaveBpmFailed(bpmIsmsL410DTO.getProcessInstanceId());
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


    /*
     * @param formId 流程實例ID
     * @param CompleteReqDTO 完成任務的request dto
     */
    @PostMapping(path = "/completeTask/{formId}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<String> completeTask(
        @RequestPart("completeReqDTO") CompleteReqDTO completeReqDTO,
        @PathVariable String formId,
        @Valid @RequestPart(name = "fileDto", required = false) List<BpmUploadFileDTO> dto,
        @RequestPart(name = "appendixFiles", required = false) List<MultipartFile> appendixFiles) {

        //驗證上傳檔案的大小與格式
        validateAppendixFilesSize(appendixFiles);

        int i = formId.indexOf("-");
        String key = formId.substring(0, i);
        BpmIsmsPatchService service = (BpmIsmsPatchService) applicationContext.getBean(Objects.requireNonNull(BpmIsmsServiceBeanNameEnum.getServiceBeanNameByKey(key)));

        //判斷是否是資推或機房的，如果是就去更新資料
        if (Objects.equals(completeReqDTO.getIpt(), true)) {

            service.saveBpmByPatch(completeReqDTO.getForm().get(key));
        }

        // 判斷variables裡的value，2代表前端送補件過來，需要把表單的IS_SUBMIT改回0 補件的人才能編輯
        if (completeReqDTO.getVariables() != null && completeReqDTO.getVariables().containsValue("2")) {
            service.saveBpmByPatchToIsSubmit(completeReqDTO.getProcessInstanceId());
        }

        // 簽核過程或加簽時，都有需要讓簽核人員可以上傳檔案。
        if (appendixFiles != null) {
            bpmUploadFileService.savePhoto(dto, appendixFiles, formId);
        }

        // 送出request dto
        ResponseEntity<String> exchange = sendRequestEntity(gson.toJson(completeReqDTO), "/completeTask", HttpMethod.POST);

        // 如果流程引擎回傳200，代表成功，將簽核狀態存入資料庫
        if (exchange.getStatusCodeValue() == 200) {
            bpmSignStatusService.saveBpmSignStatus(completeReqDTO, formId);
            return ResponseEntity.ok(exchange.getBody());
        }
        log.error("flowableProcess - startProcess - 90 :: {} ", "flowableConnectionError");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("流程引擎連線異常，請聯絡管理員");
    }

    @RequestMapping("/receiveEndEvent")
    public void receiveEndEvent(@RequestBody EndEventDTO endEventDTO, HttpServletRequest request) {
        log.info("ProcessL414Resource.java - receiveEndEvent - 196 :: " + endEventDTO.getProcessInstanceId());
        if (ParameterUtil.getToken().equals(request.getHeader("flowableToken"))) {
            BpmIsmsCommonService service = (BpmIsmsCommonService) applicationContext.getBean(Objects.requireNonNull(BpmIsmsServiceBeanNameEnum.getServiceBeanNameByKey(endEventDTO.getFormName())));
            MailInfo mailInfo = service.endForm(endEventDTO);
            mailService.sendMail(mailInfo);
            return;
        }
        log.warn("ProcessL414Resource.java - receiveEndEvent - 203 ::{} ", "流程發生意外終止");

        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "流程發生意外終止");
    }


    @PostMapping("/getIsms/{key}/{formId}")
    public Map<String, Object> getIsms(
        @PathVariable String key,
        @PathVariable String formId
    ) {
        BpmIsmsCommonService service = (BpmIsmsCommonService) applicationContext.getBean(Objects.requireNonNull(BpmIsmsServiceBeanNameEnum.getServiceBeanNameByKey(key)));
        return new MapUtils().getNewMap(service.getBpm(formId));
    }

    @GetMapping("/flowImage/{processInstanceId}")
    public String getImage(@PathVariable String processInstanceId) {

        ResponseEntity<String> exchange = sendRequestEntity(gson.toJson(processInstanceId), "/pic?processId=" + processInstanceId, HttpMethod.GET);
        return exchange.getBody();

    }

    @RequestMapping("/queryTask")
    public List<Map<String, Object>> queryTask(@RequestBody BpmFormQueryDto bpmFormQueryDto) {
        User userInfo = getUserInfo();
        log.info("ProcessL414Resource.java - queryTask - 193 :: " + userInfo.getUserId());
        log.info("ProcessL414Resource.java - queryTask - 194 :: " + bpmFormQueryDto);

        ResponseEntity<String> exchange = sendRequestEntity(userInfo.getUserId(), "/queryProcessingTask", HttpMethod.POST);

        if (exchange.getStatusCodeValue() == 200) {
            String body = exchange.getBody();
            Type listType = new TypeToken<ArrayList<TaskDTO>>() {
            }.getType();
            List<TaskDTO> taskDTOS = new Gson().fromJson(body, listType);
            assert taskDTOS != null;
            return getMaps(bpmFormQueryDto, taskDTOS);
        }

        return Collections.emptyList();
    }


    @PostMapping("/queryProcessingTaskNumbers")
    public Integer queryProcessingTaskNumbers(@RequestBody String id) {


        ResponseEntity<String> exchange = sendRequestEntity(gson.toJson(id), "/queryProcessingTaskNumbers", HttpMethod.POST);

        if (exchange.getStatusCodeValue() == 200) {
            return Integer.parseInt(Objects.requireNonNull(exchange.getBody()));
        }

        return 0;

    }

    @RequestMapping("/deleteProcessInstance")
    public void deleteProcessInstance(@RequestBody ProcessInstanceIdRequestDTO request) {
        log.info("ProcessL414Resource.java - deleteProcessInstance - 206 :: " + request.getProcessInstanceId());

        Map<String, String> deleteRequest = new HashMap<>();
        deleteRequest.put(PROCESS_INSTANCE_ID, request.getProcessInstanceId());
        deleteRequest.put("token", ParameterUtil.getToken());

        // 查看是否此任務在加簽中，若是，則把加簽的processInstance也一併刪除
        bpmIsmsAdditionalService.setDeleteRequestIfInAdditional(deleteRequest, request.getProcessInstanceId());

        sendRequestEntity(gson.toJson(deleteRequest), "/deleteProcess", HttpMethod.POST);
        BpmIsmsCommonService service = (BpmIsmsCommonService) applicationContext.getBean(Objects.requireNonNull(BpmIsmsServiceBeanNameEnum.getServiceBeanNameByKey(request.getKey())));

        //註銷流程後，需要把表單內的ProcessInstanceStatus改成3,來判斷此表單已註銷
        service.cancel(request.getProcessInstanceId());
    }

    @PostMapping("/getAllSubordinateTask")
    public List<Map<String, Object>> getAllSubordinateTask(@Valid @RequestBody(required = false) BpmFormQueryDto bpmFormQueryDto) {
        User userInfo = getUserInfo();
        String titleName = userInfo.getTitleName();
        if ("處長".equals(titleName) || "副處長".equals(titleName) || "主任".equals(titleName)) {
            List<String> allSubordinate = subordinateTaskService.findAllSubordinate(userInfo.getUserId());
            ResponseEntity<String> exchange = sendRequestEntity(gson.toJson(allSubordinate), "/getAllSubordinateTask", HttpMethod.POST);
            if (exchange.getStatusCodeValue() == 200) {
                String body = exchange.getBody();
                Type listType = new TypeToken<ArrayList<TaskDTO>>() {
                }.getType();
                List<TaskDTO> taskDTOS = gson.fromJson(body, listType);
                assert taskDTOS != null;
                return getMaps(bpmFormQueryDto, taskDTOS);
            }
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "權限不足");
    }


    @RequestMapping("/notify/queryTask")
    public List<Map<String, Object>> notifyQueryTask(@RequestBody BpmFormQueryDto bpmFormQueryDto) {
        User userInfo = getUserInfo();

        ResponseEntity<String> exchange = sendRequestEntity(userInfo.getUserId(), "/getAllTask", HttpMethod.POST);

        if (exchange.getStatusCodeValue() == 200) {
            String body = exchange.getBody();
            Type listType = new TypeToken<ArrayList<TaskDTO>>() {
            }.getType();
            List<TaskDTO> taskDTOS = new Gson().fromJson(body, listType);

            assert taskDTOS != null;
            return taskDTOS.isEmpty() ? null :
                taskDTOS.stream()
                    .map(taskDTO -> {
                        List<Map<String, Object>> mapList = bpmIsmsAdditionalService.findAllByProcessInstanceId(taskDTO.getProcessInstanceId(), bpmFormQueryDto);
                        if (!mapList.isEmpty()) {
                            Map<String, Object> map = new HashMap<>(new MapUtils().getNewMap(mapList.get(0)));
                            map.put(TASK_ID, taskDTO.getTaskId());
                            map.put("taskName", taskDTO.getTaskName());
                            map.put("decisionRole", SignerDecisionEnum.getDecisionByName(taskDTO.getTaskName()));
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

    /**
     * Retrieves the list of maps representing the task information based on the given query and task list.
     *
     * @param bpmFormQueryDto The query parameters used to filter the tasks.
     * @param taskDTOS        The list of tasks to retrieve the information from.
     * @return The list of maps representing the task information, or null if the task list is empty.
     */
    @Nullable
    private List<Map<String, Object>> getMaps(BpmFormQueryDto bpmFormQueryDto, List<TaskDTO> taskDTOS) {
        return taskDTOS.isEmpty() ? null :
            taskDTOS.stream()
                .map(taskDTO -> {
                    if (taskDTO.getFormName().equals("Additional")) {
                        BpmIsmsAdditional bpmIsmsAdditional = bpmIsmsAdditionalService.findByProcessInstanceId(taskDTO.getProcessInstanceId());
                        List<Map<String, Object>> mapList = bpmIsmsAdditionalService.findAllByProcessInstanceId(bpmIsmsAdditional.getMainProcessInstanceId(), bpmFormQueryDto);
                        if (!mapList.isEmpty()) {
                            Map<String, Object> map = new HashMap<>(new MapUtils().getNewMap(mapList.get(0)));
                            map.put(PROCESS_INSTANCE_ID, taskDTO.getProcessInstanceId());
                            map.put(TASK_ID, taskDTO.getTaskId());
                            map.put("taskName", "加簽-" + bpmIsmsAdditional.getTaskName());
                            map.put("decisionRole", SignerDecisionEnum.getDecisionByName(taskDTO.getTaskName()));
                            map.put("additional", true);
                            return map;
                        } else {
                            return null;
                        }
                    } else {

                        List<Map<String, Object>> mapList = bpmIsmsAdditionalService.findAllByProcessInstanceId(taskDTO.getProcessInstanceId(), bpmFormQueryDto);

                        if (!mapList.isEmpty()) {
                            Map<String, Object> map = new HashMap<>(new MapUtils().getNewMap(mapList.get(0)));
                            map.put(TASK_ID, taskDTO.getTaskId());
                            map.put("taskName", taskDTO.getTaskName());
                            map.put("decisionRole", SignerDecisionEnum.getDecisionByName(taskDTO.getTaskName()));
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

    /**
     * Retrieves the user information stored in the httpSession.
     *
     * @return the User object representing the user information stored in the httpSession or null if no user information is found
     */
    private User getUserInfo() {
        return (User) httpSession.getAttribute("userInfo");
    }

    /**
     * 驗證上傳檔案的大小與格式
     *
     * @param appendixFiles file list
     */
    private void validateAppendixFilesSize(List<MultipartFile> appendixFiles) {
        if (!Objects.equals(appendixFiles, null)) {
            for (MultipartFile file : appendixFiles) {
                CommonUtils.checkFile(file, 1024 * 1024L, fileTypeLimit);
            }
        }
    }


    /**
     * Delete processInstance when Bpm insert failed
     *
     * @param processInstanceId the id of the processInstance.
     */
    private void deleteProcessWhenSaveBpmFailed(String processInstanceId) {

        log.info("ProcessL414Resource.java - deleteProcessWhenSaveBpmFailed - 206 :: " + processInstanceId);
        HashMap<String, String> deleteRequest = new HashMap<>();
        deleteRequest.put(PROCESS_INSTANCE_ID, processInstanceId);
        deleteRequest.put("token", ParameterUtil.getToken());
        sendRequestEntity(gson.toJson(deleteRequest), "/deleteProcess", HttpMethod.POST);

    }

    /**
     * 建立HttpHeaders，及設定ContentType為application/json
     * 利用RestTemplate送出requestEntity，並取得responseEntity
     * @param json   要送出請求的json
     * @param path   要送出請求的路徑
     * @param method 要送出請求的方法
     * @return ResponseEntity<String> 回傳responseEntity
     */
    private ResponseEntity<String> sendRequestEntity(String json, String path, HttpMethod method) {
        HTTP_HEADERS.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(json, HTTP_HEADERS);
        return restTemplate.exchange(flowableProcessUrl + path, method, requestEntity, String.class);
    }

}
