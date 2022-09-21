package cmm.util.matcher;

/**
 * 匹配器
 * Created by  on 15/5/17.
 */
public interface Matcher<T> {

    /**
     * 是否匹配
     *
     * @param target 目标字符串
     * @return 目标字符串是否匹配表达式
     */
    boolean matching(T target);

}
