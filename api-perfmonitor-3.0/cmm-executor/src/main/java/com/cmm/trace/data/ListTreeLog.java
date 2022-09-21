package com.cmm.trace.data;

import com.cmm.trace.LogVo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author caomm
 * @Title TODO
 * @Description TODO
 * @Date 2022/8/25 10:18
 */
public class ListTreeLog {
    /**
     *  解析构建tree状结构
     * */
    public static List<LogVo> parse(List<LogVo> log) {
        List<LogVo> logList = new ArrayList<>();
        int seq = 1;
        for (int i = 0; i < log.size(); i++) {
            if ("@before".equals(log.get(i).getSign())) {
                log.get(i).setSeq(seq++);
                logList.add(log.get(i));
            }
        }
        List<LogVo> resultList = new ArrayList<>();
        getLogLast(logList, -1, null, resultList);
        return resultList;
    }


    private static List<LogVo> getLogLast(List<LogVo> logList, int spaceLenLast0, LogVo logVoLast0, List<LogVo> resultList) {
        int spaceLenLast = 0;
        LogVo logVoLast = new LogVo();

        for (int i = 0; i < logList.size(); i++) {
            int spaceLen = logList.get(i).getSpace().length();
            int seq = logList.get(i).getSeq();
            LogVo logVo = logList.get(i);
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
                logLately.getChildList().add(logVo);
                spaceLenLast = spaceLen;
                logVoLast = logList.get(i);
            }
        }
        return resultList;
    }

    /**
     *  获取上阶层日志对象
     * */
    static LogVo logLately0 = new LogVo();
    private static LogVo getLogLately(List<LogVo> logList, List<LogVo> resultList, int i, int spaceLen, int seq) {
        int seqLogLately = 0;
        for (int j = i - 1; j < i; j--) {
            int spaceLatelyLen = logList.get(j).getSpace().length();
            if (spaceLatelyLen == (spaceLen - 1)) {
                seqLogLately = logList.get(j).getSeq();
                break;
            }
        }
        getLogLately(resultList, seqLogLately , 0 );
        return logLately0;
    }

    /**
     *  获取上阶层日志对象
     * */
    private static void getLogLately(List<LogVo> resultList, int seqLogLately , int seq) {
        for (int i = 0; i < resultList.size(); i++) {
            seq = resultList.get(i).getSeq();
            if(seq != seqLogLately){
                resultList = resultList.get(i).getChildList();
                getLogLately(resultList,seqLogLately,seq);
            }
            if(seq == seqLogLately){
                logLately0 = resultList.get(i);
                return;
            }
        }
    }
}
