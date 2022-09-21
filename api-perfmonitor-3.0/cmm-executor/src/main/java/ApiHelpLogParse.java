import com.alibaba.fastjson.JSON;
import com.cmm.trace.TraceTree;
import com.cmm.trace.LogVo;
import com.cmm.trace.utils.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author caomm
 * @Title 日志解析
 * @Description TODO
 * @Date 2022/8/21 23:50
 */
public class ApiHelpLogParse {
    private static final Logger logger = LoggerFactory.getLogger(ApiHelpLogParse.class);
    final static String LogPath = "C:\\ApiHelpV1.0\\log\\";

    public static void main(String[] args) throws IOException {
        String fileName = ".log";
        File file = new File(FileUtils.getLocalPath() + File.separator + fileName);
        // 字符tree日志
        System.out.println(TraceTree.parseTree(parse(file) , "String"));
        // 数组tree日志
        System.out.println(TraceTree.parseTree(parse(file),"List").toString());
    }

    public static List<LogVo> parse(File file) throws IOException {
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
            logger.error("日志解析错误，检查API是否正常执行结束，API异常会导致监控失效，请修复异常后，重新启动应用程序 和 监控程序！");
        } finally {
            buffReader.close();
            reader.close();
            fin.close();
        }
        if (logData.length() > 0) {
            resultList = JSON.parseArray(logData.toString(), LogVo.class);
        }
        return resultList;
    }
}
