package fpoly.edu.ungdungbantrasua.Fragment.Admin;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import fpoly.edu.ungdungbantrasua.Adapter.NhanVienAdapter;
import fpoly.edu.ungdungbantrasua.DAO.NhanVienDAO;
import fpoly.edu.ungdungbantrasua.DTO.NhanVien;
import fpoly.edu.ungdungbantrasua.R;

public class NhanVienFragment extends Fragment {
    ListView lvNhanVien;
    ArrayList<NhanVien> list;
    static NhanVienDAO dao;
    NhanVienAdapter adapter;
    NhanVien item;
    FloatingActionButton float_addNV;
    Dialog dialog;
    EditText edt_MaNV, edt_TenNV, edt_NamSinh, edt_matKhau;
    Button btn_addNV, btn_huyNV;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_nhan_vien, container, false);
        lvNhanVien = v.findViewById(R.id.lvNhanVien);
        float_addNV = v.findViewById(R.id.float_addNhanVien);
        dao = new NhanVienDAO(getActivity());
        capNhatLV();
        //Nhấn float
        float_addNV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(getActivity(), 0);//Lenh add
            }
        });
        //Giữ item DS
        lvNhanVien.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                item = list.get(position);
                openDialog(getActivity(),1);//Lenh update

                return false;
            }
        });

        return v;
    }

    void capNhatLV(){
        list = (ArrayList<NhanVien>) dao.getAll();
        adapter = new NhanVienAdapter(getActivity(), this, list);
        lvNhanVien.setAdapter(adapter);
    }

    public void xoaNV(String Id) {
        //Sử dụng alertdialog
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
                capNhatLV();
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

    protected void openDialog(final Context context, int type) {
        //Custom dialog
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_nhanvien);
        //ÁNh xạ
        edt_MaNV = dialog.findViewById(R.id.edt_MaNV);
        edt_TenNV = dialog.findViewById(R.id.edt_TenNV);
        edt_NamSinh = dialog.findViewById(R.id.edt_NamSinh);
        edt_matKhau = dialog.findViewById(R.id.edt_matKhau);
        btn_addNV = dialog.findViewById(R.id.btn_addNV);
        btn_huyNV = dialog.findViewById(R.id.btn_huyNV);

        //Kiểm tra type insert = 0 hay 1
        edt_MaNV.setEnabled(false);
        if (type != 0) {
            edt_MaNV.setText(String.valueOf(item.getMaNhanVien()));
            edt_TenNV.setText(String.valueOf(item.getHoTen()));
            edt_NamSinh.setText(String.valueOf(item.getNamSinh()));
            edt_matKhau.setText(String.valueOf(item.getMatKhau()));
        }
        btn_huyNV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btn_addNV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item = new NhanVien();
                //Xét dữ liệu
                item.setHoTen(edt_TenNV.getText().toString());
                item.setNamSinh(edt_NamSinh.getText().toString());
                item.setMatKhau(edt_matKhau.getText().toString());
                if (KiemTra()>0){
                    if (type==0){
                        //type = 0 sẽ gọi lệnh thêm
                        if (dao.insert(item)>0) {
                            Toast.makeText(context, "Thêm thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        //type = 1 sẽ gọi lệnh sửa
                        item.setMaNhanVien(Integer.parseInt(edt_MaNV.getText().toString()));
                        if (dao.update(item)>0){
                            Toast.makeText(context, "Sửa thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Sửa thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                    capNhatLV();
                    dialog.dismiss();
                }
            }
        });
        dialog.show();
    }

    public int KiemTra() {
        int check = 1;
        if (edt_TenNV.getText().length()==0 || edt_NamSinh.getText().length()==0) {
            Toast.makeText(getContext(), "Thông tin không được để trống", Toast.LENGTH_SHORT).show();
            check = -1;
        }
        return check;
    }
}