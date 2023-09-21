package tw.gov.pcc.eip.framework.spring.interceptor;

import tw.gov.pcc.eip.framework.tag.ValidationMessageTag;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * 用於將 Bean Validation 及 Spring Validation 的 Validation Message 放到 Session 供 <code>ValidationMessageTag</code> 使用
 *
 * @author Goston
 */
public class BindingResultInterceptor extends HandlerInterceptorAdapter {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(BindingResultInterceptor.class);
    @Autowired
    private MessageSource messageSource;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
        List<String> list = new ArrayList<>();
        List<String> idList = new ArrayList<>();
        if (modelAndView != null && modelAndView.getModel() != null) {
            Set<String> keySet = modelAndView.getModel().keySet();
            keySet.stream().filter(key -> StringUtils.startsWith(key, BindingResult.MODEL_KEY_PREFIX)).map(key -> (BindingResult) modelAndView.getModel().get(key)).forEach(bindingResult -> bindingResult.getAllErrors().forEach(objectError -> {
                String message = messageSource.getMessage(objectError, null);
                if (StringUtils.isBlank(message)) message = objectError.getCode();
                list.add(message);
                if (objectError instanceof FieldError) {
                    String field = ((FieldError) objectError).getField();
                    if (StringUtils.isBlank(field)) {
                        if (Objects.requireNonNull(objectError.getArguments()).length >= 2) {
                            field = objectError.getArguments()[1].toString();
                        }
                    }
                    if (StringUtils.isNotBlank(field)) {
                        idList.add(field);
                    }
                }
            }));
        }
        HttpSession session = request.getSession();
        if (session != null) {
            session.removeAttribute(ValidationMessageTag.VALIDATION_MESSAGE_SESSION_KEY);
            session.setAttribute(ValidationMessageTag.VALIDATION_MESSAGE_SESSION_KEY, list);
            session.removeAttribute(ValidationMessageTag.VALIDATION_ID_SESSION_KEY);
            session.setAttribute(ValidationMessageTag.VALIDATION_ID_SESSION_KEY, idList);
        }
    }
}
