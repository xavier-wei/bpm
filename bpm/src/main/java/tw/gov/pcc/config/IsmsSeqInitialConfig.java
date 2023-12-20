package tw.gov.pcc.config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import tw.gov.pcc.domain.BpmIsmsL410;
import tw.gov.pcc.domain.BpmIsmsL414;
import tw.gov.pcc.repository.BpmIsmsL410Repository;
import tw.gov.pcc.repository.BpmIsmsL414Repository;
import tw.gov.pcc.cache.BpmSeqCache;

import java.util.List;

@Component
@Slf4j
public class IsmsSeqInitialConfig implements CommandLineRunner {

    private final BpmIsmsL410Repository bpmIsmsL410Repository;
    private final BpmIsmsL414Repository bpmIsmsL414Repository;


    public IsmsSeqInitialConfig(BpmIsmsL410Repository bpmIsmsL410Repository, BpmIsmsL414Repository bpmIsmsL414Repository) {
        this.bpmIsmsL410Repository = bpmIsmsL410Repository;
        this.bpmIsmsL414Repository = bpmIsmsL414Repository;
    }


    @Override
    public void run(String... args) throws Exception {
        List<BpmIsmsL414> l414maxFormId = bpmIsmsL414Repository.getMaxFormId();
        List<BpmIsmsL410> l410maxFormId = bpmIsmsL410Repository.getMaxFormId();
        BpmSeqCache.setL414Seq((!l414maxFormId.isEmpty()) ? l414maxFormId.get(0).getFormId() : null);
        BpmSeqCache.setL410Seq((!l410maxFormId.isEmpty() )? l410maxFormId.get(0).getFormId() : null);
        log.info("init isms seq success");
    }

}
