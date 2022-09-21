package com.cmm.view.event.monitor;

import com.cmm.perfmonitor.PrefLogParse;
import com.cmm.perfmonitor.TraceTreeLogParse;
import com.cmm.view.menu.style.MyTableModel;
import com.cmm.perfmonitor.pref.vo.ThreadVo;
import com.cmm.perfmonitor.run.MonitorBoot;
import com.cmm.perfmonitor.trace.vo.LogVo;
import com.cmm.utils.TimeUtils;
import com.cmm.view.utils.JTreeUtils;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author caomm
 * @Title TODO
 * @Description 性能监控功能相关事件
 * @Date 2022/8/25 11:09
 */
public class MonitorEvent {
    /**
     * 开启监控
     */
    public static void runEven(JButton runBut, JTextArea consoleField, JTextField tracePathField, JTextField ignoreFileField) {
        runBut.addActionListener(new ActionListener() {
            /**
             * 开启监控
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // 环境准备
                    MonitorBoot.checkEnvironment();

                    // 获取java vm列表
                    Map javaVmMap = MonitorBoot.getJavaVmList(consoleField);
                    if (javaVmMap != null) {
                        consoleField.setForeground(Color.white);
                        consoleField.setText(TimeUtils.newTimeSuccess() + "正在对当前应用开启监控：" + javaVmMap.get("name") + "\n");
                    }

                    if (!String.valueOf(javaVmMap.get("vmPid")).isEmpty() && !"null".equals(String.valueOf(javaVmMap.get("vmPid")))) {
                        // 开启监控
                        MonitorBoot.run(String.valueOf(javaVmMap.get("vmPid")), tracePathField.getText(), ignoreFileField.getText());
                        consoleField.append(TimeUtils.newTimeSuccess() + "监控程序已启动！");
                    }

                } catch (Exception ee) {
                    consoleField.setForeground(Color.red);
                    consoleField.setText(TimeUtils.newTimeError() + ee.getMessage());
                }
            }
        });
    }

    /**
     * 监控日志分析
     */
    public static void logParseEven(JButton logParseBut, JPanel straceTreeJpanl, JTextArea consoleField, JTextField level, JLabel useTime, JLabel cpuField, JLabel blockCountField, JLabel blockTimeField, JPanel jcJpanel) {
        logParseBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<LogVo> logTreeList = null;
                try {
                    // 链路分析
                    // 设置父节点
                    DefaultMutableTreeNode root = new DefaultMutableTreeNode("run");
                    // 获取最新链路日志
                    logTreeList = TraceTreeLogParse.getLogTreeList();
                    // 构建tree
                    JTreeUtils.createTree(root, logTreeList);
                    JTree jTree = new JTree(root);
                    // 展开视图
                    JTreeUtils.expandAll(jTree, new TreePath(root), true, JTreeUtils.getSpace(level.getText()));

                    // 更新tree view
                    straceTreeJpanl.removeAll();
                    straceTreeJpanl.add(jTree);
                    straceTreeJpanl.updateUI();

                    // 性能分析
                    parsePerf(logTreeList.get(0).getUserTime(), useTime, cpuField, blockCountField, blockTimeField, jcJpanel);

                    consoleField.setForeground(Color.white);
                    consoleField.setText(TimeUtils.newTimeSuccess() + "链路log解析成功，结果数据已生成！\n"+TimeUtils.newTimeSuccess() + "性能log解析成功，结果数据已生成！");
                } catch (Exception ex) {
                    consoleField.setForeground(Color.red);
                    consoleField.setText(TimeUtils.newTimeError() + ex.getMessage());
                    // 更新tree view
                    straceTreeJpanl.removeAll();
                    straceTreeJpanl.updateUI();
                }
            }

            /**
             *  性能分析 数据视图
             * */
            private void parsePerf(String useTime, JLabel useTimeView, JLabel cpuField, JLabel blockCountField, JLabel blockTimeField, JPanel gcJpanel) throws Exception {
                // 执行时间
                useTimeView.setText(useTime + " ms");
                // 日志解析
                ThreadVo threadVo = PrefLogParse.parse();
                if (threadVo != null) {
                    String threadCpuUse = "0.00%";
                    try {
                        // cpu
                        Double threadCpuUseDouble = Double.parseDouble(threadVo.getThreadCpuUse()) * 100;
                        threadCpuUse = String.format("%.1f", threadCpuUseDouble) + "%";
                        cpuField.setText(threadCpuUse);

                        // 阻塞
                        blockCountField.setText(threadVo.getThreadBlockCount() + " 次");
                        blockTimeField.setText(threadVo.getThreadBlockTime() + " ms");

                        // gc log日志列表
                        Object[] title={"S0C","S1C","S0U","S1U","EC","EU","OC","OU","MC","MU","CCSC","CCSU","YGC","YGCT","FGC","FGCT","GCT"};
                        CopyOnWriteArrayList<List<String>> gcInfo = threadVo.getGcInfo();
                        if (gcInfo.size() > 0) {
                            // 数据解析获取
                            Object[][] gcLogs = getGCLog(gcInfo);

                            MyTableModel myTableModel  = new MyTableModel(gcLogs, title);
                            JTable gcTable = new JTable(myTableModel);
//                            gcTable.setEnabled(false); // 禁用选中、编辑效果
                            gcTable.setRowSelectionAllowed(false);
                            gcTable.setColumnSelectionAllowed(true);
                            gcTable.setForeground(Color.white);
                            gcTable.setBackground(Color.black);

                            gcJpanel.removeAll();
                            gcJpanel.add(gcTable);
                            gcJpanel.updateUI();
                        }
                    } catch (Exception e) {
                    }
                }
            }

            /**
             *  获取gc日志table数据
             * */
            private Object[][] getGCLog(CopyOnWriteArrayList<List<String>> gcInfo) {
                Object[][] array = new Object[3][17];
                // 直接加Jtable 到JPanel 需要自己加头，不会自动显示
                Object[] title={"S0C","S1C","S0U","S1U","EC","EU","OC","OU","MC","MU","CCSC","CCSU","YGC","YGCT","FGC","FGCT","GCT"};
                for (int t = 0; t < 17; t++) {
                    array[0][t] = title[t];
                }
                for (int i = 0; i < gcInfo.size(); i++) {
                     List<String> gclog = gcInfo.get(i);
                    for (int j = 0; j < gclog.size(); j++) {
                        array[i+1][j] = gclog.get(j);
                    }
                }
                return array;
            }
        });
    }
}
