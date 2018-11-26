package group.ydq.authority;

/**
 * =============================================
 *
 * @author wu
 * @create 2018-11-26 20:42
 * =============================================
 */
public interface PatternMatcher {
    boolean match(String url, String path);
}
