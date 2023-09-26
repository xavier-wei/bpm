package tw.gov.pcc.web.rest;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;
import tw.gov.pcc.domain.BpmUploadFile;
import tw.gov.pcc.repository.BpmUploadFileRepository;
import tw.gov.pcc.service.BpmUploadFileService;
import tw.gov.pcc.service.dto.BpmUploadFileDTO;
import tw.gov.pcc.service.mapper.BpmUploadFileMapper;
import tw.gov.pcc.web.rest.errors.BadRequestAlertException;
import tw.gov.pcc.web.rest.io.DownloadableResource;

import static tw.gov.pcc.utils.CommonUtils.normalizePath;

/**
 * REST controller for managing {@link tw.gov.pcc.domain.BpmUploadFile}.
 */
@RestController
@RequestMapping("/api")
public class BpmUploadFileResource {

    private final Logger log = LoggerFactory.getLogger(BpmUploadFileResource.class);

    private static final String ENTITY_NAME = "bpmUploadFile";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BpmUploadFileService bpmUploadFileService;

    private final BpmUploadFileRepository bpmUploadFileRepository;

    private final BpmUploadFileMapper bpmUploadFileMapper;

    public BpmUploadFileResource(BpmUploadFileService bpmUploadFileService, BpmUploadFileRepository bpmUploadFileRepository, BpmUploadFileMapper bpmUploadFileMapper) {
        this.bpmUploadFileService = bpmUploadFileService;
        this.bpmUploadFileRepository = bpmUploadFileRepository;
        this.bpmUploadFileMapper = bpmUploadFileMapper;
    }

    /**
     * {@code POST  /bpm-upload-files} : Create a new bpmUploadFile.
     *
     * @param bpmUploadFileDTO the bpmUploadFileDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bpmUploadFileDTO, or with status {@code 400 (Bad Request)} if the bpmUploadFile has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/bpm-upload-files")
    public ResponseEntity<BpmUploadFileDTO> createBpmUploadFile(@Valid @RequestBody BpmUploadFileDTO bpmUploadFileDTO)
            throws URISyntaxException {
        log.debug("REST request to save BpmUploadFile : {}", bpmUploadFileDTO);
        if (bpmUploadFileDTO.getId() != null) {
            throw new BadRequestAlertException("A new bpmUploadFile cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BpmUploadFileDTO result = bpmUploadFileService.save(bpmUploadFileDTO);
        return ResponseEntity
                .created(new URI("/api/bpm-upload-files/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    /**
     * {@code PUT  /bpm-upload-files/:uuid} : Updates an existing bpmUploadFile.
     *
     * @param uuid             the id of the bpmUploadFileDTO to save.
     * @param bpmUploadFileDTO the bpmUploadFileDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bpmUploadFileDTO,
     * or with status {@code 400 (Bad Request)} if the bpmUploadFileDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bpmUploadFileDTO couldn't be updated.
     */
    @PutMapping("/bpm-upload-files/{uuid}")
    public ResponseEntity<BpmUploadFileDTO> updateBpmUploadFile(
            @PathVariable(value = "uuid", required = false) final Long uuid,
            @Valid @RequestBody BpmUploadFileDTO bpmUploadFileDTO
    )  {
        log.debug("REST request to update BpmUploadFile : {}, {}", uuid, bpmUploadFileDTO);
        if (bpmUploadFileDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(uuid, bpmUploadFileDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bpmUploadFileRepository.existsById(uuid)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BpmUploadFileDTO result = bpmUploadFileService.save(bpmUploadFileDTO);
        return ResponseEntity
                .ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bpmUploadFileDTO.getId().toString()))
                .body(result);
    }

    /**
     * {@code PATCH  /bpm-upload-files/:uuid} : Partial updates given fields of an existing bpmUploadFile, field will ignore if it is null
     *
     * @param uuid             the id of the bpmUploadFileDTO to save.
     * @param bpmUploadFileDTO the bpmUploadFileDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bpmUploadFileDTO,
     * or with status {@code 400 (Bad Request)} if the bpmUploadFileDTO is not valid,
     * or with status {@code 404 (Not Found)} if the bpmUploadFileDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the bpmUploadFileDTO couldn't be updated.
     */
    @PatchMapping(value = "/bpm-upload-files/{uuid}", consumes = {"application/json", "application/merge-patch+json"})
    public ResponseEntity<BpmUploadFileDTO> partialUpdateBpmUploadFile(
            @PathVariable(value = "uuid", required = false) final Long uuid,
            @NotNull @RequestBody BpmUploadFileDTO bpmUploadFileDTO
    )  {
        log.debug("REST request to partial update BpmUploadFile partially : {}, {}", uuid, bpmUploadFileDTO);
        if (bpmUploadFileDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(uuid, bpmUploadFileDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bpmUploadFileRepository.existsById(uuid)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BpmUploadFileDTO> result = bpmUploadFileService.partialUpdate(bpmUploadFileDTO);

        return ResponseUtil.wrapOrNotFound(
                result,
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bpmUploadFileDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /bpm-upload-files} : get all the bpmUploadFiles.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bpmUploadFiles in body.
     */
    @GetMapping("/bpm-upload-files")
    public List<BpmUploadFileDTO> getAllBpmUploadFiles() {
        log.debug("REST request to get all BpmUploadFiles");
        return bpmUploadFileService.findAll();
    }

    /**
     * {@code GET  /bpm-upload-files/:id} : get the "id" bpmUploadFile.
     *
     * @param id the id of the bpmUploadFileDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bpmUploadFileDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/bpm-upload-files/{id}")
    public ResponseEntity<BpmUploadFileDTO> getBpmUploadFile(@PathVariable Long id) {
        log.debug("REST request to get BpmUploadFile : {}", id);
        Optional<BpmUploadFileDTO> bpmUploadFileDTO = bpmUploadFileService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bpmUploadFileDTO);
    }

    /**
     * {@code DELETE  /bpm-upload-files/:id} : delete the "id" bpmUploadFile.
     *
     * @param id the id of the bpmUploadFileDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/eip/delete/bpmUploadFiles/{id}")
    public ResponseEntity<Void> deleteBpmUploadFile(@PathVariable Long id) {
        log.debug("REST request to delete BpmUploadFile : {}", id);
        bpmUploadFileService.delete(id);
        return ResponseEntity
                .noContent()
                .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
                .build();
    }


    @PostMapping("/eip/bpmUploadFile")
    public ResponseEntity<BpmUploadFileDTO> bpmUploadFile(
            @RequestPart(name = "file", required = false) MultipartFile file,
            @Valid @RequestPart(name = "dto", required = false) BpmUploadFileDTO dto
    ) throws IOException {

        final  BpmUploadFileDTO result = bpmUploadFileService.bpmUploadFile(bpmUploadFileMapper.toEntity(dto), file);

        return ResponseEntity
                .ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, "BpmUploadFile", result.getId().toString()))
                .body(result);

    }

    @GetMapping("/eip/bpm-upload-files/formId/{formId}")
    public List<BpmUploadFileDTO> getBpmUploadFileByFormId(@PathVariable String formId) {
        log.debug("REST request to get BpmUploadFile : {}", formId);
        return bpmUploadFileMapper.toDto(bpmUploadFileRepository.findBpmUploadFileByFormId(formId));
    }

    @GetMapping("/eip/bpm-upload-files/downloadFile/{id}")
    public DownloadableResource downloadFile(@PathVariable Long id) throws IOException {

        Optional<BpmUploadFile> bpmFile = bpmUploadFileRepository.findById(id);

        if (bpmFile.isPresent()) {
            Path path = Path.of(normalizePath(bpmFile.get().getFilePath()));
            if (path.toFile().exists()) {
                return new DownloadableResource(new ByteArrayResource(Files.readAllBytes(path)), bpmFile.get().getFileName());
            } else {
                return null;
            }
        }
        return null;
    }


}
