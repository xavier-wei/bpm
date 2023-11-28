package tw.gov.pcc.cache;

import lombok.Getter;
import tw.gov.pcc.domain.BpmSupervisor;
import tw.gov.pcc.domain.BpmSupervisorPrimayKey;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class BpmCache {

    // 判斷上級用快取
    @Getter
    private static final Map<BpmSupervisorPrimayKey, BpmSupervisor> bpmSupervisorHashMap = new HashMap<>();

    // 判斷是否長官用快取(查詢下屬時)
    @Getter
    private static final Set<String> supervisorJudgeSet = new HashSet<>();

    // 判斷排序用快取
    @Getter
    private static final Map<String,Map<String,Integer>> bpmIsmsSignerOrderMap = new HashMap<>();
    private BpmCache() {
    }
}
