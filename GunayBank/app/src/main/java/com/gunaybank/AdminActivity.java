package com.gunaybank;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDate;
import java.util.List;

public class AdminActivity extends AppCompatActivity {

    private DatabaseHelper db;

    private LinearLayout panelGenel, panelKullanicilar, panelEkle;
    private Button btnNavGenel, btnNavKullanicilar, btnNavEkle;

    // Genel bakış
    private TextView tvToplamKullanici;

    // Kullanıcılar listesi
    private LinearLayout llKullaniciListesi;

    // Kullanıcı ekle
    private EditText etAd, etSifre, etEmail, etTelefon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        db = new DatabaseHelper(this);

        // Navbar
        Button btnCikis = findViewById(R.id.btnCikis);
        btnCikis.setOnClickListener(v -> {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });

        // Panel referansları
        panelGenel        = findViewById(R.id.panelGenel);
        panelKullanicilar = findViewById(R.id.panelKullanicilar);
        panelEkle         = findViewById(R.id.panelEkle);

        btnNavGenel        = findViewById(R.id.btnNavGenel);
        btnNavKullanicilar = findViewById(R.id.btnNavKullanicilar);
        btnNavEkle         = findViewById(R.id.btnNavEkle);

        tvToplamKullanici  = findViewById(R.id.tvToplamKullanici);
        llKullaniciListesi = findViewById(R.id.llKullaniciListesi);

        etAd      = findViewById(R.id.etAd);
        etSifre   = findViewById(R.id.etSifre);
        etEmail   = findViewById(R.id.etEmail);
        etTelefon = findViewById(R.id.etTelefon);

        Button btnEkle = findViewById(R.id.btnEkle);
        btnEkle.setOnClickListener(v -> kullaniciEkle());

        // Navbar tıklamaları
        btnNavGenel.setOnClickListener(v -> {
            panelGoster(panelGenel);
            genelDoldur();
        });
        btnNavKullanicilar.setOnClickListener(v -> {
            panelGoster(panelKullanicilar);
            kullanicilariDoldur();
        });
        btnNavEkle.setOnClickListener(v -> panelGoster(panelEkle));

        // Başlangıç
        panelGoster(panelGenel);
        genelDoldur();
    }

    private void panelGoster(LinearLayout hedef) {
        panelGenel.setVisibility(View.GONE);
        panelKullanicilar.setVisibility(View.GONE);
        panelEkle.setVisibility(View.GONE);
        hedef.setVisibility(View.VISIBLE);
    }

    // ── GENEL BAKIŞ ──
    private void genelDoldur() {
        List<Kullanici> liste = db.tumKullanicilar();
        tvToplamKullanici.setText(String.valueOf(liste.size()));
    }

    // ── KULLANICILAR ──
    private void kullanicilariDoldur() {
        llKullaniciListesi.removeAllViews();
        List<Kullanici> liste = db.tumKullanicilar();

        if (liste.isEmpty()) {
            TextView tv = new TextView(this);
            tv.setText("Henüz kayıtlı kullanıcı yok.");
            tv.setTextColor(0xFF888888);
            tv.setPadding(16, 16, 16, 16);
            llKullaniciListesi.addView(tv);
            return;
        }

        for (Kullanici k : liste) {
            // Satır oluştur
            LinearLayout satir = new LinearLayout(this);
            satir.setOrientation(LinearLayout.HORIZONTAL);
            satir.setPadding(16, 20, 16, 20);
            satir.setBackgroundColor(0xFFFFFFFF);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 0, 0, 2);
            satir.setLayoutParams(params);

            // Bilgi sütunu
            LinearLayout bilgiCol = new LinearLayout(this);
            bilgiCol.setOrientation(LinearLayout.VERTICAL);
            LinearLayout.LayoutParams bilgiParams = new LinearLayout.LayoutParams(0,
                    LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
            bilgiCol.setLayoutParams(bilgiParams);

            TextView tvAd = new TextView(this);
            tvAd.setText(k.adSoyad);
            tvAd.setTextColor(0xFF1A1A2E);
            tvAd.setTextSize(14);
            tvAd.setTypeface(null, android.graphics.Typeface.BOLD);
            bilgiCol.addView(tvAd);

            TextView tvNo = new TextView(this);
            tvNo.setText("Müşteri No: " + k.musteriNo + "  |  " + k.kayitTarihi);
            tvNo.setTextColor(0xFF888888);
            tvNo.setTextSize(12);
            bilgiCol.addView(tvNo);

            satir.addView(bilgiCol);

            // Sil butonu
            Button btnSil = new Button(this);
            btnSil.setText("Sil");
            btnSil.setTextColor(0xFFFFFFFF);
            btnSil.setBackgroundColor(0xFFC82828);
            btnSil.setTextSize(12);
            LinearLayout.LayoutParams btnParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            btnParams.setMargins(8, 0, 0, 0);
            btnSil.setLayoutParams(btnParams);

            btnSil.setOnClickListener(v -> {
                new AlertDialog.Builder(this)
                        .setTitle("Kullanıcı Sil")
                        .setMessage(k.adSoyad + " adlı kullanıcıyı silmek istediğinize emin misiniz?")
                        .setPositiveButton("Evet", (d, w) -> {
                            db.sil(k.musteriNo);
                            kullanicilariDoldur();
                        })
                        .setNegativeButton("Hayır", null)
                        .show();
            });

            satir.addView(btnSil);
            llKullaniciListesi.addView(satir);
        }
    }

    // ── KULLANICI EKLE ──
    private void kullaniciEkle() {
        String ad    = etAd.getText().toString().trim();
        String sifre = etSifre.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String tel   = etTelefon.getText().toString().trim();

        if (ad.isEmpty() || sifre.isEmpty()) {
            Toast.makeText(this, "Zorunlu (*) alanları doldurunuz.", Toast.LENGTH_SHORT).show();
            return;
        }

        String no    = db.yeniMusteriNo();
        String tarih = LocalDate.now().toString();

        Kullanici yeni = new Kullanici(ad, no, sifre, email, tel, tarih, 5000.0, 1000.0, 100.0);
        if (db.kaydet(yeni)) {
            new AlertDialog.Builder(this)
                    .setTitle("Başarılı")
                    .setMessage(ad + " adlı kullanıcı eklendi.\nMüşteri No: " + no)
                    .setPositiveButton("Tamam", null)
                    .show();
            etAd.setText("");
            etSifre.setText("");
            etEmail.setText("");
            etTelefon.setText("");
        } else {
            Toast.makeText(this, "Ekleme sırasında hata oluştu!", Toast.LENGTH_SHORT).show();
        }
    }
}