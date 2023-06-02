package tw.gov.pcc.eip.framework.json;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Jackson Custom ObjectMapper
 * <p>
 * 註冊到 Spring MVC 的方法：
 * <pre><code>
 * &lt;mvc:annotation-driven validator=&quot;validator&quot;&gt;
 *     &lt;mvc:message-converters register-defaults=&quot;true&quot;&gt;
 *         &lt;bean class=&quot;org.springframework.http.converter.json.MappingJackson2HttpMessageConverter&quot;&gt;
 *             &lt;property name=&quot;objectMapper&quot;&gt;
 *                 &lt;bean class=&quot;tw.gov.pcc.pp.framework.json.CustomObjectMapper&quot;/&gt;
 *             &lt;/property&gt;
 *         &lt;/bean&gt;
 *     &lt;/mvc:message-converters&gt;
 * &lt;/mvc:annotation-driven&gt;
 * </code></pre>
 *
 * @author Goston
 */
public class CustomObjectMapper extends ObjectMapper {

    private static final long serialVersionUID = 7240568552948563281L;


    public CustomObjectMapper() {
    	this.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); // 防止 JSON 遇到 Bean 沒有的欄位會出 Exception（未實測）
        CustomNullStringSerializerProvider sp = new CustomNullStringSerializerProvider();
        this.setSerializerProvider(sp);
    }

}
