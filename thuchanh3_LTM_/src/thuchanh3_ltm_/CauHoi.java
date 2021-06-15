/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thuchanh3_ltm_;

/**
 *
 * @author thanh
 */
public class CauHoi {
    int id;
    String noiDung;
    String[] dapAn = new String[4];
    String dapAnDung;
    String chuyenNganh;
    int doKho;
    public CauHoi(String noiDung, String[] dapAn, String dapAnDung, String chuyenNganh, int doKho) {
        this.noiDung = noiDung;
        this.dapAn = dapAn;
        this.dapAnDung = dapAnDung;
        this.chuyenNganh = chuyenNganh;
        this.doKho = doKho;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public String[] getDapAn() {
        return dapAn;
    }

    public void setDapAn(String[] dapAn) {
        this.dapAn = dapAn;
    }

    public String getDapAnDung() {
        return dapAnDung;
    }

    public void setDapAnDung(String dapAnDung) {
        this.dapAnDung = dapAnDung;
    }

    public String getChuyenNganh() {
        return chuyenNganh;
    }

    public void setChuyenNganh(String chuyenNganh) {
        this.chuyenNganh = chuyenNganh;
    }

    public int getDoKho() {
        return doKho;
    }

    public void setDoKho(int doKho) {
        this.doKho = doKho;
    }
    
}
