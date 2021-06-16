package com.example.ailatrieuphu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
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
        configActionBar();
        setControl();
    }
    private void configActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Trở lại");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            actionBar.setBackgroundDrawable(getDrawable(R.drawable.home_background));
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                break;
        }
        return super.onOptionsItemSelected(item);
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
            // lưu người chơi vào shared references
            SharedPreferences.Editor editor = getSharedPreferences(MainActivity.mySettingRef,MODE_PRIVATE).edit();
            editor.putString("tenDangNhap",tenDangNhap); // lưu lại thông tin login
            editor.putString("is_login","yes"); // lưu lại cờ login
            editor.apply();
            //gọi mainmenu
            Intent intent = new Intent(this,MainMenu.class);
            // đưa user vào intent
            intent.putExtra("tenDangNhap",tenDangNhap);
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