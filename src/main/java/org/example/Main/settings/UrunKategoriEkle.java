package org.example.Main.settings;

import org.example.Main.database.dbServlet;
import org.example.Main.util.TextFieldCapitalListener;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;

public class UrunKategoriEkle extends JInternalFrame {
    JLabel kategoriAdi;
    JTextField textUrunAdi;
    JButton kaydet;
    dbServlet db;
    JPanel urunEkle;

    public UrunKategoriEkle() {
        this.closable = true;
        this.KategoriEkle();
    }

    public void KategoriEkle() {
        this.db = new dbServlet();
        this.urunEkle = new JPanel((LayoutManager)null);
        this.urunEkle.setBounds(400, 200, 600, 400);
        this.urunEkle.setBorder(new TitledBorder(new LineBorder(Color.BLUE, 1), "Ürün Kategorisi Ekle"));
        this.kategoriAdi = new JLabel("ÜRÜN KATEGORİSİ:", 4);
        this.kategoriAdi.setBounds(50, 110, 150, 30);
        this.kategoriAdi.setLayout((LayoutManager)null);
        this.textUrunAdi = new JTextField(20);
        this.textUrunAdi.setBounds(210, 110, 250, 30);
        this.textUrunAdi.setLayout((LayoutManager)null);
        KeyListener bkeyTyped = new TextFieldCapitalListener();
        this.textUrunAdi.addKeyListener(bkeyTyped);
        this.kaydet = new JButton("KAYDET");
        this.kaydet.setBounds(240, 150, 150, 30);
        this.kaydet.setLayout((LayoutManager)null);
        this.kaydet.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (UrunKategoriEkle.this.textUrunAdi.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(UrunKategoriEkle.this.urunEkle, "BOŞ ALAN BIRAKMAYINIZ");
                } else {
                    int id = UrunKategoriEkle.this.db.UrunKategoriId(UrunKategoriEkle.this.textUrunAdi.getText());
                    if (id != 0) {
                        JOptionPane.showMessageDialog(UrunKategoriEkle.this.urunEkle, "KATEGORİ ZATEN KAYITLI");
                    } else {
                        UrunBean list = new UrunBean();
                        list.setUrunKategori(UrunKategoriEkle.this.textUrunAdi.getText());
                        System.out.println(UrunKategoriEkle.this.textUrunAdi.getText());
                        UrunKategoriEkle.this.db.UrunKategoriEkle(list);
                        JOptionPane.showMessageDialog(UrunKategoriEkle.this.urunEkle, "KAYDEDİLDİ...");
                    }
                }

            }
        });
        this.urunEkle.add(this.kategoriAdi);
        this.urunEkle.add(this.textUrunAdi);
        this.urunEkle.add(this.kaydet);
        this.add(this.urunEkle);
    }

    public static void main(String[] args) {
        UrunKategoriEkle ue = new UrunKategoriEkle();
        ue.KategoriEkle();
    }
}
