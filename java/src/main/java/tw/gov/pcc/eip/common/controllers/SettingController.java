package tw.gov.pcc.eip.common.controllers;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tw.gov.pcc.eip.common.cases.Eipxx0w030Case;
import tw.gov.pcc.eip.framework.domain.UserBean;
import tw.gov.pcc.eip.framework.spring.controllers.BaseController;
import tw.gov.pcc.eip.services.ProfileService;
import tw.gov.pcc.eip.util.BeanUtility;
import tw.gov.pcc.eip.util.ExceptionUtility;
import tw.gov.pcc.eip.util.ObjectUtility;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.Optional;

/**
 * Ajax Controller
 *
 * @author swho
 */
@Controller
@Slf4j
public class SettingController extends BaseController {
    public static final String SETTING = "_setting";
    public static final String ENTRY_LIST_ORDER = "drag2,drag3,drag4";

    private final ProfileService profileService;
    private final UserBean userData;

    public SettingController(ProfileService profileService, UserBean userData) {
        this.profileService = profileService;
        this.userData = userData;
    }

    @ModelAttribute(SETTING)
    public Eipxx0w030Case getEipxx0w030Case() {
        Eipxx0w030Case eipxx0w030Case = new Eipxx0w030Case();
        eipxx0w030Case.setEntryListOrder(ObjectUtils.defaultIfNull(profileService.readProfileMap(userData.getUserId())
                        .get(ProfileService.ENTRY_LIST_ORDER_KEY), ENTRY_LIST_ORDER)
                .toString());
        return ObjectUtility.normalizeObject(eipxx0w030Case);
    }

    @RequestMapping(value = {"/*_saveSetting.action"}, method = RequestMethod.POST)
    @ResponseBody
    public void saveSetting(@RequestBody Eipxx0w030Case bfxx0w030Case, HttpSession httpSession) {
        Eipxx0w030Case cloneCase = new Eipxx0w030Case();
        Eipxx0w030Case sessionCaseData = (Eipxx0w030Case) Optional.ofNullable(httpSession.getAttribute(SETTING)).orElse(getEipxx0w030Case());
        try {
            BeanUtility.copyProperties(cloneCase, sessionCaseData);
            sessionCaseData.setClosed(ObjectUtility.normalizeObject(StringUtils.defaultIfBlank(bfxx0w030Case.getClosed(), sessionCaseData.getClosed())));
            String entryListOrder = ObjectUtility.normalizeObject(StringUtils.defaultIfBlank(bfxx0w030Case.getEntryListOrder(),
                    StringUtils.defaultIfBlank(sessionCaseData.getEntryListOrder(), ENTRY_LIST_ORDER)));
            sessionCaseData.setEntryListOrder(entryListOrder);
            boolean valid = Arrays.stream(StringUtils.split(entryListOrder, ","))
                    .distinct()
                    .count() == StringUtils.split(ENTRY_LIST_ORDER, ",").length;
            if (valid && !cloneCase.equals(sessionCaseData)) { //避免頻繁寫入，不同才寫
                profileService.saveProfile(userData.getUserId(), ProfileService.ENTRY_LIST_ORDER_KEY, sessionCaseData.getEntryListOrder());
            }
        } catch (Exception e) {
            log.warn("saving profile failed. {}", ExceptionUtility.getStackTrace(e));
        }
        httpSession.setAttribute(SETTING, ObjectUtility.normalizeObject(sessionCaseData));
    }
}
