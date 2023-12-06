package fpoly.edu.ungdungbantrasua.Fragment.KhachHang;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import fpoly.edu.ungdungbantrasua.Adapter.GioHangAdapter;
import fpoly.edu.ungdungbantrasua.Adapter.SwipeToDeleteCallback;
import fpoly.edu.ungdungbantrasua.DAO.AdminDAO;
import fpoly.edu.ungdungbantrasua.DAO.GioHangDAO;
import fpoly.edu.ungdungbantrasua.DAO.SessionManager;
import fpoly.edu.ungdungbantrasua.DTO.GioHang;
import fpoly.edu.ungdungbantrasua.KhachHangHomeActivity;
import fpoly.edu.ungdungbantrasua.R;

public class GioHangFragment extends Fragment implements GioHangAdapter.OnQuantityChangeListener {

    private RecyclerView recyclerViewCart;
    private GioHangAdapter cartAdapter;
    private ArrayList<GioHang> cartArrayList;
    private GioHangDAO cartDao;
    private TextView txtTotalPrice, txtVuotXoa;
    private Button btnThanhToan, btbMuaNgay;
    private List<GioHang> selectedItems = new ArrayList<>();
    private GioHang cartItem;
    private View view;
    private SessionManager sessionManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_gio_hang, container, false);

        recyclerViewCart = view.findViewById(R.id.recyclerView_cart);
        txtTotalPrice = view.findViewById(R.id.txt_total_price);
        txtVuotXoa = view.findViewById(R.id.txtVuotXoa);
        btnThanhToan = view.findViewById(R.id.btn_dat_hang);
        btbMuaNgay = view.findViewById(R.id.muasp);
        cartDao = new GioHangDAO(getContext());
        sessionManager = new SessionManager(getContext());
        Animation fadeAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_in);
        txtVuotXoa.startAnimation(fadeAnimation);

        showData();
        btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Đặt hàng thành công", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getContext(), DatHangOKActivity.class));
                cartDao.clearCart();
                showData();
            }
        });
        btbMuaNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), KhachHangHomeActivity.class));
            }
        });

        return view;
    }

    public void showData() {

        cartArrayList = (ArrayList<GioHang>) cartDao.getAllCart(sessionManager.getLoggedInCustomerId());
        if (cartArrayList.isEmpty()) {
            view.findViewById(R.id.layout_dat_hang).setVisibility(View.GONE);
            view.findViewById(R.id.layout_sum).setVisibility(View.GONE);
            view.findViewById(R.id.layout_cart_null).setVisibility(View.VISIBLE);
        } else {
            view.findViewById(R.id.layout_dat_hang).setVisibility(View.VISIBLE);
            view.findViewById(R.id.layout_sum).setVisibility(View.VISIBLE);
            view.findViewById(R.id.layout_cart_null).setVisibility(View.GONE);
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerViewCart.setLayoutManager(layoutManager);

        cartAdapter = new GioHangAdapter(cartArrayList, getContext(), this);
        recyclerViewCart.setAdapter(cartAdapter);

        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(cartAdapter);
        swipeToDeleteCallback.setSwipeActionListener(new SwipeToDeleteCallback.SwipeActionListener() {
            @Override
            public void onItemDeleted() {
                showData();
            }
        });
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchHelper.attachToRecyclerView(recyclerViewCart);


    }

    @Override
    public void onIncreaseClick(int position) {
        cartItem = cartArrayList.get(position);
        int currentQuantity = cartItem.getGiaGioHang();
        currentQuantity++;
        cartItem.setGiaGioHang(currentQuantity);
        cartDao.updateCartItem(cartItem);
        cartAdapter.notifyDataSetChanged();
        updateTotalPrice();
    }

    @Override
    public void onDecreaseClick(int position) {
        cartItem = cartArrayList.get(position);
        int currentQuantity = cartItem.getGiaGioHang();
        if (currentQuantity > 1) {
            currentQuantity--;
            cartItem.setGiaGioHang(currentQuantity);
            cartDao.updateCartItem(cartItem);
            updateTotalPrice();
            cartAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onItemCheckedChange(int position, boolean isChecked) {
        cartItem = cartArrayList.get(position);
        cartItem.setChecked(isChecked);

        if (cartItem.isChecked()) {
            selectedItems.add(cartItem);
        } else {
            selectedItems.remove(cartItem);
        }
        updateTotalPrice();
    }

    private void updateTotalPrice() {
        double totalPrice = 0;
        for (GioHang item : selectedItems) {
            int quantity = item.getGiaGioHang();
            int price = item.getGia();
            totalPrice += quantity * price;
        }
        DecimalFormat decimalFormat = new DecimalFormat("#,###.##");
        String formattedPrice = decimalFormat.format(totalPrice);
        txtTotalPrice.setText(formattedPrice + " vnđ");

    }
}