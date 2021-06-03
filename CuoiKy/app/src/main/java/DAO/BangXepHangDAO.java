package DAO;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import CSDL_bean.BangXepHang;
import CSDL_bean.User;

public class BangXepHangDAO {
    public static int themKyLuc(BangXepHang bangXepHang, SQLiteOpenHelper dbHelper) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(BangXepHang.cotNgay, bangXepHang.getNgay());
        values.put(BangXepHang.cotDiem, bangXepHang.getDiem());
        long newRowId = db.insert(BangXepHang.tenBang, null, values);
        if (newRowId == -1) return -1;
        return 0;
    }
}
