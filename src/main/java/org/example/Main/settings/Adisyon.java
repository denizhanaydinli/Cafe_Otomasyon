package org.example.Main.settings;

import org.example.Main.database.dbServlet;
import com.toedter.calendar.JDateChooser;
import org.example.Main.sale.SatisBean;
import org.example.Main.util.AdisyonExcelOlustur;
import org.example.Main.util.AdisyonYazdir;
import org.example.Main.util.ParaFormati;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class Adisyon extends JInternalFrame {
    dbServlet db = new dbServlet();
    int i = 0;
    JLabel basTarihLabel;
    JLabel bitTarihLabel;
    JLabel adisyonAdi;
    JLabel UrunAdi;
    JLabel Durum;
    JLabel MasaNo;
    JComboBox kategoriCombo;
    JComboBox urunCombo;
    JComboBox durumCombo;
    JTextField masaField;
    JTextField adisyonField;
    JButton ara;
    JButton adisyonYazdir;
    JButton adisyonGeri;
    JButton adisyonDetay;
    JButton excel;
    JDateChooser basTarih;
    JDateChooser bitTarih;
    JTable table;
    DefaultTableModel model;
    int index = -1;
    int[] satisId;
    JLabel toplamFiyat = new JLabel();
    JLabel indirim = new JLabel();
    JLabel genelFiyat = new JLabel();

    public Adisyon() throws Exception {
        this.closable = true;
        this.AdisyonAra();
    }

    public void AdisyonAra() {
        JPanel aramaPanel = new JPanel((LayoutManager)null);
        aramaPanel.setLayout(new GridLayout(1, 4, 10, 10));
        aramaPanel.setBorder(new TitledBorder(new LineBorder(Color.BLUE, 1), "Arama"));
        JPanel tarihPanel = new JPanel();
        tarihPanel.setLayout(new GridLayout(2, 2, 10, 10));
        this.basTarihLabel = new JLabel("Başlangıç Tarihi :");
        this.basTarih = new JDateChooser();
        this.basTarih.setDateFormatString("dd/MM/yyyy");
        this.bitTarihLabel = new JLabel("Bitiş Tarihi :");
        this.bitTarih = new JDateChooser();
        this.bitTarih.setDateFormatString("dd/MM/yyyy");
        tarihPanel.add(this.basTarihLabel);
        tarihPanel.add(this.basTarih);
        tarihPanel.add(this.bitTarihLabel);
        tarihPanel.add(this.bitTarih);
        JPanel adisyonPanel = new JPanel();
        adisyonPanel.setLayout(new GridLayout(1, 2, 10, 10));
        this.adisyonAdi = new JLabel("Adisyon No :");
        this.adisyonField = new JTextField(50);
        adisyonPanel.add(this.adisyonAdi);
        adisyonPanel.add(this.adisyonField);
        JPanel durumPanel = new JPanel();
        durumPanel.setLayout(new GridLayout(2, 2, 10, 10));
        this.Durum = new JLabel("Durum");
        String[] dizi2 = new String[]{" ", "SATIŞ", "İPTAL"};
        this.durumCombo = new JComboBox(dizi2);
        this.MasaNo = new JLabel("Masa No :");
        this.masaField = new JTextField();
        durumPanel.add(this.Durum);
        durumPanel.add(this.durumCombo);
        durumPanel.add(this.MasaNo);
        durumPanel.add(this.masaField);
        JPanel buttonPanel = new JPanel((LayoutManager)null);
        this.ara = new JButton("ARA");
        ActionListener araListener = new araListener();
        this.ara.addActionListener(araListener);
        this.ara.setLayout((LayoutManager)null);
        this.ara.setBounds(10, 10, 130, 48);
        buttonPanel.add(this.ara);
        aramaPanel.add(tarihPanel);
        aramaPanel.add(adisyonPanel);
        aramaPanel.add(durumPanel);
        aramaPanel.add(buttonPanel);
        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BorderLayout());
        tablePanel.setBorder(new TitledBorder(new LineBorder(Color.BLUE, 1), "Arama Sonuçları"));
        this.table = new JTable() {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JScrollPane spane = new JScrollPane(this.table);
        String[] dizi = new String[]{"MASA NO", "ADİSYON NO", "MASA AÇILIŞ", "MASA KAPANIŞ", "FİYAT", "İNDİRİM", "GENEL FİYAT", "DURUM"};
        this.model = new DefaultTableModel(dizi, 0);
        this.table.setModel(this.model);
        this.table.setRowHeight(25);
        tablePanel.add(spane);
        ListSelectionModel selectionModel = this.table.getSelectionModel();
        selectionModel.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                Adisyon.this.index = Adisyon.this.table.getSelectedRow();
            }
        });
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(10, 10));
        JPanel adisyonButtonPanel = new JPanel();
        adisyonButtonPanel.setLayout(new GridLayout(4, 1, 10, 10));
        adisyonButtonPanel.setBorder(new TitledBorder(new LineBorder(Color.BLUE, 1), "Adisyon İşlemleri"));
        this.adisyonGeri = new JButton("ADİSYONU GERİ AL");
        ActionListener adisyonGeriListener = new adisyonGeriListener();
        this.adisyonGeri.addActionListener(adisyonGeriListener);
        this.adisyonGeri.setLayout((LayoutManager)null);
        this.adisyonGeri.setBounds(10, 10, 130, 48);
        this.adisyonYazdir = new JButton("ADİSYONU YAZDIR");
        ActionListener adisyonYazdirListener = new adisyonYazdirListener();
        this.adisyonYazdir.addActionListener(adisyonYazdirListener);
        this.adisyonYazdir.setLayout((LayoutManager)null);
        this.adisyonYazdir.setBounds(10, 68, 130, 48);
        this.adisyonDetay = new JButton("ADİSYON DETAY");
        ActionListener adisyonDetayListener = new adisyonDetayListener();
        this.adisyonDetay.addActionListener(adisyonDetayListener);
        this.adisyonDetay.setLayout((LayoutManager)null);
        this.adisyonDetay.setBounds(10, 126, 130, 48);
        this.excel = new JButton("EXCEL AL");
        ActionListener excelListener = new excelListener();
        this.excel.addActionListener(excelListener);
        this.excel.setLayout((LayoutManager)null);
        this.excel.setBounds(10, 126, 130, 48);
        adisyonButtonPanel.add(this.adisyonDetay);
        adisyonButtonPanel.add(this.adisyonYazdir);
        adisyonButtonPanel.add(this.adisyonGeri);
        adisyonButtonPanel.add(this.excel);
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
        JPanel fiyatPanel = new JPanel();
        fiyatPanel.setLayout(new GridLayout(3, 1, 10, 10));
        fiyatPanel.add(label, "West");
        fiyatPanel.add(this.toplamFiyat, "East");
        fiyatPanel.add(label1, "East");
        fiyatPanel.add(this.indirim, "West");
        fiyatPanel.add(label2, "East");
        fiyatPanel.add(this.genelFiyat, "West");
        panel.add(adisyonButtonPanel, "First");
        panel.add(fiyatPanel, "Last");
        this.add(aramaPanel, "First");
        this.add(tablePanel, "Center");
        this.add(panel, "After");
    }

    public void Tablo() {
        ParaFormati pf = new ParaFormati();
        BigDecimal tfiyat = new BigDecimal(0.0);
        BigDecimal gfiyat = new BigDecimal(0.0);
        BigDecimal iskonto = new BigDecimal(0.0);
        Date date = this.basTarih.getDate();
        String baslangıcTarih = String.format("%1$tY-%1$tm-%1$td", date);
        Date date1 = this.bitTarih.getDate();
        String bitisTarih = String.format("%1$tY-%1$tm-%1$td", date1);
        String adisyon1 = this.adisyonField.getText();
        String durum1 = this.durumCombo.getSelectedItem().toString();
        String masa_no = this.masaField.getText();
        List<SatisBean> liste = this.db.AdisyonArama(baslangıcTarih, bitisTarih, adisyon1, durum1, masa_no);
        this.satisId = new int[liste.size()];
        Object[][] yliste = new Object[liste.size()][];

        for(int i = 0; i < liste.size(); ++i) {
            this.satisId[i] = ((SatisBean)liste.get(i)).getSatisId();
            String iptal;
            if (((SatisBean)liste.get(i)).getIptal() == 0) {
                iptal = "SATIŞ";
            } else {
                iptal = "İPTAL";
            }

            String adisyon;
            if (((SatisBean)liste.get(i)).getAdisyon() == 0) {
                adisyon = " ";
            } else {
                adisyon = String.valueOf(((SatisBean)liste.get(i)).getAdisyon());
            }

            BigDecimal fiyat = ((SatisBean)liste.get(i)).getToplamFiyat().subtract(((SatisBean)liste.get(i)).getIndirim());
            yliste[i] = new Object[]{String.valueOf(((SatisBean)liste.get(i)).getMasaNo()), adisyon, ((SatisBean)liste.get(i)).getTarih(), ((SatisBean)liste.get(i)).getkapTarih(), pf.ParayaCevir(((SatisBean)liste.get(i)).getToplamFiyat()), pf.ParayaCevir(((SatisBean)liste.get(i)).getIndirim()), pf.ParayaCevir(fiyat), iptal};
            this.model.addRow(yliste[i]);
            tfiyat = tfiyat.add(((SatisBean)liste.get(i)).getToplamFiyat());
            iskonto = iskonto.add(((SatisBean)liste.get(i)).getIndirim());
            gfiyat = tfiyat.subtract(iskonto);
        }

        this.toplamFiyat.setText(pf.ParayaCevir(tfiyat));
        this.genelFiyat.setText(pf.ParayaCevir(gfiyat));
        this.indirim.setText(pf.ParayaCevir(iskonto));
    }

    class listener implements ActionListener {
        listener() {
        }

        public void actionPerformed(ActionEvent event) {
            int id = Adisyon.this.db.UrunKategoriId(Adisyon.this.kategoriCombo.getSelectedItem().toString());
            Adisyon.this.urunCombo.removeAllItems();
            Adisyon.this.urunCombo.addItem(" ");
            Iterator i$ = Adisyon.this.db.UrunAdi(id).iterator();

            while(i$.hasNext()) {
                UrunBean list = (UrunBean)i$.next();
                Adisyon.this.urunCombo.addItem(list.getName());
            }

        }
    }

    class adisyonDetayListener implements ActionListener {
        adisyonDetayListener() {
        }

        public void actionPerformed(ActionEvent event) {
            if (Adisyon.this.index != -1) {
                AdisyonDetay ad = new AdisyonDetay();
                ad.Adisyon(Adisyon.this.satisId[Adisyon.this.index]);
            }

        }
    }

    class excelListener implements ActionListener {
        excelListener() {
        }

        public void actionPerformed(ActionEvent event) {
            try {
                AdisyonExcelOlustur excelbas = new AdisyonExcelOlustur();
                excelbas.toExcel(Adisyon.this.table, Adisyon.this.toplamFiyat.getText(), Adisyon.this.indirim.getText(), Adisyon.this.genelFiyat.getText());
                JOptionPane.showMessageDialog((Component)null, "Dosya  kaydedildi.", "Tablo Oluşturuldu", 1);
            } catch (Exception var3) {
                var3.printStackTrace();
            }

        }
    }

    class adisyonYazdirListener implements ActionListener {
        adisyonYazdirListener() {
        }

        public void actionPerformed(ActionEvent event) {
            if (Adisyon.this.index != -1 && Adisyon.this.table.getValueAt(Adisyon.this.index, 7).toString() == "SATIŞ") {
                String adisyon = Adisyon.this.table.getValueAt(Adisyon.this.index, 1).toString();
                String tarih = Adisyon.this.table.getValueAt(Adisyon.this.index, 3).toString();
                AdisyonYazdir ad = new AdisyonYazdir();
                ad.Yazdir(Integer.parseInt(adisyon), tarih);
            }

        }
    }

    class adisyonGeriListener implements ActionListener {
        adisyonGeriListener() {
        }

        public void actionPerformed(ActionEvent event) {
            if (Adisyon.this.index != -1) {
                String masa = Adisyon.this.table.getValueAt(Adisyon.this.index, 0).toString();
                if (Adisyon.this.db.MasaNoDurumu(masa) == 0) {
                    JOptionPane.showMessageDialog(Adisyon.this.table, " MASA " + masa + " ' de açık adisyon bulunmaktadır. ");
                } else {
                    int a = JOptionPane.showConfirmDialog(Adisyon.this.table, "ADİSYONU GERİ ALMAK İSTEDİĞİNİZE EMİN MİSİNİZ?", "SEÇİM YAPINIZ", 0);
                    if (a == 0) {
                        System.out.println(Adisyon.this.index);
                        Adisyon.this.db.AdisyonGeriAl(Adisyon.this.satisId[Adisyon.this.index]);
                        Adisyon.this.db.MasaDurumGuncelleAdisyon(Integer.parseInt(masa));
                        Adisyon.this.model.removeRow(Adisyon.this.index);
                    }
                }
            }

        }
    }

    class araListener implements ActionListener {
        araListener() {
        }

        public void actionPerformed(ActionEvent event) {
            int rowCount = Adisyon.this.model.getRowCount();

            for(int i = rowCount - 1; i >= 0; --i) {
                Adisyon.this.model.removeRow(i);
            }

            Adisyon.this.Tablo();
        }
    }
}