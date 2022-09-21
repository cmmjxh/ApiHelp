package cmm.util.matcher;

/**
 * regex matcher
 * @author
 */
public class RegexMatcher implements Matcher<String> {

    private final String pattern;

    public RegexMatcher(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public boolean matching(String target) {
        return null != target
                && null != pattern
                && target.matches(pattern);
    }
}