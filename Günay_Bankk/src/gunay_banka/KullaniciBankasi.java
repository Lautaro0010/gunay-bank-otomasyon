package gunay_banka;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class KullaniciBankasi {

    private static final String DB_URL = "jdbc:sqlite:gunaybank.db";

    public static class Kullanici {
        public String adSoyad;
        public String musteriNo;
        public String sifre;
        public String email;
        public String telefon;
        public String kayitTarihi;
        public double vadesiz;
        public double birikim;
        public double doviz;

        public Kullanici(String adSoyad, String musteriNo, String sifre,
                         String email, String telefon, String kayitTarihi,
                         double vadesiz, double birikim, double doviz) {
            this.adSoyad     = adSoyad;
            this.musteriNo   = musteriNo;
            this.sifre       = sifre;
            this.email       = email;
            this.telefon     = telefon;
            this.kayitTarihi = kayitTarihi;
            this.vadesiz     = vadesiz;
            this.birikim     = birikim;
            this.doviz       = doviz;
        }
    }

    private static Connection baglantiAc() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    // Tablo oluştur — yeni alanlar eklendi
    public static void tabloOlustur() {
        String sql = "CREATE TABLE IF NOT EXISTS kullanicilar (" +
                     "musteriNo   TEXT PRIMARY KEY," +
                     "adSoyad     TEXT NOT NULL," +
                     "sifre       TEXT NOT NULL," +
                     "email       TEXT," +
                     "telefon     TEXT," +
                     "kayitTarihi TEXT," +
                     "vadesiz     REAL DEFAULT 5000.0," +
                     "birikim     REAL DEFAULT 1000.0," +
                     "doviz       REAL DEFAULT 100.0" +
                     ")";
        try (Connection con = baglantiAc();
             Statement  st  = con.createStatement()) {
            st.execute(sql);
            // Eski tabloya yeni sütunlar ekle (eğer yoksa)
            try { st.execute("ALTER TABLE kullanicilar ADD COLUMN vadesiz REAL DEFAULT 5000.0"); } catch (SQLException ignored) {}
            try { st.execute("ALTER TABLE kullanicilar ADD COLUMN birikim REAL DEFAULT 1000.0"); } catch (SQLException ignored) {}
            try { st.execute("ALTER TABLE kullanicilar ADD COLUMN doviz   REAL DEFAULT 100.0");  } catch (SQLException ignored) {}
        } catch (SQLException e) {
            System.err.println("Tablo oluşturma hatası: " + e.getMessage());
        }
    }

    // Yeni müşteri no üret
    public static String yeniMusteriNo() {
        String sql = "SELECT musteriNo FROM kullanicilar ORDER BY CAST(musteriNo AS INTEGER) DESC LIMIT 1";
        try (Connection con = baglantiAc();
             Statement  st  = con.createStatement();
             ResultSet  rs  = st.executeQuery(sql)) {
            if (rs.next()) {
                int sonNo = Integer.parseInt(rs.getString("musteriNo"));
                return String.valueOf(sonNo + 1);
            }
        } catch (SQLException e) {
            System.err.println("Müşteri no hatası: " + e.getMessage());
        }
        return "10001";
    }

    // Kullanıcı kaydet — başlangıç bakiyeleri ile
    public static boolean kaydet(Kullanici k) {
        String sql = "INSERT INTO kullanicilar " +
                     "(musteriNo, adSoyad, sifre, email, telefon, kayitTarihi, vadesiz, birikim, doviz) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = baglantiAc();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, k.musteriNo);
            ps.setString(2, k.adSoyad);
            ps.setString(3, k.sifre);
            ps.setString(4, k.email);
            ps.setString(5, k.telefon);
            ps.setString(6, k.kayitTarihi);
            ps.setDouble(7, k.vadesiz);
            ps.setDouble(8, k.birikim);
            ps.setDouble(9, k.doviz);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Kayıt hatası: " + e.getMessage());
            return false;
        }
    }

    // Giriş doğrulama
    public static Kullanici bul(String musteriNo, String sifre) {
        String sql = "SELECT * FROM kullanicilar WHERE musteriNo = ? AND sifre = ?";
        try (Connection con = baglantiAc();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, musteriNo);
            ps.setString(2, sifre);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return kullanicidanOku(rs);
            }
        } catch (SQLException e) {
            System.err.println("Giriş hatası: " + e.getMessage());
        }
        return null;
    }

    // Tüm kullanıcıları getir
    public static List<Kullanici> tumKullanicilar() {
        List<Kullanici> liste = new ArrayList<>();
        String sql = "SELECT * FROM kullanicilar ORDER BY kayitTarihi DESC";
        try (Connection con = baglantiAc();
             Statement  st  = con.createStatement();
             ResultSet  rs  = st.executeQuery(sql)) {
            while (rs.next()) {
                liste.add(kullanicidanOku(rs));
            }
        } catch (SQLException e) {
            System.err.println("Listeleme hatası: " + e.getMessage());
        }
        return liste;
    }

    // Bakiye güncelle
    public static boolean bakiyeGuncelle(String musteriNo, double vadesiz, double birikim, double doviz) {
        String sql = "UPDATE kullanicilar SET vadesiz=?, birikim=?, doviz=? WHERE musteriNo=?";
        try (Connection con = baglantiAc();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDouble(1, vadesiz);
            ps.setDouble(2, birikim);
            ps.setDouble(3, doviz);
            ps.setString(4, musteriNo);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Bakiye güncelleme hatası: " + e.getMessage());
            return false;
        }
    }

    // Kullanıcı sil
    public static boolean sil(String musteriNo) {
        String sql = "DELETE FROM kullanicilar WHERE musteriNo = ?";
        try (Connection con = baglantiAc();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, musteriNo);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Silme hatası: " + e.getMessage());
            return false;
        }
    }

    // ResultSet'ten kullanıcı oku (yardımcı metod)
    private static Kullanici kullanicidanOku(ResultSet rs) throws SQLException {
        return new Kullanici(
            rs.getString("adSoyad"),
            rs.getString("musteriNo"),
            rs.getString("sifre"),
            rs.getString("email")       != null ? rs.getString("email")   : "",
            rs.getString("telefon")     != null ? rs.getString("telefon") : "",
            rs.getString("kayitTarihi") != null ? rs.getString("kayitTarihi") : "",
            rs.getDouble("vadesiz"),
            rs.getDouble("birikim"),
            rs.getDouble("doviz")
        );
    }
}