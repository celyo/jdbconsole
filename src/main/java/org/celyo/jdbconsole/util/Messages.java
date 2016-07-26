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

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Messages {

  private static final String BUNDLE_NAME = "messages"; //$NON-NLS-1$
  private static final ResourceBundle BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

  private static String getString(String key) {
    try {
      return BUNDLE.getString(key);
    } catch (MissingResourceException e) {
      return key;
    }
  }

  public static String getString(MessageKey key, Object... params) {
    String message = getString(key.getKey());
    
    if (params != null && params.length > 0) {
      return MessageFormat.format(message, params);
    } else {
      return message;
    }
  }
}
