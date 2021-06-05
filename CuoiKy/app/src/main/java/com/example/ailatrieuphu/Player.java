package com.example.ailatrieuphu;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;

import CSDL_bean.CauHoi;

public class Player extends AppCompatActivity {
    ImageButton stopImbtn, changeImbtn, namMuoiImbtn, audienceImbtn, callImbtn;
    TextView timerTv, questionTv, levelTv;
    ArrayList<CauHoi> danhSachCauHoi;
    int index;
    CountDownTimer cTimer = null;

    CSDLAilatrieuphu database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        setControl();
    }

    private void setControl() {
        // cài đặt tiêu đề cho action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        stopImbtn = findViewById(R.id.imbtnStop_Player);
        changeImbtn = findViewById(R.id.imbtnChange_Player);
        namMuoiImbtn = findViewById(R.id.imbtn5050_Player);
        audienceImbtn = findViewById(R.id.imbtnAudience_Player);
        callImbtn = findViewById(R.id.imbtnCall_Player);
        timerTv = findViewById(R.id.tvTimer_Player);
        questionTv = findViewById(R.id.tvQuestion_Player);
        levelTv = findViewById(R.id.tvLevel_Player);

        danhSachCauHoi = new ArrayList<>();
        database = new CSDLAilatrieuphu(this);

        stopImbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater layoutInflater = LayoutInflater.from(Player.this);
                View canhBaoDialog = layoutInflater.inflate(R.layout.canh_bao_dialog, null); // tìm dialog view layout từ inflater
                MaterialAlertDialogBuilder alertDialogBuilder = new MaterialAlertDialogBuilder(Player.this); // tạo dialog builder : lớp hỗ trợ xây dựng dialog
                alertDialogBuilder.setView(canhBaoDialog); // set view tìm được cho dialog
                TextView tenCanhBao = (TextView) canhBaoDialog.findViewById(R.id.tieuDeTv_CanhBaoDialog); // lấy control các trường đã tạo trên dialog
                TextView noiDungCanhBao = canhBaoDialog.findViewById(R.id.noiDungTv_CanhBaoDialog);
                tenCanhBao.setText("");
                noiDungCanhBao.setText("Bạn muốn dừng chơi?");
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("Dừng chơi", // cài đặt nút đồng ý hành động
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        ketThucLuotChoi();
                                    }
                                })
                        .setNegativeButton("Huỷ", // cài đặt nút huỷ hành đọng
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });

                AlertDialog alertDialog = alertDialogBuilder.create(); // tạo dialog từ dialog builder
                alertDialog.show();//show diaglo
            }
        });

        changeImbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeImbtn.setVisibility(View.INVISIBLE);
//                danhSachCauHoi.remove(index);
//                hienThiCauHoi(index);
                cancelTimer();
                startTimer();
            }
        });
//        getQuestions();
//        hienThiCauHoi(index);

    }

    private void getQuestions() {
        /**
         * lấy danh sách các câu hỏi cho lượt chơi - 16 câu
         *
         * @return 0 nếu không có lỗi.
         */
        // lấy danh sách câu hỏi - 16 câu
//        danhSachCauHoi = CauHoi

    }

    private void hienThiCauHoi(int index) {
        // lấy câu hỏi tại vị trí index
        CauHoi c = danhSachCauHoi.get(index);
        questionTv.setText(c.getNoiDung());
        String[] dapAn = c.getDapAn();
//        caseATv.setText(dapAn[0]);
//        caseBTv.setText(dapAn[1]);
//        caseCTv.setText(dapAn[2]);
//        caseDTv.setText(dapAn[3]);
    }

    //start timer function
    void startTimer() {
        /**
         * Tạo bộ đếm thời gian
         */
        cTimer = new CountDownTimer(15000, 1000) {
            public void onTick(long millisUntilFinished) {
                timerTv.setText(String.valueOf(millisUntilFinished / 1000));
            }
            public void onFinish() {
                LayoutInflater layoutInflater = LayoutInflater.from(Player.this);
                View canhBaoDialog = layoutInflater.inflate(R.layout.canh_bao_dialog, null); // tìm dialog view layout từ inflater
                MaterialAlertDialogBuilder alertDialogBuilder = new MaterialAlertDialogBuilder(Player.this); // tạo dialog builder : lớp hỗ trợ xây dựng dialog
                alertDialogBuilder.setView(canhBaoDialog); // set view tìm được cho dialog
                TextView tenCanhBao = (TextView) canhBaoDialog.findViewById(R.id.tieuDeTv_CanhBaoDialog); // lấy control các trường đã tạo trên dialog
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
                                    }
                                });

                AlertDialog alertDialog = alertDialogBuilder.create(); // tạo dialog từ dialog builder
                alertDialog.show();//show diaglo
            }
        };
        cTimer.start();
    }

    void cancelTimer() {
        /**
         * huỷ timer
         *
         * @return arrayList công trình
         */
        if(cTimer!=null)
            cTimer.cancel();
    }

    private int ketThucLuotChoi(){
        /**
         * kết thức lượt chơi hiện tại. Hiển thị dialog, thông báo số câu trả lời đúng.
         *
         */
        try{
            LayoutInflater layoutInflater = LayoutInflater.from(this);
            View stopGameDialog = layoutInflater.inflate(R.layout.stop_game_dialog, null); // tìm dialog view layout từ inflater
            MaterialAlertDialogBuilder alertDialogBuilder = new MaterialAlertDialogBuilder(this); // tạo dialog builder : lớp hỗ trợ xây dựng dialog
            alertDialogBuilder.setView(stopGameDialog); // set view tìm được cho dialog
            TextView tenCanhBao = (TextView) stopGameDialog.findViewById(R.id.tieuDeTv_StopGameDialog); // lấy control các trường đã tạo trên dialog
            TextView noiDungCanhBao = (TextView) stopGameDialog.findViewById(R.id.noiDungTv_StopGameDialog);
            if (index <= 10){
                tenCanhBao.setText("Cố gắng hơn nhé !!");
                noiDungCanhBao.setText("Bạn trả lời đúng " + (index) +" câu.");
            }
            else if (index < 15){
                tenCanhBao.setText("Xuất sắc !!");
                noiDungCanhBao.setText("Bạn trả lời đúng " + (index) +" câu.");
            }
            else{
                tenCanhBao.setText("Tuyệt vời !!!");
                noiDungCanhBao.setText("Bạn trả lời đúng tất cả 15 câu.");
            }
            alertDialogBuilder
                    .setCancelable(false)
                    .setPositiveButton("OK", // cài đặt nút đồng ý hành động
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            });

            AlertDialog alertDialog = alertDialogBuilder.create(); // tạo dialog từ dialog builder
            alertDialog.show();//show diaglo
            return 0;
        }
        catch (Exception e){
            return -1;
        }
    }
}