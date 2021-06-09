package DAO;

import android.content.ContentValues;
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
        values.put(User.cotMaXacThuc, "");
        long newRowId = db.insert(User.tenBang, null, values);
        if (newRowId == -1) return -1;
        return 0;
    }
}
