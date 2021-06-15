/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Linh
 */
public class test {
    public static CauHoi getCauHoi(Scanner sc){
        String noiDung;
        String[] dapAn = new String[4];
        String dapAnDung;
        String chuyenNganh;
        int doKho;
        String tmp = "";
        tmp = sc.nextLine();
        if(tmp.equalsIgnoreCase("start")){
            tmp = sc.nextLine();
        }
        noiDung = tmp;
        tmp = sc.nextLine();
        tmp = sc.nextLine();
        dapAn[0] = tmp;
        tmp = sc.nextLine();
        tmp = sc.nextLine();
        dapAn[1] = tmp;
        tmp = sc.nextLine();
        tmp = sc.nextLine();
        dapAn[2] = tmp;
        tmp = sc.nextLine();
        tmp = sc.nextLine();
        dapAn[3] = tmp;
        tmp = sc.nextLine();
        tmp = sc.nextLine();
        dapAnDung = tmp.substring(13, 14);
        tmp = sc.nextLine();
        tmp = sc.nextLine();
        chuyenNganh = tmp.substring(12);
        tmp = sc.nextLine();
        tmp = sc.nextLine();
        doKho = Integer.parseInt(tmp.substring(8));
        System.out.println(noiDung + "\n" + dapAn[0]
            + "\n" + dapAn[1]
            + "\n" + dapAn[2]
            + "\n" + dapAn[3]
            + "\n" + dapAnDung
            + "\n" + chuyenNganh
            + "\n" + doKho);
        CauHoi cauHoi = new CauHoi(noiDung, dapAn, dapAnDung, chuyenNganh, doKho);
        return cauHoi;
    }
    
    public static void main(String[] args) throws FileNotFoundException {
        String fileName = "cauhoi_ALTP.txt";  // tên file hoặc đường dẫn
        File file = new File(fileName);
        if(!file.exists()){
            System.out.println("Không thể mở file");
            System.exit(-1);
        }
        Scanner sc = new Scanner(file);
        ArrayList<CauHoi> danhSach = new ArrayList<>();
        CauHoi cauHoi;
        while(sc.hasNextLine()){
            cauHoi = getCauHoi(sc);
            danhSach.add(cauHoi);
        }
        System.out.println(danhSach.size());
        System.exit(0);
    }
}
