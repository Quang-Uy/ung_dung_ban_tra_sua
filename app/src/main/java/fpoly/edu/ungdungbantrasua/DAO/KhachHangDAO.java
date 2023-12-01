package fpoly.edu.ungdungbantrasua.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import fpoly.edu.ungdungbantrasua.DTO.KhachHang;
import fpoly.edu.ungdungbantrasua.Database.DbHelper;

public class KhachHangDAO {
    private SQLiteDatabase db;

    public KhachHangDAO(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public boolean insert(KhachHang obj) {
        ContentValues values = new ContentValues();
        values.put("maKH", obj.getMaKH());
        values.put("tenDangNhap", obj.getTenDangNhap());
        values.put("hoTen", obj.getHoTen());
        values.put("matKhau", obj.getMatKhau());
        long check = db.insert("KhachHang", null, values);
        return check != 1;
    }

    public int updatePass(KhachHang obj) {
        ContentValues values = new ContentValues();
        values.put("tenDangNhap", obj.getHoTen());
        values.put("matKhau", obj.getMatKhau());
        return db.update("KhachHang", values, "tenDangNhap=?", new String[]{obj.getTenDangNhap()});
    }

    public int delete(String id){
        return db.delete("KhachHang", "tenDangNhap=?", new String[]{id});
    }

    @SuppressLint("Range")
    public List<KhachHang> getData(String sql, String...selectionArgs) {
        List<KhachHang> list = new ArrayList<>();
        Cursor c = db.rawQuery(sql, selectionArgs);
        while (c.moveToNext()) {
            KhachHang obj = new KhachHang();
            //*******
            obj.setMaKH(Integer.parseInt(c.getString(c.getColumnIndex("maKH"))));
            obj.setTenDangNhap(c.getString(c.getColumnIndex("tenDangNhap")));
            obj.setHoTen(c.getString(c.getColumnIndex("hoTen")));
            obj.setMatKhau(c.getString(c.getColumnIndex("matKhau")));
            list.add(obj);
        }
        return list;
    }

    //Get tất cả dữ liệu
    public List<KhachHang> getAll() {
        String sql = "SELECT * FROM KhachHang";
        return getData(sql);
    }

    //Get dữ liệu theo id
    public KhachHang getID(String id) {
        String sql = "SELECT * FROM KhachHang WHERE maKH=?";
        List<KhachHang> list = getData(sql, id);
        return list.get(0);
    }

    //Check login
    public int checkLogin(String id, String password) {
        String sql = "SELECT * FROM KhachHang WHERE tenDangNhap=? AND matKhau=?";
        List<KhachHang> list = getData(sql, id, password);
        if (list.size() == 0) {
            return -1;
        }
        return 1;
    }

    //Sign up
    public boolean Register(String tenDangNhap, String hoTen, String matKhau, String s) {

        ContentValues contentValues = new ContentValues();
        contentValues.put("tenDangNhap", tenDangNhap);
        contentValues.put("hoTen", hoTen);
        contentValues.put("matKhau", matKhau);

        long check = db.insert("KhachHang", null, contentValues);
        if (check != -1) {
            return true;
        } else {
            return false;
        }

    }
}
