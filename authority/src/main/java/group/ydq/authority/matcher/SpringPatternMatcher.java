package group.ydq.authority.matcher;

import group.ydq.authority.PatternMatcher;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

/**
 * =============================================
 *
 * @author wu
 * @create 2018-11-26 21:45
 * =============================================
 */
public class SpringPatternMatcher implements PatternMatcher {
    private PathMatcher antPathMatcher;

    public SpringPatternMatcher() {
        antPathMatcher = new AntPathMatcher();
    }

    @Override
    public boolean match(String url, String path) {
        return antPathMatcher.match(path, url);
    }
}
