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
import java.util.LinkedList;
import java.util.List;

public class SqlScriptParser {
// see: http://www.docjar.org/html/api/com/ibatis/common/jdbc/ScriptRunner.java.html
  
  private static final char DEFAULT_SEPARATOR = ';';

  private char separator = DEFAULT_SEPARATOR;
  private List<SqlStatement> statements = new ArrayList<>();

  public void clear() {
    statements.clear();
  }

  public void parse(List<String> lines) {
    clear();

    StringBuilder sql = new StringBuilder();
    int startRow = 0;
    int startCol = 0;
    boolean inBlockComment = false;
    
    for (int row = 0; row < lines.size(); row++) {
        if (sql.length() > 0) {
          sql.append('\n');
        }
        
        String line = lines.get(row);
        String trimmedLine = line.trim();
        // soft processing
        if (trimmedLine.isEmpty() || trimmedLine.startsWith("--") || (inBlockComment && (!trimmedLine.contains("*/")))) {
          sql.append(line);
        } else { // hard processing
          boolean inLineComment = false;
          boolean inString = false;
          char[] lineChars = line.toCharArray();
          for (int col = 0; col < lineChars.length; col++) {
            char currChar = lineChars[col];
            char prevChar = (col > 0) ? lineChars[col - 1] : 0;
            //char nextChar = (col < lineChars.length - 1) ? lineChars[col + 1] : 0;
              
            sql.append(currChar);
            
            if (currChar == '\'') {
              if (!inBlockComment && !inLineComment) {
                inString = !inString;
              }
            } else if (currChar == '*' && prevChar == '/') { // check for comment begin '/*'
              if (!inString && !inLineComment) {
                inBlockComment = true;
              }
            } else if (currChar == '/' && prevChar == '*') { // check for comment end '*/'
              if (!inString && !inLineComment) {
                inBlockComment = false;
              }
            } else if (currChar == '-' && prevChar == '-') { // check for line comment '--'
              if (!inString && !inBlockComment && !inLineComment) {
                inLineComment = true;
              }
            } else if (currChar == separator) { // check for sql separator
              if (!inString && !inBlockComment && !inLineComment) {
                SqlStatement st = new SqlStatement(sql.toString(), startRow, startCol, row, col);
                statements.add(st);
                
                sql.setLength(0);
                if (col < lineChars.length - 1) {
                  startRow = row;
                  startCol = col + 1;
                } else {
                  startRow = row + 1;
                  startCol = 0;
                }
              }
            }
          }
        }
    }    
    
    if (!lines.isEmpty() && !sql.toString().trim().isEmpty()) {
      int row = lines.size() - 1;
      int col = lines.get(lines.size() - 1).length() - 1;
      if (col < 0) {
        col = 0;
      }
      SqlStatement st = new SqlStatement(sql.toString(), startRow, startCol, row, col);
      statements.add(st);
    }

    System.out.println("SqlScriptParser.parse (lines): TODO - real impementation needed");
  }

  public void parse(String text) {
    clear();
    
    if (text == null || text.isEmpty()) {
      return;
    }
    
    List<String> lines = new LinkedList<>();
    
    String[] rawLines = text.split("\n");
    for (String line : rawLines) {
      lines.add(line.replace("\r", ""));
    }
    
    parse(lines);
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
