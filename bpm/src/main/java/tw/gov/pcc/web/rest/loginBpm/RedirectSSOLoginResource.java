package tw.gov.pcc.web.rest.loginBpm;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RedirectSSOLoginResource {

    @RequestMapping("/api/loginBpm")
    public ModelAndView redirectSSOLogin(
        @RequestParam("referer") String referer,
        @RequestParam("path") String path) {
        String redirect = "redirect:" + referer + "/eip" + path;
        return new ModelAndView(redirect);
    }

}
