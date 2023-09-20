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
    private BpmSignerListRepository bpmSignerListRepository;
    private UserRepository userRepository;
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
            if (ids.contains(",")) {
                List<User> users = userRepository.findByUserIdIn(List.of(ids.split(",")));
                BpmSignerList bpmSignerList = new BpmSignerList();
                bpmSignerList.setFormId(formId);
                bpmSignerList.setTaskName(SinerTaskEnum.getNameByTask(key));
                bpmSignerList.setDeptId(users.get(0).getCpape05m().getPeunit());
                bpmSignerList.setEmpIds(ids);
                List<String> empNames = users.stream().map(User::getUserName).collect(Collectors.toList());
                bpmSignerList.setEmpNames(String.join(",", empNames));
                bpmSignerList.setCreateTime(Timestamp.valueOf(LocalDateTime.now()));
                bpmSignerLists.add(bpmSignerList);
            }else {
                User user = userRepository.findByUserId(ids);
                BpmSignerList bpmSignerList = new BpmSignerList();
                bpmSignerList.setFormId(formId);
                bpmSignerList.setTaskName(SinerTaskEnum.getNameByTask(key));
                bpmSignerList.setDeptId(user.getCpape05m().getPeunit());
                bpmSignerList.setEmpIds(ids);
                bpmSignerList.setEmpNames(user.getUserName());
                bpmSignerList.setCreateTime(Timestamp.valueOf(LocalDateTime.now()));
                bpmSignerLists.add(bpmSignerList);
            }
        });
        bpmSignerListRepository.saveAll(bpmSignerLists);
    }


}
