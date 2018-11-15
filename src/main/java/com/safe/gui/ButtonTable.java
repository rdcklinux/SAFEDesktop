/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.safe.gui;

import java.awt.Component;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author familia
 */
class ButtonTable extends JButton implements  TableCellRenderer
{

  //CONSTRUCTOR
  public ButtonTable(String label) {
    //SET BUTTON PROPERTIES
    setOpaque(true);
    setText(label);
  }
  
  @Override
  public Component getTableCellRendererComponent(JTable table, Object obj,
      boolean selected, boolean focused, int row, int col) {

    //SET PASSED OBJECT AS BUTTON TEXT
    //setText((obj==null) ? "":obj.toString());

    return this;
  }
}
