package com.example.ailatrieuphu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Optional;

import CSDL_bean.BangXepHang;
import CSDL_bean.User;
import DAO.BangXepHangDAO;
import DAO.UserDAO;
import myHelper.MySuperFunc;

public class UserInfoCRUD extends AppCompatActivity {
    String tenDangNhap;
    User loadUser;
    CSDLAilatrieuphu database = new CSDLAilatrieuphu(this);

    EditText mailEdt,tenDangNhapEdt,matKhauEdt;
    Button suaBtn, xoaBtn;
    ImageView userImageIv;
    TextView highScoreTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info_crud);
        tenDangNhap = getIntent().getStringExtra("tenDangNhap");
        loadUser = UserDAO.getUserByTenDangNhap(tenDangNhap,database);
        setControl();
        configActionBar();
        setData();
        setEvent();
    }

    private void setEvent() {

    }

    /**
     *  cài đặt ảnh đại diện và điểm cao cho người chơi
     */
    private void setData() {
        // set ảnh
        String imageUri = loadUser.getUrlImage();
        Picasso.with(this).load(imageUri).into(userImageIv);

        // set điểm cao
        ArrayList<BangXepHang> bangXepHangs = BangXepHangDAO.layBangXepHang(database, -1, tenDangNhap);
        int max =0;
        for (BangXepHang i: bangXepHangs) {
            if(i.getDiem()>max) max = i.getDiem();
        }
        highScoreTv.setText(MySuperFunc.printCurrency(max));
//        if(userImageIv.getDrawable()==null){
//            Toast.makeText(this, "Không thể load ảnh từ url", Toast.LENGTH_SHORT).show();
//            userImageIv.setImageResource(R.drawable.icon_logo_app);
//        }
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

        highScoreTv = findViewById(R.id.highScore_UIC);
        userImageIv = findViewById(R.id.userImage_UIC);
    }

    /**
     * @param view setResul = -2 để trả về result code cho activity cha
     */
    public void onClickxoaTaiKhoan(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Chú ý");
        builder.setMessage("Bạn có chắc muốn xoá tài khoản");

        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // Do nothing but close the dialog
                // TODO: 6/13/2021 xoá  user
                if(UserDAO.deleteUserByMail(loadUser.getEmail(),tenDangNhap,database)==1){
                    Toast.makeText(UserInfoCRUD.this, "Đã xoá người chơi thành công !", Toast.LENGTH_SHORT).show();
                    setResult(-2);
                    // xoá login ra khỏi sharedReference, xoá cờ login, xoá info
                    SharedPreferences.Editor editor = getSharedPreferences(MainActivity.mySettingRef, MODE_PRIVATE).edit();
                    editor.clear(); // xoá tất cả references đã lưu
                    editor.apply();
                    // finish activity
                    UserInfoCRUD.this.finish();
                }
                else{
                    Toast.makeText(UserInfoCRUD.this, "Đã có lỗi xảy ra. Liên hệ nhà sản xuất để biết thêm chi tiết !", Toast.LENGTH_SHORT).show();
                }
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

    /**
     * @param view setResult = -1 để xác định là logout mà ko xoá tài khoản
     */
    public void onClickDangXuatTaiKhoan(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Chú ý");
        builder.setMessage("Bạn có chắc muốn đăng xuất tài khoản ?");

        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // Do nothing but close the dialog
                // TODO: 6/13/2021 thoát user
                // xoá login ra khỏi sharedReference, xoá cờ login, xoá info
                SharedPreferences.Editor editor = getSharedPreferences(MainActivity.mySettingRef, MODE_PRIVATE).edit();
                editor.clear(); // xoá tất cả references đã lưu
                editor.apply();
                setResult(-1);
                UserInfoCRUD.this.finish();
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


    public void onClickChangeUserImage(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
// I'm using fragment here so I'm using getView() to provide ViewGroup
// but you can provide here any other instance of ViewGroup from your Fragment / Activity
        View viewInflated = LayoutInflater.from(this).inflate(R.layout.text_inpu_password, null);
// Set up the input
        final EditText input = (EditText) viewInflated.findViewById(R.id.inputNewUrl);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        builder.setView(viewInflated);

// Set up the buttons
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                String m_Text = input.getText().toString();
                int ret = UserDAO.updateUserImage(loadUser,m_Text,database);
                if(ret == 1){
                    Toast.makeText(UserInfoCRUD.this, "Cập nhật xong !", Toast.LENGTH_SHORT).show();
                    loadUser.setUrlImage(m_Text);
                    String imageUri = loadUser.getUrlImage();
                    Picasso.with(UserInfoCRUD.this).load(imageUri).into(userImageIv);
                } else Toast.makeText(UserInfoCRUD.this, "Cập nhật thất bại !", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();

    }
}