package org.example.Main.sale;

import org.example.Main.database.dbServlet;
import org.example.Main.settings.UrunBean;
import org.example.Main.util.ParaFormati;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CreatedSatis extends JInternalFrame {
    private JButtonTableCellRenderer buttonRenderer;
    private JButtonTableCellEditor buttonEditor;
    Satis s;
    dbServlet db;
    String adet;
    JCheckBoxMenuItem checkbox;
    JCheckBoxMenuItem checkbox_urun;
    JTable table;
    DefaultTableModel model;
    String urun;
    BigDecimal yfiyat;
    SatisBean list;
    int satisId = 0;
    int masa = 0;
    String delete = "SİL";
    JButton button;
    JButton indirimButon;
    JButton araOdeme;
    JPanel urunPanel;
    JLabel toplamFiyat = new JLabel();
    JLabel indirim = new JLabel();
    JLabel genelFiyat = new JLabel();
    JLabel odeme = new JLabel();
    JLabel kalanOdeme = new JLabel();
    BigDecimal iskonto = new BigDecimal(0.0);
    ParaFormati pf = new ParaFormati();
    BigDecimal tfiyat = new BigDecimal(0.0);
    BigDecimal gfiyat = new BigDecimal(0.0);
    BigDecimal aodeme = new BigDecimal(0.0);
    BigDecimal kodeme = new BigDecimal(0.0);

    public CreatedSatis(int masa_no) {
        this.closable = true;
        this.CreatedSale(masa_no);
    }

    public void CreatedSale(int masa_no) {
        this.masa = masa_no;
        this.db = new dbServlet();
        ActionListener satisListener = new SatisListener();
        JPanel masaNoPanel = new JPanel();
        masaNoPanel.setLayout(new BorderLayout());
        masaNoPanel.setBorder(new TitledBorder(new LineBorder(Color.BLUE, 1), "Masa " + this.masa + ""));
        JPanel urunKategori = new JPanel();
        urunKategori.setLayout(new GridLayout(15, 1, 10, 10));
        urunKategori.setBorder(new TitledBorder(new LineBorder(Color.BLUE, 1), "Ürün Kategorileri"));
        Iterator i$ = this.db.UrunKategori().iterator();

        while(i$.hasNext()) {
            UrunBean list = (UrunBean)i$.next();
            this.button = new JButton(list.getUrunKategori());
            this.button.addActionListener(satisListener);
            this.button.setSize(50, 50);
            this.button.setBackground(new Color(102, 102, 255));
            this.button.setForeground(Color.WHITE);
            urunKategori.add(this.button);
        }

        this.urunPanel = new JPanel();
        this.urunPanel.setLayout(new GridLayout(10, 2, 10, 10));
        this.urunPanel.setBorder(new TitledBorder(new LineBorder(Color.BLUE, 1), "Ürünler"));
        JPanel tablePanel = new JPanel();
        tablePanel.setBorder(new TitledBorder(new LineBorder(Color.BLUE, 1), "Satışlar"));
        tablePanel.setLayout(new GridLayout(1, 1, 10, 10));
        this.table = new JTable() {
            public boolean isCellEditable(int row, int column) {
                return column == 3;
            }
        };
        JScrollPane spane = new JScrollPane(this.table);
        String[] dizi = new String[]{"ÜRÜN", "ADET", "FİYAT", " "};
        this.model = new DefaultTableModel(dizi, 0);
        this.table.setModel(this.model);
        this.table.setRowHeight(25);
        this.Tablo();
        tablePanel.add(spane);
        JPanel toplamPanel = new JPanel();
        toplamPanel.setLayout(new GridLayout(6, 1, 10, 10));
        this.araOdeme = new JButton("ARA ÖDEME GİR");
        ActionListener AraOdemeListener = new AraOdemeListener();
        this.araOdeme.addActionListener(AraOdemeListener);
        this.indirimButon = new JButton("İNDİRİM GİR");
        ActionListener indirimListener = new indirimListener();
        this.indirimButon.addActionListener(indirimListener);
        JLabel label = new JLabel("Toplam Fiyat :......");
        label.setHorizontalAlignment(4);
        JLabel label1 = new JLabel("İndirim :......");
        label1.setHorizontalAlignment(4);
        JLabel label2 = new JLabel("Genel Toplam :......");
        label2.setHorizontalAlignment(4);
        JLabel label3 = new JLabel("Ödeme :......");
        label3.setHorizontalAlignment(4);
        JLabel label5 = new JLabel("Kalan Tutar :......");
        label5.setHorizontalAlignment(4);
        this.toplamFiyat.setForeground(Color.RED);
        this.toplamFiyat.setHorizontalAlignment(2);
        this.indirim.setForeground(Color.RED);
        this.indirim.setHorizontalAlignment(2);
        this.genelFiyat.setForeground(Color.RED);
        this.genelFiyat.setHorizontalAlignment(2);
        this.odeme.setForeground(Color.RED);
        this.odeme.setHorizontalAlignment(2);
        this.kalanOdeme.setForeground(Color.RED);
        this.kalanOdeme.setHorizontalAlignment(2);
        toplamPanel.add(this.araOdeme, "Center");
        toplamPanel.add(this.indirimButon, "Center");
        toplamPanel.add(label, "West");
        toplamPanel.add(this.toplamFiyat, "East");
        toplamPanel.add(label1, "West");
        toplamPanel.add(this.indirim, "East");
        toplamPanel.add(label2, "West");
        toplamPanel.add(this.genelFiyat, "East");
        toplamPanel.add(label3, "West");
        toplamPanel.add(this.odeme, "East");
        toplamPanel.add(label5, "West");
        toplamPanel.add(this.kalanOdeme, "East");
        JPanel panelTop = new JPanel();
        panelTop.setLayout(new BorderLayout(10, 10));
        panelTop.add(tablePanel, "Center");
        panelTop.add(toplamPanel, "South");
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 1, 10, 10));
        tablePanel.setBorder(new TitledBorder(new LineBorder(Color.BLUE, 1), "Satış İşlemleri"));
        this.add(masaNoPanel, "North");
        this.add(urunKategori, "Before");
        this.add(this.urunPanel, "Center");
        this.add(panelTop, "After");
    }

    public void Kaydet() {
        this.tfiyat = new BigDecimal(0.0);
        java.util.List<SatisBean> liste = new ArrayList();
        this.db = new dbServlet();
        byte a;
        if (this.model.getRowCount() != 0) {
            int rowCount;
            for(rowCount = 0; rowCount < this.model.getRowCount(); ++rowCount) {
                this.list = new SatisBean();
                this.list.setSatisId(this.satisId);
                this.list.setUrunId(this.db.UrunGetId(this.table.getValueAt(rowCount, 0).toString()));
                this.list.setAdet(Integer.parseInt(this.table.getValueAt(rowCount, 1).toString()));
                this.list.setFiyat(new BigDecimal(this.pf.GeriCevir(this.table.getValueAt(rowCount, 2).toString())));
                this.tfiyat = this.tfiyat.add(new BigDecimal(this.pf.GeriCevir(this.table.getValueAt(rowCount, 2).toString())));
                this.list.setToplamFiyat(this.tfiyat);
                this.list.setMasaNo(this.masa);
                this.list.setIndirim(new BigDecimal(this.pf.GeriCevir(this.indirim.getText())));
                liste.add(this.list);
            }

            if (this.satisId != 0) {
                this.db.SatisGuncelle(this.list);
            } else {
                this.db.SatisKayit(this.list);
                a = 0;
                this.db.MasaDurumGuncelle(this.list, a);
            }

            this.db.SatisAyrintiKayit(liste);
            rowCount = this.model.getRowCount();

            for(int i = rowCount - 1; i >= 0; --i) {
                this.model.removeRow(i);
            }

            this.Tablo();
        } else {
            this.db.SatisSil(this.satisId);
            this.list = new SatisBean();
            this.list.setMasaNo(this.masa);
            a = 1;
            this.db.MasaDurumGuncelle(this.list, a);
            JOptionPane.showMessageDialog(this.urunPanel, "Satış silindi...");
        }

    }

    public void Tablo() {
        this.tfiyat = new BigDecimal(0.0);
        this.gfiyat = new BigDecimal(0.0);
        this.aodeme = new BigDecimal(0.0);
        this.kodeme = new BigDecimal(0.0);
        this.satisId = 0;
        java.util.List<SatisBean> liste = this.db.SatilanUrun(this.masa);
        Object[][] yliste = new Object[liste.size()][];

        for(int i = 0; i < liste.size(); ++i) {
            this.iskonto = ((SatisBean)liste.get(i)).getIndirim();
            this.satisId = ((SatisBean)liste.get(i)).getSatisId();
            BigDecimal fiyat = this.db.UrunFiyat(((SatisBean)liste.get(i)).getUrun()).multiply(new BigDecimal(((SatisBean)liste.get(i)).getAdet()));
            yliste[i] = new Object[]{String.valueOf(((SatisBean)liste.get(i)).getUrun()), ((SatisBean)liste.get(i)).getAdet(), this.pf.ParayaCevir(fiyat), this.delete};
            this.model.addRow(yliste[i]);
            this.tfiyat = this.tfiyat.add(fiyat);
            this.gfiyat = this.tfiyat.subtract(this.iskonto);
        }

        if (this.satisId != 0) {
            this.aodeme = this.db.GetSatisAraOdeme(this.satisId);
            this.kodeme = this.gfiyat.subtract(this.aodeme);
        }

        this.buttonRenderer = new JButtonTableCellRenderer();
        this.buttonEditor = new JButtonTableCellEditor();
        this.buttonEditor.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int editingRow = CreatedSatis.this.table.getSelectedRow();
                    BigDecimal sfiyat = (new BigDecimal(CreatedSatis.this.pf.GeriCevir(CreatedSatis.this.table.getValueAt(editingRow, 2).toString()))).divide(new BigDecimal(CreatedSatis.this.table.getValueAt(editingRow, 1).toString()));
                    int yAdet = Integer.parseInt(CreatedSatis.this.table.getValueAt(editingRow, 1).toString());
                    if (yAdet != 1) {
                        BigDecimal yfiyat = (new BigDecimal(CreatedSatis.this.pf.GeriCevir(CreatedSatis.this.table.getValueAt(editingRow, 2).toString()))).subtract(sfiyat);
                        CreatedSatis.this.table.setValueAt(CreatedSatis.this.pf.ParayaCevir(yfiyat), editingRow, 2);
                        CreatedSatis.this.table.setValueAt(yAdet - 1, editingRow, 1);
                    } else {
                        CreatedSatis.this.model.removeRow(editingRow);
                    }

                    CreatedSatis.this.tfiyat = (new BigDecimal(CreatedSatis.this.pf.GeriCevir(CreatedSatis.this.toplamFiyat.getText()))).subtract(sfiyat);
                    CreatedSatis.this.gfiyat = (new BigDecimal(CreatedSatis.this.pf.GeriCevir(CreatedSatis.this.genelFiyat.getText()))).subtract(sfiyat);
                    CreatedSatis.this.kodeme = CreatedSatis.this.gfiyat.subtract(CreatedSatis.this.aodeme);
                    CreatedSatis.this.toplamFiyat.setText(CreatedSatis.this.pf.ParayaCevir(CreatedSatis.this.tfiyat));
                    CreatedSatis.this.genelFiyat.setText(CreatedSatis.this.pf.ParayaCevir(CreatedSatis.this.gfiyat));
                    CreatedSatis.this.kalanOdeme.setText(CreatedSatis.this.pf.ParayaCevir(CreatedSatis.this.kodeme));
                    CreatedSatis.this.table.setAutoCreateRowSorter(true);
                } catch (Exception var6) {
                    System.out.println(var6);
                }

                CreatedSatis.this.Kaydet();
            }
        });
        this.toplamFiyat.setText(this.pf.ParayaCevir(this.tfiyat));
        this.genelFiyat.setText(this.pf.ParayaCevir(this.gfiyat));
        this.indirim.setText(this.pf.ParayaCevir(this.iskonto));
        this.odeme.setText(this.pf.ParayaCevir(this.aodeme));
        this.kalanOdeme.setText(this.pf.ParayaCevir(this.kodeme));
        TableColumnModel columnModel = this.table.getColumnModel();
        columnModel.getColumn(3).setCellRenderer(this.buttonRenderer);
        columnModel.getColumn(3).setCellEditor(this.buttonEditor);
    }

    class indirimListener implements ActionListener {
        indirimListener() {
        }

        public void actionPerformed(ActionEvent event) {
            SatisAdet sa = new SatisAdet();
            CreatedSatis.this.adet = sa.Adet();
            if (CreatedSatis.this.adet != null) {
                CreatedSatis.this.iskonto = new BigDecimal(CreatedSatis.this.pf.GeriCevir(CreatedSatis.this.adet));
                BigDecimal topFiyat = new BigDecimal(CreatedSatis.this.pf.GeriCevir(CreatedSatis.this.toplamFiyat.getText()));
                new BigDecimal(CreatedSatis.this.pf.GeriCevir(CreatedSatis.this.genelFiyat.getText()));
                BigDecimal genFiyat = topFiyat.subtract(CreatedSatis.this.iskonto);
                if (CreatedSatis.this.iskonto.compareTo(topFiyat) < 0 || CreatedSatis.this.iskonto.compareTo(topFiyat) == 0) {
                    CreatedSatis.this.indirim.setText(CreatedSatis.this.pf.ParayaCevir(CreatedSatis.this.iskonto));
                    CreatedSatis.this.genelFiyat.setText(CreatedSatis.this.pf.ParayaCevir(genFiyat));
                    CreatedSatis.this.kodeme = genFiyat.subtract(CreatedSatis.this.aodeme);
                    CreatedSatis.this.kalanOdeme.setText(CreatedSatis.this.pf.ParayaCevir(CreatedSatis.this.kodeme));
                    java.util.List<SatisBean> liste = new ArrayList();
                    CreatedSatis.this.list = new SatisBean();
                    CreatedSatis.this.list.setSatisId(CreatedSatis.this.satisId);
                    CreatedSatis.this.list.setIndirim(new BigDecimal(CreatedSatis.this.pf.GeriCevir(CreatedSatis.this.indirim.getText())));
                    liste.add(CreatedSatis.this.list);
                    CreatedSatis.this.db.IndirimGir(liste);
                }
            }

        }
    }

    class AraOdemeListener implements ActionListener {
        AraOdemeListener() {
        }

        public void actionPerformed(ActionEvent event) {
            ParaFormati pf = new ParaFormati();
            CreatedSatis.this.db = new dbServlet();
            int satis_id = CreatedSatis.this.db.SatisId(CreatedSatis.this.masa);
            if (satis_id != 0) {
                SatisAdet sa = new SatisAdet();
                String tutar = sa.Adet();
                System.out.println(tutar);
                BigDecimal genFiyat = new BigDecimal(pf.GeriCevir(CreatedSatis.this.genelFiyat.getText()));
                BigDecimal ilkodeme = new BigDecimal(pf.GeriCevir(CreatedSatis.this.odeme.getText()));
                if (tutar != null) {
                    CreatedSatis.this.db.SatisAraOdeme(satis_id, new BigDecimal(tutar));
                    tutar = pf.GeriCevir(tutar);
                    CreatedSatis.this.aodeme = ilkodeme.add(new BigDecimal(tutar));
                    CreatedSatis.this.odeme.setText(pf.ParayaCevir(CreatedSatis.this.aodeme));
                    CreatedSatis.this.kodeme = genFiyat.subtract(CreatedSatis.this.aodeme);
                    CreatedSatis.this.kalanOdeme.setText(pf.ParayaCevir(CreatedSatis.this.kodeme));
                }
            }

        }
    }

    class iptalListener implements ActionListener {
        iptalListener() {
        }

        public void actionPerformed(ActionEvent event) {
            try {
                CreatedSatis.this.s = new Satis();
                CreatedSatis.this.s.CreatedSale();
            } catch (Exception var3) {
                Logger.getLogger(CreatedSatis.class.getName()).log(Level.SEVERE, (String)null, var3);
            }

        }
    }

    class kaydetListener implements ActionListener {
        kaydetListener() {
        }

        public void actionPerformed(ActionEvent event) {
            CreatedSatis.this.tfiyat = new BigDecimal(0.0);
            List<SatisBean> liste = new ArrayList();
            CreatedSatis.this.db = new dbServlet();
            byte a;
            if (CreatedSatis.this.model.getRowCount() != 0) {
                int rowCount;
                for(rowCount = 0; rowCount < CreatedSatis.this.model.getRowCount(); ++rowCount) {
                    CreatedSatis.this.list = new SatisBean();
                    CreatedSatis.this.list.setSatisId(CreatedSatis.this.satisId);
                    CreatedSatis.this.list.setUrunId(CreatedSatis.this.db.UrunGetId(CreatedSatis.this.table.getValueAt(rowCount, 0).toString()));
                    CreatedSatis.this.list.setAdet(Integer.parseInt(CreatedSatis.this.table.getValueAt(rowCount, 1).toString()));
                    CreatedSatis.this.list.setFiyat(new BigDecimal(CreatedSatis.this.pf.GeriCevir(CreatedSatis.this.table.getValueAt(rowCount, 2).toString())));
                    CreatedSatis.this.tfiyat = CreatedSatis.this.tfiyat.add(new BigDecimal(CreatedSatis.this.pf.GeriCevir(CreatedSatis.this.table.getValueAt(rowCount, 2).toString())));
                    CreatedSatis.this.list.setToplamFiyat(CreatedSatis.this.tfiyat);
                    CreatedSatis.this.list.setMasaNo(CreatedSatis.this.masa);
                    CreatedSatis.this.list.setIndirim(new BigDecimal(CreatedSatis.this.pf.GeriCevir(CreatedSatis.this.indirim.getText())));
                    liste.add(CreatedSatis.this.list);
                }

                if (CreatedSatis.this.satisId != 0) {
                    CreatedSatis.this.db.SatisGuncelle(CreatedSatis.this.list);
                } else {
                    CreatedSatis.this.db.SatisKayit(CreatedSatis.this.list);
                    a = 0;
                    CreatedSatis.this.db.MasaDurumGuncelle(CreatedSatis.this.list, a);
                }

                CreatedSatis.this.db.SatisAyrintiKayit(liste);
                JOptionPane.showMessageDialog(CreatedSatis.this.urunPanel, "KAYDEDİLDİ...");
                rowCount = CreatedSatis.this.model.getRowCount();

                for(int i = rowCount - 1; i >= 0; --i) {
                    CreatedSatis.this.model.removeRow(i);
                }

                CreatedSatis.this.Tablo();
            } else {
                CreatedSatis.this.db.SatisSil(CreatedSatis.this.satisId);
                CreatedSatis.this.list = new SatisBean();
                CreatedSatis.this.list.setMasaNo(CreatedSatis.this.masa);
                a = 1;
                CreatedSatis.this.db.MasaDurumGuncelle(CreatedSatis.this.list, a);
                JOptionPane.showMessageDialog(CreatedSatis.this.urunPanel, "Satış silindi...");
            }

        }
    }

    class SatisListener1 implements ActionListener {
        SatisListener1() {
        }

        public void actionPerformed(ActionEvent event) {
            CreatedSatis.this.tfiyat = new BigDecimal(CreatedSatis.this.pf.GeriCevir(CreatedSatis.this.toplamFiyat.getText().toString()));
            CreatedSatis.this.gfiyat = new BigDecimal(CreatedSatis.this.pf.GeriCevir(CreatedSatis.this.genelFiyat.getText().toString()));
            CreatedSatis.this.urun = event.getActionCommand();
            CreatedSatis.this.db = new dbServlet();
            CreatedSatis.this.yfiyat = CreatedSatis.this.db.UrunFiyat(CreatedSatis.this.urun).multiply(new BigDecimal(1));
            CreatedSatis.this.model.addRow(new Object[]{CreatedSatis.this.urun, 1, CreatedSatis.this.pf.ParayaCevir(CreatedSatis.this.yfiyat), CreatedSatis.this.delete});
            CreatedSatis.this.tfiyat = CreatedSatis.this.tfiyat.add(CreatedSatis.this.yfiyat);
            CreatedSatis.this.gfiyat = CreatedSatis.this.tfiyat.subtract(CreatedSatis.this.iskonto);
            CreatedSatis.this.toplamFiyat.setText(CreatedSatis.this.pf.ParayaCevir(CreatedSatis.this.tfiyat));
            CreatedSatis.this.genelFiyat.setText(CreatedSatis.this.pf.ParayaCevir(CreatedSatis.this.gfiyat));
            CreatedSatis.this.Kaydet();
        }
    }

    class SatisListener implements ActionListener {
        SatisListener() {
        }

        public void actionPerformed(ActionEvent event) {
            CreatedSatis.this.urunPanel.removeAll();
            ActionListener satisListener1 = CreatedSatis.this.new SatisListener1();
            String urun_kategori = event.getActionCommand();
            int id = CreatedSatis.this.db.UrunKategoriId(urun_kategori);
            Iterator i$ = CreatedSatis.this.db.UrunAdi(id).iterator();

            while(i$.hasNext()) {
                UrunBean list = (UrunBean)i$.next();
                CreatedSatis.this.button = new JButton(list.getName());
                CreatedSatis.this.button.addActionListener(satisListener1);
                CreatedSatis.this.button.setSize(50, 50);
                CreatedSatis.this.button.setBackground(new Color(0, 153, 102));
                CreatedSatis.this.button.setForeground(Color.WHITE);
                CreatedSatis.this.urunPanel.add(CreatedSatis.this.button);
            }

            CreatedSatis.this.urunPanel.setVisible(false);
            CreatedSatis.this.urunPanel.setVisible(true);
        }
    }
}
