package com.example.myapplication;


import static com.example.myapplication.Database.DAO_Work.capnhatgioKetThuc;
import static com.example.myapplication.Database.DAO_Work.saveDataWork;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.myapplication.Model.Users;
import com.example.myapplication.Model.Work;
import com.example.myapplication.ui.cuahang.CuahangFragment;
import com.example.myapplication.ui.dathang.DathangFragment;
import com.example.myapplication.ui.qlns.QuanLyNhanSuFragment;
import com.example.myapplication.ui.qlsp.QuanLySanPhamFragment;
import com.example.myapplication.ui.trangchu.TrangchuFragment;
import com.github.angads25.toggle.interfaces.OnToggledListener;
import com.github.angads25.toggle.model.ToggleableView;
import com.github.angads25.toggle.widget.LabeledSwitch;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;

public class MonitorCF extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView bottomNavigationView;
    NavigationView navigationView;
    private NavController navController;

    private static final int Fragment_CuaHang = 1;
    private static final int Fragment_DatHang = 2;
    private static final int Fragment_TrangChu = 3;
    private static final int Fragment_QuanLySanPham = 4;
    private static final int Fragment_QuanLyNhanSu = 5;

    private int currentFragment = Fragment_TrangChu;
    Realm realm;
    Menu navMenu;
    MenuItem menuItem;
    TextView txt_manv, txt_tenNV;

    View headerview;
    LabeledSwitch btn_on_off;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        replaceFragment(new NavHostFragment());
        setContentView(R.layout.activity_monitor_cf);
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_left);
        //lấy header trong nav
        headerview = navigationView.getHeaderView(0);
        //ánh xạ thông qua headerview
        txt_tenNV = headerview.findViewById(R.id.txt_user_nav);
        txt_manv = headerview.findViewById(R.id.txt_sdt_nav);
        btn_on_off = headerview.findViewById(R.id.on_off);
        navMenu = navigationView.getMenu();
        menuItem = navMenu.findItem(R.id.quanlycuahang_nav);
        String userType = getIntent().getStringExtra("userType");

        realm = Realm.getDefaultInstance();

//        Calendar calendar = Calendar.getInstance();
//        calendar.set(Calendar.HOUR_OF_DAY, 8); // Set giờ bắt đầu là 8
//        calendar.set(Calendar.MINUTE, 0);
//        gioBatDau=calendar.getTime();

        Date gioBatDau = new Date();
        btn_on_off.setOnToggledListener(new OnToggledListener() {
            @Override
            public void onSwitched(ToggleableView toggleableView, boolean isOn) {
                if (isOn == true) {

                    SharedPreferences preferences = getSharedPreferences("User", MODE_PRIVATE);
                    int userId = preferences.getInt("user_id", 0);

                    Users users = realm.where(Users.class).equalTo("user_id", userId).findFirst();


                    saveDataWork(gioBatDau, users.getUser_id());



                    System.out.println("bắt đầu làm");
                } else {
                    SharedPreferences preferences = getSharedPreferences("User", MODE_PRIVATE);
                    int userId = preferences.getInt("user_id", 0);
                    SharedPreferences preferences1 = getSharedPreferences("Work", MODE_PRIVATE);
                    int userId1 = preferences.getInt("worktime_id", 0);


                    Users users = realm.where(Users.class).equalTo("user_id", userId).findFirst();

                    Work work = realm.where(Work.class).equalTo("worktime_id", userId1).findFirst();

                    capnhatgioKetThuc(new Date(), work.getWorktime_id());
                    System.out.println(work.getWorktime_id());


                }
            }
        });


        if (userType != null && userType.equals("Nhân viên")) {
            // Người dùng đã đăng nhập là Nhân viên, ẩn MenuItem
            menuItem.setVisible(false);
            System.out.println("Nhân viên");
        } else {
            // Người dùng chưa đăng nhập hoặc là Admin, hiển thị MenuItem
            menuItem.setVisible(true);
            System.out.println("Quản lý");
        }

        SharedPreferences preferences = getSharedPreferences("User", MODE_PRIVATE);
        int userId = preferences.getInt("user_id", 0);

        Users users = realm.where(Users.class).equalTo("user_id", userId).findFirst();

//        Work work=realm.where(Work.class).equalTo("users.user_id",users.getUser_id()).findFirst();

        txt_tenNV.setText(users.getHoTen());
        txt_manv.setText(String.valueOf(users.getUser_id()));

        BottomNavigationView bottomNavigationView1w = findViewById(R.id.Bottom_nv_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        bottomNavigationView1w.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                invalidateOptionsMenu();
                int id = item.getItemId();
                System.out.println(item.getItemId());
                if (id == R.id.nav_info) {
                    if (Fragment_TrangChu != currentFragment) {
                        replaceFragment(new TrangchuFragment());
                        currentFragment = Fragment_TrangChu;
                    }
                } else if (id == R.id.nav_set) {
                    if (Fragment_CuaHang != currentFragment) {
                        replaceFragment((new CuahangFragment()));
                        currentFragment = Fragment_CuaHang;
                    }

                } else if (id == R.id.nav_address) {
                    if (Fragment_DatHang != currentFragment) {
                        replaceFragment(new DathangFragment());
                        currentFragment = Fragment_DatHang;
                    }
                }

                DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
        replaceFragment(new TrangchuFragment());


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        System.out.println(item.getItemId());
        if (id == R.id.nav_home) {
            if (Fragment_TrangChu != currentFragment) {
                replaceFragment(new TrangchuFragment());
                currentFragment = Fragment_TrangChu;
            }
        } else if (id == R.id.nav_gallery) {
            if (Fragment_CuaHang != currentFragment) {
                replaceFragment((new DathangFragment()));
                currentFragment = Fragment_DatHang;
            }

        } else if (id == R.id.nav_slideshow) {
            if (Fragment_DatHang != currentFragment) {
                replaceFragment(new CuahangFragment());
                currentFragment = Fragment_CuaHang;
            }
        } else if (id == R.id.nav_QlySanPham) {


            SharedPreferences preferences = getSharedPreferences("User", MODE_PRIVATE);
            int userId = preferences.getInt("user_id", 0);


            realm = Realm.getDefaultInstance();
            Users users = realm.where(Users.class).equalTo("user_id", userId).findFirst();
            System.out.println(users);


            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, 16); // Set giờ bắt đầu là 8
            calendar.set(Calendar.MINUTE, 0);
            Date gioketthuc = new Date();
//            saveDataWork(gioBatDau,gioketthuc,users.getUser_id());
//
            DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
            Work work = realm.where(Work.class).equalTo("users.user_id", userId).findFirst();

            RealmResults<Work> works = realm.where(Work.class).equalTo("users.user_id", userId).findAll();
            System.out.println(works + "\n");
            if (users.getChucVu().equals("Quản lý")) {
                Toast.makeText(this, "Trang quản lý", Toast.LENGTH_SHORT).show();
                if (Fragment_QuanLySanPham != currentFragment) {
                    replaceFragment(new QuanLySanPhamFragment());
                    currentFragment = Fragment_QuanLySanPham;
                }
            } else {

                Toast.makeText(this, "Trang này không dành cho bạn", Toast.LENGTH_SHORT).show();
            }

        } else if (id == R.id.nav_logout) {

            SharedPreferences preferences = getSharedPreferences("User", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.remove("user_id");
            editor.apply();

            Intent i = new Intent(MonitorCF.this, SignIn.class);
            startActivity(i);
        } else if (id == R.id.nav_info) {
            Intent i = new Intent(MonitorCF.this, UpdateInfoUserActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_set) {
            Intent i = new Intent(MonitorCF.this, SignIn.class);
            startActivity(i);

        } else if (id == R.id.nav_QlyNhanSu) {
            if (Fragment_QuanLyNhanSu != currentFragment) {
                replaceFragment(new QuanLyNhanSuFragment());
                currentFragment = Fragment_QuanLyNhanSu;
            }
        }

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);


        return true;
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
}