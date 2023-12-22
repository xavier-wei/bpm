package tw.gov.pcc.utils;

import java.util.HashMap;
import java.util.Map;

public class MapUtils {

    /**
     * 將給定的Map中的所有鍵轉換為駝峰式（Camel Case）並返回新的Map。
     *
     * @param map 要轉換的原始Map。
     * @return 包含駝峰式鍵的新Map。
     */
    public  Map<String, Object> getNewMap( Map<String, Object> map) {
        // 創建新的Map來存儲駝峰式鍵值對
        Map<String, Object> camelCaseMap = new HashMap<>();

        //過濾所有key
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String originalKey = entry.getKey();
            Object value = entry.getValue();

            //將map的key轉成小寫
            String lowerCaseKey = originalKey.toLowerCase();

            //將小寫轉成駝峰
            String camelCaseKey = toCamelCase(lowerCaseKey);

            //全部完成後存到新的map
            camelCaseMap.put(camelCaseKey, value);
        }

        return camelCaseMap;
    }

    /**
     * 將具有底線分隔的字母組轉換為駝峰式表示法。
     *
     * @param input 要轉換的字串。
     * @return 駝峰式表示法的字串。
     */
    public static String toCamelCase(String input) {
        StringBuilder camelCase = new StringBuilder();
        boolean nextUpperCase = false;

        // 遍歷輸入字串的每個字符
        for (int i = 0; i < input.length(); i++) {
            char currentChar = input.charAt(i);

            // 如果是底線，則設置下一個字符為大寫
            if (currentChar == '_') {
                nextUpperCase = true;
            } else {
                // 如果下一個字符應該是大寫，則將當前字符轉換為大寫
                if (nextUpperCase) {
                    camelCase.append(Character.toUpperCase(currentChar));
                    nextUpperCase = false;
                } else {
                    // 否則將字符轉換為小寫
                    camelCase.append(Character.toLowerCase(currentChar));
                }
            }
        }
        return camelCase.toString();
    }

}
