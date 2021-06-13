package com.example.ailatrieuphu;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import DAO.UserDAO;

public class DangNhap extends AppCompatActivity {
    String tenDangNhap, matKhau;
    EditText tenDangNhapEdt, matKhauEdt;
    CSDLAilatrieuphu database = new CSDLAilatrieuphu(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setControl();
    }

    private void setControl() {
        tenDangNhapEdt = findViewById(R.id.tenDangNhapEdt_DN);
        matKhauEdt = findViewById(R.id.matKhauEdt_DN);
    }
    public void onClickSubmitLogin(View view) {
        // TODO: 6/12/2021 xử lý đăng nhập
        int check_result = checkValidInfo();
        if(check_result==1){
            // TODO: 6/12/2021 goi main menu
            Toast.makeText(this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this,MainMenu.class);
            startActivity(intent);
        }
    }

    private int checkValidInfo() {
        int ret = 1;
        tenDangNhap = tenDangNhapEdt.getText().toString().trim();
        matKhau = matKhauEdt.getText().toString().trim();
        if(tenDangNhap.isEmpty()){
            ret =0;
            tenDangNhapEdt.setError("Không thể để trống !");
        }else if(matKhau.isEmpty()){
            ret = 0;
            matKhauEdt.setError("Không thể để trống !");
        }else {
            // kiểm tra thông tin đăng nhập
            int tontai = UserDAO.kiemTraDangNhap(tenDangNhap, matKhau, database);
            if(tontai==0){
                ret = 0;
                Toast.makeText(this, "Tên đăng nhập hoặc mật khẩu không đúng !", Toast.LENGTH_LONG).show();
            }
        }
        return ret;
    }
}