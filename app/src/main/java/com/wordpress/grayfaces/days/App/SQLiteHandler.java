package com.wordpress.grayfaces.days.App;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.wordpress.grayfaces.days.Models.Anniversary;
import com.wordpress.grayfaces.days.Models.AppSetting;

import java.util.ArrayList;
import java.util.List;

/**
 * Project Days
 * Created by gray on 2/15/2017.
 */

public class SQLiteHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Days";
    public SQLiteHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE `APP_SETTING` ( `ISNOTIFY100D` BOOLEAN, `ISNOTIFYANI` BOOLEAN, `ISUSEPASSWORD` BOOLEAN,`PASSWORD` NVARCHAR(6) )");
        db.execSQL("CREATE TABLE `QL_ANY` ( `ID` INTEGER PRIMARY KEY AUTOINCREMENT, `PERSON1` NVARCHAR(50),`PERSON2` NVARCHAR(50),`TITLE` NVARCHAR(50), `STARTDATE` NVARCHAR(50) )");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS APP_SETTING");
        db.execSQL("DROP TABLE IF EXISTS QL_ANY");
        onCreate(db);
    }
    public void setAppSettingMacDinh(){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("ISNOTIFY100D", true);
        values.put("ISNOTIFYANI", true);
        values.put("ISUSEPASSWORD", false);
        db.insert("APP_SETTING", null, values);
        db.close();
    }
    public void changeAppSetting(AppSetting setting){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("ISNOTIFY100D", setting.isNotify100D());
        values.put("ISNOTIFYANI", setting.isNotifyANY());
        values.put("ISUSEPASSWORD", setting.isUsePassword());
        values.put("PASSWORD", setting.getPassword());
        db.update("APP_SETTING", values, null, null);
        db.close();
    }

    public void addAni(Anniversary anniversary){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        //values.put("ID", anniversary.getID());
        values.put("PERSON1", anniversary.getPerson1());
        values.put("PERSON2", anniversary.getPerson2());
        values.put("TITLE", anniversary.getTitle());
        values.put("STARTDATE", anniversary.getStartDate());
        db.insert("QL_ANY", null, values);
        db.close();
    }
    public int countAni(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT ID FROM QL_ANY",null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        return count;
    }
    public List<Anniversary> getAllAni(){
        List<Anniversary> aniList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM QL_ANY" ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Anniversary ani = new Anniversary();
                ani.setID(Integer.parseInt(cursor.getString(0)));
                ani.setPerson1(cursor.getString(1));
                ani.setPerson2(cursor.getString(2));
                ani.setTitle(cursor.getString(3));
                ani.setStartDate(cursor.getString(4));

                aniList.add(ani);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return aniList;
    }
    public Anniversary getAni(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("QL_ANY",null,"ID=?",new String[]{String.valueOf(id)},null,null,null );
        Anniversary ani = null;
        if (cursor!=null){
            cursor.moveToFirst();
            ani = new Anniversary(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4));
            cursor.close();
        }
        return ani;
    }
    /*public void addUser(int NGUOIDUNG,String TAIKHOAN,String MATKHAU,String HOTEN,int KHO,int BOPHAN,String sKEY) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("HT_NGUOIDUNG", null, null);
        ContentValues values = new ContentValues();
        values.put("NGUOIDUNG", NGUOIDUNG);
        values.put("TAIKHOAN", TAIKHOAN);
        values.put("MATKHAU", MATKHAU);
        values.put("HOTEN", HOTEN);
        values.put("KHO", KHO);
        values.put("BOPHAN", BOPHAN);
        values.put("sKEY", sKEY);
        db.insert("HT_NGUOIDUNG", null, values);
        db.close();
    }
    public int updateUser(int NGUOIDUNG,String TAIKHOAN,String MATKHAU,String HOTEN,int KHO,int BOPHAN,String sKEY) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("NGUOIDUNG", NGUOIDUNG);
        values.put("MATKHAU", MATKHAU);
        values.put("HOTEN", HOTEN);
        values.put("KHO", KHO);
        values.put("BOPHAN", BOPHAN);
        values.put("sKEY", sKEY);
        return db.update("HT_NGUOIDUNG", values, "TAIKHOAN" + " = ?",
                new String[] { TAIKHOAN });
    }
    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        String selectQuery = "SELECT  * FROM HT_NGUOIDUNG" ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            user.put("NGUOIDUNG", cursor.getString(0));
            user.put("TAIKHOAN", cursor.getString(1));
            user.put("MATKHAU", cursor.getString(2));
            user.put("HOTEN", cursor.getString(3));
            user.put("KHO", cursor.getString(4));
            user.put("BOPHAN", cursor.getString(5));
            user.put("sKEY", cursor.getString(6));
        }
        cursor.close();
        db.close();
        return user;
    }
    public int getUserID() {
        int ID=0;
        String selectQuery = "SELECT   * FROM HT_NGUOIDUNG" ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            ID=Integer.parseInt(cursor.getString(0));
        }
        cursor.close();
        db.close();
        return ID;
    }
    public  void deleteUser(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("HT_NGUOIDUNG", null, null);
        db.close();
    }
    /*public void addNhapKho(NhapKho  nhapKho) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("sKey", nhapKho.getID());
        values.put("ProductID", nhapKho.getProductID());
        values.put("ProductNumber", nhapKho.getProductNumber());
        values.put("ProductName", nhapKho.getProductName());
        values.put("LotNumber", nhapKho.getLotNumber());
        values.put("LotNumber2", nhapKho.getLotNumber2());
        values.put("SoLuong", nhapKho.getSoLuong());
        values.put("BoxNumber", nhapKho.getBoxNumber());
        values.put("Serial", nhapKho.getSerial());
        values.put("ManuDate", nhapKho.getManuDate());
        values.put("Manufactor", nhapKho.getManufactor());
        values.put("NguoiDung", nhapKho.getNguoiDung());
        values.put("NgayQuet", nhapKho.getNgayQuet());
        values.put("RAW", nhapKho.getRAW());
        values.put("TonTai", nhapKho.getTonTai());
        values.put("QuyCach", nhapKho.getQuyCach());
        db.insert("QL_NHAPKHO", null, values);
        db.close();
    }
    public List<NhapKho> getNhapKhoDetails() {
       List<NhapKho> nhapkho = new ArrayList<NhapKho>() {
       };
        String selectQuery = "SELECT  * FROM QL_NHAPKHO" ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        for(int i=0;i<cursor.getCount();i++){
            cursor.move(1);
            NhapKho  nk = new NhapKho();
            nk.setID(cursor.getString(0));
            nk.setProductID( cursor.getInt(1));
            nk.setProductNumber( cursor.getString(2));
            nk.setProductName( cursor.getString(3));
            nk.setLotNumber( cursor.getString(4));
            nk.setLotNumber2( cursor.getString(5));
            nk.setSoLuong( cursor.getInt(6));
            nk.setBoxNumber( cursor.getString(7));
            nk.setSerial( cursor.getString(8));
            nk.setManuDate( cursor.getString(9));
            nk.setManufactor( cursor.getString(10));
            nk.setNguoiDung( cursor.getInt(11));
            nk.setNgayQuet( cursor.getString(12));
            nk.setRAW( cursor.getString(13));
            nk.setTonTai( cursor.getInt(14));
            nk.setQuyCach( cursor.getInt(15));
            System.out.println("Kho: "+nk);
            nhapkho.add(nk);
        }
        cursor.close();
        db.close();
        return nhapkho;
    }
    public int checkSanPhamKho(String id){
        int dem=0;
        String selectQuery = "SELECT  COUNT(sKey) AS DEM FROM QL_NHAPKHO WHERE sKey='"+id+"'" ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            dem= cursor.getInt(0);
        }
        cursor.close();
        db.close();
        return dem;
    }
    public int countSanPhamKho(){
        int dem=0;
        String selectQuery = "SELECT  COUNT(sKey) AS DEM FROM QL_NHAPKHO";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            dem= cursor.getInt(0);
        }
        cursor.close();
        db.close();
        return dem;
    }
    public void deleteSanPhamKho(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("QL_NHAPKHO", "sKey=?", new String[] { id });
        db.close();
    }
    public int updateSoLuong(int SoLuong,String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("SoLuong", SoLuong);
        return db.update("QL_NHAPKHO", values, "sKey=?",new String[] { id });
    }*/
}
