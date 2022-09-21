package cmm.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * @author caomm
 * @Title 工具类
 * @Description TODO
 * @Date 2022/8/5 14:30
 */
public class FileUtils {
    private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);
    static String EnvironmentSources = "";
    static final String RUN_JAR_NAME = "cmm-enhance.jar";

    /**
     * classPath 格式转换
     */
    public static String classPathChange(String classPath, String packagePath) {
        if (!classPath.isEmpty()) {
            try {
                return classPath.replace(packagePath + "/", "").replace("/", ".");
            } catch (Exception e) {
                logger.info("【ApiHelp】类方法名称转换异常" + e);
            }
        }
        return "";
    }

    /**
     * classPath 格式转换
     */
    public static String classPathChange(String className) {
        if (!className.isEmpty()) {
            try {
                return className.replace("/", ".");
            } catch (Exception e) {
                logger.info("【ApiHelp】类方法名称转换异常" + e);
            }
        }
        return "";
    }

    /**
     * classPath 格式转换
     */
    public static String classPathChange0(String classPath) {
        if (!classPath.isEmpty()) {
            try {
                return classPath.replace(".", "/");
            } catch (Exception e) {
                logger.info("【ApiHelp】类方法名称转换异常" + e);
            }
        }
        return "";
    }

    /**
     * 写入out流
     */
    public static void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1048576];
        boolean var3 = false;
        int len;
        while ((len = in.read(buffer)) > -1) {
            out.write(buffer, 0, len);
        }
    }

    public static String[] classPathChange1(String ignoreFile) {
        if (ignoreFile != null && !ignoreFile.isEmpty()) {
            return ignoreFile.split(",");
        }
        return null;
    }

    /**
     * 获取当前执行程序所在路经
     */
    public static String getLocalPath() throws UnsupportedEncodingException {
        EnvironmentSources = java.net.URLDecoder.decode(new FileUtils().getClass().getProtectionDomain().getCodeSource().getLocation().getFile(), "UTF-8");
        if (EnvironmentSources != null && !EnvironmentSources.isEmpty()) {
            EnvironmentSources = EnvironmentSources.replace(RUN_JAR_NAME, "");
            EnvironmentSources = EnvironmentSources.substring(1, EnvironmentSources.length());
        }
        return EnvironmentSources;
    }

    public static String getClassName(String classzPath) {
        if (classzPath.isEmpty()) {
            return "";
        }
        return classzPath.substring(classzPath.lastIndexOf(".")+1,classzPath.length());
    }

    public static void writeByte(String classzPath , byte[] classfileBuffer) throws IOException {
        File file = new File("E:\\桌面\\Desktop\\text\\"+classzPath+".class");
        OutputStream outputStream = new FileOutputStream(file);
        outputStream.write(classfileBuffer);
        outputStream.close();
    }
}
