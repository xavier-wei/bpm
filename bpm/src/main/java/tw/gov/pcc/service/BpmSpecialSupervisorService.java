package tw.gov.pcc.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tw.gov.pcc.domain.BpmSpecialSupervisor;
import tw.gov.pcc.repository.BpmSpecialSupervisorRepository;
import tw.gov.pcc.service.dto.BpmSpecialSupervisorDTO;
import tw.gov.pcc.service.mapper.BpmSpecialSupervisorMapper;

import java.util.List;
import java.util.Optional;

@Service
public class BpmSpecialSupervisorService {

    private final BpmSpecialSupervisorRepository bpmSpecialSupervisorRepository;
    private final BpmSpecialSupervisorMapper bpmSpecialSupervisorMapper;
    public BpmSpecialSupervisorService(BpmSpecialSupervisorRepository bpmSpecialSupervisorRepository, BpmSpecialSupervisorMapper bpmSpecialSupervisorMapper) {
        this.bpmSpecialSupervisorRepository = bpmSpecialSupervisorRepository;
        this.bpmSpecialSupervisorMapper = bpmSpecialSupervisorMapper;
    }

    public Optional<BpmSpecialSupervisor> findById(String id) {

        return bpmSpecialSupervisorRepository.findById(id);
    }

    @Transactional
    public void save(BpmSpecialSupervisorDTO bpmSpecialSupervisorDTO) {
        bpmSpecialSupervisorRepository.save(bpmSpecialSupervisorMapper.toEntity(bpmSpecialSupervisorDTO));
    }
    @Transactional
    public void saveAll(List<BpmSpecialSupervisorDTO> bpmSpecialSupervisorDTOS) {
        bpmSpecialSupervisorRepository.saveAll(bpmSpecialSupervisorMapper.toEntity(bpmSpecialSupervisorDTOS));

    }

    public void delete(String id) {
        bpmSpecialSupervisorRepository.deleteById(id);
    }
}
