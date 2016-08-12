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

import java.util.List;
import org.celyo.jdbconsole.util.StrUtils;

public class SqlUtils {

  private SqlUtils() {
  }
  
  public static String stripComments(String sql) {
    StringBuilder result = new StringBuilder();

    List<String> lines = StrUtils.splitLines(sql);

    boolean inBlockComment = false;
    for (String line : lines) {
      String trimmedLine = line.trim();
      if (!trimmedLine.isEmpty() && !trimmedLine.startsWith("--") && (!inBlockComment || trimmedLine.contains("*/"))) {
        boolean inLineComment = false;
        boolean inString = false;
        char[] lineChars = line.toCharArray();
        for (int col = 0; col < lineChars.length; col++) {
          char currChar = lineChars[col];
          char prevChar = (col > 0) ? lineChars[col - 1] : 0;
          //char nextChar = (col < lineChars.length - 1) ? lineChars[col + 1] : 0;

          if (currChar == '*' && prevChar == '/') { // check for comment begin '/*'
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
          } else {
            if (!inBlockComment && !inLineComment) {
              if (currChar == '\'') {
                inString = !inString;
              }
              result.append(currChar);
            }
          }
        }
        
        result.append("\n");
      }
    }
    
    return result.toString().trim();
  }
  
  
  public static boolean isSelectStatement(String sql) {
    String strippedSql = stripComments(sql);
    
    return strippedSql.toLowerCase().startsWith("select");
  }
}
