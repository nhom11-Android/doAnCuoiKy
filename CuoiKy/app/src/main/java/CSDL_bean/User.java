package CSDL_bean;

public class User {


    int ID;
    String tenDangNhap;
    String matKhau;
    String email;
    String urlImage;

    public static final String tenBang = "USER";
    public static final String cotId = "ID";
    public static final String cotTenDangNhap = "tenDangNhap";
    public static final String cotEmail = "email";
    public static final String cotMatKhau = "matKhau";
    public static String cotUrlImage = "url";

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public User(int ID, String tenDangNhap, String matKhau, String maXacThuc, String email) {
        this.ID = ID;
        this.tenDangNhap = tenDangNhap;
        this.matKhau = matKhau;
        this.email = email;

    }

    public User(String tenDangNhap, String matKhau, String email, String urlImage) {
        this.tenDangNhap = tenDangNhap;
        this.matKhau = matKhau;
        this.email = email;
        this.urlImage = urlImage;
    }
}
