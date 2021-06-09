package com.example.ailatrieuphu;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.Random;

import CSDL_bean.CauHoi;
import DAO.CauHoiDAO;

public class Player extends AppCompatActivity {
    ImageButton stopImbtn, changeImbtn, namMuoiImbtn, audienceImbtn, callImbtn;
    TextView timerTv, questionTv, levelTv;
    RadioButton caseARb, caseBRb, caseCRb, caseDRb;
    RadioGroup danhSachDapAn;
    ArrayList<CauHoi> danhSachCauHoi;
    int index, level = 1;
    CountDownTimer cTimer = null;
    AlertDialog dialogA = null;
    long currentTime;
    SQLiteOpenHelper database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        setControl();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(dialogA != null){
            dialogA.cancel();
            dialogA = null;
        }
    }

    private void setControl() {
        // ẩn action bar
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
        caseARb = findViewById(R.id.rbCaseA_Player);
        caseBRb = findViewById(R.id.rbCaseB_Player);
        caseCRb = findViewById(R.id.rbCaseC_Player);
        caseDRb = findViewById(R.id.rbCaseD_Player);
        danhSachDapAn = findViewById(R.id.danhSachDapAnRg_Player);

        danhSachCauHoi = new ArrayList<>();
        database = new CSDLAilatrieuphu(this);

        stopImbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater layoutInflater = LayoutInflater.from(Player.this);
                View canhBaoDialog = layoutInflater.inflate(R.layout.canh_bao_dialog, null); // tìm dialog view layout từ inflater
                MaterialAlertDialogBuilder alertDialogBuilder = new MaterialAlertDialogBuilder(Player.this); // tạo dialog builder : lớp hỗ trợ xây dựng dialog
                alertDialogBuilder.setView(canhBaoDialog); // set view tìm được cho dialog
                TextView tenCanhBao = canhBaoDialog.findViewById(R.id.tieuDeTv_CanhBaoDialog); // lấy control các trường đã tạo trên dialog
                TextView noiDungCanhBao = canhBaoDialog.findViewById(R.id.noiDungTv_CanhBaoDialog);
                tenCanhBao.setText("");
                noiDungCanhBao.setText("Bạn muốn dừng chơi?");
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("Dừng chơi", // cài đặt nút đồng ý hành động
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        cancelTimer();
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
                alertDialog.show();//show dialog
            }
        });

        changeImbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeImbtn.setVisibility(View.INVISIBLE);
                index++;
                hienThiCauHoi();
                cancelTimer();
                startTimer();
            }
        });

        namMuoiImbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // lấy đáp án đúng và một câu hỏi bất kỳ
//                CauHoi c = danhSachCauHoi.get(index);
//                int dapAnDung = -1;
                int dapAnDung = 1;
//                String tmp = c.getDapAnDung();
//                if (tmp.equals("A")){
//                    dapAnDung = 0;
//                }
//                else if(tmp.equals("B")){
//                    dapAnDung = 1;
//                }
//                else if(tmp.equals("C")){
//                    dapAnDung = 2;
//                }
//                else {
//                    dapAnDung = 3;
//                }

                int random = -1;
                Random rand = new Random();
                do {
                    random = rand.nextInt(4);

                }while(random == dapAnDung);

                //Hiển thị đáp án đúng và 1 đáp án ngẫu nhiên.
                namMuoiImbtn.setVisibility(View.INVISIBLE);
                caseARb.setVisibility(View.INVISIBLE);
                caseBRb.setVisibility(View.INVISIBLE);
                caseCRb.setVisibility(View.INVISIBLE);
                caseDRb.setVisibility(View.INVISIBLE);
                troGiupDapAn(dapAnDung);
                troGiupDapAn(random);
            }
        });

//        getQuestions();
        try {
            Thread.sleep(1000);
            hienThiCauHoi();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void getQuestions() {
        /**
         * Lấy danh sách các câu hỏi cho lượt chơi - 18 câu
         *
         * @return 0 nếu không có lỗi.
         */
        danhSachCauHoi = CauHoiDAO.layBoCauHoi(database);
    }

    private int hienThiCauHoi() {
        /**
         * Hiển thị câu hỏi và các đáp án tại vị trí index
         *
         * @return 0 nếu không có lỗi.
         */
        try {
            if (level <= 15) {
                levelTv.setText(String.valueOf(level));
//                CauHoi c = danhSachCauHoi.get(pos);
//                questionTv.setText(c.getNoiDung());
//                String[] dapAn = c.getDapAn();
//                caseATv.setText(dapAn[0]);
//                caseBTv.setText(dapAn[1]);
//                caseCTv.setText(dapAn[2]);
//                caseDTv.setText(dapAn[3]);
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
                timerTv.setText(String.valueOf(millisUntilFinished / 1000));
                currentTime = millisUntilFinished / 1000;
            }
            public void onFinish() {
                LayoutInflater layoutInflater = LayoutInflater.from(Player.this);
                View canhBaoDialog = layoutInflater.inflate(R.layout.canh_bao_dialog, null); // tìm dialog view layout từ inflater
                MaterialAlertDialogBuilder alertDialogBuilder = new MaterialAlertDialogBuilder(Player.this); // tạo dialog builder : lớp hỗ trợ xây dựng dialog
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
                                    }
                                });

                AlertDialog alertDialog = alertDialogBuilder.create(); // tạo dialog từ dialog builder
                alertDialog.show();//show dialog
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
            LayoutInflater layoutInflater = LayoutInflater.from(Player.this);
            View canhBaoDialog = layoutInflater.inflate(R.layout.canh_bao_dialog, null); // tìm dialog view layout từ inflater
            MaterialAlertDialogBuilder alertDialogBuilder = new MaterialAlertDialogBuilder(Player.this); // tạo dialog builder : lớp hỗ trợ xây dựng dialog
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
                                    dialogA = null;
                                    if (currentTime != 0)
                                        kiemTraDapAn(radioSelected);
                                }
                            })
                    .setNegativeButton("Huỷ", // cài đặt nút huỷ hành đọng
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                    dialogA = null;
                                }
                            });

            AlertDialog alertDialog = alertDialogBuilder.create(); // tạo dialog từ dialog builder
            alertDialog.show();//show dialog
            dialogA = alertDialog;
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
         *  Nếu đúng, tăng index lên 1, gọi hàm hienThiCauHoi()
         *  Ngược lại, kết thức lượt chơi, gọi hàm ketThucLuotChoi()
         *
         */
        cancelTimer();
        try {
//            CauHoi c = danhSachCauHoi.get(index);
//            RadioButton dapAnDung = getDapAnDung(c.getDapAnDung());
            RadioButton dapAnDung = caseBRb;
            dapAnDung.setBackgroundResource(R.drawable.player_answer_background_true);
            if(dapAnChon.getId() == dapAnDung.getId()){
                level++;
                if(level == 6){
                    index = 6;
                }
                else if(level == 11){
                    index = 12;
                }
                else
                    index++;
                Runnable r = new Runnable() {
                    @Override
                    public void run(){
                        hienThiCauHoi();
                        dapAnDung.setBackgroundResource(R.drawable.btn_answer);
                    }
                };
                Handler h = new Handler();
                h.postDelayed(r, 1000);
            }
            else{
                dapAnChon.setBackgroundResource(R.drawable.player_answer_background_wrong);
                Runnable r = new Runnable() {
                    @Override
                    public void run(){
                        ketThucLuotChoi();
                    }
                };
                Handler h = new Handler();
                h.postDelayed(r, 1000);
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
        try{
            LayoutInflater layoutInflater = LayoutInflater.from(this);
            View stopGameDialog = layoutInflater.inflate(R.layout.stop_game_dialog, null); // tìm dialog view layout từ inflater
            MaterialAlertDialogBuilder alertDialogBuilder = new MaterialAlertDialogBuilder(this); // tạo dialog builder : lớp hỗ trợ xây dựng dialog
            alertDialogBuilder.setView(stopGameDialog); // set view tìm được cho dialog
            TextView tenCanhBao = stopGameDialog.findViewById(R.id.tieuDeTv_StopGameDialog); // lấy control các trường đã tạo trên dialog
            TextView noiDungCanhBao = stopGameDialog.findViewById(R.id.noiDungTv_StopGameDialog);
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
            alertDialog.show();//show dialog
            return 0;
        }
        catch (Exception e){
            return -1;
        }
    }
}