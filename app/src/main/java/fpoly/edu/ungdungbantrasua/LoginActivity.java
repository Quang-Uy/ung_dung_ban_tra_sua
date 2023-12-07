package fpoly.edu.ungdungbantrasua;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import fpoly.edu.ungdungbantrasua.DAO.AdminDAO;
import fpoly.edu.ungdungbantrasua.DAO.KhachHangDAO;
import fpoly.edu.ungdungbantrasua.DTO.Admin;

public class LoginActivity extends AppCompatActivity {

    EditText edt_login_ten, edt_login_matkhau;
    TextView tv_Signup;
    Button btn_login;
    CheckBox remember_pass;
    String strUser, strPass;
    AdminDAO dao;
    KhachHangDAO khachHangDAO;
    Spinner spinner_role;
    String value_role;
    int role_position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Ánh xạ
        edt_login_ten = findViewById(R.id.edt_login_ten);
        edt_login_matkhau = findViewById(R.id.edt_login_matkhau);
        remember_pass = findViewById(R.id.remember_pass);
        tv_Signup = findViewById(R.id.tv_sign_up);
        btn_login = findViewById(R.id.btn_login);
        dao = new AdminDAO(this);
        khachHangDAO = new KhachHangDAO(this);

        spinner_role = findViewById(R.id.spinner_role);
        ArrayList<String> list = new ArrayList<>();
        list.add("Quản trị viên");
        list.add("Người dùng");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, list);
        spinner_role.setAdapter(adapter);
        spinner_role.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                role_position = position;
                value_role = list.get(position);
                Toast.makeText(LoginActivity.this, role_position + value_role, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        edt_login_ten.setText("");
        edt_login_matkhau.setText("");
        remember_pass.setChecked(false);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLogin();
            }
        });

        //Đăng kí
        tv_Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });
        ReadFile();
    }

    private void ReadFile() {
        //Đọc user, pass trong SharedPreferences
        SharedPreferences pref = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        edt_login_ten.setText(pref.getString("USERNAME",""));
        edt_login_matkhau.setText(pref.getString("PASSWORD",""));
        remember_pass.setChecked(pref.getBoolean("REMEMBER",false));
        spinner_role.setSelection(pref.getInt("ROLE", 1));
    }

    public void checkLogin() {
        strUser = edt_login_ten.getText().toString().trim();
        strPass = edt_login_matkhau.getText().toString().trim();
        //Để trống
        if (strUser.isEmpty()||strPass.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
        } else {
            if (dao.checkLogin(strUser, strPass, String.valueOf(role_position))) {
                Admin admin = dao.getID(strUser);
                if (admin.getRole() == 0) {
                    value_role = "Quản trị viên";
                    Toast.makeText(this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                    rememberUser(strUser, strPass, remember_pass.isChecked(), role_position);
                    Intent intent = new Intent(this, HomeActivity.class);
                    intent.putExtra("role", value_role);
                    startActivity(intent);
                    finish();
                } else {
                    value_role = "Người dùng";
                    Toast.makeText(this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                    rememberUser(strUser, strPass, remember_pass.isChecked(), role_position);
                    Intent intent = new Intent(this, KhachHangHomeActivity.class);
                    intent.putExtra("role", value_role);
                    startActivity(intent);
                    finish();
                }
            } else {
                Toast.makeText(this, "Tên đăng nhập hoặc mật khẩu không đúng", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void rememberUser(String u, String p, boolean status, int role) {
        SharedPreferences pref = getSharedPreferences("USER_FILE",MODE_PRIVATE);
        SharedPreferences.Editor edit = pref.edit();
        if (!status) {
            //Xóa dữ liệu trước đó
            edit.clear();
        } else {
            //Lưu dữ liệu
            edit.putString("USERNAME",u);
            edit.putString("PASSWORD",p);
            edit.putBoolean("REMEMBER",status);
            edit.putInt("ROLE", role);
        }
        //Lưu lại toàn bộ
        edit.commit();
    }
}