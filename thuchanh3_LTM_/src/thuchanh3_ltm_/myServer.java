/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thuchanh3_ltm_;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * ý tưởng chính cho server là: tạo socket ở port 3333 với mỗi client được
 * accept thì tạo 2 thread để nhận gửi dữ liệu
 *
 * @author thanh
 */
public class myServer {
    int count_part = 0;

    int port;
    public static ArrayList<Socket> listClient;

    public myServer(int port) {
        this.port = port;
    }

    public void run() {

        try {
            ServerSocket sv = new ServerSocket(port);
            writeTo write = new writeTo();
            write.start();
            System.out.println("Mở port kết nối ở: " + port + "\nĐang chờ kết nối từ client");
            while (true) {
                Socket soc = sv.accept();
                // thêm client vào list
                listClient.add(soc);
                System.out.println("Kết nối đến client : " + soc);
                write.write2server(String.valueOf(listClient.size()));
                readFrom read = new readFrom(soc);
                read.start();
            }
        } catch (IOException ex) {
            Logger.getLogger(myServer.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void main(String[] args) {
        myServer.listClient = new ArrayList<>();
        myServer newServer = new myServer(1234);
        newServer.run();
    }
}

// create reader / writter for server
class readFrom extends Thread {

    Socket svSoc;

    public readFrom(Socket svSoc) {
        this.svSoc = svSoc;
    }

    @Override
    public void run() {

        DataInputStream recvStream = null;
        try {
                recvStream = new DataInputStream(svSoc.getInputStream());
            while (true) {

                String messg = recvStream.readUTF(); // đọc từ client về
                for (Socket item : myServer.listClient) {
                    if (item.getPort() != svSoc.getPort()) {
                        DataOutputStream dos = new DataOutputStream(item.getOutputStream());
                        dos.writeUTF(messg);
                    }
                }
                System.out.println(messg); // ghi ra màn hình
                
            }
        } catch (Exception e) {
            try {
                recvStream.close();
                svSoc.close();
            } catch (IOException ex) {
                System.out.println("Ngắt kết nối");
            }
        }
    }
}

class writeTo extends Thread {
    DataOutputStream writeToStream = null;
    @Override
    public void run() {
        
        Scanner sc = new Scanner(System.in);
        while (true) {
            String messg = sc.nextLine();
            write2server(messg);
        }
    }
    public void write2server(String messg){
        for (Iterator<Socket> it = myServer.listClient.iterator(); it.hasNext();) {
            Socket client = it.next();
            try {
                writeToStream = new DataOutputStream(client.getOutputStream());
                writeToStream.writeUTF("Server: " + messg);
            } catch (IOException ex) {
                it.remove();
                System.out.println(client + " đã ngắt kết nối !");
//                    Logger.getLogger(writeTo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
