package DAO;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import CSDL_bean.CauHoi;
import CSDL_bean.User;

public class CauHoiDAO {
    /**
     * @param cauHoi : đối tượng câu hỏi mới
     * @param dbHelper : đối tượng CSDLAilatrieuphu
     * @return -1 nếu thất bại, 0 nếu thành công
     */
    public static int themCauHoi(CauHoi cauHoi, SQLiteOpenHelper dbHelper) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CauHoi.cotNoiDung, cauHoi.getNoiDung());
        values.put(CauHoi.cotDapAnA, cauHoi.getDapAn()[0]);
        values.put(CauHoi.cotDapAnB, cauHoi.getDapAn()[1]);
        values.put(CauHoi.cotDapAnC, cauHoi.getDapAn()[2]);
        values.put(CauHoi.cotDapAnD, cauHoi.getDapAn()[3]);
        values.put(CauHoi.cotDapAnDung, cauHoi.getDapAnDung());
        values.put(CauHoi.cotChuyenNganh, cauHoi.getChuyenNganh());
        values.put(CauHoi.cotDoKho, cauHoi.getDoKho());
        long newRowId = db.insert(CauHoi.tenBang, null, values);
        if (newRowId == -1) return -1;
        return 0;
    }
}
