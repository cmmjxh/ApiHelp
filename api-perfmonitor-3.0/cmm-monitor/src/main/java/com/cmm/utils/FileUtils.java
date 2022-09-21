package com.cmm.utils;

import com.alibaba.fastjson.JSON;
import com.cmm.agent.AgentMain;
import com.cmm.vo.LogVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author caomm
 * @Title 导出文件日志
 * @Description TODO
 * @Date 2022/8/21 23:24
 */
public class FileUtils {
    private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);
    static final String RUN_JAR_NAME ="cmm-monitor.jar";
    static String EnvironmentSources="";
    /**
     *  <>
     *      导出追踪链路文件日志log，供客户端解析
     *  </>
     * */
    public static synchronized void write(String logPath , String fileName ,String logData) throws IOException {
        File file = new File(logPath + File.separator + fileName);
        File parent = file.getParentFile();
        if(!parent.exists()) {
            parent.mkdirs();
        }
        if(!file.exists()) {
            file.createNewFile();
        }
        try {
            FileOutputStream fos = new FileOutputStream(file,false);
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            BufferedWriter bw = new BufferedWriter(osw);
            bw.write(logData);
            bw.newLine();
            bw.flush();
            bw.close();
            osw.close();
            fos.close();
        }catch (Exception e) {
            logger.info("【ApiHelp】日志写入失败!");
        }
    }

    /**
     *  清除日志文件
     * */
    public static void clearCacheFile(String logPath , String fileName){
        try {
            File file = new File(logPath+ File.separator + fileName);
            if(file.exists()) {
                file.delete();
            }
        }catch (Exception e){
            logger.info("【ApiHelp】清除缓存失败!");
        }
    }

    /**
     *  获取当前执行程序所在路经
     * */
    public static String getLocalPath() throws UnsupportedEncodingException {
        EnvironmentSources = java.net.URLDecoder.decode(new FileUtils().getClass().getProtectionDomain().getCodeSource().getLocation().getFile(), "UTF-8");
        if (EnvironmentSources != null && !EnvironmentSources.isEmpty()) {
            EnvironmentSources =  EnvironmentSources.replace(RUN_JAR_NAME,"");
            EnvironmentSources = EnvironmentSources.substring(1,EnvironmentSources.length());
        }
        return EnvironmentSources;
    }
}
