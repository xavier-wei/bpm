package tw.gov.pcc.web.rest.process;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
public class ProcessL414Resource {

    @RequestMapping("/process/L414/start")
    public String start(@RequestParam String id , HttpServletRequest req) {



        return null;
    }



}
