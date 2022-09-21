package com.cmm.utils;

import java.util.LinkedList;

/**
 * @author caomm
 * @Title 用于接受执行日志输出
 * @Description TODO
 * @Date 2022/7/6 16:34
 */
public class LogOutQueue {
    public static LinkedList<String> logLinkList = new LinkedList<>();

    /**
     * 入队列
     */
    public static void add(String logInfo) {
        logLinkList.add(logInfo);
    }

    /**
     * 获取队列数据
     */
    public static LinkedList<String> get() {
        return logLinkList;
    }

    /**
     * 获取队列数据
     */
    public static String getStr() {
        StringBuilder stringBuilder = new StringBuilder();
        if (logLinkList.size() > 0) {
            for (String info : logLinkList){
                stringBuilder.append(info+"\n");
            }
        }
        return stringBuilder.toString();
    }

    /**
     * 清空
     * */
    public static void clear(){
        logLinkList.clear();
    }

    /**
     * 重置日志
     * */
    public static void logResetError(String logInfo){
        logLinkList.clear();
        add(TimeUtils.newTimeError()+logInfo);
    }

}
