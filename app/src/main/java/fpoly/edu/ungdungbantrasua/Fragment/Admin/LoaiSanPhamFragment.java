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

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import fpoly.edu.ungdungbantrasua.Adapter.LoaiSanPhamAdapter;
import fpoly.edu.ungdungbantrasua.DAO.LoaiSanPhamDAO;
import fpoly.edu.ungdungbantrasua.DTO.LoaiSanPham;
import fpoly.edu.ungdungbantrasua.R;

public class LoaiSanPhamFragment extends Fragment {
    ListView lv_LoaiSanPham;
    ArrayList<LoaiSanPham> list;
    LoaiSanPhamDAO loaiSanPhamDAO;
    LoaiSanPhamAdapter adapter;
    LoaiSanPham item;
    FloatingActionButton float_addLoaiSP;
    Dialog dialog;
    EditText edt_MaLoai, edt_tenLoai;

    Button btn_addLoaiSP, btn_huyLoaiSP;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_loai_san_pham, container, false);
        //Thiết kế giao diện
        lv_LoaiSanPham = v.findViewById(R.id.lvLoaiSanPham);
        float_addLoaiSP = v.findViewById(R.id.float_addLoaiSanPham);
        loaiSanPhamDAO = new LoaiSanPhamDAO(getActivity());
        //Nhấn float
        float_addLoaiSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogAdd(getActivity(), 0);//Lenh add
            }
        });
        //Giữ item DS
        lv_LoaiSanPham.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                item = list.get(position);
                showDialogAdd(getActivity(),1);//Lenh update

                return false;
            }
        });
        loadData();
        return v;
    }

    private void loadData(){
        //Adapter
        list = (ArrayList<LoaiSanPham>) loaiSanPhamDAO.getAll();
        adapter = new LoaiSanPhamAdapter(getActivity(), this, list);
        lv_LoaiSanPham.setAdapter(adapter);
    }

    public void XoaLoaiSP(String Id) {
        //Sử dụng alertdialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Thông báo");
        builder.setMessage("Bạn có muốn xóa không ?");
        builder.setCancelable(true);
        builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Gọi id delete
                loaiSanPhamDAO.delete(Id);
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

    protected void showDialogAdd(final Context context, int type){
        //Custom dialog
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_loaisanpham);
        //ÁNh xạ
        edt_MaLoai = dialog.findViewById(R.id.edt_MaLoai);
        edt_tenLoai = dialog.findViewById(R.id.edt_TenLoai);
        btn_addLoaiSP = dialog.findViewById(R.id.btn_addLS);
        btn_huyLoaiSP = dialog.findViewById(R.id.btn_huyLS);

        //Kiểm tra type insert = 0 hay 1
        edt_MaLoai.setEnabled(false);
        if (type != 0) {
            edt_MaLoai.setText(String.valueOf(item.getMaLoai()));
            edt_tenLoai.setText(String.valueOf(item.getTenLoai()));
        }
        btn_huyLoaiSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btn_addLoaiSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item = new LoaiSanPham();
                //Xét dữ liệu
                item.setTenLoai(edt_tenLoai.getText().toString());

                if (KiemTra() > 0) {
                    if (type == 0) {
                        //type = 0 sẽ gọi lệnh thêm
                        if (loaiSanPhamDAO.insert(item) > 0) {
                            Toast.makeText(context, "Thêm thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        //type = 1 sẽ gọi lệnh sửa
                        item.setMaLoai(Integer.parseInt(edt_MaLoai.getText().toString()));
                        if (loaiSanPhamDAO.update(item) > 0) {
                            Toast.makeText(context, "Sửa thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Sửa thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                    loadData();
                    dialog.dismiss();
                }
            }
        });
        dialog.show();
    }

    public int KiemTra() {
        int check = 1;
        if (edt_tenLoai.getText().length()==0) {
            Toast.makeText(getContext(), "Thông tin không được để trống", Toast.LENGTH_SHORT).show();
            check = -1;
        }
        return check;
    }
}