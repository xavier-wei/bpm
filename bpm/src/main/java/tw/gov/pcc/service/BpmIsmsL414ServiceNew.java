package tw.gov.pcc.service;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import tw.gov.pcc.domain.BpmIsmsL414;
import tw.gov.pcc.domain.User;
import tw.gov.pcc.domain.UserRole;
import tw.gov.pcc.domain.entity.BpmSignStatus;
import tw.gov.pcc.repository.BpmIsmsL414Repository;
import tw.gov.pcc.repository.UserRoleRepository;
import tw.gov.pcc.service.dto.BpmIsmsL414DTO;
import tw.gov.pcc.service.dto.BpmUploadFileDTO;
import tw.gov.pcc.service.dto.EndEventDTO;
import tw.gov.pcc.service.dto.TaskDTO;
import tw.gov.pcc.service.mapper.BpmIsmsL414Mapper;
import tw.gov.pcc.utils.SeqNumber;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service("L414Service")
public class BpmIsmsL414ServiceNew implements BpmIsmsService {
    private static final String REVIEWER = "BPM_CR_Reviewer";
    private static final String[] ROLE_IDS = {"BPM_IPT_Operator", "BPM_IPT_Mgr", "BPM_CR_Operator", REVIEWER, "BPM_CR_Mgr"};

    private final Logger log = LoggerFactory.getLogger(BpmIsmsL414ServiceNew.class);
    private static final HashMap<UUID, BpmIsmsL414DTO> DTO_HOLDER = new HashMap<>();
    private static final HashMap<UUID, Map<String, Object>> VARIABLES_HOLDER = new HashMap<>();
    private final SupervisorService supervisorService;
    private final BpmIsmsL414Repository bpmIsmsL414Repository;
    private final BpmIsmsL414Mapper bpmIsmsL414Mapper;
    private final BpmUploadFileService bpmUploadFileService;
    private final BpmSignStatusService bpmSignStatusService;
    private final UserRoleRepository userRoleRepository;
    private final BpmSignerListService bpmSignerListService;
    private final Gson gson = new Gson();

    public BpmIsmsL414ServiceNew(SupervisorService supervisorService, BpmIsmsL414Repository bpmIsmsL414Repository, BpmIsmsL414Mapper bpmIsmsL414Mapper, BpmUploadFileService bpmUploadFileService, BpmSignStatusService bpmSignStatusService, UserRoleRepository userRoleRepository, BpmSignerListService bpmSignerListService) {
        this.supervisorService = supervisorService;
        this.bpmIsmsL414Repository = bpmIsmsL414Repository;
        this.bpmIsmsL414Mapper = bpmIsmsL414Mapper;
        this.bpmUploadFileService = bpmUploadFileService;
        this.bpmSignStatusService = bpmSignStatusService;
        this.userRoleRepository = userRoleRepository;
        this.bpmSignerListService = bpmSignerListService;
    }

    @Override
    @Transactional(rollbackFor = SQLException.class)
    public synchronized void saveBpm(UUID uuid, String processInstanceId, TaskDTO taskDTO, List<BpmUploadFileDTO> dto, List<MultipartFile> appendixFiles) throws ResponseStatusException {
        BpmIsmsL414DTO bpmIsmsL414DTO = DTO_HOLDER.get(uuid);
        List<BpmIsmsL414> maxFormId = bpmIsmsL414Repository.getMaxFormId();
        //取得表單最後的流水號
        String lastFormId = !maxFormId.isEmpty() ? maxFormId.get(0).getFormId() : null;
        String formId = bpmIsmsL414DTO.getFormName() + "-" + new SeqNumber().getNewSeq(lastFormId);
        bpmIsmsL414DTO.setFormId(formId);
        bpmIsmsL414DTO.setProcessInstanceId(processInstanceId);
        bpmIsmsL414DTO.setProcessInstanceStatus("0");
        bpmIsmsL414DTO.setUpdateTime(Timestamp.valueOf(LocalDateTime.now()));
        bpmIsmsL414DTO.setUpdateUser(bpmIsmsL414DTO.getFilName());
        bpmIsmsL414DTO.setCreateTime(Timestamp.valueOf(LocalDateTime.now()));
        bpmIsmsL414DTO.setCreateUser(bpmIsmsL414DTO.getFilName());

        log.debug("Request to save EipBpmIsmsL414 : {}", bpmIsmsL414DTO);

        // 儲存表單
        BpmIsmsL414 bpmIsmsL414 = bpmIsmsL414Mapper.toEntity(bpmIsmsL414DTO);

        bpmSignerListService.saveBpmSignerList(VARIABLES_HOLDER.get(uuid), formId);

        bpmIsmsL414Repository.save(bpmIsmsL414);

        //儲存照片
        bpmUploadFileService.savePhoto(dto, appendixFiles, formId);

        // 如果申請者選擇直接送出則跑下面這段完成申請者確認
        if ("1".equals(bpmIsmsL414DTO.getIsSubmit())) {
            bpmSignStatusService.saveBpmSignStatus(
                formId,
                processInstanceId,
                taskDTO,
                bpmIsmsL414DTO.getAppEmpid(),
                bpmIsmsL414DTO.getAppName(),
                bpmIsmsL414DTO.getAppUnit()
            );
        }

        DTO_HOLDER.remove(uuid);
        VARIABLES_HOLDER.remove(uuid);
    }

    public void saveBpmByPatch(String form) {
        BpmIsmsL414DTO bpmIsmsL414DTO = gson.fromJson(form, BpmIsmsL414DTO.class);
        bpmIsmsL414DTO.setUpdateTime(Timestamp.valueOf(LocalDateTime.now()));
        bpmIsmsL414Repository.save(bpmIsmsL414Mapper.toEntity(bpmIsmsL414DTO));
    }

    @Override
    public String saveBpmByPatch(String form, List<BpmUploadFileDTO> dto, List<MultipartFile> appendixFiles) {

        BpmIsmsL414DTO bpmIsmsL414DTO = gson.fromJson(form, BpmIsmsL414DTO.class);
        bpmIsmsL414DTO.setUpdateTime(Timestamp.valueOf(LocalDateTime.now()));
        bpmIsmsL414DTO.setUpdateUser(bpmIsmsL414DTO.getFilName());
        String formId = bpmIsmsL414DTO.getFormId();

        //儲存照片
        bpmUploadFileService.savePhoto(dto, appendixFiles, formId);

        return gson.toJson(bpmIsmsL414Mapper.toDto(bpmIsmsL414Repository.save(bpmIsmsL414Mapper.toEntity(bpmIsmsL414DTO))));
    }

    @Override
    public HashMap<String, Object> saveBpmByPatch(HashMap<String, Object> variables, String form, List<BpmUploadFileDTO> dto, List<MultipartFile> appendixFiles, User userInfo) {
        return new HashMap<>();
    }


    @Override
    public UUID setVariables(HashMap<String, Object> variables, String form, User userInfo) {
        BpmIsmsL414DTO bpmIsmsL414DTO = gson.fromJson(form, BpmIsmsL414DTO.class);
        UUID uuid = UUID.randomUUID();
        DTO_HOLDER.put(uuid, bpmIsmsL414DTO);
        variables.put("applier", bpmIsmsL414DTO.getAppEmpid());
        variables.put("isSubmit", bpmIsmsL414DTO.getIsSubmit());

        // 填入上級

        List<UserRole> userRoles = userRoleRepository.findByRoleIdIn(List.of(ROLE_IDS));
        supervisorService.setSupervisor(variables, bpmIsmsL414DTO.getAppEmpid(), userInfo);

        HashMap<String, String> signerIds = new HashMap<>();
        Arrays.stream(ROLE_IDS).forEach(s -> {
            List<String> userIds = userRoles.stream().filter(userRole -> userRole.getRoleId().equals(s)).map(UserRole::getUserId).collect(Collectors.toList());

            signerIds.put(s, String.join(",", userIds));
        });

        variables.put("infoGroup", signerIds.get("BPM_IPT_Operator"));
        variables.put("seniorTechSpecialist", signerIds.get("BPM_IPT_Mgr"));
        variables.put("serverRoomOperator", signerIds.get("BPM_CR_Operator"));
        variables.put("reviewStaff", signerIds.get(REVIEWER));
        variables.put("serverRoomManager", signerIds.get("BPM_CR_Mgr"));

        VARIABLES_HOLDER.put(uuid, variables);
        return uuid;
    }

    @Override
    public Map<String, Object> getBpm(String formId) {

        List<Map<String, Object>> bpmIsmsL414 = bpmIsmsL414Repository.findByFormId(formId);

        if (!bpmIsmsL414.isEmpty()) return bpmIsmsL414Repository.findByFormId(formId).get(0);

        return Map.of();
    }


    @Override
    @Transactional(rollbackFor = SQLException.class)
    public void endForm(EndEventDTO endEventDTO) {
        BpmIsmsL414 bpmIsmsL414 = bpmIsmsL414Repository.findFirstByProcessInstanceId(endEventDTO.getProcessInstanceId());
        bpmIsmsL414.setProcessInstanceStatus(endEventDTO.getProcessStatus());
        bpmIsmsL414.setUpdateTime(Timestamp.valueOf(LocalDateTime.now()));
        bpmIsmsL414Repository.save(bpmIsmsL414);
    }

    @Override
    public void saveBpmByPatchToIsSubmit(String processInstanceId) {
        BpmIsmsL414 bpmIsmsL414 = bpmIsmsL414Repository.findFirstByProcessInstanceId(processInstanceId);
        bpmIsmsL414.setIsSubmit("0");
        bpmIsmsL414.setProcessInstanceStatus("2");
        bpmIsmsL414.setUpdateTime(Timestamp.valueOf(LocalDateTime.now()));
        bpmIsmsL414Repository.save(bpmIsmsL414);
    }

    @Override
    public void cancel(String processInstanceId) {
        BpmIsmsL414 bpmIsmsL414 = bpmIsmsL414Repository.findFirstByProcessInstanceId(processInstanceId);
        bpmIsmsL414.setProcessInstanceStatus("3");
        bpmIsmsL414.setUpdateTime(Timestamp.valueOf(LocalDateTime.now()));
        bpmIsmsL414Repository.save(bpmIsmsL414);
        saveSignStatus(bpmIsmsL414);
    }

    @Override
    public void saveAppendixFiles(List<MultipartFile> appendixFiles, List<BpmUploadFileDTO> dto, String formId) {
        //儲存照片
        bpmUploadFileService.savePhoto(dto, appendixFiles, formId);
    }

    @Override
    public void removeHolder(UUID uuid) {
        DTO_HOLDER.remove(uuid);
        VARIABLES_HOLDER.remove(uuid);
    }

    private void saveSignStatus(BpmIsmsL414 bpmIsmsL414) {
        BpmSignStatus bpmSignStatus = new BpmSignStatus();
        bpmSignStatus.setFormId(bpmIsmsL414.getFormId());
        bpmSignStatus.setTaskId("cancel process");
        bpmSignStatus.setTaskName("cancel process");
        bpmSignStatus.setProcessInstanceId(bpmIsmsL414.getProcessInstanceId());
        bpmSignStatus.setSignerId(bpmIsmsL414.getAppEmpid());
        bpmSignStatus.setSignUnit(bpmIsmsL414.getAppUnit());
        bpmSignStatus.setSigner(bpmIsmsL414.getAppName());
        bpmSignStatusService.saveBpmSignStatus(bpmSignStatus, bpmIsmsL414.getFormId());
    }


}
