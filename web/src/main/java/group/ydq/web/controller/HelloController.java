package group.ydq.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("show")
    public String show(){
        return "Hello World";
    }
}
