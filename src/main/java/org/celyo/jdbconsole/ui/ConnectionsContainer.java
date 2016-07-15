package org.celyo.jdbconsole.ui;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.BorderLayout;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.ComboBox;
import com.googlecode.lanterna.gui2.Component;
import com.googlecode.lanterna.gui2.Panel;

import java.util.ArrayList;
import java.util.List;
import org.celyo.jdbconsole.model.ConnectionInfo;

public class ConnectionsContainer implements ConnectionsView {
  private static final int BTN_WIDTH = 3;
  
  private Panel root;
  private ComboBox<ConnectionInfo> connectionsCombo;
  private Button editConnectionsButton;
  private ConnectionChangeListener connectionChangeListener;

  @Override
  public void init(TerminalSize size) {
    root = new Panel();
    root.setPreferredSize(size);
    root.setLayoutManager(new BorderLayout());
    connectionsCombo = new ComboBox<>();
    connectionsCombo.setReadOnly(true);
    connectionsCombo.setPreferredSize(size.withRelativeColumns(-BTN_WIDTH));
    root.addComponent(connectionsCombo, BorderLayout.Location.CENTER);
    editConnectionsButton = new Button("E", new Runnable() {
      @Override
      public void run() {
        editConnectionsHandler();
      }
    });
    editConnectionsButton.setEnabled(false);
    editConnectionsButton.setPreferredSize(size.withColumns(BTN_WIDTH));
    root.addComponent(editConnectionsButton, BorderLayout.Location.RIGHT);
    
    connectionsCombo.addListener(new ComboBox.Listener() {

      @Override
      public void onSelectionChanged(int selectedIndex, int previousSelection) {
        if (connectionChangeListener != null) {
          ConnectionInfo conn = null;
          if (selectedIndex >= 0) {
            conn = connectionsCombo.getItem(selectedIndex);
          }
          
          connectionChangeListener.onConnectionChange(conn);
        }
      }
    });
  }

  @Override
  public void uninit() {
    editConnectionsButton = null;
    connectionsCombo = null;
    root = null;
  }

  @Override
  public Component asComponent() {
    return root;
  }

  private int indexOfConnection(ConnectionInfo conn) {
    if (conn != null && connectionsCombo.getItemCount() > 0) {
      for (int i = 0; i < connectionsCombo.getItemCount(); i++) {
        if (conn.equals(connectionsCombo.getItem(i))) {
          return i;
        }
      }
    }
    
    return -1;
  }
  
  @Override
  public void setConncections(List<ConnectionInfo> conns) {
    connectionsCombo.clearItems();
    for (ConnectionInfo conn : conns) {
      connectionsCombo.addItem(conn);
    }
  }

  @Override
  public List<ConnectionInfo> getConncections() {
    List<ConnectionInfo> result = new ArrayList<>();
    for (int i = 0; i < connectionsCombo.getItemCount(); i++) {
      result.add(connectionsCombo.getItem(i));
    }

    return result;
  }

  @Override
  public ConnectionInfo getCurrentConnection() {
    if (connectionsCombo.getSelectedIndex() >= 0) {
      return connectionsCombo.getSelectedItem();
    } else {
      return null;
    }
  }

  @Override
  public void setCurrentConnection(ConnectionInfo conn) {
    connectionsCombo.setSelectedIndex(indexOfConnection(conn));
  }

  @Override
  public void setConnectionChangeListener(ConnectionChangeListener listener) {
    connectionChangeListener = listener;
  }

  private void editConnectionsHandler() {
    //TODO Implement real editing
  }
}
