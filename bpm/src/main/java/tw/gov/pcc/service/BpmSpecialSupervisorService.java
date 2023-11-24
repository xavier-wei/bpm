package tw.gov.pcc.service;

import com.google.gson.Gson;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tw.gov.pcc.cache.BpmCache;
import tw.gov.pcc.domain.BpmSpecialSupervisor;
import tw.gov.pcc.domain.BpmSupervisor;
import tw.gov.pcc.domain.BpmSupervisorPrimayKey;
import tw.gov.pcc.repository.BpmSpecialSupervisorRepository;
import tw.gov.pcc.service.dto.BpmSpecialSupervisorDTO;
import tw.gov.pcc.service.mapper.BpmSpecialSupervisorMapper;

import java.util.List;
import java.util.Optional;

@Service
public class BpmSpecialSupervisorService {

    private final BpmSpecialSupervisorRepository bpmSpecialSupervisorRepository;
    private final BpmSpecialSupervisorMapper bpmSpecialSupervisorMapper;
    private final Gson gson = new Gson();
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

    /**
     * 刪除 表單特例管理
     * @param id 申請者員編
     */
    public void delete(String id) {
        bpmSpecialSupervisorRepository.deleteById(id);
    }

    /**
     *
     * @param pecard 申請者員編
     * @return List<BpmSpecialSupervisor> 表單特例管理
     */
    public List<BpmSpecialSupervisor> setSupervisor(String pecard) {
        return bpmSpecialSupervisorRepository.findAllByPecard(pecard);
    }

    /**
     * 新增跟編輯 表單特例管理
     * @param bpmSpecialSupervisor 表單特例管理
     * @return String 表單特例管理
     */
    public String patchSpecialSupervisor(BpmSpecialSupervisor bpmSpecialSupervisor) {
        return gson.toJson(bpmSpecialSupervisorRepository.save(bpmSpecialSupervisor));
    }
}
