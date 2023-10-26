package tw.gov.pcc.eip.framework.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import tw.gov.pcc.eip.common.cases.Eip0aw010Case;
import tw.gov.pcc.eip.common.controllers.Eip0aw010Controller;
import tw.gov.pcc.eip.common.controllers.Eip0aw011Controller;
import tw.gov.pcc.eip.services.Eip0aw010Service;
import tw.gov.pcc.eip.util.ObjectUtility;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@AllArgsConstructor
@ControllerAdvice
public class GlobalControllerAdvice {

    private Eip0aw010Service eip0aw010Service;

    /**
     * 差勤
     *
     * @return eip0aw010Case
     */
    @ModelAttribute(Eip0aw010Controller.CASE_KEY)
    public Eip0aw010Case getEip0aw010Case(HttpSession httpSession) {
        Eip0aw010Case newCase = Optional.ofNullable((Eip0aw010Case) httpSession.getAttribute(Eip0aw010Controller.CASE_KEY)).orElse(eip0aw010Service.getEmptyEip0aw010Case("1"));
        return ObjectUtility.normalizeObject(newCase);
    }

    /**
     * 公文
     *
     * @return eip0aw011Case
     */
    @ModelAttribute(Eip0aw011Controller.CASE_KEY)
    public Eip0aw010Case getEip0aw011Case(HttpSession httpSession) {
        Eip0aw010Case newCase = Optional.ofNullable((Eip0aw010Case) httpSession.getAttribute(Eip0aw011Controller.CASE_KEY)).orElse(eip0aw010Service.getEmptyEip0aw010Case("2"));
        return ObjectUtility.normalizeObject(newCase);
    }


}
