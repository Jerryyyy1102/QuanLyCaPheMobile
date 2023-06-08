package com.example.myapplication.ui.trangchu;

import static com.example.myapplication.Database.DAO_HoaDon.capnhattrangthaiHoaDon;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adapter.adapterChiTietHoaDon;
import com.example.myapplication.Adapter.adapterGioHang;
import com.example.myapplication.Model.ChiTietHoaDon;
import com.example.myapplication.Model.GioHang;
import com.example.myapplication.Model.HoaDon;
import com.example.myapplication.Model.Users;
import com.example.myapplication.MonitorCF;
import com.example.myapplication.R;
import com.example.myapplication.ui.dathang.DathangFragment;

import io.realm.Realm;
import io.realm.RealmResults;

public class ChiTietHoaDonActivity extends AppCompatActivity {
    RecyclerView rcv_cthoaDon;
    Button btn_hoanthanh;
    Realm realm;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activiti_chitiethoadon);
        rcv_cthoaDon = findViewById(R.id.rcv_giohang);
        btn_hoanthanh = findViewById(R.id.btn_HoanThanhDon_CTHD);
        rcv_cthoaDon.setLayoutManager(new LinearLayoutManager(this));


        realm = Realm.getDefaultInstance();
        SharedPreferences preferences = getSharedPreferences("ListHD", MODE_PRIVATE);
        int sp_Id = preferences.getInt("maHoaDon", 0);

        HoaDon hd=realm.where(HoaDon.class).equalTo("maHoaDon",sp_Id).findFirst();
        if(hd.isChecktrangthai()==true)
        {
            btn_hoanthanh.setVisibility(View.INVISIBLE);
        }


        RealmResults<ChiTietHoaDon> cthd = realm.where(ChiTietHoaDon.class).equalTo("maHoaDon", sp_Id).findAll();
        btn_hoanthanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                capnhattrangthaiHoaDon(sp_Id);
                Intent i=new Intent(ChiTietHoaDonActivity.this, MonitorCF.class);
                startActivity(i);



            }
        });
        adapterChiTietHoaDon gioHang = new adapterChiTietHoaDon(ChiTietHoaDonActivity.this, cthd);
        rcv_cthoaDon.setAdapter(gioHang);

        realm.close();

    }
}
