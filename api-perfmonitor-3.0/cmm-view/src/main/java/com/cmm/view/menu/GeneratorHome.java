package com.cmm.view.menu;

import com.cmm.utils.LogOutQueue;
import com.cmm.view.event.HomeMenuEvent;
import com.cmm.view.setConfig;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

/**
 * @author caomm
 * @Title TODO
 * @Description TODO
 * @Date 2022/7/18 12:12
 */
public class GeneratorHome extends JFrame{
    private JPanel rootJpanel;
    private JTextField textFieldApiName;
    private JTextField textFieldInterfactPath;
    private JTextField textFieldProductName;
    private JTextField textFieldInterfactName;
    private JTextField textFieldAuthor;
    private JTextField textFieldImplPath;
    private JTextField textFieldControllerName;
    private JPanel configText;
    private JPanel configSelect;
    private JTextArea jTextArea;
    private JCheckBox isParamGenerator;
    private JCheckBox isResPonEntity;
    private JCheckBox isFileGenerator;
    private JCheckBox isCodeGenerator;
    private JCheckBox isMapper;
    private JButton executeBut;
    private JLabel labelApiName;
    private JPanel otherJpanel;
    private JLabel ps;
    private JLabel labelInterfactPath;
    private JLabel labelImplPath;
    private JLabel labelImplProductName;
    private JLabel labelImplInterfactName;
    private JLabel labelImplControllerName;
    private JLabel labelImplAuthor;
    private JPanel title0;
    private JLabel title00;
    private JButton clearButton;
    private JLabel title003;
    private JPanel menuJpanel;

    public GeneratorHome(Frame frame) {
        initBasicStyle();

        executeBut.addActionListener(new ActionListener() {
            /**
             * 启动
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                // 获取解析配置数据
                try {
                    int code = JOptionPane.showConfirmDialog(frame, "注意：请勿重复生成，防止生成重复API相关代码！", "生成提示", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
                    if (code == JOptionPane.YES_OPTION) {
                        setConfig.setConfigAndExe(textFieldApiName.getText(), textFieldInterfactPath.getText(), textFieldImplPath.getText(), textFieldProductName.getText(), textFieldAuthor.getText(), textFieldInterfactName.getText(), textFieldControllerName.getText(), isFileGenerator, isCodeGenerator, isResPonEntity, isParamGenerator, isMapper);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {
                    // 处理控制台打印
                    jTextArea.setFont(new Font(null, 0, 16));
                    LinkedList<String> messageList = LogOutQueue.get();
                    for(String message : messageList){
                        if(message.contains("错误")){
                            jTextArea.setForeground(Color.red);
                        }else{
                            jTextArea.setForeground(Color.white);
                        }
                        jTextArea.append(message+"\n");
                    }
                }
            }
        });

        /**
         *  清空打印台clear
         * */
        clearButton.addActionListener(new ActionListener() {
            /**
             * clear
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                jTextArea.setText("*打印台*");
            }
        });

    }

    public static void init(JFrame frame) {
        frame.setIconImage(new ImageIcon(GeneratorHome.class.getClassLoader().getResource("logo.png")).getImage());
        frame.setContentPane(new GeneratorHome(frame).rootJpanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(1366,768);
        frame.setMinimumSize(new Dimension(1024,768));
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
        homeMenuEvent.MenuEvent(frame,jMenuItem0,jMenuItem1);

            // 设置窗口出现位置
        frame.setLocationRelativeTo(frame);
    }

    private void initBasicStyle() {
        // 按钮样式
        executeBut.setContentAreaFilled(false);
        executeBut.setFocusPainted(false);

        clearButton.setContentAreaFilled(false);
        clearButton.setFocusPainted(false);
    }
}
