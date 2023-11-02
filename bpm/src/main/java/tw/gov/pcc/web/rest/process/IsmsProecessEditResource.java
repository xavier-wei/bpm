package tw.gov.pcc.web.rest.process;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import tw.gov.pcc.domain.BpmIsmsServiceBeanNameEnum;
import tw.gov.pcc.domain.User;
import tw.gov.pcc.service.BpmIsmsService;
import tw.gov.pcc.service.dto.BpmUploadFileDTO;
import tw.gov.pcc.service.dto.EditVariablesDTO;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/process")
public class IsmsProecessEditResource {

    private static final Logger log = LoggerFactory.getLogger(IsmsProecessEditResource.class);
    private final ApplicationContext applicationContext;
    private final Gson gson = new Gson();

    @Value("${flowable.url}")
    private String flowableProcessUrl;
    private final RestTemplate restTemplate = new RestTemplate();
    private final HttpSession httpSession;

    public IsmsProecessEditResource(ApplicationContext applicationContext, HttpSession httpSession) {
        this.applicationContext = applicationContext;
        this.httpSession = httpSession;
    }

    // 如果編輯後SignerList會變化的打這支
    @PostMapping(path = "/edit/{key}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<String> editProcess(
        @PathVariable String key,
        @Valid @RequestPart("form") HashMap<String, String> form,
        @Valid @RequestPart(name = "fileDto", required = false) List<BpmUploadFileDTO> dto,
        @RequestPart(name = "appendixFiles", required = false) List<MultipartFile> appendixFiles
    ) {

        BpmIsmsService service = (BpmIsmsService) applicationContext.getBean(Objects.requireNonNull(BpmIsmsServiceBeanNameEnum.getServiceBeanNameByKey(key)));
        HashMap<String, Object> variables = new HashMap<>();
        variables = service.saveBpmByPatch(variables, form.get(key), dto, appendixFiles, getUserInfo());
        EditVariablesDTO editVariablesDTO = new EditVariablesDTO();
        editVariablesDTO.setVariable(variables);
        editVariablesDTO.setProcessInstanceId((String) variables.get("processInstanceId"));
        variables.remove("processInstanceId");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(gson.toJson(editVariablesDTO), headers);

        restTemplate.exchange(flowableProcessUrl + "/editVariables", HttpMethod.POST, requestEntity, String.class);


        return ResponseEntity.ok("");

    }

    private User getUserInfo() {
        return (User) httpSession.getAttribute("userInfo");
    }

}
