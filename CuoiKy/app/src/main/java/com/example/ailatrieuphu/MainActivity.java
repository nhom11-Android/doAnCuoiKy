package com.example.ailatrieuphu;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import myHelper.MySound;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        Log.d("test", "create");
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("test", "pause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!MySound.nhacNenIsPlaying())
            setSound();
    }

    private void setSound() {
        MySound.startNhacNen(MainActivity.this, R.raw.nhac_nen);
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
        if(MySound.nhacNenIsPlaying())
            MySound.stopNhacNen();
        Intent intent = new Intent(this,Player.class);
        startActivity(intent);
    }
}