package CSDL_bean;

public class BangXepHang {
    public static final String tenBang = "BANGXEPHANG";
    public static final String cotNgay = "ngay";
    public static final String cotDiem = "diem";

    String ngay;
    int diem;

    public BangXepHang(String ngay, int diem) {
        this.ngay = ngay;
        this.diem = diem;
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
}
