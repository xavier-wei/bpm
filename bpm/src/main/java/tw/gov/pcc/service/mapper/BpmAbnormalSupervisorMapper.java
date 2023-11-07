package tw.gov.pcc.service.mapper;

import org.mapstruct.Mapper;
import tw.gov.pcc.domain.BpmAbnormalSupervisor;
import tw.gov.pcc.service.dto.BpmAbnormalSupervisorDTO;

@Mapper(componentModel = "spring", uses = {})

public interface BpmAbnormalSupervisorMapper extends EntityMapper<BpmAbnormalSupervisorDTO, BpmAbnormalSupervisor> {
}
