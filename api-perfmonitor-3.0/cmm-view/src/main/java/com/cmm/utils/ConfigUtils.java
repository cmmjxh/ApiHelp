package com.cmm.utils;


import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * TODO 配置相关工具类
 *
 * @version 1.0
 * @author: Survivor
 * @createTime: 2022/6/29$ 16:37$
 */
public class ConfigUtils {
    /**
     * 读取配置文件
     * @return
     */
    public static Properties readConfigFile(String cfgFile) {
        try {
            InputStream in = ConfigUtils.class.getClassLoader().getResourceAsStream(cfgFile);
            Properties prop = new Properties();
            prop.load(new InputStreamReader(in,"UTF-8"));
            return prop;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getConfig(String key) {
        Properties properties = readConfigFile("globaldefault.properties");
        return (String) properties.get(key);
    }
    public static String getConfigMy(String key) {
        Properties properties = readConfigFile("my.properties");
        return (String) properties.get(key);
    }
}