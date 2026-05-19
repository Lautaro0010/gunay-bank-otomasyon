package com.gunaybank;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME    = "gunaybank.db";
    private static final int    DB_VERSION = 1;
    private static final String TABLE      = "kullanicilar";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE + " (" +
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
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
        onCreate(db);
    }

    public String yeniMusteriNo() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(
                "SELECT musteriNo FROM " + TABLE +
                        " ORDER BY CAST(musteriNo AS INTEGER) DESC LIMIT 1", null);
        if (c.moveToFirst()) {
            try {
                int sonNo = Integer.parseInt(c.getString(0));
                c.close();
                return String.valueOf(sonNo + 1);
            } catch (NumberFormatException e) { c.close(); }
        } else { c.close(); }
        return "10001";
    }

    public boolean kaydet(Kullanici k) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("musteriNo",   k.musteriNo);
        cv.put("adSoyad",     k.adSoyad);
        cv.put("sifre",       k.sifre);
        cv.put("email",       k.email);
        cv.put("telefon",     k.telefon);
        cv.put("kayitTarihi", k.kayitTarihi);
        cv.put("vadesiz",     k.vadesiz);
        cv.put("birikim",     k.birikim);
        cv.put("doviz",       k.doviz);
        try {
            long result = db.insertOrThrow(TABLE, null, cv);
            return result != -1;
        } catch (Exception e) { return false; }
    }

    public Kullanici bul(String musteriNo, String sifre) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(TABLE, null,
                "musteriNo=? AND sifre=?",
                new String[]{musteriNo, sifre},
                null, null, null);
        if (c.moveToFirst()) {
            Kullanici k = cursordenOku(c);
            c.close();
            return k;
        }
        c.close();
        return null;
    }

    public List<Kullanici> tumKullanicilar() {
        List<Kullanici> liste = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(TABLE, null, null, null, null, null, "kayitTarihi DESC");
        while (c.moveToNext()) { liste.add(cursordenOku(c)); }
        c.close();
        return liste;
    }

    public boolean bakiyeGuncelle(String musteriNo, double vadesiz, double birikim, double doviz) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("vadesiz", vadesiz);
        cv.put("birikim", birikim);
        cv.put("doviz",   doviz);
        return db.update(TABLE, cv, "musteriNo=?", new String[]{musteriNo}) > 0;
    }

    public boolean sil(String musteriNo) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(TABLE, "musteriNo=?", new String[]{musteriNo}) > 0;
    }

    private Kullanici cursordenOku(Cursor c) {
        return new Kullanici(
                c.getString(c.getColumnIndexOrThrow("adSoyad")),
                c.getString(c.getColumnIndexOrThrow("musteriNo")),
                c.getString(c.getColumnIndexOrThrow("sifre")),
                c.getString(c.getColumnIndexOrThrow("email")),
                c.getString(c.getColumnIndexOrThrow("telefon")),
                c.getString(c.getColumnIndexOrThrow("kayitTarihi")),
                c.getDouble(c.getColumnIndexOrThrow("vadesiz")),
                c.getDouble(c.getColumnIndexOrThrow("birikim")),
                c.getDouble(c.getColumnIndexOrThrow("doviz"))
        );
    }
}