package DAO;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.ailatrieuphu.CSDLAilatrieuphu;

import CSDL_bean.BangXepHang;
import CSDL_bean.CauHoi;
import CSDL_bean.User;

public class UserDAO {
    public static int themUser(User newUser, SQLiteOpenHelper dbHelper) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(User.cotEmail, newUser.getEmail());
        values.put(User.cotMatKhau, newUser.getMatKhau());
        values.put(User.cotTenDangNhap, newUser.getTenDangNhap());
        long newRowId = db.insert(User.tenBang, null, values);
        if (newRowId == -1) return -1;
        return 0;
    }
    public static int kiemTraTonTaiTenDN(String tenDangNhap, SQLiteOpenHelper dbHelper){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String[] projection = {
          User.cotTenDangNhap
        };
        String selection = User.cotTenDangNhap + " = ?";
        String[] selectionArgs = {tenDangNhap};
        ContentValues values = new ContentValues();
        Cursor cursor = db.query(User.tenBang, projection, selection, selectionArgs, null, null, null);
        if (cursor.moveToNext()){
            cursor.close();
            return 1;
        }
        cursor.close();
        return 0;
    }
    public static int kiemTraTonTaiMail(String mail, SQLiteOpenHelper dbHelper){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String[] projection = {
                User.cotEmail
        };
        String selection = User.cotEmail + " = ?";
        String[] selectionArgs = {mail};
        Cursor cursor = db.query(User.tenBang, projection, selection, selectionArgs, null, null, null);
        if (cursor.moveToNext()) {
            cursor.close();
            return 1;
        }
        cursor.close();
        return 0;
    }
    public static int kiemTraDangNhap(String tenDangNhap, String matKhau, SQLiteOpenHelper dbHelper){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String[] projection = {
                User.cotTenDangNhap
        };
        String selection = User.cotTenDangNhap + " = ? AND " + User.cotMatKhau + " = ? ";
        String[] selectionArgs = {tenDangNhap,matKhau};
        Cursor cursor = db.query(User.tenBang, projection, selection, selectionArgs, null, null, null);
        if (cursor.moveToNext()) {
            cursor.close();
            return 1;
        }
        cursor.close();
        return 0;
    }


    public static User getUserByTenDangNhap(String tenDangNhap, CSDLAilatrieuphu dbHelper) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        User leakUser =null;
        String[] projection = {
                User.cotTenDangNhap,
                User.cotEmail,
                User.cotMatKhau
        };
        String selection = User.cotTenDangNhap + " = ?";
        String[] selectionArgs = {tenDangNhap};
        Cursor cursor = db.query(User.tenBang, projection, selection, selectionArgs, null, null, null);
        if (cursor.moveToNext()) {
            leakUser = new User(
                    cursor.getString(cursor.getColumnIndex(User.cotTenDangNhap)),
                    cursor.getString(cursor.getColumnIndex(User.cotMatKhau)),
                    cursor.getString(cursor.getColumnIndex(User.cotEmail))
            );
        }
        cursor.close();
        return leakUser;
    }
    public static int deleteUserByMail(String mail,String tenDangNhap, CSDLAilatrieuphu dbHelper){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String selection = User.cotEmail + "LIKE ?";
        String[] selectionArgs = {mail};
        int rows = db.delete(User.tenBang, selection, selectionArgs); // xoá user
        // xoá các bảng record của user
        db.execSQL("delete from "+ BangXepHang.tenBang +" where user=" + tenDangNhap);
        return rows;
    }

    public static int updateUser(String old,String tenDangNhap, String matKhau, CSDLAilatrieuphu dbHelper) {
        ContentValues values = new ContentValues();
        values.put(User.cotTenDangNhap,tenDangNhap);
        values.put(User.cotMatKhau,matKhau);
        String seletion = User.cotTenDangNhap + " LIKE ?";
        String[] selectionArgs = {old};
        int count = dbHelper.getWritableDatabase().update(User.tenBang,values,seletion,selectionArgs);
        return count;
    }
}
