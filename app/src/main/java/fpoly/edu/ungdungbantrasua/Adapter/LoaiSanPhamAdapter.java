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

import fpoly.edu.ungdungbantrasua.DTO.LoaiSanPham;
import fpoly.edu.ungdungbantrasua.Fragment.Admin.LoaiSanPhamFragment;
import fpoly.edu.ungdungbantrasua.R;

public class LoaiSanPhamAdapter extends ArrayAdapter<LoaiSanPham> {
    private Context context;
    private ArrayList<LoaiSanPham> list;
    LoaiSanPhamFragment fragment;
    TextView tv_maLoaiSanPham, tv_tenLoaiSanPham;
    ImageView iv_del;

    public LoaiSanPhamAdapter(Context context, LoaiSanPhamFragment fragment, ArrayList<LoaiSanPham> list) {
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
            v = inflater.inflate(R.layout.item_loaisanpham, null);
        }
        final LoaiSanPham item = list.get(position);
        if (item != null){
            tv_maLoaiSanPham = v.findViewById(R.id.tv_maLoaiSanPham);
            tv_maLoaiSanPham.setText("Mã: "+item.getMaLoai());
            tv_tenLoaiSanPham = v.findViewById(R.id.tv_tenLoaiSanPham);
            tv_tenLoaiSanPham.setText("Tên loại: "+item.getTenLoai());

            iv_del = v.findViewById(R.id.iv_delete);
        }
        iv_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Gọi phương thức xóa
                fragment.XoaLoaiSP(String.valueOf(item.getMaLoai()));
            }
        });

        return v;
    }
}
