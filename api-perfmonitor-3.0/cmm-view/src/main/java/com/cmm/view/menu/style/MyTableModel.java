package com.cmm.view.menu.style;

import javax.swing.table.DefaultTableModel;
import java.util.HashSet;

/**
 * @author caomm
 * @Title 自定义TableModel 用于设定table 不可编辑
 * @Description TODO
 * @Date 2022/9/7 09:19
 */
public class MyTableModel extends DefaultTableModel {

    public MyTableModel(Object[][] data, Object[] columnNames){
         super(data,columnNames);
    }
    /**
     *  重写isCellEditable
     * */
    @Override
    public boolean isCellEditable(int row, int column) {
        //返回true表示能编辑，false表示不能编辑
        return false;
    }
}
