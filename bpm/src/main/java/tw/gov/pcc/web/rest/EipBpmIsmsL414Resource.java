package tw.gov.pcc.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;
import tw.gov.pcc.repository.EipBpmIsmsL414Repository;
import tw.gov.pcc.service.EipBpmIsmsL414Service;
import tw.gov.pcc.service.dto.EipBpmIsmsL414DTO;
import tw.gov.pcc.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link tw.gov.pcc.domain.EipBpmIsmsL414}.
 */
@RestController
@RequestMapping("/api/eip")
public class EipBpmIsmsL414Resource {

    private final Logger log = LoggerFactory.getLogger(EipBpmIsmsL414Resource.class);

    private static final String ENTITY_NAME = "eipBpmIsmsL414";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EipBpmIsmsL414Service eipBpmIsmsL414Service;

    private final EipBpmIsmsL414Repository eipBpmIsmsL414Repository;

    public EipBpmIsmsL414Resource(EipBpmIsmsL414Service eipBpmIsmsL414Service, EipBpmIsmsL414Repository eipBpmIsmsL414Repository) {
        this.eipBpmIsmsL414Service = eipBpmIsmsL414Service;
        this.eipBpmIsmsL414Repository = eipBpmIsmsL414Repository;
    }

    /**
     * {@code POST  /eip-bpm-isms-l414} : Create a new eipBpmIsmsL414.
     *
     * @param eipBpmIsmsL414DTO the eipBpmIsmsL414DTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new eipBpmIsmsL414DTO, or with status {@code 400 (Bad Request)} if the eipBpmIsmsL414 has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/eip-bpm-isms-l414")
    public ResponseEntity<EipBpmIsmsL414DTO> createEipBpmIsmsL414(@Valid @RequestBody EipBpmIsmsL414DTO eipBpmIsmsL414DTO)
        throws URISyntaxException {
        log.debug("REST request to save EipBpmIsmsL414 : {}", eipBpmIsmsL414DTO);
        if (eipBpmIsmsL414DTO.getFormId() != null) {
            throw new BadRequestAlertException("A new eipBpmIsmsL414 cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EipBpmIsmsL414DTO result = eipBpmIsmsL414Service.save(eipBpmIsmsL414DTO);
        return ResponseEntity
            .created(new URI("/api/eip-bpm-isms-l414/" + result.getFormId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getFormId()))
            .body(result);
    }

    /**
     * {@code PUT  /eip-bpm-isms-l414/:formId} : Updates an existing eipBpmIsmsL414.
     *
     * @param formId the id of the eipBpmIsmsL414DTO to save.
     * @param eipBpmIsmsL414DTO the eipBpmIsmsL414DTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated eipBpmIsmsL414DTO,
     * or with status {@code 400 (Bad Request)} if the eipBpmIsmsL414DTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the eipBpmIsmsL414DTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/eip-bpm-isms-l414/{formId}")
    public ResponseEntity<EipBpmIsmsL414DTO> updateEipBpmIsmsL414(
        @PathVariable(value = "formId", required = false) final String formId,
        @Valid @RequestBody EipBpmIsmsL414DTO eipBpmIsmsL414DTO
    ) throws URISyntaxException {
        log.debug("REST request to update EipBpmIsmsL414 : {}, {}", formId, eipBpmIsmsL414DTO);
        if (eipBpmIsmsL414DTO.getFormId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(formId, eipBpmIsmsL414DTO.getFormId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!eipBpmIsmsL414Repository.existsById(formId)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EipBpmIsmsL414DTO result = eipBpmIsmsL414Service.save(eipBpmIsmsL414DTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, eipBpmIsmsL414DTO.getFormId()))
            .body(result);
    }

    /**
     * {@code PATCH  /eip-bpm-isms-l414/:formId} : Partial updates given fields of an existing eipBpmIsmsL414, field will ignore if it is null
     *
     * @param formId the id of the eipBpmIsmsL414DTO to save.
     * @param eipBpmIsmsL414DTO the eipBpmIsmsL414DTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated eipBpmIsmsL414DTO,
     * or with status {@code 400 (Bad Request)} if the eipBpmIsmsL414DTO is not valid,
     * or with status {@code 404 (Not Found)} if the eipBpmIsmsL414DTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the eipBpmIsmsL414DTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/eip-bpm-isms-l414/{formId}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EipBpmIsmsL414DTO> partialUpdateEipBpmIsmsL414(
        @PathVariable(value = "formId", required = false) final String formId,
        @NotNull @RequestBody EipBpmIsmsL414DTO eipBpmIsmsL414DTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update EipBpmIsmsL414 partially : {}, {}", formId, eipBpmIsmsL414DTO);
        if (eipBpmIsmsL414DTO.getFormId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(formId, eipBpmIsmsL414DTO.getFormId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!eipBpmIsmsL414Repository.existsById(formId)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EipBpmIsmsL414DTO> result = eipBpmIsmsL414Service.partialUpdate(eipBpmIsmsL414DTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, eipBpmIsmsL414DTO.getFormId())
        );
    }

    /**
     * {@code GET  /eip-bpm-isms-l414} : get all the eipBpmIsmsL414s.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of eipBpmIsmsL414s in body.
     */
    @GetMapping("/eip-bpm-isms-l414")
    public List<EipBpmIsmsL414DTO> getAllEipBpmIsmsL414s() {
        log.debug("REST request to get all EipBpmIsmsL414s");
        return eipBpmIsmsL414Service.findAll();
    }

    /**
     * {@code GET  /eip-bpm-isms-l414/:id} : get the "id" eipBpmIsmsL414.
     *
     * @param id the id of the eipBpmIsmsL414DTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the eipBpmIsmsL414DTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/eip-bpm-isms-l414/{id}")
    public ResponseEntity<EipBpmIsmsL414DTO> getEipBpmIsmsL414(@PathVariable String id) {
        log.debug("REST request to get EipBpmIsmsL414 : {}", id);
        Optional<EipBpmIsmsL414DTO> eipBpmIsmsL414DTO = eipBpmIsmsL414Service.findOne(id);
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
        eipBpmIsmsL414Service.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
