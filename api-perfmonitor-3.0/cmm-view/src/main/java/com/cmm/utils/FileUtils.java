package com.cmm.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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
    static final String RUN_JAR_NAME ="view.jar";
    private final static Log log = LogFactory.getLog(FileUtils.class);

    /**
     * FileChannel
     * 创建文件 并写入文件内容
     */
    public static void createFileAndData(File fileIs, String interfaceData) throws IOException {
        // 获取输出流
        FileOutputStream out = new FileOutputStream(fileIs);
        // 获取通道
        FileChannel fileChannel = out.getChannel();
        try {
            // 设置缓冲ByteBuffer
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            byteBuffer.put(interfaceData.getBytes());
            byteBuffer.flip();
            // 写入(初始文件内容量少，一次性接入即可)
            fileChannel.write(byteBuffer);
        } catch (Exception e) {
            log.info(e);
            LogOutQueue.add(TimeUtils.newTime()+" 错误: "+e.toString());
        } finally {
            fileChannel.close();
        }

    }

    /**
     * FileChannel
     * 追加写入文件
     * 方案：由于并非追加到文件末尾，则采取优先获取文件数据放入缓冲区，缓冲区完成数据处理后进行替换源文件内容
     */
    public static void write(File fileIs, String interfaceData, String fileTemp) throws IOException {
        // 获取输入流、输出流
        FileInputStream input = new FileInputStream(fileIs);
        // 获取通道
        FileChannel inChannel = input.getChannel();
        try {
            // 设置读取缓冲区
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            // 作为缓冲区，接受原文件内容，并处理后续新增内容
            StringBuilder stringBuilder = new StringBuilder();
            String newFileInfo = "";
            while (inChannel.read(byteBuffer) != -1) {
                byteBuffer.flip();
                stringBuilder.append(new String(byteBuffer.array()));
                byteBuffer.clear();
            }
            // 追加新API接口信息
            if (stringBuilder.length() > 0) {
                int lastEnd = stringBuilder.lastIndexOf("}");
                newFileInfo = stringBuilder.substring(0,lastEnd)+"\n";
                newFileInfo = new StringBuilder(newFileInfo).append(interfaceData).append("\n}").toString();
            }
            // 文件内容写入文件
            FileOutputStream out = new FileOutputStream(fileIs);
            FileChannel outChannel = out.getChannel();
            try {
                ByteBuffer wbyteBuffer = ByteBuffer.allocate(newFileInfo.getBytes().length);
                wbyteBuffer.put(newFileInfo.getBytes());
                wbyteBuffer.flip();
                outChannel.write(wbyteBuffer);
                wbyteBuffer.clear();
            }catch (Exception E){
                log.info(E);
                LogOutQueue.add(E.toString());
            }finally {
                outChannel.close();
                out.close();
            }
        } catch (Exception e) {
            log.info("代码生成失败"+e);
            LogOutQueue.add(TimeUtils.newTime()+" 错误: "+"代码生成失败"+e);
        } finally {
            inChannel.close();
            input.close();
        }
    }

    /**
     *  API名称转类名
     * */
    public static String doServerName(String serverName) {
        String result = "";
        String[] arr = serverName.split("\\.");
        for (int i = 0; i < arr.length; i++) {
            String data = arr[i];
            if (i != 0) {
                data = data.substring(0, 1).toUpperCase() + data.substring(1);
            }
            result += data;
        }
        return result;
    }

    /**
     * 首字母小写
     * @param str
     * @return
     */
    public static String lowerFirstCase(String str) {
        return str.substring(0,1).toLowerCase()+str.substring(1,str.length());
    }

    /**
     * 首字母大写
     * @param str
     * @return
     * */
    public static String UpperFrist(String str) {
        return str.substring(0,1).toUpperCase()+str.substring(1,str.length());
    }

    /**
     * 文件拷贝
     * */
    public static void copyFileByChannel(File source, File dest) throws
            IOException {
        try (FileChannel sourceChannel = new FileInputStream(source)
                .getChannel();
             FileChannel targetChannel = new FileOutputStream(dest).getChannel
                     ();){
            for (long count = sourceChannel.size() ;count>0 ;) {
                long transferred = sourceChannel.transferTo(
                        sourceChannel.position(), count, targetChannel);
                sourceChannel.position(sourceChannel.position() + transferred);
                count -= transferred;
            }
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