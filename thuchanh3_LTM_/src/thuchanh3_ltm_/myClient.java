/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thuchanh3_ltm_;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author thanh
 */
public class myClient {

    private InetAddress host;
    private int port;
    String id;

    public myClient(InetAddress host, int port, String id) {
        this.host = host;
        this.port = port;
        this.id = id;
    }

    public void run() throws IOException {
        Socket client = new Socket(host, port);
        ReadClient read = new ReadClient(client);
        read.start();
        WriteClient write = new WriteClient(client, id);
        write.start();
    }
}

class ReadClient extends Thread {

    private Socket client;

    public ReadClient(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {
        DataInputStream readFrom = null;
        try {
            readFrom = new DataInputStream(client.getInputStream());
            while (true) {
                String messg = readFrom.readUTF(); // đọc từ server
                System.out.println(messg);
            }
        } catch (Exception e) {
            try {
                readFrom.close();
                client.close();
            } catch (IOException ex) {
                System.out.println("Ngat ket noi");
            }
        }
    }
}

class WriteClient extends Thread {

    private Socket client;
    private String id;

    public WriteClient(Socket client, String id) {
        this.client = client;
        this.id = id;
    }

    @Override
    public void run() {
        DataOutputStream writeTo = null;
        Scanner sc = null;
        try {
            writeTo = new DataOutputStream(client.getOutputStream());
            writeTo.writeUTF("Injoin");
            writeTo.writeUTF("Fake player");
            sc = new Scanner(System.in);
            boolean logout = false;
            while (!logout) {
                String messg = sc.nextLine();
                if (messg.equals("logout")) {
                    System.out.println("Ngắt kết nối đến server");
                    writeTo.close();
                    this.client.close();
                }
                writeTo.writeUTF(id + " : " + messg);
            }
        } catch (IOException e) {
            try {
                writeTo.close();
                client.close();
            } catch (IOException ex) {
                System.out.println("Ngắt kết nối đến server");
            }
        }
    }
}
