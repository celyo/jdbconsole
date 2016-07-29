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
package org.celyo.jdbconsole.util;

public enum MessageKey {

  WORKSPACE_TOOLBAR_TITLE("workspace.toolbar.title"),
  WORKSPACE_CONNECTIONS_TITLE("workspace.connections.title"),
  WORKSPACE_SQL_TITLE("workspace.sql.title"),
  WORKSPACE_RESULT_TITLE("workspace.result.title"),
  
  CONNECTIONS_BUTTON_EDIT_CONNECTIONS("connections.button.edit_connections"),

  MESSAGE_TYPE_INFO("message_type.info"),
  MESSAGE_TYPE_WARNING("message_type.warning"),
  MESSAGE_TYPE_ERROR("message_type.error"),
  
  EXCEPTION_SCRIPT_NO_CURRENT_STATEMENT("exception.script.no_current_statement"),
  EXCEPTION_SCRIPT_NO_STATEMENTS("exception.script.no_statements"),
  EXCEPTION_CONVERT_CANNOT_CONVERT_RESULTSET("exception.convert.cannot_convert_resultset"),
  ;

  private final String key;

  private MessageKey(String key) {
    this.key = key;
  }

  public String getKey() {
    return key;
  }

}
