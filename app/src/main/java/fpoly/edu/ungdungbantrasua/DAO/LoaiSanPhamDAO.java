package fpoly.edu.ungdungbantrasua.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import fpoly.edu.ungdungbantrasua.DTO.LoaiSanPham;
import fpoly.edu.ungdungbantrasua.Database.DbHelper;
import fpoly.edu.ungdungbantrasua.LoginActivity;

public class LoaiSanPhamDAO {
    private SQLiteDatabase db;
    private DbHelper dbHelper;

    public LoaiSanPhamDAO(Context context) {
        dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insert(LoaiSanPham obj) {
        ContentValues values = new ContentValues();
        values.put("tenLoai", obj.getTenLoai());
        return db.insert("LoaiTraSua", null, values);
    }

    public int update(LoaiSanPham obj) {
        ContentValues values = new ContentValues();
        values.put("tenLoai", obj.getTenLoai());
        return db.update("LoaiTraSua", values, "maLoai=?", new String[]{String.valueOf(obj.getMaLoai())});
    }


    public int delete(String id) {
        return db.delete("LoaiTraSua", "maLoai=?", new String[]{id});
    }

    @SuppressLint("Range")
    public List<LoaiSanPham> getData(String sql, String...selectionArgs) {
        List<LoaiSanPham> list = new ArrayList<LoaiSanPham>();
        Cursor c = db.rawQuery(sql, selectionArgs);
        while (c.moveToNext()) {
            LoaiSanPham obj = new LoaiSanPham();
            obj.setMaLoai(Integer.parseInt(c.getString(c.getColumnIndex("maLoai"))));
            obj.setTenLoai(c.getString(c.getColumnIndex("tenLoai")));
            list.add(obj);
        }
        return list;
    }

    //Get tất cả data
    public List<LoaiSanPham> getAll() {
        String sql = "SELECT * FROM LoaiTraSua";
        return getData(sql);
    }

    //Get data theo id
    public LoaiSanPham getID(String id) {
        String sql = "SELECT *FROM LoaiTraSua WHERE maLoai=?";
        List<LoaiSanPham> list = getData(sql, id);
        return list.get(0);
    }
}
