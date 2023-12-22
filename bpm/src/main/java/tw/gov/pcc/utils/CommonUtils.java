package tw.gov.pcc.utils;

import org.apache.tika.mime.MimeTypeException;
import org.apache.tika.mime.MimeTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import tw.gov.pcc.service.BpmUploadFileService;
import tw.gov.pcc.web.rest.errors.BadRequestAlertException;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

public class CommonUtils {

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
     * 檢查上傳的檔案是否符合限制的大小和格式。
     *
     * @param file           要檢查的檔案，以MultipartFile形式傳入。
     * @param fileSizeLimit  允許的檔案大小上限，以字節為單位。
     * @param fileTypeLimit  允許的檔案格式，以MIME類型的字符串形式傳入。
     * @throws ResponseStatusException 如果檔案大小或格式不符合限制，拋出ResponseStatusException。
     */
    public static void checkFile(MultipartFile file, long fileSizeLimit, String[] fileTypeLimit) {

        // 檢查檔案大小是否超過限制
        if (file.getSize() > fileSizeLimit) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "檔案不可超過" + (fileSizeLimit / (1024 * 1024) + "MB / " + file.getOriginalFilename()));
        }
        // 檢查檔案格式是否符合限制
        String contentType = getContentType(file.getOriginalFilename(), "");
        if (Arrays.stream(fileTypeLimit).noneMatch(type -> Objects.equals(type, file.getContentType()))
            || Arrays.stream(fileTypeLimit).noneMatch(type -> Objects.equals(type, contentType))
        ) {
            // 獲取允許的檔案格式的檔案擴展名
            MimeTypes allTypes = MimeTypes.getDefaultMimeTypes();
            List<String> fileExtensions = Arrays.stream(fileTypeLimit).map(type -> {
                try {
                    return allTypes.forName(type).getExtension().replace(".", "");
                } catch (MimeTypeException e) {
                    throw new RuntimeException(e);
                }
            }).collect(Collectors.toList());

            // 檔案格式不符合限制，拋出ResponseStatusException
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, " 檔案格式錯誤,請確認是否為下列格式 " + fileExtensions + " ,錯誤檔案名稱: " + file.getOriginalFilename());
        }
    }

    /**
     * 獲取檔案的MIME類型。
     *
     * @param filename 要獲取MIME類型的檔案名稱。
     * @param orElse   如果無法獲取MIME類型，則返回的預設值。
     * @return 檔案的MIME類型。
     */
    private static String getContentType(String filename, String orElse) {
        return Optional.ofNullable(filename)
            .map(s -> Path.of(s))
            .map(p -> {
                try {
                    return Files.probeContentType(p);
                } catch (Throwable e) {
                    return null;
                }
            })
            .orElse(orElse);
    }
}
