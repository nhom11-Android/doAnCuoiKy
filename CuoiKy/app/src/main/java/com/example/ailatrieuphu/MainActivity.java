package com.example.ailatrieuphu;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import myHelper.MySound;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }

    @Override
    protected void onStop() {
        super.onStop();
        MySound.stopNhacNen();
        System.out.println("stop");
    }

    @Override
    protected void onPause() {
        super.onPause();
//        MySound.stopNhacNen();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setSound();
        System.out.println("resume");
    }

    private void setSound() {
        MySound.startNhacNen(MainActivity.this, R.raw.piano_loop);
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