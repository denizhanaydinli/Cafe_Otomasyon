package org.example.Main.util;

import org.example.Main.database.dbServlet;
import org.example.Main.sale.SatisBean;

import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.OrientationRequested;
import javax.swing.*;
import java.awt.*;
import java.awt.print.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AdisyonYazdir implements Printable {
    int adisyonNo;
    String tarih;

    public AdisyonYazdir() {
    }

    public void Yazdir(int adisyon, String date) {
        this.adisyonNo = adisyon;
        this.tarih = date;
        PageFormat format = new PageFormat();
        Paper paper = new Paper();
        double paperWidth = 3.0;
        double paperHeight = 3.69;
        double leftMargin = 0.12;
        double rightMargin = 0.1;
        double topMargin = 0.0;
        double bottomMargin = 0.01;
        paper.setSize(paperWidth * 200.0, paperHeight * 200.0);
        paper.setImageableArea(leftMargin * 200.0, topMargin * 200.0, (paperWidth - leftMargin - rightMargin) * 200.0, (paperHeight - topMargin - bottomMargin) * 200.0);
        format.setPaper(paper);
        PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();
        aset.add(OrientationRequested.PORTRAIT);
        PrinterJob printerJob = PrinterJob.getPrinterJob();
        Printable printable = new ReceiptPrint();
        format = printerJob.validatePage(format);
        printerJob.setPrintable(printable, format);

        try {
            printerJob.print(aset);
        } catch (Exception var21) {
            var21.printStackTrace();
        }

    }

    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public static void main(String[] args) {
        new AdisyonYazdir();
    }

    class ReceiptPrint implements Printable {
        SimpleDateFormat df = new SimpleDateFormat();
        public static final String pspace = "               ";

        ReceiptPrint() {
        }

        public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
            ParaFormati pf = new ParaFormati();
            this.df.applyPattern("dd/MM/yyyy HH:mm:ss");
            String strText = null;
            String LF = "\n";
            String SPACES = "               ";
            String dline = "--------------------------------------------";
            String greetings = "KAFE TATLI HAYATLAR";
            Graphics2D g2d = (Graphics2D)graphics;
            Font font = new Font("MONOSPACED", 1, 10);
            if (pageIndex >= 0 && pageIndex < 1) {
                new ArrayList();
                dbServlet db = new dbServlet();
                ArrayList<SatisBean> liste = (ArrayList)db.AdisyonYazdir(AdisyonYazdir.this.adisyonNo, AdisyonYazdir.this.tarih);
                JTextArea textarea = new JTextArea(10, 10);
                textarea.append("               " + greetings + "\n" + "\n");
                textarea.append("TARİH : " + this.df.format(new Date()) + "\n");
                textarea.append("MASA NO : " + ((SatisBean)liste.get(0)).getMasaNo() + "\n");
                textarea.append("ADİSYON NO : " + AdisyonYazdir.this.adisyonNo + "\n");
                textarea.append("--------------------------------------------\n");

                BigDecimal atutar;
                String araOdemeYaz;
                for(int i = 0; i < liste.size(); ++i) {
                    new BigDecimal(0.0);
                    atutar = ((SatisBean)liste.get(i)).getFiyat().multiply(new BigDecimal(((SatisBean)liste.get(i)).getAdet()));
                    araOdemeYaz = String.format("%-27s %-5s %8s\n", ((SatisBean)liste.get(i)).getUrun(), ((SatisBean)liste.get(i)).getAdet(), pf.ParayaCevir(atutar));
                    textarea.append(araOdemeYaz);
                }

                new BigDecimal(0.0);
                BigDecimal gtoplam = ((SatisBean)liste.get(0)).getToplamFiyat().subtract(((SatisBean)liste.get(0)).getIndirim());
                textarea.append("--------------------------------------------\n");
                String kalanOdemeYaz;
                String toplamYaz;
                if (Integer.parseInt(pf.GeriCevir(((SatisBean)liste.get(0)).getIndirim().toString())) != 0) {
                    toplamYaz = String.format("%-32s %8s\n", "TOPLAM", pf.ParayaCevir(((SatisBean)liste.get(0)).getToplamFiyat()) + " TL");
                    textarea.append(toplamYaz);
                    araOdemeYaz = String.format("%-32s %8s\n", "İNDİRİM", pf.ParayaCevir(((SatisBean)liste.get(0)).getIndirim()) + " TL");
                    textarea.append(araOdemeYaz);
                    kalanOdemeYaz = String.format("%-32s %8s\n", "GENEL TOPLAM", pf.ParayaCevir(((SatisBean)liste.get(0)).getToplamFiyat().subtract(((SatisBean)liste.get(0)).getIndirim())) + " TL");
                    textarea.append(kalanOdemeYaz);
                } else {
                    toplamYaz = String.format("%-32s %8s\n", "GENEL TOPLAM", pf.ParayaCevir(((SatisBean)liste.get(0)).getToplamFiyat()) + " TL");
                    textarea.append(toplamYaz);
                }

                atutar = db.GetSatisAraOdeme(((SatisBean)liste.get(0)).getSatisId());
                if (atutar.compareTo(BigDecimal.ZERO) != 0) {
                    araOdemeYaz = String.format("%-32s %8s\n", "ÖDEME", pf.ParayaCevir(atutar) + " TL");
                    textarea.append(araOdemeYaz);
                    kalanOdemeYaz = String.format("%-32s %8s\n", "KALAN TUTAR", pf.ParayaCevir(gtoplam.subtract(atutar)) + " TL");
                    textarea.append(kalanOdemeYaz);
                }

                textarea.setEditable(false);
                g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
                g2d.setFont(font);
                int lineNumber = 0;
                int lineCount = textarea.getLineCount();

                for(strText = textarea.getText(); lineCount != 0; --lineCount) {
                    try {
                        int lineStart = textarea.getLineStartOffset(lineNumber);
                        int lineEnd = textarea.getLineEndOffset(lineNumber);
                        strText = textarea.getText(lineStart, lineEnd - lineStart);
                    } catch (Exception var23) {
                        System.out.println("Printing error:" + var23);
                    }

                    g2d.drawString(strText, 1, (lineNumber + 1) * 18);//line arasındaki boşluğu açtı
                    ++lineNumber;
                }

                return 0;
            } else {
                return 1;
            }
        }
    }
}