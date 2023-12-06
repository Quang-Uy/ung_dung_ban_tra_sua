package fpoly.edu.ungdungbantrasua;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.RelativeLayout;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;

import fpoly.edu.ungdungbantrasua.Fragment.KhachHang.GioHangFragment;
import fpoly.edu.ungdungbantrasua.Fragment.KhachHang.HomeFragment;
import fpoly.edu.ungdungbantrasua.Fragment.KhachHang.ThongTinCaNhanFragment;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class KhachHangHomeActivity extends AppCompatActivity {

    MeowBottomNavigation bottomNavigation;
    RelativeLayout khachhang_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_khach_hang_home);


        khachhang_layout = findViewById(R.id.khachhang_layout);


        bottomNavigation = findViewById(R.id.bottomNavigation);

        replace(new HomeFragment());

        bottomNavigation.show(1, true);//Màn hình chính

        bottomNavigation.add(new MeowBottomNavigation.Model(1, R.drawable.ic_home));
        bottomNavigation.add(new MeowBottomNavigation.Model(2, R.drawable.ic_giohang));
        bottomNavigation.add(new MeowBottomNavigation.Model(3, R.drawable.ic_thongtincanhan));

        BottonNavigation();
        khachhang_layout.setBackgroundColor(Color.parseColor("#FFFBF5"));
    }

    private void BottonNavigation() {
        bottomNavigation.setOnClickMenuListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {
                switch (model.getId()) {
                    case 1:

                        replace(new HomeFragment());
                        khachhang_layout.setBackgroundColor(Color.parseColor("#FFFBF5"));
                        break;

                    case 2:

                        replace(new GioHangFragment());
                        khachhang_layout.setBackgroundColor(Color.parseColor("#FFFBF5"));
                        break;

                    case 3:

                        replace(new ThongTinCaNhanFragment());
                        khachhang_layout.setBackgroundColor(Color.parseColor("#4F6F52"));
                        break;
                }

                return null;
            }
        });
    }

    private void replace(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_layout, fragment);
        transaction.commit();
    }
}