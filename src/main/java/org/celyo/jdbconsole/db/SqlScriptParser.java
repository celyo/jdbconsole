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
package org.celyo.jdbconsole.db;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SqlScriptParser {
// see: http://www.docjar.org/html/api/com/ibatis/common/jdbc/ScriptRunner.java.html

  private List<SqlStatement> statements = new ArrayList<>();

  public void clear() {
    statements.clear();
  }

  public void parse(List<String> lines) {
    System.out.println("SqlScriptParser.parse (lines): TODO - real impementation needed");
  }

  public void parse(String text) {
    System.out.println("SqlScriptParser.parse (text): TODO - real impementation needed");
  }

  public List<SqlStatement> getStatements() {
    return Collections.unmodifiableList(statements);
  }

  public SqlStatement getSattement(int row, int column) {
    for (SqlStatement st : statements) {
      if (row >= st.getStartRow() && row <= st.getEndRow()
              && column >= st.getStartColumn() && column <= st.getEndColumn()) {
        return st;
      }
    }

    return null;
  }
}
