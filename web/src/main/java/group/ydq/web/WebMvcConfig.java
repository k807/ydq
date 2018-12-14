package group.ydq.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Objects;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    /**
     * 配置静态资源的web访问路径，例如上传的文件 abc.png 保存在 D:/book/upload 下
     * 那么在浏览器中访问的路径为：http://localhost:8080/upload/abc.png
     */
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }
}
