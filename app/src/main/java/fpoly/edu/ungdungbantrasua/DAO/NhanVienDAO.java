package fpoly.edu.ungdungbantrasua.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import fpoly.edu.ungdungbantrasua.DTO.Admin;
import fpoly.edu.ungdungbantrasua.DTO.NhanVien;
import fpoly.edu.ungdungbantrasua.Database.DbHelper;

public class NhanVienDAO {
    private SQLiteDatabase db;

    public NhanVienDAO(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insert(NhanVien obj) {
        ContentValues values = new ContentValues();
        values.put("hoTen", obj.getHoTen());
        values.put("namSinh", obj.getNamSinh());
        values.put("matKhau", obj.getMatKhau());
        return db.insert("NhanVien", null, values);
    }

    public int update(NhanVien obj) {
        ContentValues values = new ContentValues();
        values.put("hoTen", obj.getHoTen());
        values.put("namSinh", obj.getNamSinh());
        values.put("matKhau", obj.getMatKhau());
        return db.update("NhanVien", values, "maNV=?", new String[]{String.valueOf(obj.getMaNhanVien())});
    }

    public int delete(String id){
        return db.delete("NhanVien", "maNV=?", new String[]{id});
    }

    @SuppressLint("Range")
    public List<NhanVien> getData(String sql, String...selectionArgs) {
        List<NhanVien> list = new ArrayList<>();
        Cursor c = db.rawQuery(sql, selectionArgs);
        while (c.moveToNext()) {
            NhanVien obj = new NhanVien();
            obj.setMaNhanVien(Integer.parseInt(c.getString(c.getColumnIndex("maNV"))));
            obj.setHoTen(c.getString(c.getColumnIndex("hoTen")));
            obj.setNamSinh(c.getString(c.getColumnIndex("namSinh")));
            obj.setMatKhau(c.getString(c.getColumnIndex("matKhau")));

            list.add(obj);
        }
        return list;
    }

    //Get tất cả dữ liệu
    public List<NhanVien> getAll() {
        String sql = "SELECT * FROM NhanVien";
        return getData(sql);
    }

    //Get dữ liệu theo id
    public NhanVien getID(String id) {
        String sql = "SELECT * FROM NhanVien WHERE maNV=?";
        List<NhanVien> list = getData(sql, id);
        return list.get(0);
    }

    public int checkLogin(String id, String password) {
        String sql = "SELECT * FROM NhanVien WHERE tenDangNhap=? AND matKhau=?";
        List<NhanVien> list = getData(sql, id, password);
        if (list.size() == 0) {
            return -1;
        }
        return 1;
    }
}

