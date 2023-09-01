package tw.gov.pcc.share;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tw.gov.pcc.domain.Eipcode;
import tw.gov.pcc.eip.dao.EipcodeDao;

import java.util.List;

@RestController
@RequestMapping("/api/eip")
public class BpmShareResource {

    private final Logger log = LoggerFactory.getLogger(BpmShareResource.class);

    @Autowired
    private EipcodeDao eipcodeDao;

    @GetMapping("/bpmUnitOptions")
    public List<Eipcode> unitOptions()  {


        List<Eipcode> unitList = eipcodeDao.findByCodekindScodekindOrderByCodeno("REGISQUAL","U");

        log.info("BpmShareResource.java - unitOptions - 30 :: " + unitList );

        return unitList;
    }


}