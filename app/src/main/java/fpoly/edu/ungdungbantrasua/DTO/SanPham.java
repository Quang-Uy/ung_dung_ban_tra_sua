package fpoly.edu.ungdungbantrasua.DTO;

public class SanPham {
    private int maTraSua;
    private String tenTraSua;
    private int maLoai;
    private int soLuongKho;
    private int Gia;

    public SanPham() {
    }

    public SanPham(int maTraSua, String tenTraSua, int maLoai, int soLuongKho, int gia) {
        this.maTraSua = maTraSua;
        this.tenTraSua = tenTraSua;
        this.maLoai = maLoai;
        this.soLuongKho = soLuongKho;
        Gia = gia;
    }

    public int getMaTraSua() {
        return maTraSua;
    }

    public void setMaTraSua(int maTraSua) {
        this.maTraSua = maTraSua;
    }

    public String getTenTraSua() {
        return tenTraSua;
    }

    public void setTenTraSua(String tenTraSua) {
        this.tenTraSua = tenTraSua;
    }

    public int getMaLoai() {
        return maLoai;
    }

    public void setMaLoai(int maLoai) {
        this.maLoai = maLoai;
    }

    public int getSoLuongKho() {
        return soLuongKho;
    }

    public void setSoLuongKho(int soLuongKho) {
        this.soLuongKho = soLuongKho;
    }

    public int getGia() {
        return Gia;
    }

    public void setGia(int gia) {
        Gia = gia;
    }
}
