package com.gunaybank;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class HomeActivity extends AppCompatActivity {

    private DatabaseHelper db;
    private Kullanici kullanici;

    // Navbar butonları
    private Button btnAnaSayfa, btnBakiye, btnTransfer, btnProfil;

    // Tüm paneller
    private LinearLayout panelAnaSayfa, panelBakiye, panelTransfer, panelProfil;

    // Ana sayfa
    private TextView tvAnaSayfaVadesiz, tvAnaSayfaBirikim, tvAnaSayfaDoviz;

    // Bakiye
    private TextView tvBakiyeVadesiz, tvBakiyeBirikim, tvBakiyeDoviz;

    // Transfer
    private EditText etAliciAd, etAliciIban, etTutar, etAciklama;
    private TextView tvTransferMevcut;

    // Profil
    private TextView tvProfilAd, tvProfilNo, tvProfilEmail, tvProfilTel,
            tvProfilTarih, tvProfilToplam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        db = new DatabaseHelper(this);
        kullaniciYukle();

        baglaView();
        navbarAyarla();
        panelGoster(panelAnaSayfa);
        anaSayfaDoldur();
    }

    private void kullaniciYukle() {
        Intent i = getIntent();
        kullanici = new Kullanici(
                i.getStringExtra("adSoyad"),
                i.getStringExtra("musteriNo"),
                "",
                i.getStringExtra("email"),
                i.getStringExtra("telefon"),
                i.getStringExtra("kayitTarihi"),
                i.getDoubleExtra("vadesiz", 0),
                i.getDoubleExtra("birikim", 0),
                i.getDoubleExtra("doviz",   0)
        );
    }

    private void baglaView() {
        btnAnaSayfa = findViewById(R.id.btnNavAnaSayfa);
        btnBakiye   = findViewById(R.id.btnNavBakiye);
        btnTransfer = findViewById(R.id.btnNavTransfer);
        btnProfil   = findViewById(R.id.btnNavProfil);

        panelAnaSayfa = findViewById(R.id.panelAnaSayfa);
        panelBakiye   = findViewById(R.id.panelBakiye);
        panelTransfer = findViewById(R.id.panelTransfer);
        panelProfil   = findViewById(R.id.panelProfil);

        // Ana sayfa
        tvAnaSayfaVadesiz = findViewById(R.id.tvAnaSayfaVadesiz);
        tvAnaSayfaBirikim = findViewById(R.id.tvAnaSayfaBirikim);
        tvAnaSayfaDoviz   = findViewById(R.id.tvAnaSayfaDoviz);

        // Bakiye
        tvBakiyeVadesiz = findViewById(R.id.tvBakiyeVadesiz);
        tvBakiyeBirikim = findViewById(R.id.tvBakiyeBirikim);
        tvBakiyeDoviz   = findViewById(R.id.tvBakiyeDoviz);

        // Transfer
        etAliciAd    = findViewById(R.id.etAliciAd);
        etAliciIban  = findViewById(R.id.etAliciIban);
        etTutar      = findViewById(R.id.etTutar);
        etAciklama   = findViewById(R.id.etAciklama);
        tvTransferMevcut = findViewById(R.id.tvTransferMevcut);

        // Profil
        tvProfilAd     = findViewById(R.id.tvProfilAd);
        tvProfilNo     = findViewById(R.id.tvProfilNo);
        tvProfilEmail  = findViewById(R.id.tvProfilEmail);
        tvProfilTel    = findViewById(R.id.tvProfilTel);
        tvProfilTarih  = findViewById(R.id.tvProfilTarih);
        tvProfilToplam = findViewById(R.id.tvProfilToplam);

        // Navbar hoş geldin + çıkış
        TextView tvHosgeldin = findViewById(R.id.tvHosgeldin);
        tvHosgeldin.setText("Hoş geldiniz, " + kullanici.adSoyad);

        Button btnCikis = findViewById(R.id.btnCikis);
        btnCikis.setOnClickListener(v -> {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });

        // Transfer gönder butonu
        Button btnGonder = findViewById(R.id.btnGonder);
        btnGonder.setOnClickListener(v -> transferYap());
    }

    private void navbarAyarla() {
        btnAnaSayfa.setOnClickListener(v -> {
            panelGoster(panelAnaSayfa);
            anaSayfaDoldur();
        });
        btnBakiye.setOnClickListener(v -> {
            panelGoster(panelBakiye);
            bakiyeDoldur();
        });
        btnTransfer.setOnClickListener(v -> {
            panelGoster(panelTransfer);
            transferDoldur();
        });
        btnProfil.setOnClickListener(v -> {
            panelGoster(panelProfil);
            profilDoldur();
        });
    }

    private void panelGoster(LinearLayout hedef) {
        panelAnaSayfa.setVisibility(View.GONE);
        panelBakiye.setVisibility(View.GONE);
        panelTransfer.setVisibility(View.GONE);
        panelProfil.setVisibility(View.GONE);
        hedef.setVisibility(View.VISIBLE);
    }

    // ── ANA SAYFA ──
    private void anaSayfaDoldur() {
        tvAnaSayfaVadesiz.setText(tlFormat(kullanici.vadesiz));
        tvAnaSayfaBirikim.setText(tlFormat(kullanici.birikim));
        tvAnaSayfaDoviz.setText(String.format(Locale.US, "$ %,.2f", kullanici.doviz));
    }

    // ── BAKİYE ──
    private void bakiyeDoldur() {
        tvBakiyeVadesiz.setText(tlFormat(kullanici.vadesiz));
        tvBakiyeBirikim.setText(tlFormat(kullanici.birikim));
        tvBakiyeDoviz.setText(String.format(Locale.US, "$ %,.2f", kullanici.doviz));
    }

    // ── TRANSFERi ──
    private void transferDoldur() {
        tvTransferMevcut.setText("Mevcut Bakiye: " + tlFormat(kullanici.vadesiz));
    }

    private void transferYap() {
        String ad      = etAliciAd.getText().toString().trim();
        String iban    = etAliciIban.getText().toString().trim();
        String tutarStr = etTutar.getText().toString().trim();

        if (ad.isEmpty() || iban.isEmpty() || tutarStr.isEmpty()) {
            Toast.makeText(this, "Lütfen zorunlu alanları doldurunuz.", Toast.LENGTH_SHORT).show();
            return;
        }

        double tutar;
        try {
            tutar = Double.parseDouble(tutarStr.replace(",", "."));
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Geçerli bir tutar giriniz.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (tutar <= 0) {
            Toast.makeText(this, "Tutar 0'dan büyük olmalıdır.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (tutar > kullanici.vadesiz) {
            Toast.makeText(this, "Yetersiz bakiye! Mevcut: " + tlFormat(kullanici.vadesiz), Toast.LENGTH_LONG).show();
            return;
        }

        kullanici.vadesiz -= tutar;
        db.bakiyeGuncelle(kullanici.musteriNo, kullanici.vadesiz, kullanici.birikim, kullanici.doviz);
        tvTransferMevcut.setText("Mevcut Bakiye: " + tlFormat(kullanici.vadesiz));

        new AlertDialog.Builder(this)
                .setTitle("Transfer Başarılı")
                .setMessage(tlFormat(tutar) + " tutarında transfer\n" +
                        ad + " adlı kişiye başarıyla gönderildi.\n\n" +
                        "Kalan Bakiye: " + tlFormat(kullanici.vadesiz))
                .setPositiveButton("Tamam", null)
                .show();

        etAliciAd.setText("");
        etAliciIban.setText("");
        etTutar.setText("");
        etAciklama.setText("");
    }

    // ── PROFİL ──
    private void profilDoldur() {
        tvProfilAd.setText(kullanici.adSoyad);
        tvProfilNo.setText(kullanici.musteriNo);
        tvProfilEmail.setText(kullanici.email.isEmpty()   ? "—" : kullanici.email);
        tvProfilTel.setText(kullanici.telefon.isEmpty()   ? "—" : kullanici.telefon);
        tvProfilTarih.setText(kullanici.kayitTarihi);
        tvProfilToplam.setText(tlFormat(kullanici.vadesiz + kullanici.birikim));
    }

    private String tlFormat(double tutar) {
        return String.format(new Locale("tr", "TR"), "₺ %,.2f", tutar);
    }
}