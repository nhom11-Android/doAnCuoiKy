package com.example.ailatrieuphu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import CSDL_bean.User;
import DAO.UserDAO;

public class UserInfoCRUD extends AppCompatActivity {
    String tenDangNhap;
    User loadUser;
    CSDLAilatrieuphu database = new CSDLAilatrieuphu(this);

    EditText mailEdt,tenDangNhapEdt,matKhauEdt;
    Button suaBtn, xoaBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info_crud);
        tenDangNhap = getIntent().getStringExtra("tenDangNhap");
        loadUser = UserDAO.getUserByTenDangNhap(tenDangNhap,database);
        setControl();
        configActionBar();

    }

    private void configActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Thông tin tài khoản");
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
        mailEdt = findViewById(R.id.mailEdt_UIC);
        tenDangNhapEdt = findViewById(R.id.tenDangNhapEdt_UIC);
        matKhauEdt = findViewById(R.id.matKhauEdt_UIC);
        suaBtn = findViewById(R.id.suaTaiKhoanBtn_UIC);
        xoaBtn = findViewById(R.id.xoaTaiKhoanBtn_UIC);

        mailEdt.setText(loadUser.getEmail());
        tenDangNhapEdt.setText(loadUser.getTenDangNhap());
        matKhauEdt.setText(loadUser.getMatKhau());
    }

    public void onClickxoaTaiKhoan(View view) {
        final boolean[] is_deleted = {false};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Chú ý");
        builder.setMessage("Bạn có chắc muốn xoá tài khoản");

        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // Do nothing but close the dialog
                // TODO: 6/13/2021 xoá  user
                if(UserDAO.deleteUserByMail(loadUser.getEmail(),database)==1){
                    Toast.makeText(UserInfoCRUD.this, "Đã xoá người chơi thành công !", Toast.LENGTH_SHORT).show();
                    is_deleted[0] = true;
                    setResult(-1);
                    UserInfoCRUD.this.finish();
                }
                else{
                    Toast.makeText(UserInfoCRUD.this, "Đã có lỗi xảy ra. Liên hệ nhà sản xuất để biết thêm chi tiết !", Toast.LENGTH_SHORT).show();
                }
//                is_deleted[0] = true;

            }
        });

        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                setResult(0);
                // Do nothing
                dialog.cancel();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
        System.out.println("after call dialog");
//        if(is_deleted[0] == true){
//            System.out.println("call exit");
//            Intent intent = new Intent(this,MainActivity.class);
//            startActivity(intent);
//            this.finish();
//        }
    }

    public void onClickSuaTaiKhoan(View view) {
        tenDangNhapEdt.setEnabled(true);
        matKhauEdt.setEnabled(true);
        if(suaBtn.getTag().equals("sua")){
            suaBtn.setText("Cập nhật");
            suaBtn.setTag("ok");
        }
        else if(suaBtn.getTag().equals("ok")){
            suaBtn.setText("Sửa tài khoản");
            suaBtn.setTag("sua");
            if(kiemTraThongTin()==1){
                int check = UserDAO.updateUser(loadUser.getTenDangNhap(), tenDangNhapEdt.getText().toString().trim(), matKhauEdt.getText().toString().trim(), database);
                if(check == 1){
                    Toast.makeText(this, "Cập nhật thành công !", Toast.LENGTH_SHORT).show();
                    tenDangNhapEdt.setEnabled(false);
                    matKhauEdt.setEnabled(false);
                    loadUser.setMatKhau(matKhauEdt.getText().toString().trim());
                    loadUser.setTenDangNhap(tenDangNhapEdt.getText().toString().trim());
                }
            }
        }
    }
    private int kiemTraThongTin() {
        int ret = 1;
        String tenDangNhap = tenDangNhapEdt.getText().toString().trim();
        String matKhau = matKhauEdt.getText().toString().trim();
        if (tenDangNhap.isEmpty()) {
            tenDangNhapEdt.setError("Không được để trống !");
            ret = 0;
        } else if (!tenDangNhap.equals(loadUser.getTenDangNhap()) && UserDAO.kiemTraTonTaiTenDN(tenDangNhap, database) == 1) {
            tenDangNhapEdt.setError("Tên đăng nhập đã được sử dụng !");
            ret = 0;
        }
        if (matKhau.isEmpty()) {
            tenDangNhapEdt.setError("Không được để trống !");
            ret = 0;
        }
        return ret;
    }
}