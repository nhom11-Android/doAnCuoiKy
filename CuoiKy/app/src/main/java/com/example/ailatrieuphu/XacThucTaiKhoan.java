package com.example.ailatrieuphu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import CSDL_bean.User;
import DAO.UserDAO;

public class XacThucTaiKhoan extends AppCompatActivity {
    String maXacThuc,tenDangNhap,matKhau,mail;
    EditText nhapMaEdt;
    Button submitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xac_thuc_tai_khoan2);
        Intent intent = getIntent();
        maXacThuc = intent.getStringExtra("maXacThuc");
        tenDangNhap = intent.getStringExtra("tenDangNhap");
        matKhau = intent.getStringExtra("matKhau");
        mail = intent.getStringExtra("mail");
        setControl();

    }

    private void setControl() {
        nhapMaEdt = findViewById(R.id.maXacThucEdt_XTTK2);
        submitBtn = findViewById(R.id.nhapMaBtn_XTTK2);
    }

    public void onClickSubmitCode(View view) {
        String code = nhapMaEdt.getText().toString().trim();
        if(code.isEmpty()==true){
            nhapMaEdt.setError("Không được để trống");
        }else if(code.equals(maXacThuc)==true){
            // thông báo
            Toast.makeText(this, "Bạn đã tạo tài khoản thành công, xin hãy đăng nhập !", Toast.LENGTH_SHORT).show();
            // ghi tài khoản vào database
            User user = new User(tenDangNhap,matKhau,mail);
            CSDLAilatrieuphu database = new CSDLAilatrieuphu(this);
            UserDAO.themUser(user,database);
            database.close();
            // gọi đăng nhập
            Intent intent = new Intent(this, DangNhap.class);
            startActivity(intent);
        }else{
            nhapMaEdt.setError("Mã xác thực sai ! Xin hãy kiểm tra kĩ !");
        }
    }
}