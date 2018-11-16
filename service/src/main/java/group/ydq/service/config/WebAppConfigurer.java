package group.ydq.service.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * =============================================
 *
 * @author wu
 * @create 2018-11-11 22:43
 * =============================================
 */
@Configuration
public class WebAppConfigurer implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 可添加多个
//        registry.addInterceptor(new PermissionInterceptor()).addPathPatterns("/**");
    }
}
