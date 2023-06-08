package com.example.myapplication.ui.dathang;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adapter.adapterGioHang;
import com.example.myapplication.Adapter.adapterHoaDon;
import com.example.myapplication.Model.GioHang;
import com.example.myapplication.Model.HoaDon;
import com.example.myapplication.Model.Users;
import com.example.myapplication.R;
import com.example.myapplication.ui.qlsp.AddSPActivity;
import com.example.myapplication.ui.trangchu.GioHangMainActivity;

import io.realm.Realm;
import io.realm.RealmResults;


public class DathangFragment extends Fragment {

    Toolbar toolbar;
    RecyclerView recyclerView;
    Realm realm;
    RealmResults<HoaDon> hoaDon;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gio_hang, container, false);
        toolbar = view.findViewById(R.id.toolbar_giohang);
        recyclerView = view.findViewById(R.id.rcv_TypeCoffee);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        AppCompatActivity compatActivity = (AppCompatActivity) getActivity();
        compatActivity.setSupportActionBar(toolbar);
        compatActivity.getSupportActionBar().setTitle("Hóa đơn");

        realm = Realm.getDefaultInstance();
        hoaDon = realm.where(HoaDon.class).findAll();

        adapterHoaDon hoaDon1 = new adapterHoaDon(hoaDon);
        recyclerView.setAdapter(hoaDon1);

        realm.close();


        return view;
    }
    private BroadcastReceiver hoadonUpdatedReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Cập nhật lại danh sách hóa đơn và UI tương ứng
            loadHoaDonList(); // Hàm để tải lại danh sách hóa đơn
             // Hàm để cập nhật UI
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        // Đăng ký BroadcastReceiver
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(
                hoadonUpdatedReceiver,
                new IntentFilter("com.example.myapplication.ui.trangchu.ChiTietHoaDon")
        );
    }

    @Override
    public void onPause() {
        super.onPause();
        // Hủy đăng ký BroadcastReceiver
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(hoadonUpdatedReceiver);
    }

    private void loadHoaDonList() {
        // Thực hiện truy vấn Realm để lấy danh sách hóa đơn
        Realm realm = Realm.getDefaultInstance();
        RealmResults<HoaDon> hoaDonList = realm.where(HoaDon.class).findAll();

        // Cập nhật danh sách hóa đơn
        adapterHoaDon hoaDon2 = new adapterHoaDon(hoaDonList);
        recyclerView.setAdapter(hoaDon2);

        realm.close();
    }


}