package com.gunaybank;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class LoginActivity extends AppCompatActivity {

    private static final String ADMIN_NO    = "admin";
    private static final String ADMIN_SIFRE = "admin123";

    private EditText etMusteriNo, etSifre, etCaptcha;
    private TextView tvCaptcha;
    private String captchaKodu;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = new DatabaseHelper(this);

        etMusteriNo = findViewById(R.id.etMusteriNo);
        etSifre     = findViewById(R.id.etSifre);
        etCaptcha   = findViewById(R.id.etCaptcha);
        tvCaptcha   = findViewById(R.id.tvCaptcha);

        Button btnGiris  = findViewById(R.id.btnGiris);
        Button btnKayit  = findViewById(R.id.btnKayit);
        TextView tvYenile = findViewById(R.id.tvYenileCaptcha);

        captchaKodu = captchaUret();
        tvCaptcha.setText(captchaKodu);

        tvYenile.setOnClickListener(v -> {
            captchaKodu = captchaUret();
            tvCaptcha.setText(captchaKodu);
            etCaptcha.setText("");
        });

        btnGiris.setOnClickListener(v -> girisYap());

        btnKayit.setOnClickListener(v -> {
            startActivity(new Intent(this, RegisterActivity.class));
        });
    }

    private void girisYap() {
        String no      = etMusteriNo.getText().toString().trim();
        String sifre   = etSifre.getText().toString().trim();
        String captcha = etCaptcha.getText().toString().trim();

        if (no.isEmpty() || sifre.isEmpty() || captcha.isEmpty()) {
            Toast.makeText(this, "Lütfen tüm alanları doldurunuz.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!captcha.equalsIgnoreCase(captchaKodu)) {
            Toast.makeText(this, "Güvenlik kodu hatalı!", Toast.LENGTH_SHORT).show();
            captchaKodu = captchaUret();
            tvCaptcha.setText(captchaKodu);
            etCaptcha.setText("");
            return;
        }

        // Admin kontrolü
        if (no.equals(ADMIN_NO) && sifre.equals(ADMIN_SIFRE)) {
            startActivity(new Intent(this, AdminActivity.class));
            finish();
            return;
        }

        // Normal kullanıcı
        Kullanici k = db.bul(no, sifre);
        if (k != null) {
            Intent intent = new Intent(this, HomeActivity.class);
            intent.putExtra("musteriNo",   k.musteriNo);
            intent.putExtra("adSoyad",     k.adSoyad);
            intent.putExtra("email",       k.email);
            intent.putExtra("telefon",     k.telefon);
            intent.putExtra("kayitTarihi", k.kayitTarihi);
            intent.putExtra("vadesiz",     k.vadesiz);
            intent.putExtra("birikim",     k.birikim);
            intent.putExtra("doviz",       k.doviz);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Müşteri No veya Şifre hatalı!", Toast.LENGTH_SHORT).show();
            captchaKodu = captchaUret();
            tvCaptcha.setText(captchaKodu);
            etCaptcha.setText("");
        }
    }

    private String captchaUret() {
        String chars = "abcdefghjkmnpqrstuvwxyz23456789";
        StringBuilder sb = new StringBuilder();
        Random rnd = new Random();
        for (int i = 0; i < 6; i++)
            sb.append(chars.charAt(rnd.nextInt(chars.length())));
        return sb.toString();
    }
}