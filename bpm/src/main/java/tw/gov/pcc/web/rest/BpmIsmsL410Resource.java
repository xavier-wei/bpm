package tw.gov.pcc.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;
import tw.gov.pcc.repository.BpmIsmsL410Repository;
import tw.gov.pcc.service.BpmIsmsL410Service;
import tw.gov.pcc.service.dto.BpmIsmsL410DTO;
import tw.gov.pcc.service.mapper.BpmIsmsL410Mapper;
import tw.gov.pcc.web.rest.errors.BadRequestAlertException;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing {@link tw.gov.pcc.domain.BpmIsmsL410}.
 */
@RestController
@RequestMapping("/api/eip")
public class BpmIsmsL410Resource {

    private final Logger log = LoggerFactory.getLogger(BpmIsmsL410Resource.class);

    private static final String ENTITY_NAME = "bpmIsmsL410";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BpmIsmsL410Service bpmIsmsL410Service;

    private final BpmIsmsL410Repository bpmIsmsL410Repository;

    private final BpmIsmsL410Mapper bpmIsmsL410Mapper;

    public BpmIsmsL410Resource(BpmIsmsL410Service bpmIsmsL410Service, BpmIsmsL410Repository bpmIsmsL410Repository, BpmIsmsL410Mapper bpmIsmsL410Mapper) {
        this.bpmIsmsL410Service = bpmIsmsL410Service;
        this.bpmIsmsL410Repository = bpmIsmsL410Repository;
        this.bpmIsmsL410Mapper = bpmIsmsL410Mapper;
    }

    /**
     * {@code POST  /bpm-isms-l410} : Create a new bpmIsmsL410.
     *
     * @param bpmIsmsL410DTO the bpmIsmsL410DTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bpmIsmsL410DTO, or with status {@code 400 (Bad Request)} if the bpmIsmsL410 has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/bpm-isms-l410")
    public ResponseEntity<BpmIsmsL410DTO> createBpmIsmsL410(@Valid @RequestBody BpmIsmsL410DTO bpmIsmsL410DTO) throws URISyntaxException {
        log.debug("REST request to save BpmIsmsL410 : {}", bpmIsmsL410DTO);
        if (bpmIsmsL410DTO.getFormId() != null) {
            throw new BadRequestAlertException("A new bpmIsmsL410 cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BpmIsmsL410DTO result = bpmIsmsL410Service.save(bpmIsmsL410DTO);
        return ResponseEntity
            .created(new URI("/api/bpm-isms-l410/" + result.getFormId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getFormId()))
            .body(result);
    }

    /**
     * {@code PUT  /bpm-isms-l410/:formId} : Updates an existing bpmIsmsL410.
     *
     * @param formId the id of the bpmIsmsL410DTO to save.
     * @param bpmIsmsL410DTO the bpmIsmsL410DTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bpmIsmsL410DTO,
     * or with status {@code 400 (Bad Request)} if the bpmIsmsL410DTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bpmIsmsL410DTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/bpm-isms-l410/{formId}")
    public ResponseEntity<BpmIsmsL410DTO> updateBpmIsmsL410(
        @PathVariable(value = "formId", required = false) final String formId,
        @Valid @RequestBody BpmIsmsL410DTO bpmIsmsL410DTO
    ) throws URISyntaxException {
        log.debug("REST request to update BpmIsmsL410 : {}, {}", formId, bpmIsmsL410DTO);
        if (bpmIsmsL410DTO.getFormId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(formId, bpmIsmsL410DTO.getFormId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bpmIsmsL410Repository.existsById(formId)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BpmIsmsL410DTO result = bpmIsmsL410Service.save(bpmIsmsL410DTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bpmIsmsL410DTO.getFormId()))
            .body(result);
    }

    /**
     * {@code PATCH  /bpm-isms-l410/:formId} : Partial updates given fields of an existing bpmIsmsL410, field will ignore if it is null
     *
     * @param formId the id of the bpmIsmsL410DTO to save.
     * @param bpmIsmsL410DTO the bpmIsmsL410DTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bpmIsmsL410DTO,
     * or with status {@code 400 (Bad Request)} if the bpmIsmsL410DTO is not valid,
     * or with status {@code 404 (Not Found)} if the bpmIsmsL410DTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the bpmIsmsL410DTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/bpm-isms-l410/{formId}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BpmIsmsL410DTO> partialUpdateBpmIsmsL410(
        @PathVariable(value = "formId", required = false) final String formId,
        @NotNull @RequestBody BpmIsmsL410DTO bpmIsmsL410DTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update BpmIsmsL410 partially : {}, {}", formId, bpmIsmsL410DTO);
        if (bpmIsmsL410DTO.getFormId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(formId, bpmIsmsL410DTO.getFormId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bpmIsmsL410Repository.existsById(formId)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BpmIsmsL410DTO> result = bpmIsmsL410Service.partialUpdate(bpmIsmsL410DTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bpmIsmsL410DTO.getFormId())
        );
    }

    /**
     * {@code GET  /bpm-isms-l410} : get all the bpmIsmsL410s.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bpmIsmsL410s in body.
     */
    @GetMapping("/bpm-isms-l410")
    public List<BpmIsmsL410DTO> getAllBpmIsmsL410s() {
        log.debug("REST request to get all BpmIsmsL410s");
        return bpmIsmsL410Service.findAll();
    }

    /**
     * {@code GET  /bpm-isms-l410/:id} : get the "id" bpmIsmsL410.
     *
     * @param id the id of the bpmIsmsL410DTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bpmIsmsL410DTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/bpm-isms-l410/{id}")
    public ResponseEntity<BpmIsmsL410DTO> getBpmIsmsL410(@PathVariable String id) {
        log.debug("REST request to get BpmIsmsL410 : {}", id);
        Optional<BpmIsmsL410DTO> bpmIsmsL410DTO = bpmIsmsL410Service.findOne(id);
        return ResponseUtil.wrapOrNotFound(bpmIsmsL410DTO);
    }

    /**
     * {@code DELETE  /bpm-isms-l410/:id} : delete the "id" bpmIsmsL410.
     *
     * @param id the id of the bpmIsmsL410DTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/bpm-isms-l410/{id}")
    public ResponseEntity<Void> deleteBpmIsmsL410(@PathVariable String id) {
        log.debug("REST request to delete BpmIsmsL410 : {}", id);
        bpmIsmsL410Service.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }

    @GetMapping("/eip-bpm-isms-l410/findByWord")
    public List<BpmIsmsL410DTO> findByWord(
        @RequestParam(required = false) String word,
        @RequestParam(required = false) String number,
        @RequestParam(required = false) String appEmpid,
        @RequestParam(required = false) String processInstanceStatus
    ) {

        log.info("BpmIsmsL410Resource.java - findByWord - 186 :: " + bpmIsmsL410Repository.findByWord(word,appEmpid,processInstanceStatus)
            .stream()
            .map(bpmIsmsL410Mapper::toDto)
            .collect(Collectors.toList()) );

        return bpmIsmsL410Repository.findByWord(word,appEmpid,processInstanceStatus)
            .stream()
            .map(bpmIsmsL410Mapper::toDto)
            .collect(Collectors.toList());
    }
}
