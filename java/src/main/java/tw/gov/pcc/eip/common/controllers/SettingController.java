package tw.gov.pcc.eip.common.controllers;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tw.gov.pcc.eip.common.cases.Eipxx0w030Case;
import tw.gov.pcc.eip.framework.spring.controllers.BaseController;
import tw.gov.pcc.eip.util.ObjectUtility;

/**
 * Ajax Controller
 *
 * @author swho
 */
@Controller
@SessionAttributes({SettingController.SETTING})
@Slf4j
public class SettingController extends BaseController {
    public static final String SETTING = "_setting";
    public static final String ENTRY_LIST_ORDER = "drag1,drag2,drag3,drag4";

    @ModelAttribute(SETTING)
    public Eipxx0w030Case getEipxx0w030Case() {
        Eipxx0w030Case eipxx0w030Case = new Eipxx0w030Case();
        eipxx0w030Case.setEntryListOrder(ENTRY_LIST_ORDER);
        return eipxx0w030Case;
    }

    @RequestMapping(value = {"/*_saveSetting.action"}, method = RequestMethod.POST)
    @ResponseBody
    public void saveSetting(@RequestBody Eipxx0w030Case bfxx0w030Case, @ModelAttribute(SETTING) Eipxx0w030Case sessionCaseData) {
        sessionCaseData.setClosed(ObjectUtility.normalizeObject(StringUtils.defaultIfBlank(bfxx0w030Case.getClosed(), sessionCaseData.getClosed())));
        sessionCaseData.setEntryListOrder(ObjectUtility.normalizeObject(StringUtils.defaultIfBlank(bfxx0w030Case.getEntryListOrder(),
                StringUtils.defaultIfBlank(sessionCaseData.getEntryListOrder(), ENTRY_LIST_ORDER))));
    }
}
