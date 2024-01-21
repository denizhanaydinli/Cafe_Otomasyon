package org.example.Main.settings;

import org.example.Main.database.dbServlet;
import org.example.Main.util.ParaFormati;
import org.example.Main.util.TextFieldCapitalListener;
import org.example.Main.util.TextFieldKeyListener;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.math.BigDecimal;
import java.util.Iterator;

public class UrunEkle extends JInternalFrame {
    JPanel urunEkle;
    JLabel kategoriAdi;
    JLabel labelUrun;
    JLabel labelUrunFiyati;
    JTextField textUrunAdi;
    JTextField textUrunFiyat;
    JButton kaydet;
    JComboBox combo;
    UrunBean list;
    dbServlet db;

    public UrunEkle() {
        this.closable = true;
        this.Urun_Ekle();
    }

    public void Urun_Ekle() {
        this.db = new dbServlet();
        this.urunEkle = new JPanel((LayoutManager)null);
        this.urunEkle.setBounds(400, 200, 600, 400);
        this.urunEkle.setBorder(new TitledBorder(new LineBorder(Color.BLUE, 1), "Ürün Ekle"));
        this.kategoriAdi = new JLabel("ÜRÜN KATEGORİSİ:", 4);
        this.kategoriAdi.setBounds(50, 70, 150, 30);
        this.kategoriAdi.setLayout((LayoutManager)null);
        String[] dizi1 = new String[this.db.UrunKategori().size()];
        int i = 0;

        for(Iterator i$ = this.db.UrunKategori().iterator(); i$.hasNext(); ++i) {
            UrunBean list = (UrunBean)i$.next();
            dizi1[i] = list.getUrunKategori();
        }

        this.combo = new JComboBox(dizi1);
        this.combo.setBounds(210, 70, 250, 30);
        this.combo.setLayout((LayoutManager)null);
        this.labelUrun = new JLabel("ÜRÜN ADI:", 4);
        this.labelUrun.setBounds(50, 110, 150, 30);
        this.labelUrun.setLayout((LayoutManager)null);
        this.textUrunAdi = new JTextField(20);
        this.textUrunAdi.setBounds(210, 110, 250, 30);
        this.textUrunAdi.setLayout((LayoutManager)null);
        KeyListener bkeyTyped = new TextFieldCapitalListener();
        this.textUrunAdi.addKeyListener(bkeyTyped);
        this.labelUrunFiyati = new JLabel("ÜRÜNÜN FİYATI:", 4);
        this.labelUrunFiyati.setBounds(50, 150, 150, 30);
        this.labelUrunFiyati.setLayout((LayoutManager)null);
        this.textUrunFiyat = new JTextField(20);
        this.textUrunFiyat.setBounds(210, 150, 250, 30);
        this.textUrunFiyat.setLayout((LayoutManager)null);
        KeyListener keyTyped = new TextFieldKeyListener();
        this.textUrunFiyat.addKeyListener(keyTyped);
        this.kaydet = new JButton("KAYDET");
        this.kaydet.setBounds(240, 190, 150, 30);
        this.kaydet.setLayout((LayoutManager)null);
        this.kaydet.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ParaFormati pf = new ParaFormati();
                if (!UrunEkle.this.textUrunAdi.getText().isEmpty() && !UrunEkle.this.textUrunFiyat.getText().isEmpty()) {
                    int id = UrunEkle.this.db.UrunGetId(UrunEkle.this.textUrunAdi.getText());
                    if (id != 0) {
                        JOptionPane.showMessageDialog(UrunEkle.this.urunEkle, "ÜRÜN ZATEN KAYITLI");
                    } else {
                        UrunBean list = new UrunBean();
                        list.setUrunKategoriId(UrunEkle.this.db.UrunKategoriId(UrunEkle.this.combo.getSelectedItem().toString()));
                        list.setName(UrunEkle.this.textUrunAdi.getText());
                        list.setFiyat(new BigDecimal(pf.GeriCevir(UrunEkle.this.textUrunFiyat.getText().toString())));
                        dbServlet var10000 = UrunEkle.this.db;
                        dbServlet.UrunEkle(list);
                        JOptionPane.showMessageDialog(UrunEkle.this.urunEkle, "KAYDEDİLDİ...");
                    }
                } else {
                    JOptionPane.showMessageDialog(UrunEkle.this.urunEkle, "BOŞ ALAN BIRAKMAYINIZ");
                }

            }
        });
        this.urunEkle.add(this.kategoriAdi);
        this.urunEkle.add(this.combo);
        this.urunEkle.add(this.labelUrun);
        this.urunEkle.add(this.textUrunAdi);
        this.urunEkle.add(this.labelUrunFiyati);
        this.urunEkle.add(this.textUrunFiyat);
        this.urunEkle.add(this.kaydet);
        this.add(this.urunEkle);
    }

    public static void main(String[] args) {
        UrunEkle ue = new UrunEkle();
        ue.Urun_Ekle();
    }
}
