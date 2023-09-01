package tw.gov.pcc.service.mapper;

import org.mapstruct.Mapper;
import tw.gov.pcc.domain.entity.BpmIsmsAdditional;
import tw.gov.pcc.service.dto.BpmIsmsAdditionalDTO;

@Mapper(componentModel = "spring", uses = {})
public interface BpmIsmsAdditionalMapper extends EntityMapper<BpmIsmsAdditionalDTO, BpmIsmsAdditional>{
}
