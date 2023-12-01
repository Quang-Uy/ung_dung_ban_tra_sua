package fpoly.edu.ungdungbantrasua.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import fpoly.edu.ungdungbantrasua.DAO.LoaiSanPhamDAO;
import fpoly.edu.ungdungbantrasua.DTO.LoaiSanPham;
import fpoly.edu.ungdungbantrasua.DTO.SanPham;
import fpoly.edu.ungdungbantrasua.R;

public class DanhSachAdapter extends RecyclerView.Adapter<DanhSachAdapter.ViewHolder>{

    Context context;
    List<SanPham> sanPhamList;

    public DanhSachAdapter(Context context, List<SanPham> sanPhamList){
        this.context = context;
        this.sanPhamList = sanPhamList;
    }

    @NonNull
    @Override
    public DanhSachAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = View.inflate(context, R.layout.item_danhsach, null);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final SanPham item = sanPhamList.get(position);
        if (item != null) {
            LoaiSanPhamDAO loaiSanPhamDAO = new LoaiSanPhamDAO(context);
            LoaiSanPham loaiSanPham = loaiSanPhamDAO.getID(String.valueOf(item.getMaLoai()));
            holder.tv_DSname.setText("" + item.getTenTraSua());
            holder.tv_DSLoai.setText("Loại: " + loaiSanPham.getTenLoai());
            holder.tv_DSgia.setText(""+ item.getGia() + " VNĐ");
            holder.img_dathang.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return sanPhamList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_DSname, tv_DSLoai, tv_DSgia;
        ImageView image_khachhang, img_dathang;
        CardView cardView_product;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_DSname = (TextView) itemView.findViewById(R.id.tv_DSname);
            tv_DSLoai = (TextView) itemView.findViewById(R.id.tv_DSLoai);
            tv_DSgia = (TextView) itemView.findViewById(R.id.tv_DSgia);
            img_dathang = (ImageView) itemView.findViewById(R.id.img_dathang);
            cardView_product = (CardView) itemView.findViewById(R.id.cardView_product);
        }
    }

}
