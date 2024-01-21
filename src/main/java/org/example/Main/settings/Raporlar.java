package org.example.Main.settings;

import com.toedter.calendar.JDateChooser;
import org.example.Main.database.dbServlet;
import org.example.Main.sale.SatisBean;
import org.example.Main.util.AutoComboBox;
import org.example.Main.util.ParaFormati;
import org.example.Main.util.RaporlarExcelOlustur;

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

public class Raporlar extends JInternalFrame {
    dbServlet db = new dbServlet();
    int i = 0;
    JLabel basTarihLabel;
    JLabel bitTarihLabel;
    JLabel KategoriAdi;
    JLabel UrunAdi;
    JComboBox kategoriCombo;
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
    AutoComboBox autoComboBox;

    public Raporlar() throws Exception {
        this.closable = true;
        this.RaporOlustur();
    }

    public void RaporOlustur() {
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
        JPanel UrunPanel = new JPanel();
        UrunPanel.setLayout(new GridLayout(2, 2, 10, 10));
        this.KategoriAdi = new JLabel("Kategori :");
        String[] dizi1 = new String[this.db.UrunKategori().size() + 1];
        int i = 1;
        dizi1[0] = " ";

        for(Iterator i$ = this.db.UrunKategori().iterator(); i$.hasNext(); ++i) {
            UrunBean list = (UrunBean)i$.next();
            dizi1[i] = list.getUrunKategori();
        }

        this.kategoriCombo = new JComboBox(dizi1);
        ActionListener listener = new listener();
        this.kategoriCombo.addActionListener(listener);
        this.UrunAdi = new JLabel("Ürün :");
        String[] dizi = new String[this.db.Urunler().size() + 1];
        int j = 1;
        dizi[0] = " ";

        for(Iterator i$ = this.db.Urunler().iterator(); i$.hasNext(); ++j) {
            UrunBean list = (UrunBean)i$.next();
            dizi[j] = list.getName();
        }

        this.autoComboBox = new AutoComboBox(dizi);
        UrunPanel.add(this.KategoriAdi);
        UrunPanel.add(this.kategoriCombo);
        UrunPanel.add(this.UrunAdi);
        UrunPanel.add(this.autoComboBox);
        JPanel buttonPanel = new JPanel((LayoutManager)null);
        this.ara = new JButton("ARA");
        ActionListener araListener = new araListener();
        this.ara.addActionListener(araListener);
        this.ara.setLayout((LayoutManager)null);
        this.ara.setBounds(10, 10, 130, 48);
        buttonPanel.add(this.ara);
        JPanel durumPanel = new JPanel();
        aramaPanel.add(tarihPanel);
        aramaPanel.add(UrunPanel);
        aramaPanel.add(buttonPanel);
        aramaPanel.add(durumPanel);
        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BorderLayout());
        tablePanel.setBorder(new TitledBorder(new LineBorder(Color.BLUE, 1), "Arama Sonuçları"));
        this.table = new JTable() {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JScrollPane spane = new JScrollPane(this.table);
        String[] dizi2 = new String[]{"KATEGORİ", "ÜRÜN", "ADET", "FİYAT"};
        this.model = new DefaultTableModel(dizi2, 0);
        this.table.setModel(this.model);
        this.table.setRowHeight(25);
        tablePanel.add(spane);
        ListSelectionModel selectionModel = this.table.getSelectionModel();
        selectionModel.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                Raporlar.this.index = Raporlar.this.table.getSelectedRow();
            }
        });
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(10, 10));
        JPanel adisyonButtonPanel = new JPanel();
        adisyonButtonPanel.setLayout(new GridLayout(2, 1, 10, 10));
        adisyonButtonPanel.setBorder(new TitledBorder(new LineBorder(Color.BLUE, 1), "Rapor İşlemleri"));
        this.adisyonYazdir = new JButton(" ");
        ActionListener adisyonYazdirListener = new adisyonYazdirListener();
        this.adisyonYazdir.addActionListener(adisyonYazdirListener);
        this.adisyonYazdir.setLayout((LayoutManager)null);
        this.adisyonYazdir.setBounds(10, 68, 130, 48);
        this.excel = new JButton("EXCEL AL");
        ActionListener excelListener = new excelListener();
        this.excel.addActionListener(excelListener);
        this.excel.setLayout((LayoutManager)null);
        this.excel.setBounds(10, 126, 130, 48);
        adisyonButtonPanel.add(this.excel);
        JLabel label = new JLabel("Toplam Fiyat :......");
        label.setHorizontalAlignment(4);
        this.toplamFiyat.setForeground(Color.RED);
        this.toplamFiyat.setHorizontalAlignment(2);
        JPanel fiyatPanel = new JPanel();
        fiyatPanel.setLayout(new GridLayout(1, 2, 10, 10));
        fiyatPanel.add(label, "West");
        fiyatPanel.add(this.toplamFiyat, "East");
        panel.add(adisyonButtonPanel, "First");
        panel.add(fiyatPanel, "Last");
        this.add(aramaPanel, "First");
        this.add(tablePanel, "Center");
        this.add(panel, "After");
    }

    public void Tablo() {
        ParaFormati pf = new ParaFormati();
        BigDecimal tfiyat = new BigDecimal(0.0);
        Date date = this.basTarih.getDate();
        String baslangıcTarih = String.format("%1$tY-%1$tm-%1$td", date);
        Date date1 = this.bitTarih.getDate();
        String bitisTarih = String.format("%1$tY-%1$tm-%1$td", date1);
        int in = this.autoComboBox.getSelectedIndex();
        if (in == -1) {
            this.autoComboBox.setSelectedIndex(0);
        }

        String urun = this.autoComboBox.getSelectedItem().toString();
        String kategori = this.kategoriCombo.getSelectedItem().toString();
        if (!kategori.equals(" ")) {
            int id = this.db.UrunKategoriId(kategori);
            kategori = String.valueOf(id);
        }

        List<SatisBean> liste = this.db.UrunArama(baslangıcTarih, bitisTarih, kategori, urun);
        this.satisId = new int[liste.size()];
        Object[][] yliste = new Object[liste.size()][];
        int satir = 0;

        for(int i = 0; i < liste.size(); ++i) {
            String kategoriAd = this.db.UrunKategoriAdi(((SatisBean)liste.get(i)).getUrunKategoriId());
            BigDecimal fiyat = ((SatisBean)liste.get(i)).getFiyat().multiply(new BigDecimal(((SatisBean)liste.get(i)).getAdet()));
            yliste[i] = new Object[]{String.valueOf(kategoriAd), ((SatisBean)liste.get(i)).getUrun(), ((SatisBean)liste.get(i)).getAdet(), pf.ParayaCevir(fiyat)};
            if (i > 0 && yliste[i][1].equals(yliste[i - 1][1].toString())) {
                ++satir;
                int adet1 = Integer.parseInt(this.table.getValueAt(i - satir, 2).toString());
                int adet2 = Integer.parseInt(yliste[i][2].toString());
                int adet = adet1 + adet2;
                new BigDecimal(0.0);
                BigDecimal toplamfiyat = (new BigDecimal(pf.GeriCevir(this.table.getValueAt(i - satir, 3).toString()))).add(fiyat);
                this.table.setValueAt(adet, i - satir, 2);
                this.table.setValueAt(pf.ParayaCevir(toplamfiyat), i - satir, 3);
            } else {
                this.model.addRow(yliste[i]);
            }

            tfiyat = tfiyat.add(fiyat);
        }

        this.toplamFiyat.setText(pf.ParayaCevir(tfiyat));
    }

    class listener implements ActionListener {
        listener() {
        }

        public void actionPerformed(ActionEvent event) {
            Raporlar.this.autoComboBox.removeAllItems();
            Raporlar.this.autoComboBox.addItem(" ");
            if (Raporlar.this.kategoriCombo.getSelectedItem().toString() == " ") {
                Iterator i$x = Raporlar.this.db.Urunler().iterator();

                while(i$x.hasNext()) {
                    UrunBean list = (UrunBean)i$x.next();
                    Raporlar.this.autoComboBox.addItem(list.getName());
                }
            } else {
                int id = Raporlar.this.db.UrunKategoriId(Raporlar.this.kategoriCombo.getSelectedItem().toString());
                Iterator i$ = Raporlar.this.db.UrunAdi(id).iterator();

                while(i$.hasNext()) {
                    UrunBean listx = (UrunBean)i$.next();
                    Raporlar.this.autoComboBox.addItem(listx.getName());
                }
            }

        }
    }

    class excelListener implements ActionListener {
        excelListener() {
        }

        public void actionPerformed(ActionEvent event) {
            try {
                RaporlarExcelOlustur excelbas = new RaporlarExcelOlustur();
                excelbas.toExcel(Raporlar.this.table, Raporlar.this.toplamFiyat.getText());
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
        }
    }

    class araListener implements ActionListener {
        araListener() {
        }

        public void actionPerformed(ActionEvent event) {
            int rowCount = Raporlar.this.model.getRowCount();

            for(int i = rowCount - 1; i >= 0; --i) {
                Raporlar.this.model.removeRow(i);
            }

            Raporlar.this.Tablo();
        }
    }
}