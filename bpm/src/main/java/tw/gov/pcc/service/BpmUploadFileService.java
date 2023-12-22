package tw.gov.pcc.service;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import tw.gov.pcc.domain.BpmUploadFile;
import tw.gov.pcc.repository.BpmUploadFileRepository;
import tw.gov.pcc.service.dto.BpmUploadFileDTO;
import tw.gov.pcc.service.mapper.BpmUploadFileMapper;
import tw.gov.pcc.web.rest.errors.BadRequestAlertException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;

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
     * Delete the bpmUploadFile by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete BpmUploadFile : {}", id);
        bpmUploadFileRepository.deleteById(id);
    }

    /**
     * 將上傳的檔案資訊及檔案本身保存到資料庫中，並返回相應的DTO。
     *
     * @param bpmUploadFile 要保存的檔案資訊的實體物件。
     * @param file          要保存的檔案，以MultipartFile形式傳入。
     * @return              已保存的檔案資訊的DTO。
     * @throws IOException  如果檔案操作發生錯誤，拋出IOException。
     */
    public BpmUploadFileDTO bpmUploadFile(BpmUploadFile bpmUploadFile, MultipartFile file) throws IOException {
        // 指定保存檔案的相對路徑
        String path = "/bpmUploadFile";

        // 設定檔案資訊的創建和更新時間、使用者
        bpmUploadFile.setCreateTime(Instant.now());
        bpmUploadFile.setCreateUser("SYS");
        bpmUploadFile.setUpdateTime(Instant.now());
        bpmUploadFile.setUpdateUser("SYS");

        // 調用saveFile方法保存檔案，並獲得保存後的實體物件
        BpmUploadFile result = saveFile(bpmUploadFile, file, path);

        // 將保存後的實體物件轉換為DTO，並返回
        return bpmUploadFileMapper.toDto(result);
    }


    /**
     * 儲存上傳檔案並更新相關資訊至資料庫。
     *
     * @param bpmUploadFile   上傳檔案的實體物件，包含檔案資訊。
     * @param file            要儲存的檔案，以MultipartFile形式傳入。
     * @param functionPath    儲存檔案的相對路徑，通常表示功能模組的路徑。
     * @return                已儲存的上傳檔案的實體物件。
     * @throws IOException    如果檔案操作發生錯誤，拋出IOException。
     */
    public BpmUploadFile saveFile(BpmUploadFile bpmUploadFile, MultipartFile file, String functionPath) throws IOException {
        // 構建檔案儲存的絕對路徑
        Path path = Paths.get(dir + functionPath);
        // 如果路徑不存在，創建路徑及其父路徑
        if (!Files.exists(path)) createDirectoriesWithPermissions(path);

        // 如果上傳檔案實體中已存在檔案路徑資訊，則刪除舊檔案
        if (StringUtils.isNotBlank(bpmUploadFile.getFilePath())) {
            Path oldFilePathName = Path.of(normalizePath(bpmUploadFile.getFilePath()));
            if (Files.exists(oldFilePathName)) {
                Files.delete(oldFilePathName);
            }
        }

        // 設定上傳檔案實體的檔案名稱為原始檔案名稱
        bpmUploadFile.setFileName(file.getOriginalFilename());

        // 生成隨機UUID，並構建新的檔案名稱
        String randomUUID = UUID.randomUUID().toString();
        int index = randomUUID.lastIndexOf("-");
        String uuid = randomUUID.substring(0, index) + "." + randomUUID.substring(index + 1, randomUUID.length());
        Path filePath = Paths.get(path.normalize().toString(), uuid);
        String filePathName = filePath.toString();
        bpmUploadFile.setFilePath(filePathName);

        // 構建檔案物件，並檢查檔案是否已存在
        File file1 = filePath.toFile();
        if (file1.exists()) {
            throw new BadRequestAlertException("檔案已存在", BpmUploadFile.class.getSimpleName(), "");
        }

        // 將上傳的檔案儲存到目標檔案
        file.transferTo(file1.getAbsoluteFile());

        // 設定檔案權限
        setPermission(file1);

        log.info("BpmUploadFileService.java - saveFile - 156 :: " + bpmUploadFile );

        return  bpmUploadFileRepository.save(bpmUploadFile);
    }

    /**
     * 根據作業系統不同，創建包含權限的目錄。
     *
     * @param path 要創建的目錄的路徑。
     * @throws IOException 如果發生檔案操作錯誤，拋出IOException。
     */
    public static void createDirectoriesWithPermissions(Path path) throws IOException {

        // 檢查作業系統類型，以選擇適當的權限設定
        if (SystemUtils.IS_OS_LINUX) {
            // 如果是Linux系統，設定POSIX權限
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
            // 將權限轉換為檔案屬性，並創建目錄
            FileAttribute<Set<PosixFilePermission>> fileAttributes = PosixFilePermissions.asFileAttribute(permissions);
            Files.createDirectories(path, fileAttributes);
            // 設定目錄的POSIX權限
            Files.setPosixFilePermissions(path, permissions);
        } else if (SystemUtils.IS_OS_WINDOWS) {
            // 如果是Windows系統，直接創建目錄
            Files.createDirectories(path);
        }
    }

    /**
     * 將檔案路徑正規化，替換反斜線和正斜線為標準檔案分隔符號。
     *
     * @param path 要正規化的檔案路徑。
     * @return 正規化後的檔案路徑。
     */
    public static String normalizePath(String path) {
        // 使用正規表達式替換所有反斜線和正斜線為標準檔案分隔符號
        return path.replaceAll("(\\\\+|/+)", Matcher.quoteReplacement(File.separator));
    }

    /**
     * 根據作業系統不同，設定檔案的權限。
     *
     * @param file 要設定權限的檔案。
     * @throws IOException 如果發生檔案操作錯誤，拋出IOException。
     */
    public static void setPermission(File file) throws IOException {
        // 檢查作業系統類型，以選擇適當的權限設定
        if (SystemUtils.IS_OS_LINUX) {
            // 如果是Linux系統，設定POSIX權限
            Set<PosixFilePermission> permissions = new HashSet<>();
            permissions.add(PosixFilePermission.OWNER_READ);
            permissions.add(PosixFilePermission.OWNER_WRITE);
            permissions.add(PosixFilePermission.GROUP_READ);
            permissions.add(PosixFilePermission.GROUP_WRITE);
            permissions.add(PosixFilePermission.OTHERS_READ);
            // 將權限設定應用到檔案
            Files.setPosixFilePermissions(file.toPath(), permissions);
        } else if (SystemUtils.IS_OS_WINDOWS) {
            // 如果是Windows系統，設定檔案可讀可寫
            file.setWritable(true, false);
            file.setReadable(true, false);
        }
    }

    /**
     * 儲存相片檔案及相關資訊到資料庫。
     *
     * @param dto            包含相片檔案資訊的DTO列表。
     * @param appendixFiles  包含實際相片檔案的MultipartFile列表。
     * @param formId         表單ID，關聯相片檔案與表單。
     */
    public void savePhoto(List<BpmUploadFileDTO> dto, List<MultipartFile> appendixFiles, String formId) {
        // 遍歷相片檔案列表
        if (appendixFiles != null) {
            for (int i = 0; i < appendixFiles.size(); i++) {
                // 設定相片檔案相關資訊
                dto.get(i).setFormId(formId);
                dto.get(i).setFileName(appendixFiles.get(i).getName());
                try {
                    // 將DTO轉換為實體物件並儲存相片檔案
                    bpmUploadFile(bpmUploadFileMapper.toEntity(dto.get(i)), appendixFiles.get(i));
                } catch (IOException e) {
                    // 處理檔案儲存失敗的異常
                    log.error("檔案存取失敗 :{}", e.getMessage());
                    throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "檔案儲存失敗");
                }
            }
        }
    }
}
