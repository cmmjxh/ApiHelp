package com.cmm.perfmonitor.trace;

import com.cmm.perfmonitor.trace.data.ListTreeLog;
import com.cmm.perfmonitor.trace.data.StringLog;
import com.cmm.perfmonitor.trace.vo.LogVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * @author caomm
 * @Title 日志结果调用链路解析为可视tree
 * @Description TODO
 * @Date 2022/8/22 10:30
 */
public class TraceTree {
    final static String LIST_DATA = "List";
    private static final Logger logger = LoggerFactory.getLogger(TraceTree.class);

    public static Object parseTree(List<LogVo> log , String type) throws Exception {
        try {
            if (log.size() > 0) {
                int len = log.size();
                // @before数据处理
                for (int i = 0; i < len; i++) {

                    if (i == 0) {
                        log.get(0).setSpace("");
                    }
                    if ("@before".equals(log.get(i).getSign())) {
                        // 去除时间和尾部标识
                        String apiBeforeMethod = log.get(i).getMethod();
                        long timeBefore = log.get(i).getExecutorTime();

                        // @after数据处理
                        for (int j = i + 1; j < len; j++) {

                            // 结束计算处理
                            if (("@after").equals(log.get(j).getSign())) {
                                String apiAfterMethod = log.get(j).getMethod();
                                if (apiAfterMethod.equals(apiBeforeMethod)) {
                                    long timeAfter = log.get(j).getExecutorTime();
                                    long useTime = timeAfter - timeBefore;

                                    // 分析content内容，耗时占比${percentage}，后续计算替换
                                    log.get(i).setContent(getContent(useTime));

                                    // 耗时计算
                                    log.get(i).setUserTime(String.valueOf(timeAfter - timeBefore));
                                    break;
                                }
                            } else {
                                // 不是结束则添加 空格符号，意味这当前方法log 不是上一个的结束，而是child
                                String space = "\t";
                                try {
                                    space = (String) log.get(j).getSpace();
                                    if (space != null && !space.isEmpty()) {
                                        space = space + "\t";
                                    } else {
                                        space = "\t";
                                    }
                                } catch (Exception e) {
                                }
                                log.get(j).setSpace(space);
                            }
                        }
                    }

                }
            }
        }catch (Exception e){
            logger.error("链路log解析失败，请重新发起请求后尝试！");
            throw new Exception("链路log解析失败，请重新发起请求后尝试！");
        }
        if(LIST_DATA.equals(type)){
            return ListTreeLog.parse(log);
        }
        return StringLog.parse(log);
    }

    /**
     * 获取提示内容
     */
    private static String getContent(long useTime) {
        String content = "";
        if (useTime >= 3000) {
            return " 【${percentage}】 【Critical】 ";
        }
        if (useTime >= 1000 && useTime < 3000) {
            return "【${percentage}】 【Major】 ";
        }
        if (useTime >= 600 && useTime < 1000) {
            return "【${percentage}】 【Minor】 ";
        }
        return content;
    }


    private static String getKey(Map m) {
        String apKey = "";
        for (Object key : m.keySet()) {
            if (!"space".equals(key) && !"time".equals(key)) {
                apKey = (String) key;
                break;
            }
        }
        return apKey;
    }
}
