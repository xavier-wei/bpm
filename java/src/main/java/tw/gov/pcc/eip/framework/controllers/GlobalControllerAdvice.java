package tw.gov.pcc.eip.framework.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import tw.gov.pcc.eip.common.cases.Eip0aw010Case;
import tw.gov.pcc.eip.services.Eip0aw010Service;

@ControllerAdvice
public class GlobalControllerAdvice {

    @Autowired
    private Eip0aw010Service eip0aw010Service;

    @ModelAttribute("eip0aw010Case")
    public Eip0aw010Case setupHeaderMetrics() {
        return eip0aw010Service.getEip0aw010Case();
    }
}
