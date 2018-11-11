package group.ydq.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Natsukawa
 * @Date: 2018/11/11 22:10
 * @Version 1.0
 */

@RestController
@RequestMapping("/user/*")
public class TestController {
    @GetMapping("Natsukawa")
    public String getName(){
        return "Natsukawa";
    }

}
