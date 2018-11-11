package group.ydq.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Daylight
 * @date 2018/11/11 22:37
 */

@RestController
@RequestMapping("/test")
public class TestController {
    /**
     * @return
     */
    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }

    /**
     * @param str
     * @return
     */
    @RequestMapping("input")
    public String input(String str){
        return str;
    }
}
