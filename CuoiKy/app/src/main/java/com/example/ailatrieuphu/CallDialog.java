package com.example.ailatrieuphu;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;

import CSDL_bean.CauHoi;
import DAO.CauHoiDAO;

public class CallDialog extends AppCompatActivity {
    ImageButton btn_help_call_01, btn_help_call_02, btn_help_call_03, btn_help_call_04;
    LinearLayout answerLayout, callLayout_1, callLayout_2, callLayout_3, callLayout_4;
    TextView tvAnswer;
    RelativeLayout callsLayout;
    Button btn_close;
    int idCauhoi;
    String trueAnswer;
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

        CauHoi cauHoi = CauHoiDAO.timCauHoiTuID(idCauhoi, new CSDLAilatrieuphu(this));
        String da1,da2,da3,da4,dad;
        da1 = cauHoi.getDapAn()[0];
        da2 = cauHoi.getDapAn()[1];
        da3 = cauHoi.getDapAn()[2];
        da4 = cauHoi.getDapAn()[3];
        dad = cauHoi.getDapAnDung();
        if(da1.equals(dad)){
            trueAnswer = "A";
        }
        if(da2.equals(dad)){
            trueAnswer = "B";
        }
        if(da3.equals(dad)){
            trueAnswer = "C";
        }
        if(da4.equals(dad)){
            trueAnswer = "D";
        }
        btn_help_call_01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callsLayout.removeAllViews();
                callsLayout.addView(answerLayout);
                answerLayout.setVisibility(View.VISIBLE);
                answerLayout.addView(callLayout_1, 0);
                if(cauHoi.getChuyenNganh()=="Bác Sĩ"){
                    tvAnswer.setText("Theo tôi đáp án đúng là " + trueAnswer);
                }else{
                    Random rand = new Random();
                    int random = rand.nextInt(4);
                    if(random==0){
                        tvAnswer.setText("Theo tôi đáp án đúng là A");
                    }
                    if(random==1){
                        tvAnswer.setText("Theo tôi đáp án đúng là B");
                    }
                    if(random==2){
                        tvAnswer.setText("Theo tôi đáp án đúng là C");
                    }
                    if(random==3){
                        tvAnswer.setText("Theo tôi đáp án đúng là D");
                    }
                }
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
                CallDialog.this.finish();
                //CallDialog.this.finishAffinity();
            }
        });
    }

    private void setControl() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
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