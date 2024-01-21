package org.example.Main.sale;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class JButtonTableCellRenderer extends JButton implements TableCellRenderer {
    public JButtonTableCellRenderer() {
        this.setRolloverEnabled(true);
    }

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        String buttonText = (String)value;
        this.setText(buttonText);
        this.getModel().setRollover(hasFocus);
        return this;
    }

    public void invalidate() {
    }

    public void validate() {
    }

    public void revalidate() {
    }

    public void repaint(long tm, int x, int y, int width, int height) {
    }

    public void repaint(Rectangle r) {
    }

    public void repaint() {
    }

    protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
    }

    public void firePropertyChange(String propertyName, boolean oldValue, boolean newValue) {
    }
}
