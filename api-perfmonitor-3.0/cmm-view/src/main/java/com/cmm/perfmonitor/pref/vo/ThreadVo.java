package com.cmm.perfmonitor.pref.vo;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author caomm
 * @Title 线程消耗
 * @Description TODO
 * @Date 2022/9/6 11:02
 */
public class ThreadVo {
    /**
     *  阻塞次数
     * */
    private long threadBlockCount;
    /**
     *  阻塞时间
     * */
    private long threadBlockTime;
    /**
     *  cpu消耗
     * */
    private String threadCpuUse;
    /**
     *  内存消耗
     * */
    private String threadMemoryUse;
    /**
     *  gc信息
     * */
    private CopyOnWriteArrayList<List<String>> gcInfo;

    public CopyOnWriteArrayList<List<String>> getGcInfo() {
        return gcInfo;
    }

    public void setGcInfo(CopyOnWriteArrayList<List<String>> gcInfo) {
        this.gcInfo = gcInfo;
    }

    public long getThreadBlockCount() {
        return threadBlockCount;
    }


    public void setThreadBlockCount(long threadBlockCount) {
        this.threadBlockCount = threadBlockCount;
    }

    public long getThreadBlockTime() {
        return threadBlockTime;
    }

    public void setThreadBlockTime(long threadBlockTime) {
        this.threadBlockTime = threadBlockTime;
    }

    public String getThreadCpuUse() {
        return threadCpuUse;
    }

    public void setThreadCpuUse(String threadCpuUse) {
        this.threadCpuUse = threadCpuUse;
    }

    public String getThreadMemoryUse() {
        return threadMemoryUse;
    }

    public void setThreadMemoryUse(String threadMemoryUse) {
        this.threadMemoryUse = threadMemoryUse;
    }

}
