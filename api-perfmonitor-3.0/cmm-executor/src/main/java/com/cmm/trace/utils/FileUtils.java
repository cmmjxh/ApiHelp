package com.cmm.trace.utils;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * TODO 文件操作类
 *
 * @version 1.0
 * @author: caomm
 * @createTime: 2022/7/1 10:34
 */
public class FileUtils {
    static String EnvironmentSources="";
    static final String RUN_JAR_NAME ="cmm-executor.jar";

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