package tw.gov.pcc.service;

import com.google.gson.Gson;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import tw.gov.pcc.domain.MailInfo;
import tw.gov.pcc.domain.User;
import tw.gov.pcc.domain.entity.BpmIsmsAdditional;
import tw.gov.pcc.repository.BpmIsmsAdditionalRepository;
import tw.gov.pcc.service.dto.*;
import tw.gov.pcc.service.mapper.BpmIsmsAdditionalMapper;
import tw.gov.pcc.utils.SeqNumber;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service("additionalService")
public class BpmIsmsAdditionalService implements BpmIsmsCommonService {

    private static final Map<UUID, BpmIsmsAdditionalDTO> DTO_HOLDER = new HashMap<>();

    private final BpmIsmsAdditionalMapper bpmIsmsAdditionalMapper;
    private final BpmSignStatusService bpmSignStatusService;
    private final BpmIsmsAdditionalRepository bpmIsmsAdditionalRepository;


    private final Gson gson = new Gson();


    public BpmIsmsAdditionalService(BpmIsmsAdditionalMapper bpmIsmsAdditionalMapper, BpmSignStatusService bpmSignStatusService, BpmIsmsAdditionalRepository bpmIsmsAdditionalRepository) {
        this.bpmIsmsAdditionalMapper = bpmIsmsAdditionalMapper;
        this.bpmSignStatusService = bpmSignStatusService;
        this.bpmIsmsAdditionalRepository = bpmIsmsAdditionalRepository;
    }

    @Override
    public UUID setVariables(HashMap<String, Object> variables, String form, User userInfo) {
        BpmIsmsAdditionalDTO bpmIsmsAdditionalDTO = gson.fromJson(form, BpmIsmsAdditionalDTO.class);
        UUID uuid = UUID.randomUUID();
        DTO_HOLDER.put(uuid, bpmIsmsAdditionalDTO);
        variables.put("additionalSigner", bpmIsmsAdditionalDTO.getAdditionalSignerId());
        variables.put("mainProcessInstanceId", bpmIsmsAdditionalDTO.getMainProcessInstanceId());
        variables.put("mainProcessTaskId", bpmIsmsAdditionalDTO.getMainProcessTaskId());
        return uuid;
    }

    @Override
    @Transactional(rollbackFor = SQLException.class)
    public void saveBpm(UUID uuid, String processInstanceId, TaskDTO taskDTO, List<BpmUploadFileDTO> dto, List<MultipartFile> appendixFiles) {
        BpmIsmsAdditionalDTO bpmIsmsAdditionalDTO = DTO_HOLDER.get(uuid);

        String lastFormId = !bpmIsmsAdditionalRepository.getMaxFormId().isEmpty() ? bpmIsmsAdditionalRepository.getMaxFormId().get(0).getFormId() : null;
        String formId = taskDTO.getFormName() + "-" + new SeqNumber().getNewSeq(lastFormId);
        bpmIsmsAdditionalDTO.setProcessInstanceId(processInstanceId);
        bpmIsmsAdditionalDTO.setFormId(formId);
        bpmIsmsAdditionalDTO.setProcessInstanceStatus("0");
        bpmIsmsAdditionalDTO.setCreateTime(Timestamp.valueOf(LocalDateTime.now()));
        // 儲存表單
        bpmIsmsAdditionalRepository.save(bpmIsmsAdditionalMapper.toEntity(bpmIsmsAdditionalDTO));

        // 儲存簽核狀態
        bpmSignStatusService.saveBpmSignStatus(bpmIsmsAdditionalDTO);

        DTO_HOLDER.remove(uuid);
    }

    @Override
    public MailInfo endForm(EndEventDTO endEventDTO) {
        BpmIsmsAdditional bpmIsmsAdditional = bpmIsmsAdditionalRepository.findFirstByProcessInstanceId(endEventDTO.getProcessInstanceId());
        bpmIsmsAdditional.setProcessInstanceStatus(endEventDTO.getProcessStatus());
        bpmIsmsAdditionalRepository.save(bpmIsmsAdditional);
        return new MailInfo(null, null, null, null, null, false);
    }

    @Override
    public Map<String, Object> getBpm(String formId) {
        return Map.of();
    }

    @Override
    public void cancel(String formId) {
        // TODO document why this method is empty
    }

    @Override
    public void removeHolder(UUID uuid) {
        DTO_HOLDER.remove(uuid);
    }

    public List<Map<String, Object>> findAllByProcessInstanceId(String processInstanceId, @NotNull BpmFormQueryDto bpmFormQueryDto) {

        return bpmIsmsAdditionalRepository.findAllByProcessInstanceId(
            processInstanceId,
            bpmFormQueryDto.getFormId(),
            bpmFormQueryDto.getProcessInstanceStatus(),
            bpmFormQueryDto.getUnit(),
            bpmFormQueryDto.getAppName(),
            bpmFormQueryDto.getDateStart(),
            bpmFormQueryDto.getDateEnd()
        );
    }

    public void setDeleteRequestIfInAdditional(Map<String, String> deleteRequest, String mainProcessInstanceId) {
        bpmIsmsAdditionalRepository
            .findFirstByMainProcessInstanceId(mainProcessInstanceId)
            .ifPresent(bpmIsmsAdditional ->
                deleteRequest.put("additionalProcessInstanceId", bpmIsmsAdditional.getProcessInstanceId())
            );
    }

    public BpmIsmsAdditional findByProcessInstanceId(String processInstanceId) {
        return bpmIsmsAdditionalRepository.findByProcessInstanceId(processInstanceId);
    }

}
