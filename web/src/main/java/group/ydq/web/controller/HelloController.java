package group.ydq.web.controller;

import group.ydq.authority.Subject;
import group.ydq.authority.SubjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * =============================================
 *
 * @author wu
 * @create 2018-11-11 18:15
 * =============================================
 */
@RestController
@RequestMapping("/user/*")
public class HelloController {

    @RequestMapping(value = "hello", method = RequestMethod.GET)
    public String show() {
        return "Hello World";
    }


    @RequestMapping(value = "index", method = RequestMethod.GET)
    public String index() {
        return "you have not login, please login";
    }

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public void login(String username, String password, HttpServletResponse response) throws IOException {
        Subject subject = SubjectUtils.getSubject();
        subject.setPrincipal(username);
        subject.setAccess(password);
        if (subject.login()) {
            response.sendRedirect("/user/hello");
        } else {
            response.sendRedirect("/user/index");
        }
    }

    @RequestMapping(value = "logout", method = RequestMethod.GET)
    public void logout(HttpServletResponse response) throws IOException {
        SubjectUtils.getSubject().logout();
        response.sendRedirect("/user/index");
    }
}
