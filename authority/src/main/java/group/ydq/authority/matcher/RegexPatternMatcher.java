package group.ydq.authority.matcher;

import group.ydq.authority.PatternMatcher;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * =============================================
 *
 * @author wu
 * @create 2018-11-25 22:36
 * =============================================
 */
public class RegexPatternMatcher implements PatternMatcher {
    @Override
    public boolean match(String url, String path) {
        Pattern pattern = Pattern.compile(path);
        Matcher matcher = pattern.matcher(url);
        return matcher.find();
    }
}
