package group.ydq.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

@Configuration
@SpringBootApplication(scanBasePackages = "group.ydq")
// 是否启用权限控制
//@EnableAuthorityManage
public class WebApplication {

    @Bean
    public WebServerFactoryCustomizer<ConfigurableWebServerFactory> containerCustomizer() {

        return container -> {
            ErrorPage error401Page = new ErrorPage(HttpStatus.UNAUTHORIZED, "/error.html");
            ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/404.html");
            ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error.html");

            container.addErrorPages(error401Page, error404Page, error500Page);
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }
}
