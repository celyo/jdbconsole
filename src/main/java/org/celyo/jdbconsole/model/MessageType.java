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

import org.celyo.jdbconsole.util.MessageKey;
import org.celyo.jdbconsole.util.Messages;

public enum MessageType {
  INFO(Messages.getString(MessageKey.MESSAGE_TYPE_INFO)), 
  WARNING(Messages.getString(MessageKey.MESSAGE_TYPE_WARNING)), 
  ERROR(Messages.getString(MessageKey.MESSAGE_TYPE_ERROR));
  
  private final String caption;
  
  private MessageType(String caption) {
    this.caption = caption;
  }

  public String getCaption() {
    return caption;
  }

}
