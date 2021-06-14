package com.example.ailatrieuphu;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
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
    }

    private void setControl() {
        nhacNenTgBtn = findViewById(R.id.togMusic_Setting);
        amThanhTgBtn = findViewById(R.id.togSound_Setting);
    }

    private void setEvent() {
        nhacNenTgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!nhacNenTgBtn.isChecked()){
                    nhacNenTgBtn.setBackgroundResource(R.drawable.toggle_button_off);
                    MySound.setNhacNen(0);
                    if(MySound.nhacNenIsPlaying())
                        MySound.stopNhacNen();
                }
                else {
                    nhacNenTgBtn.setBackgroundResource(R.drawable.toggle_button_on);
                    MySound.setNhacNen((float)0.5);
                    if(!MySound.nhacNenIsPlaying())
                        MySound.stopNhacNen();
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