package tw.gov.pcc.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import tw.gov.pcc.domain.BpmIsmsL414;
import tw.gov.pcc.repository.BpmIsmsL414Repository;
import tw.gov.pcc.repository.BpmSignStatusRepository;
import tw.gov.pcc.service.dto.BpmFormQueryDto;
import tw.gov.pcc.service.dto.BpmIsmsL414DTO;
import tw.gov.pcc.service.dto.BpmSignStatusDTO;
import tw.gov.pcc.service.mapper.BpmIsmsL414Mapper;
import tw.gov.pcc.service.mapper.BpmSignStatusMapper;

import javax.validation.Valid;
import java.util.List;
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


    private final BpmIsmsL414Repository bpmIsmsL414Repository;

    private final BpmIsmsL414Mapper bpmIsmsL414Mapper;

    private final BpmSignStatusRepository bpmSignStatusRepository;

    private final BpmSignStatusMapper bpmSignStatusMapper;

    public BpmIsmsL414Resource( BpmIsmsL414Repository bpmIsmsL414Repository, BpmIsmsL414Mapper bpmIsmsL414Mapper, BpmSignStatusRepository bpmSignStatusRepository, BpmSignStatusMapper bpmSignStatusMapper) {
        this.bpmIsmsL414Repository = bpmIsmsL414Repository;
        this.bpmIsmsL414Mapper = bpmIsmsL414Mapper;
        this.bpmSignStatusRepository = bpmSignStatusRepository;
        this.bpmSignStatusMapper = bpmSignStatusMapper;
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
