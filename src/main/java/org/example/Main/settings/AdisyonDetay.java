package org.example.Main.settings;

import org.example.Main.database.dbServlet;
import org.example.Main.sale.SatisBean;
import org.example.Main.util.ParaFormati;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.util.List;

public class AdisyonDetay {
    dbServlet db;
    public JFrame mp;
    JTable table;
    DefaultTableModel model;
    JLabel toplamFiyat = new JLabel();
    JLabel indirim = new JLabel();
    JLabel genelFiyat = new JLabel();

    public AdisyonDetay() {
    }

    public void Adisyon(int id) {
        this.db = new dbServlet();
        this.mp = new JFrame("ADİSYON DETAY");
        this.mp.setDefaultCloseOperation(2);
        this.mp.setBounds(400, 20, 500, 500);
        JPanel tablePanel = new JPanel();
        tablePanel.setBorder(new TitledBorder(new LineBorder(Color.BLUE, 1), "Satışlar"));
        tablePanel.setLayout(new GridLayout(1, 1, 10, 10));
        this.table = new JTable() {
            public boolean isCellEditable(int row, int column) {
                return column == 3;
            }
        };
        JScrollPane spane = new JScrollPane(this.table);
        String[] dizi = new String[]{"KATEGORİ", "ÜRÜN", "ADET", "FİYAT"};
        this.model = new DefaultTableModel(dizi, 0);
        this.table.setModel(this.model);
        this.table.setRowHeight(25);
        this.Tablo(id);
        tablePanel.add(spane);
        JPanel toplamPanel = new JPanel();
        toplamPanel.setLayout(new GridLayout(4, 1, 10, 10));
        JLabel label = new JLabel("Toplam Fiyat :......");
        label.setHorizontalAlignment(4);
        JLabel label1 = new JLabel("İndirim :......");
        label1.setHorizontalAlignment(4);
        JLabel label2 = new JLabel("Genel Toplam :......");
        label2.setHorizontalAlignment(4);
        this.toplamFiyat.setForeground(Color.RED);
        this.toplamFiyat.setHorizontalAlignment(2);
        this.indirim.setForeground(Color.RED);
        this.indirim.setHorizontalAlignment(2);
        this.genelFiyat.setForeground(Color.RED);
        this.genelFiyat.setHorizontalAlignment(2);
        toplamPanel.add(label, "West");
        toplamPanel.add(this.toplamFiyat, "East");
        toplamPanel.add(label1, "East");
        toplamPanel.add(this.indirim, "West");
        toplamPanel.add(label2, "East");
        toplamPanel.add(this.genelFiyat, "West");
        JPanel panelTop = new JPanel();
        panelTop.setLayout(new BorderLayout(10, 10));
        panelTop.add(tablePanel, "Center");
        panelTop.add(toplamPanel, "South");
        this.mp.getContentPane().add(panelTop);
        this.mp.setVisible(true);
    }

    public void Tablo(int id) {
        BigDecimal tfiyat = new BigDecimal(0.0);
        BigDecimal gfiyat = new BigDecimal(0.0);
        BigDecimal iskonto = new BigDecimal(0.0);
        new BigDecimal(0.0);
        ParaFormati pf = new ParaFormati();
        List<SatisBean> liste = this.db.AdisyonDetay(String.valueOf(id));
        Object[][] yliste = new Object[liste.size()][];

        for(int i = 0; i < liste.size(); ++i) {
            BigDecimal UrunFiyat = this.db.UrunAdisyonFiyat(((SatisBean)liste.get(i)).getUrunId()).multiply(new BigDecimal(((SatisBean)liste.get(i)).getAdet()));
            String kategori = this.db.UrunKategoriAdi(((SatisBean)liste.get(i)).getUrunKategoriId());
            yliste[i] = new Object[]{String.valueOf(kategori), ((SatisBean)liste.get(i)).getUrun(), ((SatisBean)liste.get(i)).getAdet(), pf.ParayaCevir(UrunFiyat)};
            this.model.addRow(yliste[i]);
            tfiyat = tfiyat.add(UrunFiyat);
            iskonto = ((SatisBean)liste.get(i)).getIndirim();
            gfiyat = tfiyat.subtract(iskonto);
        }

        this.toplamFiyat.setText(pf.ParayaCevir(tfiyat));
        this.genelFiyat.setText(pf.ParayaCevir(gfiyat));
        this.indirim.setText(pf.ParayaCevir(iskonto));
    }
}
