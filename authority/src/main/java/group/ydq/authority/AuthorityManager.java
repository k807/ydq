package group.ydq.authority;

import group.ydq.authority.util.SpringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
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
    private static final String INDEX_PATH = "index-path";
    private Map<String, Object> cofigurer = new ConcurrentHashMap<>();

    {
        cofigurer.put(DEFAULT_SCAN_PATH_KEY, new ArrayList<String>());
        cofigurer.put(INDEX_PATH, "index");
    }

    private AuthorityChecker authorityChecker;
    private PatternMatcher patternMatcher;


    public AuthorityManager(AuthorityChecker authorityChecker, PatternMatcher patternMatcher) {
        this.authorityChecker = authorityChecker;
        this.patternMatcher = patternMatcher;
    }

    @SuppressWarnings("unchecked")
    public void configureDefaultScanPath(String[] path) {
        Object defaultScanPath = cofigurer.get(DEFAULT_SCAN_PATH_KEY);

        if (defaultScanPath instanceof List) {
            List pathList = (List) defaultScanPath;
            pathList.addAll(Arrays.asList(path));
        }
    }


    public void configureIndexPath(String path) {
        cofigurer.put(INDEX_PATH, path);
    }

    public String[] getDefaultScanPath() {
        return (String[]) ((List) cofigurer.get(DEFAULT_SCAN_PATH_KEY)).toArray(new String[0]);
    }

    public String getIndexPath() {
        return (String) cofigurer.get(INDEX_PATH);
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
