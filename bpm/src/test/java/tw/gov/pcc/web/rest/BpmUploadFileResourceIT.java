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
import tw.gov.pcc.domain.BpmUploadFile;
import tw.gov.pcc.repository.BpmUploadFileRepository;
import tw.gov.pcc.service.dto.BpmUploadFileDTO;
import tw.gov.pcc.service.mapper.BpmUploadFileMapper;

/**
 * Integration tests for the {@link BpmUploadFileResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BpmUploadFileResourceIT {

    private static final String DEFAULT_FORM_ID = "AAAAAAAAAA";
    private static final String UPDATED_FORM_ID = "BBBBBBBBBB";

    private static final String DEFAULT_FILE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FILE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FILE_SIZE = "AAAAAAAAAA";
    private static final String UPDATED_FILE_SIZE = "BBBBBBBBBB";

    private static final String DEFAULT_AUTHOR_NAME = "AAAAAAAAAA";
    private static final String UPDATED_AUTHOR_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FILE_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_FILE_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_FILE_PATH = "AAAAAAAAAA";
    private static final String UPDATED_FILE_PATH = "BBBBBBBBBB";

    private static final String DEFAULT_UPDATE_USER = "AAAAAAAAAA";
    private static final String UPDATED_UPDATE_USER = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPDATE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CREATE_USER = "AAAAAAAAAA";
    private static final String UPDATED_CREATE_USER = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/bpm-upload-files";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{uuid}";

    @Autowired
    private BpmUploadFileRepository bpmUploadFileRepository;

    @Autowired
    private BpmUploadFileMapper bpmUploadFileMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBpmUploadFileMockMvc;

    private BpmUploadFile bpmUploadFile;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BpmUploadFile createEntity(EntityManager em) {
        BpmUploadFile bpmUploadFile = new BpmUploadFile()
            .formId(DEFAULT_FORM_ID)
            .fileName(DEFAULT_FILE_NAME)
            .fileSize(DEFAULT_FILE_SIZE)
            .authorName(DEFAULT_AUTHOR_NAME)
            .fileDescription(DEFAULT_FILE_DESCRIPTION)
            .filePath(DEFAULT_FILE_PATH)
            .updateUser(DEFAULT_UPDATE_USER)
            .updateTime(DEFAULT_UPDATE_TIME)
            .createUser(DEFAULT_CREATE_USER)
            .createTime(DEFAULT_CREATE_TIME);
        return bpmUploadFile;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BpmUploadFile createUpdatedEntity(EntityManager em) {
        BpmUploadFile bpmUploadFile = new BpmUploadFile()
            .formId(UPDATED_FORM_ID)
            .fileName(UPDATED_FILE_NAME)
            .fileSize(UPDATED_FILE_SIZE)
            .authorName(UPDATED_AUTHOR_NAME)
            .fileDescription(UPDATED_FILE_DESCRIPTION)
            .filePath(UPDATED_FILE_PATH)
            .updateUser(UPDATED_UPDATE_USER)
            .updateTime(UPDATED_UPDATE_TIME)
            .createUser(UPDATED_CREATE_USER)
            .createTime(UPDATED_CREATE_TIME);
        return bpmUploadFile;
    }

    @BeforeEach
    public void initTest() {
        bpmUploadFile = createEntity(em);
    }

    @Test
    @Transactional
    void createBpmUploadFile() throws Exception {
        int databaseSizeBeforeCreate = bpmUploadFileRepository.findAll().size();
        // Create the BpmUploadFile
        BpmUploadFileDTO bpmUploadFileDTO = bpmUploadFileMapper.toDto(bpmUploadFile);
        restBpmUploadFileMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bpmUploadFileDTO))
            )
            .andExpect(status().isCreated());

        // Validate the BpmUploadFile in the database
        List<BpmUploadFile> bpmUploadFileList = bpmUploadFileRepository.findAll();
        assertThat(bpmUploadFileList).hasSize(databaseSizeBeforeCreate + 1);
        BpmUploadFile testBpmUploadFile = bpmUploadFileList.get(bpmUploadFileList.size() - 1);
        assertThat(testBpmUploadFile.getFormId()).isEqualTo(DEFAULT_FORM_ID);
        assertThat(testBpmUploadFile.getFileName()).isEqualTo(DEFAULT_FILE_NAME);
        assertThat(testBpmUploadFile.getFileSize()).isEqualTo(DEFAULT_FILE_SIZE);
        assertThat(testBpmUploadFile.getAuthorName()).isEqualTo(DEFAULT_AUTHOR_NAME);
        assertThat(testBpmUploadFile.getFileDescription()).isEqualTo(DEFAULT_FILE_DESCRIPTION);
        assertThat(testBpmUploadFile.getFilePath()).isEqualTo(DEFAULT_FILE_PATH);
        assertThat(testBpmUploadFile.getUpdateUser()).isEqualTo(DEFAULT_UPDATE_USER);
        assertThat(testBpmUploadFile.getUpdateTime()).isEqualTo(DEFAULT_UPDATE_TIME);
        assertThat(testBpmUploadFile.getCreateUser()).isEqualTo(DEFAULT_CREATE_USER);
        assertThat(testBpmUploadFile.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
    }

    @Test
    @Transactional
    void createBpmUploadFileWithExistingId() throws Exception {
        // Create the BpmUploadFile with an existing ID
        bpmUploadFileRepository.saveAndFlush(bpmUploadFile);
        BpmUploadFileDTO bpmUploadFileDTO = bpmUploadFileMapper.toDto(bpmUploadFile);

        int databaseSizeBeforeCreate = bpmUploadFileRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBpmUploadFileMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bpmUploadFileDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BpmUploadFile in the database
        List<BpmUploadFile> bpmUploadFileList = bpmUploadFileRepository.findAll();
        assertThat(bpmUploadFileList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkFormIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = bpmUploadFileRepository.findAll().size();
        // set the field null
        bpmUploadFile.setFormId(null);

        // Create the BpmUploadFile, which fails.
        BpmUploadFileDTO bpmUploadFileDTO = bpmUploadFileMapper.toDto(bpmUploadFile);

        restBpmUploadFileMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bpmUploadFileDTO))
            )
            .andExpect(status().isBadRequest());

        List<BpmUploadFile> bpmUploadFileList = bpmUploadFileRepository.findAll();
        assertThat(bpmUploadFileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFileNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = bpmUploadFileRepository.findAll().size();
        // set the field null
        bpmUploadFile.setFileName(null);

        // Create the BpmUploadFile, which fails.
        BpmUploadFileDTO bpmUploadFileDTO = bpmUploadFileMapper.toDto(bpmUploadFile);

        restBpmUploadFileMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bpmUploadFileDTO))
            )
            .andExpect(status().isBadRequest());

        List<BpmUploadFile> bpmUploadFileList = bpmUploadFileRepository.findAll();
        assertThat(bpmUploadFileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFileSizeIsRequired() throws Exception {
        int databaseSizeBeforeTest = bpmUploadFileRepository.findAll().size();
        // set the field null
        bpmUploadFile.setFileSize(null);

        // Create the BpmUploadFile, which fails.
        BpmUploadFileDTO bpmUploadFileDTO = bpmUploadFileMapper.toDto(bpmUploadFile);

        restBpmUploadFileMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bpmUploadFileDTO))
            )
            .andExpect(status().isBadRequest());

        List<BpmUploadFile> bpmUploadFileList = bpmUploadFileRepository.findAll();
        assertThat(bpmUploadFileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAuthorNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = bpmUploadFileRepository.findAll().size();
        // set the field null
        bpmUploadFile.setAuthorName(null);

        // Create the BpmUploadFile, which fails.
        BpmUploadFileDTO bpmUploadFileDTO = bpmUploadFileMapper.toDto(bpmUploadFile);

        restBpmUploadFileMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bpmUploadFileDTO))
            )
            .andExpect(status().isBadRequest());

        List<BpmUploadFile> bpmUploadFileList = bpmUploadFileRepository.findAll();
        assertThat(bpmUploadFileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFilePathIsRequired() throws Exception {
        int databaseSizeBeforeTest = bpmUploadFileRepository.findAll().size();
        // set the field null
        bpmUploadFile.setFilePath(null);

        // Create the BpmUploadFile, which fails.
        BpmUploadFileDTO bpmUploadFileDTO = bpmUploadFileMapper.toDto(bpmUploadFile);

        restBpmUploadFileMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bpmUploadFileDTO))
            )
            .andExpect(status().isBadRequest());

        List<BpmUploadFile> bpmUploadFileList = bpmUploadFileRepository.findAll();
        assertThat(bpmUploadFileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreateUserIsRequired() throws Exception {
        int databaseSizeBeforeTest = bpmUploadFileRepository.findAll().size();
        // set the field null
        bpmUploadFile.setCreateUser(null);

        // Create the BpmUploadFile, which fails.
        BpmUploadFileDTO bpmUploadFileDTO = bpmUploadFileMapper.toDto(bpmUploadFile);

        restBpmUploadFileMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bpmUploadFileDTO))
            )
            .andExpect(status().isBadRequest());

        List<BpmUploadFile> bpmUploadFileList = bpmUploadFileRepository.findAll();
        assertThat(bpmUploadFileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreateTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = bpmUploadFileRepository.findAll().size();
        // set the field null
        bpmUploadFile.setCreateTime(null);

        // Create the BpmUploadFile, which fails.
        BpmUploadFileDTO bpmUploadFileDTO = bpmUploadFileMapper.toDto(bpmUploadFile);

        restBpmUploadFileMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bpmUploadFileDTO))
            )
            .andExpect(status().isBadRequest());

        List<BpmUploadFile> bpmUploadFileList = bpmUploadFileRepository.findAll();
        assertThat(bpmUploadFileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllBpmUploadFiles() throws Exception {
        // Initialize the database
        bpmUploadFileRepository.saveAndFlush(bpmUploadFile);

        // Get all the bpmUploadFileList
        restBpmUploadFileMockMvc
            .perform(get(ENTITY_API_URL + "?sort=uuid,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].uuid").value(hasItem(bpmUploadFile.getUuid().toString())))
            .andExpect(jsonPath("$.[*].formId").value(hasItem(DEFAULT_FORM_ID)))
            .andExpect(jsonPath("$.[*].fileName").value(hasItem(DEFAULT_FILE_NAME)))
            .andExpect(jsonPath("$.[*].fileSize").value(hasItem(DEFAULT_FILE_SIZE)))
            .andExpect(jsonPath("$.[*].authorName").value(hasItem(DEFAULT_AUTHOR_NAME)))
            .andExpect(jsonPath("$.[*].fileDescription").value(hasItem(DEFAULT_FILE_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].filePath").value(hasItem(DEFAULT_FILE_PATH)))
            .andExpect(jsonPath("$.[*].updateUser").value(hasItem(DEFAULT_UPDATE_USER)))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(DEFAULT_UPDATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].createUser").value(hasItem(DEFAULT_CREATE_USER)))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(DEFAULT_CREATE_TIME.toString())));
    }

    @Test
    @Transactional
    void getBpmUploadFile() throws Exception {
        // Initialize the database
        bpmUploadFileRepository.saveAndFlush(bpmUploadFile);

        // Get the bpmUploadFile
        restBpmUploadFileMockMvc
            .perform(get(ENTITY_API_URL_ID, bpmUploadFile.getUuid()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.uuid").value(bpmUploadFile.getUuid().toString()))
            .andExpect(jsonPath("$.formId").value(DEFAULT_FORM_ID))
            .andExpect(jsonPath("$.fileName").value(DEFAULT_FILE_NAME))
            .andExpect(jsonPath("$.fileSize").value(DEFAULT_FILE_SIZE))
            .andExpect(jsonPath("$.authorName").value(DEFAULT_AUTHOR_NAME))
            .andExpect(jsonPath("$.fileDescription").value(DEFAULT_FILE_DESCRIPTION))
            .andExpect(jsonPath("$.filePath").value(DEFAULT_FILE_PATH))
            .andExpect(jsonPath("$.updateUser").value(DEFAULT_UPDATE_USER))
            .andExpect(jsonPath("$.updateTime").value(DEFAULT_UPDATE_TIME.toString()))
            .andExpect(jsonPath("$.createUser").value(DEFAULT_CREATE_USER))
            .andExpect(jsonPath("$.createTime").value(DEFAULT_CREATE_TIME.toString()));
    }

    @Test
    @Transactional
    void getNonExistingBpmUploadFile() throws Exception {
        // Get the bpmUploadFile
        restBpmUploadFileMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewBpmUploadFile() throws Exception {
        // Initialize the database
        bpmUploadFileRepository.saveAndFlush(bpmUploadFile);

        int databaseSizeBeforeUpdate = bpmUploadFileRepository.findAll().size();

        // Update the bpmUploadFile
        BpmUploadFile updatedBpmUploadFile = bpmUploadFileRepository.findById(bpmUploadFile.getUuid()).get();
        // Disconnect from session so that the updates on updatedBpmUploadFile are not directly saved in db
        em.detach(updatedBpmUploadFile);
        updatedBpmUploadFile
            .formId(UPDATED_FORM_ID)
            .fileName(UPDATED_FILE_NAME)
            .fileSize(UPDATED_FILE_SIZE)
            .authorName(UPDATED_AUTHOR_NAME)
            .fileDescription(UPDATED_FILE_DESCRIPTION)
            .filePath(UPDATED_FILE_PATH)
            .updateUser(UPDATED_UPDATE_USER)
            .updateTime(UPDATED_UPDATE_TIME)
            .createUser(UPDATED_CREATE_USER)
            .createTime(UPDATED_CREATE_TIME);
        BpmUploadFileDTO bpmUploadFileDTO = bpmUploadFileMapper.toDto(updatedBpmUploadFile);

        restBpmUploadFileMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bpmUploadFileDTO.getUuid())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bpmUploadFileDTO))
            )
            .andExpect(status().isOk());

        // Validate the BpmUploadFile in the database
        List<BpmUploadFile> bpmUploadFileList = bpmUploadFileRepository.findAll();
        assertThat(bpmUploadFileList).hasSize(databaseSizeBeforeUpdate);
        BpmUploadFile testBpmUploadFile = bpmUploadFileList.get(bpmUploadFileList.size() - 1);
        assertThat(testBpmUploadFile.getFormId()).isEqualTo(UPDATED_FORM_ID);
        assertThat(testBpmUploadFile.getFileName()).isEqualTo(UPDATED_FILE_NAME);
        assertThat(testBpmUploadFile.getFileSize()).isEqualTo(UPDATED_FILE_SIZE);
        assertThat(testBpmUploadFile.getAuthorName()).isEqualTo(UPDATED_AUTHOR_NAME);
        assertThat(testBpmUploadFile.getFileDescription()).isEqualTo(UPDATED_FILE_DESCRIPTION);
        assertThat(testBpmUploadFile.getFilePath()).isEqualTo(UPDATED_FILE_PATH);
        assertThat(testBpmUploadFile.getUpdateUser()).isEqualTo(UPDATED_UPDATE_USER);
        assertThat(testBpmUploadFile.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
        assertThat(testBpmUploadFile.getCreateUser()).isEqualTo(UPDATED_CREATE_USER);
        assertThat(testBpmUploadFile.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
    }

    @Test
    @Transactional
    void putNonExistingBpmUploadFile() throws Exception {
        int databaseSizeBeforeUpdate = bpmUploadFileRepository.findAll().size();
        bpmUploadFile.setUuid(UUID.randomUUID());

        // Create the BpmUploadFile
        BpmUploadFileDTO bpmUploadFileDTO = bpmUploadFileMapper.toDto(bpmUploadFile);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBpmUploadFileMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bpmUploadFileDTO.getUuid())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bpmUploadFileDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BpmUploadFile in the database
        List<BpmUploadFile> bpmUploadFileList = bpmUploadFileRepository.findAll();
        assertThat(bpmUploadFileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBpmUploadFile() throws Exception {
        int databaseSizeBeforeUpdate = bpmUploadFileRepository.findAll().size();
        bpmUploadFile.setUuid(UUID.randomUUID());

        // Create the BpmUploadFile
        BpmUploadFileDTO bpmUploadFileDTO = bpmUploadFileMapper.toDto(bpmUploadFile);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBpmUploadFileMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bpmUploadFileDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BpmUploadFile in the database
        List<BpmUploadFile> bpmUploadFileList = bpmUploadFileRepository.findAll();
        assertThat(bpmUploadFileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBpmUploadFile() throws Exception {
        int databaseSizeBeforeUpdate = bpmUploadFileRepository.findAll().size();
        bpmUploadFile.setUuid(UUID.randomUUID());

        // Create the BpmUploadFile
        BpmUploadFileDTO bpmUploadFileDTO = bpmUploadFileMapper.toDto(bpmUploadFile);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBpmUploadFileMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bpmUploadFileDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BpmUploadFile in the database
        List<BpmUploadFile> bpmUploadFileList = bpmUploadFileRepository.findAll();
        assertThat(bpmUploadFileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBpmUploadFileWithPatch() throws Exception {
        // Initialize the database
        bpmUploadFileRepository.saveAndFlush(bpmUploadFile);

        int databaseSizeBeforeUpdate = bpmUploadFileRepository.findAll().size();

        // Update the bpmUploadFile using partial update
        BpmUploadFile partialUpdatedBpmUploadFile = new BpmUploadFile();
        partialUpdatedBpmUploadFile.setUuid(bpmUploadFile.getUuid());

        partialUpdatedBpmUploadFile
            .formId(UPDATED_FORM_ID)
            .fileName(UPDATED_FILE_NAME)
            .authorName(UPDATED_AUTHOR_NAME)
            .fileDescription(UPDATED_FILE_DESCRIPTION)
            .filePath(UPDATED_FILE_PATH)
            .createUser(UPDATED_CREATE_USER)
            .createTime(UPDATED_CREATE_TIME);

        restBpmUploadFileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBpmUploadFile.getUuid())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBpmUploadFile))
            )
            .andExpect(status().isOk());

        // Validate the BpmUploadFile in the database
        List<BpmUploadFile> bpmUploadFileList = bpmUploadFileRepository.findAll();
        assertThat(bpmUploadFileList).hasSize(databaseSizeBeforeUpdate);
        BpmUploadFile testBpmUploadFile = bpmUploadFileList.get(bpmUploadFileList.size() - 1);
        assertThat(testBpmUploadFile.getFormId()).isEqualTo(UPDATED_FORM_ID);
        assertThat(testBpmUploadFile.getFileName()).isEqualTo(UPDATED_FILE_NAME);
        assertThat(testBpmUploadFile.getFileSize()).isEqualTo(DEFAULT_FILE_SIZE);
        assertThat(testBpmUploadFile.getAuthorName()).isEqualTo(UPDATED_AUTHOR_NAME);
        assertThat(testBpmUploadFile.getFileDescription()).isEqualTo(UPDATED_FILE_DESCRIPTION);
        assertThat(testBpmUploadFile.getFilePath()).isEqualTo(UPDATED_FILE_PATH);
        assertThat(testBpmUploadFile.getUpdateUser()).isEqualTo(DEFAULT_UPDATE_USER);
        assertThat(testBpmUploadFile.getUpdateTime()).isEqualTo(DEFAULT_UPDATE_TIME);
        assertThat(testBpmUploadFile.getCreateUser()).isEqualTo(UPDATED_CREATE_USER);
        assertThat(testBpmUploadFile.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
    }

    @Test
    @Transactional
    void fullUpdateBpmUploadFileWithPatch() throws Exception {
        // Initialize the database
        bpmUploadFileRepository.saveAndFlush(bpmUploadFile);

        int databaseSizeBeforeUpdate = bpmUploadFileRepository.findAll().size();

        // Update the bpmUploadFile using partial update
        BpmUploadFile partialUpdatedBpmUploadFile = new BpmUploadFile();
        partialUpdatedBpmUploadFile.setUuid(bpmUploadFile.getUuid());

        partialUpdatedBpmUploadFile
            .formId(UPDATED_FORM_ID)
            .fileName(UPDATED_FILE_NAME)
            .fileSize(UPDATED_FILE_SIZE)
            .authorName(UPDATED_AUTHOR_NAME)
            .fileDescription(UPDATED_FILE_DESCRIPTION)
            .filePath(UPDATED_FILE_PATH)
            .updateUser(UPDATED_UPDATE_USER)
            .updateTime(UPDATED_UPDATE_TIME)
            .createUser(UPDATED_CREATE_USER)
            .createTime(UPDATED_CREATE_TIME);

        restBpmUploadFileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBpmUploadFile.getUuid())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBpmUploadFile))
            )
            .andExpect(status().isOk());

        // Validate the BpmUploadFile in the database
        List<BpmUploadFile> bpmUploadFileList = bpmUploadFileRepository.findAll();
        assertThat(bpmUploadFileList).hasSize(databaseSizeBeforeUpdate);
        BpmUploadFile testBpmUploadFile = bpmUploadFileList.get(bpmUploadFileList.size() - 1);
        assertThat(testBpmUploadFile.getFormId()).isEqualTo(UPDATED_FORM_ID);
        assertThat(testBpmUploadFile.getFileName()).isEqualTo(UPDATED_FILE_NAME);
        assertThat(testBpmUploadFile.getFileSize()).isEqualTo(UPDATED_FILE_SIZE);
        assertThat(testBpmUploadFile.getAuthorName()).isEqualTo(UPDATED_AUTHOR_NAME);
        assertThat(testBpmUploadFile.getFileDescription()).isEqualTo(UPDATED_FILE_DESCRIPTION);
        assertThat(testBpmUploadFile.getFilePath()).isEqualTo(UPDATED_FILE_PATH);
        assertThat(testBpmUploadFile.getUpdateUser()).isEqualTo(UPDATED_UPDATE_USER);
        assertThat(testBpmUploadFile.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
        assertThat(testBpmUploadFile.getCreateUser()).isEqualTo(UPDATED_CREATE_USER);
        assertThat(testBpmUploadFile.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
    }

    @Test
    @Transactional
    void patchNonExistingBpmUploadFile() throws Exception {
        int databaseSizeBeforeUpdate = bpmUploadFileRepository.findAll().size();
        bpmUploadFile.setUuid(UUID.randomUUID());

        // Create the BpmUploadFile
        BpmUploadFileDTO bpmUploadFileDTO = bpmUploadFileMapper.toDto(bpmUploadFile);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBpmUploadFileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, bpmUploadFileDTO.getUuid())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bpmUploadFileDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BpmUploadFile in the database
        List<BpmUploadFile> bpmUploadFileList = bpmUploadFileRepository.findAll();
        assertThat(bpmUploadFileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBpmUploadFile() throws Exception {
        int databaseSizeBeforeUpdate = bpmUploadFileRepository.findAll().size();
        bpmUploadFile.setUuid(UUID.randomUUID());

        // Create the BpmUploadFile
        BpmUploadFileDTO bpmUploadFileDTO = bpmUploadFileMapper.toDto(bpmUploadFile);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBpmUploadFileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bpmUploadFileDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BpmUploadFile in the database
        List<BpmUploadFile> bpmUploadFileList = bpmUploadFileRepository.findAll();
        assertThat(bpmUploadFileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBpmUploadFile() throws Exception {
        int databaseSizeBeforeUpdate = bpmUploadFileRepository.findAll().size();
        bpmUploadFile.setUuid(UUID.randomUUID());

        // Create the BpmUploadFile
        BpmUploadFileDTO bpmUploadFileDTO = bpmUploadFileMapper.toDto(bpmUploadFile);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBpmUploadFileMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bpmUploadFileDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BpmUploadFile in the database
        List<BpmUploadFile> bpmUploadFileList = bpmUploadFileRepository.findAll();
        assertThat(bpmUploadFileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBpmUploadFile() throws Exception {
        // Initialize the database
        bpmUploadFileRepository.saveAndFlush(bpmUploadFile);

        int databaseSizeBeforeDelete = bpmUploadFileRepository.findAll().size();

        // Delete the bpmUploadFile
        restBpmUploadFileMockMvc
            .perform(delete(ENTITY_API_URL_ID, bpmUploadFile.getUuid().toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BpmUploadFile> bpmUploadFileList = bpmUploadFileRepository.findAll();
        assertThat(bpmUploadFileList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
