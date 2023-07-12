package tw.gov.pcc.flowable.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tw.gov.pcc.flowable.service.ProcessL414Service;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/process")
public class ProcessL414Resource {
    private final ProcessL414Service service;

    public ProcessL414Resource(ProcessL414Service service) {
        this.service = service;
    }


    @RequestMapping("/startL414")
    public String startL414Process() {
        Map<String, Object> variables = Map.of(
                "applier", "Tester"
                , "applierConfirm", "confirm",
                "sectionChief", "chief");
        return service.startProcess(variables);
    }

    @RequestMapping("/chief")
    public String chief() {
        Map<String, Object> variables = Map.of("chiefDecision", "2");
        service.completeTask("Chief", variables);
        return "OK";
    }
}
