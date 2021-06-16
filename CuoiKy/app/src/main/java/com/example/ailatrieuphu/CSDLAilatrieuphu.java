package com.example.ailatrieuphu;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import CSDL_bean.BangXepHang;
import CSDL_bean.CauHoi;
import CSDL_bean.User;

public class CSDLAilatrieuphu extends SQLiteOpenHelper {
    public static final String name = "AiLaTrieuPhu.db";
    public static final int version = 1;
    public CSDLAilatrieuphu(@Nullable Context context) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String user = String.format("CREATE TABLE %s(%s integer primary key autoincrement,%s TEXT, %s TEXT,%s TEXT);",
                User.tenBang,
                User.cotId, //autoincre integer
                User.cotEmail, // text
                User.cotTenDangNhap, // text
                User.cotMatKhau); // text
        String cauhoi = String.format("CREATE TABLE %s(" +
                        "%s integer primary key autoincrement," +
                        "%s TEXT," +
                        "%s TEXT," +
                        "%s TEXT," +
                        "%s TEXT," +
                        "%s TEXT," +
                        "%s TEXT," +
                        "%s TEXT," +
                        "%s integer)",
                CauHoi.tenBang,
                CauHoi.cotId,
                CauHoi.cotNoiDung,
                CauHoi.cotDapAnA,
                CauHoi.cotDapAnB,
                CauHoi.cotDapAnC,
                CauHoi.cotDapAnD,
                CauHoi.cotDapAnDung,
                CauHoi.cotChuyenNganh,
                CauHoi.cotDoKho
                );
        String bxh = String.format("CREATE TABLE %s(%s TEXT, %s TEXT,%s TEXT);",
                BangXepHang.tenBang,
                BangXepHang.cotNgay,
                BangXepHang.cotDiem,
                BangXepHang.cotUser);
        db.execSQL(user);
        db.execSQL(cauhoi);
        db.execSQL(bxh);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql_upgrade = "DROP TABLE IF EXISTS ";
        db.execSQL(sql_upgrade + User.tenBang);
        db.execSQL(sql_upgrade+CauHoi.tenBang);
        db.execSQL(sql_upgrade+BangXepHang.tenBang);
        onCreate(db);
    }
}
