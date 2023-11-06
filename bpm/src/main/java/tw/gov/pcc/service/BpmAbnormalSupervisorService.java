package tw.gov.pcc.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tw.gov.pcc.domain.BpmAbnormalSupervisor;
import tw.gov.pcc.repository.BpmAbnormalSupervisorRepository;

import java.util.List;
import java.util.Optional;

@Service
public class BpmAbnormalSupervisorService {

    private final BpmAbnormalSupervisorRepository bpmAbnormalSupervisorRepository;

    public BpmAbnormalSupervisorService(BpmAbnormalSupervisorRepository bpmAbnormalSupervisorRepository) {
        this.bpmAbnormalSupervisorRepository = bpmAbnormalSupervisorRepository;
    }

    public Optional<BpmAbnormalSupervisor> findById(String id) {

        return bpmAbnormalSupervisorRepository.findById(id);
    }

    @Transactional
    public void save(BpmAbnormalSupervisor bpmAbnormalSupervisor) {
        bpmAbnormalSupervisorRepository.save(bpmAbnormalSupervisor);
    }
    @Transactional
    public void saveAll(List<BpmAbnormalSupervisor> bpmAbnormalSupervisors) {
        bpmAbnormalSupervisorRepository.saveAll(bpmAbnormalSupervisors);

    }

    public void delete(String id) {
        bpmAbnormalSupervisorRepository.deleteById(id);
    }
}
