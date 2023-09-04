package tw.gov.pcc.service;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import tw.gov.pcc.repository.BpmIsmsAdditionalRepository;
import tw.gov.pcc.service.dto.BpmIsmsAdditionalDTO;
import tw.gov.pcc.service.dto.BpmUploadFileDTO;
import tw.gov.pcc.service.dto.TaskDTO;
import tw.gov.pcc.service.mapper.BpmIsmsAdditionalMapper;
import tw.gov.pcc.service.mapper.BpmSignStatusMapper;
import tw.gov.pcc.utils.SeqNumber;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Service("AdditionalService")
public class BpmIsmsAdditionalService implements BpmIsmsService{

    private final Logger log = LoggerFactory.getLogger(BpmIsmsAdditionalService.class);
    public static final HashMap<UUID, BpmIsmsAdditionalDTO> DTO_HOLDER = new HashMap<>();

    private final BpmIsmsAdditionalMapper bpmIsmsAdditionalMapper;
    private final BpmSignStatusMapper bpmSignStatusMapper;
    private final BpmSignStatusService bpmSignStatusService;
    private final BpmIsmsAdditionalRepository bpmIsmsAdditionalRepository;
    private final Gson gson = new Gson();

    public BpmIsmsAdditionalService(BpmIsmsAdditionalMapper bpmIsmsAdditionalMapper, BpmSignStatusMapper bpmSignStatusMapper, BpmSignStatusService bpmSignStatusService, BpmIsmsAdditionalRepository bpmIsmsAdditionalRepository) {
        this.bpmIsmsAdditionalMapper = bpmIsmsAdditionalMapper;
        this.bpmSignStatusMapper = bpmSignStatusMapper;
        this.bpmSignStatusService = bpmSignStatusService;
        this.bpmIsmsAdditionalRepository = bpmIsmsAdditionalRepository;
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

        DTO_HOLDER.remove(uuid);
    }

    @Override
    public void saveBpmByPatch(String form) {

    }

    @Override
    public String saveBpmByPatch(String form, List<BpmUploadFileDTO> dto, List<MultipartFile> appendixFiles) {
        return null;
    }

    @Override
    public UUID setVariables(HashMap<String, Object> variables, String form) {
        BpmIsmsAdditionalDTO bpmIsmsAdditionalDTO = gson.fromJson(form, BpmIsmsAdditionalDTO.class);
        UUID uuid = UUID.randomUUID();
        DTO_HOLDER.put(uuid, bpmIsmsAdditionalDTO);
//        DirectorTester
        variables.put("additionalSigner", bpmIsmsAdditionalDTO.getAdditionalSigner());
//        variables.put("additionalSigner", "DirectorTester");
        variables.put("mainProcessInstanceId", bpmIsmsAdditionalDTO.getMainProcessInstanceId());

        return uuid;
    }
}
