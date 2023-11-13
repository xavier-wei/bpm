package tw.gov.pcc.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tw.gov.pcc.domain.BpmL410ApplyManage;
import tw.gov.pcc.repository.BpmL410ApplyManageRepository;
import tw.gov.pcc.service.dto.BpmL410ApplyManageDTO;
import tw.gov.pcc.service.mapper.BpmL410ApplyManageMapper;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

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
}
