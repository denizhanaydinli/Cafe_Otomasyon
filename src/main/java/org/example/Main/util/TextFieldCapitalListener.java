package org.example.Main.util;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class TextFieldCapitalListener implements KeyListener {
    public TextFieldCapitalListener() {
    }

    public void keyTyped(KeyEvent e) {
        char keyChar = e.getKeyChar();
        if (Character.isLowerCase(keyChar)) {
            e.setKeyChar(Character.toUpperCase(keyChar));
        }

    }

    public void keyPressed(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
    }
}
