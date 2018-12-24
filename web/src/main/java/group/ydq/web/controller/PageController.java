package group.ydq.web.controller;

import group.ydq.authority.SubjectUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Daylight
 * @date 2018/11/26 23:12
 */
@Controller
public class PageController {
    @RequestMapping("/")
    public String index(Model model) {
        model.addAttribute("user", SubjectUtils.getSubject().getBindMap("user"));
        return "home";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/register")
    public String register() {
        return "register";
    }

}
