package group.ydq.service.config;

import group.ydq.authority.AuthorityChecker;
import group.ydq.authority.AuthorityManager;
import group.ydq.authority.PatternMatcher;
import group.ydq.service.authority.AuthorityCheckerImpl;
import group.ydq.authority.matcher.RegexPatternMatcher;
import group.ydq.service.authority.LogListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * =============================================
 *
 * @author wu
 * @create 2018-11-11 22:43
 * =============================================
 */
@Configuration
public class AuthorityConfigurer {

    @Bean
    public AuthorityManager authorityManager(AuthorityChecker authorityChecker, PatternMatcher patternMatcher) {
        AuthorityManager manager = new AuthorityManager(authorityChecker, patternMatcher);
        manager.configureDefaultScanPath(new String[]{".*"});
        manager.configureIndexPath("/user/index");
        manager.configureLoginPath("/user/login");
        manager.configureLogoutPath("/user/logout");
        // 配置不受检查的路径 也可以使用@Unlimited注解
        manager.configureUnlimitedPath("/test/hello");
        // 支持注册监听器
        manager.registListener("logListener", new LogListener());
        return manager;
    }

    @Bean
    public AuthorityChecker authorityChecker() {
        return new AuthorityCheckerImpl();
    }

    /**
     * 提供两种路径匹配器：
     * <p>
     * 1. springmvc 的路径匹配器 : SpringPatternMatcher
     * 2. 正则表达式的路径匹配器  ： RegexPatternMatcher
     *
     * @return
     */
    @Bean
    public PatternMatcher patternMatcher() {
        return new RegexPatternMatcher();
    }

}
