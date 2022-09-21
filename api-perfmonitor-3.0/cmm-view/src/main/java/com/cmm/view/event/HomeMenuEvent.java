package com.cmm.view.event;

import com.cmm.view.menu.GeneratorHome;
import com.cmm.view.menu.PerfMonitorHome;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


/**
 * @author caomm
 * @Title 首页菜单事件触发器
 * @Description TODO
 * @Date 2022/2/8 15:54
 */
public class HomeMenuEvent {
    public void MenuEvent(final JFrame frame, JMenuItem newMenuItem, JMenuItem cxMenuItem){
        // “API辅助工具" 点击事件
        newMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GeneratorHome.init(frame);
            }
        });

        // ”API性能评估分析“ 点击事件
        cxMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    PerfMonitorHome.init(frame);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
}
