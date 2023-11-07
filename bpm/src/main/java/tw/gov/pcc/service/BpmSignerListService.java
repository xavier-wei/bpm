package tw.gov.pcc.service;

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

        HashMap<String, String> userTaskMap = new HashMap<>();

        variables.keySet()
            .stream()
            .filter(key -> !key.equals("applier")) // 過濾申請者
            .filter(key -> !variables.get(key).equals("NO_SIGN")) // 過濾無須簽核之欄位
            .filter(key -> SinerTaskEnum.getNameByTask(key) != null) // 過濾不在定義中的taskName
            .forEach(key -> userTaskMap.put(key, (String) variables.get(key))); // 將有查到的任務名稱放入key，該任務名稱在variable拿到的value應該會是一組ids

        // 最終要存取的簽核人員名單
        List<BpmSignerList> bpmSignerLists = new ArrayList<>();

        //取得各表單簽核排序依據
        List<BpmIsmsSignerOrder> bpmIsmsSignerOrders = bpmIsmsSignerOrderRepository.findByBpmIsmsFormOrderBySortAsc(formId.split("-")[0]);

        // 將taskname 及 排序分別放入key 、 value
        Map<String, Integer> sortMap = new HashMap<>();
        bpmIsmsSignerOrders.forEach(bpmIsmsSignerOrder -> sortMap.put(bpmIsmsSignerOrder.getTaskName(), bpmIsmsSignerOrder.getSort()));

        userTaskMap.keySet().forEach(key -> {

            String ids = userTaskMap.get(key); //利用keyName取得ids 範例：單人："1121" 、 多人："1121，1122"

            // 將ids splits後至user表中分別查出其個人資訊
            Optional<List<User>> optionalUsers = userRepository.findByUserIdIn(List.of(ids.split(",")));

            // 如果沒查到東西給空List，然後拋例外(不太可能沒查到，但預防萬一)
            List<User> users = optionalUsers.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, key + "人員未於Users table建檔，請洽管理人員"));

            // 將上述資訊製成 BpmSignerList並放入List
            users.forEach(user -> {
                BpmSignerList bpmSignerList = new BpmSignerList();
                bpmSignerList.setFormId(formId);
                bpmSignerList.setTaskName(SinerTaskEnum.getNameByTask(key));
                bpmSignerList.setDeptId(user.getDeptId());
                bpmSignerList.setEmpIds(ids);
                bpmSignerList.setEmpNames(ids.contains(",") ? users.stream().map(User::getUserName).collect(Collectors.joining(",")) : user.getUserName());
                bpmSignerList.setCreateTime(Timestamp.valueOf(LocalDateTime.now()));
                bpmSignerList.setSort(sortMap.get(bpmSignerList.getTaskName()));
                bpmSignerLists.add(bpmSignerList);
            });
        });

        // 存進資料庫
        bpmSignerListRepository.saveAll(bpmSignerLists);
    }

    @Transactional
    public int deleteAllByFormId(String id) {

        String deleteAllByIdSQL = "DELETE FROM BPM_SIGNER_LIST WHERE FORM_ID = :id";

        return namedParameterJdbcTemplate.update(deleteAllByIdSQL, Map.of("id", id));
    }

}
