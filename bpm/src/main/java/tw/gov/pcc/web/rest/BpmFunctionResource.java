package tw.gov.pcc.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.keycloak.KeycloakSecurityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;
import tw.gov.pcc.repository.BpmFunctionRepository;
import tw.gov.pcc.service.BpmFunctionService;
import tw.gov.pcc.service.dto.BpmFunctionDTO;
import tw.gov.pcc.service.dto.MenuTreeDTO;
import tw.gov.pcc.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link tw.gov.pcc.domain.BpmFunction}.
 */
@RestController
@RequestMapping("/api")
public class BpmFunctionResource {

    private final Logger log = LoggerFactory.getLogger(BpmFunctionResource.class);

    private static final String ENTITY_NAME = "bpmFunction";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BpmFunctionService bpmFunctionService;

    private final BpmFunctionRepository bpmFunctionRepository;

    public BpmFunctionResource(BpmFunctionService bpmFunctionService, BpmFunctionRepository bpmFunctionRepository) {
        this.bpmFunctionService = bpmFunctionService;
        this.bpmFunctionRepository = bpmFunctionRepository;
    }

    /**
     * {@code POST  /bpm-functions} : Create a new bpmFunction.
     *
     * @param bpmFunctionDTO the bpmFunctionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bpmFunctionDTO, or with status {@code 400 (Bad Request)} if the bpmFunction has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/bpm-functions")
    public ResponseEntity<BpmFunctionDTO> createBpmFunction(@Valid @RequestBody BpmFunctionDTO bpmFunctionDTO) throws URISyntaxException {
        log.debug("REST request to save BpmFunction : {}", bpmFunctionDTO);
        if (bpmFunctionDTO.getId() != null) {
            throw new BadRequestAlertException("A new bpmFunction cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BpmFunctionDTO result = bpmFunctionService.save(bpmFunctionDTO);
        return ResponseEntity
            .created(new URI("/api/bpm-functions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /bpm-functions/:id} : Updates an existing bpmFunction.
     *
     * @param id             the id of the bpmFunctionDTO to save.
     * @param bpmFunctionDTO the bpmFunctionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bpmFunctionDTO,
     * or with status {@code 400 (Bad Request)} if the bpmFunctionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bpmFunctionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/bpm-functions/{id}")
    public ResponseEntity<BpmFunctionDTO> updateBpmFunction(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody BpmFunctionDTO bpmFunctionDTO
    ) throws URISyntaxException {
        log.debug("REST request to update BpmFunction : {}, {}", id, bpmFunctionDTO);
        if (bpmFunctionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bpmFunctionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bpmFunctionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BpmFunctionDTO result = bpmFunctionService.save(bpmFunctionDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bpmFunctionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /bpm-functions/:id} : Partial updates given fields of an existing bpmFunction, field will ignore if it is null
     *
     * @param id             the id of the bpmFunctionDTO to save.
     * @param bpmFunctionDTO the bpmFunctionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bpmFunctionDTO,
     * or with status {@code 400 (Bad Request)} if the bpmFunctionDTO is not valid,
     * or with status {@code 404 (Not Found)} if the bpmFunctionDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the bpmFunctionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/bpm-functions/{id}", consumes = {"application/json", "application/merge-patch+json"})
    public ResponseEntity<BpmFunctionDTO> partialUpdateBpmFunction(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody BpmFunctionDTO bpmFunctionDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update BpmFunction partially : {}, {}", id, bpmFunctionDTO);
        if (bpmFunctionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bpmFunctionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bpmFunctionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BpmFunctionDTO> result = bpmFunctionService.partialUpdate(bpmFunctionDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bpmFunctionDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /bpm-functions} : get all the bpmFunctions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bpmFunctions in body.
     */
    @GetMapping("/bpm-functions")
    public List<BpmFunctionDTO> getAllBpmFunctions() {
        log.debug("REST request to get all BpmFunctions");
        return bpmFunctionService.findAll();
    }

    /**
     * {@code GET  /bpm-functions/:id} : get the "id" bpmFunction.
     *
     * @param id the id of the bpmFunctionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bpmFunctionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/bpm-functions/{id}")
    public ResponseEntity<BpmFunctionDTO> getBpmFunction(@PathVariable Long id) {
        log.debug("REST request to get BpmFunction : {}", id);
        Optional<BpmFunctionDTO> bpmFunctionDTO = bpmFunctionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bpmFunctionDTO);
    }

    /**
     * {@code DELETE  /bpm-functions/:id} : delete the "id" bpmFunction.
     *
     * @param id the id of the bpmFunctionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/bpm-functions/{id}")
    public ResponseEntity<Void> deleteBpmFunction(@PathVariable Long id) {
        log.debug("REST request to delete BpmFunction : {}", id);
        bpmFunctionService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    @GetMapping("/eip/menu")
    public ResponseEntity<List<MenuTreeDTO>> makeMenu() {

        List<MenuTreeDTO> menuTreeDTOs = bpmFunctionService.makeAdmFunctionMenu();

        return ResponseEntity.ok().body(menuTreeDTOs);
    }

}
