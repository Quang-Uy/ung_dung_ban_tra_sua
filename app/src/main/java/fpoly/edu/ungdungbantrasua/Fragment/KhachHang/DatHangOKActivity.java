package fpoly.edu.ungdungbantrasua.Fragment.KhachHang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.time.Instant;

import fpoly.edu.ungdungbantrasua.KhachHangHomeActivity;
import fpoly.edu.ungdungbantrasua.R;

public class DatHangOKActivity extends AppCompatActivity {

    private ImageView imageView;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplication(), KhachHangHomeActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dat_hang_okactivity);

        Button comtinue = findViewById(R.id.btn_datHangOK);
        comtinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplication(), KhachHangHomeActivity.class));
            }
        });
    }
}