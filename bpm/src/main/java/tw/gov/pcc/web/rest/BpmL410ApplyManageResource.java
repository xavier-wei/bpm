package tw.gov.pcc.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;
import tw.gov.pcc.repository.BpmL410ApplyManageRepository;
import tw.gov.pcc.service.BpmL410ApplyManageService;
import tw.gov.pcc.service.dto.BpmL410ApplyManageDTO;
import tw.gov.pcc.web.rest.errors.BadRequestAlertException;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * REST controller for managing {@link tw.gov.pcc.domain.BpmL410ApplyManage}.
 */
@RestController
@RequestMapping("/api/eip")
public class BpmL410ApplyManageResource {

    private final Logger log = LoggerFactory.getLogger(BpmL410ApplyManageResource.class);

    private static final String ENTITY_NAME = "bpmL410ApplyManage";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BpmL410ApplyManageService bpmL410ApplyManageService;

    private final BpmL410ApplyManageRepository bpmL410ApplyManageRepository;

    public BpmL410ApplyManageResource(
        BpmL410ApplyManageService bpmL410ApplyManageService,
        BpmL410ApplyManageRepository bpmL410ApplyManageRepository
    ) {
        this.bpmL410ApplyManageService = bpmL410ApplyManageService;
        this.bpmL410ApplyManageRepository = bpmL410ApplyManageRepository;
    }

    /**
     * {@code POST  /bpm-l410-apply-manages} : Create a new bpmL410ApplyManage.
     *
     * @param bpmL410ApplyManageDTO the bpmL410ApplyManageDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bpmL410ApplyManageDTO, or with status {@code 400 (Bad Request)} if the bpmL410ApplyManage has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/bpm-l410-apply-manages")
    public ResponseEntity<BpmL410ApplyManageDTO> createBpmL410ApplyManage(@Valid @RequestBody BpmL410ApplyManageDTO bpmL410ApplyManageDTO)
        throws URISyntaxException {
        log.debug("REST request to save BpmL410ApplyManage : {}", bpmL410ApplyManageDTO);
        if (bpmL410ApplyManageDTO.getId() != null) {
            throw new BadRequestAlertException("A new bpmL410ApplyManage cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BpmL410ApplyManageDTO result = bpmL410ApplyManageService.save(bpmL410ApplyManageDTO);
        return ResponseEntity
            .created(new URI("/api/bpm-l410-apply-manages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /bpm-l410-apply-manages/:id} : Updates an existing bpmL410ApplyManage.
     *
     * @param id the id of the bpmL410ApplyManageDTO to save.
     * @param bpmL410ApplyManageDTO the bpmL410ApplyManageDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bpmL410ApplyManageDTO,
     * or with status {@code 400 (Bad Request)} if the bpmL410ApplyManageDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bpmL410ApplyManageDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/bpm-l410-apply-manages/{id}")
    public ResponseEntity<BpmL410ApplyManageDTO> updateBpmL410ApplyManage(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody BpmL410ApplyManageDTO bpmL410ApplyManageDTO
    ) throws URISyntaxException {
        log.debug("REST request to update BpmL410ApplyManage : {}, {}", id, bpmL410ApplyManageDTO);
        if (bpmL410ApplyManageDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bpmL410ApplyManageDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bpmL410ApplyManageRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BpmL410ApplyManageDTO result = bpmL410ApplyManageService.save(bpmL410ApplyManageDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bpmL410ApplyManageDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /bpm-l410-apply-manages/:id} : Partial updates given fields of an existing bpmL410ApplyManage, field will ignore if it is null
     *
     * @param id the id of the bpmL410ApplyManageDTO to save.
     * @param bpmL410ApplyManageDTO the bpmL410ApplyManageDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bpmL410ApplyManageDTO,
     * or with status {@code 400 (Bad Request)} if the bpmL410ApplyManageDTO is not valid,
     * or with status {@code 404 (Not Found)} if the bpmL410ApplyManageDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the bpmL410ApplyManageDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/bpm-l410-apply-manages/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BpmL410ApplyManageDTO> partialUpdateBpmL410ApplyManage(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody BpmL410ApplyManageDTO bpmL410ApplyManageDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update BpmL410ApplyManage partially : {}, {}", id, bpmL410ApplyManageDTO);
        if (bpmL410ApplyManageDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bpmL410ApplyManageDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bpmL410ApplyManageRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BpmL410ApplyManageDTO> result = bpmL410ApplyManageService.partialUpdate(bpmL410ApplyManageDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bpmL410ApplyManageDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /bpm-l410-apply-manages} : get all the bpmL410ApplyManages.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bpmL410ApplyManages in body.
     */
    @GetMapping("/bpm-l410-apply-manages")
    public List<BpmL410ApplyManageDTO> getAllBpmL410ApplyManages() {
        log.debug("REST request to get all BpmL410ApplyManages");
        return bpmL410ApplyManageService.findAll();
    }

    /**
     * {@code GET  /bpm-l410-apply-manages/:id} : get the "id" bpmL410ApplyManage.
     *
     * @param id the id of the bpmL410ApplyManageDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bpmL410ApplyManageDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/bpm-l410-apply-manages/{id}")
    public ResponseEntity<BpmL410ApplyManageDTO> getBpmL410ApplyManage(@PathVariable Long id) {
        log.debug("REST request to get BpmL410ApplyManage : {}", id);
        Optional<BpmL410ApplyManageDTO> bpmL410ApplyManageDTO = bpmL410ApplyManageService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bpmL410ApplyManageDTO);
    }

    /**
     * {@code DELETE  /bpm-l410-apply-manages/:id} : delete the "id" bpmL410ApplyManage.
     *
     * @param id the id of the bpmL410ApplyManageDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/bpm-l410-apply-manages/{id}")
    public ResponseEntity<Void> deleteBpmL410ApplyManage(@PathVariable Long id) {
        log.debug("REST request to delete BpmL410ApplyManage : {}", id);
        bpmL410ApplyManageService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
