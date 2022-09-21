package com.cmm.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * TODO 类描述
 *
 * @version 1.0
 * @author: caomm
 * @createTime: 2022/6/30 16:40
 */
public class TimeUtils {
    static final SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String newTime() {
        return s.format(new Date());
    }

    public static String newTimeError() {
        return s.format(new Date())+" 错误：";
    }

    public static String newTimeSuccess() {
        return s.format(new Date())+" 成功：";
    }
}