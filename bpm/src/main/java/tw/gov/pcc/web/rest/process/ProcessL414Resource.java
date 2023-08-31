package tw.gov.pcc.web.rest.process;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import tw.gov.pcc.domain.BpmIsmsL414;
import tw.gov.pcc.domain.SingerEnum;
import tw.gov.pcc.domain.entity.BpmSignStatus;
import tw.gov.pcc.repository.BpmIsmsL414Repository;
import tw.gov.pcc.service.BpmIsmsL414Service;
import tw.gov.pcc.service.BpmSignStatusService;
import tw.gov.pcc.service.BpmUploadFileService;
import tw.gov.pcc.service.dto.*;
import tw.gov.pcc.service.mapper.BpmIsmsL414Mapper;
import tw.gov.pcc.service.mapper.BpmSignStatusMapper;
import tw.gov.pcc.service.mapper.BpmUploadFileMapper;
import tw.gov.pcc.utils.SeqNumber;

import javax.validation.Valid;
import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/process")
public class ProcessL414Resource {

    private static final Logger log = LoggerFactory.getLogger(ProcessL414Resource.class);

    @Autowired
    private BpmIsmsL414Service bpmIsmsL414Service;

    @Autowired
    private BpmIsmsL414Repository bpmIsmsL414Repository;

    @Autowired
    private BpmIsmsL414Mapper bpmIsmsL414Mapper;
    // 測試中若flowable沒在同一個container啟動，記得修改下方port
    // todo: 上線後之後記得要改成自動抓取domain的方式

    @Autowired
    private BpmUploadFileService bpmUploadFileService;

    @Autowired
    private BpmUploadFileMapper bpmUploadFileMapper;

    @Autowired
    private BpmSignStatusMapper bpmSignStatusMapper;

    @Autowired
    private BpmSignStatusService bpmSignStatusService;

    @Value("${bpm.token}")
    private String TOKEN;
    private final Gson gson = new Gson();
    // todo
    private final String FLOWABLE_PROCESS_URL = "http://localhost:8081/process";
    private final RestTemplate restTemplate = new RestTemplate();

    @PostMapping(path = "/startL414/{key}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public String start(
        @Valid @RequestPart("form") HashMap<String,String> form,
        @PathVariable String key,
        @Valid @RequestPart(name = "fileDto", required = false) List<BpmUploadFileDTO> dto,
        @RequestPart(name = "appendixFiles", required = false) List<MultipartFile> appendixFiles) throws IOException {
        log.info("ProcessL414Resource.java - start - 59 :: " + form);
        log.info("ProcessL414Resource.java - start - 60 :: " + dto);
        log.info("ProcessL414Resource.java - start - 61 :: " + appendixFiles);


        BpmIsmsL414DTO bpmIsmsL414DTO = gson.fromJson(form.get(key), BpmIsmsL414DTO.class);
        ProcessReqDTO processReqDTO = new ProcessReqDTO();
        processReqDTO.setFormName(key);
        HashMap<String, Object> variables = new HashMap<>();

        variables.put("applier", bpmIsmsL414DTO.getAppName());
        variables.put("isSubmit", bpmIsmsL414DTO.getIsSubmit());
        variables.put("sectionChief", "ChiefTester");
        variables.put("director", "DirectorTester");
        variables.put("infoGroup", "InfoTester");
        variables.put("seniorTechSpecialist", "seniorTechSpecialistTester");
        variables.put("serverRoomOperator", "serverRoomOperatorTester");
        variables.put("reviewStaff", "reviewStaffTester");
        variables.put("serverRoomManager", "serverRoomManagerTester");
        processReqDTO.setVariables(variables);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(gson.toJson(processReqDTO), headers);


        // 到flowable啟動流程
        ResponseEntity<String> exchange = restTemplate.exchange(FLOWABLE_PROCESS_URL + "/startProcess", HttpMethod.POST, requestEntity, String.class);

        String processInstanceId;
        TaskDTO taskDTO;
        if (exchange.getStatusCodeValue() == 200) {
            taskDTO = gson.fromJson(exchange.getBody(), TaskDTO.class);
            processInstanceId = taskDTO.getProcessInstanceId();
        } else {
            return "流程引擎忙碌中，請稍候再試";
        }



        //取得表單最後的流水號
        String lastFormId = !bpmIsmsL414Repository.getMaxFormId().isEmpty() ? bpmIsmsL414Repository.getMaxFormId().get(0).getFormId() : null;
        String formId = bpmIsmsL414DTO.getFormName() + "-" + new SeqNumber().getNewSeq(lastFormId);
        bpmIsmsL414DTO.setFormId(formId);

        //存入table
        bpmIsmsL414DTO.setProcessInstanceId(processInstanceId);
        bpmIsmsL414DTO.setProcessInstanceStatus("0");
        bpmIsmsL414DTO.setUpdateTime(Timestamp.valueOf(LocalDateTime.now()));
        bpmIsmsL414DTO.setUpdateUser(bpmIsmsL414DTO.getFilName());
        bpmIsmsL414DTO.setCreateTime(Timestamp.valueOf(LocalDateTime.now()));
        bpmIsmsL414DTO.setCreateUser(bpmIsmsL414DTO.getFilName());
        bpmIsmsL414Service.save(bpmIsmsL414DTO);

        //儲存照片
        if (appendixFiles != null) {
            for (int i = 0; i < appendixFiles.size(); i++) {
                dto.get(i).setFormId(bpmIsmsL414DTO.getFormName() + "-" + new SeqNumber().getNewSeq(lastFormId));
                dto.get(i).setFileName(appendixFiles.get(i).getName());
                bpmUploadFileService.bpmUploadFile(bpmUploadFileMapper.toEntity(dto.get(i)), appendixFiles.get(i));
            }
        }

        if ("1".equals(bpmIsmsL414DTO.getIsSubmit())) {
            saveApplierSignStatus(bpmIsmsL414DTO, formId, processInstanceId, taskDTO);
        }


        return processInstanceId;
    }

    private void saveApplierSignStatus(BpmIsmsL414DTO bpmIsmsL414DTO, String formId, String processInstanceId, TaskDTO taskDTO) {
        BpmSignStatusDTO bpmSignStatusDTO = new BpmSignStatusDTO();
        bpmSignStatusDTO.setFormId(formId);
        bpmSignStatusDTO.setProcessInstanceId(processInstanceId);
        bpmSignStatusDTO.setTaskId(taskDTO.getTaskId());
        bpmSignStatusDTO.setTaskName(taskDTO.getTaskName());
        bpmSignStatusDTO.setSignerId(bpmIsmsL414DTO.getAppEmpid());
        bpmSignStatusDTO.setSigner(bpmIsmsL414DTO.getAppName());
        bpmSignStatusDTO.setSignUnit(bpmIsmsL414DTO.getAppUnit());
        bpmSignStatusDTO.setSignResult("1");
        bpmSignStatusDTO.setSigningDatetime(Timestamp.from(Instant.now()));
        BpmSignStatus bpmSignStatus = bpmSignStatusMapper.toEntity(bpmSignStatusDTO);

        bpmSignStatusService.saveBpmSignStatus(bpmSignStatus);
    }


    @PatchMapping(path = "/startL414/patch", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public BpmIsmsL414DTO startPatch(
        @Valid @RequestPart("form") BpmIsmsL414DTO bpmIsmsL414DTO,
        @Valid @RequestPart(name = "fileDto", required = false) List<BpmUploadFileDTO> dto,
        @RequestPart(name = "appendixFiles", required = false) List<MultipartFile> appendixFiles) throws IOException {
        log.info("ProcessL414Resource.java - start - 59 :: " + bpmIsmsL414DTO);
        log.info("ProcessL414Resource.java - start - 60 :: " + dto);
        log.info("ProcessL414Resource.java - start - 61 :: " + appendixFiles);

        //存入table
        bpmIsmsL414DTO.setUpdateTime(Timestamp.valueOf(LocalDateTime.now()));
        bpmIsmsL414DTO.setUpdateUser(bpmIsmsL414DTO.getFilName());
        BpmIsmsL414DTO newBpmIsmsL414DTO = bpmIsmsL414Service.save(bpmIsmsL414DTO);

        //儲存照片
        if (appendixFiles != null) {
            for (int i = 0; i < appendixFiles.size(); i++) {
                dto.get(i).setFormId(bpmIsmsL414DTO.getFormId());
                dto.get(i).setFileName(appendixFiles.get(i).getName());
                bpmUploadFileService.bpmUploadFile(bpmUploadFileMapper.toEntity(dto.get(i)), appendixFiles.get(i));
            }
        }

        return newBpmIsmsL414DTO;
    }


    @RequestMapping("/queryTask/{id}")
    public List<BpmIsmsL414DTO> queryTask(@PathVariable String id, @Valid @RequestPart(required = false) BpmFormQueryDto bpmFormQueryDto) {
        log.info("ProcessL414Resource.java - queryTask - 193 :: " + id);
        log.info("ProcessL414Resource.java - queryTask - 194 :: " + bpmFormQueryDto);

//        String id="ApplyTester";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(id, headers);
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
//                        BpmIsmsL414DTO dto = bpmIsmsL414Mapper.toDto(bpmIsmsL414Repository.findFirstByProcessInstanceId(taskDTO.getProcessInstanceId()));
                        BpmIsmsL414DTO dto = bpmIsmsL414Repository.findByBpmIsmsL414(bpmFormQueryDto, taskDTO.getProcessInstanceId());
                        if (dto != null) {
                            dto.setTaskId(taskDTO.getTaskId());
                            dto.setTaskName(taskDTO.getTaskName());
                            dto.setDecisionRole(SingerEnum.getDecisionByName(taskDTO.getTaskName()));
                        }
                        return dto;
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        }

        return null;
    }


    @RequestMapping("/completeTask")
    public String completeTask(@RequestBody CompleteReqDTO completeReqDTO) {
        log.info("ProcessL414Resource.java - completeTask - 183 :: " + completeReqDTO);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(gson.toJson(completeReqDTO), headers);
        ResponseEntity<String> exchange = restTemplate.exchange(FLOWABLE_PROCESS_URL + "/completeTask", HttpMethod.POST, requestEntity, String.class);
        if (exchange.getStatusCodeValue() == 200) {
            BpmSignStatusDTO bpmSignStatusDTO = getBpmSignStatusDTO(completeReqDTO);
            BpmSignStatus bpmSignStatus = bpmSignStatusMapper.toEntity(bpmSignStatusDTO);
            bpmSignStatusService.saveBpmSignStatus(bpmSignStatus);
            if (Objects.equals(completeReqDTO.getIpt(), true)) {
                bpmIsmsL414Service.save(completeReqDTO.getBpmIsmsL414DTO());
            }
            return exchange.getBody();
        }
        return "伺服器忙碌中或查無任務，請稍候再試";
    }




    private static BpmSignStatusDTO getBpmSignStatusDTO(CompleteReqDTO completeReqDTO) {
        BpmSignStatusDTO bpmSignStatusDTO = new BpmSignStatusDTO();
        bpmSignStatusDTO.setFormId(completeReqDTO.getBpmIsmsL414DTO().getFormId());
        bpmSignStatusDTO.setProcessInstanceId(completeReqDTO.getProcessInstanceId());
        bpmSignStatusDTO.setTaskId(completeReqDTO.getTaskId());
        bpmSignStatusDTO.setTaskName(completeReqDTO.getTaskName());
        bpmSignStatusDTO.setSignerId(completeReqDTO.getSignerId());
        bpmSignStatusDTO.setSigner(completeReqDTO.getSigner());
        bpmSignStatusDTO.setSignUnit(completeReqDTO.getSignUnit());
        bpmSignStatusDTO.setSigningDatetime(Timestamp.from(Instant.now()));
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

    @PostMapping("/receiveEndEvent")
    public void receiveEndEvent(@RequestBody EndEventDTO endEventDTO) {
        log.info("ProcessL414Resource.java - receiveEndEvent - 196 :: " + endEventDTO.getProcessInstanceId());
        if (TOKEN.equals(endEventDTO.getToken())) {
            BpmIsmsL414 bpmIsmsL414 = bpmIsmsL414Repository.findFirstByProcessInstanceId(endEventDTO.getProcessInstanceId());
            bpmIsmsL414.setProcessInstanceStatus(endEventDTO.getProcessStatus());
            bpmIsmsL414.setUpdateTime(Timestamp.valueOf(LocalDateTime.now()));
            bpmIsmsL414Repository.save(bpmIsmsL414);
            return;
        }
        log.warn("ProcessL414Resource.java - receiveEndEvent - 203 ::{} ", "流程發生意外終止");
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

}
