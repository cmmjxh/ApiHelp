package com.cmm.view;

import com.cmm.perfmonitor.TraceTreeLogParse;
import com.cmm.perfmonitor.trace.vo.LogVo;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.util.ArrayList;
import java.util.List;

/**
 * @author caomm
 * @Title TODO
 * @Description TODO
 * @Date 2022/8/22 17:41
 */
public class test {
    public static void main(String[] args) throws Exception {
        List<LogVo> logTreeList = TraceTreeLogParse.getLogTreeList();
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("链路追踪");
        createTree(root, logTreeList);

        final JTree tree = new JTree(root);
        JFrame f = new JFrame("JTreeDemo");
        f.add(tree);
        f.setSize(300, 300);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    static void createTree(DefaultMutableTreeNode root, List<LogVo> list) {
        if (list==null || list.size() <= 0) {
            return;
        }
        for (int i = 0; i < list.size(); i++) {
            String v = list.get(i).getTime()+" "+list.get(i).getSpace()+"|-->"+list.get(i).getMethod()+"  耗时："+list.get(i).getUserTime()+" "+ list.get(i).getContent();
            DefaultMutableTreeNode nowNode = new DefaultMutableTreeNode(v);
            root.add(nowNode);
            List<LogVo> childList = new ArrayList<>();
            try {
                childList = list.get(i).getChildList();
            } catch (Exception e) {}
            createTree(nowNode, childList);
        }
    }
}
