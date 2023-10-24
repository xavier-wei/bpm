package tw.gov.pcc.eip.framework.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import tw.gov.pcc.eip.common.cases.Eip0aw010Case;
import tw.gov.pcc.eip.services.Eip0aw010Service;

@AllArgsConstructor
@ControllerAdvice
public class GlobalControllerAdvice {

    private Eip0aw010Service eip0aw010Service;

    /**
     * 差勤
     *
     * @return eip0aw010Case
     */
    @ModelAttribute("eip0aw010Case")
    public Eip0aw010Case getEip0aw010Case() {
        return eip0aw010Service.getEmptyEip0aw010Case("1");
    }

    /**
     * 公文
     *
     * @return eip0aw011Case
     */
    @ModelAttribute("eip0aw011Case")
    public Eip0aw010Case getEip0aw011Case() {
        return eip0aw010Service.getEmptyEip0aw010Case("2");
    }


}
