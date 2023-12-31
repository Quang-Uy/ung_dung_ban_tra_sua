package fpoly.edu.ungdungbantrasua.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    public DbHelper(Context context) {
        super(context, "Duan1", null, 23);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Tạo bảng Admin
//        String tAdmin = "CREATE TABLE Admin (" +
//                "maAdmin    TEXT PRIMARY KEY," +
//                "hoTen   TEXT NOT NULL," +
//                "matKhau TEXT NOT NULL" +
//                ");";
//        db.execSQL(tAdmin);
//        //Dữ liệu mẫu
//        db.execSQL("INSERT INTO Admin VALUES('Admin', 'buiquanguy', '123')");

        //Tạo bảng Admin
        String tAdmin = "CREATE TABLE Admin (" +
                "maAdmin    TEXT PRIMARY KEY," +
                "hoTen   TEXT NOT NULL," +
                "matKhau TEXT NOT NULL," +
                "role INTEGER NOT NULL" +
                ");";
        db.execSQL(tAdmin);
//        //Dữ liệu mẫu
        db.execSQL("INSERT INTO Admin VALUES('admin','Quản trị viên', '1234', 0)," +
                "('quanguy', 'Quang Uy Bùi','1234', 1)");

        //Tạo bảng nhân viên
        String tNhanVien = "CREATE TABLE NhanVien (" +
                "maNV    INTEGER PRIMARY KEY AUTOINCREMENT," +
                "hoTen   TEXT NOT NULL," +
                "namSinh   TEXT NOT NULL," +
                "matKhau TEXT NOT NULL" +
                ");";
        db.execSQL(tNhanVien);
        //Dữ liệu mẫu
        db.execSQL("INSERT INTO NhanVien VALUES(1, 'Nguyễn Văn A','2004', '456')," +
                "(2, 'Phạm Anh B', '2004','456')," +
                "(3, 'Đinh Thị C', '2004','456')");

        //Tạo bảng khách hàng
        //*********
        String tKhachHang = "CREATE TABLE KhachHang (" +
                "maKH    TEXT PRIMARY KEY," +
                "tenDangNhap   TEXT NOT NULL," +
                "hoTen   TEXT NOT NULL," +
                "matKhau TEXT NOT NULL" +
                ");";
        db.execSQL(tKhachHang);
        //Dữ liệu mẫu
        db.execSQL("INSERT INTO KhachHang VALUES(1, 'uy', 'Bùi Quang Uy', '000')," +
                "(2, 'quan', 'Phạm Anh Quân', '000')," +
                "(3, 'nam', 'Lê Đức Nam', '000')");

        //Tạo bảng trà sữa
        String tTraSua = "CREATE TABLE TraSua(" +
                "    maTraSua Integer PRIMARY KEY AUTOINCREMENT," +
                "    tenTraSua text NOT NULL," +
                "    maLoai Integer REFERENCES LoaiTraSua(maLoai)," +
                "    soLuongKho Integer NOT NULL," +
                "    gia Integer NOT NULL" +
                ");";
        db.execSQL(tTraSua);
        //Dữ liệu mẫu
        db.execSQL("INSERT INTO TraSua VALUES(1, 'Trà sữa trân châu đường đen', 1, '6', '25000')," +
                "(2, 'Trà sữa Oolong', 2, '9', '35000')," +
                "(3, 'Trà sữa Nhadam', 3, '12', '30000')," +
                "(4, 'Trà sữa Trứng muối', 2, '10', '35000')," +
                "(5, 'Trà sữa Matcha', 3, '16', '30000')," +
                "(6, 'Trà sữa Kem', 1, '20', '25000')," +
                "(7, 'Trà sữa Socola', 2, '13', '35000')," +
                "(8, 'Trà sữa dâu tây', 3, '240', '30000')");

        //Tạo bảng loại trà sữa
        String tLoai = "CREATE TABLE LoaiTraSua(" +
                "    maLoai Integer PRIMARY KEY AUTOINCREMENT," +
                "    tenLoai TEXT NOT NULL" +
                ");";
        db.execSQL(tLoai);
        //Dữ liệu mẫu
        db.execSQL("INSERT INTO LoaiTraSua VALUES(1, 'Trà sữa truyền thống')," +
                "(2, 'Trà sữa đăc biệt')," +
                "(3, 'Trà sữa hoa quả')");

        //Tạo bảng đơn hàng
        String tDonHang = "CREATE TABLE DonHang (" +
                "    maDonHang    INTEGER PRIMARY KEY AUTOINCREMENT," +
                "    maKH    INTEGER    REFERENCES KhachHang (maKH)," +
                "    maTraSua  INTEGER REFERENCES TraSua (maTraSua)," +
                "    ngay    DATE    NOT NULL," +
                "    soLuong Integer NOT NULL," +
                "    gia INTEGER NOT NULL," +
                "    trangthai INTEGER NOT NULL" +
                ");";
        db.execSQL(tDonHang);
        //Dữ liệu mẫu
        db.execSQL("INSERT INTO DonHang VALUES(1, 1, 1, '15/11/2023', '2', '50000', 1)," +
                "(2, 2, 2, '10/11/2023', '1', '35000', 2)," +
                "(3, 3, 3, '05/11/2023', '3', '90000', 1)");

        //Tạo bảng giỏ hàng
        String tb_cart = "CREATE TABLE GioHang ( " +
                "maGioHang INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "maTraSua INTEGER REFERENCES TraSua(maTraSua), " +
                "role INTEGER REFERENCES Admin(role), " +
                "giaGioHang TEXT)";
        db.execSQL(tb_cart);
        String insert_Cart = "INSERT INTO GioHang VALUES " +
                "(0, 2, 1, 1), " +
                "(1, 3, 1, 1), " +
                "(2, 1, 1, 1) ";
        db.execSQL(insert_Cart);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS Admin");
            db.execSQL("DROP TABLE IF EXISTS NhanVien");
            db.execSQL("DROP TABLE IF EXISTS KhachHang");
            db.execSQL("DROP TABLE IF EXISTS TraSua");
            db.execSQL("DROP TABLE IF EXISTS LoaiTraSua");
            db.execSQL("DROP TABLE IF EXISTS DonHang");

            db.execSQL("DROP TABLE IF EXISTS GioHang");
            onCreate(db);
        }
    }
}
