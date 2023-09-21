package tw.gov.pcc.eip.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import tw.gov.pcc.common.helper.SpringHelper;

import java.util.Locale;

/**
 * error message utility
 *
 * @author swho
 */
public class MessageUtility {

    private static MessageSource getMessageSource() {
        return (MessageSource) SpringHelper.getBeanById("messageSource");
    }

    public static String getMessage(String key, Object... args) {
        if (StringUtils.isNotBlank(key))
            return getMessageSource().getMessage(key, args, key + " 未被定義", Locale.getDefault());
        else
            return "";
    }

    public static ObjectError getObjectError(String objectName, String key, Object... args) {
        return new ObjectError(objectName, getMessage(key, args));
    }

    public static FieldError getFieldError(String fieldName, String key, Object... args) {
        return new FieldError(fieldName, fieldName, getMessage(key, args));
    }

}
