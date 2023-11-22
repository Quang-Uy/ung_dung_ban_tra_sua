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

import fpoly.edu.ungdungbantrasua.DTO.SanPham;
import fpoly.edu.ungdungbantrasua.R;

public class SanPhamSpinnerAdapter extends ArrayAdapter<SanPham> {

    private Context context;
    private ArrayList<SanPham> list;
    TextView tv_maSanPham, tv_tenSanPham;

    public SanPhamSpinnerAdapter(@NonNull Context context, ArrayList<SanPham> list) {
        super(context, 0,list);
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.item_sanpham_spinner, null);
        }
        final SanPham item = list.get(position);
        if (item!=null){
            tv_maSanPham = v.findViewById(R.id.tv_MaSanPhamSP);
            tv_maSanPham.setText(item.getMaTraSua() + ". ");
            tv_tenSanPham = v.findViewById(R.id.tv_TenSanPhamSP);
            tv_tenSanPham.setText(item.getTenTraSua());
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
        final SanPham item = list.get(position);
        if (item!=null){
            tv_maSanPham = v.findViewById(R.id.tv_MaSanPhamSP);
            tv_maSanPham.setText(item.getMaTraSua() + ". ");
            tv_tenSanPham = v.findViewById(R.id.tv_TenSanPhamSP);
            tv_tenSanPham.setText(item.getTenTraSua());
        }
        return v;
    }
}
