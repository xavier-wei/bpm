package tw.gov.pcc.share;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tw.gov.pcc.domain.BpmSignerList;
import tw.gov.pcc.domain.Depts;
import tw.gov.pcc.domain.Eipcode;
import tw.gov.pcc.domain.User;
import tw.gov.pcc.eip.dao.DeptsDao;
import tw.gov.pcc.eip.dao.EipcodeDao;
import tw.gov.pcc.repository.BpmIsmsAdditionalRepository;
import tw.gov.pcc.repository.BpmSignerListRepository;
import tw.gov.pcc.repository.UserRepository;
import tw.gov.pcc.service.dto.BpmSignStatusDTO;
import tw.gov.pcc.utils.MapUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/eip")
public class BpmShareResource {

    private final Logger log = LoggerFactory.getLogger(BpmShareResource.class);

    @Autowired
    private EipcodeDao eipcodeDao;

    @Autowired
    private DeptsDao deptsDao;

    @Autowired
    private BpmIsmsAdditionalRepository bpmIsmsAdditionalRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BpmSignerListRepository bpmSignerListRepository;

    @GetMapping("/bpmUnitOptions")
    public List<Eipcode> unitOptions() {
        return eipcodeDao.findByCodekindScodekindOrderByCodeno("REGISQUAL", "U");
    }

    @GetMapping("/bpmDeptsOptions")
    public List<Depts> deptsOptions() {
        return deptsDao.getEip01wDepts();
    }

    @GetMapping("/peunitOptions/{id}")
    public List<Map<String, Object>> peunitOptions(@PathVariable String id) {

        List<Map<String, Object>> peunitOptionsConvert = new ArrayList<>();

        //先去一般差勤撈資料，沒有就去測試用差勤撈，目前只有IVV帳號在一般差勤會找不到
        List<Map<String, Object>> peunitOptions = bpmIsmsAdditionalRepository.peunitOptions(id);
        if (peunitOptions.isEmpty()) {
            peunitOptions = bpmIsmsAdditionalRepository.peunitOptionsForTest(id);
        }

        peunitOptions.forEach(data -> {
            peunitOptionsConvert.add(new MapUtils().getNewMap(data));
        });

        return peunitOptionsConvert;
    }

    @GetMapping("/signatureOptions")
    public List<Map<String, Object>> signatureOptions(
        @RequestParam(required = false) String selectName,
        @RequestParam(required = false) String selectUnit,
        @RequestParam(required = false) String selectTitle) {

        List<Map<String, Object>> signatureOptions = bpmIsmsAdditionalRepository.signatureOptions(selectName, selectUnit, selectTitle);

        List<Map<String, Object>> signatureOptionsConvert = new ArrayList<>();

        signatureOptions.forEach(data -> {
            signatureOptionsConvert.add(new MapUtils().getNewMap(data));
        });

        return signatureOptionsConvert;
    }

    @GetMapping("/getBpmSignerList/{id}")
    public List<BpmSignerList> findByBpmSignerList(
        @PathVariable String id
    ) {
        return bpmSignerListRepository.findByFormIdOrderBySortAsc(id);
    }

    @GetMapping("/getUsers")
    public List<User> findByUsers() {
        return userRepository.findByAcntIsValid("Y");
    }

    @GetMapping("/findTitleIdList")
    public List<Eipcode> findTitleIdList() {
        return eipcodeDao.findByCodeKind("TITLE");
    }
}
