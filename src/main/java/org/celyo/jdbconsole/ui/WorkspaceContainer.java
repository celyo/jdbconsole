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

import java.util.Arrays;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.BorderLayout;
import com.googlecode.lanterna.gui2.Borders;
import com.googlecode.lanterna.gui2.Component;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.Window;

public class WorkspaceContainer implements WorkspaceView {

  private static final int CONN_WIDTH = 25;
  private static final int TOOLBAR_HEIGHT = 4;
  // private static final int SQL_HEIGHT = 10;

  private ToolbarView toolbarView = new ToolbarContainer();
  private ConnectionsView connectionsView = new ConnectionsContainer();
  private SqlView sqlView = new SqlContainer();
  private ResultView resultView = new ResultContainer();
  
  private BasicWindow mainWindow;

  private void initMainWindow(TerminalSize screenSize) {
    mainWindow = new BasicWindow();

    Panel mainPanel = new Panel();
    mainPanel.setLayoutManager(new BorderLayout());

    // build toolbar
    Panel topPanel = new Panel();
    topPanel.setPreferredSize(screenSize.withRows(TOOLBAR_HEIGHT));
    topPanel.setLayoutManager(new BorderLayout());
    topPanel.addComponent(toolbarView.asComponent().withBorder(Borders.singleLine("Toolbar")),
        BorderLayout.Location.CENTER);
    topPanel.addComponent(connectionsView.asComponent().withBorder(Borders.singleLine("Connections")),
        BorderLayout.Location.RIGHT);
    mainPanel.addComponent(topPanel, BorderLayout.Location.TOP);

    // build central area
    Panel centralPanel = new Panel();
    centralPanel.setPreferredSize(screenSize.withRelativeRows(-TOOLBAR_HEIGHT));
    centralPanel.setLayoutManager(new BorderLayout());
    centralPanel.addComponent(sqlView.asComponent().withBorder(Borders.singleLine("SQL")),
        BorderLayout.Location.TOP);
    centralPanel.addComponent(resultView.asComponent().withBorder(Borders.singleLine("Result")),
        BorderLayout.Location.CENTER);
    mainPanel.addComponent(centralPanel, BorderLayout.Location.CENTER);

    mainWindow.setComponent(mainPanel);

    // Tip: Sets the window to be full screen.
    mainWindow.setHints(Arrays.asList(Window.Hint.FULL_SCREEN));
  }

  @Override
  public void init(TerminalSize size) {
    toolbarView.init(new TerminalSize(size.getColumns() - CONN_WIDTH, TOOLBAR_HEIGHT));
    connectionsView.init(new TerminalSize(CONN_WIDTH, TOOLBAR_HEIGHT));
    sqlView.init(new TerminalSize(size.getColumns(), (size.getRows() - TOOLBAR_HEIGHT) / 2));
    resultView.init(new TerminalSize(size.getColumns(), (size.getRows() - TOOLBAR_HEIGHT) / 2));
    
    initMainWindow(size);
  }

  @Override
  public void uninit() {
    toolbarView.uninit();
    connectionsView.uninit();
    sqlView.uninit();
    resultView.uninit();
    
    mainWindow = null;
  }

  @Override
  public Component asComponent() {
    return mainWindow.getComponent();
  }

  @Override
  public BasicWindow asWindow() {
    return mainWindow;
  }

  @Override
  public ToolbarView getToolbarView() {
    return toolbarView;
  }

  @Override
  public ConnectionsView getConnectionsView() {
    return connectionsView;
  }

  @Override
  public SqlView getSqlView() {
    return sqlView;
  }

  @Override
  public ResultView getResultView() {
    return resultView;
  }
  
  

}
