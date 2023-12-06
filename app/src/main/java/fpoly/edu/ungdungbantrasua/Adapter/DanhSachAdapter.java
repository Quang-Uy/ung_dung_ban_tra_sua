package fpoly.edu.ungdungbantrasua.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import fpoly.edu.ungdungbantrasua.DAO.GioHangDAO;
import fpoly.edu.ungdungbantrasua.DAO.LoaiSanPhamDAO;
import fpoly.edu.ungdungbantrasua.DAO.SessionManager;
import fpoly.edu.ungdungbantrasua.DTO.GioHang;
import fpoly.edu.ungdungbantrasua.DTO.LoaiSanPham;
import fpoly.edu.ungdungbantrasua.DTO.SanPham;
import fpoly.edu.ungdungbantrasua.R;

public class DanhSachAdapter extends RecyclerView.Adapter<DanhSachAdapter.ViewHolder>{

    Context context;
    List<SanPham> sanPhamList;
    ImageView tv_datHang;
    private GioHangDAO gioHangDAO;
    private GioHang gioHang;
    private SessionManager sessionManager;
    private List<GioHang> selectedItems = new ArrayList<>();

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

            sessionManager = new SessionManager(context);
            holder.tv_datHang.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    gioHangDAO = new GioHangDAO(context);
                    Animation anim = AnimationUtils.loadAnimation(context, R.anim.button_scale_animation);
                    v.startAnimation(anim);
                    if (gioHangDAO.cartExists(sessionManager.getLoggedInCustomerId(), item.getMaTraSua())) {

                        Toast.makeText(context, "Sản phẩm đã tồn tại trong giỏ hàng", Toast.LENGTH_SHORT).show();
                    } else {
                        gioHang = new GioHang();
                        gioHang.setRole(sessionManager.getLoggedInCustomerId());
                        gioHang.setMaTraSua(item.getMaTraSua());
                        gioHang.setGiaGioHang(1);

                        if (gioHangDAO.addGioHang(gioHang) > 0) {

                            Toast.makeText(context, "Sản phẩm "  + item.getTenTraSua() + " đã thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
                        } else {

                            Toast.makeText(context, "Failed to add product to cart.", Toast.LENGTH_SHORT).show();
                        }
                    }
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
        ImageView tv_datHang;
        CardView cardView_product;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_DSname = (TextView) itemView.findViewById(R.id.tv_DSname);
            tv_DSLoai = (TextView) itemView.findViewById(R.id.tv_DSLoai);
            tv_DSgia = (TextView) itemView.findViewById(R.id.tv_DSgia);
            tv_datHang = (ImageView) itemView.findViewById(R.id.tv_datHang);
            cardView_product = (CardView) itemView.findViewById(R.id.cardView_product);
        }
    }

}
