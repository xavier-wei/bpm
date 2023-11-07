package tw.gov.pcc.web.rest.loginBpm;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;
import tw.gov.pcc.domain.User;
import tw.gov.pcc.service.UserService;
import tw.gov.pcc.utils.AESDecryption;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class RedirectSSOLoginResource {
    private final Logger log = LoggerFactory.getLogger(RedirectSSOLoginResource.class);

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
        String id;
        try {
            id = AESDecryption.decrypt(token);
        } catch (Exception e) {
            log.error("bpm嘗試登入失敗，錯誤訊息： {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
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
        User userInfo = (User) session.getAttribute(USER_INFO);
        log.info("{} 登入成功", userInfo.getUserId());
        return gson.toJson(userInfo);
    }


}
