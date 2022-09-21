import com.alibaba.fastjson.JSON;
import com.cmm.thread.GCLog;
import com.cmm.thread.ThreadCPU;
import com.cmm.utils.FileUtils;
import com.cmm.vo.ThreadVo;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author caomm
 * @Title TODO
 * @Description TODO
 * @Date 2022/9/6 10:30
 */
public class ThreadMonitorHandler {

    private Thread requestThread;
    private ThreadMXBean threadMXBean;
    private ThreadInfo threadInfo;
    private ThreadVo threadVo;
    private RuntimeMXBean runtimeMXBean;

    ThreadMonitorHandler(Thread requestThread) {
        this.requestThread = requestThread;
        this.threadMXBean = ManagementFactory.getThreadMXBean();
        this.threadInfo = this.threadMXBean.getThreadInfo(Thread.currentThread().getId());
        this.threadVo = new ThreadVo();
        this.runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        // 开启
        threadMXBean.setThreadContentionMonitoringEnabled(true);
    }

    public void initThreadMonitorHandler(CopyOnWriteArrayList<List<String>> GCLogs) throws Exception {
        FileUtils.clearCacheFile(FileUtils.getLocalPath().replace("lib", "") + "log", "perf.log");

        // 线程阻塞信息
        initThreadBlockCount();
        initThreadBlockTime();
        // cpu占用率
        ThreadCPU.countCpuRate(threadVo, requestThread, threadMXBean, runtimeMXBean);
        // gc信息
        GCLogs.add(GCLog.initGC());
        threadVo.setGcInfo(GCLogs);

        // 日志记录
        FileUtils.write(FileUtils.getLocalPath().replace("lib", "") + "log", "perf.log", JSON.toJSONString(threadVo));
    }

    private void initThreadBlockTime() {
        threadVo.setThreadBlockTime(this.threadInfo.getBlockedTime());
    }

    private void initThreadBlockCount() {
        threadVo.setThreadBlockCount(this.threadInfo.getBlockedCount());
    }
}
