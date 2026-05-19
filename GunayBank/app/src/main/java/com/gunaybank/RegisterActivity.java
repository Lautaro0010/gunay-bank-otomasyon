package com.gunaybank;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDate;

public class RegisterActivity extends AppCompatActivity {

    private EditText etAd, etSifre, etSifre2, etEmail, etTelefon;
    private TextView tvMusteriNo;
    private DatabaseHelper db;
    private String yeniNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        db = new DatabaseHelper(this);

        etAd      = findViewById(R.id.etAd);
        etSifre   = findViewById(R.id.etSifre);
        etSifre2  = findViewById(R.id.etSifre2);
        etEmail   = findViewById(R.id.etEmail);
        etTelefon = findViewById(R.id.etTelefon);
        tvMusteriNo = findViewById(R.id.tvMusteriNo);

        yeniNo = db.yeniMusteriNo();
        tvMusteriNo.setText(yeniNo);

        Button btnKayit = findViewById(R.id.btnKayit);
        TextView tvGeri = findViewById(R.id.tvGeri);

        btnKayit.setOnClickListener(v -> kayitOl());
        tvGeri.setOnClickListener(v -> finish());
    }

    private void kayitOl() {
        String ad     = etAd.getText().toString().trim();
        String sifre  = etSifre.getText().toString().trim();
        String sifre2 = etSifre2.getText().toString().trim();
        String email  = etEmail.getText().toString().trim();
        String tel    = etTelefon.getText().toString().trim();

        if (ad.isEmpty() || sifre.isEmpty() || sifre2.isEmpty()) {
            Toast.makeText(this, "Zorunlu (*) alanları doldurunuz.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!email.isEmpty() && !email.contains("@")) {
            Toast.makeText(this, "Geçerli bir e-posta adresi giriniz.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!sifre.equals(sifre2)) {
            Toast.makeText(this, "Şifreler eşleşmiyor!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (sifre.length() < 4) {
            Toast.makeText(this, "Şifre en az 4 karakter olmalıdır.", Toast.LENGTH_SHORT).show();
            return;
        }

        String tarih = LocalDate.now().toString();
        Kullanici yeni = new Kullanici(ad, yeniNo, sifre, email, tel, tarih, 5000.0, 1000.0, 100.0);

        if (db.kaydet(yeni)) {
            new AlertDialog.Builder(this)
                    .setTitle("Kayıt Tamamlandı")
                    .setMessage("Hoş geldiniz, " + ad + "!\n\n" +
                            "Müşteri Numaranız : " + yeniNo + "\n" +
                            "Vadesiz Hesap     : 5.000,00 TL\n" +
                            "Birikim Hesabı    : 1.000,00 TL\n" +
                            "Döviz Hesabı      : 100,00 $\n\n" +
                            "Bu numara ile giriş yapabilirsiniz.")
                    .setPositiveButton("Tamam", (d, w) -> finish())
                    .show();
        } else {
            Toast.makeText(this, "Kayıt sırasında bir hata oluştu!", Toast.LENGTH_SHORT).show();
        }
    }
}