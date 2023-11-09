package tw.gov.pcc.service.mapper;

import org.mapstruct.Mapper;
import tw.gov.pcc.domain.BpmSpecialSupervisor;
import tw.gov.pcc.service.dto.BpmSpecialSupervisorDTO;

@Mapper(componentModel = "spring", uses = {})

public interface BpmSpecialSupervisorMapper extends EntityMapper<BpmSpecialSupervisorDTO, BpmSpecialSupervisor> {
}
