package tw.gov.pcc.service.mapper;

import org.mapstruct.*;
import tw.gov.pcc.domain.BpmL410ApplyManage;
import tw.gov.pcc.service.dto.BpmL410ApplyManageDTO;

/**
 * Mapper for the entity {@link BpmL410ApplyManage} and its DTO {@link BpmL410ApplyManageDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BpmL410ApplyManageMapper extends EntityMapper<BpmL410ApplyManageDTO, BpmL410ApplyManage> {}
