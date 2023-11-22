package fpoly.edu.ungdungbantrasua;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import fpoly.edu.ungdungbantrasua.DAO.KhachHangDAO;

public class SignUpActivity extends AppCompatActivity {

    private KhachHangDAO khachHangDAO;
    TextView tv_Signin;

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
        
        khachHangDAO = new KhachHangDAO(this);
        
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
                    boolean check = khachHangDAO.Register(ten, tendangnhap, matkhau);
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