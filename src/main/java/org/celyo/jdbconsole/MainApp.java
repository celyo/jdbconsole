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


package org.celyo.jdbconsole;

import org.celyo.jdbconsole.ui.UIBuilder;
import java.io.IOException;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.DefaultWindowManager;
import com.googlecode.lanterna.gui2.EmptySpace;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;


public class MainApp {
  private static Terminal terminal = null;
  private static Screen screen = null;
  private static MultiWindowTextGUI gui = null;
  private static UIBuilder builder = new UIBuilder();

  public static void main(String[] args) throws IOException {
    init();

    screen.startScreen();

    builder.build(screen.getTerminalSize());

    // Create gui and start gui
    gui.addWindowAndWait(builder.getMainWindow());
  }

  private static void init() throws IOException {
    AppConfig.load();
    AppConfig.print();
    AppConfig.save();
    terminal = new DefaultTerminalFactory().createTerminal();
    screen = new TerminalScreen(terminal);
    gui = new MultiWindowTextGUI(screen, new DefaultWindowManager(),
        new EmptySpace(TextColor.ANSI.BLUE));
  }

}