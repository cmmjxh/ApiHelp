package cmm.log;

import cmm.util.FileUtils;

import java.io.*;

/**
 * @author caomm
 * @Title TODO
 * @Description TODO
 * @Date 2022/9/4 15:50
 */
public class LogUtils {
    /**
     *  日志写入
     * */
    public static synchronized void print(String log) throws IOException {
        String fileName = "run.log";
        File file = new File(FileUtils.getLocalPath().replace("lib","") +"run" + File.separator + fileName);
        File parent = file.getParentFile();

        if(!parent.exists()) {
            parent.mkdirs();
        }
        if(!file.exists()) {
            file.createNewFile();
        }
        try {
            FileOutputStream fos = new FileOutputStream(file,true);
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            BufferedWriter bw = new BufferedWriter(osw);
            bw.write(log);
            bw.newLine();
            bw.flush();
            bw.close();
            osw.close();
            fos.close();
        }catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }
}
