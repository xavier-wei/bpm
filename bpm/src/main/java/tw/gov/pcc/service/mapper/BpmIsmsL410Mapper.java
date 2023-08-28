package tw.gov.pcc.service.mapper;

import org.mapstruct.*;
import tw.gov.pcc.domain.BpmIsmsL410;
import tw.gov.pcc.service.dto.BpmIsmsL410DTO;

/**
 * Mapper for the entity {@link BpmIsmsL410} and its DTO {@link BpmIsmsL410DTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BpmIsmsL410Mapper extends EntityMapper<BpmIsmsL410DTO, BpmIsmsL410> {}
