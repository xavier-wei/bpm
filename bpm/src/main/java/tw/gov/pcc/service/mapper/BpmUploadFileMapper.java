package tw.gov.pcc.service.mapper;

import org.mapstruct.*;
import tw.gov.pcc.domain.BpmUploadFile;
import tw.gov.pcc.service.dto.BpmUploadFileDTO;

/**
 * Mapper for the entity {@link BpmUploadFile} and its DTO {@link BpmUploadFileDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BpmUploadFileMapper extends EntityMapper<BpmUploadFileDTO, BpmUploadFile> {}
