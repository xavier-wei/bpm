package tw.gov.pcc.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tw.gov.pcc.domain.BpmAbnormalSupervisor;
import tw.gov.pcc.repository.BpmAbnormalSupervisorRepository;
import tw.gov.pcc.service.dto.BpmAbnormalSupervisorDTO;
import tw.gov.pcc.service.mapper.BpmAbnormalSupervisorMapper;

import java.util.List;
import java.util.Optional;

@Service
public class BpmAbnormalSupervisorService {

    private final BpmAbnormalSupervisorRepository bpmAbnormalSupervisorRepository;
    private final BpmAbnormalSupervisorMapper bpmAbnormalSupervisorMapper;
    public BpmAbnormalSupervisorService(BpmAbnormalSupervisorRepository bpmAbnormalSupervisorRepository, BpmAbnormalSupervisorMapper bpmAbnormalSupervisorMapper) {
        this.bpmAbnormalSupervisorRepository = bpmAbnormalSupervisorRepository;
        this.bpmAbnormalSupervisorMapper = bpmAbnormalSupervisorMapper;
    }

    public Optional<BpmAbnormalSupervisor> findById(String id) {

        return bpmAbnormalSupervisorRepository.findById(id);
    }

    @Transactional
    public void save(BpmAbnormalSupervisorDTO bpmAbnormalSupervisorDTO) {
        bpmAbnormalSupervisorRepository.save(bpmAbnormalSupervisorMapper.toEntity(bpmAbnormalSupervisorDTO));
    }
    @Transactional
    public void saveAll(List<BpmAbnormalSupervisorDTO> bpmAbnormalSupervisorDTOS) {
        bpmAbnormalSupervisorRepository.saveAll(bpmAbnormalSupervisorMapper.toEntity(bpmAbnormalSupervisorDTOS));

    }

    public void delete(String id) {
        bpmAbnormalSupervisorRepository.deleteById(id);
    }
}
