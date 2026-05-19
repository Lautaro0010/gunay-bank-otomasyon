package com.gunaybank;

public class Kullanici {
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
        this.email       = email  != null ? email   : "";
        this.telefon     = telefon != null ? telefon : "";
        this.kayitTarihi = kayitTarihi != null ? kayitTarihi : "";
        this.vadesiz     = vadesiz;
        this.birikim     = birikim;
        this.doviz       = doviz;
    }
}