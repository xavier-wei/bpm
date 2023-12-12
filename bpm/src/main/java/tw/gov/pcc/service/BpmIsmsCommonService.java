package tw.gov.pcc.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tw.gov.pcc.domain.MailInfo;
import tw.gov.pcc.domain.User;
import tw.gov.pcc.service.dto.BpmUploadFileDTO;
import tw.gov.pcc.service.dto.EndEventDTO;
import tw.gov.pcc.service.dto.TaskDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public interface BpmIsmsCommonService {

    UUID setVariables(HashMap<String, Object> variables, String form, User user);

    void saveBpm(UUID uuid, String processInstanceId, TaskDTO taskDTO, List<BpmUploadFileDTO> dto, List<MultipartFile> appendixFiles);

    Map<String, Object> getBpm(String formId);

    MailInfo endForm(EndEventDTO endEventDTO);

    void cancel(String processInstanceId);


    void removeHolder(UUID uuid);
}
