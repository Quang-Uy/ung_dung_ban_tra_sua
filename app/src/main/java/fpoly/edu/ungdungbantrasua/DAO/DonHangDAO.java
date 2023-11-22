package fpoly.edu.ungdungbantrasua.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import fpoly.edu.ungdungbantrasua.DTO.DonHang;
import fpoly.edu.ungdungbantrasua.DTO.SanPham;
import fpoly.edu.ungdungbantrasua.DTO.Top;
import fpoly.edu.ungdungbantrasua.Database.DbHelper;

public class DonHangDAO {
    private SQLiteDatabase db;
    private Context context;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public DonHangDAO(Context context) {
        this.context = context;
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insert(DonHang obj) {
        ContentValues values = new ContentValues();
//        values.put("maDonHang", obj.getMaDonHang());
        values.put("maKH", obj.getMaKH());
        values.put("maTraSua", obj.getMaTraSua());
        values.put("ngay", sdf.format(obj.getNgay()));
        values.put("soLuong", obj.getSoLuong());
        values.put("gia", obj.getGia());
        values.put("trangthai", obj.getTrangThai());
        return db.insert("DonHang", null, values);
    }

    public int update(DonHang obj) {
        ContentValues values = new ContentValues();
//        values.put("maDonHang", obj.getMaDonHang());
        values.put("maKH", obj.getMaKH());
        values.put("maTraSua", obj.getMaTraSua());
        values.put("ngay", sdf.format(obj.getNgay()));
        values.put("soLuong", obj.getSoLuong());
        values.put("gia", obj.getGia());
        values.put("trangthai", obj.getTrangThai());
        return db.update("DonHang", values, "maDonHang=?", new String[]{String.valueOf(obj.getMaDonHang())});
    }

    public int delete(String id) {
        return db.delete("DonHang", "maDonHang=?", new String[]{id});
    }

    @SuppressLint("Range")
    public List<DonHang> getData(String sql, String...selectionArgs) {

        List<DonHang> list = new ArrayList<>();
        Cursor c = db.rawQuery(sql, selectionArgs);
        while (c.moveToNext()) {
            DonHang obj = new DonHang();
            obj.setMaDonHang(Integer.parseInt(c.getString(c.getColumnIndex("maDonHang"))));
            obj.setMaKH(Integer.parseInt(c.getString(c.getColumnIndex("maKH"))));
            obj.setMaTraSua(Integer.parseInt(c.getString(c.getColumnIndex("maTraSua"))));
            try {
                obj.setNgay(sdf.parse(c.getString(c.getColumnIndex("ngay"))));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            obj.setSoLuong(Integer.parseInt(c.getString(c.getColumnIndex("soLuong"))));
            obj.setGia(Integer.parseInt(c.getString(c.getColumnIndex("gia"))));
            obj.setTrangThai(Integer.parseInt(c.getString(c.getColumnIndex("trangthai"))));
            list.add(obj);
        }
        return list;
    }

    //Get tất cả data
    public List<DonHang> getAll() {
        String sql = "SELECT * FROM DonHang";
        return getData(sql);
    }

    //Get data theo id
    public DonHang getID(String id) {
        String sql = "SELECT *FROM DonHang WHERE maDonHang=?";
        List<DonHang> list = getData(sql, id);
        return list.get(0);
    }

    //Thống kê top 10
    @SuppressLint("Range")
    public List<Top> getTop() {
        String sqlTop = "SELECT maTraSua, count(maTraSua) as soLuong FROM DonHang GROUP BY maTraSua ORDER BY soLuong DESC LIMIT 10";
        List<Top> list = new ArrayList<Top>();
        SanPhamDAO sanPhamDAO = new SanPhamDAO(context);
        Cursor c = db.rawQuery(sqlTop, null);

        while (c.moveToNext()) {
            Top top = new Top();
            @SuppressLint("Range") SanPham sanPham = sanPhamDAO.getID(c.getString(c.getColumnIndex("maTraSua")));
            top.setTenTraSua(sanPham.getTenTraSua());
            top.setSoLuong(Integer.parseInt(c.getString(c.getColumnIndex("soLuongKho"))));
            list.add(top);
        }

        return list;
    }

    //Thống kê doanh thu
    @SuppressLint("Range")
    public int getDoanhThu(String tuNgay, String denNgay) {
        String sqlDoanhThu = "SELECT SUM(gia) as doanhThu FROM DonHang WHERE ngay BETWEEN ? AND ?";
        List<Integer> list = new ArrayList<Integer>();
        Cursor c = db.rawQuery(sqlDoanhThu, new String[]{tuNgay, denNgay});

        while (c.moveToNext()) {
            try {
                list.add(Integer.parseInt(c.getString(c.getColumnIndex("doanhThu"))));
            } catch (Exception e) {
                list.add(0);
            }
        }
        return list.get(0);
    }
}
