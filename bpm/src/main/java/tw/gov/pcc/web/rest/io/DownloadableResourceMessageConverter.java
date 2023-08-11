package tw.gov.pcc.web.rest.io;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.ResourceHttpMessageConverter;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * 跟 {@link ResourceHttpMessageConverter} 唯一的不同就是多寫入 {@code Content-Disposition}。
 */
public class DownloadableResourceMessageConverter extends ResourceHttpMessageConverter {

    @Override
    protected boolean supports(Class<?> clazz) {
        return DownloadableResource.class.isAssignableFrom(clazz);
    }

    /**
     * 跟 {@link ResourceHttpMessageConverter} 唯一的不同就是多寫入 {@code Content-Disposition}。
     */
    @Override
    protected void writeInternal(Resource resource, HttpOutputMessage outputMessage)
            throws IOException {
        String filename = URLEncoder.encode(Objects.requireNonNull(resource.getFilename()), StandardCharsets.UTF_8);
        outputMessage.getHeaders().add("Content-Disposition",
                "attachment; filename=\"" + filename + "\" ; filename*=UTF-8''" + filename);
        super.writeInternal(resource, outputMessage);
    }
}
