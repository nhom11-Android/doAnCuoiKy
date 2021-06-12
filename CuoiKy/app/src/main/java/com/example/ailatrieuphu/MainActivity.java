package com.example.ailatrieuphu;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }

    public void onClickTaoTaiKhoan(View view) {
        Intent intent = new Intent(this,TaoTaiKhoan.class);
        startActivity(intent);
    }

    public void onClickDangNhap(View view) {
        Intent intent = new Intent(this,DangNhap.class);
        startActivity(intent);
    }

    public void onClickChoiNgay(View view) {
        Intent intent = new Intent(this,Player.class);
        startActivity(intent);
    }
}