import com.alibaba.fastjson.JSON;
import com.cmm.thread.GCLog;
import com.cmm.utils.DataUtils;
import com.cmm.utils.FileUtils;
import com.cmm.vo.LogVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author caomm
 * @Title
 * <pre>
 *     设置链路处理器，before和 after 方法，通过字节码增强加入目标class，
 *     用于处理方法性能监控业务处理
 * </pre>
 * @Description TODO
 * @Date 2022/8/10 15:39
 */
public class TraceMonitorHandler {
    private static final Logger logger = LoggerFactory.getLogger(TraceMonitorHandler.class);
    private static volatile String requestThreadName;
    private static volatile Thread requestThread = null;
    private static volatile String fristMethdName;
    private static ConcurrentLinkedQueue<LogVo> claszMethodCache = new ConcurrentLinkedQueue<>();
    private static CopyOnWriteArrayList<List<String>> GCLogs = new CopyOnWriteArrayList<>();
    private static LogVo logVo;

    /**
     * 前置处理
     */
    public static synchronized void before(String claszMethod) throws Exception {
        logVo = new LogVo();
        // 清除缓存
        beforeClearCache();
        // 记录入口函数
        recordFristMethod(claszMethod);

        logVo.setMethod(claszMethod);
        logVo.setExecutorTime(System.currentTimeMillis());
        logVo.setTime(DataUtils.dateStr());
        logVo.setSign("@before");

        // 计入类方法链路缓存
        claszMethodCache.add(logVo);

    }
    
    /**
     * 后置处理
     */
    public static synchronized void after(String claszMethod) throws Exception {
        if(requestThread.getName() != Thread.currentThread().getName()){
            throw new Exception("【ApiHelp】已有请求正在监控，请等待！");
        }
        logVo = new LogVo();

        logVo.setMethod(claszMethod);
        logVo.setExecutorTime(System.currentTimeMillis());
        logVo.setTime(DataUtils.dateStr());
        logVo.setSign("@after");
        claszMethodCache.add(logVo);

        // 检查调用链路是否结束
        checkMethodEnd(claszMethod);
    }


    /**
     * 方法异常处理
     */
    public static synchronized void methodException(Exception e) throws Exception {
        if(requestThread.getName() != Thread.currentThread().getName()){
            throw new Exception("【ApiHelp】已有请求正在监控，请等待！");
        }

        // 数据输出导文件

        FileUtils.write(FileUtils.getLocalPath().replace("lib","") +"log" ,"trace.log" , JSON.toJSONString(claszMethodCache));

        // init线程性能
        new ThreadMonitorHandler(requestThread).initThreadMonitorHandler(GCLogs);

        // 清除缓存
        clearCache();
        logger.info("【APIHelp】 方法执行结束，监控log准备完成!");
    }



    /**
     *  方法结束检测
     * */
    private static void checkMethodEnd(String claszMethod) throws Exception {
        if (fristMethdName != null && !fristMethdName.isEmpty()) {
            if (fristMethdName.equals(claszMethod)) {
                // 数据输出导文件
                FileUtils.write(FileUtils.getLocalPath().replace("lib","") +"log" ,"trace.log" , JSON.toJSONString(claszMethodCache));

                // init线程性能
                new ThreadMonitorHandler(requestThread).initThreadMonitorHandler(GCLogs);

                // 清除缓存
                clearCache();
                logger.info("【APIHelp】 方法执行结束，监控log准备完成!");
            }
        }
    }

    /**
     *  记录当前线程名称
     * */
    private static void recordFristMethod(String claszMethod) {
        if (fristMethdName == null || fristMethdName.isEmpty()) {
            fristMethdName = claszMethod;
        }
    }

    /**
     *  清除缓存
     * */
    private static void clearCache() {
        fristMethdName = "";
        requestThread = null;
        requestThreadName = "";
        claszMethodCache.clear();
        GCLogs.clear();
    }

    /**
     *  清除缓存
     * */
    private static void beforeClearCache() throws Exception {
        // 检查当前线程是为新请求线程
        if (requestThreadName == null || requestThreadName.isEmpty()) {

            requestThreadName = Thread.currentThread().getName();
            requestThread =Thread.currentThread();
            // 清除缓存
            claszMethodCache.clear();
            // 删除输出log文件
            FileUtils.clearCacheFile(FileUtils.getLocalPath().replace("lib","") +"log" ,"trace.log");

            // 记录当前GC日志
            GCLogs.clear();
            GCLogs.add(GCLog.initGC());

        } else {
            if (!requestThreadName.equals(Thread.currentThread().getName())) {
                throw new Exception("【ApiHelp】已有线程正在监控中，请等待结束！");
            }
        }
    }
}
