package group.ydq.authority;

import group.ydq.authority.event.EventPublisher;
import group.ydq.authority.event.Listener;
import group.ydq.authority.event.ListenerRegister;
import group.ydq.authority.event.impl.DefaultEventPublisher;
import group.ydq.authority.util.SpringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * =============================================
 *
 * @author wu
 * @create 2018-11-25 22:27
 * =============================================
 */
public class AuthorityManager implements ListenerRegister {
    private static Logger logger = LoggerFactory.getLogger(AuthorityManager.class);

    // 拦截的页面路径, 支持匹配器路径匹配
    private static final String DEFAULT_SCAN_PATH_KEY = "default-scan-path";
    // 不做拦截的页面路径, 支持匹配器路径匹配
    private static final String UNLIMITED_PATH = "unlimited-path";
    // 登陆成功默认跳转的页面
    private static final String INDEX_PAGE = "index-page";
    // 登陆的页面路径，不做拦截
    private static final String LOGIN_PAGE = "login-page";
    // 登出的页面路径，不做拦截
    private static final String LOGOUT_PAGE = "logout-page";
    // 需要登陆才能访问的路径, 支持匹配器路径匹配
    private static final String LOGIN_LIMITED_PATH = "limited-login";

    private Map<String, Object> configurer = new ConcurrentHashMap<>();
    private Map<String, Listener> listeners = new ConcurrentHashMap<>();

    {
        configurer.put(DEFAULT_SCAN_PATH_KEY, new HashSet<>());
        configurer.put(UNLIMITED_PATH, new HashSet<String>());
        configurer.put(LOGIN_LIMITED_PATH, new HashSet<String>());
        configurer.put(INDEX_PAGE, "index");
        configurer.put(LOGIN_PAGE, "index");
        configurer.put(LOGOUT_PAGE, "index");
    }

    private AuthorityChecker authorityChecker;
    private PatternMatcher patternMatcher;
    private static EventPublisher publisher;

    public AuthorityManager(AuthorityChecker authorityChecker, PatternMatcher patternMatcher) {
        this.authorityChecker = authorityChecker;
        this.patternMatcher = patternMatcher;
        publisher = new DefaultEventPublisher(this);
    }

    public AuthorityManager(AuthorityChecker authorityChecker, PatternMatcher patternMatcher, EventPublisher publisher) {
        this.authorityChecker = authorityChecker;
        this.patternMatcher = patternMatcher;
        AuthorityManager.publisher = publisher;
    }

    @SuppressWarnings("unchecked")
    public void configureDefaultScanPath(String[] path) {
        Object defaultScanPath = configurer.get(DEFAULT_SCAN_PATH_KEY);

        if (defaultScanPath instanceof Set) {
            Set pathList = (Set) defaultScanPath;
            pathList.addAll(Arrays.asList(path));
        }
    }


    public void configureIndexPage(String page) {
        configurer.put(INDEX_PAGE, page);
    }

    public void configureLoginPage(String page) {
        configurer.put(LOGIN_PAGE, page);
    }

    public void configureLogoutPage(String page) {
        configurer.put(LOGOUT_PAGE, page);
    }

    public void configureUnlimitedPath(String path) {
        Set paths = (Set) configurer.get(UNLIMITED_PATH);
        paths.add(path);
    }

    public void configureLoginLimitedPath(String path) {
        Set paths = (Set) configurer.get(LOGIN_LIMITED_PATH);
        paths.add(path);
    }

    public String[] getDefaultScanPath() {
        return (String[]) ((Set) configurer.get(DEFAULT_SCAN_PATH_KEY)).toArray(new String[0]);
    }

    public String getIndexPage() {
        return (String) configurer.get(INDEX_PAGE);
    }

    public String getLoginPage() {
        return (String) configurer.get(LOGIN_PAGE);
    }

    public String getLogoutPage() {
        return (String) configurer.get(LOGOUT_PAGE);
    }

    public String[] getUnlimitedPath() {
        return (String[]) ((Set) configurer.get(UNLIMITED_PATH)).toArray(new String[0]);
    }

    public String[] getLoginLimitedPath() {
        return (String[]) ((Set) configurer.get(LOGIN_LIMITED_PATH)).toArray(new String[0]);
    }

    public PatternMatcher getPatternMatcher() {
        return this.patternMatcher;
    }

    public static EventPublisher getPublisher() {
        return publisher;
    }

    // 检查某个用户是否有某个url的权限
    // todo: 加缓存
    public boolean checkPermission(Subject subject, String url) {
        try {
            String role = authorityChecker.getRoleByUser(subject.getPrincipal());
            String[] permissions = authorityChecker.getPermissionByRole(role);
            for (String permission : permissions) {
                if (patternMatcher.match(url, permission)) {
                    return true;
                }
            }
        } catch (NullPointerException e) {
            logger.error("cant find roles or permissions, so no interception!");
            return true;
        }
        return false;
    }

    static boolean checkSubject(Subject subject) {
        AuthorityChecker checker = SpringUtils.getApplicationContext().getBean(AuthorityChecker.class);
        return checker.checkPrincipal(subject);
    }

    @Override
    public List<Listener> getListeners() {
        return new ArrayList<>(listeners.values());
    }

    @Override
    public Listener getListenerByName(String name) {
        return listeners.get(name);
    }

    @Override
    public void registListener(String name, Listener listener) {
        listeners.put(name, listener);
    }
}
