package tw.gov.pcc.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import tw.gov.pcc.IntegrationTest;
import tw.gov.pcc.domain.EipBpmIsmsL414;
import tw.gov.pcc.repository.EipBpmIsmsL414Repository;
import tw.gov.pcc.service.dto.EipBpmIsmsL414DTO;
import tw.gov.pcc.service.mapper.EipBpmIsmsL414Mapper;

/**
 * Integration tests for the {@link EipBpmIsmsL414Resource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EipBpmIsmsL414ResourceIT {

    private static final String DEFAULT_PROCESS_INSTANCE_ID = "AAAAAAAAAA";
    private static final String UPDATED_PROCESS_INSTANCE_ID = "BBBBBBBBBB";

    private static final Instant DEFAULT_APPLY_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_APPLY_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_FIL_EMPID = "AAAAAAAAAA";
    private static final String UPDATED_FIL_EMPID = "BBBBBBBBBB";

    private static final String DEFAULT_FIL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIL_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FIL_UNIT = "AAAAAAAAAA";
    private static final String UPDATED_FIL_UNIT = "BBBBBBBBBB";

    private static final String DEFAULT_APP_EMPID = "AAAAAAAAAA";
    private static final String UPDATED_APP_EMPID = "BBBBBBBBBB";

    private static final String DEFAULT_APP_NAME = "AAAAAAAAAA";
    private static final String UPDATED_APP_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_APP_UNIT = "AAAAAAAAAA";
    private static final String UPDATED_APP_UNIT = "BBBBBBBBBB";

    private static final String DEFAULT_IS_SUBMIT = "A";
    private static final String UPDATED_IS_SUBMIT = "B";

    private static final String DEFAULT_IS_ENABLE = "A";
    private static final String UPDATED_IS_ENABLE = "B";

    private static final String DEFAULT_ENABLE_TIME = "A";
    private static final String UPDATED_ENABLE_TIME = "B";

    private static final String DEFAULT_OTHER_ENABLE_TIME = "AAAAAAAAAA";
    private static final String UPDATED_OTHER_ENABLE_TIME = "BBBBBBBBBB";

    private static final String DEFAULT_SELECTE_EDATE_TYPE = "A";
    private static final String UPDATED_SELECTE_EDATE_TYPE = "B";

    private static final Instant DEFAULT_SDATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_SDATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_EDATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_EDATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_OTHERE_EDATE = "AAAAAAAAAA";
    private static final String UPDATED_OTHERE_EDATE = "BBBBBBBBBB";

    private static final Instant DEFAULT_DEL_ENABLE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DEL_ENABLE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_SOURCE_IP = "AAAAAAAAAA";
    private static final String UPDATED_SOURCE_IP = "BBBBBBBBBB";

    private static final String DEFAULT_TARGET_IP = "AAAAAAAAAA";
    private static final String UPDATED_TARGET_IP = "BBBBBBBBBB";

    private static final String DEFAULT_PORT = "AAAAAAAAAA";
    private static final String UPDATED_PORT = "BBBBBBBBBB";

    private static final String DEFAULT_IS_TCP = "A";
    private static final String UPDATED_IS_TCP = "B";

    private static final String DEFAULT_IS_UDP = "A";
    private static final String UPDATED_IS_UDP = "B";

    private static final String DEFAULT_INSTRUCTIONS = "AAAAAAAAAA";
    private static final String UPDATED_INSTRUCTIONS = "BBBBBBBBBB";

    private static final String DEFAULT_AGREE_TYPE = "A";
    private static final String UPDATED_AGREE_TYPE = "B";

    private static final Instant DEFAULT_SCHEDULE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_SCHEDULE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_SETTING_REASON = "AAAAAAAAAA";
    private static final String UPDATED_SETTING_REASON = "BBBBBBBBBB";

    private static final String DEFAULT_IS_EXTERNAL_FIREWALL = "A";
    private static final String UPDATED_IS_EXTERNAL_FIREWALL = "B";

    private static final String DEFAULT_IS_INTERNAL_FIREWALL = "A";
    private static final String UPDATED_IS_INTERNAL_FIREWALL = "B";

    private static final String DEFAULT_FIREWALL_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_FIREWALL_CONTENT = "BBBBBBBBBB";

    private static final Instant DEFAULT_FINISH_DATETIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FINISH_DATETIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_PROCESS_INSTANCE_STATUS = "A";
    private static final String UPDATED_PROCESS_INSTANCE_STATUS = "B";

    private static final String DEFAULT_UPDATE_USER = "AAAAAAAAAA";
    private static final String UPDATED_UPDATE_USER = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPDATE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CREATE_USER = "AAAAAAAAAA";
    private static final String UPDATED_CREATE_USER = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/eip-bpm-isms-l-414-s";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{formId}";

    @Autowired
    private EipBpmIsmsL414Repository eipBpmIsmsL414Repository;

    @Autowired
    private EipBpmIsmsL414Mapper eipBpmIsmsL414Mapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEipBpmIsmsL414MockMvc;

    private EipBpmIsmsL414 eipBpmIsmsL414;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EipBpmIsmsL414 createEntity(EntityManager em) {
        EipBpmIsmsL414 eipBpmIsmsL414 = new EipBpmIsmsL414()
            .processInstanceId(DEFAULT_PROCESS_INSTANCE_ID)
            .applyDate(DEFAULT_APPLY_DATE)
            .filEmpid(DEFAULT_FIL_EMPID)
            .filName(DEFAULT_FIL_NAME)
            .filUnit(DEFAULT_FIL_UNIT)
            .appEmpid(DEFAULT_APP_EMPID)
            .appName(DEFAULT_APP_NAME)
            .appUnit(DEFAULT_APP_UNIT)
            .isSubmit(DEFAULT_IS_SUBMIT)
            .isEnable(DEFAULT_IS_ENABLE)
            .enableTime(DEFAULT_ENABLE_TIME)
            .otherEnableTime(DEFAULT_OTHER_ENABLE_TIME)
            .selecteEdateType(DEFAULT_SELECTE_EDATE_TYPE)
            .sdate(DEFAULT_SDATE)
            .edate(DEFAULT_EDATE)
            .othereEdate(DEFAULT_OTHERE_EDATE)
            .delEnableDate(DEFAULT_DEL_ENABLE_DATE)
            .sourceIp(DEFAULT_SOURCE_IP)
            .targetIp(DEFAULT_TARGET_IP)
            .port(DEFAULT_PORT)
            .isTcp(DEFAULT_IS_TCP)
            .isUdp(DEFAULT_IS_UDP)
            .instructions(DEFAULT_INSTRUCTIONS)
            .agreeType(DEFAULT_AGREE_TYPE)
            .scheduleDate(DEFAULT_SCHEDULE_DATE)
            .settingReason(DEFAULT_SETTING_REASON)
            .isExternalFirewall(DEFAULT_IS_EXTERNAL_FIREWALL)
            .isInternalFirewall(DEFAULT_IS_INTERNAL_FIREWALL)
            .firewallContent(DEFAULT_FIREWALL_CONTENT)
            .finishDatetime(DEFAULT_FINISH_DATETIME)
            .processInstanceStatus(DEFAULT_PROCESS_INSTANCE_STATUS)
            .updateUser(DEFAULT_UPDATE_USER)
            .updateTime(DEFAULT_UPDATE_TIME)
            .createUser(DEFAULT_CREATE_USER)
            .createTime(DEFAULT_CREATE_TIME);
        return eipBpmIsmsL414;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EipBpmIsmsL414 createUpdatedEntity(EntityManager em) {
        EipBpmIsmsL414 eipBpmIsmsL414 = new EipBpmIsmsL414()
            .processInstanceId(UPDATED_PROCESS_INSTANCE_ID)
            .applyDate(UPDATED_APPLY_DATE)
            .filEmpid(UPDATED_FIL_EMPID)
            .filName(UPDATED_FIL_NAME)
            .filUnit(UPDATED_FIL_UNIT)
            .appEmpid(UPDATED_APP_EMPID)
            .appName(UPDATED_APP_NAME)
            .appUnit(UPDATED_APP_UNIT)
            .isSubmit(UPDATED_IS_SUBMIT)
            .isEnable(UPDATED_IS_ENABLE)
            .enableTime(UPDATED_ENABLE_TIME)
            .otherEnableTime(UPDATED_OTHER_ENABLE_TIME)
            .selecteEdateType(UPDATED_SELECTE_EDATE_TYPE)
            .sdate(UPDATED_SDATE)
            .edate(UPDATED_EDATE)
            .othereEdate(UPDATED_OTHERE_EDATE)
            .delEnableDate(UPDATED_DEL_ENABLE_DATE)
            .sourceIp(UPDATED_SOURCE_IP)
            .targetIp(UPDATED_TARGET_IP)
            .port(UPDATED_PORT)
            .isTcp(UPDATED_IS_TCP)
            .isUdp(UPDATED_IS_UDP)
            .instructions(UPDATED_INSTRUCTIONS)
            .agreeType(UPDATED_AGREE_TYPE)
            .scheduleDate(UPDATED_SCHEDULE_DATE)
            .settingReason(UPDATED_SETTING_REASON)
            .isExternalFirewall(UPDATED_IS_EXTERNAL_FIREWALL)
            .isInternalFirewall(UPDATED_IS_INTERNAL_FIREWALL)
            .firewallContent(UPDATED_FIREWALL_CONTENT)
            .finishDatetime(UPDATED_FINISH_DATETIME)
            .processInstanceStatus(UPDATED_PROCESS_INSTANCE_STATUS)
            .updateUser(UPDATED_UPDATE_USER)
            .updateTime(UPDATED_UPDATE_TIME)
            .createUser(UPDATED_CREATE_USER)
            .createTime(UPDATED_CREATE_TIME);
        return eipBpmIsmsL414;
    }

    @BeforeEach
    public void initTest() {
        eipBpmIsmsL414 = createEntity(em);
    }

    @Test
    @Transactional
    void createEipBpmIsmsL414() throws Exception {
        int databaseSizeBeforeCreate = eipBpmIsmsL414Repository.findAll().size();
        // Create the EipBpmIsmsL414
        EipBpmIsmsL414DTO eipBpmIsmsL414DTO = eipBpmIsmsL414Mapper.toDto(eipBpmIsmsL414);
        restEipBpmIsmsL414MockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eipBpmIsmsL414DTO))
            )
            .andExpect(status().isCreated());

        // Validate the EipBpmIsmsL414 in the database
        List<EipBpmIsmsL414> eipBpmIsmsL414List = eipBpmIsmsL414Repository.findAll();
        assertThat(eipBpmIsmsL414List).hasSize(databaseSizeBeforeCreate + 1);
        EipBpmIsmsL414 testEipBpmIsmsL414 = eipBpmIsmsL414List.get(eipBpmIsmsL414List.size() - 1);
        assertThat(testEipBpmIsmsL414.getProcessInstanceId()).isEqualTo(DEFAULT_PROCESS_INSTANCE_ID);
        assertThat(testEipBpmIsmsL414.getApplyDate()).isEqualTo(DEFAULT_APPLY_DATE);
        assertThat(testEipBpmIsmsL414.getFilEmpid()).isEqualTo(DEFAULT_FIL_EMPID);
        assertThat(testEipBpmIsmsL414.getFilName()).isEqualTo(DEFAULT_FIL_NAME);
        assertThat(testEipBpmIsmsL414.getFilUnit()).isEqualTo(DEFAULT_FIL_UNIT);
        assertThat(testEipBpmIsmsL414.getAppEmpid()).isEqualTo(DEFAULT_APP_EMPID);
        assertThat(testEipBpmIsmsL414.getAppName()).isEqualTo(DEFAULT_APP_NAME);
        assertThat(testEipBpmIsmsL414.getAppUnit()).isEqualTo(DEFAULT_APP_UNIT);
        assertThat(testEipBpmIsmsL414.getIsSubmit()).isEqualTo(DEFAULT_IS_SUBMIT);
        assertThat(testEipBpmIsmsL414.getIsEnable()).isEqualTo(DEFAULT_IS_ENABLE);
        assertThat(testEipBpmIsmsL414.getEnableTime()).isEqualTo(DEFAULT_ENABLE_TIME);
        assertThat(testEipBpmIsmsL414.getOtherEnableTime()).isEqualTo(DEFAULT_OTHER_ENABLE_TIME);
        assertThat(testEipBpmIsmsL414.getSelecteEdateType()).isEqualTo(DEFAULT_SELECTE_EDATE_TYPE);
        assertThat(testEipBpmIsmsL414.getSdate()).isEqualTo(DEFAULT_SDATE);
        assertThat(testEipBpmIsmsL414.getEdate()).isEqualTo(DEFAULT_EDATE);
        assertThat(testEipBpmIsmsL414.getOthereEdate()).isEqualTo(DEFAULT_OTHERE_EDATE);
        assertThat(testEipBpmIsmsL414.getDelEnableDate()).isEqualTo(DEFAULT_DEL_ENABLE_DATE);
        assertThat(testEipBpmIsmsL414.getSourceIp()).isEqualTo(DEFAULT_SOURCE_IP);
        assertThat(testEipBpmIsmsL414.getTargetIp()).isEqualTo(DEFAULT_TARGET_IP);
        assertThat(testEipBpmIsmsL414.getPort()).isEqualTo(DEFAULT_PORT);
        assertThat(testEipBpmIsmsL414.getIsTcp()).isEqualTo(DEFAULT_IS_TCP);
        assertThat(testEipBpmIsmsL414.getIsUdp()).isEqualTo(DEFAULT_IS_UDP);
        assertThat(testEipBpmIsmsL414.getInstructions()).isEqualTo(DEFAULT_INSTRUCTIONS);
        assertThat(testEipBpmIsmsL414.getAgreeType()).isEqualTo(DEFAULT_AGREE_TYPE);
        assertThat(testEipBpmIsmsL414.getScheduleDate()).isEqualTo(DEFAULT_SCHEDULE_DATE);
        assertThat(testEipBpmIsmsL414.getSettingReason()).isEqualTo(DEFAULT_SETTING_REASON);
        assertThat(testEipBpmIsmsL414.getIsExternalFirewall()).isEqualTo(DEFAULT_IS_EXTERNAL_FIREWALL);
        assertThat(testEipBpmIsmsL414.getIsInternalFirewall()).isEqualTo(DEFAULT_IS_INTERNAL_FIREWALL);
        assertThat(testEipBpmIsmsL414.getFirewallContent()).isEqualTo(DEFAULT_FIREWALL_CONTENT);
        assertThat(testEipBpmIsmsL414.getFinishDatetime()).isEqualTo(DEFAULT_FINISH_DATETIME);
        assertThat(testEipBpmIsmsL414.getProcessInstanceStatus()).isEqualTo(DEFAULT_PROCESS_INSTANCE_STATUS);
        assertThat(testEipBpmIsmsL414.getUpdateUser()).isEqualTo(DEFAULT_UPDATE_USER);
        assertThat(testEipBpmIsmsL414.getUpdateTime()).isEqualTo(DEFAULT_UPDATE_TIME);
        assertThat(testEipBpmIsmsL414.getCreateUser()).isEqualTo(DEFAULT_CREATE_USER);
        assertThat(testEipBpmIsmsL414.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
    }

    @Test
    @Transactional
    void createEipBpmIsmsL414WithExistingId() throws Exception {
        // Create the EipBpmIsmsL414 with an existing ID
        eipBpmIsmsL414.setFormId("existing_id");
        EipBpmIsmsL414DTO eipBpmIsmsL414DTO = eipBpmIsmsL414Mapper.toDto(eipBpmIsmsL414);

        int databaseSizeBeforeCreate = eipBpmIsmsL414Repository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEipBpmIsmsL414MockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eipBpmIsmsL414DTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EipBpmIsmsL414 in the database
        List<EipBpmIsmsL414> eipBpmIsmsL414List = eipBpmIsmsL414Repository.findAll();
        assertThat(eipBpmIsmsL414List).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkProcessInstanceIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = eipBpmIsmsL414Repository.findAll().size();
        // set the field null
        eipBpmIsmsL414.setProcessInstanceId(null);

        // Create the EipBpmIsmsL414, which fails.
        EipBpmIsmsL414DTO eipBpmIsmsL414DTO = eipBpmIsmsL414Mapper.toDto(eipBpmIsmsL414);

        restEipBpmIsmsL414MockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eipBpmIsmsL414DTO))
            )
            .andExpect(status().isBadRequest());

        List<EipBpmIsmsL414> eipBpmIsmsL414List = eipBpmIsmsL414Repository.findAll();
        assertThat(eipBpmIsmsL414List).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkApplyDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = eipBpmIsmsL414Repository.findAll().size();
        // set the field null
        eipBpmIsmsL414.setApplyDate(null);

        // Create the EipBpmIsmsL414, which fails.
        EipBpmIsmsL414DTO eipBpmIsmsL414DTO = eipBpmIsmsL414Mapper.toDto(eipBpmIsmsL414);

        restEipBpmIsmsL414MockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eipBpmIsmsL414DTO))
            )
            .andExpect(status().isBadRequest());

        List<EipBpmIsmsL414> eipBpmIsmsL414List = eipBpmIsmsL414Repository.findAll();
        assertThat(eipBpmIsmsL414List).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFilEmpidIsRequired() throws Exception {
        int databaseSizeBeforeTest = eipBpmIsmsL414Repository.findAll().size();
        // set the field null
        eipBpmIsmsL414.setFilEmpid(null);

        // Create the EipBpmIsmsL414, which fails.
        EipBpmIsmsL414DTO eipBpmIsmsL414DTO = eipBpmIsmsL414Mapper.toDto(eipBpmIsmsL414);

        restEipBpmIsmsL414MockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eipBpmIsmsL414DTO))
            )
            .andExpect(status().isBadRequest());

        List<EipBpmIsmsL414> eipBpmIsmsL414List = eipBpmIsmsL414Repository.findAll();
        assertThat(eipBpmIsmsL414List).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFilNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = eipBpmIsmsL414Repository.findAll().size();
        // set the field null
        eipBpmIsmsL414.setFilName(null);

        // Create the EipBpmIsmsL414, which fails.
        EipBpmIsmsL414DTO eipBpmIsmsL414DTO = eipBpmIsmsL414Mapper.toDto(eipBpmIsmsL414);

        restEipBpmIsmsL414MockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eipBpmIsmsL414DTO))
            )
            .andExpect(status().isBadRequest());

        List<EipBpmIsmsL414> eipBpmIsmsL414List = eipBpmIsmsL414Repository.findAll();
        assertThat(eipBpmIsmsL414List).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFilUnitIsRequired() throws Exception {
        int databaseSizeBeforeTest = eipBpmIsmsL414Repository.findAll().size();
        // set the field null
        eipBpmIsmsL414.setFilUnit(null);

        // Create the EipBpmIsmsL414, which fails.
        EipBpmIsmsL414DTO eipBpmIsmsL414DTO = eipBpmIsmsL414Mapper.toDto(eipBpmIsmsL414);

        restEipBpmIsmsL414MockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eipBpmIsmsL414DTO))
            )
            .andExpect(status().isBadRequest());

        List<EipBpmIsmsL414> eipBpmIsmsL414List = eipBpmIsmsL414Repository.findAll();
        assertThat(eipBpmIsmsL414List).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAppEmpidIsRequired() throws Exception {
        int databaseSizeBeforeTest = eipBpmIsmsL414Repository.findAll().size();
        // set the field null
        eipBpmIsmsL414.setAppEmpid(null);

        // Create the EipBpmIsmsL414, which fails.
        EipBpmIsmsL414DTO eipBpmIsmsL414DTO = eipBpmIsmsL414Mapper.toDto(eipBpmIsmsL414);

        restEipBpmIsmsL414MockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eipBpmIsmsL414DTO))
            )
            .andExpect(status().isBadRequest());

        List<EipBpmIsmsL414> eipBpmIsmsL414List = eipBpmIsmsL414Repository.findAll();
        assertThat(eipBpmIsmsL414List).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAppNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = eipBpmIsmsL414Repository.findAll().size();
        // set the field null
        eipBpmIsmsL414.setAppName(null);

        // Create the EipBpmIsmsL414, which fails.
        EipBpmIsmsL414DTO eipBpmIsmsL414DTO = eipBpmIsmsL414Mapper.toDto(eipBpmIsmsL414);

        restEipBpmIsmsL414MockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eipBpmIsmsL414DTO))
            )
            .andExpect(status().isBadRequest());

        List<EipBpmIsmsL414> eipBpmIsmsL414List = eipBpmIsmsL414Repository.findAll();
        assertThat(eipBpmIsmsL414List).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAppUnitIsRequired() throws Exception {
        int databaseSizeBeforeTest = eipBpmIsmsL414Repository.findAll().size();
        // set the field null
        eipBpmIsmsL414.setAppUnit(null);

        // Create the EipBpmIsmsL414, which fails.
        EipBpmIsmsL414DTO eipBpmIsmsL414DTO = eipBpmIsmsL414Mapper.toDto(eipBpmIsmsL414);

        restEipBpmIsmsL414MockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eipBpmIsmsL414DTO))
            )
            .andExpect(status().isBadRequest());

        List<EipBpmIsmsL414> eipBpmIsmsL414List = eipBpmIsmsL414Repository.findAll();
        assertThat(eipBpmIsmsL414List).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIsSubmitIsRequired() throws Exception {
        int databaseSizeBeforeTest = eipBpmIsmsL414Repository.findAll().size();
        // set the field null
        eipBpmIsmsL414.setIsSubmit(null);

        // Create the EipBpmIsmsL414, which fails.
        EipBpmIsmsL414DTO eipBpmIsmsL414DTO = eipBpmIsmsL414Mapper.toDto(eipBpmIsmsL414);

        restEipBpmIsmsL414MockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eipBpmIsmsL414DTO))
            )
            .andExpect(status().isBadRequest());

        List<EipBpmIsmsL414> eipBpmIsmsL414List = eipBpmIsmsL414Repository.findAll();
        assertThat(eipBpmIsmsL414List).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIsEnableIsRequired() throws Exception {
        int databaseSizeBeforeTest = eipBpmIsmsL414Repository.findAll().size();
        // set the field null
        eipBpmIsmsL414.setIsEnable(null);

        // Create the EipBpmIsmsL414, which fails.
        EipBpmIsmsL414DTO eipBpmIsmsL414DTO = eipBpmIsmsL414Mapper.toDto(eipBpmIsmsL414);

        restEipBpmIsmsL414MockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eipBpmIsmsL414DTO))
            )
            .andExpect(status().isBadRequest());

        List<EipBpmIsmsL414> eipBpmIsmsL414List = eipBpmIsmsL414Repository.findAll();
        assertThat(eipBpmIsmsL414List).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEnableTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = eipBpmIsmsL414Repository.findAll().size();
        // set the field null
        eipBpmIsmsL414.setEnableTime(null);

        // Create the EipBpmIsmsL414, which fails.
        EipBpmIsmsL414DTO eipBpmIsmsL414DTO = eipBpmIsmsL414Mapper.toDto(eipBpmIsmsL414);

        restEipBpmIsmsL414MockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eipBpmIsmsL414DTO))
            )
            .andExpect(status().isBadRequest());

        List<EipBpmIsmsL414> eipBpmIsmsL414List = eipBpmIsmsL414Repository.findAll();
        assertThat(eipBpmIsmsL414List).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSelecteEdateTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = eipBpmIsmsL414Repository.findAll().size();
        // set the field null
        eipBpmIsmsL414.setSelecteEdateType(null);

        // Create the EipBpmIsmsL414, which fails.
        EipBpmIsmsL414DTO eipBpmIsmsL414DTO = eipBpmIsmsL414Mapper.toDto(eipBpmIsmsL414);

        restEipBpmIsmsL414MockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eipBpmIsmsL414DTO))
            )
            .andExpect(status().isBadRequest());

        List<EipBpmIsmsL414> eipBpmIsmsL414List = eipBpmIsmsL414Repository.findAll();
        assertThat(eipBpmIsmsL414List).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkProcessInstanceStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = eipBpmIsmsL414Repository.findAll().size();
        // set the field null
        eipBpmIsmsL414.setProcessInstanceStatus(null);

        // Create the EipBpmIsmsL414, which fails.
        EipBpmIsmsL414DTO eipBpmIsmsL414DTO = eipBpmIsmsL414Mapper.toDto(eipBpmIsmsL414);

        restEipBpmIsmsL414MockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eipBpmIsmsL414DTO))
            )
            .andExpect(status().isBadRequest());

        List<EipBpmIsmsL414> eipBpmIsmsL414List = eipBpmIsmsL414Repository.findAll();
        assertThat(eipBpmIsmsL414List).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreateUserIsRequired() throws Exception {
        int databaseSizeBeforeTest = eipBpmIsmsL414Repository.findAll().size();
        // set the field null
        eipBpmIsmsL414.setCreateUser(null);

        // Create the EipBpmIsmsL414, which fails.
        EipBpmIsmsL414DTO eipBpmIsmsL414DTO = eipBpmIsmsL414Mapper.toDto(eipBpmIsmsL414);

        restEipBpmIsmsL414MockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eipBpmIsmsL414DTO))
            )
            .andExpect(status().isBadRequest());

        List<EipBpmIsmsL414> eipBpmIsmsL414List = eipBpmIsmsL414Repository.findAll();
        assertThat(eipBpmIsmsL414List).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreateTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = eipBpmIsmsL414Repository.findAll().size();
        // set the field null
        eipBpmIsmsL414.setCreateTime(null);

        // Create the EipBpmIsmsL414, which fails.
        EipBpmIsmsL414DTO eipBpmIsmsL414DTO = eipBpmIsmsL414Mapper.toDto(eipBpmIsmsL414);

        restEipBpmIsmsL414MockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eipBpmIsmsL414DTO))
            )
            .andExpect(status().isBadRequest());

        List<EipBpmIsmsL414> eipBpmIsmsL414List = eipBpmIsmsL414Repository.findAll();
        assertThat(eipBpmIsmsL414List).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllEipBpmIsmsL414s() throws Exception {
        // Initialize the database
        eipBpmIsmsL414.setFormId(UUID.randomUUID().toString());
        eipBpmIsmsL414Repository.saveAndFlush(eipBpmIsmsL414);

        // Get all the eipBpmIsmsL414List
        restEipBpmIsmsL414MockMvc
            .perform(get(ENTITY_API_URL + "?sort=formId,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].formId").value(hasItem(eipBpmIsmsL414.getFormId())))
            .andExpect(jsonPath("$.[*].processInstanceId").value(hasItem(DEFAULT_PROCESS_INSTANCE_ID)))
            .andExpect(jsonPath("$.[*].applyDate").value(hasItem(DEFAULT_APPLY_DATE.toString())))
            .andExpect(jsonPath("$.[*].filEmpid").value(hasItem(DEFAULT_FIL_EMPID)))
            .andExpect(jsonPath("$.[*].filName").value(hasItem(DEFAULT_FIL_NAME)))
            .andExpect(jsonPath("$.[*].filUnit").value(hasItem(DEFAULT_FIL_UNIT)))
            .andExpect(jsonPath("$.[*].appEmpid").value(hasItem(DEFAULT_APP_EMPID)))
            .andExpect(jsonPath("$.[*].appName").value(hasItem(DEFAULT_APP_NAME)))
            .andExpect(jsonPath("$.[*].appUnit").value(hasItem(DEFAULT_APP_UNIT)))
            .andExpect(jsonPath("$.[*].isSubmit").value(hasItem(DEFAULT_IS_SUBMIT)))
            .andExpect(jsonPath("$.[*].isEnable").value(hasItem(DEFAULT_IS_ENABLE)))
            .andExpect(jsonPath("$.[*].enableTime").value(hasItem(DEFAULT_ENABLE_TIME)))
            .andExpect(jsonPath("$.[*].otherEnableTime").value(hasItem(DEFAULT_OTHER_ENABLE_TIME)))
            .andExpect(jsonPath("$.[*].selecteEdateType").value(hasItem(DEFAULT_SELECTE_EDATE_TYPE)))
            .andExpect(jsonPath("$.[*].sdate").value(hasItem(DEFAULT_SDATE.toString())))
            .andExpect(jsonPath("$.[*].edate").value(hasItem(DEFAULT_EDATE.toString())))
            .andExpect(jsonPath("$.[*].othereEdate").value(hasItem(DEFAULT_OTHERE_EDATE)))
            .andExpect(jsonPath("$.[*].delEnableDate").value(hasItem(DEFAULT_DEL_ENABLE_DATE.toString())))
            .andExpect(jsonPath("$.[*].sourceIp").value(hasItem(DEFAULT_SOURCE_IP)))
            .andExpect(jsonPath("$.[*].targetIp").value(hasItem(DEFAULT_TARGET_IP)))
            .andExpect(jsonPath("$.[*].port").value(hasItem(DEFAULT_PORT)))
            .andExpect(jsonPath("$.[*].isTcp").value(hasItem(DEFAULT_IS_TCP)))
            .andExpect(jsonPath("$.[*].isUdp").value(hasItem(DEFAULT_IS_UDP)))
            .andExpect(jsonPath("$.[*].instructions").value(hasItem(DEFAULT_INSTRUCTIONS)))
            .andExpect(jsonPath("$.[*].agreeType").value(hasItem(DEFAULT_AGREE_TYPE)))
            .andExpect(jsonPath("$.[*].scheduleDate").value(hasItem(DEFAULT_SCHEDULE_DATE.toString())))
            .andExpect(jsonPath("$.[*].settingReason").value(hasItem(DEFAULT_SETTING_REASON)))
            .andExpect(jsonPath("$.[*].isExternalFirewall").value(hasItem(DEFAULT_IS_EXTERNAL_FIREWALL)))
            .andExpect(jsonPath("$.[*].isInternalFirewall").value(hasItem(DEFAULT_IS_INTERNAL_FIREWALL)))
            .andExpect(jsonPath("$.[*].firewallContent").value(hasItem(DEFAULT_FIREWALL_CONTENT)))
            .andExpect(jsonPath("$.[*].finishDatetime").value(hasItem(DEFAULT_FINISH_DATETIME.toString())))
            .andExpect(jsonPath("$.[*].processInstanceStatus").value(hasItem(DEFAULT_PROCESS_INSTANCE_STATUS)))
            .andExpect(jsonPath("$.[*].updateUser").value(hasItem(DEFAULT_UPDATE_USER)))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(DEFAULT_UPDATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].createUser").value(hasItem(DEFAULT_CREATE_USER)))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(DEFAULT_CREATE_TIME.toString())));
    }

    @Test
    @Transactional
    void getEipBpmIsmsL414() throws Exception {
        // Initialize the database
        eipBpmIsmsL414.setFormId(UUID.randomUUID().toString());
        eipBpmIsmsL414Repository.saveAndFlush(eipBpmIsmsL414);

        // Get the eipBpmIsmsL414
        restEipBpmIsmsL414MockMvc
            .perform(get(ENTITY_API_URL_ID, eipBpmIsmsL414.getFormId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.formId").value(eipBpmIsmsL414.getFormId()))
            .andExpect(jsonPath("$.processInstanceId").value(DEFAULT_PROCESS_INSTANCE_ID))
            .andExpect(jsonPath("$.applyDate").value(DEFAULT_APPLY_DATE.toString()))
            .andExpect(jsonPath("$.filEmpid").value(DEFAULT_FIL_EMPID))
            .andExpect(jsonPath("$.filName").value(DEFAULT_FIL_NAME))
            .andExpect(jsonPath("$.filUnit").value(DEFAULT_FIL_UNIT))
            .andExpect(jsonPath("$.appEmpid").value(DEFAULT_APP_EMPID))
            .andExpect(jsonPath("$.appName").value(DEFAULT_APP_NAME))
            .andExpect(jsonPath("$.appUnit").value(DEFAULT_APP_UNIT))
            .andExpect(jsonPath("$.isSubmit").value(DEFAULT_IS_SUBMIT))
            .andExpect(jsonPath("$.isEnable").value(DEFAULT_IS_ENABLE))
            .andExpect(jsonPath("$.enableTime").value(DEFAULT_ENABLE_TIME))
            .andExpect(jsonPath("$.otherEnableTime").value(DEFAULT_OTHER_ENABLE_TIME))
            .andExpect(jsonPath("$.selecteEdateType").value(DEFAULT_SELECTE_EDATE_TYPE))
            .andExpect(jsonPath("$.sdate").value(DEFAULT_SDATE.toString()))
            .andExpect(jsonPath("$.edate").value(DEFAULT_EDATE.toString()))
            .andExpect(jsonPath("$.othereEdate").value(DEFAULT_OTHERE_EDATE))
            .andExpect(jsonPath("$.delEnableDate").value(DEFAULT_DEL_ENABLE_DATE.toString()))
            .andExpect(jsonPath("$.sourceIp").value(DEFAULT_SOURCE_IP))
            .andExpect(jsonPath("$.targetIp").value(DEFAULT_TARGET_IP))
            .andExpect(jsonPath("$.port").value(DEFAULT_PORT))
            .andExpect(jsonPath("$.isTcp").value(DEFAULT_IS_TCP))
            .andExpect(jsonPath("$.isUdp").value(DEFAULT_IS_UDP))
            .andExpect(jsonPath("$.instructions").value(DEFAULT_INSTRUCTIONS))
            .andExpect(jsonPath("$.agreeType").value(DEFAULT_AGREE_TYPE))
            .andExpect(jsonPath("$.scheduleDate").value(DEFAULT_SCHEDULE_DATE.toString()))
            .andExpect(jsonPath("$.settingReason").value(DEFAULT_SETTING_REASON))
            .andExpect(jsonPath("$.isExternalFirewall").value(DEFAULT_IS_EXTERNAL_FIREWALL))
            .andExpect(jsonPath("$.isInternalFirewall").value(DEFAULT_IS_INTERNAL_FIREWALL))
            .andExpect(jsonPath("$.firewallContent").value(DEFAULT_FIREWALL_CONTENT))
            .andExpect(jsonPath("$.finishDatetime").value(DEFAULT_FINISH_DATETIME.toString()))
            .andExpect(jsonPath("$.processInstanceStatus").value(DEFAULT_PROCESS_INSTANCE_STATUS))
            .andExpect(jsonPath("$.updateUser").value(DEFAULT_UPDATE_USER))
            .andExpect(jsonPath("$.updateTime").value(DEFAULT_UPDATE_TIME.toString()))
            .andExpect(jsonPath("$.createUser").value(DEFAULT_CREATE_USER))
            .andExpect(jsonPath("$.createTime").value(DEFAULT_CREATE_TIME.toString()));
    }

    @Test
    @Transactional
    void getNonExistingEipBpmIsmsL414() throws Exception {
        // Get the eipBpmIsmsL414
        restEipBpmIsmsL414MockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewEipBpmIsmsL414() throws Exception {
        // Initialize the database
        eipBpmIsmsL414.setFormId(UUID.randomUUID().toString());
        eipBpmIsmsL414Repository.saveAndFlush(eipBpmIsmsL414);

        int databaseSizeBeforeUpdate = eipBpmIsmsL414Repository.findAll().size();

        // Update the eipBpmIsmsL414
        EipBpmIsmsL414 updatedEipBpmIsmsL414 = eipBpmIsmsL414Repository.findById(eipBpmIsmsL414.getFormId()).get();
        // Disconnect from session so that the updates on updatedEipBpmIsmsL414 are not directly saved in db
        em.detach(updatedEipBpmIsmsL414);
        updatedEipBpmIsmsL414
            .processInstanceId(UPDATED_PROCESS_INSTANCE_ID)
            .applyDate(UPDATED_APPLY_DATE)
            .filEmpid(UPDATED_FIL_EMPID)
            .filName(UPDATED_FIL_NAME)
            .filUnit(UPDATED_FIL_UNIT)
            .appEmpid(UPDATED_APP_EMPID)
            .appName(UPDATED_APP_NAME)
            .appUnit(UPDATED_APP_UNIT)
            .isSubmit(UPDATED_IS_SUBMIT)
            .isEnable(UPDATED_IS_ENABLE)
            .enableTime(UPDATED_ENABLE_TIME)
            .otherEnableTime(UPDATED_OTHER_ENABLE_TIME)
            .selecteEdateType(UPDATED_SELECTE_EDATE_TYPE)
            .sdate(UPDATED_SDATE)
            .edate(UPDATED_EDATE)
            .othereEdate(UPDATED_OTHERE_EDATE)
            .delEnableDate(UPDATED_DEL_ENABLE_DATE)
            .sourceIp(UPDATED_SOURCE_IP)
            .targetIp(UPDATED_TARGET_IP)
            .port(UPDATED_PORT)
            .isTcp(UPDATED_IS_TCP)
            .isUdp(UPDATED_IS_UDP)
            .instructions(UPDATED_INSTRUCTIONS)
            .agreeType(UPDATED_AGREE_TYPE)
            .scheduleDate(UPDATED_SCHEDULE_DATE)
            .settingReason(UPDATED_SETTING_REASON)
            .isExternalFirewall(UPDATED_IS_EXTERNAL_FIREWALL)
            .isInternalFirewall(UPDATED_IS_INTERNAL_FIREWALL)
            .firewallContent(UPDATED_FIREWALL_CONTENT)
            .finishDatetime(UPDATED_FINISH_DATETIME)
            .processInstanceStatus(UPDATED_PROCESS_INSTANCE_STATUS)
            .updateUser(UPDATED_UPDATE_USER)
            .updateTime(UPDATED_UPDATE_TIME)
            .createUser(UPDATED_CREATE_USER)
            .createTime(UPDATED_CREATE_TIME);
        EipBpmIsmsL414DTO eipBpmIsmsL414DTO = eipBpmIsmsL414Mapper.toDto(updatedEipBpmIsmsL414);

        restEipBpmIsmsL414MockMvc
            .perform(
                put(ENTITY_API_URL_ID, eipBpmIsmsL414DTO.getFormId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(eipBpmIsmsL414DTO))
            )
            .andExpect(status().isOk());

        // Validate the EipBpmIsmsL414 in the database
        List<EipBpmIsmsL414> eipBpmIsmsL414List = eipBpmIsmsL414Repository.findAll();
        assertThat(eipBpmIsmsL414List).hasSize(databaseSizeBeforeUpdate);
        EipBpmIsmsL414 testEipBpmIsmsL414 = eipBpmIsmsL414List.get(eipBpmIsmsL414List.size() - 1);
        assertThat(testEipBpmIsmsL414.getProcessInstanceId()).isEqualTo(UPDATED_PROCESS_INSTANCE_ID);
        assertThat(testEipBpmIsmsL414.getApplyDate()).isEqualTo(UPDATED_APPLY_DATE);
        assertThat(testEipBpmIsmsL414.getFilEmpid()).isEqualTo(UPDATED_FIL_EMPID);
        assertThat(testEipBpmIsmsL414.getFilName()).isEqualTo(UPDATED_FIL_NAME);
        assertThat(testEipBpmIsmsL414.getFilUnit()).isEqualTo(UPDATED_FIL_UNIT);
        assertThat(testEipBpmIsmsL414.getAppEmpid()).isEqualTo(UPDATED_APP_EMPID);
        assertThat(testEipBpmIsmsL414.getAppName()).isEqualTo(UPDATED_APP_NAME);
        assertThat(testEipBpmIsmsL414.getAppUnit()).isEqualTo(UPDATED_APP_UNIT);
        assertThat(testEipBpmIsmsL414.getIsSubmit()).isEqualTo(UPDATED_IS_SUBMIT);
        assertThat(testEipBpmIsmsL414.getIsEnable()).isEqualTo(UPDATED_IS_ENABLE);
        assertThat(testEipBpmIsmsL414.getEnableTime()).isEqualTo(UPDATED_ENABLE_TIME);
        assertThat(testEipBpmIsmsL414.getOtherEnableTime()).isEqualTo(UPDATED_OTHER_ENABLE_TIME);
        assertThat(testEipBpmIsmsL414.getSelecteEdateType()).isEqualTo(UPDATED_SELECTE_EDATE_TYPE);
        assertThat(testEipBpmIsmsL414.getSdate()).isEqualTo(UPDATED_SDATE);
        assertThat(testEipBpmIsmsL414.getEdate()).isEqualTo(UPDATED_EDATE);
        assertThat(testEipBpmIsmsL414.getOthereEdate()).isEqualTo(UPDATED_OTHERE_EDATE);
        assertThat(testEipBpmIsmsL414.getDelEnableDate()).isEqualTo(UPDATED_DEL_ENABLE_DATE);
        assertThat(testEipBpmIsmsL414.getSourceIp()).isEqualTo(UPDATED_SOURCE_IP);
        assertThat(testEipBpmIsmsL414.getTargetIp()).isEqualTo(UPDATED_TARGET_IP);
        assertThat(testEipBpmIsmsL414.getPort()).isEqualTo(UPDATED_PORT);
        assertThat(testEipBpmIsmsL414.getIsTcp()).isEqualTo(UPDATED_IS_TCP);
        assertThat(testEipBpmIsmsL414.getIsUdp()).isEqualTo(UPDATED_IS_UDP);
        assertThat(testEipBpmIsmsL414.getInstructions()).isEqualTo(UPDATED_INSTRUCTIONS);
        assertThat(testEipBpmIsmsL414.getAgreeType()).isEqualTo(UPDATED_AGREE_TYPE);
        assertThat(testEipBpmIsmsL414.getScheduleDate()).isEqualTo(UPDATED_SCHEDULE_DATE);
        assertThat(testEipBpmIsmsL414.getSettingReason()).isEqualTo(UPDATED_SETTING_REASON);
        assertThat(testEipBpmIsmsL414.getIsExternalFirewall()).isEqualTo(UPDATED_IS_EXTERNAL_FIREWALL);
        assertThat(testEipBpmIsmsL414.getIsInternalFirewall()).isEqualTo(UPDATED_IS_INTERNAL_FIREWALL);
        assertThat(testEipBpmIsmsL414.getFirewallContent()).isEqualTo(UPDATED_FIREWALL_CONTENT);
        assertThat(testEipBpmIsmsL414.getFinishDatetime()).isEqualTo(UPDATED_FINISH_DATETIME);
        assertThat(testEipBpmIsmsL414.getProcessInstanceStatus()).isEqualTo(UPDATED_PROCESS_INSTANCE_STATUS);
        assertThat(testEipBpmIsmsL414.getUpdateUser()).isEqualTo(UPDATED_UPDATE_USER);
        assertThat(testEipBpmIsmsL414.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
        assertThat(testEipBpmIsmsL414.getCreateUser()).isEqualTo(UPDATED_CREATE_USER);
        assertThat(testEipBpmIsmsL414.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
    }

    @Test
    @Transactional
    void putNonExistingEipBpmIsmsL414() throws Exception {
        int databaseSizeBeforeUpdate = eipBpmIsmsL414Repository.findAll().size();
        eipBpmIsmsL414.setFormId(UUID.randomUUID().toString());

        // Create the EipBpmIsmsL414
        EipBpmIsmsL414DTO eipBpmIsmsL414DTO = eipBpmIsmsL414Mapper.toDto(eipBpmIsmsL414);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEipBpmIsmsL414MockMvc
            .perform(
                put(ENTITY_API_URL_ID, eipBpmIsmsL414DTO.getFormId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(eipBpmIsmsL414DTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EipBpmIsmsL414 in the database
        List<EipBpmIsmsL414> eipBpmIsmsL414List = eipBpmIsmsL414Repository.findAll();
        assertThat(eipBpmIsmsL414List).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEipBpmIsmsL414() throws Exception {
        int databaseSizeBeforeUpdate = eipBpmIsmsL414Repository.findAll().size();
        eipBpmIsmsL414.setFormId(UUID.randomUUID().toString());

        // Create the EipBpmIsmsL414
        EipBpmIsmsL414DTO eipBpmIsmsL414DTO = eipBpmIsmsL414Mapper.toDto(eipBpmIsmsL414);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEipBpmIsmsL414MockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(eipBpmIsmsL414DTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EipBpmIsmsL414 in the database
        List<EipBpmIsmsL414> eipBpmIsmsL414List = eipBpmIsmsL414Repository.findAll();
        assertThat(eipBpmIsmsL414List).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEipBpmIsmsL414() throws Exception {
        int databaseSizeBeforeUpdate = eipBpmIsmsL414Repository.findAll().size();
        eipBpmIsmsL414.setFormId(UUID.randomUUID().toString());

        // Create the EipBpmIsmsL414
        EipBpmIsmsL414DTO eipBpmIsmsL414DTO = eipBpmIsmsL414Mapper.toDto(eipBpmIsmsL414);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEipBpmIsmsL414MockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eipBpmIsmsL414DTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EipBpmIsmsL414 in the database
        List<EipBpmIsmsL414> eipBpmIsmsL414List = eipBpmIsmsL414Repository.findAll();
        assertThat(eipBpmIsmsL414List).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEipBpmIsmsL414WithPatch() throws Exception {
        // Initialize the database
        eipBpmIsmsL414.setFormId(UUID.randomUUID().toString());
        eipBpmIsmsL414Repository.saveAndFlush(eipBpmIsmsL414);

        int databaseSizeBeforeUpdate = eipBpmIsmsL414Repository.findAll().size();

        // Update the eipBpmIsmsL414 using partial update
        EipBpmIsmsL414 partialUpdatedEipBpmIsmsL414 = new EipBpmIsmsL414();
        partialUpdatedEipBpmIsmsL414.setFormId(eipBpmIsmsL414.getFormId());

        partialUpdatedEipBpmIsmsL414
            .processInstanceId(UPDATED_PROCESS_INSTANCE_ID)
            .filEmpid(UPDATED_FIL_EMPID)
            .filName(UPDATED_FIL_NAME)
            .filUnit(UPDATED_FIL_UNIT)
            .appEmpid(UPDATED_APP_EMPID)
            .appName(UPDATED_APP_NAME)
            .appUnit(UPDATED_APP_UNIT)
            .isSubmit(UPDATED_IS_SUBMIT)
            .isEnable(UPDATED_IS_ENABLE)
            .enableTime(UPDATED_ENABLE_TIME)
            .otherEnableTime(UPDATED_OTHER_ENABLE_TIME)
            .othereEdate(UPDATED_OTHERE_EDATE)
            .delEnableDate(UPDATED_DEL_ENABLE_DATE)
            .sourceIp(UPDATED_SOURCE_IP)
            .port(UPDATED_PORT)
            .isTcp(UPDATED_IS_TCP)
            .isUdp(UPDATED_IS_UDP)
            .settingReason(UPDATED_SETTING_REASON)
            .isInternalFirewall(UPDATED_IS_INTERNAL_FIREWALL)
            .firewallContent(UPDATED_FIREWALL_CONTENT)
            .finishDatetime(UPDATED_FINISH_DATETIME)
            .processInstanceStatus(UPDATED_PROCESS_INSTANCE_STATUS)
            .updateUser(UPDATED_UPDATE_USER)
            .createTime(UPDATED_CREATE_TIME);

        restEipBpmIsmsL414MockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEipBpmIsmsL414.getFormId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEipBpmIsmsL414))
            )
            .andExpect(status().isOk());

        // Validate the EipBpmIsmsL414 in the database
        List<EipBpmIsmsL414> eipBpmIsmsL414List = eipBpmIsmsL414Repository.findAll();
        assertThat(eipBpmIsmsL414List).hasSize(databaseSizeBeforeUpdate);
        EipBpmIsmsL414 testEipBpmIsmsL414 = eipBpmIsmsL414List.get(eipBpmIsmsL414List.size() - 1);
        assertThat(testEipBpmIsmsL414.getProcessInstanceId()).isEqualTo(UPDATED_PROCESS_INSTANCE_ID);
        assertThat(testEipBpmIsmsL414.getApplyDate()).isEqualTo(DEFAULT_APPLY_DATE);
        assertThat(testEipBpmIsmsL414.getFilEmpid()).isEqualTo(UPDATED_FIL_EMPID);
        assertThat(testEipBpmIsmsL414.getFilName()).isEqualTo(UPDATED_FIL_NAME);
        assertThat(testEipBpmIsmsL414.getFilUnit()).isEqualTo(UPDATED_FIL_UNIT);
        assertThat(testEipBpmIsmsL414.getAppEmpid()).isEqualTo(UPDATED_APP_EMPID);
        assertThat(testEipBpmIsmsL414.getAppName()).isEqualTo(UPDATED_APP_NAME);
        assertThat(testEipBpmIsmsL414.getAppUnit()).isEqualTo(UPDATED_APP_UNIT);
        assertThat(testEipBpmIsmsL414.getIsSubmit()).isEqualTo(UPDATED_IS_SUBMIT);
        assertThat(testEipBpmIsmsL414.getIsEnable()).isEqualTo(UPDATED_IS_ENABLE);
        assertThat(testEipBpmIsmsL414.getEnableTime()).isEqualTo(UPDATED_ENABLE_TIME);
        assertThat(testEipBpmIsmsL414.getOtherEnableTime()).isEqualTo(UPDATED_OTHER_ENABLE_TIME);
        assertThat(testEipBpmIsmsL414.getSelecteEdateType()).isEqualTo(DEFAULT_SELECTE_EDATE_TYPE);
        assertThat(testEipBpmIsmsL414.getSdate()).isEqualTo(DEFAULT_SDATE);
        assertThat(testEipBpmIsmsL414.getEdate()).isEqualTo(DEFAULT_EDATE);
        assertThat(testEipBpmIsmsL414.getOthereEdate()).isEqualTo(UPDATED_OTHERE_EDATE);
        assertThat(testEipBpmIsmsL414.getDelEnableDate()).isEqualTo(UPDATED_DEL_ENABLE_DATE);
        assertThat(testEipBpmIsmsL414.getSourceIp()).isEqualTo(UPDATED_SOURCE_IP);
        assertThat(testEipBpmIsmsL414.getTargetIp()).isEqualTo(DEFAULT_TARGET_IP);
        assertThat(testEipBpmIsmsL414.getPort()).isEqualTo(UPDATED_PORT);
        assertThat(testEipBpmIsmsL414.getIsTcp()).isEqualTo(UPDATED_IS_TCP);
        assertThat(testEipBpmIsmsL414.getIsUdp()).isEqualTo(UPDATED_IS_UDP);
        assertThat(testEipBpmIsmsL414.getInstructions()).isEqualTo(DEFAULT_INSTRUCTIONS);
        assertThat(testEipBpmIsmsL414.getAgreeType()).isEqualTo(DEFAULT_AGREE_TYPE);
        assertThat(testEipBpmIsmsL414.getScheduleDate()).isEqualTo(DEFAULT_SCHEDULE_DATE);
        assertThat(testEipBpmIsmsL414.getSettingReason()).isEqualTo(UPDATED_SETTING_REASON);
        assertThat(testEipBpmIsmsL414.getIsExternalFirewall()).isEqualTo(DEFAULT_IS_EXTERNAL_FIREWALL);
        assertThat(testEipBpmIsmsL414.getIsInternalFirewall()).isEqualTo(UPDATED_IS_INTERNAL_FIREWALL);
        assertThat(testEipBpmIsmsL414.getFirewallContent()).isEqualTo(UPDATED_FIREWALL_CONTENT);
        assertThat(testEipBpmIsmsL414.getFinishDatetime()).isEqualTo(UPDATED_FINISH_DATETIME);
        assertThat(testEipBpmIsmsL414.getProcessInstanceStatus()).isEqualTo(UPDATED_PROCESS_INSTANCE_STATUS);
        assertThat(testEipBpmIsmsL414.getUpdateUser()).isEqualTo(UPDATED_UPDATE_USER);
        assertThat(testEipBpmIsmsL414.getUpdateTime()).isEqualTo(DEFAULT_UPDATE_TIME);
        assertThat(testEipBpmIsmsL414.getCreateUser()).isEqualTo(DEFAULT_CREATE_USER);
        assertThat(testEipBpmIsmsL414.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
    }

    @Test
    @Transactional
    void fullUpdateEipBpmIsmsL414WithPatch() throws Exception {
        // Initialize the database
        eipBpmIsmsL414.setFormId(UUID.randomUUID().toString());
        eipBpmIsmsL414Repository.saveAndFlush(eipBpmIsmsL414);

        int databaseSizeBeforeUpdate = eipBpmIsmsL414Repository.findAll().size();

        // Update the eipBpmIsmsL414 using partial update
        EipBpmIsmsL414 partialUpdatedEipBpmIsmsL414 = new EipBpmIsmsL414();
        partialUpdatedEipBpmIsmsL414.setFormId(eipBpmIsmsL414.getFormId());

        partialUpdatedEipBpmIsmsL414
            .processInstanceId(UPDATED_PROCESS_INSTANCE_ID)
            .applyDate(UPDATED_APPLY_DATE)
            .filEmpid(UPDATED_FIL_EMPID)
            .filName(UPDATED_FIL_NAME)
            .filUnit(UPDATED_FIL_UNIT)
            .appEmpid(UPDATED_APP_EMPID)
            .appName(UPDATED_APP_NAME)
            .appUnit(UPDATED_APP_UNIT)
            .isSubmit(UPDATED_IS_SUBMIT)
            .isEnable(UPDATED_IS_ENABLE)
            .enableTime(UPDATED_ENABLE_TIME)
            .otherEnableTime(UPDATED_OTHER_ENABLE_TIME)
            .selecteEdateType(UPDATED_SELECTE_EDATE_TYPE)
            .sdate(UPDATED_SDATE)
            .edate(UPDATED_EDATE)
            .othereEdate(UPDATED_OTHERE_EDATE)
            .delEnableDate(UPDATED_DEL_ENABLE_DATE)
            .sourceIp(UPDATED_SOURCE_IP)
            .targetIp(UPDATED_TARGET_IP)
            .port(UPDATED_PORT)
            .isTcp(UPDATED_IS_TCP)
            .isUdp(UPDATED_IS_UDP)
            .instructions(UPDATED_INSTRUCTIONS)
            .agreeType(UPDATED_AGREE_TYPE)
            .scheduleDate(UPDATED_SCHEDULE_DATE)
            .settingReason(UPDATED_SETTING_REASON)
            .isExternalFirewall(UPDATED_IS_EXTERNAL_FIREWALL)
            .isInternalFirewall(UPDATED_IS_INTERNAL_FIREWALL)
            .firewallContent(UPDATED_FIREWALL_CONTENT)
            .finishDatetime(UPDATED_FINISH_DATETIME)
            .processInstanceStatus(UPDATED_PROCESS_INSTANCE_STATUS)
            .updateUser(UPDATED_UPDATE_USER)
            .updateTime(UPDATED_UPDATE_TIME)
            .createUser(UPDATED_CREATE_USER)
            .createTime(UPDATED_CREATE_TIME);

        restEipBpmIsmsL414MockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEipBpmIsmsL414.getFormId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEipBpmIsmsL414))
            )
            .andExpect(status().isOk());

        // Validate the EipBpmIsmsL414 in the database
        List<EipBpmIsmsL414> eipBpmIsmsL414List = eipBpmIsmsL414Repository.findAll();
        assertThat(eipBpmIsmsL414List).hasSize(databaseSizeBeforeUpdate);
        EipBpmIsmsL414 testEipBpmIsmsL414 = eipBpmIsmsL414List.get(eipBpmIsmsL414List.size() - 1);
        assertThat(testEipBpmIsmsL414.getProcessInstanceId()).isEqualTo(UPDATED_PROCESS_INSTANCE_ID);
        assertThat(testEipBpmIsmsL414.getApplyDate()).isEqualTo(UPDATED_APPLY_DATE);
        assertThat(testEipBpmIsmsL414.getFilEmpid()).isEqualTo(UPDATED_FIL_EMPID);
        assertThat(testEipBpmIsmsL414.getFilName()).isEqualTo(UPDATED_FIL_NAME);
        assertThat(testEipBpmIsmsL414.getFilUnit()).isEqualTo(UPDATED_FIL_UNIT);
        assertThat(testEipBpmIsmsL414.getAppEmpid()).isEqualTo(UPDATED_APP_EMPID);
        assertThat(testEipBpmIsmsL414.getAppName()).isEqualTo(UPDATED_APP_NAME);
        assertThat(testEipBpmIsmsL414.getAppUnit()).isEqualTo(UPDATED_APP_UNIT);
        assertThat(testEipBpmIsmsL414.getIsSubmit()).isEqualTo(UPDATED_IS_SUBMIT);
        assertThat(testEipBpmIsmsL414.getIsEnable()).isEqualTo(UPDATED_IS_ENABLE);
        assertThat(testEipBpmIsmsL414.getEnableTime()).isEqualTo(UPDATED_ENABLE_TIME);
        assertThat(testEipBpmIsmsL414.getOtherEnableTime()).isEqualTo(UPDATED_OTHER_ENABLE_TIME);
        assertThat(testEipBpmIsmsL414.getSelecteEdateType()).isEqualTo(UPDATED_SELECTE_EDATE_TYPE);
        assertThat(testEipBpmIsmsL414.getSdate()).isEqualTo(UPDATED_SDATE);
        assertThat(testEipBpmIsmsL414.getEdate()).isEqualTo(UPDATED_EDATE);
        assertThat(testEipBpmIsmsL414.getOthereEdate()).isEqualTo(UPDATED_OTHERE_EDATE);
        assertThat(testEipBpmIsmsL414.getDelEnableDate()).isEqualTo(UPDATED_DEL_ENABLE_DATE);
        assertThat(testEipBpmIsmsL414.getSourceIp()).isEqualTo(UPDATED_SOURCE_IP);
        assertThat(testEipBpmIsmsL414.getTargetIp()).isEqualTo(UPDATED_TARGET_IP);
        assertThat(testEipBpmIsmsL414.getPort()).isEqualTo(UPDATED_PORT);
        assertThat(testEipBpmIsmsL414.getIsTcp()).isEqualTo(UPDATED_IS_TCP);
        assertThat(testEipBpmIsmsL414.getIsUdp()).isEqualTo(UPDATED_IS_UDP);
        assertThat(testEipBpmIsmsL414.getInstructions()).isEqualTo(UPDATED_INSTRUCTIONS);
        assertThat(testEipBpmIsmsL414.getAgreeType()).isEqualTo(UPDATED_AGREE_TYPE);
        assertThat(testEipBpmIsmsL414.getScheduleDate()).isEqualTo(UPDATED_SCHEDULE_DATE);
        assertThat(testEipBpmIsmsL414.getSettingReason()).isEqualTo(UPDATED_SETTING_REASON);
        assertThat(testEipBpmIsmsL414.getIsExternalFirewall()).isEqualTo(UPDATED_IS_EXTERNAL_FIREWALL);
        assertThat(testEipBpmIsmsL414.getIsInternalFirewall()).isEqualTo(UPDATED_IS_INTERNAL_FIREWALL);
        assertThat(testEipBpmIsmsL414.getFirewallContent()).isEqualTo(UPDATED_FIREWALL_CONTENT);
        assertThat(testEipBpmIsmsL414.getFinishDatetime()).isEqualTo(UPDATED_FINISH_DATETIME);
        assertThat(testEipBpmIsmsL414.getProcessInstanceStatus()).isEqualTo(UPDATED_PROCESS_INSTANCE_STATUS);
        assertThat(testEipBpmIsmsL414.getUpdateUser()).isEqualTo(UPDATED_UPDATE_USER);
        assertThat(testEipBpmIsmsL414.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
        assertThat(testEipBpmIsmsL414.getCreateUser()).isEqualTo(UPDATED_CREATE_USER);
        assertThat(testEipBpmIsmsL414.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
    }

    @Test
    @Transactional
    void patchNonExistingEipBpmIsmsL414() throws Exception {
        int databaseSizeBeforeUpdate = eipBpmIsmsL414Repository.findAll().size();
        eipBpmIsmsL414.setFormId(UUID.randomUUID().toString());

        // Create the EipBpmIsmsL414
        EipBpmIsmsL414DTO eipBpmIsmsL414DTO = eipBpmIsmsL414Mapper.toDto(eipBpmIsmsL414);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEipBpmIsmsL414MockMvc
            .perform(
                patch(ENTITY_API_URL_ID, eipBpmIsmsL414DTO.getFormId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(eipBpmIsmsL414DTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EipBpmIsmsL414 in the database
        List<EipBpmIsmsL414> eipBpmIsmsL414List = eipBpmIsmsL414Repository.findAll();
        assertThat(eipBpmIsmsL414List).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEipBpmIsmsL414() throws Exception {
        int databaseSizeBeforeUpdate = eipBpmIsmsL414Repository.findAll().size();
        eipBpmIsmsL414.setFormId(UUID.randomUUID().toString());

        // Create the EipBpmIsmsL414
        EipBpmIsmsL414DTO eipBpmIsmsL414DTO = eipBpmIsmsL414Mapper.toDto(eipBpmIsmsL414);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEipBpmIsmsL414MockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(eipBpmIsmsL414DTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EipBpmIsmsL414 in the database
        List<EipBpmIsmsL414> eipBpmIsmsL414List = eipBpmIsmsL414Repository.findAll();
        assertThat(eipBpmIsmsL414List).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEipBpmIsmsL414() throws Exception {
        int databaseSizeBeforeUpdate = eipBpmIsmsL414Repository.findAll().size();
        eipBpmIsmsL414.setFormId(UUID.randomUUID().toString());

        // Create the EipBpmIsmsL414
        EipBpmIsmsL414DTO eipBpmIsmsL414DTO = eipBpmIsmsL414Mapper.toDto(eipBpmIsmsL414);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEipBpmIsmsL414MockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(eipBpmIsmsL414DTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EipBpmIsmsL414 in the database
        List<EipBpmIsmsL414> eipBpmIsmsL414List = eipBpmIsmsL414Repository.findAll();
        assertThat(eipBpmIsmsL414List).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEipBpmIsmsL414() throws Exception {
        // Initialize the database
        eipBpmIsmsL414.setFormId(UUID.randomUUID().toString());
        eipBpmIsmsL414Repository.saveAndFlush(eipBpmIsmsL414);

        int databaseSizeBeforeDelete = eipBpmIsmsL414Repository.findAll().size();

        // Delete the eipBpmIsmsL414
        restEipBpmIsmsL414MockMvc
            .perform(delete(ENTITY_API_URL_ID, eipBpmIsmsL414.getFormId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EipBpmIsmsL414> eipBpmIsmsL414List = eipBpmIsmsL414Repository.findAll();
        assertThat(eipBpmIsmsL414List).hasSize(databaseSizeBeforeDelete - 1);
    }
}
