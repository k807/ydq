package group.ydq.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * =============================================
 *
 * @author simple
 * @create 2018-11-11 22:05
 * =============================================
 */
@RestController
@RequestMapping("/user/*")
public class SimpleController {
    @GetMapping("simple")
    public String show() {
        return "Hello Simple";
    }
}
