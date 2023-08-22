package tw.gov.pcc.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;
import tw.gov.pcc.domain.BpmIsmsL414;
import tw.gov.pcc.repository.BpmIsmsL414Repository;
import tw.gov.pcc.repository.BpmSignStatusRepository;
import tw.gov.pcc.service.BpmIsmsL414Service;
import tw.gov.pcc.service.dto.BpmFormQueryDto;
import tw.gov.pcc.service.dto.BpmIsmsL414DTO;
import tw.gov.pcc.service.dto.BpmSignStatusDTO;
import tw.gov.pcc.service.mapper.BpmIsmsL414Mapper;
import tw.gov.pcc.service.mapper.BpmSignStatusMapper;
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
 * REST controller for managing {@link BpmIsmsL414}.
 */
@RestController
@RequestMapping("/api/eip")
public class BpmIsmsL414Resource {

    private final Logger log = LoggerFactory.getLogger(BpmIsmsL414Resource.class);

    private static final String ENTITY_NAME = "eipBpmIsmsL414";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BpmIsmsL414Service bpmIsmsL414Service;

    private final BpmIsmsL414Repository bpmIsmsL414Repository;

    private final BpmIsmsL414Mapper bpmIsmsL414Mapper;

    private final BpmSignStatusRepository bpmSignStatusRepository;

    private final BpmSignStatusMapper bpmSignStatusMapper;

    public BpmIsmsL414Resource(BpmIsmsL414Service bpmIsmsL414Service, BpmIsmsL414Repository bpmIsmsL414Repository, BpmIsmsL414Mapper bpmIsmsL414Mapper, BpmSignStatusRepository bpmSignStatusRepository, BpmSignStatusMapper bpmSignStatusMapper) {
        this.bpmIsmsL414Service = bpmIsmsL414Service;
        this.bpmIsmsL414Repository = bpmIsmsL414Repository;
        this.bpmIsmsL414Mapper = bpmIsmsL414Mapper;
        this.bpmSignStatusRepository = bpmSignStatusRepository;
        this.bpmSignStatusMapper = bpmSignStatusMapper;
    }

    /**
     * {@code POST  /eip-bpm-isms-l414} : Create a new eipBpmIsmsL414.
     *
     * @param bpmIsmsL414DTO the eipBpmIsmsL414DTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new eipBpmIsmsL414DTO, or with status {@code 400 (Bad Request)} if the eipBpmIsmsL414 has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/eip-bpm-isms-l414")
    public ResponseEntity<BpmIsmsL414DTO> createEipBpmIsmsL414(@Valid @RequestBody BpmIsmsL414DTO bpmIsmsL414DTO)
        throws URISyntaxException {
        log.debug("REST request to save EipBpmIsmsL414 : {}", bpmIsmsL414DTO);
        if (bpmIsmsL414DTO.getFormId() != null) {
            throw new BadRequestAlertException("A new eipBpmIsmsL414 cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BpmIsmsL414DTO result = bpmIsmsL414Service.save(bpmIsmsL414DTO);
        return ResponseEntity
            .created(new URI("/api/eip-bpm-isms-l414/" + result.getFormId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getFormId()))
            .body(result);
    }

    /**
     * {@code PUT  /eip-bpm-isms-l414/:formId} : Updates an existing eipBpmIsmsL414.
     *
     * @param formId the id of the eipBpmIsmsL414DTO to save.
     * @param bpmIsmsL414DTO the eipBpmIsmsL414DTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated eipBpmIsmsL414DTO,
     * or with status {@code 400 (Bad Request)} if the eipBpmIsmsL414DTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the eipBpmIsmsL414DTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/eip-bpm-isms-l414/{formId}")
    public ResponseEntity<BpmIsmsL414DTO> updateEipBpmIsmsL414(
        @PathVariable(value = "formId", required = false) final String formId,
        @Valid @RequestBody BpmIsmsL414DTO bpmIsmsL414DTO
    ) throws URISyntaxException {
        log.debug("REST request to update EipBpmIsmsL414 : {}, {}", formId, bpmIsmsL414DTO);
        if (bpmIsmsL414DTO.getFormId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(formId, bpmIsmsL414DTO.getFormId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bpmIsmsL414Repository.existsById(formId)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BpmIsmsL414DTO result = bpmIsmsL414Service.save(bpmIsmsL414DTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bpmIsmsL414DTO.getFormId()))
            .body(result);
    }

    /**
     * {@code PATCH  /eip-bpm-isms-l414/:formId} : Partial updates given fields of an existing eipBpmIsmsL414, field will ignore if it is null
     *
     * @param formId the id of the eipBpmIsmsL414DTO to save.
     * @param bpmIsmsL414DTO the eipBpmIsmsL414DTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated eipBpmIsmsL414DTO,
     * or with status {@code 400 (Bad Request)} if the eipBpmIsmsL414DTO is not valid,
     * or with status {@code 404 (Not Found)} if the eipBpmIsmsL414DTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the eipBpmIsmsL414DTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/eip-bpm-isms-l414/{formId}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BpmIsmsL414DTO> partialUpdateEipBpmIsmsL414(
        @PathVariable(value = "formId", required = false) final String formId,
        @NotNull @RequestBody BpmIsmsL414DTO bpmIsmsL414DTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update EipBpmIsmsL414 partially : {}, {}", formId, bpmIsmsL414DTO);
        if (bpmIsmsL414DTO.getFormId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(formId, bpmIsmsL414DTO.getFormId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bpmIsmsL414Repository.existsById(formId)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BpmIsmsL414DTO> result = bpmIsmsL414Service.partialUpdate(bpmIsmsL414DTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bpmIsmsL414DTO.getFormId())
        );
    }

    /**
     * {@code GET  /eip-bpm-isms-l414} : get all the eipBpmIsmsL414s.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of eipBpmIsmsL414s in body.
     */
    @GetMapping("/eip-bpm-isms-l414")
    public List<BpmIsmsL414DTO> getAllEipBpmIsmsL414s() {
        log.debug("REST request to get all EipBpmIsmsL414s");
        return bpmIsmsL414Service.findAll();
    }

    /**
     * {@code GET  /eip-bpm-isms-l414/:id} : get the "id" eipBpmIsmsL414.
     *
     * @param id the id of the eipBpmIsmsL414DTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the eipBpmIsmsL414DTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/eip-bpm-isms-l414/{id}")
    public ResponseEntity<BpmIsmsL414DTO> getEipBpmIsmsL414(@PathVariable String id) {
        log.debug("REST request to get EipBpmIsmsL414 : {}", id);
        Optional<BpmIsmsL414DTO> eipBpmIsmsL414DTO = bpmIsmsL414Service.findOne(id);
        return ResponseUtil.wrapOrNotFound(eipBpmIsmsL414DTO);
    }

    /**
     * {@code DELETE  /eip-bpm-isms-l414/:id} : delete the "id" eipBpmIsmsL414.
     *
     * @param id the id of the eipBpmIsmsL414DTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/eip-bpm-isms-l414/{id}")
    public ResponseEntity<Void> deleteEipBpmIsmsL414(@PathVariable String id) {
        log.debug("REST request to delete EipBpmIsmsL414 : {}", id);
        bpmIsmsL414Service.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }

    @GetMapping("/eip-bpm-isms-l414/findByWord")
    public List<BpmIsmsL414DTO> findByWord(
            @RequestParam(required = false) String word,
            @RequestParam(required = false) String number,
            @RequestParam(required = false) String appEmpid,
            @RequestParam(required = false) String processInstanceStatus
    ) {

        return bpmIsmsL414Repository.findByWord(word,appEmpid,processInstanceStatus)
                .stream()
                .map(bpmIsmsL414Mapper::toDto)
                .collect(Collectors.toList());
    }


    @GetMapping("/getBpmSignStatus/{id}")
    public List<BpmSignStatusDTO> findByBpmSignStatus(
            @PathVariable String id
    ) {

        List<BpmSignStatusDTO> bpmSignStatus = bpmSignStatusRepository.findByFormIdOrderBySigningDatetime(id)
                .stream()
                .map(bpmSignStatusMapper::toDto)
                .collect(Collectors.toList());

        log.info("BpmIsmsL414Resource.java - findByBpmSignStatus - 212 :: " + bpmSignStatus );

        return bpmSignStatus;
    }

    @RequestMapping("/getNotify")
    public List<BpmIsmsL414DTO> getNotify(@Valid @RequestPart(required = false) BpmFormQueryDto bpmFormQueryDto) {
        log.info("ProcessL414Resource.java - queryTask - 194 :: " + bpmFormQueryDto);
        return bpmIsmsL414Repository.getNotify(bpmFormQueryDto);
    }

}
