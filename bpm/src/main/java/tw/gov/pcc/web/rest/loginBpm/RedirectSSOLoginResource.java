package tw.gov.pcc.web.rest.loginBpm;

import org.keycloak.KeycloakSecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class RedirectSSOLoginResource {

    @RequestMapping("/api/loginBpm")
    public ModelAndView redirectSSOLogin(
        @RequestParam("referer") String referer,
        @RequestParam("path") String path) {
        KeycloakSecurityContext keycloakSecurityContext = (KeycloakSecurityContext)session.getAttribute(KeycloakSecurityContext.class.getName());




        String redirect = "redirect:" + referer + "/eip" + path;
        return new ModelAndView(redirect);
    }
    private HttpSession session;

    public RedirectSSOLoginResource(HttpSession session) {
        this.session = session;
    }

}
