package tw.gov.pcc.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static tw.gov.pcc.web.rest.TestUtil.sameNumber;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
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
import tw.gov.pcc.domain.BpmFunction;
import tw.gov.pcc.repository.BpmFunctionRepository;
import tw.gov.pcc.service.dto.BpmFunctionDTO;
import tw.gov.pcc.service.mapper.BpmFunctionMapper;

/**
 * Integration tests for the {@link BpmFunctionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BpmFunctionResourceIT {

    private static final String DEFAULT_FUNCTION_ID = "AAAAAAAAAA";
    private static final String UPDATED_FUNCTION_ID = "BBBBBBBBBB";

    private static final String DEFAULT_FUNCTION_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FUNCTION_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FUNCTION_DESCRIPT = "AAAAAAAAAA";
    private static final String UPDATED_FUNCTION_DESCRIPT = "BBBBBBBBBB";

    private static final String DEFAULT_FUNCTION_PATH = "AAAAAAAAAA";
    private static final String UPDATED_FUNCTION_PATH = "BBBBBBBBBB";

    private static final String DEFAULT_FUNCTION_CATEGORY = "A";
    private static final String UPDATED_FUNCTION_CATEGORY = "B";

    private static final int DEFAULT_SORT_NO = 0;
    private static final int UPDATED_SORT_NO = 1;

    private static final String DEFAULT_MASTER_FUNCTION_ID = "AAAAAAAAAA";
    private static final String UPDATED_MASTER_FUNCTION_ID = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "A";
    private static final String UPDATED_STATUS = "B";

    private static final String DEFAULT_UPDATE_USER = "AAAAAAAAAA";
    private static final String UPDATED_UPDATE_USER = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPDATE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/bpm-functions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BpmFunctionRepository bpmFunctionRepository;

    @Autowired
    private BpmFunctionMapper bpmFunctionMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBpmFunctionMockMvc;

    private BpmFunction bpmFunction;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BpmFunction createEntity(EntityManager em) {
        BpmFunction bpmFunction = new BpmFunction()
            .functionId(DEFAULT_FUNCTION_ID)
            .functionName(DEFAULT_FUNCTION_NAME)
            .functionDescript(DEFAULT_FUNCTION_DESCRIPT)
            .functionPath(DEFAULT_FUNCTION_PATH)
            .functionCategory(DEFAULT_FUNCTION_CATEGORY)
            .sortNo(DEFAULT_SORT_NO)
            .masterFunctionId(DEFAULT_MASTER_FUNCTION_ID)
            .status(DEFAULT_STATUS)
            .updateUser(DEFAULT_UPDATE_USER)
            .updateTime(DEFAULT_UPDATE_TIME);
        return bpmFunction;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BpmFunction createUpdatedEntity(EntityManager em) {
        BpmFunction bpmFunction = new BpmFunction()
            .functionId(UPDATED_FUNCTION_ID)
            .functionName(UPDATED_FUNCTION_NAME)
            .functionDescript(UPDATED_FUNCTION_DESCRIPT)
            .functionPath(UPDATED_FUNCTION_PATH)
            .functionCategory(UPDATED_FUNCTION_CATEGORY)
            .sortNo(UPDATED_SORT_NO)
            .masterFunctionId(UPDATED_MASTER_FUNCTION_ID)
            .status(UPDATED_STATUS)
            .updateUser(UPDATED_UPDATE_USER)
            .updateTime(UPDATED_UPDATE_TIME);
        return bpmFunction;
    }

    @BeforeEach
    public void initTest() {
        bpmFunction = createEntity(em);
    }

    @Test
    @Transactional
    void createBpmFunction() throws Exception {
        int databaseSizeBeforeCreate = bpmFunctionRepository.findAll().size();
        // Create the BpmFunction
        BpmFunctionDTO bpmFunctionDTO = bpmFunctionMapper.toDto(bpmFunction);
        restBpmFunctionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bpmFunctionDTO))
            )
            .andExpect(status().isCreated());

        // Validate the BpmFunction in the database
        List<BpmFunction> bpmFunctionList = bpmFunctionRepository.findAll();
        assertThat(bpmFunctionList).hasSize(databaseSizeBeforeCreate + 1);
        BpmFunction testBpmFunction = bpmFunctionList.get(bpmFunctionList.size() - 1);
        assertThat(testBpmFunction.getFunctionId()).isEqualTo(DEFAULT_FUNCTION_ID);
        assertThat(testBpmFunction.getFunctionName()).isEqualTo(DEFAULT_FUNCTION_NAME);
        assertThat(testBpmFunction.getFunctionDescript()).isEqualTo(DEFAULT_FUNCTION_DESCRIPT);
        assertThat(testBpmFunction.getFunctionPath()).isEqualTo(DEFAULT_FUNCTION_PATH);
        assertThat(testBpmFunction.getFunctionCategory()).isEqualTo(DEFAULT_FUNCTION_CATEGORY);
        assertThat(testBpmFunction.getSortNo()).isEqualTo(DEFAULT_SORT_NO);
        assertThat(testBpmFunction.getMasterFunctionId()).isEqualTo(DEFAULT_MASTER_FUNCTION_ID);
        assertThat(testBpmFunction.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testBpmFunction.getUpdateUser()).isEqualTo(DEFAULT_UPDATE_USER);
        assertThat(testBpmFunction.getUpdateTime()).isEqualTo(DEFAULT_UPDATE_TIME);
    }

    @Test
    @Transactional
    void createBpmFunctionWithExistingId() throws Exception {
        // Create the BpmFunction with an existing ID
        bpmFunction.setId(1L);
        BpmFunctionDTO bpmFunctionDTO = bpmFunctionMapper.toDto(bpmFunction);

        int databaseSizeBeforeCreate = bpmFunctionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBpmFunctionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bpmFunctionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BpmFunction in the database
        List<BpmFunction> bpmFunctionList = bpmFunctionRepository.findAll();
        assertThat(bpmFunctionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkFunctionIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = bpmFunctionRepository.findAll().size();
        // set the field null
        bpmFunction.setFunctionId(null);

        // Create the BpmFunction, which fails.
        BpmFunctionDTO bpmFunctionDTO = bpmFunctionMapper.toDto(bpmFunction);

        restBpmFunctionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bpmFunctionDTO))
            )
            .andExpect(status().isBadRequest());

        List<BpmFunction> bpmFunctionList = bpmFunctionRepository.findAll();
        assertThat(bpmFunctionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFunctionNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = bpmFunctionRepository.findAll().size();
        // set the field null
        bpmFunction.setFunctionName(null);

        // Create the BpmFunction, which fails.
        BpmFunctionDTO bpmFunctionDTO = bpmFunctionMapper.toDto(bpmFunction);

        restBpmFunctionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bpmFunctionDTO))
            )
            .andExpect(status().isBadRequest());

        List<BpmFunction> bpmFunctionList = bpmFunctionRepository.findAll();
        assertThat(bpmFunctionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFunctionDescriptIsRequired() throws Exception {
        int databaseSizeBeforeTest = bpmFunctionRepository.findAll().size();
        // set the field null
        bpmFunction.setFunctionDescript(null);

        // Create the BpmFunction, which fails.
        BpmFunctionDTO bpmFunctionDTO = bpmFunctionMapper.toDto(bpmFunction);

        restBpmFunctionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bpmFunctionDTO))
            )
            .andExpect(status().isBadRequest());

        List<BpmFunction> bpmFunctionList = bpmFunctionRepository.findAll();
        assertThat(bpmFunctionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFunctionPathIsRequired() throws Exception {
        int databaseSizeBeforeTest = bpmFunctionRepository.findAll().size();
        // set the field null
        bpmFunction.setFunctionPath(null);

        // Create the BpmFunction, which fails.
        BpmFunctionDTO bpmFunctionDTO = bpmFunctionMapper.toDto(bpmFunction);

        restBpmFunctionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bpmFunctionDTO))
            )
            .andExpect(status().isBadRequest());

        List<BpmFunction> bpmFunctionList = bpmFunctionRepository.findAll();
        assertThat(bpmFunctionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFunctionCategoryIsRequired() throws Exception {
        int databaseSizeBeforeTest = bpmFunctionRepository.findAll().size();
        // set the field null
        bpmFunction.setFunctionCategory(null);

        // Create the BpmFunction, which fails.
        BpmFunctionDTO bpmFunctionDTO = bpmFunctionMapper.toDto(bpmFunction);

        restBpmFunctionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bpmFunctionDTO))
            )
            .andExpect(status().isBadRequest());

        List<BpmFunction> bpmFunctionList = bpmFunctionRepository.findAll();
        assertThat(bpmFunctionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSortNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = bpmFunctionRepository.findAll().size();
        // set the field null
        bpmFunction.setSortNo(0);

        // Create the BpmFunction, which fails.
        BpmFunctionDTO bpmFunctionDTO = bpmFunctionMapper.toDto(bpmFunction);

        restBpmFunctionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bpmFunctionDTO))
            )
            .andExpect(status().isBadRequest());

        List<BpmFunction> bpmFunctionList = bpmFunctionRepository.findAll();
        assertThat(bpmFunctionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMasterFunctionIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = bpmFunctionRepository.findAll().size();
        // set the field null
        bpmFunction.setMasterFunctionId(null);

        // Create the BpmFunction, which fails.
        BpmFunctionDTO bpmFunctionDTO = bpmFunctionMapper.toDto(bpmFunction);

        restBpmFunctionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bpmFunctionDTO))
            )
            .andExpect(status().isBadRequest());

        List<BpmFunction> bpmFunctionList = bpmFunctionRepository.findAll();
        assertThat(bpmFunctionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = bpmFunctionRepository.findAll().size();
        // set the field null
        bpmFunction.setStatus(null);

        // Create the BpmFunction, which fails.
        BpmFunctionDTO bpmFunctionDTO = bpmFunctionMapper.toDto(bpmFunction);

        restBpmFunctionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bpmFunctionDTO))
            )
            .andExpect(status().isBadRequest());

        List<BpmFunction> bpmFunctionList = bpmFunctionRepository.findAll();
        assertThat(bpmFunctionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdateUserIsRequired() throws Exception {
        int databaseSizeBeforeTest = bpmFunctionRepository.findAll().size();
        // set the field null
        bpmFunction.setUpdateUser(null);

        // Create the BpmFunction, which fails.
        BpmFunctionDTO bpmFunctionDTO = bpmFunctionMapper.toDto(bpmFunction);

        restBpmFunctionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bpmFunctionDTO))
            )
            .andExpect(status().isBadRequest());

        List<BpmFunction> bpmFunctionList = bpmFunctionRepository.findAll();
        assertThat(bpmFunctionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdateTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = bpmFunctionRepository.findAll().size();
        // set the field null
        bpmFunction.setUpdateTime(null);

        // Create the BpmFunction, which fails.
        BpmFunctionDTO bpmFunctionDTO = bpmFunctionMapper.toDto(bpmFunction);

        restBpmFunctionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bpmFunctionDTO))
            )
            .andExpect(status().isBadRequest());

        List<BpmFunction> bpmFunctionList = bpmFunctionRepository.findAll();
        assertThat(bpmFunctionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllBpmFunctions() throws Exception {
        // Initialize the database
        bpmFunctionRepository.saveAndFlush(bpmFunction);

        // Get all the bpmFunctionList
        restBpmFunctionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bpmFunction.getId().intValue())))
            .andExpect(jsonPath("$.[*].functionId").value(hasItem(DEFAULT_FUNCTION_ID)))
            .andExpect(jsonPath("$.[*].functionName").value(hasItem(DEFAULT_FUNCTION_NAME)))
            .andExpect(jsonPath("$.[*].functionDescript").value(hasItem(DEFAULT_FUNCTION_DESCRIPT)))
            .andExpect(jsonPath("$.[*].functionPath").value(hasItem(DEFAULT_FUNCTION_PATH)))
            .andExpect(jsonPath("$.[*].functionCategory").value(hasItem(DEFAULT_FUNCTION_CATEGORY)))
            .andExpect(jsonPath("$.[*].sortNo").value(hasItem(DEFAULT_SORT_NO)))
            .andExpect(jsonPath("$.[*].masterFunctionId").value(hasItem(DEFAULT_MASTER_FUNCTION_ID)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].updateUser").value(hasItem(DEFAULT_UPDATE_USER)))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(DEFAULT_UPDATE_TIME.toString())));
    }

    @Test
    @Transactional
    void getBpmFunction() throws Exception {
        // Initialize the database
        bpmFunctionRepository.saveAndFlush(bpmFunction);

        // Get the bpmFunction
        restBpmFunctionMockMvc
            .perform(get(ENTITY_API_URL_ID, bpmFunction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bpmFunction.getId().intValue()))
            .andExpect(jsonPath("$.functionId").value(DEFAULT_FUNCTION_ID))
            .andExpect(jsonPath("$.functionName").value(DEFAULT_FUNCTION_NAME))
            .andExpect(jsonPath("$.functionDescript").value(DEFAULT_FUNCTION_DESCRIPT))
            .andExpect(jsonPath("$.functionPath").value(DEFAULT_FUNCTION_PATH))
            .andExpect(jsonPath("$.functionCategory").value(DEFAULT_FUNCTION_CATEGORY))
            .andExpect(jsonPath("$.sortNo").value(DEFAULT_SORT_NO))
            .andExpect(jsonPath("$.masterFunctionId").value(DEFAULT_MASTER_FUNCTION_ID))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.updateUser").value(DEFAULT_UPDATE_USER))
            .andExpect(jsonPath("$.updateTime").value(DEFAULT_UPDATE_TIME.toString()));
    }

    @Test
    @Transactional
    void getNonExistingBpmFunction() throws Exception {
        // Get the bpmFunction
        restBpmFunctionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewBpmFunction() throws Exception {
        // Initialize the database
        bpmFunctionRepository.saveAndFlush(bpmFunction);

        int databaseSizeBeforeUpdate = bpmFunctionRepository.findAll().size();

        // Update the bpmFunction
        BpmFunction updatedBpmFunction = bpmFunctionRepository.findById(bpmFunction.getId()).get();
        // Disconnect from session so that the updates on updatedBpmFunction are not directly saved in db
        em.detach(updatedBpmFunction);
        updatedBpmFunction
            .functionId(UPDATED_FUNCTION_ID)
            .functionName(UPDATED_FUNCTION_NAME)
            .functionDescript(UPDATED_FUNCTION_DESCRIPT)
            .functionPath(UPDATED_FUNCTION_PATH)
            .functionCategory(UPDATED_FUNCTION_CATEGORY)
            .sortNo(UPDATED_SORT_NO)
            .masterFunctionId(UPDATED_MASTER_FUNCTION_ID)
            .status(UPDATED_STATUS)
            .updateUser(UPDATED_UPDATE_USER)
            .updateTime(UPDATED_UPDATE_TIME);
        BpmFunctionDTO bpmFunctionDTO = bpmFunctionMapper.toDto(updatedBpmFunction);

        restBpmFunctionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bpmFunctionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bpmFunctionDTO))
            )
            .andExpect(status().isOk());

        // Validate the BpmFunction in the database
        List<BpmFunction> bpmFunctionList = bpmFunctionRepository.findAll();
        assertThat(bpmFunctionList).hasSize(databaseSizeBeforeUpdate);
        BpmFunction testBpmFunction = bpmFunctionList.get(bpmFunctionList.size() - 1);
        assertThat(testBpmFunction.getFunctionId()).isEqualTo(UPDATED_FUNCTION_ID);
        assertThat(testBpmFunction.getFunctionName()).isEqualTo(UPDATED_FUNCTION_NAME);
        assertThat(testBpmFunction.getFunctionDescript()).isEqualTo(UPDATED_FUNCTION_DESCRIPT);
        assertThat(testBpmFunction.getFunctionPath()).isEqualTo(UPDATED_FUNCTION_PATH);
        assertThat(testBpmFunction.getFunctionCategory()).isEqualTo(UPDATED_FUNCTION_CATEGORY);
        assertThat(testBpmFunction.getSortNo()).isEqualByComparingTo(UPDATED_SORT_NO);
        assertThat(testBpmFunction.getMasterFunctionId()).isEqualTo(UPDATED_MASTER_FUNCTION_ID);
        assertThat(testBpmFunction.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testBpmFunction.getUpdateUser()).isEqualTo(UPDATED_UPDATE_USER);
        assertThat(testBpmFunction.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
    }

    @Test
    @Transactional
    void putNonExistingBpmFunction() throws Exception {
        int databaseSizeBeforeUpdate = bpmFunctionRepository.findAll().size();
        bpmFunction.setId(count.incrementAndGet());

        // Create the BpmFunction
        BpmFunctionDTO bpmFunctionDTO = bpmFunctionMapper.toDto(bpmFunction);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBpmFunctionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bpmFunctionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bpmFunctionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BpmFunction in the database
        List<BpmFunction> bpmFunctionList = bpmFunctionRepository.findAll();
        assertThat(bpmFunctionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBpmFunction() throws Exception {
        int databaseSizeBeforeUpdate = bpmFunctionRepository.findAll().size();
        bpmFunction.setId(count.incrementAndGet());

        // Create the BpmFunction
        BpmFunctionDTO bpmFunctionDTO = bpmFunctionMapper.toDto(bpmFunction);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBpmFunctionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bpmFunctionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BpmFunction in the database
        List<BpmFunction> bpmFunctionList = bpmFunctionRepository.findAll();
        assertThat(bpmFunctionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBpmFunction() throws Exception {
        int databaseSizeBeforeUpdate = bpmFunctionRepository.findAll().size();
        bpmFunction.setId(count.incrementAndGet());

        // Create the BpmFunction
        BpmFunctionDTO bpmFunctionDTO = bpmFunctionMapper.toDto(bpmFunction);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBpmFunctionMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bpmFunctionDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the BpmFunction in the database
        List<BpmFunction> bpmFunctionList = bpmFunctionRepository.findAll();
        assertThat(bpmFunctionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBpmFunctionWithPatch() throws Exception {
        // Initialize the database
        bpmFunctionRepository.saveAndFlush(bpmFunction);

        int databaseSizeBeforeUpdate = bpmFunctionRepository.findAll().size();

        // Update the bpmFunction using partial update
        BpmFunction partialUpdatedBpmFunction = new BpmFunction();
        partialUpdatedBpmFunction.setId(bpmFunction.getId());

        partialUpdatedBpmFunction.functionPath(UPDATED_FUNCTION_PATH);

        restBpmFunctionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBpmFunction.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBpmFunction))
            )
            .andExpect(status().isOk());

        // Validate the BpmFunction in the database
        List<BpmFunction> bpmFunctionList = bpmFunctionRepository.findAll();
        assertThat(bpmFunctionList).hasSize(databaseSizeBeforeUpdate);
        BpmFunction testBpmFunction = bpmFunctionList.get(bpmFunctionList.size() - 1);
        assertThat(testBpmFunction.getFunctionId()).isEqualTo(DEFAULT_FUNCTION_ID);
        assertThat(testBpmFunction.getFunctionName()).isEqualTo(DEFAULT_FUNCTION_NAME);
        assertThat(testBpmFunction.getFunctionDescript()).isEqualTo(DEFAULT_FUNCTION_DESCRIPT);
        assertThat(testBpmFunction.getFunctionPath()).isEqualTo(UPDATED_FUNCTION_PATH);
        assertThat(testBpmFunction.getFunctionCategory()).isEqualTo(DEFAULT_FUNCTION_CATEGORY);
        assertThat(testBpmFunction.getSortNo()).isEqualByComparingTo(DEFAULT_SORT_NO);
        assertThat(testBpmFunction.getMasterFunctionId()).isEqualTo(DEFAULT_MASTER_FUNCTION_ID);
        assertThat(testBpmFunction.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testBpmFunction.getUpdateUser()).isEqualTo(DEFAULT_UPDATE_USER);
        assertThat(testBpmFunction.getUpdateTime()).isEqualTo(DEFAULT_UPDATE_TIME);
    }

    @Test
    @Transactional
    void fullUpdateBpmFunctionWithPatch() throws Exception {
        // Initialize the database
        bpmFunctionRepository.saveAndFlush(bpmFunction);

        int databaseSizeBeforeUpdate = bpmFunctionRepository.findAll().size();

        // Update the bpmFunction using partial update
        BpmFunction partialUpdatedBpmFunction = new BpmFunction();
        partialUpdatedBpmFunction.setId(bpmFunction.getId());

        partialUpdatedBpmFunction
            .functionId(UPDATED_FUNCTION_ID)
            .functionName(UPDATED_FUNCTION_NAME)
            .functionDescript(UPDATED_FUNCTION_DESCRIPT)
            .functionPath(UPDATED_FUNCTION_PATH)
            .functionCategory(UPDATED_FUNCTION_CATEGORY)
            .sortNo(UPDATED_SORT_NO)
            .masterFunctionId(UPDATED_MASTER_FUNCTION_ID)
            .status(UPDATED_STATUS)
            .updateUser(UPDATED_UPDATE_USER)
            .updateTime(UPDATED_UPDATE_TIME);

        restBpmFunctionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBpmFunction.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBpmFunction))
            )
            .andExpect(status().isOk());

        // Validate the BpmFunction in the database
        List<BpmFunction> bpmFunctionList = bpmFunctionRepository.findAll();
        assertThat(bpmFunctionList).hasSize(databaseSizeBeforeUpdate);
        BpmFunction testBpmFunction = bpmFunctionList.get(bpmFunctionList.size() - 1);
        assertThat(testBpmFunction.getFunctionId()).isEqualTo(UPDATED_FUNCTION_ID);
        assertThat(testBpmFunction.getFunctionName()).isEqualTo(UPDATED_FUNCTION_NAME);
        assertThat(testBpmFunction.getFunctionDescript()).isEqualTo(UPDATED_FUNCTION_DESCRIPT);
        assertThat(testBpmFunction.getFunctionPath()).isEqualTo(UPDATED_FUNCTION_PATH);
        assertThat(testBpmFunction.getFunctionCategory()).isEqualTo(UPDATED_FUNCTION_CATEGORY);
        assertThat(testBpmFunction.getSortNo()).isEqualByComparingTo(UPDATED_SORT_NO);
        assertThat(testBpmFunction.getMasterFunctionId()).isEqualTo(UPDATED_MASTER_FUNCTION_ID);
        assertThat(testBpmFunction.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testBpmFunction.getUpdateUser()).isEqualTo(UPDATED_UPDATE_USER);
        assertThat(testBpmFunction.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
    }

    @Test
    @Transactional
    void patchNonExistingBpmFunction() throws Exception {
        int databaseSizeBeforeUpdate = bpmFunctionRepository.findAll().size();
        bpmFunction.setId(count.incrementAndGet());

        // Create the BpmFunction
        BpmFunctionDTO bpmFunctionDTO = bpmFunctionMapper.toDto(bpmFunction);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBpmFunctionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, bpmFunctionDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bpmFunctionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BpmFunction in the database
        List<BpmFunction> bpmFunctionList = bpmFunctionRepository.findAll();
        assertThat(bpmFunctionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBpmFunction() throws Exception {
        int databaseSizeBeforeUpdate = bpmFunctionRepository.findAll().size();
        bpmFunction.setId(count.incrementAndGet());

        // Create the BpmFunction
        BpmFunctionDTO bpmFunctionDTO = bpmFunctionMapper.toDto(bpmFunction);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBpmFunctionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bpmFunctionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BpmFunction in the database
        List<BpmFunction> bpmFunctionList = bpmFunctionRepository.findAll();
        assertThat(bpmFunctionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBpmFunction() throws Exception {
        int databaseSizeBeforeUpdate = bpmFunctionRepository.findAll().size();
        bpmFunction.setId(count.incrementAndGet());

        // Create the BpmFunction
        BpmFunctionDTO bpmFunctionDTO = bpmFunctionMapper.toDto(bpmFunction);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBpmFunctionMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(bpmFunctionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BpmFunction in the database
        List<BpmFunction> bpmFunctionList = bpmFunctionRepository.findAll();
        assertThat(bpmFunctionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBpmFunction() throws Exception {
        // Initialize the database
        bpmFunctionRepository.saveAndFlush(bpmFunction);

        int databaseSizeBeforeDelete = bpmFunctionRepository.findAll().size();

        // Delete the bpmFunction
        restBpmFunctionMockMvc
            .perform(delete(ENTITY_API_URL_ID, bpmFunction.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BpmFunction> bpmFunctionList = bpmFunctionRepository.findAll();
        assertThat(bpmFunctionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
