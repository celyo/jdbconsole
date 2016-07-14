package org.celyo.jdbconsole.ui;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.Component;
import com.googlecode.lanterna.gui2.Panel;
import java.util.List;
import org.celyo.jdbconsole.model.ConnectionInfo;

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

  @Override
  public void setConncections(List<ConnectionInfo> conns) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public List<ConnectionInfo> getConncections() {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public ConnectionInfo getCurrentConnection() {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public void setCurrentConnection(ConnectionInfo conn) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public void setConnectionChangeListener(ConnectionChangeListener listener) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  
}
