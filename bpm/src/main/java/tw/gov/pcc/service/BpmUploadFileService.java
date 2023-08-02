package tw.gov.pcc.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.time.Instant;
import java.util.*;
import java.util.regex.Matcher;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import tw.gov.pcc.domain.BpmUploadFile;
import tw.gov.pcc.repository.BpmUploadFileRepository;
import tw.gov.pcc.service.dto.BpmUploadFileDTO;
import tw.gov.pcc.service.mapper.BpmUploadFileMapper;
import tw.gov.pcc.web.rest.errors.BadRequestAlertException;

/**
 * Service Implementation for managing {@link BpmUploadFile}.
 */
@Service
@Transactional
public class BpmUploadFileService {

    private final String dir = File.separator + "mnt" + File.separator + "stsdat" + File.separator + "bpm" + File.separator;
    private final Logger log = LoggerFactory.getLogger(BpmUploadFileService.class);

    private final BpmUploadFileRepository bpmUploadFileRepository;

    private final BpmUploadFileMapper bpmUploadFileMapper;

    public BpmUploadFileService(BpmUploadFileRepository bpmUploadFileRepository, BpmUploadFileMapper bpmUploadFileMapper) {
        this.bpmUploadFileRepository = bpmUploadFileRepository;
        this.bpmUploadFileMapper = bpmUploadFileMapper;
    }

    /**
     * Save a bpmUploadFile.
     *
     * @param bpmUploadFileDTO the entity to save.
     * @return the persisted entity.
     */
    public BpmUploadFileDTO save(BpmUploadFileDTO bpmUploadFileDTO) {
        log.debug("Request to save BpmUploadFile : {}", bpmUploadFileDTO);
        BpmUploadFile bpmUploadFile = bpmUploadFileMapper.toEntity(bpmUploadFileDTO);
        bpmUploadFile = bpmUploadFileRepository.save(bpmUploadFile);
        return bpmUploadFileMapper.toDto(bpmUploadFile);
    }

    /**
     * Partially update a bpmUploadFile.
     *
     * @param bpmUploadFileDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<BpmUploadFileDTO> partialUpdate(BpmUploadFileDTO bpmUploadFileDTO) {
        log.debug("Request to partially update BpmUploadFile : {}", bpmUploadFileDTO);

        return bpmUploadFileRepository
                .findById(bpmUploadFileDTO.getUuid())
                .map(existingBpmUploadFile -> {
                    bpmUploadFileMapper.partialUpdate(existingBpmUploadFile, bpmUploadFileDTO);

                    return existingBpmUploadFile;
                })
                .map(bpmUploadFileRepository::save)
                .map(bpmUploadFileMapper::toDto);
    }

    /**
     * Get all the bpmUploadFiles.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<BpmUploadFileDTO> findAll() {
        log.debug("Request to get all BpmUploadFiles");
        return bpmUploadFileRepository.findAll().stream().map(bpmUploadFileMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one bpmUploadFile by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BpmUploadFileDTO> findOne(UUID id) {
        log.debug("Request to get BpmUploadFile : {}", id);
        return bpmUploadFileRepository.findById(id).map(bpmUploadFileMapper::toDto);
    }

    /**
     * Delete the bpmUploadFile by id.
     *
     * @param id the id of the entity.
     */
    public void delete(UUID id) {
        log.debug("Request to delete BpmUploadFile : {}", id);
        bpmUploadFileRepository.deleteById(id);
    }

    public BpmUploadFileDTO bpmUploadFile(BpmUploadFile bpmUploadFile, MultipartFile file) throws IOException {

        String path = "/bpmUploadFile";

        bpmUploadFile.setCreateTime(Instant.now());
        bpmUploadFile.setCreateUser("SYS");
        bpmUploadFile.setUpdateTime(Instant.now());
        bpmUploadFile.setUpdateUser("SYS");

        BpmUploadFile result = saveFile(bpmUploadFile, file, path);

        return bpmUploadFileMapper.toDto(result);
    }


    public BpmUploadFile saveFile(BpmUploadFile bpmUploadFile, MultipartFile file, String functionPath) throws IOException {


        Path path = Paths.get(dir + functionPath);
        if (!Files.exists(path)) createDirectoriesWithPermissions(path);

        if (StringUtils.isNotBlank(bpmUploadFile.getFilePath())) {
            Path oldFilePathName = Path.of(normalizePath(bpmUploadFile.getFilePath()));
            if (Files.exists(oldFilePathName)) {
                Files.delete(oldFilePathName);
            }
        }

        if (StringUtils.isBlank(bpmUploadFile.getFileName())) {
            bpmUploadFile.setFileName(file.getName());
        }
        String randomUUID = UUID.randomUUID().toString();
        int index = randomUUID.lastIndexOf("-");
        String uuid = randomUUID.substring(0, index) + "." + randomUUID.substring(index + 1, randomUUID.length());
        Path filePath = Paths.get(path.normalize().toString(), uuid);
        String filePathName = filePath.toString();
        bpmUploadFile.setFilePath(filePathName);

        File file1 = filePath.toFile();
        if (file1.exists()) {
            throw new BadRequestAlertException("檔案已存在", BpmUploadFile.class.getSimpleName(), "");
        }
        file.transferTo(file1.getAbsoluteFile());
        setPermission(file1);

        return  bpmUploadFileRepository.save(bpmUploadFile);
    }

    public static void createDirectoriesWithPermissions(Path path) throws IOException {
        if (SystemUtils.IS_OS_LINUX) {
            Set<PosixFilePermission> permissions = new HashSet<>();
            permissions.add(PosixFilePermission.OWNER_EXECUTE);
            permissions.add(PosixFilePermission.OWNER_READ);
            permissions.add(PosixFilePermission.OWNER_WRITE);
            permissions.add(PosixFilePermission.GROUP_EXECUTE);
            permissions.add(PosixFilePermission.GROUP_READ);
            permissions.add(PosixFilePermission.GROUP_WRITE);
            permissions.add(PosixFilePermission.OTHERS_EXECUTE);
            permissions.add(PosixFilePermission.OTHERS_READ);
            permissions.add(PosixFilePermission.OTHERS_WRITE);
            FileAttribute<Set<PosixFilePermission>> fileAttributes = PosixFilePermissions.asFileAttribute(permissions);
            Files.createDirectories(path, fileAttributes);
            Files.setPosixFilePermissions(path, permissions);
        } else if (SystemUtils.IS_OS_WINDOWS) {
            Files.createDirectories(path);
        }
    }

    public static String normalizePath(String path) {
        return path.replaceAll("(\\\\+|/+)", Matcher.quoteReplacement(File.separator));
    }

    public static void setPermission(File file) throws IOException {
        if (SystemUtils.IS_OS_LINUX) {
            Set<PosixFilePermission> permissions = new HashSet<>();
            permissions.add(PosixFilePermission.OWNER_READ);
            permissions.add(PosixFilePermission.OWNER_WRITE);
            permissions.add(PosixFilePermission.GROUP_READ);
            permissions.add(PosixFilePermission.GROUP_WRITE);
            permissions.add(PosixFilePermission.OTHERS_READ);
            Files.setPosixFilePermissions(file.toPath(), permissions);
        } else if (SystemUtils.IS_OS_WINDOWS) {
            file.setWritable(true, false);
            file.setReadable(true, false);
        }
    }
}
