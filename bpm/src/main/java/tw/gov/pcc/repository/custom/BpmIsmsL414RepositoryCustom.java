package tw.gov.pcc.repository.custom;

import tw.gov.pcc.service.dto.BpmFormQueryDto;
import tw.gov.pcc.service.dto.BpmIsmsL414DTO;

import java.util.List;

public interface BpmIsmsL414RepositoryCustom {


    BpmIsmsL414DTO findByBpmIsmsL414(BpmFormQueryDto dto,String processId);
    List<BpmIsmsL414DTO> getNotify(BpmFormQueryDto dto);

}
