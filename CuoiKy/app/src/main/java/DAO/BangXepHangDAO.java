package DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

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


    /**
     * @param dbHelper : CSDLAiLaTrieuPhu database
     * @param n : -1 nếu muốn lấy hết, n >0 sẽ trả về danh sách với n kết quả
     * @return danh sách bảng xếp hạng
     */
    public static ArrayList<BangXepHang> layBangXepHang(SQLiteOpenHelper dbHelper,int n){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {
                BangXepHang.cotNgay,
                BangXepHang.cotDiem
        };
        Cursor cursor;
        if(n!=-1) {
            String limit = String.valueOf(n);

            cursor = db.query(
                    BangXepHang.tenBang,   // The table to query
                    projection,             // The array of columns to return (pass null to get all)
                    null,              // The columns for the WHERE clause
                    null,          // The values for the WHERE clause
                    null,                   // don't group the rows
                    null,                   // don't filter by row groups
                    null,               // The sort order
                    limit
            );
        }
        else{
            cursor = db.query(
                    BangXepHang.tenBang,   // The table to query
                    projection,             // The array of columns to return (pass null to get all)
                    null,              // The columns for the WHERE clause
                    null,          // The values for the WHERE clause
                    null,                   // don't group the rows
                    null,                   // don't filter by row groups
                    null
                    );
        }
        ArrayList<BangXepHang> dsBxh = new ArrayList<>();
        while (cursor.moveToNext()){
            String ngay = cursor.getString(cursor.getColumnIndex(BangXepHang.cotNgay));
            int diem = cursor.getInt(cursor.getColumnIndex(BangXepHang.cotDiem));
            dsBxh.add(new BangXepHang(ngay,diem));
        }
        return dsBxh;
    }
}
