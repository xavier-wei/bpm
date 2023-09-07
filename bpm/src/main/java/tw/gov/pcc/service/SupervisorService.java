package tw.gov.pcc.service;

import org.springframework.stereotype.Service;
import tw.gov.pcc.domain.User;
import tw.gov.pcc.repository.SupervisorRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SupervisorService {
    public static final String PECARD = "PECARD";
    public static final String NO_SIGN = "NO_SIGN";
    private final SupervisorRepository supervisorRepository;

    public SupervisorService(SupervisorRepository supervisorRepository) {
        this.supervisorRepository = supervisorRepository;
    }


    public void setSupervisor(HashMap<String,Object> variables, String appEmpId,User user) {
        List<Map<String, Object>> maps = supervisorRepository.executeQuery(user.getOrgId(),appEmpId);
        // 三種情況 POSNAME為科員、科長、處長(或更高階主管情形)
        Map<String, Object> positionData = maps.get(0);
        String sectionChief;
        String director;
        if ("科員".equals(positionData.get("POSNAME"))) {
           sectionChief = positionData.get("F1_ACCOUNT") == null ? NO_SIGN : (String) positionData.get("F1_ACCOUNT");
           director = (String) positionData.get("F2_ACCOUNT");
            variables.put("sectionChief", sectionChief);
            variables.put("director", director);
        } else if ("科長".equals(positionData.get("POSNAME"))) {
            sectionChief = NO_SIGN;
            director = (String) positionData.get("F1_ACCOUNT");
        }else{
            sectionChief = NO_SIGN;
            director = NO_SIGN;
        }
        variables.put("sectionChief", sectionChief);
        variables.put("director", director);
    }

}
