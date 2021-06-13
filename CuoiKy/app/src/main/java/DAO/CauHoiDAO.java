package DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import CSDL_bean.CauHoi;
import CSDL_bean.User;

public class CauHoiDAO {
    /**
     * @param cauHoi   : đối tượng câu hỏi mới
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

    public static CauHoi layCauHoi(int doKho, SQLiteOpenHelper dbHelper) {
        CauHoi cauHoi = null;
        String sql = "select * from " + CauHoi.tenBang + " where " + CauHoi.cotDoKho + "=" + String.valueOf(doKho) + " order by random() limit 1;";
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(CauHoi.cotId));
            String noidung = cursor.getString(cursor.getColumnIndex(CauHoi.cotNoiDung));
            String dapana = cursor.getString(cursor.getColumnIndex(CauHoi.cotDapAnA));
            String dapanb = cursor.getString(cursor.getColumnIndex(CauHoi.cotDapAnB));
            String dapanc = cursor.getString(cursor.getColumnIndex(CauHoi.cotDapAnC));
            String dapand = cursor.getString(cursor.getColumnIndex(CauHoi.cotDapAnD));
            String dapandung = cursor.getString(cursor.getColumnIndex(CauHoi.cotDapAnDung));
            String chuyennganh = cursor.getString(cursor.getColumnIndex(CauHoi.cotChuyenNganh));
            cauHoi = new CauHoi(noidung, new String[]{dapana, dapanb, dapanc, dapand}, dapandung, chuyennganh, doKho);
            cauHoi.setId(id);
        }
        cursor.close();
        return cauHoi;
    }

    public static ArrayList<CauHoi> layBoCauHoi(SQLiteOpenHelper dbHelper) {
        ArrayList<CauHoi> dsCauHoi = new ArrayList<>();
        //6 cau de
        int count = 0;
        while (count <= 5) { // lấy 6 câu đầu
            CauHoi cauHoi = layCauHoi(1, dbHelper);
            if (cauHoi != null) { // nếu null count không tăng
                boolean check = true;
                for (CauHoi i : dsCauHoi) {
                    if (i.getId() == cauHoi.getId()) {
                        check = false;
                        break;
                    }
                }
                if (check == true) {
                    // count chỉ tăng TH id không trùng
                    dsCauHoi.add(cauHoi);
                    count++;
                }
            }
            // chỉ thoát vòng lặp khi count đủ
        }
        //6 cau trung binh , count hiện tại = 6
        while (count <= 11) {
            CauHoi cauHoi = layCauHoi(2, dbHelper);
            if (cauHoi != null) { // nếu null count không tăng
                boolean check = true;
                for (CauHoi i : dsCauHoi) {
                    if (i.getId() == cauHoi.getId()) {
                        check = false;
                        break;
                    }
                }
                if (check == true) {
                    // count chỉ tăng TH id không trùng
                    dsCauHoi.add(cauHoi);
                    count++;
                }
            }
            // chỉ thoát vòng lặp khi count đủ
        }
        //6 cau kho
        while (count <= 17) {
            CauHoi cauHoi = layCauHoi(3, dbHelper);
            if (cauHoi != null) { // nếu null count không tăng
                boolean check = true;
                for (CauHoi i : dsCauHoi) {
                    if (i.getId() == cauHoi.getId()) {
                        check = false;
                        break;
                    }
                }
                if (check == true) {
                    // count chỉ tăng TH id không trùng
                    dsCauHoi.add(cauHoi);
                    count++;
                }
            }
            // chỉ thoát vòng lặp khi count đủ
        }
        return dsCauHoi;
    }
    public static CauHoi timCauHoiTuID(int id,SQLiteOpenHelper dbHelper){
        CauHoi cauHoi = null;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String[] projection = {
                CauHoi.cotDoKho,
                CauHoi.cotChuyenNganh,
                CauHoi.cotNoiDung,
                CauHoi.cotDapAnA,
                CauHoi.cotDapAnB,
                CauHoi.cotDapAnC,
                CauHoi.cotDapAnD,
                CauHoi.cotDapAnDung
        };
        String selection = CauHoi.cotId + " = ?";
        String[] selectionArgs = {String.valueOf(id)};
        Cursor cursor = db.query(CauHoi.tenBang, projection, selection, selectionArgs, null, null, null);
        if (cursor.moveToNext()) {
            String da1,da2,da3,da4,dad,nd,chuyen;
            int dok;
            da1 = cursor.getString(cursor.getColumnIndex(CauHoi.cotDapAnA));
            da2 = cursor.getString(cursor.getColumnIndex(CauHoi.cotDapAnB));
            da3 = cursor.getString(cursor.getColumnIndex(CauHoi.cotDapAnC));
            da4 = cursor.getString(cursor.getColumnIndex(CauHoi.cotDapAnD));
            dad = cursor.getString(cursor.getColumnIndex(CauHoi.cotDapAnDung));
            nd = cursor.getString(cursor.getColumnIndex(CauHoi.cotNoiDung));
            dok = cursor.getInt(cursor.getColumnIndex(CauHoi.cotDoKho));
            chuyen = cursor.getString(cursor.getColumnIndex(CauHoi.cotChuyenNganh));
            cauHoi = new CauHoi(nd, new String[]{da1, da2, da3, da4},dad,chuyen,dok);
            cauHoi.setId(id);
            // TODO: 6/13/2021 check nếu kiểu lưu đáp án đúng khác với dự tính

            cursor.close();
        }
        cursor.close();
        return cauHoi;
    }
}
