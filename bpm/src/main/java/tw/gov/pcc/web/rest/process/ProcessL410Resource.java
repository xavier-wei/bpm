package tw.gov.pcc.web.rest.process;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import tw.gov.pcc.domain.entity.BpmSignStatus;
import tw.gov.pcc.repository.BpmIsmsL410Repository;
import tw.gov.pcc.service.BpmIsmsL410Service;
import tw.gov.pcc.service.BpmSignStatusService;
import tw.gov.pcc.service.BpmUploadFileService;
import tw.gov.pcc.service.dto.*;
import tw.gov.pcc.service.mapper.BpmSignStatusMapper;
import tw.gov.pcc.service.mapper.BpmUploadFileMapper;
import tw.gov.pcc.utils.SeqNumber;

import javax.validation.Valid;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/process")
public class ProcessL410Resource {

    private static final Logger log = LoggerFactory.getLogger(ProcessL410Resource.class);

    @Value("${bpm.token}")
    private String TOKEN;
    private final Gson gson = new Gson();
    // todo
    private final String FLOWABLE_PROCESS_URL = "http://localhost:8081/process";
    private final RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private BpmIsmsL410Repository bpmIsmsL410Repository;

    @Autowired
    private BpmIsmsL410Service bpmIsmsL410Service;

    @Autowired
    private BpmUploadFileService bpmUploadFileService;

    @Autowired
    private BpmUploadFileMapper bpmUploadFileMapper;

    @Autowired
    private BpmSignStatusService bpmSignStatusService;

    @Autowired
    private BpmSignStatusMapper bpmSignStatusMapper;

    @PostMapping(path = "/start/{key}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public String startL410(
        @Valid @RequestPart("form") HashMap<String, HashMap<String, String>> form,
        @PathVariable String key,
        @Valid @RequestPart(name = "fileDto", required = false) List<BpmUploadFileDTO> dto,
        @RequestPart(name = "appendixFiles", required = false) List<MultipartFile> appendixFiles
    ) throws IOException {
        log.info("ProcessL410Resource.java - startL410 - 67 :: " + form);
        log.info("ProcessL410Resource.java - startL410 - 67 :: " + key);
        log.info("ProcessL414Resource.java - start - 60 :: " + dto);
        log.info("ProcessL414Resource.java - start - 61 :: " + appendixFiles);


        CompleteReql410DTO completeReql410DTO = gson.fromJson(String.valueOf(form.get(key)), CompleteReql410DTO.class);
        log.info("ProcessL410Resource.java - startL410 - 73 :: " + completeReql410DTO);

        //TODO:尚未起流程先存L410表
//        ProcessReqDTO processReqDTO = new ProcessReqDTO();
//        processReqDTO.setFormName("L410");
//        HashMap<String, Object> variables = new HashMap<>();
//
//        variables.put("applier", bpmIsmsL414DTO.getAppName());
//        variables.put("isSubmit", bpmIsmsL414DTO.getIsSubmit());
//        variables.put("sectionChief", "ChiefTester");
//        variables.put("director", "DirectorTester");
//        variables.put("infoGroup", "InfoTester");
//        variables.put("seniorTechSpecialist", "seniorTechSpecialistTester");
//        variables.put("serverRoomOperator", "serverRoomOperatorTester");
//        variables.put("reviewStaff", "reviewStaffTester");
//        variables.put("serverRoomManager", "serverRoomManagerTester");
//        processReqDTO.setVariables(variables);
//        String json = gson.toJson(processReqDTO);
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        HttpEntity<String> requestEntity = new HttpEntity<>(json, headers);
//
//
//        // 到flowable啟動流程
//        ResponseEntity<String> exchange = restTemplate.exchange(FLOWABLE_PROCESS_URL + "/startProcess", HttpMethod.POST, requestEntity, String.class);
//
        String processInstanceId = "12e2uir90-132[utr432tpjug-2341po234";
        TaskDTO taskDTO;
//        if (exchange.getStatusCodeValue() == 200) {
//            taskDTO = gson.fromJson(exchange.getBody(), TaskDTO.class);
//            processInstanceId = taskDTO.getProcessInstanceId();
//        } else {
//            return "流程引擎忙碌中，請稍候再試";
//        }


//        completeReql410DTO.getBpmIsmsL410DTO().setProcessInstanceId(processInstanceId);
//
        //取得表單最後的流水號
        String lastFormId = !bpmIsmsL410Repository.getMaxFormId().isEmpty() ? bpmIsmsL410Repository.getMaxFormId().get(0).getFormId() : null;
        String formId = completeReql410DTO.getBpmIsmsL410DTO().getFormName() + "-" + new SeqNumber().getNewSeq(lastFormId);

        completeReql410DTO.getBpmIsmsL410DTO().setFormId(formId);

//        存入table
        completeReql410DTO.getBpmIsmsL410DTO().setProcessInstanceId(processInstanceId);
        completeReql410DTO.getBpmIsmsL410DTO().setProcessInstanceStatus("0");
        completeReql410DTO.getBpmIsmsL410DTO().setUpdateTime(Timestamp.valueOf(LocalDateTime.now()));
        completeReql410DTO.getBpmIsmsL410DTO().setUpdateUser(completeReql410DTO.getBpmIsmsL410DTO().getFilName());
        completeReql410DTO.getBpmIsmsL410DTO().setCreateTime(Timestamp.valueOf(LocalDateTime.now()));
        completeReql410DTO.getBpmIsmsL410DTO().setCreateUser(completeReql410DTO.getBpmIsmsL410DTO().getFilName());
        bpmIsmsL410Service.save(completeReql410DTO.getBpmIsmsL410DTO());

        //儲存照片
        if (appendixFiles != null) {
            for (int i = 0; i < appendixFiles.size(); i++) {
                dto.get(i).setFormId(completeReql410DTO.getBpmIsmsL410DTO().getFormName() + "-" + new SeqNumber().getNewSeq(lastFormId));
                dto.get(i).setFileName(appendixFiles.get(i).getName());
                bpmUploadFileService.bpmUploadFile(bpmUploadFileMapper.toEntity(dto.get(i)), appendixFiles.get(i));
            }
        }

//        if ("1".equals(bpmIsmsL414DTO.getIsSubmit())) {
//            saveApplierSignStatus(bpmIsmsL414DTO, formId, processInstanceId, taskDTO);
//        }


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

}
