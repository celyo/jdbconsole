package org.celyo.jdbconsole.ui;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.TextBox;
import com.googlecode.lanterna.input.KeyStroke;

public class ScriptBox extends TextBox {
  private static final Character SPACE =  new Character(' ');
  public static interface Listener {
    
  } 
  
  public ScriptBox() {
    super("", Style.MULTI_LINE);
  }

  public ScriptBox(TerminalSize preferredSize) {
    super(preferredSize, Style.MULTI_LINE);
  }

  @Override
  public synchronized Result handleKeyStroke(KeyStroke keyStroke) {
    if (!isReadOnly()) {
      switch (keyStroke.getKeyType()) {
        case Enter:
          // Ctrl + Enter is pressed
          if (keyStroke.isCtrlDown() && !keyStroke.isAltDown() && !keyStroke.isShiftDown()) {
            //todo add onExecuteStatement event
            System.out.println("onExecuteStatement event");
            return Result.HANDLED;
          }
          break;
        case F5:
          //todo add onExecuteScript event
          System.out.println("onExecuteScript event");
          return Result.HANDLED;
          //break;
        case Character:
          System.out.println("'" + keyStroke.getCharacter() + "' : " + (int)keyStroke.getCharacter().charValue());
          // Shift + SPACE is pressed
          if (keyStroke.getCharacter().equals(SPACE) && !keyStroke.isCtrlDown() && !keyStroke.isAltDown() && keyStroke.isShiftDown()) {
            //todo add onCodeComplete event
            System.out.println("onCodeComplete event");
            return Result.HANDLED;
          }
          break;
        default:
          break;
      }
    }

    return super.handleKeyStroke(keyStroke);
  }


}
