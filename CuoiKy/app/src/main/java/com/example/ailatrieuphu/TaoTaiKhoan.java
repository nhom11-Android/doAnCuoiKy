package com.example.ailatrieuphu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.service.autofill.UserData;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import DAO.UserDAO;
import myHelper.MySuperFunc;

public class TaoTaiKhoan extends AppCompatActivity {
    EditText tenDangNhapEdt, matKhauEdt, mailEdt;
    Button submitBtn;
    CSDLAilatrieuphu database = new CSDLAilatrieuphu(this);
    String message;
    String tenDangNhap;
    String matKhau ;
    String mail;
    String maxacthuc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tao_tai_khoan);
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
        tenDangNhapEdt = (EditText) findViewById(R.id.tenDangNhapEdt_TTK);
        matKhauEdt = (EditText) findViewById(R.id.matKhauEdt_TTK);
        mailEdt = (EditText) findViewById(R.id.mailEdt_TTK);
        submitBtn = findViewById(R.id.submitBtn_TTK);
    }

    public void onClickSubmitAccount(View view) {
        // TODO: 6/11/2021 : gửi mail, gửi mã số, gọi diaglog nhập mã, check mã, lưu tk,

//        mailEdt.is
//        System.out.println("ten dang nhap " + tenDangNhap + "\nmat khau: " + matKhau + "\nmail:" +mail );
        if (kiemTraThongTin() == 1) {
//            System.out.println("đã tạo tài khaonr thành công !");
            // TODO: 6/11/2021 gửi thông tin cho server
            // lấy mã số
            maxacthuc = MySuperFunc.generateCodeRamdom();
            message = mail+"\n"+tenDangNhap+"\n"+matKhau+"\n"+maxacthuc;
            myTask mt = new myTask(this);
            mt.execute();
//            System.out.println("Excute my Task");
            Intent intent = new Intent(this,XacThucTaiKhoan.class);
            intent.putExtra("maXacThuc",maxacthuc);
            intent.putExtra("tenDangNhap",tenDangNhap);
            intent.putExtra("matKhau",matKhau);
            intent.putExtra("mail",mail);
            startActivity(intent);
        }
    }

    private int kiemTraThongTin() {
        int ret = 1;
        tenDangNhap = tenDangNhapEdt.getText().toString().trim();
        matKhau = matKhauEdt.getText().toString().trim();
        mail = mailEdt.getText().toString().trim();
        if (tenDangNhap.isEmpty()) {
            tenDangNhapEdt.setError("Không được để trống !");
            ret = 0;
        } else if (UserDAO.kiemTraTonTaiTenDN(tenDangNhap, database) == 1) {
            tenDangNhapEdt.setError("Tên đăng nhập đã được sử dụng !");
            ret = 0;
        }
        if (matKhau.isEmpty()) {
            tenDangNhapEdt.setError("Không được để trống !");
            ret = 0;
        }
        if (mail.isEmpty()) {
            tenDangNhapEdt.setError("Không được để trống !");
            ret = 0;
        } else {
            String mailPattern = "[a-zA-Z0-9._-]+@[a-zA-Z0-9._-]+.com";
            if (mail.matches(mailPattern) == false) {
                mailEdt.setError("Sai định dạng mail !");
                ret = 0;
            } else if (UserDAO.kiemTraTonTaiMail(mail, database) == 1) {
                mailEdt.setError("Mail đã được sử dụng !");
                ret = 0;
            }
        }
        return ret;
    }

    class myTask extends AsyncTask<String, Void, String> {
        Context parent;

        public myTask(Context parent) {
            this.parent = parent;
        }

        public String connectSever(final String ip, final String port, final String a) {
            String output = null;
            try {
                System.out.println("try connect to serrver");
                Socket cl = new Socket(ip, 32323);//ket noi server
                System.out.println("try connect to serrver");
                PrintWriter pw = new PrintWriter(cl.getOutputStream(), true);//tao luong gui du lieu
                pw.println("" + a);// Gửi dữ liệu đi
                BufferedReader br = new BufferedReader(new InputStreamReader(cl.getInputStream()));//tao luong nhan du lieu
                output = br.readLine();// Đọc dữ liệu trả về từ Server
                cl.close();
            } catch (NumberFormatException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (UnknownHostException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return output;//tra ve ket qua từ Server
        }

        @Override
        protected String doInBackground(String... strings) {
//            String a = strings[0];
            String s = connectSever("192.168.1.5", "8888", message);
            return s;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
//            Toast.makeText(parent, s, Toast.LENGTH_SHORT).show();
            System.out.println(s);
            if(s.trim().equals("Success"))
                Toast.makeText(parent, "Mã xác thực đã gửi tới hòm thư của bạn !", Toast.LENGTH_LONG).show();
        }
    }
}