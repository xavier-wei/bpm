package tw.gov.pcc.repository.impl;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tw.gov.pcc.db.Query;
import tw.gov.pcc.db.SqlExecutor;
import tw.gov.pcc.repository.custom.BpmIsmsL414RepositoryCustom;
import tw.gov.pcc.service.dto.BpmFormQueryDto;
import tw.gov.pcc.service.dto.BpmIsmsL414DTO;
import tw.gov.pcc.web.rest.process.ProcessL414Resource;

import java.util.List;


public class BpmIsmsL414RepositoryImpl implements BpmIsmsL414RepositoryCustom {
    private static final Logger log = LoggerFactory.getLogger(BpmIsmsL414RepositoryImpl.class);
    private final SqlExecutor sqlExecutor;

    public BpmIsmsL414RepositoryImpl(SqlExecutor sqlExecutor) {
        this.sqlExecutor = sqlExecutor;
    }

    @Override
    public BpmIsmsL414DTO findByBpmIsmsL414(BpmFormQueryDto dto, String processId) {
        Query query = Query
            .builder()
            .append(" SELECT * FROM BPM_ISMS_L414 WHERE PROCESS_INSTANCE_ID = :processId ",processId)
            .appendWhen(StringUtils.isNotBlank(dto.getFormId())," AND substring(FORM_ID,1,4) = :formId",dto.getFormId())
            .appendWhen(StringUtils.isNotBlank(dto.getProcessInstanceStatus()), "AND PROCESS_INSTANCE_STATUS = :processInstanceStatus ", dto.getProcessInstanceStatus())
            .appendWhen(dto.getDateStart() != null, " AND APPLY_DATE BETWEEN :dateStart ",  dto.getDateStart())
            .appendWhen(dto.getDateEnd() != null, " AND :dateEnd ", dto.getDateEnd())
            .build();
        List<BpmIsmsL414DTO> list = sqlExecutor.queryForList(query, BpmIsmsL414DTO.class);

        if (list.isEmpty()) {
            return null;
        }

        return list.get(0);
    }


}
