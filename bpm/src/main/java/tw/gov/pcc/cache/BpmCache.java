package tw.gov.pcc.cache;

import tw.gov.pcc.domain.BpmSupervisor;
import tw.gov.pcc.domain.BpmSupervisorPrimayKey;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class BpmCache {
    public static Map<BpmSupervisorPrimayKey, BpmSupervisor> bpmSupervisorHashMap = new HashMap<>();
    public static Set<String> supervisorJudgeSet = new HashSet<>();
    private BpmCache() {
    }

}
