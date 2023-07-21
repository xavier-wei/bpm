package tw.gov.pcc.service.mapper;

import org.mapstruct.*;
import tw.gov.pcc.domain.EipBpmIsmsL414;
import tw.gov.pcc.service.dto.EipBpmIsmsL414DTO;

/**
 * Mapper for the entity {@link EipBpmIsmsL414} and its DTO {@link EipBpmIsmsL414DTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EipBpmIsmsL414Mapper extends EntityMapper<EipBpmIsmsL414DTO, EipBpmIsmsL414> {}
