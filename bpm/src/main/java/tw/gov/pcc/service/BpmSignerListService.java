package tw.gov.pcc.service;

import org.springframework.stereotype.Service;
import tw.gov.pcc.domain.BpmSignerList;
import tw.gov.pcc.domain.SinerTaskEnum;
import tw.gov.pcc.domain.User;
import tw.gov.pcc.repository.BpmSignerListRepository;
import tw.gov.pcc.repository.UserRepository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BpmSignerListService {
    private final BpmSignerListRepository bpmSignerListRepository;
    private final UserRepository userRepository;
    public BpmSignerListService(BpmSignerListRepository bpmSignerListRepository, UserRepository userRepository) {
        this.bpmSignerListRepository = bpmSignerListRepository;
        this.userRepository = userRepository;
    }

    public void saveBpmSignerList(Map<String, Object> variables,String formId) {

        HashMap<String, String> userTaskMap = new HashMap<>();
        variables.keySet()
            .stream()
            .filter(key -> !key.equals("applier") && !variables.get(key).equals("NO_SIGN") && SinerTaskEnum.getNameByTask(key) != null)
            .forEach(key-> userTaskMap.put(key, (String) variables.get(key)));

        List<BpmSignerList> bpmSignerLists = new ArrayList<>();
        userTaskMap.keySet().forEach(key->{
            String ids = userTaskMap.get(key);
            List<User> users = ids.contains(",") ? userRepository.findByUserIdIn(List.of(ids.split(","))) : List.of(userRepository.findByUserId(ids));

            users.forEach(user -> {
                BpmSignerList bpmSignerList = new BpmSignerList();
                bpmSignerList.setFormId(formId);
                bpmSignerList.setTaskName(SinerTaskEnum.getNameByTask(key));
                bpmSignerList.setDeptId(user.getCpape05m().getPeunit());
                bpmSignerList.setEmpIds(ids);
                bpmSignerList.setEmpNames(ids.contains(",") ? users.stream().map(User::getUserName).collect(Collectors.joining(",")) : user.getUserName());
                bpmSignerList.setCreateTime(Timestamp.valueOf(LocalDateTime.now()));
                bpmSignerLists.add(bpmSignerList);
            });
        });
        bpmSignerListRepository.saveAll(bpmSignerLists);
    }


}
