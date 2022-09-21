import Utils.ProcessUtls;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static Utils.ProcessUtls.isLessThanJava9;

/**
 * @author caomm
 * @Title ApiHelp 启动
 * @Description TODO
 * @Date 2022/8/27 17:19
 */
public class Run {
    static final String RUN_JAR_NAME ="run.jar";
    static String EnvironmentSources="";

    public static void main(String[] args) throws Exception {
        checkEnvironment();
        run();
    }

    /**
     * @author caomm
     * @Date 2022/8/27 17:19
     *  <pre>
     *      1、检查和获取jdk环境
     *      2、检查tools.jar 主要后续程序执行（vm相关操作需要 VirtualMachine ）中，需要tools.jar的支持，而tools.jar不位于jre，不会被系统类加载器加载
     *      3、通过拓展类加载器扫描范围，启动后置加载tools.jar，防止后续 VirtualMachine操作失败
     *      4、开启新的进程进行Xbootclasspath拓展，并启动view，这也就是run.jar存在的目的
     *  </pre>
     * */
    private static void run() throws Exception {
        try {

            // JDK环境检查，主要检查tools.jar
            String javaHome = ProcessUtls.findJavaHome();

            // 找到jdk目录
            File javaPath = ProcessUtls.findJava(javaHome);
            if (javaPath == null) {
                throw new IllegalArgumentException("未找到jdk环境 java home: " + javaHome);
            }

            // 获取tools.jar 路径
            File toolsJar = ProcessUtls.findToolsJar(javaHome);

            if (isLessThanJava9()) {
                if (toolsJar == null || !toolsJar.exists()) {
                    throw new IllegalArgumentException("JDK环境中未发现tools.jar包 java home: " + javaHome);
                }
            }

            if (isLessThanJava9()) {
                if (toolsJar == null || !toolsJar.exists()) {
                    throw new IllegalArgumentException("JDK环境中未发现tools.jar包 java home: " + javaHome);
                }
            }

            List<String> command = new ArrayList<String>();
            command.add(javaPath.getAbsolutePath());
            // -Xbootclasspath/a: 后缀在核心class搜索路径后面 - 表示扩展bootstrap类加载器加载内容
            if (toolsJar != null && toolsJar.exists()) {
                command.add("-Xbootclasspath/a:" + toolsJar.getAbsolutePath());
            }
            command.add("-jar");
            command.add(EnvironmentSources+"view.jar");

            /**
             *  开启一个进程，启动 view.jar
             * */
            ProcessBuilder pb = new ProcessBuilder(command);
            pb.start();
        }catch (Exception e){
            throw new Exception("启动失败！请检查JDK环境配置！");
        }

    }

    /**
     *  view.jar 视图启动包检查
     * */
    private static void checkEnvironment() throws Exception {
        EnvironmentSources = java.net.URLDecoder.decode(new Run().getClass().getProtectionDomain().getCodeSource().getLocation().getFile(), "UTF-8");
        if (EnvironmentSources != null && !EnvironmentSources.isEmpty()) {
            EnvironmentSources =  EnvironmentSources.replace(RUN_JAR_NAME,"");
            EnvironmentSources = EnvironmentSources.substring(1,EnvironmentSources.length());
        }

        File targetFile = new File(EnvironmentSources + "view.jar");
        if (!targetFile.exists()) {
            throw new Exception("目录下，未发现视图包！");
            // 后期可以通过公网下载....
        }





    }




}
