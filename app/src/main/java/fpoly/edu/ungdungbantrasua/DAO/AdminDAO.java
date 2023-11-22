package fpoly.edu.ungdungbantrasua.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import fpoly.edu.ungdungbantrasua.DTO.Admin;
import fpoly.edu.ungdungbantrasua.Database.DbHelper;

public class AdminDAO {
    private SQLiteDatabase db;

    public AdminDAO(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public boolean insert(Admin obj) {
        ContentValues values = new ContentValues();
        values.put("maAdmin", obj.getMaAdmin());
        values.put("haTen", obj.getHoTen());
        values.put("matKhau", obj.getMatKhau());
        long check = db.insert("Admin", null, values);
        return check != 1;
    }

    public int updatePass(Admin obj) {
        ContentValues values = new ContentValues();
        values.put("hoTen", obj.getHoTen());
        values.put("matKhau", obj.getMatKhau());
        return db.update("Admin", values, "maAdmin=?", new String[]{obj.getMaAdmin()});
    }

    public int delete(String id){
        return db.delete("Admin", "maAdmin=?", new String[]{id});
    }

    @SuppressLint("Range")
    public List<Admin> getData(String sql, String...selectionArgs) {
        List<Admin> list = new ArrayList<Admin>();
        Cursor c = db.rawQuery(sql, selectionArgs);
        while (c.moveToNext()) {
            Admin obj = new Admin();
            //*******
            obj.setMaAdmin(c.getString(c.getColumnIndex("maAdmin")));
            obj.setHoTen(c.getString(c.getColumnIndex("hoTen")));
            obj.setMatKhau(c.getString(c.getColumnIndex("matKhau")));
            list.add(obj);
        }
        return list;
    }

    //Get tất cả dữ liệu
    public List<Admin> getAll() {
        String sql = "SELECT * FROM Admin";
        return getData(sql);
    }

    //Get dữ liệu theo id
    public Admin getID(String id) {
        String sql = "SELECT * FROM Admin WHERE maAdmin=?";
        List<Admin> list = getData(sql, id);
        return list.get(0);
    }

    //Check login
    public int checkLogin(String id, String password) {
        String sql = "SELECT * FROM Admin WHERE maAdmin=? AND matKhau=?";
        List<Admin> list = getData(sql, id, password);
        if (list.size() == 0) {
            return -1;
        }
        return 1;
    }
}
