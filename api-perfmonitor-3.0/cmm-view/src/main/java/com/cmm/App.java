package com.cmm;


import com.cmm.view.menu.GeneratorHome;

import javax.swing.*;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        JFrame frame = new JFrame("鼎捷-应用APIHelp1.0(测试版)");
        GeneratorHome.init(frame);
        frame.setVisible(true);
    }
}