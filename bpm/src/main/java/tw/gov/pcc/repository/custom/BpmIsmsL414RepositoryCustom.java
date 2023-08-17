package tw.gov.pcc.repository.custom;

import tw.gov.pcc.service.dto.BpmFormQueryDto;
import tw.gov.pcc.service.dto.BpmIsmsL414DTO;

public interface BpmIsmsL414RepositoryCustom {


    BpmIsmsL414DTO findByBpmIsmsL414(BpmFormQueryDto dto,String processId);

}
