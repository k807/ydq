package group.ydq.authority.util;

import org.springframework.context.ApplicationContext;

/**
 * =============================================
 *
 * @author wu
 * @create 2018-11-26 22:50
 * =============================================
 */
public class SpringUtils {
    private SpringUtils() {
    }

    private static ApplicationContext applicationContext;

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static void setApplicationContext(ApplicationContext applicationContext) {
        SpringUtils.applicationContext = applicationContext;
    }
}
