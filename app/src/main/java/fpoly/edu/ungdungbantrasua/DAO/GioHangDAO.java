package fpoly.edu.ungdungbantrasua.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import fpoly.edu.ungdungbantrasua.DTO.GioHang;
import fpoly.edu.ungdungbantrasua.Database.DbHelper;

public class GioHangDAO {
    private DbHelper dbHelper;
    private SQLiteDatabase db;

    public GioHangDAO(Context context) {
        dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long addGioHang(GioHang gioHang) {
        ContentValues values = new ContentValues();
        values.put("role", gioHang.getRole());
        values.put("maTraSua", gioHang.getMaTraSua());
        values.put("giaGioHang", gioHang.getGiaGioHang());
        long cartId = db.insert("GioHang", null, values);
        db.close();
        return cartId;
    }
    public boolean cartExists(int role, int maTraSua) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT maGioHang FROM GioHang WHERE role = ? AND maTraSua = ?";
        String[] selectionArgs = {String.valueOf(role), String.valueOf(maTraSua)};
        Cursor cursor = db.rawQuery(query, selectionArgs);

        boolean exists = cursor.moveToFirst();

        return exists;
    }

    public int xoaGioHang(int maGioHang) {
        String whereClause = "maGioHang = ?";
        String[] whereArgs = {String.valueOf(maGioHang)};
        int rowsAffected = db.delete("GioHang", whereClause, whereArgs);
        db.close();
        return rowsAffected;
    }

    public void deleteCarts(List<GioHang> gioHangs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.beginTransaction();
        try {
            for (GioHang gioHang : gioHangs) {
                int cartId = gioHang.getMaGioHang();
                String whereClause = "maGioHang = ?";
                String[] whereArgs = {String.valueOf(cartId)};
                db.delete("GioHang", whereClause, whereArgs);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    @SuppressLint("Range")
    public List<GioHang> getAllCart(int role) {

        List<GioHang> cartItems = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT c.maGioHang, p.maTraSua, p.tenTraSua, p.gia, c.giaGioHang " +
                "FROM GioHang c " +
                "INNER JOIN TraSua p ON c.maTraSua = p.maTraSua " +
                "WHERE c.role = " + role;

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                int cartId = cursor.getInt(cursor.getColumnIndex("maGioHang"));
                int productId = cursor.getInt(cursor.getColumnIndex("maTraSua"));
                String productName = cursor.getString(cursor.getColumnIndex("tenTraSua"));
                int price = cursor.getInt(cursor.getColumnIndex("gia"));
                int quantity = cursor.getInt(cursor.getColumnIndex("giaGioHang"));

                GioHang cartItem = new GioHang(cartId, productId, productName, price, quantity);
                cartItems.add(cartItem);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return cartItems;
    }

    public void updateCartItem(GioHang cartItem) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("giaGioHang", cartItem.getGiaGioHang());

        db.update("GioHang", values, "maGioHang=?", new String[]{String.valueOf(cartItem.getMaGioHang())});
        db.close();
    }

    public void clearCart() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("GioHang", null, null);
        db.close();
    }
}
