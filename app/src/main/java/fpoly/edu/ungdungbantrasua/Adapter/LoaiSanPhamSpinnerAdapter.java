package fpoly.edu.ungdungbantrasua.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import fpoly.edu.ungdungbantrasua.DTO.LoaiSanPham;
import fpoly.edu.ungdungbantrasua.DTO.SanPham;
import fpoly.edu.ungdungbantrasua.R;

public class LoaiSanPhamSpinnerAdapter extends ArrayAdapter<LoaiSanPham>{
    private Context context;
    private ArrayList<LoaiSanPham> list;
    TextView tv_maSanPham, tv_tenSanPham;

    public LoaiSanPhamSpinnerAdapter(@NonNull Context context, @NonNull ArrayList<LoaiSanPham> list1) {
        super(context, 0, list1);
        this.context = context;
        this.list = list1;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.item_sanpham_spinner, null);
        }
        final LoaiSanPham item = list.get(position);
        if (item!=null){
            tv_maSanPham = v.findViewById(R.id.tv_MaSanPhamSP);
            tv_maSanPham.setText(item.getMaLoai() + ". ");
            tv_tenSanPham = v.findViewById(R.id.tv_TenSanPhamSP);
            tv_tenSanPham.setText(item.getTenLoai());
        }
        return v;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.item_sanpham_spinner, null);
        }
        final LoaiSanPham item = list.get(position);
        if (item!=null){
            tv_maSanPham = v.findViewById(R.id.tv_MaSanPhamSP);
            tv_maSanPham.setText(item.getMaLoai() + ". ");
            tv_tenSanPham = v.findViewById(R.id.tv_TenSanPhamSP);
            tv_tenSanPham.setText(item.getTenLoai());
        }
        return v;
    }
}
