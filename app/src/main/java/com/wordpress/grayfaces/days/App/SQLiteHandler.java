package com.wordpress.grayfaces.days.App;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Project LamHongDMS
 * Created by pcquy on 3/25/2017.
 */

public class SQLiteHandler  extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Days";
    public SQLiteHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE `HT_CONFIG` ( NGUOIDUNG INTEGER, TAIKHOAN NVARCHAR(100), MATKHAU NVARCHAR(100), HOTEN NVARCHAR(100), MANHANSU INTEGER, NGUOIDUNG_NHANSU INTEGER, NHOMNGUOIDUNG INTEGER, TENNHOMNGUOIDUNG NVARCHAR(100), WIDTH INTEGER, HEIGHT INTEGER, QUALITY INTEGER, SAISOVITRI INTEGER, ISCHECKIN INTEGER, ISCHECKOUT INTEGER, MaxGetLocation INTEGER, URLHINH NVARCHAR(250), KEY NVARCHAR(350), PRIMARY KEY(`TAIKHOAN`) )");
        db.execSQL("CREATE TABLE IF NOT EXISTS  Anniversary ( 'ID' INTEGER, 'Day' NVARCHAR(150)  NULL,PRIMARY KEY(`ID`) )");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS HT_CONFIG");
        onCreate(db);
    }
    /*public void addConfig(Config config) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("HT_CONFIG", null, null);
        ContentValues values = new ContentValues();
        values.put("NGUOIDUNG", config.getNGUOIDUNG());
        values.put("TAIKHOAN", config.getTAIKHOAN());
        values.put("MATKHAU", config.getMATKHAU());
        values.put("HOTEN", config.getHOTEN());
        values.put("MANHANSU", config.getMANHANSU());
        values.put("NGUOIDUNG_NHANSU",config.getNGUOIDUNG_NHANSU());
        values.put("NHOMNGUOIDUNG", config.getNHOMNGUOIDUNG());
        values.put("TENNHOMNGUOIDUNG", config.getTENNHOMNGUOIDUNG());
        values.put("WIDTH", config.getWIDTH());
        values.put("HEIGHT", config.getHEIGHT());
        values.put("QUALITY", config.getQUALITY());
        values.put("SAISOVITRI", config.getSAISOVITRI());
        values.put("ISCHECKIN", config.getISCHECKIN());
        values.put("ISCHECKOUT", config.getISCHECKOUT());
        values.put("MaxGetLocation",config.getMaxGetLocation());
        values.put("URLHINH", config.getURLHINH());
        values.put("KEY", config.getKEY());
        db.insert("HT_CONFIG", null, values);
        db.close();
    }
    public int updateConfig(Config config) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("MATKHAU", config.getMATKHAU());
        values.put("HOTEN", config.getHOTEN());
        values.put("MANHANSU", config.getMANHANSU());
        values.put("NGUOIDUNG_NHANSU",config.getNGUOIDUNG_NHANSU());
        values.put("NHOMNGUOIDUNG", config.getNHOMNGUOIDUNG());
        values.put("TENNHOMNGUOIDUNG", config.getTENNHOMNGUOIDUNG());
        values.put("WIDTH", config.getWIDTH());
        values.put("HEIGHT", config.getHEIGHT());
        values.put("QUALITY", config.getQUALITY());
        values.put("SAISOVITRI", config.getSAISOVITRI());
        values.put("ISCHECKIN", config.getISCHECKIN());
        values.put("ISCHECKOUT", config.getISCHECKOUT());
        values.put("MaxGetLocation",config.getMaxGetLocation());
        values.put("URLHINH", config.getURLHINH());
        values.put("KEY", config.getKEY());
        return db.update("HT_CONFIG", values, "TAIKHOAN" + " = ?",
                new String[] { config.getTAIKHOAN() });
    }
    public Config getConfigDetails() {
        Config config=new Config();
        String selectQuery = "SELECT  * FROM HT_CONFIG" ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            config.setNGUOIDUNG(cursor.getInt(0));
            config.setTAIKHOAN(cursor.getString(1));
            config.setMATKHAU(cursor.getString(2));
            config.setHOTEN(cursor.getString(3));
            config.setMANHANSU(cursor.getInt(4));
            config.setNGUOIDUNG_NHANSU(cursor.getInt(5));
            config.setNHOMNGUOIDUNG(cursor.getInt(6));
            config.setTENNHOMNGUOIDUNG(cursor.getString(7));
            config.setWIDTH(cursor.getInt(8));
            config.setHEIGHT(cursor.getInt(9));
            config.setQUALITY(cursor.getInt(10));
            config.setSAISOVITRI(cursor.getInt(11));
            config.setISCHECKIN(cursor.getInt(12));
            config.setISCHECKOUT(cursor.getInt(13));
            config.setMaxGetLocation(cursor.getInt(14));
            config.setURLHINH(cursor.getString(15));
            config.setKEY(cursor.getString(16));
        }
        cursor.close();
        db.close();
        return config;
    }
    public  void deleteConfig(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("HT_CONFIG", null, null);
        db.close();
    }*/
    //LOCK TIME
    public void addLockTime(String Time) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("lock_user", null, null);
        ContentValues values = new ContentValues();
        values.put("locktime", Time);
        db.insert("lock_user", null, values);
        db.close();
    }
    public String getLockTime() {
        String Time="";
        String selectQuery = "SELECT   * FROM lock_user" ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            Time=cursor.getString(0);
        }
        cursor.close();
        db.close();
        return Time;
    }
    public  void deleteLockTime(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("lock_user", null, null);
        db.close();
    }
}