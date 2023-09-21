package tw.gov.pcc.eip.framework.spring.interceptor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import tw.gov.pcc.eip.framework.websocket.WebSocketMessage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Optional;

/**
 * 於 Request 處理完成發送畫面鎖定解鎖的 WebSocket 訊息
 * HandlerInterceptorAdapter 已被標示@Deprecated，用HandlerInterceptor改寫。
 *
 * @author swho
 */
@Slf4j
@RequiredArgsConstructor
public class DoubleSubmitInterceptor implements HandlerInterceptor {
    private final SimpMessagingTemplate messagingTemplate;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        Optional.ofNullable(request.getSession())
                .map(HttpSession::getId)
                .ifPresent(userId -> messagingTemplate.convertAndSendToUser(userId, "/queue/unlock", new WebSocketMessage("Unlock")));
    }
}
