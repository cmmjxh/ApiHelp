package com.cmm.perfmonitor;

import com.alibaba.fastjson.JSON;
import com.cmm.perfmonitor.trace.TraceTree;
import com.cmm.perfmonitor.trace.vo.LogVo;
import com.cmm.utils.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author caomm
 * @Title 日志解析
 * @Description TODO
 * @Date 2022/8/21 23:50
 */
public class TraceTreeLogParse {
    final static String fileName = "trace.log";
    private static final Logger logger = LoggerFactory.getLogger(TraceTreeLogParse.class);

    public static List<LogVo> getLogTreeList() throws Exception {
        File file = new File(FileUtils.getLocalPath() + "../log" + File.separator + fileName);
        return (List<LogVo>) TraceTree.parseTree(parse(file), "List");
    }

    public static List<LogVo> parse(File file) throws Exception {
        List<LogVo> resultList = new ArrayList<>();

        FileInputStream fin = null;
        InputStreamReader reader = null;
        BufferedReader buffReader = null;
        StringBuilder logData = new StringBuilder();
        try {
            fin = new FileInputStream(file);
            reader = new InputStreamReader(fin);
            buffReader = new BufferedReader(reader);
            String strTmp = "";
            while ((strTmp = buffReader.readLine()) != null) {
                logData.append(strTmp);
            }
        } catch (Exception e) {
            logger.error("链路log解析失败，检查API是否正常执行结束，API异常会导致监控失效，请修复异常后，重新启动应用程序 和 监控程序后尝试！");
            throw new Exception("链路log解析失败，检查API是否正常执行结束，API异常会导致监控失效，请修复异常后，重新启动应用程序 和 监控程序后尝试！");
        } finally {
            if (fin != null) {
                buffReader.close();
                reader.close();
                fin.close();
            }
        }
        if (logData.length() > 0) {
            resultList = JSON.parseArray(logData.toString(), LogVo.class);
        }
        return resultList;
    }
}
