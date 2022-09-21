import com.sun.tools.attach.*;

import java.io.IOException;
import java.util.List;

/**
 * @author caomm
 * @Title TODO
 * @Description TODO
 * @Date 2022/8/17 17:04
 */
public class Run {
    public static void main(String[] args) throws IOException, AttachNotSupportedException, AgentLoadException, AgentInitializationException {
        //1.获取系统中所有虚拟机，类似jps命令；
        List<VirtualMachineDescriptor> list = VirtualMachine.list();
        for (VirtualMachineDescriptor vmd : list)
        {
            //2.遍历获取PrintParamTargetTest虚拟机；
            if (vmd.displayName().contains("com.digiwin.gateway")) {
                //3.将当前虚拟机连接到目标虚拟机上
                // 注意：VirtualMachine是tool.jar的功能非jre工具中jar，但jdk启动时不会被加载，所以外部启动该程序，需要 java -Xbootclasspath/a:lib\tools.jar -jar ****.jar 来拓展系统加载器加载
                VirtualMachine virtualMachine = VirtualMachine.attach(vmd.id());
                //4.并在当前虚拟机上加载目标jar包和输入参数，同时进行加载jar中的类，类加载器为AppClassLoader
                virtualMachine.loadAgent("E:\\个人\\cmmspace\\api-perfmonitor\\dw-monitor\\target\\dw-monitor.jar","com.digiwin.pcc.pcc.service.impl;utils/,entity/");
                System.out.println("ok");
                //5.卸载虚拟机
                virtualMachine.detach();
            }
        }
    }
}
