package org.example.Main.sale;

import javax.swing.*;
import javax.swing.event.CellEditorListener;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JButtonTableCellEditor extends AbstractCellEditor implements TableCellEditor {
    private JButton button = new InternalButton();

    public JButtonTableCellEditor() {
    }

    public void addActionListener(ActionListener l) {
        this.button.addActionListener(l);
    }

    public void removeActionListener(ActionListener l) {
        this.button.removeActionListener(l);
    }

    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        String buttonText = (String)value;
        this.button.setText(buttonText);
        return this.button;
    }

    public Object getCellEditorValue() {
        return this.button.getText();
    }

    private class InternalButton extends JButton {
        private InternalButton() {
        }

        protected void fireActionPerformed(ActionEvent event) {
            super.fireActionPerformed(event);
            JButtonTableCellEditor.this.addCellEditorListener((CellEditorListener)null);
            JButtonTableCellEditor.this.fireEditingStopped();
        }
    }
}
