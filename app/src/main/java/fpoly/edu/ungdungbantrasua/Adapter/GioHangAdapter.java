package fpoly.edu.ungdungbantrasua.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.List;

import fpoly.edu.ungdungbantrasua.DAO.GioHangDAO;
import fpoly.edu.ungdungbantrasua.DTO.GioHang;
import fpoly.edu.ungdungbantrasua.Fragment.KhachHang.GioHangFragment;
import fpoly.edu.ungdungbantrasua.R;

public class GioHangAdapter extends RecyclerView.Adapter<GioHangAdapter.GioHangViewHolder> {

    private List<GioHang> cartItems;
    private Context context;
    private GioHangDAO gioHangDAO;
    private OnQuantityChangeListener onQuantityChangeListener;
    private GioHang item;


    public GioHangAdapter(List<GioHang> cartItems, Context context, OnQuantityChangeListener listener) {
        this.cartItems = cartItems;
        this.context = context;
        this.onQuantityChangeListener = listener;
    }

    @NonNull
    @Override
    public GioHangViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_giohang, parent, false);
        return new GioHangViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GioHangAdapter.GioHangViewHolder holder, int position) {
        item = cartItems.get(position);

        holder.txtTotalItems.setText(String.valueOf(item.getGiaGioHang()));
        holder.txtItemName.setText(item.getTenSanPham());
        DecimalFormat decimalFormat = new DecimalFormat("#,###.##");
        String formattedPrice = decimalFormat.format(item.getGia());
        holder.txtItemPrice.setText(formattedPrice);

        holder.increase.setOnClickListener(view -> {
            if (onQuantityChangeListener != null) {
                onQuantityChangeListener.onIncreaseClick(position);

            }
        });

        holder.decrease.setOnClickListener(view -> {
            if (onQuantityChangeListener != null) {
                onQuantityChangeListener.onDecreaseClick(position);
            }
        });
        holder.checkODer.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (onQuantityChangeListener != null) {
                onQuantityChangeListener.onItemCheckedChange(position, isChecked);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public static class GioHangViewHolder extends RecyclerView.ViewHolder {
        ImageView itemImage, delete, decrease, increase;
        TextView txtItemName, txtItemPrice, txtTotalItems;
        CheckBox checkODer;

        public GioHangViewHolder(@NonNull View itemView) {
            super(itemView);
            itemImage = itemView.findViewById(R.id.img_back);
            decrease = itemView.findViewById(R.id.tru);
            increase = itemView.findViewById(R.id.cong);
            txtItemName = itemView.findViewById(R.id.tvProductName);
            txtItemPrice = itemView.findViewById(R.id.tvProductPrice);
            txtTotalItems = itemView.findViewById(R.id.tvQuantity);
            checkODer = itemView.findViewById(R.id.checkbox_item);
        }
    }

    // Interface to handle quantity change events
    public interface OnQuantityChangeListener {
        void onIncreaseClick(int position);

        void onDecreaseClick(int position);

        void onItemCheckedChange(int position, boolean isChecked);
    }

    public void deleteCartItem(int position) {
        gioHangDAO = new GioHangDAO(context);
        gioHangDAO.xoaGioHang(item.getMaGioHang());
        notifyItemRemoved(position);
        notifyDataSetChanged();

    }
}
