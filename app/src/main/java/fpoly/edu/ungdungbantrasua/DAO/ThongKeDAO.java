package fpoly.edu.ungdungbantrasua.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import fpoly.edu.ungdungbantrasua.DTO.SanPham;
import fpoly.edu.ungdungbantrasua.DTO.Top;
import fpoly.edu.ungdungbantrasua.Database.DbHelper;

public class ThongKeDAO {
    DbHelper dbHelper;
    Context context;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public ThongKeDAO(Context context) {
        this.context = context;
        dbHelper = new DbHelper(context);
    }

    //Thống kê  top 10
    public ArrayList<Top> getTop() {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        String sql = "SELECT maTraSua ,Count(maTraSua) as soLuong FROM DonHang GROUP BY maTraSua ORDER BY soLuong DESC LIMIT 10";
        ArrayList<Top> list = new ArrayList<>();
        SanPhamDAO sanPhamDAO = new SanPhamDAO(context);
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                Top top = new Top();
                SanPham sanPham = sanPhamDAO.getID(cursor.getString(cursor.getColumnIndexOrThrow("maTraSua")));
                top.tenTraSua = sanPham.getTenTraSua();
                top.soLuong = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow("soLuong")));
                list.add(top);
            } while (cursor.moveToNext());

        }
        return list;
    }

    //Thống kê doanh thu
    public int DoanhThu(String tuNgay, String denNgay) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        String sql = "SELECT SUM(gia) as doanhThu FROM DonHang WHERE ngay BETWEEN ? AND ?";
        String dk[] = {tuNgay, denNgay};
        int doanhThu = 0;
        Cursor cursor = sqLiteDatabase.rawQuery(sql, dk);
        if (cursor.moveToFirst()) {
            try {
                doanhThu = cursor.getInt(cursor.getColumnIndexOrThrow("doanhThu"));
            } catch (Exception e) {
                doanhThu = 0;
            }
        }
        cursor.close();
        return doanhThu;
    }
}
