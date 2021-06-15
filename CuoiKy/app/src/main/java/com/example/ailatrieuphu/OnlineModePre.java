package com.example.ailatrieuphu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
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

import socketConnect.ConnectionHandler;

public class OnlineModePre extends AppCompatActivity {
    TextView infoRoomTv;
    String join = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_mode_pre);
        setControl();
        setConnectToServer();
    }

    private void setConnectToServer() {
        AsyncConnection asyncConnection = new AsyncConnection("192.168.114.63", 1234);
        asyncConnection.execute();
    }

    private void setToView(String info) {
        infoRoomTv.setText(info+"/2");
        if(info.equals("Server: 2")) System.out.println("du nguoi roi. load cau hoi");
    }

    private void setControl() {
        infoRoomTv = findViewById(R.id.inRoomInfo_OMP);
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
                    String line = in1.readUTF();
                    System.out.println(line);
//                    join = line;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            setToView(line);
                        }
                    });

                    //Log.d(TAG, "Received:" + line);
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