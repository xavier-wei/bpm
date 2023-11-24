package tw.gov.pcc.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import tech.jhipster.web.util.HeaderUtil;
import tw.gov.pcc.domain.BpmSpecialSupervisor;
import tw.gov.pcc.domain.User;
import tw.gov.pcc.repository.UserRepository;
import tw.gov.pcc.service.BpmSpecialSupervisorService;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/eip")
public class BpmSpecialSupervisorResource {
    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private static final String ENTITY_NAME = "bpmSpecialSupervisor";
    private final Logger log = LoggerFactory.getLogger(BpmSpecialSupervisorResource.class);

    private final BpmSpecialSupervisorService bpmSpecialSupervisorService;
    private final UserRepository userRepository;

    public BpmSpecialSupervisorResource(BpmSpecialSupervisorService bpmSpecialSupervisorService, UserRepository userRepository) {
        this.bpmSpecialSupervisorService = bpmSpecialSupervisorService;
        this.userRepository = userRepository;
    }

    @GetMapping("/setSupervisor")
    public List<BpmSpecialSupervisor> setSupervisor(@RequestParam(required = false) String pecard) {
        log.info("BpmSpecialSupervisorResource.java - setSupervisor - 28 :: " + pecard);
        return bpmSpecialSupervisorService.setSupervisor(pecard);
    }

    @PatchMapping("/save/setSupervisor")
    public String patchSupervisor(@RequestBody() BpmSpecialSupervisor bpmSpecialSupervisor) {
        log.info("BpmSpecialSupervisorResource.java - patchSupervisor - 32 :: " + bpmSpecialSupervisor);

        List<String> userIds = Stream.of(
                bpmSpecialSupervisor.getPecard(),
                bpmSpecialSupervisor.getF1Account(),
                bpmSpecialSupervisor.getF2Account()
            )
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
        userIds.forEach(userId -> {
            if (userId != null) {
                User user = userRepository.findByUserId(userId);
                if (user == null) {
                    throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "找不到 員編:" +userId + "的使用者");
                }
            }
        });

        return bpmSpecialSupervisorService.patchSpecialSupervisor(bpmSpecialSupervisor);
    }

    @DeleteMapping("/delete/setSupervisor/{id}")
    public ResponseEntity<Void> deleteBpmUploadFile(@PathVariable String id) {
        log.info("BpmSpecialSupervisorResource.java - deleteBpmUploadFile - 64 :: " + id );
        bpmSpecialSupervisorService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id))
            .build();
    }
}
