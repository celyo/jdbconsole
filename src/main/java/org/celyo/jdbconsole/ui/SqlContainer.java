package org.celyo.jdbconsole.ui;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.BorderLayout;
import com.googlecode.lanterna.gui2.Component;
import com.googlecode.lanterna.gui2.Panel;

public class SqlContainer implements SqlView {
  
  
  
  private Panel root;
  private ScriptBox sqlBox;

  @Override
  public void init(TerminalSize size) {
    root = new Panel();
    root.setPreferredSize(size);
    root.setLayoutManager(new BorderLayout());
    sqlBox = new ScriptBox(size);
    root.addComponent(sqlBox, BorderLayout.Location.CENTER);
  }

  @Override
  public void uninit() {
    sqlBox = null;
    root = null;
  }

  @Override
  public Component asComponent() {
    return root;
  }

}
