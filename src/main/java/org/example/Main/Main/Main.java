package org.example.Main.Main;

import org.example.Main.database.dbServlet;
import org.example.Main.sale.Satis;
import org.example.Main.settings.UrunDuzenle;
import org.example.Main.settings.UrunEkle;
import org.example.Main.settings.UrunKategoriDuzenle;
import org.example.Main.settings.UrunKategoriEkle;
import org.example.Main.settings.Adisyon;
import org.example.Main.settings.SifreDegis;
import org.example.Main.settings.Raporlar;
import org.example.Main.settings.Masa;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static JDesktopPane desktop;
    public JMenuBar menubar;
    public JMenu Ana_Sayfa;
    public JMenu Urunler;
    public JMenu Masa;
    public JMenu Raporlar;
    public JMenu Adisyon;
    public JMenu Giris;
    public JMenu SifreDegis;
    public JMenuItem urun_ekle;
    public JMenuItem urun_duzenle;
    public JMenuItem urun_katekle;
    public JMenuItem urun_katduzenle;
    public JFrame mp;
    public dbServlet db;

    public Main() {
    }

    public void menuOlustur() {
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy-EEEE ");
        Date d = new Date();
        String date = df.format(d);
        this.mp = new JFrame("TATLI HAYATLAR ADİSYON PROGRAMI      " + date);
        ImageIcon icon = new ImageIcon("src\\main\\java\\org\\example\\Main\\Main\\tatli_hayat.png");
        Image image = icon.getImage();
        this.mp.setIconImage(image);
        this.mp.setExtendedState(6);
        this.mp.setDefaultCloseOperation(3);
        desktop = new JDesktopPane();
        desktop.setLayout(new BorderLayout());
        this.menubar = new JMenuBar();
        this.menubar.setPreferredSize(new Dimension(0, 50));
        this.Ana_Sayfa = new JMenu("  Satış Gir");
        this.Ana_Sayfa.setBackground(Color.WHITE);
        this.Ana_Sayfa.setPreferredSize(new Dimension(100, 50));
        this.Urunler = new JMenu("   Ürünler");
        this.Urunler.setBackground(Color.BLUE);
        this.Urunler.setPreferredSize(new Dimension(100, 50));
        this.Urunler.setVisible(false);
        this.Masa = new JMenu("   Masa");
        this.Masa.setPreferredSize(new Dimension(100, 50));
        this.Masa.setVisible(false);
        this.Raporlar = new JMenu("   Raporlar");
        this.Raporlar.setPreferredSize(new Dimension(100, 50));
        this.Raporlar.setVisible(false);
        this.Adisyon = new JMenu("   Adisyon");
        this.Adisyon.setPreferredSize(new Dimension(100, 50));
        this.Adisyon.setVisible(false);
        this.SifreDegis = new JMenu("   Şifre Değiştir");
        this.SifreDegis.setPreferredSize(new Dimension(100, 50));
        this.SifreDegis.setVisible(false);
        this.Giris = new JMenu("        GİRİŞ");
        this.Giris.setPreferredSize(new Dimension(100, 50));
        this.Giris.setVisible(true);
        this.menubar.add(this.Ana_Sayfa);
        MenuListener anaSayfaListener = new anaSayfaListener();
        this.Ana_Sayfa.addMenuListener(anaSayfaListener);
        this.menubar.add(this.Urunler);
        this.menubar.add(this.Masa);
        MenuListener MasaListener = new MasaListener();
        this.Masa.addMenuListener(MasaListener);
        this.menubar.add(this.Raporlar);
        MenuListener RaporlarListener = new RaporlarListener();
        this.Raporlar.addMenuListener(RaporlarListener);
        this.menubar.add(this.Adisyon);
        MenuListener AdisyonListener = new AdisyonListener();
        this.Adisyon.addMenuListener(AdisyonListener);
        this.menubar.add(this.SifreDegis);
        MenuListener SifreDegisListener = new SifreDegisListener();
        this.SifreDegis.addMenuListener(SifreDegisListener);
        this.menubar.add(Box.createHorizontalGlue());
        MenuListener GirisListener = new GirisListener();
        this.Giris.addMenuListener(GirisListener);
        this.menubar.add(this.Giris);
        this.urun_ekle = new JMenuItem("Ürün Ekle");
        this.Urunler.add(this.urun_ekle);
        ActionListener urunListener = new UrunListener();
        this.urun_ekle.addActionListener(urunListener);
        this.urun_ekle.setPreferredSize(new Dimension(200, 50));
        this.urun_duzenle = new JMenuItem("Ürün Düzenle");
        this.urun_duzenle.setPreferredSize(new Dimension(200, 50));
        this.Urunler.add(this.urun_duzenle);
        ActionListener urunDuzenleListener = new urunDuzenleListener();
        this.urun_duzenle.addActionListener(urunDuzenleListener);
        this.urun_katekle = new JMenuItem("Ürün Kategori Ekle");
        this.urun_katekle.setPreferredSize(new Dimension(200, 50));
        this.Urunler.add(this.urun_katekle);
        ActionListener kategoriListener = new kategoriListener();
        this.urun_katekle.addActionListener(kategoriListener);
        this.urun_katduzenle = new JMenuItem("Ürün Kategori Düzenle");
        this.urun_katduzenle.setPreferredSize(new Dimension(200, 50));
        this.Urunler.add(this.urun_katduzenle);
        ActionListener kategoriDuzenleListener = new kategoriDuzenleListener();
        this.urun_katduzenle.addActionListener(kategoriDuzenleListener);

        try {
            desktop.removeAll();
            Satis s = new Satis();
            desktop.add(s);
            s.setVisible(true);
        } catch (Exception var16) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, (String)null, var16);
        }

        this.mp.setJMenuBar(this.menubar);
        this.mp.add(desktop);
        this.mp.setVisible(true);
    }

    public static void main(String[] args) {
        Main m = new Main();
        m.menuOlustur();
    }

    class kategoriDuzenleListener implements ActionListener {
        kategoriDuzenleListener() {
        }

        public void actionPerformed(ActionEvent event) {
            try {
                Main.desktop.removeAll();
                UrunKategoriDuzenle m = new UrunKategoriDuzenle();
                Main.desktop.add(m);
                m.pack();
                m.setVisible(true);
            } catch (Exception var4) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, (String)null, var4);
            }

        }
    }

    class kategoriListener implements ActionListener {
        kategoriListener() {
        }

        public void actionPerformed(ActionEvent event) {
            try {
                Main.desktop.removeAll();
                UrunKategoriEkle m = new UrunKategoriEkle();
                Main.desktop.add(m);
                m.pack();
                m.setVisible(true);
            } catch (Exception var4) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, (String)null, var4);
            }

        }
    }

    class urunDuzenleListener implements ActionListener {
        urunDuzenleListener() {
        }

        public void actionPerformed(ActionEvent event) {
            try {
                Main.desktop.removeAll();
                UrunDuzenle m = new UrunDuzenle();
                Main.desktop.add(m);
                m.pack();
                m.setVisible(true);
            } catch (Exception var4) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, (String)null, var4);
            }

        }
    }

    class UrunListener implements ActionListener {
        UrunListener() {
        }

        public void actionPerformed(ActionEvent event) {
            try {
                Main.desktop.removeAll();
                UrunEkle m = new UrunEkle();
                Main.desktop.add(m);
                m.pack();
                m.setVisible(true);
            } catch (Exception var4) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, (String)null, var4);
            }

        }
    }

    class AdisyonListener implements MenuListener {
        AdisyonListener() {
        }

        public void menuSelected(MenuEvent e) {
            try {
                Main.desktop.removeAll();
                Adisyon a = new Adisyon();
                Main.desktop.add(a);
                a.pack();
                a.setVisible(true);
            } catch (Exception var4) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, (String)null, var4);
            }

        }

        public void menuDeselected(MenuEvent e) {
        }

        public void menuCanceled(MenuEvent e) {
        }
    }

    class GirisListener implements MenuListener {
        GirisListener() {
        }

        public void menuSelected(MenuEvent e) {
            Main.this.db = new dbServlet();

            try {
                if (Main.this.Giris.getText().toString().equals("        GİRİŞ")) {
                    JLabel jPassword = new JLabel("Şifre");
                    JTextField password = new JPasswordField();
                    Object[] ob = new Object[]{jPassword, password};
                    int result = JOptionPane.showConfirmDialog((Component)null, ob, "Lütfen Şifrenizi Giriniz", 2);
                    String sifre = password.getText();
                    if (result == 0 && sifre.equals(Main.this.db.Sifre())) {
                        Main.this.Urunler.setVisible(true);
                        Main.this.Adisyon.setVisible(true);
                        Main.this.Masa.setVisible(true);
                        Main.this.Raporlar.setVisible(true);
                        Main.this.SifreDegis.setVisible(true);
                        Main.this.Giris.setText("        ÇIKIŞ");
                    } else {
                        System.out.println("şifre yanlış");
                    }
                } else {
                    int b = JOptionPane.showConfirmDialog(Main.desktop, "ÇIKIŞ YAPMAK ETMEK İSTEDİĞİNİZDEN EMİM MİSİNİZ?", "ÇIKIŞ", 0);
                    if (b == 0) {
                        Main.this.Urunler.setVisible(false);
                        Main.this.Adisyon.setVisible(false);
                        Main.this.Masa.setVisible(false);
                        Main.this.Raporlar.setVisible(false);
                        Main.this.SifreDegis.setVisible(false);
                        Main.desktop.removeAll();
                        Satis s = new Satis();
                        Main.desktop.add(s);
                        s.setVisible(true);
                        Main.this.Giris.setText("        GİRİŞ");
                    }
                }
            } catch (Exception var8) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, (String)null, var8);
            }

        }

        public void menuDeselected(MenuEvent e) {
        }

        public void menuCanceled(MenuEvent e) {
        }
    }

    class SifreDegisListener implements MenuListener {
        SifreDegisListener() {
        }

        public void menuSelected(MenuEvent e) {
            SifreDegis sd = new SifreDegis();
            sd.Panel();
        }

        public void menuDeselected(MenuEvent e) {
        }

        public void menuCanceled(MenuEvent e) {
        }
    }

    class RaporlarListener implements MenuListener {
        RaporlarListener() {
        }

        public void menuSelected(MenuEvent e) {
            try {
                Main.desktop.removeAll();
                Raporlar r = new Raporlar();
                Main.desktop.add(r);
                r.pack();
                r.setVisible(true);
            } catch (Exception var4) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, (String)null, var4);
            }

        }

        public void menuDeselected(MenuEvent e) {
        }

        public void menuCanceled(MenuEvent e) {
        }
    }

    class MasaListener implements MenuListener {
        MasaListener() {
        }

        public void menuSelected(MenuEvent e) {
            try {
                Main.desktop.removeAll();
                Masa m = new Masa();
                Main.desktop.add(m);
                m.pack();
                m.setVisible(true);
            } catch (Exception var4) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, (String)null, var4);
            }

        }

        public void menuDeselected(MenuEvent e) {
        }

        public void menuCanceled(MenuEvent e) {
        }
    }

    class anaSayfaListener implements MenuListener {
        anaSayfaListener() {
        }

        public void menuSelected(MenuEvent e) {
            try {
                Main.desktop.removeAll();
                Satis s = new Satis();
                Main.desktop.add(s);
                s.pack();
                s.setVisible(true);
            } catch (Exception var4) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, (String)null, var4);
            }

        }

        public void menuDeselected(MenuEvent e) {
        }

        public void menuCanceled(MenuEvent e) {
        }
    }
}