package tw.gov.pcc.repository.impl;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tw.gov.pcc.db.Query;
import tw.gov.pcc.db.SqlExecutor;
import tw.gov.pcc.repository.custom.BpmIsmsL414RepositoryCustom;
import tw.gov.pcc.service.dto.BpmFormQueryDto;
import tw.gov.pcc.service.dto.BpmIsmsL414DTO;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;


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
            .append(" SELECT * FROM BPM_ISMS_L414 WHERE PROCESS_INSTANCE_ID = :processId ", processId)
            .appendWhen(StringUtils.isNotBlank(dto.getFormId()), " AND substring(FORM_ID,1,4) = :formId", dto.getFormId())
            .appendWhen(StringUtils.isNotBlank(dto.getProcessInstanceStatus()), "AND PROCESS_INSTANCE_STATUS = :processInstanceStatus ", dto.getProcessInstanceStatus())
            .appendWhen(dto.getDateStart() != null, " AND APPLY_DATE BETWEEN :dateStart ", dto.getDateStart())
            .appendWhen(dto.getDateEnd() != null, " AND :dateEnd ", dto.getDateEnd())
            .build();
        List<BpmIsmsL414DTO> list = sqlExecutor.queryForList(query, BpmIsmsL414DTO.class);

        if (list.isEmpty()) {
            return null;
        }

        return list.get(0);
    }

    @Override
    public List<BpmIsmsL414DTO> getNotify(BpmFormQueryDto dto) {

        Query query = Query
            .builder()
            .append(" SELECT bss.SIGNING_DATETIME, ")
            .append("        bss.SIGN_UNIT, ")
            .append("        bss.SIGNER, ")
            .append("        bss.SIGNING_DATETIME AS SIGNING_DATETIME, ")
            .append("        b414.* ")
            .append(" FROM BPM_ISMS_L414 b414 ")
            .append("          LEFT JOIN (Select FORM_ID, SIGN_UNIT, SIGNING_DATETIME, SIGNER ")
            .append("                     From (Select FORM_ID, ")
            .append("                                  SIGN_UNIT, ")
            .append("                                  SIGNING_DATETIME, ")
            .append("                                  SIGNER, ")
            .append("                                  ROW_NUMBER() Over (Partition By FORM_ID Order By SIGNING_DATETIME Desc) As Sort ")
            .append("                           From BPM_SIGN_STATUS) bss ")
            .append("                     Where bss.Sort = 1) bss ON b414.FORM_ID = bss.FORM_ID ")
            .append(" where 1 = 1 ")
            .appendWhen(StringUtils.isNotBlank(dto.getFormId()), "AND substring(b414.FORM_ID,1,4) = :formId ", dto.getFormId())
            .appendWhen(StringUtils.isNotBlank(dto.getProcessInstanceStatus()), "AND b414.PROCESS_INSTANCE_STATUS = :processInstanceStatus ", dto.getProcessInstanceStatus())
            .appendWhen(StringUtils.isNotBlank(dto.getUnit()), "AND b414.APP_UNIT = :signUnit ", dto.getUnit())
            .appendWhen(StringUtils.isNotBlank(dto.getAppName()), "AND b414.APP_NAME = :appName ", dto.getAppName())
            .appendWhen(dto.getDateStart() != null, " AND b414.APPLY_DATE BETWEEN :dateStart ", dto.getDateStart())
            .appendWhen(dto.getDateEnd() != null, " AND :dateEnd ", dto.getDateEnd())
            .build();
        List<BpmIsmsL414DTO> list = sqlExecutor.queryForList(query, BpmIsmsL414DTO.class);

        log.info("BpmIsmsL414RepositoryImpl.java - getNotify - 69 :: " + list.size());
        log.info("BpmIsmsL414RepositoryImpl.java - getNotify - 70 :: " + list);

        if (list.isEmpty()) {
            return null;
        }

        return list;
    }
}
