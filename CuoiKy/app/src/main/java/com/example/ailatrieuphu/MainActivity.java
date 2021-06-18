package com.example.ailatrieuphu;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import CSDL_bean.CauHoi;
import CSDL_bean.User;
import DAO.BangXepHangDAO;
import DAO.CauHoiDAO;
import DAO.UserDAO;
import myHelper.HttpWorking;
import myHelper.MySound;
import myHelper.MySuperFunc;

public class MainActivity extends AppCompatActivity {
    public static final String mySettingRef = "SettingRef";
    CSDLAilatrieuphu database = new CSDLAilatrieuphu(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        Log.d("test", "create");
//        chiGoiMotLanDeKhoiTaoDatabase(); // chỉ 1 lần
//        BangXepHangDAO.xoaTatCaBanGhi(database);
        boolean checkLogon = checkingIsLoginBefore();
        if(checkLogon){ // nếu đúng thì bỏ qua khỏi đăng nhập
            String tenDangNhap = getSharedPreferences(mySettingRef,MODE_PRIVATE).getString("tenDangNhap","no_user");
            Intent intent = new Intent(this,MainMenu.class);
            intent.putExtra("tenDangNhap",tenDangNhap);
            startActivity(intent);
        }
    }

    private void chiGoiMotLanDeKhoiTaoDatabase() {
        cauhoi2Database();
//        User a = new User("letuan123","12345","thanhtuan9906@gmail.com","https://github.com/nhom11-Android/doAnCuoiKy/blob/main/icon.jpg");
        User b = new User("user2","12345","n17dcat061@student.ptithcm.edu.vn","https://github.com/nhom11-Android/doAnCuoiKy/blob/main/icon.jpg");
//        UserDAO.themUser(a,database);
        UserDAO.themUser(b,database);
    }

    private boolean checkingIsLoginBefore() {
        SharedPreferences pref = getSharedPreferences(mySettingRef,MODE_PRIVATE);
        String is_login = pref.getString("is_login","not_login");
        if(is_login.equals("not_login")) return false;
        return true;

    }

    private void cauhoi2Database() {

        InputStream inputStream = this.getResources().openRawResource(R.raw.cauhoi);

        InputStreamReader inputreader = new InputStreamReader(inputStream);
        BufferedReader buffreader = new BufferedReader(inputreader);
        String line;
        StringBuilder text = new StringBuilder();

        try {
            while (( line = buffreader.readLine()) != null) {
                String noiDung;
                String[] dapAn = new String[4];
                String dapAnDung;
                String chuyenNganh;
                int doKho;
                if(line.equalsIgnoreCase("start")){
                    line = buffreader.readLine();
                }
                noiDung = line;
                line = buffreader.readLine();
                line = buffreader.readLine();
                dapAn[0] = line;
                line = buffreader.readLine();
                line = buffreader.readLine();
                dapAn[1] = line;
                line = buffreader.readLine();
                line = buffreader.readLine();
                dapAn[2] = line;
                line = buffreader.readLine();
                line = buffreader.readLine();
                dapAn[3] = line;
                line = buffreader.readLine();
                line = buffreader.readLine();
                dapAnDung = line.substring(13, 14);
                line = buffreader.readLine();
                line = buffreader.readLine();
                chuyenNganh = line.substring(12);
                line = buffreader.readLine();
                line = buffreader.readLine();
                doKho = Integer.parseInt(line.substring(8));
                System.out.println(noiDung
                        + "\n" + dapAn[0]
                        + "\n" + dapAn[1]
                        + "\n" + dapAn[2]
                        + "\n" + dapAn[3]
                        + "\n" + dapAnDung
                        + "\n" + chuyenNganh
                        + "\n" + doKho + "\n=============================================\n");
                CauHoi cauHoi = new CauHoi(noiDung, dapAn, dapAnDung, chuyenNganh, doKho);
                CauHoiDAO.themCauHoi(cauHoi,database);
//                return cauHoi;
            }
        } catch (IOException e) {
            return ;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("test", "pause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!MySound.nhacNenIsPlaying())
            setSound();
    }

    private void setSound() {
        MySound.startNhacNen(MainActivity.this, R.raw.nhac_nen);
    }

    public void onClickTaoTaiKhoan(View view) {
        Intent intent = new Intent(this,TaoTaiKhoan.class);
        startActivity(intent);
    }

    public void onClickDangNhap(View view) {
        Intent intent = new Intent(this,DangNhap.class);
        startActivity(intent);
    }

    public void onClickChoiNgay(View view) {
        if(MySound.nhacNenIsPlaying())
            MySound.stopNhacNen();
        Intent intent = new Intent(this,Player.class);
        startActivity(intent);
    }
}