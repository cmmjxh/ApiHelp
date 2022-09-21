package cmm.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class StringUtils {
    private static final Logger logger = LoggerFactory.getLogger(StringUtils.class);

    public static boolean isEmpty(Object str) {
        return str == null || "".equals(str);
    }

    public static boolean hasLength(String str) {
        return hasLength((CharSequence) str);
    }

    public static boolean hasLength(CharSequence str) {
        return str != null && str.length() > 0;
    }

    public static String replace(String inString, String oldPattern, String newPattern) {
        if (hasLength(inString) && hasLength(oldPattern) && newPattern != null) {
            int pos = 0;
            int index = inString.indexOf(oldPattern);
            if (index < 0) {
                //no need to replace
                return inString;
            }

            StringBuilder sb = new StringBuilder();
            for (int patLen = oldPattern.length(); index >= 0; index = inString.indexOf(oldPattern, pos)) {
                sb.append(inString, pos, index);
                sb.append(newPattern);
                pos = index + patLen;
            }

            sb.append(inString.substring(pos));
            return sb.toString();
        } else {
            return inString;
        }
    }

    public static boolean isCEmpty(String str) {
        if ("null".equals(String.valueOf(str)) || str.isEmpty()) {
            return true;
        }else{
            return false;
        }
    }
}
