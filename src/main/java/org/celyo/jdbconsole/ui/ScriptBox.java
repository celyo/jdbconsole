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
import com.googlecode.lanterna.gui2.TextBox;
import com.googlecode.lanterna.input.KeyStroke;
import java.util.ArrayList;
import java.util.List;

public class ScriptBox extends TextBox {

  private static final Character SPACE = ' ';

  public static interface ExecuteStatementListener {

    void onExecuteStatement(List<String> txtLines, int row, int column);
  }

  public static interface ExecuteScriptListener {

    void onExecuteScript(List<String> txtLines);
  }

  public static interface CodeCompleteListener {

    void onCodeComplete(List<String> txtLines, int row, int column);
  }

  private ExecuteStatementListener executeStatementListener;
  private ExecuteScriptListener executeScriptListener;
  private CodeCompleteListener codeCompleteListener;

  public ScriptBox() {
    super("", Style.MULTI_LINE);
  }

  public ScriptBox(TerminalSize preferredSize) {
    super(preferredSize, Style.MULTI_LINE);
  }

  public void setExecuteStatementListener(ExecuteStatementListener executeStatementListener) {
    this.executeStatementListener = executeStatementListener;
  }

  public void setExecuteScriptListener(ExecuteScriptListener executeScriptListener) {
    this.executeScriptListener = executeScriptListener;
  }

  public void setCodeCompleteListener(CodeCompleteListener codeCompleteListener) {
    this.codeCompleteListener = codeCompleteListener;
  }

  public List<String> getLines() {
    List<String> result = new ArrayList<>();
    for (int i = 0; i < getLineCount(); i++) {
      result.add(getLine(i));
    }

    return result;
  }

  @Override
  public synchronized Result handleKeyStroke(KeyStroke keyStroke) {
    if (!isReadOnly()) {
      switch (keyStroke.getKeyType()) {
        case Enter:
          // Ctrl + Enter is pressed
          if (keyStroke.isCtrlDown() && !keyStroke.isAltDown() && !keyStroke.isShiftDown()) {
            if (executeStatementListener != null) {
              executeStatementListener.onExecuteStatement(getLines(), getCaretPosition().getRow(), getCaretPosition().getColumn());
            }
            return Result.HANDLED;
          }
          break;
        case F5:
          if (executeScriptListener != null) {
            executeScriptListener.onExecuteScript(getLines());
          }
          return Result.HANDLED;
        //break;
        case Character:
          // Ctrl + SPACE is pressed
          if (keyStroke.getCharacter().equals(SPACE) && keyStroke.isCtrlDown() && !keyStroke.isAltDown() && !keyStroke.isShiftDown()) {
            if (codeCompleteListener != null) {
              codeCompleteListener.onCodeComplete(getLines(), getCaretPosition().getRow(), getCaretPosition().getColumn());
            }
            return Result.HANDLED;
          }
          break;
        default:
          break;
      }
    }

    return super.handleKeyStroke(keyStroke);
  }

}
