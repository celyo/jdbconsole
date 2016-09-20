/*
 * 
 * Copyright 2016 Celyo
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 * 
 */

package org.celyo.jdbconsole.ui;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.BorderLayout;
import com.googlecode.lanterna.gui2.Component;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.TextBox;
import com.googlecode.lanterna.gui2.table.Table;
import com.googlecode.lanterna.gui2.table.TableRenderer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.celyo.jdbconsole.model.ResultSet;
import org.celyo.jdbconsole.model.TextMessage;

public class ResultContainer implements ResultView {
  
  private final List<TextMessage> messages = new ArrayList<>();
  private final TableRenderer<String> renderer = new CustomTableRenderer<>();

  private Panel root;
  private TextBox msgBox;
  private Table<String> resultTable;
  
  private void setActiveComponent(Component comp) {
    root.removeAllComponents();
    root.addComponent(comp, BorderLayout.Location.CENTER);
  }

  @Override
  public void init(TerminalSize size) {
    root = new Panel();
    root.setPreferredSize(size);
    root.setLayoutManager(new BorderLayout());
    
    msgBox = new ScriptBox(size);
    msgBox.setReadOnly(true);
    resultTable = new Table("#");
    resultTable.setCellSelection(true);
    resultTable.setRenderer(renderer);
    
    setActiveComponent(resultTable);
  }

  @Override
  public void uninit() {
    msgBox = null;
    resultTable = null;
    root = null;
  }

  @Override
  public Component asComponent() {
    return root;
  }

  @Override
  public void clearMessages() {
    messages.clear();
    msgBox.setText("");
    //msgBox.invalidate();
  }

  @Override
  public void addMessage(TextMessage message) {
    msgBox.addLine(message.getType() + " : " + message.getText());
    setActiveComponent(msgBox);
  }

  @Override
  public void setMessage(TextMessage message) {
    clearMessages();
    addMessage(message);
  }

  @Override
  public void clearResult() {
    for (int i = resultTable.getTableModel().getRowCount() - 1; i >= 0; i--) {
      resultTable.getTableModel().removeRow(i);
    }

    for (int i = resultTable.getTableModel().getColumnCount()- 1; i >= 0; i--) {
      if (!"#".equals(resultTable.getTableModel().getColumnLabel(i))) {
        resultTable.getTableModel().removeColumn(i);
      }
    }
  }

  @Override
  public void setResult(ResultSet rs) {
    clearResult();
    for (String col : rs.getColumns()) {
      resultTable.getTableModel().addColumn(col, null);
    }
    
    int rowNum = 0;
    for (Map<String, Object> row : rs.getRows()) {
      rowNum++;
      List<String> values = new ArrayList<>();
      values.add(String.valueOf(rowNum));

      for (String col : rs.getColumns()) {
        values.add(formatValue(row.get(col)));
      }
      
      resultTable.getTableModel().addRow(values);
    }

    resultTable.invalidate();
    setActiveComponent(resultTable);
  }

  @Override
  public void clearAll() {
    clearMessages();
    clearResult();
    setActiveComponent(resultTable);
  }
  
  private String formatValue(Object src) {
    if (src != null) {
      return src.toString();
    } else {
      return "<null>";
    }
  }

}
