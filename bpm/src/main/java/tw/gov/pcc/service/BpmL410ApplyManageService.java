package tw.gov.pcc.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tw.gov.pcc.domain.BpmL410ApplyManage;
import tw.gov.pcc.repository.BpmL410ApplyManageRepository;
import tw.gov.pcc.service.dto.BpmL410ApplyManageDTO;
import tw.gov.pcc.service.mapper.BpmL410ApplyManageMapper;

/**
 * Service Implementation for managing {@link BpmL410ApplyManage}.
 */
@Service
@Transactional
public class BpmL410ApplyManageService {

    private final Logger log = LoggerFactory.getLogger(BpmL410ApplyManageService.class);

    private final BpmL410ApplyManageRepository bpmL410ApplyManageRepository;

    private final BpmL410ApplyManageMapper bpmL410ApplyManageMapper;

    public BpmL410ApplyManageService(
        BpmL410ApplyManageRepository bpmL410ApplyManageRepository,
        BpmL410ApplyManageMapper bpmL410ApplyManageMapper
    ) {
        this.bpmL410ApplyManageRepository = bpmL410ApplyManageRepository;
        this.bpmL410ApplyManageMapper = bpmL410ApplyManageMapper;
    }

    /**
     * Save a bpmL410ApplyManage.
     *
     * @param bpmL410ApplyManageDTO the entity to save.
     * @return the persisted entity.
     */
    public BpmL410ApplyManageDTO save(BpmL410ApplyManageDTO bpmL410ApplyManageDTO) {
        log.debug("Request to save BpmL410ApplyManage : {}", bpmL410ApplyManageDTO);
        BpmL410ApplyManage bpmL410ApplyManage = bpmL410ApplyManageMapper.toEntity(bpmL410ApplyManageDTO);
        bpmL410ApplyManage = bpmL410ApplyManageRepository.save(bpmL410ApplyManage);
        return bpmL410ApplyManageMapper.toDto(bpmL410ApplyManage);
    }

    /**
     * Partially update a bpmL410ApplyManage.
     *
     * @param bpmL410ApplyManageDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<BpmL410ApplyManageDTO> partialUpdate(BpmL410ApplyManageDTO bpmL410ApplyManageDTO) {
        log.debug("Request to partially update BpmL410ApplyManage : {}", bpmL410ApplyManageDTO);

        return bpmL410ApplyManageRepository
            .findById(bpmL410ApplyManageDTO.getId())
            .map(existingBpmL410ApplyManage -> {
                bpmL410ApplyManageMapper.partialUpdate(existingBpmL410ApplyManage, bpmL410ApplyManageDTO);

                return existingBpmL410ApplyManage;
            })
            .map(bpmL410ApplyManageRepository::save)
            .map(bpmL410ApplyManageMapper::toDto);
    }

    /**
     * Get all the bpmL410ApplyManages.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<BpmL410ApplyManageDTO> findAll() {
        log.debug("Request to get all BpmL410ApplyManages");
        return bpmL410ApplyManageRepository
            .findAll()
            .stream()
            .map(bpmL410ApplyManageMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one bpmL410ApplyManage by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BpmL410ApplyManageDTO> findOne(Long id) {
        log.debug("Request to get BpmL410ApplyManage : {}", id);
        return bpmL410ApplyManageRepository.findById(id).map(bpmL410ApplyManageMapper::toDto);
    }

    /**
     * Delete the bpmL410ApplyManage by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete BpmL410ApplyManage : {}", id);
        bpmL410ApplyManageRepository.deleteById(id);
    }
}
