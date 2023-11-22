package fpoly.edu.ungdungbantrasua.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import fpoly.edu.ungdungbantrasua.DTO.SanPham;
import fpoly.edu.ungdungbantrasua.Database.DbHelper;

public class SanPhamDAO {

    private SQLiteDatabase db;

    public SanPhamDAO(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insert(SanPham obj) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("tenTraSua", obj.getTenTraSua());
        contentValues.put("maLoai", obj.getMaLoai());
        contentValues.put("soLuongKho", obj.getSoLuongKho());
        contentValues.put("gia", obj.getGia());
        return db.insert("TraSua", null, contentValues);
    }

    public int update(SanPham obj) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("tenTraSua", obj.getTenTraSua());
        contentValues.put("maLoai", obj.getMaLoai());
        contentValues.put("soLuongKho", obj.getSoLuongKho());
        contentValues.put("gia", obj.getGia());
        return db.update("TraSua", contentValues, "maTraSua=?", new String[]{String.valueOf(obj.getMaTraSua())});
    }

    public int delete(String id) {
        return db.delete("TraSua", "maTraSua=?", new String[]{id});
    }

    //Get data
    @SuppressLint("Range")
    public List<SanPham> getData(String sql, String...selectionArgs) {
        List<SanPham> list = new ArrayList<SanPham>();
        Cursor c = db.rawQuery(sql, selectionArgs);
        while (c.moveToNext()) {
            SanPham obj = new SanPham();
            obj.setMaTraSua(Integer.parseInt(c.getString(c.getColumnIndex("maTraSua"))));
            obj.setTenTraSua(c.getString(c.getColumnIndex("tenTraSua")));
            obj.setMaLoai(Integer.parseInt(c.getString(c.getColumnIndex("maLoai"))));
            obj.setSoLuongKho(Integer.parseInt(c.getString(c.getColumnIndex("soLuongKho"))));
            obj.setGia(Integer.parseInt(c.getString(c.getColumnIndex("gia"))));
            list.add(obj);
        }
        return list;
    }

    //Get tất cả data
    public List<SanPham> getAll() {
        String sql = "SELECT * FROM TraSua";
        return getData(sql);
    }

    //Get data theo id
    public SanPham getID(String id) {
        String sql = "SELECT * FROM TraSua WHERE maTraSua=?";
        List<SanPham> list = getData(sql, id);
        return list.get(0);
    }
}
