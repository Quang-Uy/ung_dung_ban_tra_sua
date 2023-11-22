package fpoly.edu.ungdungbantrasua.DTO;

import java.util.Date;

public class DonHang {
    private int maDonHang;
    private int maKH;
    private int maTraSua;
    private Date ngay;
    private int soLuong;
    private int gia;
    private int trangThai;

    public DonHang() {
    }

    public DonHang(int maDonHang, int maKH, int maTraSua, Date ngay, int soLuong, int gia, int trangThai) {
        this.maDonHang = maDonHang;
        this.maKH = maKH;
        this.maTraSua = maTraSua;
        this.ngay = ngay;
        this.soLuong = soLuong;
        this.gia = gia;
        this.trangThai = trangThai;
    }

    public int getMaDonHang() {
        return maDonHang;
    }

    public void setMaDonHang(int maDonHang) {
        this.maDonHang = maDonHang;
    }

    public int getMaKH() {
        return maKH;
    }

    public void setMaKH(int maKH) {
        this.maKH = maKH;
    }

    public int getMaTraSua() {
        return maTraSua;
    }

    public void setMaTraSua(int maTraSua) {
        this.maTraSua = maTraSua;
    }

    public Date getNgay() {
        return ngay;
    }

    public void setNgay(Date ngay) {
        this.ngay = ngay;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getGia() {
        return gia;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }
}
