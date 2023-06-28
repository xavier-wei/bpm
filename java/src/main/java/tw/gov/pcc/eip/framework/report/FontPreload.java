package tw.gov.pcc.eip.framework.report;

import com.iisigroup.easyreport.pdf.Helper.EnvHelper;
import com.iisigroup.easyreport.pdf.Helper.PropertiesHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;
import tw.gov.pcc.eip.util.ExceptionUtility;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Objects;

/**
 * 配合pcc字形jar，進行前置作業
 *
 * @author swho
 */
@Component
@Slf4j
public class FontPreload implements InitializingBean {
    @Override
    public void afterPropertiesSet() {
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
            Resource[] resources = resolver.getResources("classpath:/fonts/**/*.ttf");
            String destDirectory = Objects.requireNonNull(PropertiesHelper.getProperties(PropertiesHelper.USER_CONFIG))
                    .getProperty(EnvHelper.FONT_PATH_KY);
            Path destDirPath = Paths.get(destDirectory);
            Files.createDirectories(destDirPath);

            long preSize = 0;
            File chkFile = new File(destDirPath.toFile(), FilenameUtils.getBaseName("size.txt"));
            if (chkFile.isFile()) {
                preSize = Integer.parseInt(StringUtils.defaultIfBlank(FileUtils.readFileToString(chkFile, StandardCharsets.UTF_8), "0"));
            }
            long totalSize = Arrays.stream(resources)
                    .mapToLong(resource -> {
                        try {
                            return resource.contentLength();
                        } catch (IOException e) {
                            throw new UncheckedIOException(e);
                        }
                    })
                    .reduce(0, Long::sum);
            if (preSize == totalSize) {
                log.info("字形初始化前置作業檢核大小相同，跳過。");
                return;
            }

            for (Resource resource : resources) {
                Path destPath = destDirPath.resolve(Paths.get(Objects.requireNonNull(resource.getFilename()))
                        .getFileName());
                IOUtils.copy(resource.getInputStream(), Files.newOutputStream(destPath));
            }

            FileUtils.writeStringToFile(chkFile, String.valueOf(totalSize), StandardCharsets.UTF_8);
            log.info("字形初始化前置作業完成。");
        } catch (Exception e) {
            log.error("字形初始化前置作業失敗！ {}", ExceptionUtility.getStackTrace(e));
        }
    }
}
