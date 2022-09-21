package com.cmm.thread;

import com.cmm.vo.ThreadVo;

import java.io.IOException;
import java.io.InputStream;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author caomm
 * @Title TODO
 * @Description TODO
 * @Date 2022/9/6 16:43
 */
public class GCLog {
    public static List<String> initGC() throws Exception {
        String name = ManagementFactory.getRuntimeMXBean().getName();
        String PID = name.substring(0,name.indexOf("@"));
        String osName = System.getProperty("os.name");
        String command = "";
        if (osName.contains("Windows")) {
            command = "jstat -gc "+PID;
        }
        return executeCommand(command);
    }

    /**
     * 执行command指令
     *
     * @param command
     * @return
     */
    public static List<String> executeCommand(String command) throws IOException {
        String gcContent = "";
        Process process = null;
        InputStream inputStream = null;
        try {
            process = Runtime.getRuntime().exec(command);
            inputStream = process.getInputStream();
            StringBuilder sb = new StringBuilder();
            byte buffer[] = new byte[10];
            while (inputStream.read(buffer) != -1) {
                sb.append(new String(buffer));
            }
            gcContent = sb.toString().replace("\r", "").replace("\n", "");
        } finally {
            if (process != null) {
                process.destroy();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return gcFormatConsole(gcContent);
    }

    /**
     *  输入数据格式化
     * */
    private static List<String> gcFormatConsole(String gcContent) {
        //"S0C       S1C     S0U  S1U     EC        EU        OC        OU       MC       MU       CCSC     CCSU   YGC  YGCT  FGC  FGCT  GCT\n"
        List<String> lgclist= new ArrayList<>();
        List<String> gcLogs = new ArrayList<>();
        String[] split = gcContent.split(" ");
        for (int i = 0; i < split.length; i++) {
            if (split[i] != null && split[i].length() > 0) {
                gcLogs.add(split[i]);
            }
        }
        for (int j = 17; j < gcLogs.size()-1; j++) {
            if(j == 17 ){
                lgclist.add(gcLogs.get(j).substring(1,gcLogs.get(j).length()));
            }else{
                lgclist.add(gcLogs.get(j));
            }
        }
        return lgclist;
    }
}
