package tw.gov.pcc.flowable.rest;

import org.flowable.bpmn.model.BpmnModel;
import org.flowable.engine.*;
import org.flowable.engine.runtime.Execution;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.image.ProcessDiagramGenerator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

@RestController
public class Pic {

    private final RuntimeService runtimeService;

    private final RepositoryService repositoryService;

    private final ProcessEngine processEngine;

    public Pic(RuntimeService runtimeService, RepositoryService repositoryService, ProcessEngine processEngine) {
        this.runtimeService = runtimeService;
        this.repositoryService = repositoryService;
        this.processEngine = processEngine;
    }

    @GetMapping("/pic")
    public void showPic(HttpServletResponse resp, @RequestParam String processId) throws Exception {
        resp.setCharacterEncoding("UTF-8");
        ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(processId).singleResult();
        if (pi == null) {
            return;
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
        try (InputStream in = diagramGenerator.generateDiagram(bpmnModel, "png", activityIds, flows, "宋體","宋體", "宋體", engconf.getClassLoader(), 1.0, false); OutputStream out = resp.getOutputStream()) {
            while ((length = in.read(buf)) != -1) {
                out.write(buf, 0, length);
            }
        }
    }
}