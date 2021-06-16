package com.example.ailatrieuphu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import CSDL_bean.CauHoi;
import socketConnect.ConnectionHandler;

public class OnlineModePre extends AppCompatActivity {
    TextView infoCauhoiTv,infoDiemTv,infoDiemLableTv;
    ImageView pl1, pl2, pl3, pl4;
    ImageButton readyBtn;
    String diem="";
    public boolean is_done = false;
    public int diemCuaToi;
    String tenDangNhap;
    AsyncConnection asyncConnection;
    ArrayList<CauHoi> dsCauHoi = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_mode_pre);
        setControl();
        configActionBar();
        setConnectToServer();
        SharedPreferences prefs = getSharedPreferences(MainActivity.mySettingRef, MODE_PRIVATE);
        tenDangNhap = prefs.getString("tenDangNhap", "None");
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
                if(asyncConnection.isCancelled()==false) asyncConnection.cancel(true);
                this.finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setControl() {
//        infoRoomTv = findViewById(R.id.inRoomInfo_OMP);
        infoCauhoiTv = findViewById(R.id.cauHoiInfo_OMP);
        readyBtn = findViewById(R.id.readyBtn_OMP);
        pl1 = findViewById(R.id.player1);
        pl2 = findViewById(R.id.player2);
         infoDiemTv = findViewById(R.id.infoDiemHidden_OMP);
        infoDiemLableTv = findViewById(R.id.hiddenLabel_OMP);
    }

    private void setConnectToServer() {
        readyBtn.setEnabled(false);
        asyncConnection = new AsyncConnection("192.168.1.5", 1234,this);
        asyncConnection.execute();
    }

    public ArrayList<String> cauHoi2String(ArrayList<CauHoi> dsCauHoi) {
        ArrayList<String> ret = new ArrayList<>();
        for (CauHoi i : dsCauHoi) {
            ret.add(i.getNoiDung());
            ret.add(i.getDapAn()[0]);
            ret.add(i.getDapAn()[1]);
            ret.add(i.getDapAn()[2]);
            ret.add(i.getDapAn()[3]);
            ret.add(i.getDapAnDung());
            ret.add(i.getChuyenNganh());
            ret.add(String.valueOf(i.getDoKho()));
        }
        return ret;
    }

    /**
     * @param view hàm gọi player online chơi trò chơi
     */
    public void onClickReadyPlayOnline(View view) {
        ArrayList<String> strings = cauHoi2String(dsCauHoi);
        Intent intent = new Intent(this, PlayerOnline.class);
        intent.putStringArrayListExtra("danhSachCauHoi", strings);
        startActivityForResult(intent,2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==2 && resultCode== Activity.RESULT_OK){
            infoDiemTv.setVisibility(View.VISIBLE);
            readyBtn.setVisibility(View.INVISIBLE);
            infoDiemLableTv.setVisibility(View.VISIBLE);
            diemCuaToi = data.getIntExtra("diemCuaToi", 0);
            String remain = infoDiemTv.getText().toString();
            remain = (remain + "\n" + tenDangNhap + " : " + diemCuaToi);
            infoDiemTv.setText(remain);
            is_done = true;
            if(asyncConnection.isCancelled()==false) asyncConnection.cancel(true);
        }
    }

    class AsyncConnection extends android.os.AsyncTask<Void, String, Exception> {
        private String url;
        private int port;

        private BufferedReader in;
        private BufferedWriter out;
        DataInputStream in1;
        DataOutputStream out1;
        private Socket socket;
        private boolean interrupted = false;
        private String TAG = getClass().getName();
        Context parent;
        public AsyncConnection(String url, int port,Context parent) {
            this.url = url;
            this.port = port;
            this.parent = parent;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Exception result) {
            super.onPostExecute(result);

            Log.d(TAG, "Finished communication with the socket. Result = " + result);
            //TODO If needed move the didDisconnect(error); method call here to implement it on UI thread.
        }

        @Override
        protected Exception doInBackground(Void... params) {
            Exception error = null;
            try {
                Log.d(TAG, "Opening socket connection.");
                socket = new Socket();
                socket.connect(new InetSocketAddress(url, port));
                in1 = new DataInputStream(socket.getInputStream());
                out1 = new DataOutputStream(socket.getOutputStream());

                while (!interrupted) {
                    if(is_done==true){
                        write("diem");
                        write(tenDangNhap);
                        write(String.valueOf(diemCuaToi));
                        interrupted = true;
                    }
                    if (dsCauHoi.size() == 15) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                readyBtn.setEnabled(true);
                                readyBtn.setImageResource(R.drawable.launchpad_50px);
                            }
                        });
                    }
                    String command = in1.readUTF();
                    Log.d("socket tell", command);
                    switch (command) {
                        case "start":
                            String noidung = in1.readUTF();
                            String dapan1 = in1.readUTF();
                            String dapan2 = in1.readUTF();
                            String dapan3 = in1.readUTF();
                            String dapan4 = in1.readUTF();
                            String daadung = in1.readUTF();
                            String chuyennganh = in1.readUTF();
                            String dokho = in1.readUTF();
                            Log.d("cauHoi " + String.valueOf(dsCauHoi.size()), "đã nhận: \n" + noidung
                                    + "\n" + dapan1
                                    + "\n" + dapan2
                                    + "\n" + dapan3
                                    + "\n" + dapan4
                                    + "\n" + daadung
                                    + "\n" + chuyennganh
                                    + "\n" + dokho);
                            CauHoi x = new CauHoi(noidung, new String[]{dapan1, dapan2, dapan3, dapan4}, daadung, chuyennganh, Integer.parseInt(dokho));
                            dsCauHoi.add(x);
                            Thread.sleep(500);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    infoCauhoiTv.setText(String.valueOf(dsCauHoi.size()));
                                }
                            });
                            break;
                        case "Injoin":
                            String whojoin = in1.readUTF();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    pl2.setTag(whojoin);
                                    Toast.makeText(OnlineModePre.this, whojoin + " đã tham gia !", Toast.LENGTH_SHORT).show();
                                }
                            });
                            break;
                        case "user":
                            String soLuongUser = in1.readUTF();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (soLuongUser.equals("1")) {
                                        pl1.setImageResource(R.drawable.player_50px);
                                    }
                                    if (soLuongUser.equals("2")) {
                                        pl2.setImageResource(R.drawable.player_50px);
                                        pl1.setImageResource(R.drawable.player_50px);
                                    }
                                }
                            });
                            break;
                        case "diem":
                            String ten = in1.readUTF(); // nhanaj ten
                            String diem = in1.readUTF(); // nhanaj diem
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    String remain = infoDiemTv.getText().toString();
                                    remain = remain + "\n" + ten + " : " + diem;
                                    infoDiemTv.setText(remain);
                                }
                            });
                            break;
                    }

//                    System.out.println(line);
//                    join = line;
//                    if(!command.equals("start")) {
//                        String soLuongUser = in1.readUTF();
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
////                                infoRoomTv.setText(soLuongUser +"/2");
//                                if(soLuongUser.equals("1")){
//                                    pl1.setImageResource(R.drawable.player_50px);
//                                }
//                                if(soLuongUser.equals("2")){
//                                    pl2.setImageResource(R.drawable.player_50px);
//                                }
//                            }
//                        });
//                    } else if(command.equals("Injoin")) {
//                        String whojoin = in1.readUTF();
//                        Toast.makeText(OnlineModePre.this, whojoin+" đã tham gia !", Toast.LENGTH_SHORT).show();
//                    }
//                    else{ // nhan cau hoi
//                        String noidung = in1.readUTF();
//                        String dapan1 = in1.readUTF();
//                        String dapan2 = in1.readUTF();
//                        String dapan3 = in1.readUTF();
//                        String dapan4 = in1.readUTF();
//                        String daadung = in1.readUTF();
//                        String chuyennganh = in1.readUTF();
//                        String dokho = in1.readUTF();
//                        Log.d("cauHoi "+String.valueOf(dsCauHoi.size()), "đã nhận: \n" + noidung
//                                + "\n" + dapan1
//                                + "\n" + dapan2
//                                + "\n" + dapan3
//                                + "\n" + dapan4
//                                + "\n" + daadung
//                                + "\n" + chuyennganh
//                                + "\n" + dokho);
//                        CauHoi x = new CauHoi(noidung, new String[]{dapan1, dapan2, dapan3, dapan4},daadung,chuyennganh,Integer.parseInt(dokho));
//                        dsCauHoi.add(x);
//                        Thread.sleep(500);
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                infoCauhoiTv.setText(String.valueOf(dsCauHoi.size()));
//                            }
//                        });
//                    }
                }
            } catch (UnknownHostException ex) {
                Log.e(TAG, "doInBackground(): " + ex.toString());
                error = interrupted ? null : ex;
            } catch (IOException ex) {
                Log.d(TAG, "doInBackground(): " + ex.toString());
                error = interrupted ? null : ex;
            } catch (Exception ex) {
                Log.e(TAG, "doInBackground(): " + ex.toString());
                error = interrupted ? null : ex;
            } finally {
                try {
                    socket.close();
                    out.close();
                    in.close();
                } catch (Exception ex) {
                }
            }
            disconnect();
            return error;
        }

        public void write(final String data) {
            try {
                out1.writeUTF(data);
            } catch (IOException ex) {
                Log.e(TAG, "write(): " + ex.toString());
            } catch (NullPointerException ex) {
                Log.e(TAG, "write(): " + ex.toString());
            }
        }

        public void disconnect() {
            try {
                Log.d(TAG, "Closing the socket connection.");

                interrupted = true;
                if (socket != null) {
                    socket.close();
                }
                if (out != null & in != null) {
                    out.close();
                    in.close();
                }
            } catch (IOException ex) {
                Log.e(TAG, "disconnect(): " + ex.toString());
            }
        }
    }
}