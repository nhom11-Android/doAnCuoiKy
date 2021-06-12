package DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
        ContentValues values = new ContentValues();
        Cursor cursor = db.query(User.tenBang, projection, selection, selectionArgs, null, null, null);
        if (cursor.moveToNext()) {
            cursor.close();
            return 1;
        }
        cursor.close();
        return 0;
    }
}
