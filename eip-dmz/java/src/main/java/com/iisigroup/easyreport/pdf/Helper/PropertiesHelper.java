package com.iisigroup.easyreport.pdf.Helper;

import tw.gov.pcc.eip.util.ObjectUtility;

import java.io.InputStream;
import java.util.Properties;

/**
 * 讀取 Properties 檔用<br>
 * 僅供 Easy Report 內部使用
 *
 * @author Goston
 */
public class PropertiesHelper {
    public static final String DEFAULT_CONFIG = "/com/iisigroup/easyreport/easyreport-default-config.properties";
    public static final String USER_CONFIG = "/easyreport.properties";

    public static Properties getProperties(String resource) throws Exception {
        InputStream inputStream = PropertiesHelper.class.getResourceAsStream(resource);

        try {
            if (inputStream != null) {
                Properties props = new Properties();
                props.load(inputStream);
                return ObjectUtility.normalizeObject(props);
            }
        }
        finally {
            if (inputStream != null)
                inputStream.close();
        }

        return null;
    }
}
