package tw.gov.pcc.service;

import org.springframework.stereotype.Service;
import tw.gov.pcc.domain.entity.BpmSignStatus;
import tw.gov.pcc.repository.BpmSignStatusRepository;
import tw.gov.pcc.service.dto.BpmSignStatusDTO;
import tw.gov.pcc.service.dto.TaskDTO;
import tw.gov.pcc.service.mapper.BpmSignStatusMapper;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Service
public class BpmSignStatusService {

   private BpmSignStatusRepository bpmSignStatusRepository;
    private BpmSignStatusMapper bpmSignStatusMapper;
    public BpmSignStatusService(BpmSignStatusRepository bpmSignStatusRepository, BpmSignStatusMapper bpmSignStatusMapper) {
        this.bpmSignStatusRepository = bpmSignStatusRepository;
        this.bpmSignStatusMapper = bpmSignStatusMapper;
    }

    public List<BpmSignStatus> updateSignStatus(String processInstanceId) {
        return bpmSignStatusRepository.findByProcessInstanceId(processInstanceId);
    }

    public void saveBpmSignStatus(BpmSignStatus bpmSignStatus) {


        bpmSignStatusRepository.save(bpmSignStatus);
    }

    public void saveBpmSignStatus(String formId, String processInstanceId, TaskDTO taskDTO,String signerId,String signer,String signUnit) {
        BpmSignStatusDTO bpmSignStatusDTO = new BpmSignStatusDTO();
        bpmSignStatusDTO.setFormId(formId);
        bpmSignStatusDTO.setProcessInstanceId(processInstanceId);
        bpmSignStatusDTO.setTaskId(taskDTO.getTaskId());
        bpmSignStatusDTO.setTaskName(taskDTO.getTaskName());
        bpmSignStatusDTO.setSignerId(signerId);
        bpmSignStatusDTO.setSigner(signer);
        bpmSignStatusDTO.setSignUnit(signUnit);
        bpmSignStatusDTO.setSignResult("1");
        bpmSignStatusDTO.setSigningDatetime(Timestamp.from(Instant.now()));
        BpmSignStatus bpmSignStatus = bpmSignStatusMapper.toEntity(bpmSignStatusDTO);
        bpmSignStatusRepository.save(bpmSignStatus);
    }

}
