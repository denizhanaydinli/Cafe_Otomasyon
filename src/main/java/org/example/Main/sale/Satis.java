package org.example.Main.sale;

import org.example.Main.Main.Main;
import org.example.Main.database.dbServlet;
import org.example.Main.util.AdisyonYazdir;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Satis extends JInternalFrame {
    String digit;
    dbServlet db = new dbServlet();
    JPanel panelim;
    JPanel masaPanel;
    JButton MasaAc;
    JButton AdisyonAl;
    JButton MasaIptal;
    JButton MasaTasi;
    JButton AraOdeme;
    JButton MusteriEkle;
    SatisBean list;
    JPanel genelPanel;
    JPanel buttonPanel;
    JPanel labelPanel;
    JPanel aciklama;
    List<SatisBean> liste;
    List<SatisBean> masaSayisi;
    List<SatisBean> mekanSayisi;
    ActionListener numberListener;

    public Satis() throws Exception {
        this.closable = true;
        this.CreatedSale();
    }

    public void CreatedSale() throws Exception {
        this.liste = new ArrayList();
        this.liste = this.db.MasaDurumu();
        this.numberListener = new NumberListener();
        this.labelPanel = new JPanel();
        JLabel jt = new JLabel("MASALAR");
        jt.setHorizontalAlignment(0);
        this.labelPanel.add(jt);
        this.buttonPanel = new JPanel();
        this.buttonPanel.setLayout(new GridLayout(8, 2, 10, 10));
        this.masaPanel = new JPanel();
        this.masaPanel.setLayout(new GridLayout(5, 8, 10, 10));

        for(int i = 0; i < this.liste.size(); ++i) {
            JButton button = new JButton();
            if (((SatisBean)this.liste.get(i)).getMusteri() != null) {
                button.setText(i + 1 + "   " + ((SatisBean)this.liste.get(i)).getMusteri().toString());
            } else {
                button.setText(String.valueOf(i + 1));
            }

            if (((SatisBean)this.liste.get(i)).getStatus() == 0) {
                button.setBackground(Color.RED);
            } else {
                button.setBackground(Color.WHITE);
            }

            button.addActionListener(this.numberListener);
            this.masaPanel.add(button);
        }

        this.MasaAc = new JButton("SİPARİŞ GİR ");
        this.MasaAc.setLayout((LayoutManager)null);
        this.MasaAc.setBounds(10, 70, 130, 50);
        this.MasaAc.setBackground(Color.WHITE);
        this.MasaAc.setVisible(false);
        ActionListener MasaAcListener = new MasaAcListener();
        this.MasaAc.addActionListener(MasaAcListener);
        this.AdisyonAl = new JButton("ADİSYON AL ");
        this.AdisyonAl.setLayout((LayoutManager)null);
        this.AdisyonAl.setBounds(10, 130, 130, 50);
        this.AdisyonAl.setBackground(Color.WHITE);
        this.AdisyonAl.setVisible(false);
        ActionListener sonlandirListener = new sonlandirListener();
        this.AdisyonAl.addActionListener(sonlandirListener);
        this.MasaIptal = new JButton("SATIŞI İPTAL ET ");
        this.MasaIptal.setLayout((LayoutManager)null);
        this.MasaIptal.setBounds(10, 190, 130, 50);
        this.MasaIptal.setBackground(Color.WHITE);
        this.MasaIptal.setVisible(false);
        ActionListener silListener = new silListener();
        this.MasaIptal.addActionListener(silListener);
        this.MasaTasi = new JButton("MASAYI TAŞI ");
        this.MasaTasi.setLayout((LayoutManager)null);
        this.MasaTasi.setBounds(10, 250, 130, 50);
        this.MasaTasi.setBackground(Color.WHITE);
        this.MasaTasi.setVisible(false);
        ActionListener MasaTasiListener = new MasaTasiListener();
        this.MasaTasi.addActionListener(MasaTasiListener);
        this.MusteriEkle = new JButton("MÜŞTERİ EKLE ");
        this.MusteriEkle.setLayout((LayoutManager)null);
        this.MusteriEkle.setBounds(10, 370, 130, 50);
        this.MusteriEkle.setBackground(Color.WHITE);
        this.MusteriEkle.setVisible(false);
        ActionListener MusteriEkleListener = new MusteriEkleListener();
        this.MusteriEkle.addActionListener(MusteriEkleListener);
        this.buttonPanel.add(this.MasaAc);
        this.buttonPanel.add(this.AdisyonAl);
        this.buttonPanel.add(this.MasaIptal);
        this.buttonPanel.add(this.MasaTasi);
        this.buttonPanel.add(this.MusteriEkle);
        this.buttonPanel.setBorder(new TitledBorder(new LineBorder(Color.BLUE, 1), "Masa "));
        this.genelPanel = new JPanel();
        this.genelPanel.setLayout(new BorderLayout(40, 40));
        this.genelPanel.add(this.masaPanel, "Center");
        this.genelPanel.add(this.buttonPanel, "After");
        this.genelPanel.setBorder(new TitledBorder(new LineBorder(Color.BLUE, 1), "Masa "));
        JPanel sat1 = new JPanel();
        sat1.setBackground(Color.RED);
        JPanel sat2 = new JPanel();
        sat2.setBackground(Color.WHITE);
        JLabel jl = new JLabel("DOLU MASA", 2);
        JLabel jl2 = new JLabel("BOŞ  MASA", 2);
        JPanel east = new JPanel();
        east.setLayout(new GridLayout(2, 1, 10, 10));
        east.add(jl);
        east.add(jl2);
        JPanel west = new JPanel();
        west.setLayout(new GridLayout(2, 1, 20, 20));
        west.add(sat1, "North");
        west.add(sat2, "South");
        this.aciklama = new JPanel();
        this.aciklama.setLayout(new BorderLayout());
        this.aciklama.add(west, "West");
        this.aciklama.add(east, "Center");
        this.panelim = new JPanel();
        this.panelim.setLayout(new BorderLayout(4, 4));
        this.panelim.add(this.genelPanel, "Center");
        this.panelim.add(this.labelPanel, "North");
        this.panelim.add(this.aciklama, "South");
        this.add(this.panelim, "Center");
    }

    public static void main(String[] args) throws Exception {
        Satis s = new Satis();
        s.CreatedSale();
    }

    class MusteriEkleListener implements ActionListener {
        MusteriEkleListener() {
        }

        public void actionPerformed(ActionEvent event) {
            JTextField urun = new JTextField();
            Object[] fields = new Object[]{"MÜŞTERİ ", urun};
            int a = JOptionPane.showConfirmDialog((Component)null, fields, "GÜNCELLE", 2);
            if (a == 0) {
                Satis.this.db.MusteriEkle(Integer.parseInt(Satis.this.digit), urun.getText());

                try {
                    Satis s = new Satis();
                    Main main = new Main();
                    Main.desktop.removeAll();
                    Main.desktop.add(s);
                    s.pack();
                    s.setVisible(true);
                } catch (Exception var8) {
                    Logger.getLogger(Satis.class.getName()).log(Level.SEVERE, (String)null, var8);
                }
            }

        }
    }

    class MasaTasiListener implements ActionListener {
        MasaTasiListener() {
        }

        public void actionPerformed(ActionEvent event) {
            Satis.this.db = new dbServlet();
            if (Satis.this.db.MasaNoDurumu(Satis.this.digit) == 0) {
                MasaTasi mt = new MasaTasi();
                int a = 0;

                try {
                    a = mt.Tasi();
                } catch (Exception var8) {
                    Logger.getLogger(Satis.class.getName()).log(Level.SEVERE, (String)null, var8);
                }

                if (a != 0) {
                    Satis.this.db.MasaTasi(Integer.parseInt(Satis.this.digit), a);
                    Satis.this.db.MasaDurumGuncelleAdisyon(a);
                    Satis.this.db.MasaDurumGuncelleTasi(Integer.parseInt(Satis.this.digit));

                    try {
                        Satis s = new Satis();
                        Main main = new Main();
                        Main.desktop.removeAll();
                        Main.desktop.add(s);
                        s.pack();
                        s.setVisible(true);
                    } catch (Exception var7) {
                        Logger.getLogger(Satis.class.getName()).log(Level.SEVERE, (String)null, var7);
                    }
                }
            }

        }
    }

    class silListener implements ActionListener {
        silListener() {
        }

        public void actionPerformed(ActionEvent event) {
            int b = JOptionPane.showConfirmDialog(Satis.this.genelPanel, "SATIŞI İPTAL ETMEK İSTEDİĞİNİZDEN EMİM MİSİNİZ?", "SATIŞ İPTAL", 0);
            if (b == 0) {
                Satis.this.db = new dbServlet();
                int a = 1;
                Satis.this.list = new SatisBean();
                Satis.this.list.setMasaNo(Integer.parseInt(Satis.this.digit));
                Satis.this.db.MasaDurumGuncelle(Satis.this.list, a);
                Satis.this.db.SatisIptal(Satis.this.list);

                try {
                    Satis s = new Satis();
                    Main main = new Main();
                    Main.desktop.removeAll();
                    Main.desktop.add(s);
                    s.pack();
                    s.setVisible(true);
                } catch (Exception var7) {
                    Logger.getLogger(Satis.class.getName()).log(Level.SEVERE, (String)null, var7);
                }
            }

        }
    }

    class sonlandirListener implements ActionListener {
        sonlandirListener() {
        }

        public void actionPerformed(ActionEvent event) {
            Satis.this.db = new dbServlet();
            int a = 1;
            if (Satis.this.db.MasaNoDurumu(Satis.this.digit) == 0) {
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                Date d = new Date();
                String date = df.format(d);
                int adisyon = Satis.this.db.AdisyonNo() + 1;
                Satis.this.list = new SatisBean();
                Satis.this.list.setMasaNo(Integer.parseInt(Satis.this.digit));
                AdisyonYazdir ay = new AdisyonYazdir();
                Satis.this.list.setAdisyon(adisyon);
                Satis.this.db.SatisSonlandir(Satis.this.list);
                ay.Yazdir(adisyon, date);
                Satis.this.db.MasaDurumGuncelle(Satis.this.list, a);
            }

            try {
                Satis s = new Satis();
                Main main = new Main();
                Main.desktop.removeAll();
                Main.desktop.add(s);
                s.pack();
                s.setVisible(true);
            } catch (Exception var8) {
                Logger.getLogger(Satis.class.getName()).log(Level.SEVERE, (String)null, var8);
            }

        }
    }

    class MasaAcListener implements ActionListener {
        MasaAcListener() {
        }

        public void actionPerformed(ActionEvent event) {
            try {
                Main main = new Main();
                Main.desktop.removeAll();
                CreatedSatis m = new CreatedSatis(Integer.parseInt(Satis.this.digit));
                Main.desktop.add(m);
                m.pack();
                m.setVisible(true);
            } catch (Exception var5) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, (String)null, var5);
            }

        }
    }

    class NumberListener implements ActionListener {
        NumberListener() {
        }

        public void actionPerformed(ActionEvent event) {
            Satis.this.MasaAc.setVisible(true);
            Satis.this.AdisyonAl.setVisible(true);
            Satis.this.MasaIptal.setVisible(true);
            Satis.this.MasaTasi.setVisible(true);
            Satis.this.MusteriEkle.setVisible(true);
            Satis.this.digit = event.getActionCommand();

            try {
                Satis.this.digit = String.valueOf(NumberFormat.getInstance().parse(Satis.this.digit).intValue());
            } catch (ParseException var3) {
                Logger.getLogger(Satis.class.getName()).log(Level.SEVERE, (String)null, var3);
            }

            int masano = Integer.parseInt(Satis.this.digit) - 1;
            if (((SatisBean)Satis.this.liste.get(masano)).getStatus() == 1) {
                Satis.this.AdisyonAl.setEnabled(false);
                Satis.this.MasaIptal.setEnabled(false);
                Satis.this.MasaTasi.setEnabled(false);
                Satis.this.MusteriEkle.setEnabled(false);
            } else {
                Satis.this.AdisyonAl.setEnabled(true);
                Satis.this.MasaIptal.setEnabled(true);
                Satis.this.MasaTasi.setEnabled(true);
                Satis.this.MusteriEkle.setEnabled(true);
            }

            Satis.this.buttonPanel.setBorder(new TitledBorder(new LineBorder(Color.BLUE, 1), "Masa " + Satis.this.digit + ""));
        }
    }
}