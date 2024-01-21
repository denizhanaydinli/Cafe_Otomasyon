package org.example.Main.settings;

import org.example.Main.database.dbServlet;
import org.example.Main.sale.JButtonTableCellEditor;
import org.example.Main.sale.JButtonTableCellRenderer;
import org.example.Main.sale.SatisBean;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Masa extends JInternalFrame {
    private JButtonTableCellRenderer buttonRenderer;
    private JButtonTableCellEditor buttonEditor;
    SatisBean list;
    JButton jb;
    JTextField jt;
    JLabel jl;
    dbServlet db;
    JTable table;
    DefaultTableModel model;
    String delete = "SİL";

    public Masa() {
        this.closable = true;
        this.MasaEkle();
    }

    public void MasaEkle() {
        JPanel masaOlustur = new JPanel((LayoutManager)null);
        masaOlustur.setBounds(300, 30, 750, 700);
        masaOlustur.setBorder(new TitledBorder(new LineBorder(Color.BLUE, 1), "Masa Sayısı "));
        this.jl = new JLabel("Masa Sayısı :");
        this.jl.setBounds(50, 100, 80, 30);
        this.jl.setLayout((LayoutManager)null);
        this.jt = new JTextField();
        this.jt.setBounds(140, 100, 200, 30);
        this.jt.setLayout((LayoutManager)null);
        this.jb = new JButton("KAYDET");
        this.jb.setBounds(200, 140, 100, 30);
        this.jb.setLayout((LayoutManager)null);
        this.jb.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!Masa.this.jt.getText().equals("")) {
                    int durum = 0;

                    try {
                        durum = Masa.this.MasaDurumu();
                    } catch (Exception var4) {
                        Logger.getLogger(Masa.class.getName()).log(Level.SEVERE, (String)null, var4);
                    }

                    if (durum == 1) {
                        Masa.this.db = new dbServlet();
                        int masa = Integer.parseInt(Masa.this.jt.getText().toString());
                        Masa.this.db.MasaKayit(masa);
                        JOptionPane.showMessageDialog((Component)null, "MASALAR OLUŞTURULDU...");
                    } else {
                        JOptionPane.showMessageDialog((Component)null, "AÇIK MASA BULUNMAKTADIR.MASA SAYISINI GÜNCELLEMEK İÇİN ÖNCE AÇIK MASALARI KAPATMANIZ GEREKMEKTEDİR.");
                    }
                } else {
                    JOptionPane.showMessageDialog((Component)null, "LÜTFEN BİR DEĞER GİRİNİZ......");
                }

            }
        });
        masaOlustur.add(this.jl);
        masaOlustur.add(this.jt);
        masaOlustur.add(this.jb);
        this.add(masaOlustur);
    }

    public int MasaDurumu() throws Exception {
        int durum = 1;
        this.db = new dbServlet();
        Iterator i$ = this.db.MasaDurumu().iterator();

        do {
            if (!i$.hasNext()) {
                return durum;
            }

            SatisBean list = (SatisBean)i$.next();
            durum = list.getStatus();
        } while(durum != 0);

        return durum;
    }

    public static void main(String[] args) {
        Masa m = new Masa();
        m.MasaEkle();
    }
}
