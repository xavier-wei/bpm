package tw.gov.pcc.eip.framework.domain;

import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 儲存庫
 */
@Component
public class FileStore {

    private final Map<String, byte[]> ByteArrayMap = createLRUMap(3);

    public static <K, V> Map<K, V> createLRUMap(final int maxEntries) {
        return new LinkedHashMap<K, V>(maxEntries * 10 / 7, 0.7f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
                return size() > maxEntries;
            }
        };
    }

    public void put(String filename, byte[] bytes) {
        ByteArrayMap.put(filename, bytes);
    }

    public byte[] get(String filename) {
        return ByteArrayMap.get(filename);
    }

    public void remove(String filename) {
        ByteArrayMap.remove(filename);
    }
}
