package tw.gov.pcc.utils;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValue;

import java.util.Map;
import java.util.Objects;

/**
 * 物件讀取或設定屬性工具類
 **/
public final class PwcPropertyUtils {

    private static final Logger LOG = LoggerFactory.getLogger(PwcPropertyUtils.class);

    private PwcPropertyUtils() {
        throw new AssertionError();
    }

    /**
     * 設定物件指定屬性名稱的值
     * @param  dataObj 要設定值的物件
     * @param  key 屬性名稱
     * @param  value 要設定的值
     **/
    public static void setProperty(final Object dataObj, final String key, final Object value) {
        try {
            final BeanWrapper objectWrapper = new BeanWrapperImpl(dataObj);
            objectWrapper.setPropertyValue(new PropertyValue(key, value));
        } catch (final BeansException e) {
            LOG.trace("{}", e.getMessage());
        }
    }

    /**
     * 讀取物件指定屬性名稱的值
     * @param  dataObj 要讀取值的物件
     * @param  key 屬性名稱
     **/
    public static Object readProperty(final Object dataObj, final String key) {
        if (dataObj == null || StringUtils.isBlank(key)) {
            return dataObj;
        }

        if (dataObj instanceof Map) {
            return readMap((Map<?, ?>) dataObj, key);
        }

        Object property = null;
        try {
            final BeanWrapper objectWrapper = new BeanWrapperImpl(dataObj);
            property = objectWrapper.getPropertyValue(key);
        } catch (final BeansException e) {
            LOG.warn(e.getMessage());
        }
        return property;
    }

    /**
     * 讀取物件指定屬性名稱的值轉成字串
     * @param  dataObj 要讀取值的物件
     * @param  key 屬性名稱
     **/
    public static String readPropertyToString(final Object dataObj, final String key) {
        return Objects.toString(readProperty(dataObj, key), "");
    }

    private static Object readMap(final Map<?, ?> map, final String key) {
        if (StringUtils.isBlank(key)) {
            return map;
        }
        return map.get(key);
    }

    /**
     * 讀取物件指定屬性名稱的值
     * @param  bean 要讀取值的物件
     * @param  name 屬性名稱
     **/
    public static Object getProperty(final Object bean, final String name) {
        try {
            return PropertyUtils.getProperty(bean, name);
        } catch (Exception e) {
            LOG.warn(e.getMessage());
            return null;
        }
    }
}
