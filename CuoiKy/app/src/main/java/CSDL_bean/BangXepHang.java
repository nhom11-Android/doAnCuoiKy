package CSDL_bean;

import java.util.Comparator;

public class BangXepHang implements Comparable<BangXepHang> {
    public static final String tenBang = "BANGXEPHANG";
    public static final String cotNgay = "ngay";
    public static final String cotDiem = "diem";
    public static final String cotUser = "user";

    String ngay;
    int diem;
    String user;

    public BangXepHang(String ngay, int diem, String tenDangNhap) {
        this.ngay = ngay;
        this.diem = diem;
        this.user = tenDangNhap;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public int getDiem() {
        return diem;
    }

    public void setDiem(int diem) {
        this.diem = diem;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    /**
     * @param o
     * @return
     */
    @Override
    public int compareTo(BangXepHang o) {
        if(this.getDiem()<o.getDiem()) return 1;
        else return -1;
    }
}
