package com.example.ailatrieuphu;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import DAO.UserDAO;
import myHelper.MySound;

public class MainMenu extends AppCompatActivity {
    ImageButton btn_about, btn_high_score, btn_setting, btn_close, btn_play, btn_luyentap, btn_online;
    Button btn_thoat_dialog, btn_thoat_yes, btn_thoat_no;
    Dialog info_dialog, thoat_dialog;
    int request_code_for_user_crud = 1;
    String tenDangNhap; // lấy trong intent
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        getSupportActionBar().hide();
//        CSDLAilatrieuphu csdlAilatrieuphu = new CSDLAilatrieuphu(this);
        setControl();
        dialogInfo();
        dialogExit();
        setEvent();
    }

    public void dialogInfo() {
        info_dialog = new Dialog(this); // Context, this, etc.
        info_dialog.setContentView(R.layout.activity_about_dialog);
        info_dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        btn_thoat_dialog =info_dialog.findViewById(R.id.btn_thoat_dialog);
        btn_thoat_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                info_dialog.dismiss();
            }
        });
    }
    public void dialogExit() {
        thoat_dialog = new Dialog(this); // Context, this, etc.
        thoat_dialog.setContentView(R.layout.thoat_dialog);
        thoat_dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        btn_thoat_yes =thoat_dialog.findViewById(R.id.btn_thoat_yes);
        btn_thoat_no =thoat_dialog.findViewById(R.id.btn_thoat_no);

        btn_thoat_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainMenu.this.finishAffinity();
                if(MySound.nhacNenIsPlaying())
                    MySound.stopNhacNen();
                System.exit(0);
            }
        });
        btn_thoat_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thoat_dialog.dismiss();
            }
        });
    }

    private void setEvent() {
        btn_luyentap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainMenu.this, Player.class);
                startActivity(intent);
            }
        });
        btn_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                info_dialog.show();
            }
        });
        btn_high_score.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainMenu.this, HighScore.class);
                startActivity(intent);
            }
        });
        btn_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainMenu.this, Setting.class);
                startActivity(intent);
            }
        });
        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thoat_dialog.show();
            }
        });
    }


    private void setControl() {
        btn_about= findViewById(R.id.btn_about);
        btn_high_score= findViewById(R.id.btn_high_score);
        btn_setting= findViewById(R.id.btn_setting);
        btn_close= findViewById(R.id.btn_close);
//        btn_play= findViewById(R.id.btn_play);
        btn_luyentap=findViewById(R.id.luyentapBtn_MM);
        btn_online=findViewById(R.id.onlineBtn_MM);
    }

    /**
     * @param view gọi 1 activity để nhận về kết quả với hàm
     *             nếu result = -1 thì exit về màn hình đăng nhập
     */
    public void onClickUserInfo(View view) {
        // TODO: 6/13/2021 xoá sửa thông tin người chơi, gọi userinfoCURD
        tenDangNhap = getIntent().getStringExtra("tenDangNhap");
        Intent intent = new Intent(this,UserInfoCRUD.class);
        intent.putExtra("tenDangNhap",tenDangNhap);
        startActivityForResult(intent,request_code_for_user_crud);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==request_code_for_user_crud){
            if(resultCode==-1) this.finish(); // code for logout user
            if(resultCode==-2){
                this.finish(); // code for delete account user
            }
        }
    }

    public void onClickOnlinePlayBtn(View view) {
        // gọi layout chờ người chơi vào
        // gọi layout load câu hỏi
        Intent intent = new Intent(this, OnlineModePre.class);
        startActivity(intent);
    }
}