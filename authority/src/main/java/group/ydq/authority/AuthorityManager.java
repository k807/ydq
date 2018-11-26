package group.ydq.authority;

import group.ydq.authority.util.SpringUtils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * =============================================
 *
 * @author wu
 * @create 2018-11-25 22:27
 * =============================================
 */
public class AuthorityManager {
    private static final String DEFAULT_SCAN_PATH_KEY = "default-scan-path";
    private static final String UNLIMITED_PATH = "unlimited-path";
    private static final String INDEX_PATH = "index-path";
    private static final String LOGIN_PATH = "login-path";
    private static final String LOGOUT_PATH = "logout-path";

    private Map<String, Object> configurer = new ConcurrentHashMap<>();

    {
        configurer.put(DEFAULT_SCAN_PATH_KEY, new ArrayList<String>());
        configurer.put(UNLIMITED_PATH, new HashSet<String>());
        configurer.put(INDEX_PATH, "index");
        configurer.put(LOGIN_PATH, "index");
        configurer.put(LOGOUT_PATH, "index");
    }

    private AuthorityChecker authorityChecker;
    private PatternMatcher patternMatcher;


    public AuthorityManager(AuthorityChecker authorityChecker, PatternMatcher patternMatcher) {
        this.authorityChecker = authorityChecker;
        this.patternMatcher = patternMatcher;
    }

    @SuppressWarnings("unchecked")
    public void configureDefaultScanPath(String[] path) {
        Object defaultScanPath = configurer.get(DEFAULT_SCAN_PATH_KEY);

        if (defaultScanPath instanceof List) {
            List pathList = (List) defaultScanPath;
            pathList.addAll(Arrays.asList(path));
        }
    }


    public void configureIndexPath(String path) {
        configurer.put(INDEX_PATH, path);
    }

    public void configureLoginPath(String path) {
        configurer.put(LOGIN_PATH, path);
    }

    public void configureLogoutPath(String path) {
        configurer.put(LOGOUT_PATH, path);
    }

    public void configureUnlimitedPath(String path) {
        Set paths = (Set) configurer.get(UNLIMITED_PATH);
        paths.add(path);
    }

    public String[] getDefaultScanPath() {
        return (String[]) ((List) configurer.get(DEFAULT_SCAN_PATH_KEY)).toArray(new String[0]);
    }

    public String getIndexPath() {
        return (String) configurer.get(INDEX_PATH);
    }

    public String getLoginPath() {
        return (String) configurer.get(LOGIN_PATH);
    }

    public String getLogoutPath() {
        return (String) configurer.get(LOGOUT_PATH);
    }

    public String[] getUnlimitedPath() {
        return (String[]) ((Set) configurer.get(UNLIMITED_PATH)).toArray(new String[0]);
    }

    public PatternMatcher getPatternMatcher() {
        return this.patternMatcher;
    }

    // 检查某个用户是否有某个url的权限
    // todo: 加缓存
    public boolean checkPermission(Subject subject, String url) {
        String role = authorityChecker.getRoleByUser(subject.getPrincipal());
        String[] permissions = authorityChecker.getPermissionByRole(role);
        for (String permission : permissions) {
            if (patternMatcher.match(url, permission)) {
                return true;
            }
        }
        return false;
    }

    static boolean checkSubject(Subject subject) {
        AuthorityChecker checker = SpringUtils.getApplicationContext().getBean(AuthorityChecker.class);
        return checker.checkPrincipal(subject);
    }

}
