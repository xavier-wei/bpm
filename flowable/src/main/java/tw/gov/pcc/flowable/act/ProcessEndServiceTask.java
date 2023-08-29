package tw.gov.pcc.flowable.act;

import com.google.gson.Gson;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import tw.gov.pcc.flowable.domain.ProcessEnum;
import tw.gov.pcc.flowable.service.dto.EndEventDTO;

public class ProcessEndServiceTask implements JavaDelegate {
    private final Logger log = LoggerFactory.getLogger(ProcessEndServiceTask.class);

    @Value("${bpm.token}")
    public static final String TOKEN = "QAZWSXEDCRffvYTHnujkljkfyffd14651456uhweuhuijwdijoichchuhqiheciqhoieh";
    @Override
    public void execute(DelegateExecution execution) {
        // get processDefinitionKey
        String processDefinitionKey = execution.getProcessDefinitionId().split(":")[0];
        EndEventDTO endEventDTO = new EndEventDTO(execution.getProcessInstanceId(),TOKEN, ProcessEnum.getFormNameByProcessKey(processDefinitionKey),"1");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Gson gson=new Gson();
        HttpEntity<String> requestEntity = new HttpEntity<>(gson.toJson(endEventDTO), headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> exchange = restTemplate.exchange("http://localhost:8080/bpm/api/process/receiveEndEvent", HttpMethod.POST, requestEntity, String.class);
        log.info("process end event response: {}",exchange.getBody());
    }
}