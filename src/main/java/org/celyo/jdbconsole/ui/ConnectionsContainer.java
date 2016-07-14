package org.celyo.jdbconsole.ui;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.Component;
import com.googlecode.lanterna.gui2.Panel;

public class ConnectionsContainer implements ConnectionsView {
  
  private Panel root;

  @Override
  public void init(TerminalSize size) {
    root = new Panel();
    root.setPreferredSize(size);
  }

  @Override
  public void uninit() {
    root = null;
  }

  @Override
  public Component asComponent() {
    return root;
  }

}
