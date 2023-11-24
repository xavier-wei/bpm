package tw.gov.pcc.web.rest;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import tw.gov.pcc.domain.BpmSupervisor;
import tw.gov.pcc.service.SupervisorService;
import tw.gov.pcc.service.dto.BpmIsmsL410DTO;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/eip")
public class BpmSupervisorAdminResource {

    private final Logger log = LoggerFactory.getLogger(BpmSupervisorAdminResource.class);
    private final SupervisorService supervisorService;

    public BpmSupervisorAdminResource(SupervisorService supervisorService) {
        this.supervisorService = supervisorService;
    }

    /**
     *
     */
    @GetMapping("/supervisorAdmin")
    public List<BpmSupervisor> supervisorAdmin(@RequestParam(required = false) String title) {
        log.info("BpmSupervisorAdminResource.java - getSupervisor - 28 :: " + title);
        return supervisorService.supervisorAdmin(title);
    }

    @PatchMapping("/save/supervisorAdmin")
    public String patchSupervisor(@RequestBody() BpmSupervisor bpmSupervisor) {
        log.info("BpmSupervisorAdminResource.java - getSupervisor - 28 :: " + bpmSupervisor);
        return supervisorService.patchSupervisor(bpmSupervisor);
    }


}
