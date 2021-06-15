package com.example.ailatrieuphu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.widget.ToggleButton;

import myHelper.MySound;

public class Setting extends AppCompatActivity {
    public static float amThanh;
    public static float nhacNen;
    ToggleButton nhacNenTgBtn, amThanhTgBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        setControl();
        setEvent();
        MySound.startNhacNen(Setting.this, R.raw.nhac_nen);
    }

    private void setControl() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        nhacNenTgBtn = findViewById(R.id.togMusic_Setting);
        amThanhTgBtn = findViewById(R.id.togSound_Setting);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setEvent() {
        nhacNenTgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!nhacNenTgBtn.isChecked()){
                    nhacNenTgBtn.setBackgroundResource(R.drawable.toggle_button_off);
                    MySound.setNhacNen(0);
                    if(MySound.nhacNenIsPlaying())
                        MySound.settingNhacNen();
                }
                else {
                    nhacNenTgBtn.setBackgroundResource(R.drawable.toggle_button_on);
                    MySound.setNhacNen((float)0.5);
                    if(MySound.nhacNenIsPlaying())
                        MySound.settingNhacNen();
                }
            }
        });

        amThanhTgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!amThanhTgBtn.isChecked()){
                    amThanhTgBtn.setBackgroundResource(R.drawable.toggle_button_off);
                    MySound.setAmThanh(0);
                }
                else {
                    amThanhTgBtn.setBackgroundResource(R.drawable.toggle_button_on);
                    MySound.setAmThanh((float)0.5);
                }
            }
        });
    }

}