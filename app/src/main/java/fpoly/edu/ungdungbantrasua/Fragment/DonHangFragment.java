package fpoly.edu.ungdungbantrasua.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import fpoly.edu.ungdungbantrasua.Adapter.DonHangAdapter;
import fpoly.edu.ungdungbantrasua.Adapter.KhachHangSpinnerAdapter;
import fpoly.edu.ungdungbantrasua.Adapter.SanPhamSpinnerAdapter;
import fpoly.edu.ungdungbantrasua.DAO.DonHangDAO;
import fpoly.edu.ungdungbantrasua.DAO.KhachHangDAO;
import fpoly.edu.ungdungbantrasua.DAO.SanPhamDAO;
import fpoly.edu.ungdungbantrasua.DTO.DonHang;
import fpoly.edu.ungdungbantrasua.DTO.KhachHang;
import fpoly.edu.ungdungbantrasua.DTO.SanPham;
import fpoly.edu.ungdungbantrasua.R;

public class DonHangFragment extends Fragment {
    ListView lv_DonHang;
    ArrayList<DonHang> list;
    static DonHangDAO dao;
    DonHangAdapter adapter;
    DonHang item;

    //Dialog
    FloatingActionButton float_addDH;
    Dialog dialog;
    EditText edt_maDH;
    Spinner spnKH, spnSanPham;
    TextView tv_Ngay, tv_gia, tv_soLuong;
    CheckBox chk_trangThai;
    Button btn_addDH, btn_huyDH;

    //********* Spinner Khách hàng
    KhachHangSpinnerAdapter khachHangSpinnerAdapter;
    ArrayList<KhachHang> listKH;
    KhachHangDAO khachHangDAO;
    KhachHang khachHang;
    int maKhachHang;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    //********* Spinner Sản Phẩm
    SanPhamSpinnerAdapter sanPhamSpinnerAdapter;
    ArrayList<SanPham> listSanPham;
    SanPhamDAO sanPhamDAO;
    SanPham sanPham;
    int  soLuong;
    int maSanPham, gia;
    int positionKH, positionSanPham;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_don_hang, container, false);
        lv_DonHang = v.findViewById(R.id.lvDonHang);
        float_addDH = v.findViewById(R.id.float_addDonHang);
        dao = new DonHangDAO(getActivity());
        float_addDH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(getActivity(), 0);//Insert
            }
        });
        lv_DonHang.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                item = list.get(position);
                showDialog(getActivity(),1);//Update
                return false;
            }
        });

        loadData();
        return v;
    }

    void loadData(){
        list = (ArrayList<DonHang>) dao.getAll();
        adapter = new DonHangAdapter(getActivity(), this,list);
        lv_DonHang.setAdapter(adapter);
    }

    protected void showDialog(final Context context, int type){
        //Custom dialog
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_donhang);
        edt_maDH = dialog.findViewById(R.id.edt_MaDonHang);
        edt_maDH.setEnabled(false);
        spnKH = dialog.findViewById(R.id.spn_MaKhachHang);
        spnSanPham = dialog.findViewById(R.id.spn_MaTraSua);
        tv_Ngay = dialog.findViewById(R.id.tv_ngay);
        tv_gia = dialog.findViewById(R.id.tv_gia);
        tv_soLuong = dialog.findViewById(R.id.tv_soLuong);
        chk_trangThai = dialog.findViewById(R.id.chk_trangThai);
        btn_addDH = dialog.findViewById(R.id.btn_addDonHang);
        btn_huyDH = dialog.findViewById(R.id.btn_huyDonHang);
        //set Ngay thue mac dinh
        tv_Ngay.setText("Ngày đặt: "+sdf.format(new Date()));

        //Spinner khách hàng
        khachHangDAO = new KhachHangDAO(context);
        listKH = new ArrayList<KhachHang>();
        listKH = (ArrayList<KhachHang>)khachHangDAO.getAll();
        khachHangSpinnerAdapter = new KhachHangSpinnerAdapter(context,listKH);
        spnKH.setAdapter(khachHangSpinnerAdapter);
        spnKH.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                maKhachHang = listKH.get(position).getMaKH();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Spinner Sản phẩm
        sanPhamDAO = new SanPhamDAO(context);
        listSanPham = new ArrayList<SanPham>();
        listSanPham = (ArrayList<SanPham>)sanPhamDAO.getAll();
        sanPhamSpinnerAdapter = new SanPhamSpinnerAdapter(context, listSanPham);
        spnSanPham.setAdapter(sanPhamSpinnerAdapter);
        //Lấy mã loại sản phẩm
        spnSanPham.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                maSanPham = listSanPham.get(position).getMaLoai();

                soLuong = listSanPham.get(position).getSoLuongKho();//get số lượng đơn hàng vào đây
                tv_soLuong.setText("Số lượng: "+soLuong);
                gia = listSanPham.get(position).getGia();
                tv_gia.setText("Giá: "+gia);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //Edit set data
        if (type != 0){
            edt_maDH.setText(String.valueOf(item.getMaDonHang()));
            for (int i = 0; i < listKH.size(); i++)
                if (item.getMaKH() == (listKH.get(i).getMaKH())){
                    positionKH = i;
                }
            spnKH.setSelection(positionKH);

            for (int i = 0; i < listSanPham.size(); i++)
                if (item.getMaTraSua() == (listSanPham.get(i).getMaTraSua())){
                    positionSanPham = i;
                }
            spnSanPham.setSelection(positionSanPham);
            tv_Ngay.setText("Ngày: "+sdf.format(item.getNgay()));
//            tv_soLuong.setText("Số lượng: "+item.getSoLuong());
            tv_gia.setText("Giá: "+item.getGia());
            if (item.getTrangThai()==1){
                chk_trangThai.setChecked(true);
            } else {
                chk_trangThai.setChecked(false);
            }
        }
        btn_huyDH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btn_addDH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item = new DonHang();
                item.setMaTraSua(maSanPham);
                item.setMaKH(maKhachHang);
                item.setNgay(new Date());
                item.setSoLuong(soLuong); //***
                item.setGia(gia);
                if (chk_trangThai.isChecked()){
                    item.setTrangThai(1);
                } else {
                    item.setTrangThai(0);
                }

                if (type == 0){
                    //type = 0 (insert)
                    if (dao.insert(item)>0){
                        Toast.makeText(context, "Thêm thành công", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    //type = 1 (update)
                    item.setMaDonHang(Integer.parseInt(edt_maDH.getText().toString()));
                    if (dao.update(item)>0){
                        Toast.makeText(context, "Sửa thành công", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Sửa thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
                loadData();
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void xoa(String Id){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Thông báo");
        builder.setMessage("Bạn có muốn xóa không ?");
        builder.setCancelable(true);
        builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Gọi id delete
                dao.delete(Id);
                //Cập nhật lại LV
                loadData();
                dialog.cancel();
            }
        });
        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        builder.show();
    }
}