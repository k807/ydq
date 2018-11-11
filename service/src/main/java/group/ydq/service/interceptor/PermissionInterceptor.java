package group.ydq.service.interceptor;

import group.ydq.service.service.RBACService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * =============================================
 *
 * @author wu
 * @create 2018-11-11 22:34
 * =============================================
 */
public class PermissionInterceptor implements HandlerInterceptor {

    @Autowired
    private RBACService rbacService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        request.getSession();
        return false;
    }
}
