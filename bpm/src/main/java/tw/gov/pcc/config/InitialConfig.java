package tw.gov.pcc.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import tw.gov.pcc.cache.BpmCache;
import tw.gov.pcc.domain.BpmIsmsSignerOrder;
import tw.gov.pcc.domain.BpmSupervisor;
import tw.gov.pcc.domain.BpmSupervisorPrimayKey;
import tw.gov.pcc.eip.dao.EipcodeDao;
import tw.gov.pcc.repository.BpmIsmsSignerOrderRepository;
import tw.gov.pcc.repository.BpmSupervisorRepository;
import tw.gov.pcc.utils.ParameterUtil;

import java.util.*;
import java.util.stream.Collectors;

@Component
@Slf4j
public class InitialConfig implements CommandLineRunner {
    private final EipcodeDao eipcodeDao;
    private final BpmSupervisorRepository bpmSupervisorRepository;
    private final BpmIsmsSignerOrderRepository bpmIsmsSignerOrderRepository;

    public InitialConfig(EipcodeDao eipcodeDao, BpmSupervisorRepository bpmSupervisorRepository, BpmIsmsSignerOrderRepository bpmIsmsSignerOrderRepository) {
        this.eipcodeDao = eipcodeDao;
        this.bpmSupervisorRepository = bpmSupervisorRepository;
        this.bpmIsmsSignerOrderRepository = bpmIsmsSignerOrderRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        initBpmConfig();
        log.info("init bpm config success");
        initBpmSupervisor();
        log.info("init bpm supervisor success");
        initBpmIsmsSignerOrder();
        log.info("init bpm isms signer order success");

    }

    /*
     * init bpm config
     */
    private void initBpmConfig() {
        String bpmToken = "BPM_TOKEN";
        String bpmAes = "BPM_AES";
        String bpmCipher = "BPM_CIPHER";
        ParameterUtil.setToken(eipcodeDao.findByCodeKindOrderByScodeno(bpmToken).get(0).getCodename());
        ParameterUtil.setAesKey(eipcodeDao.findByCodeKindOrderByScodeno(bpmAes).get(0).getCodename());
        ParameterUtil.setBpmCipher(eipcodeDao.findByCodeKindOrderByScodeno(bpmCipher).get(0).getCodename());
    }

    private void initBpmSupervisor() {
        // init bpm supervisor
        Map<BpmSupervisorPrimayKey, BpmSupervisor> collect = bpmSupervisorRepository
            .findAll()
            .stream()
            .collect(Collectors.toMap(e -> new BpmSupervisorPrimayKey(e.getAppUnit(), e.getAppTitle()), e -> e));
        BpmCache.getBpmSupervisorHashMap().putAll(collect);

        // init supervisor judge set
        Set<String> title = new HashSet<>();
        collect.values().forEach(e -> title.add(e.getFirstLayerSupervisor()));
        collect.values().forEach(e -> title.add(e.getSecondLayerSupervisor()));
        Set<String> stringStream = title.stream().filter(Objects::nonNull).collect(Collectors.toSet());
        BpmCache.getSupervisorJudgeSet().addAll(stringStream);

    }

    private void initBpmIsmsSignerOrder() {
        // init bpm isms signer order
        List<BpmIsmsSignerOrder> bpmIsmsSignerOrders = bpmIsmsSignerOrderRepository.findAll();
        Set<String> keys = bpmIsmsSignerOrders.stream().map(BpmIsmsSignerOrder::getBpmIsmsForm).collect(Collectors.toSet());
        keys.forEach(key -> {
            log.info("init bpm isms signer order key:{}", key);
            List<BpmIsmsSignerOrder> keySignerOrders = bpmIsmsSignerOrders.stream().filter(e -> e.getBpmIsmsForm().equals(key)).collect(Collectors.toList());
            Map<String, Integer> collect = keySignerOrders.stream().collect(Collectors.toMap(BpmIsmsSignerOrder::getTaskName, BpmIsmsSignerOrder::getSort));
            BpmCache.getBpmIsmsSignerOrderMap().put(key, collect);
        });
    }
}
