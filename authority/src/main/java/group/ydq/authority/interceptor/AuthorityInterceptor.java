package group.ydq.authority.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import group.ydq.authority.AuthorityManager;
import group.ydq.authority.PatternMatcher;
import group.ydq.authority.Subject;
import group.ydq.authority.SubjectUtils;
import group.ydq.authority.annotion.Unlimited;
import group.ydq.authority.event.impl.AuthorityEvent;
import group.ydq.model.dto.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * =============================================
 *
 * @author wu
 * @create 2018-11-11 22:34
 * =============================================
 */
public class AuthorityInterceptor implements HandlerInterceptor {

    private static final String SUBJECT_ID = "subjectId";

    private static final String STATUS_ONLINE = "online";
    @Autowired
    private AuthorityManager authorityManager;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestUrl = request.getRequestURI();

        BaseResponse authorityFailResponse = new BaseResponse();
        authorityFailResponse.setStatusCode("401");


        // 获取handlerMethod对象
        HandlerMethod handlerMethod = null;
        if (handler instanceof HandlerMethod) {
            handlerMethod = (HandlerMethod) handler;
        }

        // 1. 检查方法是否有Unlimited注解，有的话则不做权限检查
        if (!Objects.isNull(handlerMethod)) {
            if (handlerMethod.hasMethodAnnotation(Unlimited.class)) {
                return true;
            }
        }

        // 2. 是否是登陆和登出页面，如果是则不做权限检查
        if (requestUrl.equals(authorityManager.getLoginPage())
                || requestUrl.equals(authorityManager.getLogoutPage())) {
            return true;
        }

        PatternMatcher matcher = authorityManager.getPatternMatcher();

        // 3. 检查是否在配置的unlimited的路径下
        for (String path : authorityManager.getUnlimitedPath()) {
            if (matcher.match(requestUrl, path)) {
                return true;
            }
        }

        // 4. 检查是否在配置的权限检查路径下
        for (String path : authorityManager.getDefaultScanPath()) {
            if (matcher.match(requestUrl, path)) {
                // 4.1 检查登陆状态
                Subject subject = SubjectUtils.getSubject();
                // 如果已经登陆并且访问默认页面，则访问通过
                if (SubjectUtils.isOnline(subject)) {
                    if (requestUrl.equals(authorityManager.getIndexPage())) {
                        AuthorityManager.getPublisher().
                                fireAuthenticationSuccessEvent(new AuthorityEvent("visit index page success", request, request.getSession()));
                        return true;
                    }
                    // 限制需要登陆的路径
                    for (String loginLimitedPath : authorityManager.getLoginLimitedPath()) {
                        if (matcher.match(requestUrl, loginLimitedPath)) {
                            AuthorityManager.getPublisher().
                                    fireAuthenticationSuccessEvent(new AuthorityEvent("visit login limited page success", request, request.getSession()));
                            return true;
                        }
                    }
                }
                // 如果没有登陆
                if (!SubjectUtils.isOnline(subject)) {
                    response.sendRedirect(authorityManager.getLoginPage());
                    AuthorityManager.getPublisher().
                            fireAuthenticationFailEvent(new AuthorityEvent("have not login", request, request.getSession()));

                    return false;
                }
                // 4.2 检查权限
                if (authorityManager.checkPermission(subject, requestUrl)) {
                    AuthorityManager.getPublisher().
                            fireAuthenticationSuccessEvent(new AuthorityEvent("authentication success", request, request.getSession()));
                    return true;
                } else {
                    AuthorityManager.getPublisher().
                            fireAuthenticationFailEvent(new AuthorityEvent("authentication fail", request, request.getSession()));
                    authorityFailResponse.setMsg("AUTHORITY FAIL PATH:" + requestUrl);
                    response.getWriter().write(objectMapper.writeValueAsString(authorityFailResponse));
                    return false;
                }

            }
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }
}
