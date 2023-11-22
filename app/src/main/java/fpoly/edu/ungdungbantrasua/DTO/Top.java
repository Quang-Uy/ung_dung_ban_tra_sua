package fpoly.edu.ungdungbantrasua.DTO;

public class Top {
    public String tenTraSua;
    public int soLuong;

    public Top() {
    }

    public Top(String tenTraSua, int soLuong) {
        this.tenTraSua = tenTraSua;
        this.soLuong = soLuong;
    }

    public String getTenTraSua() {
        return tenTraSua;
    }

    public void setTenTraSua(String tenTraSua) {
        this.tenTraSua = tenTraSua;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }
}
