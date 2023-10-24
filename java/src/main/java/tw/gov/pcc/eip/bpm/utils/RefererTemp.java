package tw.gov.pcc.eip.bpm.utils;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class RefererTemp {
    private RefererTemp() {
    }

    public static final Map<String, String> REFERER_MAP = new HashMap<>();
}
