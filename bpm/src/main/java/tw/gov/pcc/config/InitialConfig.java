package tw.gov.pcc.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import tw.gov.pcc.cache.BpmCache;
import tw.gov.pcc.domain.BpmSupervisor;
import tw.gov.pcc.domain.BpmSupervisorPrimayKey;
import tw.gov.pcc.eip.dao.EipcodeDao;
import tw.gov.pcc.repository.BpmSupervisorRepository;
import tw.gov.pcc.utils.ParameterUtil;

import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Slf4j
public class InitialConfig implements CommandLineRunner {
    private final EipcodeDao eipcodeDao;
    private final BpmSupervisorRepository bpmSupervisorRepository;


    public InitialConfig(EipcodeDao eipcodeDao, BpmSupervisorRepository bpmSupervisorRepository) {
        this.eipcodeDao = eipcodeDao;
        this.bpmSupervisorRepository = bpmSupervisorRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        initBpmConfig();
        log.info("init bpm config success");
        initBpmSupervisor();
        log.info("init bpm supervisor success");
    }

    private void initBpmConfig() {
        String bpmToken = "BPM_TOKEN";
        String bpmAes = "BPM_AES";
        String bpmCipher = "BPM_CIPHER";
        ParameterUtil.setToken(eipcodeDao.findByCodeKindOrderByScodeno(bpmToken).get(0).getCodename());
        ParameterUtil.setAesKey(eipcodeDao.findByCodeKindOrderByScodeno(bpmAes).get(0).getCodename());
        ParameterUtil.setBpmCipher(eipcodeDao.findByCodeKindOrderByScodeno(bpmCipher).get(0).getCodename());
    }

    private void initBpmSupervisor() {
        Map<BpmSupervisorPrimayKey, BpmSupervisor> collect = bpmSupervisorRepository
            .findAll()
            .stream()
            .collect(Collectors.toMap(e -> new BpmSupervisorPrimayKey(e.getAppUnit(), e.getAppTitle()), e -> e));
        BpmCache.getBpmSupervisorHashMap().putAll(collect);

        Set<String> title = new HashSet<>();
        collect.values().forEach(e -> title.add(e.getFirstLayerSupervisor()));
        collect.values().forEach(e -> title.add(e.getSecondLayerSupervisor()));
        Set<String> stringStream = title.stream().filter(Objects::nonNull).collect(Collectors.toSet());
        BpmCache.getSupervisorJudgeSet().addAll(stringStream);

    }
}
