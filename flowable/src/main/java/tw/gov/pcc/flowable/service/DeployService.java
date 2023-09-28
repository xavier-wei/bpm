//package tw.gov.pcc.flowable.service;
//
//import org.flowable.engine.RepositoryService;
//import org.springframework.stereotype.Service;
//
//@Service
//public class DeployService {
//
//    private final RepositoryService repositoryService;
//
//    public DeployService(RepositoryService repositoryService) {
//        this.repositoryService = repositoryService;
//    }
//
//
//    public void deploy() {
//        repositoryService.createDeployment()
//            .addClasspathResource("processes/leave.bpmn20.xml")
//            .deploy();
//    }
//}
