package com.cmm.thread;

import com.cmm.vo.ThreadVo;
import com.sun.management.OperatingSystemMXBean;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.lang.management.ThreadMXBean;

/**
 * @author caomm
 * @Title TODO
 * @Description TODO
 * @Date 2022/9/6 13:34
 */
public class ThreadCPU {

    /**
     *  获取当前线程CPU占用率
     *  <pre>
     *     计算公式（进程计算公式）： float cpuUsage = Math.min(99F,elapsedCpu / (elapsedTime * 10000F * result.nCPUs));
     *     线程的cpu时间ThreadMXBean的getThreadCpuTime
     *     jvm运行时间RuntimeMXBean的getUptime
     *     jvm可用的核心数OperatingSystemMXBean的getAvailableProcessors
     * <pre/>
     * */
    public static void countCpuRate(ThreadVo threadVo, Thread requestThread, ThreadMXBean threadMXBean, RuntimeMXBean runtimeMXBean) {
        OperatingSystemMXBean operatingSystemMXBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        long threadCpuTime = threadMXBean.getThreadCpuTime(requestThread.getId());
        long uptime = runtimeMXBean.getUptime();
        int availableProcessors = operatingSystemMXBean.getAvailableProcessors();

        float cpuUsage = Math.min(99F,threadCpuTime / (uptime * 10000F * availableProcessors));
        threadVo.setThreadCpuUse(String.valueOf(cpuUsage));
    }
}

















