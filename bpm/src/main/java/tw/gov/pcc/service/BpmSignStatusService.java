package tw.gov.pcc.service;

import org.springframework.stereotype.Service;
import tw.gov.pcc.domain.entity.BpmSignStatus;
import tw.gov.pcc.repository.BpmSignStatusRepository;

import java.util.List;

@Service
public class BpmSignStatusService {

   private BpmSignStatusRepository bpmSignStatusRepository;

    public BpmSignStatusService(BpmSignStatusRepository bpmSignStatusRepository) {
        this.bpmSignStatusRepository = bpmSignStatusRepository;
    }

    public List<BpmSignStatus> updateSignStatus(String processInstanceId) {
        return bpmSignStatusRepository.findByProcessInstanceId(processInstanceId);
    }

    public void saveBpmSignStatus(BpmSignStatus bpmSignStatus) {
        bpmSignStatusRepository.save(bpmSignStatus);
    }

}
