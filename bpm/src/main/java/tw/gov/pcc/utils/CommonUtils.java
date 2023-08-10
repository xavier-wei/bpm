package tw.gov.pcc.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CommonUtils {
    private static final Logger log = LoggerFactory.getLogger(CommonUtils.class);

    private static final String UNFILLED = "(未填)";

    public static BigDecimal empty2Zero(BigDecimal amt) {
        return (amt == null ? BigDecimal.ZERO : amt);
    }

    public static Integer empty2Zero(Integer amt) {
        return (amt == null ? 0 : amt);
    }

    public static String empty2String(String str) {
        return (str == null ? "" : str.replaceAll("\t", " "));
    }

    public static String empty2String(String str, String defaultValue) {
        return (StringUtils.isBlank(str) ? defaultValue : str.replaceAll("\t", " "));
    }

    public static String defaultString(String str) {
        return empty2String(str, UNFILLED);
    }

    /**
     * 數字格式轉換
     */
    public static String numberFormat(BigDecimal number) {
        String numString = null;
        if (number != null) {
            DecimalFormat df = new DecimalFormat("#,###");
            numString = df.format(number);
        }
        return numString;
    }

    public static String numberFormat(BigDecimal number, String pattern) {
        String numString = null;
        if (number != null) {
            if (StringUtils.isBlank(pattern)) {
                numString = numberFormat(number);
            } else {
                DecimalFormat df = new DecimalFormat(pattern);
                numString = df.format(number);
            }
        }
        return numString;
    }

    private static ObjectMapper objectMapper = null;

    public static ObjectMapper getObjectMapper() {
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
            JavaTimeModule javaTimeModule = new JavaTimeModule();
            javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern("HH:mm:ss")));
            objectMapper.registerModule(javaTimeModule);
        }
        return objectMapper;
    }

//    public static void checkFile(MultipartFile file, long fileSizeLimit, String[] fileTypeLimit) {
//        // 檢查檔案大小
//        if (file.getSize() > fileSizeLimit) {
//            throw new MessageCodeException(MessageCodes.PWC_COMMON_0011_E(fileSizeLimit / (1024 * 1024) + "MB", file.getOriginalFilename()));
//        }
//        // 檢查檔案格式
//        if (Arrays.stream(fileTypeLimit).noneMatch(type -> Objects.equals(type, file.getContentType()))) {
//            throw new MessageCodeException(MessageCodes.PWC_COMMON_0012_E(Arrays.toString(fileTypeLimit), file.getOriginalFilename()));
//        }
//    }

    public static String getEncodingByBOM(MultipartFile file) {
        String encoding = "";
        try (BufferedInputStream bin = new BufferedInputStream(file.getInputStream())) {
            int p = (bin.read() << 8) + bin.read();
            switch (p) {
                case 0xefbb:
                    encoding = "UTF-8";
                    break;
                case 0xfffe:
                    encoding = "Unicode";
                    break;
                case 0xfeff:
                    encoding = "UTF-16BE";
                    break;
                default:
                    encoding = "BIG5";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return encoding;
    }

//    public static String getEncodingByCpdetector(MultipartFile file) {
//        CodepageDetectorProxy detector = CodepageDetectorProxy.getInstance();
//        //ParsingDetector可用於檢查HTML、XML等文件的編碼，false不顯示探測過程的詳細信息。
//        //detector.add(new ParsingDetector(false));
//
//        //為了增加準確性，採用多個探測器，增加探測器不會增加執行時間。
//        //因為detector按照「誰最先返回非空的探測結果，就以該結果為準」不會繼續執行其他探測。
//
//        //如果是unicode的文件, ByteOrderMarkDetector可快速檢測
//        //detector.add(new ByteOrderMarkDetector());
//
//        //JChardetFacade封裝了由Mozilla組織提供的JChardet，它可以完成大多數文件的編碼
//        //測定。所以，一般有了這個探測器就可滿足大多數項目的要求，如果你還不放心，可以
//        //再多加幾個探測器，比如下面的ASCIIDetector、UnicodeDetector等
//        detector.add(UnicodeDetector.getInstance());
//        detector.add(ASCIIDetector.getInstance());
//        detector.add(JChardetFacade.getInstance());
//
//        String encoding = "";
//        try (InputStream inputStream = file.getInputStream()) {
//            Charset charset = detector.detectCodepage(inputStream, inputStream.available());
//            encoding = charset.name();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return encoding;
//    }

    public static String getEncodingBySourceCompare(MultipartFile file) {
        String[] encodes = {"BIG5", "MS950", "ISO-8859-1", "UTF-8"};
        try {
            String str = new String(file.getInputStream().readAllBytes());
            for (String encode : encodes) {
                byte[] strBytes = str.getBytes(encode);
                if (str.equals(new String(strBytes, encode))) {
                    return encode;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
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

    public static String recursiveRemove2Zero(String k){
        if(StringUtils.isBlank(k)){
            return "";
        }

        if(k.length() < 3){
            return k;
        }

        if("00".equals(k.substring(k.length() -2))){
            return recursiveRemove2Zero(k.substring(0, k.length() -2));
        }

        return k;
    }

    public static String secretKey(int days) {
        String secretKey = UUID.randomUUID().toString();
        int n = 3600 * days;

        //n天後時間
        Timestamp outDate = new Timestamp(System.currentTimeMillis() + n * 60 * 1000);
        long date = outDate.getTime() / 1000 * 1000;
        String key = "$" + date + "$" + secretKey;
        return key;
    }

    /**
     * Execute Command.
     *
     * @param command
     * @return
     */
    public static String executeCommand(String command) {
        StringBuffer output = new StringBuffer();
        if (SystemUtils.IS_OS_LINUX) {
            Process ps = null;
            BufferedReader reader = null;
            int exitVal = -1;
            int tries = 0;
            int maxTime = 2;
            try {
                while (tries < maxTime) {  // try 5 times
                    System.out.println("executeCommand tries=[" + tries + "]");
                    ps = Runtime.getRuntime().exec(command);
                    exitVal = ps.waitFor();
                    System.out.println("waitFor exitVal:[" + exitVal + "]");
                    if (exitVal == 0) {
                        System.out.println("--- execute complete! ---");
                        break;  // exit while loop
                    } else {
                        System.out.println("--- execute failure! ---");
                    }
                    if (tries == (maxTime - 1)) {
                        // last tries
                    } else {
                        System.out.println("@@@ sleep 5 sec start...");
                        TimeUnit.SECONDS.sleep(2); //休眠1秒
                        System.out.println("@@@ sleep 5 sec end!");
                    }
                    tries++;
                }
                reader = new BufferedReader(new InputStreamReader(ps.getInputStream()));
                String line = "";
                while ((line = reader.readLine()) != null) {
                    output.append(line + "\n");
                }
            } catch (Exception e) {
                output.setLength(0);
                output.append(e.getMessage());
                e.printStackTrace();
            } finally {
                try {
                    if (reader != null) {
                        reader.close();
                    }
                    if (ps != null) {
                        ps = null;
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return output.toString();
    }

    public static File convertMultipartToFile(MultipartFile multipartFile) throws Exception {
        try {
            File file = new File(multipartFile.getOriginalFilename());
            Files.copy(multipartFile.getInputStream(), file.toPath(), StandardCopyOption.REPLACE_EXISTING);
            return file;
        } catch (IOException e) {
            log.error("Failed to convert multipart file to file: " + e.getMessage(), e);
            throw new Exception("Failed to convert multipart file to file", e);
        }
    }

    public static <T> T requestForJson(String url, HttpMethod httpMethod,ParameterizedTypeReference<T> clazz){
        if(url == null || url.isEmpty()){
            return null;
        }

        //Prepare for request
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(new MediaType("application", "json")));

        //Send request
        ResponseEntity<T> response = new RestTemplate()
                .exchange(url, httpMethod, new HttpEntity<>(null, headers), clazz);

        if( ! HttpStatus.OK.equals(response.getStatusCode())){
            return null;
        }

        return response.getBody();
    }

    public static <T> Collection<T> concatCollections(Collection<T> list1, Collection<T> list2){
        if(CollectionUtils.isEmpty(list1) && CollectionUtils.isEmpty(list2)){
            return new ArrayList<>(0);
        }

        if(CollectionUtils.isNotEmpty(list1) && CollectionUtils.isNotEmpty(list2)){
            return Stream.concat(list1.stream(), list2.stream()).collect(Collectors.toList());
        }

        if(CollectionUtils.isNotEmpty(list1)){
            return list1;
        }

        return list2;
    }
}
