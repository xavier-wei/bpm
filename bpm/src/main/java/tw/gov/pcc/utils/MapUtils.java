package tw.gov.pcc.utils;

import java.util.HashMap;
import java.util.Map;

public class MapUtils {

    public  Map<String, Object> getNewMap( Map<String, Object> map) {

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

    //將有底線分割字母轉成駝峰
    public static String toCamelCase(String input) {
        StringBuilder camelCase = new StringBuilder();
        boolean nextUpperCase = false;

        for (int i = 0; i < input.length(); i++) {
            char currentChar = input.charAt(i);

            if (currentChar == '_') {
                nextUpperCase = true;
            } else {
                if (nextUpperCase) {
                    camelCase.append(Character.toUpperCase(currentChar));
                    nextUpperCase = false;
                } else {
                    camelCase.append(Character.toLowerCase(currentChar));
                }
            }
        }
        return camelCase.toString();
    }

}
