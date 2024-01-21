package org.example.Main.sale;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

public class SatisAdet {
    String digit = null;
    JTextField jt;

    public SatisAdet() {
    }

    public String Adet() {
        int rakam = 10;
        JPanel textPanel = new JPanel();
        this.jt = new JTextField(20);
        Font newTextFieldFont = new Font(this.jt.getFont().getName(), 1, this.jt.getFont().getSize());
        this.jt.setFont(newTextFieldFont);
        this.jt.setForeground(Color.red);
        textPanel.add(this.jt);
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 4, 10, 10));
        ActionListener numberListener = new NumberListener();

        for(int i = 0; i < rakam; ++i) {
            JButton button = new JButton(String.valueOf((i + 1) % 10));
            button.addActionListener(numberListener);
            buttonPanel.add(button);
        }

        JButton virgul = new JButton(",");
        buttonPanel.add(virgul);
        ActionListener virgulListener = new virgulListener();
        virgul.addActionListener(virgulListener);
        JButton c = new JButton("C");
        buttonPanel.add(c);
        ActionListener cListener = new cListener();
        c.addActionListener(cListener);
        Object[] fields = new Object[]{textPanel, buttonPanel};
        int a = JOptionPane.showConfirmDialog((Component)null, fields, "Tutar Gir", 2);
        if (a == 0 && !this.jt.getText().equals("")) {
            this.digit = this.jt.getText().toString();
        }

        return this.digit;
    }

    public static void main(String[] args) {
        SatisAdet sa = new SatisAdet();
        sa.Adet();
    }

    class cListener implements ActionListener {
        cListener() {
        }

        public void actionPerformed(ActionEvent event) {
            SatisAdet.this.jt.setText((String)null);
        }
    }

    class virgulListener implements ActionListener {
        virgulListener() {
        }

        public void actionPerformed(ActionEvent event) {
            SatisAdet.this.jt.setText(SatisAdet.this.jt.getText() + ",");
        }
    }

    class NumberListener implements ActionListener {
        NumberListener() {
        }

        public void actionPerformed(ActionEvent event) {
            String digit = event.getActionCommand();
            SatisAdet.this.jt.setText(SatisAdet.this.jt.getText() + digit);
        }
    }
}
