package tw.gov.pcc.db;


import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import tw.gov.pcc.web.rest.errors.BadRequestAlertException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class Resources {
    private final ResourceLoader resourceLoader;

    public Resources(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public String readAsString(String location) {
        Resource resource = resourceLoader.getResource(location);
        try (
                InputStream inputStream = resource.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
                BufferedReader reader = new BufferedReader(inputStreamReader);
        ) {
            return reader.lines().collect(Collectors.joining(System.lineSeparator()));
        } catch (IOException e) {
//            throw new ResourceReadingException(e);
            List<String> errorCode = new ArrayList<>();
            errorCode.add("確認SQL語法");
            throw new BadRequestAlertException(" ", errorCode);
        }
    }

    public Query.Builder readAsQueryBuilder(String location, Object... parameters) {
        String string = readAsString(location);
        return Query.builder(string, parameters);
    }

    public Query.Builder readAsQueryBuilderFromResources(final Class<?> clz,String fileName, Object... parameters) {

        final String packageURL = clz.getPackage().getName().replaceAll("\\.", "/");
        final String location = "classpath:sql-templates/"+packageURL + "/" + clz.getSimpleName() + "/" + fileName;
        String string = readAsString(location);
        return Query.builder(string, parameters);
    }

    public Query readAsQuery(String location, Object... parameters) {
        return readAsQueryBuilder(location, parameters).build();
    }

//    public static class ResourceReadingException extends MessageCodeException {
//
//        public ResourceReadingException(Throwable cause) {
//            super(MessageCodes.PWC_COMMON_0006_E());
//        }
//    }

    public String readAsString(Resource resource) {
        try (
                InputStream inputStream = resource.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
                BufferedReader reader = new BufferedReader(inputStreamReader);
        ) {
            return reader.lines().collect(Collectors.joining(System.lineSeparator()));
        } catch (IOException e) {
//            throw new ResourceReadingException(e);
            List<String> errorCode = new ArrayList<>();
            errorCode.add("確認SQL語法");
            throw new BadRequestAlertException(" ", errorCode);
        }
    }

    public String readSqlFromResouces(String resourceName) {
        String location = "classpath:sql-templates/"
                + resourceName.replace(".", "/")
                + ".sql";
        return readAsString(location);
    }
}
