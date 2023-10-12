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
