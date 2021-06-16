package com.example.ailatrieuphu;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import CSDL_bean.BangXepHang;
import CSDL_bean.CauHoi;
import DAO.BangXepHangDAO;
import DAO.CauHoiDAO;
import myHelper.MySound;

public class PlayerOnline extends AppCompatActivity {

    TextView timerTv, questionTv, levelTv, diemTv, chuanBiTv, batDauTv;
    RadioButton caseARb, caseBRb, caseCRb, caseDRb;
    RadioGroup danhSachDapAn;
    ImageView loadingIv;
    LinearLayout loadingLayout, chuanBiLayout, batDauLayout, playLayout;
    ArrayList<CauHoi> danhSachCauHoi;
    int index = 0, level = 1, diem = 0;

    CountDownTimer cTimer = null;
    AlertDialog dialog1 = null;
    AlertDialog dialog2 = null;
    long currentTime;
    SQLiteOpenHelper database;

    // điều khiển countdowntimer
    boolean isPause;
    long timeRemaining = -1;
    Animation xoay, fadeOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_online);
        setControl();
        setSound();
        setAnimation();
        getDanhSachCauHoi();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(dialog1 != null){
            dialog1.cancel();
            dialog1 = null;
        }
        if(dialog2 != null){
            dialog2.cancel();
            dialog2 = null;
        }
    }

    private void setAnimation(){
        Runnable chuanBi = new Runnable() {
            @Override
            public void run(){
                fadeOut= AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_in);
                chuanBiTv.startAnimation(fadeOut);
            }
        };

        Runnable batDau = new Runnable() {
            @Override
            public void run(){
                chuanBiLayout.setVisibility(View.INVISIBLE);
                batDauLayout.setVisibility(View.VISIBLE);
                fadeOut= AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_in);
                batDauTv.startAnimation(fadeOut);
            }
        };

        Runnable playGame = new Runnable() {
            @Override
            public void run(){
                batDauLayout.setVisibility(View.INVISIBLE);
                playLayout.setVisibility(View.VISIBLE);
                setEvent();
                if (!MySound.nhacNenIsPlaying())
                    MySound.startNhacNen(PlayerOnline.this, R.raw.nhac_nen);
            }
        };

        Handler h1 = new Handler();
        h1.postDelayed(chuanBi, 0);
        Handler h2 = new Handler();
        h2.postDelayed(batDau, 2500);
        Handler h3 = new Handler();
        h3.postDelayed(playGame, 6000);
    }

    private void setSound() {
        MySound.amThanhGame(PlayerOnline.this, R.raw.start);
    }

    private void setControl() {
        // ẩn action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        timerTv = findViewById(R.id.tvTimer_PlayerOnline);
        questionTv = findViewById(R.id.tvQuestion_PlayerOnline);
        levelTv = findViewById(R.id.tvLevel_PlayerOnline);
        diemTv = findViewById(R.id.tvDiem_PlayerOnline);
        loadingIv = findViewById(R.id.loadingIv_PlayerOnline);
        chuanBiTv = findViewById(R.id.chuanBiTv_PlayerOnline);
        batDauTv = findViewById(R.id.startGameTv_PlayerOnline);
        caseARb = findViewById(R.id.rbCaseA_PlayerOnline);
        caseBRb = findViewById(R.id.rbCaseB_PlayerOnline);
        caseCRb = findViewById(R.id.rbCaseC_PlayerOnline);
        caseDRb = findViewById(R.id.rbCaseD_PlayerOnline);
        danhSachDapAn = findViewById(R.id.danhSachDapAnRg_PlayerOnline);
        loadingLayout = findViewById(R.id.loadingLayout_PlayerOnline);
        chuanBiLayout = findViewById(R.id.chuanBiLayout_PlayerOnline);
        batDauLayout = findViewById(R.id.startGameLayout_PlayerOnline);
        playLayout = findViewById(R.id.playGameLayout_PlayerOnline);

        danhSachCauHoi = new ArrayList<>();
        database = new CSDLAilatrieuphu(this);
    }

    private void setEvent() {
        try {
            Thread.sleep(1000);
            diemTv.setText(String.valueOf(diem));
            hienThiCauHoi();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void getDanhSachCauHoi() {
        /**
         * Lấy danh sách các câu hỏi cho lượt chơi - 18 câu
         *
         * @return 0 nếu không có lỗi.
         */
//        danhSachCauHoi = CauHoiDAO.layBoCauHoi(database);
        Intent intent = getIntent();
        ArrayList<String> cauhoiStrings = intent.getStringArrayListExtra("danhSachCauHoi");
        int j = 0;
        while(j<cauhoiStrings.size()){
            String noidung = cauhoiStrings.get(j++);
            String dapan1 = cauhoiStrings.get(j++);
            String dapan2 = cauhoiStrings.get(j++);
            String dapan3 = cauhoiStrings.get(j++);
            String dapan4 = cauhoiStrings.get(j++);
            String daadung = cauhoiStrings.get(j++);
            String chuyennganh = cauhoiStrings.get(j++);
            String dokho = cauhoiStrings.get(j++);
            CauHoi x = new CauHoi(noidung, new String[]{dapan1, dapan2, dapan3, dapan4},daadung,chuyennganh,Integer.parseInt(dokho));
            danhSachCauHoi.add(x);
        }
    }

    private int hienThiCauHoi() {
        /**
         * Hiển thị câu hỏi và các đáp án tại vị trí index
         *
         * @return 0 nếu không có lỗi.
         */
        try {
            if (level <= 15) {
                levelTv.setText("Câu " + String.valueOf(level));
                CauHoi c = danhSachCauHoi.get(index);
                questionTv.setText(c.getNoiDung());
                String[] dapAn = c.getDapAn();
                caseARb.setText(dapAn[0]);
                caseBRb.setText(dapAn[1]);
                caseCRb.setText(dapAn[2]);
                caseDRb.setText(dapAn[3]);
                caseARb.setVisibility(View.VISIBLE);
                caseBRb.setVisibility(View.VISIBLE);
                caseCRb.setVisibility(View.VISIBLE);
                caseDRb.setVisibility(View.VISIBLE);

                danhSachDapAn.clearCheck();
                startTimer();
            }
            else{
                ketThucLuotChoi();
            }
            return 0;
        }
        catch (Exception e){
            return -1;
        }
    }

    void startTimer() {
        /**
         * Tạo bộ đếm thời gian
         */
        cTimer = new CountDownTimer(16000, 1000) {
            public void onTick(long millisUntilFinished) {
                if(isPause){
                    cancel();
                }else{
                    timerTv.setText(String.valueOf(millisUntilFinished / 1000));
                    currentTime = millisUntilFinished / 1000;
                    timeRemaining = millisUntilFinished;
                }
            }
            public void onFinish() {
                LayoutInflater layoutInflater = LayoutInflater.from(PlayerOnline.this);
                View canhBaoDialog = layoutInflater.inflate(R.layout.canh_bao_dialog, null); // tìm dialog view layout từ inflater
                MaterialAlertDialogBuilder alertDialogBuilder = new MaterialAlertDialogBuilder(PlayerOnline.this); // tạo dialog builder : lớp hỗ trợ xây dựng dialog
                alertDialogBuilder.setView(canhBaoDialog); // set view tìm được cho dialog
                TextView tenCanhBao = canhBaoDialog.findViewById(R.id.tieuDeTv_CanhBaoDialog); // lấy control các trường đã tạo trên dialog
                TextView noiDungCanhBao = canhBaoDialog.findViewById(R.id.noiDungTv_CanhBaoDialog);
                tenCanhBao.setText("");
                noiDungCanhBao.setText("Hết giờ !!!");
                noiDungCanhBao.setTextSize(25);
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK", // cài đặt nút đồng ý hành động
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        ketThucLuotChoi();
                                        dialog2 = null;
                                    }
                                });

                AlertDialog alertDialog = alertDialogBuilder.create(); // tạo dialog từ dialog builder
                alertDialog.show();//show dialog
                dialog2 = alertDialog;
            }
        };
        cTimer.start();
    }

    void cancelTimer() {
        /**
         * huỷ timer
         *
         */
        if(cTimer!=null)
            cTimer.cancel();
    }

    private int troGiupDapAn(int pos){
        /**
         *  Hiển thị đáp án A,B,C, D tương ứng với giá trị 0, 1, 2, 3
         *
         */
        try{
            switch (pos){
                case 0:
                    caseARb.setVisibility(View.VISIBLE);
                    break;
                case 1:
                    caseBRb.setVisibility(View.VISIBLE);
                    break;
                case 2:
                    caseCRb.setVisibility(View.VISIBLE);
                    break;
                case 3:
                    caseDRb.setVisibility(View.VISIBLE);
                    break;
            }
            return  0;
        }
        catch (Exception e){
            return -1;
        }
    }

    public int chonDapAn(View view) {
        /**
         *  Chọn đáp án. Hiển thị Dialog, xác nhận đáp án đã chọn.
         */
        try{
            int id = view.getId();
            RadioButton radioSelected = findViewById(id);
            LayoutInflater layoutInflater = LayoutInflater.from(PlayerOnline.this);
            View canhBaoDialog = layoutInflater.inflate(R.layout.canh_bao_dialog, null); // tìm dialog view layout từ inflater
            MaterialAlertDialogBuilder alertDialogBuilder = new MaterialAlertDialogBuilder(PlayerOnline.this); // tạo dialog builder : lớp hỗ trợ xây dựng dialog
            alertDialogBuilder.setView(canhBaoDialog); // set view tìm được cho dialog
            TextView tenCanhBao = canhBaoDialog.findViewById(R.id.tieuDeTv_CanhBaoDialog); // lấy control các trường đã tạo trên dialog
            TextView noiDungCanhBao = canhBaoDialog.findViewById(R.id.noiDungTv_CanhBaoDialog);
            tenCanhBao.setText("");
            noiDungCanhBao.setText("Bạn chọn đáp án\n" + radioSelected.getText());
            alertDialogBuilder
                    .setCancelable(false)
                    .setPositiveButton("Tiếp tục", // cài đặt nút đồng ý hành động
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    cancelTimer();
                                    dialog.cancel();
                                    dialog1 = null;
                                    if (currentTime != 0)
                                        kiemTraDapAn(radioSelected);
                                }
                            })
                    .setNegativeButton("Huỷ", // cài đặt nút huỷ hành đọng
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                    dialog1 = null;
                                }
                            });

            AlertDialog alertDialog = alertDialogBuilder.create(); // tạo dialog từ dialog builder
            alertDialog.show();//show dialog
            dialog1 = alertDialog;
            return 0;
        }
        catch (Exception e){
            return -1;
        }
    }

    private RadioButton getDapAnDung(String dapAn){
        /**
         *  Lấy đáp án của câu hỏi hiện tại
         *
         * @return RadioButton chứa đáp án đúng
         */
        if (dapAn.equals("A"))
            return caseARb;
        else if(dapAn.equals("B"))
            return caseBRb;
        else if(dapAn.equals("C"))
            return caseCRb;
        else
            return caseDRb;
    }

    private int kiemTraDapAn(RadioButton dapAnChon){
        /**
         *  Kiểm tra đáp án đã chọn có đúng không.
         *  Nếu đúng, tăng level lên 1, kiểm tra level và chọn độ khó (index) phù hợp -> gọi hàm hienThiCauHoi()
         *  Ngược lại, kết thức lượt chơi, gọi hàm ketThucLuotChoi()
         *
         */
        cancelTimer();
        int diem = Integer.parseInt(diemTv.getText().toString());
        try {
            CauHoi c = danhSachCauHoi.get(index);
            RadioButton dapAnDung = getDapAnDung(c.getDapAnDung());
//            RadioButton dapAnDung = caseBRb;
            dapAnDung.setBackgroundResource(R.drawable.player_answer_background_true);
            if(dapAnChon.getId() == dapAnDung.getId()){
                MySound.amThanhGame(PlayerOnline.this, R.raw.am_thanh_tra_loi_dung);
//                diem += c.getDoKho();
                diemTv.setText(String.valueOf(diem*100000));
                level++;
                if(level == 6){
                    index = 6;
                }
                else if(level == 11){
                    index = 12;
                }
                else
                    index++;

                Runnable loading = new Runnable() {
                    @Override
                    public void run(){
                        playLayout.setAlpha((float) 0.5);
                        loadingLayout.setVisibility(View.VISIBLE);
                        xoay= AnimationUtils.loadAnimation(getApplicationContext(), R.anim.xoay);
                        loadingIv.startAnimation(xoay);
                    }
                };

                Runnable cauHoiMoi = new Runnable() {
                    @Override
                    public void run(){
                        playLayout.setAlpha((float) 1);
                        loadingLayout.setVisibility(View.INVISIBLE);
                        hienThiCauHoi();
                        dapAnDung.setBackgroundResource(R.drawable.btn_answer);
                    }
                };
                Handler h1 = new Handler();
                h1.postDelayed(loading, 3000);
                Handler h2 = new Handler();
                h2.postDelayed(cauHoiMoi, 4000);
            }
            else{
                MySound.amThanhGame(PlayerOnline.this, R.raw.am_thanh_tra_loi_sai);
                dapAnChon.setBackgroundResource(R.drawable.player_answer_background_wrong);
                Runnable r = new Runnable() {
                    @Override
                    public void run(){
                        ketThucLuotChoi();
                    }
                };
                Handler h = new Handler();
                h.postDelayed(r, 4000);
            }
            return 0;
        }
        catch (Exception e){
            return -1;
        }
    }

    private int ketThucLuotChoi(){
        /**
         * kết thức lượt chơi hiện tại. Hiển thị dialog, thông báo số câu trả lời đúng.
         */
        if(MySound.nhacNenIsPlaying())
            MySound.stopNhacNen();
        MySound.amThanhGame(PlayerOnline.this, R.raw.end);
        try{
            LayoutInflater layoutInflater = LayoutInflater.from(this);
            View stopGameDialog = layoutInflater.inflate(R.layout.stop_game_dialog, null); // tìm dialog view layout từ inflater
            MaterialAlertDialogBuilder alertDialogBuilder = new MaterialAlertDialogBuilder(this); // tạo dialog builder : lớp hỗ trợ xây dựng dialog
            alertDialogBuilder.setView(stopGameDialog); // set view tìm được cho dialog
            TextView tenCanhBao = stopGameDialog.findViewById(R.id.tieuDeTv_StopGameDialog); // lấy control các trường đã tạo trên dialog
            TextView noiDungCanhBao = stopGameDialog.findViewById(R.id.noiDungTv_StopGameDialog);
            String msg = "";
            if (level - 1 <= 10){
                tenCanhBao.setText("Cố gắng hơn nhé !!");
                msg += "Bạn trả lời đúng " + (level - 1) +" câu.\n";
                noiDungCanhBao.setText(msg);
            }
            else if (level - 1 < 15){
                tenCanhBao.setText("Xuất sắc !!");
                msg += "Bạn trả lời đúng " + (level - 1) +" câu.\n";
                noiDungCanhBao.setText(msg);
            }
            else {
                tenCanhBao.setText("Tuyệt vời !!!");
                msg += "Bạn trả lời đúng tất cả 15 câu.\n";
            }
            msg += " Tiền thưởng đạt được " + String.valueOf(diem*100000);
            noiDungCanhBao.setText(msg);

            alertDialogBuilder
                    .setCancelable(false)
                    .setPositiveButton("OK", // cài đặt nút đồng ý hành động
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    SimpleDateFormat simpleDate =  new SimpleDateFormat("dd/MM/yyyy");
                                    Date date = new Date();
                                    String ngay = simpleDate.format(date);
                                    BangXepHang bangXepHang = new BangXepHang(ngay, diem);
                                    int check = BangXepHangDAO.themKyLuc(bangXepHang, database);
                                    if (check != 0)
                                        Toast.makeText(PlayerOnline.this, "Thêm kỷ lục thất bại !", Toast.LENGTH_SHORT).show();
                                    MySound.stopAmThanh();
                                    finish();
                                }
                            });

            AlertDialog alertDialog = alertDialogBuilder.create(); // tạo dialog từ dialog builder
            alertDialog.show();//show dialog
            return 0;
        }
        catch (Exception e){
            return -1;
        }
    }
}