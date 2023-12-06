package fpoly.edu.ungdungbantrasua;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import fpoly.edu.ungdungbantrasua.Fragment.Admin.DoanhThuFragment;
import fpoly.edu.ungdungbantrasua.Fragment.Admin.DonHangFragment;
import fpoly.edu.ungdungbantrasua.Fragment.Admin.LoaiSanPhamFragment;
import fpoly.edu.ungdungbantrasua.Fragment.Admin.NhanVienFragment;
import fpoly.edu.ungdungbantrasua.Fragment.Admin.SanPhamFragment;
import fpoly.edu.ungdungbantrasua.Fragment.Admin.TopFragment;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    private static final int FRAGMENT_QLSP = 0;
    private static final int FRAGMENT_QLLSP = 1;
    private static final int FRAGMENT_QLDH = 2;
    private static final int FRAGMENT_QLNV = 3;
    private static final int FRAGMENT_TOP = 4;
    private static final int FRAGMENT_DOANHTHU = 5;
    private static final int FRAGMENT_DANGXUAT = 6;

    private int mCurrenFragment = FRAGMENT_QLSP;
    String role1;
    TextView txt_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.toobar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        //Fragment chính
        FragmentManager manager = getSupportFragmentManager();
        SanPhamFragment sanPhamFragment = new SanPhamFragment();
        manager.beginTransaction().replace(R.id.content_frame,sanPhamFragment).commit();

        //Code bắt sk khi click vào item nav
        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener) this);

        replaceFragment(new SanPhamFragment());
        navigationView.getMenu().findItem(R.id.nav_qlsp).setChecked(true);

        Intent intent = getIntent();
        role1 = intent.getStringExtra("role");
        ReadFile(role1);
        txt_user = navigationView.getHeaderView(0).findViewById(R.id.txt_user);
        txt_user.setText("Xin chào " + role1);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        //Click item
        if (id == R.id.nav_qlsp) {
            if (mCurrenFragment != FRAGMENT_QLSP) {
                replaceFragment(new SanPhamFragment());
                mCurrenFragment = FRAGMENT_QLSP;
            }
        } else if (id == R.id.nav_qllsp) {
            if (mCurrenFragment != FRAGMENT_QLLSP) {
                replaceFragment(new LoaiSanPhamFragment());
                mCurrenFragment = FRAGMENT_QLLSP;
            }
        } else if (id == R.id.nav_qldh) {
            if (mCurrenFragment != FRAGMENT_QLDH) {
                replaceFragment(new DonHangFragment());
                mCurrenFragment = FRAGMENT_QLDH;
            }
        } else if (id == R.id.nav_qlnv) {
            if (mCurrenFragment != FRAGMENT_QLNV) {
                replaceFragment(new NhanVienFragment());
                mCurrenFragment = FRAGMENT_QLNV;
            }
        } else if (id == R.id.nav_top) {
            if (mCurrenFragment != FRAGMENT_TOP) {
                replaceFragment(new TopFragment());
                mCurrenFragment = FRAGMENT_TOP;
            }
        } else if (id == R.id.nav_doanhthu) {
            if (mCurrenFragment != FRAGMENT_DOANHTHU) {
                replaceFragment(new DoanhThuFragment());
                mCurrenFragment = FRAGMENT_DOANHTHU;
            }
        } else if (id == R.id.nav_dangxuat) {
            startActivity(new Intent(HomeActivity.this, LoginActivity.class));
        }

        //Thay đổi Tilte trên Toolbar
        getSupportActionBar().setTitle(item.getTitle());

        //Đóng drawer
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    //Ấn back ko bị thoát app
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, fragment);
        transaction.commit();
    }

    private void ReadFile(String username) {
        SharedPreferences sharedPreferences = getSharedPreferences("user_use", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username_user", username);
        editor.apply();
    }
}