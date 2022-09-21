package com.cmm.perfmonitor.trace.data;

import com.cmm.perfmonitor.trace.vo.LogVo;
import com.cmm.view.utils.JTreeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author caomm
 * @Title TODO
 * @Description TODO
 * @Date 2022/8/25 10:18
 */
public class ListTreeLog {
    private static final Logger logger = LoggerFactory.getLogger(ListTreeLog.class);
    private static final String EXCEPTIONCONTENT = " 【当前方法抛出异常】 ";
    private static final String NUM_0 = "0";

    /**
     * 解析构建tree状结构
     */
    public static List<LogVo> parse(List<LogVo> log) throws Exception {
        List<LogVo> logList = new ArrayList<>();
        List<LogVo> resultList = new ArrayList<>();
        try {
            int seq = 1;
            for (int i = 0; i < log.size(); i++) {
                if ("@before".equals(log.get(i).getSign())) {
                    log.get(i).setSeq(seq++);
                    logList.add(log.get(i));
                }
            }
            getLogLast(logList, -1, null, resultList);
        } catch (Exception e) {
            logger.error("链路log解析失败，请重新发起请求后尝试！");
            throw new Exception("链路log解析失败，请重新发起请求后尝试！");
        }
        return resultList;
    }


    private static List<LogVo> getLogLast(List<LogVo> logList, int spaceLenLast0, LogVo logVoLast0, List<LogVo> resultList) {
        int spaceLenLast = 0;
        LogVo logVoLast = new LogVo();

        // 首层耗时
        String parentUseTime = (logList.get(0).getUserTime() == null || logList.get(0).getUserTime().isEmpty()) ? "0" : logList.get(0).getUserTime();

        for (int i = 0; i < logList.size(); i++) {
            LogVo logVo = logList.get(i);
            int spaceLen = logVo.getSpace().length();
            int seq = logVo.getSeq();

            // 计算耗时占比
            String useTime = logVo.getUserTime();
            // 如果useTime 为null 代表当前方法抛出异常，未产生@after日志
            if (useTime == null || useTime.isEmpty()) {
                logVo.setContent(EXCEPTIONCONTENT);
                logVo.setUserTime("-1");
            } else {
                String useTimePercentage = "0.0%";
                if (!NUM_0.equals(parentUseTime)) {
                    useTimePercentage = JTreeUtils.timePercentage(useTime, parentUseTime);
                }

                logVo.setContent(logVo.getContent().replace("${percentage}", useTimePercentage));
            }

            // 首层
            if (spaceLen == 0) {
                resultList.add(logList.get(i));
                spaceLenLast = spaceLen;
                logVoLast = logList.get(i);
                continue;
            }

            // 下阶层
            if (spaceLen > spaceLenLast) {
                logVoLast.getChildList().add(logVo);

                spaceLenLast = spaceLen;
                logVoLast = logList.get(i);
                continue;
            }

            // 同阶层
            if (spaceLen <= spaceLenLast) {
                // 获取最近的上阶级对象
                LogVo logLately = getLogLately(logList, resultList, i, spaceLen, seq);

                if (logLately != null) {
                    logLately.getChildList().add(logVo);
                    spaceLenLast = spaceLen;
                    logVoLast = logList.get(i);
                }
            }
        }
        return resultList;
    }

    /**
     * 获取上阶层日志对象
     */
    private static LogVo getLogLately(List<LogVo> logList, List<LogVo> resultList, int i, int spaceLen, int seq) {
        int seqLogLately = 0;
        for (int j = i - 1; j < i; j--) {
            int spaceLatelyLen = logList.get(j).getSpace().length();
            if (spaceLatelyLen == (spaceLen - 1)) {
                seqLogLately = logList.get(j).getSeq();
                break;
            }
        }
        return getLogLately(resultList.get(0), seqLogLately);
    }

    /**
     * 获取上阶层日志对象
     */
    private static LogVo getLogLately(LogVo logVo, int seqLogLately) {
        if (seqLogLately == logVo.getSeq()) {
            return logVo;
        } else {
            List<LogVo> childList = logVo.getChildList();
            if (childList.size() > 0) {
                for (LogVo logVoc : childList) {
                    LogVo logVoResult = getLogLately(logVoc, seqLogLately);
                    if (logVoResult != null) {
                        return logVoResult;
                    }
                }
            }
        }
        return null;
    }
}
