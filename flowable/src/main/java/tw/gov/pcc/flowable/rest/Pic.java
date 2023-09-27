package tw.gov.pcc.flowable.rest;

import com.google.gson.Gson;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.engine.ProcessEngine;
import org.flowable.engine.ProcessEngineConfiguration;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.runtime.Execution;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.image.ProcessDiagramGenerator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping("/process")
public class Pic {

    private final RuntimeService runtimeService;

    private final RepositoryService repositoryService;

    private final ProcessEngine processEngine;
    private final Gson gson = new Gson();

    public Pic(RuntimeService runtimeService, RepositoryService repositoryService, ProcessEngine processEngine) {
        this.runtimeService = runtimeService;
        this.repositoryService = repositoryService;
        this.processEngine = processEngine;
    }

    @GetMapping("/pic")
    public String showPicOld(HttpServletResponse resp, @RequestParam String processId) throws Exception {
        resp.setCharacterEncoding("UTF-8");
        ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(processId).singleResult();
        if (pi == null) {
            return null;
        }
        List<Execution> executions = runtimeService
                .createExecutionQuery()
                .processInstanceId(processId)
                .list();

        List<String> activityIds = new ArrayList<>();
        List<String> flows = new ArrayList<>();
        for (Execution exe : executions) {
            List<String> ids = runtimeService.getActiveActivityIds(exe.getId());
            activityIds.addAll(ids);
        }

        /**
         * 生成流程圖
         */
        BpmnModel bpmnModel = repositoryService.getBpmnModel(pi.getProcessDefinitionId());
        ProcessEngineConfiguration engconf = processEngine.getProcessEngineConfiguration();
        ProcessDiagramGenerator diagramGenerator = engconf.getProcessDiagramGenerator();
        byte[] buf = new byte[1024];
        int length;
        String res = null;

        try (InputStream in = diagramGenerator.generateDiagram(bpmnModel, "png", activityIds, flows, "宋體", "宋體", "宋體", engconf.getClassLoader(), 1.0, false)) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            while ((length = in.read(buf)) != -1) {
                byteArrayOutputStream.write(buf, 0, length);
            }
            res = "data:image/png;base64," + Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray());
            return res;

        }
    }
}