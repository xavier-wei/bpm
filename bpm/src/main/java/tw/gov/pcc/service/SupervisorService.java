package tw.gov.pcc.service;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tw.gov.pcc.cache.BpmCache;
import tw.gov.pcc.domain.BpmSpecialSupervisor;
import tw.gov.pcc.domain.BpmSupervisor;
import tw.gov.pcc.domain.BpmSupervisorPrimayKey;
import tw.gov.pcc.domain.User;
import tw.gov.pcc.repository.BpmSupervisorRepository;
import tw.gov.pcc.repository.SupervisorRepository;

import java.util.List;
import java.util.Map;
import java.util.Objects;

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
    private final Gson gson = new Gson();

    public SupervisorService(UserService userService, SupervisorRepository supervisorRepository, BpmSupervisorRepository bpmSupervisorRepository, BpmSpecialSupervisorService bpmSpecialSupervisorService) {
        this.userService = userService;
        this.supervisorRepository = supervisorRepository;
        this.bpmSupervisorRepository = bpmSupervisorRepository;
        this.bpmSpecialSupervisorService = bpmSpecialSupervisorService;
    }

    /**
     * @param variables 流程變數
     * @param id 申請者id (L410情況為填寫者的員工編號，因為有可能幫新進人員申請)
     * @Return void
     */
    public void setSupervisor(Map<String, Object> variables, String id) {
        // 查詢此人是否為特例
        BpmSpecialSupervisor bpmSpecialSupervisor = bpmSpecialSupervisorService.findById(id).orElse(null);
        if (bpmSpecialSupervisor != null) {
            log.info("申請者為特例，id: {}", id);
            setSigner(variables, bpmSpecialSupervisor.getF1Account(), bpmSpecialSupervisor.getF2Account());
            return;
        }

        // 查詢申請者的資料
        User applierUserInfo = userService.getUserInfo(id);

        // 依申請者的單位職稱查出應簽核的長官單位及職稱並設置簽核者
        BpmSupervisor bpmSupervisor = BpmCache.getBpmSupervisorHashMap().get(new BpmSupervisorPrimayKey(applierUserInfo.getDeptId(), applierUserInfo.getTitleName()));
        if (Objects.nonNull(bpmSupervisor)) {
            setSigner(variables, getPecard(bpmSupervisor.getFirstLayerUnit(), bpmSupervisor.getFirstLayerSupervisor()), getPecard(bpmSupervisor.getSecondLayerUnit(), bpmSupervisor.getSecondLayerSupervisor()));
        } else {
            setSigner(variables, NO_SIGN, NO_SIGN);
        }
        //

    }


    /**
     *
     * @param variables 流程變數
     * @param sectionChief 科長
     * @param director 單位主管
     */
    private static void setSigner(Map<String, Object> variables, String sectionChief, String director) {
        variables.put(SECTION_CHIEF, sectionChief == null ? NO_SIGN : sectionChief);
        variables.put(DIRECTOR, director == null ? NO_SIGN : director);
        if (NO_SIGN.equals(sectionChief)) variables.put(SECTION_CHIEF + DECISION, "1");
        if (NO_SIGN.equals(director)) variables.put(DIRECTOR + DECISION, "1");
    }

    /**
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

    /**
     * 查詢 表單簽核上級管理
     * @param title 申請者職稱
     * @Return List<BpmSupervisor> 表單簽核上級管理
     */
    public List<BpmSupervisor> supervisorAdmin(String title) {
        return bpmSupervisorRepository.findAllByTitle(title);
    }

    /**
     * 新增跟編輯 表單簽核上級管理
     * @param bpmSupervisor 表單簽核上級管理
     * @Return String 表單簽核上級管理
     */
    public String patchSupervisor(BpmSupervisor bpmSupervisor) {
        BpmSupervisorPrimayKey bpmSupervisorPrimayKey = new BpmSupervisorPrimayKey(bpmSupervisor.getAppUnit(), bpmSupervisor.getAppTitle());
        BpmCache.getBpmSupervisorHashMap().put(bpmSupervisorPrimayKey, bpmSupervisor);
        return gson.toJson(bpmSupervisorRepository.save(bpmSupervisor));
    }

}
