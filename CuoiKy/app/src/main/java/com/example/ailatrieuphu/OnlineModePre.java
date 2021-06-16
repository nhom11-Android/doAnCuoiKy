package com.example.ailatrieuphu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
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
    TextView infoRoomTv,infoCauhoiTv;
    ImageView pl1,pl2,pl3,pl4;
    ImageButton readyBtn;
    String join = "";
    boolean is_full = false;
    ArrayList<CauHoi> dsCauHoi = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_mode_pre);
        setControl();
        configActionBar();
        setConnectToServer();
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
//        infoRoomTv = findViewById(R.id.inRoomInfo_OMP);
        infoCauhoiTv = findViewById(R.id.cauHoiInfo_OMP);
        readyBtn = findViewById(R.id.readyBtn_OMP);
        pl1 = findViewById(R.id.player1);
        pl2 = findViewById(R.id.player2);
    }
    private void setConnectToServer() {
        readyBtn.setEnabled(false);
        AsyncConnection asyncConnection = new AsyncConnection("192.168.1.5", 1234);
        asyncConnection.execute();
    }
    public ArrayList<String> cauHoi2String(ArrayList<CauHoi> dsCauHoi){
        ArrayList<String> ret = new ArrayList<>();
        for(CauHoi i:dsCauHoi){
            ret.add(i.getNoiDung());
            ret.add(i.getDapAn()[0]);
            ret.add(i.getDapAn()[1]);
            ret.add(i.getDapAn()[2]);
            ret.add(i.getDapAn()[3]);
            ret.add(i.getDapAnDung());
            ret.add(i.getChuyenNganh());
            ret.add(String.valueOf(i.getDoKho()));
        }
        return  ret;
    }

    public void onClickReadyPlayOnline(View view) {
        ArrayList<String> strings = cauHoi2String(dsCauHoi);
        Intent intent = new Intent(this,PlayerOnline.class);
        intent.putStringArrayListExtra("danhSachCauHoi",strings);
        startActivity(intent);
    }


    class AsyncConnection extends android.os.AsyncTask<Void, String, Exception> {
        private String url;
        private int port;
        private int timeout;

        private BufferedReader in;
        private BufferedWriter out;
        DataInputStream in1;
        DataOutputStream out1;
        private Socket socket;
        private boolean interrupted = false;
        private String TAG = getClass().getName();

        public AsyncConnection(String url, int port) {
            this.url = url;
            this.port = port;
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
                    if(dsCauHoi.size()==15){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                readyBtn.setEnabled(true);
                                readyBtn.setImageResource(R.drawable.launchpad_50px);
                            }
                        });
                    }
                    String command = in1.readUTF();
//                    System.out.println(line);
//                    join = line;
                    if(command.equals("start")==false) {
                        String soLuongUser = in1.readUTF();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
//                                infoRoomTv.setText(soLuongUser +"/2");
                                if(soLuongUser.equals("1")){
                                    pl1.setImageResource(R.drawable.player_50px);
                                }
                                if(soLuongUser.equals("2")){
                                    pl2.setImageResource(R.drawable.player_50px);
                                }
                            }
                        });
                    } else{ // nhan cau hoi
                        String noidung = in1.readUTF();
                        String dapan1 = in1.readUTF();
                        String dapan2 = in1.readUTF();
                        String dapan3 = in1.readUTF();
                        String dapan4 = in1.readUTF();
                        String daadung = in1.readUTF();
                        String chuyennganh = in1.readUTF();
                        String dokho = in1.readUTF();
                        Log.d("cauHoi "+String.valueOf(dsCauHoi.size()), "đã nhận: \n" + noidung
                                + "\n" + dapan1
                                + "\n" + dapan2
                                + "\n" + dapan3
                                + "\n" + dapan4
                                + "\n" + daadung
                                + "\n" + chuyennganh
                                + "\n" + dokho);
                        CauHoi x = new CauHoi(noidung, new String[]{dapan1, dapan2, dapan3, dapan4},daadung,chuyennganh,Integer.parseInt(dokho));
                        dsCauHoi.add(x);
                        Thread.sleep(500);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                infoCauhoiTv.setText(String.valueOf(dsCauHoi.size()));
                            }
                        });
                    }
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