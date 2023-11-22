package fpoly.edu.ungdungbantrasua.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ConcurrentModificationException;
import java.util.List;

import fpoly.edu.ungdungbantrasua.DAO.LoaiSanPhamDAO;
import fpoly.edu.ungdungbantrasua.DTO.LoaiSanPham;
import fpoly.edu.ungdungbantrasua.DTO.SanPham;
import fpoly.edu.ungdungbantrasua.Fragment.SanPhamFragment;
import fpoly.edu.ungdungbantrasua.R;

public class SanPhamAdapter extends ArrayAdapter<SanPham> {
    private Context context;
    SanPhamFragment fragment;
    private List<SanPham> list;
    TextView tv_maSanPham, tv_tenSanPham, tv_Loai, tv_soLuong, tv_gia;
    ImageView imgDel;

    public SanPhamAdapter(@NonNull Context context, SanPhamFragment fragment, List<SanPham> list) {
        super(context, 0, list);
        this.context = context;
        this.fragment = fragment;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.item_sanpham, null);
        }

        final SanPham item = list.get(position);
        if (item != null) {
            LoaiSanPhamDAO loaiSanPhamDAO = new LoaiSanPhamDAO(context);
            LoaiSanPham loaiSanPham = loaiSanPhamDAO.getID(String.valueOf(item.getMaLoai()));
            tv_maSanPham = v.findViewById(R.id.tv_MaSanPham);
            tv_maSanPham.setText("Mã sản phẩm: "+item.getMaTraSua());
            tv_tenSanPham = v.findViewById(R.id.tv_tenSanPham);
            tv_tenSanPham.setText("Tên sản phẩm: "+item.getTenTraSua());
            tv_Loai = v.findViewById(R.id.tv_Loai);
            tv_Loai.setText("Loại sản phẩm: "+loaiSanPham.getTenLoai());
            tv_soLuong = v.findViewById(R.id.tv_SoLuong);
            tv_soLuong.setText("Số lượng tồn: "+item.getSoLuongKho());
            tv_gia = v.findViewById(R.id.tv_Gia);
            tv_gia.setText("Giá: "+item.getGia());

            imgDel = v.findViewById(R.id.imgDeleteSanPham);
        }
        imgDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Xóa
                fragment.xoaSanPham(String.valueOf(item.getMaTraSua()));
            }
        });

        return v;
    }
}
