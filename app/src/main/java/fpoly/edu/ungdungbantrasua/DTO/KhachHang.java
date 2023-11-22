package fpoly.edu.ungdungbantrasua.DTO;

public class KhachHang {
    private int maKH;
    String tenDangNhap;
    String hoTen;
    String matKhau;

    public KhachHang() {
    }

    public KhachHang(int maKH, String tenDangNhap, String hoTen, String matKhau) {
        this.maKH = maKH;
        this.tenDangNhap = tenDangNhap;
        this.hoTen = hoTen;
        this.matKhau = matKhau;
    }

    public int getMaKH() {
        return maKH;
    }

    public void setMaKH(int maKH) {
        this.maKH = maKH;
    }

    public String getTenDangNhap() {
        return tenDangNhap;
    }

    public void setTenDangNhap(String tenDangNhap) {
        this.tenDangNhap = tenDangNhap;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }
}
