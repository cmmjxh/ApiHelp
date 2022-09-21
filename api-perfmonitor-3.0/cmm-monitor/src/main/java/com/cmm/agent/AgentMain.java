package com.cmm.agent;

import com.cmm.classloader.MyClassLoader;
import com.cmm.utils.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.lang.instrument.Instrumentation;
import java.lang.reflect.Method;
import java.net.URL;

/**
 * @Title 代理启动类
 * @Author 曹敏敏
 * @Date 2022/8/17 17:43
 */
public class AgentMain {
    private static final Logger logger = LoggerFactory.getLogger(AgentMain.class);

    private static volatile ClassLoader myClassLoader;

    public static void agentmain(String args, Instrumentation inst) throws Throwable {
        logger.info("【APIHelp】 开启API监控...");

        // 初始化PerfMonitorInterceptor
        initPerfMonitorInterceptor();

        // 初始化 initMainBoot
        initMainBoot(args, inst);

    }

    /**
     * 初始化initMainBoot，自定义类加载器加载，防止入侵业务类
     */
    private static void initMainBoot(String args, Instrumentation inst) throws Throwable {
        File coreJarFile = new File(FileUtils.getLocalPath()  + File.separator + "cmm-enhance.jar");
        logger.info("【ApiHelp】注入增强包："+coreJarFile.toString());
        if (!coreJarFile.exists()) {
            return;
        }
        try {
            final ClassLoader myClassLoder = getClassLoader(coreJarFile);
            Class<?> bootstrapClass = myClassLoder.loadClass("MainBoot");
            Object obj = bootstrapClass.newInstance();
            Method bootstrap = bootstrapClass.getMethod("invoke", String.class, Instrumentation.class);
            bootstrap.invoke(obj, args, inst);
        }catch (Exception e){
            logger.info("【ApiHelp】启动MainBoot.invoke异常："+e);
        }

    }

    /**
     * 初始化initPerfMonitorInterceptor，使用app类加载器加载，目的使得业务类能正常访问该类
     */
    private static void initPerfMonitorInterceptor() {
        ClassLoader parent = ClassLoader.getSystemClassLoader();
        Class<?> clasz = null;
        if (parent != null) {
            try {
                clasz = parent.loadClass("TraceMonitorHandler");
            } catch (Exception e) {
            }
        }
    }

    /**
     * 获取自定义类加载器，目的防止业务类中有重复类名加载
     */
    private static ClassLoader getClassLoader(File coreJarFile) throws Throwable {
        if (myClassLoader == null) {
            myClassLoader = new MyClassLoader(new URL[]{coreJarFile.toURI().toURL()});
        }
        return myClassLoader;
    }
}
