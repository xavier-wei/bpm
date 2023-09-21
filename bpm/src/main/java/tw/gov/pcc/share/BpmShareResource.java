package tw.gov.pcc.share;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tw.gov.pcc.domain.Depts;
import tw.gov.pcc.domain.Eipcode;
import tw.gov.pcc.eip.dao.DeptsDao;
import tw.gov.pcc.eip.dao.EipcodeDao;
import tw.gov.pcc.repository.BpmIsmsAdditionalRepository;
import tw.gov.pcc.utils.MapUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    @GetMapping("/bpmUnitOptions")
    public List<Eipcode> unitOptions() {

        List<Eipcode> unitList = eipcodeDao.findByCodekindScodekindOrderByCodeno("REGISQUAL", "U");

        log.info("BpmShareResource.java - unitOptions - 30 :: " + unitList);

        return unitList;
    }

    @GetMapping("/bpmDeptsOptions")
    public List<Depts> deptsOptions() {

        List<Depts> deptsList = deptsDao.getEip01wDepts();

        log.info("BpmShareResource.java - unitOptions - 30 :: " + deptsList);

        return deptsList;
    }

    @GetMapping("/peunitOptions/{id}")
    public List<Map<String, Object>> peunitOptions(@PathVariable String id) {

        List<Map<String, Object>> peunitOptions = bpmIsmsAdditionalRepository.peunitOptions(id);

        List<Map<String, Object>> peunitOptionsConvert = new ArrayList<>();

        peunitOptions.forEach(data ->{
            peunitOptionsConvert.add(new MapUtils().getNewMap(data));
        });

        return peunitOptionsConvert;
    }

    @GetMapping("/signatureOptions")
    public List<Map<String, Object>> signatureOptions(
        @RequestParam(required = false) String selectName,
        @RequestParam(required = false) String selectUnit,
        @RequestParam(required = false) String selectTitle) {

        List<Map<String, Object>> signatureOptions = bpmIsmsAdditionalRepository.signatureOptions(selectName,selectUnit,selectTitle);

        List<Map<String, Object>> signatureOptionsConvert = new ArrayList<>();

        signatureOptions.forEach(data ->{
            signatureOptionsConvert.add(new MapUtils().getNewMap(data));
        });

        return signatureOptionsConvert;
    }

}
