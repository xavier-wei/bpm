package tw.gov.pcc.service;

import org.springframework.stereotype.Service;
import tw.gov.pcc.domain.User;
import tw.gov.pcc.domain.entity.BpmSignStatus;
import tw.gov.pcc.repository.BpmSignStatusRepository;
import tw.gov.pcc.service.dto.BpmIsmsAdditionalDTO;
import tw.gov.pcc.service.dto.BpmSignStatusDTO;
import tw.gov.pcc.service.dto.TaskDTO;
import tw.gov.pcc.service.mapper.BpmSignStatusMapper;

import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Service
public class BpmSignStatusService {

    private final BpmSignStatusRepository bpmSignStatusRepository;
    private final BpmSignStatusMapper bpmSignStatusMapper;

    private final HttpSession session;

    public BpmSignStatusService(BpmSignStatusRepository bpmSignStatusRepository, BpmSignStatusMapper bpmSignStatusMapper, HttpSession session) {
        this.bpmSignStatusRepository = bpmSignStatusRepository;
        this.bpmSignStatusMapper = bpmSignStatusMapper;
        this.session = session;
    }

    public List<BpmSignStatus> updateSignStatus(String processInstanceId) {
        return bpmSignStatusRepository.findByProcessInstanceId(processInstanceId);
    }

    public void saveBpmSignStatus(BpmSignStatus bpmSignStatus) {


        bpmSignStatusRepository.save(bpmSignStatus);
    }

    public void saveBpmSignStatus(String formId, String processInstanceId, TaskDTO taskDTO, String signerId, String signer, String signUnit) {
        BpmSignStatusDTO bpmSignStatusDTO = new BpmSignStatusDTO();
        bpmSignStatusDTO.setFormId(formId);
        bpmSignStatusDTO.setProcessInstanceId(processInstanceId);
        bpmSignStatusDTO.setTaskId(taskDTO.getTaskId());
        bpmSignStatusDTO.setTaskName(taskDTO.getTaskName());
        bpmSignStatusDTO.setSignerId(signerId);
        bpmSignStatusDTO.setSigner(signer);
        bpmSignStatusDTO.setSignUnit(signUnit);
        bpmSignStatusDTO.setSignResult("1");
        bpmSignStatusDTO.setSigningDatetime(Timestamp.from(Instant.now()));
        BpmSignStatus bpmSignStatus = bpmSignStatusMapper.toEntity(bpmSignStatusDTO);
        bpmSignStatusRepository.save(bpmSignStatus);
    }

    public void saveBpmSignStatus(BpmIsmsAdditionalDTO bpmIsmsAdditionalDTO) {
        User user = getUserInfo();
        BpmSignStatusDTO bpmSignStatusDTO = new BpmSignStatusDTO();
        bpmSignStatusDTO.setFormId(bpmIsmsAdditionalDTO.getMainFormId());
        bpmSignStatusDTO.setProcessInstanceId(bpmIsmsAdditionalDTO.getMainProcessInstanceId());
        bpmSignStatusDTO.setTaskId(bpmIsmsAdditionalDTO.getMainProcessTaskId());
        bpmSignStatusDTO.setTaskName(bpmIsmsAdditionalDTO.getTaskName());
        bpmSignStatusDTO.setSignerId(bpmIsmsAdditionalDTO.getRequesterId());
        bpmSignStatusDTO.setSigner(bpmIsmsAdditionalDTO.getRequester());
        bpmSignStatusDTO.setSignUnit(user.getDeptId());
        bpmSignStatusDTO.setSignResult("3");
        bpmSignStatusDTO.setOpinion("(加簽)");
        bpmSignStatusDTO.setDirections(bpmIsmsAdditionalDTO.getTaskName());
        bpmSignStatusDTO.setSigningDatetime(Timestamp.from(Instant.now()));
        BpmSignStatus bpmSignStatus = bpmSignStatusMapper.toEntity(bpmSignStatusDTO);
        bpmSignStatusRepository.save(bpmSignStatus);
    }

    private User getUserInfo() {
        return (User) session.getAttribute("userInfo");
    }
}
