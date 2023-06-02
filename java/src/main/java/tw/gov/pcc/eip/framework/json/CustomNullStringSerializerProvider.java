package tw.gov.pcc.eip.framework.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider;
import com.fasterxml.jackson.databind.ser.SerializerFactory;
import java.io.IOException;

/**
 * Jackson 輸出 JSON 時，若字串為 <code>null</code> 將輸出 <code>"null"</code> 字串，
 * 此 SerializerProvider 用來將其輸出結果替代為空字串 (<code>""</code>)，
 * 並且不影響其它物件類別的行為，其它物件類別若為 <code>null</code>，其 JSON 將維持輸出 <code>null</code>。
 *
 * @author Goston
 */
public class CustomNullStringSerializerProvider extends DefaultSerializerProvider {

    private static final long serialVersionUID = -772975111690402443L;

    public CustomNullStringSerializerProvider() {
        super();
    }

    public CustomNullStringSerializerProvider(CustomNullStringSerializerProvider provider, SerializationConfig config,
                                              SerializerFactory jsf) {
        super(provider, config, jsf);
    }

    @Override
    public CustomNullStringSerializerProvider createInstance(SerializationConfig config,
                                                             SerializerFactory jsf) {
        return new CustomNullStringSerializerProvider(this, config, jsf);
    }

    @Override
    public JsonSerializer<Object> findNullValueSerializer(BeanProperty property) throws JsonMappingException {
        if (property.getType().getRawClass().equals(String.class)) {
            return EmptyStringSerializer.INSTANCE;
        }
        else {
            return super.findNullValueSerializer(property);
        }
    }
}

class EmptyStringSerializer extends JsonSerializer<Object> {

    public static final JsonSerializer<Object> INSTANCE = new EmptyStringSerializer();

    private EmptyStringSerializer() {
    }

    @Override
    public void serialize(Object o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
        throws IOException {
        jsonGenerator.writeString("");
    }

}
