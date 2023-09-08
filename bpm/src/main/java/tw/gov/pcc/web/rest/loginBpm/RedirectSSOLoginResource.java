package tw.gov.pcc.web.rest.loginBpm;

import org.keycloak.KeycloakSecurityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import tw.gov.pcc.domain.User;
import tw.gov.pcc.repository.SupervisorRepository;
import tw.gov.pcc.service.UserService;

import javax.servlet.http.HttpSession;

@Controller
public class RedirectSSOLoginResource {
    private final Logger log = LoggerFactory.getLogger(RedirectSSOLoginResource.class);

    private final HttpSession session;
    private final SupervisorRepository supervisorRepository;
    private final UserService userService;
    public RedirectSSOLoginResource(HttpSession session, SupervisorRepository supervisorRepository, UserService userService) {
        this.session = session;
        this.supervisorRepository = supervisorRepository;
        this.userService = userService;
    }

    @RequestMapping("/api/loginBpm")
    public ModelAndView redirectSSOLogin(
        @RequestParam("referer") String referer,
        @RequestParam("path") String path) {

        KeycloakSecurityContext keycloakSecurityContext = (KeycloakSecurityContext)session.getAttribute(KeycloakSecurityContext.class.getName());
        User userInfo = userService.getUserInfo(keycloakSecurityContext.getToken().getPreferredUsername());
        log.info("User {} 登入bpm系統",userInfo);
        session.setAttribute("userInfo", userInfo);
        String redirect = "redirect:" + referer + "/eip" + path;
        return new ModelAndView(redirect);
    }


    @RequestMapping("/api/loginBpmDev")
    @ResponseBody
    public User redirectSSOLogin() {

        KeycloakSecurityContext keycloakSecurityContext = (KeycloakSecurityContext)session.getAttribute(KeycloakSecurityContext.class.getName());
        User userInfo = userService.getUserInfo(keycloakSecurityContext.getToken().getPreferredUsername());
        session.setAttribute("userInfo", userInfo);
        log.info("{} 登入成功",userInfo.getUserId());
        return userInfo;

    }

}
