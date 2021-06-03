package CSDL_bean;

public class User {

    int ID;
    String tenDangNhap;
    String matKhau;
    String maXacThuc;
    String email;

    public static final String tenBang = "USER";
    public static final String cotId = "ID";
    public static final String cotTenDangNhap = "tenDangNhap";
    public static final String cotMaXacThuc = "maXacThuc";
    public static final String cotEmail = "email";
    public static final String cotMatKhau = "matKhau";

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTenDangNhap() {
        return tenDangNhap;
    }

    public void setTenDangNhap(String tenDangNhap) {
        this.tenDangNhap = tenDangNhap;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getMaXacThuc() {
        return maXacThuc;
    }

    public void setMaXacThuc(String maXacThuc) {
        this.maXacThuc = maXacThuc;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User(int ID, String tenDangNhap, String matKhau, String maXacThuc, String email) {
        this.ID = ID;
        this.tenDangNhap = tenDangNhap;
        this.matKhau = matKhau;
        this.maXacThuc = maXacThuc;
        this.email = email;

    }

    public User(String tenDangNhap, String matKhau, String email) {
        this.tenDangNhap = tenDangNhap;
        this.matKhau = matKhau;
        this.maXacThuc = "";
        this.email = email;
    }
}
