ApiHelp 视图实现
当前功能视图：1、代码自动生成器。2、性能评估

性能评估：核心代码位于event\monitor目录下。入口方法MonitorEvent
主要功能为：1、开启监控
            -- 环境jar包检查
            -- VirtualMachine.attach 将cmm-monitor.jar加入到目标虚拟机中。注意：因为VirtualMachine位于tools.jar中，不在jre下，所以
               使用该tools.jar方法时，需要拓展jdk类加载器的扫描路径， Xbootclasspath。对该程序进行启动，才能正常使用该功能。所以这就是run.jar存在的目的。
          2、日志解析
             --主要对log文件进行解析，实现可视化展示。 



    