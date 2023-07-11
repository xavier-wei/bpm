package tw.gov.pcc.flowable.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/process")
public class ProcessL414Resource {
    @PostMapping("/startL414")
    public String startL414Process() {
        return null;
    }
}
