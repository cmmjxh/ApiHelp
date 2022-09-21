package cmm.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author caomm
 * @Title TODO
 * @Description TODO
 * @Date 2022/8/21 20:07
 */
public class DataUtils {
    private static final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String dataStr(){
        return df.format(new Date());
    }
}
