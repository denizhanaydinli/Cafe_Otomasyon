package org.example.Main.settings;

import org.example.Main.Main.Main;
import org.example.Main.database.dbServlet;
import org.example.Main.sale.JButtonTableCellEditor;
import org.example.Main.sale.JButtonTableCellRenderer;
import org.example.Main.sale.Satis;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UrunAktiflestir extends JInternalFrame {
    private JButtonTableCellRenderer buttonRenderer;
    private JButtonTableCellEditor buttonEditor;
    dbServlet db;
    JTable table;
    DefaultTableModel model;
    UrunBean list;
    String delete = "ÜRÜNÜ AKTİFLEŞTİR";

    public UrunAktiflestir() {
        this.closable = true;
        this.aktiflestir();
    }

    public void aktiflestir() {
        this.db = new dbServlet();
        JPanel urunDuzenle = new JPanel((LayoutManager)null);
        urunDuzenle.setBounds(300, 30, 750, 700);
        urunDuzenle.setBorder(new TitledBorder(new LineBorder(Color.BLUE, 1), "Ürün Aktifleştir"));
        this.table = new JTable() {
            public boolean isCellEditable(int row, int column) {
                return column == 1;
            }
        };
        String[] dizi = new String[]{"ÜRÜN", " "};
        this.model = new DefaultTableModel(dizi, 0);
        this.table.setModel(this.model);
        this.table.setRowHeight(25);
        this.table.setBounds(30, 50, 460, 300);
        JScrollPane spane = new JScrollPane(this.table);
        spane.setBounds(this.table.getBounds());
        urunDuzenle.add(spane);
        this.Tablo();
        JButton kapat = new JButton("KAPAT");
        ActionListener kapatListener = new kapatListener();
        kapat.addActionListener(kapatListener);
        kapat.setBounds(30, 360, 460, 30);
        kapat.setLayout((LayoutManager)null);
        urunDuzenle.add(kapat);
        this.add(urunDuzenle);
    }

    public void Tablo() {
        int i = 0;
        Object[][] liste = new Object[this.db.AktifOlmayanUrunler().size()][];
        Iterator i$ = this.db.AktifOlmayanUrunler().iterator();

        while(i$.hasNext()) {
            UrunBean list = (UrunBean)i$.next();
            liste[i] = new Object[]{String.valueOf(list.getName()), this.delete};
            this.model.addRow(liste[i]);
        }

        this.buttonRenderer = new JButtonTableCellRenderer();
        this.buttonEditor = new JButtonTableCellEditor();
        this.buttonEditor.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int editingRow = UrunAktiflestir.this.table.getEditingRow();
                int a = JOptionPane.showConfirmDialog(UrunAktiflestir.this.table, "ÜRÜNÜ AKTİFLEŞTİRMEK İSTEDİĞİNİZE EMİN MİSİNİZ?", "SEÇİM YAPINIZ", 0);
                if (a == 0) {
                    UrunAktiflestir.this.db.UrunAktiflestir(UrunAktiflestir.this.table.getValueAt(editingRow, 0).toString());
                    UrunAktiflestir.this.table.setAutoCreateRowSorter(true);
                    UrunAktiflestir.this.model.removeRow(editingRow);
                }

            }
        });
        TableColumnModel columnModel = this.table.getColumnModel();
        columnModel.getColumn(1).setCellRenderer(this.buttonRenderer);
        columnModel.getColumn(1).setCellEditor(this.buttonEditor);
    }

    class kapatListener implements ActionListener {
        kapatListener() {
        }

        public void actionPerformed(ActionEvent event) {
            try {
                UrunDuzenle s = new UrunDuzenle();
                Main main = new Main();
                Main.desktop.removeAll();
                Main.desktop.add(s);
                s.pack();
                s.setVisible(true);
            } catch (Exception var5) {
                Logger.getLogger(Satis.class.getName()).log(Level.SEVERE, (String)null, var5);
            }

        }
    }
}
