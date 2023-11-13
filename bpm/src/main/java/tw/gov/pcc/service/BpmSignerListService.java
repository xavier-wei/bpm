package tw.gov.pcc.service;

import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import tw.gov.pcc.domain.BpmIsmsSignerOrder;
import tw.gov.pcc.domain.BpmSignerList;
import tw.gov.pcc.domain.SinerTaskEnum;
import tw.gov.pcc.domain.User;
import tw.gov.pcc.repository.BpmIsmsSignerOrderRepository;
import tw.gov.pcc.repository.BpmSignerListRepository;
import tw.gov.pcc.repository.UserRepository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;

@Service
public class BpmSignerListService {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final BpmSignerListRepository bpmSignerListRepository;
    private final UserRepository userRepository;
    private final BpmIsmsSignerOrderRepository bpmIsmsSignerOrderRepository;

    public BpmSignerListService(NamedParameterJdbcTemplate namedParameterJdbcTemplate, BpmSignerListRepository bpmSignerListRepository, UserRepository userRepository, BpmIsmsSignerOrderRepository bpmIsmsSignerOrderRepository) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.bpmSignerListRepository = bpmSignerListRepository;
        this.userRepository = userRepository;
        this.bpmIsmsSignerOrderRepository = bpmIsmsSignerOrderRepository;
    }

    public void saveBpmSignerList(Map<String, Object> variables, String formId) throws ResponseStatusException {

        // 取得任務名稱及簽核者ids的對應，如果暫存後申請任務有增減，應該要來檢查這段
        Map<String, String> userTaskMap = variables.keySet()
            .stream()
            .filter(key -> !key.equals("applier")) // 過濾申請者
            .filter(key -> !variables.get(key).equals("NO_SIGN")) // 過濾無須簽核之欄位
            .filter(key -> SinerTaskEnum.getNameByTask(key) != null) // 過濾不在定義中的taskName
            .collect(toMap(key -> key, key -> (String) variables.get(key))); // values應為使用者ids

        // 取得各表單簽核排序依據
        List<BpmIsmsSignerOrder> bpmIsmsSignerOrders = bpmIsmsSignerOrderRepository.findByBpmIsmsFormOrderBySortAsc(formId.split("-")[0]);

        // 將taskname 及 排序分別放入key 、 value
        Map<String, Integer> sortMap = bpmIsmsSignerOrders
            .stream()
            .collect(toMap(BpmIsmsSignerOrder::getTaskName, BpmIsmsSignerOrder::getSort));
        // 最終要存取的簽核人員名單
        List<BpmSignerList> bpmSignerLists =
            userTaskMap.keySet()
                .stream()
                .flatMap(key -> {
                    // 利用keyName取得ids 範例：單人："1121" 、 多人："1121，1122"， (雖然用values更直觀可以直接跑stream，但會沒辦法拿到taskName)
                    String ids = userTaskMap.get(key);
                    // 將ids splits後至user表中分別查出其個人資訊，如果沒查到東西給空List，然後拋例外(不太可能沒查到，但預防萬一還是拋個錯)
                    List<User> users =
                        userRepository
                            .findByUserIdIn(List.of(ids.split(",")))
                            .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, key + "人員未於Users table建檔，請洽管理人員"));
                    // 將上述資訊製成 BpmSignerList並放入List
                    return users.stream().map(user -> getBpmSignerList(formId, key, user, ids, users, sortMap));
                }).collect(Collectors.toList());

        // 將簽核人員名單存進資料庫
        bpmSignerListRepository.saveAll(bpmSignerLists);

    }

    // 將上述資訊製成 BpmSignerList並放入List
    @NotNull
    private static BpmSignerList getBpmSignerList(String formId, String key, User user, String ids, List<User> users, Map<String, Integer> sortMap) {
        BpmSignerList bpmSignerList = new BpmSignerList();
        bpmSignerList.setFormId(formId);
        bpmSignerList.setTaskName(SinerTaskEnum.getNameByTask(key));
        bpmSignerList.setDeptId(user.getDeptId());
        bpmSignerList.setEmpIds(ids);
        bpmSignerList.setEmpNames(ids.contains(",") ? users.stream().map(User::getUserName).collect(Collectors.joining(",")) : user.getUserName());
        bpmSignerList.setCreateTime(Timestamp.valueOf(LocalDateTime.now()));
        bpmSignerList.setSort(sortMap.get(bpmSignerList.getTaskName()));
        return bpmSignerList;
    }

    @Transactional
    public int deleteAllByFormId(String id) {

        String deleteAllByIdSQL = "DELETE FROM BPM_SIGNER_LIST WHERE FORM_ID = :id";

        return namedParameterJdbcTemplate.update(deleteAllByIdSQL, Map.of("id", id));
    }

}
