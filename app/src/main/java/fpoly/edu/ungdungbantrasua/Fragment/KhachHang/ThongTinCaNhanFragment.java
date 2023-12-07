package fpoly.edu.ungdungbantrasua.Fragment.KhachHang;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import fpoly.edu.ungdungbantrasua.Adapter.LoaiSanPhamSpinnerAdapter;
import fpoly.edu.ungdungbantrasua.DAO.AdminDAO;
import fpoly.edu.ungdungbantrasua.DAO.KhachHangDAO;
import fpoly.edu.ungdungbantrasua.DAO.LoaiSanPhamDAO;
import fpoly.edu.ungdungbantrasua.DTO.Admin;
import fpoly.edu.ungdungbantrasua.DTO.KhachHang;
import fpoly.edu.ungdungbantrasua.DTO.LoaiSanPham;
import fpoly.edu.ungdungbantrasua.LoginActivity;
import fpoly.edu.ungdungbantrasua.R;

public class ThongTinCaNhanFragment extends Fragment {
    private Button btn_dangxuat, btn_savePass, btn_canclePass;
    Dialog dialog_doimk;
    AdminDAO adminDAO;
    SharedPreferences sharedPreferences;
    EditText edPassCu, edPassMoi, edPassMoiNhapLai;
    TextView layout_mk, txt_name;
    private boolean isChuoi(String str) {
        return str.matches("[a-zA-Z0-9]+");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_thong_tin_ca_nhan, container, false);
        btn_dangxuat = v.findViewById(R.id.dang_xuat);
        layout_mk = v.findViewById(R.id.layout_mk);
        adminDAO = new AdminDAO(getContext());
        txt_name = v.findViewById(R.id.txt_name);

        btn_dangxuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                adminDAO.logoutCustomer();
                startActivity(new Intent(getContext(), LoginActivity.class));
            }
        });
        layout_mk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDialogDoiMK(getContext());
            }
        });

        return v;
    }

    private void ShowDialogDoiMK(final Context context) {
        //Custom dialog
        dialog_doimk = new Dialog(context);
        dialog_doimk.setContentView(R.layout.dialog_doimk);

        edPassCu = dialog_doimk.findViewById(R.id.edPassCu);
        edPassMoi = dialog_doimk.findViewById(R.id.edPassMoi);
        edPassMoiNhapLai = dialog_doimk.findViewById(R.id.edPassMoiNhapLai);
        btn_savePass = dialog_doimk.findViewById(R.id.btn_savePass);
        btn_canclePass = dialog_doimk.findViewById(R.id.btn_canclePass);

        adminDAO = new AdminDAO(context);
        sharedPreferences = getContext().getSharedPreferences("USER_FILE", Context.MODE_PRIVATE);

        btn_savePass.setOnClickListener(v -> {
            String passCu = edPassCu.getText().toString();
            String passMoi = edPassMoi.getText().toString();
            String RePassMoi = edPassMoiNhapLai.getText().toString();
            if (validate(passCu, passMoi, RePassMoi)) {
                String userName = sharedPreferences.getString("USERNAME", "");
                Admin admin = adminDAO.getID(userName);
                admin.setMatKhau(passMoi);
                if (adminDAO.updatePass(admin)) {
                    Toast.makeText(getContext(), "Thay đổi thành công ", Toast.LENGTH_SHORT).show();
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("PASSWORD", passMoi);
                    editor.apply();
                    clearFrom();
                    dialog_doimk.dismiss();
                } else {
                    Toast.makeText(getContext(), "Thay đổi thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn_canclePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_doimk.dismiss();
            }
        });
        dialog_doimk.show();
    }

    private void clearFrom() {
        edPassCu.setText("");
        edPassMoi.setText("");
        edPassMoiNhapLai.setText("");
    }

    private boolean validate(String passCu, String passMoi, String RePassMoi) {
        if (passCu.isEmpty() || passMoi.isEmpty() || RePassMoi.isEmpty()) {
            Toast.makeText(getContext(), "Vui lòng không bỏ trống", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!isChuoi(passCu) || !isChuoi(passMoi) || !isChuoi(RePassMoi)) {
            Toast.makeText(getContext(), "Nhập sai định dạng", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            String pass_Old = sharedPreferences.getString("PASSWORD", "");
            if (!passCu.equals(pass_Old)) {
                Toast.makeText(getContext(), "Mật khẩu cũ không đúng", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (!passMoi.equals(RePassMoi)) {
                Toast.makeText(getContext(), "Mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }
}
