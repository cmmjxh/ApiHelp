package com.cmm.view.menu;

import com.cmm.utils.LogOutQueue;
import com.cmm.view.event.HomeMenuEvent;
import com.cmm.view.event.monitor.MonitorEvent;
import com.cmm.view.menu.style.RoundedBorder;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.ButtonUI;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * @author caomm
 * @Title TODO
 * @Description TODO
 * @Date 2022/8/24 21:04
 */
public class PerfMonitorHome {
    private JPanel root01;
    private JPanel configJpane;
    private JPanel root02;
    private JPanel perfParseMesJpane;
    private JTextField tracePathField;
    private JLabel tracePath;
    private JTextField ignoreFileField;
    private JLabel ignoreFile;
    private JButton logParseBut;
    private JButton runBut;
    private JLabel cpuKey;
    private JLabel ps;
    private JTextArea consoleField;
    private JLabel cpuField;
    private JLabel blockCount;
    private JLabel blockCountField;
    private JLabel blockTime;
    private JLabel blockTimeField;
    private JLabel threadKey;
    private JLabel timeKey;
    private JPanel errorJpanel;
    private JScrollPane consoleJSsroll;
    private JTextField level;
    private JLabel useTime;
    private JPanel gcJpanel;
    private JScrollPane jstree;
    private JPanel straceTreeJpanl;
    private JButton utton;

    public PerfMonitorHome() throws IOException {
        // 初始化组件样式
        initBasicStyle();

        // 页面事件
        MonitorEvent.runEven(this.runBut, this.consoleField , this.tracePathField , this.ignoreFileField);
        MonitorEvent.logParseEven(logParseBut, straceTreeJpanl, consoleField , level , useTime ,cpuField ,blockCountField , blockTimeField ,gcJpanel);
    }

    private void initBasicStyle() {
        // 按钮样式
        runBut.setContentAreaFilled(false);
        runBut.setFocusPainted(false);

        logParseBut.setContentAreaFilled(false);
        logParseBut.setFocusPainted(false);

        utton.setContentAreaFilled(false);
        utton.setFocusPainted(false);

        // 设置tree滚动速度
        jstree.getVerticalScrollBar().setUnitIncrement(50);
    }

    public static void init(JFrame frame) throws IOException {
        frame.setIconImage(new ImageIcon(GeneratorHome.class.getClassLoader().getResource("logo.png")).getImage());
        frame.setContentPane(new PerfMonitorHome().root01);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(1366, 768);
        frame.setMinimumSize(new Dimension(1024, 768));
        // 设置菜单栏样式
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("选项");
        JMenu editMenu = new JMenu("退出");
        fileMenu.setFont(new Font("宋体", Font.PLAIN, 18));
        editMenu.setFont(new Font("宋体", Font.PLAIN, 18));

        JMenuItem jMenuItem0 = new JMenuItem("API辅助工具");
        jMenuItem0.setFont(new Font("宋体", Font.PLAIN, 16));

        JMenuItem jMenuItem1 = new JMenuItem("性能分析评估");
        jMenuItem1.setFont(new Font("宋体", Font.PLAIN, 16));

        jMenuItem0.setBackground(Color.white);
        jMenuItem1.setBackground(Color.white);

        fileMenu.add(jMenuItem0);
        fileMenu.add(jMenuItem1);

        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        frame.setJMenuBar(menuBar);

        // 菜单选择事件
        HomeMenuEvent homeMenuEvent = new HomeMenuEvent();
        homeMenuEvent.MenuEvent(frame, jMenuItem0, jMenuItem1);

        // 设置窗口出现位置
        frame.setLocationRelativeTo(frame);
    }


}
