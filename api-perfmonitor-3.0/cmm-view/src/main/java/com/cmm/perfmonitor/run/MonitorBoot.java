package com.cmm.perfmonitor.run;

import com.cmm.utils.AppDataDoUtils;
import com.cmm.utils.ConfigUtils;
import com.cmm.utils.FileUtils;
import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;

import javax.swing.*;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author caomm
 * @Title ApiHelp 监控启动入口
 * @Description TODO
 * @Date 2022/8/25 10:43
 */
public class MonitorBoot {
    static String ProjectVM_KEY = ConfigUtils.getConfigMy("ProjectVM_KEY");
    static String StacePath = ConfigUtils.getConfigMy("StacePath");
    static String IgnoreFile = ConfigUtils.getConfigMy("IgnoreFile");

    public static void run(String vmPid , String tracePathField , String ignoreFileField) throws Exception {
        VirtualMachine virtualMachine = null;
        try {
            if(tracePathField == null || "".equals(tracePathField) || "null".equals(tracePathField)){
                tracePathField = FileUtils.getLocalPath();
            }
            if(ignoreFileField == null || "".equals(ignoreFileField) || "null".equals(ignoreFileField)){
                ignoreFileField = FileUtils.getLocalPath();
            }
            virtualMachine = VirtualMachine.attach(vmPid);
            //1.并在当前虚拟机上加载目标jar包和输入参数，同时进行加载jar中的类，类加载器为AppClassLoader
            virtualMachine.loadAgent(FileUtils.getLocalPath() + "../lib" + File.separator + "cmm-monitor.jar", tracePathField + ";" + ignoreFileField);
            //2.卸载虚拟机
            virtualMachine.detach();
        } catch (Exception e) {
            throw new Exception("开启监控失败，请检查配置信息正确后重试！");
        } finally {
            if (virtualMachine != null) {
                virtualMachine.detach();
            }
        }
    }

    /**
     * 获取虚拟机vm列表
     */
    public static Map getJavaVmList(JTextArea consoleField) throws Exception {
        Map map = new HashMap();
        StringBuilder vmStr = new StringBuilder("");
        //1.获取系统中所有虚拟机，类似jps命令；
        List<VirtualMachineDescriptor> list = VirtualMachine.list();
        if (list.size() > 0) {
            // 1、过滤；2、根据pid 和 name 做分组处理
            list = AppDataDoUtils.getGroupData(list.stream().filter(cmm -> cmm.displayName().contains(ProjectVM_KEY)).collect(Collectors.toList()));
            if (list.size() <= 0) {
                throw new Exception("[101] 非发现可监控的应用vm进程！");
            }
            for (VirtualMachineDescriptor vm : list) {
                vmStr.append("PID：" + vm.id() + " <=> NAME：" + vm.displayName() + "\n");
                map.put("vmPid", vm.id());
                map.put("name", "PID：" + vm.id() + " <=> NAME：" + vm.displayName() + "\n");
            }
            if (list.size() > 1) {
                throw new Exception("发现多个可监控vm进程，请关闭其他应用:\n" + vmStr);
            }
        } else {
            throw new Exception("[100] 非发现可监控的应用vm进程！");
        }
        return map;
    }

    /**
     * 环境准备
     */
    public static boolean checkEnvironment() throws Exception {
        File targetFile0 = new File(FileUtils.getLocalPath() + "../lib" + File.separator + "cmm-monitor.jar");
        File targetFile1 = new File(FileUtils.getLocalPath() + "../lib" + File.separator + "cmm-enhance.jar");

        // 核心jar检查
        if (!targetFile0.exists() || !targetFile1.exists()) {
            throw new Exception(FileUtils.getLocalPath() + "目录下，未发现核心支持包！");
            // 后期可以通过公网下载....
        }
        return true;
    }
}
