package tw.gov.pcc.service;

import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import tw.gov.pcc.cache.BpmCache;
import tw.gov.pcc.domain.BpmSignerList;
import tw.gov.pcc.domain.SinerTaskEnum;
import tw.gov.pcc.domain.User;
import tw.gov.pcc.repository.BpmSignerListRepository;
import tw.gov.pcc.repository.UserRepository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;

@Service
public class BpmSignerListService {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final BpmSignerListRepository bpmSignerListRepository;
    private final UserRepository userRepository;

    public BpmSignerListService(NamedParameterJdbcTemplate namedParameterJdbcTemplate, BpmSignerListRepository bpmSignerListRepository, UserRepository userRepository) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.bpmSignerListRepository = bpmSignerListRepository;
        this.userRepository = userRepository;
    }

    /**
     * 儲存 BPM 簽核者列表。
     * 根據傳入的變數和表單 ID 資訊，方法將處理關於簽核任務的詳細資訊，在這個過程中，我們將過濾掉不需要簽核的欄位，
     * 以及不在定義中的任務名稱。然後，我們會根據每一個簽核者的 ID 在資料庫中查找對應的使用者資訊，並將這些資訊轉換為 `BpmSignerList` 對象，
     * 進行相應的排序並最後存入資料庫中。
     * 在這個過程中，如果查詢不到一個簽核者的相應使用者資訊，會拋出一個 BAD_REQUEST 錯誤。
     * @param variables 一個包含任務名稱和簽核者 ID 的 Map。
     * @param formId 表單的 ID。
     * @throws ResponseStatusException 當無法在 Users 表找到對應的使用者資訊時會拋出此異常。
     */
    public void saveBpmSignerList(Map<String, Object> variables, String formId) throws ResponseStatusException {

        // 取得任務名稱及簽核者ids的對應，如果暫存後申請任務有增減，應該要來檢查這段， 如果有需要也可把filter都合併，拆開純粹為了好維護
        Map<String, String> userTaskMap = variables.keySet()
            .stream()
            .filter(key -> !key.equals("applier")) // 過濾申請者
            .filter(key -> !variables.get(key).equals("NO_SIGN")) // 過濾無須簽核之欄位
            .filter(key -> SinerTaskEnum.getNameByTask(key) != null) // 過濾不在定義中的taskName
            .collect(toMap(key -> key, key -> (String) variables.get(key))); // values應為使用者ids

        // 將taskName 及 排序分別放入key 、 value
        Map<String, Integer> sortMap = BpmCache.getBpmIsmsSignerOrderMap().get(formId.split("-")[0]);
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
    public void deleteAllByFormId(String id) {
        String deleteAllByIdSQL = "DELETE FROM BPM_SIGNER_LIST WHERE FORM_ID = :id";
        namedParameterJdbcTemplate.update(deleteAllByIdSQL, Map.of("id", id));
    }

}
