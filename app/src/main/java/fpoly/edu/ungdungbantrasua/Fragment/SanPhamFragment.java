package fpoly.edu.ungdungbantrasua.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import fpoly.edu.ungdungbantrasua.Adapter.LoaiSanPhamSpinnerAdapter;
import fpoly.edu.ungdungbantrasua.Adapter.SanPhamAdapter;
import fpoly.edu.ungdungbantrasua.DAO.LoaiSanPhamDAO;
import fpoly.edu.ungdungbantrasua.DAO.SanPhamDAO;
import fpoly.edu.ungdungbantrasua.DTO.LoaiSanPham;
import fpoly.edu.ungdungbantrasua.DTO.SanPham;
import fpoly.edu.ungdungbantrasua.R;
public class SanPhamFragment extends Fragment {
    List<SanPham> list;
    FloatingActionButton float_addSanPham;
    Dialog dialog;
    EditText edt_maSanPham, edt_tenSanPham, edt_gia, edt_soLuongKho;
    Spinner spinner;
    Button btn_addSanPham, btn_huySanPham;
    ListView lvSanPham;
    SanPhamDAO sanPhamDAO;
    SanPhamAdapter adapter;
    SanPham item;

    LoaiSanPhamSpinnerAdapter spinnerAdapter;
    ArrayList<LoaiSanPham> listLoaiSanPham;
    LoaiSanPhamDAO loaiSanPhamDAO;
    LoaiSanPham loaiSanPham;
    int maLoaiSanPham, position;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_san_pham, container, false);
        lvSanPham = v.findViewById(R.id.lvSanPham);
        sanPhamDAO = new SanPhamDAO(getActivity());
        capNhatSanPham();
        float_addSanPham = v.findViewById(R.id.float_addSanPham);
        float_addSanPham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(getActivity(),0);
            }
        });
        lvSanPham.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                item = list.get(position);
                openDialog(getActivity(),1);
                return false;
            }
        });

        return v;
    }

    void capNhatSanPham() {
        list = (List<SanPham>) sanPhamDAO.getAll();
        adapter = new SanPhamAdapter(getActivity(), this, list);
        lvSanPham.setAdapter(adapter);
    }

    public void xoaSanPham(String Id) {
        //Sử dụng alertdialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Thông báo");
        builder.setMessage("Bạn có muốn xóa không ?");
        builder.setCancelable(true);
        builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Gọi id delete
                sanPhamDAO.delete(Id);
                //Cập nhật lại LV
                capNhatSanPham();
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

    protected void openDialog(final Context context, int type){
        //Custom dialog
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_sanpham);
        edt_maSanPham = dialog.findViewById(R.id.edt_MaSanPham);
        edt_tenSanPham = dialog.findViewById(R.id.edt_tenSanPham);
        edt_soLuongKho = dialog.findViewById(R.id.edt_soLuongKho);
        edt_gia = dialog.findViewById(R.id.edt_Gia);
        spinner = dialog.findViewById(R.id.spn_LoaiSanPham);
        btn_addSanPham = dialog.findViewById(R.id.btn_addSanPham);
        btn_huySanPham = dialog.findViewById(R.id.btn_huySanPham);

        listLoaiSanPham = new ArrayList<LoaiSanPham>();
        loaiSanPhamDAO = new LoaiSanPhamDAO(context);
        listLoaiSanPham = (ArrayList<LoaiSanPham>) loaiSanPhamDAO.getAll();
        spinnerAdapter = new LoaiSanPhamSpinnerAdapter(context, listLoaiSanPham);
        spinner.setAdapter(spinnerAdapter);
        //Lấy mã loại sản phẩm
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                maLoaiSanPham = listLoaiSanPham.get(position).getMaLoai();
                Toast.makeText(context, "Chọn "+listLoaiSanPham.get(position).getTenLoai(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Kiểm tra type
        edt_maSanPham.setEnabled(false);
        if (type != 0){
            edt_maSanPham.setText(String.valueOf(item.getMaTraSua()));
            edt_tenSanPham.setText(item.getTenTraSua());
            edt_soLuongKho.setText(String.valueOf(item.getSoLuongKho()));
            edt_gia.setText(String.valueOf(item.getGia()));
            for (int i = 0; i < listLoaiSanPham.size(); i++)
                if (item.getMaLoai() == (listLoaiSanPham.get(i).getMaLoai())){
                    position = i;
                }
            Log.i("demo","posSanPham"+position);
            spinner.setSelection(position);
        }
        btn_huySanPham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btn_addSanPham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (KiemTra()>0){
                item = new SanPham();
                item.setTenTraSua(edt_tenSanPham.getText().toString());
                item.setMaLoai(maLoaiSanPham);
                item.setSoLuongKho(Integer.parseInt(edt_soLuongKho.getText().toString()));
                item.setGia(Integer.parseInt(edt_gia.getText().toString()));
                    if (type==0){
                        //Type = 0 (insert)
                        if (sanPhamDAO.insert(item)>0){
                            Toast.makeText(context, "Thêm thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        //Type = 1 (update)
                        item.setMaTraSua(Integer.parseInt(edt_maSanPham.getText().toString()));
                        if (sanPhamDAO.update(item)>0){
                            Toast.makeText(context, "Sửa thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Sửa thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                    capNhatSanPham();
                    dialog.dismiss();
                }
            }
        });
        dialog.show();
    }

    public int KiemTra(){
        int check = 1;
        if (edt_tenSanPham.getText().length()==0 || edt_soLuongKho.getText().length()==0 || edt_gia.getText().length()==0){
            Toast.makeText(getContext(), "Hãy nhập đủ thông tin", Toast.LENGTH_SHORT).show();
            check = -1;
        }
        return check;
    }
}