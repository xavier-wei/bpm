package tw.gov.pcc.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tw.gov.pcc.domain.EipBpmIsmsL414;
import tw.gov.pcc.repository.EipBpmIsmsL414Repository;
import tw.gov.pcc.service.dto.EipBpmIsmsL414DTO;
import tw.gov.pcc.service.mapper.EipBpmIsmsL414Mapper;

/**
 * Service Implementation for managing {@link EipBpmIsmsL414}.
 */
@Service
@Transactional
public class EipBpmIsmsL414Service {

    private final Logger log = LoggerFactory.getLogger(EipBpmIsmsL414Service.class);

    private final EipBpmIsmsL414Repository eipBpmIsmsL414Repository;

    private final EipBpmIsmsL414Mapper eipBpmIsmsL414Mapper;

    public EipBpmIsmsL414Service(EipBpmIsmsL414Repository eipBpmIsmsL414Repository, EipBpmIsmsL414Mapper eipBpmIsmsL414Mapper) {
        this.eipBpmIsmsL414Repository = eipBpmIsmsL414Repository;
        this.eipBpmIsmsL414Mapper = eipBpmIsmsL414Mapper;
    }

    /**
     * Save a eipBpmIsmsL414.
     *
     * @param eipBpmIsmsL414DTO the entity to save.
     * @return the persisted entity.
     */
    public EipBpmIsmsL414DTO save(EipBpmIsmsL414DTO eipBpmIsmsL414DTO) {
        log.debug("Request to save EipBpmIsmsL414 : {}", eipBpmIsmsL414DTO);
        EipBpmIsmsL414 eipBpmIsmsL414 = eipBpmIsmsL414Mapper.toEntity(eipBpmIsmsL414DTO);
        eipBpmIsmsL414 = eipBpmIsmsL414Repository.save(eipBpmIsmsL414);
        return eipBpmIsmsL414Mapper.toDto(eipBpmIsmsL414);
    }

    /**
     * Partially update a eipBpmIsmsL414.
     *
     * @param eipBpmIsmsL414DTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<EipBpmIsmsL414DTO> partialUpdate(EipBpmIsmsL414DTO eipBpmIsmsL414DTO) {
        log.debug("Request to partially update EipBpmIsmsL414 : {}", eipBpmIsmsL414DTO);

        return eipBpmIsmsL414Repository
            .findById(eipBpmIsmsL414DTO.getFormId())
            .map(existingEipBpmIsmsL414 -> {
                eipBpmIsmsL414Mapper.partialUpdate(existingEipBpmIsmsL414, eipBpmIsmsL414DTO);

                return existingEipBpmIsmsL414;
            })
            .map(eipBpmIsmsL414Repository::save)
            .map(eipBpmIsmsL414Mapper::toDto);
    }

    /**
     * Get all the eipBpmIsmsL414s.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<EipBpmIsmsL414DTO> findAll() {
        log.debug("Request to get all EipBpmIsmsL414s");
        return eipBpmIsmsL414Repository
            .findAll()
            .stream()
            .map(eipBpmIsmsL414Mapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one eipBpmIsmsL414 by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EipBpmIsmsL414DTO> findOne(String id) {
        log.debug("Request to get EipBpmIsmsL414 : {}", id);
        return eipBpmIsmsL414Repository.findById(id).map(eipBpmIsmsL414Mapper::toDto);
    }

    /**
     * Delete the eipBpmIsmsL414 by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete EipBpmIsmsL414 : {}", id);
        eipBpmIsmsL414Repository.deleteById(id);
    }
}
