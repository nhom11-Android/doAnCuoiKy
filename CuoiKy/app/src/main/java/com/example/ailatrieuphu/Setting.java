package com.example.ailatrieuphu;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ToggleButton;

public class Setting extends AppCompatActivity {
    public float amThanh;
    public float nhacNen;
    private AudioManager audioManager;
    // Maximumn sound stream.
    private static final int MAX_STREAMS = 5;
    // Stream type.
    private static final int streamType = AudioManager.STREAM_MUSIC;
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
        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
    }

    private void setEvent() {
        nhacNenTgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!nhacNenTgBtn.isChecked()){
                    System.out.println("true -> false");
                    nhacNenTgBtn.setBackgroundResource(R.drawable.toggle_button_off);
                    nhacNen = 0;
                }
                else {
                    System.out.println("false -> true");
                    nhacNenTgBtn.setBackgroundResource(R.drawable.toggle_button_on);
                    // Current volumn Index of particular stream type.
                    float currentVolumeIndex = (float) audioManager.getStreamVolume(streamType);

                    // Get the maximum volume index for a particular stream type.
                    float maxVolumeIndex  = (float) audioManager.getStreamMaxVolume(streamType);

                    // Volumn (0 --> 1)
                    nhacNen = currentVolumeIndex / maxVolumeIndex;
                }
            }
        });

        amThanhTgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!amThanhTgBtn.isChecked()){
                    System.out.println("true -> false");
                    amThanhTgBtn.setBackgroundResource(R.drawable.toggle_button_off);
                    amThanh = 0;
                }
                else {
                    System.out.println("false -> true");
                    amThanhTgBtn.setBackgroundResource(R.drawable.toggle_button_on);
                    // Current volumn Index of particular stream type.
                    float currentVolumeIndex = (float) audioManager.getStreamVolume(streamType);

                    // Get the maximum volume index for a particular stream type.
                    float maxVolumeIndex  = (float) audioManager.getStreamMaxVolume(streamType);

                    // Volumn (0 --> 1)
                    amThanh = currentVolumeIndex / maxVolumeIndex;
                }
            }
        });
    }

    public float getAmThanh() {
        return amThanh;
    }

    public void setAmThanh(float amThanh) {
        this.amThanh = amThanh;
    }

    public float getNhacNen() {
        return nhacNen;
    }

    public void setNhacNen(float nhacNen) {
        this.nhacNen = nhacNen;
    }
}