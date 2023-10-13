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


    public BpmL410ApplyManageResource(
        BpmL410ApplyManageService bpmL410ApplyManageService
    ) {
        this.bpmL410ApplyManageService = bpmL410ApplyManageService;
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


}
