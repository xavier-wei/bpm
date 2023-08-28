package tw.gov.pcc.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tw.gov.pcc.domain.BpmIsmsL410;
import tw.gov.pcc.repository.BpmIsmsL410Repository;
import tw.gov.pcc.service.dto.BpmIsmsL410DTO;
import tw.gov.pcc.service.mapper.BpmIsmsL410Mapper;

/**
 * Service Implementation for managing {@link BpmIsmsL410}.
 */
@Service
@Transactional
public class BpmIsmsL410Service {

    private final Logger log = LoggerFactory.getLogger(BpmIsmsL410Service.class);

    private final BpmIsmsL410Repository bpmIsmsL410Repository;

    private final BpmIsmsL410Mapper bpmIsmsL410Mapper;

    public BpmIsmsL410Service(BpmIsmsL410Repository bpmIsmsL410Repository, BpmIsmsL410Mapper bpmIsmsL410Mapper) {
        this.bpmIsmsL410Repository = bpmIsmsL410Repository;
        this.bpmIsmsL410Mapper = bpmIsmsL410Mapper;
    }

    /**
     * Save a bpmIsmsL410.
     *
     * @param bpmIsmsL410DTO the entity to save.
     * @return the persisted entity.
     */
    public BpmIsmsL410DTO save(BpmIsmsL410DTO bpmIsmsL410DTO) {
        log.debug("Request to save BpmIsmsL410 : {}", bpmIsmsL410DTO);
        BpmIsmsL410 bpmIsmsL410 = bpmIsmsL410Mapper.toEntity(bpmIsmsL410DTO);
        bpmIsmsL410 = bpmIsmsL410Repository.save(bpmIsmsL410);
        return bpmIsmsL410Mapper.toDto(bpmIsmsL410);
    }

    /**
     * Partially update a bpmIsmsL410.
     *
     * @param bpmIsmsL410DTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<BpmIsmsL410DTO> partialUpdate(BpmIsmsL410DTO bpmIsmsL410DTO) {
        log.debug("Request to partially update BpmIsmsL410 : {}", bpmIsmsL410DTO);

        return bpmIsmsL410Repository
            .findById(bpmIsmsL410DTO.getFormId())
            .map(existingBpmIsmsL410 -> {
                bpmIsmsL410Mapper.partialUpdate(existingBpmIsmsL410, bpmIsmsL410DTO);

                return existingBpmIsmsL410;
            })
            .map(bpmIsmsL410Repository::save)
            .map(bpmIsmsL410Mapper::toDto);
    }

    /**
     * Get all the bpmIsmsL410s.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<BpmIsmsL410DTO> findAll() {
        log.debug("Request to get all BpmIsmsL410s");
        return bpmIsmsL410Repository.findAll().stream().map(bpmIsmsL410Mapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one bpmIsmsL410 by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BpmIsmsL410DTO> findOne(String id) {
        log.debug("Request to get BpmIsmsL410 : {}", id);
        return bpmIsmsL410Repository.findById(id).map(bpmIsmsL410Mapper::toDto);
    }

    /**
     * Delete the bpmIsmsL410 by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete BpmIsmsL410 : {}", id);
        bpmIsmsL410Repository.deleteById(id);
    }
}
