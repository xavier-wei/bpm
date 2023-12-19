package tw.gov.pcc.config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import tw.gov.pcc.repository.BpmIsmsL410Repository;
import tw.gov.pcc.repository.BpmIsmsL414Repository;
import tw.gov.pcc.cache.BpmSeqCache;

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
        BpmSeqCache.setL414Seq((!bpmIsmsL414Repository.getMaxFormId().isEmpty()) ? bpmIsmsL414Repository.getMaxFormId().get(0).getFormId() : null);
        BpmSeqCache.setL410Seq((!bpmIsmsL410Repository.getMaxFormId().isEmpty() )? bpmIsmsL410Repository.getMaxFormId().get(0).getFormId() : null);
        log.info("init isms seq success");
    }

}
