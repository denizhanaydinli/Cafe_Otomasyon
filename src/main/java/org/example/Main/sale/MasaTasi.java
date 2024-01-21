package org.example.Main.sale;

import org.example.Main.database.dbServlet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;

public class MasaTasi {
    JFrame mp;
    int digit = 0;
    dbServlet db;
    JButton button;
    JPanel buttonPanel;
    ActionListener numberListener = new numberListener();
    int i = 0;

    public MasaTasi() {
    }

    public int Tasi() throws Exception {
        this.db = new dbServlet();
        int masa_sayisi = this.db.MasaDurumu().size();
        this.mp = new JFrame("MASA TAŞI");
        this.mp.setBounds(300, 100, 800, 400);
        this.mp.setDefaultCloseOperation(2);
        this.buttonPanel = new JPanel();
        this.buttonPanel.setLayout(new GridLayout(4, 4, 10, 10));
        this.masa();
        Object[] fields = new Object[]{this.buttonPanel};
        int a = JOptionPane.showConfirmDialog((Component)null, fields, "TAŞINACAK MASAYI SEÇİN ", -1);
        return a == 0 ? this.digit : 0;
    }

    public void masa() throws Exception {
        for(Iterator i$ = this.db.MasaDurumu().iterator(); i$.hasNext(); ++this.i) {
            SatisBean list = (SatisBean)i$.next();
            this.button = new JButton(String.valueOf(this.i + 1));
            this.button.setBackground(Color.WHITE);
            if (list.getStatus() == 0) {
                this.button.setEnabled(false);
                this.button.setBackground(Color.RED);
            }

            this.button.addActionListener(this.numberListener);
            this.buttonPanel.add(this.button);
        }

    }

    public static void main(String[] args) throws Exception {
        MasaTasi mt = new MasaTasi();
        mt.Tasi();
    }

    class numberListener implements ActionListener {
        numberListener() {
        }

        public void actionPerformed(ActionEvent event) {
            MasaTasi.this.digit = Integer.parseInt(event.getActionCommand());
            JButton masa = (JButton)event.getSource();
            masa.setBackground(Color.GREEN);
        }
    }
}
