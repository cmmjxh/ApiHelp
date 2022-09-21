package Utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author caomm
 * @Title TODO
 * @Description TODO
 * @Date 2022/8/27 18:14
 */
public class ProcessUtls {
    private static final Logger logger = LoggerFactory.getLogger(ProcessUtls.class);
    private static final String VERSION_PROP_NAME = "java.specification.version";
    private static final String JAVA_VERSION_STR = System.getProperty(VERSION_PROP_NAME);
    private static final float JAVA_VERSION = Float.parseFloat(JAVA_VERSION_STR);
    private static String FOUND_JAVA_HOME = null;

    public static String findJavaHome() {
        if (FOUND_JAVA_HOME != null) {
            return FOUND_JAVA_HOME;
        }

        String javaHome = System.getProperty("java.home");

        if (isLessThanJava9()) {
            File toolsJar = new File(javaHome, "lib/tools.jar");
            if (!toolsJar.exists()) {
                toolsJar = new File(javaHome, "../lib/tools.jar");
            }
            if (!toolsJar.exists()) {
                // maybe jre
                toolsJar = new File(javaHome, "../../lib/tools.jar");
            }

            if (toolsJar.exists()) {
                FOUND_JAVA_HOME = javaHome;
                return FOUND_JAVA_HOME;
            }

            if (!toolsJar.exists()) {
                String javaHomeEnv = System.getenv("JAVA_HOME");
                if (javaHomeEnv != null && !javaHomeEnv.isEmpty()) {
                    // $JAVA_HOME/lib/tools.jar
                    toolsJar = new File(javaHomeEnv, "lib/tools.jar");
                    if (!toolsJar.exists()) {
                        // maybe jre
                        toolsJar = new File(javaHomeEnv, "../lib/tools.jar");
                    }
                }

                if (toolsJar.exists()) {
                    FOUND_JAVA_HOME = javaHomeEnv;
                    return FOUND_JAVA_HOME;
                }

                throw new IllegalArgumentException("未发现 tools.jar 包再jdk中: " + javaHome);
            }
        } else {
            FOUND_JAVA_HOME = javaHome;
        }
        return FOUND_JAVA_HOME;
    }

    public static File findJava(String javaHome) {
        String[] paths = { "bin/java", "bin/java.exe", "../bin/java", "../bin/java.exe" };

        List<File> javaList = new ArrayList<File>();
        for (String path : paths) {
            File javaFile = new File(javaHome, path);
            if (javaFile.exists()) {
                javaList.add(javaFile);
            }
        }

        if (javaList.isEmpty()) {
            logger.info("未找到jdk环境 java home: " + javaHome);
            return null;
        }

        // find the shortest path, jre path longer than jdk path
        if (javaList.size() > 1) {
            Collections.sort(javaList, new Comparator<File>() {
                @Override
                public int compare(File file1, File file2) {
                    try {
                        return file1.getCanonicalPath().length() - file2.getCanonicalPath().length();
                    } catch (IOException e) {
                        // ignore
                    }
                    return -1;
                }
            });
        }
        return javaList.get(0);
    }

    public static File findToolsJar(String javaHome) {
        if (isGreaterThanJava8()) {
            return null;
        }

        File toolsJar = new File(javaHome, "lib/tools.jar");
        if (!toolsJar.exists()) {
            toolsJar = new File(javaHome, "../lib/tools.jar");
        }
        if (!toolsJar.exists()) {
            // maybe jre
            toolsJar = new File(javaHome, "../../lib/tools.jar");
        }

        if (!toolsJar.exists()) {
            throw new IllegalArgumentException("Can not find tools.jar under java home: " + javaHome);
        }
        return toolsJar;
    }

    public static boolean isLessThanJava9() {
        return JAVA_VERSION < 9.0f;
    }

    public static boolean isGreaterThanJava8() {
        return JAVA_VERSION > 1.8f;
    }
}
