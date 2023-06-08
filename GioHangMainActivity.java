package com.example.myapplication.ui.trangchu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import com.example.myapplication.Adapter.adapterGioHang;
import com.example.myapplication.Model.GioHang;
import com.example.myapplication.Model.Users;
import com.example.myapplication.R;

import io.realm.DefaultCompactOnLaunchCallback;
import io.realm.Realm;
import io.realm.RealmResults;

public class GioHangMainActivity extends AppCompatActivity {

    Realm realm;
    static RealmResults<GioHang> gh;
    TextView txt_tongtien;
    RecyclerView recycler_giohang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang_main);
        recycler_giohang = findViewById(R.id.rcv_giohang);
        recycler_giohang.setLayoutManager(new LinearLayoutManager(this));
        SharedPreferences preferences = getSharedPreferences("User", MODE_PRIVATE);
        int userId = preferences.getInt("user_id", 0);

        realm = Realm.getDefaultInstance();
        Users users = realm.where(Users.class).equalTo("user_id",userId).findFirst();
        gh = realm.where(GioHang.class).findAll();

        adapterGioHang gioHang = new adapterGioHang(GioHangMainActivity.this, gh);
        recycler_giohang.setAdapter(gioHang);
        recycler_giohang.scrollToPosition(gh.size());
        realm.close();

        //tên sản phẩm, tên user,tổng tiền từng sản phẩm, nút button thanh toán
    }
}