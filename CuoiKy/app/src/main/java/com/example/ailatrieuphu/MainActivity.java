package com.example.ailatrieuphu;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import CSDL_bean.CauHoi;
import DAO.CauHoiDAO;
import myHelper.HttpWorking;
import myHelper.MySound;
import myHelper.MySuperFunc;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        Log.d("test", "create");
//        cauhoi2Database();
    }

    private void cauhoi2Database() {
        CSDLAilatrieuphu database = new CSDLAilatrieuphu(this);
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