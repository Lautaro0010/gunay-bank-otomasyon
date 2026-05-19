package gunay_banka;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;

public class KayitEkrani extends JFrame {

    private static final Color KOYU_MAVI = new Color(0, 71, 148);
    private static final Color ACIK_MAVI = new Color(0, 153, 224);
    private static final Color BEYAZ     = Color.WHITE;

    public KayitEkrani() {
        setTitle("Günay Bank - Kayıt Ol");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(580, 660);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel anaPanel = new JPanel(null);
        anaPanel.setBackground(new Color(235, 242, 252));
        setContentPane(anaPanel);

        // Üst bant
        JPanel ust = new JPanel(null);
        ust.setBackground(KOYU_MAVI);
        ust.setBounds(0, 0, 580, 60);
        anaPanel.add(ust);

        JLabel lblLogo = new JLabel("Günay Bank");
        lblLogo.setFont(new Font("Serif", Font.BOLD, 22));
        lblLogo.setForeground(BEYAZ);
        lblLogo.setBounds(20, 10, 200, 30);
        ust.add(lblLogo);

        JLabel lblAlt = new JLabel("Yeni Hesap Oluştur");
        lblAlt.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lblAlt.setForeground(new Color(200, 220, 255));
        lblAlt.setBounds(20, 38, 200, 16);
        ust.add(lblAlt);

        // Hoş geldin notu
        JPanel notPanel = new JPanel(null);
        notPanel.setBackground(new Color(220, 240, 220));
        notPanel.setBounds(25, 68, 530, 36);
        notPanel.setBorder(BorderFactory.createLineBorder(new Color(150, 200, 150), 1));
        anaPanel.add(notPanel);

        JLabel lblNot = new JLabel("🎁  Kayıt olanlara: 5.000 TL vadesiz + 1.000 TL birikim + 100 $ döviz hesabı açılır!");
        lblNot.setFont(new Font("SansSerif", Font.BOLD, 11));
        lblNot.setForeground(new Color(30, 100, 30));
        lblNot.setBounds(10, 8, 510, 20);
        notPanel.add(lblNot);

        // Form kartı
        JPanel kart = new JPanel(null);
        kart.setBackground(BEYAZ);
        kart.setBounds(25, 112, 530, 500);
        kart.setBorder(BorderFactory.createLineBorder(new Color(200, 215, 240), 1));
        anaPanel.add(kart);

        // Ad Soyad
        JLabel lblAd = new JLabel("Ad Soyad *");
        lblAd.setFont(new Font("SansSerif", Font.PLAIN, 13));
        lblAd.setForeground(new Color(60, 60, 80));
        lblAd.setBounds(30, 18, 200, 22);
        kart.add(lblAd);
        JTextField txtAd = new JTextField();
        txtAd.setBounds(30, 42, 460, 34);
        txtAd.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(160, 190, 230), 1),
            BorderFactory.createEmptyBorder(4, 8, 4, 8)));
        txtAd.setFont(new Font("SansSerif", Font.PLAIN, 14));
        kart.add(txtAd);

        // Müşteri No
        JLabel lblNo = new JLabel("Müşteri No (Otomatik)");
        lblNo.setFont(new Font("SansSerif", Font.PLAIN, 13));
        lblNo.setForeground(new Color(60, 60, 80));
        lblNo.setBounds(30, 88, 220, 22);
        kart.add(lblNo);
        String yeniNo = KullaniciBankasi.yeniMusteriNo();
        JTextField txtNo = new JTextField(yeniNo);
        txtNo.setBounds(30, 112, 460, 34);
        txtNo.setEditable(false);
        txtNo.setBackground(new Color(240, 245, 255));
        txtNo.setForeground(KOYU_MAVI);
        txtNo.setFont(new Font("Monospaced", Font.BOLD, 14));
        txtNo.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(160, 190, 230), 1),
            BorderFactory.createEmptyBorder(4, 8, 4, 8)));
        kart.add(txtNo);

        // Şifre
        JLabel lblSifre = new JLabel("Şifre *");
        lblSifre.setFont(new Font("SansSerif", Font.PLAIN, 13));
        lblSifre.setForeground(new Color(60, 60, 80));
        lblSifre.setBounds(30, 158, 200, 22);
        kart.add(lblSifre);
        JPasswordField txtSifre = new JPasswordField();
        txtSifre.setBounds(30, 182, 460, 34);
        txtSifre.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(160, 190, 230), 1),
            BorderFactory.createEmptyBorder(4, 8, 4, 8)));
        kart.add(txtSifre);

        // Şifre Tekrar
        JLabel lblSifre2 = new JLabel("Şifre Tekrar *");
        lblSifre2.setFont(new Font("SansSerif", Font.PLAIN, 13));
        lblSifre2.setForeground(new Color(60, 60, 80));
        lblSifre2.setBounds(30, 228, 200, 22);
        kart.add(lblSifre2);
        JPasswordField txtSifre2 = new JPasswordField();
        txtSifre2.setBounds(30, 252, 460, 34);
        txtSifre2.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(160, 190, 230), 1),
            BorderFactory.createEmptyBorder(4, 8, 4, 8)));
        kart.add(txtSifre2);

        // E-posta
        JLabel lblEmail = new JLabel("E-posta");
        lblEmail.setFont(new Font("SansSerif", Font.PLAIN, 13));
        lblEmail.setForeground(new Color(60, 60, 80));
        lblEmail.setBounds(30, 298, 200, 22);
        kart.add(lblEmail);
        JTextField txtEmail = new JTextField();
        txtEmail.setBounds(30, 322, 460, 34);
        txtEmail.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(160, 190, 230), 1),
            BorderFactory.createEmptyBorder(4, 8, 4, 8)));
        txtEmail.setFont(new Font("SansSerif", Font.PLAIN, 14));
        kart.add(txtEmail);

        // Telefon
        JLabel lblTel = new JLabel("Telefon");
        lblTel.setFont(new Font("SansSerif", Font.PLAIN, 13));
        lblTel.setForeground(new Color(60, 60, 80));
        lblTel.setBounds(30, 368, 200, 22);
        kart.add(lblTel);
        JTextField txtTel = new JTextField();
        txtTel.setBounds(30, 392, 460, 34);
        txtTel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(160, 190, 230), 1),
            BorderFactory.createEmptyBorder(4, 8, 4, 8)));
        txtTel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        // Sadece rakam, max 11 karakter
        ((javax.swing.text.AbstractDocument) txtTel.getDocument()).setDocumentFilter(
            new javax.swing.text.DocumentFilter() {
                public void insertString(FilterBypass fb, int off, String str, javax.swing.text.AttributeSet a) throws javax.swing.text.BadLocationException {
                    String temiz = str.replaceAll("[^0-9]", "");
                    if (fb.getDocument().getLength() + temiz.length() <= 11)
                        super.insertString(fb, off, temiz, a);
                }
                public void replace(FilterBypass fb, int off, int len, String str, javax.swing.text.AttributeSet a) throws javax.swing.text.BadLocationException {
                    String temiz = str.replaceAll("[^0-9]", "");
                    if (fb.getDocument().getLength() - len + temiz.length() <= 11)
                        super.replace(fb, off, len, temiz, a);
                }
            }
        );
        kart.add(txtTel);

        // Butonlar
        JButton btnKayit = new JButton("Kayıt Ol");
        btnKayit.setBounds(30, 448, 150, 38);
        btnKayit.setBackground(ACIK_MAVI);
        btnKayit.setForeground(BEYAZ);
        btnKayit.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnKayit.setFocusPainted(false);
        btnKayit.setBorderPainted(false);
        btnKayit.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        kart.add(btnKayit);

        JLabel lblGeri = new JLabel("<html><u>Giriş sayfasına dön</u></html>");
        lblGeri.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lblGeri.setForeground(ACIK_MAVI);
        lblGeri.setBounds(210, 457, 180, 22);
        lblGeri.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblGeri.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) { dispose(); }
        });
        kart.add(lblGeri);

        btnKayit.addActionListener(e -> {
            String ad     = txtAd.getText().trim();
            String sifre  = new String(txtSifre.getPassword()).trim();
            String sifre2 = new String(txtSifre2.getPassword()).trim();
            String email  = txtEmail.getText().trim();
            String tel    = txtTel.getText().trim();

            if (ad.isEmpty() || sifre.isEmpty() || sifre2.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Zorunlu (*) alanları doldurunuz.", "Uyarı", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (!email.isEmpty() && !email.contains("@")) {
                JOptionPane.showMessageDialog(this, "Geçerli bir e-posta adresi giriniz. (@  işareti eksik)", "Hata", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!sifre.equals(sifre2)) {
                JOptionPane.showMessageDialog(this, "Şifreler eşleşmiyor!", "Hata", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (sifre.length() < 4) {
                JOptionPane.showMessageDialog(this, "Şifre en az 4 karakter olmalıdır.", "Hata", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Başlangıç bakiyeleri ile kaydet
            KullaniciBankasi.Kullanici yeni = new KullaniciBankasi.Kullanici(
                ad, yeniNo, sifre, email, tel,
                LocalDate.now().toString(),
                5000.0,   // vadesiz
                1000.0,   // birikim
                100.0     // döviz ($)
            );

            if (KullaniciBankasi.kaydet(yeni)) {
                JOptionPane.showMessageDialog(this,
                    "Kayıt başarılı! Hoş geldiniz, " + ad + "!\n\n" +
                    "Müşteri Numaranız : " + yeniNo + "\n" +
                    "Vadesiz Hesap     : 5.000,00 TL\n" +
                    "Birikim Hesabı    : 1.000,00 TL\n" +
                    "Döviz Hesabı      : 100,00 $\n\n" +
                    "Bu numara ile giriş yapabilirsiniz.",
                    "Kayıt Tamamlandı", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Kayıt sırasında bir hata oluştu!", "Hata", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}