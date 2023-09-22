package tw.gov.pcc.service;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import tw.gov.pcc.domain.BpmIsmsL410;
import tw.gov.pcc.domain.User;
import tw.gov.pcc.repository.BpmIsmsL410Repository;
import tw.gov.pcc.service.dto.BpmIsmsL410DTO;
import tw.gov.pcc.service.dto.BpmUploadFileDTO;
import tw.gov.pcc.service.dto.EndEventDTO;
import tw.gov.pcc.service.dto.TaskDTO;
import tw.gov.pcc.service.mapper.BpmIsmsL410Mapper;
import tw.gov.pcc.service.mapper.BpmUploadFileMapper;
import tw.gov.pcc.utils.SeqNumber;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service("L410Service")
public class BpmIsmsL410ServiceNew implements BpmIsmsService {

    // BPM_IPT_Operator 資推小組承辦人、 BPM_IPT_Operator 簡任技正/科長 、 BPM_PR_Operator
    private final String[] ROLE_IDS = {"BPM_IPT_Operator", "BPM_IPT_Operator", "BPM_PR_Operator", "BPM_SEC_Operator"};

    private final Logger log = LoggerFactory.getLogger(BpmIsmsL410ServiceNew.class);
    public static final HashMap<UUID, BpmIsmsL410DTO> DTO_HOLDER = new HashMap<>();
    private static final HashMap<UUID,Map<String,Object>> VARIABLES_HOLDER = new HashMap<>();
    private final Gson gson = new Gson();

    private final BpmIsmsL410Repository bpmIsmsL410Repository;

    private final BpmUploadFileService bpmUploadFileService;

    private final BpmUploadFileMapper bpmUploadFileMapper;

    private final BpmSignStatusService bpmSignStatusService;

    private final BpmSignerListService bpmSignerListService;
    private final BpmIsmsL410Mapper bpmIsmsL410Mapper;
    private final SupervisorService supervisorService;

    public BpmIsmsL410ServiceNew(BpmIsmsL410Repository bpmIsmsL410Repository, BpmUploadFileService bpmUploadFileService, BpmUploadFileMapper bpmUploadFileMapper, BpmSignStatusService bpmSignStatusService, BpmSignerListService bpmSignerListService, BpmIsmsL410Mapper bpmIsmsL410Mapper, SupervisorService supervisorService) {
        this.bpmIsmsL410Repository = bpmIsmsL410Repository;
        this.bpmUploadFileService = bpmUploadFileService;
        this.bpmUploadFileMapper = bpmUploadFileMapper;
        this.bpmSignStatusService = bpmSignStatusService;
        this.bpmSignerListService = bpmSignerListService;
        this.bpmIsmsL410Mapper = bpmIsmsL410Mapper;
        this.supervisorService = supervisorService;
    }

    @Override
    @Transactional(rollbackFor = SQLException.class)
    public void saveBpm(UUID uuid, String processInstanceId, TaskDTO taskDTO, List<BpmUploadFileDTO> dto, List<MultipartFile> appendixFiles) {

        BpmIsmsL410DTO bpmIsmsL410DTO =  DTO_HOLDER.get(uuid);
        //取得表單最後的流水號
        String lastFormId = !bpmIsmsL410Repository.getMaxFormId().isEmpty() ? bpmIsmsL410Repository.getMaxFormId().get(0).getFormId() : null;
        String formId = bpmIsmsL410DTO.getFormName() + "-" + new SeqNumber().getNewSeq(lastFormId);

        bpmIsmsL410DTO.setFormId(formId);

//        存入table
        bpmIsmsL410DTO.setProcessInstanceId(processInstanceId);
        bpmIsmsL410DTO.setProcessInstanceStatus("0");
        bpmIsmsL410DTO.setUpdateTime(Timestamp.valueOf(LocalDateTime.now()));
        bpmIsmsL410DTO.setUpdateUser(bpmIsmsL410DTO.getFilName());
        bpmIsmsL410DTO.setCreateTime(Timestamp.valueOf(LocalDateTime.now()));
        bpmIsmsL410DTO.setCreateUser(bpmIsmsL410DTO.getFilName());
        log.info("BpmIsmsL410ServiceNew.java - saveBpm - 71 :: " + bpmIsmsL410DTO );
        BpmIsmsL410 bpmIsmsL410 = bpmIsmsL410Mapper.toEntity(bpmIsmsL410DTO);

        bpmIsmsL410Repository.save(bpmIsmsL410);

        //儲存照片
        if (appendixFiles != null) {
            for (int i = 0; i < appendixFiles.size(); i++) {
                dto.get(i).setFormId(bpmIsmsL410DTO.getFormName() + "-" + new SeqNumber().getNewSeq(lastFormId));
                dto.get(i).setFileName(appendixFiles.get(i).getName());
                try {
                    bpmUploadFileService.bpmUploadFile(bpmUploadFileMapper.toEntity(dto.get(i)), appendixFiles.get(i));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        // 如果申請者選擇直接送出則跑下面這段完成申請者確認
        if ("1".equals(bpmIsmsL410DTO.getIsSubmit())) {
            bpmSignStatusService.saveBpmSignStatus(
                formId,
                processInstanceId,
                taskDTO,
                bpmIsmsL410DTO.getAppEmpid(),
                bpmIsmsL410DTO.getAppName(),
                bpmIsmsL410DTO.getAppUnit1()
            );
        }
        bpmSignerListService.saveBpmSignerList(VARIABLES_HOLDER.get(uuid), formId);
        VARIABLES_HOLDER.remove(uuid);
        DTO_HOLDER.remove(uuid);
    }

    @Override
    public void saveBpmByPatch(String form) {
        BpmIsmsL410DTO bpmIsmsL410DTO = gson.fromJson(form, BpmIsmsL410DTO.class);
        bpmIsmsL410Repository.save(bpmIsmsL410Mapper.toEntity(bpmIsmsL410DTO));
    }

    @Override
    public String saveBpmByPatch(String form, List<BpmUploadFileDTO> dto, List<MultipartFile> appendixFiles) {
        BpmIsmsL410DTO bpmIsmsL410DTO = gson.fromJson(form, BpmIsmsL410DTO.class);
        bpmIsmsL410DTO.setUpdateTime(Timestamp.valueOf(LocalDateTime.now()));
        bpmIsmsL410DTO.setUpdateUser(bpmIsmsL410DTO.getFilName());
        String formId = bpmIsmsL410DTO.getFormId();

        //儲存照片
        bpmUploadFileService.savePhoto(dto, appendixFiles, formId);

        return gson.toJson(bpmIsmsL410Mapper.toDto(bpmIsmsL410Repository.save(bpmIsmsL410Mapper.toEntity(bpmIsmsL410DTO))));

    }

    @Override
    public UUID setVariables(HashMap<String, Object> variables, String form, User userInfo) {
        BpmIsmsL410DTO bpmIsmsL410DTO = gson.fromJson(form, BpmIsmsL410DTO.class);
        UUID uuid = UUID.randomUUID();
        DTO_HOLDER.put(uuid, bpmIsmsL410DTO);
        variables.put("applier", bpmIsmsL410DTO.getAppEmpid());
        variables.put("isSubmit", bpmIsmsL410DTO.getIsSubmit());
        // 填入上級
        supervisorService.setSupervisor(variables,bpmIsmsL410DTO.getAppEmpid(),userInfo);




        VARIABLES_HOLDER.put(uuid, variables);
        return uuid;
    }

    @Override
    public void endForm(EndEventDTO endEventDTO) {
        BpmIsmsL410 bpmIsmsL410 = bpmIsmsL410Repository.findFirstByProcessInstanceId(endEventDTO.getProcessInstanceId());
        bpmIsmsL410.setProcessInstanceStatus(endEventDTO.getProcessStatus());
        bpmIsmsL410.setUpdateTime(Timestamp.valueOf(LocalDateTime.now()));
        bpmIsmsL410Repository.save(bpmIsmsL410);
    }

    @Override
    public Map<String, Object> getBpm(String formId) {

        List<Map<String,Object>> bpmIsmsL410 =  bpmIsmsL410Repository.findByFormId(formId);

        if(!bpmIsmsL410.isEmpty()) return bpmIsmsL410Repository.findByFormId(formId).get(0);

        return null;
    }

    private void savePhoto(List<BpmUploadFileDTO> dto, List<MultipartFile> appendixFiles, String formId) {
        if (appendixFiles != null) {
            for (int i = 0; i < appendixFiles.size(); i++) {
                dto.get(i).setFormId(formId);
                dto.get(i).setFileName(appendixFiles.get(i).getName());
                try {
                    bpmUploadFileService.bpmUploadFile(bpmUploadFileMapper.toEntity(dto.get(i)), appendixFiles.get(i));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
