package org.example.Main.sale;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SatisBean {
    private int satis_id;
    private String tarih;
    private String kaptarih;
    private int iptal;
    private int masa_no;
    private int satis_ayrinti_id;
    private String urun;
    private int adet;
    private int status;
    private BigDecimal fiyat;
    private BigDecimal tfiyat;
    private BigDecimal indirim;
    private BigDecimal araOdeme;
    private int urun_id;
    private int urun_kategori_id;
    private String urun_kategori;
    private String mekan;
    private int adisyon;
    private String musteri;

    public SatisBean() {
    }

    public int getSatisId() {
        return this.satis_id;
    }

    public void setSatisId(int satis_id) {
        this.satis_id = satis_id;
    }

    public String getTarih() {
        return this.tarih;
    }

    public void setTarih(String tarih) {
        this.tarih = tarih;
    }

    public String getkapTarih() {
        return this.kaptarih;
    }

    public void setkapTarih(String kaptarih) {
        this.kaptarih = kaptarih;
    }

    public String getMekan() {
        return this.mekan;
    }

    public void setMekan(String mekan) {
        this.mekan = mekan;
    }

    public int getIptal() {
        return this.iptal;
    }

    public void setIptal(int iptal) {
        this.iptal = iptal;
    }

    public int getMasaNo() {
        return this.masa_no;
    }

    public void setMasaNo(int masa_no) {
        this.masa_no = masa_no;
    }

    public int getSatisAyrintiId() {
        return this.satis_ayrinti_id;
    }

    public void setSatisAyrintiId(int satis_ayrinti_id) {
        this.satis_ayrinti_id = satis_ayrinti_id;
    }

    public String getUrun() {
        return this.urun;
    }

    public void setUrun(String urun) {
        this.urun = urun;
    }

    public int getAdet() {
        return this.adet;
    }

    public void setAdet(int adet) {
        this.adet = adet;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public BigDecimal getToplamFiyat() {
        return this.tfiyat;
    }

    public void setToplamFiyat(BigDecimal tfiyat) {
        this.tfiyat = tfiyat;
    }

    public BigDecimal getFiyat() {
        return this.fiyat;
    }

    public void setFiyat(BigDecimal fiyat) {
        this.fiyat = fiyat;
    }

    public BigDecimal getIndirim() {
        return this.indirim;
    }

    public void setIndirim(BigDecimal indirim) {
        this.indirim = indirim;
    }

    public int getUrunId() {
        return this.urun_id;
    }

    public void setUrunId(int urun_id) {
        this.urun_id = urun_id;
    }

    public int getUrunKategoriId() {
        return this.urun_kategori_id;
    }

    public void setUrunKategoriId(int urun_kategori_id) {
        this.urun_kategori_id = urun_kategori_id;
    }

    public String getUrunKategori() {
        return this.urun_kategori;
    }

    public void setUrunKategori(String urun_kategori) {
        this.urun_kategori = urun_kategori;
    }

    public void setAdisyon(int adisyon) {
        this.adisyon = adisyon;
    }

    public int getAdisyon() {
        return this.adisyon;
    }

    public BigDecimal getAraOdeme() {
        return this.tfiyat;
    }

    public void setAraOdeme(BigDecimal tfiyat) {
        this.tfiyat = tfiyat;
    }

    public String getMusteri() {
        return this.musteri;
    }

    public void setMusteri(String musteri) {
        this.musteri = musteri;
    }

    public Object[] getObjects() {
        Object[] listVeriler = new Object[]{this.satis_id, this.tarih, this.kaptarih, this.mekan, this.iptal, this.masa_no, this.satis_ayrinti_id, this.urun, this.adet, this.status, this.tfiyat, this.fiyat, this.indirim, this.urun_id, this.urun_kategori_id, this.urun_kategori, this.adisyon, this.araOdeme, this.musteri};
        return listVeriler;
    }
}
