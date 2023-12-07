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

import java.util.ArrayList;

import fpoly.edu.ungdungbantrasua.DTO.NhanVien;
import fpoly.edu.ungdungbantrasua.Fragment.Admin.NhanVienFragment;
import fpoly.edu.ungdungbantrasua.R;

public class NhanVienAdapter extends ArrayAdapter<NhanVien> {
    private Context context;
    NhanVienFragment fragment;
    private ArrayList<NhanVien> list;

    TextView tvMaNV, tvTenNV, tvNamSinh, tv_matKhau;
    ImageView imgDel;


    public NhanVienAdapter(@NonNull Context context, NhanVienFragment fragment, ArrayList<NhanVien> list) {
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
            v = inflater.inflate(R.layout.item_nhanvien, null);
        }
        final NhanVien item = list.get(position);
        if (item != null) {
            tvMaNV = v.findViewById(R.id.tv_MaNV);
            tvMaNV.setText("Mã: "+item.getMaNhanVien());
            tvTenNV = v.findViewById(R.id.tv_TenNV);
            tvTenNV.setText("Tên nhân viên: "+item.getHoTen());
            tvNamSinh = v.findViewById(R.id.tv_NamSinh);
            tvNamSinh.setText("Năm sinh: "+item.getNamSinh());
            tv_matKhau = v.findViewById(R.id.tv_matKhau);
            tv_matKhau.setText("Mật khẩu: "+item.getMatKhau());
            imgDel = v.findViewById(R.id.imgDeleteNV);
        }
        imgDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Phương thức xóa
                fragment.xoaNV(String.valueOf(item.getMaNhanVien()));
            }
        });
        return v;
    }
}
