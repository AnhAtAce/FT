/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import javax.swing.table.DefaultTableModel;

/**
 *
 * @author AN2
 */
public class MyTable extends DefaultTableModel {

    public MyTable(Object[][] dataRows, Object[] header) {
        super(dataRows, header);
    }

    public MyTable() {
        super();
    }
    
    @Override
    public boolean isCellEditable(int row, int col) {
        return col != 1 || col != 2;
    }
    
@Override
    public Class<?> getColumnClass(int columnIndex) {
      Class clazz = String.class;
      switch (columnIndex) {
        case 0:
          clazz = String.class;
          break;
        case 2:
          clazz = Boolean.class;
          break;
      }
      return clazz;
    }
}
