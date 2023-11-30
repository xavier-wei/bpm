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

    public static String normalizePath(String path) {
        return path.replaceAll("(\\\\+|/+)", Matcher.quoteReplacement(File.separator));
    }

    public static void checkFile(MultipartFile file, long fileSizeLimit, String[] fileTypeLimit) {

        // 檢查檔案大小
        if (file.getSize() > fileSizeLimit) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "檔案不可超過" + (fileSizeLimit / (1024 * 1024) + "MB / " + file.getOriginalFilename()));
        }
        // 檢查檔案格式
        String contentType = getContentType(file.getOriginalFilename(), "");
        if (Arrays.stream(fileTypeLimit).noneMatch(type -> Objects.equals(type, file.getContentType()))
            || Arrays.stream(fileTypeLimit).noneMatch(type -> Objects.equals(type, contentType))
        ) {
            MimeTypes allTypes = MimeTypes.getDefaultMimeTypes();
            List<String> fileExtensions = Arrays.stream(fileTypeLimit).map(type -> {
                try {
                    return allTypes.forName(type).getExtension().replace(".", "");
                } catch (MimeTypeException e) {
                    throw new RuntimeException(e);
                }
            }).collect(Collectors.toList());

            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, " 檔案格式錯誤,請確認是否為下列格式 " + fileExtensions + " ,錯誤檔案名稱: " + file.getOriginalFilename());
        }
    }

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
