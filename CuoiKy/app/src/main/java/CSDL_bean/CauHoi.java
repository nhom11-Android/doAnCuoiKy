package CSDL_bean;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Objects;

public class CauHoi {
    int id;
    String noiDung;
    String[] dapAn = new String[4];
    String dapAnDung;
    String chuyenNganh;
    int doKho;

    public static final String tenBang = "CAUHOI";
    public static final String cotNoiDung = "noiDung";
    public static final String cotDapAnA = "A";
    public static final String cotDapAnB = "B";
    public static final String cotDapAnC = "C";
    public static final String cotDapAnD = "D";
    public static final String cotDapAnDung = "dapAnDung";
    public static final String cotChuyenNganh = "chuyenNganh";
    public static final String cotDoKho = "doKho";
    public static final String cotId = "ID";

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

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (!(o instanceof CauHoi)) return false;
//        CauHoi cauHoi = (CauHoi) o;
//        return getId() == cauHoi.getId();
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(getId());
//    }
}
