package gunay_banka;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class AdminEkrani extends JFrame {

    private static final Color KOYU_MAVI = new Color(0, 71, 148);
    private static final Color ACIK_MAVI = new Color(0, 153, 224);
    private static final Color BEYAZ     = Color.WHITE;
    private static final Color ARKAPLAN  = new Color(235, 242, 252);

    private JPanel icerikPanel;

    public AdminEkrani() {
        setTitle("Günay Bank - Yönetici Paneli");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 750);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel anaPanel = new JPanel(null);
        anaPanel.setBackground(ARKAPLAN);
        setContentPane(anaPanel);

        // NAVBAR
        JPanel navbar = new JPanel(null);
        navbar.setBackground(new Color(80, 0, 0));
        navbar.setBounds(0, 0, 1200, 55);
        anaPanel.add(navbar);

        JLabel lblLogo = new JLabel("Günay Bank  |  Yönetici Paneli");
        lblLogo.setFont(new Font("Serif", Font.BOLD, 20));
        lblLogo.setForeground(BEYAZ);
        lblLogo.setBounds(20, 13, 380, 28);
        navbar.add(lblLogo);

        JLabel lblAdmin = new JLabel("👑  Admin");
        lblAdmin.setFont(new Font("SansSerif", Font.BOLD, 13));
        lblAdmin.setForeground(new Color(255, 210, 150));
        lblAdmin.setBounds(1000, 18, 120, 20);
        navbar.add(lblAdmin);

        JButton btnCikis = new JButton("Çıkış");
        btnCikis.setBounds(1110, 13, 75, 30);
        btnCikis.setBackground(new Color(160, 30, 30));
        btnCikis.setForeground(BEYAZ);
        btnCikis.setFont(new Font("SansSerif", Font.BOLD, 12));
        btnCikis.setFocusPainted(false);
        btnCikis.setBorderPainted(false);
        btnCikis.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        navbar.add(btnCikis);
        btnCikis.addActionListener(e -> { new BankaGiris().setVisible(true); dispose(); });

        // SOL MENÜ
        JPanel solMenu = new JPanel(null);
        solMenu.setBackground(new Color(60, 0, 0));
        solMenu.setBounds(0, 55, 210, 695);
        anaPanel.add(solMenu);

        JLabel lblBaslik = new JLabel("YÖNETİCİ MENÜSÜ");
        lblBaslik.setFont(new Font("SansSerif", Font.BOLD, 11));
        lblBaslik.setForeground(new Color(200, 160, 120));
        lblBaslik.setBounds(18, 20, 180, 18);
        solMenu.add(lblBaslik);

        String[] menuler = {"📊  Genel Bakış", "👥  Tüm Kullanıcılar", "➕  Kullanıcı Ekle"};
        JButton[] menuButonlari = new JButton[menuler.length];

        for (int i = 0; i < menuler.length; i++) {
            JButton btn = new JButton(menuler[i]);
            btn.setBounds(0, 50 + i * 52, 210, 46);
            btn.setBackground(new Color(60, 0, 0));
            btn.setForeground(new Color(220, 200, 190));
            btn.setFont(new Font("SansSerif", Font.PLAIN, 13));
            btn.setFocusPainted(false);
            btn.setBorderPainted(false);
            btn.setHorizontalAlignment(SwingConstants.LEFT);
            btn.setBorder(BorderFactory.createEmptyBorder(0, 18, 0, 0));
            btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            btn.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) { btn.setBackground(new Color(100, 20, 20)); }
                public void mouseExited(MouseEvent e)  { btn.setBackground(new Color(60, 0, 0)); }
            });
            solMenu.add(btn);
            menuButonlari[i] = btn;
        }

        // İÇERİK ALANI
        icerikPanel = new JPanel(null);
        icerikPanel.setBackground(ARKAPLAN);
        icerikPanel.setBounds(210, 55, 990, 695);
        anaPanel.add(icerikPanel);

        genelBakisGoster();

        menuButonlari[0].addActionListener(e -> genelBakisGoster());
        menuButonlari[1].addActionListener(e -> kullanicilariGoster());
        menuButonlari[2].addActionListener(e -> kullaniciEkleGoster());
    }

    private void temizle() {
        icerikPanel.removeAll();
        icerikPanel.revalidate();
        icerikPanel.repaint();
    }

    private void baslikEkle(String baslik) {
        JLabel lbl = new JLabel(baslik);
        lbl.setFont(new Font("Serif", Font.BOLD, 22));
        lbl.setForeground(new Color(100, 0, 0));
        lbl.setBounds(30, 28, 600, 35);
        icerikPanel.add(lbl);

        JSeparator sep = new JSeparator();
        sep.setBounds(30, 68, 930, 1);
        sep.setForeground(new Color(220, 200, 200));
        icerikPanel.add(sep);
    }

    // GENEL BAKIŞ
    private void genelBakisGoster() {
        temizle();

        // ← Veritabanından oku
        List<KullaniciBankasi.Kullanici> liste = KullaniciBankasi.tumKullanicilar();
        baslikEkle("Genel Bakış");

        String[][] kartlar = {
            {"👥", "Toplam Kullanıcı", String.valueOf(liste.size())},
            {"📅", "Bugünün Tarihi",   java.time.LocalDate.now().toString()},
            {"🏦", "Banka Adı",        "Günay Bank"},
        };

        for (int i = 0; i < kartlar.length; i++) {
            JPanel kart = new JPanel(null);
            kart.setBackground(BEYAZ);
            kart.setBounds(30 + i * 310, 95, 280, 110);
            kart.setBorder(BorderFactory.createLineBorder(new Color(220, 200, 200), 1));
            icerikPanel.add(kart);

            JLabel ikon = new JLabel(kartlar[i][0]);
            ikon.setFont(new Font("SansSerif", Font.PLAIN, 28));
            ikon.setBounds(15, 15, 40, 35);
            kart.add(ikon);

            JLabel baslikLbl = new JLabel(kartlar[i][1]);
            baslikLbl.setFont(new Font("SansSerif", Font.BOLD, 12));
            baslikLbl.setForeground(new Color(120, 60, 60));
            baslikLbl.setBounds(60, 15, 200, 22);
            kart.add(baslikLbl);

            JLabel deger = new JLabel(kartlar[i][2]);
            deger.setFont(new Font("Serif", Font.BOLD, 20));
            deger.setForeground(new Color(80, 0, 0));
            deger.setBounds(15, 55, 250, 30);
            kart.add(deger);
        }

        // Son kayıt olanlar
        JLabel lblSon = new JLabel("Son Kayıt Olan Kullanıcılar");
        lblSon.setFont(new Font("Serif", Font.BOLD, 15));
        lblSon.setForeground(new Color(80, 0, 0));
        lblSon.setBounds(30, 235, 300, 25);
        icerikPanel.add(lblSon);

        if (liste.isEmpty()) {
            JLabel lblBos = new JLabel("Henüz kayıtlı kullanıcı yok.");
            lblBos.setFont(new Font("SansSerif", Font.ITALIC, 13));
            lblBos.setForeground(Color.GRAY);
            lblBos.setBounds(30, 275, 400, 25);
            icerikPanel.add(lblBos);
        } else {
            int baslangic = Math.max(0, liste.size() - 5);
            for (int i = baslangic; i < liste.size(); i++) {
                KullaniciBankasi.Kullanici k = liste.get(i);
                JPanel satir = new JPanel(null);
                satir.setBackground((i % 2 == 0) ? BEYAZ : new Color(250, 245, 245));
                satir.setBounds(30, 270 + (i - baslangic) * 44, 900, 44);
                satir.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(235, 220, 220)));
                icerikPanel.add(satir);

                JLabel lblAd = new JLabel(k.adSoyad);
                lblAd.setFont(new Font("SansSerif", Font.BOLD, 13));
                lblAd.setBounds(15, 12, 250, 20);
                satir.add(lblAd);

                JLabel lblMusNo = new JLabel("Müşteri No: " + k.musteriNo);
                lblMusNo.setFont(new Font("Monospaced", Font.PLAIN, 12));
                lblMusNo.setForeground(Color.GRAY);
                lblMusNo.setBounds(300, 12, 200, 20);
                satir.add(lblMusNo);

                JLabel lblTarih = new JLabel("Kayıt: " + k.kayitTarihi);
                lblTarih.setFont(new Font("SansSerif", Font.PLAIN, 12));
                lblTarih.setForeground(Color.GRAY);
                lblTarih.setBounds(560, 12, 200, 20);
                satir.add(lblTarih);
            }
        }

        icerikPanel.revalidate();
        icerikPanel.repaint();
    }

    // TÜM KULLANICILAR
    private void kullanicilariGoster() {
        temizle();

        // ← Veritabanından oku
        List<KullaniciBankasi.Kullanici> liste = KullaniciBankasi.tumKullanicilar();
        baslikEkle("Tüm Kullanıcılar  (" + liste.size() + " kişi)");

        String[] sutunlar = {"Ad Soyad", "Müşteri No", "E-posta", "Telefon", "Kayıt Tarihi", "İşlem"};
        Object[][] veri = new Object[liste.size()][6];
        for (int i = 0; i < liste.size(); i++) {
            KullaniciBankasi.Kullanici k = liste.get(i);
            veri[i][0] = k.adSoyad;
            veri[i][1] = k.musteriNo;
            veri[i][2] = k.email.isEmpty()   ? "—" : k.email;
            veri[i][3] = k.telefon.isEmpty()  ? "—" : k.telefon;
            veri[i][4] = k.kayitTarihi;
            veri[i][5] = "Sil";
        }

        DefaultTableModel model = new DefaultTableModel(veri, sutunlar) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable tablo = new JTable(model);
        tablo.setFont(new Font("SansSerif", Font.PLAIN, 13));
        tablo.setRowHeight(34);
        tablo.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 13));
        tablo.getTableHeader().setBackground(new Color(180, 40, 40));
        tablo.getTableHeader().setForeground(BEYAZ);
        tablo.setSelectionBackground(new Color(255, 220, 220));
        tablo.setGridColor(new Color(235, 220, 220));

        // Sil butonu sütunu
        tablo.getColumn("İşlem").setCellRenderer(new ButtonRenderer());
        tablo.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int satir = tablo.getSelectedRow();
                int sutun = tablo.getSelectedColumn();
                if (sutun == 5 && satir >= 0) {
                    String musteriNo = (String) tablo.getValueAt(satir, 1);
                    String ad        = (String) tablo.getValueAt(satir, 0);
                    int onay = JOptionPane.showConfirmDialog(AdminEkrani.this,
                        ad + " adlı kullanıcıyı silmek istediğinize emin misiniz?",
                        "Kullanıcı Sil", JOptionPane.YES_NO_OPTION);
                    if (onay == JOptionPane.YES_OPTION) {
                        KullaniciBankasi.sil(musteriNo); // ← Veritabanından sil
                        kullanicilariGoster();
                    }
                }
            }
        });

        JScrollPane scroll = new JScrollPane(tablo);
        scroll.setBounds(30, 90, 930, 480);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(220, 200, 200), 1));
        icerikPanel.add(scroll);

        if (liste.isEmpty()) {
            JLabel lblBos = new JLabel("Henüz kayıtlı kullanıcı yok.");
            lblBos.setFont(new Font("SansSerif", Font.ITALIC, 14));
            lblBos.setForeground(Color.GRAY);
            lblBos.setBounds(30, 100, 400, 30);
            icerikPanel.add(lblBos);
        }

        icerikPanel.revalidate();
        icerikPanel.repaint();
    }

    // KULLANICI EKLE
    private void kullaniciEkleGoster() {
        temizle();
        baslikEkle("Kullanıcı Ekle");

        JPanel form = new JPanel(null);
        form.setBackground(BEYAZ);
        form.setBounds(30, 90, 550, 400);
        form.setBorder(BorderFactory.createLineBorder(new Color(220, 200, 200), 1));
        icerikPanel.add(form);

        String[] etiketler = {"Ad Soyad *", "Şifre *", "E-posta", "Telefon"};
        JTextField[] alanlar = new JTextField[4];

        for (int i = 0; i < etiketler.length; i++) {
            JLabel lbl = new JLabel(etiketler[i]);
            lbl.setFont(new Font("SansSerif", Font.PLAIN, 13));
            lbl.setForeground(new Color(80, 40, 40));
            lbl.setBounds(30, 18 + i * 75, 200, 22);
            form.add(lbl);

            JTextField txt = new JTextField();
            txt.setBounds(30, 42 + i * 75, 480, 34);
            txt.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 170, 170), 1),
                BorderFactory.createEmptyBorder(4, 8, 4, 8)));
            txt.setFont(new Font("SansSerif", Font.PLAIN, 14));
            form.add(txt);
            alanlar[i] = txt;
        }

        JButton btnEkle = new JButton("Ekle");
        btnEkle.setBounds(30, 338, 140, 40);
        btnEkle.setBackground(new Color(180, 30, 30));
        btnEkle.setForeground(BEYAZ);
        btnEkle.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnEkle.setFocusPainted(false);
        btnEkle.setBorderPainted(false);
        btnEkle.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        form.add(btnEkle);

        btnEkle.addActionListener(e -> {
            String ad    = alanlar[0].getText().trim();
            String sifre = alanlar[1].getText().trim();
            String email = alanlar[2].getText().trim();
            String tel   = alanlar[3].getText().trim();

            if (ad.isEmpty() || sifre.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Zorunlu (*) alanları doldurunuz.", "Uyarı", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String no    = KullaniciBankasi.yeniMusteriNo();
            String tarih = java.time.LocalDate.now().toString();

            // ← Veritabanına kaydet (başlangıç bakiyeleri ile)
            boolean basarili = KullaniciBankasi.kaydet(
                new KullaniciBankasi.Kullanici(ad, no, sifre, email, tel, tarih, 5000.0, 1000.0, 100.0));

            if (basarili) {
                JOptionPane.showMessageDialog(this,
                    ad + " adlı kullanıcı eklendi.\nMüşteri No: " + no,
                    "Başarılı", JOptionPane.INFORMATION_MESSAGE);
                for (JTextField t : alanlar) t.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Ekleme sırasında hata oluştu!", "Hata", JOptionPane.ERROR_MESSAGE);
            }
        });

        icerikPanel.revalidate();
        icerikPanel.repaint();
    }

    // Tablo Sil butonu için renderer
    static class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
            setBackground(new Color(200, 40, 40));
            setForeground(Color.WHITE);
            setFont(new Font("SansSerif", Font.BOLD, 11));
        }
        public Component getTableCellRendererComponent(JTable t, Object v,
                boolean sel, boolean foc, int r, int c) {
            setText("Sil");
            return this;
        }
    }
}