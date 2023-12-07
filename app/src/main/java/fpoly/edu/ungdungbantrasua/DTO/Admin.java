package fpoly.edu.ungdungbantrasua.DTO;

public class Admin {
    String maAdmin;
    String hoTen;
    String matKhau;
    private int role;

    public Admin() {
    }

    public Admin(String maAdmin, String hoTen, String matKhau,int role) {
        this.maAdmin = maAdmin;
        this.hoTen = hoTen;
        this.matKhau = matKhau;
        this.role = role;
    }

    public String getMaAdmin() {
        return maAdmin;
    }

    public void setMaAdmin(String maAdmin) {
        this.maAdmin = maAdmin;
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

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
}
