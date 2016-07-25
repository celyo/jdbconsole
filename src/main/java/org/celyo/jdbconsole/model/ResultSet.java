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
package org.celyo.jdbconsole.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ResultSet {

  private final List<String> columns = new ArrayList<>();
  private final List<Map<String, Object>> rows = new ArrayList<>();

  public List<String> getColumns() {
    return columns;
  }

  public List<Map<String, Object>> getRows() {
    return rows;
  }

  public void clear() {
    rows.clear();
    columns.clear();
  }
}
