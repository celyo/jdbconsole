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
import java.util.List;
import org.celyo.jdbconsole.db.SqlScriptParser;

public class SqlContainer implements SqlView {

  private Panel root;
  private ScriptBox sqlBox;
  
  private final SqlScriptParser parser = new SqlScriptParser();

  private SqlView.ExecuteStatementListener executeStatementListener;

  private final ScriptBox.ExecuteStatementListener sqlBoxExecuteStatementListener = new ScriptBox.ExecuteStatementListener() {
    @Override
    public void onExecuteStatement(List<String> txtLines, int row, int column) {
      //TODO
      System.out.println(".onExecuteStatement()");
    }
  };

  private final ScriptBox.ExecuteScriptListener sqlBoxExecuteScriptListener = new ScriptBox.ExecuteScriptListener() {
    @Override
    public void onExecuteScript(List<String> txtLines) {
      //TODO
      System.out.println(".onExecuteScript()");
    }
  };

  private final ScriptBox.CodeCompleteListener sqlBoxCodeCompleteListener = new ScriptBox.CodeCompleteListener() {
    @Override
    public void onCodeComplete(List<String> txtLines, int row, int column) {
      //TODO
      System.out.println(".onCodeComplete()");
    }
  };

  @Override
  public void init(TerminalSize size) {
    root = new Panel();
    root.setPreferredSize(size);
    root.setLayoutManager(new BorderLayout());
    sqlBox = new ScriptBox(size);
    root.addComponent(sqlBox, BorderLayout.Location.CENTER);

    sqlBox.setExecuteStatementListener(sqlBoxExecuteStatementListener);
    sqlBox.setExecuteScriptListener(sqlBoxExecuteScriptListener);
    sqlBox.setCodeCompleteListener(sqlBoxCodeCompleteListener);
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

  @Override
  public void setExecuteStatementListener(ExecuteStatementListener listener) {
    executeStatementListener = listener;
  }

}
