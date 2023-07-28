package tw.gov.pcc.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tw.gov.pcc.domain.BpmIsmsL414;
import tw.gov.pcc.repository.BpmIsmsL414Repository;
import tw.gov.pcc.service.dto.BpmIsmsL414DTO;
import tw.gov.pcc.service.mapper.BpmIsmsL414Mapper;

/**
 * Service Implementation for managing {@link BpmIsmsL414}.
 */
@Service
@Transactional
public class BpmIsmsL414Service {

    private final Logger log = LoggerFactory.getLogger(BpmIsmsL414Service.class);

    private final BpmIsmsL414Repository bpmIsmsL414Repository;

    private final BpmIsmsL414Mapper bpmIsmsL414Mapper;

    public BpmIsmsL414Service(BpmIsmsL414Repository bpmIsmsL414Repository, BpmIsmsL414Mapper bpmIsmsL414Mapper) {
        this.bpmIsmsL414Repository = bpmIsmsL414Repository;
        this.bpmIsmsL414Mapper = bpmIsmsL414Mapper;
    }

    /**
     * Save a eipBpmIsmsL414.
     *
     * @param bpmIsmsL414DTO the entity to save.
     * @return the persisted entity.
     */
    public BpmIsmsL414DTO save(BpmIsmsL414DTO bpmIsmsL414DTO) {
        log.debug("Request to save EipBpmIsmsL414 : {}", bpmIsmsL414DTO);
        BpmIsmsL414 bpmIsmsL414 = bpmIsmsL414Mapper.toEntity(bpmIsmsL414DTO);
        bpmIsmsL414 = bpmIsmsL414Repository.save(bpmIsmsL414);
        return bpmIsmsL414Mapper.toDto(bpmIsmsL414);
    }

    /**
     * Partially update a eipBpmIsmsL414.
     *
     * @param bpmIsmsL414DTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<BpmIsmsL414DTO> partialUpdate(BpmIsmsL414DTO bpmIsmsL414DTO) {
        log.debug("Request to partially update EipBpmIsmsL414 : {}", bpmIsmsL414DTO);

        return bpmIsmsL414Repository
            .findById(bpmIsmsL414DTO.getFormId())
            .map(existingEipBpmIsmsL414 -> {
                bpmIsmsL414Mapper.partialUpdate(existingEipBpmIsmsL414, bpmIsmsL414DTO);

                return existingEipBpmIsmsL414;
            })
            .map(bpmIsmsL414Repository::save)
            .map(bpmIsmsL414Mapper::toDto);
    }

    /**
     * Get all the eipBpmIsmsL414s.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<BpmIsmsL414DTO> findAll() {
        log.debug("Request to get all EipBpmIsmsL414s");
        return bpmIsmsL414Repository
            .findAll()
            .stream()
            .map(bpmIsmsL414Mapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one eipBpmIsmsL414 by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BpmIsmsL414DTO> findOne(String id) {
        log.debug("Request to get EipBpmIsmsL414 : {}", id);
        return bpmIsmsL414Repository.findById(id).map(bpmIsmsL414Mapper::toDto);
    }

    /**
     * Delete the eipBpmIsmsL414 by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete EipBpmIsmsL414 : {}", id);
        bpmIsmsL414Repository.deleteById(id);
    }
}
