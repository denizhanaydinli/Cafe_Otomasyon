package org.example.Main.util;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class TextFieldKeyListener implements KeyListener {
    public TextFieldKeyListener() {
    }

    public void keyTyped(KeyEvent e) {
        if (!Character.isDigit(e.getKeyChar()) && e.getKeyChar() != ',') {
            e.consume();
        }

    }

    public void keyPressed(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
    }
}
