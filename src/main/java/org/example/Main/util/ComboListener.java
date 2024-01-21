package org.example.Main.util;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Vector;
import org.apache.commons.lang3.text.WordUtils;

public class ComboListener extends KeyAdapter {
    JComboBox cbListener;
    Vector vector;

    public ComboListener(JComboBox cbListenerParam, Vector vectorParam) {
        this.cbListener = cbListenerParam;
        this.vector = vectorParam;
    }

    public void keyReleased(KeyEvent key) {
        String text = ((JTextField)key.getSource()).getText();
        this.cbListener.setModel(new DefaultComboBoxModel(this.getFilteredList(text)));
        this.cbListener.setSelectedIndex(-1);
        ((JTextField)this.cbListener.getEditor().getEditorComponent()).setText(text);
        this.cbListener.showPopup();
    }

    public Vector getFilteredList(String text) {
        Vector v = new Vector();

        for(int a = 0; a < this.vector.size(); ++a) {
            if (this.vector.get(a).toString().startsWith(text)) {
                v.add(this.vector.get(a).toString());
            } else if (this.vector.get(a).toString().startsWith(text.toLowerCase())) {
                v.add(this.vector.get(a).toString());
            } else if (this.vector.get(a).toString().startsWith(text.toUpperCase())) {
                v.add(this.vector.get(a).toString());
            } else if (this.vector.get(a).toString().startsWith(WordUtils.capitalizeFully(text))) {
                v.add(this.vector.get(a).toString());
            } else if (this.vector.get(a).toString().startsWith(WordUtils.uncapitalize(text))) {
                v.add(this.vector.get(a).toString());
            }
        }

        return v;
    }
}
