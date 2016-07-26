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

import java.util.List;
import org.celyo.jdbconsole.AppConfig;
import org.celyo.jdbconsole.db.SqlStatement;
import org.celyo.jdbconsole.model.ConnectionInfo;

public class WorkspaceControler {

  private WorkspaceView view;

  private ConnectionChangeListener connectionChangeListener = new ConnectionChangeListener() {
    @Override
    public void onConnectionChange(ConnectionInfo conn) {
      System.out.println("WorkspaceControler.ConnectionChangeListener.onConnectionChange: TODO - real impementation needed");
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

}
