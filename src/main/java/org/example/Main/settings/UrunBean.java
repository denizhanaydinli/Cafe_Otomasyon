package org.example.Main.settings;

import java.math.BigDecimal;

public class UrunBean {
    private String name;
    private BigDecimal fiyat;
    private int id;
    private String urun_kategori;
    private int UrunKategoriId;

    public UrunBean() {
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getFiyat() {
        return this.fiyat;
    }

    public void setFiyat(BigDecimal fiyat) {
        this.fiyat = fiyat;
    }

    public int getUrunKategoriId() {
        return this.UrunKategoriId;
    }

    public void setUrunKategoriId(int UrunKategoriId) {
        this.UrunKategoriId = UrunKategoriId;
    }

    public String getUrunKategori() {
        return this.urun_kategori;
    }

    public void setUrunKategori(String urun_kategori) {
        this.urun_kategori = urun_kategori;
    }

    public Object[] getObjects() {
        Object[] listVeriler = new Object[]{this.id, this.name, this.fiyat, this.urun_kategori, this.UrunKategoriId};
        return listVeriler;
    }
}
