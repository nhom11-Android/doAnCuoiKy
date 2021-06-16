package com.example.ailatrieuphu;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import CSDL_bean.BangXepHang;
import DAO.BangXepHangDAO;

public class HighScore extends AppCompatActivity {

    ListView bangXepHangLv;
    ArrayList<BangXepHang> data;
    CSDLAilatrieuphu database;
    private ScoreAdapter adapter; // lưu trữ biến cục bộ adapter

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);
        setControl();
        setEvent();
    }


    private ArrayList<BangXepHang> loadData() {
        /**
         * load dữ liệu từ database
         *
         * @return arrayList bangXepHang
         */
        // lấy user đang đăng nhập
        SharedPreferences prefs = getSharedPreferences(MainActivity.mySettingRef, MODE_PRIVATE);
        // lấy user hiện tại
        String tenDangNhap = prefs.getString("tenDangNhap", "None");
        ArrayList<BangXepHang> highScore = BangXepHangDAO.layBangXepHang(database, -1,tenDangNhap);
        return highScore;
    }

    private void setEvent() {
        // set adapter lên listview
        ScoreAdapter adapter = new ScoreAdapter(this, R.layout.score_custom_listview, data);
        this.adapter = adapter;
        adapter.setDb(database); //set database cho adapter
        bangXepHangLv.setAdapter(adapter);
        bangXepHangLv.setClickable(true);

    }

    private void setControl() {
        bangXepHangLv = findViewById(R.id.bangXepHangLv_HighScore);
        // cài đặt cho action bar
        ActionBar actionBar = (ActionBar) getSupportActionBar();
        actionBar.hide();
        // khai báo csdl
        database = new CSDLAilatrieuphu(this);
        data = loadData();// load dữ liệu lên arraylist
    }

}