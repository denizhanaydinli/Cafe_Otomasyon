package org.example.Main.settings;

import org.example.Main.Main.Main;
import org.example.Main.database.dbServlet;
import org.example.Main.sale.Satis;
import org.example.Main.util.TextFieldCapitalListener;

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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UrunKategoriDuzenle extends JInternalFrame {
    JTable table;
    JLabel kategoriAdi;
    JTextField textUrunAdi;
    DefaultTableModel model;
    UrunBean list;
    dbServlet db;
    JButton sil;
    JButton guncelle;
    JButton silinen_urun;
    int index = -1;

    public UrunKategoriDuzenle() {
        this.closable = true;
        this.UrunlerKategori();
    }

    public void UrunlerKategori() {
        this.db = new dbServlet();
        JPanel urunDuzenle = new JPanel((LayoutManager)null);
        urunDuzenle.setBounds(300, 30, 750, 700);
        urunDuzenle.setBorder(new TitledBorder(new LineBorder(Color.BLUE, 1), "Ürün Kategori Düzenle"));
        this.kategoriAdi = new JLabel("KATEGORİ:", 2);
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
        String[] dizi = new String[]{"ÜRÜN KATEGORİSİ"};
        this.model = new DefaultTableModel(dizi, 0);
        this.table.setModel(this.model);
        this.table.setRowHeight(25);
        this.table.setLayout((LayoutManager)null);
        this.table.setBounds(30, 70, 460, 400);
        ListSelectionModel selectionModel = this.table.getSelectionModel();
        selectionModel.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                UrunKategoriDuzenle.this.index = UrunKategoriDuzenle.this.table.getSelectedRow();
            }
        });
        final TableRowSorter<TableModel> rowSorter = new TableRowSorter(this.table.getModel());
        this.textUrunAdi.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                String text = UrunKategoriDuzenle.this.textUrunAdi.getText();
                if (text.trim().length() == 0) {
                    rowSorter.setRowFilter((RowFilter)null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter(text, new int[0]));
                }

            }

            public void removeUpdate(DocumentEvent e) {
                String text = UrunKategoriDuzenle.this.textUrunAdi.getText();
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
        this.sil = new JButton("Seçilen Kategoriyi Sil");
        this.sil.setBounds(520, 70, 190, 80);
        this.sil.setLayout((LayoutManager)null);
        ActionListener UrunSilListener = new UrunSilListener();
        this.sil.addActionListener(UrunSilListener);
        this.guncelle = new JButton("Seçilen Kategoriyi Güncelle");
        this.guncelle.setBounds(520, 170, 190, 80);
        this.guncelle.setLayout((LayoutManager)null);
        ActionListener UrunGuncelleListener = new UrunGuncelleListener();
        this.guncelle.addActionListener(UrunGuncelleListener);
        this.silinen_urun = new JButton("Silinen Kategoriler");
        this.silinen_urun.setBounds(520, 270, 190, 80);
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
        List<UrunBean> liste = this.db.UrunKategori();
        Object[][] yliste = new Object[liste.size()][];

        for(int i = 0; i < liste.size(); ++i) {
            yliste[i] = new Object[]{String.valueOf(((UrunBean)liste.get(i)).getUrunKategori())};
            this.model.addRow(yliste[i]);
        }

    }

    public void Guncelle(String text) {
        if (text.isEmpty()) {
            JOptionPane.showMessageDialog(this.table, "BOŞ ALAN BIRAKMAYINIZ");
        } else {
            int id = this.db.UrunKategoriId(text);
            if (id != 0) {
                JOptionPane.showMessageDialog(this.table, "KATEGORİ ZATEN KAYITLI");
            } else {
                this.list = new UrunBean();
                this.list.setUrunKategoriId(this.db.UrunKategoriId(this.table.getValueAt(this.index, 0).toString()));
                this.list.setUrunKategori(text);
                this.db.UrunKategoriGuncelle(this.list);
                int rowCount = this.model.getRowCount();

                for(int i = rowCount - 1; i >= 0; --i) {
                    this.model.removeRow(i);
                }

                this.Tablo();
                JOptionPane.showMessageDialog(this.table, "ÜRÜN KATEGORİSİ GÜNCELLENDİ...");
            }
        }

    }

    public static void main(String[] args) {
        UrunKategoriDuzenle ue = new UrunKategoriDuzenle();
        ue.UrunlerKategori();
    }

    class SilinenUrunListener implements ActionListener {
        SilinenUrunListener() {
        }

        public void actionPerformed(ActionEvent event) {
            try {
                UrunKategoriAktiflestir s = new UrunKategoriAktiflestir();
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
            if (UrunKategoriDuzenle.this.index != -1) {
                JTextField urun = new JTextField();
                KeyListener keyTyped = new TextFieldCapitalListener();
                urun.addKeyListener(keyTyped);
                urun.setText(UrunKategoriDuzenle.this.table.getValueAt(UrunKategoriDuzenle.this.index, 0).toString());
                Object[] fields = new Object[]{"ÜRÜN KATEGORİ", urun};
                int a = JOptionPane.showConfirmDialog((Component)null, fields, "GÜNCELLE", 2);
                if (a == 0) {
                    UrunKategoriDuzenle.this.Guncelle(urun.getText());
                }
            }

        }
    }

    class UrunSilListener implements ActionListener {
        UrunSilListener() {
        }

        public void actionPerformed(ActionEvent event) {
            if (UrunKategoriDuzenle.this.index != -1) {
                int a = JOptionPane.showConfirmDialog(UrunKategoriDuzenle.this.table, "ÜRÜNÜ KATEGORİSİNİ SİLMEK İSTEDİĞİNİZE EMİN MİSİNİZ?", "SEÇİM YAPINIZ", 0);
                if (a == 0) {
                    UrunKategoriDuzenle.this.db.UrunKategoriSil(UrunKategoriDuzenle.this.table.getValueAt(UrunKategoriDuzenle.this.index, 0).toString());
                    UrunKategoriDuzenle.this.model.removeRow(UrunKategoriDuzenle.this.index);
                }
            }

        }
    }
}
