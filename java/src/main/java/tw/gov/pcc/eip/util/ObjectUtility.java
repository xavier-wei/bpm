package tw.gov.pcc.eip.util;

import org.apache.commons.collections.CollectionUtils;

import java.util.Collections;

public class ObjectUtility {

    /**
     * for Fortify
     *
     * @param target
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T normalizeObject(T target) {
        if (target == null) {
            return null;
        }
        return (T) CollectionUtils.get(Collections.singletonList(target), 0);
    }
}
