package tw.gov.pcc.flowable.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.sql.DataSource;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Properties;

@Configuration
@Slf4j
public class DataSourceConfig {

    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Value("${setting.path}")
    private String settingPath;

    @Value("${setting.decode}")
    private String decodePath;

    private String getSettingDev() {
        Resource resource = new ClassPathResource(settingPath);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))
        ) {
            return reader.readLine().trim();
        } catch (IOException e) {
            return null;
        }
    }

    private String getSettingWildfly() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(settingPath), StandardCharsets.UTF_8))) {
            return decode(reader.readLine().trim());
        } catch (IOException e) {
            return null;
        }
    }



    @Bean
    @Profile("dev")
    public DataSource dataSourceDev() {
        log.info("profile:{}", System.getProperty("spring.profiles.active"));
        log.info("dbUrl:{}",dbUrl);
        HikariConfig config = getHikariConfig(getSettingDev());

        return new HikariDataSource(config);
    }

    @Bean
    @Profile("prod || prod_dr || prod_pra")
    public DataSource dataSourceWildfly() {
        log.info("profile:{}", System.getProperty("spring.profiles.active"));
        log.info("dbUrl:{}",dbUrl);
        log.info("dataSourceWildfly:{}", getSettingWildfly());
        HikariConfig config = getHikariConfig(getSettingWildfly());

        return new HikariDataSource(config);
    }

    private HikariConfig getHikariConfig(String pw) {
        HikariConfig config = new HikariConfig();
        // Database configuration
        config.setJdbcUrl(dbUrl);
        config.setUsername("eip");
        config.setPassword(pw);

        // HikariCP configuration
        config.setPoolName("Hikari");
        config.setAutoCommit(false);
        config.setConnectionTimeout(30000);
        config.setMaximumPoolSize(10);

        // dataSource properties
        Properties dsProperties = new Properties();
        dsProperties.put("cachePrepStmts", "true");
        dsProperties.put("prepStmtCacheSize", "250");
        dsProperties.put("prepStmtCacheSqlLimit", "2048");
        dsProperties.put("useServerPrepStmts", "true");
        config.setDataSourceProperties(dsProperties);
        return config;
    }

    private String decode(String encryptedText) {
        String[] decodeInfo;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(decodePath), StandardCharsets.UTF_8))) {
            decodeInfo = reader.readLine().trim().split(",");
            log.info("decodeInfo:{}",decodeInfo[0]);
            log.info("decodeInfo:{}",decodeInfo[1]);
        } catch (IOException e) {
            return null;
        }
        String key = decodeInfo[1];
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");
        String cipherInstance = decodeInfo[0];
        Cipher cipher;
        byte[] original;
        try {
            cipher = Cipher.getInstance(cipherInstance);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(new byte[16]));
            original = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
            return new String(original, StandardCharsets.UTF_8);
        }catch (Exception e){
            log.error("decode error",e);
            throw new RuntimeException();
        }
    }
}