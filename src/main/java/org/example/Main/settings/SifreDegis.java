package org.example.Main.settings;

import org.example.Main.database.dbServlet;

import javax.swing.*;
import java.awt.*;

public class SifreDegis {
    JTextField EskiSifre;
    JTextField YeniSifre;
    JTextField YeniSifreTekrar;
    int a;

    public SifreDegis() {
    }

    public void Panel() {
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new GridLayout(4, 2, 10, 10));
        JLabel EskiSifreLabel = new JLabel("Şifre :");
        this.EskiSifre = new JPasswordField();
        JLabel YeniSifreLabel = new JLabel(" Yeni Şifre :");
        this.YeniSifre = new JPasswordField();
        JLabel YeniSifreTekrarLabel = new JLabel("Yeni Şifre Tekrar :");
        this.YeniSifreTekrar = new JPasswordField();
        jPanel.add(EskiSifreLabel);
        jPanel.add(this.EskiSifre);
        jPanel.add(YeniSifreLabel);
        jPanel.add(this.YeniSifre);
        jPanel.add(YeniSifreTekrarLabel);
        jPanel.add(this.YeniSifreTekrar);
        Object[] fields = new Object[]{jPanel};
        this.a = JOptionPane.showConfirmDialog((Component)null, fields, "Şifre Değiş", 2);
        this.Degis();
    }

    public void Degis() {
        dbServlet db = new dbServlet();
        if (this.a == 0) {
            if (!this.EskiSifre.getText().equals("") && !this.YeniSifre.getText().equals("") && !this.YeniSifreTekrar.getText().equals("")) {
                String sifre = db.Sifre();
                if (sifre.equals(this.EskiSifre.getText())) {
                    if (this.YeniSifre.getText().equals(this.YeniSifreTekrar.getText())) {
                        db.SifreGuncelle(this.YeniSifre.getText());
                        JOptionPane.showMessageDialog((Component)null, "Şifreniz Güncellenmiştir..");
                    } else {
                        JOptionPane.showMessageDialog((Component)null, "Yeni Şifre ve Tekrarı Uyuşmuyor");
                        this.Panel();
                    }
                } else {
                    JOptionPane.showMessageDialog((Component)null, "Mevcut Şifrenizi Hatalı Girdiniz");
                    this.Panel();
                }
            } else {
                JOptionPane.showMessageDialog((Component)null, "Lütfen boş alan bırakmayınız");
                this.Panel();
            }
        }

    }
}
