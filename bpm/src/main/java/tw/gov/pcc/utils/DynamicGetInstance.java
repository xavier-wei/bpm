package tw.gov.pcc.utils;

import org.springframework.stereotype.Component;

@Component
public class DynamicGetInstance {

    public static <T> T getEipBpmIsmsDTO(String formName) {
        try {
            Class clz = Class.forName("EipBpmIsms" + formName + "DTO");
            return (T) clz.newInstance();

        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }



}
