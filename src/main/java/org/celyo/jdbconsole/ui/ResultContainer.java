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
import com.googlecode.lanterna.gui2.Component;
import com.googlecode.lanterna.gui2.Panel;

public class ResultContainer implements ResultView {
  
  private Panel root;

  @Override
  public void init(TerminalSize size) {
    root = new Panel();
    root.setPreferredSize(size);
  }

  @Override
  public void uninit() {
    root = null;
  }

  @Override
  public Component asComponent() {
    return root;
  }

}
