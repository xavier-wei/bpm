package tw.gov.pcc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tw.gov.pcc.domain.BpmSignerList;
import tw.gov.pcc.domain.entity.BpmIsmsAdditional;
import tw.gov.pcc.repository.custom.BpmIsmsAdditionalRepositoryCustom;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Repository
public interface BpmIsmsAdditionalRepository extends JpaRepository<BpmIsmsAdditional, String>, BpmIsmsAdditionalRepositoryCustom {

    @Query(value = " select top 1 * from BPM_ISMS_ADDITIONAL order by CREATE_TIME desc  ", nativeQuery = true)
    List<BpmIsmsAdditional> getMaxFormId();

    BpmIsmsAdditional findFirstByProcessInstanceId(String processInstanceId);

    BpmIsmsAdditional findByProcessInstanceId(String processInstanceId);

    @Query(value = " select * " +
        " from (select l414.FORM_ID, " +
        "             l414.PROCESS_INSTANCE_ID, " +
        "             l414.PROCESS_INSTANCE_STATUS, " +
        "             l414.APP_UNIT, " +
        "             l414.APPLY_DATE, " +
        "             l414.APP_NAME, " +
        "             l414.FIL_NAME, " +
        "             l414.FIL_EMPID, " +
        "             l414.APP_EMPID, " +
        "             l414.IS_SUBMIT, " +
        "             bss.SIGNING_DATETIME, " +
        "             bss.SIGN_UNIT ,       " +
        "             bss.signer, " +
        "             bss.DIRECTIONS " +
        "             from BPM_ISMS_L414 l414 " +
        "             LEFT JOIN (Select FORM_ID, SIGN_UNIT, SIGNING_DATETIME, SIGNER ,DIRECTIONS                                              " +
        "                        From (Select FORM_ID,                                                                             " +
        "                                     SIGN_UNIT,                                                                           " +
        "                                     SIGNING_DATETIME,                                                                    " +
        "                                     SIGNER,    " +
        "                                     DIRECTIONS, " +
        "                                     ROW_NUMBER() Over (Partition By FORM_ID Order By SIGNING_DATETIME Desc) As Sort      " +
        "                              From BPM_SIGN_STATUS) bss                                                                   " +
        "                        Where bss.Sort = 1) bss ON l414.FORM_ID = bss.FORM_ID                                             " +
        "      union " +
        "      select l410.form_id, " +
        "             l410.PROCESS_INSTANCE_ID, " +
        "             l410.PROCESS_INSTANCE_STATUS, " +
        "             l410.APP_UNIT, " +
        "             l410.APPLY_DATE, " +
        "             l410.APP_NAME, " +
        "             l410.FIL_NAME, " +
        "             l410.FIL_EMPID, " +
        "             l410.APP_EMPID, " +
        "             l410.IS_SUBMIT, " +
        "             bss.SIGNING_DATETIME," +
        "             bss.SIGN_UNIT,   " +
        "             bss.signer, " +
        "             bss.DIRECTIONS " +
        "      from BPM_ISMS_L410 l410" +
        "      LEFT JOIN (Select FORM_ID, SIGN_UNIT, SIGNING_DATETIME, SIGNER,DIRECTIONS                                            " +
        "                  From (Select FORM_ID,                                                                          " +
        "                               SIGN_UNIT,                                                                        " +
        "                               SIGNING_DATETIME,                                                                 " +
        "                               SIGNER, " +
        "                               DIRECTIONS, " +
        "                               ROW_NUMBER() Over (Partition By FORM_ID Order By SIGNING_DATETIME Desc) As Sort   " +
        "                        From BPM_SIGN_STATUS) bss                                                                " +
        "                  Where bss.Sort = 1) bss ON l410.FORM_ID = bss.FORM_ID  ) allData " +
        " where allData.PROCESS_INSTANCE_ID = :processInstanceId " +
        " AND (LEN(COALESCE(:formId,'')) =0 OR substring(allData.form_id,1,4) = :formId) " +
        " AND (LEN(COALESCE(:processInstanceStatus,'')) =0 OR allData.PROCESS_INSTANCE_STATUS = :processInstanceStatus) " +
        " AND (LEN(COALESCE(:unit,'')) =0 OR allData.APP_UNIT = :unit) " +
        " AND (LEN(COALESCE(:appName,'')) =0 OR allData.APP_NAME LIKE N'%' + :appName + '%' )  " +
        " AND (:dateStart IS NULL OR :dateStart <= substring(CONVERT(varchar, allData.APPLY_DATE, 120) ,1,10)) " +
        " AND (:dateEnd IS NULL OR substring(CONVERT(varchar, allData.APPLY_DATE, 120) ,1,10) <= :dateEnd) ", nativeQuery = true)
    List<Map<String, Object>> findAllByProcessInstanceId(
        @Param("processInstanceId") String processInstanceId,
        @Param("formId") String formId,
        @Param("processInstanceStatus") String processInstanceStatus,
        @Param("unit") String unit,
        @Param("appName") String appName,
        @Param("dateStart") LocalDate dateStart,
        @Param("dateEnd") LocalDate dateEnd
    );


    @Query(value = "  SELECT *  " +
        " FROM [view_cpape05m]  " +
        " where 1=1  " +
        "   and [PEORG] = '360000000G'  " +
        "   and PELEVDATE = ''  " +
        "   and PEUNIT != '600037'  " +
        "   and PEUNIT in (  " +
        "     SELECT [unit_id]  " + //使用職位ID，取得單位ID
        "     FROM [view_oup_unit]  " +
        "     where uhead_pos_id in (  " +
        "         SELECT [posid]  " + //使用員工編號，取得職位ID
        "         FROM [position] P  " +
        "                  left join [view_cpape05m] C on P.id = C.PEIDNO  " +
        "         where C.PECARD =  :pecard " +
        "         UNION ALL  " +
        "         SELECT [posid] FROM [position]  " +
        "         WHERE [fid] in (  " +
        "             SELECT [posid]  " +
        "             FROM [position] P  " +
        "                      left join [view_cpape05m] C on P.id = C.PEIDNO  " +
        "             where C.PECARD = :pecard  " +
        "         )  " +
        "         UNION ALL  " +
        "         SELECT [posid] FROM [position]  " +
        "         WHERE [fid] in (  " +
        "             SELECT [posid] FROM [position]  " +
        "             WHERE [fid] in (  " +
        "                 SELECT [posid]  " +
        "                 FROM [position] P  " +
        "                          left join [view_cpape05m] C on P.id = C.PEIDNO  " +
        "                 where C.PECARD = :pecard  " +
        "             )  " +
        "         )  " +
        "     )  " +
        " )  " +
        " order by PENAME     ", nativeQuery = true)
    List<Map<String, Object>> peunitOptions(@Param("pecard") String pecard);


    @Query(value = "  SELECT *  " +
        " FROM [view_cpape05m_forTest]  " +
        " where 1=1  " +
        "   and [PEORG] = '360000000G'  " +
        "   and PELEVDATE = ''  " +
        "   and PEUNIT != '600037'  " +
        "   and PEUNIT in (  " +
        "     SELECT [unit_id]  " + //使用職位ID，取得單位ID
        "     FROM [view_oup_unit_forTest]  " +
        "     where uhead_pos_id in (  " +
        "         SELECT [posid]  " + //使用員工編號，取得職位ID
        "         FROM [position_forTest] P  " +
        "                  left join [view_cpape05m_forTest] C on P.id = C.PEIDNO  " +
        "         where C.PECARD =  :pecard " +
        "         UNION ALL  " +
        "         SELECT [posid] FROM [position_forTest]  " +
        "         WHERE [fid] in (  " +
        "             SELECT [posid]  " +
        "             FROM [position_forTest] P  " +
        "                      left join [view_cpape05m_forTest] C on P.id = C.PEIDNO  " +
        "             where C.PECARD = :pecard  " +
        "         )  " +
        "         UNION ALL  " +
        "         SELECT [posid] FROM [position_forTest]  " +
        "         WHERE [fid] in (  " +
        "             SELECT [posid] FROM [position_forTest]  " +
        "             WHERE [fid] in (  " +
        "                 SELECT [posid]  " +
        "                 FROM [position_forTest] P  " +
        "                          left join [view_cpape05m_forTest] C on P.id = C.PEIDNO  " +
        "                 where C.PECARD = :pecard  " +
        "             )  " +
        "         )  " +
        "     )  " +
        " )  " +
        " order by PENAME     ", nativeQuery = true)
    List<Map<String, Object>> peunitOptionsForTest(@Param("pecard") String pecard);

    @Query(value = " SELECT * FROM [eip].[dbo].[view_cpape05m] " +
        " WHERE 1=1 " +
        " AND [PEORG] = '360000000G' " +
        " and PELEVDATE = ''  " +
        " AND PEUNIT != '600037' " +
        " AND (LEN(COALESCE(:selectName,'')) =0 OR PENAME LIKE N'%' + :selectName + '%')  " +
        " AND (LEN(COALESCE(:selectUnit,'')) =0 OR PEUNIT = :selectUnit)  " +
        " AND (LEN(COALESCE(:selectTitle,'')) =0 OR title = :selectTitle) " +
        " ORDER BY PEUNIT, PETIT  ", nativeQuery = true)
    List<Map<String, Object>> signatureOptions(
        @Param("selectName") String selectName,
        @Param("selectUnit") String selectUnit,
        @Param("selectTitle") String selectTitle);

}
