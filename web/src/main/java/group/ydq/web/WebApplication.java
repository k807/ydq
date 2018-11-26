package group.ydq.web;

import group.ydq.authority.annotion.EnableAuthorityManage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

@Configuration
@SpringBootApplication(scanBasePackages = "group.ydq")
// 是否启用权限控制
@EnableAuthorityManage
public class WebApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }
}
