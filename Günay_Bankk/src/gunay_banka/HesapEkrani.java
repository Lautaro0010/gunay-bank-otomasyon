package gunay_banka;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Locale;

public class HesapEkrani extends JFrame {

    private static final Color KOYU_MAVI  = new Color(0, 71, 148);
    private static final Color ACIK_MAVI  = new Color(0, 153, 224);
    private static final Color MENU_MAVI  = new Color(0, 55, 120);
    private static final Color HOVER_MAVI = new Color(0, 90, 170);
    private static final Color BEYAZ      = Color.WHITE;
    private static final Color ARKAPLAN   = new Color(235, 242, 252);

    private JPanel icerikPanel;
    private KullaniciBankasi.Kullanici kullanici;

    public HesapEkrani(KullaniciBankasi.Kullanici kullanici) {
        this.kullanici = kullanici;

        setTitle("Günay Bank - Hesabım");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 750);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel anaPanel = new JPanel(null);
        anaPanel.setBackground(ARKAPLAN);
        setContentPane(anaPanel);

        // NAVBAR
        JPanel navbar = new JPanel(null);
        navbar.setBackground(KOYU_MAVI);
        navbar.setBounds(0, 0, 1200, 55);
        anaPanel.add(navbar);

        JLabel lblLogo = new JLabel("Günay Bank");
        lblLogo.setFont(new Font("Serif", Font.BOLD, 22));
        lblLogo.setForeground(BEYAZ);
        lblLogo.setBounds(20, 12, 200, 30);
        navbar.add(lblLogo);

        JLabel lblHosgeldin = new JLabel("Hoş geldiniz, " + kullanici.adSoyad);
        lblHosgeldin.setFont(new Font("SansSerif", Font.PLAIN, 13));
        lblHosgeldin.setForeground(new Color(200, 220, 255));
        lblHosgeldin.setBounds(800, 18, 280, 20);
        navbar.add(lblHosgeldin);

        JButton btnCikis = new JButton("Çıkış Yap");
        btnCikis.setBounds(1090, 13, 95, 30);
        btnCikis.setBackground(new Color(200, 40, 40));
        btnCikis.setForeground(BEYAZ);
        btnCikis.setFont(new Font("SansSerif", Font.BOLD, 12));
        btnCikis.setFocusPainted(false);
        btnCikis.setBorderPainted(false);
        btnCikis.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        navbar.add(btnCikis);
        btnCikis.addActionListener(e -> { new BankaGiris().setVisible(true); dispose(); });

        // SOL MENÜ
        JPanel solMenu = new JPanel(null);
        solMenu.setBackground(MENU_MAVI);
        solMenu.setBounds(0, 55, 220, 695);
        anaPanel.add(solMenu);

        JPanel kullaniciKutu = new JPanel(null);
        kullaniciKutu.setBackground(new Color(0, 40, 100));
        kullaniciKutu.setBounds(0, 0, 220, 90);
        solMenu.add(kullaniciKutu);

        JLabel lblAvatar = new JLabel("👤");
        lblAvatar.setFont(new Font("SansSerif", Font.PLAIN, 30));
        lblAvatar.setBounds(15, 20, 40, 40);
        kullaniciKutu.add(lblAvatar);

        JLabel lblIsim = new JLabel(kullanici.adSoyad);
        lblIsim.setFont(new Font("SansSerif", Font.BOLD, 13));
        lblIsim.setForeground(BEYAZ);
        lblIsim.setBounds(60, 22, 150, 20);
        kullaniciKutu.add(lblIsim);

        JLabel lblMusteriNo = new JLabel("Müşteri No: " + kullanici.musteriNo);
        lblMusteriNo.setFont(new Font("SansSerif", Font.PLAIN, 11));
        lblMusteriNo.setForeground(new Color(160, 200, 240));
        lblMusteriNo.setBounds(60, 44, 150, 18);
        kullaniciKutu.add(lblMusteriNo);

        String[] menuIsimleri = {"🏠  Ana Sayfa", "💳  Hesap Bakiyesi", "📋  Son İşlemler", "💸  Para Transferi", "👤  Profil Bilgileri"};
        JButton[] menuButonlari = new JButton[menuIsimleri.length];

        for (int i = 0; i < menuIsimleri.length; i++) {
            JButton btn = new JButton(menuIsimleri[i]);
            btn.setBounds(0, 100 + i * 55, 220, 50);
            btn.setBackground(MENU_MAVI);
            btn.setForeground(new Color(210, 225, 245));
            btn.setFont(new Font("SansSerif", Font.PLAIN, 13));
            btn.setFocusPainted(false);
            btn.setBorderPainted(false);
            btn.setHorizontalAlignment(SwingConstants.LEFT);
            btn.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
            btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            solMenu.add(btn);
            menuButonlari[i] = btn;
            btn.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) { btn.setBackground(HOVER_MAVI); }
                public void mouseExited(MouseEvent e)  { btn.setBackground(MENU_MAVI); }
            });
        }

        // İÇERİK ALANI
        icerikPanel = new JPanel(null);
        icerikPanel.setBackground(ARKAPLAN);
        icerikPanel.setBounds(220, 55, 980, 695);
        anaPanel.add(icerikPanel);

        anaSayfaGoster();

        menuButonlari[0].addActionListener(e -> anaSayfaGoster());
        menuButonlari[1].addActionListener(e -> bakiyeGoster());
        menuButonlari[2].addActionListener(e -> islemlerGoster());
        menuButonlari[3].addActionListener(e -> transferGoster());
        menuButonlari[4].addActionListener(e -> profilGoster());
    }

    private void temizle() {
        icerikPanel.removeAll();
        icerikPanel.revalidate();
        icerikPanel.repaint();
    }

    private void baslikEkle(String baslik) {
        JLabel lbl = new JLabel(baslik);
        lbl.setFont(new Font("Serif", Font.BOLD, 22));
        lbl.setForeground(KOYU_MAVI);
        lbl.setBounds(30, 30, 500, 35);
        icerikPanel.add(lbl);
        JSeparator sep = new JSeparator();
        sep.setBounds(30, 68, 920, 1);
        sep.setForeground(new Color(200, 215, 240));
        icerikPanel.add(sep);
    }

    // TL formatı
    private String tlFormat(double tutar) {
        return String.format(new Locale("tr", "TR"), "₺ %,.2f", tutar);
    }

    // ── ANA SAYFA ──
    private void anaSayfaGoster() {
        temizle();
        baslikEkle("Ana Sayfa");

        String[][] kartlar = {
            {"💳", "Vadesiz Hesap",  tlFormat(kullanici.vadesiz), "TR" + kullanici.musteriNo + " 0001 2345"},
            {"💰", "Birikim Hesabı", tlFormat(kullanici.birikim), "TR" + kullanici.musteriNo + " 0002 3456"},
            {"💵", "Döviz Hesabı",   String.format("$ %,.2f", kullanici.doviz), "TR" + kullanici.musteriNo + " 0003 4567"},
        };

        for (int i = 0; i < kartlar.length; i++) {
            JPanel kart = ozetKartOlustur(kartlar[i][0], kartlar[i][1], kartlar[i][2], kartlar[i][3]);
            kart.setBounds(30 + i * 310, 90, 285, 130);
            icerikPanel.add(kart);
        }

        JLabel lblSon = new JLabel("Son İşlemler");
        lblSon.setFont(new Font("Serif", Font.BOLD, 16));
        lblSon.setForeground(KOYU_MAVI);
        lblSon.setBounds(30, 250, 200, 25);
        icerikPanel.add(lblSon);

        String[][] islemler = {
            {"Hoş Geldin Bonusu",   kullanici.kayitTarihi, "+ ₺ 5.000,00", "yesil"},
            {"Birikim Hesabı Açıldı", kullanici.kayitTarihi, "+ ₺ 1.000,00", "yesil"},
            {"Döviz Hesabı Açıldı",   kullanici.kayitTarihi, "+ $ 100,00",   "yesil"},
        };
        islemListesiEkle(islemler, 285);

        icerikPanel.revalidate();
        icerikPanel.repaint();
    }

    // ── BAKİYE ──
    private void bakiyeGoster() {
        temizle();
        baslikEkle("Hesap Bakiyesi");

        String[][] hesaplar = {
            {"💳 Vadesiz Hesap",  "TR" + kullanici.musteriNo + " 0001 2345 6789 00", tlFormat(kullanici.vadesiz), "TL Hesabı"},
            {"💰 Birikim Hesabı", "TR" + kullanici.musteriNo + " 0002 3456 7890 00", tlFormat(kullanici.birikim), "TL Hesabı"},
            {"💵 Döviz Hesabı",   "TR" + kullanici.musteriNo + " 0003 4567 8901 00", String.format("$ %,.2f", kullanici.doviz), "USD Hesabı"},
        };

        for (int i = 0; i < hesaplar.length; i++) {
            JPanel kart = new JPanel(null);
            kart.setBackground(BEYAZ);
            kart.setBounds(30, 90 + i * 130, 900, 110);
            kart.setBorder(BorderFactory.createLineBorder(new Color(200, 215, 240), 1));
            icerikPanel.add(kart);

            JLabel lblIsim = new JLabel(hesaplar[i][0]);
            lblIsim.setFont(new Font("SansSerif", Font.BOLD, 15));
            lblIsim.setForeground(KOYU_MAVI);
            lblIsim.setBounds(25, 15, 300, 25);
            kart.add(lblIsim);

            JLabel lblIBAN = new JLabel("IBAN: " + hesaplar[i][1]);
            lblIBAN.setFont(new Font("Monospaced", Font.PLAIN, 12));
            lblIBAN.setForeground(Color.GRAY);
            lblIBAN.setBounds(25, 42, 400, 20);
            kart.add(lblIBAN);

            JLabel lblTur = new JLabel(hesaplar[i][3]);
            lblTur.setFont(new Font("SansSerif", Font.PLAIN, 12));
            lblTur.setForeground(Color.GRAY);
            lblTur.setBounds(25, 65, 200, 20);
            kart.add(lblTur);

            JLabel lblBakiye = new JLabel(hesaplar[i][2]);
            lblBakiye.setFont(new Font("Serif", Font.BOLD, 22));
            lblBakiye.setForeground(new Color(0, 140, 60));
            lblBakiye.setBounds(640, 35, 240, 35);
            kart.add(lblBakiye);
        }

        icerikPanel.revalidate();
        icerikPanel.repaint();
    }

    // ── SON İŞLEMLER ──
    private void islemlerGoster() {
        temizle();
        baslikEkle("Son İşlemler");

        String[][] islemler = {
            {"Hoş Geldin Bonusu",     kullanici.kayitTarihi, "+ ₺ 5.000,00",  "yesil"},
            {"Birikim Hesabı Açıldı", kullanici.kayitTarihi, "+ ₺ 1.000,00",  "yesil"},
            {"Döviz Hesabı Açıldı",   kullanici.kayitTarihi, "+ $ 100,00",    "yesil"},
        };
        islemListesiEkle(islemler, 90);

        icerikPanel.revalidate();
        icerikPanel.repaint();
    }

    // ── PARA TRANSFERİ ──
    private void transferGoster() {
        temizle();
        baslikEkle("Para Transferi");

        JPanel form = new JPanel(null);
        form.setBackground(BEYAZ);
        form.setBounds(30, 90, 580, 490);
        form.setBorder(BorderFactory.createLineBorder(new Color(200, 215, 240), 1));
        icerikPanel.add(form);

        // Mevcut bakiye göster
        JLabel lblMevcutBaslik = new JLabel("Vadesiz Hesap Bakiyeniz:");
        lblMevcutBaslik.setFont(new Font("SansSerif", Font.PLAIN, 13));
        lblMevcutBaslik.setForeground(Color.GRAY);
        lblMevcutBaslik.setBounds(30, 18, 220, 22);
        form.add(lblMevcutBaslik);

        JLabel lblMevcut = new JLabel(tlFormat(kullanici.vadesiz));
        lblMevcut.setFont(new Font("Serif", Font.BOLD, 20));
        lblMevcut.setForeground(new Color(0, 130, 60));
        lblMevcut.setBounds(260, 14, 260, 30);
        form.add(lblMevcut);

        JSeparator sep = new JSeparator();
        sep.setBounds(20, 52, 530, 1);
        sep.setForeground(new Color(220, 230, 245));
        form.add(sep);

        String[] etiketler = {"Alıcı Ad Soyad:", "Alıcı IBAN:", "Tutar (₺):", "Açıklama:"};
        JTextField[] alanlar = new JTextField[4];

        for (int i = 0; i < etiketler.length; i++) {
            JLabel lbl = new JLabel(etiketler[i]);
            lbl.setFont(new Font("SansSerif", Font.PLAIN, 13));
            lbl.setForeground(new Color(60, 60, 60));
            lbl.setBounds(30, 68 + i * 80, 200, 25);
            form.add(lbl);

            JTextField txt = new JTextField();
            txt.setBounds(30, 96 + i * 80, 500, 38);
            txt.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(160, 190, 230), 1),
                BorderFactory.createEmptyBorder(4, 10, 4, 10)));
            txt.setFont(new Font("SansSerif", Font.PLAIN, 14));
            form.add(txt);
            alanlar[i] = txt;
        }

        JButton btnGonder = new JButton("💸  Gönder");
        btnGonder.setBounds(30, 418, 160, 44);
        btnGonder.setBackground(ACIK_MAVI);
        btnGonder.setForeground(BEYAZ);
        btnGonder.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnGonder.setFocusPainted(false);
        btnGonder.setBorderPainted(false);
        btnGonder.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        form.add(btnGonder);

        btnGonder.addActionListener(e -> {
            String ad    = alanlar[0].getText().trim();
            String iban  = alanlar[1].getText().trim();
            String tutarStr = alanlar[2].getText().trim();

            if (ad.isEmpty() || iban.isEmpty() || tutarStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Lütfen zorunlu alanları doldurunuz.", "Uyarı", JOptionPane.WARNING_MESSAGE);
                return;
            }

            double tutar;
            try {
                tutar = Double.parseDouble(tutarStr.replace(",", "."));
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Geçerli bir tutar giriniz.", "Hata", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (tutar <= 0) {
                JOptionPane.showMessageDialog(this, "Tutar 0'dan büyük olmalıdır.", "Hata", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (tutar > kullanici.vadesiz) {
                JOptionPane.showMessageDialog(this, "Yetersiz bakiye!\nMevcut bakiyeniz: " + tlFormat(kullanici.vadesiz), "Hata", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Bakiyeyi düş ve veritabanına yaz
            kullanici.vadesiz -= tutar;
            KullaniciBankasi.bakiyeGuncelle(kullanici.musteriNo, kullanici.vadesiz, kullanici.birikim, kullanici.doviz);
            lblMevcut.setText(tlFormat(kullanici.vadesiz));

            JOptionPane.showMessageDialog(this,
                tlFormat(tutar) + " tutarında transfer\n" + ad + " adlı kişiye başarıyla gönderildi.\n\nKalan Bakiye: " + tlFormat(kullanici.vadesiz),
                "Transfer Başarılı", JOptionPane.INFORMATION_MESSAGE);

            for (JTextField t : alanlar) t.setText("");
        });

        icerikPanel.revalidate();
        icerikPanel.repaint();
    }

    // ── PROFİL BİLGİLERİ ──
    private void profilGoster() {
        temizle();
        baslikEkle("Profil Bilgileri");

        JPanel kart = new JPanel(null);
        kart.setBackground(BEYAZ);
        kart.setBounds(30, 90, 620, 430);
        kart.setBorder(BorderFactory.createLineBorder(new Color(200, 215, 240), 1));
        icerikPanel.add(kart);

        JLabel lblAvatar = new JLabel("👤");
        lblAvatar.setFont(new Font("SansSerif", Font.PLAIN, 52));
        lblAvatar.setBounds(260, 22, 70, 70);
        kart.add(lblAvatar);

        String email  = kullanici.email.isEmpty()   ? "—" : kullanici.email;
        String telefon= kullanici.telefon.isEmpty()  ? "—" : kullanici.telefon;

        String[][] bilgiler = {
            {"Ad Soyad",        kullanici.adSoyad},
            {"Müşteri No",      kullanici.musteriNo},
            {"E-posta",         email},
            {"Telefon",         telefon},
            {"Kayıt Tarihi",    kullanici.kayitTarihi},
            {"Toplam Varlık",   tlFormat(kullanici.vadesiz + kullanici.birikim)},
        };

        for (int i = 0; i < bilgiler.length; i++) {
            JLabel lblEtiket = new JLabel(bilgiler[i][0] + ":");
            lblEtiket.setFont(new Font("SansSerif", Font.BOLD, 13));
            lblEtiket.setForeground(new Color(100, 120, 160));
            lblEtiket.setBounds(35, 112 + i * 48, 160, 25);
            kart.add(lblEtiket);

            JLabel lblDeger = new JLabel(bilgiler[i][1]);
            lblDeger.setFont(new Font("SansSerif", i == 5 ? Font.BOLD : Font.PLAIN, 13));
            lblDeger.setForeground(i == 5 ? new Color(0, 130, 60) : new Color(30, 30, 30));
            lblDeger.setBounds(220, 112 + i * 48, 360, 25);
            kart.add(lblDeger);

            if (i < bilgiler.length - 1) {
                JSeparator s = new JSeparator();
                s.setBounds(25, 140 + i * 48, 570, 1);
                s.setForeground(new Color(230, 235, 245));
                kart.add(s);
            }
        }

        icerikPanel.revalidate();
        icerikPanel.repaint();
    }

    // ── YARDIMCI METODLAR ──
    private JPanel ozetKartOlustur(String ikon, String baslik, String tutar, String alt) {
        JPanel kart = new JPanel(null);
        kart.setBackground(BEYAZ);
        kart.setBorder(BorderFactory.createLineBorder(new Color(200, 215, 240), 1));

        JLabel lblIkon = new JLabel(ikon);
        lblIkon.setFont(new Font("SansSerif", Font.PLAIN, 28));
        lblIkon.setBounds(15, 15, 40, 35);
        kart.add(lblIkon);

        JLabel lblBaslik = new JLabel(baslik);
        lblBaslik.setFont(new Font("SansSerif", Font.BOLD, 13));
        lblBaslik.setForeground(new Color(80, 100, 140));
        lblBaslik.setBounds(60, 15, 200, 22);
        kart.add(lblBaslik);

        JLabel lblTutar = new JLabel(tutar);
        lblTutar.setFont(new Font("Serif", Font.BOLD, 16));
        lblTutar.setForeground(new Color(0, 130, 60));
        lblTutar.setBounds(15, 50, 255, 30);
        kart.add(lblTutar);

        JLabel lblAlt = new JLabel(alt);
        lblAlt.setFont(new Font("Monospaced", Font.PLAIN, 10));
        lblAlt.setForeground(Color.GRAY);
        lblAlt.setBounds(15, 85, 260, 18);
        kart.add(lblAlt);

        return kart;
    }

    private void islemListesiEkle(String[][] islemler, int startY) {
        JPanel baslikSatir = new JPanel(null);
        baslikSatir.setBackground(new Color(220, 232, 250));
        baslikSatir.setBounds(30, startY, 900, 36);
        icerikPanel.add(baslikSatir);

        String[] basliklar = {"İşlem Açıklaması", "Tarih", "Tutar"};
        int[] xler = {20, 480, 700};
        for (int i = 0; i < basliklar.length; i++) {
            JLabel lbl = new JLabel(basliklar[i]);
            lbl.setFont(new Font("SansSerif", Font.BOLD, 12));
            lbl.setForeground(KOYU_MAVI);
            lbl.setBounds(xler[i], 8, 200, 20);
            baslikSatir.add(lbl);
        }

        for (int i = 0; i < islemler.length; i++) {
            JPanel satir = new JPanel(null);
            satir.setBackground(i % 2 == 0 ? BEYAZ : new Color(245, 248, 255));
            satir.setBounds(30, startY + 36 + i * 44, 900, 44);
            satir.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 235, 245)));
            icerikPanel.add(satir);

            JLabel lblAciklama = new JLabel(islemler[i][0]);
            lblAciklama.setFont(new Font("SansSerif", Font.PLAIN, 13));
            lblAciklama.setForeground(new Color(40, 40, 40));
            lblAciklama.setBounds(20, 12, 420, 20);
            satir.add(lblAciklama);

            JLabel lblTarih = new JLabel(islemler[i][1]);
            lblTarih.setFont(new Font("SansSerif", Font.PLAIN, 12));
            lblTarih.setForeground(Color.GRAY);
            lblTarih.setBounds(480, 12, 200, 20);
            satir.add(lblTarih);

            JLabel lblTutar = new JLabel(islemler[i][2]);
            lblTutar.setFont(new Font("SansSerif", Font.BOLD, 13));
            lblTutar.setForeground(islemler[i][3].equals("yesil") ? new Color(0, 140, 60) : new Color(200, 30, 30));
            lblTutar.setBounds(700, 12, 180, 20);
            satir.add(lblTutar);
        }
    }
}