package tw.gov.pcc.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tw.gov.pcc.repository.BpmIsmsL410Repository;
import tw.gov.pcc.service.dto.BpmIsmsL410DTO;
import tw.gov.pcc.service.mapper.BpmIsmsL410Mapper;

import java.util.List;
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


    private final BpmIsmsL410Repository bpmIsmsL410Repository;

    private final BpmIsmsL410Mapper bpmIsmsL410Mapper;

    public BpmIsmsL410Resource(BpmIsmsL410Repository bpmIsmsL410Repository, BpmIsmsL410Mapper bpmIsmsL410Mapper) {
        this.bpmIsmsL410Repository = bpmIsmsL410Repository;
        this.bpmIsmsL410Mapper = bpmIsmsL410Mapper;
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
