package tw.gov.pcc.web.rest.loginBpm;

import com.google.gson.Gson;
import org.keycloak.KeycloakSecurityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import tw.gov.pcc.domain.User;
import tw.gov.pcc.service.UserService;
import tw.gov.pcc.utils.AESDecryption;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class RedirectSSOLoginResource {
    private final Logger log = LoggerFactory.getLogger(RedirectSSOLoginResource.class);
    private final RestTemplate restTemplate = new RestTemplate();
    @Value("${bpm.key}")
    private String key;
    private static final String USER_INFO = "userInfo";
    private final HttpSession session;
    private final UserService userService;

    public RedirectSSOLoginResource(HttpSession session, UserService userService) {
        this.session = session;
        this.userService = userService;
    }

    @RequestMapping("/api/loginBpm")
    public ModelAndView redirectSSOLogin(
        @RequestParam("referer") String referer,
        @RequestParam("path") String path,
        @RequestParam("token") String token, HttpServletResponse response) {
        session.invalidate();
        String id = null;
        try {
            id = AESDecryption.decrypt(token, key);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        User userInfo = userService.getUserInfo(id);
        log.info("User {} 登入bpm系統", userInfo);
        session.removeAttribute(USER_INFO);
        session.setAttribute(USER_INFO, userInfo);
        String redirect = "redirect:" + referer + "/eip" + path;
        response.setHeader("isBpmLogin", "true");
        return new ModelAndView(redirect).addObject("bpm", "true");
    }


    @RequestMapping("/api/loginBpmDev")
    @ResponseBody
    public String redirectSSOLogin() {
        Gson gson = new Gson();
//        KeycloakSecurityContext keycloakSecurityContext = (KeycloakSecurityContext) session.getAttribute(KeycloakSecurityContext.class.getName());
//        User userInfo = userService.getUserInfo(keycloakSecurityContext.getToken().getPreferredUsername());
//        System.out.println("帳號:" + keycloakSecurityContext.getToken().getPreferredUsername());
        User userInfo = (User) session.getAttribute(USER_INFO);
        log.info("{} 登入成功", userInfo.getUserId());
        return gson.toJson(userInfo);

    }

    @RequestMapping("/api/logout")
    public String logout() {
        KeycloakSecurityContext keycloakSecurityContext = (KeycloakSecurityContext) session.getAttribute(KeycloakSecurityContext.class.getName());
        KeycloakSecurityContext attribute = (KeycloakSecurityContext) session.getAttribute(KeycloakSecurityContext.class.getName());
        String keycloakLogoutUrl = attribute
            .getIdToken()
            .getIssuer() + "/protocol/openid-connect/logout";
        log.info("User {} 登出", keycloakSecurityContext.getToken().getPreferredUsername());
        session.invalidate();
        return "redirect:"+keycloakLogoutUrl;
    }
}
