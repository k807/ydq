package group.ydq.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Daylight
 * @date 2018/11/26 23:12
 */
@Controller
public class PageController {
    @RequestMapping("/")
    public String index() {
        return "home.html";
    }

    @RequestMapping("/login")
    public String login() {
        return "login.html";
    }

    @RequestMapping("/register")
    public String register() {
        return "register.html";
    }

    @RequestMapping("/pm")
    public String message() {
        return "privateMessage.html";
    }

    @RequestMapping("/pmTest")
    public String messageTest() {
        return "pmTest.html";
    }
}
