package tw.gov.pcc.service;

import org.springframework.web.multipart.MultipartFile;
import tw.gov.pcc.service.dto.BpmUploadFileDTO;

import java.util.List;

public interface BpmIsmsPatchService {
    void saveBpmByPatch(String form);

    String saveBpmByPatch(String form, List<BpmUploadFileDTO> dto, List<MultipartFile> appendixFiles);

    void saveBpmByPatchToIsSubmit(String processInstanceId);

}
