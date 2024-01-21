package org.example.Main.util;

import javax.swing.*;
import java.util.Vector;

public class AutoComboBox extends JComboBox<Object> {
    String[] keyWord;
    Vector myVector = new Vector();

    public AutoComboBox(String[] keyWord) {
        this.keyWord = keyWord;
        this.setModel(new DefaultComboBoxModel(this.myVector));
        this.setSelectedIndex(-1);
        this.setEditable(true);
        JTextField text = (JTextField)this.getEditor().getEditorComponent();
        text.setFocusable(true);
        text.setText("");
        text.addKeyListener(new ComboListener(this, this.myVector));
        this.setMyVector();
    }

    public void setKeyWord(String[] keyWord) {
        this.keyWord = keyWord;
        this.setMyVectorInitial();
    }

    private void setMyVector() {
        for(int a = 0; a < this.keyWord.length; ++a) {
            this.myVector.add(this.keyWord[a]);
        }

    }

    private void setMyVectorInitial() {
        this.myVector.clear();

        for(int a = 0; a < this.keyWord.length; ++a) {
            this.myVector.add(this.keyWord[a]);
        }

    }
}