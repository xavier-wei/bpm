package tw.gov.pcc.service.mapper;

import org.mapstruct.*;
import tw.gov.pcc.domain.BpmFunction;
import tw.gov.pcc.service.dto.BpmFunctionDTO;

/**
 * Mapper for the entity {@link BpmFunction} and its DTO {@link BpmFunctionDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BpmFunctionMapper extends EntityMapper<BpmFunctionDTO, BpmFunction> {}
