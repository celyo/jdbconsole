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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import org.celyo.jdbconsole.AppConfig;
import org.celyo.jdbconsole.db.DriverLoader;
import org.celyo.jdbconsole.db.SqlStatement;
import org.celyo.jdbconsole.model.ConnectionInfo;
import org.celyo.jdbconsole.util.MessageKey;
import org.celyo.jdbconsole.util.Messages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WorkspaceControler {
  private static final Logger LOG = LoggerFactory.getLogger(WorkspaceControler.class);

  private final WorkspaceView view;
  private final DriverLoader drvLoader = new DriverLoader(AppConfig.getDriverDirName());
  private Connection connection;

  private ConnectionChangeListener connectionChangeListener = new ConnectionChangeListener() {
    @Override
    public void onConnectionChange(ConnectionInfo conn) {
      closeConnection();
      openConnection(conn);
    }
  };

  private ExecuteStatementListener executeStatementListener = new ExecuteStatementListener() {
    @Override
    public void onExecuteStatement(SqlStatement statement) {
      System.out.println("WorkspaceControler.ExecuteStatementListener.onExecuteStatement: TODO - real impementation needed");
    }
  };

  private ExecuteStatementsListener executeStatementsListener = new ExecuteStatementsListener() {
    @Override
    public void onExecuteStatements(List<SqlStatement> statements) {
      System.out.println("WorkspaceControler.ExecuteStatementsListener.onExecuteStatements: TODO - real impementation needed");
    }
  };

  public WorkspaceControler(WorkspaceView view) {
    this.view = view;
  }

  public WorkspaceView getView() {
    return view;
  }

  public void init() {
    view.getConnectionsView().setConnectionChangeListener(null);
    view.getConnectionsView().setConncections(AppConfig.getConnections());
    view.getConnectionsView().setConnectionChangeListener(connectionChangeListener);

    view.getSqlView().setExecuteStatementListener(executeStatementListener);
    view.getSqlView().setExecuteStatementsListener(executeStatementsListener);
  }

  public void uninit() {
    view.getConnectionsView().setConnectionChangeListener(null);
    view.getConnectionsView().setConnectionChangeListener(null);

    view.getSqlView().setExecuteStatementListener(null);
    view.getSqlView().setExecuteStatementsListener(null);
    
    closeConnection();
  }
  
  private void openConnection(ConnectionInfo conn) {
    if (conn.getDriver() == null || conn.getDriver().trim().isEmpty()) {
      throw new IllegalArgumentException(Messages.getString(MessageKey.EXCEPTION_CONNECTION_DRIVER_NOT_SET));
    }
    if (conn.getUrl() == null || conn.getUrl().trim().isEmpty()) {
      throw new IllegalArgumentException(Messages.getString(MessageKey.EXCEPTION_CONNECTION_URL_NOT_SET));
    }

    closeConnection();

    try {
      DriverManager.registerDriver(drvLoader.loadDriver(conn.getDriver()));
      connection = DriverManager.getConnection(conn.getUrl(), conn.getUser(), conn.getPassword());
      LOG.info("Connection enstablished successfully.");
    } catch (SQLException ex) {
      throw new RuntimeException(Messages.getString(MessageKey.EXCEPTION_CONNECTION_CANNOT_CONNECT), ex);
    }
  }

  private void closeConnection() {
    try {
      if (connection != null && !connection.isClosed()) {
        connection.close();
        connection = null;

        LOG.info("Connection closed successfully.");
      }
    } catch (SQLException ex) {
      throw new RuntimeException(Messages.getString(MessageKey.EXCEPTION_CONNECTION_CANNOT_CLOSE), ex);
    }
  }

}
