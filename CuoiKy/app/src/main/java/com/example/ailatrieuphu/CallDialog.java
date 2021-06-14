package com.example.ailatrieuphu;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CallDialog extends AppCompatActivity {
    ImageButton btn_help_call_01, btn_help_call_02, btn_help_call_03, btn_help_call_04;
    LinearLayout answerLayout, callLayout_1, callLayout_2, callLayout_3, callLayout_4;
    TextView tvAnswer;
    RelativeLayout callsLayout;
    Button btn_close;
    RadioButton caseARb, caseBRb, caseCRb, caseDRb;
    RadioGroup danhSachDapAn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_dialog);
        setControl();
        setEvent();
    }

    private void setEvent() {
        getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        btn_help_call_01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callsLayout.removeAllViews();
                callsLayout.addView(answerLayout);
                answerLayout.setVisibility(View.VISIBLE);
                answerLayout.addView(callLayout_1, 0);

            }
        });
        btn_help_call_02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callsLayout.removeAllViews();
                callsLayout.addView(answerLayout);
                answerLayout.setVisibility(View.VISIBLE);
                answerLayout.addView(callLayout_2, 0);
            }
        });
        btn_help_call_03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callsLayout.removeAllViews();
                callsLayout.addView(answerLayout);
                answerLayout.setVisibility(View.VISIBLE);
                answerLayout.addView(callLayout_3, 0);
            }
        });
        btn_help_call_04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callsLayout.removeAllViews();
                callsLayout.addView(answerLayout);
                answerLayout.setVisibility(View.VISIBLE);
                answerLayout.addView(callLayout_4, 0);
            }
        });
        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CallDialog.this.finishAffinity();
            }
        });
    }

    private void setControl() {
        btn_help_call_01= findViewById(R.id.btn_help_01);
        btn_help_call_02= findViewById(R.id.btn_help_02);
        btn_help_call_03= findViewById(R.id.btn_help_03);
        btn_help_call_04= findViewById(R.id.btn_help_04);

        answerLayout = findViewById(R.id.ln_answer);
        callsLayout = findViewById(R.id.rl_calls);
        callLayout_1 = findViewById(R.id.ln_call_01);
        callLayout_2 = findViewById(R.id.ln_call_02);
        callLayout_3 = findViewById(R.id.ln_call_03);
        callLayout_4 = findViewById(R.id.ln_call_04);
        tvAnswer = findViewById(R.id.tv_answer);
        btn_close = findViewById(R.id.btn_close);
    }
}