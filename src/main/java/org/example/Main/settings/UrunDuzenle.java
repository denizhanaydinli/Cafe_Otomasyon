package org.example.Main.settings;

import org.example.Main.Main.Main;
import org.example.Main.database.dbServlet;
import org.example.Main.sale.Satis;
import org.example.Main.util.ParaFormati;
import org.example.Main.util.TextFieldCapitalListener;
import org.example.Main.util.TextFieldKeyListener;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UrunDuzenle extends JInternalFrame {
    JTable table;
    JLabel kategoriAdi;
    JTextField textUrunAdi;
    DefaultTableModel model;
    UrunBean list;
    dbServlet db;
    JMenuBar menubar;
    JButton sil;
    JButton guncelle;
    JButton silinen_urun;
    int index = -1;
    ParaFormati pf = new ParaFormati();

    public UrunDuzenle() {
        this.closable = true;
        this.Urunler();
    }

    public void Urunler() {
        this.db = new dbServlet();
        JPanel urunDuzenle = new JPanel((LayoutManager)null);
        urunDuzenle.setBounds(300, 30, 750, 700);
        urunDuzenle.setBorder(new TitledBorder(new LineBorder(Color.BLUE, 1), "Ürün Düzenle"));
        this.kategoriAdi = new JLabel("ÜRÜN ADI:", 2);
        this.kategoriAdi.setBounds(30, 30, 70, 30);
        this.kategoriAdi.setLayout((LayoutManager)null);
        this.textUrunAdi = new JTextField(20);
        this.textUrunAdi.setBounds(110, 30, 360, 30);
        this.textUrunAdi.setLayout((LayoutManager)null);
        KeyListener bkeyTyped = new TextFieldCapitalListener();
        this.textUrunAdi.addKeyListener(bkeyTyped);
        this.table = new JTable() {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        String[] dizi = new String[]{"KATEGORİ", "ÜRÜN", "FİYAT"};
        this.model = new DefaultTableModel(dizi, 0);
        this.table.setModel(this.model);
        this.table.setRowHeight(25);
        this.table.setLayout((LayoutManager)null);
        this.table.setBounds(30, 70, 460, 400);
        ListSelectionModel selectionModel = this.table.getSelectionModel();
        selectionModel.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                UrunDuzenle.this.index = UrunDuzenle.this.table.getSelectedRow();
            }
        });
        final TableRowSorter<TableModel> rowSorter = new TableRowSorter(this.table.getModel());
        this.textUrunAdi.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                String text = UrunDuzenle.this.textUrunAdi.getText();
                if (text.trim().length() == 0) {
                    rowSorter.setRowFilter((RowFilter)null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter(text, new int[0]));
                }

            }

            public void removeUpdate(DocumentEvent e) {
                String text = UrunDuzenle.this.textUrunAdi.getText();
                if (text.trim().length() == 0) {
                    rowSorter.setRowFilter((RowFilter)null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter(text, new int[0]));
                }

            }

            public void changedUpdate(DocumentEvent e) {
            }
        });
        this.table.setRowSorter(rowSorter);
        JScrollPane spane = new JScrollPane(this.table);
        spane.setBounds(this.table.getBounds());
        this.Tablo();
        this.sil = new JButton("Seçilen\n Ürünü Sil");
        this.sil.setBounds(520, 70, 170, 80);
        this.sil.setLayout((LayoutManager)null);
        ActionListener UrunSilListener = new UrunSilListener();
        this.sil.addActionListener(UrunSilListener);
        this.guncelle = new JButton("Seçilen Ürünü Güncelle");
        this.guncelle.setBounds(520, 170, 170, 80);
        this.guncelle.setLayout((LayoutManager)null);
        ActionListener UrunGuncelleListener = new UrunGuncelleListener();
        this.guncelle.addActionListener(UrunGuncelleListener);
        this.silinen_urun = new JButton("Silinen Ürünler");
        this.silinen_urun.setBounds(520, 270, 170, 80);
        this.silinen_urun.setLayout((LayoutManager)null);
        ActionListener SilinenUrunListener = new SilinenUrunListener();
        this.silinen_urun.addActionListener(SilinenUrunListener);
        urunDuzenle.add(this.kategoriAdi);
        urunDuzenle.add(this.textUrunAdi);
        urunDuzenle.add(this.sil);
        urunDuzenle.add(this.guncelle);
        urunDuzenle.add(this.silinen_urun);
        urunDuzenle.add(spane);
        this.add(urunDuzenle);
    }

    public void Tablo() {
        this.db = new dbServlet();
        new ArrayList();
        List<UrunBean> liste = this.db.Urunler();
        Object[][] yliste = new Object[liste.size()][];

        for(int i = 0; i < liste.size(); ++i) {
            yliste[i] = new Object[]{String.valueOf(this.db.UrunKategoriAdi(((UrunBean)liste.get(i)).getUrunKategoriId())), ((UrunBean)liste.get(i)).getName(), this.pf.ParayaCevir(((UrunBean)liste.get(i)).getFiyat())};
            this.model.addRow(yliste[i]);
        }

    }

    public void Guncelle(String kategori, String text, String fiyat) {
        if (!text.isEmpty() && !fiyat.isEmpty()) {
            this.list = new UrunBean();
            this.list.setUrunKategoriId(this.db.UrunKategoriId(kategori));
            this.list.setName(text);
            this.list.setFiyat(new BigDecimal(this.pf.GeriCevir(fiyat)));
            this.db.UrunGuncelle(this.list);
            int rowCount = this.model.getRowCount();

            for(int i = rowCount - 1; i >= 0; --i) {
                this.model.removeRow(i);
            }

            this.Tablo();
            JOptionPane.showMessageDialog(this.table, "ÜRÜN GÜNCELLENDİ...");
        } else {
            JOptionPane.showMessageDialog(this.table, "BOŞ ALAN BIRAKMAYINIZ");
        }

    }

    class SilinenUrunListener implements ActionListener {
        SilinenUrunListener() {
        }

        public void actionPerformed(ActionEvent event) {
            try {
                UrunAktiflestir s = new UrunAktiflestir();
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

    class UrunGuncelleListener implements ActionListener {
        UrunGuncelleListener() {
        }

        public void actionPerformed(ActionEvent event) {
            JComboBox combobox = new JComboBox();
            if (UrunDuzenle.this.index != -1) {
                String[] dizi1 = new String[UrunDuzenle.this.db.UrunKategori().size()];
                int b = 0;

                for(Iterator i$ = UrunDuzenle.this.db.UrunKategori().iterator(); i$.hasNext(); ++b) {
                    UrunBean list = (UrunBean)i$.next();
                    dizi1[b] = list.getUrunKategori();
                }

                ComboBoxModel combomodel = new DefaultComboBoxModel(dizi1);
                combobox.setModel(combomodel);
                JTextField urun = new JTextField();
                KeyListener keyTyped = new TextFieldCapitalListener();
                urun.addKeyListener(keyTyped);
                JTextField fiyat = new JTextField();
                KeyListener akeyTyped = new TextFieldKeyListener();
                fiyat.addKeyListener(akeyTyped);
                combobox.setSelectedItem(UrunDuzenle.this.table.getValueAt(UrunDuzenle.this.index, 0).toString());
                urun.setText(UrunDuzenle.this.table.getValueAt(UrunDuzenle.this.index, 1).toString());
                fiyat.setText(UrunDuzenle.this.table.getValueAt(UrunDuzenle.this.index, 2).toString());
                Object[] fields = new Object[]{"ÜRÜN KATEGORİ", combobox, "ÜRÜN ADI", urun, "ÜRÜN FİYATI:", fiyat};
                int a = JOptionPane.showConfirmDialog(UrunDuzenle.this.table, fields, "GÜNCELLE", 2);
                if (a == 0) {
                    UrunDuzenle.this.Guncelle(combobox.getSelectedItem().toString(), urun.getText(), fiyat.getText());
                }
            }

        }
    }

    class UrunSilListener implements ActionListener {
        UrunSilListener() {
        }

        public void actionPerformed(ActionEvent event) {
            if (UrunDuzenle.this.index != -1) {
                int a = JOptionPane.showConfirmDialog(UrunDuzenle.this.table, "ÜRÜNÜ SİLMEK İSTEDİĞİNİZE EMİN MİSİNİZ?", "SEÇİM YAPINIZ", 0);
                if (a == 0) {
                    UrunDuzenle.this.db.UrunSil(UrunDuzenle.this.table.getValueAt(UrunDuzenle.this.index, 1).toString());
                    UrunDuzenle.this.model.removeRow(UrunDuzenle.this.index);
                }
            }

        }
    }
}
