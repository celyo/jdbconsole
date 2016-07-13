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
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.Window;

public class UIBuilder {

  private static final int CONN_WIDTH = 25;
  private static final int TOOLBAR_HEIGHT = 4;
  // private static final int SQL_HEIGHT = 10;

  private Panel connectionsPanel;
  private Panel toolbarPanel;
  private Panel sqlPanel;
  private Panel resultPanel;
  private BasicWindow mainWindow;

  private void buildConnectionsPanel(TerminalSize size) {
    connectionsPanel = new Panel();
    connectionsPanel.setPreferredSize(size);

  }

  private void buildToolbarPanel(TerminalSize size) {
    toolbarPanel = new Panel();
    toolbarPanel.setPreferredSize(size);
  }

  private void buildSQLPanel(TerminalSize size) {
    sqlPanel = new Panel();
    sqlPanel.setPreferredSize(size);
  }

  private void buildResultPanel(TerminalSize size) {
    resultPanel = new Panel();
    resultPanel.setPreferredSize(size);
  }

  private void buildMainWindow(TerminalSize screenSize) {
    mainWindow = new BasicWindow();

    Panel mainPanel = new Panel();
    mainPanel.setLayoutManager(new BorderLayout());

    // build toolbar
    Panel topPanel = new Panel();
    topPanel.setPreferredSize(screenSize.withRows(TOOLBAR_HEIGHT));
    topPanel.setLayoutManager(new BorderLayout());
    topPanel.addComponent(toolbarPanel.withBorder(Borders.singleLine("Toolbar")),
        BorderLayout.Location.CENTER);
    topPanel.addComponent(connectionsPanel.withBorder(Borders.singleLine("Connections")),
        BorderLayout.Location.RIGHT);
    mainPanel.addComponent(topPanel, BorderLayout.Location.TOP);

    // build central area
    Panel centralPanel = new Panel();
    centralPanel.setPreferredSize(screenSize.withRelativeRows(-TOOLBAR_HEIGHT));
    centralPanel.setLayoutManager(new BorderLayout());
    centralPanel.addComponent(sqlPanel.withBorder(Borders.singleLine("SQL")),
        BorderLayout.Location.TOP);
    centralPanel.addComponent(resultPanel.withBorder(Borders.singleLine("Result")),
        BorderLayout.Location.CENTER);
    mainPanel.addComponent(centralPanel, BorderLayout.Location.CENTER);

    mainWindow.setComponent(mainPanel);

    // Tip: Sets the window to be full screen.
    mainWindow.setHints(Arrays.asList(Window.Hint.FULL_SCREEN));
  }

  public void build(TerminalSize screenSize) {
    buildToolbarPanel(new TerminalSize(screenSize.getColumns() - CONN_WIDTH, TOOLBAR_HEIGHT));
    buildConnectionsPanel(new TerminalSize(CONN_WIDTH, TOOLBAR_HEIGHT));
    buildSQLPanel(
        new TerminalSize(screenSize.getColumns(), (screenSize.getRows() - TOOLBAR_HEIGHT) / 2));
    buildResultPanel(
        new TerminalSize(screenSize.getColumns(), (screenSize.getRows() - TOOLBAR_HEIGHT) / 2));
    buildMainWindow(screenSize);
  }

  public Panel getConnectionsPanel() {
    return connectionsPanel;
  }

  public Panel getToolbarPanel() {
    return toolbarPanel;
  }

  public Panel getSqlPanel() {
    return sqlPanel;
  }

  public Panel getResultPanel() {
    return resultPanel;
  }

  public BasicWindow getMainWindow() {
    return mainWindow;
  }

}
