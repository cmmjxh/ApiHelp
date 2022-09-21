package com.cmm.perfmonitor.trace.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author caomm
 * @Title TODO
 * @Description TODO
 * @Date 2022/8/22 12:11
 */
public class LogVo {
    // 方法名称
    private String method;

    // 空格符
    private String space;

    // 记录log时间
    private String time;

    // 方法消耗时间
    private String userTime;

    // 标记
    private String sign;

    // 执行时间戳
    private long executorTime;

    private String callTime;

    private String content;

    private int seq;

    private List<LogVo> childList = new ArrayList<>();

    private LogVo parentLogVo;


    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }
    public LogVo getParentLogVo() {
        return parentLogVo;
    }

    public void setParentLogVo(LogVo parentLogVo) {
        this.parentLogVo = parentLogVo;
    }

    public List<LogVo> getChildList() {
        return childList;
    }

    public void setChildList(List<LogVo> childList) {
        this.childList = childList;
    }

    public String getCallTime() {
        return callTime;
    }

    public void setCallTime(String callTime) {
        this.callTime = callTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public long getExecutorTime() {
        return executorTime;
    }

    public void setExecutorTime(long executorTime) {
        this.executorTime = executorTime;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getSpace() {
        return space;
    }

    public void setSpace(String space) {
        this.space = space;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUserTime() {
        return userTime;
    }

    public void setUserTime(String userTime) {
        this.userTime = userTime;
    }
}
