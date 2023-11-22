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

import fpoly.edu.ungdungbantrasua.DTO.KhachHang;
import fpoly.edu.ungdungbantrasua.R;

public class KhachHangSpinnerAdapter extends ArrayAdapter<KhachHang> {
    private Context context;
    private ArrayList<KhachHang> list;
    TextView tv_maKH, tv_tenKH;

    public KhachHangSpinnerAdapter(@NonNull Context context, ArrayList<KhachHang> list) {
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
            v = inflater.inflate(R.layout.item_khachhang_spinner, null);
        }
        final KhachHang item = list.get(position);
        if (item!=null){
            tv_maKH = v.findViewById(R.id.tv_MaKHSP);
            tv_maKH.setText(item.getMaKH() + ". ");
            tv_tenKH = v.findViewById(R.id.tv_TenKHSP);
            tv_tenKH.setText(item.getHoTen());
        }
        return v;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.item_khachhang_spinner, null);
        }
        final KhachHang item = list.get(position);
        if (item!=null){
            tv_maKH = v.findViewById(R.id.tv_MaKHSP);
            tv_maKH.setText(item.getMaKH() + ". ");
            tv_tenKH = v.findViewById(R.id.tv_TenKHSP);
            tv_tenKH.setText(item.getHoTen());
        }
        return v;
    }
}
