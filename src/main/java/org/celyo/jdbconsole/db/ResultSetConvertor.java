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

import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import org.celyo.jdbconsole.model.ResultSet;
import org.celyo.jdbconsole.util.MessageKey;
import org.celyo.jdbconsole.util.Messages;

public class ResultSetConvertor {
  
  public ResultSet convert(java.sql.ResultSet rs) {
    ResultSet result = new ResultSet();
    try {
      ResultSetMetaData rsm = rs.getMetaData();
      // retrieve columns
      for (int i = 0; i < rsm.getColumnCount(); i++) {
        result.getColumns().add(rsm.getColumnLabel(i));
      }
      
      rs.first();
      while(rs.next()) {
        Map<String, Object> row = new HashMap<>();
        for (int i = 0; i < rsm.getColumnCount(); i++) {
          row.put(rsm.getColumnLabel(i), rs.getObject(i));
        }
        result.getRows().add(row);
      }
    } catch (SQLException e) {
      throw new RuntimeException(Messages.getString(MessageKey.EXCEPTION_CONVERT_CANNOT_CONVERT_RESULTSET), e);
    }
    
    return result;
  }
  
}
