package com.cmm.perfmonitor.trace.data;


import com.cmm.perfmonitor.trace.TraceTree;
import com.cmm.perfmonitor.trace.vo.LogVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author caomm
 * @Title 字符类型打印
 * @Description TODO
 * @Date 2022/8/25 10:17
 */
public class StringLog {
    private static final Logger logger = LoggerFactory.getLogger(TraceTree.class);

    /**
     *  解析出字符日志格式
     * */
    public static String parse(List<LogVo> log) throws Exception {
        StringBuilder logStr = new StringBuilder();
        try {
            for (LogVo logVo : log) {
                if ("@before".equals(logVo.getSign())) {
                    logStr.append(logVo.getTime() + " " + logVo.getSpace() + "|-->" + logVo.getMethod() + "  耗时：" + logVo.getUserTime() + "        " + logVo.getContent() + "\n");
                }
            }
        }catch (Exception e){
            logger.error("链路log解析失败，请重新发起请求后尝试！");
            throw new Exception("链路log解析失败，请重新发起请求后尝试！");
        }
        return logStr.toString();
    }
}
