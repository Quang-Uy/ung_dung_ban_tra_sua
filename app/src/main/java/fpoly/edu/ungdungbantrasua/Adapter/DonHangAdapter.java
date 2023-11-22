package fpoly.edu.ungdungbantrasua.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import fpoly.edu.ungdungbantrasua.DAO.KhachHangDAO;
import fpoly.edu.ungdungbantrasua.DAO.SanPhamDAO;
import fpoly.edu.ungdungbantrasua.DTO.DonHang;
import fpoly.edu.ungdungbantrasua.DTO.KhachHang;
import fpoly.edu.ungdungbantrasua.DTO.SanPham;
import fpoly.edu.ungdungbantrasua.Fragment.DonHangFragment;
import fpoly.edu.ungdungbantrasua.R;

public class DonHangAdapter extends ArrayAdapter<DonHang> {
    private Context context;
    DonHangFragment fragment;
    private ArrayList<DonHang> list;
    TextView tv_MaDonHang, tv_TenKH, tv_TenSanPham, tv_gia, tv_Ngay, tv_trangThai, tv_soLuong;
    ImageView img_Del;
    SanPhamDAO sanPhamDAO;
    KhachHangDAO khachHangDAO;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public DonHangAdapter(@NonNull Context context, DonHangFragment fragment, ArrayList<DonHang> list) {
        super(context, 0, list);
        this.context = context;
        this.fragment = fragment;
        this.list = list;
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.item_donhang, null);
        }

        final DonHang item = list.get(position);
        if (item != null){
            tv_MaDonHang = v.findViewById(R.id.tv_MaDonHang);
            tv_MaDonHang.setText("Mã đơn: "+item.getMaDonHang());

            sanPhamDAO = new SanPhamDAO(context);
            SanPham sanPham = sanPhamDAO.getID(String.valueOf(item.getMaTraSua()));
            tv_TenSanPham= v.findViewById(R.id.tv_TenSanPham);
            tv_TenSanPham.setText("Tên sản phẩm: "+sanPham.getTenTraSua());
            khachHangDAO = new KhachHangDAO(context);
            KhachHang khachHang = khachHangDAO.getID(String.valueOf(item.getMaKH()));
            tv_TenKH = v.findViewById(R.id.tv_TenKH);
            tv_TenKH.setText("Khách hàng: "+khachHang.getHoTen());
            tv_gia = v.findViewById(R.id.tv_giaSanPham);
            tv_gia.setText("Giá: "+item.getGia());
            tv_soLuong = v.findViewById(R.id.tv_soLuong);
            tv_soLuong.setText("Số lượng: "+item.getSoLuong());
            tv_Ngay = v.findViewById(R.id.tv_Ngay);
            //*****
            tv_Ngay.setText("Ngày: "+sdf.format(item.getNgay()));
            tv_trangThai = v.findViewById(R.id.tv_trangThai);
            if (item.getTrangThai()==1){
                tv_trangThai.setTextColor(Color.BLUE);
                tv_trangThai.setText("Đã xử lý");
            } else {
                tv_trangThai.setTextColor(Color.RED);
                tv_trangThai.setText("Chờ xử lý");
            }
            img_Del = v.findViewById(R.id.img_DelDH);
        }
        img_Del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Phương thức xóa
                fragment.xoa(String.valueOf(item.getMaDonHang()));
            }
        });
        return v;
    }
}
