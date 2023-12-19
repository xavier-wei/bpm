package tw.gov.pcc.service;

import com.google.gson.Gson;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import tw.gov.pcc.cache.BpmSeqCache;
import tw.gov.pcc.domain.*;
import tw.gov.pcc.repository.BpmIsmsL410Repository;
import tw.gov.pcc.repository.UserRoleRepository;
import tw.gov.pcc.service.dto.BpmIsmsL410DTO;
import tw.gov.pcc.service.dto.BpmUploadFileDTO;
import tw.gov.pcc.service.dto.EndEventDTO;
import tw.gov.pcc.service.dto.TaskDTO;
import tw.gov.pcc.service.mapper.BpmIsmsL410Mapper;
import tw.gov.pcc.service.mapper.BpmUploadFileMapper;
import tw.gov.pcc.utils.SeqNumber;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service("l410Service")
public class BpmIsmsL410ServiceNew implements BpmIsmsCommonService, BpmIsmsPatchService {

    // BPM_IPT_Operator 資推小組承辦人、 BPM_IPT_Mgr 簡任技正/科長 、 BPM_PR_Operator 人事室、BPM_SEC_Operator 秘書處
    static final String BPM_IPT_OPERATOR = "BPM_IPT_Operator";
    private static final String[] ROLE_IDS = {BPM_IPT_OPERATOR, "BPM_IPT_Mgr", "BPM_PR_Operator", "BPM_SEC_Operator"};

    private final Logger log = LoggerFactory.getLogger(BpmIsmsL410ServiceNew.class);
    private static final HashMap<UUID, BpmIsmsL410DTO> DTO_HOLDER = new HashMap<>();
    private static final HashMap<UUID, Map<String, Object>> VARIABLES_HOLDER = new HashMap<>();
    private final Gson gson = new Gson();

    private final BpmIsmsL410Repository bpmIsmsL410Repository;

    private final BpmUploadFileService bpmUploadFileService;

    private final BpmUploadFileMapper bpmUploadFileMapper;

    private final BpmSignStatusService bpmSignStatusService;

    private final BpmSignerListService bpmSignerListService;
    private final BpmIsmsL410Mapper bpmIsmsL410Mapper;
    private final SupervisorService supervisorService;
    private final UserRoleRepository userRoleRepository;


    public BpmIsmsL410ServiceNew(BpmIsmsL410Repository bpmIsmsL410Repository, BpmUploadFileService bpmUploadFileService, BpmUploadFileMapper bpmUploadFileMapper, BpmSignStatusService bpmSignStatusService, BpmSignerListService bpmSignerListService, BpmIsmsL410Mapper bpmIsmsL410Mapper, SupervisorService supervisorService, UserRoleRepository userRoleRepository) {
        this.bpmIsmsL410Repository = bpmIsmsL410Repository;
        this.bpmUploadFileService = bpmUploadFileService;
        this.bpmUploadFileMapper = bpmUploadFileMapper;
        this.bpmSignStatusService = bpmSignStatusService;
        this.bpmSignerListService = bpmSignerListService;
        this.bpmIsmsL410Mapper = bpmIsmsL410Mapper;
        this.supervisorService = supervisorService;
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public UUID setVariables(HashMap<String, Object> variables, String form, User userInfo) {
        BpmIsmsL410DTO bpmIsmsL410DTO = gson.fromJson(form, BpmIsmsL410DTO.class);
        UUID uuid = UUID.randomUUID();
        DTO_HOLDER.put(uuid, bpmIsmsL410DTO);
        variables.put("applier", bpmIsmsL410DTO.getFilEmpid());
        variables.put("isSubmit", bpmIsmsL410DTO.getIsSubmit());
        // 填入上級
        supervisorService.setSupervisor(variables, bpmIsmsL410DTO.getFilEmpid());
        HashMap<String, String> signerIdsHashMap = getSignerIdsHashMap(variables);
        // 設定需要申請的Task有哪些及各task的Signer
        setSys(variables, bpmIsmsL410DTO, signerIdsHashMap);
        VARIABLES_HOLDER.put(uuid, variables);
        DTO_HOLDER.put(uuid, bpmIsmsL410DTO);

        return uuid;
    }

    @Override
    @Transactional(rollbackFor = SQLException.class)
    public  void saveBpm(UUID uuid, String processInstanceId, TaskDTO taskDTO, List<BpmUploadFileDTO> dto, List<MultipartFile> appendixFiles) {

        BpmIsmsL410DTO bpmIsmsL410DTO = DTO_HOLDER.get(uuid);
        String formId;

            if (bpmIsmsL410DTO.getFormId() == null || bpmIsmsL410DTO.getFormId().isEmpty()) {
                synchronized (this) {
                    //取得表單最後的流水號
                    String lastFormId = BpmSeqCache.getL410Seq();
                    formId = bpmIsmsL410DTO.getFormName() + "-" + new SeqNumber().getNewSeq(lastFormId);
                    BpmSeqCache.setL410Seq(formId);
                }
            } else {
                formId = bpmIsmsL410DTO.getFormId();
            }

        bpmIsmsL410DTO.setFormId(formId);
        //取得表單最後的流水號

//        存入table
        bpmIsmsL410DTO.setProcessInstanceId(processInstanceId);
        bpmIsmsL410DTO.setProcessInstanceStatus("0");
        bpmIsmsL410DTO.setUpdateTime(Timestamp.valueOf(LocalDateTime.now()));
        bpmIsmsL410DTO.setUpdateUser(bpmIsmsL410DTO.getFilName());
        bpmIsmsL410DTO.setCreateTime(Timestamp.valueOf(LocalDateTime.now()));
        bpmIsmsL410DTO.setCreateUser(bpmIsmsL410DTO.getFilName());
        log.info("BpmIsmsL410ServiceNew.java - saveBpm - 71 :: " + bpmIsmsL410DTO);
        BpmIsmsL410 bpmIsmsL410 = bpmIsmsL410Mapper.toEntity(bpmIsmsL410DTO);

        bpmIsmsL410Repository.save(bpmIsmsL410);

        //儲存照片
        bpmUploadFileService.savePhoto(dto, appendixFiles, formId);

        // 如果申請者選擇直接送出則跑下面這段完成申請者確認
        if ("1".equals(bpmIsmsL410DTO.getIsSubmit())) {
            bpmSignStatusService.saveBpmSignStatus(
                formId,
                processInstanceId,
                taskDTO,
                bpmIsmsL410DTO.getAppEmpid(),
                bpmIsmsL410DTO.getAppName(),
                bpmIsmsL410DTO.getAppUnit()
            );
        }
        bpmSignerListService.saveBpmSignerList(VARIABLES_HOLDER.get(uuid), formId);
        VARIABLES_HOLDER.remove(uuid);
        DTO_HOLDER.remove(uuid);
    }

    @Override
    public void saveBpmByPatch(String form) {
        BpmIsmsL410DTO bpmIsmsL410DTO = gson.fromJson(form, BpmIsmsL410DTO.class);
        bpmIsmsL410DTO.setUpdateTime(Timestamp.valueOf(LocalDateTime.now()));
        bpmIsmsL410Repository.save(bpmIsmsL410Mapper.toEntity(bpmIsmsL410DTO));
    }

    @Override
    public String saveBpmByPatch(String form, List<BpmUploadFileDTO> dto, List<MultipartFile> appendixFiles) {
        // 取得dto (包含修改過的值)
        return null;
    }

    @NotNull
    private HashMap<String, String> getSignerIdsHashMap(HashMap<String, Object> variables) {
        // 設定取得所有簽核者的Id
        HashMap<String, String> signerIds = new HashMap<>();
        List<UserRole> userRoles = userRoleRepository.findByRoleIdIn(List.of(ROLE_IDS));
        Arrays.stream(ROLE_IDS).forEach(s -> {
            List<String> userIds = userRoles.stream().filter(userRole -> userRole.getRoleId().equals(s)).map(UserRole::getUserId).collect(Collectors.toList());
            signerIds.put(s, String.join(",", userIds));
        });
        variables.put("infoGroup", signerIds.get(BPM_IPT_OPERATOR));
        variables.put("seniorTechSpecialist", signerIds.get("BPM_IPT_Mgr"));
        // BPM_IPT_Operator 資推小組承辦人、 BPM_IPT_Mgr 簡任技正/科長 、 BPM_PR_Operator 人事室、BPM_SEC_Operator 秘書處
        return signerIds;
    }

    // 設定需要申請的Task及各task的Signer
    private static void setSys(HashMap<String, Object> variables, BpmIsmsL410DTO bpmIsmsL410DTO, HashMap<String, String> signerIds) {
        HashMap<String, Object> sysNameMap = new HashMap<>();

        bpmIsmsL410DTO.getL410Variables().forEach(s -> s.keySet().forEach(sysName -> sysNameMap.put(sysName, s.get(sysName))));
        sysNameMap.keySet().forEach(sysName -> {
            if (sysNameMap.get(sysName) == null) {
                variables.put(sysName, "0");
            } else {
                variables.put(sysName, "1");
            }
        });
        HashMap<String, String> signerMapTemp = new HashMap<>();
        variables.keySet()
            .stream()
            .filter(s -> s.startsWith("is") && !"isSubmit".equals(s))
            .filter(s -> "1".equals(variables.get(s)))
            .forEach(s -> {
                String siner = s.replaceFirst("is", "") + "Signer";
                signerMapTemp.put(siner, signerIds.get(SysSignerEnum.getSignerUnitBySigner(siner)));
            });
        signerMapTemp.keySet().forEach(s -> variables.put(s, signerMapTemp.get(s)));
        signerMapTemp.clear();

    }

    @Override
    public MailInfo endForm(EndEventDTO endEventDTO) {
        BpmIsmsL410 bpmIsmsL410 = bpmIsmsL410Repository.findFirstByProcessInstanceId(endEventDTO.getProcessInstanceId());
        bpmIsmsL410.setProcessInstanceStatus(endEventDTO.getProcessStatus());
        bpmIsmsL410.setUpdateTime(Timestamp.valueOf(LocalDateTime.now()));
        BpmIsmsL410 save = bpmIsmsL410Repository.save(bpmIsmsL410);
        String fullName= IsmsFullNameEnum.getFullNameBySimpleName(save.getFormId().split("-")[0]);
        return new MailInfo(fullName,save.getFormId() , save.getAppName(),save.getAppEmpid(), save.getProcessInstanceStatus().equals("1")?"處理完成":"退件", true);
    }

    @Override
    public Map<String, Object> getBpm(String formId) {
        List<Map<String, Object>> bpmIsmsL410 = bpmIsmsL410Repository.findByFormId(formId);
        if (!bpmIsmsL410.isEmpty()) return bpmIsmsL410Repository.findByFormId(formId).get(0);
        return Map.of();
    }

    @Override
    public void saveBpmByPatchToIsSubmit(String processInstanceId) {
        BpmIsmsL410 bpmIsmsL410 = bpmIsmsL410Repository.findFirstByProcessInstanceId(processInstanceId);
        bpmIsmsL410.setIsSubmit("0");
        bpmIsmsL410.setProcessInstanceStatus("2");
        bpmIsmsL410.setUpdateTime(Timestamp.valueOf(LocalDateTime.now()));
        bpmIsmsL410Repository.save(bpmIsmsL410);
    }

    @Override
    public void cancel(String processInstanceId) {
        BpmIsmsL410 bpmIsmsL410 = bpmIsmsL410Repository.findFirstByProcessInstanceId(processInstanceId);
        bpmIsmsL410.setProcessInstanceStatus("3");
        bpmIsmsL410.setUpdateTime(Timestamp.valueOf(LocalDateTime.now()));
        bpmIsmsL410Repository.save(bpmIsmsL410);
        bpmSignStatusService.saveCancelBpmSignStatus(bpmIsmsL410);
    }

    @Override
    public void removeHolder(UUID uuid) {
        DTO_HOLDER.remove(uuid);
        VARIABLES_HOLDER.remove(uuid);
    }

}

enum SysSignerEnum {

    HR_SYS("HrSysSigner", "BPM_PR_Operator"),
    AD_SYS("AdSysSigner", BpmIsmsL410ServiceNew.BPM_IPT_OPERATOR),
    OD_SYS("OdSysSigner", "BPM_SEC_Operator"),
    MEETING_ROOM("MeetingRoomSigner", "BPM_SEC_Operator"),
    EMAIL_SYS("EmailSysSigner", BpmIsmsL410ServiceNew.BPM_IPT_OPERATOR),
    WEB_SITE("WebSiteSigner", BpmIsmsL410ServiceNew.BPM_IPT_OPERATOR),
    PCC_PIS("PccPisSigner", BpmIsmsL410ServiceNew.BPM_IPT_OPERATOR),
    ENG_AND_PRJ_INFO_SYS("EngAndPrjInfoSysSigner", BpmIsmsL410ServiceNew.BPM_IPT_OPERATOR),
    REV_SYS("RevSysSigner", BpmIsmsL410ServiceNew.BPM_IPT_OPERATOR),
    BID_SYS("BidSysSigner", BpmIsmsL410ServiceNew.BPM_IPT_OPERATOR),
    REC_SYS("RecSysSigner", BpmIsmsL410ServiceNew.BPM_IPT_OPERATOR),
    OTHER_SYS_1("OtherSys1Signer", BpmIsmsL410ServiceNew.BPM_IPT_OPERATOR),
    OTHER_SYS_2("OtherSys2Signer", BpmIsmsL410ServiceNew.BPM_IPT_OPERATOR),
    OTHER_SYS_3("OtherSys3Signer", BpmIsmsL410ServiceNew.BPM_IPT_OPERATOR);

    private final String signer;
    private final String signerUnit;

    SysSignerEnum(String signer, String signerUnit) {
        this.signer = signer;
        this.signerUnit = signerUnit;
    }

    public static String getSignerUnitBySigner(String signer) {
        for (SysSignerEnum sysSignerEnum : SysSignerEnum.values()) {
            if (sysSignerEnum.signer.equals(signer)) {
                return sysSignerEnum.signerUnit;
            }
        }
        return null;
    }


}
