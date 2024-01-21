package org.example.Main.database;

import org.example.Main.sale.SatisBean;
import org.example.Main.settings.UrunBean;

import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

public class dbServlet {
    static Connection con;
    static ResultSet rs;
    static PreparedStatement ps;
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date d = new Date();
    String date;

    public dbServlet() {
        this.date = this.df.format(this.d);
    }

    public String baglantiAc() throws Exception {
        Properties prop = new Properties();
        InputStream input = null;
        String link = "dfsdfsdf";
        String database = null;
        input = new FileInputStream("C:\\Users\\Denizhan\\IdeaProjects\\satiss\\src\\main\\java\\org\\example\\Main\\config\\config");

        prop.load(input);
        link = prop.getProperty("link");
        database = prop.getProperty("database");
        Class.forName(link);
        return database;
    }

    public List<SatisBean> MasaDurumu() throws Exception {
        List<SatisBean> liste = new ArrayList();

        try {
            con = DriverManager.getConnection(this.baglantiAc());
            String sorgu = "select durum,musteri from status ";
            ps = con.prepareStatement(sorgu);
            rs = ps.executeQuery();

            while(rs.next()) {
                SatisBean list = new SatisBean();
                list.setStatus(rs.getInt("durum"));
                list.setMusteri(rs.getString("musteri"));
                liste.add(list);
            }

            con.close();
        } catch (SQLException var4) {
            System.out.println(var4);
        }

        return liste;
    }

    public int MasaNoDurumu(String masa) {
        int i = 0;

        try {
            con = DriverManager.getConnection(this.baglantiAc());
            String sorgu = "select durum from status where masa_no = '" + masa + "'";
            ps = con.prepareStatement(sorgu);
            rs = ps.executeQuery();
            if (rs.next()) {
                i = rs.getInt("durum");
            }

            con.close();
        } catch (Exception var4) {
            System.out.println(var4);
        }

        return i;
    }

    public String Sifre() {
        String i = null;

        try {
            con = DriverManager.getConnection(this.baglantiAc());
            String sorgu = "select sifre from kullanici";
            ps = con.prepareStatement(sorgu);
            rs = ps.executeQuery();
            if (rs.next()) {
                i = rs.getString("sifre");
            }

            con.close();
        } catch (Exception var3) {
            System.out.println(var3);
        }

        return i;
    }

    public void SifreGuncelle(String sifre) {
        String i = null;

        try {
            con = DriverManager.getConnection(this.baglantiAc());
            String sorgu = "update kullanici set sifre = " + sifre + " ";
            con.setAutoCommit(false);
            ps = con.prepareStatement(sorgu);
            ps.executeUpdate();
            con.commit();
            con.close();
        } catch (Exception var4) {
            System.out.println(var4);
        }

    }

    public int AdisyonNo() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date d = new Date();
        String date = df.format(d);
        int i = 0;

        try {
            con = DriverManager.getConnection(this.baglantiAc());
            String sorgu = "select max(adisyon_no) from satis where kapanisTarih like '" + date + "%' ";
            ps = con.prepareStatement(sorgu);
            rs = ps.executeQuery();
            if (rs.next()) {
                i = rs.getInt(1);
            }

            con.close();
        } catch (Exception var6) {
            System.out.println(var6);
        }

        System.out.println(i);
        return i;
    }

    public int SatisAdisyonNo(int id) {
        int i = 0;

        try {
            con = DriverManager.getConnection(this.baglantiAc());
            String sorgu = "select adisyon_no from satis where id = '" + id + "' ";
            ps = con.prepareStatement(sorgu);
            rs = ps.executeQuery();
            if (rs.next()) {
                i = rs.getInt(1);
            }

            con.close();
        } catch (Exception var4) {
            System.out.println(var4);
        }

        System.out.println(i);
        return i;
    }

    public List<UrunBean> Urunler() {
        List<UrunBean> liste = new ArrayList();

        try {
            con = DriverManager.getConnection(this.baglantiAc());
            String sorgu = "select urun.id, urun.urun_kategori_id, urun.urun_adi , urun.fiyat from urun INNER JOIN urun_kategori ON urun.urun_kategori_id = urun_kategori.id where urun.status = 0 AND urun.guncel = 0 and urun_kategori.status = 0 ORDER BY urun.urun_kategori_id , urun.urun_adi";
            ps = con.prepareStatement(sorgu);
            rs = ps.executeQuery();

            while(rs.next()) {
                UrunBean list = new UrunBean();
                list.setId(rs.getInt("id"));
                list.setName(rs.getString("urun_adi"));
                list.setFiyat(new BigDecimal(rs.getDouble("fiyat")));
                list.setUrunKategoriId(rs.getInt("urun_kategori_id"));
                liste.add(list);
            }

            con.close();
        } catch (Exception var4) {
            System.out.println(var4);
        }

        return liste;
    }

    public List<UrunBean> UrunAdi(int id) {
        List<UrunBean> liste = new ArrayList();

        try {
            con = DriverManager.getConnection(this.baglantiAc());
            String sorgu = "select urun_adi from urun where urun_kategori_id ='" + id + "' and status = 0  and guncel = 0";
            ps = con.prepareStatement(sorgu);
            rs = ps.executeQuery();

            while(rs.next()) {
                UrunBean list = new UrunBean();
                list.setName(rs.getString("urun_adi"));
                liste.add(list);
            }

            con.close();
        } catch (Exception var5) {
            System.out.println(var5);
        }

        return liste;
    }

    public List<UrunBean> UrunKategori() {
        List<UrunBean> liste = new ArrayList();

        try {
            con = DriverManager.getConnection(this.baglantiAc());
            String sorgu = "select * from urun_kategori where status = 0";
            ps = con.prepareStatement(sorgu);
            rs = ps.executeQuery();

            while(rs.next()) {
                UrunBean list = new UrunBean();
                list.setUrunKategoriId(rs.getInt("id"));
                list.setUrunKategori(rs.getString("kategori_adi"));
                liste.add(list);
            }

            con.close();
        } catch (Exception var4) {
            System.out.println(var4);
        }

        return liste;
    }

    public List<SatisBean> SatilanUrun(int masa_no) {
        List<SatisBean> liste = new ArrayList();

        try {
            con = DriverManager.getConnection(this.baglantiAc());
            String sorgu = "SELECT satis.id,sum(satis_ayrinti.adet) as adet,urun.urun_adi,urun.fiyat,satis.indirim,satis.fiyat as toplam,satis.adisyon_no FROM satis_ayrinti  INNER JOIN satis  ON satis.id=satis_ayrinti.satis_id  INNER JOIN urun  ON satis_ayrinti.urun_id=urun.id where satis.masa_no = '" + masa_no + "' and satis.status = 0 and satis.iptal = 0  group by urun.urun_adi";
            ps = con.prepareStatement(sorgu);
            rs = ps.executeQuery();

            while(rs.next()) {
                SatisBean list = new SatisBean();
                list.setSatisId(rs.getInt("id"));
                list.setAdet(rs.getInt("adet"));
                list.setUrun(rs.getString("urun_adi"));
                list.setFiyat(new BigDecimal(rs.getDouble("fiyat")));
                list.setToplamFiyat(new BigDecimal(rs.getDouble("toplam")));
                list.setIndirim(new BigDecimal(rs.getDouble("indirim")));
                list.setAdisyon(rs.getInt("adisyon_no"));
                liste.add(list);
            }

            con.close();
        } catch (Exception var5) {
            System.out.println(var5);
        }

        return liste;
    }

    public BigDecimal UrunFiyat(String ad) {
        BigDecimal fiyat = null;

        try {
            con = DriverManager.getConnection(this.baglantiAc());
            String sorgu = "select * from urun where urun_adi='" + ad + "' and status = 0 and guncel = 0";
            ps = con.prepareStatement(sorgu);

            for(rs = ps.executeQuery(); rs.next(); fiyat = new BigDecimal(rs.getDouble("fiyat"))) {
            }

            con.close();
        } catch (Exception var4) {
            System.out.println(var4);
        }

        return fiyat;
    }

    public BigDecimal UrunAdisyonFiyat(int id) {
        BigDecimal fiyat = null;

        try {
            con = DriverManager.getConnection(this.baglantiAc());
            String sorgu = " select  fiyat  from urun where id = '" + id + "'  ";
            ps = con.prepareStatement(sorgu);
            rs = ps.executeQuery();
            if (rs.next()) {
                fiyat = new BigDecimal(rs.getDouble("fiyat"));
            }

            con.close();
        } catch (Exception var4) {
            System.out.println(var4);
        }

        return fiyat;
    }

    public void SatisGuncelle(SatisBean list) {
        Connection con = null;
        String sorgu2 = "delete from satis_ayrinti where satis_id='" + list.getSatisId() + "' ";
        String sorgu = "update satis set fiyat='" + list.getToplamFiyat().doubleValue() + "', indirim = '" + list.getIndirim().doubleValue() + "' where id='" + list.getSatisId() + "' ";

        try {
            con = DriverManager.getConnection(this.baglantiAc());
            con.setAutoCommit(false);
            PreparedStatement ps = con.prepareStatement(sorgu2);
            ps.executeUpdate();
            ps = con.prepareStatement(sorgu);
            ps.executeUpdate();
            con.commit();
            con.close();
        } catch (Exception var8) {
            System.out.println(var8);
        }

    }

    public void SatisKayit(SatisBean list) {
        Connection con = null;
        String sorgu = "INSERT INTO satis (tarih,masa_no,fiyat,indirim) VALUES (? , ? , ? , ?) ";

        try {
            con = DriverManager.getConnection(this.baglantiAc());
            con.setAutoCommit(false);
            PreparedStatement ps = con.prepareStatement(sorgu);
            ps.setString(1, this.date);
            ps.setInt(2, list.getMasaNo());
            ps.setDouble(3, list.getToplamFiyat().doubleValue());
            ps.setDouble(4, list.getIndirim().doubleValue());
            ps.addBatch();
            ps.executeBatch();
            con.commit();
            con.close();
        } catch (Exception var7) {
            System.out.println(var7);
        }

    }

    public void SatisSil(int satisId) {
        try {
            Connection con = DriverManager.getConnection(this.baglantiAc());
            String sorgu = "delete from satis where id='" + satisId + "' ";
            PreparedStatement ps = con.prepareStatement(sorgu);
            ps.executeUpdate();
            String sorgu2 = "delete from satis_ayrinti where satis_id='" + satisId + "' ";
            ps = con.prepareStatement(sorgu2);
            ps.executeUpdate();
            con.close();
        } catch (Exception var7) {
        }

    }

    public void SatisAyrintiKayit(List<SatisBean> liste) {
        int id = 0;

        try {
            Connection con = DriverManager.getConnection(this.baglantiAc());
            con.setAutoCommit(false);
            String sorgu = "SELECT id from satis where masa_no= '" + ((SatisBean)liste.get(0)).getMasaNo() + "' and status = 0 and iptal = 0";
            PreparedStatement ps = con.prepareStatement(sorgu);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                id = rs.getInt("id");
            }

            String sorgu2 = "INSERT INTO satis_ayrinti (satis_id,urun_id,adet,tarih) VALUES ( ?, ? , ? , ?) ";
            ps = con.prepareStatement(sorgu2);

            for(int i = 0; i < liste.size(); ++i) {
                ps.setInt(1, id);
                ps.setInt(2, ((SatisBean)liste.get(i)).getUrunId());
                ps.setInt(3, ((SatisBean)liste.get(i)).getAdet());
                ps.setString(4, this.date);
                ps.addBatch();
            }

            ps.executeBatch();
            con.commit();
            con.close();
        } catch (Exception var9) {
            System.out.println(var9);
        }

    }

    public int UrunGetId(String ad) {
        int id = 0;

        try {
            con = DriverManager.getConnection(this.baglantiAc());
            String sorgu = "select * from urun where urun_adi='" + ad + "'";
            ps = con.prepareStatement(sorgu);

            for(rs = ps.executeQuery(); rs.next(); id = rs.getInt("id")) {
            }

            con.close();
        } catch (Exception var4) {
            System.out.println(var4);
        }

        return id;
    }

    public int UrunKatId(String ad) {
        int id = 0;

        try {
            con = DriverManager.getConnection(this.baglantiAc());
            String sorgu = "select * from urun where urun_adi='" + ad + "'";
            ps = con.prepareStatement(sorgu);

            for(rs = ps.executeQuery(); rs.next(); id = rs.getInt("urun_kategori_id")) {
            }

            con.close();
        } catch (Exception var4) {
            System.out.println(var4);
        }

        return id;
    }

    public int UrunKategoriId(String ad) {
        int id = 0;

        try {
            con = DriverManager.getConnection(this.baglantiAc());
            String sorgu = "select id from urun_kategori where kategori_adi='" + ad + "'";
            ps = con.prepareStatement(sorgu);

            for(rs = ps.executeQuery(); rs.next(); id = rs.getInt("id")) {
            }

            con.close();
        } catch (Exception var4) {
            System.out.println(var4);
        }

        return id;
    }

    public void MasaDurumGuncelle(SatisBean list, int i) {
        try {
            con = DriverManager.getConnection(this.baglantiAc());
            con.setAutoCommit(false);
            String sorgu = " UPDATE status set durum = '" + i + "', musteri = null   where masa_no = '" + list.getMasaNo() + "' ";
            ps = con.prepareStatement(sorgu);
            ps.executeUpdate();
            con.commit();
            con.close();
        } catch (Exception var4) {
            System.out.println(var4);
        }

    }

    public void MasaDurumGuncelleAdisyon(int masa) {
        try {
            con = DriverManager.getConnection(this.baglantiAc());
            String sorgu = " UPDATE status set durum = 0, musteri = null where masa_no = '" + masa + "' ";
            ps = con.prepareStatement(sorgu);
            ps.executeUpdate();
            con.close();
        } catch (Exception var3) {
            System.out.println(var3);
        }

    }

    public void MasaDurumGuncelleTasi(int masa) {
        try {
            con = DriverManager.getConnection(this.baglantiAc());
            String sorgu = " UPDATE status set durum = 1 where masa_no = '" + masa + "' ";
            ps = con.prepareStatement(sorgu);
            ps.executeUpdate();
            con.close();
        } catch (Exception var3) {
            System.out.println(var3);
        }

    }

    public void MasaTasi(int ilkMasa, int masa) {
        try {
            con = DriverManager.getConnection(this.baglantiAc());
            String sorgu = " UPDATE satis set  masa_no = '" + masa + "' where  masa_no = '" + ilkMasa + "' and status = 0 and iptal = 0 ";
            ps = con.prepareStatement(sorgu);
            ps.executeUpdate();
            con.close();
        } catch (Exception var4) {
            System.out.println(var4);
        }

    }

    public void SatisSonlandir(SatisBean list) {
        new Date();
        new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        try {
            con = DriverManager.getConnection(this.baglantiAc());
            String sorgu = " UPDATE satis set status = 1, kapanisTarih = datetime('now' ,'localtime'), adisyon_no= '" + list.getAdisyon() + "' where masa_no = '" + list.getMasaNo() + "' AND status=0  ";
            ps = con.prepareStatement(sorgu);
            ps.executeUpdate();
            con.close();
        } catch (Exception var5) {
            System.out.println(var5);
        }

    }

    public void SatisIptal(SatisBean list) {
        new Date();
        new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        try {
            con = DriverManager.getConnection(this.baglantiAc());
            String sorgu = " UPDATE satis set iptal = 1, status = 1, kapanisTarih = datetime('now' ,'localtime') where masa_no = '" + list.getMasaNo() + "' AND status=0 ";
            ps = con.prepareStatement(sorgu);
            ps.executeUpdate();
            con.close();
        } catch (Exception var5) {
            System.out.println(var5);
        }

    }

    public static void UrunEkle(UrunBean kayit) {
        try {
            dbServlet db = new dbServlet();
            con = DriverManager.getConnection(db.baglantiAc());
            String sorgu = " INSERT INTO urun (urun_adi,fiyat,urun_kategori_id,tarih)  VALUES ('" + kayit.getName() + "','" + kayit.getFiyat().doubleValue() + "','" + kayit.getUrunKategoriId() + "', datetime('now','localtime'))";
            ps = con.prepareStatement(sorgu);
            ps.executeUpdate();
            con.close();
        } catch (Exception var3) {
            var3.printStackTrace();
        }

    }

    public void UrunKategoriEkle(UrunBean kayit) {
        try {
            con = DriverManager.getConnection(this.baglantiAc());
            String sorgu = " INSERT INTO urun_kategori  (kategori_adi)  VALUES ('" + kayit.getUrunKategori() + "')";
            ps = con.prepareStatement(sorgu);
            ps.executeUpdate();
            con.close();
        } catch (Exception var3) {
            var3.printStackTrace();
        }

    }

    public void MasaKayit(int masa) {
        try {
            con = DriverManager.getConnection(this.baglantiAc());
            con.setAutoCommit(false);
        } catch (Exception var5) {
            System.out.println(var5);
        }

        try {
            String sorgu = "delete from status ";
            ps = con.prepareStatement(sorgu);
            ps.executeUpdate();
            ps.close();
            String sorgu2 = "INSERT INTO status (masa_no) VALUES (?)";
            ps = con.prepareStatement(sorgu2);

            for(int i = 0; i < masa; ++i) {
                ps.setInt(1, i + 1);
                ps.addBatch();
            }

            ps.executeBatch();
            con.commit();
            con.close();
        } catch (Exception var6) {
            System.out.println(var6);
        }

    }

    public void UrunGuncelle(UrunBean list) {
        try {
            con = DriverManager.getConnection(this.baglantiAc());
            con.setAutoCommit(false);
            String sorgu2 = "UPDATE urun SET guncel = 1 WHERE urun_adi ='" + list.getName() + "'";
            ps = con.prepareStatement(sorgu2);
            ps.executeUpdate();
            String sorgu = " INSERT INTO urun (urun_adi,fiyat,urun_kategori_id,tarih) VALUES  ('" + list.getName() + "','" + list.getFiyat() + "','" + list.getUrunKategoriId() + "',datetime('now','localtime')) ";
            ps = con.prepareStatement(sorgu);
            ps.executeUpdate();
            con.commit();
            con.close();
        } catch (Exception var4) {
            System.out.println(var4);
        }

    }

    public void UrunKategoriGuncelle(UrunBean list) {
        try {
            con = DriverManager.getConnection(this.baglantiAc());
            String sorgu = " UPDATE urun_kategori set kategori_adi = '" + list.getUrunKategori() + "' where id = '" + list.getUrunKategoriId() + "' ";
            ps = con.prepareStatement(sorgu);
            ps.executeUpdate();
            con.close();
        } catch (Exception var3) {
            System.out.println(var3);
        }

    }

    public void UrunSil(String ad) {
        try {
            con = DriverManager.getConnection(this.baglantiAc());
            String sorgu = " UPDATE urun set status = 1 where urun_adi = '" + ad + "' ";
            ps = con.prepareStatement(sorgu);
            ps.executeUpdate();
            con.close();
        } catch (Exception var3) {
            System.out.println(var3);
        }

    }

    public void UrunKategoriSil(String ad) {
        try {
            con = DriverManager.getConnection(this.baglantiAc());
            String sorgu = " UPDATE urun_kategori set status = 1 where kategori_adi = '" + ad + "' ";
            ps = con.prepareStatement(sorgu);
            ps.executeUpdate();
            con.close();
        } catch (Exception var3) {
            System.out.println(var3);
        }

    }

    public void UrunAktiflestir(String ad) {
        try {
            con = DriverManager.getConnection(this.baglantiAc());
            String sorgu = " UPDATE urun set status = 0 where urun_adi = '" + ad + "' ";
            ps = con.prepareStatement(sorgu);
            ps.executeUpdate();
            con.close();
        } catch (Exception var3) {
            System.out.println(var3);
        }

    }

    public void UrunKategoriAktiflestir(String ad) {
        try {
            con = DriverManager.getConnection(this.baglantiAc());
            String sorgu = " UPDATE urun_kategori set status = 0 where kategori_adi = '" + ad + "' ";
            ps = con.prepareStatement(sorgu);
            ps.executeUpdate();
            con.close();
        } catch (Exception var3) {
            System.out.println(var3);
        }

    }

    public List<UrunBean> AktifOlmayanUrunler() {
        List<UrunBean> liste = new ArrayList();

        try {
            con = DriverManager.getConnection(this.baglantiAc());
            String sorgu = "select * from urun where status = 1 and guncel = 0 ";
            ps = con.prepareStatement(sorgu);
            rs = ps.executeQuery();

            while(rs.next()) {
                UrunBean list = new UrunBean();
                list.setName(rs.getString("urun_adi"));
                liste.add(list);
            }

            con.close();
        } catch (Exception var4) {
            System.out.println(var4);
        }

        return liste;
    }

    public List<UrunBean> AktifOlmayanKategoriler() {
        List<UrunBean> liste = new ArrayList();

        try {
            con = DriverManager.getConnection(this.baglantiAc());
            String sorgu = "select * from urun_kategori where status = 1 ";
            ps = con.prepareStatement(sorgu);
            rs = ps.executeQuery();

            while(rs.next()) {
                UrunBean list = new UrunBean();
                list.setUrunKategori(rs.getString("kategori_adi"));
                liste.add(list);
            }

            con.close();
        } catch (Exception var4) {
            System.out.println(var4);
        }

        return liste;
    }

    public String UrunKategoriAdi(int id) {
        String a = null;

        try {
            con = DriverManager.getConnection(this.baglantiAc());
            String sorgu = "select urun_kategori.kategori_adi from urun_kategori where id='" + id + "'";
            ps = con.prepareStatement(sorgu);

            for(rs = ps.executeQuery(); rs.next(); a = rs.getString("kategori_adi")) {
            }

            con.close();
        } catch (Exception var4) {
            System.out.println(var4);
        }

        return a;
    }

    public List<SatisBean> UrunArama(String basTarih, String bitTarih, String kategori, String urun) {
        List<SatisBean> liste = new ArrayList();

        try {
            con = DriverManager.getConnection(this.baglantiAc());
            StringBuilder sql = new StringBuilder(" SELECT satis_ayrinti.adet as adet, urun.urun_adi, urun.fiyat, urun.urun_kategori_id FROM satis_ayrinti  INNER JOIN satis  ON satis.id = satis_ayrinti.satis_id  INNER JOIN urun  ON satis_ayrinti.urun_id = urun.id   where satis.status = 1 AND satis.iptal = 0 ");
            if (!urun.equals(" ")) {
                sql.append(" AND urun.urun_adi = ?");
            }

            if (!kategori.equals(" ")) {
                sql.append(" AND urun.urun_kategori_id = ?");
            }

            if (!basTarih.equals("null-null-null")) {
                sql.append(" AND satis.kapanisTarih >= ?");
            }

            if (!bitTarih.equals("null-null-null")) {
                sql.append(" AND satis.kapanisTarih <= ?");
            }

            sql.append(" ORDER BY urun.urun_kategori_id, urun.urun_adi");
            ps = con.prepareStatement(sql.toString());
            int i = 0;
            if (!urun.equals(" ")) {
                ++i;
                ps.setString(i, urun);
            }

            if (!kategori.equals(" ")) {
                ++i;
                ps.setString(i, kategori);
            }

            if (!basTarih.equals("null-null-null")) {
                ++i;
                ps.setString(i, basTarih + " 02:59:59 ");
            }

            if (!bitTarih.equals("null-null-null")) {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                Calendar c = Calendar.getInstance();
                Date date = formatter.parse(bitTarih);
                c.setTime(date);
                c.add(5, 1);
                date = c.getTime();
                bitTarih = String.format("%1$tY-%1$tm-%1$td", date);
                ++i;
                ps.setString(i, bitTarih + " 02:59:59 ");
            }

            rs = ps.executeQuery();

            while(rs.next()) {
                SatisBean list = new SatisBean();
                list.setFiyat(new BigDecimal(rs.getDouble("fiyat")));
                list.setUrun(rs.getString("urun_adi"));
                list.setAdet(rs.getInt("adet"));
                list.setUrunKategoriId(rs.getInt("urun_kategori_id"));
                liste.add(list);
            }

            con.close();
        } catch (Exception var12) {
            System.out.println(var12);
        }

        return liste;
    }

    public List<SatisBean> AdisyonArama(String basTarih, String bitTarih, String adisyon, String durum, String masaNo) {
        List<SatisBean> liste = new ArrayList();

        try {
            con = DriverManager.getConnection(this.baglantiAc());
            StringBuilder sql = new StringBuilder(" SELECT  id,fiyat,indirim,tarih,kapanisTarih,masa_no,iptal,adisyon_no FROM satis where status = 1 ");
            if (durum.equals("SATIŞ")) {
                sql.append(" AND iptal = ?");
            }

            if (durum.equals("İPTAL")) {
                sql.append(" AND iptal = ?");
            }

            if (!adisyon.isEmpty()) {
                sql.append(" AND adisyon_no = ?");
            }

            if (!masaNo.isEmpty()) {
                sql.append(" AND masa_no = ?");
            }

            if (!basTarih.equals("null-null-null")) {
                sql.append(" AND kapanisTarih >= ?");
            }

            if (!bitTarih.equals("null-null-null")) {
                sql.append(" AND kapanisTarih <= ?");
            }

            sql.append(" ORDER BY tarih DESC ");
            ps = con.prepareStatement(sql.toString());
            int i = 0;
            if (durum.equals("SATIŞ")) {
                ++i;
                ps.setString(i, "0");
            }

            if (durum.equals("İPTAL")) {
                ++i;
                ps.setString(i, "1");
            }

            if (!adisyon.isEmpty()) {
                ++i;
                ps.setString(i, adisyon);
            }

            if (!masaNo.isEmpty()) {
                ++i;
                ps.setString(i, masaNo);
            }

            if (!basTarih.equals("null-null-null")) {
                ++i;
                ps.setString(i, basTarih + " 02:59:59 ");
            }

            if (!bitTarih.equals("null-null-null")) {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                Calendar c = Calendar.getInstance();
                Date date = formatter.parse(bitTarih);
                c.setTime(date);
                c.add(5, 1);
                date = c.getTime();
                bitTarih = String.format("%1$tY-%1$tm-%1$td", date);
                ++i;
                ps.setString(i, bitTarih + " 02:59:59 ");
            }

            rs = ps.executeQuery();

            while(rs.next()) {
                SatisBean list = new SatisBean();
                list.setSatisId(rs.getInt("id"));
                list.setToplamFiyat(new BigDecimal(rs.getDouble("fiyat")));
                list.setIndirim(new BigDecimal(rs.getDouble("indirim")));
                list.setTarih(rs.getString("tarih"));
                list.setkapTarih(rs.getString("kapanisTarih"));
                list.setMasaNo(rs.getInt("masa_no"));
                list.setIptal(rs.getInt("iptal"));
                list.setAdisyon(rs.getInt("adisyon_no"));
                liste.add(list);
            }

            con.close();
        } catch (Exception var13) {
            System.out.println(var13);
        }

        return liste;
    }

    public List<SatisBean> AdisyonDetay(String id) {
        List<SatisBean> liste = new ArrayList();

        try {
            con = DriverManager.getConnection(this.baglantiAc());
            String sorgu = "SELECT satis.id,sum(satis_ayrinti.adet) as adet,urun.urun_adi,urun.fiyat,satis.indirim,urun.urun_kategori_id,urun.id as urun_id FROM satis_ayrinti  INNER JOIN satis  ON satis.id=satis_ayrinti.satis_id INNER JOIN urun  ON satis_ayrinti.urun_id=urun.id  where satis.id = '" + id + "'  group by urun.urun_adi";
            ps = con.prepareStatement(sorgu);
            rs = ps.executeQuery();

            while(rs.next()) {
                SatisBean list = new SatisBean();
                list.setUrunId(rs.getInt("urun_id"));
                list.setAdet(rs.getInt("adet"));
                list.setUrun(rs.getString("urun_adi"));
                list.setFiyat(new BigDecimal(rs.getDouble("fiyat")));
                list.setIndirim(new BigDecimal(rs.getDouble("indirim")));
                list.setUrunKategoriId(rs.getInt("urun_kategori_id"));
                liste.add(list);
            }

            con.close();
        } catch (Exception var5) {
            System.out.println(var5);
        }

        return liste;
    }

    public void AdisyonGeriAl(int id) {
        try {
            con = DriverManager.getConnection(this.baglantiAc());
            String sorgu = " UPDATE satis set status = 0 , iptal = 0 where id = '" + id + "' ";
            ps = con.prepareStatement(sorgu);
            ps.executeUpdate();
            con.close();
        } catch (Exception var3) {
            System.out.println(var3);
        }

    }

    public List<SatisBean> AdisyonYazdir(int adisyon_no, String tarih) {
        List<SatisBean> liste = new ArrayList();

        try {
            con = DriverManager.getConnection(this.baglantiAc());
            String sorgu = " SELECT satis.id,sum(satis_ayrinti.adet) as adet,urun.urun_adi,urun.fiyat,satis.indirim,satis.fiyat as toplam,satis.masa_no FROM satis_ayrinti  INNER JOIN satis  ON satis.id=satis_ayrinti.satis_id  INNER JOIN urun ON satis_ayrinti.urun_id=urun.id  where  satis.adisyon_no = '" + adisyon_no + "' and satis.kapanisTarih like '" + tarih + "%'  group by urun.urun_adi";
            ps = con.prepareStatement(sorgu);
            rs = ps.executeQuery();

            while(rs.next()) {
                SatisBean list = new SatisBean();
                list.setSatisId(rs.getInt("id"));
                list.setAdet(rs.getInt("adet"));
                list.setUrun(rs.getString("urun_adi"));
                list.setFiyat(new BigDecimal(rs.getDouble("fiyat")));
                list.setToplamFiyat(new BigDecimal(rs.getDouble("toplam")));
                list.setIndirim(new BigDecimal(rs.getDouble("indirim")));
                list.setMasaNo(rs.getInt("masa_no"));
                liste.add(list);
            }

            con.close();
        } catch (Exception var6) {
            System.out.println(var6);
        }

        return liste;
    }

    public int SatisId(int masa_no) {
        int satis_id = 0;

        try {
            con = DriverManager.getConnection(this.baglantiAc());
            String sorgu = " select id from satis where masa_no = " + masa_no + " and status = 0 ";
            ps = con.prepareStatement(sorgu);

            for(rs = ps.executeQuery(); rs.next(); satis_id = rs.getInt("id")) {
            }

            con.close();
        } catch (Exception var4) {
            System.out.println(var4);
        }

        return satis_id;
    }

    public BigDecimal GetSatisAraOdeme(int satis_id) {
        BigDecimal tutar = new BigDecimal(0.0);

        try {
            con = DriverManager.getConnection(this.baglantiAc());
            String sorgu = " select sum(tutar) as tutar from satis_odeme where satis_id = '" + satis_id + "' ";
            ps = con.prepareStatement(sorgu);

            for(rs = ps.executeQuery(); rs.next(); tutar = new BigDecimal(rs.getDouble("tutar"))) {
            }

            con.close();
        } catch (Exception var4) {
            System.out.println(var4);
        }

        return tutar;
    }

    public void SatisAraOdeme(int satis_id, BigDecimal tutar) {
        try {
            con = DriverManager.getConnection(this.baglantiAc());
            String sorgu = " INSERT INTO satis_odeme (satis_id,tutar)  VALUES ('" + satis_id + "','" + tutar + "' )";
            ps = con.prepareStatement(sorgu);
            ps.executeUpdate();
            con.close();
        } catch (Exception var4) {
            var4.printStackTrace();
        }

    }

    public void IndirimGir(List<SatisBean> liste) {
        String sorgu = "update satis set indirim = '" + ((SatisBean)liste.get(0)).getIndirim() + "' where id='" + ((SatisBean)liste.get(0)).getSatisId() + "' ";

        try {
            con = DriverManager.getConnection(this.baglantiAc());
            con.setAutoCommit(false);
            ps = con.prepareStatement(sorgu);
            ps.executeUpdate();
            con.commit();
            con.close();
        } catch (Exception var4) {
            System.out.println(var4);
        }

    }

    public void MusteriEkle(int masaNo, String musteri) {
        String sorgu = "update status set musteri = '" + musteri + "' where masa_no='" + masaNo + "' ";

        try {
            con = DriverManager.getConnection(this.baglantiAc());
            con.setAutoCommit(false);
            ps = con.prepareStatement(sorgu);
            ps.executeUpdate();
            con.commit();
            con.close();
        } catch (Exception var5) {
            System.out.println(var5);
        }

    }

    public static void main(String[] args) throws Exception {
        dbServlet db = new dbServlet();
        db.AdisyonNo();
    }
}
