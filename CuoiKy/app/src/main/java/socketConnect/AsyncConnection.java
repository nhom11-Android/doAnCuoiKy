package socketConnect;
//
//import java.io.BufferedReader;
//import java.io.BufferedWriter;
//import java.io.DataInputStream;
//import java.io.DataOutputStream;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.OutputStreamWriter;
//import java.net.InetSocketAddress;
//import java.net.Socket;
//import java.net.UnknownHostException;
//
//import android.util.Log;
//import android.widget.TextView;
//
//
//public class AsyncConnection extends android.os.AsyncTask<Void, String, Exception> {
//    private String url;
//    private int port;
//    private int timeout;
//    private ConnectionHandler connectionHandler;
//
//    private BufferedReader in;
//    private BufferedWriter out;
//    DataInputStream in1;
//    DataOutputStream out1;
//    private Socket socket;
//    private boolean interrupted = false;
//    TextView textUI;
//    private String TAG = getClass().getName();
//
//    public AsyncConnection(String url, int port, int timeout, ConnectionHandler connectionHandler) {
//        this.url = url;
//        this.port = port;
//        this.timeout = timeout;
//        this.connectionHandler = connectionHandler;
//    }
//    public void setHandleWorkOnUi(TextView textUI){
//        this.textUI = textUI;
//    }
//    @Override
//    protected void onProgressUpdate(String... values) {
//        super.onProgressUpdate(values);
//    }
//
//    @Override
//    protected void onPostExecute(Exception result) {
//        super.onPostExecute(result);
//        Log.d(TAG, "Finished communication with the socket. Result = " + result);
//        //TODO If needed move the didDisconnect(error); method call here to implement it on UI thread.
//    }
//
//    @Override
//    protected Exception doInBackground(Void... params) {
//        Exception error = null;
////        this.connectionHandler = new ConnectionHandler() {
////            @Override
////            public void didReceiveData(String data) {
////                System.out.println("server tell: " + data);
////                textUI.setText(data);
////            }
////
////            @Override
////            public void didDisconnect(Exception error) {
////                disconnect();
////                System.out.println("Socket disconnect");
////            }
////
////            @Override
////            public void didConnect() {
////                System.out.println("Connect to server");
////            }
////        };
//        try {
//            Log.d(TAG, "Opening socket connection.");
//            socket = new Socket();
//            socket.connect(new InetSocketAddress(url, port));
//
////            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
////            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
//            in1 = new DataInputStream(socket.getInputStream());
//            out1 = new DataOutputStream(socket.getOutputStream());
//            connectionHandler.didConnect();
//
//            while (!interrupted) {
//                String line = in1.readUTF();
//                System.out.println(line);
//                //Log.d(TAG, "Received:" + line);
//                connectionHandler.didReceiveData(line);
//            }
//        } catch (UnknownHostException ex) {
//            Log.e(TAG, "doInBackground(): " + ex.toString());
//            error = interrupted ? null : ex;
//        } catch (IOException ex) {
//            Log.d(TAG, "doInBackground(): " + ex.toString());
//            error = interrupted ? null : ex;
//        } catch (Exception ex) {
//            Log.e(TAG, "doInBackground(): " + ex.toString());
//            error = interrupted ? null : ex;
//        } finally {
//            try {
//                socket.close();
//                out.close();
//                in.close();
//            } catch (Exception ex) {
//            }
//        }
//
//        connectionHandler.didDisconnect(error);
//        return error;
//    }
//
//    public void write(final String data) {
//        try {
////            Log.d(TAG, "writ(): data = " + data);
////            out.write(data + "\n");
////            out.flush();
//            out1.writeUTF(data);
//        } catch (IOException ex) {
//            Log.e(TAG, "write(): " + ex.toString());
//        } catch (NullPointerException ex) {
//            Log.e(TAG, "write(): " + ex.toString());
//        }
//    }
//
//    public void disconnect() {
//        try {
//            Log.d(TAG, "Closing the socket connection.");
//
//            interrupted = true;
//            if (socket != null) {
//                socket.close();
//            }
//            if (out != null & in != null) {
//                out.close();
//                in.close();
//            }
//        } catch (IOException ex) {
//            Log.e(TAG, "disconnect(): " + ex.toString());
//        }
//    }
//}