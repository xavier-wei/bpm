package tw.gov.pcc.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import tw.gov.pcc.domain.BpmAbnormalSupervisor;
import tw.gov.pcc.domain.User;
import tw.gov.pcc.repository.BpmSupervisorRepository;
import tw.gov.pcc.repository.SupervisorRepository;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class SupervisorService {
    private static final String NO_SIGN = "NO_SIGN";
    private static final String POSNAME = "POSNAME";
    private static final String F1_POSNAME = "F1_POSNAME";
    private static final String F2_POSNAME = "F2_POSNAME";
    private static final String F1_ACCOUNT = "F1_ACCOUNT";
    private static final String F2_ACCOUNT = "F2_ACCOUNT";
    private static final String F3_ACCOUNT = "F3_ACCOUNT";
    private static final String DECISION = "decision";
    private static final String SECTION_CHIEF = "sectionChief";
    private static final String DIRECTOR = "director";
    private final SupervisorRepository supervisorRepository;
    private final BpmSupervisorRepository bpmSupervisorRepository;

    private final BpmAbnormalSupervisorService bpmAbnormalSupervisorService;

    public SupervisorService(SupervisorRepository supervisorRepository, BpmSupervisorRepository bpmSupervisorRepository, BpmAbnormalSupervisorService bpmAbnormalSupervisorService) {
        this.supervisorRepository = supervisorRepository;
        this.bpmSupervisorRepository = bpmSupervisorRepository;
        this.bpmAbnormalSupervisorService = bpmAbnormalSupervisorService;
    }


    public void setSupervisor(Map<String, Object> variables, String id, User user) {


        String sectionChief;
        String director;

        // 查詢此人是否為特例
        BpmAbnormalSupervisor bpmAbnormalSupervisor = bpmAbnormalSupervisorService.findById(id).orElse(null);
        if (bpmAbnormalSupervisor != null) {

            log.info("申請者為特例，id: {}", id);
            sectionChief = bpmAbnormalSupervisor.getF1Account();
            director = bpmAbnormalSupervisor.getF2Account();
        } else {

            List<Map<String, Object>> maps = supervisorRepository.executeQuery(user.getOrgId(), id);
            // 三種情況 POSNAME為科員、科長、處長(或更高階主管情形)
            Map<String, Object> positionData;
            try {
                positionData = maps.get(0);
            } catch (IndexOutOfBoundsException e) {
                log.error("{}", "申請人員未建檔");
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "申請人員未建檔");
            }
            log.info("申請者職稱：{}", positionData.get(POSNAME));

            if ("處長".equals(positionData.get(F1_POSNAME)) || "主任".equals(positionData.get(F1_POSNAME)) || "副處長".equals(positionData.get(F1_POSNAME))) {
                // 查到第一層長官為以上三種，表示自己為科長級或副處長級或室級成員
                sectionChief = NO_SIGN;
                director = "副處長".equals(positionData.get(F1_POSNAME)) ? (String) positionData.get(F2_ACCOUNT) : (String) positionData.get(F1_ACCOUNT);

            } else if ("科長".equals(positionData.get(F1_POSNAME)) || (null == positionData.get(F1_POSNAME) && !"處長".equals(positionData.get(POSNAME)))) {
                // 查到第一層長官為科長，表示自己為科員或同級

                sectionChief = (null == positionData.get(F1_POSNAME)) ? NO_SIGN : (String) positionData.get(F1_ACCOUNT);
                director = (String) (("副處長".equals(positionData.get(F2_POSNAME))) ? positionData.get(F3_ACCOUNT) : positionData.get(F2_ACCOUNT));

            } else {
                sectionChief = NO_SIGN;
                director = NO_SIGN;
            }
        }
        variables.put(SECTION_CHIEF, sectionChief);
        variables.put(DIRECTOR, director);
        if (NO_SIGN.equals(sectionChief)) variables.put(SECTION_CHIEF + DECISION, "1");
        if (NO_SIGN.equals(director)) variables.put(DIRECTOR + DECISION, "1");

    }

    // 利用申請者職稱及單位查出其上級簽核者
//    public void setSupervisor(Map<String, Object> variables, String id, User user,String appTitle) {
//
//
//        bpmSupervisorRepository.findById(new BpmSupervisorPrimayKey());
//    }

}
