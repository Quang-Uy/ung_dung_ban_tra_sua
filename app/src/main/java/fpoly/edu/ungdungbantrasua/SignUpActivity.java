package fpoly.edu.ungdungbantrasua;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import fpoly.edu.ungdungbantrasua.DAO.AdminDAO;
import fpoly.edu.ungdungbantrasua.DAO.KhachHangDAO;

public class SignUpActivity extends AppCompatActivity {

    private AdminDAO adminDAO;
    TextView tv_Signin;
    Spinner spinner_role;
    String value_role;
    int role_position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //Ánh xạ
        EditText edt_signup_ten = findViewById(R.id.edt_signup_ten);
        EditText edt_signup_tendangnhap = findViewById(R.id.edt_signup_tendangnhap);
        EditText edt_signup_matkhau = findViewById(R.id.edt_signup_matkhau);
        Button btnSignUp = findViewById(R.id.btn_signup);
        tv_Signin = findViewById(R.id.tv_sign_in);
        
        adminDAO = new AdminDAO(this);

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
                Toast.makeText(SignUpActivity.this, role_position + value_role, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //lấy DL
                String ten = edt_signup_ten.getText().toString();
                String tendangnhap = edt_signup_tendangnhap.getText().toString();
                String matkhau = edt_signup_matkhau.getText().toString();

                //Kiểm tra trống
                if (ten.isEmpty()||tendangnhap.isEmpty()||matkhau.isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    boolean check = adminDAO.Register(ten, tendangnhap, matkhau, String.valueOf(role_position));
                    if (check) {
                        Toast.makeText(SignUpActivity.this, "Tạo tài khoản thành công", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(SignUpActivity.this, "Tên đăng nhập đã tồn tại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        tv_Signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
            }
        });
    }
}