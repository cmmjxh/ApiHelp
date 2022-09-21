package com.cmm.perfmonitor;

import com.alibaba.fastjson.JSON;
import com.cmm.perfmonitor.pref.vo.ThreadVo;
import com.cmm.perfmonitor.trace.TraceTree;
import com.cmm.perfmonitor.trace.vo.LogVo;
import com.cmm.utils.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author caomm
 * @Title 性能日志解析
 * @Description TODO
 * @Date 2022/9/6 17:34
 */
public class PrefLogParse {
    final static String fileName = "perf.log";
    private static final Logger logger = LoggerFactory.getLogger(PrefLogParse.class);
    /**
     * API性能日志解析
     * */
    public static ThreadVo parse() throws Exception {
        File file = new File(FileUtils.getLocalPath() + "../log" + File.separator + fileName);
        return dataParse(file);
    }

    public static ThreadVo dataParse(File file) throws Exception {
        ThreadVo threadVo = new ThreadVo();

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
            logger.error("性能log解析失败！检查是否正确进行监控操作。");
        } finally {
            if (fin != null) {
                buffReader.close();
                reader.close();
                fin.close();
            }
        }
        if (logData.length() > 0) {
            threadVo = (ThreadVo) JSON.parseObject(logData.toString(), ThreadVo.class);
        }
        return threadVo;
    }
}
