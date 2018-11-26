package group.ydq.authority;

import group.ydq.authority.interceptor.AuthorityInterceptor;
import group.ydq.authority.util.SpringUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * =============================================
 *
 * @author wu
 * @create 2018-11-25 22:23
 * =============================================
 */
public class AuthorityConfigurer implements WebMvcConfigurer, ApplicationContextAware {


    @Bean
    public AuthorityInterceptor authorityInterceptor() {
        return new AuthorityInterceptor();
    }


    @DependsOn("authorityInterceptor")
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor((HandlerInterceptor) SpringUtils.getApplicationContext().getBean("authorityInterceptor"));
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringUtils.setApplicationContext(applicationContext);
    }
}
