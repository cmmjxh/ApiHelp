package com.cmm.trace.data;

import com.cmm.trace.LogVo;

import java.util.List;

/**
 * @author caomm
 * @Title 字符类型打印
 * @Description TODO
 * @Date 2022/8/25 10:17
 */
public class StringLog {
    /**
     *  解析出字符日志格式
     * */
    public static String parse(List<LogVo> log) {
        StringBuilder logStr = new StringBuilder();
        int seq = 1;
        for (LogVo logVo : log) {
            if ("@before".equals(logVo.getSign())) {
                logStr.append(logVo.getTime() + " " + logVo.getSpace() + "|-->" + logVo.getMethod() + "  耗时：" + logVo.getUserTime() + " " + logVo.getContent() + "\n");
            }
        }
        return logStr.toString();
    }
}
