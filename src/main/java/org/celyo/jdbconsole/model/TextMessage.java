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

import java.util.Objects;

public class TextMessage {
  private final MessageType type;
  private final String text;

  public TextMessage(MessageType type, String text) {
    this.type = type;
    this.text = text;
  }

  public MessageType getType() {
    return type;
  }

  public String getText() {
    return text;
  }

  @Override
  public int hashCode() {
    int hash = 5;
    hash = 67 * hash + Objects.hashCode(this.type);
    hash = 67 * hash + Objects.hashCode(this.text);
    return hash;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final TextMessage other = (TextMessage) obj;
    if (!Objects.equals(this.text, other.text)) {
      return false;
    }
    if (this.type != other.type) {
      return false;
    }
    return true;
  }
  
  
}
