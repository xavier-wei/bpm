package tw.gov.pcc.service.mapper;

import org.mapstruct.Mapper;
import tw.gov.pcc.domain.entity.BpmSignStatus;
import tw.gov.pcc.service.dto.BpmSignStatusDTO;

@Mapper(componentModel = "spring", uses = {})

public interface BpmSignStatusMapper extends EntityMapper<BpmSignStatusDTO, BpmSignStatus> {
}
