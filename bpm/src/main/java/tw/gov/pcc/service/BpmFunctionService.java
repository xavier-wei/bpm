package tw.gov.pcc.service;

import java.util.*;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tw.gov.pcc.domain.BpmFunction;
import tw.gov.pcc.repository.BpmFunctionRepository;
import tw.gov.pcc.service.dto.BpmFunctionDTO;
import tw.gov.pcc.service.dto.MenuTreeDTO;
import tw.gov.pcc.service.mapper.BpmFunctionMapper;
import org.apache.commons.beanutils.BeanUtils;
/**
 * Service Implementation for managing {@link BpmFunction}.
 */
@Service
@Transactional
public class BpmFunctionService {

    private final String MENU_STATUS = "Y";
    private final Logger log = LoggerFactory.getLogger(BpmFunctionService.class);

    private final BpmFunctionRepository bpmFunctionRepository;

    private final BpmFunctionMapper bpmFunctionMapper;

    public BpmFunctionService(BpmFunctionRepository bpmFunctionRepository, BpmFunctionMapper bpmFunctionMapper) {
        this.bpmFunctionRepository = bpmFunctionRepository;
        this.bpmFunctionMapper = bpmFunctionMapper;
    }

    /**
     * Save a bpmFunction.
     *
     * @param bpmFunctionDTO the entity to save.
     * @return the persisted entity.
     */
    public BpmFunctionDTO save(BpmFunctionDTO bpmFunctionDTO) {
        log.debug("Request to save BpmFunction : {}", bpmFunctionDTO);
        BpmFunction bpmFunction = bpmFunctionMapper.toEntity(bpmFunctionDTO);
        bpmFunction = bpmFunctionRepository.save(bpmFunction);
        return bpmFunctionMapper.toDto(bpmFunction);
    }

    /**
     * Partially update a bpmFunction.
     *
     * @param bpmFunctionDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<BpmFunctionDTO> partialUpdate(BpmFunctionDTO bpmFunctionDTO) {
        log.debug("Request to partially update BpmFunction : {}", bpmFunctionDTO);

        return bpmFunctionRepository
            .findById(bpmFunctionDTO.getId())
            .map(existingBpmFunction -> {
                bpmFunctionMapper.partialUpdate(existingBpmFunction, bpmFunctionDTO);

                return existingBpmFunction;
            })
            .map(bpmFunctionRepository::save)
            .map(bpmFunctionMapper::toDto);
    }

    /**
     * Get all the bpmFunctions.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<BpmFunctionDTO> findAll() {
        log.debug("Request to get all BpmFunctions");
        return bpmFunctionRepository.findAll().stream().map(bpmFunctionMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one bpmFunction by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BpmFunctionDTO> findOne(Long id) {
        log.debug("Request to get BpmFunction : {}", id);
        return bpmFunctionRepository.findById(id).map(bpmFunctionMapper::toDto);
    }

    /**
     * Delete the bpmFunction by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete BpmFunction : {}", id);
        bpmFunctionRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<MenuTreeDTO> makeAdmFunctionMenu() {

        final List<BpmFunction> sourceFunction = bpmFunctionRepository.findByStatus(MENU_STATUS);
        log.info("BpmFunctionService.java - makeAdmFunctionMenu - 107 :: " + sourceFunction);
        List<MenuTreeDTO> menuTreeDTOs = sourceFunction.stream().sorted(Comparator.comparing(BpmFunction::getSortNo)).map(funMap -> {
            log.info("BpmFunctionService.java - makeAdmFunctionMenu - 109 :: " + funMap);
            final MenuTreeDTO menuTreeDTO = new MenuTreeDTO();
            try {
                BeanUtils.copyProperties(menuTreeDTO, funMap);
            } catch (Exception e) {
                log.info("function convert to tree was failed.");
            }
            return menuTreeDTO;
        }).collect(Collectors.toList());

        return makeMenuTree(menuTreeDTOs, "");
    }

    public List<MenuTreeDTO> makeMenuTree(List<MenuTreeDTO> menuTreeDTOs, String functionId) {
        log.info("BpmFunctionService.java - makeMenuTree - 123 :: " + menuTreeDTOs );
        final List<MenuTreeDTO> children = menuTreeDTOs.stream()
            .filter(treeItem -> StringUtils.equals(treeItem.getMasterFunctionId(), functionId))
            .collect(Collectors.toList());

        final List<MenuTreeDTO> successor = menuTreeDTOs.stream()
            .filter(treeItem -> !StringUtils.equals(treeItem.getMasterFunctionId(), functionId))
            .collect(Collectors.toList());

        children.forEach(linkedTree ->
            makeMenuTree(successor, linkedTree.getFunctionId()).forEach(
                childrenItem -> linkedTree.getChildren().add(childrenItem)));

        return children;
    }
}
