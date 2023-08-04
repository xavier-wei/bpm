package tw.gov.pcc.service.mapper;

import org.mapstruct.*;
import tw.gov.pcc.domain.BpmIsmsL414;
import tw.gov.pcc.service.dto.BpmIsmsL414DTO;

/**
 * Mapper for the entity {@link BpmIsmsL414} and its DTO {@link BpmIsmsL414DTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BpmIsmsL414Mapper extends EntityMapper<BpmIsmsL414DTO, BpmIsmsL414> {}
