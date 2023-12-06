package fpoly.edu.ungdungbantrasua.DTO;

public class GioHang {
    private int maGioHang, role, giaGioHang, gia, maTraSua ;
    private String TenSanPham;
    private boolean isChecked;

    public GioHang() {
    }

    public GioHang(int maGioHang, int maTraSua, String tenSanPham, int gia, int giaGioHang) {
        this.maGioHang = maGioHang;
        this.giaGioHang = giaGioHang;
        this.gia = gia;
        this.maTraSua = maTraSua;
        this.TenSanPham = tenSanPham;
    }

    public int getMaGioHang() {
        return maGioHang;
    }

    public void setMaGioHang(int maGioHang) {
        this.maGioHang = maGioHang;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public int getGiaGioHang() {
        return giaGioHang;
    }

    public void setGiaGioHang(int giaGioHang) {
        this.giaGioHang = giaGioHang;
    }

    public int getGia() {
        return gia;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }

    public int getMaTraSua() {
        return maTraSua;
    }

    public void setMaTraSua(int maTraSua) {
        this.maTraSua = maTraSua;
    }

    public String getTenSanPham() {
        return TenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        TenSanPham = tenSanPham;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
