package gunay_banka;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class BankaGiris extends JFrame {

    private JTextField txtNo;
    private JPasswordField txtSifre;
    private JTextField txtCaptcha;
    private JLabel lblCaptchaImg;
    private String captchaKodu;

    private static final Color KOYU_MAVI = new Color(0, 71, 148);
    private static final Color ACIK_MAVI = new Color(0, 153, 224);
    private static final Color BEYAZ     = Color.WHITE;

    // Admin bilgileri
    private static final String ADMIN_NO    = "admin";
    private static final String ADMIN_SIFRE = "admin123";

    public BankaGiris() {
        setTitle("Günay Bank - İnternet Şubesi");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1050, 720);
        setLocationRelativeTo(null);
        setResizable(false);

        MaviArkaPanel anaPanel = new MaviArkaPanel();
        anaPanel.setLayout(null);
        setContentPane(anaPanel);

        // NAVBAR
        JPanel navbar = new JPanel(null);
        navbar.setOpaque(false);
        navbar.setBounds(0, 0, 1050, 60);
        anaPanel.add(navbar);

        JLabel lblLogo = new JLabel("Günay Bank");
        lblLogo.setFont(new Font("Serif", Font.BOLD, 28));
        lblLogo.setForeground(BEYAZ);
        lblLogo.setBounds(30, 12, 220, 36);
        navbar.add(lblLogo);

        JLabel lblEng = new JLabel("English");
        lblEng.setFont(new Font("SansSerif", Font.PLAIN, 13));
        lblEng.setForeground(new Color(200, 220, 255));
        lblEng.setBounds(840, 20, 65, 22);
        navbar.add(lblEng);

        JLabel lblTr = new JLabel("Türkçe");
        lblTr.setFont(new Font("SansSerif", Font.BOLD, 13));
        lblTr.setForeground(BEYAZ);
        lblTr.setBounds(915, 20, 65, 22);
        navbar.add(lblTr);

        // BEYAZ KART
        JPanel beyazKart = new JPanel(null);
        beyazKart.setBackground(BEYAZ);
        beyazKart.setBounds(30, 75, 990, 600);
        beyazKart.setBorder(BorderFactory.createLineBorder(new Color(200, 215, 240), 1));
        anaPanel.add(beyazKart);

        JLabel lblAlt1 = new JLabel("Günay Bank");
        lblAlt1.setFont(new Font("Serif", Font.BOLD, 20));
        lblAlt1.setForeground(KOYU_MAVI);
        lblAlt1.setBounds(25, 12, 220, 28);
        beyazKart.add(lblAlt1);

        JLabel lblAlt2 = new JLabel("Banka İnternet Şubesine Hoş Geldiniz");
        lblAlt2.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblAlt2.setForeground(KOYU_MAVI);
        lblAlt2.setBounds(25, 38, 400, 22);
        beyazKart.add(lblAlt2);

        // GİRİŞ KUTUSU
        JPanel girisKutu = new JPanel(null);
        girisKutu.setBounds(20, 72, 650, 500);
        girisKutu.setBackground(BEYAZ);
        girisKutu.setBorder(BorderFactory.createLineBorder(new Color(180, 200, 235), 1));
        beyazKart.add(girisKutu);

        // Sekme
        JPanel sekmePanel = new JPanel(null);
        sekmePanel.setBounds(0, 0, 650, 50);
        sekmePanel.setBackground(BEYAZ);
        sekmePanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(180, 200, 235)));
        girisKutu.add(sekmePanel);

        JLabel lblLogoSekme = new JLabel("✦");
        lblLogoSekme.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblLogoSekme.setForeground(KOYU_MAVI);
        lblLogoSekme.setBounds(15, 13, 20, 24);
        sekmePanel.add(lblLogoSekme);

        JLabel lblSekmeAdi = new JLabel("Günay Bireysel Şube");
        lblSekmeAdi.setFont(new Font("SansSerif", Font.BOLD, 13));
        lblSekmeAdi.setForeground(KOYU_MAVI);
        lblSekmeAdi.setBounds(38, 13, 185, 24);
        sekmePanel.add(lblSekmeAdi);

        JPanel aktifCizgi = new JPanel();
        aktifCizgi.setBounds(0, 47, 230, 3);
        aktifCizgi.setBackground(KOYU_MAVI);
        sekmePanel.add(aktifCizgi);

        int sol = 90, genislik = 300;

        // Müşteri No
        JLabel lblNo = new JLabel("Müşteri / T.C. Kimlik No:");
        lblNo.setFont(new Font("SansSerif", Font.PLAIN, 13));
        lblNo.setBounds(sol, 70, 220, 25);
        girisKutu.add(lblNo);

        txtNo = new JTextField();
        txtNo.setBounds(sol, 98, genislik, 36);
        txtNo.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(160, 190, 230), 1),
            BorderFactory.createEmptyBorder(4, 8, 4, 8)));
        txtNo.setFont(new Font("SansSerif", Font.PLAIN, 14));
        girisKutu.add(txtNo);

        JLabel lblSoru = new JLabel("?");
        lblSoru.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblSoru.setForeground(ACIK_MAVI);
        lblSoru.setBounds(sol + genislik + 10, 104, 20, 22);
        girisKutu.add(lblSoru);

        // Şifre
        JLabel lblSifre = new JLabel("Mobil / İnternet Şube Şifresi:");
        lblSifre.setFont(new Font("SansSerif", Font.PLAIN, 13));
        lblSifre.setBounds(sol, 153, 230, 25);
        girisKutu.add(lblSifre);

        txtSifre = new JPasswordField();
        txtSifre.setBounds(sol, 181, genislik, 36);
        txtSifre.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(160, 190, 230), 1),
            BorderFactory.createEmptyBorder(4, 8, 4, 8)));
        girisKutu.add(txtSifre);

        // CAPTCHA
        JLabel lblGKod = new JLabel("Güvenlik Kodu:");
        lblGKod.setFont(new Font("SansSerif", Font.PLAIN, 13));
        lblGKod.setBounds(sol, 236, 150, 25);
        girisKutu.add(lblGKod);

        captchaKodu = captchaUret();
        lblCaptchaImg = new JLabel(captchaKodu);
        lblCaptchaImg.setFont(new Font("Courier New", Font.BOLD | Font.ITALIC, 22));
        lblCaptchaImg.setForeground(new Color(30, 60, 140));
        lblCaptchaImg.setBounds(sol, 264, 170, 38);
        lblCaptchaImg.setBorder(BorderFactory.createLineBorder(new Color(160, 190, 230), 1));
        lblCaptchaImg.setOpaque(true);
        lblCaptchaImg.setBackground(new Color(240, 245, 255));
        lblCaptchaImg.setHorizontalAlignment(SwingConstants.CENTER);
        girisKutu.add(lblCaptchaImg);

        JLabel lblYenile = new JLabel("↻");
        lblYenile.setFont(new Font("SansSerif", Font.PLAIN, 22));
        lblYenile.setForeground(ACIK_MAVI);
        lblYenile.setBounds(sol + 178, 266, 30, 30);
        lblYenile.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblYenile.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                captchaKodu = captchaUret();
                lblCaptchaImg.setText(captchaKodu);
                txtCaptcha.setText("");
            }
        });
        girisKutu.add(lblYenile);

        txtCaptcha = new JTextField();
        txtCaptcha.setBounds(sol, 310, genislik, 36);
        txtCaptcha.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(160, 190, 230), 1),
            BorderFactory.createEmptyBorder(4, 8, 4, 8)));
        txtCaptcha.setFont(new Font("SansSerif", Font.PLAIN, 14));
        girisKutu.add(txtCaptcha);

        // Şifremi unuttum
        JLabel lblUnutum = new JLabel("<html><u>Şifre Al / Şifremi Unuttum</u></html>");
        lblUnutum.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lblUnutum.setForeground(ACIK_MAVI);
        lblUnutum.setBounds(sol, 360, 210, 22);
        lblUnutum.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        girisKutu.add(lblUnutum);

        // Giriş butonu
        JButton btnGiris = new JButton("🔒  Güvenli Giriş");
        btnGiris.setBounds(sol, 395, 200, 44);
        btnGiris.setBackground(ACIK_MAVI);
        btnGiris.setForeground(BEYAZ);
        btnGiris.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnGiris.setFocusPainted(false);
        btnGiris.setBorderPainted(false);
        btnGiris.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        girisKutu.add(btnGiris);

        // Kayıt Ol butonu
        JButton btnKayit = new JButton("Kayıt Ol");
        btnKayit.setBounds(sol + 215, 395, 130, 44);
        btnKayit.setBackground(new Color(0, 71, 148));
        btnKayit.setForeground(BEYAZ);
        btnKayit.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnKayit.setFocusPainted(false);
        btnKayit.setBorderPainted(false);
        btnKayit.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        girisKutu.add(btnKayit);

        // Buton aksiyonları
        btnGiris.addActionListener(e -> girisYap());
        txtCaptcha.addActionListener(e -> girisYap()); // Enter'a basınca da çalışır

        btnKayit.addActionListener(e -> {
            new KayitEkrani().setVisible(true);
        });
    }

    private void girisYap() {
        String no       = txtNo.getText().trim();
        String sifre    = new String(txtSifre.getPassword()).trim();
        String gCaptcha = txtCaptcha.getText().trim();

        if (no.isEmpty() || sifre.isEmpty() || gCaptcha.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Lütfen tüm alanları doldurunuz.", "Uyarı", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!gCaptcha.equalsIgnoreCase(captchaKodu)) {
            JOptionPane.showMessageDialog(this, "Güvenlik kodu hatalı!", "Hata", JOptionPane.ERROR_MESSAGE);
            captchaKodu = captchaUret();
            lblCaptchaImg.setText(captchaKodu);
            txtCaptcha.setText("");
            return;
        }

        // Admin kontrolü
        if (no.equals(ADMIN_NO) && sifre.equals(ADMIN_SIFRE)) {
            new AdminEkrani().setVisible(true);
            dispose();
            return;
        }

        // Normal kullanıcı kontrolü
        KullaniciBankasi.Kullanici k = KullaniciBankasi.bul(no, sifre);
        if (k != null) {
            new HesapEkrani(k).setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Müşteri No veya Şifre hatalı!", "Hata", JOptionPane.ERROR_MESSAGE);
            captchaKodu = captchaUret();
            lblCaptchaImg.setText(captchaKodu);
            txtCaptcha.setText("");
        }
    }

    private String captchaUret() {
        String chars = "abcdefghjkmnpqrstuvwxyz23456789";
        StringBuilder sb = new StringBuilder();
        Random rnd = new Random();
        for (int i = 0; i < 7; i++)
            sb.append(chars.charAt(rnd.nextInt(chars.length())));
        return sb.toString();
    }

    static class MaviArkaPanel extends JPanel {
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            GradientPaint gp = new GradientPaint(0, 0, new Color(0, 71, 148), getWidth(), getHeight(), new Color(0, 40, 100));
            g2.setPaint(gp);
            g2.fillRect(0, 0, getWidth(), getHeight());
            g2.setColor(new Color(255, 255, 255, 18));
            g2.setStroke(new BasicStroke(1.2f));
            for (int i = 0; i < 10; i++) {
                g2.drawLine(550 + i * 55, 0, 850 + i * 55, 720);
                g2.drawLine(550 + i * 55, 0, 350 + i * 30, 720);
            }
        }
    }

    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception ignored) {}
        KullaniciBankasi.tabloOlustur();
        SwingUtilities.invokeLater(() -> new BankaGiris().setVisible(true));
    }
}