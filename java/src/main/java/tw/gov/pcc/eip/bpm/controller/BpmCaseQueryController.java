package tw.gov.pcc.eip.bpm.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import tw.gov.pcc.eip.framework.domain.UserBean;

import javax.servlet.http.HttpServletRequest;

@Controller
@Slf4j
public class BpmCaseQueryController {
    private final String PATH = "/Common_getPendingBpm.action";
    private final UserBean userData;
    private final RestTemplate restTemplate = new RestTemplate();
    public BpmCaseQueryController(UserBean userData) {
        this.userData = userData;
    }

    @GetMapping(PATH)
    @ResponseBody
    public Integer getPendingBpm(HttpServletRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(userData.getUserId(), headers);

        String path = request.getRequestURL().toString();
        String keyword = "/eip"+PATH;
        path = path.replace(keyword, "");
        System.out.println(path);
        ResponseEntity<Integer> exchange = restTemplate.exchange(path + "/bpm/api/process/queryProcessingTaskNumbers", HttpMethod.POST, requestEntity, Integer.class);
        System.out.println("exchange = " + exchange.getBody());
        if(exchange.getStatusCodeValue()==200)  {
            return exchange.getBody();
        }
        return 0;
    }
}
