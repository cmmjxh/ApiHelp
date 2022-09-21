package com.cmm.view.utils;

import com.cmm.perfmonitor.trace.vo.LogVo;
import com.cmm.utils.LogOutQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * @author caomm
 * @Title 工具类
 * @Description TODO
 * @Date 2022/8/25 00:54
 */
public class JTreeUtils {
    private static final Logger logger = LoggerFactory.getLogger(JTreeUtils.class);

    /**
     * 层级转换
     * 0 : 全部
     */
    public static String getSpace(String levelStr) {
        int level = 2;
        try {
            level = Integer.parseInt(levelStr);
        }catch (Exception e){}
        StringBuilder stringBuilder = new StringBuilder();
        if (level <= 0) {
            return "all";
        }
        for (int i = 0; i < level; i++) {
            stringBuilder.append("\t");
        }
        return stringBuilder.toString();
    }

    /**
     * 递归构建树
     */
    public static void expandAll(JTree tree, TreePath parent, boolean expand , String space) {
        // Traverse children
        TreeNode node = (TreeNode) parent.getLastPathComponent();
        if (node.getChildCount() >= 0) {
            for (Enumeration e = node.children(); e.hasMoreElements(); ) {
                TreeNode n = (TreeNode) e.nextElement();
                if(!"all".equals(space)){
                    if (n.toString().contains(space)) {
                        break;
                    }
                }
                TreePath path = parent.pathByAddingChild(n);
                expandAll(tree, path, expand ,space);
            }
        }

        // Expansion or collapse must be done bottom-up
        if (expand) {
            tree.expandPath(parent);
        } else {
            tree.collapsePath(parent);
        }
    }

    /**
     * 构建树结构Jtree
     */
    public static void createTree(DefaultMutableTreeNode root, List<LogVo> list) {
        try {
            if (list == null || list.size() <= 0) {
                return;
            }
            for (int i = 0; i < list.size(); i++) {
                // 节点显示内容
                String v = list.get(i).getTime() + " " + list.get(i).getSpace() + "|-->" + list.get(i).getMethod() + "    耗时：" + list.get(i).getUserTime() +" ms"+ "             " + list.get(i).getContent();
                DefaultMutableTreeNode nowNode = new DefaultMutableTreeNode(v);
                root.add(nowNode);
                List<LogVo> childList = new ArrayList<>();
                try {
                    childList = list.get(i).getChildList();
                } catch (Exception e) {

                }
                createTree(nowNode, childList);
            }
        } catch (Exception e) {
            LogOutQueue.logResetError("链路log解析错误，检查API是否正常执行结束，API异常会导致监控失效，请修复异常后，重新启动应用程序 和 监控程序！");
            logger.error("链路log解析错误，检查API是否正常执行结束，API异常会导致监控失效，请修复异常后，重新启动应用程序 和 监控程序！");
        }
    }

    /**
     *  耗时占比计算
     * */
    public static String timePercentage(String useTime, String parentTime){
        if("null".equals(useTime) || useTime.isEmpty() || "null".equals(parentTime) || parentTime.isEmpty()){
             return "0.0%";
        }
        Double percentage = 0.0;
        try {
            percentage = Double.valueOf((Double.valueOf(useTime)/Double.valueOf(parentTime))*100);
        }catch (Exception e){}
        return String.format("%.1f",percentage)+"%";
    }
}
