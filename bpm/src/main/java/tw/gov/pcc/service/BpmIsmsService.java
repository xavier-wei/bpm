package tw.gov.pcc.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tw.gov.pcc.service.dto.BpmUploadFileDTO;
import tw.gov.pcc.service.dto.TaskDTO;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Service
public interface BpmIsmsService {

    void saveBpm(UUID uuid, String processInstanceId, TaskDTO taskDTO, List<BpmUploadFileDTO> dto, List<MultipartFile> appendixFiles);

    void saveBpmByPatch(String form);

    String saveBpmByPatch(String form, List<BpmUploadFileDTO> dto, List<MultipartFile> appendixFiles);
    UUID setVariables(HashMap<String, Object> variables, String form);

}
