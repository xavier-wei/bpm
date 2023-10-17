package tw.gov.pcc.web.rest;

import com.google.gson.Gson;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import tw.gov.pcc.service.SubordinateTaskService;

@RestController
public class SubordinateResource {

    private final SubordinateTaskService subordinateTaskService;
    private Gson gson = new Gson();

    public SubordinateResource(SubordinateTaskService subordinateTaskService) {
        this.subordinateTaskService = subordinateTaskService;
    }

    @GetMapping("/api/getSubordinateProcess")
    public String getSubordinateProcess() {
//        subordinateTaskService.findAllSubordinate("1510");

        return gson.toJson(subordinateTaskService.findAllSubordinate("1510"));
    }
}
