package tw.gov.pcc.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tw.gov.pcc.domain.BpmSpecialSupervisor;
import tw.gov.pcc.domain.BpmSupervisor;
import tw.gov.pcc.domain.BpmSupervisorPrimayKey;
import tw.gov.pcc.domain.User;
import tw.gov.pcc.repository.BpmSupervisorRepository;
import tw.gov.pcc.repository.SupervisorRepository;

import java.util.Map;
import java.util.Optional;

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
    private final UserService userService;
    private final SupervisorRepository supervisorRepository;
    private final BpmSupervisorRepository bpmSupervisorRepository;
    private final BpmSpecialSupervisorService bpmSpecialSupervisorService;

    public SupervisorService(UserService userService, SupervisorRepository supervisorRepository, BpmSupervisorRepository bpmSupervisorRepository, BpmSpecialSupervisorService bpmSpecialSupervisorService) {
        this.userService = userService;
        this.supervisorRepository = supervisorRepository;
        this.bpmSupervisorRepository = bpmSupervisorRepository;
        this.bpmSpecialSupervisorService = bpmSpecialSupervisorService;
    }
    //   這個方法是之前判斷的方式，有需要再切回來
//    public void setSupervisor(Map<String, Object> variables, String id, User user) {
//
//
//        String sectionChief;
//        String director;
//
//        // 查詢此人是否為特例
//        BpmSpecialSupervisor bpmSpecialSupervisor = bpmSpecialSupervisorService.findById(id).orElse(null);
//        if (bpmSpecialSupervisor != null) {
//
//            log.info("申請者為特例，id: {}", id);
//            sectionChief = bpmSpecialSupervisor.getF1Account();
//            director = bpmSpecialSupervisor.getF2Account();
//        } else {
//
//            List<Map<String, Object>> maps = supervisorRepository.executeQuery(user.getOrgId(), id);
//            // 三種情況 POSNAME為科員、科長、處長(或更高階主管情形)
//            Map<String, Object> positionData;
//            try {
//                positionData = maps.get(0);
//            } catch (IndexOutOfBoundsException e) {
//                log.error("{}", "申請人員未建檔");
//                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "申請人員未建檔");
//            }
//            log.info("申請者職稱：{}", positionData.get(POSNAME));
//
//            if ("處長".equals(positionData.get(F1_POSNAME)) || "主任".equals(positionData.get(F1_POSNAME)) || "副處長".equals(positionData.get(F1_POSNAME))) {
//                // 查到第一層長官為以上三種，表示自己為科長級或副處長級或室級成員
//                sectionChief = NO_SIGN;
//                director = "副處長".equals(positionData.get(F1_POSNAME)) ? (String) positionData.get(F2_ACCOUNT) : (String) positionData.get(F1_ACCOUNT);
//
//            } else if ("科長".equals(positionData.get(F1_POSNAME)) || (null == positionData.get(F1_POSNAME) && !"處長".equals(positionData.get(POSNAME)))) {
//                // 查到第一層長官為科長，表示自己為科員或同級
//
//                sectionChief = (null == positionData.get(F1_POSNAME)) ? NO_SIGN : (String) positionData.get(F1_ACCOUNT);
//                director = (String) (("副處長".equals(positionData.get(F2_POSNAME))) ? positionData.get(F3_ACCOUNT) : positionData.get(F2_ACCOUNT));
//
//            } else {
//                sectionChief = NO_SIGN;
//                director = NO_SIGN;
//            }
//        }
//        variables.put(SECTION_CHIEF, sectionChief);
//        variables.put(DIRECTOR, director);
//        if (NO_SIGN.equals(sectionChief)) variables.put(SECTION_CHIEF + DECISION, "1");
//        if (NO_SIGN.equals(director)) variables.put(DIRECTOR + DECISION, "1");
//
//    }

    /*
     * @param variables 流程變數
     * @param id 申請者id (L410情況為填寫者的員工編號，因為有可能幫新進人員申請)
     * @Return void
     */
    public void setSupervisor(Map<String, Object> variables, String id) {


        String sectionChief;
        String director;

        // 查詢此人是否為特例
        BpmSpecialSupervisor bpmSpecialSupervisor = bpmSpecialSupervisorService.findById(id).orElse(null);
        if (bpmSpecialSupervisor != null) {

            log.info("申請者為特例，id: {}", id);
            sectionChief = bpmSpecialSupervisor.getF1Account();
            director = bpmSpecialSupervisor.getF2Account();
        } else {

            // 查詢申請者的資料
            User applierUserInfo = userService.getUserInfo(id);

            // 依申請者的單位職稱查出應簽核的長官
            Optional<BpmSupervisor> bpmSupervisorOptional =
                bpmSupervisorRepository.findById(new BpmSupervisorPrimayKey(applierUserInfo.getDeptId(), applierUserInfo.getTitleName()));

            // 分別查出第一層長官與第二層長官
            if (bpmSupervisorOptional.isPresent()) {
                BpmSupervisor bpmSupervisor = bpmSupervisorOptional.get();
                sectionChief = getPecard(bpmSupervisor.getFirstLayerUnit(), bpmSupervisor.getFirstLayerSupervisor());
                director = getPecard(bpmSupervisor.getSecondLayerUnit(), bpmSupervisor.getSecondLayerSupervisor());
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

    /*
     * @param unit 單位ID
     * @param titleName 職稱
     * @Return String 簽核者的員工編號
     */
    private String getPecard(String unit, String titleName) {
        if (unit == null || titleName == null) {
            return NO_SIGN;
        }
        return supervisorRepository.executeQueryRuleModel(unit, titleName) == null ? NO_SIGN : supervisorRepository.executeQueryRuleModel(unit, titleName);
    }

}
